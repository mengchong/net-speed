/**
 * Created by songby on 2019/10/11 0011.
 */
/**
 * 引入模块
 */
layui.use(['laydate', 'form', 'table', 'util', 'element'], function () {
    var laydate = layui.laydate
        , form = layui.form
        , table = layui.table
        , $ = layui.jquery
        , exportData;

    //执行一个laydate实例
    laydate.render({
        elem: '#startTime' //指定元素
    });

    //执行一个laydate实例
    laydate.render({
        elem: '#endTime' //指定元素
    });

    var tableData = table.render({
        elem: '#contentTable'
        , url: '../vesapp/networkSpeed/list.do' //数据接口
        , page: true //开启分页
        , id: 'tableReload'
        , title: '网络测速报表'
        , response: {
            statusName: 'code' //规定数据状态的字段名称，默认：code
            , statusCode: 200 //规定成功的状态码，默认：0
            , msgName: 'msg' //规定状态信息的字段名称，默认：msg
            , countName: 'total' //规定数据总数的字段名称，默认：count
            , dataName: 'rows' //规定数据列表的字段名称，默认：data
        }
        , request: {
            pageName: 'pageNumber',//页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        }
        , cols: [[ //表头
            {field: 'networkDelay', title: '网络延迟(ms)', sort: true}
            , {field: 'downloadSpeed', title: '下载速度', sort: true}
            , {field: 'uploadSpeed', title: '上传速度', sort: true}
            , {
                field: 'time',
                title: '测速时间',
                sort: true,
                templet: '<div>{{ layui.util.toDateString(d.time, "yyyy-MM-dd HH:mm:ss") }}</div>',
            }
            , {field: 'address', title: '地址'}
            , {field: 'macAddress', title: 'mac地址'}
            , {field: 'macAddress5G', title: 'mac5G地址'}
            , {field: 'sceneId', title: '场景id', hide: true}
            , {field: 'areaId', title: '区域id', hide: true}
            , {field: 'apName', title: 'ap名称'}
            , {field: 'sceneName', title: '场景'}
            , {field: 'areaName', title: '区域'}
            , {field: 'speedometer', title: '测速人'}
        ]]
        , done: function (res, curr, count) {
            exportData = res.data;
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

//根据条件查询
    var active = {
        reload: function () {

            var demoReload = $('#speedometer')
                , startTime = new Date($('#startTime').val())
                , endTime = new Date($('#endTime').val())
                , sceneId = $('#sceneSelect').val()
                , areaId = $('#areaSelect').val();

            if (startTime.getTime() > endTime.getTime()) {
                layer.alert('开始时间不能大于结束时间,请重新选择!');
                $("#startTime")[0].value = '';
                $('#endTime')[0].value = '';
                return false;
            }

            //如果只查询用户,就手动设置时间为1970-系统时间
            if (isNaN(startTime.getTime()) && isNaN(endTime.getTime())) {
                startTime = new Date(14400);
                endTime = new Date();
            }

            if (isNaN(startTime.getTime()) || isNaN(endTime.getTime())) {
                layer.alert('请保证开始日与截止日都已经选择!');
                $("#startTime")[0].value = '';
                $('#endTime')[0].value = '';
                return false;
            }

            //执行重载
            table.reload('tableReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    startTime: Date.parse(startTime),
                    endTime: Date.parse(endTime),
                    speedometer: demoReload.val(),
                    sceneId: sceneId,
                    areaId: areaId
                }
            }, 'data');
        }
    };

    $('#submit').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
    //导出excel
    $("#export").click(function () {
        // table.exportFile(tableData.config.id, exportData, 'xls');
        var speedometer = $('#speedometer').val()
            , startTime = new Date($('#startTime').val())
            , endTime = new Date($('#endTime').val())
            , sceneId = $('#sceneSelect').val()
            , areaId = $('#areaSelect').val();
        if (startTime.getTime() > endTime.getTime()) {
            layer.alert('开始时间不能大于结束时间,请重新选择!');
            $("#startTime")[0].value = '';
            $('#endTime')[0].value = '';
            return false;
        }

        //如果只查询用户,就手动设置时间为1970-系统时间
        if (isNaN(startTime.getTime()) && isNaN(endTime.getTime())) {
            startTime = new Date(14400);
            endTime = new Date();
        }

        if (isNaN(startTime.getTime()) || isNaN(endTime.getTime())) {
            layer.alert('请保证开始日与截止日都已经选择!');
            $("#startTime")[0].value = '';
            $('#endTime')[0].value = '';
            return false;
        }

        $.ajax({
            url: '../vesapp/networkSpeed/export.do?speedometer=' + speedometer + '&startTime=' + new Date(startTime).getTime() + '&endTime=' + new Date(endTime).getTime() + '&sceneId=' + sceneId + '&areaId=' + areaId,
            type: 'get',
            beforeSend : function(){
                loading("下载中...");
            },
            success: function (data) {
                if(data.data.data.status==="0"){
                    layer.closeAll();
                    window.open("../excel/"+data.data.data.name);
                }else{
                    layer.closeAll();
                    layer.alert("导出失败!");
                }
            }
        });
    });
    //回车键触发条件查询请求
    $('#speedometer').on('keydown', function (event) {
        if (event.keyCode == 13) {
            $("#submit").trigger("click");
            return false;

        }
    });

    function loading(msg) {
        layer.msg(msg, {
            icon: 16,
            shade: [0.6, '#000005'],//遮罩的颜色与透明度
            time: false  //取消自动关闭
        })
    };

    /*     //监听行工具事件
     table.on('submit(search)', function(obj){
     var data = obj.data;
     console.log(data);
     //console.log(obj)
     /!*if(obj.event === 'del'){
     layer.confirm('确认删除?', function(index){
     obj.del();
     layer.close(index);
     });
     } else if(obj.event === 'edit'){
     layer.prompt({
     formType: 2
     ,value: data.email
     }, function(value, index){
     obj.update({
     email: value
     });
     layer.close(index);
     });
     }*!/
     });*/
    /* // 监听全选
     form.on('submit(search)', function(data){
     table.reload('contentTable', {
     where: {
     id: data.field.username
     }
     });
     if(data.elem.checked){
     $('tbody input').prop('checked',true);
     }else{
     $('tbody input').prop('checked',false);
     }
     form.render('checkbox');
     });*/

});
