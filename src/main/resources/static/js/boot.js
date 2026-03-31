/**
 * Created by zhzbin on 10/16/2015.
 */

(function () {
    var _ves = window.ves = window.ves || {}|| window.ves1
    var bootPATH="";
    _ves.avoidConsoleError = function () {
        var method;
        var noop = function () {
        };
        var methods = [
            'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
            'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
            'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
            'timeStamp', 'trace', 'warn'
        ];
        var length = methods.length;
        var console = (window.console = window.console || {});

        while (length--) {
            method = methods[length];

            // Only stub undefined methods.
            if (!console[method]) {
                console[method] = noop;
            }
        }
    };


    _ves.initBootPath = function () {
        var bootjs = document.getElementsByTagName("script");
        for (var i = 0; i < bootjs.length; i++) {
            var src = bootjs[i].src;
            var idx = src.indexOf("boot.js");
            if (idx > 0) {
                bootURL = src;
                bootPATH = src.substring(0, idx);
                var len = src.length;
                if (len > idx + 1) {
                    _ves.userlibs = src.substring(idx + 8).split(',');
                }
            }
        }
    };
    _ves.loadJs = function (paths) {
        // console.debug('loadJs:%o', paths);
        for (var _i = 0, _len = paths.length; _i < _len; _i++) {
            var path = paths[_i];
            if (!_ves.loaded[path]) {
                document.write('<script src="' + bootPATH + path + '" type="text/javascript"></script>');
                _ves.loaded[path] = true;
            }
        }
    };
    _ves.loadCss = function (paths) {
        // console.debug('loadCss: %o', paths);
        for (var _i = 0, _len = paths.length; _i < _len; _i++) {
            var path = paths[_i];
            if (!_ves.loaded[path]) {
                document.write('<link href="' + bootPATH + path + '" rel="stylesheet" type="text/css" />');
                _ves.loaded[path] = true;
            }
        }
    };
    _ves.loadLib = function (lib) {
        // console.debug('loadLib: %o', lib);
        if (lib.length != 2) return;
        if (lib[0] && lib[0].length > 0) {
            this.loadJs(lib[0]);
        }
        if (lib[1] && lib[1].length > 0) {
            this.loadCss(lib[1]);
        }
    };
    _ves.loadLibs = function (libs) {
        // console.debug('loadLibs: %o', libs);
        var sef = this;
        for (var _i = 0, _len = libs.length; _i < _len; _i++) {
            var libId = libs[_i];
            var libConf = _ves.CONF_LIBS[libId];
            if (libConf) {
                sef.loadLib(libConf);
            }
        }
    };

    _ves.avoidConsoleError();

    // 公有库配置：{id:[[js列表],[css列表]]}
    _ves.CONF_LIBS = {
        'bootstrap': [['bootstrap/bootstrap.min.js'], ['bootstrap/css/bootstrap.min.css']]
    };

    _ves.loaded = {};

    // 确定boot.js加载路径
    _ves.path = window.location.origin ? window.location.origin + "/" : "../";
    _ves.bootURL = '';
    _ves.userlibs = [];

    _ves.initBootPath();
    console.debug('bootPath %o', bootPATH);
    _ves.bootPath = bootPATH;
    // 加载公共库
    _ves.preloadLibs = ['jQuery-2.1.4.min.js','confirm/jquery-confirm.js', 'jquery.form.js','bootstrap/bootstrap.min.js','lodash/lodash.min.js',
        'require.js','boottable/bootstrap-table.min.js','boottable/bootstrap-table-zh-CN.js'];

    _ves.preloadCSSLibs = ['fontawesome/font-awesome.min.css','confirm/jquery-confirm.css','bootstrap/css/bootstrap.min.css','boottable/bootstrap-table.min.css'];

    //--
    _ves.loadCss(_ves.preloadCSSLibs);
    _ves.loadJs(_ves.preloadLibs);
    _ves.loadLibs(_ves.userlibs);
    //skin
    /*_ves.loadMiniSkin=function (){
        console.debug('loadMiniSkin');
        var skin = this.getCookie("miniuiSkin");
        if (!skin){
            skin = 'riil';
        }
        console.debug('skin: %o', skin);
        this.loadCss(['js/miniui/themes/'+skin+'/skin.css']);
    };*/
    _ves.setCookie = function (name, value) {
        var argv = this.setCookie.arguments;
        var argc = this.setCookie.arguments.length;
        var expires = (argc > 2) ? argv[2] : null;
        if (expires != null) {
            var LargeExpDate = new Date();
            LargeExpDate.setTime(LargeExpDate.getTime() + (expires * 1000 * 3600 * 24));
        }
        document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + LargeExpDate.toGMTString()));
    };

    _ves.getCookie = function (sName) {
        var aCookie = document.cookie.split("; ");
        var lastMatch = null;
        for (var i = 0; i < aCookie.length; i++) {
            var aCrumb = aCookie[i].split("=");
            if (sName == aCrumb[0]) {
                lastMatch = aCrumb;
            }
        }
        if (lastMatch) {
            var v = lastMatch[1];
            if (v === undefined) return v;
            return unescape(v);
        }
        return null;
    };
    _ves.cacheHtml = {};
    _ves.cacheJS = {};
    _ves.getRealHtml = function (path, sucfn, failfn) {
        this.getHtml(path, sucfn, failfn);
    };
    _ves.getIndexHtml = function (path, sucfn, failfn) {
        this.getHtml(path + "/index.html", sucfn, failfn);
    };
    _ves.getViewHtml = function (path, sucfn, failfn) {
        this.getVHtml(path + "/view.html", sucfn, failfn);
    };
    _ves.getVHtml = function (path, sucfn, failfn) {
        var sef = this;

        function success(e) {

            _ves._extracted(e);
            if (typeof sucfn == "function") {
                sucfn(e);
            }
        }

        // $.ajax({url:path,async:false,success:success});
        $.ajax(path).success(success).fail(failfn);
    };
    _ves._extracted=function (e) {
        if (e == "/") {
            window.location("/");
        }
    }

    _ves.getConfHtml = function (path, sucfn, failfn) {
        this.getHtml(path + "/config.html", sucfn, failfn);
    };
    _ves.getHtml = function (path, sucfn, failfn) {
        var sef = this;
        // if (this.cacheHtml[path]) {
        //     sucfn(new String(sef.cacheHtml[path]));
        // } else {
        function success(e) {
            if (typeof sucfn == "function") {
                _ves._extracted(e);

                //sef.cacheHtml[path] = new String(e);
                sucfn(new String(e));
            }
        }

        $.ajax({url:path,async:false,data:"",success:success,error:failfn});
        // $.ajax(path).success(success).fail(failfn);
        //}
    };

    _ves.getRealJS = function (path, fn) {
        this.getJS(path , fn);
    };
    _ves.getIndexJS = function (path, fn) {
        this.getJS(path + "index.js", fn);
    };
    _ves.getViewJS = function (path, fn) {
        this.getJS(path + "view.js", fn);
    };
    _ves.getConfJS = function (path, fn) {
        this.getJS(path + "config.js", fn);
    };
    _ves.getJS = function (path, fn) {
        var sef = this;
        // if (sef.cacheJS[path]) {
        //     fn.call(this, new $.extend(sef.cacheJS[path]));
        // } else {
        function success(e) {
            _ves._extracted(e);
            // sef.cacheJS[path] = e;
            if (typeof fn == "function") {
                fn.call(this, new $.extend(e));
            }
        }
        require([path], function (exports) {
            success(exports);
        });
        //  }

    };
    _ves.ajax = {
        url: ves.path + "vesapp/",
        call: function (method, params, callBack, type) {
            $.ajax({
                type: type ? type : "POST",
                url: this.url + method,
                contentType: "application/json;charset=UTF-8",
                data: params,
                async: true,
                cache:false,
                dataType: "json",
                success: function(e){
                    _ves._extracted(e);
                    callBack(e);
                },
                error: function(e){
                    _ves._extracted(e);
                    callBack(e);
                }
            });
        }
    };
    /*
     * 根据数据获取数据的树级结构或树级节点信息；
     * */
    _ves.getDataTree = function (data, id, pid) {
        id = id ? id : "id";
        pid = pid ? pid : "pid";
        var sef = this;
        var maptreeData = {}, rootId = '';
        $.each(data, function (i, item) {
            // sef.mapIdData[item.id] = $.extend("",item);
            maptreeData[item[id]] = item;
        });
        var treeData = {};
        $.each(data, function (i, item) {
            if (maptreeData[item[pid]]) {
                if (treeData[item[pid]]) {
                    if (treeData[item[pid]].children) {
                        treeData[item[pid]].children.push(item);
                    } else {
                        treeData[item[pid]].children = [];
                        treeData[item[pid]].children.push(item);
                    }
                } else {
                    treeData[item[pid]] = maptreeData[item[pid]];
                    treeData[item[pid]].children = [];
                    treeData[item[pid]].children.push(item);
                }

            } else {
                rootId = item[pid];
                treeData[item[id]] = item;
            }
        });
        var arrayData = [];
        $.each(treeData, function (i, item) {
            if (rootId == item[pid]) {
                arrayData.push(item);
            }
        });
        return arrayData;
    };
    /*
     * 初始化树节点高度函数方法；
     * */
    _ves.initTreeHeight = function (e) {
        var _height = $(window).height() - 70;
        var _count = $(".tree-height-auto").length;
        var size = (_height - 120) / _count;

        $(".sidebar").attr("style", "height:" + _height + "px;");
        $(".tree-height-auto").off("mouseover");
        $(".tree-height-auto").off("mouseout");
        $(".tree-height-auto").attr("style", "max-height:" + size + "px;overflow:hidden;");
        $(".tree-height-auto").on("mouseover", function (e) {
            $(this).attr("style", "max-height:" + size + "px;overflow:auto;");
        });
        $(".tree-height-auto").on("mouseout", function (e) {
            $(this).attr("style", "max-height:" + size + "px;overflow:hidden;");
        });
    };
