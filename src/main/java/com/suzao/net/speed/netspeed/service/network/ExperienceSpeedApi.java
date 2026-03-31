package com.suzao.net.speed.netspeed.service.network;


import com.suzao.net.speed.netspeed.base.BaseApi;
import com.suzao.net.speed.netspeed.bo.network.ExperienceSpeedBo;
import com.suzao.net.speed.netspeed.vo.network.ExperienceSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;

import java.util.List;

/**
 * @author mengc
 */
public interface ExperienceSpeedApi extends BaseApi<ExperienceSpeedBo, ExperienceSpeedVo> {

    /**
     * 查询测速下合格和不合格数量
     * @param vo
     * @return
     */
    List<ExperienceSpeedVo> experiencePassvalue(NetworkSpeedVo vo);

    /**
     * 查询体验测速区域下合格和不合格数量
     * @param vo
     * @return
     */
    List<ExperienceSpeedVo> experiencePassvalueArea(NetworkSpeedVo vo);

    /**
     * 查询体验测速AP下合格和不合格数量
     * @param vo
     * @return
     */
    List<ExperienceSpeedVo> experiencePassvalueAP(NetworkSpeedVo vo);

    /**
     * 查询场景/区域下所有测试ap总数
     * @param vo
     * @return
     */
    List<PassValueVo> selectAPCountByTestFromExperienceSpeed(NetworkSpeedVo vo);

    /**
     * 查询大于等于网速值标准的信息
     * @param vo
     * @return
     */
    List<ExperienceSpeedVo> selectGTETData(PassValueVo vo);

    /**
     * 查询小于网速值标准的信息
     * @param vo
     * @return
     */
    List<ExperienceSpeedVo> selectLTData(PassValueVo vo);

    /**
     * 查询网速值标准的信息
     * @param vo
     * @return
     */
    List<ExperienceSpeedVo> selectData(PassValueVo vo);
}
