package com.suzao.net.speed.netspeed.vo.user;


import com.suzao.net.speed.netspeed.bo.user.UserBo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserPageVo extends UserBo implements Serializable {
  private  String searchText;

  private List<Long> ids;

  private Long groupId;

}
