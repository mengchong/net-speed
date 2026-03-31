package com.suzao.net.speed.netspeed.dao.network;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suzao.net.speed.netspeed.base.BaseDao;
import com.suzao.net.speed.netspeed.bo.network.ExperienceSpeedBo;
import com.suzao.net.speed.netspeed.vo.network.ExperienceSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @name ExperienceSpeedDao
 * @author mc
 * @date 2022/4/9 20:36
 * @version 1.0
 **/
@Mapper
@Repository
public interface ExperienceSpeedDao extends BaseDao<ExperienceSpeedBo, ExperienceSpeedVo>,BaseMapper<ExperienceSpeedBo> {

    /**
     * 查询体验测速场景下合格和不合格数量
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
