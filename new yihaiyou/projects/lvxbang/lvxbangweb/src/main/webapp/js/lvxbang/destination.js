
$(document).ready(function() {
    abroad();
	show_mainbox();
	hide_mainbox();
	internal();
	international();
	$('.smaller_city_change span').css('color','#33be82');
	$('.smaller_city_change2 span').css('color','#33be82');

	//搜索历史
	var searchHistoryDiv = $(".des_main .seach_history .selected_city");
	var internalSearchHistory = JSON.parse(getCookie("internal_search_history"));
	if (!isNull(internalSearchHistory) && internalSearchHistory.length > 0) {
		$.each(internalSearchHistory, function (i, data) {
			var li = "<li class='history_city'>" + data +"</li>";
			searchHistoryDiv.eq(0).append(li);
		});
	}
	var abroadSearchHistory = JSON.parse(getCookie("abroad_search_history"));
	if (!isNull(abroadSearchHistory) && abroadSearchHistory.length > 0) {
		$.each(abroadSearchHistory, function (i, data) {
			var li = "<li class='history_city'>" + data +"</li>";
			searchHistoryDiv.eq(1).append(li);
		});
	}

	$(".des_main .follower_city li,.des_main .follower_city2 li,.des_main .seach_history .history_city").click(function () {
		var input = $(this).parents(".des_main").siblings(".des_into");
		var multi = input.data("multi");
		input.data("id", $(this).data("id"))
		var text = $(this).text();
		var inputVal = input.val();
		if (multi) {
			if (inputVal.indexOf(text) < 0) {
				input.val(inputVal + text + "；");
			}
		} else {
			input.val(text + "；");
		}
		$('.des_close').click();
	});
});
//国内外切换	
function abroad(){
	var Li=$('#internal li');
	Li.click(function(){
		var now=$(this).hasClass('des_color');
		var num=$(this).index();
		var thistank=$('.city_change').eq(num);
		var seach_history=$('.selected_city').eq(num);
		if(now==false){
			Li.removeClass('des_color');
			$(this).addClass('des_color');
			$('.city_change').hide();
			$('.selected_city').hide();
			thistank.show();
			seach_history.show();
			}
		});
	}
//输入框显示
function show_mainbox(){
	var des_into=$('.des_into');
	    des_into.click(function(){
			$(".des_main").show();
			});
	}
//输入框隐藏
function hide_mainbox(){
	var des_close=$('.des_close');
	    des_close.click(function(){
			$(".des_main").hide();
			});
	}
//国内城市切换	
function internal(){
	var Li=$('.guide li');
	Li.click(function(){
		var now=$(this).hasClass('bg_color');
		var num=$(this).index();
		var thistank=$('.smaller_city_change').eq(num);
		if(now==false){
			Li.removeClass('bg_color');
			$(this).addClass('bg_color');
			$('.smaller_city_change').hide();
			thistank.show();
			}
		});
	}
//境外城市切换	
function international(){
	var Li=$('.internation li');
	Li.click(function(){
		var now=$(this).hasClass('bg_color2');
		var num=$(this).index();
		var thistank=$('.smaller_city_change2').eq(num);
		if(now==false){
			Li.removeClass('bg_color2');
			$(this).addClass('bg_color2');
			$('.smaller_city_change2').hide();
			thistank.show();
			}
		});
	}
	
	
	
	
	