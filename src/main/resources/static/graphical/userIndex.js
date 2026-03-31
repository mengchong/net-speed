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
        , user = [];

    table.render({
        elem: '#contentTable'
        ,
        url: 'http://121.69.196.95:1284/vesapp/static/user.do?limit=' + $('#TOP input[name="TOP"]:checked ').val() + '&dateTye=' + $('#lastTime input[name="lastTime"]:checked ').val() //数据接口
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
        cols: [[ //表头
            {field: 'name', title: '手机号'}
            , {field: 'osType', title: '系统类型'}
            , {field: 'source', title: '认证来源'}
            , {field: 'longTime', title: '上网时长'}
            , {field: 'outFlow', title: '上网流量'}
        ]]
        ,
        done: function (res, curr, count) {

            $.each(res.data, function (index, data) {
                user.push(data.name);
                flow.push(data.outFlow.substring(0, data.outFlow.length - 2));
                longTime.push(data.longTime.substring(0, data.longTime.length - 3));
            });

            echartsInit(user, flow, $('#type input[name="type"]:checked ').attr('title'));
        }
    });

    var active = {
        reload: function () {

            //执行重载
            table.reload('tableReload', {
                page: false
                , url: 'http://121.69.196.95:1284/vesapp/static/user.do'
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

    $('#type').on('click', function () {

        if ($('#type input[name="type"]:checked ').val() == 'time') {

            echartsInit(user, longTime, $('#type input[name="type"]:checked ').attr('title'));
        } else {

            echartsInit(user, flow, $('#type input[name="type"]:checked ').attr('title'));
        }
    });

    $('#TOP').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    $('#lastTime').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    function echartsInit(userData, seriesData, seriesName) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main1'));
        $("#main1").removeAttr("style").removeAttr("_echarts_instance_");

        // 指定图表的配置项和数据
        var option = {
            grid: {
                top: '5%',
                right: '1%',
                left: '1%',
                bottom: '10%',
                containLabel: true
            },
            tooltip: {
                trigger: 'axis'
            },
            xAxis: {
                type: 'category',
                data: userData,
                axisTick: {
                    show: false
                },
                axisLabel: {
                    // interval: 0
                }
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                name: seriesName,
                data: seriesData,
                type: 'line',
                smooth: true
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }

    //根据条件查询
    /* var $ = layui.$, active = {
     reload: function () {
     var demoReload = $('#username');
     //执行重载
     table.reload('tableReload', {
     page: {
     curr: 1 //重新从第 1 页开始
     }
     , where: {
     username: demoReload.val()
     }
     }, 'data');
     }
     };

     $('#submit').on('click', function () {
     var type = $(this).data('type');
     active[type] ? active[type].call(this) : '';
     });

     //回车键触发条件查询请求
     $('#username').on('keydown', function (event) {
     if (event.keyCode == 13) {
     $("#submit").trigger("click");
     return false;

     }
     });*/
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
