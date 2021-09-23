var auditList = {
	init:function(){
		auditList.initComp();
		auditList.initStatus();
	},
    // 初始化控件
    initComp : function() {
        // 查询控件
        $('#qry_lineType').combobox({
            data:LineConstants.lineType,
            valueField:'id',
            textField:'text'
        });
        $('#qry_source').combobox({
            data:LineConstants.source,
            valueField:'id',
            textField:'text'
        });
        $('#qry_productAttr').combobox({
            data:LineConstants.productAttr,
            valueField:'id',
            textField:'text'
        });
        $('#qry_buypay').combobox({
            data:LineConstants.buypay,
            valueField:'id',
            textField:'text'
        });
        $('#qry_lineStatus').combobox({
            data:LineConstants.lineStatus,
            valueField:'id',
            textField:'text'
        });
        // 初始化表格数据
        $("#dg").datagrid({
            fit:true,
            //title:'线路列表',
            //height:400,
            url:'/line/line/search.jhtml',
            pagination:true,
            pageList:[20,30,50],
            rownumbers:true,
            //fitColumns:true,
            singleSelect:false,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                { field: 'ck', checkbox: true },
                { field: 'name', title: '线路名称', width: 320, sortable: true,
                    formatter : function(value, rowData, rowIndex) {
                        if (rowData.agentFlag) {
                            return value+"[代理]";
                        }
                        return value;
                    }},
                { field: 'commentSum', title: '人气', width: 160, sortable: true},
                { field: 'orderSum', title: '订单', width: 160, sortable: true },
                { field: 'categoryName', title: '分类', width: 200, sortable: true },
                { field: 'productAttr', title: '产品性质', width: 160, sortable: true, codeType: 'productAttr', formatter: LineUtil.codeFmt},
                { field: 'combineType', title: '组合类型', width: 160, codeType: 'combineType', formatter: LineUtil.codeFmt},
                { field: 'showOrder', title: '排序', width: 160, sortable: true},
                { field: 'updateTime', title: '最后更新', width: 320, sortable: true},
                { field: 'lineStatus', title: '状态', width: 140, align: 'center', sortable: true, codeType: 'lineStatus', formatter: LineUtil.codeFmt},
                { field: 'auditReason', title: '审核原因', width: 160},
                { field: 'opt', title: '操作', width: 200, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='auditList.doView(" + rowData.id + ")'>详情</a>";
                        if (rowData.lineStatus == 'show') {
                            btn = btn + "&nbsp;&nbsp;" + "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='auditList.doHide("+rowData.id+")'>下架</a>";
                        } else if (rowData.lineStatus == 'hide') {
                            btn = btn + "&nbsp;&nbsp;" + "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='auditList.doShow("+rowData.id+")'>上架</a>";
                        } else if (rowData.lineStatus == 'checking') {
                            btn = btn + "&nbsp;&nbsp;" + "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='auditList.doThrough("+rowData.id+")'>通过</a>"
                                + "&nbsp;&nbsp;" + "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='auditList.doUnThrough("+rowData.id+")'>不通过</a>";
                        }
                        return btn;
                    }}
            ]],
            toolbar: '#tb',
            onBeforeLoad : function(data) {   // 查询参数
                data.lineType = $("#qry_lineType").combobox("getValue");
                data.productAttr = $("#qry_productAttr").combobox("getValue");
                data.category = $("#qry_customType").combobox("getValue");
                data.sourceStr = $("#qry_source").combobox("getValue");
                data.buypay = $("#qry_buypay").combobox("getValue");
                data.lineStatus = $("#qry_lineStatus").combobox("getValue");
                data.keyword = $("#qry_keyword").textbox("getValue");
            },
            onLoadSuccess: function(data) {
                if (data.rows) {
                    var rows = data.rows;
                    for (var i = 0; i < rows.length; i++) {
                        auditList.fmtTbCategoryName('dg', i, rows[i].category);
                    }
                }
            }
        });
    },
	// 初始状态
	initStatus : function() {
		
	},
	// 表格查询
	doSearch:function(){
		$('#dg').datagrid('load', {});
	},
	// 查看
	doView:function(id){
        var url = FG_DOMAIN + "/line_detail_" + id + ".html";
        window.open(url, '_blank');
	},
    // 批量通过
    doBatchThrough : function() {
        auditList.doBatch('checking', 'show');
    },
    // 批量不通过
    doBatchUnThrough : function() {
        auditList.doBatch('checking', 'hide');
    },
    // 批量上架
    doBatchShow : function() {
        auditList.doBatch('hide', 'show');
    },
    // 批量下架
    doBatchHide : function() {
        auditList.doBatch('show', 'hide');
    },
    // 批量操作
    doBatch : function(lineStatusLimit, lineStatusToUpdate) {
        var rows = $('#dg').datagrid('getSelections');
        if (rows.length < 1) {
            show_msg("请选择记录");
            return ;
        }
        var idsArray = [];
        for (var i = 0; i < rows.length; i++){
            var row = rows[i];
            if (row.lineStatus != lineStatusLimit) {		// 状态：show("上架"), hide("下架"), checking("审核中");
                var lineStatusDesc = '';
                if (lineStatusLimit == 'show') {
                    lineStatusDesc = '上架';
                } else if (lineStatusLimit == 'hide') {
                    lineStatusDesc = '下架';
                } else if (lineStatusLimit == 'checking') {
                    lineStatusDesc = '审核中';
                }
                show_msg("请选择状态为【" + lineStatusDesc + "】的记录");
                return ;
            }
            idsArray.push(row.id);
        }
        var ids = idsArray.join(',');
        if (lineStatusLimit == 'checking' && lineStatusToUpdate == 'hide') {    // 如果是审核，且不通过，弹出填写原因窗口
            $("#lineId").val(ids);
            $('#reasonDg').dialog('open');
        } else {
            auditList.doUpdateStatus(ids, lineStatusToUpdate);
        }
    },
    // 更新状态
    doUpdateStatus: function(ids, lineStatusToUpdate, reason) {
        if (!reason) {  // 未传入该参数，默认值
            reason = '';
        }


        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post("/line/line/doUpdateStatus.jhtml",
            {ids : ids, lineStatus : lineStatusToUpdate, reason : reason},
            function(result) {
                $.messager.progress("close");
                if (result.success==true) {
                    show_msg("线路操作成功");

                    var index = ids.lastIndexOf(",");
                    var idArr = [];
                    if (index > 0) {
                        idArr = ids.split(",");
                    } else {
                        idArr.push(ids);
                    }
                    for (var j = 0; j < idArr.length; j++) {
                        LineUtil.buildLine(idArr[j]);
                    }
                    auditList.doSearch();
                } else {
                    auditList.doSearch();
                    show_msg(result.errorMsg);
                }
            }
        );
    },
    // 不通过-确认
    doUnThroughDg : function() {
        var lineId = $("#lineId").val();
        var reason = $("#reason").val();
        auditList.doUpdateStatus(lineId, 'hide', reason);
        $('#reasonDg').dialog('close');
    },
    // 通过
    doThrough : function(id) {
        auditList.doUpdateStatus(id, 'show');
    },
    // 不通过
    doUnThrough : function(id) {
        $("#lineId").val(id);
        $('#reasonDg').dialog('open');
    },
    // 上架
    doShow : function(id) {
        auditList.doUpdateStatus(id, 'show');
    },
    // 下架
    doHide : function(id) {
        auditList.doUpdateStatus(id, 'hide');
    },
    // 表格分类名称显示
    fmtTbCategoryName : function(tbId, rowIndex, categoryId) {
        $.post("/line/line/findCategoryName.jhtml", {categoryId: categoryId},
            function(data){
                $('#'+tbId).datagrid('updateRow', {index:rowIndex, row:{categoryName:data.categoryName}});
            }
        );
    }
};

$(function(){
	auditList.init();
});

