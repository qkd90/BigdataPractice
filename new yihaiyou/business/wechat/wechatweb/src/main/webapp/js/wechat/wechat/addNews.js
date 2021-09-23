/**
 * Created by vacuity on 15/11/20.
 */
$(function() {
    AddNews.init();
    /*$('#btn').bind('click', function(){    
       $("#show_iframe").prop("src","http://www.jeasyui.net/demo/382.html"); 
    });  */
//    window.setInterval("AddNews.saveStorage()",10000); 
});
var AddNews={
    init: function(){
    	AddNews.initTextArea();
//    	AddNews.initInput();
//    	AddNews.initDrag();
    	AddNews.initJsp();
    	AddNews.makeData();
    	AddNews.initData();
    },
    initData:function(){
    	var newlist = $("#hidden_newlist").val();
    	if(newlist){
    		var newsobj = eval(newlist);
    		for(var i=0;i<newsobj.length;i++){
    		   $("#itemId").val(newsobj[0].itemId);
    		   $("#app_1").val(JSON.stringify(newsobj[0]));
    		   if(newsobj[i].index>1){
    			   var objStr = JSON.stringify(newsobj[i]);
    			   $("#show_iframe").after("<input type='hidden' name='app_data' id='app_"+newsobj[i].index+"' value='"+objStr+"' >");
    		   }
				if (newsobj[0].img_path) {
					$("#mytitle_img_1").attr("src", QINIU_BUCKET_URL + newsobj[0].img_path);
				}

    		   if(newsobj[i].index!=1){
    			   var index = newsobj[i].index;
    			   var html = '';
    	    		html += '<div id="show_news_'+index+'" class="show_news"  onclick="AddNews.selectDiv('+index+')" style="width:320px;height:100px;background:#fff;border:1px solid #ccc;margin-top:5px;">';
    	    		html += '<input type="hidden" id="news_'+index+'" class="news_index"  value="'+index+'">';
    	    		html += '<div id="title_'+index+'"  style="float:left;margin-left:10px;margin-top:40px;">';
    	    		html += ''+newsobj[i].title+'';
    	    		html += '</div>';
    	    		html += '<div id="img_div_'+index+'"  style="margin-left:230px;margin-top:10px;">';
					   if (newsobj[i].img_path) {
						   html += '<img id="img_'+index+'"  alt="" src="'+ QINIU_BUCKET_URL +newsobj[i].img_path+'" width="80" height="80">';
					   } else {
						   html += '<img id="img_'+index+'"  alt="" src="" width="80" height="80">';
					   }
    	    		html += '</div>';
    	    		html += '<div id="hide_tool_div2" class="tooldiv" style="  position: relative; bottom: 30px;display:none;">';
    	    		html += '<div style="width:320px;height:40px; background: #ddd; position: relative; opacity: 0.5;"></div>';
    	    		html += '<input  onclick="AddNews.delNews('+index+')" type="button" value="删除" class="main">';
    	    		html += '</div>';
    	    		html += '</div>';
    	    		$("#btn_addNews").before(html);
    	    		AddNews.initJsp();
    		   }
    		}
    	}
    },
    makeData:function(){
//    	var nameList = $(window.frames["show"].document).find('input[name]');
    	var nameList = $("input[name='app_data']");
    	var arrayObj = new Array();
    	$.each(nameList,function(i,perValue){
    		if((perValue.value).length>0){
    			arrayObj[i] = perValue.value;
    		}
    		
    	});
    	var objList = $.grep( arrayObj, function(n,i){
    		  return $.trim(n).length > 0;
    	});
    	var str = "["+objList.join(",")+"]";
    	return str;
    },
    saveStorage:function(){
//    	var str = JSON.stringify(data);
    	var data = AddNews.makeData();
    	var store = window.localStorage;
    	if(data.length>=2){
    		store.setItem("data", data);
        	store.setItem("time", new Date());
    	}
    },
    initJsp:function(){		//鼠标移动隐藏或显示图文工具条
    	$(".show_news").mouseover(function(){
    		$(this).children(".tooldiv").show();
    	});
    	$(".show_news").mouseout(function(){
    		$(this).children(".tooldiv").hide();
    	});
    },
    selectDiv:function(index){
    	$("#show_iframe").prop("src","/wechat/wechatData/editNews.jhtml?index="+index);
    },
    selectColor:function(index){
    	var show_newsList = $(".show_news");
    	$.each(show_newsList,function(i,perValue){
    		var per = $(perValue);
    		var flag = per.children(".news_index").val();
    		if(parseInt(maxIndex) == index){
//    			console.log(index);
    			$("#show_news_"+index+"").css("border","2px solid green");
    			return false;
    		}
    	});
    },
    addNews:function(){
    	var show_news = $(".show_news");
    	var maxIndex = $(show_news[show_news.length-1]).children(".news_index").val();
    	var index =parseInt(maxIndex) + 1;
    	if(show_news.length <= 8){
    		var html = '';
    		html += '<div id="show_news_'+index+'" class="show_news"  onclick="AddNews.selectDiv('+index+')" style="width:320px;height:100px;background:#fff;border:1px solid #ccc;margin-top:5px;">';
    		html += '<input type="hidden" id="news_'+index+'" class="news_index"  value="'+index+'">';
    		html += '<div id="title_'+index+'"  style="float:left;margin-left:10px;margin-top:40px;">';
    		
    		html += '';
    		html += '</div>';
    		html += '<div id="img_div_'+index+'"  style="margin-left:230px;margin-top:10px;">';
    		html += '<img id="img_'+index+'"  alt="" src="" width="80" height="80">';
    		html += '</div>';
    		html += '<div id="hide_tool_div2" class="tooldiv" style="  position: relative; bottom: 30px;display:none;">';
    		html += '<div style="width:320px;height:40px; background: #ddd; position: relative; opacity: 0.5;"></div>';
    		html += '<input  onclick="AddNews.delNews('+index+')" type="button" value="删除" class="main">';
    		html += '</div>';
    		html += '</div>';
    		$("#btn_addNews").before(html);
    		AddNews.initJsp();
    		
    		var obj = document.getElementById("app_"+index);
    		if (!obj){
    			$("#show_iframe").after('<input type="hidden" name="app_data" id="app_'+index+'" value="" >');
    		}else{
    			obj.value = "";
    		}
    		$("#show_iframe").prop("src","/wechat/wechatData/editNews.jhtml?index="+index); 
    		
    		
    	}
    		
    		
    },
    delNews:function(index){
    	$.messager.alert('温馨提示', '确定删除此文章？', 'info', function() {
    		
    		var objStr = $("#app_"+index).val();
    		if(objStr){
    			var obj = eval("("+objStr+")");
    			if(obj.id){
    				var url = "/wechat/wechatDataNews/delPerNews.jhtml";
    				var data = {
    						newsId:obj.id
    				};
    				$.post(url,data,
    	    				function(result){
    			    			if(result.success){
    			    				$("#show_news_"+index).remove();
    			    	    		$("#app_"+index).remove();
    			    			}
    				});
    			}else{
    				$("#show_news_"+index).remove();
    	    		$("#app_"+index).remove();
    			}
    		}else{
    			$("#show_news_"+index).remove();
        		$("#app_"+index).remove();
    		}
    		
    		AddNews.selectDiv(1);
		});
    },
    sortInput:function(){
    	var show_news = $(".show_news");
    	$.each(show_news,function(i,perValue){
    		
    		i=i+1;
    		var per = $(perValue);
//    		per.prop("id","app_"+(show_news.length-i));
    		
    		per.attr("onclick","AddNews.selectDiv("+i+")");
    		per.attr("id","show_news_"+i);
    		per.children(".news_index").attr("id","news_"+i);
    		per.children(".news_img_index").attr("id","img_div_"+i);
    		per.children('.news_img_index').children('.news_img').attr("id","img_"+i);
    		per.children(".news_click").attr("onclick","AddNews.delNews("+i+")");
    		
    	});
    	var app_datas = $("input[name='app_data']");
//    	alert("news="+show_news.length+",app="+app_datas.length);	
    	$.each(app_datas,function(i,perValue){
    		var per = $(perValue);
    		per.prop("id","app_"+(app_datas.length-i));
    		var data = per.val();
    		if(data.length>0){
    			var dataStr = eval("("+data+")");
    			dataStr.index = (app_datas.length-i);
    			data = JSON.stringify(dataStr);
    			
    		}
//    		console.log(data);
    		per.val(data);
    	});
    	/*var flag = index + 1;
    	var total = show_news.length;
    	if(index+1){
    		var inputData = $("#app_"+index+1).val();
    	}*/
    },
    
    initInput:function(){
    	$("input",$("#input_title").next("span")[1]).blur(function(){
    			
    		var textTitle = $("#input_title").textbox("getValue");
    			
    		var titleLength = textTitle.length;
    		if(titleLength<64){
    			$("#textLength").html(titleLength);
    		}else if(titleLength==0){
    			$("#textLength").html("0");
    		}else{
    			show_msg("标题长度过长，请重新编辑！");
    		}
    		
    	});
    },
    saveBefore:function(){
    	var flag = true;
    	if($("#input_title").textbox("getValue").length<=0){
    		return flag = false;
    	}
    	var html = $("#text_content").val();
//    	alert(html);
    	if(html.length<=0){
    		return flag = false;
    	}
    	return flag;
    },
    AddNews:function(){
    	$('#textFormId').form('submit', {
			url : "/wechat/wechatDataText/saveText.jhtml",
			onSubmit : function() {
				var isValid = AddNews.saveBefore();
				if(isValid){
					$.messager.progress({
						title:'温馨提示',
						text:'数据处理中,请耐心等待...'
					});
				} else {
					show_msg("请完善当前页面数据");
				}
				return isValid;
			},
			success : function(result) {
				$.messager.progress("close");
				var result = eval('(' + result + ')');
				if(result.success==true){
//					show_msg("操作成功");
					$.messager.alert('温馨提示', '操作成功', 'info', function() {
						parent.window.WechatData.closeEditText(true);
	  				});	
				}else{
					show_msg("操作失败");
				}
			}
		});
//    	alert("a");
    },
    initTextArea:function(){
    	var content;
		KindEditor.ready(function(K) {
			content = K.create('#news_content', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				allowImageUpload : false,
//				items : [ 'link'],
				allowFileManager : true,
				afterChange: function() { 
					this.sync(); 
//					if(this.count()<=1200){
////						K('#textareaLength1').html(1200-this.count());
//					}else{
//						show_msg("链接元素过长，请重新编辑！");
//					}
//
//					if(this.count('text')<=600){
////						K('#textareaLength2').html(600-this.count('text'));
//					}else{
//						show_msg("纯文本字数，请重新编辑！");
//					}
					
				}, 
				afterBlur: function() { 
					this.sync(); 
					
				}
			});
		});
    }
	
}
