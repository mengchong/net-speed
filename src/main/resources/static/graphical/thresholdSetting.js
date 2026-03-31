/**
 * Created by songby on 2019/10/11 0011.
 */
/**
 * 引入模块
 */
layui.use(['form', 'jquery'], function () {
    var form = layui.form;
    //保存数据
    form.on('submit(save)', function (data) {

        let formData = {
            "networkPassValue": data.field.networkPassValue,
            "lostPacketRatePassValue": data.field.lostPacketRatePassValue,
            "delayPassValue": data.field.delayPassValue,
            "networkDelayPassValue": data.field.networkDelayPassValue
        };

        sendData("../vesapp/passValue/save.do", formData)

    });

    //查询数据
    $.ajax({
        url: "../vesapp/passValue/listAll.do",
        type: "get",
        success: function (result) {

            let res=result.data;
            //回显数据
            $("#networkPassValue").val(res.networkPassValue);
            $("#lostPacketRatePassValue").val(res.lostPacketRatePassValue);
            $("#delayPassValue").val(res.delayPassValue);
            $("#networkDelayPassValue").val(res.networkDelayPassValue);

            form.render();

            return false;
        }
    });

    function sendData(url, data) {

        $.ajax({
            url: url,
            data: data,
            type: "post",
            success: function (result) {
                layer.alert("保存成功!");
            }
        });

    }

});
