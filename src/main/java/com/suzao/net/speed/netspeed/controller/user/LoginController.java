package com.suzao.net.speed.netspeed.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suzao.net.speed.netspeed.bo.user.UserBo;
import com.suzao.net.speed.netspeed.service.user.UserApi;

import com.suzao.net.speed.netspeed.util.LoginUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.suzao.net.speed.netspeed.util.ResponseUtil.*;

/**
 * @name LoginController
 * @author mc
 * @date 2022/4/9 20:37
 * @version 1.0
 **/
@RestController
@RequestMapping("/app/login")
@Api(tags = "登录")
public class LoginController {

    @Autowired
    private UserApi userApi;

    @ApiOperation(value = "检测是否存在相同账号", notes = "检测是否存在相同账号")
    @GetMapping(value = "/isExsitUsers.do")
    public JSONObject isExsitUsers(String account) {
        return toSuccess(this.userApi.getUserByAccount(account));
    }

    @ApiOperation(value = "APP登录", notes = "APP登录")
    @GetMapping(value = "/getAccount")
    public JSONObject getAccount(@ApiIgnore  HttpServletRequest request,@ApiIgnore  HttpServletResponse response, String username, String password) {
        request.getSession().setAttribute("username", username);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sessionId", request.getSession().getId());
        return toSuccess(map);
    }

    @ApiOperation(value = "查看当前用户信息", notes = "查看当前用户信息")
    @GetMapping(value = "/getLoginInfo.do")
    public JSONObject getLoginInfo(HttpServletRequest request) {
        return toSuccess(LoginUserUtil.getLoginUser(request));
    }


    @ApiOperation(value = "后台登录", notes = "后台登录")
    @PostMapping(value = "/login.do")
    public JSONObject login(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("username");
        String password = request.getParameter("password");

        if ((account == null || account.equals("")) || (password == null || password.equals(""))) {
            return toJsonObjectMsg(201, "请输入用户名或密码！");
        }
        UserBo user = this.userApi.getUserByAccount(account);
        if (user == null) {
            return toJsonObjectMsg(202, "用户不存在！");
        }

        String loginMsg = "登录成功！";
        if ((user.getStatus() == 0) && (user.getLockType() == 1)) {
            return toJsonObjectMsg(204, "账号已被停用，请联系管理员！");
        }
        if (DigestUtils.md5Hex(password).equals(user.getPassword())) {

            HttpSession session = request.getSession();
            session.setAttribute("LOGIN_USER", JSON.toJSONString(user));

            return toJsonObjectMsg(200, loginMsg);
        }
        String errorMsg = "密码错误！";

        return toJsonObjectMsg(203, errorMsg);
    }

}
