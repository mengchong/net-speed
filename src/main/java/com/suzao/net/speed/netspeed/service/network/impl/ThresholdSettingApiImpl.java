package com.suzao.net.speed.netspeed.service.network.impl;

import com.suzao.net.speed.netspeed.bo.network.ThresholdSettingBo;
import com.suzao.net.speed.netspeed.dao.network.ThresholdSettingDao;
import com.suzao.net.speed.netspeed.service.network.ThresholdSettingApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ThresholdSettingApiImpl implements ThresholdSettingApi {

    @Autowired
    private ThresholdSettingDao thresholdSettingDao;


    @Override
    public ThresholdSettingBo getThresholdSetting(Long id) {
        return this.thresholdSettingDao.getThresholdSetting(id);
    }

    @Override
    public int insert(ThresholdSettingBo settingBo) {
        return this.thresholdSettingDao.insert(settingBo);
    }

    @Override
    public int update(ThresholdSettingBo settingBo) {
        return this.thresholdSettingDao.update(settingBo);
    }
}
