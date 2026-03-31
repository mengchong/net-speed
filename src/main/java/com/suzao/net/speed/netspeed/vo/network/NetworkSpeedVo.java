package com.suzao.net.speed.netspeed.vo.network;

import com.suzao.net.speed.netspeed.bo.network.NetworkSpeedBo;
import lombok.Data;

import java.util.Date;

@Data
public class NetworkSpeedVo extends NetworkSpeedBo implements TimeGranularity{

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

    /**
     * 合格和不合格数量
     */
    private long passValue;

    /**
     * 导出标识
     */
    private String export;
}
