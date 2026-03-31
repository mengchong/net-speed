package com.suzao.net.speed.netspeed.bo.network;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @name ExperienceSpeedBo
 * @author mc
 * @date 2022/4/9 22:01
 * @version 1.0
 **/
@Data
@ApiModel
public class ExperienceSpeedBo {
    /**
     * 主键
     */
    @ApiModelProperty(hidden = true)
    private Long Id;
    /**
     * 平台
     */
    @ApiModelProperty(value = "平台",dataType = "String",example = "百度")
    private String platform;
    /**
     * 速度
     */
    @ApiModelProperty(value = "速度",dataType = "String",example = "1MB/s")
    private String speed;
    /**
     * 测速时间
     */
    @ApiModelProperty(hidden = true)
    private Date time;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址",dataType = "String",example = "中国北京市朝阳区")
    private String address;
    /**
     * mac地址
     */
    @ApiModelProperty(value = "mac地址",dataType = "String",example = "3C:4F:33:55:6F:CC")
    private String macAddress;
    /**
     * mac5G地址
     */
    @ApiModelProperty(value = "mac5G地址",dataType = "String",example = "6B:4F:33:55:6F:CC")
    private String macAddress5G;
    /**
     * 场景id
     */
    @ApiModelProperty(value = "场景id",dataType = "Integer",example = "304005")
    private Integer sceneId;
    /**
     * 区域id
     */
    @ApiModelProperty(value = "区域id",dataType = "Integer",example = "245010")
    private Integer areaId;
    /**
     * apid
     */
    @ApiModelProperty(value = "apid",dataType = "Integer",example = "830501")
    private Integer apId;
    /**
     * 测速人
     */
    @ApiModelProperty(value = "用户",dataType = "String",example = "zhangsan")
    private String speedometer;

    @ApiModelProperty(value = "状态",dataType = "String",example = "200")
    private String responseState;

    @ApiModelProperty(value = "建立链接时延",dataType = "String",example = "20")
    private String connectTime;

    @ApiModelProperty(value = "首包时延",dataType = "String",example = "20")
    private String redirectTime;

    @ApiModelProperty(value = "首屏时延",dataType = "String",example = "20")
    private String responseTime;

    @ApiModelProperty(value = "发送请求时延",dataType = "String",example = "20")
    private String requestTime;

    @ApiModelProperty(value = "解析域名时延",dataType = "String",example = "20")
    private String domainTime;

    @ApiModelProperty(value = "整页加载时延",dataType = "String",example = "20")
    private String webTime;

    @ApiModelProperty(value = "服务器IP",dataType = "String",example = "20")
    private String serviceIp;

    @ApiModelProperty(value = "感知情况",dataType = "String",example = "20")
    private String webSuccess;

}
