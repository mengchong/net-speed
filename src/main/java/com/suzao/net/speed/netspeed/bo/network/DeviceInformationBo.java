package com.suzao.net.speed.netspeed.bo.network;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @name DeviceInformationBo
 * @author mc
 * @date 2022/4/9 22:01
 * @version 1.0
 **/
@ApiModel
@Data
public class DeviceInformationBo {
    /**
     * 主键
     */
    @ApiModelProperty(hidden = true)
    private Long id;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址",dataType = "String",example = "中国北京市朝阳区")
    private String address;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度",dataType = "String",example = "23.152")
    private String longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度",dataType = "String",example = "23.152")
    private String latitude;
    /**
     * 本机IP
     */
    @ApiModelProperty(value = "本机IP",dataType = "String",example = "192.168.0.1")
    private String localIP;
    /**
     * 外网IP
     */
    @ApiModelProperty(value = "外网IP",dataType = "String",example = "121.16.13.1")
    private String extranetIP;
    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统",dataType = "String",example = "Android 9")
    private String operatingSystem;
    /**
     * 生产商
     */
    @ApiModelProperty(value = "生产商",dataType = "String",example = "Xiaomi")
    private String manufacturer;
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号",dataType = "String",example = "PBC7DU88")
    private String deviceID;
    /**
     * 无线名称
     */
    @ApiModelProperty(value = "无线名称",dataType = "String",example = "Vnet-cmcc")
    private String wlanName;
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
     * 用户
     */
    @ApiModelProperty(value = "用户",dataType = "String",example = "zhangsan")
    private String username;


}
