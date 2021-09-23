/**
 * Created by vacuity on 15/11/20.
 */

$(function() {
	ReplyNews.init();
});

var ReplyNews = {

    init: function(){
    	ReplyNews.initNews();
    },
 // 打开新增窗口
    addNews : function() {
		var t = Math.random(); 	// 保证页面刷新
		
		var url = "/wechat/wechatData/addNews.jhtml?reply=reply&t="+t+'#loctop';
		var ifr = $("#editNews").children()[0];
		$(ifr).attr("src", url);
		$("#editNews").dialog({
//			width: 200,    
//		    height: 500,
		});
		$("#editNews").dialog("open");
	},
    doSearch:function(){
		$('#news_dg').datagrid({url:'/wechat/wechatData/dataGrid.jhtml'});
	},
    initNews:function(){
		// 构建表格
		$('#news_dg').datagrid({   
//			title:"门票列表",
			data:[],
			url:'/wechat/wechatData/dataGrid.jhtml?type=news',
			border:true,
			singleSelect:true,
			striped:true,
			pagination:true,
//			height:480,
			pageList:[ 10, 20, 30 ],
			rownumbers:true,
			fitColumns:true,
			columns : [[
				{ field : 'id', checkbox : 'true' }, 
				{ field : 'imgUrl', title : '文本标题', align : 'left', width : 300 ,
					formatter : function(value, rowData, rowIndex) {
						var newslist = rowData.newsList;
						console.log("newslist:"+newslist);
						if(newslist){
							var returnStr = "<div style='float:left;margin-right:10px;'><img src='"+value+"' alt='' width='100px' height='100px'></img></div>"; 
							var titleList = "<div  style='font-size:16px;'><ul>";
							if(newslist.length<=4){
								for(var i=0;i<newslist.length;i++){
									var obj = newslist[i];
									var title = obj.title;
									if(title.length>10){
										title = title.substr(0,30)+"...";
									}
									
									titleList = titleList + "<li style='font-size:16px;margin:5px;'>"+(i+1)+".<a style='font-size:16px;margin:5px;'>"+title+"</a></li>";
								}
							}else{
								for(var i=0;i<4;i++){
									var obj = newslist[i];
									var title = obj.title;
									if(title.length>10){
										title = title.substr(0,30)+"...";
									}
									titleList = titleList + "<li style='font-size:16px;margin:5px;'>"+(i+1)+".<a style='font-size:16px;margin:5px;'>"+title+"</a></li>";
								}
							}
							
							titleList = titleList + "</ul></div>";
							return returnStr+titleList;
						}else{
							return returnStr;
						}
							
					}	
				},
				{ field : 'updateTime', title : '更新时间', align : "right", align : 'center', width : 150}, 
			    { field : "OPT", title : "操作", align : 'center', width : 100,
					formatter : function(value, rowData, rowIndex) {
						var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='ReplyNews.selectNews("+rowData.id+")'>选取</a>&nbsp;";
						/*var btnDel = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='ReplyNews.delItemAndNews("+rowData.id+")'>删除</a>&nbsp;";*/
							return btnEdit;
					}
				}]], 
			onBeforeLoad : function(data) {   // 查询参数 
				data.keyword = $("#news_keword").textbox("getValue");
				data.type = "news";

			}, 
			onLoadSuccess : function(data) {
				
			}
		});
		
		
	},
	selectNews:function(id){
		var url = "/wechat/wechatData/selectNews.jhtml";
		$.post(url,
			{ itemId: id},
			   function(data){
				if(data){
					
					if(data.success){
//						var index = window.parent.$("#replyList").children().length;
						var index = $("#indexId").val();
						
						if(index){
							var html = "";
							html += '<div style="border-top:1px solid #e7e7eb;" id="reply_div_'+index+'">';
//				    		html += '<input type="hidden" name="itmeId_'+index+'" value="'+data.id+'">';
				    		html += '<ul>';
				    		
				    		window.parent.$("#itmeId_"+index+"").val(data.id);
				    		var newslist = data.newsList;
				    		
//				    		console.log(newslist);
				    		
				    		$.each(newslist,function(i,perValue){
				    			
//				    			console.log(perValue.id);
				    			
				    			html += '	<li style="padding:2px;height:60px;">'+
					    		'		<div style="float:left;"><img src="'+perValue.img_path+'" alt="" width="50px" height="50px"></div>'+
					    		'		<div style="margin-top: 15px;float:left;">'+
					    		'			<em>'+perValue.title+'</em>'+
					    		'		</div>'+
					    		'	</li>';
				    			
				    			
				    		});
				    		
				    		/*html += '	<li style="padding:2px;height:60px;">';
				    		html += '		<div style="float:left;"><img src="" alt="" width="50" heith="50"></div>';
				    		html += '		<div style="margin-top: 15px;float:left;">';
				    		html += '			<em>大家好'+index+'</em>';
				    		html += '		</div>';
				    		html += '		<div style="margin-left: 1000px;margin-top: 11px;">';
				    		html += '			<a href="">删除</a>';
				    		html += '		</div>';
				    		html += '	</li>';*/
				    		
				    		html += '		<div style="margin-left: 1000px;position: relative;top: -15;">';
				    		html += '			<a href="#" onclick="AutoReply.delReplys('+index+')">删除</a>';
				    		html += '		</div>';
				    		html += '</ul>';
				    		html += '</div>';
				    		
				    		window.parent.$("#replyList_"+index+"").append(html);
				    		
				    		
				    		window.parent.$("#editNews").dialog('close');
							
						}else{
							show_msg("只能增加一条回复！");
						}
						
						
					}
				}
		});
		
	},
	
	
	 
    
    
}



