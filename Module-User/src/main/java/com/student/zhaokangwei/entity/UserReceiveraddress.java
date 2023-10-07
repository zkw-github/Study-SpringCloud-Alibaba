package com.student.zhaokangwei.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户收货地址 实体类
 */
@Data
public class UserReceiveraddress implements Serializable {
    private Integer id;//收获ID
    private Integer userId;//用户ID
    private String addprovince;//收货省份
    private String addcity;//收货市
    private String addcounty;//收货区
    private String adddetail;//收获详细地址
    private String postcode;//邮政编码
    private String recipients;//收货人
    private String mobile;//收货人电话
    private Integer sort;//货物分类

}
