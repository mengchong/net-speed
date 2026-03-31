package com.suzao.net.speed.netspeed.controller.network;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.network.NetworkSpeedBo;
import com.suzao.net.speed.netspeed.bo.network.PassValueBo;
import com.suzao.net.speed.netspeed.bo.network.SpeedRecordBo;
import com.suzao.net.speed.netspeed.service.handle.SceneApi;
import com.suzao.net.speed.netspeed.service.network.NetworkSpeedApi;
import com.suzao.net.speed.netspeed.service.network.PassValueApi;
import com.suzao.net.speed.netspeed.util.JxlsUtils;
import com.suzao.net.speed.netspeed.vo.handle.ApVo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import static com.suzao.net.speed.netspeed.util.ResponseUtil.toSuccess;

/**
 * @author mc
 * @version 1.0
 * @name NetworkSpeedController
 * @date 2022/4/9 20:37
 **/
@Api(tags = "网络测速接口")
@RestController
@RequestMapping({"/vesapp/networkSpeed"})
@Slf4j
public class NetworkSpeedController {

    @Autowired
    private NetworkSpeedApi networkSpeedApi;
    @Autowired
    private SceneApi sceneApi;

    @Autowired
    private PassValueApi passValueApi;

    @ApiOperation(value = "保存")
    @PostMapping({"/save.do"})
    public JSONObject save(@RequestBody NetworkSpeedBo networkSpeedBo) {
        Date date = new Date();
        networkSpeedBo.setTime(date);
        /*
         * 根据mac地址或mac5G查询关联的场景和区域
         */
        //判断传输过来的数据是否存在mac属性或mac5G属性
        if (networkSpeedBo.getMacAddress() != null || networkSpeedBo.getMacAddress5G() != null) {
            String macAddress = networkSpeedBo.getMacAddress();
            ApVo sceneAndArea = sceneApi.findSceneAndArea(macAddress);
            //判断是否根据mac地址查询到了相关联的场景和区域
            if (sceneAndArea == null) {
                //如果mac属性没有查到再判断mac5G是否有数据
                if (networkSpeedBo.getMacAddress5G() != null) {
                    String macAddress5G = networkSpeedBo.getMacAddress5G();
                    ApVo sceneAndArea5G = sceneApi.findSceneAndArea(macAddress5G);
                    //判断是否根据mac5G地址查询到了相关联的场景和区域
                    if (sceneAndArea5G != null) {
                        String mac5G = sceneAndArea5G.getMac();
                        if (mac5G == null) {
                            networkSpeedBo.setSceneId(null);
                            networkSpeedBo.setAreaId(null);
                            networkSpeedBo.setApId(null);
                        } else {
                            networkSpeedBo.setSceneId(sceneAndArea5G.getSceneId());
                            networkSpeedBo.setAreaId(sceneAndArea5G.getAreaId());
                            networkSpeedBo.setApId(sceneAndArea5G.getId());
                        }
                    }
                }
            } else {
                networkSpeedBo.setSceneId(sceneAndArea.getSceneId());
                networkSpeedBo.setAreaId(sceneAndArea.getAreaId());
                networkSpeedBo.setApId(sceneAndArea.getId());
            }
        }

        ArrayList<NetworkSpeedBo> networkSpeedList = new ArrayList<>();
        if (ObjectUtils.isEmpty(networkSpeedBo.getId())) {
            networkSpeedBo.setId(0L);
        }
        networkSpeedList.add(networkSpeedBo);
        return toSuccess(networkSpeedApi.insert(networkSpeedList));
    }


    @ApiOperation(value = "分页")
    @GetMapping({"/list.do"})
    public JSONObject list(int pageNumber, int pageSize, String speedometer, Long startTime, Long endTime, String sceneId, String areaId, String apId) {

        Page<NetworkSpeedBo, NetworkSpeedVo> page = new Page<>();
        NetworkSpeedVo networkSpeedVo = new NetworkSpeedVo();

        if (speedometer == null || speedometer.equals("")) {
            networkSpeedVo.setSpeedometer("");
        } else {
            networkSpeedVo.setSpeedometer(speedometer);
        }

        if (sceneId == null || sceneId.equals("")) {
            networkSpeedVo.setSceneId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            networkSpeedVo.setSceneId(scene);
        }

        if (areaId == null || areaId.equals("")) {
            networkSpeedVo.setAreaId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            networkSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            networkSpeedVo.setAreaId(area);
        }

        if (apId == null || apId.equals("")) {
            networkSpeedVo.setApId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            networkSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            networkSpeedVo.setAreaId(area);
            int ap = Integer.parseInt(apId);
            networkSpeedVo.setApId(ap);
        }
        //判断是否有时间条件,没有就手动插入,查询1970到系统时间为止
        if (startTime == null || startTime.equals("")) {
            if (endTime == null || endTime.equals("")) {
                networkSpeedVo.setStartTime(new Date(14400));
                networkSpeedVo.setEndTime(new Date());
            }
        } else {
            networkSpeedVo.setStartTime(new Date(startTime));
            networkSpeedVo.setEndTime(new Date(endTime));
        }

        int start = (pageNumber - 1) * pageSize;
        page.setStartRow(start);
        page.setRowCount(pageSize);
        page.setCondition(networkSpeedVo);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        networkSpeedApi.selectPage(page);

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

        NetworkSpeedVo networkSpeedVo = new NetworkSpeedVo();

        if (speedometer == null || speedometer.equals("")) {
            networkSpeedVo.setSpeedometer("");
        } else {
            networkSpeedVo.setSpeedometer(speedometer);
        }

        if (sceneId == null || sceneId.equals("") || "null".equals(areaId)) {
            networkSpeedVo.setSceneId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            networkSpeedVo.setSceneId(scene);
        }

        if (areaId == null || areaId.equals("") || "null".equals(areaId)) {
            networkSpeedVo.setAreaId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            networkSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            networkSpeedVo.setAreaId(area);
        }

        if (apId == null || apId.equals("") || "null".equals(apId)) {
            networkSpeedVo.setApId(null);
        } else {
            int scene = Integer.parseInt(sceneId);
            networkSpeedVo.setSceneId(scene);
            int area = Integer.parseInt(areaId);
            networkSpeedVo.setAreaId(area);
            int ap = Integer.parseInt(apId);
            networkSpeedVo.setApId(ap);
        }
        //判断是否有时间条件,没有就手动插入,查询1970到系统时间为止
        if (startTime == null || startTime.equals("")) {
            if (endTime == null || endTime.equals("")) {
                networkSpeedVo.setStartTime(new Date(14400));
                networkSpeedVo.setEndTime(new Date());
            }
        } else {
            networkSpeedVo.setStartTime(new Date(startTime));
            networkSpeedVo.setEndTime(new Date(endTime));
        }

        List<NetworkSpeedVo> networkSpeedVoList = networkSpeedApi.selectAll(networkSpeedVo);

        JSONObject export = export(networkSpeedVoList, request, response);

        return toSuccess(export);
    }

