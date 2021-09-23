var Trader=$.fn.extend({
	doSearch:function(){
		$('#dg').datagrid('load', {
			'trader.tradername' : $('#memberName').val(),
			'trader.tradercode' : $('#memberCode').val(),
			'trader.startTime' : $('#startTime').val(),
			'trader.endTime' : $('#endTime').val()
		});
	},
	doSearch2:function(){
		$('#dg2').datagrid('load', {
			'tabChange.changeType' : $('#changeType').val(),
			'tabChange.startTime' : $('#startTime2').val(),
			'tabChange.endTime' : $('#endTime2').val()
		});
	},
	//导入会员
	importChecked:function(flg){
		var rows=$("#dg").datagrid("getChecked");
		var ids="";
		Trader.openPropress();
		if(flg=="1"){
			$.each(rows, function(n, value) {
				var len=rows.length;
				ids+=value.traderid;
				if(n!=len-1){
					ids+=",";
				}
			});
			if(ids==""){
				$.messager.alert("温馨提示", "请选择要导入的数据!");
				Trader.closePropress();
				return false;
			}
		}
		//将要导入的sp会员id,保存到表单中
		$("#ids").attr("value",ids);
		$("#flg").attr("value",flg);
		Trader.formSubmit("fm",'/trader/trader/importMember.jhtml');
	},
	//同步会员
	synchronous:function(flg){
		Trader.openPropress();
		var rows=$("#dg2").datagrid("getChecked");
		var ids="";
		if(flg=="1"){
			$.each(rows, function(n, value) {
				var len=rows.length;
				ids+=value.tabId;
				if(n!=len-1){
					ids+=",";
				}
			});
			if(ids==""){
				Trader.closePropress();
				$.messager.alert("温馨提示", "请选择要同步的数据!");
				return false;
			}
		}
		//将要导入的sp会员id,保存到表单中
		$("#ids2").attr("value",ids);
		$("#flg2").attr("value",flg);
		Trader.formSubmit("fm2",'/trader/trader/synchronous.jhtml');
	},
	formSubmit:function(fmid,url){
		$('#'+fmid).form('submit', {
			url : url,
			success : function(result) {
				Trader.closePropress();
				var result = eval('(' + result + ')');
				$.messager.show({
					title : '温馨提示',
					msg : result.errorMsg
				});
			}
		});
	},
	tabChange:function(){
		//tab变换时显示查询结果
		$('#tt').tabs({
		    border:false,
		    onSelect:function(title){
		    	var tab = $('#tt').tabs('getSelected');
		    	var index = $('#tt').tabs('getTabIndex',tab);
		    	if(index==1){
		    		$('#dg2').datagrid({
			    	    url:'/trader/trader/searchChange.jhtml?tabChange.tabName=trader',
			    	    columns:[[
							{field:'ck',checkbox:true},
			    	        {field:'changeType',title:'异动类型',width:80,
								formatter : function(value, row, index) { 
									if(row.changeType==1){
										return "修改";
									}else if(row.changeType==2){
										return "新增";
									}else if(row.changeType==3){
										return "删除";
									}
								}
							},
			    	        {field:'changeTime',title:'异动时间',width:120},
			    	        {field:'tabName',title:'异动表名',width:80},
			    	        {field:'tradercode',title:'用户编号',width:150,
			    	        	formatter : function(value, row, index) {
			    	        		if(row.trader!=null){
			    	        			return row.trader.tradercode;
			    	        		}
			    	        	}
			    	        },
			    	        {field:'tradername',title:'用户名',width:150,
			    	        	formatter : function(value, row, index) {
			    	        		if(row.trader!=null){
			    	        			return row.trader.tradername;
			    	        		}
			    	        	}
			    	        },
			    	        {field:'lev',title:'等级',width:60,
			    	        	formatter : function(value, row, index) {
			    	        		if(row.trader!=null){
			    	        			return row.trader.lev;
			    	        		}
			    	        	}
			    	        },
			    	        {field:'typeName',title:'用户来源',width:100,
			    	        	formatter : function(value, row, index) {
			    	        		if(row.trader!=null){
			    	        			return row.trader.typeName;
			    	        		}
			    	        	}
			    	        },
			    	        {field:'contactor',title:'联系人',width:70,
			    	        	formatter : function(value, row, index) {
			    	        		if(row.trader!=null){
			    	        			return row.trader.contactor;
			    	        		}
			    	        	}
			    	        },
			    	        {field:'phone',title:'手机号码',width:150,
			    	        	formatter : function(value, row, index) {
			    	        		if(row.trader!=null){
			    	        			return row.trader.phone;
			    	        		}
			    	        	}
			    	        }
			    	    ]]
			    	});
		    	}
		    }
		});
	},
	openPropress:function(){
		var win = $.messager.progress({
		    title:'加载中...',
		    msg:'正在提交中,请稍候.',
		});
	},
	closePropress:function(){
		$.messager.progress('close');
	},
	sysMemberType:function(){
		doPost("","/trader/trader/synchronousType.jhtml");
	}
});
$(function(){
	Trader.tabChange();
});