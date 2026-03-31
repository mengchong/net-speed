package com.suzao.net.speed.netspeed.service.network.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.network.PingSpeedBo;
import com.suzao.net.speed.netspeed.dao.network.PingSpeedDao;
import com.suzao.net.speed.netspeed.service.network.PingSpeedApi;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import com.suzao.net.speed.netspeed.vo.network.PingSpeedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PingSpeedApiImpl implements PingSpeedApi {

    @Autowired
    private PingSpeedDao pingSpeedDao;

    @Override
    public int insert(List<PingSpeedBo> pingSpeedBo) {
        return pingSpeedDao.insert(pingSpeedBo);
    }

    @Override
    public List<PingSpeedVo> selectAll(PingSpeedVo pingSpeedVo) {
        return pingSpeedDao.selectAll(pingSpeedVo);
    }

    @Override
    public void selectPage(Page<PingSpeedBo, PingSpeedVo> page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<PingSpeedVo> vos = pingSpeedDao.selectAll(page.getCondition());
        PageInfo<PingSpeedVo> pageInfo = new PageInfo<>(vos);
        page.setDatas(vos);
        page.setTotalRecord((int) pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
    }

    @Override
    public PingSpeedVo findById(Long id) {
        return pingSpeedDao.findById(id);
    }

    @Override
    public List<PingSpeedVo> pingPassvalue(NetworkSpeedVo vo) {
        return pingSpeedDao.pingPassvalue(vo);
    }

    @Override
    public List<PingSpeedVo> pingPassvalueArea(NetworkSpeedVo vo) {
        return pingSpeedDao.pingPassvalueArea(vo);
    }

    @Override
    public List<PingSpeedVo> pingPassvalueAP(NetworkSpeedVo vo) {
        return pingSpeedDao.pingPassvalueAP(vo);
    }

    @Override
    public List<PassValueVo> selectAPCountByTestFromPingSpeed(NetworkSpeedVo vo) {
        return pingSpeedDao.selectAPCountByTestFromPingSpeed(vo);
    }

    @Override
    public List<PingSpeedVo> selectGTETData(PassValueVo vo) {
        return pingSpeedDao.selectGTETData(vo);
    }

    @Override
    public List<PingSpeedVo> selectLTData(PassValueVo vo) {
        return pingSpeedDao.selectLTData(vo);
    }

    @Override
    public List<PingSpeedVo> selectData(PassValueVo vo) {
        return pingSpeedDao.selectData(vo);
    }

}
