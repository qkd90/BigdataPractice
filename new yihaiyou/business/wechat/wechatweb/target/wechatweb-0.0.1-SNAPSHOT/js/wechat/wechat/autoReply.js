/**
 * Created by vacuity on 15/11/20.
 */

$(function() {
	AutoReply.init();
});

var AutoReply = {

    init: function(){
    	AutoReply.initTable();
    },

    searchReplys:function(){
    	$('#rule_dg').datagrid('load',{ 
    		accountId : $("#search_account").combobox("getValue"),
	        ruleName  : $("#search_ruleName").textbox("getValue")
    	});
    },
    clearReplys:function(){
    	$("#search_account").combobox("setValue","");
    	$("#search_ruleName").textbox("setValue","");
    	$('#rule_dg').datagrid('load',{ });
    },
    
    initTable:function(){
    	
    	$('#rule_dg').datagrid({   
			title:"自动回复列表",
			data:[],
			url:'/wechat/wechatAutoReply/ruleDataList.jhtml',
			border:true,
			singleSelect:true,
			striped:true,
			pagination:true,
			height:"100%",
			pageList:[ 10, 20, 30 ],
			rownumbers:true,
			fitColumns:true,
			columns : [[
			    { field : 'id', checkbox : 'true' }, 
			    { field : 'name', title : '规则名称', align : 'left', width : 100 ,
			    	formatter : function(value, rowData, rowIndex) {
						return value;
					}	
			    },
			    { field : 'keywordList', title : '关键词', align : "left", width : 100,
			    	formatter : function(value, rowData, rowIndex) {
			    		var rowkeywordList = rowData.keywordList;
			    		var html = '';
//			    			console.log(rowkeywordList);
			    		if(rowkeywordList.length>0){
			    			html += '<ul>';
			    			$.each(rowkeywordList,function(i,perValue){
//			    				console.log(perValue.id);
			    				html += '<li>'+(i+1)+'.';
			    				html += '<a>';
			    				html += perValue.keyword;
			    				html += '</a>';
			    				html += '</li>';
			    				
			    			});
			    			html += '</ul>';
			    		}else{
			    			html += '<span style="color:gray;">请添加关键词</span>';
			    		}
			    		
			    			
			    		
					return html;
				}	
			    }, 
			    { field : 'newsList', title : '回复内容', align : "left",  width : 200,
			    	formatter : function(value, rowData, rowIndex) {
			    		var html = '';
			    		if(rowData.msgTypes =="text"){
			    			var textList = rowData.textList;
			    			
				    		if(textList.length>0){
				    			html += '<ul>';
				    			$.each(textList,function(i,perValue){
				    				html += '<li><span style="color:gray;">文本'+(i+1)+':</span>';
				    				var content = perValue.content;
				    				content = content.replace(/<[^>]+>/g,"");
				    				if(content.length>30){
				    					content = content.substr(0,30)+'......';
				    					html += content;
				    				}else{
				    					html += content;
				    				}
				    				
				    				html += '</li>';
				    			});
				    			html += '</ul>';
				    		}
			    			
			    			
			    		}else if(rowData.msgTypes =="news"){
			    			var newsList = rowData.newsList;
				    		
				    		if(newsList.length>0){
				    			html += '<ul>';
				    			$.each(newsList,function(i,perValue){
				    				html += '<li><span style="color:gray;">图文'+(i+1)+':</span>';
				    				html += perValue.title;
				    				html += '</li>';
				    			});
				    			html += '</ul>';
				    		}
			    		}else{
			    			html += '<span style="color:gray;">请添加回复内容</span>';
			    		}
			    		 return html;
			    	}
			    },
			    { field : 'account', title : '公众号', align : 'center', width : 80,
			    	formatter : function(value, rowData, rowIndex) {
			    		var html = '';
			    		if(rowData.account){
			    			html += rowData.account.account;
			    		}
			    		
			    		return html;
			    	}
			    },
			    { field : 'updateTime', title : '回复内容', align : "right", align : 'center', width : 80},
			    { field : "OPT", title : "操作", align : 'center', width : 80,
					formatter : function(value, rowData, rowIndex) {
						var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='AutoReply.editRules("+rowData.id+")'>编辑</a>&nbsp;";
						var btnDel = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='AutoReply.delRules("+rowData.id+")'>删除</a>&nbsp;";
							return btnEdit+"&nbsp;&nbsp;"+btnDel;
						
					}
				}]], 
			onBeforeLoad : function(data) {   // 查询参数 
		        data.accountId = $("#search_account").combobox("getValue");
		        data.ruleName  = $("#search_ruleName").textbox("getValue");
			}, 
			onLoadSuccess : function(data) {
				
			}
		});
    	
    	
    	
    	
    	
    	$("#search_account")
 		.combobox(
 				{
 					 url:'/wechat/wechatAutoReply/selectAccountList.jhtml', 
 			          editable:true, //不可编辑状态
 			          cache: false,
 			          panelHeight: 'auto',//自动高度适合
 			          valueField:'id',   
 			          textField:'account',
 			          onSelect: function(rec){  
// 			        	  $("#sel_acc_"+index+"").val(rec.id);
 			          },
 			          onLoadSuccess : function() {
// 			        	 $("#selReply_account")
//					 		.combobox('setValue',data.wechatAccount.id);
 						}
 				});
    	
    },
    
    delRules:function(id){
    	
    	var ruleId = id;
    	
   	 $.messager.confirm("操作提示", "此操作会删除当前回复的所有内容，您确定要执行操作吗？", function (data) {  
            if (data) {  
           	 if(ruleId){
            		
            		var url = "/wechat/wechatAutoReply/delRule.jhtml";
            		var data = {
            				ruleId : ruleId	
            		};
            		$.post(url,
            				data,
            			   function(result){
            				if(result.success){
            					show_msg("删除成功！");
            					$('#rule_dg').datagrid('load',{});
            				}
            		});
            		
            	}
            }  
            
        });  
   	
   	
    	
    },
    
    editRules:function(id){
//    	var rulesId = $("#rulesId").val();
    	if(id){
    		
    		var ruleArr = $(id).toArray();
    		
    		$.each(ruleArr,function(i,perValue){
    			var url = "/wechat/wechatAutoReply/findRuleById.jhtml";
    			$.post(url, { ruleId: perValue},
    					   function(data){
    					     if(data.success){
    					    	 var index = i+1;
    					     		var html = '';
    					     		html += '<div class="frm_control_group" style="margin-bottom:5px;margin-top:10px;" >';
    					     		html += '	<div style="float:left;"><label for="" class="frm_label" style="padding-left: 14px;font-size:14px;">规则名:</label></div>';
    					     		html += '		<div class="frm_controls" style="">';
    					     		html += '			<div style="float:left;">';
    					     		html += '			<input type="hidden" name="hidden_rule_'+index+'" value="'+data.id+'">';
    					     		html += '				<span class="frm_input_box"><input class="easyui-textbox" id="rule_'+index+'" name="rule_name_'+index+'" data-options="" style="width:300px" value="'+data.name+'"></span>';
    					     		html += '				<span class="frm_tips" style="color: #8d8d8d;">规则名最多60个字</span>';
    					     		html += '			</div>';
    					     		html += '			<div style="position: relative;margin-left: 900px;margin-bottom: 8px;">';
    					     		html += '				<select id="select_'+index+'" class="easyui-combobox" style="width: 193px" id="select_account" data-options="prompt:\'请选择公众号\'" name="category"  ></select>';
    					     		html += '				<input type="hidden" name="selectAccount_'+index+'" id="sel_acc_'+index+'" value="'+data.wechatAccount.id+'" >';
    					     		html += '			</div>';
    					     		html += '		</div>';
    					     		html += '	<div>';
    					     		html += '<div style="padding-left:14px; margin-bottom:5px;">';
    					     		html += '<div class="easyui-panel" id="keyword_panel_'+index+'" title="关键词" style="width:100%;height:0;padding-left: 14px;padding-right: 14px;" data-options="tools:\'#tt_'+index+'\'">';
    					     		html += '</div> ';
    					     		html += '<div id="tt_'+index+'">';
    					     		html += '<a href="javascript:void(0)" class="icon-add" onclick="AutoReply.addKeyword('+index+')"></a>';
    					     		html += '</div></div>';
    					     		html += '<div style="padding-left:14px;">';
    					     		html += '<div class="easyui-panel" title="回复内容" style="width:100%;height:300px;padding:10px;padding-left: 14px;" data-options="">';
    					     		html += '<div style="margin-bottom:5px;">';
    					     		html += '<a id="btn" href="#" class="easyui-linkbutton" onclick="AutoReply.addText('+index+')" style="margin-right:15px;" data-options="iconCls:\'icon-add\'">添加文本</a>';
    					     		html += '<a id="btn" href="#" class="easyui-linkbutton" onclick="AutoReply.addNews('+index+')" data-options="iconCls:\'icon-add\'">添加图文</a>';
    					     		html += '</div>';
    					     		
    					     		html += '<input type="hidden" id="itmeId_'+index+'" name="itmeId_'+index+'" value="">';
    					     		html += '<div id="replyList_'+index+'">';
    					     		
    					     		if(data.dataItem){
    					     			
    					     			html += '<div style="border-top:1px solid #e7e7eb;" id="reply_div_'+index+'">';
    					     			html += '<ul>';
        					    		
        					    		html += '		<div id="del_div_'+index+'" style="margin-left: 1000px;position: relative;top: -43;">';
        					    		html += '			<a href="#" onclick="AutoReply.delReplys('+index+')">删除</a>';
        					    		html += '		</div>';
        					    		html += '</ul>';
        					    		html += '</div>';
        					    		
    					     		}
    					    		
    					     		
    					     		html += '</div>';
    					     		html += '</div></div>';
    					     		html += '<div style="margin-top:5px;margin-left: 500px;">';
    					     		html += '<a id="btn" href="#" class="easyui-linkbutton" onclick="AutoReply.submitKeyword('+index+')" data-options="iconCls:\'icon-save\'">确定</a> ';
    					     		html += '<a id="btn" href="#" class="easyui-linkbutton" onclick="AutoReply.cancelSub('+index+')" style="margin-left:20px;" data-options="iconCls:\'icon-cancel\'">取消</a> ';
    					     		html += '</div>';
    					     		html += '</div>';
    					     	
    					     	
    					     	$("#accordionId").accordion('add', {
    					     		title: '规则：'+data.name,
    					     		content: html,
    					     		iconCls:'icon-ok',
    					     		height:450,
    					     		selected: true,
    					     		tools:[{
    									iconCls:'icon-cancel',
    									handler:function(){
    										$('#accordionId').accordion('remove', 0);
//    										$('#dg').datagrid('reload');
    									}
    								}]
    					     	});
    					     	
    					     	if(data.dataItem){
    					     		if(data.dataItem.type==="text"){
    					     			console.log(data.dataItem.id);
    					     			
    					     			$("#itmeId_"+index+"").val(data.dataItem.id);
        					     		var htmlText = "";
        								$.post("/wechat/wechatAutoReply/findText.jhtml", { itemId: data.dataItem.id, type: "text" },
        					 				   function(result){
        						    				$.each(result,function(i,perValue){
        						    					var str = "";
        						    					var content = perValue.content;
//        								    			if(content.length>10){
//        								    				content = content.substr(0,30)+"";
//        								    			}
        						    					htmlText += '	<li style="padding:2px;height:60px;">'+
        									    		'		<div style="float:left;"></div>'+
        									    		'		<div style="margin-top: 15px;float:left;width: 880px;">'+
        									    		'			<em>'+content+'</em>'+
        									    		'		</div>'+
        									    		'	</li>';
        					    					});
        						    				$("#del_div_"+index+"").before(htmlText);
        					 				   });
//        								return htmlStr;
        							}else{
        								var htmlNews = "";
        								$("#itmeId_"+index+"").val(data.dataItem.id);
        								$.post("/wechat/wechatAutoReply/findNews.jhtml", {itemId: data.dataItem.id, type: "news" },
        					  				   function(result){
        					    					$.each(result,function(i,perValue){
        					    						
        					    						var title = perValue.title;
        								    			if(title.length>10){
        								    				title = title.substr(0,30)+"...";
        								    			}
        					    						htmlNews += '	<li style="padding:2px;height:60px;">'+
        									    		'		<div style="float:left;"><img src="'+perValue.img_path+'" alt="" width="50px" height="50px"></div>'+
        									    		'		<div style="margin-top: 15px;float:left;">'+
        									    		'			<em>'+title+'</em>'+
        									    		'		</div>'+
        									    		'	</li>';
        					    						
        					    						
        					    					});
        					    					$("#del_div_"+index+"").before(htmlNews);	
        					  				   });
//        								return htmlStr;    
        							}
    					     	}
    					     	
    					     	
    					     	if(data.id){
    					     		$.post("/wechat/wechatAutoReply/findkeywordList.jhtml", {ruleId: data.id},
     					  				   function(result){
    					     			
	    					     			$.each(result,function(i,perValue){
					    						i = i+1;
	    					     				var ht = '<div style="height:22px;border-top:1px solid #e7e7eb;padding:15px;" name="'+i+'" id="div_'+index+'_'+(i)+'">';
	    					    				ht += '<div style="float:left;"><em id="keyword_'+index+'_'+(i)+'" name="em_keyword_'+index+'">';
//	    					    				ht += "<input type='hidden' id='keyword_input_"+index+"_"+(i)+"' name='keyword_"+index+"' value='"+perValue.keyword+"'>";
	    					    				ht += perValue.keyword;
	    					    				ht += '</em></div>';
	    					    				ht += "<div style='margin-left:900px;'>";
	    					    				ht += "<select id='select_"+index+"_"+(i)+"' onchange='AutoReply.selectPipei("+index+","+(i)+")'>";
	    					    				ht += "<option value='explicit'>";
	    					    				ht += "全匹配";
	    					    				ht += "</option>";
	    					    				ht += "<option value='implicit'>";
	    					    				ht += "模糊";
	    					    				ht += "</option>";
	    					    				ht += "</select>";
	    					    				ht += "<input type='hidden' id='select_pi_"+index+"_"+(i)+"' name='selet_pi_"+index+"' value='"+perValue.matchType+"'>";
	    					    				ht += "<a href='#' class='easyui-linkbutton' style='margin-left: 20px;' onclick='AutoReply.editKeyword("+index+","+(i)+")' data-options=''>编辑</a>";
	    					    				ht += "<a href='#' class='easyui-linkbutton' style='margin-left: 20px;' onclick='AutoReply.delKeyword("+index+","+(i)+")' data-options=''>删除</a>";
	    					    				ht += '</div></div>';
	    					    				$("#keyword_panel_"+index+"").append(ht);
	    					    				$("#select_"+index+"_"+(i)+"").find("option[value="+perValue.matchType+"]").attr("selected",true); 
					    					});
    					     			
    					     			}
    					     		);
    					     	}
    					     	
    					     	
    					     	
    					     	$("#select_"+index+"")
    					 		.combobox(
    					 				{
    					 					 url:'/wechat/wechatAutoReply/selectAccountList.jhtml', 
    					 			          editable:true, //不可编辑状态
    					 			          cache: false,
    					 			          panelHeight: 'auto',//自动高度适合
    					 			          valueField:'id',   
    					 			          textField:'account',
    					 			          onSelect: function(rec){  
    					 			        	  $("#sel_acc_"+index+"").val(rec.id);
    					 			          },
    					 			          onLoadSuccess : function() {
    					 			        	 $("#select_"+index+"")
    				    					 		.combobox('setValue',data.wechatAccount.id);
    					 						}
    					 				});
    					    	 
    					    	 
    					    	 
    					    	 
    					    	 
    					    	 
    					     }
    					}
    				);
    			
    			
    			
    		});
    		
//    		console.log(urleArr);
    		
    	}
    	
    	
    	
    },
    
    
    initReplyList:function(id,type){
    	
    	var htmlStr = ""; 
    	
		if(type==="text"){
			$.post("/wechat/wechatAutoReply/findText.jhtml", { itemId: id, type: "text" },
 				   function(result){
	    				$.each(result,function(i,perValue){
	    					var str = "";
	    					str += '	<li style="padding:2px;height:60px;">'+
				    		'		<div style="float:left;"></div>'+
				    		'		<div style="margin-top: 15px;float:left;">'+
				    		'			<em>'+perValue.content+'</em>'+
				    		'		</div>'+
				    		'	</li>';
	    					htmlStr = htmlStr + str;
    						console.log("textcontent:"+perValue.content);
    						console.log("htmlStr:"+htmlStr);
    					});
	    				
 				   });
			return htmlStr;
		}else{
			$.post("/wechat/wechatAutoReply/findNews.jhtml", {itemId: id, type: "news" },
  				   function(result){
    					$.each(result,function(i,perValue){
    						
    						htmlStr += '	<li style="padding:2px;height:60px;">'+
				    		'		<div style="float:left;"><img src="'+perValue.img_path+'" alt="" width="50" heith="50"></div>'+
				    		'		<div style="margin-top: 15px;float:left;">'+
				    		'			<em>'+perValue.title+'</em>'+
				    		'		</div>'+
				    		'	</li>';
    						
    						
    					});
    						
  				   });
			return htmlStr;    
		}
		
		
    	
    },
    
    initCom:function(index){
    	
    	$("#select_"+index+"")
		.combobox(
				{
					 url:'/wechat/wechatAutoReply/selectAccountList.jhtml', 
			          editable:true, //不可编辑状态
			          cache: false,
			          panelHeight: 'auto',//自动高度适合
			          valueField:'id',   
			          textField:'account',
			          onSelect: function(rec){ 
			        	  
			          },
			          onLoadSuccess : function() {
			        	  
						}
				});
    	
    },
    
    selectPipei:function(index,key_index){
    	var value = $("#select_"+index+"_"+key_index+"").val();
    	$("#select_pi_"+index+"_"+key_index+"").val(value);
    	
    },
    
    addReplyAcc:function(){
    	
    	var index = ($("#accordionId").children().length)+1;
    	if(index<=1){
    	
	    	var html = '';
	    		/*html += '<div title="规则1" data-options="iconCls:'icon-ok'"  style="overflow:auto;padding:10px;height:200px;">';*/
	    		html += '<div class="frm_control_group" style="margin-bottom:5px;margin-top:10px;" >';
	    		html += '	<div style="float:left;"><label for="" class="frm_label" style="padding-left: 14px;font-size:14px;">规则名:</label></div>';
	    		html += '			<input type="hidden" name="hidden_rule" value="0">';
	    		html += '		<div class="frm_controls" style="">';
	    		html += '			<div style="float:left;">';
	    		html += '				<span class="frm_input_box"><input class="easyui-textbox" id="rule_'+index+'" name="rule_name_'+index+'" data-options="" style="width:300px"></span>';
	    		html += '				<span class="frm_tips" style="color: #8d8d8d;">规则名最多60个字</span>';
	    		html += '			</div>';
	    		html += '			<div style="position: relative;margin-left: 900px;margin-bottom: 8px;">';
	    		html += '				<select id="select_'+index+'" class="easyui-combobox" style="width: 193px" id="select_account" data-options="prompt:\'请选择公众号\'" name="category"  ></select>';
	    		html += '				<input type="hidden" name="selectAccount_'+index+'" id="sel_acc_'+index+'" value="" >';
	    		html += '			</div>';
	    		html += '		</div>';
	    		html += '	<div>';
	    		html += '<div style="padding-left:14px; margin-bottom:5px;">';
	    		html += '<div class="easyui-panel" id="keyword_panel_'+index+'" title="关键词" style="width:100%;height:0;padding-left: 14px;padding-right: 14px;" data-options="tools:\'#tt_'+index+'\'">';
	    		html += '</div> ';
	    		html += '<div id="tt_'+index+'">';
	    		html += '<a href="javascript:void(0)" class="icon-add" onclick="AutoReply.addKeyword('+index+')"></a>';
	    		html += '</div></div>';
	    		html += '<div style="padding-left:14px;">';
	    		html += '<div class="easyui-panel" title="回复内容" style="width:100%;height:300px;padding:10px;padding-left: 14px;" data-options="">';
	    		html += '<div style="margin-bottom:5px;">';
	    		html += '<a id="btn" href="#" class="easyui-linkbutton" onclick="AutoReply.addText('+index+')" style="margin-right:15px;" data-options="iconCls:\'icon-add\'">添加文本</a>';
	    		html += '<a id="btn" href="#" class="easyui-linkbutton" onclick="AutoReply.addNews('+index+')" data-options="iconCls:\'icon-add\'">添加图文</a>';
	    		html += '</div>';
	    		html += '<input type="hidden" id="itmeId_'+index+'" name="itmeId_'+index+'" value="">';
	    		html += '<div id="replyList_'+index+'">';
	    		html += '</div>';
	    		html += '</div></div>';
	    		html += '<div style="margin-top:5px;margin-left: 500px;">';
	    		html += '<a id="btn" href="#" class="easyui-linkbutton" onclick="AutoReply.submitKeyword('+index+')" data-options="iconCls:\'icon-save\'">确定</a> ';
	    		html += '<a id="btn" href="#" class="easyui-linkbutton" onclick="AutoReply.cancelSub('+index+')" style="margin-left:20px;" data-options="iconCls:\'icon-cancel\'">取消</a> ';
	    		html += '</div>';
	    		html += '</div>';
	    	
	    	$("#accordionId").accordion('add', {
	    		title: '规则'+index,
	    		content: html,
	    		iconCls:'icon-ok',
	    		height:450,
	    		selected: true,
	    		tools:[{
					iconCls:'icon-cancel',
					handler:function(){
						$('#accordionId').accordion('remove', 0);
//						$('#dg').datagrid('reload');
					}
				}]
	    	});
	
	    	
	    	$("#select_"+index+"")
			.combobox(
					{
						 url:'/wechat/wechatAutoReply/selectAccountList.jhtml', 
				          editable:true, //不可编辑状态
				          cache: false,
				          panelHeight: 'auto',//自动高度适合
				          valueField:'id',   
				          textField:'account',
				          onSelect: function(rec){  
				        	  $("#sel_acc_"+index+"").val(rec.id);
				          },
				          onLoadSuccess : function() {
				        	  
							}
					});
    	}else{
    		show_msg("请先保存当前自动回复规则！");
    	}
    },
    
    
    submitKeyword:function(index){
    	
    	var ruleName = $("input[name='rule_name_"+index+"']").val();
    	var ruleId = $("input[name='hidden_rule_"+index+"']").val();
    	var selectAccount = $("input[name='selectAccount_"+index+"']").val();
    	var keywordList = $("input[name='keyword_"+index+"']");
    	var emkeywordList = $("em[name='em_keyword_"+index+"']");
    	var selet_piList = $("input[name='selet_pi_"+index+"']");
    	var itmeId = $("input[name='itmeId_"+index+"']").val();
    	
    	
    	var data = "{";
    	var keyArry = "[";
    	var keyStr = ""
    	$.each(emkeywordList,function(i,perValue){
    		var keyword = $(perValue).html();
    		var pipei = $(selet_piList[i]).val();
    		
    		keyStr = keyStr+ "{";
    		keyStr = keyStr+ "keyword:\""+keyword +"\",";
    		keyStr = keyStr+ "pipei:\""+pipei +"\"},";
    		
    		
    		
    	});
    	
    	
    	
    	keyStr = keyStr.substring(0, keyStr.length-1);
    	keyArry = keyArry+ keyStr +"]";
    	data += "ruleName:\""+ruleName+"\",";
    	data += "ruleId:\""+ruleId+"\",";
    	data += "accId:\""+selectAccount+"\",";
    	data += "itmeId:\""+itmeId+"\",";
    	data += "keyArry:"+keyArry+"}";
    	if(ruleName.length>0&&ruleName.length<60){
    		if(selectAccount.length>0){
    			var url = "/wechat/wechatAutoReply/submitKeyword.jhtml";
        		$.post(url,
        				{"data":data},
        			   function(result){
        				if(result.success){
        					show_msg("保存成功！");
        					$('#rule_dg').datagrid('load',{});
        					$('#accordionId').accordion('remove', 0);
        				}
        		});
        	}else{
        		show_msg("请选择公众号！");
        	}
    		
        	
    	}else if(ruleName.length==0){
    		show_msg("请完善规则名称！");
    	}else if(ruleName.length>=60){
    		show_msg("规则名称数字不能超过60个字！");
    	}
    	
    },
    
    cancelSub:function(index){
    	$('#accordionId').accordion('remove', 0);
    },
    
    addText:function(index){
    	
    	var replyList = $("#replyList_"+index+"").children().length;
    	if(replyList<1){
	    	//明天继续
			var url = "/wechat/wechatAutoReply/addTextJsp.jhtml?index="+index;
		    // 打开编辑窗口
			var ifr = $("#editPanel").children()[0];
			$(ifr).attr("src", url);
			$("#editPanel").dialog({
				 title:'选择文本素材',
				 zIndex:100
			});
			$("#editPanel").dialog("open");
	    		
    	}else{
    		show_msg("只能增加一条回复内容！");
    	}
    	
    	
    },
    
    addNews:function(index){
    	
    	var replyList = $("#replyList_"+index+"").children().length;
    	if(replyList<1){
	    	//明天继续
			var url = "/wechat/wechatAutoReply/addNewsJsp.jhtml?index="+index;
		    // 打开编辑窗口
			var ifr = $("#editNews").children()[0];
			$(ifr).attr("src", url);
			$("#editNews").dialog({
				 title:'选择图文素材',
	//			 width:1000,    
	//			 height:100
	//			 zIndex:100
			});
			$("#editNews").dialog("open");
    	}else{
    		show_msg("只能增加一条回复内容！");
    	}
    	
    },
    
    editKeyword:function(index,key_index){
    	
    	
    	var keyword = $("#keyword_"+index+"_"+key_index+"").html();
    	
    	
    	//明天继续
		var url = "/wechat/wechatAutoReply/editKeyword.jhtml?index="+index+"&keyword="+keyword+"&key_index="+key_index;
	    // 打开编辑窗口
		var ifr = $("#editPanel").children()[0];
		$(ifr).attr("src", url);
		var top=(screen.height-750)/2;    
		var left=(screen.width-800)/2;
		$("#editPanel").dialog({
			 title:'修改关键词',
			 width: 600,    
			 height: 510,
			 left:left,
			 top:top
//			 zIndex:100
		});
		$("#editPanel").dialog("open");
    },
    delKeyword:function(index,key_index){
    	
    	$("#div_"+index+"_"+key_index+"").remove();
    	
    },
    
    
    addKeyword:function(index){
    		
    		var url = "/wechat/wechatAutoReply/addKeyword.jhtml?index="+index;
    	// 打开编辑窗口
    		var ifr = $("#editPanel").children()[0];
    		$(ifr).attr("src", url);
    		
    		var top=(screen.height-750)/2;    
    		var left=(screen.width-800)/2;
    		
    		$("#editPanel").dialog({
    			 title:'添加关键词',
    			 width: 600,    
    			 height: 510,
    			 left:left,
    			 top:top
    			 
    		});
    		
    		$("#editPanel").dialog("open");
    },
    
    delReplys:function(index){
    	
    	$("#reply_div_"+index+"").remove();
    	$("#itmeId_"+index+"").val("");
    },
}



