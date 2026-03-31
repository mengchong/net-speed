package com.suzao.net.speed.netspeed.controller.network;

import com.alibaba.fastjson.JSONObject;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.network.SignalQualityBo;
import com.suzao.net.speed.netspeed.service.handle.SceneApi;
import com.suzao.net.speed.netspeed.service.network.SignalQualityApi;
import com.suzao.net.speed.netspeed.util.JxlsUtils;
import com.suzao.net.speed.netspeed.vo.handle.ApVo;
import com.suzao.net.speed.netspeed.vo.network.SignalQualityVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

import static com.suzao.net.speed.netspeed.util.ResponseUtil.toSuccess;


/**
 * @name ExperienceSpeedController
 * @author mc
 * @date 2022/4/9 9:25
 * @version 1.0
 **/
@Api(tags = "信号质量")
@RestController
@RequestMapping({"/vesapp/signalquality"})
public class SignalQualityController {

    @Autowired
    private SignalQualityApi signalQualityApi;
    @Autowired
    private SceneApi sceneApi;

    @ApiOperation(value = "保存")
    @PostMapping({"/save.do"})
    public JSONObject save(@RequestBody List<SignalQualityBo> signalQualityBos) {

        Date date = new Date();
        ArrayList<SignalQualityBo> experienceSpeedList = new ArrayList<>();

        for (SignalQualityBo experienceSpeedBo : signalQualityBos) {

            experienceSpeedBo.setTime(date);
            /*
             * 根据mac地址或mac5G查询关联的场景和区域和ap
             */
            //判断传输过来的数据是否存在mac属性或mac5G属性
            if (experienceSpeedBo.getMacAddress() != null || experienceSpeedBo.getMacAddress5G() != null) {
                String macAddress = experienceSpeedBo.getMacAddress();
                ApVo sceneAndArea = sceneApi.findSceneAndArea(macAddress);
                //判断是否根据mac地址查询到了相关联的场景和区域
                if (sceneAndArea == null) {
                    //如果mac属性没有查到再判断mac5G是否有数据
                    if (experienceSpeedBo.getMacAddress5G() != null) {
                        String macAddress5G = experienceSpeedBo.getMacAddress5G();
                        ApVo sceneAndArea5G = sceneApi.findSceneAndArea(macAddress5G);
                        //判断是否根据mac5G地址查询到了相关联的场景和区域
                        if (sceneAndArea5G != null) {
                            String mac5G = sceneAndArea5G.getMac();
                            if (mac5G == null) {
                                experienceSpeedBo.setSceneId(null);
                                experienceSpeedBo.setAreaId(null);
                                experienceSpeedBo.setApId(null);
                            } else {
                                experienceSpeedBo.setSceneId(sceneAndArea5G.getSceneId());
                                experienceSpeedBo.setAreaId(sceneAndArea5G.getAreaId());
                                experienceSpeedBo.setApId(sceneAndArea5G.getId());
                            }
                        }
                    }
                } else {
                    experienceSpeedBo.setSceneId(sceneAndArea.getSceneId());
                    experienceSpeedBo.setAreaId(sceneAndArea.getAreaId());
                    experienceSpeedBo.setApId(sceneAndArea.getId());
                }
            }
            if(ObjectUtils.isEmpty(experienceSpeedBo.getId())){
                experienceSpeedBo.setId(0L);
            }
            experienceSpeedList.add(experienceSpeedBo);

        }
        return toSuccess(signalQualityApi.insert(experienceSpeedList));
    }


    @ApiOperation(value = "列表")
    @GetMapping({"/listAll.do"})
    public JSONObject listAll(SignalQualityVo experienceSpeedVo) {
        return toSuccess(signalQualityApi.selectAll(experienceSpeedVo));
    }