//zhaojb 显示告警内容
    _ves.showAlarmContent= function(value,row){
        var msg = '<span style=\"cursor:pointer;\" title="';
        msg += value;
        msg += '"><i class="fa fa-square fa-rotate-45 ';
        var temp = {
            "UNKOWN": "text-muted",
            "CRITICAL": "text-red",
            "SERIOUS": "text-yellow",
            "WARN": "text-yellow2",
            "NORMAL": "text-green"
        };
        msg += temp[row.level];
        msg += '"></i>&nbsp;';
        msg +=value.substr(0, 15) + "...";
        msg += "</span>"
        return msg;
    }
//zhaojb显示告警信息的长显示名称
    _ves.showAlarmContentLong= function(value,row) {
        var msg = '<span style=\"cursor:pointer;\" title="';
        msg += value;
        msg += '"><i class="fa fa-square fa-rotate-45 ';
        var temp = {
            "UNKOWN": "text-muted",
            "CRITICAL": "text-red",
            "SERIOUS": "text-yellow",
            "WARN": "text-yellow2",
            "NORMAL": "text-green"
        };
        msg += temp[row.level];
        msg += '"></i>&nbsp;';
        if (value.length > 70) {
            msg += value.substr(0, 70) + "...";
        }else{
            msg += value;
        }
        msg += "</span>"
        return msg;1
    }
    // zhaojb 显示指标状态
    _ves.showStatus= function(status){
        var msg = '<span  title="';
        msg += status;
        msg += '"><i class="fa fa-square fa-rotate-45 ';
        var temp = {
            "UNKOWN": "text-muted",
            "CRITICAL": "text-red",
            "SERIOUS": "text-yellow",
            "WARN": "text-yellow2",
            "NORMAL": "text-green"
        };
        msg += temp[status];
        msg += '"></i>&nbsp;';
        msg += "</span>"
        return msg;
    }
    //zhaojb解析日期
    _ves.parseDate = function (_timeStamp) {
        if(_timeStamp!=null){
            var date = new Date();
            date.setTime(_timeStamp);
            return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() ;
        }else{
            return "-";
        }
    };

    //zhaojb显示loading提示
    _ves.loadingTip =function(){
        var _loadingHtml='<div    width="200px"  height=100% ' +
            'color="#fff" align="center" valign="middle"><span style="display:inline">' +
            '<i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i> ' +
            '<span class="sr-only">Loading...</span></span></div>';
        return _loadingHtml;
    }

    /*
     * 绑定和删除事件，针对于标签绑定事件；
     * */
    _ves.bindEvent = function (id, event, _callback) {
        $(id).off(event);
        $(id).on(event, _callback);
    };
    /*
     * 弹出信息提示框,warning,success,info,danger
     * */
    _ves.showMessage = function (type, message, time) {
        var msg = {
            warning: {name: "警告!", className: "alert alert-warning"},
            success: {name: "成功!", className: "alert alert-success"},
            info: {name: "信息!", className: "alert alert-info"},
            danger: {name: "错误!", className: "alert alert-danger"}
        };
        var _html = '<strong>' + message + '</strong>';
        $("#showMessage").html(_html);
        $("#showMessage").removeClass();
        $("#showMessage").addClass(msg[type].className);
        $("#showMessage").fadeOut();
        $("#showMessage").slideDown();
        time = time ? time * 1000 : 2000;
        setTimeout(function (e) {
            $("#showMessage").fadeIn();
            $("#showMessage").slideUp();
        }, time);
    };
    /*
     * 异步上传文件信息，支持IE9以上版本；
     * */
    _ves.upLoad = function (domId, url, _callBack, size, number) {
        if (window.FormData) {
            var formData = new FormData();
            formData.append(domId, document.getElementById(domId).files[0]);
            $.each(document.getElementById(domId).files, function (i, item) {
                //TODO 检查数据大小
                // size 文件大小
                // number  上传文件个数；
            });
            var xhr = new XMLHttpRequest();
            xhr.open('POST', url);
            xhr.onload = function () {
                var result = {status: xhr.status, success: true};
                if (xhr.status === 200 && typeof   _callBack == "function") {
                    result.success = true;
                } else if (typeof   _callBack == "function") {
                    result.success = false;
                }
                _callBack.call(this, result);
            };
            xhr.send(formData);
        } else {
            return {success: false, message: "此浏览器不支持上传！"};
        }
    };
    _ves.local={};
    _ves._verify = {};
    /*
     * 传入值为input Dom 值  eg：document.getElementById("inputID");
     * return 返回值为验证状态，通过返回true，否则返回false
     * */
    _ves.verification = function (dom,content,useTitle) {
        var method = dom.getAttribute("data-verify");
        if(!method){
            return true;
        }
        var verifyStatus = ves._verify[method].validator.call(this, dom.value);
        if (verifyStatus && !content) {
            $(dom).removeClass("input-red-boder");
            $(dom).off("mouseover");
            $(dom).off("mouseout");
            $(dom).tipso("destroy");
        } else {
            if (!$(dom).hasClass("input-red-boder")) {
                $(dom).addClass("input-red-boder");
            }
            var mes={
                useTitle: true
            };
            if(content){
                mes["content"]=content;
            }
            if(useTitle==false){
                mes["useTitle"]=useTitle;
            }
            $(dom).tipso(mes);
            $(dom).tipso('show');
            /*  $(dom).on("mouseover",function (e) {//这个是后来注释掉的，就是不让它鼠标移上去才提示错误信息。
             $(dom).tipso('show');
             });*/
            $(dom).on("mouseout",function (e) {
                $(dom).tipso('hide');
            });
        }
        return verifyStatus;
        // return $.extend(true,_ves._verify);
    };
    _ves.confirm=function (_callback,ids) {
        $("#confirmModal").modal("show");
        ves.bindEvent("#confirmDeleOk","click",function (e) {
            _callback.call(this,ids);
            $("#confirmModal").modal("hide");
        });
    };

    _ves.confirm3=function (dothis) {
        $("#confirmModal").modal("show");
        ves.bindEvent("#confirmDeleOk","click",function (e) {
            dothis();
            $("#confirmModal").modal("hide");
        });
    };
    _ves.GetQueryString=function (name){
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null)return  unescape(r[2]); return null;
    }

    //提示信息，直接关闭
    _ves.confirmTip=function (message) {
        $("#confirmModal").modal("show");
        $("#confirmModal h3").html(message);
        $("#confirmModal .modal-body").show();
        ves.bindEvent("#confirmDeleOk","click",function (e) {
            $("#confirmModal").modal("hide");
        });
    };
    /*
     * 提示信息，返回true或者false
     * */
    /*xueln 更改弹框*/
    _ves.confirm2=function(message,success,cancel,title){
        debugger;
        var dlg=$('<div/>');
        dlg.dialog({
            id:'confirmModal2',
            title:title,
            width: 300,
            height: 150,
            modal: true,
            buttons:[
                {text:'确定',
                    handler:function(){
                        success&&success.call(this);

                        dlg.dialog('destroy')
                    }

                },{
                    text:'取消',
                    handler:function(){
                        cancel&&cancel.call(this);
                        dlg.dialog('destroy')
                    }
                }
            ]
        });
        dlg.html(message);
    };
    _ves.confirm2Noused=function (message,success,cancel) {
        $("#confirmModal2").modal("show");
        $("#confirmModal2 h3").html(message);
        $("#confirmModal2 .modal-body").show();
        ves.bindEvent("#confirmDeleOk2","click",function () {
            $("#confirmModal2").modal("hide");
            success.call(this);
        });
        ves.bindEvent("#confirmDeleCancel","click",function () {
            $("#confirmModal2").modal("hide");
            cancel.call(this);
        });
    };
    _ves.UrlParamDel=function(url ,name ){

        var reg=new RegExp("\\\? | &"+name+"= ([^&]+)(&|&)","i");

        return url.replace(reg,"");

    };
        _ves.btQueryParam=function(e){
            console.log(e);
            var store=e.offset / e.limit;//offset是数据总行数 limit是每页的显示行数
            var params = {
                startRow:((store - 1) < 0 ? 0 :store) * e.limit,//开始行号
                rowCount: e.limit,//多少行
                page:store
            };
            $.extend(params, e);
            return params;
        },
        _ves.btResultHandler=function(data){
            var data = {
                total: parseInt(data.data.totalRecord),
                rows: data.data.rows
            };
            return data;
        }
})();
