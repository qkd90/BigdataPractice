
$(document).ready(function(){
	 titleList();
	title_secList('.secnav_list ul li','.roomset','HSsec_active'); //头部二级目录

	bottomBorder('.messageList_header table tr'); //底部加一个border
	bottomBorder('.financeList table tr');
	bottomBorder('.statementList table tr');
	remind('.roomBaseMess .introForm .form-control','.remind_service');
	remind('.roomBaseMess .policyForm .form-control','.remind_policy');
	remind('.roomBaseMess .introForm .form-control','.remind_describ');
	remind('.sailMine .introForm .Tips','.remind_Tips');
	remind('.sailMine .introForm .scenicIntro','.remind_scenicIntro');
	remind('.roomType .beknowForm .form-control','.remind_beKnow');
	browserjudje ();
	cludeTable();
	successSubmit('#mineSub','successSub','提交成功');//我的产品提交
	successSubmit('#typeSub','successSub','提交成功');//票型设置提交
	successSubmit('#statementSub','successSub','已确认');//财务结算审核
	operation('#delete','containBody','是否删除，删除后将无法还原。');
	operation('#delSale','containBody','是否下架，下架后将停止销售。');
	operation('#revoke','containBody','是否撤销审核');
	operation('#refuse','orderDetailBody','是否删除');
	refuse('shadow');//拒绝理由
	withdraw('shadow');//提现金额
	secFun();
	x();

    //房态
    //stateIn('.state_in','.ckeakin');
    //stateIn('.state_out','.cheakout');
    //stateIn('.state_mess','.cheakmess');
    //closeCheakIn('.cheakclose','.ckeakin');
    //closeCheakIn('.cheakclose','.cheakout');
    //closeCheakIn('.cheakclose','.cheakmess');
	var timer1 = setInterval(function(){
		viewBox(timer1);
	},500)
});

//取消遮罩
function shadowHide() {
    $('.shadow').remove()
}
//遮罩
function shadowBox() {
    var wWidth = $(window).width(),
        wHeight = $(window).height(),
        shadow = $('<div></div>');
    $('body').append(shadow);
    shadow.css({'width':wWidth,'height':wHeight});
    shadow.addClass('shadow');
}

//弹出目标区域
function stateIn(tag,effect) {
    var wWidth = $(window).width(),
        wHeight = $(window).height(),
        tWidth = $(effect).width(),
        tHeight = $(effect).height(),
        left = (wWidth - tWidth)/2,
        topr = (wHeight - tHeight)/2;
    $(effect).css({'left':left,'top':topr})
    $(tag).click(function(){
		shadowBox();
        $(effect).fadeIn();
    })
}

//弹出目标区域(遮罩)
function stateIn_shade(tag,effect) {
	var wWidth = $(window).width(),
		wHeight = $(window).height(),
		tWidth = $(effect).width(),
		tHeight = $(effect).height(),
		left = (wWidth - tWidth)/2,
		topr = (wHeight - tHeight)/2;
	$(effect).css({'left':left,'top':topr});
	$(effect).fadeIn();
	$(tag).click(function(){
		shadowHide();
		$(effect).fadeOut();
	})
}

//关闭目标区域
function closeCheakIn(tag,effect){
    var tag = $(tag);
    tag.click(function(){
        $(effect).fadeOut();
        shadowHide()
    })
}


//头部列表切换
 function titleList(){
	 var Li = $('.header_list ul li');
	 var classHomestay = ["homestayIndex", "homestayOrder","homestayFinance","homestayRoomState","homestayComment","homestayTenant"];
	 var classSailboat = ["sailIndex", "sailOrder","sailFinance","sailCheck","sailComment","sailTenant"];
	 	$.each(classHomestay, function(id, element){
			if($('body').hasClass(element)){
				 Li.removeClass('tagHeaderList');
				 Li.eq(id).addClass('tagHeaderList');
			}
		});
	 	$.each(classSailboat, function(id, element){
		 	if($('body').hasClass(element)){
				 Li.removeClass('tagHeaderList');
				 Li.eq(id).addClass('tagHeaderList');
			 }
	 	})
 }
//头部二级列表切换
function title_secList(tag,tagB,pattern){
	var Li = $(tag);
		Li.click(function(){
            window.location.href = $(this).attr('data-href');
		})
}
//底部加边框
function bottomBorder(tag){
	var lastchild = $(tag);
		lastchild.css({'border-bottom':'1px solid #ddd'})
}

//文本框输入提示
function remind(tag,box){
	var remind = $(tag);
		remind.focus(function(){
			$(box).show();
		})
		remind.blur(function(){
			$(box).hide();
		})
}
//是否含早选择
function cludeBreak(tag,pattern){
	var span = $(tag);
		span.click(function(){
			span.removeClass(pattern);
			$(this).addClass(pattern);
	});
}

