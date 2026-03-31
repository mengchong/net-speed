package com.suzao.net.speed.netspeed.vo.network;

import com.suzao.net.speed.netspeed.bo.network.PassValueBo;
import lombok.Data;

import java.util.Date;

@Data
public class PassValueVo extends PassValueBo {

    //名称
    private String name;
    //场景名称
    private String sceneName;
    //区域名称
    private String areaName;
    //ap名称
    private String apName;
    //网络测速合格数量
    private long networkSpeedQualified;
    //网络测速不合格数量
    private long networkSpeedFailed;
    //网络测速数量统计
    private long networkSpeedSum;
    //体验测速合格数量
    private long experienceSpeedQualified;
    //体验测速不合格数量
    private long experienceSpeedFailed;
    //ping测速数量统计
    private long experienceSpeedSum;
    //ping测速合格数量
    private long pingSpeedQualified;
    //ping测速不合格数量
    private long pingSpeedFailed;
    //ping测速数量统计
    private long pingSpeedSum;
    //统计场景区域下记录总数
    private long sum;
    //统计AP总数
    private long apCount;
    //统计测试ap数量
    private long testApCount;
    //导出标识
    private String export;
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
}
