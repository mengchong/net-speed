package com.suzao.net.speed.netspeed.bo.network;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @name NetworkSpeedBo
 * @author mc
 * @date 2022/4/9 22:01
 * @version 1.0
 **/
@Data
@ApiModel
public class NetworkSpeedBo implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(hidden = true)
    private Long id;
    /**
     * 网络延迟
     */
    @ApiModelProperty(value = "网络延迟",dataType = "String",example = "15.6 ms")
    private String networkDelay;
    /**
     * 下载速度
     */
    @ApiModelProperty(value = "下载速度",dataType = "String",example = "3MB/s")
    private String downloadSpeed;
    /**
     * 上传速度
     */
    @ApiModelProperty(value = "上传速度",dataType = "String",example = "2MB/s")
    private String uploadSpeed;
    /**
     * 测试时间
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

}
