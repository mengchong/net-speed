package com.suzao.net.speed.netspeed.interceptor;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UriInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object handler) throws Exception {
        httpResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");
        httpResponse.setHeader("Access-Control-Expose-Headers", "*");

        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        String host = "http://" + httpRequest.getServerName() + ":" + httpRequest.getServerPort();
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }

        HttpSession session = httpRequest.getSession();
        System.out.println("LOGIN_USER："+session.getAttribute("LOGIN_USER"));
        System.out.println("URL："+url);
        if (isInclude(url)) {
            if(url.contains("portal/speed")){
                session.setAttribute("LOGIN_USER", JSON.toJSONString(null));
                httpResponse.sendRedirect(host + "/index.html?type=speed");
            }else if(url.contains("portal/statistic")){
                session.setAttribute("LOGIN_USER", JSON.toJSONString(null));
                httpResponse.sendRedirect(host + "/index.html?type=statistic");
            }else{
                return true;
            }
        } else {
            if (session.getAttribute("LOGIN_USER") != null) {
                return true;
            } else {
                if ("XMLHttpRequest".equals(httpRequest.getHeader("X-Requested-With"))) {
                    //告诉ajax我是重定向
                    httpResponse.setHeader("REDIRECT", "REDIRECT");
                    //告诉ajax我重定向的路径
                    httpResponse.setHeader("CONTENTPATH", host + "/login.html");
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } else {
                    httpResponse.sendRedirect(host + "/login.html");
                }
            }
        }
        return false;
    }
    private boolean isInclude(String url) {
        if (url.contains("js/") ||
                url.contains("css/") ||
                url.contains("fonts/") ||
                url.contains("image/") ||
                url.contains("images/") ||
                url.contains("list.do") ||
                url.contains("app/") ||
                url.contains("save.do") ||
                url.contains("portal/speed") ||
                url.contains("webjars") ||
                url.contains("swagger-resources") ||
                url.contains("v2/api-docs") ||
                url.contains("doc.html") ||
                url.contains("login")||
                url.contains("url")) {
            return true;
        }
        return false;
    }

}
