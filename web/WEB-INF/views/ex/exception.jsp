<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="${ctx}/resources/images/guanjun.me.ico" type=image/x-icon rel="shortcut icon">
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap/bootstrap.min.css">

    <script src="${ctx}/resources/js/jquery/jquery-1.10.1.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
    <title>错误信息提示</title>
    <style>
        body {
            padding-top: 70px;
            padding-bottom: 30px;
            color: #5a5a5a;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="alert alert-danger">
        <strong>出错了!</strong> ${exception.message}
    </div>
    <button type="button" class="btn btn-primary" onclick="history.go(-1)">返回</button>
    <button type="button" class="btn btn-danger" data-toggle="collapse" data-target="#collapseOne"> 详细信息 </button>
    <div id="collapseOne" class="panel-collapse collapse">
        <div class="panel-body">
            <pre>${exceptionStack}</pre>
        </div>
    </div>
</div>
</body>

</html>

