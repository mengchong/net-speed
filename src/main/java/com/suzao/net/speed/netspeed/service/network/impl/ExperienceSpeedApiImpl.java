package com.suzao.net.speed.netspeed.service.network.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.network.ExperienceSpeedBo;
import com.suzao.net.speed.netspeed.dao.network.ExperienceSpeedDao;
import com.suzao.net.speed.netspeed.service.network.ExperienceSpeedApi;
import com.suzao.net.speed.netspeed.vo.network.DeviceInformationVo;
import com.suzao.net.speed.netspeed.vo.network.ExperienceSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceSpeedApiImpl implements ExperienceSpeedApi {


    @Autowired
    private ExperienceSpeedDao experienceSpeedDao;

    @Override
    public int insert(List<ExperienceSpeedBo> experienceSpeedBo) {
        return experienceSpeedDao.insert(experienceSpeedBo);
    }

    @Override
    public List<ExperienceSpeedVo> selectAll(ExperienceSpeedVo experienceSpeedVo) {
        return experienceSpeedDao.selectAll(experienceSpeedVo);
    }

    @Override
    public void selectPage(Page<ExperienceSpeedBo, ExperienceSpeedVo> page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<ExperienceSpeedVo> vos = experienceSpeedDao.selectAll(page.getCondition());
        PageInfo<ExperienceSpeedVo> pageInfo = new PageInfo<>(vos);
        page.setDatas(vos);
        page.setTotalRecord((int) pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
    }

    @Override
    public ExperienceSpeedVo findById(Long id) {
        return experienceSpeedDao.findById(id);
    }

    @Override
    public List<ExperienceSpeedVo> experiencePassvalue(NetworkSpeedVo vo) {
        return experienceSpeedDao.experiencePassvalue(vo);
    }

    @Override
    public List<ExperienceSpeedVo> experiencePassvalueArea(NetworkSpeedVo vo) {
        return experienceSpeedDao.experiencePassvalueArea(vo);
    }

    @Override
    public List<ExperienceSpeedVo> experiencePassvalueAP(NetworkSpeedVo vo) {
        return experienceSpeedDao.experiencePassvalueAP(vo);
    }

    @Override
    public List<PassValueVo> selectAPCountByTestFromExperienceSpeed(NetworkSpeedVo vo) {
        return experienceSpeedDao.selectAPCountByTestFromExperienceSpeed(vo);
    }

    @Override
    public List<ExperienceSpeedVo> selectGTETData(PassValueVo vo) {
        return experienceSpeedDao.selectGTETData(vo);
    }

    @Override
    public List<ExperienceSpeedVo> selectLTData(PassValueVo vo) {
        return experienceSpeedDao.selectLTData(vo);
    }

    @Override
    public List<ExperienceSpeedVo> selectData(PassValueVo vo) {
        return experienceSpeedDao.selectData(vo);
    }

}
