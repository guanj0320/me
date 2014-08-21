<%@ page contentType="text/html; charset=utf-8" language="java" import="pf.constant.SystemConfig" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="这里guanjun的个人站点，这里有我关心的事，有我关心的blog，有我喜欢的照片。同时也希望感兴趣的朋友通过最下面的联系方式联系我。">
    <meta name="author" content="guanj0320@gmail.com">
    <meta name="keywords" content="个人网站 guanjun 管俊">
    <title>Guanjun's Home</title>
    <link href="${ctx}/resources/images/guanjun.me.ico" type=image/x-icon rel="shortcut icon">
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/resources/css/me.css">

    <script src="${ctx}/resources/js/jquery/jquery-1.10.1.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap3-validation.js"></script>
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


        /* CUSTOMIZE THE CAROUSEL
        -------------------------------------------------- */

        /* Carousel base class */
        .carousel {

            height: 500px;
            margin-bottom: 60px;
            margin-left: auto;
            margin-right: auto;
            max-width: 1200px;

        }
        /* Since positioning the image, we need to help out the caption */
        .carousel-caption {
            z-index: 10;
        }

        /* Declare heights because of positioning of img element */
        .carousel .item {
            height: 500px;
            /*
            background-color: #777;
            */
            background-color: #fff;
        }
        .carousel-inner > .item > img {
            position: absolute;
            top: 0;
            left: 0;
            width: 1200px;
            height: 500px;
        }

        .lead {

            font-size: 18px;
        }

        .weather {
            margin-bottom: 50px;
        }

    /* MARKETING CONTENT
    -------------------------------------------------- */

        /* Pad the edges of the mobile views a bit */
        .marketing {
            padding-left: 15px;
            padding-right: 15px;
        }

        /* Center align the text within the three columns below the carousel */
        .marketing .col-lg-4 {
            text-align: center;
            margin-bottom: 20px;
        }
        .marketing h2 {
            font-weight: normal;
        }
        .marketing .col-lg-4 p {
            margin-left: 10px;
            margin-right: 10px;
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



        /* RESPONSIVE CSS
        -------------------------------------------------- */

        @media (min-width: 768px) {

            /* Remove the edge padding needed for mobile */
            .marketing {
                padding-left: 0;
                padding-right: 0;
            }

            /* Navbar positioning foo */
            .navbar-wrapper {
                margin-top: 20px;
            }
            .navbar-wrapper .container {
                padding-left:  15px;
                padding-right: 15px;
            }
            .navbar-wrapper .navbar {
                padding-left:  0;
                padding-right: 0;
            }

            /* The navbar becomes detached from the top, so we round the corners */
            .navbar-wrapper .navbar {
                border-radius: 4px;
            }

            /* Bump up size of carousel content */
            .carousel-caption p {
                margin-bottom: 20px;
                font-size: 21px;
                line-height: 1.4;
            }

            .featurette-heading {
                font-size: 30px;
            }

        }

        @media (min-width: 992px) {
            .featurette-heading {
                margin-top: 20px;
            }
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
<c:import url="/include/head.jsp"/>

<!-- Carousel ================================================== -->
<div class="carousel slide" id="myCarousel" data-ride="carousel">
    <!-- Indicators -->
    <ol class="carousel-indicators">
        <c:forEach items="${banners}" var="banner" varStatus="status">
            <c:if test="${status.index==0}">
                <li class="active" data-target="#myCarousel" data-slide-to="${status.index}"></li>
            </c:if>
            <c:if test="${status.index!=0}">
                <li data-target="#myCarousel" data-slide-to="${status.index}"></li>
            </c:if>
        </c:forEach>
    </ol>
    <div class="carousel-inner">
        <c:forEach items="${banners}" var="banner" varStatus="status">
            <c:if test="${status.index==0}">
                <div class="active item">
                    <img src="${banner.pic}" alt="${banner.title}">
                    <div class="container">
                        <div class="carousel-caption">
                            <h1>${banner.title}</h1>
                            <p>${banner.content}</p>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${status.index!=0}">
                <div class="item">
                    <img src="${banner.pic}" alt="${banner.title}">
                    <div class="container">
                        <div class="carousel-caption">
                            <h1>${banner.title}</h1>
                            <p>${banner.content}</p>
                        </div>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
    <a class="left carousel-control" href="#myCarousel" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
</div><!-- /.carousel -->

<!-- Marketing messaging and featurettes
    ================================================== -->
<!-- Wrap the rest of the page in another container to center all the content. -->
<%--<div class="container weather">--%>
    <%--<div class="row">--%>
        <%--<div class="col-md-2">--%>
            <%--<h3 style=" text-align: center;">${weather.city}</h3>--%>
            <%--<p style=" text-align: center;">${weather.date_y}<br>${weather.week}</p>--%>
        <%--</div>--%>
        <%--<div class="col-md-1">--%>
            <%--<center><img src="${ctx}/resources/images/weather/${weather.fa}.png"></center>--%>
            <%--<p style=" text-align: center;">${weather.weather}<br>${weather.temperature}</p>--%>
        <%--</div>--%>
        <%--<div class="col-md-9 table-responsive">--%>
            <%--<table class="table-bordered" width="100%">--%>
                <%--<tr style="background-color: #ccc;">--%>
                    <%--<th style=" text-align: center;">当前天气</th>--%>
                    <%--<c:forEach items="${weather.futureList}" var="future" varStatus="status">--%>
                        <%--<th style=" text-align: center;">${future.date}</th>--%>
                    <%--</c:forEach>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--<td>温度: ${weather.temp}<br>风向: ${weather.wind_direction}<br>风力: ${weather.wind_strength}<br>湿度: ${weather.humidity}</td>--%>
                    <%--<c:forEach items="${weather.futureList}" var="future" varStatus="status">--%>
                        <%--<td>${future.week}<br>${future.temperature}<br><img src="${ctx}/resources/images/weather/${future.fa}_mini.png"> ${future.weather}<br>${future.wind}</td>--%>
                    <%--</c:forEach>--%>
                <%--</tr>--%>
            <%--</table>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<div class="container marketing">
    <!-- Three columns of text below the carousel -->
    <div class="row">
        <div class="col-xs-6 col-md-4">
            <center><img class="img-circle" src="/banner/s_banner1.jpg" style="width: 200px;height: 200px" alt="guitar"></center>
            <h2>Guitar</h2>
            <p><%= SystemConfig.S_BANNER1 %></p>
        </div><!-- /.col-lg-4 -->
        <div class="col-xs-6 col-md-4">
            <center><img class="img-circle" src="/banner/s_banner2.jpg" style="width: 200px;height: 200px" alt="photography"></center>
            <h2>Photography</h2>
            <p><%= SystemConfig.S_BANNER2 %></p>
        </div><!-- /.col-lg-4 -->
        <div class="col-xs-6 col-md-4">
            <center><img class="img-circle" src="/banner/s_banner3.jpg" style="width: 200px;height: 200px" alt="cooking"></center>
            <h2>Cooking</h2>
            <p><%= SystemConfig.S_BANNER3 %></p>
        </div><!-- /.col-lg-4 -->
    </div><!-- /.row -->


    <!-- START THE FEATURETTES -->
<c:forEach items="${articles}" var="article" varStatus="status">
    <hr class="featurette-divider">

    <div class="row featurette">
    <c:if test="${article.property eq 2}">
        <c:if test="${status.index % 2 == 0}">
        <div class="col-md-7">
            <h2 class="featurette-heading">${article.title} <span class="text-muted"><small><fmt:formatDate value="${article.createtime}" type="date"/></small></span></h2>
            <p class="lead">${article.description}</p>
            <p><a class="btn btn-default" href="${ctx}/blog/show?id=${article.arcid}" role="button">View details &raquo;</a></p>
        </div>

        <div class="col-md-5">
            <img class="img-rounded featurette-image-right img-responsive" src="${article.pic}" alt="Generic placeholder image">
        </div>
        </c:if>
        <c:if test="${status.index % 2 == 1}">
        <div class="col-md-5">
            <img class="img-rounded featurette-image-left img-responsive" src="${article.pic}" alt="Generic placeholder image">
        </div>

        <div class="col-md-7">
            <h2 class="featurette-heading">${article.title} <span class="text-muted"><small><fmt:formatDate value="${article.createtime}" type="date"/></small></span></h2>
            <p class="lead">${article.description}</p>
            <p><a class="btn btn-default" href="${ctx}/blog/show?id=${article.arcid}" role="button">View details &raquo;</a></p>
        </div>
        </c:if>
    </c:if>
    <c:if test="${article.property eq 1}">
        <div class="featurette-text">
            <h2 class="featurette-heading">${article.title} <span class="text-muted"><small><fmt:formatDate value="${article.createtime}" type="date"/></small></span></h2>
            <p class="lead">${article.description}</p>
            <p><a class="btn btn-default" href="${ctx}/blog/show?id=${article.arcid}" role="button">View details &raquo;</a></p>
        </div>
    </c:if>
    </div>
</c:forEach>
    <hr class="featurette-divider">

    <!-- /END THE FEATURETTES -->
</div>
</div>
<!-- FOOTER -->
<c:import url="/include/footer.jsp"/>
</body>
</html>
