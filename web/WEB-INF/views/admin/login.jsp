<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
    <title>Guanjun.me Admin</title>
    <link href="${ctx}/resources/images/guanjun.me.ico" type=image/x-icon rel="shortcut icon">
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/resources/css/messenger/messenger.css">
    <link rel="stylesheet" href="${ctx}/resources/css/messenger/messenger-theme-block.css">
    <link rel="stylesheet" href="${ctx}/resources/css/fontawesome/font-awesome.min.css">

    <script src="${ctx}/resources/js/jquery/jquery-1.10.1.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
    <script src="${ctx}/resources/js/messenger/messenger.min.js"></script>
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

        .validateCodeDiv {
             margin: 10px 0 10px 10px;

        }

        #validatecode {
            width: 40px;
            height: 24px;
            padding-left: 10px;
        }

        .form-signin {
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
            height: 96%
        }
        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }
        .form-signin .checkbox {
            font-weight: normal;
        }
        .form-signin .form-control {
            position: relative;
            font-size: 16px;
            height: auto;
            padding: 10px;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }
        .form-signin .form-control:focus {
            z-index: 2;
        }

    </style>
    <script>
        $(document).ready(function(){
            $._messengerDefaults = {
                extraClasses: 'messenger-fixed messenger-theme-block messenger-on-top'
            };
            var msg = "${msg}";
            if(null!=msg && ""!=msg) {
                Messenger().post({
                    message: msg,
                    type: "error"
                });
            }

            $('input').iCheck({
                checkboxClass: 'icheckbox_flat-blue',
                radioClass: 'iradio_flat-blue'
            });
        });
        function refreshValidateCode(id) {
            $("#" + id).attr("src", "");
            $("#" + id).attr("src", "${ctx}/admin/code");
        }
    </script>
</head>
<body>
<div id="wrap">
<div class="container">
<form class="form-signin" id="loginForm" action="${ctx}/admin/logon" method="post">
    <h2 class="form-signin-heading">GUANJUN.ME Admin</h2>
    <div class="input-group">
        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
        <input class="form-control" name="userid" type="text" placeholder="Username" required autofocus>
    </div>
    <div class="input-group">
        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
        <input class="form-control" name="pwd" type="password" placeholder="Password" required>
    </div>
    <div class="validateCodeDiv">
            <a href="javascript:refreshValidateCode('codeImage');" title="点击刷新" style="width: 130px; height: 30px;">
                <img id="codeImage" width="80px" height="24px" src="/admin/code">
            </a>
            <input id="validatecode" name="validatecode" type="text" size="2" value=""/>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
</form>
</div></div>
<!-- FOOTER -->
<c:import url="/include/footer_admin.jsp"/>
</body>
</html>
