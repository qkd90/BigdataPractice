var recommendPage = 1;
var myPage = 1;
$(function() 
{
	if (hasLogin())
	{
       	getMsgCount();
   	}
	
	initHash(closeSearch);
    //new NoClickDelay(document.getElementById('J_foot_menu'));
	
	//取得了用户的经纬度信息得到cityId以后
	judgeReload()

	/* 选择下拉事件 */
    adjustHeight();
    $(".list-title").find(".select-block").click(_navSelect);
	$(".bottom").click(_closenavSelect);
	$(".every-content").find("li").click(didSelect);
	//二级筛选的一级目录
	$(".left-menu-select").click(firstSelect);
	
    /* 加载行程 */
	if($("#page_type").val() == 2)
	{
		myplan();
	}else{
		firstList();
	}

    /* 搜索页面相关 */
    $("#J_search").find(".tips-wrap").find("a").click(function()
    {
    	/* console.log($(this).find("a").html()); */
    	$("#searchCon").val($(this).html());
    	searchPlan();
    });
    
    scrollFun();
});

function judgeReload()
{
	var recommendWrap = $(".recommend-wrap");
	
	var cityId = $("#cityId").val();
	if(cityId != ""){
		$(".right-menu").find("li").removeClass("active");
		$(".right-menu").addClass("hide");
		$(".left-menu-select").removeClass("on");
		
		$(".right-menu").find("li").each(function()
		{
			if($(this).attr("data-id") == $("#cityId").val()){
				$(this).addClass("active");
				$(this).parents(".left-menu-select").addClass("on");
				$(this).parents(".left-menu-select").find(".right-menu").removeClass("hide");
				$(".select-place").find("b").html($(this).html());
			}
		});
	}else{
		$(".select-place").find("b").html("目的地");
	}
	if(!recommendWrap.find(".right-menu").find("li").hasClass("active")){
        recommendWrap.find(".left-menu").find("li").eq(1).find(".none").addClass("active");
	}
	
	if($("input[name='dayL']").val() != "")
	{
		$(".select-content-wrap").find("li").removeClass("active");
		var dayL = $("input[name='dayL']").val();
		switch (dayL) {
		case "1":{
			$(".select-content-wrap").find("li").eq(1).addClass("active");
			$(".select-content").find("b").html("1-3天");
		}
			break;
		case "4":{
			$(".select-content-wrap").find("li").eq(2).addClass("active");
			$(".select-content").find("b").html("4-7天");
		}
			break;
		case "8":{
			$(".select-content-wrap").find("li").eq(3).addClass("active");
			$(".select-content").find("b").html("8-14天");
		}
			break;
		case "14":{
			$(".select-content-wrap").find("li").eq(4).addClass("active");
			$(".select-content").find("b").html("14天以上");
		}
			break;
		default:{
			$(".select-content-wrap").find("li").eq(0).addClass("active");
			$(".select-content").find("b").html("不限");
		}
			break;
		}
	}
	
	if($("input[name='planCostL']").val() != "")
	{
		$(".select-spend-wrap").find("li").removeClass("active");
		var planCostL = $("input[name='planCostL']").val();
		switch (planCostL) {
		case "0":{
			$(".select-spend-wrap").find("li").eq(1).addClass("active");
			$(".select-sortway").find("b").html("0~1000");
		}
			break;
		case "1000":{
			$(".select-spend-wrap").find("li").eq(2).addClass("active");
			$(".select-sortway").find("b").html("1000~3000");
		}
			break;
		case "3000":{
			$(".select-spend-wrap").find("li").eq(3).addClass("active");
			$(".select-sortway").find("b").html("3000~5000");
		}
			break;
		case "5000":{
			$(".select-spend-wrap").find("li").eq(4).addClass("active");
			$(".select-sortway").find("b").html("5000~不限");
		}
			break;
		default:{
			$(".select-spend-wrap").find("li").eq(0).addClass("active");
			$(".select-sortway").find("b").html("不限");
		}
			break;
		}
	}

	if($("input[name='orderColumn']").val() != ""){
		$(".select-brain-wrap").find("li").removeClass("active");
		var orderColumn = $("input[name='orderColumn']").val();
		var orderType = $("input[name='orderType']").val();
		switch (orderColumn) {
		case "quote_num":{
			$(".select-brain-wrap").find("li").eq(1).addClass("active");
			$(".select-brain").find("b").html("最多引用");
		}
			break;
		case "plan_days":{
			if(orderType == "desc"){
				$(".select-brain-wrap").find("li").eq(2).addClass("active");
				$(".select-brain").find("b").html("天数最多");
			}else if(orderType == "asc"){
				$(".select-brain-wrap").find("li").eq(3).addClass("active");
				$(".select-brain").find("b").html("天数最少");
			}
			
		}
			break;
		case "plan_cost":{
			if(orderType == "desc"){
				$(".select-brain-wrap").find("li").eq(4).addClass("active");
				$(".select-brain").find("b").html("花费最高");
			}else if(orderType == "asc"){
				$(".select-brain-wrap").find("li").eq(5).addClass("active");
				$(".select-brain").find("b").html("花费最低");
			}
		}
			break;
		default:{
			$(".select-brain-wrap").find("li").eq(0).addClass("active");
			$(".select-brain").find("b").html("智能排序");
		}
			break;
		}
	}
}

