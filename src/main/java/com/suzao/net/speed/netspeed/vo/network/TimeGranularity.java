package com.suzao.net.speed.netspeed.vo.network;

import lombok.Data;

import java.util.Date;

public interface TimeGranularity {

    Date getStartTime() ;
    void setStartTime(Date startTime);

    Date getEndTime();
    void setEndTime(Date endTime);
}
