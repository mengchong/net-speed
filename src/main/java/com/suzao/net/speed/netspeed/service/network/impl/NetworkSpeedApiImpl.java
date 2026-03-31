package com.suzao.net.speed.netspeed.service.network.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.network.NetworkSpeedBo;
import com.suzao.net.speed.netspeed.bo.network.SpeedRecordBo;
import com.suzao.net.speed.netspeed.dao.network.NetworkSpeedDao;
import com.suzao.net.speed.netspeed.service.network.NetworkSpeedApi;
import com.suzao.net.speed.netspeed.vo.network.ExperienceSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NetworkSpeedApiImpl implements NetworkSpeedApi {

    @Autowired
    private NetworkSpeedDao networkSpeedDao;

    @Override
    public int insert(List<NetworkSpeedBo> networkSpeedBo) {
        return networkSpeedDao.insert(networkSpeedBo);
    }

    @Override
    public List<NetworkSpeedVo> selectAll(NetworkSpeedVo networkSpeedVo) {
        return networkSpeedDao.selectAll(networkSpeedVo);
    }

    @Override
    public void selectPage(Page<NetworkSpeedBo, NetworkSpeedVo> page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<NetworkSpeedVo> vos = networkSpeedDao.selectAll(page.getCondition());
        PageInfo<NetworkSpeedVo> pageInfo = new PageInfo<>(vos);
        page.setDatas(vos);
        page.setTotalRecord((int) pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
    }

    @Override
    public NetworkSpeedVo findById(Long id) {
        return networkSpeedDao.findById(id);
    }

    @Override
    public List<NetworkSpeedVo> networkPassvalue(NetworkSpeedVo vo) {
        return networkSpeedDao.networkPassvalue(vo);
    }

    @Override
    public List<NetworkSpeedVo> networkPassvalueArea(NetworkSpeedVo vo) {
        return networkSpeedDao.networkPassvalueArea(vo);
    }

    @Override
    public List<NetworkSpeedVo> networkPassvalueAP(NetworkSpeedVo vo) {
        return networkSpeedDao.networkPassvalueAP(vo);
    }

    @Override
    public List<PassValueVo> selectAPCountByTestFromNetworkSpeed(NetworkSpeedVo vo) {
        return networkSpeedDao.selectAPCountByTestFromNetworkSpeed(vo);
    }

    @Override
    public List<NetworkSpeedVo> selectGTETData(PassValueVo vo) {
        return networkSpeedDao.selectGTETData(vo);
    }

    @Override
    public List<NetworkSpeedVo> selectLTData(PassValueVo vo) {
        return networkSpeedDao.selectLTData(vo);
    }

    @Override
    public List<NetworkSpeedVo> selectData(PassValueVo vo) {
        return networkSpeedDao.selectData(vo);
    }

    @Override
    public List<SpeedRecordBo> getSpeedRecord() {
        return this.networkSpeedDao.getSpeedRecord();
    }

}
