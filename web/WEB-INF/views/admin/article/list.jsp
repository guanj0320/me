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
            $('#nav_2_publishblog').addClass('active');

            //选中checkbox
            var property = '${property}'.split(',');
            if(property!=null && property!="") {
                $("[name = property]:checkbox").each(function () {
                    for (var i = 0; i < property.length; i++) {
                        if (property[i] == $(this).val()) {
                            $(this).attr("checked", "checked");
                        }
                    }
                });
            }

            //选中select
            var colid = '${colid}';
            $('#colid').val(colid);


            $('#btnQuery').click(function(){
                $('#articleForm').attr("action","${ctx}/admin/article/list");
                $('#articleForm').submit();
            });

            $('#btnAdd').click(function(){
                location.href = "${ctx}/admin/article/add";
            });

        });
        function doUpdate(id) {
            location.href = "${ctx}/admin/article/modify?id="+id;
        }
        function doOpenComment(id) {
            location.href = "${ctx}/admin/comment/list?arcid="+id;
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
                    $('#articleForm').attr("action","${ctx}/admin/article/remove");
                    $('#articleForm').submit();
                });
            });
        }
    </script>
</head>
<body>
<div id="wrap">
    <form id="articleForm" action="#" method="post">
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
                <li class="active">Publish BLOG</li>
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
                                    <label class="col-sm-2 control-label" for="title">文章标题</label>
                                    <div class="col-sm-6">
                                        <input class="form-control" type="text" id="title" name="title" value="${title}" maxlength="20">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="colid">专栏</label>
                                    <div class="col-sm-6">
                                        <select class="form-control" id="colid" name="colid">
                                            <option value="">全部</option>
                                            <c:forEach items="${columns}" var="column">
                                                <option value="${column.colid}">${column.colname}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" for="property">类型</label>
                                    <div class="col-sm-6">
                                        <input id="property" name="property" type="checkbox" value="1"> 纯文字
                                        <input name="property" type="checkbox" value="2"> 图文
                                    </div>
                                </div>
                            </div>
                            <button class="btn btn-primary" type="button" id="btnQuery">查 询</button>
                            <button class="btn btn-default" type="button" id="btnAdd">新 增</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>文章标题</th>
                        <th>所属专栏</th>
                        <th>发表人</th>
                        <th>创建时间</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${articles}" var="article" varStatus="status">
                        <tr>
                            <td>${status.count}</td>
                            <td>${article.title}</td>
                            <td>${article.colname}</td>
                            <td>${article.username}</td>
                            <td><fmt:formatDate value="${article.createtime}" type="both"/></td>
                            <td>
                                <a href="#" onclick="doUpdate('${article.arcid}')" title="打开"><i class="glyphicon glyphicon-folder-open"></i></a>
                                <a href="#" onclick="doOpenComment('${article.arcid}')" title="打开评论"><i class="glyphicon glyphicon-comment"></i></a>
                                <a href="#" onclick="doDelete('${article.arcid}')" title="删除"><i class="glyphicon glyphicon-trash"></i></a>
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
