package com.suzao.net.speed.netspeed.vo.network;


import lombok.Data;

@Data
public class StatisticalUser {
    private String name;
    private String osType;
    private String source;
    private String longTime;
    private String inFlow;
    private String outFlow;

}