function firstSelect()
{
	$(this).parents(".left-menu").find("li").removeClass("on");
	$(this).parents(".left-menu").find(".right-menu").addClass("hide");
	
	if($(this).hasClass("all-city")){
		$(this).parents(".left-menu").find("li").eq(1).addClass("on");
		$(this).parents(".left-menu").find("li").eq(1).find(".right-menu").removeClass("hide");
		didSelect();
		return;
	}
	
	$(this).addClass("on");
	$(this).find(".right-menu").removeClass("hide");
}

function didSelect(){
	getEvtTgt().parents(".list-content").find("li").removeClass("active");
	getEvtTgt().addClass("active");
	//填充头部
	var con_index = getEvtTgt().parents(".list-content:first").index();
	$(".list-title").find(".chosen").find("b").html(getEvtTgt().html());
	switch (con_index)
	{
	case 0:{
		if(getEvtTgt().index() == 0){
			$(".list-title").find(".chosen").find("b").html("目的地");
			$(".right-menu").find("li").removeClass("active");
		}
		$("#cityId").val(getEvtTgt().attr("data-id"));
        $("#cityNames").val(getEvtTgt().text()=="不限"?'':getEvtTgt().text());
	}
		break;
	case 1:{
		if(getEvtTgt().index() == 0){
			$(".list-title").find(".chosen").find("b").html("天数");
		}
		$("#dayL").val(getEvtTgt().attr("data-day-L"));
        $("#dayU").val(getEvtTgt().attr("data-day-U"));
	}
		break;
	case 2:{
		if(getEvtTgt().index() == 0){
			$(".list-title").find(".chosen").find("b").html("花费");
		}
		var lowestSpan = getEvtTgt().html().split("~")[0];
    	var highestSpan = getEvtTgt().html().split("~")[1];

        var number = /^[0-9]*$/;
        $("#planCostL").val(lowestSpan);
        if (number.test(highestSpan)) {
            $("#planCostU").val(highestSpan);
        } else {
            $("#planCostU").val("");
        }
        if (getEvtTgt().html() == "不限") {
            $("#planCostL").val("");
            $("#planCostU").val("");
        }
	}
		break;
	case 3:{
		$("input[name='orderColumn']").val(getEvtTgt().attr("ordercolumn"));
		$("input[name='orderType']").val(getEvtTgt().attr("ordertype"));
	}
		break;
	}
	updateList();
}


function updateList()
{
	_closenavSelect();
	listRecommend();
}

/* 加载更多 */
function whichPage(this_a){
	if($(this_a).hasClass("load-recommend")){
		recommendPage = recommendPage + 1;
		listRecommend(2);
	}else if($(this_a).hasClass("load-my")){
		myPage = myPage + 1;
		listMyPlan(2);
	}
}

function scrollFun()
{
	$(window).scroll(function()
	{
		var bodyTop = $("body").scrollTop();
		var bodyHeight = $(window).height();
		
		if($(".recommend-wrap").css("display") == "block"){
			if (document.getElementById("J_load-recommend") == null)
			{
				return;
			}
			var btnTop = document.getElementById("J_load-recommend").offsetTop;
			if (btnTop >= bodyTop && btnTop < (bodyTop + bodyHeight))
	        {
				$(".load-recommend").click();
	        }
		}else{
			if (document.getElementById("J_load-my") == null)
			{
				return;
			}
			var btnTop =document.getElementById("J_load-my").offsetTop;
			if (btnTop >= bodyTop && btnTop < (bodyTop + bodyHeight))
	        {
				$(".load-my").click();
	        }
		}
		
		
	});
}

