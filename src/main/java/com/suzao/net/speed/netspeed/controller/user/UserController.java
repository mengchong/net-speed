package com.suzao.net.speed.netspeed.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.user.UserBo;
import com.suzao.net.speed.netspeed.service.user.UserApi;
import com.suzao.net.speed.netspeed.vo.user.UserPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.suzao.net.speed.netspeed.util.ResponseUtil.toJsonObject;
import static com.suzao.net.speed.netspeed.util.ResponseUtil.toSuccess;


/**
 * @name UserController
 * @author mc
 * @date 2022/4/9 20:38
 * @version 1.0
 **/
@RestController
@RequestMapping(value = "/app/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserApi userApi;

    @GetMapping(value = "/list.do")
    @ResponseBody
    public JSONObject list(String account, int pageNumber, int pageSize) {
        Page<UserBo, UserPageVo> page = new Page<>();

        UserPageVo userPageVo = new UserPageVo();

        if ("".equals(account) && account == null) {
            userPageVo.setAccount("");
        } else {
            userPageVo.setAccount(account);
        }

        int start = (pageNumber - 1) * pageSize;
        page.setStartRow(start);
        page.setRowCount(pageSize);
        page.setCondition(userPageVo);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        userApi.selectPage(page);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        jsonObject.put("rows", page.getDatas());
        jsonObject.put("total", page.getTotalRecord());
        jsonObject.put("page", page.getStartRow());
        return jsonObject;
    }

    @ApiOperation(value="查询用户信息",notes = "查询用户信息")
    @PostMapping(value = "/getUserDetail.do")
    public JSONObject getUserDetail(String id) {
        long longId = Long.parseLong(id);
        return toSuccess(this.userApi.get(longId));
    }

    @ApiOperation(value = "检测是否存在相同账号", notes = "检测是否存在相同账号")
    @PostMapping(value = "/isExsitUsers.do")
    public JSONObject isExsitUsers(String account) {
        return toSuccess(this.userApi.getUserByAccount(account));
    }

    @ApiOperation(value = "根据用户类型获取用户分页", notes = "根据用户类型获取用户分页")
    @PostMapping(value = "/getUserPageList.do")
    public JSONObject getUserPageList(@RequestBody Page<UserBo, UserPageVo> page) {
        List<UserBo> list = this.userApi.getList(page);
        return toSuccess(page);
    }

     @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    @PostMapping(value = "/update.do")
    public JSONObject update(@RequestBody UserBo po) {
//        if (po.getType() == 2) {
            po.setUserType(6);
//        }
        return toSuccess(this.userApi.updateByAccount(po));
    }

    @ApiOperation(value = "添加用户", notes = "添加用户")
    @PostMapping(value = "/insert.do")
    public JSONObject insert(@RequestBody UserBo po) {
//        if (po.getType() == 2) {
            po.setCreatorId(1L);
            po.setCreatedTime(new Date());
            po.setUserType(6);
//        }
        if (this.userApi.getUserByAccount(po.getAccount()) != null) {
            return toJsonObject(202, "该用户已存在！");
        }
        return toSuccess(this.userApi.insert(po));
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @GetMapping(value = "/delByIds.do")
    public JSONObject delByIds(String[] ids) {
        List<Long> idList = new ArrayList<>();
        for (String id : ids) {
            idList.add(Long.parseLong(id));
        }
        return toSuccess(this.userApi.delByIds(idList));
    }


}
