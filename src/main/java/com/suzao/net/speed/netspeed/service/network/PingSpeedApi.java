package com.suzao.net.speed.netspeed.service.network;


import com.suzao.net.speed.netspeed.base.BaseApi;
import com.suzao.net.speed.netspeed.bo.network.PingSpeedBo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import com.suzao.net.speed.netspeed.vo.network.PingSpeedVo;

import java.util.List;


public interface PingSpeedApi extends BaseApi<PingSpeedBo,PingSpeedVo> {

    /**
     * 查询测速下合格和不合格数量
     * @param vo
     * @return
     */
    List<PingSpeedVo> pingPassvalue(NetworkSpeedVo vo);

    /**
     * 查询ping测速区域下合格和不合格数量
     * @param vo
     * @return
     */
    List<PingSpeedVo> pingPassvalueArea(NetworkSpeedVo vo);

    /**
     * 查询ping测速AP下合格和不合格数量
     * @param vo
     * @return
     */
    List<PingSpeedVo> pingPassvalueAP(NetworkSpeedVo vo);

    /**
     * 查询场景/区域下所有测试ap总数
     * @param vo
     * @return
     */
    List<PassValueVo> selectAPCountByTestFromPingSpeed(NetworkSpeedVo vo);

    /**
     * 查询大于等于网络延迟标准的信息
     * @param vo
     * @return
     */
    List<PingSpeedVo> selectGTETData(PassValueVo vo);

    /**
     * 查询小于网络延迟标准的信息
     * @param vo
     * @return
     */
    List<PingSpeedVo> selectLTData(PassValueVo vo);

    /**
     * 查询网络延迟标准的信息
     * @param vo
     * @return
     */
    List<PingSpeedVo> selectData(PassValueVo vo);
}
