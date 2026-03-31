package com.suzao.net.speed.netspeed.service.network.impl;

import com.suzao.net.speed.netspeed.bo.network.PassValueBo;
import com.suzao.net.speed.netspeed.dao.network.PassValueDao;
import com.suzao.net.speed.netspeed.service.network.PassValueApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassValueApiImpl implements PassValueApi {

    @Autowired
    private PassValueDao passValueDao;

    @Override
    public int insert(List<PassValueBo> passValueBo) {
        return passValueDao.insert(passValueBo);
    }

    @Override
    public PassValueBo selectAll() {
        return passValueDao.selectAll();
    }
}