    @ApiOperation(value = "分页")
    @GetMapping({"/list.do"})
    public JSONObject list(int pageNumber, int pageSize, String speedometer, Long startTime, Long endTime, String sceneId, String areaId, String apId) {

        Page<SignalQualityBo, SignalQualityVo> page = new Page<>();
        SignalQualityVo experienceSpeedVo = new SignalQualityVo();

        if (speedometer == null || speedometer.equals("")) {
            experienceSpeedVo.setSpeedometer("");
        } else {
            experienceSpeedVo.setSpeedometer(speedometer);
        }

        if (sceneId == null || sceneId.equals("")) {
            experienceSpeedVo.setSceneId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            experienceSpeedVo.setSceneId(scene);
        }

        if (areaId == null || areaId.equals("")) {
            experienceSpeedVo.setAreaId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            experienceSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            experienceSpeedVo.setAreaId(area);
        }

        if (apId == null || apId.equals("")) {
            experienceSpeedVo.setApId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            experienceSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            experienceSpeedVo.setAreaId(area);
            int ap = Integer.parseInt(apId);
            experienceSpeedVo.setAreaId(ap);
        }

        //判断是否有时间条件,没有就手动插入,查询1970到系统时间为止
        if (startTime == null || startTime.equals("")) {
            if (endTime == null || endTime.equals("")) {
                experienceSpeedVo.setStartTime(new Date(14400));
                experienceSpeedVo.setEndTime(new Date());
            }
        } else {
            experienceSpeedVo.setStartTime(new Date(startTime));
            experienceSpeedVo.setEndTime(new Date(endTime));
        }

        int start = (pageNumber - 1) * pageSize;
        page.setStartRow(start);
        page.setRowCount(pageSize);
        page.setCondition(experienceSpeedVo);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        signalQualityApi.selectPage(page);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        jsonObject.put("rows", page.getDatas());
        jsonObject.put("total", page.getTotalRecord());
        jsonObject.put("page", page.getStartRow());
        jsonObject.put("startTime", page.getCondition().getStartTime().getTime());
        jsonObject.put("endTime", page.getCondition().getEndTime().getTime());
        jsonObject.put("sceneId", page.getCondition().getSceneId());
        jsonObject.put("areaId", page.getCondition().getAreaId());
        jsonObject.put("apId", page.getCondition().getApId());
        return jsonObject;
    }
    @ApiOperation(value = "导出")
    @GetMapping({"/export.do"})
    @ApiIgnore
    public JSONObject list(HttpServletResponse response, HttpServletRequest request, String speedometer, Long startTime, Long endTime, String sceneId, String areaId, String apId) throws Exception {

        SignalQualityVo experienceSpeedVo = new SignalQualityVo();

        if (speedometer == null || speedometer.equals("")) {
            experienceSpeedVo.setSpeedometer("");
        } else {
            experienceSpeedVo.setSpeedometer(speedometer);
        }

        if (sceneId == null || sceneId.equals("") || "null".equals(areaId)) {
            experienceSpeedVo.setSceneId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            experienceSpeedVo.setSceneId(scene);
        }

        if (areaId == null || areaId.equals("") || "null".equals(areaId)) {
            experienceSpeedVo.setAreaId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            experienceSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            experienceSpeedVo.setAreaId(area);
        }

        if (apId == null || apId.equals("") || "null".equals(apId)) {
            experienceSpeedVo.setApId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            experienceSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            experienceSpeedVo.setAreaId(area);
            int ap = Integer.parseInt(apId);
            experienceSpeedVo.setAreaId(ap);
        }
        //判断是否有时间条件,没有就手动插入,查询1970到系统时间为止
        if (startTime == null || startTime.equals("")) {
            if (endTime == null || endTime.equals("")) {
                experienceSpeedVo.setStartTime(new Date(14400));
                experienceSpeedVo.setEndTime(new Date());
            }
        } else {
            experienceSpeedVo.setStartTime(new Date(startTime));
            experienceSpeedVo.setEndTime(new Date(endTime));
        }

        List<SignalQualityVo> ExperienceSpeedVoList = signalQualityApi.selectAll(experienceSpeedVo);

        JSONObject export = export(ExperienceSpeedVoList, request);
        return toSuccess(export);
    }


    @ApiOperation(value = "详情")
    @GetMapping({"/findById.do"})
    public JSONObject findById(String id) {

        long longId = Long.parseLong(id);

        return toSuccess(signalQualityApi.findById(longId));
    }


    public JSONObject export(List<SignalQualityVo> list, HttpServletRequest request) {
        Map<String, Object> bakMap = new HashMap<>();

        bakMap.put("signalQuality", list);
        JSONObject jsonObject = new JSONObject();
        String excelNamePath = this.getClass().getClassLoader().getResource("").getPath()  + "static" + File.separator+"excel" + File.separator;
        String newName = System.currentTimeMillis() + ".xlsx";
        String URL = this.getClass().getClassLoader().getResource("").getPath() + "excel" + File.separator;
//        URL=URL.substring(1);
        try {
            JxlsUtils.testExportExcel(URL + "signalQuality.xlsx", bakMap, excelNamePath + newName);
            jsonObject.put("status", "0");
            jsonObject.put("name", newName);
            return toSuccess(jsonObject);
        } catch (Exception e) {
            jsonObject.put("status", "1");
            return toSuccess(jsonObject);
        }
    }
}
