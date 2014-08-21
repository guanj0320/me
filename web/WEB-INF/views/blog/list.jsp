<%@ page contentType="text/html; charset=utf-8" language="java"%>
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
    <meta name="description" content="guanjun觉得非常好的blog">
    <meta name="author" content="guanj0320@gmail.com">
    <meta name="keywords" content="个人网站 guanjun 管俊">
    <title>BLOG</title>
    <link href="${ctx}/resources/images/guanjun.me.ico" type=image/x-icon rel="shortcut icon">
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/resources/css/me.css">

    <script src="${ctx}/resources/js/jquery/jquery-1.10.1.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap-paginator.min.js"></script>

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
            padding: 60px 15px 0;
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
            font-size: 30px;
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

        .lead {

            font-size: 18px;
        }

        /**
                 * Sort buttons
                 */
        #sortbys {
            list-style-type: none;
            text-align: center;
            margin: 0 5% 0 5%;
            padding: 60px 15px 0;
        }

        #sortbys li {
            font-size: 12px;
            float: left;
            padding: 6px 0 4px 0;
            cursor: pointer;
            margin: 0 1% 0 1%;
            width: 8%;
            -webkit-transition: all 0.15s ease-out;
            -moz-transition: all 0.15s ease-out;
            -o-transition: all 0.15s ease-out;
            transition: all 0.15s ease-out;
            -webkit-border-radius: 3px;
            -moz-border-radius: 3px;
            border-radius: 3px;
        }

        #sortbys li:hover {
            background: #dedede;
        }

        #sortbys li.active {
            background: #428BCA;
            color: #ffffff;
        }
    </style>
    <script>
        $(document).ready(function(){
            //导航条的选中样式
            $('#nav_blog').addClass('active');

            var tid = '${tid}';

            if(tid=="" || tid==null) {
                $('#all').addClass('active');
            } else {
                $('#' + tid).addClass('active');
            }
        });

        function flushBlog(id,name) {
            $('#tid').val(id);
            $('#tagname').val(name);
            $('#blogForm').attr('action','${ctx}/blog');
            $('#blogForm').submit();
        }
    </script>
</head>
<body>
<div id="wrap">
    <!-- HEAD -->
    <c:import url="/include/head.jsp"/>

    <form id="blogForm" action="#" method="post">
        <input id="id" name="id" type="hidden"/>
        <input id="tid" name="tid" type="hidden" value="${tid}"/>
        <input id="tagname" name="tagname" type="hidden" value="${tagname}"/>

    <div class="container">
        <ol id="sortbys">
            <li id="all" onclick="flushBlog('')">全部</li>
            <c:forEach items="${tags}" var="tag">

                <li id="${tag.tid}" onclick="flushBlog('${tag.tid}','${tag.tagname}')">${tag.tagname}</li>

            </c:forEach>
        </ol>
        <c:forEach items="${articles}" var="article" varStatus="status">
            <hr class="featurette-divider">

            <div class="row featurette">
                <c:if test="${article.property eq 2}">
                    <c:if test="${status.index % 2 == 0}">
                        <div class="col-md-7">
                            <h2 class="featurette-heading">${article.title} <span class="text-muted"><fmt:formatDate value="${article.createtime}" type="date"/></span></h2>
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
                            <h2 class="featurette-heading">${article.title} <span class="text-muted"><fmt:formatDate value="${article.createtime}" type="date"/></span></h2>
                            <p class="lead">${article.description}</p>
                            <p><a class="btn btn-default" href="${ctx}/blog/show?id=${article.arcid}" role="button">View details &raquo;</a></p>
                        </div>
                    </c:if>
                </c:if>
                <c:if test="${article.property eq 1}">
                    <div class="featurette-text">
                        <h2 class="featurette-heading">${article.title} <span class="text-muted"><fmt:formatDate value="${article.createtime}" type="date"/></span></h2>
                        <p class="lead">${article.description}</p>
                        <p><a class="btn btn-default" href="${ctx}/blog/show?id=${article.arcid}" role="button">View details &raquo;</a></p>
                    </div>
                </c:if>
            </div>
        </c:forEach>
        <hr class="featurette-divider">
        <c:import url="/include/page_blog.jsp"/>
    </div>
    </form>
</div>
<!-- FOOTER -->
<c:import url="/include/footer.jsp"/>
</body>
</html>
