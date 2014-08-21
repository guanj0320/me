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
    <title>PICTURE WALL</title>
    <link href="${ctx}/resources/images/guanjun.me.ico" type=image/x-icon rel="shortcut icon">
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap/bootstrap.min.css">
    <!--<link rel="stylesheet" href="${ctx}/resources/css/colorbox.css">-->
    <link rel="stylesheet" href="${ctx}/resources/css/me.css">

    <script src="${ctx}/resources/js/jquery/jquery-1.10.1.min.js"></script>
    <script src="${ctx}/resources/js/bootstrap/bootstrap.min.js"></script>
    <script src="${ctx}/resources/js/jquery/jquery.imagesloaded.js"></script>
    <!--<script src="${ctx}/resources/js/jquery/jquery.colorbox-min.js"></script>-->
    <script src="${ctx}/resources/js/jquery/jquery.wookmark.min.js"></script>


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

        #main {
            margin: 30px 0;
            position: relative;
        }

        /**
         * Grid container
         */
        #tiles {
            list-style-type: none;
            position: relative; /** Needed to ensure items are laid out relative to this container **/
            margin: 0;
            padding: 0;
        }

        /**
         * Grid items
         */
        #tiles li {
            width: 210px;
            background-color: #ffffff;
            border: 1px solid #dedede;
            border-radius: 2px;
            -moz-border-radius: 2px;
            -webkit-border-radius: 2px;
            display: none; /** Hide items initially to avoid a flicker effect **/
            cursor: pointer;
            padding: 4px;
        }

        #tiles li.inactive {
            visibility: hidden;
            opacity: 0;
        }

        #tiles li img {
            display: block;
        }

        /**
         * Grid item text
         */
        #tiles li p {
            color: #666;
            font-size: 13px;
            line-height: 20px;
            text-align: center;
            font-weight: 200;
            margin: 7px 0 2px 7px;
        }

        /**
         * Sort buttons
         */
        #sortbys {
            list-style-type: none;
            text-align: center;
            margin: 0 5% 0 5%;
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
            /*background: #333333;*/
            background: #428BCA;
            color: #ffffff;
        }

    </style>
    <script>
        var aid = '${aid}';
        $(document).ready(function(){
            //导航条的选中样式
            $('#nav_pic').addClass('active');

            //瀑布流
            (function ($) {
                var handler = null,
                        page = 1,
                        isLoading = false,
                        apiURL = '${ctx}/json/pic';

                // Prepare layout options.
                var options = {
                    autoResize: true, // This will auto-update the layout when the browser window is resized.
                    container: $('#tiles'), // Optional, used for some extra CSS styling
                    offset: 2, // Optional, the distance between grid items
                    itemWidth: 210 // Optional, the width of a grid item
                };

                /**
                 * When scrolled all the way to the bottom, add more tiles.
                 */
                function onScroll(event) {
                    // Only check when we're not still waiting for data.
                    if(!isLoading) {
                        // Check if we're within 100 pixels of the bottom edge of the broser window.
                        var closeToBottom = ($(window).scrollTop() + $(window).height() > $(document).height() - 100);
                        if(closeToBottom) {
                            loadData();
                        }
                    }
                };

                /**
                 * Refreshes the layout.
                 */
                function applyLayout() {
//                    $('a', handler).colorbox({
//                        rel: 'lightbox'
//                    });
                    options.container.imagesLoaded(function() {
                        // Create a new layout handler when images have loaded.
                        handler = $('#tiles li');
                        handler.wookmark(options);
                    });
                };

                /**
                 * Loads data from the API.
                 */
                function loadData() {
                    isLoading = true;
                    $('#loaderCircle').show();

                    $.ajax({
                        url: apiURL,
                        dataType: 'json',
                        data: "page="+page+"&aid="+aid, // Page parameter to make sure we load new data
                        success: onLoadData
                    });
                };

                /**
                 * Receives data from the API, creates HTML for images and updates the layout
                 */
                function onLoadData(data) {
                    isLoading = false;
                    $('#loaderCircle').hide();

                    // Increment page index for future calls.
                    page++;

                    // Create HTML for the images.
                    var html = '';
                    var i=0, length=data.length, image;
                    for(; i<length; i++) {
                        image = data[i];
                        html += '<li>';

                        //html += '<a href="'+image.src+'" rel="lightbox">';
                        html += '<a href="${ctx}/wall/show?id='+image.pid +'" target="_blank">';
                        // Image tag (preview in Wookmark are 200px wide, so we calculate the height based on that).
                        html += '<img src="'+image.pre+'" width="200" height="'+Math.round(image.height/image.width*200)+'" alt="'+image.width+' * '+image.height+'">';

                        html += '</a>'
                        // Image title.
                        html += '<p>'+image.title+'</p>';

                        html += '</li>';
                    }

                    // Add image HTML to the page.
                    $('#tiles').append(html);

                    // Apply layout.
                    applyLayout();
                };

                // Capture scroll event.
                $(document).bind('scroll', onScroll);

                // Load first data from the API.
                loadData();
            })(jQuery);


            if(aid=="" || aid==null) {
                $('#all').addClass('active');
            } else {
                $('#' + aid).addClass('active');
            }
        });

        function flushPhoto(id) {
            location.href = '${ctx}/wall?aid='+id;
        }
    </script>
</head>
<body>
<div id="wrap">
    <!-- HEAD -->
    <c:import url="/include/head.jsp"/>
    <div class="container">
        <ol id="sortbys">
            <li id="all" onclick="flushPhoto('')">全部</li>
            <c:forEach items="${albums}" var="album">

                    <li id="${album.aid}" onclick="flushPhoto('${album.aid}')">${album.albumname}</li>

            </c:forEach>
        </ol>
        <br/>
        <div id="main" role="main">

            <ul id="tiles">
                <!-- These is where we place content loaded from the Wookmark API -->
            </ul>

            <div id="loader">
                <div id="loaderCircle"></div>
            </div>

        </div>
    </div>

</div>
<!-- FOOTER -->
<c:import url="/include/footer.jsp"/>
</body>
</html>
