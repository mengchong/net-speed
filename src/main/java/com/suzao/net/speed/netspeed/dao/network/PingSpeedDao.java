package com.suzao.net.speed.netspeed.dao.network;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suzao.net.speed.netspeed.base.BaseDao;
import com.suzao.net.speed.netspeed.bo.network.PingSpeedBo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import com.suzao.net.speed.netspeed.vo.network.PingSpeedVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @name PingSpeedDao
 * @author mc
 * @date 2022/4/9 20:37
 * @version 1.0
 **/
@Mapper
@Repository
public interface PingSpeedDao extends BaseDao<PingSpeedBo, PingSpeedVo>,BaseMapper<PingSpeedBo> {

    /**
     * 查询ping测速场景下合格和不合格数量
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
     * 查询大于等于丢包率标准的信息
     * @param vo
     * @return
     */
    List<PingSpeedVo> selectGTETData(PassValueVo vo);

    /**
     * 查询小于丢包率标准的信息
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
