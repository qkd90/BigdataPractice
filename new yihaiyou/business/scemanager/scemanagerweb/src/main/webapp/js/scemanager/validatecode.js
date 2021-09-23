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
				url : '/scemanager/scemanager/validateList.jhtml',
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
					width : 270,
					sortable : false
				},{
					field : 'name',
					title : '产品名称+票种类型',
					width : 450,
					sortable : false
				}, {
					field : 'supUserName',
					title : '销售商名称',
					width : 280,
					sortable : false
				}, {
					field : 'buyerName',
					title : '订单用户',
					width : 150,
					sortable : false
				}, {
					field : 'buyerMobile',
					title : '手机',
					width : 200,
					sortable : false
				}, {
					field : 'createTime',
					title : '订单时间',
					width : 260,
					sortable : false,
					formatter: function(value, rowData, index) {
						if (value) {
							value = value.substr(0, 16);
							return value;
						} else {
							return value;
						}
					}
				}, {
					field : 'orderCount',
					title : '剩余票数',
					width : 150,
					sortable : false
				}, {
					field : 'orderInitCount',
					title : '使用情况',
					width : 300,
					sortable : false,
					formatter: function(value, rowData, index) {
                        var content = "";
                        var validatedNum = rowData.orderInitCount - rowData.refundCount - rowData.orderCount;
                        if (validatedNum > 0) {
                            content = content + ",已验" + validatedNum + "张";
                        }
						if (rowData.refundCount > 0) {
                            content = content + ",已退" + rowData.refundCount + "张";
                        }
						if (rowData.used !== 0 && rowData.orderCount > 0) {
							content = content + ",无效" + rowData.orderCount + "张";
						}
                        if (content) {
                            content = content.substring(1);
                        } else {
                            content = "------";
                        }
						return content;
					}
				}, {
					field : 'code',
					title : '验证码',
					width : 100,
					sortable : false,
					formatter: function(value, rowData, index) {
						if (rowData.used == 0) {
							return "------";
						}
						return value;
					}
				}, {
					field : 'usedStr',
					title : '是否验证',
					width : 160,
					sortable : false
				}, {
					field : 'vaName',
					title : '最后操作人',
					width : 150,
					sortable : false
				}, {
						field : 'opt',
						title : '操作',
						width : 150,
						sortable : false,
						formatter: function(value, rowData, index) {
							var validatedNum = rowData.orderInitCount - rowData.refundCount - rowData.orderCount;
							var valiHtml = '<a class="easyui-linkbutton" style="color:blue;" onclick="ValidateCode.checkValidateDetail('+ index +','+rowData.id +')">验票详情</a>';
							if (validatedNum > 0) {
								return valiHtml;
							} else {
								return "------";
							}

						}
					}
				] ],
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
			
			var isUsed = "";

			//$("#hidden_used").val($("#com_used").combobox("getValue"));

			$("#dg").datagrid('load', {
				'ticNameid' : $("#ticNameid").textbox('getValue'),
				'orderNum' : $("#orderNum").textbox('getValue'),
				'seaStartTime' : $("#seaStartTime").datetimebox('getValue'),
				'seaEndTime' : $("#seaEndTime").datetimebox('getValue'),
				//'orderUser' : $("#orderUser").textbox('getValue'),
				'isUsed' : $("#com_used").combobox("getValue")
//					'remark' : $("#role_remark").val(),
			});
			
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