//单选
function doubleChose (tag,pattern) {
	$(tag).click(function(){
		$(tag).removeClass(pattern);
		$(this).addClass(pattern);
	})
}

//是否包含表格
function cludeTable() {
	//var blank = $('<div></div>');
	//blank.addClass('blank');
	if($('body').hasClass('includeTable')){
		$('.footer').addClass('fixedBottom');
		//$('body').append(blank);
	}else{

	}
}

//显示屏可视区域判断
function viewBox(timer) {
	//var Vwindow = $(window).height();
	//var tableHeight = $('.roomset').height();
	//if(Vwindow - 240 < tableHeight){
	//	$('.footer').removeClass('fixedBottom');
	//	clearInterval(timer);
	//} else {
    //
	//}
	var Vwindow = $(window).height();
	var tableHeight = $('.roomset').height() || $('.roomType').height();
	if(Vwindow - 225 < tableHeight){
		$('.footer').removeClass('fixedBottom');
		clearInterval(timer);
	} else {

	}
}

function secFun(){
	$(document).ajaxStop(function(){
		//var Vwindow = $(window).height();
		//var tableHeight = $('.roomset').height();
		//if(Vwindow - 240 < tableHeight){
		//	$('.footer').removeClass('fixedBottom');
		//} else {
		//	$('.footer').addClass('fixedBottom');
		//}

		var Vwindow = window.screen.availHeight;
		var tableHeight = $('.roomset').height() || $('.roomType').height();
		if(Vwindow - 225 < tableHeight){
			$('.footer').removeClass('fixedBottom');
		} else {
			$('.footer').addClass('fixedBottom');
		}
	})
}

//提交成功弹出框
function successSubmit(tag,pattern,contain) {
	$(tag).click(function(){
		$.messager.show({
			msg:contain,
			iconCls:pattern,
			timeout:1000,
		})
	})
}

//提示弹出框
function operation(tag,pattern,contain) {
	$(tag).click(function(){
		$.messager.show({
			msg:contain,
			iconCls:pattern,
			btns: [{
				btnText: '确定',
				btnCls: 'btn-success'
			}
			]
		})
	})
}

//遮罩
function shadow(pattern) {
	var dWidth = $(window).width(),
		dHeight = $(window).height(),
		shadowBox = $('<div></div>');
	$('body').append(shadowBox);
	shadowBox.addClass(pattern);
	shadowBox.css({'width':dWidth,'height':dHeight})
}

//拒绝理由
function refuse(pattern){
	$('#refuseOrder').click(function(){
		shadow(pattern);
		$('.refuseReason').fadeIn();
	});
	$('.closeBtn').click(function(){
		$("#operationDesc").val("");
		$("#sel-cancel-reason").val("");
		$('.refuseReason').hide();
		$('.shadow').hide();
	})
}

//资金流水申请提现
function withdraw(pattern) {
	var left = ($(window).width() - $('.countbox').width())/2;
	var top = ($(window).height() - $('.countbox').height())/2;
	$('#withdraw').click(function(){
		shadow(pattern);
		$("#money").val("");
		$('.countbox').show();
		$('.countbox').css({'left':left,'top':top});
	})
	$('#closeacount').click(function(){
		$("#money").val("");
		$('.shadow').hide();
		$('.countbox').hide();
	})
}

//房态入住信息滚动判断
function tourNum() {
	var checkbox = $('.homestayRoomState .checkmess').height();
	if(checkbox > 300){
		$('.homestayRoomState .checkbody').css({'overflow':'auto'})
	}else {
		$('.homestayRoomState .checkbody').css({'overflow':'inherit'})
	}
	console.info($('.homestayRoomState .checkmess').height());
}

function x() {
	if($('.checkmess').is(":visible")){
		alert(123);
		tourNum();
	}
}

function browserjudje () {
	var browser = window.navigator;
	if(browser.userAgent.indexOf("MSIE") >= 0){
			$('.roomBaseMess .photo .col-md-3').css({'width':'194.5px','float':'left'});
			$('.header').css({'backgroundColor':'#222'});
			$('.table tr:nth-child(even)').css({'backgroundColor':'#fff'});       //table奇数行样式
			$('.table tr:nth-child(odd)').css({'backgroundColor':'#f9f9f9'});     //table偶数行样式
		    $('.table tr td:last-child').css({'borderRight':'none'});     //
	}else if(browser.userAgent.indexOf("Firefox") >= 0){
			$('.selectBar .form-group select').css({'width':'130px','padding':'6px 12px','height':'30px'});
	}else{
		$('.table tr:nth-child(odd)').css({'backgroundColor':'#fff'});            //table奇数行样式
		$('.table tr:nth-child(odd)').css({'backgroundColor':'#f9f9f9'});         //table偶数行样式
		$('.table tr td:last-child').css({'borderRight':0});
	}
}


