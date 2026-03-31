/**
 * Created by songby on 2019/10/11 0011.
 */
/**
 * 引入模块
 */
layui.use(['laydate', 'form', 'table'], function () {
    var laydate = layui.laydate
        , form = layui.form
        , table = layui.table
        , flow = []
        , longTime = []
        , user = []
        , tableData;

    //判断是否存在标准
    $.ajax({
        url: "../vesapp/passValue/listAll.do",
        success: function (result) {
            let res = result.data;
            if (typeof res === 'undefined' || res === null) {
                layer.closeAll();
                layer.alert("请先输入统计标准!");
            }
        }
    });

    //执行一个laydate实例
    laydate.render({
        elem: '#startTime' //指定元素
    });

    //执行一个laydate实例
    laydate.render({
        elem: '#endTime' //指定元素
    });

    table.render({
        elem: '#contentTable'
        ,
        url: '../vesapp/passValue/passValue.do' //数据接口
        ,
        page: false //开启分页
        ,
        id: 'tableReload'
        ,
        response: {
            statusName: 'code' //规定数据状态的字段名称，默认：code
            , statusCode: 200 //规定成功的状态码，默认：0
            , msgName: 'msg' //规定状态信息的字段名称，默认：msg
            , countName: 'total' //规定数据总数的字段名称，默认：count
            , dataName: 'data' //规定数据列表的字段名称，默认：data
        }
        ,
        cols: [
            [ //表头
                {
                    field: 'name',
                    title: '名称',
                    rowspan: 2,
                    align: "center",
                    templet: '<div><span title="{{d.name}}">{{d.name}}</span></div>'
                }
                , {field: 'sum', title: '总数', rowspan: 2, align: "center"}
                , {
                field: 'apCount',
                title: 'ap总数',
                rowspan: 2,
                align: "center",
                event: 'apCount',
                style: 'cursor: pointer;'
            }
                , {
                field: 'testApCount', title: '测试ap数', rowspan: 2, align: "center", event: 'testApCount',
                style: 'cursor: pointer;'
            }
                , {field: 'networkSpeed', title: '网络测速', colspan: 3, align: "center", width: 200}
                , {field: 'experienceSpeed', title: '体验测速', colspan: 3, align: "center", width: 200}
                , {field: 'pingSpeed', title: 'ping测速', colspan: 3, align: "center", width: 200}
            ], [
                {
                    field: 'networkSpeedQualified',
                    title: '合格',
                    align: "center",
                    event: 'networkQualified',
                    style: 'cursor: pointer;'
                }
                , {
                    field: 'networkSpeedFailed',
                    title: '不合格',
                    align: "center",
                    event: 'networkFailed',
                    style: 'cursor: pointer;'
                }
                , {
                    field: 'networkSpeedSum',
                    title: '合计',
                    align: "center",
                    event: 'networkTotal',
                    style: 'cursor: pointer;'
                }
            ]
        ]
        ,
        done: function (res, curr, count) {
            //存储合格和不合格数量
            let name = [],
                qualified = [],
                failed = [],
                experienceQualified = [], experienceFailed = [], pingQualified = [], pingFailed = [];

            for (let i = 0; i < res.data.length; i++) {

                name.push(res.data[i].name);
                qualified.push(res.data[i].networkSpeedQualified);
                failed.push(res.data[i].networkSpeedFailed);
                experienceQualified.push(res.data[i].experienceSpeedQualified);
                experienceFailed.push(res.data[i].experienceSpeedFailed);
                pingQualified.push(res.data[i].pingSpeedQualified);
                pingFailed.push(res.data[i].pingSpeedFailed);
            }

            //初始化图表
            echartsInit(name, qualified, failed, experienceQualified, experienceFailed, pingQualified, pingFailed);
        }
    });

    //检查场景添加到下拉框中
    var resultData
        , htmls = '<option value="">请选择场景</option>'; //全局变量
    $.ajax({
        url: '../vesapp/scene/listAllScene.do',
        dataType: 'json',
        type: 'post',
        contentType: "application/json",
        success: function (data) {
            resultData = data.data;
            $.each(resultData, function (index, item) {
                htmls += '<option value = "' + item.id + '">' + item.name + '</option>'
            });
            $("#sceneSelect").html(htmls);

            layui.form.render("select");
        }
    });
    //选择场景联动出现场景内的区域
    form.on('select', function (data) {
        //判断选择场景后再执行添加区域到下拉框
        if (data.elem.id === "sceneSelect" && data.value !== null && typeof data.value !== "undefined" && data.value !== "") {
            $.ajax({
                url: '../vesapp/scene/findAreaBySceneId.do?sceneId=' + data.value,
                dataType: 'json',
                type: 'post',
                contentType: "application/json",
                success: function (data) {
                    resultData = null;
                    htmls = '<option value="">请选择区域</option>';
                    resultData = data.data;
                    $.each(resultData, function (index, item) {
                        htmls += '<option value = "' + item.id + '">' + item.name + '</option>'
                    });
                    $("#areaSelect").html(htmls);

                    layui.form.render("select");
                }
            });
        } else if (data.elem.id === "sceneSelect" && data.value === "") {
            $("#areaSelect").html("");
            layui.form.render("select");
        }
    });

    var active = {
        reload: function () {

            //执行重载
            table.reload('tableReload', {
                page: false
                , url: 'http://81.70.41.3:8080//vesapp/static/user.do'
                , where: {
                    limit: $('#TOP input[name="TOP"]:checked ').val(),
                    dateTye: $('#lastTime input[name="lastTime"]:checked ').val()
                }
                , done: function (res, curr, count) {
                    user = [];
                    flow = [];
                    longTime = [];
                    $.each(res.data, function (index, data) {

                        user.push(data.name);
                        flow.push(data.outFlow.substring(0, data.outFlow.length - 2));
                        longTime.push(data.longTime.substring(0, data.longTime.length - 3));
                    });

                    if ($('#type input[name="type"]:checked ').val() != 'flow') {
                        echartsInit(user, longTime, $('#type input[name="type"]:checked ').attr('title'));
                    } else {
                        echartsInit(user, flow, $('#type input[name="type"]:checked ').attr('title'));
                    }
                }
            }, 'data');
        }
    };

    table.on('tool(tableEvent)', function (obj) {
        tableData = obj.data;
        //添加自定义属性用于导出时判断
        $('#exportData').attr('data-type', obj.event);
        //判断表格点击事件
        switchClick(obj.event, tableData);
    });

    //根据条件查询
    var active = {
        reload: function () {

            var startTime = new Date($('#startTime').val())
                , endTime = new Date($('#endTime').val())
                , sceneId = $('#sceneSelect').val()
                , areaId = $('#areaSelect').val();

            if (startTime.getTime() > endTime.getTime()) {
                layer.alert('开始时间不能大于结束时间,请重新选择!');
                $("#startTime")[0].value = '';
                $('#endTime')[0].value = '';
                return false;
            }

            var freshData = {
                "sceneId": sceneId,
                "areaId": areaId
            };

            if (!isNaN(startTime.getTime()) || !isNaN(endTime.getTime())) {
                freshData.startTime = startTime;
                freshData.endTime = endTime;
            }


            //执行重载
            table.reload('tableReload', {
                page: false
                , method: 'POST'
                , where: freshData
            }, 'data');
        }
    };

    $('#submit').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    $('#export').on('click', function () {

        var startTime = new Date($('#startTime').val())
            , endTime = new Date($('#endTime').val())
            , sceneId = $('#sceneSelect').val()
            , areaId = $('#areaSelect').val();

        if (startTime.getTime() > endTime.getTime()) {
            layer.alert('开始时间不能大于结束时间,请重新选择!');
            $("#startTime")[0].value = '';
            $('#endTime')[0].value = '';
            return false;
        }

        var exportData = {
            "sceneId": sceneId,
            "areaId": areaId,
            "export": "export"
        };

        if (!isNaN(startTime.getTime()) || !isNaN(endTime.getTime())) {
            exportData.startTime = startTime;
            exportData.endTime = endTime;
        }

        exportExcel('../vesapp/passValue/passValue.do', exportData);

    });

    $('#exportData').on('click', function () {

        switchClick($('#exportData').attr('data-type'), tableData, "export");
    });

    $('.layui-layer-close').on('click', function () {
        $('#layui-layer1').hide();
        $('#layui-layer-shade1').hide();
    });

    //初始化图表
    function echartsInit(name, qualified, failed, experienceQualified, experienceFailed, pingQualified, pingFailed) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main1'));
        $("#main1").removeAttr("style").removeAttr("_echarts_instance_");

        // 指定图表的配置项和数据
        var option = {
            grid: {
                top: '5%',
                right: '1%',
                left: '1%',
                bottom: '20%',
                containLabel: true
            },
            legend: {
                data: ['网络测试/合格', '网络测试/不合格'],
                right: 20,
                formatter: function (name) {
                    return name.replace("网络测试/", "");
                }
            },
            tooltip: {
                trigger: 'axis'
            },
            xAxis: {
                type: 'category',
                data: name,
                axisTick: {
                    show: false
                }
            },
            yAxis: {
                type: 'value',
                splitLine: false
            },
            dataZoom: [{
                type: 'inside',
                start: 0,
                end: 100
            }, {
                start: 0,
                end: 100,
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }],
            series: [{
                name: "网络测试/合格",
                data: qualified,
                itemStyle: {
                    color: '#5768ef',
                    barBorderRadius: 8
                },
                type: 'bar'
            },
                {
                    name: "网络测试/不合格",
                    data: failed,
                    itemStyle: {
                        color: '#69cbf2',
                        barBorderRadius: 8
                    },
                    type: 'bar'
                },
                {
                    name: "体验测速/合格",
                    data: experienceQualified,
                    itemStyle: {
                        color: '#5768ef',
                        barBorderRadius: 8
                    },
                    type: 'bar'
                },
                {
                    name: "体验测速/不合格",
                    data: experienceFailed,
                    itemStyle: {
                        color: '#69cbf2',
                        barBorderRadius: 8
                    },
                    type: 'bar'
                },
                {
                    name: "ping测速/合格",
                    data: pingQualified,
                    itemStyle: {
                        color: '#5768ef',
                        barBorderRadius: 8
                    },
                    type: 'bar'
                },
                {
                    name: "ping测速/不合格",
                    data: pingFailed,
                    itemStyle: {
                        color: '#69cbf2',
                        barBorderRadius: 8
                    },
                    type: 'bar'
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }

    //初始化表格
    function tableRender(url, data, cols) {
        table.render({
            elem: '#dataTable'
            ,
            url: url //数据接口
            ,
            page: false //开启分页
            ,
            id: 'dataTable'
            ,
            method: 'post'
            , where: data
            ,
            response: {
                statusName: 'code' //规定数据状态的字段名称，默认：code
                , statusCode: 200 //规定成功的状态码，默认：0
                , msgName: 'msg' //规定状态信息的字段名称，默认：msg
                , countName: 'total' //规定数据总数的字段名称，默认：count
                , dataName: 'data' //规定数据列表的字段名称，默认：data
            }
            ,
            cols: cols
        });
    }

    //判断下拉框是否选中填充条件
    function selectData(data) {
        //判断查询的是场景下/区域下/ap下的信息
        let sceneSelect = $('select[id="sceneSelect"]').val(),
            areaSelect = $('select[id="areaSelect"]').val(),
            startTime = new Date($('#startTime').val()),
            endTime = new Date($('#endTime').val()),
            dataCondition = {};

        if(!isNaN(startTime.getTime()) && !isNaN(endTime.getTime())){
            dataCondition.startTime=startTime;
            dataCondition.endTime=endTime;
        }

        if (areaSelect !== "" && areaSelect !== null) {

            dataCondition.apName=data.name;
        } else if (sceneSelect !== "" && sceneSelect !== null) {

            dataCondition.areaName=data.name;
        } else {
           
            dataCondition.sceneName=data.name;
        }
        return dataCondition;
    }

    //判断点击表格选择的事件
    function switchClick(name, data, exportMark) {
        switch (name) {
            case 'apCount':
                let apData = selectData(data);
                $('#layui-layer-shade1').show();
                $('#layui-layer1').show();

                $('#exportData').hide();

                tableRender('../vesapp/scene/selectBySceneNameOrAreaNameOrApName.do', apData, cols);
                break;
            case 'testApCount':
                let testAp = selectData(data);
                $('#layui-layer-shade1').show();
                $('#layui-layer1').show();

                $('#exportData').hide();

                if (typeof testAp === 'undefined' || testAp === null) {
                    testAp.sceneName = data.name;
                }

                tableRender('../vesapp/passValue/selectTestAp.do', testAp, cols);

                break;
            case 'networkQualified':
                //点击网络测速合格事件
                let networkQualifiedData = selectData(data);
                networkQualifiedData.name = "LT";
                //判断是否导出
                if (typeof exportMark !== "undefined" && exportMark !== null) {
                    networkQualifiedData.export = exportMark;

                    exportExcel('../vesapp/networkSpeed/selectPassValue.do', networkQualifiedData);
                    return;
                }

                $('#layui-layer-shade1').show();
                $('#layui-layer1').show();

                tableRender('../vesapp/networkSpeed/selectPassValue.do', networkQualifiedData, networkCols);
                break;

            case 'networkFailed':
                //点击网络测速不合格事件
                let networkFailedData = selectData(data);
                networkFailedData.name = "GTET";
                //判断是否导出
                if (typeof exportMark !== "undefined" && exportMark !== null) {
                    networkFailedData.export = exportMark;

                    exportExcel('../vesapp/networkSpeed/selectPassValue.do', networkFailedData);
                    return;
                }

                $('#layui-layer-shade1').show();
                $('#layui-layer1').show();

                tableRender('../vesapp/networkSpeed/selectPassValue.do', networkFailedData, networkCols);
                break;
            case 'networkTotal':
                //点击网络测速合计事件
                let networkTotalData = selectData(data);

                //判断是否导出
                if (typeof exportMark !== "undefined" && exportMark !== null) {
                    networkTotalData.export = exportMark;

                    exportExcel('../vesapp/networkSpeed/selectPassValue.do', networkTotalData);
                    return;
                }

                $('#layui-layer-shade1').show();
                $('#layui-layer1').show();

                tableRender('../vesapp/networkSpeed/selectPassValue.do', networkTotalData, networkCols);
                break;
            case 'experienceQualified':
                //点击体验测速合格事件
                let experienceQualifiedData = selectData(data);
                experienceQualifiedData.name = "GTET";

                //判断是否导出
                if (typeof exportMark !== "undefined" && exportMark !== null) {
                    experienceQualifiedData.export = exportMark;

                    exportExcel('../vesapp/experienceSpeed/selectPassValue.do', experienceQualifiedData);
                    return;
                }

                $('#layui-layer-shade1').show();
                $('#layui-layer1').show();

                tableRender('../vesapp/experienceSpeed/selectPassValue.do', experienceQualifiedData, experienceCols);
                break;
            case 'experienceFailed':
                //点击体验测速不合格事件
                let experienceFailedData = selectData(data);
                experienceFailedData.name = "LT";

                //判断是否导出
                if (typeof exportMark !== "undefined" && exportMark !== null) {
                    experienceFailedData.export = exportMark;

                    exportExcel('../vesapp/experienceSpeed/selectPassValue.do', experienceFailedData);
                    return;
                }

                $('#layui-layer-shade1').show();
                $('#layui-layer1').show();

                tableRender('../vesapp/experienceSpeed/selectPassValue.do', experienceFailedData, experienceCols);
                break;
            case 'experienceTotal':
                //点击体验测速合计事件
                let experienceTotalData = selectData(data);

                //判断是否导出
                if (typeof exportMark !== "undefined" && exportMark !== null) {
                    experienceTotalData.export = exportMark;

                    exportExcel('../vesapp/experienceSpeed/selectPassValue.do', experienceTotalData);
                    return;
                }

                $('#layui-layer-shade1').show();
                $('#layui-layer1').show();

                tableRender('../vesapp/experienceSpeed/selectPassValue.do', experienceTotalData, experienceCols);
                break;
            case 'pingQualified':
                //点击ping测速合格事件
                let pingQualifiedData = selectData(data);
                pingQualifiedData.name = "LT";

                //判断是否导出
                if (typeof exportMark !== "undefined" && exportMark !== null) {
                    pingQualifiedData.export = exportMark;

                    exportExcel('../vesapp/pingSpeed/selectPassValue.do', pingQualifiedData);
                    return;
                }

                $('#layui-layer-shade1').show();
                $('#layui-layer1').show();

                tableRender('../vesapp/pingSpeed/selectPassValue.do', pingQualifiedData, pingCols);
                break;
            case'pingFailed':
                //点击ping测速不合格事件
                let pingFailedData = selectData(data);
                pingFailedData.name = "GTET";

                //判断是否导出
                if (typeof exportMark !== "undefined" && exportMark !== null) {
                    pingFailedData.export = exportMark;

                    exportExcel('../vesapp/pingSpeed/selectPassValue.do', pingFailedData);
                    return;
                }

                $('#layui-layer-shade1').show();
                $('#layui-layer1').show();

                tableRender('../vesapp/pingSpeed/selectPassValue.do', pingFailedData, pingCols);
                break;
            case 'pingTotal':
                //点击ping测速合计事件
                let pingTotalData = selectData(data);

                //判断是否导出
                if (typeof exportMark !== "undefined" && exportMark !== null) {
                    pingTotalData.export = exportMark;

                    exportExcel('../vesapp/pingSpeed/selectPassValue.do', pingTotalData);
                    return;
                }

                $('#layui-layer-shade1').show();
                $('#layui-layer1').show();

                tableRender('../vesapp/pingSpeed/selectPassValue.do', pingTotalData, pingCols);
                break;
        }
    }

    //导出excel
    function exportExcel(url, exportData) {
        $.ajax({
            url: url,
            type: 'post',
            data: exportData,
            success: function (data) {
                // layer.close(layer.index);
                // layer.alert("导出成功!");
                window.open("../excel/" + data.data.data.name);
            },
            error: function () {
                alert("导出失败!");
            }
        })
    }

    //网络测速/体验测速/ping测速表头
    var cols = [
            [ //表头
                {field: 'name', title: '名称', align: "center"}
                , {field: 'mac', title: 'mac地址', align: "center"}
                , {field: 'ipaddress', title: 'ip地址', align: "center"}
            ]
        ], networkCols = [[ //表头
            {field: 'networkDelay', title: '网络延迟(ms)', sort: true}
            , {field: 'downloadSpeed', title: '下载速度', sort: true}
            , {field: 'uploadSpeed', title: '上传速度', sort: true}
            , {
                field: 'time',
                title: '测速时间',
                sort: true,
                templet: '<div title="{{d.time}}">{{ layui.util.toDateString(d.time, "yyyy-MM-dd HH:mm:ss") }}</div>'
            }
            , {
                field: 'address',
                title: '地址',
                templet: '<div><span title="{{d.address}}">{{d.address}}</span></div>'
            }
            , {field: 'macAddress', title: 'mac地址'}
            , {field: 'macAddress5G', title: 'mac5G地址'}
            , {field: 'sceneId', title: '场景id', hide: true}
            , {field: 'areaId', title: '区域id', hide: true}
            , {field: 'apName', title: 'ap名称', templet: '<div><span title="{{d.apName}}">{{d.apName}}</span></div>'}
            , {
                field: 'sceneName',
                title: '场景',
                templet: '<div><span title="{{d.sceneName}}">{{d.sceneName}}</span></div>'
            }
            , {
                field: 'areaName',
                title: '区域',
                templet: '<div><span title="{{d.areaName}}">{{d.areaName}}</span></div>'
            }
            , {field: 'speedometer', title: '测速人'}
        ]],
        pingCols = [[ //表头
            {field: 'address', title: '地址', templet: '<div><span title="{{d.address}}">{{d.address}}</span></div>'}
            , {
                field: 'macAddress',
                title: 'mac地址',
                templet: '<div><span title="{{d.macAddress}}">{{d.macAddress}}</span></div>'
            }
            , {
                field: 'macAddress5G',
                title: 'mac5G地址',
                templet: '<div><span title="{{d.macAddress5G}}">{{d.macAddress5G}}</span></div>'
            }
            , {
                field: 'time',
                title: '测速时间',
                sort: true,
                templet: '<div title="{{d.time}}">{{ layui.util.toDateString(d.time, "yyyy-MM-dd HH:mm:ss") }}</div>',
            }
            , {field: 'size', title: '包大小(B)', sort: true}
            , {field: 'sendQuantity', title: '发送包数(个)', sort: true}
            , {field: 'receiveQuantity', title: '接受包数(个)', sort: true}
            , {field: 'packetLossRate', title: '丢包率', sort: true}
            , {field: 'avgRTT', title: '平均RTT(ms)', sort: true}
            , {field: 'minRTT', title: '最小RTT(ms)', sort: true}
            , {field: 'maxRTT', title: '最大RTT(ms)', sort: true}
            , {field: 'meanDifference', title: '均差RTT(ms)', sort: true}
            , {field: 'sceneId', title: '场景id', hide: true}
            , {field: 'areaId', title: '区域id', hide: true}
            , {
                field: 'apName',
                title: 'ap名称',
                templet: '<div><span title="{{d.sceneName}}">{{d.apName}}</span></div>'
            }
            , {
                field: 'sceneName',
                title: '场景',
                templet: '<div><span title="{{d.sceneName}}">{{d.sceneName}}</span></div>'
            }
            , {
                field: 'areaName',
                title: '区域',
                templet: '<div><span title="{{d.areaName}}">{{d.areaName}}</span></div>'
            }
            , {field: 'speedometer', title: '测速人'}
        ]],
        experienceCols = [[ //表头
            {field: 'platform', title: '平台'}
            , {field: 'speed', title: '速度', sort: true}
            , {
                field: 'time',
                title: '测速时间',
                sort: true,
                templet: '<div title="{{d.time}}">{{ layui.util.toDateString(d.time, "yyyy-MM-dd HH:mm:ss") }}</div>',
            }
            , {
                field: 'address',
                title: '地址',
                templet: '<div><span title="{{d.address}}">{{d.address}}</span></div>'
            }
            , {
                field: 'macAddress',
                title: 'mac地址',
                templet: '<div><span title="{{d.macAddress}}">{{d.macAddress}}</span></div>'
            }
            , {
                field: 'macAddress5G',
                title: 'mac5G地址',
                templet: '<div><span title="{{d.macAddress5G}}">{{d.macAddress5G}}</span></div>'
            }
            , {field: 'sceneId', title: '场景id', hide: true}
            , {field: 'areaId', title: '区域id', hide: true}
            , {field: 'apName', title: 'ap名称', templet: '<div><span title="{{d.apName}}">{{d.apName}}</span></div>'}
            , {
                field: 'sceneName',
                title: '场景',
                templet: '<div><span title="{{d.sceneName}}">{{d.sceneName}}</span></div>'
            }
            , {
                field: 'areaName',
                title: '区域',
                templet: '<div><span title="{{d.areaName}}">{{d.areaName}}</span></div>'
            }
            , {
                field: 'speedometer',
                title: '测速人',
                templet: '<div><span title="{{d.speedometer}}">{{d.speedometer}}</span></div>'
            }
        ]];
});
