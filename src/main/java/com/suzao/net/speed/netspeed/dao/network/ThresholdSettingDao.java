package com.suzao.net.speed.netspeed.dao.network;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suzao.net.speed.netspeed.bo.network.ThresholdSettingBo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @name PassValueDao
 * @author mc
 * @date 2022/4/9 20:37
 * @version 1.0
 **/
@Mapper
@Repository
public interface ThresholdSettingDao extends BaseMapper<ThresholdSettingBo> {

    ThresholdSettingBo getThresholdSetting(Long id);

    int insert(ThresholdSettingBo settingBo);

    int update(ThresholdSettingBo settingBo);
}
