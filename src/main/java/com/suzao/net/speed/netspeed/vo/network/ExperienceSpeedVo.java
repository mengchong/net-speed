package com.suzao.net.speed.netspeed.vo.network;


import com.suzao.net.speed.netspeed.bo.network.ExperienceSpeedBo;

import java.util.Date;

public class ExperienceSpeedVo extends ExperienceSpeedBo implements TimeGranularity{

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
     * 合格数量和不合格数量
     */
    private long passValue;

    /**
     * 导出标识
     */
    private String export;

    @Override
    public Date getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getApName() {
        return apName;
    }

    public void setApName(String apName) {
        this.apName = apName;
    }

    public long getPassValue() {
        return passValue;
    }

    public void setPassValue(long passValue) {
        this.passValue = passValue;
    }

    public String getExport() {
        return export;
    }

    public void setExport(String export) {
        this.export = export;
    }
}
