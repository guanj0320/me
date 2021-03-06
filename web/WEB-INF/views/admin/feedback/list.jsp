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
    <script src="${ctx}/resources/js/bootstrap/bootstrap-paginator.min.js"></script>
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

            //选中checkbox
            var isreply = '${isreply}'.split(',');
            if(isreply!=null && isreply!="") {
                $("[name = isreply]:checkbox").each(function () {
                    for (var i = 0; i < isreply.length; i++) {
                        if (isreply[i] == $(this).val()) {
                            $(this).attr("checked", "checked");
                        }
                    }
                });
            }

            $('#btnQuery').click(function(){
                $('#feedbackForm').attr("action","${ctx}/admin/feedback/list");
                $('#feedbackForm').submit();
            });

            $('#btnAdd').click(function(){
                location.href = "${ctx}/admin/feedback/add";
            });

        });
        function doUpdate(id) {
            location.href = "${ctx}/admin/feedback/modify?id="+id;
        }

        function doDelete(id) {
            //$('body').on('hidden.bs.modal', '.modal', function () {$(this).removeData('bs.modal');});
            //清除delModal对话框的缓存
            $('#delModal').on('hidden.bs.modal', function() {
                $(this).removeData('bs.modal');
            });

            $('#delModal').modal('show').on('shown.bs.modal',function(){
                $("input[name='id']").val(id);
                $('#delBtn').click(function(){
                    $('#feedbackForm').attr("action","${ctx}/admin/feedback/remove");
                    $('#feedbackForm').submit();
                });
            });
        }
    </script>
</head>
<body>
<div id="wrap">
    <form id="feedbackForm" action="#" method="post">
        <input id="id" name="id" type="hidden"/>

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
            <!-- notes .alert -->

            <div class="panel-group" id="accordion">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapse1">
                                操作
                            </a>
                        </h3>
                    </div>
                    <div id="collapse1" class="panel-collapse collapse in">
                        <div class="panel-body">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="writer">留言者</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="writer" name="writer" value="${writer}" maxlength="20">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="isreply">已回复</label>
                                    <div class="col-sm-6">
                                        <input id="isreply" name="isreply" type="checkbox" value="0"> 否
                                        <input name="isreply" type="checkbox" value="1"> 是
                                    </div>
                                </div>
                            </div>
                            <button class="btn btn-primary" type="button" id="btnQuery">查 询</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>留言者</th>
                        <th>留言者邮箱</th>
                        <th>留言时间</th>
                        <th>已回复</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${feedbacks}" var="feedback" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${feedback.writer}</td>
                            <td>${feedback.email}</td>
                            <td><fmt:formatDate value="${feedback.createtime}" type="both"/></td>
                            <td>
                                <c:if test="${feedback.isreply eq 0}">否</c:if>
                                <c:if test="${feedback.isreply eq 1}">是</c:if>
                            </td>
                            <td>
                                <a href="#" onclick="doUpdate('${feedback.fid}')" title="打开"><i class="glyphicon glyphicon-folder-open"></i></a>
                                <a href="#" onclick="doDelete('${feedback.fid}')" title="删除"><i class="glyphicon glyphicon-trash"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <c:import url="/include/page_admin.jsp"/>
            </div>
        </div>
</div>
</form>
<!-- FOOTER -->
<c:import url="/include/footer_admin.jsp"/>

<div class="modal fade" id="delModal" tabindex="-1" role="dialog" aria-labelledby="delModalLabel" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="delModalLabel">确定删除？</h4>
            </div>
            <div class="modal-body">
                <div class="alert alert-danger">警告！此操作将删除记录，请确认是否真的需要删除？</div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                <button id="delBtn" type="button" class="btn btn-default">删除</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>
