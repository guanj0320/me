<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
    /* CUSTOMIZE THE NAVBAR
-------------------------------------------------- */

    /* Special class on .container surrounding .navbar, used for positioning it into place. */
    .navbar-wrapper {
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        z-index: 20;
    }

    /* Flip around the padding for proper display in narrow viewports */
    .navbar-wrapper .container {
        padding-left: 0;
        padding-right: 0;
    }
    .navbar-wrapper .navbar {
        padding-left: 15px;
        padding-right: 0px;
    }
    .navbar-wrapper .navbar-right {
        padding-left: 0px;
        padding-right: 25px;
    }
</style>
<div class="navbar-wrapper">
    <div class="container">
        <!-- Fixed navbar
        <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        -->
        <div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse" type="button">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="${ctx}/"><p class="text-primary">GUANJUN.ME</p></a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li id="nav_home"><a href="${ctx}/"><span class="glyphicon glyphicon-home"></span> HOME</a></li>
                        <li id="nav_blog"><a href="${ctx}/blog">BLOG</a></li>
                        <li id="nav_pic"><a href="${ctx}/wall">PICTURE WALL</a></li>
                        <li><a href="#contact">WORK WITH ME</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li><a id="login" href="${ctx}/admin/login" target="_blank" ><span class="glyphicon glyphicon-leaf"></span> Login</a></li>
                        <li><a id="aboutus" data-toggle="modal" data-target="#myModal" href="#">About Me</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal" type="button" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">About me</h4>
            </div>
            <div class="modal-body">
                <p>Alex Guan</p>
                <p>is a Production director and project
                    manager living and working
                    in Nanjing Jiangsu, CHN.</p><br/>
                <img src="${ctx}/resources/images/guanjun_aboutme.jpg">
            </div>
            <div class="modal-footer">
                <button class="btn btn-primary" data-dismiss="modal" type="button">Close</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
    function logout() {
        if( confirm("Are you sure to exit?") ) {
            window.location.href="${ctx}/user/logout";
        }
    }
    window.onunload = function() {
        if(self.screenTop>9000){
            window.alert("Thanks for Using this System, Bye.");
        }
    }

</script>