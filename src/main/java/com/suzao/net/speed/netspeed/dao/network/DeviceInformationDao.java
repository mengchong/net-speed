package com.suzao.net.speed.netspeed.dao.network;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suzao.net.speed.netspeed.base.BaseDao;
import com.suzao.net.speed.netspeed.bo.network.DeviceInformationBo;
import com.suzao.net.speed.netspeed.vo.network.DeviceInformationVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DeviceInformationDao extends BaseDao<DeviceInformationBo, DeviceInformationVo>, BaseMapper<DeviceInformationBo> {

}
