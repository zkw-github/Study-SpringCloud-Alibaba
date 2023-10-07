package com.student.zhaokangwei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.zhaokangwei.entity.Project;
import com.student.zhaokangwei.mapper.IProjectMapper;
import com.student.zhaokangwei.service.IProjectService;
import org.springframework.stereotype.Service;

/**
 * 抢购活动 业务实现类
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<IProjectMapper, Project> implements IProjectService {
}
