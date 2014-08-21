<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Guanjun.me-Admin</title>
    <link href="${ctx}/resources/images/guanjun.me.ico" type=image/x-icon rel="shortcut icon">
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/resources/css/messenger/messenger.css">
    <link rel="stylesheet" href="${ctx}/resources/css/messenger/messenger-theme-block.css">

    <script src="${ctx}/resources/js/jquery/jquery-1.10.1.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap3-validation.js"></script>
    <script src="${ctx}/resources/js/holder/holder.min.js"></script>
    <script src="${ctx}/resources/js/messenger/messenger.min.js"></script>
    <style>
        body {
            height: 100%;
            /*
              padding-top: 70px;
              */
            /*padding-bottom: 30px;*/
            color: #5a5a5a;
        }

        /* Wrapper for page content to push down footer */
        #wrap {
            min-height: 100%;
            height: auto;
            /* Negative indent footer by its height */
            margin: 0 auto 0;
            /* Pad bottom by footer height */
            padding: 0 0 0;
        }

        #wrap > .container {
            padding: 100px 15px 0;
        }

        .container .text-muted {
            margin: 20px 0;
        }

        /* Featurettes
        ------------------------- */

        .featurette-divider {
            margin: 50px 0; /* Space out the Bootstrap <hr> more */
        }

        /* Thin out the marketing headings */
        .featurette-heading {
            font-weight: 300;
            line-height: 1;
            letter-spacing: -1px;
        }

        .featurette-image-left {
            margin-left: 0;
            margin-right: auto;
        }

        .featurette-image-right {
            margin-left: auto;
            margin-right: 0;
        }

        .featurette-text {
            margin-left: 20px;
            margin-right: 20px;
        }


    </style>
    <script>
        $(document).ready(function(){
            //导航条的选中样式
            $('#nav_home').addClass('active');
        });

    </script>
</head>
<body>
<div id="wrap">
    <!-- HEAD -->
    <c:import url="/include/head_admin.jsp"/>

    <!-- Marketing messaging and featurettes
        ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->

    <div class="container">

        <div class="row">
            <div class="featurette-text">
                <h2 class="featurette-heading">First featurette heading. <span class="text-muted">It'll blow your mind.</span></h2>
                <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
                <img class="featurette-image img-responsive" data-src="holder.js/500x500/auto" alt="Generic placeholder image">
            </div>
        </div>

        <hr class="featurette-divider">

        <!-- /END THE FEATURETTES -->
    </div>
</div>
<!-- FOOTER -->
<c:import url="/include/footer_admin.jsp"/>
</body>
</html>
