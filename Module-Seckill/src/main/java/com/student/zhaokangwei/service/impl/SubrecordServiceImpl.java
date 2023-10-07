package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.Subrecord;
import com.student.zhaokangwei.mapper.ISubrecordMapper;
import com.student.zhaokangwei.service.ISubrecordService;
import org.springframework.stereotype.Service;

/**
 * 抢购活动 提交记录 业务实现类
 */
@Service
public class SubrecordServiceImpl extends ServiceImpl<ISubrecordMapper, Subrecord> implements ISubrecordService {
}
