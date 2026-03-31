package com.suzao.net.speed.netspeed.controller.handle;

import com.alibaba.fastjson.JSONObject;

import com.suzao.net.speed.netspeed.base.Page;
import com.suzao.net.speed.netspeed.bo.handle.ApBo;
import com.suzao.net.speed.netspeed.bo.handle.AreaBo;
import com.suzao.net.speed.netspeed.bo.handle.SceneBo;
import com.suzao.net.speed.netspeed.service.handle.SceneApi;
import com.suzao.net.speed.netspeed.vo.handle.ApVo;
import com.suzao.net.speed.netspeed.vo.handle.AreaVo;
import com.suzao.net.speed.netspeed.vo.handle.SceneVo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import static com.suzao.net.speed.netspeed.util.ResponseUtil.toSuccess;

@Api(tags = " 场景区域AP")
@RestController
@RequestMapping({"/vesapp/scene"})
public class SceneController {

    @Autowired
    private SceneApi sceneApi;

    /*@PostMapping({"/saveSceneAndArea"})
    public JSONObject saveSceneAndArea() {
        //获取接口数据
        String getSceneData = HttpUtils.sendGet(ITMUrlConstant.ITM_HTTP_URL + ITMUrlConstant.ITM_HTTP_SCENE_URL);
        net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(getSceneData);

        List<SceneBo> sceneBoList = SceneHandle.jsonToList(jsonObj.get("data").toString(), SceneBo.class);

        //清空场景和区域数据库数据
        sceneApi.clearScene();
        sceneApi.clearArea();
        //保存场景数据
        sceneApi.batchInsert(sceneBoList);

        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("results", HttpSceneResponse.class);
        // 将JSON转换
        HttpSceneResponse weather = (HttpSceneResponse) net.sf.json.JSONObject.toBean(jsonObj, HttpSceneResponse.class, classMap);
        //获取场景个数
        int size = weather.getData().size();
        int toSuccess = 0;
        List<AreaBo> areaBoList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            //清空list内容再添加新数据
            areaBoList.clear();
            //获取区域
            List<AreaBo> sceneChildrenAllResponses = JSON.parseArray(net.sf.json.JSONObject.fromObject(weather.getData().get(i)).get("childScene").toString(), AreaBo.class);
            //添加每个区域里的场景Id
            for (AreaBo area : sceneChildrenAllResponses) {
                area.setSceneId(sceneBoList.get(i).getId());
                areaBoList.add(area);
            }

            toSuccess = sceneApi.batchInsertArea(areaBoList);
        }
        return toSuccess(toSuccess);
    }

    @RequestMapping({"/saveAp"})
    @ResponseBody
    public JSONObject saveAp() {
        //获取接口数据
        String getSceneData = HttpUtils.sendGet(ITMUrlConstant.ITM_HTTP_URL + ITMUrlConstant.ITM_HTTP_AP_URL);
        net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(getSceneData);

        List<ApBo> apBoList = SceneHandle.jsonToList(jsonObj.get("data").toString(), ApBo.class);

        Map<String, Class> classMap = new HashMap<String, Class>();
        classMap.put("results", HttpSceneResponse.class);
        // 将JSON转换
        HttpSceneResponse weather = (HttpSceneResponse) net.sf.json.JSONObject.toBean(jsonObj, HttpSceneResponse.class, classMap);

        //清空ap数据库数据
        sceneApi.clearAp();

        //获取区域个数
        int size = weather.getData().size();
        List<ApBo> apBoArrayList = new ArrayList<>();
        int toSuccess = 0;
        for (int i = 0; i < size; i++) {
            //清空list内容,避免重复添加数据
            apBoArrayList.clear();
            //获取区域
            List<ApBo> sceneChildrenAllResponses = JSON.parseArray(net.sf.json.JSONObject.fromObject(weather.getData().get(i)).get("childResource").toString(), ApBo.class);
            //添加每个Ap里的区域Id
            for (ApBo ap : sceneChildrenAllResponses) {
                ap.setAreaId(apBoList.get(i).getId());
                apBoArrayList.add(ap);
            }
            toSuccess = sceneApi.batchInsertAp(apBoArrayList);
        }
        return toSuccess(toSuccess);
    }*/

    @ApiOperation(value = "场景列表")
    @PostMapping({"/listAllScene.do"})
    public JSONObject listAll() {
        return toSuccess(sceneApi.selectAllScene());
    }

    @ApiOperation(value = "区域列表")
    @PostMapping({"/listAllArea.do"})
    public JSONObject listAllArea() {
        return toSuccess(sceneApi.selectAllArea());
    }

    @ApiOperation(value = "AP列表")
    @PostMapping({"/listAllAp.do"})
    public JSONObject listAllAp() {
        return toSuccess(sceneApi.selectAllAp());
    }


    @ApiOperation(value = "分页场景列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "当前页", required = true, dataType = "Integer",example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "Integer",example = "10"),
    })
    @GetMapping({"/listScene.do"})
    public JSONObject listScene(int pageNumber, int pageSize) {

        Page<SceneBo, SceneVo> page = new Page<>();

        int start = (pageNumber - 1) * pageSize;
        page.setStartRow(start);
        page.setRowCount(pageSize);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        sceneApi.selectPageScene(page);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        jsonObject.put("rows", page.getDatas());
        jsonObject.put("total", page.getTotalRecord());
        jsonObject.put("page", page.getStartRow());
        return jsonObject;
    }

    @ApiOperation(value = "分页区域列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "当前页", required = true, dataType = "Integer",example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "Integer",example = "10"),
    })
    @GetMapping({"/listArea.do"})
    public JSONObject listArea(int pageNumber, int pageSize) {
        Page<AreaBo, AreaVo> page = new Page<>();

        int start = (pageNumber - 1) * pageSize;
        page.setStartRow(start);
        page.setRowCount(pageSize);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        sceneApi.selectPageArea(page);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        jsonObject.put("rows", page.getDatas());
        jsonObject.put("total", page.getTotalRecord());
        jsonObject.put("page", page.getStartRow());
        return jsonObject;
    }

    @ApiOperation(value = "分页AP列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "当前页", required = true, dataType = "Integer",example = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "Integer",example = "10"),
    })
    @GetMapping({"/listAp.do"})
    public JSONObject listAp(int pageNumber, int pageSize) {
        Page<ApBo, ApVo> page = new Page<>();

        int start = (pageNumber - 1) * pageSize;
        page.setStartRow(start);
        page.setRowCount(pageSize);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        sceneApi.selectPageAp(page);


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", "200");
        jsonObject.put("rows", page.getDatas());
        jsonObject.put("total", page.getTotalRecord());
        jsonObject.put("page", page.getStartRow());
        return jsonObject;
    }

    @ApiOperation(value = "根据场景查询区域")
    @ApiImplicitParam(name = "sceneId", value = "场景ID", required = true, dataType = "Integer",example = "304005")
    @PostMapping({"/findAreaBySceneId.do"})
    public JSONObject findAreaBySceneId(int sceneId) {
        return toSuccess(sceneApi.findAreaBySceneId(sceneId));
    }

    @ApiOperation(value = "selectBySceneNameOrAreaNameOrApName")
    @RequestMapping({"/selectBySceneNameOrAreaNameOrApName.do"})
    public JSONObject selectBySceneNameOrAreaNameOrApName(NetworkSpeedVo vo) {
        return toSuccess(sceneApi.selectBySceneNameOrAreaNameOrApName(vo));
    }
}
