<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title></title>
    <link href="${ctx}/resources/images/guanjun.me.ico" type=image/x-icon rel="shortcut icon">
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap/bootstrap.min.css">

    <script src="${ctx}/resources/js/jquery/jquery-1.10.1.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
</head>
<style>
    html,body {
        height: 100%;
        background-color: #eee;
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
    /* Main marketing message and sign up button */
    .jumbotron {
        text-align: center;
        background-color: transparent;
    }
    .jumbotron .btn {
        font-size: 21px;
        padding: 14px 24px;
    }

</style>
<body>
<div id="wrap">
<div class="container">
    <div class="jumbotron">
        <h1>Session Lost!</h1>
        <p class="lead">用户验证失败，点击下面的按钮重新登录</p>
        <p><a class="btn btn-lg btn-success" href="${ctx}/admin/login" role="button">重新登录</a></p>
    </div>
</div>
</div>
<!-- FOOTER -->
<c:import url="/include/footer_admin.jsp"/>
</body>
</html>