    @ApiOperation(value = "详情")
    @GetMapping({"/findById.do"})
    public JSONObject findById(String id) {

        long longId = Long.parseLong(id);

        return toSuccess(networkSpeedApi.findById(longId));
    }

    //查询合格/不合格/全部的信息
    @ApiOperation(value = "查询合格/不合格/全部的信息")
    @RequestMapping({"/selectPassValue.do"})
    public JSONObject selectPassValue(PassValueVo vo, HttpServletRequest request, HttpServletResponse response) {
        List<NetworkSpeedVo> valueVoList;

        //查询合格标准值
        PassValueBo passValueBo = passValueApi.selectAll();

        vo.setNetworkDelayPassValue(passValueBo.getNetworkDelayPassValue());

        //根据标识判断查询大于等于/小于还是所有数据
        if (vo.getName() != null && vo.getName().equals("GTET")) {
            valueVoList = networkSpeedApi.selectGTETData(vo);
        } else if (vo.getName() != null && vo.getName().equals("LT")) {
            valueVoList = networkSpeedApi.selectLTData(vo);
        } else {
            valueVoList = networkSpeedApi.selectData(vo);
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
        if (value.size() != 0) {

            valueVoList.retainAll(value);
        }

        //判断是否需要导出
        if (StringUtils.isNotBlank(vo.getExport())) {

            JSONObject export = export(valueVoList, request, response);

            return toSuccess(export);
        }

        return toSuccess(valueVoList);

    }


    @ApiOperation(value = "测速记录")
    @GetMapping({"/record.do"})
    public JSONObject record(HttpServletRequest request) {

//        SpeedRecordBo speedRecordBo = new SpeedRecordBo();
//        speedRecordBo.setSpeedName("上下行");
//        speedRecordBo.setSpeedMetric("100MB/s");
//        speedRecordBo.setSpeedTime("2022-07-09 17:52:00");
//        speedRecordBo.setSpeedType("体验测速");
//        List<SpeedRecordBo> speedRecordBos = new ArrayList<>();
//        speedRecordBos.add(speedRecordBo);
        return toSuccess(this.networkSpeedApi.getSpeedRecord());
    }

    public JSONObject export(List<NetworkSpeedVo> list, HttpServletRequest request, HttpServletResponse response) {
        return export(list,request);
        /*ExcelWriter writer = ExcelUtil.getWriter();
        writer.setOnlyAlias(true);
        writer.write(list, true);
        exportExcelWithBlobStream(request,response, "测试导出.xls", writer);
        JSONObject jsonObject = new JSONObject();
        return toSuccess(jsonObject);*/
    }

    public static void exportExcelWithBlobStream(HttpServletRequest request,HttpServletResponse response, String excelName, ExcelWriter writer) {
        Workbook workbook = writer.getWorkbook();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ServletOutputStream out = null;
        try {
            String userAgent = request.getHeader("user-agent");
            if (userAgent != null && userAgent.indexOf("Firefox") >= 0 ||
                    userAgent.indexOf("Chrome") >= 0 ||
                    userAgent.indexOf("Safari") >= 0) {
                excelName = new String((excelName).getBytes(), "ISO8859-1");
            } else {
                excelName = URLEncoder.encode(excelName, "UTF8"); //其他浏览器
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" +excelName);
            workbook.write(response.getOutputStream());
            workbook.write(os);
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            log.error(">>> 导出数据异常：{}", e.getMessage());
        } finally {
            try {
                os.close();
                writer.close();
                IoUtil.close(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public JSONObject export(List<NetworkSpeedVo> list, HttpServletRequest request) {

        Map<String, Object> bakMap = new HashMap<>();

        bakMap.put("networkSpeed", list);
        JSONObject jsonObject = new JSONObject();

        String excelNamePath = this.getClass().getClassLoader().getResource("").getPath()  + "static" + File.separator+"excel" + File.separator;
        String newName = System.currentTimeMillis() + ".xlsx";
        String URL = this.getClass().getClassLoader().getResource("").getPath() + "excel" + File.separator;
//        URL=URL.substring(1);
        try {
            JxlsUtils.testExportExcel(URL + "network.xlsx", bakMap, excelNamePath + newName);
            jsonObject.put("name", newName);
            jsonObject.put("status", "0");
//            this.setResponseHeader(response, excelName);
            return toSuccess(jsonObject);
        } catch (Exception e) {
            jsonObject.put("status", "1");
            return toSuccess(jsonObject);
        }
    }
}
