var editStep21 = {
	init:function(){
		editStep21.initStatus();
		editStep21.initMinDiscountPrice();
		editStep21.initMinRatePrice();
        editStep21.initInsurance();
		editStep21.initBoardLoction();
		editStep21.initBoardContact();
		editStep21.initCom();
	},

	initCom: function() {
		var hid_autoSendInfo = $("#hid_autoSendInfo").val();
		if (hid_autoSendInfo == "true") {
			$("#autoSendInfoId").val(hid_autoSendInfo);
			$("#autoSendInfoId").prop("checked", true);
		} else {
			$("#autoSendInfoId").prop("checked", false);
		}


		$("#autoSendInfoId").change(function(){
			var isChecked = $("#autoSendInfoId").attr("checked");
			if (isChecked) {
				$("#autoSendInfoId").val(true);
				$("#hid_autoSendInfo").val(true);
			} else {
				$("#autoSendInfoId").val(false);
				$("#hid_autoSendInfo").val(false);
			}

		});
		var hid_hasTransfer = $("#hid_hasTransfer").val();
		if (hid_hasTransfer.length > 0) {
			$(":input[name='lineDeparture.hasTransfer'][value='" + hid_hasTransfer + "']").attr("checked", true);
		}
	},

	initBoardContact: function() {
		$('#line_board_contact').datagrid({
			url:'/line/lineContact/getLineContactList.jhtml?lineContact.line.id='+$("#hid_line_id").val(),
			fit:true,
			singleSelect:true,
			columns:[[
				{field:'contactType',title:'联系人类型',width:'120px',
					formatter : function(value, rowData, rowIndex) {
						//sendHuman ("送站/机联系人"), receive("接站/机联系人"), tourGuide ("导游"), departurePlace ("出发地联系人"), localPlace("当地联系人"), others("其他");
						if (value == "sendHuman") {
							return "送站/机联系人";
						}
						if (value == "receive") {
							return "接站/机联系人";
						}
						if (value == "tourGuide") {
							return "导游";
						}
						if (value == "departurePlace") {
							return "出发地联系人";
						}
						if (value == "localPlace") {
							return "当地联系人";
						}
						if (value == "others") {
							return "其他";
						}
					}
				},
				{field:'contactName',title:'联系人',width:'120px'},
				{field:'contactPhone',title:'联系电话',width:'120px'},
				{field:'remark',title:'备注',width:'140px'},
				{field:'opt',title:'<a href="#" onclick="editStep21.addBoardContact()" class="link-btn" >新增一条</a>',width:'80px',align:'center',
					formatter : function(value, rowData, rowIndex) {
						var btn = '<a href="#" onclick="editStep21.delBoardContact('+ rowData.id +')">删除</a>';
						return btn;
					}

				}
			]]
		});
	},
    // 线路保险操作
    initInsurance: function() {
        $('#insuranceDg').datagrid({
            url: '/sales/insurance/getInsuranceList.jhtml',
            idField: 'id',
            fit:true,
            rownumbers: false,
            queryParams: {
                'orderProperty': "createTime",
                'orderType': "desc",
                'insurance.status': 'up'
            },
            columns: [[
                {
                    field : 'ck',
                    checkbox:true
                },
                {
                    field: 'id',
                    title: 'ID',
                    width: 55,
                    align: 'center'
                },
                {
                    field: 'isRec',
                    title: '推荐',
                    width: 60,
                    align: 'center'
                },
                //{
                //    field: 'OPT',
                //    title: '操作',
                //    width: 120,
                //    align: 'center',
                //    formatter: InsuranceMgr.optFormat
                //},
                {
                    field: 'name',
                    title: '保险名称',
                    width: 300,
                    align: 'center'
                },
                //{
                //    field: 'status',
                //    title: '状态',
                //    width: 80,
                //    align: 'center',
                //    formatter: InsuranceMgr.statusFormat
                //},
                {
                    field: 'company',
                    title: '保险公司',
                    width: 250,
                    align: 'center'
                },
                {
                    field: 'price',
                    title: '价格(元)',
                    width: 60,
                    align: 'center'
                },
                {
                    field: 'category.name',
                    title: '保险分类',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    width: 140,
                    align: 'center'
                }
            ]],
            //toolbar: $('#ice-searcher'),
            //onSelect: function(rowIndex, rowData) {
            //    if (!$('#exists_ice_input_' + rowData.id).length > 0) {
            //        $("#insuranceContent").append("<input id='ice_input_" + rowData.id + "' type='hidden' name='insuranceIds' value='" + rowData.id + "'>");
            //    } else {
            //        $('#del_ice_input_' + rowData.id).remove();
            //    }
            //},
            //onUnselect: function (rowIndex, rowData) {
            //    if ($('#exists_ice_input_' + rowData.id).length > 0) {
            //        $("#insuranceContent").append("<input id='del_ice_input_" + rowData.id + "' type='hidden' name='insuranceDelIds' value='" + rowData.id + "'>");
            //    } else {
            //        $('#ice_input_' + rowData.id).remove();
            //    }
            //},
            onCheck: function(rowIndex, rowData) {
                if (!$('#exists_ice_input_' + rowData.id).length > 0) {
                    $("#insuranceContent").append("<input id='ice_input_" + rowData.id + "' type='hidden' name='insuranceIds' value='" + rowData.id + "'>");
                } else {
                    $('#del_ice_input_' + rowData.id).remove();
                }
                var rowIndex = $('#insuranceDg').datagrid('getRowIndex', rowData.id);
                $('#insuranceDg').datagrid('updateRow', {
                    index: rowIndex,
                    row: {
                        isRec: '<a class="rec-a" onclick="editStep21.recInsurance(' + rowIndex + ',' + rowData.id + ',' + false + ')">'+ '推荐' +'</a>'
                    }
                });
            },
            onUncheck: function (rowIndex, rowData) {
                if ($('#exists_ice_input_' + rowData.id).length > 0) {
                    $("#insuranceContent").append("<input id='del_ice_input_" + rowData.id + "' type='hidden' name='insuranceDelIds' value='" + rowData.id + "'>");
                } else {
                    $('#ice_input_' + rowData.id).remove();
                }
                $('#insuranceDg').datagrid('updateRow', {
                    index: rowIndex,
                    row: {
                        isRec: '-'
                    }
                });
                // 如果取消的是已经推荐的保险, 则同步取消推荐
                if ($('#rec_insurance').length > 0 && $('#rec_insurance').val() == rowData.id) {
                    $('#rec_insurance').remove();
                }
            },
            onCheckAll: function(rows) {
                $.each(rows, function(index, rowData) {
                    if (!$('#exists_ice_input_' + rowData.id).length > 0) {
                        $("#insuranceContent").append("<input id='ice_input_" + rowData.id + "' type='hidden' name='insuranceIds' value='" + rowData.id + "'>");
                    } else {
                        $('#del_ice_input_' + rowData.id).remove();
                    }
                    if (rowData.isRec == "-") {
                        var rowIndex = $('#insuranceDg').datagrid('getRowIndex', rowData.id);
                        $('#insuranceDg').datagrid('updateRow', {
                            index: rowIndex,
                            row: {
                                isRec: '<a class="rec-a" onclick="editStep21.recInsurance(' + rowIndex + ',' + rowData.id + ',' + false + ')">'+ '推荐' +'</a>'
                            }
                        });
                    }
                });
            },
            onUncheckAll: function(rows) {
                $.each(rows, function(index, rowData) {
                    if ($('#exists_ice_input_' + rowData.id).length > 0) {
                        $("#insuranceContent").append("<input id='del_ice_input_" + rowData.id + "' type='hidden' name='insuranceDelIds' value='" + rowData.id + "'>");
                    } else {
                        $('#ice_input_' + rowData.id).remove();
                    }
                    if (rowData.isRec != "-") {
                        var rowIndex = $('#insuranceDg').datagrid('getRowIndex', rowData.id);
                        $('#insuranceDg').datagrid('updateRow', {
                            index: rowIndex,
                            row: {
                                isRec: '-'
                            }
                        });
                    }
                });
                // 取消推荐
                $('#rec_insurance').remove();
            },
            onLoadSuccess: function (data) {
                // 初始化推荐状态
                $.each(data.rows, function(index, item) {
                    $('#insuranceDg').datagrid('updateRow', {
                        index: index,
                        row: {
                            isRec: '-'
                        }
                    });
                });
                // 保险加载成功后, 加载线路保险方案
                editStep21.getLineInsuranceData();
            }
        });
    },
    getLineInsuranceData: function() {
        var lineId = $('#hid_line_id').val();
        if (lineId != null && lineId != "") {
            $.ajax({
                url: '/line/lineInsurance/getSimLineInsuranceData.jhtml',
                type: 'post',
                dataType: 'json',
                data: {
                    'lineId': lineId
                },
                success: function(result) {
                    $.messager.progress('close');
                    if (result.success) {
                        $.each(result.data, function(index, item) {
                            $("#insuranceContent").append("<input id='exists_ice_input_" + item.insuranceId + "' type='hidden' name='existsInsuranceIds' value='" + item.insuranceId + "'>");
                            $('#insuranceDg').datagrid('selectRecord', item.insuranceId);
                            var rowIndex = $('#insuranceDg').datagrid('getRowIndex', item.insuranceId);
                            var isRec = item.isRec ? '取消推荐' : '推荐';
                            $('#insuranceDg').datagrid('updateRow', {
                                index: rowIndex,
                                row: {
                                    isRec: '<a class="rec-a" onclick="editStep21.recInsurance(' + rowIndex + ',' + item.insuranceId + ',' + item.isRec + ')">'+ isRec +'</a>'
                                }
                            });
                            //
                            if (item.isRec) {
                                $("#insuranceContent").append("<input id='rec_insurance' type='hidden' name='recInsuranceId' value='" + item.insuranceId + "'>");
                            }
                        });
                    } else {
                        showMsgPlus("线路编辑", result.msg);
                    }
                },
                error: function(result) {
                    showMsgPlus("线路编辑", "");
                },
                beforeSend: function (data) {
                    $.messager.progress({
                        msg: '正在加载线路保险,请稍候...'
                    });
                }
            });
        }
    },
    doSearchInsurance: function() {
        var searchForm = {};
        var searchType = $('#ice-searcher').find("#search-type").val();
        searchForm[searchType] = $('#ice-searcher').find("#search-content").val();
        searchForm['insurance.status'] = 'up';
        $('#insuranceDg').datagrid('load', searchForm);
    },
    recInsurance: function(rowIndex, insuranceId, isRec) {
        // 阻止冒泡, 避免此行被取消
        window.event.stopPropagation();
        if (isRec) {
            // 取消推荐
            $('#rec_insurance').remove();
            $('#insuranceDg').datagrid('updateRow', {
                index: rowIndex,
                row: {
                    isRec: '<a class="rec-a" onclick="editStep21.recInsurance(' + rowIndex + ',' + insuranceId + ',' + false + ')">'+ '推荐' +'</a>'
                }
            });
        } else if (!isRec) {
            // 推荐
            var recInsuranceId = 0;
            if ($('#rec_insurance').length > 0) {
                recInsuranceId = $('#rec_insurance').val();
                $('#rec_insurance').val(insuranceId);
                var existRecInsuranceRowIndex = $('#insuranceDg').datagrid('getRowIndex', recInsuranceId);
                $('#insuranceDg').datagrid('updateRow', {
                    index: existRecInsuranceRowIndex,
                    row: {
                        isRec: '<a class="rec-a" onclick="editStep21.recInsurance(' + existRecInsuranceRowIndex + ',' + recInsuranceId + ',' + false + ')">'+ '推荐' +'</a>'
                    }
                });
            } else {
                $("#insuranceContent").append("<input id='rec_insurance' type='hidden' name='recInsuranceId' value='" + insuranceId + "'>");
            }
            $('#insuranceDg').datagrid('updateRow', {
                index: rowIndex,
                row: {
                    isRec: '<a class="rec-a" onclick="editStep21.recInsurance(' + rowIndex + ',' + insuranceId + ',' + true + ')">'+ '取消推荐' +'</a>'
                }
            });
        }
        //console.log(rowIndex + "--" + isRec)
    },

	initBoardLoction: function() {
		$('#line_board_laction').datagrid({
			url:'/line/lineDeparture/getBoarLocationList.jhtml',
			fit:true,
			singleSelect:true,
			columns:[[
				{field:'originStation',title:'发车地点',width:'120px'},
				{field:'departureDateStr',title:'发车时间',width:'125px'},
				{field:'returnPlace',title:'返回地点',width:'120px'},
				{field:'remark',title:'备注',width:'140px'},
				{field:'opt', title:'<a href="#" onclick="editStep21.addBoardLocation()" class="link-btn" >新增一条</a>', width:'80px',align:'center',
					formatter : function(value, rowData, rowIndex) {

						var btn = '<a href="#" onclick="editStep21.delBoardLocation('+rowData.id+') ">删除</a>';

						return btn;
					}
				}
				/*{field:'id',title:'<a href="#" onclick="editStep21.addBoardLocation()" class="link-btn" >新增一条</a>',width:'80px',align:'center',
				 formatter : function(value, rowData, rowIndex) {

				 var btn = '<a href="#" onclick="editStep21.delBoardLocation(value)">删除</a>';

				 return btn;
				 }
				 }*/
			]],
			onBeforeLoad : function(data) {   // 查询参数
				data['lineDepartureInfo.line.id']=$("#hid_line_id").val();
                data['lineDepartureInfo.departure.id']=$("#hid_departureId").val();
			}
		});
	},

	addBoardLocation: function() {
		//var departureId = $("#hid_departureId").val();
		//if (departureId) {
		//	editStep21.openBoardLocation();
		//} else {
		var url = '/line/lineDeparture/saveDeparture.jhtml';
		$('#editForm').form('submit', {
			url: url,
			onSubmit: function () {
				var isValid = $(this).form('validate');
				if (isValid) {
					$.messager.progress();	// 显示进度条
				}
				return isValid;	// 返回false终止表单提交
			},
			success: function (result) {
				$.messager.progress('close');	// 如果提交成功则隐藏进度条

				console.log(result);

				var result = eval('(' + result + ')');

				if (result.success) {
					$("#hid_departureId").val(result.departureId);
					editStep21.openBoardLocation();
				} else {
					show_msg("无法新增发车信息！");
				}
			}
		});
		//}
	},

	openBoardLocation: function() {
		$("#boardLoactionForm").form("clear");
		$("#hid_loaction_departureId").val($("#hid_departureId").val());
		$('#bard_location_dialog').dialog({
			title: '新增发车基本信息',
			width: 450,
			height: 400,
			closed: false,
			cache: false,
			modal: true,
			buttons:[{
				text:'保存',
				handler:function(){
					editStep21.saveDepartureInfo();
				}
			},{
				text:'关闭',
				handler:function(){
					$("#boardLoactionForm").form("clear");
					$('#bard_location_dialog').dialog('close');
				}
			}]
		});
		$('#bard_location_dialog').dialog('open');
	},

	saveDepartureInfo: function() {
		var url = '/line/lineDeparture/saveDepartureInfo.jhtml';
		$('#boardLoactionForm').form('submit', {
			url: url,
			onSubmit: function () {
				var isValid = $(this).form('validate');
				var d412 = $("#d412").val();
				if (d412.length <= 0) {
					isValid = false;
					show_msg("请完善出发日期！");
				}
				if (isValid) {
					$.messager.progress();	// 显示进度条
				}

				return isValid;	// 返回false终止表单提交
			},
			success: function (result) {
				$.messager.progress('close');	// 如果提交成功则隐藏进度条
				var result = eval('(' + result + ')');
				if (result.success) {
					$('#line_board_laction').datagrid("load");
					$('#bard_location_dialog').dialog('close');
					$("#boardLoactionForm").form("clear");
				} else {
					show_msg("新增发车信息失败！");
				}
			}
		});

	},

	addBoardContact: function() {
		$("#boardContactForm").form("clear");
		$("#hid_contactLineId").val($("#hid_line_id").val());
		$('#bard_contact_dialog').dialog({
			title: '新增联系人信息',
			width: 450,
			height: 400,
			closed: false,
			cache: false,
			modal: true,
			buttons:[{
				text:'保存',
				handler:function(){
					editStep21.saveContact();
				}
			},{
				text:'关闭',
				handler:function(){
					$("#boardContactForm").form("clear");
					$('#bard_contact_dialog').dialog('close');
				}
			}]
		});
		$('#bard_contact_dialog').dialog('open');
	},

	saveContact:function() {
		$.messager.progress();	// 显示进度条
		var url = '/line/lineContact/saveContact.jhtml';
		$('#boardContactForm').form('submit', {
			url: url,
			onSubmit: function () {
				var isValid = $(this).form('validate');
				if (!isValid) {
					$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
				}
				return isValid;	// 返回false终止表单提交
			},
			success: function (result) {
				$.messager.progress('close');	// 如果提交成功则隐藏进度条
				var result = eval('(' + result + ')');
				if (result.success) {
					$('#line_board_contact').datagrid("load");
					$('#bard_contact_dialog').dialog('close');
					$("#boardContactForm").form("clear");
				} else {
					show_msg("新增发车信息失败！");
				}
			}
		});


	},

	delBoardLocation: function(id) {
		var url = '/line/lineDeparture/delDepartureInfo.jhtml';
		$.messager.progress();	// 显示进度条
		$.post(url, { 'lineDepartureInfo.id': id },
			function(result){
				$.messager.progress('close');	// 如果提交成功则隐藏进度条
				if (result.success) {
					$('#line_board_laction').datagrid("load");
					show_msg("操作成功！");
				} else {
					show_msg("操作失败！");
				}
			}
		);
	},

	delBoardContact: function(id) {

		var url = '/line/lineContact/delContact.jhtml';
		$.messager.progress();	// 显示进度条
		$.post(url, { 'lineContact.id': id },
			function(result){
				$.messager.progress('close');	// 如果提交成功则隐藏进度条
				if (result.success) {
					$('#line_board_contact').datagrid("load");
					show_msg("操作成功！");
				} else {
					show_msg("操作失败！");
				}
			}
		);

	},

	onpenQuantitySalesDialog: function(id) {

		$("#quantitySales_dialog").dialog({
			title: '设置拱量',
			closed: true,
			cache: false,
			modal: true
		});
		var ifr = $("#quantitySales_dialog").children()[0];
		var url = "/line/line/quantitySalesDialog.jhtml?typePriceId=" + id;
		$(ifr).attr("src", url);
		$("#quantitySales_dialog").dialog("open");

	},

	// 初始状态
	initStatus : function() {
		//var lineInfo = parent.window.getIfrData('step1');
		//$('#productName').html(lineInfo.productName);
	},
	// 获取最小报价时间（成人分销价）
	initMinDiscountPrice: function() {
		$('.minDiscountPrice').each(function(index, element) {
			$.post("/line/linetypeprice/findMinValue.jhtml", {linetypepriceId: $(element).attr('linetypepriceId'), prop:'discountPrice'},
				function(data){
					$(element).html(data.propValue);
				}
			);
		});
	},
	// 获取最小报价时间（成人佣金）
	initMinRatePrice: function() {
		$('.minRatePrice').each(function(index, element) {
			$.post("/line/linetypeprice/findMinValue.jhtml", {linetypepriceId: $(element).attr('linetypepriceId'), prop:'rebate'},
				function(data){
					$(element).html(data.propValue);
				}
			);
		});
	},
	// 编辑类别报价
	doEditPrice : function(priceId) {
		var productId = $('#productId').val();
		var url = "/line/line/editStep2.jhtml?linetypepriceId="+priceId+"&productId="+productId;
		window.location.href = url;	
	},

	doDelPrice: function(priceId) {

		var url = "/line/linetypeprice/delPrice.jhtml";
		$.messager.progress();	// 显示进度条
		$.post(url, {priceId: priceId},
			function(result){
				$.messager.progress('close');	// 如果表单是无效的则隐藏进度条

				if (result.success) {
					show_msg("删除成功！");
					window.location.reload();
				} else {
					show_msg("删除失败！");
				}

			}
		);

	},

	// 详细类别报价
	doViewPrice: function (priceId) {
		var productId = $('#productId').val();
		var url = "/line/line/viewStep2.jhtml?linetypepriceId=" + priceId + "&productId=" + productId;
		window.location.href = url;
	},
	// 上一步
	prevGuide:function(){
		var productId = $('#productId').val();
		var url = "/line/line/editStep2.jhtml?productId="+productId;
		window.location.href = url;	
	},	
	// 下一步
	nextGuide:function(winId){
		parent.window.showGuide(winId, true);	
	},
	saveDeparture: function(winId) {
		$.messager.progress();	// 显示进度条
		var url = '/line/lineDeparture/saveDeparture.jhtml';
		$('#editForm').form('submit', {
			url: url,
			onSubmit: function () {
				var isValid = $(this).form('validate');

				var typepricetrArr = $("#typepricetbody").children();

				if (typepricetrArr.length <= 0) {
					isValid = false;
					show_msg("请完善线路价格类型！");
					$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
					return isValid;
				}

				if (!isValid) {
					$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
				}
				return isValid;	// 返回false终止表单提交
			},
			success: function (result) {
				$.messager.progress('close');	// 如果提交成功则隐藏进度条
				var result = eval('(' + result + ')');

				if (result.success) {
					show_msg("操作成功！");
					editStep21.nextGuide(winId);
				} else {
					show_msg("操作失败！");
				}
			}
		});


	}
};

//返回本页面数据
function getIfrData(){
	var data = {};
	return data;
}	

$(function(){
	editStep21.init();
});