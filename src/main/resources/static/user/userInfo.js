/**
 * Created by songby on 2019/11/13 0013.
 */
layui.use(['laydate', 'form', 'layer', 'table'], function () {
    var laydate = layui.laydate
        , form = layui.form
        , layer = layui.layer
        , $ = layui.jquery
        , table = layui.table;

    table.render({
        elem: '#contentTable'
        , url: '../app/user/list.do' //数据接口
        , page: true //开启分页
        , id: 'tableReload'
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
            {type: 'checkbox'}
            , {field: 'id', title: 'id', hide: true}
            , {field: 'account', title: '登录名'}
            , {field: 'name', title: '名称'}
            , {field: 'mobile', title: '手机'}
            , {
                field: 'createdTime',
                title: '注册时间',
                sort: true,
                templet: '<div>{{layui.util.toDateString(d.createdTime, "yyyy-MM-dd HH:mm:ss") }}</div>',
            }
            , {fixed: 'right', title: '操作', width: 150, align: 'center', toolbar: '#toolBar'}
        ]]
    });

    //监听行工具事件
    table.on('tool(tableClick)', function (obj) {
        var data = obj.data;
        if (obj.event === 'del') {
            layer.confirm('真的删除么', function (index) {
                var str = [];
                str.push(data.id);
                $.ajax({
                    url: '../app/user/delByIds.do?ids=' + str,
                    dataType: 'json',
                    type: 'get',
                    success: function (data) {
                        if (data.code === 200) {
                            layer.msg('已删除!', {icon: 1, time: 1000});
                        } else {
                            layer.alert("删除失败!");
                        }

                    }
                });
                obj.del();
                layer.close(index);
            });
        } else if (obj.event === 'edit') {
            xadmin.open('修改用户信息', '../user/userEdit.html?id=' + data.id, 600, 400);
        }
    });

    //根据条件查询
    var active = {
        reload: function () {

            var account = $('#account').val();
            //执行重载
            table.reload('tableReload', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    account: account
                }
            }, 'data');
        }
    };

    $('#submit').on('click', function () {
        var type = $(this).data('type');
        console.log(type);
        active[type] ? active[type].call(this) : '';
    });

    //回车键触发条件查询请求
    $('#account').on('keydown', function (event) {
        if (event.keyCode == 13) {
            $("#submit").trigger("click");
            return false;

        }
    });

    //批量删除
    $('#delAll').on('click', function () {

        var checkStatus = table.checkStatus('tableReload')
            , arr = [];

        if (checkStatus.data.length === 0) {
            layer.alert("请至少选择一个!");
            return false;
        }

        for (var i = 0; i < checkStatus.data.length; i++) {
            arr.push(checkStatus.data[i].id);
        }

        layer.confirm('确认要删除吗？', function (index) {
            //捉到所有被选中的，发异步进行删除
            $.ajax({
                url: '../app/user/delByIds.do?ids=' + arr,
                dataType: 'json',
                type: 'get',
                success: function (data) {
                    if (data.code === 200) {
                        layer.msg('删除成功', {icon: 1});
                        $(".layui-form-checked").not('.header').parents('tr').remove();
                    } else {
                        layer.alert("删除失败!");
                    }

                }
            });
        });
    });

});
