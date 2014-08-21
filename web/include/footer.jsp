<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <style>
        .footer {
            background-color: #16171b;
            padding-top: 100px;
        }
        .footer > .container {
            height: auto;
            margin: 0 auto;
            padding-left: 0;
            padding-right: 0;
        }
        .footer p.me {
            font-weight: 400;
            font-style: italic;
            font-size: 19px;
            color: white;
            margin-bottom: 13px;
        }
        p.me_more {
            font-size: 13px;
            line-height: 20px;
            color: #cbcbcb;
        }
        p.me_tweet {
            font-weight: 400;
            font-style: italic;
            font-size: 19px;
            color: white;
            margin-bottom: 3px;
            margin-top: 50px;
        }

        #tweet {
            height: 50px;
            font-size: 13px;
            line-height: 20px;
            color: #cbcbcb;
        }
        #tweet a {
            color: #21738a;
        }
        #tweet ul {
            width: auto;
            height: 30px;
        }
        #tweet ul li {
            margin-bottom: 15px;
        }

        a.s1 {
            display: block;
            float: left;
            width: 32px;
            height: 32px;
            background: transparent url(${ctx}/resources/images/weibo_32.png) 0 0 no-repeat;
            margin-right: 7px;
        }
        a.s2 {
            display: block;
            float: left;
            width: 32px;
            height: 32px;
            background: transparent url(${ctx}/resources/images/facebook_32.png) 0 0 no-repeat;
            margin-right: 7px;
        }
        a.s3 {
            display: block;
            float: left;
            width: 32px;
            height: 32px;
            background: transparent url(${ctx}/resources/images/twitter_32.png) 0 0 no-repeat;
            margin-right: 7px;
        }
        a.s4 {
            display: block;
            float: left;
            width: 32px;
            height: 32px;
            background: transparent url(${ctx}/resources/images/gmail_32.png) 0 0 no-repeat;
            margin-right: 7px;
        }
        a.s5 {
            display: block;
            float: left;
            width: 32px;
            height: 32px;
            background: transparent url(${ctx}/resources/images/qq_32.png) 0 0 no-repeat;
            margin-right: 7px;
        }

        /******************************************* CONTACT *************************************************/
        #contact {display: block;font-size:13px; float:right;margin-bottom: 60px;}
        #contact h1 { margin: 10px 0 10px; font-size: 13px; color: #333333; }
        #contact hr { color: inherit; height: 0; margin: 6px 0 6px 0; padding: 0; border: 1px solid #d9d9d9; border-style: none none solid; }
        #contact fieldset { display:block; width: 430px; height: auto}
        /* Form style */
        #contact label { display: inline-block; float: left; height: 26px; line-height: 26px;font-size: 13px;margin-top: 5px; color: #cbcbcb;}
        #contact textarea, select {min-width: 409px; margin: 0; padding: 10px; color: #666; background-color: #2d2e32; border: none; margin: 5px 0;}
        #contact input:focus, textarea:focus, select:focus { background-color: #f6f6f6; color:#333; }
        #contact legend { padding:7px 10px; font-weight:bold; color:#000; border:1px solid #eee; margin-bottom:0 !important; margin-bottom:20px; }

        #contact span.required{ font-size: 13px; color: #ff0000; } /* Select the colour of the * if the field is required. */

        #message { margin: 10px 0; padding: 0; }

        .error_message { display: block; height: 20px; line-height: 20px; color:#ff0000; }

        .loader { padding: 0 10px; }

        #contact #success_page h1, #contact #success_page p { color:#75c825; }

        acronym { border-bottom:1px dotted #ccc; }

        .content_input{min-width: 409px; margin: 0; padding: 10px; color: #666; background-color: #2d2e32; border: none; margin: 5px 0;}

        #validatecode {
            width: 40px;
            height: 24px;

            padding-left: 10px;
            margin-left: -30px;
            color: #666; background-color: #2d2e32; border: none;
        }
        #btnSendMessage {
            margin-top: 10px;
            margin-left: 20px;
        }

        /************************************************************************************************************/
        .footer_down_iPad {
            height: 130px;
            margin-bottom: -10px;
            background-color: #090a0c;
        }
        .footer_down_iPad > .fdi {
            height: 70px;
            margin: 0 auto;
            font-size: 13px;
            color: #cbcbcb;
            padding-top: 60px;
        }
        .fdi p.copy {
            float: left;
            display: block;
        }
        .fdi p.pad {
            float: right;
            display: block;
        }

        @media (min-width: 768px) {
            /* Remove the edge padding needed for mobile */
            .footer > .container {
                padding-left: 0;
                padding-right: 0;
            }
        }
    </style>
<script src="${ctx}/resources/js/datachecks.js"></script>
<script>
    $(document).ready(function(){
        $('#btnSendMessage').click(function() {
            var writer = $('#writer').val();
            var email = $('#email').val();
            var content = $('#content').val();
            var validatecode = $('#validatecode').val();
            $('#message').text('');
            if(writer=="") {
                $('<p style="color:#FF0000"/>').text('Attention! You must enter your name.').appendTo('#message');
                return false;
            }
            if(email == "" || isEmail(email)==false) {
                $('<p style="color:#FF0000"/>').text('Attention! Please enter a valid email address.').appendTo('#message');
                return false;
            }
            if(content == "") {
                $('<p style="color:#FF0000"/>').text('Attention! Please enter your message.').appendTo('#message');
                return false;
            }
            if(validatecode == "") {
                $('<p style="color:#FF0000"/>').text('Attention! Please enter the verification number.').appendTo('#message');
                return false;
            } else {
                $.ajax( {
                    type : "POST",
                    url : "${ctx}/feedback/add",
                    data : "writer="+writer+"&email="+email+"&content="+content+"&validatecode="+validatecode,
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
                        if(obj.result=="-1") {
                            $('<p style="color:#FF0000"/>').text('Attention! The verification number you entered is incorrect.').appendTo('#message');
                        } else if(obj.result=="0") {
                            $('<p style="color:#FF0000"/>').text('Attention! Send error!').appendTo('#message');
                        } else {
                            $('<p style="color:#009900"/>').text('Successful! Thanks for your message.').appendTo('#message');
                            $('#writer').val("");
                            $('#email').val("");
                            $('#content').val("");
                            setTimeout(function(){
                                $('#message').text('');
                            },3000);
                        }


                    }
                });

            }
        });
    });

    function refreshValidateCode(id) {
        $("#" + id).attr("src", "");
        $("#" + id).attr("src", "${ctx}/code");
    }
