var planId;
var wxutil;
$(function()
{
	//判断上个页面是我的行程还是推荐行程进来的
	if(document.referrer)
	{
		$(".header-wrap").find(".left-btn").attr("href","javascript:history.go(-1);");
	}else
	{
		$(".header-wrap").find(".left-btn").attr("href","/");
	}
	
	$("img.lazy").lazyload({threshold : 300});
	changeIndex();
	planId = $("#planId").val();
	adjustHeight();
	//initMap();
	//initRoute();
	delCookie("_scenicinfo");
	delCookie("_ticket_planId");
	scollFun();
	wxutil = new WxUtil();
	initShare();
	$("body").bind("touchstart", function()
	{
		hideCostDetail();
	});
    $(".posts-btn").click(function() {
        window.location.href = "/shopcart/shopcart/mobileorder.jhtml?id=" + $("#planId").val() + "&proType=line&priceDateId=" + $("#priceDateId").val();
    });
});

function changeIndex()
{
	$(".everyDay-ul>.every-day").each(function()
	{
		var items = $(this).find(".scenic-items-wrap");
		items.find(".item-scenic").each(function()
		{
			$(this).find(".scenic-index").html($(this).index() + 1);
		});
	});
}

var map;
function initMap()
{
	map = new BMap.Map("J_now-map"); 
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
}

function initRoute()
{
	$(".item-signel").each(function()
	{
		_route_init($(this));
	});
}

function showSummary()
{
	$("#J_summary-list").html("");
	
	var data = new Object();
	var dayArr = new Array();
	$(".everyDay-ul>li.every-day").each(function()
	{
		var daytrip = {};
		daytrip.day = $(this).index() + 1;
		var scenics = $(this).find(".scenic-items-wrap");
		var scenicArr = new Array();
		scenics.find("li.item-scenic").each(function()
		{
			var trips = {};
			trips.id = $(this).attr("scenicId");
			trips.name = $(this).attr("sname");
			scenicArr.push(trips);
		});
		daytrip.scenics = scenicArr;
		dayArr.push(daytrip);
	});
	data.days = dayArr;
	
	tripNode = $(template("tpl-summary-scenics", data));
	$("#J_summary-list").append(tripNode);
	
	if ($("#J_plan_summary").hasClass("show"))
	{
		$("#J_plan_summary").css("-webkit-transform", "translate3d(-100%, 0, 0)");
		$("#J_plan_summary").removeClass("show");
		$(".summary-btn").show();
		
	}
	else
	{
		$("#J_plan_summary").css("-webkit-transform", "translate3d(0, 0, 0)");
		$("#J_plan_summary").addClass("show");
		//setTimeout("$('.summary-btn').hide()", 200);
	}
}

function hideSummary()
{
	$("#J_plan_summary").css("-webkit-transform", "translate3d(-100%, 0, 0)");
	$("#J_plan_summary").removeClass("show");
	$(".summary-btn").show();
}

function showScenic(day, index)
{
	hideSummary();
	var dayli = $(".every-day").eq(day-1);
	var scenicli = dayli.find(".item-scenic").eq(index);
	var h = scenicli.find(".point-a").offset().top;
	$("body").animate({scrollTop: h-44}, 200);
}

function showtips()
{
	$.dialog({width : "260px", height : "auto", cnt : $("#tpl-travel-tips").html()});
}

function returnDetail()
{
	$("#__ui-dialog").remove();
}

function delScenicTips(_this)
{
	var myli = $(_this).parents("li.item-signel:first");
	var day = myli.parents("li.every-day:first").index();
	$.confirm({title : "提示", msg : "<p class='tips-p'>确定要删除吗？</p>", initFn : function(){ delScenic(day, myli); }});
}

function delScenic(day, myli)
{
	myli.addClass("fadeOut");
	setTimeout(function()
	{
		initMap();
		myli.remove();
		updateRoute(day);
	}, 200);
}

