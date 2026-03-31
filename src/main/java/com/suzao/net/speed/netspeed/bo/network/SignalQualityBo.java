package com.suzao.net.speed.netspeed.bo.network;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @name PassValueBo
 * @author mc
 * @date 2022/4/9 22:01
 * @version 1.0
 **/
@Data
@ApiModel
public class SignalQualityBo {

    @ApiModelProperty(hidden = true)
    private long id;
    /**
     * 访问点的地址
     */
    @ApiModelProperty(value = "访问点的地址",dataType = "String",example = "23")
    private String bssid;
    /**
     * 网络名称
     */
    @ApiModelProperty(value = "网络名称",dataType = "String",example = "23")
    private String ssid;
    /**
     * 描述了身份验证、密钥管理和访问点支持的加密方案
     */
    @ApiModelProperty(value = "描述了身份验证、密钥管理和访问点支持的加密方案",dataType = "String",example = "23")
    private String delayPassValue;
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

    @ApiModelProperty(value = "centerFreq0",dataType = "String",example = "xxx")
    private String centerFreq0;

    @ApiModelProperty(value = "centerFreq1",dataType = "String",example = "xxx")
    private String centerFreq1;

    @ApiModelProperty(value = "channelWidth",dataType = "String",example = "xxx")
    private String channelWidth;

    @ApiModelProperty(value = "frequency",dataType = "String",example = "xxx")
    private String frequency;

    @ApiModelProperty(value = "level",dataType = "String",example = "xxx")
    private String level;

    @ApiModelProperty(value = "operatorFriendlyName",dataType = "String",example = "xxx")
    private String operatorFriendlyName;

    @ApiModelProperty(value = "timestamp",dataType = "String",example = "xxx")
    private String timestamp;

    @ApiModelProperty(value = "venueName",dataType = "String",example = "xxx")
    private String venueName;

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



}
