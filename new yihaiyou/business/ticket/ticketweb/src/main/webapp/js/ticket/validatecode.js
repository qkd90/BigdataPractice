var ValidateCode = {
		init:function(){
			ValidateCode.initDgList();
			ValidateCode.initBody();
		},
		
		 Save_Excel:function() {

			 $("#editPanel3").dialog({
				 width: 270,
				 height: 170,
				 top:'center',
				 left:'center',
				 closed: false,
				 cache: false,
				 modal: true,
				 onBeforeClose:function() {
					 $("#startTime").val("");
					 $("#endTime").val("");
				 },
				 buttons:[
					 {
						 text:'确定导出',
						 handler:function(){
							 var data = {
								 startTimeStr:$("#startTime").val(),
								 endTimeStr:$("#endTime").val()
							 }
							 var url = '/scemanager/scemanager/testExcel.jhtml';
							 $.messager.progress({
								 title:'温馨提示',
								 text:'数据处理中,请勿关闭页面...'
							 });
							 $.post(url, data, function(result) {
									 if (result.success) {
										 window.location = "/static" + result.downloadPath; //执行下载操作
										 show_msg(result.info);
										 $.messager.progress("close");
										 $("#editPanel3").dialog("close");
									 } else {
										 show_msg(result.info);
										 $.messager.progress("close");
									 }
								 }
							 );

						 }
					 },
					 {
						 text:'取消',
						 handler:function(){
							 $("#editPanel3").dialog("close");
						 }

					 }
				 ]
			 });
			 $("#editPanel3").dialog("open");
		 },
		
		clearForm:function(){
			$('#searchform').form('clear');
			$("#dg").datagrid('load', {});
		},




		validate:function(){
			var input_validate = $("#input_validate").val();
			var input_count = $("#input_count").val();
			if(input_validate){
				$.post('/scemanager/scemanager/checkVliCode.jhtml', {
					'validateCode' : input_validate, 'validateCount' : input_count
				}, function(result) {
					if (result.success) {
						$("#detail_dialog").dialog({
							closable: false,
							modal: true,
							title: '门票信息',
							buttons:[{
								text:'确定',
								handler:function(){
									var input_validate = $("#input_validate").val();
									$.messager.progress({
										title:'温馨提示',
										text:'数据处理中,请勿关闭页面...'
									});
									$.post('/scemanager/scemanager/valCode.jhtml', {
										'validateCode' : input_validate, 'validateCount' : input_count
									}, function(result) {
										$.messager.progress("close");
										console.log(result);
										if (result.success) {
											$("#detail_dialog").dialog("close");
											$("#validate_dialog").dialog("close");
											show_msg(result.errorMsg);
											$("#dg").datagrid("reload");
										} else {
											show_msg(result.errorMsg);
										}
									});
								}
							},{
								text:'关闭',
								handler:function(){
									$("#td_detail").html("");
									$("#validate_dialog").dialog("close");
									$("#detail_dialog").dialog("close");
								}
							}]
						});

						var html = "";
						var validateCount = result.validateCount;
						if (result.orderNo) {
							var url = "/outOrder/outOrder/getProTicketList.jhtml";
							var data = {
								orderNO:result.orderNo,
								productId:result.productId
							}
							$.post(url, data,
								function(result) {
									if (result.success) {
										var detail = result.detail;
										//var ticketList = result.ticketList;
										html += '<tr>';
										html += '<td style="width: 60%;padding: 3px;">';
										html +=  detail.ticketName
										html += '</td>';
										html += '<td style="width: 20%;text-align: center;padding: 2px;">';
										if (detail.type == "adult") {
											html +=  '成人票';
										}
										if (detail.type == "student") {
											html +=  '学生票';
										}
										if (detail.type == "child") {
											html +=  '儿童票';
										}
										if (detail.type == "oldman") {
											html +=  '老年票';
										}
										if (detail.type == "taopiao") {
											html +=  '套票';
										}
										if (detail.type == "team") {
											html +=  '团体票';
										}
										if (detail.type == "other") {
											html +=  '其他';
										}
										html += 'x';
										html +=  detail.count - detail.refundCount;
										html += '</td>';
										html += '<td style="width: 20%;text-align: center;padding: 2px;">';
										html +=  '验票<strong style="color: orange;">';
										html +=  validateCount;
										html += '</strong>张</td>';
										html += '</tr>';
										$("#td_detail").html(html);
									}
								}
							);
						} else {
							html += '<tr>';
							html += '<td style="width: 80%;padding: 3px;">';
							html += result.ticketName
							html += '</td>';
							html += '<td style="width: 10%;text-align: center;padding: 3px;">';
							html += 'x';
							html += '</td>';
							html += '<td style="width: 10%;text-align: center;padding: 3px;">';
							html += result.orderCount;
							html += '</td>';
							html += '</tr>';
							$("#td_detail").html(html);
						}
						$("#detail_dialog").dialog("open");

					} else {
						show_msg(result.errorMsg);
					}
				});
				
			}else{
				show_msg("请输入验证码！");
			}
		},
		
		
		// 初始化表格数据
		initDgList : function() {
			$("#dg").datagrid({
				//title : '门票验证列表',
//				height : 445,
				url : '/ticket/ticketValidateCode/getValidateCodeList.jhtml',
				pagination : true,
				pageList : [ 10, 20, 30 ],
				rownumbers : true,
				fitColumns : true,
				fit : true,
				singleSelect : false,
				striped : true,// 斑马线
				ctrlSelect : true,// 组合键选取多条数据：ctrl+鼠标左键
				columns : [ [  {
					field : 'orderNo',
					title : '订单编号',
					width : 100,
					sortable : false
				},{
					field : 'productName',
					title : '产品名称',
					width : 250,
					sortable : false
				}, {
					field : 'supplierName',
					title : '销售商名称',
					width : 100,
					sortable : false
				}, {
					field : 'buyerName',
					title : '订单用户',
					width : 100,
					sortable : false
				}, {
					field : 'buyerMobile',
					title : '手机',
					width : 100,
					sortable : false
				}, {
					field : 'opt',
					title : '票型',
					width : 150,
					sortable : false, formatter: function(value, rowData) {
						var html = '';
						var codeList = rowData.proValidCodeList;
						if (codeList && codeList.length > 0) {
							html += "<ul>";
							$.each(codeList ,function(i,perValue){
								if((codeList.length-1)!=i){
									html += "<li style='border-bottom:1px #7F99BE dotted;padding: 5px 0px 5px 0px;'>"+ perValue.productTypeName +"</li>";
								}else{
									html += "<li style='padding: 5px 0px 5px 0px;'>"+ perValue.productTypeName +"</li>";
								}
							});
							html += "</ul>";
							return html;

						} else {
							return "-";
						}
					}
				}, {
					field : 'code',
					title : '验证码',
					width : 100,
					sortable : false, formatter: function(value, rowData) {
						var html = '';
						var codeList = rowData.proValidCodeList;
						if (codeList && codeList.length > 0) {
							html += "<ul>";
							$.each(codeList ,function(i,perValue){
								if((codeList.length-1)!=i){
									if (perValue.used == 1) {
										html += "<li style='border-bottom:1px #7F99BE dotted;padding: 5px 0px 5px 0px;'>"+ perValue.code +"</li>";
									} else if (perValue.used == -1) {
										html += "<li style='border-bottom:1px #7F99BE dotted;padding: 5px 0px 5px 0px;'>验证码无效</li>";
									} else {
										html += "<li style='border-bottom:1px #7F99BE dotted;padding: 5px 0px 5px 0px;'>-</li>";
									}
								}else{
									if (perValue.used == 1) {
										html += "<li style='padding: 5px 0px 5px 0px;'>"+ perValue.code +"</li>";
									} else if (perValue.used == -1) {
										html += "<li style='padding: 5px 0px 5px 0px;'>验证码无效</li>";
									} else {
										html += "<li style='padding: 5px 0px 5px 0px;'>-</li>";
									}
								}
							});
							html += "</ul>";
							return html;

						} else {
							return "-";
						}
					}
				}, {
					field : 'used',
					title : '状态',
					width : 100,
					sortable : false, formatter: function(value, rowData) {
						var html = '';
						var codeList = rowData.proValidCodeList;
						if (codeList && codeList.length > 0) {
							html += "<ul>";
							$.each(codeList ,function(i,perValue){
								if((codeList.length-1)!=i){
									if (perValue.used == 1) {
										html += "<li style='border-bottom:1px #7F99BE dotted;padding: 5px 0px 5px 0px;'>已验证</li>";
									} else if (perValue.used == -1) {
										html += "<li style='border-bottom:1px #7F99BE dotted;padding: 5px 0px 5px 0px;'>验证码无效</li>";
									} else {
										html += "<li style='border-bottom:1px #7F99BE dotted;padding: 5px 0px 5px 0px;'>未验证</li>";
									}
								}else{
									if (perValue.used == 1) {
										html += "<li style='padding: 5px 0px 5px 0px;'>已验证</li>";
									} else if (perValue.used == -1) {
										html += "<li style='padding: 5px 0px 5px 0px;'>验证码无效</li>";
									} else {
										html += "<li style='padding: 5px 0px 5px 0px;'>未验证</li>";
									}
								}
							});
							html += "</ul>";
							return html;

						} else {
							return "-";
						}
					}
				}, {
					field : 'validateBy',
					title : '操作人',
					width : 100,
					sortable : false, formatter: function(value, rowData) {
						var html = '';
						var codeList = rowData.proValidCodeList;
						if (codeList && codeList.length > 0) {
							html += "<ul>";
							$.each(codeList ,function(i,perValue){

								if((codeList.length-1)!=i){
									if (perValue.validateUser) {
										html += "<li style='border-bottom:1px #7F99BE dotted;padding: 5px 0px 5px 0px;'>"+ perValue.validateUser.userName +"</li>";
									} else {
										html += "<li style='border-bottom:1px #7F99BE dotted;padding: 5px 0px 5px 0px;'>-</li>";
									}
								}else{
									if (perValue.validateUser) {
										html += "<li style='padding: 5px 0px 5px 0px;'>"+ perValue.validateUser.userName +"</li>";
									} else {
										html += "<li style='padding: 5px 0px 5px 0px;'>-</li>";
									}
								}
							});
							html += "</ul>";
							return html;

						} else {
							return "-";
						}
					}
				}
				]],
				onBeforeLoad: function(data) {
					data['vcType'] = $("#vcType").val();
					if ($("#ticNameid").textbox('getValue')) {
						data['proValidCode.productName'] = $("#ticNameid").textbox('getValue');
					}
					if ($("#com_used").combobox('getValue')) {
						data['proValidCode.used'] = $("#com_used").combobox("getValue");
					}
					if ($("#orderNum").textbox('getValue')) {
						data['proValidCode.orderNo'] = $("#orderNum").textbox('getValue');
					}
				},
				toolbar : "#tb"
			});
		},

	checkValidateDetail: function(index, id) {

		$("#editPanel2").dialog({
			width: 600,
			height: 400,
			top:10,
			left:'25%',
			closed: false,
			cache: false,
			modal: true,

			onBeforeOpen:function() {
				$("#dg").datagrid("selectRow", index);
				var row = $("#dg").datagrid("getSelected");

				$("#proName").html(row.name);

				var url = '/scemanager/scemanager/getTicketValidataInfo.jhtml?productValidateId=' + id;

				$("#dg_validateInfo").datagrid({
					url:url,
					title:'门票验证详情列表',
					singleSelect:true,
					rownumbers: true,
					border:false,
					fitColumns: true,
					columns:[[
						{field:'productName',title:'价格类型名称',width:150},
						{field:'validateCount',title:'数量',width:30, align:'center'},
						{field:'validateTime',title:'验证时间',width:100},
						{field:'validateByName',title:'操作人',width:70}
					]]

				});
			}

		});
		$("#editPanel2").dialog("open");


	},
		
		validatePerCode:function(){
			$("#validate_dialog").dialog({
				title : '验票输入',
				modal : true,
				top : "10",
				shadow:false,
				left : "100"
				
			});
			$("#input_validate").numberbox("setValue", "");
			$("#validate_dialog").dialog("open");
//			$(".window-shadow").css('display','none');
		},
		doSearch : function() {
			$("#dg").datagrid('load', {});
		}
		
		
		
}

$(function(){
	ValidateCode.initDgList();
	$('#validate_dialog').dialog({
	       onBeforeClose:function(){ 
	    	   $("#ff").form("clear");
//	           alert(111);
	       }
	   });
	
	$("#isUsed").change(function(){
//		alert("a");
		if($("#isUsed").prop("checked")){
			$("#hidden_isused").val("1");
//			alert("1");
		}else{
			$("#hidden_isused").val("0");
//			alert("0");
		}
		
	});
});