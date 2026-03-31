package com.suzao.net.speed.netspeed.controller.network;

import com.alibaba.fastjson.JSONObject;
import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.network.DeviceInformationBo;
import com.suzao.net.speed.netspeed.service.network.DeviceInformationApi;
import com.suzao.net.speed.netspeed.vo.network.DeviceInformationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.suzao.net.speed.netspeed.util.ResponseUtil.toSuccess;

/**
 * @name DeviceInformationController
 * @author mc
 * @date 2022/4/9 20:37
 * @version 1.0
 **/
@RestController
@RequestMapping({"/vesapp/deviceInformation"})
@Api(tags = "设备信息接口")
public class DeviceInformationController {

    @Autowired
    private DeviceInformationApi deviceInformationApi;

    @PostMapping("/save.do")
    @ApiOperation(value = "保存")
    public JSONObject save(@RequestBody DeviceInformationBo deviceInformationBo){

        ArrayList<DeviceInformationBo> deviceInformationList = new ArrayList<>();
        if(ObjectUtils.isEmpty(deviceInformationBo.getId())){
            deviceInformationBo.setId(0L);
        }
        deviceInformationList.add(deviceInformationBo);
        return toSuccess(deviceInformationApi.insert(deviceInformationList));
    }

    @GetMapping({"/listAll.do"})
    @ApiOperation(value = "列表")
    public JSONObject listAll(DeviceInformationVo deviceInformationVo) {
        return toSuccess(deviceInformationApi.selectAll(deviceInformationVo));
    }

    @GetMapping({"/list.do"})
    @ApiOperation(value = "分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "当前页", required = true, dataType = "Integer",example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "Integer",example = "10"),
            @ApiImplicitParam(name = "username", value = "用户", dataType = "string",example = "张三")
    })
    public JSONObject list(int pageNumber, int pageSize, String username){

        Page<DeviceInformationBo, DeviceInformationVo> page = new Page<>();
        DeviceInformationVo deviceInformationVo = new DeviceInformationVo();
        if (username == null || username.equals("")) {
            deviceInformationVo.setUsername("");
        } else {
            deviceInformationVo.setUsername(username);
        }

        int start = (pageNumber - 1)*pageSize;
        page.setStartRow(start);
        page.setRowCount(pageSize);
        page.setCondition(deviceInformationVo);

        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);

        deviceInformationApi.selectPage(page);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        jsonObject.put("rows", page.getDatas());
        jsonObject.put("total", page.getTotalRecord());
        jsonObject.put("page", page.getStartRow());
        return jsonObject;
    }

    @GetMapping({"/findById.do"})
    @ApiOperation(value = "通过id获取详情")
    @ApiImplicitParam(name = "id", value = "设备ID", required = true, dataType = "string",example = "1")
    public JSONObject findById(String id) {
        long longId = Long.parseLong(id);
        return toSuccess(deviceInformationApi.findById(longId));
    }
}
