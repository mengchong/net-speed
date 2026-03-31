package com.suzao.net.speed.netspeed.dao.network;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suzao.net.speed.netspeed.base.BaseDao;
import com.suzao.net.speed.netspeed.bo.network.NetworkSpeedBo;
import com.suzao.net.speed.netspeed.bo.network.SignalQualityBo;
import com.suzao.net.speed.netspeed.bo.network.SpeedRecordBo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @name NetworkSpeedDao
 * @author mc
 * @date 2022/4/9 20:36
 * @version 1.0
 **/
@Mapper
@Repository
public interface NetworkSpeedDao extends BaseMapper<NetworkSpeedBo>, BaseDao<NetworkSpeedBo, NetworkSpeedVo> {

    /**
     * 查询测速场景下的合格和不合格数量
     * @param vo
     * @return
     */
    List<NetworkSpeedVo> networkPassvalue(NetworkSpeedVo vo);

    /**
     * 查询测速区域下的合格和不合格数量
     * @param vo
     * @return
     */
    List<NetworkSpeedVo> networkPassvalueArea(NetworkSpeedVo vo);

    /**
     * 查询测速ap下的合格和不合格数量
     * @param vo
     * @return
     */
    List<NetworkSpeedVo> networkPassvalueAP(NetworkSpeedVo vo);

    /**
     * 查询场景/区域下所有测试ap总数
     * @param vo
     * @return
     */
    List<PassValueVo> selectAPCountByTestFromNetworkSpeed(NetworkSpeedVo vo);

    /**
     * 查询大于等于网络延迟标准的信息
     * @param vo
     * @return
     */
    List<NetworkSpeedVo> selectGTETData(PassValueVo vo);

    /**
     * 查询小于网络延迟标准的信息
     * @param vo
     * @return
     */
    List<NetworkSpeedVo> selectLTData(PassValueVo vo);

    /**
     * 查询所有该场景/区域/ap信息
     * @param vo
     * @return
     */
    List<NetworkSpeedVo> selectData(PassValueVo vo);

    List<SpeedRecordBo> getSpeedRecord();
}
