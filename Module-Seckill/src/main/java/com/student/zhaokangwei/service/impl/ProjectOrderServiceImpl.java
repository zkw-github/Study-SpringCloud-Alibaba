package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.ProjectOrder;
import com.student.zhaokangwei.mapper.IProjectOrderMapper;
import com.student.zhaokangwei.service.IProjectOrderService;
import org.springframework.stereotype.Service;
/**
 * 抢购活动 订单业务实现类
 */
@Service
public class ProjectOrderServiceImpl extends ServiceImpl<IProjectOrderMapper, ProjectOrder> implements IProjectOrderService {
}
