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
<script>
    $(document).ready(function(){
        $.ajax( {
            type : "GET",
            url : "${ctx}/admin/feedback/getcount",
            data : "",
            dataType: "text",
            success : function(msg) {
                var obj = jQuery.parseJSON(msg);
                $("#mymsg").append('<span class="badge" style="background-color: #ff0000">'+obj.count+'</span>');
            }
        });
    });


    function logout() {
        if( confirm("Are you sure to exit?") ) {
            window.location.href="${ctx}/admin/logout";
        }
    }
    window.onunload = function() {
        if(self.screenTop>9000){
            window.alert("Thanks for Using this System, Bye.");
        }
    }

</script>
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
                    <a class="navbar-brand" href="${ctx}/admin/home"><p class="text-primary">GUANJUN.ME-Admin</p></a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li id="nav_home"><a href="${ctx}/admin/home"><span class="glyphicon glyphicon-home"></span> HOME</a></li>
                        <li id="nav_management" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">MANAGEMENT <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li id="nav_2_publishblog"><a href="${ctx}/admin/article/list">Publish BLOG</a></li>
                                <li id="nav_2_replyfeedback"><a href="${ctx}/admin/feedback/list">Reply Feedback</a></li>
                                <li id="nav_2_bannermanagement"><a href="${ctx}/admin/banner/list">Banner Management</a></li>
                                <li class="divider"></li>
                                <li class="dropdown-header">Settings</li>
                                <li id="nav_2_columnssetting"><a href="${ctx}/admin/column/list">BLOG Columns Setting</a></li>
                                <li id="nav_2_tagssetting"><a href="${ctx}/admin/tag/list">BLOG Tags Setting</a></li>
                            </ul>
                        </li>
                        <li id="nav_pic" class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">PHOTO MANAGEMENT <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li id="nav_photo"><a href="${ctx}/admin/photo/list">Photo</a></li>
                                <li id="nav_album"><a href="${ctx}/admin/album/list">Album</a></li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li id="nav_msg"><a id="mymsg" href="${ctx}/admin/feedback/list" target="_self" >MESSAGES</a></li>
                        <li id="nav_monitor"><a id="monitor" href="${ctx}/druid" target="_blank" >MONITOR</a></li>
                        <li id="nav_logout"><a id="logout" href="javascript:logout();" target="_self">LOGOUT</a></li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>
    </div>
</div>