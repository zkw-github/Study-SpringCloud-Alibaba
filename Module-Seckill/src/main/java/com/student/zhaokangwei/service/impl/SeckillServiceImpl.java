package com.student.zhaokangwei.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.student.zhaokangwei.config.RabbitMQConfig;
import com.student.zhaokangwei.entity.Project;
import com.student.zhaokangwei.entity.ProjectOrder;
import com.student.zhaokangwei.entity.Subrecord;
import com.student.zhaokangwei.exception.ServiceValidationException;
import com.student.zhaokangwei.mapper.IProjectMapper;
import com.student.zhaokangwei.service.IProjectOrderService;
import com.student.zhaokangwei.service.IProjectService;
import com.student.zhaokangwei.service.ISeckillService;
import com.student.zhaokangwei.service.ISubrecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

/**
 * 秒杀业务实现类
 */
@Slf4j
@Service
public class SeckillServiceImpl implements ISeckillService {
    @Resource
    IProjectService projectService;
    @Resource
    IProjectOrderService projectOrderService;
    @Resource
    ISubrecordService subrecordService;
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    RabbitTemplate rabbitTemplate;

    @Override
    public void buy(Integer projectId) {
        //先获取请求者身份
        Integer userId=Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        log.info("当前请求者：" + userId);

        //先判断已剩数量是否大于0，才执行下面的第2、3步骤
        Project project=projectService.getById(projectId);

        //模拟部分用户网速快慢
        Random random=new Random();
        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //先进行时间的判断，如果还未开始，则不允许抢购
        if(project.getStarttime().toEpochSecond(ZoneOffset.UTC)>LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)){
            throw new ServiceValidationException("客观不要着急，抢购还未开始，请在" + project.getStarttime() + "开始抢购！！！",402);
        }
        //在这里，去project_order表里面去查询，根据user_id字段查询，如果查到了数据，则认定为重复下单
//        QueryWrapper queryWrapper=new QueryWrapper();
//        queryWrapper.eq("user_id", userId);
//        int orderCount=projectOrderService.count(queryWrapper);
//        if(orderCount > 0){
//            //说明当前请求者已经下过单了
//            throw new ServiceValidationException("不好意，你已经下过单了，下次活动再来吧！！", 402);
//        }

        String key="project_" + projectId + "_" + userId;
        if(redisTemplate.hasKey(key)){
            throw new ServiceValidationException("不好意，你已经下过单了，下次活动再来吧！！", 402);
        }else {
            redisTemplate.opsForValue().set(key, LocalDateTime.now());
        }

        //1.添加记录到subrecord表
        Subrecord subrecord=new Subrecord();
        subrecord.setId(0);
        subrecord.setProjectId(projectId);
        subrecord.setUserId(userId);
        subrecord.setSubmittime(LocalDateTime.now());
        subrecordService.save(subrecord);

        //从Redis中取出projectGoodsCount数据的值，并且在取值的同时自减1
        Integer projectGoodsCount=Integer.parseInt(redisTemplate.opsForValue().decrement("projectGoodsCount", 1).toString());
        log.info("projectGoodsCount: " + projectGoodsCount);
        if(projectGoodsCount > -1){
            //不再在这里去操作mysql如改count、插订单这些工作了，而是向RabbitMQ发送一条信息
            //1.构建一个即将要发送的消息对象
            JSONObject message=new JSONObject(true);
            message.put("projectId",projectId);
            message.put("userId", userId);
            rabbitTemplate.convertAndSend(RabbitMQConfig.exchangeName, "Seckill",message.toJSONString());

        }else {
            throw new ServiceValidationException("很遗憾，您手慢了，这次未抢购到，下次加油", 402);
        }


    }
    @Resource
    IProjectMapper projectMapper;

    @Override
    public void createOrder(Integer projectId, Integer userId) {
        //2.修改抢购活动和商品数量，让其-1
        projectMapper.decrementCount(1, projectId);

        //3.添加记录到project_order表
        ProjectOrder projectOrder=new ProjectOrder();
        projectOrder.setId(0);
        projectOrder.setProjectId(projectId);
        projectOrder.setUserId(userId);
        projectOrder.setSubmittime(LocalDateTime.now());
        projectOrderService.save(projectOrder);

        log.info("最后在这里通过WebSocket通知前端，告诉用户订单已创建成功。");
    }
}