function firstList(){
	$("#J_recommend_list").html("");
	$(".load-recommend").remove();
	$("#J_loading").show();
	
	$.getJSON("/mobile/line/recommend.jhtml?pageSize=30&page=" + recommendPage + "&" + $("#J_select-wrap").serialize()+"&planName="+encodeURI($("#searchCon").val()) , function(result)
	{
		if (!result.success)
		{
			$("#J_loading").hide();
			$("#J_recommend_list").html("<p style='padding: 50% 0; text-align: center'>加载失败，<a onclick='listRecommend()'>重新加载</a></p>");
			return;
		}
        $(".load-recommend").remove();
        var data = result.data;
        $("#J_loading").hide();
        /* $("#J_recommend_list").html(""); */
        if(data.length != 0){
            var recommendList = $("#J_recommend_list");
            for (var i = 0; i < data.length; i++)
            {
                var myLi = $(template("tpl_recommend-list", data[i]));
                recommendList.append(myLi);
                myLi.find("img.lazy").lazyload({ threshold : 300 });
            }
            if(data.length == 30)
            {
                recommendList.parents(".trip-list-wrap").append($("#tpl_load-recommend").html());
            }
            else
            {
                recommendList.parents(".trip-list-wrap").append("<a class='load-more load-recommend nomore'>已加载全部行程</a>");
            }

            $("#J_recommend_list").addClass("Loaded");
        }
        else{
            //fatherList();
            $("#J_recommend_list").html("<p class='no-more' style='padding: 50% 0; text-align: center'>暂无行程</p>");
        }
});
}

function fatherList()
{
	var CityId = $("#cityId").val();
	var FatherId = CityId.substr(0, 2);
	//根据父节点查询API
	$.getJSON("/mobile/line/recommend.jhtml?pageSize=30&page=" + recommendPage+"&planName="+encodeURI($("#searchCon").val()),{
		"singleCityId" : FatherId
	}, function(result)
        {
            if (!result.success)
            {
            	$("#J_loading").hide();
                $("#J_recommend_list").html("<p style='padding: 50% 0; text-align: center'>加载失败，<a onclick='listRecommend()'>重新加载</a></p>");
                return;
            }
            $(".load-recommend").remove();
            $("#J_loading").hide();
            var data = result.data;

            /* $("#J_recommend_list").html(""); */
            if(data.length != 0){
                var recommendList = $("#J_recommend_list");
                for (var i = 0; i < data.length; i++)
                {
                    var myLi = $(template("tpl_recommend-list", data[i]));
                    recommendList.append(myLi);
                    myLi.find("img.lazy").lazyload({ threshold : 300 });
                }
                if(data.length == 30){
                    recommendList.parents(".trip-list-wrap").append($("#tpl_load-recommend").html());
                }
                $("#J_recommend_list").addClass("Loaded");
            }else{
                $("#J_recommend_list").html("<p class='no-more' style='padding: 50% 0; text-align: center'>暂无行程</p>");
            }
	    });
}

function listRecommend(type){
    var recommendList = $("#J_recommend_list");
    recommendList.find(".no-more").remove();

	//console.log(type);
	//如果不是从点击更多进入，则加载第一页
	if(type != 2){
		$("#J_loading").show();
        recommendList.html("");
		recommendPage = 1;
		$(".load-recommend").remove();
	}else{
		$(".load-recommend").html("加载中…").attr("onclick","");
	}
	$.getJSON("/mobile/line/recommend.jhtml?pageSize=30&page=" + recommendPage + "&" + $("#J_select-wrap").serialize()+"&planName="+encodeURI($("#searchCon").val()) , function(result)
	{
		if (!result.success)
		{	
			if(type != 2){
				$("#J_loading").hide();
                recommendList.html("<p style='padding: 50% 0; text-align: center'>加载失败，<a onclick='listRecommend()'>重新加载</a></p>");
			}else{
				$("#J_load-recommend").html("<p class='load-circle load-more'>加载失败，<a style='text-decoration: underline;' onclick='listRecommend(2)'>重新加载</a></p>");
			}
			return;
		}
        $(".load-recommend").remove();
        $("#J_loading").hide();
        var data = result.data;
        /* $("#J_recommend_list").html(""); */
        if(data.length == 0){
            if(type != 2){
                recommendList.html("<p class='no-more' style='padding: 50% 0; text-align: center'>暂无行程</p>");
            }else{
                $("J_recommend_list").append("<a class='load-circle load-more'>没有更多了</a>");
            }
            return;
        }
        for (var i = 0; i < data.length; i++)
        {
            var myLi = $(template("tpl_recommend-list", data[i]));
            recommendList.append(myLi);
            myLi.find("img.lazy").lazyload({ threshold : 300 });
        }
        if(data.length == 30){
            recommendList.parents(".trip-list-wrap").append($("#tpl_load-recommend").html());
        }
        else
        {
            recommendList.parents(".trip-list-wrap").append("<a class='load-more load-recommend nomore'>已加载全部行程</a>");
        }
        
        $("#J_recommend_list").addClass("Loaded");
    });
}

