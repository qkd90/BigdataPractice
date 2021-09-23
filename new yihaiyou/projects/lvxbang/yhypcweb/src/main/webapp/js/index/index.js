var YhyIndex = {
	init: function() {
		YhyIndex.initJsp();
		//YhyIndex.submitForm();
	},

	initJsp: function() {
		$.each($("#sailList").find("li"), function(i, li){
			$(li).click(function() {
				$("#sailboat-scenic-id").val($(li).attr("data-id"));
				$("#sailboat-scenic-name").html($(li).attr("data-name"));
			});
		});
		$.each($("#sailTypeList").find("li"), function(i, li){
			$(li).click(function() {
				$("#sailboat-type-id").val($(li).attr("data-id"));
				$("#sailboat-type-name").html($(li).attr("data-name"));
			});
		});

		$.each($("#lineList").find("li"), function(i, li){
			$(li).click(function() {
				$("#ferry-number").val($(li).attr("data-number"));
				$("#ferry-name-span").html($(li).attr("data-departPort") + "-" + $(li).attr("data-arrivePort"));
			});
		});

		$.each($("#cruiseBrandList").find("li"), function(i, li){
			$(li).click(function() {
				$("#search-cruiseship-brand").val($(li).attr("data-id"));
				$("#cruiseship-brand-name").html($(li).attr("data-name"));
			});
		});

		$.each($("#cruiseList").find("li"), function(i, li){
			$(li).click(function() {
				$("#search-cruiseship-line").val($(li).attr("data-id"));
				$("#cruiseship-line-name").html($(li).attr("data-name"));
			});
		});

		//设置初始化日期
		var date = new Date();
		$("#ferry-start-date").val($.addDate(date, 0));
		$("#cruiseship-startDate").val($.addDate(date, 0));
		$("#startDate").val($.addDate(date, 0));
		$("#endDate").val($.addDate(date, 1));

		$("#hotel-keyword").change(function() {
			if (!$("#hotel-keyword").val()) {
				$("#hid-hotel-keyword").val(encodeURI("鼓浪屿"));
			} else {
				$("#hid-hotel-keyword").val(encodeURI($("#hotel-keyword").val()));
			}
		});


		$.each($(".hotPlace").children(), function(i, span) {
			$(span).click(function() {
				$("#form-ipt-scenic").val($(span).html());
				$('#form-index-scenic').submit();
			});
		});
	},

	onFoucsEndTime: function() {
		var startTime = $("#startDate").val();
		if (startTime.length <= 0) {
			return;
		}
		var date = moment(startTime);
		$("#endDate").val($.addDate(date, 1));
	},

	submitForm: function() {
		$('#goHotelListBtn').on('click', function(event) {
			var startDate = $('#startDate').val();
			var endDate = $('#endDate').val();
			var searchWord = $('#searchWord').val();
			var url = '/yhypc/hotel/list.jhtml';
			url += "?startDate=" + startDate;
			url += "&endDate=" + endDate;
			url += "&searchWord=" + encodeURI(encodeURI(searchWord));
			console.log(encodeURI(encodeURI(searchWord)));
			window.location.href = url;
		});
	}
}


$(window).ready(function(){
	advertBox();
	entrance();
	lineList('#lineList','#line');//航线列表
	lineList('#cruiseList','#cruiseLine');//邮轮航线列表
	lineList('#cruiseBrandList','#cruisBrand');//邮轮品牌列表
	lineList('#outtimeList','#outtime')//邮轮出发日期列表
	lineList('#sailList','#sail');//帆船登艇地点列表
	lineList('#sailTypeList','#sailType');//游艇帆船类型列表
	fontLimit('.travelNote .maxpic .word',60);//游记攻略字数限制
	fontLimit('.travelNote .minpic .word',52);
	pro_position('.seasonPopular','seasonPopular');
	pro_position('.hotScenic','hotScenic');
	pro_position('.hotelHomestay','hotelHomestay');
	pro_position('.saillingboat','saillingboat');
	pro_position('.cruiseboat','cruiseboat');
	pro_position('.travelNote','travelNote');
	scroll_position();
	adCenter();

	YhyIndex.init();
})

