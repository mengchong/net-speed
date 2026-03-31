/**
 * Created by songby on 2019/10/11 0011.
 */
/**
 * 引入模块
 */
layui.use(['laydate', 'form', 'table'], function () {
    var laydate = layui.laydate
        , form = layui.form
        , table = layui.table;

    table.render({
        elem: '#contentTable'
        , url: '../vesapp/scene/listAp.do' //数据接口
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
            {field: 'id', title: 'id'}
            , {field: 'name', title: '名字'}
            , {field: 'mac', title: 'mac地址'}
            , {field: 'ipaddress', title: 'ip地址'}
            , {field: 'areaName', title: '区域'}
            , {field: 'areaId', title: '区域Id', hide: true}
        ]]
    });
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
