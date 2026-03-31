package com.suzao.net.speed.netspeed.dao.user;



import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.user.CountBo;
import com.suzao.net.speed.netspeed.bo.user.UserBo;
import com.suzao.net.speed.netspeed.vo.user.UserPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @name UserDao
 * @author mc
 * @date 2022/4/9 12:27
 * @version 1.0
 **/
@Mapper
@Repository
public interface UserDao {

    UserBo get(Long id);

    List<CountBo> getUserCount(CountBo po);

    UserBo getUserByAccount(String account);

    int insert(UserBo userBo);

    int update(UserBo userBo);

    int updateByAccount(UserBo userBo);

    int delete(Long id);

    int delByIds(List<Long> ids);

    List<UserBo> getList(Page<UserBo, UserPageVo> page);

    List<UserBo> getAll();

    List<UserBo> selectPage(Page<UserBo, UserPageVo> page);

    List<UserBo> getEmailList();

    List<UserBo> getPhoneUsers();

    List<CountBo> getUserVisitCount(CountBo countBo);
}