function listMyPlan(type){
    var myList = $("#J_my_list");
    myList.find(".no-more").remove();
    
	//如果不是从点击更多进入，则加载第一页
	if(type != 2){
		$("#J_loading").show();
        myList.html("");
		myPage = 1;
		$(".load-my").remove();
	}else{
		$(".load-my").html("加载中…").attr("onclick","");
	}
	$.getJSON("/ajax/listMy?pageSize=10&page="+ myPage +"&random=" + new Date().getTime(), function (result) 
	{
		if (!result.success)
		{
			$("#J_loading").hide();
            myList.html("<p style='padding: 50% 0; text-align: center'>加载失败，<a onclick='listMyPlan()'>重新加载</a></p>");
			return;
		}
        $(".load-my").remove();
        var data = result.data;
        if(data.length != 0)
        {
        	$(".noplan-tips-wrap").addClass("hide");
            for (var i = 0; i < data.length; i++)
            {
                var myLi = $(template("tpl_myplan-list", data[i]));
                $("#J_my_list").append(myLi);
                myLi.find("img.lazy").lazyload({ threshold : 300 });
            }
            if(data.length == 10)
            {
                $("#J_my_list").parents(".trip-list-wrap").append($("#tpl_load-my").html());
            }
            else
            {
                $("#J_my_list").parents(".trip-list-wrap").append("<a class='load-more load-my nomore'>已加载全部行程</a>");
            }
            $("#J_loading").hide();
            $("#J_my_list").addClass("Loaded");
        }
        else
        {
            if(type != 2)
            {
                $(".noplan-tips-wrap").removeClass("hide");
            }
            $("#J_loading").hide();
        }
      });
}

/* 删除我的行程 */
function delectPlan(planId){
	$.confirm({title : "提示", msg : "<p class='tips-p'>您确定要删除此行程吗？</p>", initFn : function(){ del(planId); }});
}
function del(planId)
{
	$("#my_" + planId).addClass("remove");
	$.post("/ajax/delMyPlan",{planId: planId}, function(result){
		if(result.success){
			/* $("#my_" + planId).fadeOut("slow"); */
			$.tips("删除成功");
			
			setTimeout(function() {
                listMyPlan()
            }, 1000);
		}else{
			$("#my_" + planId).removeClass("remove");
			$.tips("删除失败");
		}
	});
}

/* 搜索页面的打开与关闭  */
function showSearch()
{
	$("#J_search").show();
	window.setTimeout(function () { $("#J_search").addClass("panel--show"); }, 100);
	$("body").css("overflow","hidden");
	$("html").css("overflow","hidden");
	$("input[name='search-input']").val();
}

function closeSearch()
{
	$("#J_search").removeClass("panel--show");
	$("body").css("overflow","");
	$("html").css("overflow","");
}

function searchPlan()
{
	//$("#J_search-form").submit();
    firstList();
    closePanel();
}

function showDate(planId)
{
	var myli = getEvtTgt().parents("li:first");
	var start_time = myli.attr("startTime");
	if (isNull(start_time))
	{	
		start_time = formatDate(new Date(), "yyyy-MM-dd");
	}
	
	$.confirm({
		title : "设置出发日期",
		msg : "<input class='date-input' type='date' value='" + start_time + "'/>", 
		initFn : function()
		{ 
			var time = $(".date-input").val();
			if (hasLogin())
			{
				saveInfo(myli, planId, time);
			}
		}
	});
}

function saveInfo(myli, planId, time)
{
	$.post("/plan/ajax/updateStartTime", {planId : planId, startTime : time}, function()
	{
		myli.find(".start-time").remove();
		var data = { id: planId, startTime : time};
		if (isNull(time))
		{
			myli.append($(template("tpl_myplan-null-time", data)));
		}
		else
		{
			myli.append($(template("tpl_myplan-time", data)));
		}
	});
}

function myplanDetail(planId){
    window.location.href= "/plan/detail/" + planId;
}

function recommendDetail(rcmId){
	window.location.href= "/mobile/line/detail.jhtml?lineId=" + rcmId;
}

