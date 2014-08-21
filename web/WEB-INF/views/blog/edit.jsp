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
    <meta name="description" content="${article.title}">
    <meta name="author" content="guanj0320@gmail.com">
    <meta name="keywords" content="个人网站 guanjun 管俊 ${article.title}">
    <title>GUANJUN.ME - ${article.title}</title>
    <link href="${ctx}/resources/images/guanjun.me.ico" type=image/x-icon rel="shortcut icon">
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/resources/css/me.css">

    <script src="${ctx}/resources/js/jquery/jquery-1.10.1.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
    <script src="${ctx}/resources/js/convert.js"></script>
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


        .featurette-image-left {
            margin-left: 0;
            margin-right: auto;
        }

        .featurette-text {
            margin-left: 20px;
            margin-right: 20px;
        }

        .btn-div {
            margin-bottom: 50px;
        }

        .me {
            font-weight: 400;
            font-style: italic;
            font-size: 19px;
            color: #1e90ff;
            margin-bottom: 13px;
        }
        .required{ font-size: 13px; color: #ff0000; }

        .comment-input{min-width: 409px; margin: 0; padding: 10px; margin: 5px 0;}

        .comment-divider {
            color: #0088cc;
            margin: 10px 0; /* Space out the Bootstrap <hr> more */
        }

    </style>
    <script>
        $(document).ready(function(){
            //导航条的选中样式
            $('#nav_blog').addClass('active');

            $('#btnBack').click(function() {
                history.go(-1);
            });

            $('#btnPostComment').click(function() {
                var arcid = $('#arcid').val();
                var writer = $('#c_writer').val();
                var email = $('#c_email').val();
                var content = $('#c_content').val();
                $('#c_message').text('');
                if(writer=="") {
                    $('<p style="color:#FF0000"/>').text('Attention! You must enter your name.').appendTo('#c_message');
                    return false;
                }
                if(email == "" || isEmail(email)==false) {
                    $('<p style="color:#FF0000"/>').text('Attention! Please enter a valid email address.').appendTo('#c_message');
                    return false;
                }
                if(content == "") {
                    $('<p style="color:#FF0000"/>').text('Attention! Please enter your comment.').appendTo('#c_message');
                    return false;
                } else {
                    $.ajax( {
                        type : "POST",
                        url : "${ctx}/blog/comment",
                        data : "writer="+writer+"&email="+email+"&content="+content+"&arcid="+arcid,
                        dataType: "text",
                        success : function(msg) {
                            var obj = jQuery.parseJSON(msg);
//                        $._messengerDefaults = {
//                            extraClasses: 'messenger-fixed messenger-theme-block messenger-on-top'
//                        };
//                        Messenger().post({
//                            message: obj.result,
//                            type: "success"
//                        });
                            if(obj.result=="0") {
                                $('<p style="color:#FF0000"/>').text('Attention! Send error!').appendTo('#message');
                            } else {
                                $('<p style="color:#009900"/>').text('Successful! Thanks for your comment.').appendTo('#c_message');
                                $('#c_writer').val("");
                                $('#c_email').val("");
                                $('#c_content').val("");
                                setTimeout(function () {
                                    $('#c_message').text('');
                                }, 3000);


                                var rownum = $('#tbl_comment').find('tr').length + 1;
                                var writer = obj.comment.writer;
                                var email = obj.comment.email;
                                var createtime = (new Date(obj.comment.createtime)).format("yyyy-MM-dd hh:mm:ss");
                                var content = obj.comment.content;
                                var row = '<tr><td style="max-width: 20px"><h4># ' + rownum + 'F</h4></td>';
                                row += '<td style="max-width: 60px"><p>'+writer+'</p><p>'+email+'</p><p>'+createtime+'</p></td>';
                                row += '<td>'+content+'</td></tr>';

                                //获取table最后一行 $("#tab tr:last")
                                //获取table第一行 $("#tab tr").eq(0)
                                //获取table倒数第二行 $("#tab tr").eq(-2)
                                if($('#tbl_comment').find('tr').length==0) {
                                    $('#tbl_comment tbody').append(row);
                                } else {
                                    $("#tbl_comment tr:last").after(row);
                                }
                            }
                        }
                    });

                }
            });
        });
    </script>
</head>
<body>
<div id="wrap">
    <!-- HEAD -->
    <c:import url="/include/head.jsp"/>
    <div class="container">
        <div class="row page-header">
            <div class="col-md-6"><h1>${article.title}</h1></div>
            <div class="col-md-6"><h3 class="text-right"><small>Create Time is </small><span class="text-muted"><fmt:formatDate value="${article.createtime}" type="both"/></span></h3></div>
        </div>
        <div class="row">
            <c:if test="${article.property eq 2}">
            <div class="col-md-5">
                <img class="img-rounded featurette-image-left img-responsive" src="${article.pic}" alt="Generic placeholder image">
            </div>
            <div class="col-md-7">
                <div class="panel panel-default">
                    <div class="panel-heading">Description<span class="pull-right">Tags: ${article.tid}</span></div>
                    <div class="panel-body">${article.description}</div>
                </div>
            </div>
            </c:if>
            <c:if test="${article.property eq 1}">
            <div class="featurette-text">
                <div class="panel panel-default">
                    <div class="panel-heading">Description<span class="pull-right">Tags: ${article.tid}</span></div>
                    <div class="panel-body">${article.description}</div>
                </div>
            </div>
            </c:if>
        </div>
        <hr class="featurette-divider">
        <div class="featurette-text">${article.content}</div>
        <c:if test="${acticle.source ne ''}">
        <div class="featurette-text">来源：${article.source}</div>
        </c:if>
        <p/>
        <%--<div class="bdsharebuttonbox"><a href="#" class="bds_more" data-cmd="more"></a><a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a><a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a href="#" class="bds_fbook" data-cmd="fbook" title="分享到Facebook"></a><a href="#" class="bds_twi" data-cmd="twi" title="分享到Twitter"></a><a href="#" class="bds_linkedin" data-cmd="linkedin" title="分享到linkedin"></a></div>--%>
        <%--<script>window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdMiniList":false,"bdPic":"","bdStyle":"1","bdSize":"24"},"share":{}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];</script>--%>
        <!-- JiaThis Button BEGIN-->
        <div class="jiathis_style_24x24">
            <a class="jiathis_button_tsina"></a>
            <a class="jiathis_button_tqq"></a>
            <a class="jiathis_button_weixin"></a>
            <a class="jiathis_button_qzone"></a>
            <a class="jiathis_button_fb"></a>
            <a class="jiathis_button_twitter"></a>
            <a class="jiathis_button_linkedin"></a>
            <a href="http://www.jiathis.com/share?uid=1929580" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
            <a class="jiathis_counter_style"></a>
        </div>

        <!-- JiaThis Button END -->

        <hr class="featurette-divider">

        <div class="panel panel-default" style="background-color: #eee">
            <div class="panel-body">
            <p class="me">Leave a Reply</p>
            <div id="c_message"></div>
            <form class="form-horizontal" id="commentform" name="commentform">
                <input id="arcid" name="arcid" type="hidden" value="${article.arcid}"/>
                <fieldset>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="c_writer"><span class="required">*</span> Your Name</label>
                        <div class="col-sm-6">
                            <input class="form-control comment-input" id="c_writer" name="writer" type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="c_email"><span class="required">*</span> Email</label>
                        <div class="col-sm-6">
                            <input class="form-control comment-input" id="c_email" name="email" type="text" size="30">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="c_content"><span class="required">*</span> Comment</label>
                        <div class="col-sm-6">
                            <textarea class="form-control comment-input" id="c_content" name="content" cols="40" rows="3" maxlength="400"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-6">
                            <a role="button" id="btnPostComment" class="btn btn-primary btn-lg"> Post Comment </a>
                        </div>
                    </div>

                </fieldset>
            </form>
            </div>
        </div>
        <div class="table-responsive">
            <table id="tbl_comment" class="table table-striped">
                <thead><h2> Comment: </h2></thead>
                <tbody>
                <c:forEach items="${comments}" var="comment" varStatus="status">
                    <tr>
                        <td style="max-width: 20px"><h4># ${status.count}F</h4></td>
                        <td style="max-width: 60px">
                            <p>${comment.writer}</p>
                            <p>${comment.email}</p>
                            <p><fmt:formatDate value="${comment.createtime}" type="both"/></p>
                        </td>
                        <td>${comment.content}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <hr class="featurette-divider">
        <div class="btn-div"><a role="button" id="btnBack" class="btn btn-default btn-lg btn-block"> &larr; Back to Previous page </a></div>
    </div>
</div>
<!-- FOOTER -->

<!-- jiathis share-->
<script type="text/javascript">
    var jiathis_config = {data_track_clickback:'true'};
</script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js?uid=1394419855547132" charset="utf-8"></script>

<c:import url="/include/footer.jsp"/>
</body>
</html>
