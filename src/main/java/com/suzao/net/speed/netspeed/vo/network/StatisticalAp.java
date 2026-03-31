package com.suzao.net.speed.netspeed.vo.network;


import lombok.Data;

@Data
public class StatisticalAp {
    private String name;
    private String mac;
    private String ipaddress;
    private String areaName;
    private String sceneName;
    private String longTime;
    private String inFlow;
    private String outFlow;
}
