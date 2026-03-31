package com.suzao.net.speed.netspeed.vo.network;

import com.suzao.net.speed.netspeed.bo.network.SignalQualityBo;
import lombok.Data;

import java.util.Date;


@Data
public class SignalQualityVo extends SignalQualityBo  implements TimeGranularity{
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 场景名称
     */
    private String sceneName;
    /**
     * 区域名称
     */
    private String areaName;

    /**
     *  ap名称
     */
    private String apName;
}
