package com.suzao.net.speed.netspeed.controller.network;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.suzao.net.speed.netspeed.bo.network.PassValueBo;
import com.suzao.net.speed.netspeed.service.handle.SceneApi;
import com.suzao.net.speed.netspeed.service.network.ExperienceSpeedApi;
import com.suzao.net.speed.netspeed.service.network.NetworkSpeedApi;
import com.suzao.net.speed.netspeed.service.network.PassValueApi;
import com.suzao.net.speed.netspeed.service.network.PingSpeedApi;
import com.suzao.net.speed.netspeed.util.JxlsUtils;
import com.suzao.net.speed.netspeed.vo.handle.ApVo;
import com.suzao.net.speed.netspeed.vo.network.ExperienceSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.NetworkSpeedVo;
import com.suzao.net.speed.netspeed.vo.network.PassValueVo;
import com.suzao.net.speed.netspeed.vo.network.PingSpeedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.suzao.net.speed.netspeed.util.ResponseUtil.toSuccess;


/**
 * @name PassValueController
 * @author mc
 * @date 2022/4/9 20:37
 * @version 1.0
 **/
@Api(tags = "PassValueController")
@RestController
@RequestMapping({"/vesapp/passValue"})
//@ApiIgnore
public class PassValueController {

    @Autowired
    private PassValueApi passValueApi;

    @Autowired
    private NetworkSpeedApi networkSpeedApi;

    @Autowired
    private PingSpeedApi pingSpeedApi;

    @Autowired
    private ExperienceSpeedApi experienceSpeedApi;

    @Autowired
    private SceneApi sceneApi;

    @ApiOperation(value = "保存")
    @RequestMapping({"/save.do"})
    public JSONObject save(PassValueBo passValueBo) {

        List<PassValueBo> passValueBoArrayList = new ArrayList<>();

        passValueBo.setId(1);

        passValueBoArrayList.add(passValueBo);

        return toSuccess(passValueApi.insert(passValueBoArrayList));
    }

    @ApiOperation(value = "列表")
    @RequestMapping({"/listAll.do"})
    public JSONObject listAll() {
        return toSuccess(passValueApi.selectAll());
    }

