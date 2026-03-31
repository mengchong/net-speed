package com.suzao.net.speed.netspeed.bo.user;

import lombok.Data;

@Data
public class CountBo {
    private String labelType;
    private String labelName;
    private Integer count;
    private String startDate;
    private String endDate;
    private String type;
}