function updateRoute(index)
{
	var editor = $(".everyDay-ul>li.every-day").eq(index);
	editor.find(".item-signel").each(function()
	{
		$(this).attr("status", "");
		$(this).find(".station-path-summary").attr("price", "0");
		$(this).find(".station-path-summary").attr("numtime", "0");
		_route_init($(this));
	});
	
	var size = editor.find(".item-scenic").size();
	$(".day-scenic-count").html(size);
	var total = parseInt($(".plan-title .plan-total-scenicCount").html());
	$(".plan-title .plan-total-scenicCount").html(total - 1);
	
	var saveIter = 
		window.setInterval(function()
		{ 
			if (saveReady(editor))
			{ 
				window.clearInterval(saveIter);
				_savePlanTrip(index);
			} 
		}, 200);
}

function saveReady(editor)
{
	var isReady = true;
	editor.find("li.item-signel").each(function()
	{
		if(!$(this).find(".traffic-panel").is(":hidden")) 
		{ 
			if ($(this).attr("status") != "ready")
			{
				isReady = false;
			}
		} 
	});
	
	return isReady;
};

function _route_init(endPoint)
{
	endPoint.find(".traffic-panel").hide();
	var startPoint = endPoint.prev();
	if (startPoint.size() > 0)
	{
		var startCom = startPoint.find(".traffic-panel").show();
		startCom.baiduroute({start : {lng : startPoint.attr("lng"), lat : startPoint.attr("lat")} , end : {lng : endPoint.attr("lng"), lat : endPoint.attr("lat")}});
	}
}

function howGo(lng, lat, name, address)
{
	if (isWeiXin())
	{
		wxutil.openLocation(lng, lat, name, address);
	}
	else
	{
		$.tips("您的浏览器不支持此功能,请在微信中打开");
	}
}


function showImg()
{
	var this_src = getEvtTgt().attr("bigsrc");
	var index = getEvtTgt().parents("li.item-signel:first").index();
	var imgArr = [];
	var myul = getEvtTgt().parents(".scenic-items-wrap:first");
	var counts = myul.find("li.item-signel").size();
	for(var i = 0 ; i < counts; i++)
	{
		var img_src = myul.find("li.item-signel").eq(i).find("img").attr("bigsrc");
		imgArr.push(img_src);
	}
	
	wxutil.showImage(this_src, index, imgArr);
}

// 微信分享设置
function initShare()
{
	var map = 
	{
		img : $("#img_url").val()
	};
	wxutil.shareTimeline(map);
}

function showMore(_this)
{
	$(_this).parent().find(".more-func_div").toggleClass("hide");
}

function showDetail()
{
	getEvtTgt().find(".detail").toggleClass("hide");
	getEvtTgt().find(".arr").toggleClass("active");
}

function scollFun()
{
	$(window).scroll(function()
	{
		var bodyTop = $("body").scrollTop();
		
		var everyLi = $(".everyDay-ul").find(".every-day");
		for(var i = 1; i <= everyLi.length; i++)
		{
			var thisbtn = document.getElementById("day-"+ i).offsetTop;
			
			if( (bodyTop-35) > thisbtn)
			{
				if(i == 1)
				{
					$(".summary-btn").html("行程单");
				}else{
					$(".summary-btn").html("第 "+ i +"天");
				}
			}
		}
		
	});
}

function scenicDetail(scenicId)
{
	// 将行程id存入cooike，用于门票预订
	delCookie("_scenicinfo");
	setCookie("_ticket_planId", planId);
	window.location.href = "/scenic/detail/" + scenicId;
}

// 显示花费详情
function showCostDetail(_this)
{
	try
	{
		stopEvent(event);
	}
	catch (e)
	{
		if($.browser.mozilla)
		{
			var $E = function()
			{
				var c = $E.caller; while(c.caller)c=c.caller; return c.arguments[0];
			};
			__defineGetter__("event", $E);
			event.stopPropagation();
		};
	}
	$(_this).find(".cost-detail").show();
}

function hideCostDetail()
{
	$(".cost-detail").hide();
}

function hideTag(className) {
    $("."+className).remove();
}