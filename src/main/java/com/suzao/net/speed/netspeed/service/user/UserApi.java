package com.suzao.net.speed.netspeed.service.user;

import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.user.UserBo;
import com.suzao.net.speed.netspeed.vo.user.UserPageVo;

import java.util.List;

public interface UserApi {
    UserBo getUserByAccount(String account);

    UserBo get(long id);

    int insert(UserBo userBo);

    int update(UserBo userBo);

    int delete(Long id);

    int delByIds(List<Long> ids);

    List<UserBo> getList(Page<UserBo, UserPageVo> page);

    List<UserBo> getAll();

    void selectPage(Page<UserBo, UserPageVo> page);

    List<UserBo> getEmailList();


    List<UserBo> getPhoneUsers();

    int updateByAccount(UserBo userBo);

}
