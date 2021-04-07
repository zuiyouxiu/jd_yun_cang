# ！！！！

# 应急物资物流管理测试系统

# 四川广元7.0级地震 






1月26日，京东物流由北京、上海、广州、青岛发出的口罩、防护服、消毒液、净化器、医用药品等救援物资通过铁路运输陆续到达武昌、汉口站，
截至目前，已累计通过铁路运输救援物资近40吨，预计未来仍有每日20吨的救援物资通过铁路运抵武汉。

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html class="ext-strict" xmlns="http://www.w3.org/1999/xhtml dir="ltr" lang="en-US"">
<head>
<meta name="viewport" content="width=device-width; initial-scale=1.0" />

<link rel="icon" href="/img/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />
<!-- new -->
<link rel="stylesheet" href="/css/login/superfish.css" type="text/css" media="all" />
<link rel="stylesheet" href="/css/login/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="/css/login/cream-red.css" type="text/css" media="all" />
<!--JavaScript-->
<script type=text/javascript src="/js/jquery.min.js"></script>
<script src="/js/jqgrid/jquery-ui-1.8.2.custom.min.js"></script>

<script type=text/javascript src="/js/login/superfish.js"></script>
<script type=text/javascript src="/js/login/slides.min.jquery.js"></script>
<script type=text/javascript src='/js/login/jquery.cookie.js'></script>
<script type=text/javascript src="/js/login/scripts.js"></script>
<script type=text/javascript src="/js/login/security.js"></script>
<!-- new END-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>  
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>  
<META HTTP-EQUIV="Expires" CONTENT="0"/>  

<title>配送子系统</title>
 <script type="text/javascript">
   //点击换验证码
	function updateImg(obj) { 
		obj.src = "/Kaptcha.jpg?" + Math.floor(Math.random() * 100); 
	} 
	
	function handle(obj) {
 		obj.parentNode.parentNode.removeChild(obj.parentNode);
	}
	
 </script>
<style>
#loading{
 	height: 100%;
    position: absolute;
    top: 0;
    width: 100%;
    z-index: 999999999;
    background:url("/img/loader.gif") no-repeat center center;
}
</style>
</head>
<body >
<div class="background-wrapper">
		
	<!-- BEGIN .content-wrapper -->
	<div class="content-wrapper">

		<!-- BEGIN .content-body -->
		<div class="content-body">

			<!-- BEGIN #navigation -->
			<div id="navigation" class="clearfix">
				
				<!-- BEGIN #main-menu -->
				<ul id="main-menu" class="fl clearfix">
					<li class="current-menu-item">
                        <img src="/img/logo_head.png" alt="" style="width: 283px; height: 51px; margin: 14px 0px 7px 0px;" />
					</li>
				</ul>

				<div class="corner-left"></div>
				<div class="corner-right"></div>
				
			<!-- END #navigation -->
			</div>
			
			<!-- BEGIN #slides -->
			<div id="slides" class="slide-loader" >
				
				<!-- BEGIN .slides_container -->
				<div class="slides_container">
					<div class="slide" id='p1'>
						<img id='pic1' src="/img/loginImg/pic2.jpg"  alt=""/>
					</div>

			
				<!-- END .slides_container -->
				</div>
				<!-- BEGIN .booknow -->
				<div class="booknow">
					<!--  -->
					<form class="booking-form" name="login" action="/system/login/login.action" method="post" target="_top"/>
						<div class="clearfix">
							<div id="errorMessage" style="min-height:15px;">
							<font color="red" size="2">验证码错误</font>
							</div>
						</div>
						<div class="clearfix">
							<div class="input-half-left" style="text-align:right;"> 登录名：</div>
							<input type="text" id="username" name="username" value="" class="input-half-right" />
							
						</div>
						<div class="clearfix">
							<div class="input-half-left" style="text-align:right;">密 码：</div>
							<input type="password" id="password" name="key" value="" class="input-half-right" />
						</div>
						<input type="hidden" id="language" name="language" value="zh_CN"/>
							<div class="clearfix">
								<div class="input-half-left"> 验证码:</div>
								<input name="kaptcha" id="kaptcha" class="input-half-right" style="width:50px;vertical-align:top;" size="18" " type="text">
								&nbsp;&nbsp;<img src="/Kaptcha.jpg" style="width:100px" alt="看不清楚,请点击图片刷新" title="看不清楚,请点击图片刷新" onclick="updateImg(this)" >
							</div>
								
						<input id="submitBtn" class="bookbutton" type="submit" value="登录" />
					
					</form>
					
					<div class="corner-left"></div>
				<!-- END .booknow -->
				</div>
		
			<!-- END #slides -->
			</div>

			<!-- BEGIN #footer-bottom -->
			<div id="footer-bottom" class="clearfix">
				
			<!-- END #footer-bottom -->
			</div>
		
		<!-- END .content-body -->
		</div>
		
	<!-- END .content-wrapper -->
	</div>
		
	<!-- END .background-wrapper -->
	</div>

</body>
</html>
<SCRIPT type="text/javascript">
	var uMsg = "登录名";
	var pMsg = "密 码";
	var kMsg = "验证码";
	var sMsg = "必填";
	$(function(){
		$("#language").attr("value","zh_CN");
	});
$(function(){$("form").submit(function(){var b,c,d,e,a="";return $("#errorMessage font").text(a),b=$.trim($("#username").val()),(null==b||""==b)&&(a=uMsg),c=$.trim($("#password").val()),(null==c||""==c)&&(a=""==a?pMsg:a+","+pMsg),d=$("#kaptcha"),1==d.length&&(e=$.trim(d.val()),(null==e||""==e)&&(a=""==a?kMsg:a+","+kMsg)),""!=a?($("#errorMessage font").text(a+" "+sMsg),!1):(subLoading(),$.ajax({type:"post",async:!1,url:"/system/login/loginPass.action",data:{key:parseInt(1e3*Math.random())},success:function(a){var c,d,e,f,g,h,b=a;return null!=b?(c=b.modulus,null==c&&localeChange(),d=b.exponent,e=new RSAUtils.getKeyPair(d,"",c),f=$("#password").val().split("").reverse().join(""),g=RSAUtils.encryptedString(e,f),$("form").append('<input type="hidden" id="subPwd" name="password" />'),h=(new Date).getTime(),$("#subPwd").val(b.key+h+g),$("#password").val(h),!0):(localeChange(),void 0)}}),!0)})});
		
		function subLoading(){
		if($("#loading").length==0){
			$("body").append("<div id='loading'></div>");
			$(".background-wrapper").css("opacity",0.3);
		}
		return true;
	}
	function localeChange(){
		subLoading();
        window.location.href = "/system/login/view.action?language=" + $("#language").val();
	}
 
</SCRIPT>
