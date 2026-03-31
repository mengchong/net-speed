package com.suzao.net.speed.netspeed.bo.network;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @name NetworkSpeedBo
 * @author mc
 * @date 2022/4/9 22:01
 * @version 1.0
 **/
@Data
@ApiModel
public class SpeedRecordBo {

    /**
     * 测速时间
     */
    @ApiModelProperty(value = "测速时间",dataType = "String",example = "2022-07-09 17:52:00")
    private String speedTime;

    @ApiModelProperty(value = "测速指标",dataType = "String",example = "100MB/s")
    private String speedMetric;

    @ApiModelProperty(value = "测速类型",dataType = "String",example = "体验测速")
    private String speedType;

    @ApiModelProperty(value = "测速项",dataType = "String",example = "上下行")
    private String speedName;

}
