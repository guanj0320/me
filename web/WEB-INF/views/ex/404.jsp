<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-cn">
<HEAD>
    <title>404错误页面</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="${ctx}/resources/images/guanjun.me.ico" type=image/x-icon rel="shortcut icon">
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap/bootstrap.min.css">

    <script src="${ctx}/resources/js/jquery/jquery-1.10.1.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
    <style type=text/css>
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
        .mod-notfound {
            border-right: #e1e1e1 1px solid;
            border-top: #e1e1e1 1px solid;
            margin-top: 10px;
            background-color: #fff;
            border-left: #e1e1e1 1px solid;
            border-bottom: #e1e1e1 1px solid;
            height: 585px;
            text-align: center;
            border-radius: 10px
        }

        .clearfix {
            zoom: 1
        }
        .wordwrap {
            word-break: break-all;
            word-wrap: break-word
        }
        .mod-page-body .mod-page-main .x-page-container {
            padding-right: 0;
            padding-left: 0;
            padding-bottom: 0;
            margin: 0;
            padding-top: 0;
            zoom: 1
        }

        .mod-page-body .t-remark {
            color: #959595
        }
        .mod-page-body A.a-normal {
            color: #3fa7cb
        }
        .mod-page-body A.a-normal:hover {
            color: #3cbce7
        }
        .mod-page-body A.a-incontent {
            color: #454545
        }
        .mod-page-body A.a-incontent:hover {
            color: #3fa7cb
        }
        .mod-page-body A.a-insist {
            color: #f46e6e
        }
        .mod-page-body A.a-insist:hover {
            color: #c55454
        }
        .grid-98 {
            padding-right: 0;
            padding-left: 0;
            padding-bottom: 0;
            margin: 0 auto;
            overflow: hidden;
            padding-top: 0
        }
    </style>
<body>
<div id="wrap">
<div  class="container">
    <div class="mod-page-main wordwrap clearfix">
        <div class=x-page-container>
            <div class="mod-notfound grid-98">
                <img class=img-notfound height=320 src="${ctx}/resources/images/notfound.gif" width=520>
                <p style="font-size: 24px; line-height: 70px">啊~哦~ 您要查看的页面不存在或已删除！</p>
                <p style="margin-bottom: 30px">请检查您输入的网址是否正确，或者点击链接继续浏览空间</p>
                <p style="font-size: 14px; line-height: 20px">
                    您可以回到 <a href="${ctx}/admin/home">网站首页</a>
                    或到<a href="http://www.leadto.com.cn/a/lianxiwomen/lianxifangshi/index.html" target=_blank>帮助中心</a><br>
                    如若不能解决您的问题，请进入<A href="http://www.leadto.com.cn/" target=_blank>投诉中心</a>
                    或者<a href="http://www.leadto.com.cn/" target=_blank>空间客服</a>提出建议^_^
                </p>
            </div>
        </div>
    </div>
</div>
</div>
<!-- FOOTER -->
<c:import url="/include/footer_admin.jsp"/>
</body>
</html>