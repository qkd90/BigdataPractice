
var PlanIndex = {
		
		init:function(){
			
		},
		
		recPlanByDest:function(){
//			href="lvxbang/recommendPlan/list.jhtml?cityIds=350200,11000"
			if ($("#input_planId").val()) {
				var tempValue = $("#input_planId").val();
				tempValue = PlanIndex.replaceChar(tempValue);
				var tempArr = tempValue.split("；");
				//去数组除空元素
				var newArr = PlanIndex.trimArr(tempArr);
				//判断一下是否有重复
				newArr = PlanIndex.unique(newArr);
				if(newArr.length>0){
					var url = "/lvxbang/destination/getAreaIds.jhtml";
					$.post(url, { "nameStr": newArr.join(",")},
							   function(data){
						if (data) {
							window.location.href = $('#recommendplan_path').val() + "/lvxbang/recommendPlan/list.jhtml?cityIds=" + data.join(",");
						} else {
							promptWarn("无当前目的地，请更换目的地！");
						}
					},"json");
				} else {
					promptWarn("请输入目的地！");
					$("#input_planId").val("");
				}
				
			} else {

				promptWarn("请输入目的地！");
				
			}
			
	},
	
	
	scenicByDest:function(){
		//首页自主设计入口
			if ($("#input_planId").val()) {
				var tempValue = $("#input_planId").val();
				tempValue = PlanIndex.replaceChar(tempValue);
				var tempArr = tempValue.split("；");
				var newArr = PlanIndex.trimArr(tempArr);
				//判断一下是否有重复
				newArr = PlanIndex.unique(newArr);
				window.console.log("已处理："+newArr);
				if(newArr.length>0){
                    $("#scenic-form").submit();
                    //window.location.href = "/lvxbang/scenic/list.jhtml?citys=" + newArr;
				} else {
					promptWarn("请输入目的地！");
					$("#input_planId").val("");
				}
	    	} else {
				promptWarn("请输入目的地！");
			}
			
	},
	
	
	//去除数组重复项
	unique : function(arr) {
	    var result = [], hash = {};
	    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
	        if (!hash[elem]) {
	            result.push(elem);
	            hash[elem] = true;
	        }
	        if(result[i] == "" || typeof (result[i]) == "undefined")
	        {
	        	result.splice(i,1);
	        }
	    }
	    return result;
	},
	//去除数组空值项
	trimArr : function(arr) {
	    var result = [], hash = {};
	    result = $.grep(arr, function () { 
	    	return this != ''; 
	    	});
	    return result;
	},
	//js去除字符串空格
	trim : function(str) {
		return str.replace(/(^\s*)|(\s*$)/g, "");
	},
	
	//中英文分号处理
	replaceChar : function(str){
		str = str.replace(/\;/g , "；");
		str = str.replace(/\市/g , "；");
		return str;
	} 
		
};


$(".planIndexSearch").each(function() {
    var category = $(this);
    category.find(".input").keyup(function(e) {
        if (e.keyCode == 13) {
            return;
        }
        var keyword = $.trim($(this).val());
        if (keyword.length == 0) {
            return;
        }
        var regex = /[a-zA-Z]+/;
        if (regex.test(keyword)) {
            return;
        }
        
        var inputValue = "；" + keyword;
		
		var lastCHindex = inputValue.lastIndexOf("；");
		var lastEngIndex = inputValue.lastIndexOf(";");
		var value = "";
		var oldValue = "";
		if(lastCHindex>lastEngIndex){
			value = inputValue.substring(lastCHindex+1, inputValue.length);
			oldValue = keyword.substring(0, lastCHindex);
		} else {
			value = inputValue.substring(lastEngIndex+1, inputValue.length);
			oldValue = keyword.substring(0, lastEngIndex);
		}
        if(value.length>0){
	        $.post(category.attr("data-url"), {name: value}, function (result) {
	            if (result.length > 0) {
	                var html = "";
					//window.console.log(result);
					$.each(result, function (i, data) {
	                        data.key = data.name.replace(value, "<strong>"+value+"</strong>");
	                        html += template("tpl-suggest-item", data);
	                    });
	                category.find("ul").html("").append(html);
	                category.find(".suggest-item").click(function () {
	                    category.find(".input").val(oldValue+$(this).attr("data-text")+"；");
	                })
	            }
	        },"json");
	        category.find(".categories_div").show();
        } 
    });
});





$(document).on("click", function(){
	$(".categories_div").hide(); 
});
