package com.suzao.net.speed.netspeed.util;

import com.alibaba.fastjson.JSONObject;
import com.suzao.net.speed.netspeed.bo.user.UserBo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStreamReader;


/**
 * @name LoginUserUtil
 * @author mc
 * @date 2022/4/9 22:26
 * @version 1.0
 **/
public class LoginUserUtil {

    public static UserBo getLoginUser(HttpServletRequest request){
        HttpSession session = request.getSession();
          Object o = session.getAttribute("LOGIN_USER");
          if(o==null){
              return  null;
          }
        UserBo userBo = JSONObject.parseObject(session.getAttribute("LOGIN_USER").toString(), UserBo.class);
        if(userBo !=null && userBo.getId() == null){
            userBo = null;
        }
          return userBo;
    }
    public static String getSessionId(HttpServletRequest request){

            InputStreamReader reader= null;
            String json ="";
            String sessionId = null;
            try{
                reader = new InputStreamReader(request.getInputStream(),"UTF-8");
                char [] buff=new char[1024];
                int length=0;
                while((length=reader.read(buff))!=-1){
                    json=new String(buff,0,length);
                }
                JSONObject jsonObject = JSONObject.parseObject(json);
                sessionId = jsonObject.getString("sessionId");
            }catch (Exception e){
                System.err.println("获取sessionId失败");
                return sessionId;
            }
            return sessionId;
    }

}