</script>
<div class="footer">
    <div class="container">
        <div class="col-xs-6 col-md-6">
            <p class="me">That's me</p>
            <div class="row">
                <div class="col-xs-6 col-sm-4 col-md-4">
                    <img src="${ctx}/resources/images/guanjun_profile_pic.png">
                </div>
                <div class="col-xs-12 col-sm-6 col-md-8">
                    <p class="me">Guan jun (Alex)</p>
                    <p class="me_more">is a Production director and project<br /> manager living and working<br />in Nanjing Jiangsu, CHN.</p>

                </div>
            </div>
            <div class="row">
                <div class="col-xs-6 col-sm-4 col-md-4">
                    <p class="me_tweet">Today I said</p>
                    <div id="tweet">
                        <a href="http://weibo.com/alexguan" target="_blank">Weibo by @alexguan</a>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-8">
                    <img src="${ctx}/resources/images/weixincode.png" alt="描述二维码加我吧">
                </div>
            </div>
            <div>
                <a href="http://weibo.com/alexguan" target="_blank" class="s1"></a>
                <a href="http://facebook.com/guanj0320" target="_blank" class="s2"></a>
                <a href="https://twitter.com/#!/guanj0320" target="_blank" class="s3"></a>
                <a href="mailto:guanj0320@gmailcom" class="s4"></a>
                <a href="tencent://message/?uin=7798344&Site=im.qq.com&Menu=yes" class="s5"></a>

            </div> <!-- end social -->

        </div> <!-- end footer left area -->

        <div class="col-xs-6 col-md-6">
            <div id="contact" name="#contact">
                <p class="me">The beautiful contact form</p>
                <div id="message"></div>
                <form id="contactform" name="contactform">
                    <fieldset>
                        <label class="control-label" for="writer"><span class="required">*</span> Your Name</label>
                        <input class="content_input" id="writer" name="writer" type="text">
                        <br />
                        <label class="control-label" for="email"><span class="required">*</span> Email</label>
                        <input class="content_input" id="email" name="email" type="text" size="30">
                        <br />
                        <label class="control-label" for="content"><span class="required">*</span> Message</label>
                        <textarea id="content" name="content" cols="40" rows="3" maxlength="400"></textarea>
                        <br />
                        <div class="row">
                            <div class="col-xs-6">
                                <p class="human"><span class="required">*</span> Are you human?</p>
                                <div class="row">
                                    <div class="col-md-6">
                                        <a href="javascript:refreshValidateCode('codeImage');" title="点击刷新" style="width: 130px; height: 30px;">
                                            <img id="codeImage" width="80px" height="24px" src="/code">
                                        </a>

                                    </div>
                                    <div class="col-md-6"><input id="validatecode" name="validatecode" type="text" size="2" value=""/></div>
                                </div>

                            </div>
                            <div class="col-xs-6">
                                <a role="button" id="btnSendMessage" class="btn btn-primary btn-lg"> Send Message </a>
                            </div>
                        </div>



                    </fieldset>
                </form>
            </div>
        </div> <!-- end footer content -->
    </div>
</div>
<div class="footer_down_iPad">
    <div class="container fdi">
        <p class="copy">&copy; 2014 Guanjun.me -- Code By Myself! -- <a href="http://www.miitbeian.gov.cn/" target="_blank">苏ICP备14022544号</a>
            <!--站长统计www.cnzz.com--><script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1253040057'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s95.cnzz.com/z_stat.php%3Fid%3D1253040057%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script>
        </p>
        <p class="pad">Be a man, and never give up! <a href="#" alt="Back to Top"><span class="glyphicon glyphicon-chevron-up"></span></a></p>
    </div> <!-- end fdi -->
</div>
