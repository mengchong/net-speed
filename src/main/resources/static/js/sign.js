
$(document).ready(function () {
    var countdown=60;
    var radm = null;
    var flag = false;
    function SetCookie(name, value, expires) {
        var argv = SetCookie.arguments;
        //本例中length = 3
        var argc = SetCookie.arguments.length;
        var expires = (argc > 2) ? argv[2] : null;
        var path = (argc > 3) ? argv[3] : null;
        var domain = (argc > 4) ? argv[4] : null;
        var secure = (argc > 5) ? argv[5] : false;
        document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : "");
    }

    $.ajax({
        type: "GET",
        url: "user/selectEmailConfig.do",
        success: function (responseData) {
            if (responseData.success) {
                if(responseData.data.length > 0){
                    $('#emailConfig').hide();
                    flag = false;
                }else{
                    flag = true;
                    $('#emailConfig').show();
                    $('#emailConfig').unbind('click');
                    $('#emailConfig').on('click',function(){
                        $("#emailConfigModal").modal("show");
                        bindBtnEvent();
                    })
                }
            }
        }
    });
    function bindBtnEvent(){
        $('.cancelModalbtn').on('click',function(){
            $("#emailConfigModal").modal('hide');
        })

        $('.addModalbtn').on('click',function(){
            $('.addConfigform').submit();
            $('#emailConfig').hide();
            flag = false;

        })
    }



    $('#signInForm').bootstrapValidator({
        feedbackIcons: {
         /*   valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'*/
        },
        fields: {
      /*      'userName': {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 3,
                        max: 20,
                        message: '长度必须在3-20之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '只能使用大小写字母、数字、下划线'
                    }
                }
            },
            userPass: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 16,
                        message: '长度必须在6-16之间'
                    }
                }
            }
*/
        }
    }).on("success.form.bv", function (e) {
        e.preventDefault();
        if(flag){
            alert("请先配置邮箱参数!");
            return;
        }
        var $form = $(e.target);
        var validator = $form.data('bootstrapValidator');
        var user = {
            userName: validator.getFieldElements('userName').val(),
            userPass: validator.getFieldElements('userPass').val()
        };
        var email =  $("#sendEmailCodeInput").val().trim().toLowerCase();
        // if(radm == null){
        //     $('#emailText').show();
        //     $('#emailText').html("请先发送验证码!");
        //     $('#loginSubmit').removeAttr("disabled");
        //      return;
        // }
        // if(email != radm.trim().toLowerCase()){
        //     $('#emailText').show();
        //    $('#emailText').html("请输入正确验证码!");
        //     $('#loginSubmit').removeAttr("disabled");
        //
        // }else{
            $('#emailText').hide();
            $('#emailText').html("");
            countdown = 0;
            $.ajax({
                type: "POST",
                url: "user/signIn.do",
                data: user,
                success: function (responseData) {
                    if (!responseData.success) {
                        alert(responseData.errorMsg);
                    } else {
                        SetCookie("userName",responseData.data.userName,null);
                        location.href = "index.html"
                    }
                }
            });
        // }
    });

    $('#signUpForm').bootstrapValidator({
        feedbackIcons: {
   /*         valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'*/
        },
        fields: {
          /*  'userName': {
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {
                        min: 3,
                        max: 20,
                        message: '长度必须在3-20之间'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_]+$/,
                        message: '只能使用大小写字母、数字、下划线'
                    }
                }
            },
            userEmail: {
                validators: {
                    notEmpty: {
                        message: '邮箱不能为空'
                    },
                    emailAddress: {
                        message: '格式不正确'
                    },
                    stringLength: {
                        min: 1,
                        max: 20,
                        message: '长度必须在1-20之间'
                    }
                }
            },
            userPass: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 6,
                        max: 16,
                        message: '长度必须在6-16之间'
                    }
                }
            },
            userConfirmPass: {
                validators: {
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    identical: {
                        field: 'userPass',
                        message: '两次密码不一致'
                    },
                    stringLength: {
                        min: 6,
                        max: 16,
                        message: '长度必须在6-16之间'
                    }
                }
            }*/

        }
    }).on("success.form.bv", function (e) {
        e.preventDefault();
        if(flag){
            alert("请先配置邮箱参数!");
            return;
        }
        var $form = $(e.target);
        var validator = $form.data('bootstrapValidator');
        var user = {
            userName: validator.getFieldElements('userName').val(),
            userPass: validator.getFieldElements('userPass').val(),
            userEmail: validator.getFieldElements('userEmail').val()
        };
        $.ajax({
            type: "POST",
            url: "user/signUp",
            data: user,
            success: function (responseData) {
                if (!responseData.success) {
                    alert(responseData.errorMsg);
                } else {
                    location.href = "sign-in.html"
                }
            }
        });
    });

    $('#sendEmailCode').unbind('click');
    $('#sendEmailCode').on('click',function(){
        var _sef = this;
        if(flag){
            alert("请先配置邮箱参数!");
            return;
        }
        $('#sendEmailCode').attr("disabled", true);
        var $name = $('#userName').val();
        if($name == null){
            return;
        }
        $.ajax({
            type: "GET",
            url: "user/senEmail.do?name="+$name,
            success: function (responseData) {
                if(responseData.success){
                    settime();
                }
                radm = responseData.errorMsg;
            }
        });

    });

    function settime() {
        if (countdown == 0) {
            var $val = $('#sendEmailCode');
            $val.removeAttr("disabled");
            $val.html("重新发送");
            countdown = 60;
            radm = "001";
            $('#sendEmailText').hide();
            return;
        } else {
            var $vals =  $('#sendEmailCode');
            $('#sendEmailText').show();
            $vals.attr("disabled", true);
            $vals.html("重新发送(" + countdown + ")");
            countdown--;
        }
        setTimeout(function() {
            settime()
        },1000)
    }

});