function plan()
{
	$("#J_plan-nav").toggleClass("fadeout");
	$("#J_mine-nav").addClass("fadeout");
	$("#J_find-nav").addClass("fadeout");
	$("#J_friends-nav").addClass("fadeout");
}

function find()
{
	$("#J_find-nav").toggleClass("fadeout");
	$("#J_mine-nav").addClass("fadeout");
	$("#J_plan-nav").addClass("fadeout");
	$("#J_friends-nav").addClass("fadeout");
}

function friends()
{
	$("#J_friends-nav").toggleClass("fadeout");
	$("#J_find-nav").addClass("fadeout");
	$("#J_mine-nav").addClass("fadeout");
	$("#J_plan-nav").addClass("fadeout");
}

function mine()
{
	$("#J_mine-nav").toggleClass("fadeout");
	$("#J_plan-nav").addClass("fadeout");
	$("#J_find-nav").addClass("fadeout");
	$("#J_friends-nav").addClass("fadeout");
}

function recommend()
{
    $(".pageTitle").html("推荐行程");
	$(".recommend-wrap").show();
	$(".my-wrap").hide();
	$(".publish-btn").show();
	
	if (!$("#J_recommend_list").hasClass("Loaded"))
	{
		firstList();
	}
	
	window.history.replaceState(null, null, "?type=1");
	$("#page_type").val(1)
}

function myplan()
{
	$(".pageTitle").html("我的行程");
	$(".recommend-wrap").hide();
	$(".my-wrap").show();
	if (hasLogin() && !$("#J_my_list").hasClass("Loaded"))
	{
		listMyPlan();
	}else{
		$("#J_loading").hide();
	}
	
	if ($("#J_my_list>li").size() == 0)
	{
		$(".publish-btn").hide();
	}
	
	window.history.replaceState(null, null, "?type=2");
	$("#page_type").val(2)
}

//function NoClickDelay(el) {
//    this.element = typeof el == 'object' ? el: document.getElementById(el);
//    if (window.Touch)  this.element.addEventListener('touchstart', this, false);
//}
//NoClickDelay.prototype = {
//    handleEvent: function(e) {
//        switch (e.type) {
//        case 'touchstart':
//            this.onTouchStart(e);
//            break;
//        case 'touchmove':
//            this.onTouchMove(e);
//            break;
//        case 'touchend':
//            this.onTouchEnd(e);
//            break;
//        }
//    },
//    onTouchStart: function(e) {
//        e.preventDefault(); this.moved = false;
//        this.theTarget = document.elementFromPoint(e.targetTouches[0].clientX, e.targetTouches[0].clientY);
//        if (this.theTarget.nodeType == 3) this.theTarget = theTarget.parentNode;
//        this.theTarget.className += ' pressed';
//        this.element.addEventListener('touchmove', this, false);
//        this.element.addEventListener('touchend', this, false);
//    },
//    onTouchMove: function(e) {
//        this.moved = true;
//        this.theTarget.className = this.theTarget.className.replace(/ ?pressed/gi, '');
//    },
//    onTouchEnd: function(e) {
//        this.element.removeEventListener('touchmove', this, false);
//        this.element.removeEventListener('touchend', this, false);
//        if (!this.moved && this.theTarget) {
//            this.theTarget.className = this.theTarget.className.replace(/ ?pressed/gi, '');
//            var theEvent = document.createEvent('MouseEvents');
//            theEvent.initEvent('click', true, true);
//            this.theTarget.dispatchEvent(theEvent);
//        }
//        this.theTarget = undefined;
//    }
//};

function getMsgCount()
{
	$.getJSON("/index/ajax/getmsgs?random=" + new Date().getTime(), function(result)
	{
		if (result.success)
		{
			var notices = result.data.noticecounts.noticecount;
			var msgs = result.data.messagecounts.count;
			var orders = result.data.nopayorders;
			var onsales = result.data.nopayonsale;
			
			var msg_count = parseInt(notices) + parseInt(msgs);
			if (msg_count > 0)
			{
				$("#J_msg-count").show();
				$("#J_msg-count").html(msg_count);
				$(".msg2-i").show();
			}
			var order_count = parseInt(orders) + parseInt(onsales);
			if (order_count > 0)
			{
				$("#J_msg-mine").hide();
				$(".J_orders-counts").show();
				$(".J_orders-counts").html(order_count);
			}
			
			if (result.data.needEdit == "true")
			{
				$(".msg-i.mine").show();
			}
		}
	});
}












