$(window).ready(function() {
	var body = document.getElementById("plan_content");
	pullDown('#myyihaiyou','.myyihaiyou');
	pullDown('#customService','.customService');
	titleList();
    personalNav();
	publicFn();
	document.onselectstart=new Function("return false");
});

//头部下拉菜单
function pullDown(btn,tag) {
	$(btn).click(function(event){
		event.stopPropagation();
		$(this).siblings().find('ul').hide();
        $(this).siblings().removeClass('up');
        $(this).siblings().find('ul').removeClass("open").addClass("close");
		if ($(tag).hasClass("open")) {
			$(tag).slideUp(300);
            $(tag).removeClass("open").addClass("close");
            $(this).removeClass("up");
		} else {
            $(tag).slideDown(300);
            $(tag).removeClass("close").addClass("open");
            $(this).addClass("up");
        }
	});
	$('body').click(function(event){
		$(tag).slideUp(300);
        $(tag).removeClass("open").addClass("close");
        $(tag).parent("li.listdown").removeClass("up");
	})
}


//头部列表切换
 function titleList() {
	 var Li = $('.selectBar .nav ul li a');
	 var classtag = ["Index","DIY","hotel","boatTicket","sailBoat","scenic","cruise","travel"];//"food","guide",
	 	$.each(classtag, function(id, element){
			if($('body').hasClass(element)){
				 Li.removeClass('NavList');
				 Li.eq(id).addClass('NavList');
			}
		});
 }

// 个人中心导航切换
function personalNav() {
    var pageCls = $('body').attr('data-page-class');
    $('#user_nav_ul li.' + pageCls).addClass('per_active').siblings().removeClass('per_active');
}

function publicFn(){
	var browser = window.navigator;
	var hotelAddtour = $('.hotelOrder .addtour ul li input');
	if(browser.userAgent.indexOf("Chrom") >= 0){

	}else if( browser.userAgent.indexOf("Firefox") >= 0){

	}else if(browser.userAgent.indexOf("MSIE") >= 0){
		hotelAddtour.css({'width':'165px'});
		if(browser.userAgent.indexOf("MSIE 8.0") >= 0 || browser.userAgent.indexOf("MSIE 9.0") >= 0){
			$('body').addClass('ie8')
			$('.popup_shadow .namespan').show();      //登录注册，支付密码设置placehorder（IE8，IE9）始
			$('.popup_shadow .passwordspan').show();
			$('#register_form .username').show();
			$('#register_form .phoneNum').show();
			$('#register_form .apassword').show();
			$('#register_form .bpassword').show();
			$('#register_form .checkcode').show();
			$('.with_body .nowP').show();
			$('.with_body .newPa').show();
			$('.with_body .newPb').show();
			$('.forget-namespan').show();
			$('.forget-imgcode').show();
			$('#loginComputer #account,#loginComputer #pwd,#register_form .account,#register_form .mobile,#register_form .pwd_a,#register_form .pwd_b,#register_form .checkinput,.with_body .pri_now,.with_body .pri_newa,.with_body .pri_newb,#passwordForget .username-input,#passwordForget .forget-imgcode-input').blur(function(){
				if($(this).val() == ''){
					$(this).prev().show();
				}else{
					$(this).prev().hide();
				}
			})
			$('#loginComputer #account,#loginComputer #pwd,#register_form .account,#register_form .mobile,#register_form .pwd_a,#register_form .pwd_b,#register_form .checkinput,.with_body .pri_now,.with_body .pri_newa,.with_body .pri_newb,#passwordForget .username-input,#passwordForget .forget-imgcode-input').focus(function(){
				$(this).prev().hide();
			})    //登录注册，支付密码设置placehorder（IE8，IE9）结束
		}
		if(browser.userAgent.indexOf("MSIE 8.0") >= 0){
				//$('body').addClass('ie89');
		}
	}
}

//验证码刷新
function imgClick(eleId){
	document.getElementById(eleId).src="/image/checkNum.jsp?"+ new Date().getTime();
}

/**
 * 获取cookie
 * @param name cookie名称
 * @return {*}
 */
function getCookie(name)
{
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

	if(arr=document.cookie.match(reg))

		return decodeURIComponent(arr[2].replace(/\"/g, ""));
	else
		return null;
}

function setCookie(name, value)
{
	// 默认设置30天
	var days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + days * 24 * 3600000);
	document.cookie = name + "="+ encodeURIComponent(value) + ";expires=" + exp.toGMTString() + ";path=/" + ";";
	//+
	//	"domain=.lvxbang.com";
}

function getUnCodedCookie(name)
{
	var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

	if(arr=document.cookie.match(reg))

		return arr[2];
	else
		return null;
}

function setUnCodedCookie(name, value)
{
	// 默认设置30天
	var days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + days * 24 * 3600000);
	document.cookie = name + "="+ value + ";expires=" + exp.toGMTString() + ";path=/"  + ";";
	//+
	//	"domain=.lvxbang.com";
}

function delCookie(name)
{
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if(cval!=null)
		document.cookie= name + "=" + cval + ";expires=" + exp.toGMTString() + ";path=/" + ";";
	//+
	//		"domain=.lvxbang.com";
}

function setLocalStorage(name, value) {
	window.localStorage.setItem(name, value);
}

function getLocalStorage(name) {
	return window.localStorage.getItem(name);
}

function delLocalStorage(name) {
	window.localStorage.removeItem(name);
}

/**
 * 验证是否为空
 * @param strDate
 * @return
 */
function isNull(str) {
	if (null == str || "" == str || str == undefined || "null" == str) {
		return true;
	}

	if (typeof str == "string")
	{
		str = str.replace(/(^\s*)|(\s*$)/g, "");
		return str.length == 0;
	}

	return false;
}