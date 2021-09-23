var CruiseShip = {
	init:function(){
		CruiseShip.initComp();
	},
    // 初始化控件
    initComp : function() {
        // 查询控件
        $('#qry_status').combobox({
            data:CruiseShipConstants.qry_status,
            mode: 'local',
            valueField:'id',
            panelHeight: 'auto',
            textField:'text'
        });
        // 初始化表格数据
        $("#dg").datagrid({
            fit:true,
            //title:'线路列表',
            //height:400,
            url:'/cruiseship/cruiseShip/checkingSearch.jhtml',
            pagination:true,
            pageList:[20,30,50],
            //rownumbers:true,
            fitColumns:true,
            singleSelect:false,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                //{ field: 'ck', checkbox: true },
                { field: 'id', title: '产品ID', width: 100, align:'center'},
                { field: 'name', title: '邮轮名称', width: 360},
                { field: 'status', title: '状态', width: 100, align: 'center', codeType: 'productStatus', formatter: CruiseShipUtil.codeFmt},
                { field: 'startCity', title: '出发城市', width: 140},
                { field: 'arriveCity', title: '到达城市', width: 140},
                { field: 'satisfaction', title: '满意度', width: 100,
                    formatter : function(value, rowData, rowIndex) {
                        if (value) {
                            return value + '%';
                        }
                        return '';
                    }
                },
                { field: 'commentNum', title: '评论数', width: 100},
                { field: 'collectionNum', title: '收藏数', width: 100},
                { field: 'updateTime', title: '最后更新', width: 150},
                { field: 'opt', title: '操作', width: 200, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='CruiseShip.doEdit(" + rowData.id + ")'>审核详情</a>";
                        var checkedUpBtn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='CruiseShip.doCheckedUp(" + rowData.id + ")'>通过审核</a>";
                        var checkedFailBtn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='CruiseShip.doCheckedFail(" + rowData.id + ")'>不通过</a>";

                        if (rowData.status == 'UP' || rowData.status == 'DOWN' || rowData.status == 'FAIL') {
                            return btn + "&nbsp;&nbsp;";
                        } else {
                            return btn + "&nbsp;&nbsp;" +checkedUpBtn + "&nbsp;&nbsp;" +checkedFailBtn;
                        }
                    }
                }
            ]],
            toolbar: '#tb',
            onBeforeLoad : function(data) {   // 查询参数
                //data['cruiseShip.status'] = 'CHECKING';
                //data['cruiseShip.id'] = $("#qry_proNum").textbox("getValue");
                data['cruiseShip.name'] = $("#qry_proName").textbox("getValue");
                data['cruiseShip.status'] = $("#store_status").combobox("getValue");
            }
        });
    },

    doCheckedUp: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/cruiseship/cruiseShip/doChecked.jhtml';
        $.post(url, {productId: id, 'cruiseShip.status': 'UP'}, function(data){
            if (data.success) {
                show_msg("审核成功！");
                CruiseShipUtil.buildAllCruiseship(id);
                $('#dg').datagrid('load', {});
            } else {
                show_msg("审核失败！")
            }
            $.messager.progress('close');
        });

    },
    doCheckedFail: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/cruiseship/cruiseShip/doChecked.jhtml';
        $.post(url, {productId: id, 'cruiseShip.status': 'FAIL'}, function(data){
            if (data.success) {
                show_msg("操作成功！");
                $('#dg').datagrid('load', {});
            } else {
                show_msg("操作成功！")
            }
            $.messager.progress('close');
        });

    },

    // 打开编辑窗口
    openEditPanel: function(url) {
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog("open");
    },
    // 关闭编辑窗口
    closeEditPanel: function(isRefresh) {
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", '');
        $("#editPanel").dialog("close");
        CruiseShip.doSearch();
    },
	// 表格查询
	doSearch:function(){
		$('#dg').datagrid('load', {});
	},

    doClear: function() {
        $("#store_status").combobox("setValue", "");
        $("#qry_proName").textbox("setValue", "");
        $('#dg').datagrid('load', {});
    },

	// 查看
	doView:function(id){
        var url = FG_DOMAIN + "/line_detail_" + id + ".html";
        window.open(url, '_blank');
	},

    // 修改
    doEdit:function(productId) {
        var url = "/cruiseship/cruiseShip/editWizard.jhtml?productId="+productId;
        CruiseShip.openEditPanel(url);
    }
};

$(function(){
	CruiseShip.init();
});

