package com.suzao.net.speed.netspeed.service.network.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.network.DeviceInformationBo;
import com.suzao.net.speed.netspeed.dao.network.DeviceInformationDao;
import com.suzao.net.speed.netspeed.service.network.DeviceInformationApi;
import com.suzao.net.speed.netspeed.vo.network.DeviceInformationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>DeviceInformationApiImpl</p>
 * @author mc
 * @version 1.0
 * @date 2022/4/9 0:38
 **/
@Service
public class DeviceInformationApiImpl implements DeviceInformationApi {

    @Autowired
    private DeviceInformationDao deviceInformationDao;

    @Override
    public int insert(List<DeviceInformationBo> bo) {
        return deviceInformationDao.insert(bo);
    }

    @Override
    public List<DeviceInformationVo> selectAll(DeviceInformationVo deviceInformationVo) {
        return deviceInformationDao.selectAll(deviceInformationVo);
    }

    @Override
    public void selectPage(Page<DeviceInformationBo, DeviceInformationVo> page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<DeviceInformationVo> deviceInformationVos = deviceInformationDao.selectAll(page.getCondition());
        PageInfo<DeviceInformationVo> pageInfo = new PageInfo<>(deviceInformationVos);
        page.setDatas(deviceInformationVos);
        page.setTotalRecord((int) pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
    }

    @Override
    public DeviceInformationVo findById(Long id) {
        return deviceInformationDao.findById(id);
    }
}
