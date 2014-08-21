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
    <title>Reply Feedback</title>
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
            $('#nav_2_replyfeedback').addClass('active');

            $('#btnBack').click(function(){
                location.href = "${ctx}/admin/feedback/list";
            });

            $('#btnSave').click(function() {
                $('#feedbackForm').validation();
                if ($("#feedbackForm").valid() == false){
                } else {
                    var opt = $('#opt').val();
                    if("add"==opt) {
                        $("#feedbackForm").attr("action", "${ctx}/admin/feedback/add");
                    } else if("modify"==opt) {
                        $("#feedbackForm").attr("action", "${ctx}/admin/feedback/modify");
                    }
                    $("#feedbackForm").submit();
                }
            });

            $('#btnGiveup').click(function() {
                $("#feedbackForm").attr("action", "${ctx}/admin/feedback/giveup");
                $("#feedbackForm").submit();
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
            <li class="active">Reply Feedback</li>
        </ol>
        <div class="alert alert-success">
            <h4>提示</h4>
            <ul>
                <li>这是对别人对你的留言</li>
                <li>点击“已读并回复”按钮，回复留言将通过您的邮箱把回复信息发到对方的邮箱</li>
                <li>点击“已读”按钮，就代表您已经读了，但不会发邮件给对方</li>
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
                            <form class="form-horizontal" action="#" id="feedbackForm" method="post">
                                <input type="hidden" id="fid" name="fid" value="${feedback.fid}"/>
                                <input type="hidden" id="pfid" name="pfid" value="${feedback.pfid}"/>
                                <input type="hidden" id="opt" name="opt" value="${opt}"/>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="writer">留言者</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="writer" name="writer" value="${feedback.writer}" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="ip">留言者IP</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="ip" name="ip" value="${feedback.ip}" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="email">留言者E-Mail</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="email" name="email" value="${feedback.email}" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="createtime">留言时间</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="createtime" name="createtime" value="<fmt:formatDate value="${feedback.createtime}" type="both"/>" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="content">留言内容</label>
                                    <div class="col-sm-6">
                                        <textarea class="form-control" rows="3" id="content" name="content" readonly>${feedback.content}</textarea>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="replycontent">回复内容</label>
                                    <div class="col-sm-6">
                                        <textarea class="form-control" rows="3" id="replycontent" name="replycontent" maxlength="500" check-type="required">${feedback.replycontent}</textarea>
                                    </div>
                                </div>
                            </form>
                            <div class="well">
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <button class="btn btn-primary" type="button" id="btnSave">已读并回复</button>
                                        <button class="btn btn-default" type="button" id="btnGiveup">已 读</button>
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
