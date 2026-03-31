package com.suzao.net.speed.netspeed.service.network;


import com.suzao.net.speed.netspeed.base.BaseApi;
import com.suzao.net.speed.netspeed.bo.network.NetworkSpeedBo;
import com.suzao.net.speed.netspeed.bo.network.SpeedRecordBo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;

import java.util.List;

/**
 * @name NetworkSpeedApi
 * @author mc
 * @date 2022/4/9 9:37
 * @version 1.0
 **/
public interface NetworkSpeedApi extends BaseApi<NetworkSpeedBo, NetworkSpeedVo> {

    /**
     * 查询测速场景下合格和不合格数量
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