    @ApiOperation(value = "选择测试AP")
    @RequestMapping({"/selectTestAp.do"})
    public JSONObject selectTestAp(NetworkSpeedVo networkVo) {

        List<PassValueVo> apTestCountList = new ArrayList<>();
        //获取过滤后的数据
        List<PassValueVo> Testlist = listDistinct(networkVo);

        //过滤重复ap填充到集合中
        apTestCountList.addAll(Testlist.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getApName()))), ArrayList::new)
                ));

        List<Long> idsList = new ArrayList();

        if (StringUtils.isNotBlank(networkVo.getApName())) {

            for (int i = 0; i < apTestCountList.size(); i++) {
                if (apTestCountList.get(i).getApName().equals(networkVo.getApName())) {

                    idsList.add(apTestCountList.get(i).getId());
                }
            }

        } else if (StringUtils.isNotBlank(networkVo.getAreaName())) {

            for (int i = 0; i < apTestCountList.size(); i++) {
                if (apTestCountList.get(i).getAreaName().equals(networkVo.getAreaName())) {

                    idsList.add(apTestCountList.get(i).getId());
                }
            }
        } else {

            for (int i = 0; i < apTestCountList.size(); i++) {
                if (apTestCountList.get(i).getSceneName().equals(networkVo.getSceneName())) {

                    idsList.add(apTestCountList.get(i).getId());
                }
            }
        }

        if (idsList.size() == 0) {
            return toSuccess(new ArrayList<>().add(new ApVo()));
        }

        List<ApVo> apVoList = sceneApi.selectByIds(idsList);

        return toSuccess(apVoList);
    }

    @ApiOperation(value = "记录")
    @RequestMapping({"/passValue.do"})
    public JSONObject passValue(NetworkSpeedVo networkVo, HttpServletRequest request) {

        ArrayList<PassValueVo> PassValueVoList = new ArrayList<>();

        //查询合格标准值
        PassValueBo passValueBo = passValueApi.selectAll();
        //网络测速合格标准
        int normal = Integer.parseInt(passValueBo.getNetworkDelayPassValue());

        //记录合格数量和不合格数量
        int qualified;
        int failed;

        //保存条件判断中的场景/区域/AP名称
        String EQName;
        //记录查询结果
        List<NetworkSpeedVo> networkSpeedVoList;
        List<PassValueVo> apCountList = new ArrayList<>();
        List<PassValueVo> apTestCountList = new ArrayList<>();
        //记录过滤后的测试ap数量
        List<PassValueVo> apTestCount = new ArrayList<>();
        //过滤查询出来的名称
        Set<String> names;
        //判断查询的是区域或者场景或者AP
        if (networkVo.getAreaId() != null) {

            networkSpeedVoList = networkSpeedApi.networkPassvalueAP(networkVo);

            apCountList = sceneApi.selectAPCount(networkVo);

            //过滤取出区域名称
            names = networkSpeedVoList
                    .stream()
                    .map(NetworkSpeedVo::getApName)
                    .collect(Collectors.toSet());

        } else if (networkVo.getSceneId() != null) {

            networkSpeedVoList = networkSpeedApi.networkPassvalueArea(networkVo);

            apCountList = sceneApi.selectAPCount(networkVo);

            //过滤取出区域名称
            names = networkSpeedVoList
                    .stream()
                    .map(NetworkSpeedVo::getAreaName)
                    .collect(Collectors.toSet());

        } else {

            networkSpeedVoList = networkSpeedApi.networkPassvalue(networkVo);

            apCountList = sceneApi.selectAPCount(networkVo);


            //过滤取出区域名称
            names = networkSpeedVoList
                    .stream()
                    .map(NetworkSpeedVo::getSceneName)
                    .collect(Collectors.toSet());

        }

        //判断标准区分合格和不合格数量
        for (String name : names) {

            PassValueVo passValueVo = new PassValueVo();
            //初始化计数器
            qualified = 0;
            failed = 0;

            for (NetworkSpeedVo vo : networkSpeedVoList) {
                //判断查询的是场景/区域/AP
                if (networkVo.getAreaId() != null) {
                    EQName = vo.getApName();
                } else if (networkVo.getSceneId() != null) {
                    EQName = vo.getAreaName();
                } else {
                    EQName = vo.getSceneName();
                }
                //判断是否场景名称一致
                if (name.equals(EQName)) {
                    //去除多余的单位
                    String networkDelay = vo.getNetworkDelay().trim().replace("ms", "");
                    //判断前台是否有时间条件以及判断查询出来的数据是否有时间
                    if (networkVo.getStartTime() != null && networkVo.getEndTime() != null) {

                        if (vo.getTime() != null) {
                            //判断如果查询出来的时间不在区间内则不执行之后操作
                            if (vo.getTime().getTime() < networkVo.getStartTime().getTime() || vo.getTime().getTime() > networkVo.getEndTime().getTime()) {

                                continue;
                            }

                        }
                    }

                    //小于指定延迟标准为合格(为0说明没有数据)
                    if (Float.parseFloat(networkDelay) != 0) {
                        if (Float.parseFloat(networkDelay) < normal) {
                            qualified++;
                        } else {
                            failed++;
                        }
                    }

                }
            }

            //填充区域名称和合格不合格数量
            passValueVo.setName(name);
            passValueVo.setNetworkSpeedQualified(qualified);
            passValueVo.setNetworkSpeedFailed(failed);
            passValueVo.setNetworkSpeedSum(qualified + failed);

            //填充ap数量(没有查询到说明该ap没有测试记录初始化为0)
            for (int a = 0; a < apCountList.size(); a++) {

                //if (apCountList.get(a).getSceneName().equals(passValueVo.getName()) || apCountList.get(a).getAreaName().equals(passValueVo.getName()) || apCountList.get(a).getApName().equals(passValueVo.getName())) {
                if (apCountList.get(a).getSceneName().equals(passValueVo.getName()) || passValueVo.getName().equals(apCountList.get(a).getAreaName()) || passValueVo.getName().equals(apCountList.get(a).getApName())) {
                    //填充ap总数
                    passValueVo.setApCount(apCountList.get(a).getApCount());

                }
            }

            PassValueVoList.add(passValueVo);

        }
        //查询ping测速合格和不合格数量
        List<PassValueVo> passValuePingSpeed = passValuePingSpeed(passValueBo.getLostPacketRatePassValue(), networkVo);
        //查询体验测速合格不合格数量
        List<PassValueVo> passValueExperienceSpeed = passValueExperienceSpeed(passValueBo.getNetworkPassValue(), networkVo);
        //最终数据
        List<PassValueVo> resultList = new ArrayList();

//        Set<String> set = new HashSet<>();
//
//        set.addAll(networkList.stream().map(e -> e.getApName()).collect(Collectors.toList()));
//        set.addAll(pingList.stream().map(e -> e.getApName()).collect(Collectors.toList()));
//        set.addAll(experList.stream().map(e -> e.getApName()).collect(Collectors.toList()));
        //获取过滤后的数据
        List<PassValueVo> Testlist = listDistinct(networkVo);

        //过滤重复ap填充到集合中
        apTestCountList.addAll(Testlist.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getApName()))), ArrayList::new)
                ));


        for (int i = 0; i < PassValueVoList.size(); i++) {
            //保存每个场景区域下的数据
            PassValueVo passValueVo = new PassValueVo();
            //保存场景或区域名称
            passValueVo.setName(PassValueVoList.get(i).getName());
            //保存网络测速数据
            passValueVo.setNetworkSpeedQualified(PassValueVoList.get(i).getNetworkSpeedQualified());
            passValueVo.setNetworkSpeedFailed(PassValueVoList.get(i).getNetworkSpeedFailed());
            passValueVo.setNetworkSpeedSum(PassValueVoList.get(i).getNetworkSpeedSum());
            passValueVo.setApCount(PassValueVoList.get(i).getApCount());

            int count = 0;

            if (networkVo.getAreaId() != null && networkVo.getAreaId() != 0) {
                for (PassValueVo paVo : apTestCountList) {
                    if (paVo.getApName().equals(passValueVo.getName())) {
                        passValueVo.setTestApCount(++count);
                    }
                }
            } else if (networkVo.getSceneId() != null && networkVo.getSceneId() != 0) {
                for (PassValueVo paVo : apTestCountList) {
                    if (paVo.getAreaName().equals(passValueVo.getName())) {
                        passValueVo.setTestApCount(++count);
                    }
                }
            } else {
                for (PassValueVo paVo : apTestCountList) {
                    if (paVo.getSceneName().equals(passValueVo.getName())) {
                        passValueVo.setTestApCount(++count);
                    }
                }
            }

            //保存ping测速数据
            for (PassValueVo pingVo : passValuePingSpeed) {
                //找到与名字相符的数据,保存后跳出循环
                if (pingVo.getName().equals(passValueVo.getName())) {
                    passValueVo.setPingSpeedQualified(pingVo.getPingSpeedQualified());
                    passValueVo.setPingSpeedFailed(pingVo.getPingSpeedFailed());
                    passValueVo.setPingSpeedSum(pingVo.getPingSpeedSum());
                    passValueVo.setSum(pingVo.getPingSpeedSum());

                    break;
                }
            }

            //保存体验测速数据
            for (PassValueVo experienceVo : passValueExperienceSpeed) {
                //找到与名字相符的数据,保存后跳出循环
                if (experienceVo.getName().equals(passValueVo.getName())) {
                    passValueVo.setExperienceSpeedQualified(experienceVo.getExperienceSpeedQualified());
                    passValueVo.setExperienceSpeedFailed(experienceVo.getExperienceSpeedFailed());
                    passValueVo.setExperienceSpeedSum(experienceVo.getExperienceSpeedSum());
                    passValueVo.setSum(passValueVo.getSum() + experienceVo.getExperienceSpeedSum());
                    break;
                }
            }

            //保存场景区域下记录总和
            passValueVo.setSum(passValueVo.getSum() + PassValueVoList.get(i).getNetworkSpeedSum());

            //保存场景区域下测试ap数
            resultList.add(passValueVo);

        }

        //判断是否需要导出
        if (StringUtils.isNotBlank(networkVo.getExport())) {
            JSONObject export = export(resultList, request);

            return toSuccess(export);
        }

        return toSuccess(resultList);
    }

    public List<PassValueVo> passValuePingSpeed(String passValue, NetworkSpeedVo networkVo) {
        //保存最后数据
        ArrayList<PassValueVo> PassValueVoList = new ArrayList<>();
        //ping测速合格标准
        int pingNormal = Integer.parseInt(passValue);
        //记录合格数量和不合格数量
        int qualified;
        int failed;
        //记录判断需要的场景/区域/AP名称
        String EQName;
        //记录查询结果
        List<PingSpeedVo> pingSpeedVoList;
        List<PassValueVo> apCountList = new ArrayList<>();
        List<PassValueVo> apTestCountList = new ArrayList<>();

        List<PassValueVo> apTestCount = new ArrayList<>();
        //记录过滤后的场景/区域/AP名称
        Set<String> names;
        //查询ping测速场景/区域/AP下合格和不合格数量
        if (networkVo.getAreaId() != null) {
            pingSpeedVoList = pingSpeedApi.pingPassvalueAP(networkVo);

            apCountList = sceneApi.selectAPCount(networkVo);

            apTestCountList = pingSpeedApi.selectAPCountByTestFromPingSpeed(networkVo);

            //过滤取出区域名称
            names = pingSpeedVoList
                    .stream()
                    .map(PingSpeedVo::getApName)
                    .collect(Collectors.toSet());

        } else if (networkVo.getSceneId() != null) {
            pingSpeedVoList = pingSpeedApi.pingPassvalueArea(networkVo);

            apCountList = sceneApi.selectAPCount(networkVo);

            apTestCountList = pingSpeedApi.selectAPCountByTestFromPingSpeed(networkVo);

            //过滤取出区域名称
            names = pingSpeedVoList
                    .stream()
                    .map(PingSpeedVo::getAreaName)
                    .collect(Collectors.toSet());

        } else {
            pingSpeedVoList = pingSpeedApi.pingPassvalue(networkVo);

            apCountList = sceneApi.selectAPCount(networkVo);

            apTestCountList = pingSpeedApi.selectAPCountByTestFromPingSpeed(networkVo);

            //过滤取出区域名称
            names = pingSpeedVoList
                    .stream()
                    .map(PingSpeedVo::getSceneName)
                    .collect(Collectors.toSet());

        }

        //合并合格和不合格数量
        for (String name : names) {

            PassValueVo passValueVo = new PassValueVo();
            //初始化计数器
            qualified = 0;
            failed = 0;
            for (PingSpeedVo vo : pingSpeedVoList) {
                //判断查询的是场景/区域/AP
                if (networkVo.getAreaId() != null) {
                    EQName = vo.getApName();
                } else if (networkVo.getSceneId() != null) {
                    EQName = vo.getAreaName();
                } else {
                    EQName = vo.getSceneName();
                }
                //判断是否区域名称一致
                if (name.equals(EQName)) {
                    //去除多余的单位
                    String packetLossRate = vo.getPacketLossRate().trim().replace("%", "");

                    //判断前台是否有时间条件以及判断查询出来的数据是否有时间
                    if (networkVo.getStartTime() != null && networkVo.getEndTime() != null) {

                        if (vo.getTime() != null) {
                            //判断如果查询出来的时间不在区间内则不执行之后操作
                            if (vo.getTime().getTime() < networkVo.getStartTime().getTime() || vo.getTime().getTime() > networkVo.getEndTime().getTime()) {

                                continue;
                            }

                        }
                    }

                    //判断是否是数据库传来的数据
                    if (vo.getPacketLossRate().contains("%")) {

                        //小于指定延迟标准为合格
                        if (NumberUtils.toFloat(packetLossRate) < pingNormal) {
                            qualified++;
                        } else {
                            failed++;
                        }
                    }

                }
            }
            //填充场景名称和合格不合格数量
            passValueVo.setName(name);
            passValueVo.setPingSpeedQualified(qualified);
            passValueVo.setPingSpeedFailed(failed);
            passValueVo.setPingSpeedSum(qualified + failed);
            //填充ap数量(没有查询到说明该ap没有测试记录初始化为0)
            for (int a = 0; a < apCountList.size(); a++) {

                // if (apCountList.get(a).getSceneName().equals(passValueVo.getName()) || apCountList.get(a).getAreaName().equals(passValueVo.getName()) || apCountList.get(a).getApName().equals(passValueVo.getName())) {
                if (apCountList.get(a).getSceneName().equals(passValueVo.getName()) || passValueVo.getName().equals(apCountList.get(a).getAreaName()) || passValueVo.getName().equals(apCountList.get(a).getApName())) {
                    //填充ap总数
                    passValueVo.setApCount(apCountList.get(a).getApCount());

                }
            }

            PassValueVoList.add(passValueVo);

        }

        return PassValueVoList;
    }

    public List<PassValueVo> passValueExperienceSpeed(String passValue, NetworkSpeedVo networkVo) {

        //保存最后数据
        ArrayList<PassValueVo> PassValueVoList = new ArrayList<>();
        //ping测速合格标准
        int experienceNormal = Integer.parseInt(passValue);
        //记录合格数量和不合格数量
        int qualified;
        int failed;
        float speed = 0;
        String EQName;
        //保存查询的数据
        List<ExperienceSpeedVo> experienceSpeedVoList;
        List<PassValueVo> apCountList = new ArrayList<>();
        List<PassValueVo> apTestCountList = new ArrayList<>();

        List<PassValueVo> apTestCount = new ArrayList<>();
        //保存过滤后的场景/区域/AP名称
        Set<String> names;
        if (networkVo.getAreaId() != null) {

            experienceSpeedVoList = experienceSpeedApi.experiencePassvalueAP(networkVo);

            apCountList = sceneApi.selectAPCount(networkVo);

            apTestCountList = experienceSpeedApi.selectAPCountByTestFromExperienceSpeed(networkVo);

            //过滤取出区域名称
            names = experienceSpeedVoList
                    .stream()
                    .map(ExperienceSpeedVo::getApName)
                    .collect(Collectors.toSet());

        } else if (networkVo.getSceneId() != null) {

            experienceSpeedVoList = experienceSpeedApi.experiencePassvalueArea(networkVo);

            apCountList = sceneApi.selectAPCount(networkVo);

            apTestCountList = experienceSpeedApi.selectAPCountByTestFromExperienceSpeed(networkVo);

            //过滤取出区域名称
            names = experienceSpeedVoList
                    .stream()
                    .map(ExperienceSpeedVo::getAreaName)
                    .collect(Collectors.toSet());

        } else {

            //查询ping测速场景下所有记录
            experienceSpeedVoList = experienceSpeedApi.experiencePassvalue(networkVo);

            apCountList = sceneApi.selectAPCount(networkVo);

            apTestCountList = experienceSpeedApi.selectAPCountByTestFromExperienceSpeed(networkVo);

            //过滤取出区域名称
            names = experienceSpeedVoList
                    .stream()
                    .map(ExperienceSpeedVo::getSceneName)
                    .collect(Collectors.toSet());

        }

        //合并合格和不合格数量
        for (String name : names) {

            PassValueVo passValueVo = new PassValueVo();
            //初始化计数器
            qualified = 0;
            failed = 0;

            for (ExperienceSpeedVo vo : experienceSpeedVoList) {
                //判断查询的是场景/区域/AP
                if (networkVo.getAreaId() != null) {
                    EQName = vo.getApName();
                } else if (networkVo.getSceneId() != null) {
                    EQName = vo.getAreaName();
                } else {
                    EQName = vo.getSceneName();
                }

                //大于等于标准为合格
                if (vo.getSpeed().contains("MB") || vo.getSpeed().contains("KB")) {
                    //判断是否区域名称一致
                    if (name.equals(EQName)) {

                        //判断前台是否有时间条件以及判断查询出来的数据是否有时间
                        if (networkVo.getStartTime() != null && networkVo.getEndTime() != null) {

                            if (vo.getTime() != null) {
                                //判断如果查询出来的时间不在区间内则不执行之后操作
                                if (vo.getTime().getTime() < networkVo.getStartTime().getTime() || vo.getTime().getTime() > networkVo.getEndTime().getTime()) {

                                    continue;
                                }

                            }
                        }

                        //转换单位
                        if (vo.getSpeed().contains("MB/s")) {

                            speed = Float.parseFloat(vo.getSpeed().trim().replace("MB/s", "")) * 1024;

                        } else if (vo.getSpeed().contains("KB/s")) {

                            speed = Float.parseFloat(vo.getSpeed().trim().replace("KB/s", ""));

                        }
                        if (speed >= experienceNormal) {
                            qualified++;
                        } else {
                            failed++;
                        }
                    }


                }
            }
            //填充场景名称和合格不合格数量
            passValueVo.setName(name);
            passValueVo.setExperienceSpeedQualified(qualified);
            passValueVo.setExperienceSpeedFailed(failed);
            passValueVo.setExperienceSpeedSum(qualified + failed);
            //填充ap数量(没有查询到说明该ap没有测试记录初始化为0)
            for (int a = 0; a < apCountList.size(); a++) {

                //if (apCountList.get(a).getSceneName().equals(passValueVo.getName()) || apCountList.get(a).getAreaName().equals(passValueVo.getName()) || apCountList.get(a).getApName().equals(passValueVo.getName())) {
                if (apCountList.get(a).getSceneName().equals(passValueVo.getName()) || passValueVo.getName().equals(apCountList.get(a).getAreaName()) || passValueVo.getName().equals(apCountList.get(a).getApName())) {

                        //填充ap总数
                    passValueVo.setApCount(apCountList.get(a).getApCount());

                }
            }

            PassValueVoList.add(passValueVo);

        }

        return PassValueVoList;
    }

    public JSONObject export(List<PassValueVo> list, HttpServletRequest request) {

        Map<String, Object> bakMap = new HashMap<>();

        bakMap.put("PassValueVo", list);
        JSONObject jsonObject = new JSONObject();
        String excelNamePath = this.getClass().getClassLoader().getResource("").getPath()  + "static" + File.separator+"excel" + File.separator;
        String newName = System.currentTimeMillis() + ".xlsx";
        String URL = this.getClass().getClassLoader().getResource("").getPath() + "excel" + File.separator;
//        URL=URL.substring(1);
        try {
            JxlsUtils.testExportExcel(URL + "PassValue.xlsx", bakMap, excelNamePath + newName);
            jsonObject.put("name", newName);
            jsonObject.put("status", "0");
//            this.setResponseHeader(response, excelName);
            return toSuccess(jsonObject);
        } catch (Exception e) {
            jsonObject.put("status", "1");
            return toSuccess(jsonObject);
        }
    }
    //查询网络测速,体验测速,ping测速过滤重复ap
    public List listDistinct(NetworkSpeedVo vo) {

        List Testlist = new ArrayList();

        //过滤测试ap
        List<PassValueVo> networkList = networkSpeedApi.selectAPCountByTestFromNetworkSpeed(vo);
        List<PassValueVo> pingList = pingSpeedApi.selectAPCountByTestFromPingSpeed(vo);
        List<PassValueVo> experList = experienceSpeedApi.selectAPCountByTestFromExperienceSpeed(vo);

        Testlist.addAll(networkList.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getApName()))), ArrayList::new)
                ));
        Testlist.addAll(pingList.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getApName()))), ArrayList::new)
                ));
        Testlist.addAll(experList.stream()
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getApName()))), ArrayList::new)
                ));

        return Testlist;
    }

}
