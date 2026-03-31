package com.suzao.net.speed.netspeed.controller.network;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.network.PassValueBo;
import com.suzao.net.speed.netspeed.bo.network.PingSpeedBo;
import com.suzao.net.speed.netspeed.service.handle.SceneApi;
import com.suzao.net.speed.netspeed.service.network.PassValueApi;
import com.suzao.net.speed.netspeed.service.network.PingSpeedApi;
import com.suzao.net.speed.netspeed.util.JxlsUtils;
import com.suzao.net.speed.netspeed.vo.handle.ApVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import com.suzao.net.speed.netspeed.vo.network.PingSpeedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

import static com.suzao.net.speed.netspeed.util.ResponseUtil.toSuccess;

/**
 * @name PingSpeedController
 * @author mc
 * @date 2022/4/9 20:37
 * @version 1.0
 **/
@Api(tags = "Ping测速")
@RestController
@RequestMapping({"/vesapp/pingSpeed"})
public class PingSpeedController {

    @Autowired
    private PingSpeedApi pingSpeedApi;
    @Autowired
    private SceneApi sceneApi;
    @Autowired
    private PassValueApi passValueApi;

    @ApiOperation(value = "保存")
    @PostMapping({"/save.do"})
    public JSONObject save(@RequestBody PingSpeedBo pingSpeedBo) {
        Date date = new Date();
        pingSpeedBo.setTime(date);
        /*
         * 根据mac地址或mac5G查询关联的场景和区域
         */
        //判断传输过来的数据是否存在mac属性或mac5G属性
        if (pingSpeedBo.getMacAddress() != null || pingSpeedBo.getMacAddress5G() != null) {
            String macAddress = pingSpeedBo.getMacAddress();
            ApVo sceneAndArea = sceneApi.findSceneAndArea(macAddress);
            //判断是否根据mac地址查询到了相关联的场景和区域
            if (sceneAndArea == null) {
                //如果mac属性没有查到再判断mac5G是否有数据
                if (pingSpeedBo.getMacAddress5G() != null) {
                    String macAddress5G = pingSpeedBo.getMacAddress5G();
                    ApVo sceneAndArea5G = sceneApi.findSceneAndArea(macAddress5G);
                    //判断是否根据mac5G地址查询到了相关联的场景和区域
                    if (sceneAndArea5G != null) {
                        String mac5G = sceneAndArea5G.getMac();
                        if (mac5G == null) {
                            pingSpeedBo.setSceneId(null);
                            pingSpeedBo.setAreaId(null);
                            pingSpeedBo.setApId(null);
                        } else {
                            pingSpeedBo.setSceneId(sceneAndArea5G.getSceneId());
                            pingSpeedBo.setAreaId(sceneAndArea5G.getAreaId());
                            pingSpeedBo.setApId(sceneAndArea5G.getId());
                        }
                    }
                }
            } else {
                pingSpeedBo.setSceneId(sceneAndArea.getSceneId());
                pingSpeedBo.setAreaId(sceneAndArea.getAreaId());
                pingSpeedBo.setApId(sceneAndArea.getId());
            }
        }

        ArrayList<PingSpeedBo> pingSpeedList = new ArrayList<>();
        if(ObjectUtils.isEmpty(pingSpeedBo.getId())){
            pingSpeedBo.setId(0L);
        }
        pingSpeedList.add(pingSpeedBo);
        return toSuccess(pingSpeedApi.insert(pingSpeedList));
    }

