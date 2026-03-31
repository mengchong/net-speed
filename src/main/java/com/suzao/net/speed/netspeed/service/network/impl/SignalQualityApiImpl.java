package com.suzao.net.speed.netspeed.service.network.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.network.NetworkSpeedBo;
import com.suzao.net.speed.netspeed.bo.network.SignalQualityBo;
import com.suzao.net.speed.netspeed.dao.network.NetworkSpeedDao;
import com.suzao.net.speed.netspeed.dao.network.SignalQualityDao;
import com.suzao.net.speed.netspeed.service.network.NetworkSpeedApi;
import com.suzao.net.speed.netspeed.service.network.SignalQualityApi;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import com.suzao.net.speed.netspeed.vo.network.SignalQualityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SignalQualityApiImpl implements SignalQualityApi {

    @Autowired
    private SignalQualityDao signalQualityDao;

    @Override
    public int insert(List<SignalQualityBo> signalQualityBos) {
        return signalQualityDao.insert(signalQualityBos);
    }

    @Override
    public List<SignalQualityVo> selectAll(SignalQualityVo signalQualityVo) {
        return signalQualityDao.selectAll(signalQualityVo);
    }

    @Override
    public void selectPage(Page<SignalQualityBo, SignalQualityVo> page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<SignalQualityVo> vos = signalQualityDao.selectAll(page.getCondition());
        PageInfo<SignalQualityVo> pageInfo = new PageInfo<>(vos);
        page.setDatas(vos);
        page.setTotalRecord((int) pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
    }

    @Override
    public SignalQualityVo findById(Long id) {
        return signalQualityDao.findById(id);
    }

}
