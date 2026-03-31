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
public class PassValueBo {

    @ApiModelProperty(hidden = true)
    private long id;
    /**
     * 网络测速合格值
     */
    @ApiModelProperty(value = "网络测速合格值",dataType = "String",example = "23")
    private String networkPassValue;
    /**
     * 丢包合格率
     */
    @ApiModelProperty(value = "丢包合格率",dataType = "String",example = "23")
    private String lostPacketRatePassValue;
    /**
     * 延迟合格率
     */
    @ApiModelProperty(value = "延迟合格率",dataType = "String",example = "23")
    private String delayPassValue;
    /**
     * 网络测速延迟合格率
     */
    @ApiModelProperty(value = "网络测速延迟合格率",dataType = "String",example = "23")
    private String networkDelayPassValue;
}
