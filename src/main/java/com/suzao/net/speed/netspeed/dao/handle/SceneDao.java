package com.suzao.net.speed.netspeed.dao.handle;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.handle.ApBo;
import com.suzao.net.speed.netspeed.bo.handle.AreaBo;
import com.suzao.net.speed.netspeed.bo.handle.SceneBo;
import com.suzao.net.speed.netspeed.vo.handle.ApVo;
import com.suzao.net.speed.netspeed.vo.handle.AreaVo;
import com.suzao.net.speed.netspeed.vo.handle.SceneVo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import com.suzao.net.speed.netspeed.vo.network.StatisticalAp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SceneDao extends BaseMapper<SceneBo> {

    int batchInserts(List<SceneBo> sceneBos);

    int batchInsertArea(List<AreaBo> areaBos);

    int batchInsertAp(List<ApBo> apBos);

    List<SceneBo> selectAllScene();

    List<AreaBo> selectAllArea();

    List<ApBo> selectAllAp();

    void selectPageScene(Page<SceneBo, SceneVo> page);

    void selectPageArea(Page<AreaBo, AreaVo> page);

    void selectPageAp(Page<ApBo, ApVo> page);

    List<AreaBo> findAreaBySceneId(int sceneId);

    SceneBo findBySceneId(Integer id);

    AreaBo findByAreaId(Integer id);

    ApVo findSceneAndArea(String mac);

    List<StatisticalAp> queryByMac(List<String> list);

    List<ApVo> selectAllApSceneId();

    void clearScene();

    void clearArea();

    void clearAp();

    //查询场景/区域下所有ap总数
    List<PassValueVo> selectAPCount(NetworkSpeedVo vo);

    //根据场景id/区域id/ap名称查询ap详情
    List<ApVo> selectBySceneNameOrAreaNameOrApName(NetworkSpeedVo vo);

    //根据多个id查询信息
    List<ApVo> selectByIds(List<Long> ids);

}
