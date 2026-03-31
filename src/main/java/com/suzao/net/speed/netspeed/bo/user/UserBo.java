package com.suzao.net.speed.netspeed.bo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel
public class UserBo implements Serializable {
    @ApiModelProperty(hidden = true)
    private Long id;
    private Long creatorId;
    private int sex;
    private int userType;
    //0停用；1启用
    private int status;
    private Date createdTime;
    private String name;
    @ApiModelProperty(value = "账号",dataType = "String",example = "zhangsan")
    private String account;
    @ApiModelProperty(value = "密码",dataType = "String",example = "123456")
    private String password;
    @ApiModelProperty(value = "手机号",dataType = "String",example = "13700001111")
    private String mobile;
    private String email;


    private int lockType;
    private Date lockTime;
    private Date upPassTime;
    private int passErrorCnt = 0;

    private String json;
    private String tag1;
    private String tag2;

    private String showType;

    private int type;
    private int newType;
    private int newTypes;
    private int userTypes;


    private String mac;
    private String code;

    private String portraitImg;

    //选中
    private int checked;

    private int integeral;

    private String integrals;

    private int allNums;
}
