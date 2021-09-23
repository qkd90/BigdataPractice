

function mysort(a, b) {
	return a - b;
}
var Frm = {

	recentDaydiffzr : null,

	requestFPanel : function() {
		$.ajax({
			url : "/crm/frm/findFruquenct.jhtml",
			type : "POST",
			success : function(json) {
				var html = $("#rRowTemplate").render(json);
				$("#fRowList").html(html);
				Frm.painFPanelRowColor("#fTable");
				Frm.painFPersend("#fTable");
			}
		});
	},
	requestMPanel : function() {
		var step = $("input[name='step']:checked").val();
		$.ajax({
			url : "/crm/frm/findM.jhtml",
			type : "POST",
			data : {
				step : step
			},
			success : function(json) {
				var html = $("#mRowTemplate").render(json);
				$("#mRowList").html(html);
				Frm.painFPanelRowColor("#mTable");
				Frm.painFPersend("#mTable");
			}
		});
	},
	requestFrmTotalPanel : function() {
		var site = $("select option:checked").val();
		var start = $("#start").datebox('getValue');
		var end = $("#end").datebox('getValue');
		$.ajax({
			url : "/crm/frm/queryFrm.jhtml",
			type : "POST",
			data : {
				site : site,
				start : start,
				end : end
			},
			success : function(json) {
				for(var row = 1;row <= 5;row ++){
   					for(var i = 1;i <= 5;i++ ){
   						$($(".buyers"+row)[i-1]).text(json[row][i].buyers);
   						$($(".totalBuy" + row)[i-1]).text(json[row][i].totalBuy);
   					}     			
    			}
    			Frm.count();
			}
		});
	},
	count : function count(){
		 for(var i = 1;i <= 5;i++){
	            var totalBuyers1 = parseInt($($(".buyers1")[i-1]).text());
	            var totalBuyers2 = parseInt($($(".buyers2")[i-1]).text());
	            var totalBuyers3 = parseInt($($(".buyers3")[i-1]).text());
	            var totalBuyers4 = parseInt($($(".buyers4")[i-1]).text());
	            var totalBuyers5 = parseInt($($(".buyers5")[i-1]).text());
	       		$($(".totalBuyers6")[i-1]).text(totalBuyers1 + totalBuyers2 + totalBuyers3 + totalBuyers4 + totalBuyers5);   
         }
         for(var i = 1;i <= 5;i++){
         	var total = 0;
	            $(".buyers"+i).each(function(){
	            	total += parseInt($(this).text());
	            });
	            $(".totalBuyers"+i+" :last").text(total);
         }
         var total = 0;
         $(".totalBuyers6 :last").text("0");
	        $(".totalBuyers6").each(function(){
	        	total += parseInt($(this).text());
	        });
	        $(".totalBuyers6 :last").text(total);
	        
	        for(var i = 1;i <= 5;i++){
	            var totalBuy1 = parseFloat($($(".totalBuy1")[i-1]).text());
	            var totalBuy2 = parseFloat($($(".totalBuy2")[i-1]).text());
	            var totalBuy3 = parseFloat($($(".totalBuy3")[i-1]).text());
	            var totalBuy4 = parseFloat($($(".totalBuy4")[i-1]).text());
	            var totalBuy5 = parseFloat($($(".totalBuy5")[i-1]).text());
	       		$($(".totalAmts6")[i-1]).text((totalBuy1 + totalBuy2 + totalBuy3 + totalBuy4 + totalBuy5).toFixed(2));   
         }
         for(var i = 1;i <= 5;i++){
         	var total = 0;
	            $(".totalBuy"+i).each(function(){
	            	total += parseFloat($(this).text());
	            });
	            $(".totalAmts"+i+" :last").text(total.toFixed(2));
         }
         var total = 0;
	        $(".totalAmts6 :last").text("0");
	        $(".totalAmts6").each(function(){
	        	total += parseFloat($(this).text());
	        });
	        $(".totalAmts6 :last").text(total.toFixed(2));
	},
	countFPanelNumAndPersend : function() {

	},
	districtArray : function(array) {
		var result = new Array();
		for ( var i in array) {
			if (result.indexOf(array[i]) == -1) {
				result.push(array[i]);
			}
		}
		return result;
	},
	painFPanelRowColor : function(tableid) {
		var trs = $(tableid + " tbody tr");
		var rowNums = new Array(trs.size());
		trs.each(function() {
			rowNums.push(parseInt($(this).attr('class').replace('row', '')));
		});
		var rowNums = Frm.districtArray(rowNums).sort(mysort).reverse();
		var rowColors = MyColor('#FF0000', '#FFFFFF', rowNums.length);
		for (var color = 0; color < rowColors.length; color++) {
			$(tableid + " tbody tr.row" + rowNums[color]).css('background-color', rowColors[color]);
		}
	},
	painFPersend : function(tableid) {
		var trs = $(tableid + " tbody tr");
		var total = 0;
		trs.each(function() {
			total += parseInt($(this).attr('class').replace('row', ''));
		});
		trs.each(function() {
			var num = parseInt($(this).attr('class').replace('row', ''));
			$($(this).find('td')[2]).text((parseFloat(num * 100) / parseFloat(total)).toFixed(2) + "%");
		});
	},
	bindTabEvent : function() {
		var tabs = $('.easyui-tabs').tabs({
			onSelect : function(title, index) {
				if (index == 1) {
					var start = $("#start").datebox('getValue');
					var end = $("#end").datebox('getValue');
					var site = $("select option:checked").val();
					$.ajax({
						url : "/crm/frm/recentDayUsers.jhtml",
						type : "POST",
						data : {
							start : start,
							end : end,
							site : site
						},
						success : function(json) {
							var recentDaydiffzr = require('echarts').init(document.getElementById('recentDaydiffzr'));
							var radio = parseInt($("input[name='buyDayDiff']:checked").val());
							var len = 360/ radio;
							usercycleOption.xAxis[0].data.length = 0;
							var splitNum = new Array(len+1);
							for(var i = 1;i <= len+1;i++){
								if(i * radio <= 360){
									usercycleOption.xAxis[0].data.push("["+(i-1)*radio + "-" + i*radio+")");
								}else{
									usercycleOption.xAxis[0].data.push("[360以上)");
								}
							}
							var total = 0;
							recentDaydiffOptions.series[0].data.length = 0;
							for(var i = 0;i < splitNum.length;i++){
								splitNum[i] = 0;
							}
							for (var i = 0; i < json.length; i++) {
								var day = parseInt(json[i].a);
								var num = parseInt(json[i].b);
								var item = new Array(day, num);
								recentDaydiffOptions.series[0].data.push(item);
								for(var j = 1;j <=len;j++){
									if(day > 360){
										splitNum[len] += num;
										total += num;
										break;
									}else if(day >= (j - 1) * radio &&  day < j * radio){
										splitNum[j-1] += num;
										total += num;
										break;
									}
								}
							}
							for(var i = 0;i < splitNum.length;i++){
								splitNum[i] = (splitNum[i] *100/ total).toFixed(2); 
							}
							recentDaydiffzr.setOption(recentDaydiffOptions, true);
							usercycleOption.series[0].data.length = 0;
							var userCycleZr = require('echarts').init(document.getElementById('userCycleZr'));
							usercycleOption.series[0].data = splitNum;
							userCycleZr.setOption(usercycleOption, true);
						}
					});
				}
				if (index == 2) {
					Frm.requestFPanel();
				}
				if (index == 3) {
					Frm.requestMPanel();
				}
			}
		}).tabs('tabs');
		for (var i = 0; i < tabs.length; i++) {
			tabs[i].panel('options').tab.unbind().bind('mouseenter', {
				index : i
			}, function(e) {
				$('.easyui-tabs').tabs('select', e.data.index);

			});
		}
	},
	changeRecentUserRadio : function(){
		$("input[name='buyDayDiff']").change(function(){
			var datas = recentDaydiffOptions.series[0].data;
			var radio = parseInt($("input[name='buyDayDiff']:checked").val());
			var len = 360/ radio;
			usercycleOption.xAxis[0].data.length = 0;
			var splitNum = new Array(len+1);
			for(var i = 1;i <= len+1;i++){
				if(i * radio <= 360){
					usercycleOption.xAxis[0].data.push("["+(i-1)*radio + "-" + i*radio+")");
				}else{
					usercycleOption.xAxis[0].data.push("[360以上)");
				}
			}
			var total = 0;
			for(var i = 0;i < splitNum.length;i++){
				splitNum[i] = 0;
			}
			for (var i = 0; i < datas.length; i++) {
				var day = parseInt(datas[i][0]);
				var num = parseInt(datas[i][1]);
				for(var j = 1;j <=len;j++){
					if(day > 360){
						splitNum[len] += num;
						total += num;
						break;
					}else if(day >= (j - 1) * radio &&  day < j * radio){
						splitNum[j-1] += num;
						total += num;
						break;
					}
				}
			}
			for(var i = 0;i < splitNum.length;i++){
				splitNum[i] = (splitNum[i] *100/ total).toFixed(2); 
			}
			usercycleOption.series[0].data.length = 0;
			var userCycleZr = require('echarts').init(document.getElementById('userCycleZr'));
			usercycleOption.series[0].data = splitNum;
			userCycleZr.setOption(usercycleOption, true);
		});
	},
	downfile : function(url){
		if($('#downloadcsv').length<=0){
			$('body').append("<iframe id=\"downloadcsv\" style=\"display:none\"></iframe>");
		}
		$('#downloadcsv').attr('src',url);
	},
	
	bindExcel : function(){
		 $("#btnExcel").click(function(){
			 var frmbuyCountOpe = $("#frmbuyCountOpe option:selected").val();
			 var cbuyCount = $("#frmbuyCount").val();
			 var site = $("#site option:checked").val();
			 var start = $("#start").datebox('getValue');
			 var end = $("#end").datebox('getValue');
			 var buyInfos = "site="+site + "&frmbuyCountOpe=" + frmbuyCountOpe + "&frmbuyCount=" + cbuyCount+"&start="+start+"&end="+end;
			 
			 var userbaseopdateOpe = $("#userbaseopdateOpe option:selected").val();
			 var userbaseopdate = $("#userbaseopdate").datebox('getValue');
			 buyInfos += "&userbaseopdateOpe="+userbaseopdateOpe + "&userbaseopdate="+userbaseopdate;
			 Frm.downfile("/crm/frm/excel.jhtml?"+buyInfos);
		 });
	},
	init : function() {
		Frm.bindTabEvent();
		$("#btnFrmTotalSearch").click(function(){
			Frm.requestFrmTotalPanel();
		});
		Frm.bindExcel();
		Frm.changeRecentUserRadio();
	}
};
$(function() {
	Frm.init();
});