define(function (require, exports, module) {
    function getCookie(cookie_name)
    {
        var allcookies = document.cookie;
        var cookie_pos = allcookies.indexOf(cookie_name);   //索引的长度

        // 如果找到了索引，就代表cookie存在，
        // 反之，就说明不存在。
        if (cookie_pos != -1)
        {
            // 把cookie_pos放在值的开始，只要给值加1即可。
            cookie_pos += cookie_name.length + 1;      //这里容易出问题，所以请大家参考的时候自己好好研究一下
            var cookie_end = allcookies.indexOf(";", cookie_pos);

            if (cookie_end == -1)
            {
                cookie_end = allcookies.length;
            }

            var value = unescape(allcookies.substring(cookie_pos, cookie_end));         //这里就可以得到你想要的cookie的值了。。。
        }
        return value;
    }

    var portal = {
        init: function () {
            var userName = getCookie("userName");
            $.ajax({
                type: "GET",
                url: "user/selectByUserName.do?userName="+userName,
                success: function (responseData) {
                    var $user = responseData.data;
                    if($user.userType == 1){
                        $('#userManger').show();
                    }else if($user.userType == 2){
                        $('#userManger').hide();
                    }
                    if($user.status == 0){
                        $('.sysadministrator').html(' <img src="image/user2-160x160.jpg" width="24px" height="24px" >系统管理员');
                    }else if($user.status == 1){
                        $('.sysadministrator').html(' <img src="image/user2-160x160.jpg" width="24px" height="24px" >普通用户');
                    }
                }
            });
            this.bindBtnEvent($(".content-right"));
            $($('.content-menu')[0]).trigger('click');
        },
        bindBtnEvent: function(param){
            /*点击系统管理员*/
            /*$(".sysadministrator").unbind("click");
            $(".sysadministrator").on("click",function () {
               $("#sysadministrator").modal("show")
            });*/
            $('.content-menu').unbind('click');
            $('.content-menu').on('click',function(){
                $('.content-menu').removeClass('content-menu-box');
                var sef= this;
                var $url = $(sef).attr('data-url');
                var $name = $(sef).attr('data-name');
                $(sef).addClass('content-menu-box');
                $('.content-menu-author-img').attr('src','image/authorize.png');
                $('.content-menu-user-img').attr('src','image/user.png');
                $('.content-menu-system-img').attr('src','image/system.png');
                $(sef).find('img').attr('src','image/'+$name+'select.png');
                function sucView(e) {
                    param.empty();
                    param.append(e);
                    ves.getIndexJS($url,function (exports) {
                        exports.init();
                    })
                }
                function failView(e) {
                }
                ves.getIndexHtml($url,sucView,failView);
            });

            $('#signOut').unbind('click');
            $('#signOut').on('click',function(){
                $.ajax({
                    type: "GET",
                    url: "user/signOut.do",
                    success: function (responseData) {
                        if (!responseData.success) {
                            alert(responseData.errorMsg);
                        } else {
                            location.href = "login.html"
                        }
                    }
                });
            });
        },
        initPortalPanle: function (e) {//初始化导航
            var sef= this;

        },
        refresh: function () {
           // alert("主页面板")
        }
    };
    return portal;
})
;