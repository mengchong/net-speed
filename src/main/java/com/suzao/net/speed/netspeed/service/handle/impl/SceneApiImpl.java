package com.suzao.net.speed.netspeed.service.handle.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.handle.ApBo;
import com.suzao.net.speed.netspeed.bo.handle.AreaBo;
import com.suzao.net.speed.netspeed.bo.handle.SceneBo;
import com.suzao.net.speed.netspeed.dao.handle.SceneDao;
import com.suzao.net.speed.netspeed.service.handle.SceneApi;
import com.suzao.net.speed.netspeed.util.BeanMapperUtil;
import com.suzao.net.speed.netspeed.vo.handle.ApVo;
import com.suzao.net.speed.netspeed.vo.handle.AreaVo;
import com.suzao.net.speed.netspeed.vo.handle.SceneVo;
import com.suzao.net.speed.netspeed.vo.network.ExperienceSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import com.suzao.net.speed.netspeed.vo.network.StatisticalAp;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SceneApiImpl implements SceneApi  {

    @Autowired
    private SceneDao sceneDao;

    @Override
    public int batchInsert(List<SceneBo> sceneBos) {
        return sceneDao.batchInserts(sceneBos);
    }

    @Override
    public int batchInsertArea(List<AreaBo> areaBos) {
        return sceneDao.batchInsertArea(areaBos);
    }

    @Override
    public int batchInsertAp(List<ApBo> apBos) {
        return sceneDao.batchInsertAp(apBos);
    }

    @Override
    public List<SceneBo> selectAllScene() {
        return sceneDao.selectAllScene();
    }

    @Override
    public List<AreaBo> selectAllArea() {
        return sceneDao.selectAllArea();
    }

    @Override
    public List<ApBo> selectAllAp() {
        return sceneDao.selectAllAp();
    }

    @Override
    public void selectPageScene(Page<SceneBo, SceneVo> page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<SceneBo> bos = sceneDao.selectAllScene();
        PageInfo<SceneBo> pageInfo = new PageInfo<>(bos);
        page.setDatas(BeanMapperUtil.mapList(bos,SceneVo.class));
        page.setTotalRecord((int) pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
    }

    @Override
    public void selectPageArea(Page<AreaBo, AreaVo> page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<AreaBo> bos = sceneDao.selectAllArea();
        PageInfo<AreaBo> pageInfo = new PageInfo<>(bos);

        List<AreaVo> areaVos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(bos)){
            bos.forEach(areaBo -> {
                AreaVo areaVo = BeanMapperUtil.map(areaBo, AreaVo.class);
                SceneBo sceneBo = sceneDao.findBySceneId(areaBo.getSceneId());
                if(ObjectUtils.isNotEmpty(sceneBo)){
                    areaVo.setSceneName(sceneBo.getName());
                }
                areaVos.add(areaVo);
            });
        }
        page.setDatas(areaVos);
        page.setTotalRecord((int) pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
    }

    @Override
    public void selectPageAp(Page<ApBo, ApVo> page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<ApBo> bos = sceneDao.selectAllAp();
        PageInfo<ApBo> pageInfo = new PageInfo<>(bos);

        List<ApVo> apVos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(bos)){
            bos.forEach(apBo -> {
                ApVo apVo = BeanMapperUtil.map(apBo, ApVo.class);
                AreaBo areaBo = sceneDao.findByAreaId(apBo.getAreaId());
                if(ObjectUtils.isNotEmpty(areaBo)){
                    apVo.setAreaName(areaBo.getName());
                    SceneBo sceneBo = sceneDao.findBySceneId(areaBo.getSceneId());
                    if(ObjectUtils.isNotEmpty(sceneBo)){
                        apVo.setSceneName(sceneBo.getName());
                    }
                }
                apVos.add(apVo);
            });
        }
        page.setDatas(apVos);
        page.setTotalRecord((int) pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
    }

    @Override
    public SceneBo findBySceneId(Integer id) {
        return sceneDao.findBySceneId(id);
    }

    @Override
    public AreaBo findByAreaId(Integer id) {
        return sceneDao.findByAreaId(id);
    }

    @Override
    public List<AreaBo> findAreaBySceneId(int sceneId) {
        return sceneDao.findAreaBySceneId(sceneId);
    }

    @Override
    public ApVo findSceneAndArea(String mac) {
        return sceneDao.findSceneAndArea(mac);
    }

    @Override
    public List<StatisticalAp> queryByMac(List<String> list) {
        return  sceneDao.queryByMac(list);
    }

    @Override
    public List<ApVo> selectAllApSceneId() {
        return sceneDao.selectAllApSceneId();
    }

    @Override
    public void clearScene() {
        sceneDao.clearScene();
    }

    @Override
    public void clearArea() {
        sceneDao.clearArea();
    }

    @Override
    public void clearAp() {
        sceneDao.clearAp();
    }

    @Override
    public List<PassValueVo> selectAPCount(NetworkSpeedVo vo) {
        return sceneDao.selectAPCount(vo);
    }

    @Override
    public List<ApVo> selectBySceneNameOrAreaNameOrApName(NetworkSpeedVo vo) {
        return sceneDao.selectBySceneNameOrAreaNameOrApName(vo);
    }

    @Override
    public List<ApVo> selectByIds(List<Long> ids) {
        return sceneDao.selectByIds(ids);
    }


}
