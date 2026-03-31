package com.suzao.net.speed.netspeed.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @name ResponseUtil
 * @author mc
 * @date 2022/4/9 22:27
 * @version 1.0
 **/
public class ResponseUtil {
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_SUCCESS_GOURPNAMEEXSIT = 201;
    public static final int CODE_NOT_FOUND = 404;
    public ResponseUtil() {
    }

    public static JSONObject toJsonObject(int var0, Object var1) {
        JSONObject var2 = new JSONObject();
        var2.put("code", Integer.valueOf(var0));
        var2.put("data", var1);
        return var2;
    }
    public static JSONObject toJsonObjectMsg(int var0, Object var1) {
        JSONObject var2 = new JSONObject();
        var2.put("code", Integer.valueOf(var0));
        var2.put("data", var1);
        var2.put("msg", var1);
        return var2;
    }
    public static JSONObject toJsonObject(int var0, Object var1, Object var3) {
        JSONObject var2 = new JSONObject();
        var2.put("code", Integer.valueOf(var0));
        var2.put("data", var1);
        var2.put("sessionId",var3);
        return var2;
    }

    public static JSONObject toSuccess(Object var0) {
        return toJsonObjectMsg(200, var0,"success");
    }
    public static JSONObject toJsonObjectMsg(int var0, Object var1,Object var3) {
        JSONObject var2 = new JSONObject();
        var2.put("code", Integer.valueOf(var0));
        var2.put("data", var1);
        var2.put("msg", var3);
        return var2;
    }
    public static JSONObject toFailForGroupNameExsit(Object var0) {
        return toJsonObject(201, var0);
    }

    public static JSONObject noLoginError(Object var0){
        return toJsonObject(202, var0);
    }
}