function advertBox() {
	var bleft = $('.selectBar').position().left;
		$('.outadvertBox').css({'left':bleft});
	window.onresize=function(){
		var w_scrolltop = $('body').scrollTop();
		var bleft = $('.selectBar').position().left;
		var nav_left = $('.selectBar').position().left - $('.pro_position').width();
		$('.outadvertBox').css({'left':bleft});
		scroll_position(nav_left);
		if(w_scrolltop > 541){
			$('.pro_position').css({'position':'fixed','left':nav_left})
		}
	}
}

//搜索入口切换
function entrance(){
	var li = $('.entrancelist ul li');
		li.click(function(){
			var num = $(this).index();
			var thisContain = $('.entrancecontian').eq(num);
			$(this).siblings().removeClass('en_active');
			$(this).addClass('en_active');
			$('.entrancecontian').hide();
			thisContain.show();
		})
}

//航线列表
function lineList(list,btn){
	var listBtn = $(btn);
	var anywhere = $('.hotelIndex');
		listBtn.click(function(event){
			event.stopPropagation();
			$('.linelist').hide();
			$(list).slideDown(200);
		});
		anywhere.click(function(){
			$(list).slideUp(100);
		})
}

//字数限制
function fontLimit(tag,num){
	var textList = $(tag);
		textList.each(function(){
			var text = $.trim($(this).text());
			if(text.length > num){
				$(this).html(text.substring(0,num) + '...');
			}
		})
}

//定位跳转
function pro_position(taget,id){
	//var toHere = $(taget).position().top;
	var pos_li = $('.pro_position ul li');
		pos_li.click(function(){
			$(this).addClass('pos_now').siblings().removeClass('pos_now');
			if($(this).attr('id') == id){
				$('body,html').animate({scrollTop:$(taget).offset().top-120},300);
			}
		});
}

function scroll_position(nav_left){
	var navigation = $('.pro_position');
	var nav_left = $('.selectBar').position().left - navigation.width();
	$(window).scroll(function(){
		var w_scrolltop = $(document).scrollTop();
		var navTop = $('.sectionTitle').eq(0).offset().top-120;
		var pos_li = $('.pro_position ul li');
		var hotScenic = $('.sectionTitle').eq(1).offset().top-120;
		var hotelHomestay = $('.sectionTitle').eq(2).offset().top-120;
		var saillingboat = $('.sectionTitle').eq(3).offset().top-120;
		var cruiseboat = $('.sectionTitle').eq(4).offset().top-120;
		var travelNote = $('.sectionTitle').eq(5).offset().top-120;
			if(w_scrolltop > 541){
				navigation.css({'position':'fixed','left':nav_left})
			}else{
				navigation.css({'position':'absolute','left':'-78px'})
			}
		if(w_scrolltop > navTop && w_scrolltop < hotScenic){
			pos_li.removeClass('pos_now');
			pos_li.eq(0).addClass('pos_now');
		}else if(w_scrolltop > hotScenic && w_scrolltop < hotelHomestay){
			pos_li.removeClass('pos_now');
			pos_li.eq(1).addClass('pos_now');
		}else if(w_scrolltop > hotelHomestay && w_scrolltop < saillingboat){
			pos_li.removeClass('pos_now');
			pos_li.eq(2).addClass('pos_now');
		}else if(w_scrolltop > saillingboat && w_scrolltop < cruiseboat){
			pos_li.removeClass('pos_now');
			pos_li.eq(3).addClass('pos_now');
		}else if(w_scrolltop > cruiseboat && w_scrolltop < travelNote){
			pos_li.removeClass('pos_now');
			pos_li.eq(4).addClass('pos_now');
			navigation.css({'left':nav_left,'top':'80px'})
		}else if(w_scrolltop > travelNote){
			pos_li.removeClass('pos_now');
			pos_li.eq(5).addClass('pos_now');
			navigation.css({'left':nav_left,'top':'80px'})
		}
	})
}

function adCenter(){
	var win_width = window.screen.width;
	var pic_width = $('.advert .swiper-slide img').width();
	var marginLeft = -Math.abs(pic_width - win_width)/2;
		$('.advert .swiper-slide img').css({'marginLeft':marginLeft});
}