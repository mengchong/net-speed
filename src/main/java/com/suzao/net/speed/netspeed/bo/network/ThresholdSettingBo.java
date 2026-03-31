package com.suzao.net.speed.netspeed.bo.network;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @name PassValueBo
 * @author mc
 * @date 2022/4/9 22:01
 * @version 1.0
 **/
@Data
@ApiModel
public class ThresholdSettingBo {

    @ApiModelProperty(value = "id",dataType = "String",example = "1")
    private long id;
    /**
     * 平均信号强度达标率设置
     */
    @ApiModelProperty(value = "平均信号强度达标率设置",dataType = "String",example = "80")
    private String signalValue;
    /**
     * 上行速率达标率设置
     */
    @ApiModelProperty(value = "上行速率达标率设置",dataType = "String",example = "90")
    private String upstreamValue;
    /**
     * 下行速率达标率设置
     */
    @ApiModelProperty(value = "下行速率达标率设置",dataType = "String",example = "44")
    private String downValue;
    /**
     * 网页成功率达标率设置
     */
    @ApiModelProperty(value = "网页成功率达标率设置",dataType = "String",example = "83")
    private String webSuccessValue;

    /**
     * 网页首页时延达标率设置
     */
    @ApiModelProperty(value = "网页首页时延达标率设置",dataType = "String",example = "73")
    private String webFirstValue;

    /**
     * 视频播放成功达标率设置
     */
    @ApiModelProperty(value = "视频播放成功达标率设置",dataType = "String",example = "63")
    private String videoValue;

    /**
     * Ping时延达标率设置
     */
    @ApiModelProperty(value = "Ping时延达标率设置",dataType = "String",example = "69")
    private String pingDelayValue;
    /**
     * Ping丢包率达标率设置
     */
    @ApiModelProperty(value = "Ping丢包率达标率设置",dataType = "String",example = "89")
    private String pingPackageValue;

    /**
     * 认证接入成功率达标率设置
     */
    @ApiModelProperty(value = "认证接入成功率达标率设置",dataType = "String",example = "99")
    private String authSuccessValue;

    /**
     * 认证接入时延达标率设置
     */
    @ApiModelProperty(value = "认证接入时延达标率设置",dataType = "String",example = "79")
    private String authDelayValue;
    /**
     * 认证成功上下区间
     */
    @ApiModelProperty(value = "认证成功上下区间",dataType = "String",example = "4")
    private String authSuccessSection;
    /**
     * 认证接入时延区间
     */
    @ApiModelProperty(value = "认证接入时延区间",dataType = "String",example = "5")
    private String authDelaySection;
}
