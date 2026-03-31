package com.suzao.net.speed.netspeed.dao.network;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suzao.net.speed.netspeed.base.BaseDao;
import com.suzao.net.speed.netspeed.bo.network.SignalQualityBo;
import com.suzao.net.speed.netspeed.vo.network.SignalQualityVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @name NetworkSpeedDao
 * @author mc
 * @date 2022/4/9 20:36
 * @version 1.0
 **/
@Mapper
@Repository
public interface SignalQualityDao extends BaseMapper<SignalQualityBo>, BaseDao<SignalQualityBo, SignalQualityVo> {

}
