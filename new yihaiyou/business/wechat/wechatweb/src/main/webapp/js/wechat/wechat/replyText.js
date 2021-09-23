/**
 * Created by vacuity on 15/11/20.
 */

$(function() {
	ReplyText.init();
});

var ReplyText = {
    init: function(){
    	ReplyText.initText();
    },
    
 // 打开新增窗口
	addText: function() {
		var t = Math.random(); 	// 保证页面刷新
		var url = "/wechat/wechatData/addText.jhtml?reply=reply&t="+t+'#loctop';
		var ifr = $("#editText").children()[0];
		$(ifr).attr("src", url);
		$("#editText").dialog("open");
	},
    doSearch:function(){
		$('#text_dg').datagrid({url:'/wechat/wechatData/dataGrid.jhtml'});
	},
    initText:function(){
		// 构建表格
		$('#text_dg').datagrid({   
//			title:"门票列表",
			data:[],
			url:'/wechat/wechatData/dataGrid.jhtml?type=text',
			border:true,
			singleSelect:true,
			striped:true,
			pagination:true,
			height:480,
			pageList:[ 10, 20, 30 ],
			rownumbers:true,
			fitColumns:true,
			columns : [[
			    { field : 'id', checkbox : 'true' }, 
			    { field : 'content', title : '文本内容', align : 'left', width : 450 ,
			    	formatter : function(value, rowData, rowIndex) {
			    		value = value.replace(/<[^>]+>/g,"");
						if (value.length>20) {
							return value.substring(0,20);
						}
						return value;
					}	
			    },
			    { field : 'updateTime', title : '更新时间', align : "right", align : 'center', width : 150}, 
			    { field : "OPT", title : "操作", align : 'center', width : 100,
					formatter : function(value, rowData, rowIndex) {
						var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='ReplyText.selectText("+rowData.id+")'>选取</a>&nbsp;";
						/*var btnDel = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='WechatData.delText("+rowData.id+")'>删除</a>&nbsp;";*/
							return btnEdit;
						
						
					}
				}]], 
			onBeforeLoad : function(data) {   // 查询参数 
		        data.keyword = $("#text_keword").textbox("getValue");
		        data.type = "text";
			}, 
			onLoadSuccess : function(data) {
				
			}
		});
	},
	
	selectText:function(id){
		
		var url = "/wechat/wechatData/selectText.jhtml";
		$.post(url,
			{ textId: id},
			   function(data){
				if(data){
					if(data.success){
//						var index = window.parent.$("#replyList").children().length+1;
						
						
						var index = $("#indexId").val();
						
						var html = "";
						html += '<div style="border-top:1px solid #e7e7eb;" id="reply_div_'+index+'">';
//			    		html += '<input type="hidden" name="itmeId_'+index+'" value="'+data.id+'">';
			    		html += '<ul>';
			    		
			    		window.parent.$("#itmeId_"+index+"").val(data.id);
			    		var textlist = data.textList;
			    		
			    		
			    		$.each(textlist,function(i,perValue){
			    			
			    			var content = perValue.content;
			    			
			    			html += '	<li style="padding:2px;height:60px;">'+
				    		'		<div style="float:left;"></div>'+
				    		'		<div style="margin-top: 15px;float:left;width: 880px;">'+
				    		'			<em>'+content+'</em>'+
				    		'		</div>'+
				    		'	</li>';
			    			
			    			
			    		});
			    		
			    		html += '		<div style="margin-left: 1000px;position: relative;top: -43;">';
			    		html += '			<a href="#" onclick="AutoReply.delReplys('+index+')">删除</a>';
			    		html += '		</div>';
			    		html += '</ul>';
			    		html += '</div>';
			    		
			    		window.parent.$("#replyList_"+index+"").append(html);
			    		
			    		window.parent.$("#editPanel").dialog('close');
						
					}
				}
		});
		
	}
}



