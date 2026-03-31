package com.suzao.net.speed.netspeed.service.network;


import com.suzao.net.speed.netspeed.base.BaseApi;
import com.suzao.net.speed.netspeed.bo.network.ThresholdSettingBo;
import com.suzao.net.speed.netspeed.vo.network.ThresholdSettingVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @name PassValueDao
 * @author mc
 * @date 2022/4/9 20:37
 * @version 1.0
 **/
public interface ThresholdSettingApi {

    ThresholdSettingBo getThresholdSetting(Long id);

    int insert(ThresholdSettingBo settingBo);

    int update(ThresholdSettingBo settingBo);
}
