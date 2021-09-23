/**
 * Created by vacuity on 15/11/20.
 */

$(function() {
    WechatData.init();
});

var WechatData={

    init: function(){
    	WechatData.initListener();
    	WechatData.initNews();
    	WechatData.initStatus();
    	WechatData.initText();
    	
    },
    
    closeEditNews:function(flag){
    	
    	if(flag){
    		$("#editNews").dialog("close");
    		WechatData.doSearch('news_dg');
    	}
    	
    },
    
    closeEditText:function(flag){
    	
    	if(flag){
    		$("#editText").dialog("close");
    		WechatData.doSearch('text_dg');
    	}
    	
    },
    
	// 打开新增窗口
    addNews : function() {
		var t = Math.random(); 	// 保证页面刷新
		var url = "/wechat/wechatData/addNews.jhtml?t="+t+'#loctop';
		var ifr = $("#editNews").children()[0];
		$(ifr).attr("src", url);
		$("#editNews").dialog({
		    height: 500
		});
		$("#editNews").dialog("open");
	},
	// 打开新增窗口
	addText: function() {
		var t = Math.random(); 	// 保证页面刷新
		var url = "/wechat/wechatData/addText.jhtml?t="+t+'#loctop';
		var ifr = $("#editText").children()[0];
		$(ifr).attr("src", url);
		$("#editText").dialog("open");
	},
	
	
	editText:function(id){
		var t = Math.random(); 	// 保证页面刷新
		var url = "/wechat/wechatData/addText.jhtml?textId="+id+'&t='+t+'#loctop';
		var ifr = $("#editText").children()[0];
		$(ifr).attr("src", url);
		$("#editText").dialog("open");
	},
    
 // 表格查询
	doSearch:function(tbId){
		$('#'+tbId).datagrid({url:'/wechat/wechatData/dataGrid.jhtml'});
	},
	// 监听事件
	initListener : function() {
		$('#tabs').tabs({    
		    onSelect: function(title, index){    
		    	var tab = $('#tabs').tabs('getTab', index);
		    	var tabId = tab[0].id;
		    	WechatData.doSearch(tabId+'_dg');
		    }    
		});
	},
    
	// 初始状态
	initStatus : function() {
		//$('#tabs').tabs('select', 0);
		WechatData.doSearch('text_dg');	// 执行两遍！！！
	},
	
	
	reloadStore:function(tabs){
		
		$("#store_ticketType").combobox("setValue","");
				
		WechatData.doSearch(tabs);
		
	},
	
	reload:function(tabs){
		
		$("#news_keword").textbox("getValue");
				
		WechatData.doSearch(tabs);
		
	},
    
	delText:function(id){
		
		var delUrl = "/wechat/wechatData/delItemAndText.jhtml";
		var checkUrl = "/wechat/wechatData/checkItiem.jhtml";
    	
		$.post(checkUrl, {"textId":id},
				   function(result){
		    		if(result.success){
//		    			WechatData.doSearch("text_dg");
		    			
		    			$.post(delUrl, {"textId":id},
		    					   function(result){
		    			    		if(result.success){
		    			    			WechatData.doSearch("text_dg");
		    			    		}
		    			    	});
		    		}else{
		    			show_msg("该文本已被使用，不能使用！");
		    		}
		    	});
		
		
    	
		
	},
	
	initText:function(){
		// 构建表格
		$('#text_dg').datagrid({   
//			title:"门票列表",
            fit: true,
			data:[],
//			url:'/ticket/ticket/ticketGetList.jhtml',
			border:true,
			singleSelect:true,
			striped:true,
			pagination:true,
//			height:480,
			pageList:[ 10, 20, 30 ],
			rownumbers:true,
			//fitColumns:true,
			columns : [[
			    //{ field : 'id', checkbox : 'true' },
                {field: 'title', title: "文本标题", align : 'center', width: 350},
			    { field : 'content', title : '文本内容', align : 'center', width : 620 ,
			    	formatter : function(value, rowData, rowIndex) {
                        //value = value.replace(/<[^>]+>/g,"");
                        //if(value.length>30){
			    			//value = value.substr(0,30)+"...";
                        //}
						return value;
					}
			    },
			    { field : 'updateTime', title : '更新时间', align : "right", align : 'center', width : 240},
			    { field : "OPT", title : "操作", align : 'center', width : 170,
					formatter : function(value, rowData, rowIndex) {
						var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='WechatData.editText("+rowData.id+")'>编辑</a>&nbsp;";
						var btnDel = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='WechatData.delText("+rowData.id+")'>删除</a>&nbsp;";
							return btnEdit+"&nbsp;&nbsp;"+btnDel;
					}
				}]],
            toolbar: "#text-tool",
			onBeforeLoad : function(data) {   // 查询参数 
		        data.keyword = $("#text_keword").textbox("getValue");
		        data.type = "text";
			}, 
			onLoadSuccess : function(data) {
				
			}
		});
	},
	
	
	delItemAndNews:function(id){
		
		var url = "/wechat/wechatData/delItemAndNews.jhtml";
		var checkUrl = "/wechat/wechatData/checkItiem.jhtml";
    	
		$.post(checkUrl, {"itemId":id},
				   function(result){
		    		if(result.success){
//		    			WechatData.doSearch("news_dg");
		    			
		    			$.post(url, {"itemId":id},
		    					   function(result){
		    			    		if(result.success){
		    			    			WechatData.doSearch("news_dg");
		    			    		}
		    			    	});
		    			
		    		}else{
		    			show_msg("该图文已被使用，不能删除！");
		    		}
		    	});
		
		
    	
	},
	editItemAndNews:function(id){
		
		var t = Math.random(); 	// 保证页面刷新
		var url = "/wechat/wechatData/addNews.jhtml?itemId="+id+"&t="+t+'#loctop';
		var ifr = $("#editNews").children()[0];
		$(ifr).attr("src", url);
		$("#editNews").dialog({
		    height: 500
		});
		$("#editNews").dialog("open");
	},
	
	initNews:function(){
		// 构建表格
		$('#news_dg').datagrid({   
            fit: true,
			data:[],
			singleSelect:true,
			striped:true,
			pagination:true,
			pageList:[ 10, 20, 30 ],
			rownumbers:true,
			fitColumns:true,
			columns : [[
				{ field : 'imgUrl', title : '文本标题', align : 'left', width : 300 ,
					formatter : function(value, rowData, rowIndex) {
						var newslist = rowData.newsList;
						var returnStr = "";
						if (value) {
							returnStr = "<div style='float:left;margin-right:10px;padding:5px;'><img src='"+ QINIU_BUCKET_URL + value +"' alt='' width='100px' height='100px'></img></div>";
						}
						if (newslist) {
							var titleList = "<div  style='font-size:16px;'><ul>";
							if (newslist.length <= 4) {
								for(var i=0; i < newslist.length; i++) {
									var obj = newslist[i];
									titleList = titleList + "<li style='font-size:16px;margin:5px;'>" + (i + 1) +".<span style='font-size:16px;margin:5px;'>"+obj.title+"</span></li>";
								}
							} else {
								for (var i=0; i<4; i++) {
									var obj = newslist[i];
									titleList = titleList + "<li style='font-size:16px;margin:5px;'>"+(i+1)+".<span style='font-size:16px;margin:5px;'>"+obj.title+"</span></li>";
								}
							}
							titleList = titleList + "</ul></div>";
							return returnStr + titleList;
						}else{
							return returnStr;
						}
							
					}	
				},
				{ field : 'updateTime', title : '更新时间', align : "right", align : 'center', width : 150}, 
			    { field : "OPT", title : "操作", align : 'center', width : 100,
					formatter : function(value, rowData, rowIndex) {
						var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='WechatData.editItemAndNews("+rowData.id+")'>编辑</a>&nbsp;";
						var btnDel = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='WechatData.delItemAndNews("+rowData.id+")'>删除</a>&nbsp;";
							return btnEdit+"&nbsp;&nbsp;"+btnDel;
					}
				}]],
            toolbar: "#newsTool",
			onBeforeLoad : function(data) {   // 查询参数 
				data.keyword = $("#news_keword").textbox("getValue");
				data.type = "news";

			}, 
			onLoadSuccess : function(data) {
				
			}
		});
		
		
	}
	
	
	


}



