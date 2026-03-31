package com.suzao.net.speed.netspeed.bo.network;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @name PingSpeedBo
 * @author mc
 * @date 2022/4/9 22:01
 * @version 1.0
 **/
@Data
@ApiModel
public class PingSpeedBo {
    /**
     * 主键
     */
    @ApiModelProperty(hidden = true)
    private Long Id;
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
     * 时间
     */
    @ApiModelProperty(hidden = true)
    private Date time;
    /**
     * 包大小
     */
    @ApiModelProperty(value = "包大小",dataType = "String",example = "64")
    private String size;
    /**
     * 发包个数
     */
    @ApiModelProperty(value = "发包个数",dataType = "String",example = "5")
    private String sendQuantity;
    /**
     * 接收包数
     */
    @ApiModelProperty(value = "接收包数",dataType = "String",example = "5")
    private String receiveQuantity;
    /**
     * 丢包率
     */
    @ApiModelProperty(value = "丢包率",dataType = "String",example = "0%")
    private String packetLossRate;
    /**
     * 平均RTT
     */
    @ApiModelProperty(value = "平均RTT",dataType = "String",example = "25.541")
    private String avgRTT;
    /**
     * 最小RTT
     */
    @ApiModelProperty(value = "最小RTT",dataType = "String",example = "16.341")
    private String minRTT;
    /**
     * 最大RTT
     */
    @ApiModelProperty(value = "最大RTT",dataType = "String",example = "36.341")
    private String maxRTT;
    /**
     * 均差RTT
     */
    @ApiModelProperty(value = "最大RTT",dataType = "String",example = "16.341")
    private String meanDifference;
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
