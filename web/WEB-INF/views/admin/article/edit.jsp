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
    <title>Publish BLOG</title>
    <link href="${ctx}/resources/images/guanjun.me.ico" type=image/x-icon rel="shortcut icon">
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/umeditor/themes/default/css/umeditor.min.css">

    <script src="${ctx}/resources/js/jquery/jquery-1.10.1.min.js"></script>
    <script src="${ctx}/resources/js/jquery/jquery.ui.widget.js"></script>
    <script src="${ctx}/resources/js/jquery/jquery.iframe-transport.js"></script>
    <script src="${ctx}/resources/js/jquery/jquery.fileupload.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap3-validation.js"></script>

    <!-- 配置文件 -->
    <script src="${ctx}/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script src="${ctx}/ueditor/ueditor.all.min.js"></script>
    <!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) -->
    <script src="${ctx}/ueditor/lang/zh-cn/zh-cn.js"></script>

    <!-- 配置文件 -->
    <script src="${ctx}/umeditor/umeditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script src="${ctx}/umeditor/umeditor.min.js"></script>
    <!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) -->
    <script src="${ctx}/umeditor/lang/zh-cn/zh-cn.js"></script>

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
        #dropzone {
            background: #cccccc;
            width: 150px;
            height: 50px;
            line-height: 50px;
            text-align: center;
            font-weight: bold;
        }
        #dropzone.in {
            width: 600px;
            height: 200px;
            line-height: 200px;
            font-size: larger;
        }
        #dropzone.hover {
            background: lawngreen;
        }
        #dropzone.fade {
            -webkit-transition: all 0.3s ease-out;
            -moz-transition: all 0.3s ease-out;
            -ms-transition: all 0.3s ease-out;
            -o-transition: all 0.3s ease-out;
            transition: all 0.3s ease-out;
            opacity: 1;
        }
    </style>
    <script>
        $(document).ready(function(e){
            //导航条的选中样式
            $('#nav_management').addClass('active');
            $('#nav_2_publishblog').addClass('active');

            //初始化图片显示
            var pic = '${article.pic}';
            if(pic != null && pic != "") {
                var img = '<img src="'+pic+'" width="320px" height="240px"/>';
                $("#destination").empty().append(img);
            }

            //选中checkbox
            var property = '${article.property}';
            if(property!=null && property!="") {
                $("input[name = property]").each(function () {
                    if(property==$(this).val()) {
                        $(this).attr("checked", "checked");
                    }
                });
            }

            //选中select
            var colid = '${article.colid}';
            $('#colid').val(colid);

            $('#btnBack').click(function(){
                location.href = "${ctx}/admin/article/list";
            });

            $('#btnSave').click(function() {
                $('#articleForm').validation();
                if ($("#articleForm").valid() == false){
                    $("#error-text").text("error!");
                    return false;
                } else {
                    //提示如果选择了图片，但未点击“Upload”按钮
                    if($("#btnUpload").length>0){
                        if( confirm("您选择了缩略图，但未点击“Upload”按钮进行上传。\n\n是否确定保存此文章?") ) {
                        } else {
                            return false;
                        }
                    }
                    var opt = $('#opt').val();
                    if("add"==opt) {
                        $("#articleForm").attr("action", "${ctx}/admin/article/add");
                    } else if("modify"==opt) {
                        $("#articleForm").attr("action", "${ctx}/admin/article/modify");
                    }
                    $("#articleForm").submit();
                }
            });

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
                            var img = '<img src="'+src+'" width="320px" height="240px" />';
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
                                var img = '<img src="'+event.target.files.item(i).getAsDataURL()+'" width="320px" height="240px"/>';
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
                        var img = '<img src="'+e.target.result+'" width="320px" height="240px"/>';
                        $("#destination").empty().append(img);

                    }
                    reader.readAsDataURL(file);
                });


            } //处理显示本地图片 判断浏览器是否有FileReader接口

            //处理图片拖拽的代码
            var destDom = document.getElementById('destination');
            destDom.addEventListener('dragover',function(event){
                event.stopPropagation();
                event.preventDefault();
            },false);

            destDom.addEventListener('drop',function(event){
                event.stopPropagation();
                event.preventDefault();
                var img_file = event.dataTransfer.files.item(0);    //获取拖拽过来的文件信息 暂时取一个
                //console.log(event.dataTransfer.files.item(0).type);

                fReader = new FileReader();
                fReader.readAsDataURL(img_file);
                fReader.onload = function(event){
                    destDom.innerHTML='';
                    destDom.innerHTML = '<img src="'+event.target.result+'" width="320px" height="240px"/>';
                };
            },false);//处理图片拖拽的代码

        });
        $(function(){
            window.um = UM.getEditor('container', {
                /* 传入配置参数,可配参数列表看umeditor.config.js */
                //toolbar: ['undo redo | bold italic underline']
            });
        });



        $(function () {
            //初始化，主要是设置上传参数，以及事件处理方法(回调函数)
            $('#fileupload').fileupload({
                autoUpload: false,//是否自动上传
                dataType: 'json',
                add: function (e, data) {

                    $('#divBtnUpload').text('');
                    data.context = $('<button id="btnUpload" class="btn btn-primary btn-lg"/>').text('Upload')
                            .appendTo('#divBtnUpload')
                            .click(function () {
                                $(this).replaceWith($('<p/>').text('Uploading...'));
                                data.submit();
                            });
                },

                done: function (e, data) {//设置文件上传完毕事件的回调函数
                    //$('<p/>').text(data.result.fileUrl).appendTo('#files');
                    $('#divBtnUpload').text('');
                    $('<p style="color: #008200;font-size: 16px"/>').text('Upload finished!').appendTo('#divBtnUpload');
                    $('#pic').attr('value',data.result.fileUrl);
                    //$('#uploadImg').attr('src',data.result.fileUrl);
                },
                progressall: function (e, data) {//设置上传进度事件的回调函数
                    var progress = parseInt(data.loaded / data.total * 100, 10);
                    $('#progress .bar').css(
                            'width',
                                    progress + '%'
                    );
                },

                dropZone: $('#destination')
            });
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
            <li class="active">Publish BLOG</li>
        </ol>
        <div class="alert alert-success">
            <h4>提示</h4>
            <ul>
                <li>这是对BLOG进行发布与修改</li>
                <li></li>
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
                            <form class="form-horizontal" action="#" id="articleForm" method="post">
                                <input type="hidden" id="arcid" name="arcid" value="${article.arcid}"/>
                                <input type="hidden" id="opt" name="opt" value="${opt}"/>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="title">文章标题</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="title" name="title" value="${article.title}" placeholder="此为必填项" maxlength="20" check-type="required" autofocus>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="colid">专栏</label>
                                    <div class="col-sm-6">
                                        <select class="form-control" id="colid" name="colid">
                                            <c:forEach items="${columns}" var="column">
                                                <option value="${column.colid}">${column.colname}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="tid">TAG</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="tid" name="tid" value="${article.tid}" placeholder="不同标签以空格分割" maxlength="400">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="property">文章类型</label>
                                    <div class="col-sm-6">
                                        <div class="col-sm-6">
                                            <input id="property" name="property" type="radio" value="1"> 纯文字
                                            <input name="property" type="radio" value="2"> 图文
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="ord">文章顺序</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="ord" name="ord" value="${article.ord}" maxlength="2" check-type="number required" required-message="此项必须填写" number-message="此项必须填写数字">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="description">文章描述</label>
                                    <div class="col-sm-6">
                                        <textarea class="form-control" rows="2" id="description" name="description" maxlength="400">${article.description}</textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="source">文章来源</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="source" name="source" value="${article.source}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="pic">缩略图</label>
                                    <div class="col-sm-6">
                                        <input id="fileupload" type="file" name="files[]" data-url="${ctx}/controller/uploadOne" multiple>
                                        <div id="progress">
                                            <div style="width: 0%;"></div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-6 col-md-8"><div id="destination" style="width:320px;height:240px;border:1px solid #000000;">Drop files here</div></div>
                                            <div class="col-xs-6 col-md-4"><div id="divBtnUpload"></div></div>
                                        </div>
                                        <input class="form-control" type="text" id="pic" name="pic" value="${article.pic}" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="container">文章正文</label>
                                    <div class="col-sm-6">
                                        <script id="container" name="content" type="text/plain" style="width:100%;height:200px;">${article.content}</script>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="createtime">创建时间</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="createtime" name="createtime" value='<fmt:formatDate value="${article.createtime}" type="both"/>' readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="updatetime">更新时间</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="updatetime" name="updatetime" value='<fmt:formatDate value="${article.updatetime}" type="both"/>' readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <span id="error-text" style="color: #FF0000;"></span>
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
