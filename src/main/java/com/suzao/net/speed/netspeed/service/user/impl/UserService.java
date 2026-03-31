package com.suzao.net.speed.netspeed.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.user.UserBo;
import com.suzao.net.speed.netspeed.dao.user.UserDao;
import com.suzao.net.speed.netspeed.service.user.UserApi;

import com.suzao.net.speed.netspeed.util.BeanMapperUtil;
import com.suzao.net.speed.netspeed.vo.user.UserPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

@Service
public class UserService implements UserApi {

    @Autowired
    private UserDao userDao;

    @Override
    public UserBo getUserByAccount(String account) {
        return this.userDao.getUserByAccount(account);
    }

    @Override
    public UserBo get(long id) {
        return userDao.get(id);
    }

    @Override
    public int insert(UserBo userBo) {
        if (!"".equals(userBo.getPassword()) && userBo.getPassword() != null){
            userBo.setPassword(DigestUtils.md5Hex(userBo.getPassword()));
        }
        int flag= this.userDao.insert(userBo);

        return flag;
    }

    @Override
    public int update(UserBo userBo) {
         if(userBo.getPassword()!=null&& userBo.getPassword()!=""){
             userBo.setPassword(DigestUtils.md5Hex(userBo.getPassword()));
         }
        return this.userDao.update(userBo);
    }

    @Override
    public int delete(Long id) {
        return this.userDao.delete(id);
    }

    @Override
    public int delByIds(List<Long> ids) {
        return this.userDao.delByIds(ids);
    }

    @Override
    public List<UserBo> getList(Page<UserBo, UserPageVo> page) {
        return this.userDao.getList(page);
    }

    @Override
    public List<UserBo> getAll() {
        return this.userDao.getAll();
    }

    @Override
    public void selectPage(Page<UserBo, UserPageVo> page) {

        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<UserBo> bos = this.userDao.selectPage(page);
        PageInfo<UserBo> pageInfo = new PageInfo<>(bos);
        page.setDatas(BeanMapperUtil.mapList(bos, UserPageVo.class));
        page.setTotalRecord((int) pageInfo.getTotal());
        page.setPages(pageInfo.getPages());
    }

    @Override
    public List<UserBo> getEmailList() {
        return this.userDao.getEmailList();
    }

    @Override
    public List<UserBo> getPhoneUsers() {
        return this.userDao.getPhoneUsers();
    }

    @Override
    public int updateByAccount(UserBo userBo) {

        if(userBo.getPassword()!=null&& userBo.getPassword()!=""){
            userBo.setPassword(DigestUtils.md5Hex(userBo.getPassword()));
        }
        return this.userDao.updateByAccount(userBo);
    }



}
