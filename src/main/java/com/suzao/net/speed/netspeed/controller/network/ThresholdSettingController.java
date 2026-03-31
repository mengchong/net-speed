package com.suzao.net.speed.netspeed.controller.network;


import com.alibaba.fastjson.JSONObject;
import com.suzao.net.speed.netspeed.bo.network.ThresholdSettingBo;
import com.suzao.net.speed.netspeed.service.network.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import static com.suzao.net.speed.netspeed.util.ResponseUtil.toSuccess;


/**
 * @author mc
 * @version 1.0
 * @name PassValueController
 * @date 2022/4/9 20:37
 **/
@Api(tags = "阈值配置")
@RestController
@RequestMapping({"/vesapp/thresholdSetting"})
public class ThresholdSettingController {

    @Autowired
    private ThresholdSettingApi thresholdSettingApi;


//    @ApiOperation(value = "保存")
//    @PostMapping({"/save.do"})
//    public JSONObject save(ThresholdSettingBo settingBo) {
//
//        return toSuccess(thresholdSettingApi.insert(settingBo));
//    }

    @ApiOperation(value = "获取配置")
    @GetMapping({"/get.do"})
    public JSONObject listAll(Long id) {
        return toSuccess(thresholdSettingApi.getThresholdSetting(id));
    }


    @ApiOperation(value = "修改")
    @PostMapping({"/update.do"})
    public JSONObject update(@RequestBody ThresholdSettingBo settingBo) {

        return toSuccess(thresholdSettingApi.update(settingBo));
    }


}
