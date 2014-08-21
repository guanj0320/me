<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Banner Management</title>
    <link href="${ctx}/resources/images/guanjun.me.ico" type=image/x-icon rel="shortcut icon">
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap/bootstrap.min.css">

    <script src="${ctx}/resources/js/jquery/jquery-1.10.1.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap3-validation.js"></script>

    <style>
        html,body {
            height: 100%;
            color: #5a5a5a;
        }

        /* Wrapper for page content to push down footer */
        #wrap {
            min-height: 100%;
            height: auto;
            /* Negative indent footer by its height */
            margin: 0 auto -60px;
            /* Pad bottom by footer height */
            padding: 60px 0 60px;
        }

    </style>
    <script>
        $(document).ready(function(){
            //导航条的选中样式
            $('#nav_management').addClass('active');
            $('#nav_2_bannermanagement').addClass('active');

            $('#btnBack').click(function(){
                location.href = "${ctx}/admin/banner/list";
            });

            $('#btnSave').click(function() {
                $('#bannerForm').validation();
                if ($("#bannerForm").valid() == false){
                } else {
                    var opt = $('#opt').val();
                    if("add"==opt) {
                        $("#bannerForm").attr("action", "${ctx}/admin/banner/add");
                    } else if("modify"==opt) {
                        $("#bannerForm").attr("action", "${ctx}/admin/banner/modify");
                    }
                    $("#bannerForm").submit();
                }
            });

            //初始化图片显示
            var pic = '${banner.pic}';
            if(pic != null && pic != "") {
                var img = '<img src="'+pic+'" width="360px" height="200px"/>';
                $("#destination").empty().append(img);
            }

            //处理显示本地图片 判断浏览器是否有FileReader接口
            if(typeof FileReader =='undefined')  {
                $("#destination").css({'background':'none'}).html('亲,您的浏览器还不支持HTML5的FileReader接口,无法使用图片本地预览,请更新浏览器获得最好体验');
                //如果浏览器是ie
                if($.browser.msie===true) {
                    //ie6直接用file input的value值本地预览
                    if($.browser.version==6) {
                        $("#fileupload").change(function(event) {
                            //ie6下怎么做图片格式判断?
                            var src = event.target.value;
                            //var src = document.selection.createRange().text;  //选中后 selection对象就产生了 这个对象只适合ie
                            var img = '<img src="'+src+'" width="360px" height="200px" />';
                            $("#destination").empty().append(img);
                        });
                    }
                    //ie7,8使用滤镜本地预览
                    else if($.browser.version==7 || $.browser.version==8) {
                        $("#fileupload").change(function(event){
                            $(event.target).select();
                            var src = document.selection.createRange().text;
                            var dom = document.getElementById('destination');
                            //使用滤镜 成功率高
                            dom.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src= src;
                            dom.innerHTML = '';
                            //使用和ie6相同的方式 设置src为绝对路径的方式 有些图片无法显示 效果没有使用滤镜好
                            /*var img = '<img src="'+src+'" width="200px" height="200px" />';
                             $("#destination").empty().append(img);*/
                        });
                    }
                }
                //如果是不支持FileReader接口的低版本firefox 可以用getAsDataURL接口
                else if($.browser.mozilla===true) {
                    $("#fileupload").change(function(event){
                        //firefox2.0没有event.target.files这个属性 就像ie6那样使用value值 但是firefox2.0不支持绝对路径嵌入图片 放弃firefox2.0
                        //firefox3.0开始具备event.target.files这个属性 并且开始支持getAsDataURL()这个接口 一直到firefox7.0结束 不过以后都可以用HTML5的FileReader接口了
                        if(event.target.files)  {
                            //console.log(event.target.files);
                            for(var i=0;i<event.target.files.length;i++) {
                                var img = '<img src="'+event.target.files.item(i).getAsDataURL()+'" width="360px" height="200px"/>';
                                $("#destination").empty().append(img);
                            }
                        } else {
                            //console.log(event.target.value);
                            //$("#imgPreview").attr({'src':event.target.value});
                        }
                    });
                }
            } else  {
                //单图上传 version 2
                $("#fileupload").change(function(e) {
                    var file = e.target.files[0];
                    var reader = new FileReader();
                    reader.onload = function(e){
                        //displayImage($('bd'),e.target.result);
                        //alert('load');
                        var img = '<img src="'+e.target.result+'" width="360px" height="200px"/>';
                        $("#destination").empty().append(img);

                    }
                    reader.readAsDataURL(file);
                });


            } //处理显示本地图片 判断浏览器是否有FileReader接口

        });


    </script>
</head>
<body>
<div id="wrap">
    <!-- HEAD -->
    <c:import url="/include/head_admin.jsp"/>

    <!-- BODY -->

    <div class="container">
        <!-- BREADCRUMB -->
        <ol class="breadcrumb">
            <li class="glyphicon glyphicon-home"></li>
            <li>Home</li>
            <li>MANAGEMENT</li>
            <li class="active">Banner Management</li>
        </ol>
        <div class="alert alert-success">
            <h4>提示</h4>
            <ul>
                <li>这是对首页中的banner大图进行设置</li>
                <li>图片要求宽度为900px,高度为500px</li>
            </ul>
        </div><!-- notes .alert -->
        <div class="panel-group" id="accordion">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapse2">
                            表单数据
                        </a>
                    </h4>
                </div>
                <div id="collapse2" class="panel-collapse collapse in">
                    <div class="panel-body">
                        <fieldset>
                            <form class="form-horizontal" action="#" id="bannerForm" method="post" enctype="multipart/form-data">
                                <input type="hidden" id="bid" name="bid" value="${banner.bid}"/>
                                <input type="hidden" id="opt" name="opt" value="${opt}"/>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="title">标题</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="title" name="title" value="${banner.title}" placeholder="此为必填项" maxlength="20" check-type="required" autofocus>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="content">内容</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="content" name="content" value="${banner.content}" maxlength="80">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="ord">顺序</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="ord" name="ord" value="${banner.ord}"  maxlength="1" check-type="number required" required-message="此项必须填写" number-message="此项必须填写数字">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="pic">缩略图</label>
                                    <div class="col-sm-6">
                                        <input id="fileupload" type="file" name="files[]" multiple>
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-6 col-md-8"><div id="destination" style="width:360px;height:200px;border:1px solid #000000;">请先点击"选择文件"</div></div>
                                            <div class="col-xs-6 col-md-4"><div id="divBtnUpload"></div></div>
                                        </div>
                                        <input class="form-control" type="text" id="pic" name="pic" value="${banner.pic}" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="createtime">创建时间</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="createtime" name="createtime" value='<fmt:formatDate value="${banner.createtime}" type="both"/>' readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="updatetime">更新时间</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="updatetime" name="updatetime" value='<fmt:formatDate value="${banner.updatetime}" type="both"/>' readonly>
                                    </div>
                                </div>
                            </form>
                            <div class="well">
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button class="btn btn-primary" type="button" id="btnSave">保 存</button>
                                        <button class="btn btn-default" type="button" id="btnBack">返 回</button>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- FOOTER -->
<c:import url="/include/footer_admin.jsp"/>

</body>
</html>