    @ApiOperation(value = "分页列表")
    @GetMapping({"/list.do"})
    public JSONObject list(int pageNumber, int pageSize, String speedometer, Long startTime, Long endTime, String sceneId, String areaId, String apId) {

        Page<PingSpeedBo, PingSpeedVo> page = new Page<>();
        PingSpeedVo pingSpeedVo = new PingSpeedVo();
        if (speedometer == null || speedometer.equals("")) {
            pingSpeedVo.setSpeedometer("");
        } else {
            pingSpeedVo.setSpeedometer(speedometer);
        }

        if (sceneId == null || sceneId.equals("")) {
            pingSpeedVo.setSceneId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            pingSpeedVo.setSceneId(scene);
        }

        if (areaId == null || areaId.equals("")) {
            pingSpeedVo.setAreaId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            pingSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            pingSpeedVo.setAreaId(area);
        }

        if (apId == null || apId.equals("")) {
            pingSpeedVo.setApId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            pingSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            pingSpeedVo.setAreaId(area);
            int ap = Integer.parseInt(apId);
            pingSpeedVo.setAreaId(ap);
        }

        //判断是否有时间条件,没有就手动插入,查询1970到系统时间为止
        if (startTime == null || startTime.equals("")) {
            if (endTime == null || endTime.equals("")) {
                pingSpeedVo.setStartTime(new Date(14400));
                pingSpeedVo.setEndTime(new Date());
            }
        } else {
            pingSpeedVo.setStartTime(new Date(startTime));
            pingSpeedVo.setEndTime(new Date(endTime));
        }

        int start = (pageNumber - 1) * pageSize;
        page.setStartRow(start);
        page.setRowCount(pageSize);
        page.setCondition(pingSpeedVo);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        pingSpeedApi.selectPage(page);

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

        PingSpeedVo pingSpeedVo = new PingSpeedVo();

        if (speedometer == null || speedometer.equals("")) {
            pingSpeedVo.setSpeedometer("");
        } else {
            pingSpeedVo.setSpeedometer(speedometer);
        }

        if (sceneId == null || sceneId.equals("") || "null".equals(areaId)) {
            pingSpeedVo.setSceneId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            pingSpeedVo.setSceneId(scene);
        }

        if (areaId == null || areaId.equals("") || "null".equals(areaId)) {
            pingSpeedVo.setAreaId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            pingSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            pingSpeedVo.setAreaId(area);
        }

        if (apId == null || apId.equals("") || "null".equals(apId)) {
            pingSpeedVo.setApId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            pingSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            pingSpeedVo.setAreaId(area);
            int ap = Integer.parseInt(apId);
            pingSpeedVo.setAreaId(ap);
        }
        //判断是否有时间条件,没有就手动插入,查询1970到系统时间为止
        if (startTime == null || startTime.equals("")) {
            if (endTime == null || endTime.equals("")) {
                pingSpeedVo.setStartTime(new Date(14400));
                pingSpeedVo.setEndTime(new Date());
            }
        } else {
            pingSpeedVo.setStartTime(new Date(startTime));
            pingSpeedVo.setEndTime(new Date(endTime));
        }

        List<PingSpeedVo> pingSpeedVoList = pingSpeedApi.selectAll(pingSpeedVo);

        JSONObject export = export(pingSpeedVoList, request);

        return toSuccess(export);
    }

    @ApiOperation(value = "通过id获取详情")
    @GetMapping({"/findById"})
    public JSONObject findById(String id) {

        long longId = Long.parseLong(id);

        return toSuccess(pingSpeedApi.findById(longId));
    }

    //查询合格/不合格/全部的信息
    @ApiOperation(value = "查询合格/不合格/全部的信息")
    @RequestMapping({"/selectPassValue.do"})
    public JSONObject selectPassValue(PassValueVo vo, HttpServletRequest request) {
        List<PingSpeedVo> valueVoList;

        //查询合格标准值
        PassValueBo passValueBo = passValueApi.selectAll();

        vo.setLostPacketRatePassValue(passValueBo.getLostPacketRatePassValue());

        //根据标识判断查询大于等于/小于还是所有数据
        if (vo.getName() != null && vo.getName().equals("GTET")) {
            valueVoList = pingSpeedApi.selectGTETData(vo);
        } else if (vo.getName() != null && vo.getName().equals("LT")) {
            valueVoList = pingSpeedApi.selectLTData(vo);
        } else {
            valueVoList = pingSpeedApi.selectData(vo);
        }

        //记录下标
        List value = new ArrayList();

        //判断前台是否存在时间条件,不在区间内就删除元素
        for (int i = 0; i < valueVoList.size(); i++) {
            if (vo.getStartTime() != null && vo.getEndTime() != null) {

                if (valueVoList.get(i).getTime().getTime() < vo.getStartTime().getTime() || valueVoList.get(i).getTime().getTime() > vo.getEndTime().getTime()) {

                    value.add(i);
                }
            }
        }
        //防止删除全部数据
        if(value.size()!=0){

            valueVoList.retainAll(value);
        }


        //判断是否需要导出
        if (StringUtils.isNotBlank(vo.getExport())) {

            JSONObject export = export(valueVoList, request);

            return toSuccess(export);
        }
        return toSuccess(valueVoList);
    }

    public JSONObject export(List<PingSpeedVo> list, HttpServletRequest request) {
        Map<String, Object> bakMap = new HashMap<>();

        bakMap.put("pingSpeed", list);
        JSONObject jsonObject = new JSONObject();
        String excelNamePath = this.getClass().getClassLoader().getResource("").getPath()  + "static" + File.separator+"excel" + File.separator;
        String newName = System.currentTimeMillis() + ".xlsx";
        String URL = this.getClass().getClassLoader().getResource("").getPath() + "excel" + File.separator;
//        URL=URL.substring(1);
        try {
            JxlsUtils.testExportExcel(URL + "Ping.xlsx", bakMap, excelNamePath + newName);
            jsonObject.put("status", "0");
            jsonObject.put("name", newName);
            return toSuccess(jsonObject);
        } catch (Exception e) {
            jsonObject.put("status", "1");
            return toSuccess(jsonObject);
        }
    }
}
