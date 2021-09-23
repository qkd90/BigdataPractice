var TicketValidateInfo = {
		
		init : function() {
			TicketValidateInfo.initDialog();
			TicketValidateInfo.initCom();
			TicketValidateInfo.initGrid();
		},
		
		initDialog : function() {
			$("#inform_dialog").dialog('close');
		},
		
		addValidateInfo : function() {
			$('#inform_dialog').dialog({    
			    title: '录入信息',    
			    width: 350,    
			    height: 280,    
			    closed: false,    
			    cache: false,    
			    modal: true   
			});
			$("#inform_dialog").dialog('open');
		},
		
		productList:function(param, success, error) {
			var q = param.q || '';
			if (q.length <= 1) {
				return false
			}
			$.ajax({
				url : '/ticketValidateInfo/ticketValidateInfo/getProductList.jhtml',
				dataType : 'json',
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				method : 'POST',
				data : {
					featureClass : "P",
					style : "full",
					maxRows : 20,
					name_startsWith : q
				},
				success : function(data) {

					
					var items = $.map(data, function(item) {
						return {
							id : item.id,
							name : item.name,
						};
					});
					success(items);
				},
				error : function() {
					error.apply(this, arguments);
				}
			});
		},
		
		initCom : function() {
			$("#select_product")
			.combobox(
					{
						onSelect : function(param) {
							var name = param.name;
							$("#ipt_productName").val(name);
						}
					});
		},
		
		// 表格查询
		doSearch : function() {
			$('#dg').datagrid({url:'/ticketValidateInfo/ticketValidateInfo/list.jhtml'});
		},
		
		initGrid : function() {
			
			$('#dg').datagrid({
				fit:true,
			    url:'/ticketValidateInfo/ticketValidateInfo/list.jhtml',
			    pagination : true,
				pageList : [ 10, 20, 30 ],
				fitColumns : true,
				singleSelect : false,
				striped : true,// 斑马线
			    columns:[[    
			        {field : 'id', checkbox : 'true'},    
			        {field:'productName',title:'门票名称',width:100},    
			        {field:'customerName',title:'姓名',width:100},    
			        {field:'customerPhone',title:'联系电话',width:100},    
			        {field:'amount',title:'数量',width:100},    
			        {field:'origin',title:'来源',width:100},    
			        {field:'createTime',title:'日期',width:100}  
			    ]],
				toolbar: '#addTool',
				onBeforeLoad : function(data) {   // 查询参数 
			        data.customerPhone = $("#ipt_sech_customerPhone").textbox("getValue");
			        data.createTimeStr = $("#ipt_sech_createTime").datebox("getValue");
				},
			});  

		},
		
		saveBeforeValidate : function() {
			
			var flag = true;
			
			var inputArray = $(".input");
			
			$.each(inputArray, function(i, perValue){
				
				var per = "";
				if (i != 0) {
					per = $(perValue).textbox("getValue");
				} else {
					per = $(perValue).combobox("getValue");
				}
				
				if (!per && per.length < 10) {
					flag = false;
					$(perValue).focus();
					TicketValidateInfo.erroMsg(i);
					return flag;
				}
				
				
			});
			
			return flag;
			
		},
		
		erroMsg : function(i) {
			if (i == 0) {
				show_msg("请完善产品名称！");
			} else if(i == 1) {
				show_msg("请完善姓名！");
			} else if(i == 2) {
				show_msg("请完善手机号！");
			} else if(i == 3) {
				show_msg("请完善数量！");
			}else if(i == 4) {
				show_msg("请完善来源！");
			}
		},
		
		sendInform : function() {
			// 保存表单
			$('#inform_form').form('submit', {
				url : "/ticketValidateInfo/ticketValidateInfo/saveValidateTicketInfo.jhtml",
				onSubmit : function() {
					var isValid = TicketValidateInfo.saveBeforeValidate();
					return isValid;
				},
				success : function(result) {
					
					var result = eval('(' + result + ')');
					if(result.id){
						show_msg("验证信息录入成功！");
						$('#inform_form').form("clear");
						$("#inform_dialog").dialog('close');
						$("#dg").datagrid('load');
					}else{
						show_msg("验证信息录入失败");
					}
				}
			});
		},
		
		delValidateInof : function() {
			
			var rows = $('#dg').datagrid('getChecked');  
			if (rows.length > 0) {
				
				var ids = [];
				
				$.each(rows, function(i, perValue){
					
					
					ids[i] = perValue.id;
				}); 
				
				$.post("/ticketValidateInfo/ticketValidateInfo/delValidateInfos.jhtml", 
						{"ids" : ids.toString()},
						function(data){
							if(data.success){
								show_msg("删除成功！");
								$("#dg").datagrid('load');
							}else{
								show_msg("删除门票失败");
							}
							
						},'json'
				);
				
				
				console.log(ids);
				
			} else {
				show_msg("请选择需要删除的数据！");
			}
			
		}
		
		
};

$(function(){
	TicketValidateInfo.init();
});

