/**船票管理*/
var TicketList = {
    addTicket: function () {
        var url = "/ticket/ticket/sailboatAddWizard.jhtml";
        window.location.href = url;
    },
    // 表格查询
    doShowSearch: function () {
        var data = {
            ticketType: $("#search_ticketType").combobox("getValue"),
            category: $("#search_category").combotree("getValue"),
            source: $("#search_ticketSource").combobox("getValue"),
            status: $("#search_status").combobox("getValue"),
            name: $("#search_name").textbox("getValue")
        };
        $('#show_dg').datagrid("load", data);
    },


    reload: function () {
        $("#search_ticketType").combobox("setValue", "");
        $("#search_category").combotree("clear");
        $("#search_ticketSource").combobox("setValue", "");
        $("#search_status").combobox("setValue", "");
        $("#search_name").textbox("setValue", "");
        TicketList.doShowSearch();
    },
    showCategoryPanel: true,
    initCombox: function () {
        $("#search_category").combotree({
            url: '/goods/goods/getComboCatgoryData.jhtml?type=sailboat',
            width: 'auto',
            panelHeight: 'auto',
            onBeforeSelect: function (node) {
                //var $target = $(node.target);
                var tree = $(this).tree;
                var isLeaf = tree('isLeaf', node.target);
                if (!isLeaf) {
                    TicketList.showCategoryPanel = true;
                    return false;
                }
                TicketList.showCategoryPanel = false;
                return true;
            },
            onHidePanel: function(data) {
                if (TicketList.showCategoryPanel) {
                    $('#search_category').combotree('showPanel');
                }
            },
            onShowPanel: function() {
                TicketList.showCategoryPanel = false;
            },
            onLoadSuccess: function (node, data) {
                var city = $('#startCityIdHidden').val();
                if (city) {
                    var pro = city.substr(0, 2) + '0000';
                    $("#yc_proNameId").combobox('setValue', pro);
                }
                $.each(data, function(i, node){
                    if (node.children && node.children.length > 0) {
                        $('#' + node.domId).css('cursor', 'not-allowed');
                    }
                });
            }
        });
        $("#store_category").combotree({
            url: '/goods/goods/getComboCatgoryData.jhtml?type=sailboat',
            width: 'auto',
            panelHeight: 'auto',
            onBeforeSelect: function (node) {
                //var $target = $(node.target);
                var tree = $(this).tree;
                var isLeaf = tree('isLeaf', node.target);
                if (!isLeaf) {
                    TicketList.showCategoryPanel = true;
                    return false;
                }
                TicketList.showCategoryPanel = false;
                return true;
            },
            onHidePanel: function(data) {
                if (TicketList.showCategoryPanel) {
                    $('#store_category').combotree('showPanel');
                }
            },
            onShowPanel: function() {
                TicketList.showCategoryPanel = false;
            },
            onLoadSuccess: function (node, data) {
                var city = $('#startCityIdHidden').val();
                if (city) {
                    var pro = city.substr(0, 2) + '0000';
                    $("#yc_proNameId").combobox('setValue', pro);
                }
                $.each(data, function(i, node){
                    if (node.children && node.children.length > 0) {
                        $('#' + node.domId).css('cursor', 'not-allowed');
                    }
                });
            }
        });
    },
    viewTicket: function (id, name) {
        window.parent.addTab(name, '/ticket/ticket/sailboatView.jhtml?ticketId=' + id, '');//增加tab
    },
    initTickeListData: function () {
        // 构建表格
        $('#show_dg').datagrid({
            url: '/ticket/ticket/ticketGetList.jhtml',
            pagination: true,
            pageList: [10, 20, 30],
            singleSelect: false,
            striped: true,
            fit: true,
			rownumbers:true,
			fitColumns:true,
            columns: [[
                //{field: 'ck', checkbox: true},
                {field: 'id', title: 'ID', align: 'center', width: 80},
                {
                    field: 'name', title: '产品标题', align: 'center', width: 300,
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.agentFlag) {
                            return value + "<span style='color:#3DFB1B;'>[代理]</span>";
                        }
                        return value;
                    }
                },
                {field: 'cateName', title: '船票分类', align: 'center', width: 170},
                {field: 'ticketTypeName', title: '船票类型', align: 'center', width: 170},
                {
                    field: 'status',
                    title: '状态',
                    align: 'center',
                    width: 90,
                    codeType: 'ticketStatus',
                    formatter: TicketUtil.codeFmt
                },
                //{field: 'showOrder', title: '排序', align: "right", align: 'center', width: 80},
                {field: 'popCounts', title: '人气', align: "right", align: 'center', width: 80},
                {field: 'orderCounts', title: '订单', align: "right", align: 'center', width: 80},
                {field: 'updateTime', title: '更新时间', align: "right", align: 'center', width: 140},
                {
                    field: "OPT", title: "操作", align: 'center', width: 250,
                    formatter: function (value, rowData, rowIndex) {
                        var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.viewTicket(" + rowData.id + ",\"" + rowData.name + "\")'>详情</a>";
                        var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.editTicket(" + rowData.id + ")'>修改</a>";
                        //var btnDel = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='TicketList.delTicket(" + rowData.id + ")'>删除</a>";
                        //var btnShow = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='TicketList.showOrHide(" + rowData.id + ")'>下架</a>";
                        //var btnHide = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='TicketList.showOrHide(" + rowData.id + ")'>上架</a>";
                        //if (rowData.status == "UP") {
                        //    return btnView + "&nbsp;" + btnEdit + "&nbsp;" + btnDel + "&nbsp;" + btnShow;
                        //} else if (rowData.status == "DOWN") {
                        //    return btnView + "&nbsp;" + btnEdit + "&nbsp;" + btnDel + "&nbsp;" + btnHide;
                        //}

                        return btnView + "&nbsp;" + btnEdit ;
                    }
                }]],
            toolbar: '#user_Tool',
            onBeforeLoad: function (data) {   // 查询参数
                data.ticketType = $("#search_ticketType").combobox("getValue");
                data.category = $("#search_category").combotree("getValue");
                data.source = $("#search_ticketSource").combobox("getValue");
                data.status = $("#search_status").combobox("getValue");
                data.name = $("#search_name").textbox("getValue");
                data['filterTypes[0]'] = "scenic";
                data['filterTypes[1]'] = "shows";
            },
            onLoadSuccess: function (data) {
            }
        });
    },

    doChangeTicketStatus: function(status) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var rows = $("#show_dg").datagrid("getChecked");
        if (rows.length > 0) {
            var ids = "";
            var idsArray = [];
            $.each(rows, function(i, perValue) {

                if (status == "CHECKING") {
                    if (perValue.status == "FAIL" || perValue.status == "DOWN") {
                        idsArray.push(perValue.id);
                    }
                } else {
                    if (perValue.status != status) {		// 状态：UP("上架"), DOWN("下架"), DEL("删除");
                        idsArray.push(perValue.id);
                    }
                }

                //if (perValue.status != status) {
                //    if (i < rows.length-1) {
                //        ids += perValue.id + ",";
                //    } else {
                //        ids += perValue.id;
                //    }
                //}
            });
            if (idsArray.length <= 0) {
                $.messager.progress('close');
                if (status == "CHECKING") {
                    show_msg("请选择已下架产品或审核未通过的产品");
                } else if (status == "DOWN") {
                    show_msg("请选择已上架产品或审核未通过的产品");
                }
                return;
            }
            $.ajax({
                url: '/ticket/ticket/doChangeTicketStatus.jhtml',
                type: 'post',
                dataType: 'json',
                data: {
                    idStrs:idsArray.join(","),
                    'ticket.status': status

                },
                success: function(result) {
                    if (result.success) {
                        $("#show_dg").datagrid("reload");
                        show_msg("操作成功！");
                    } else {
                        show_msg("操作失败！");
                    }
                    $.messager.progress('close');
                },
                error: function() {
                    $.messager.progress('close');
                    $.messager.alert("提示", "操作失败,稍候重试!", 'error');
                }
            });
        } else {
            $.messager.progress('close');
            show_msg("请选需要处理的产品！");
        }
    },

    showOrHide: function (id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        $.ajax({
            url: '/ticket/ticket/showOrHide.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                "ticId": id
            },
            success: function(result) {
                if (result.success) {
                    show_msg(result.temp);
                    $("#show_dg").datagrid('load', {});
                } else {
                    show_msg("操作失败");
                }
                $.messager.progress('close');
            },
            error: function() {
                $.messager.progress('close');
                $.messager.alert("提示", "操作失败,稍候重试!", 'error');
            }
        });
    },
    delTicket: function (id) {
        $.messager.confirm('删除确认', '确认要删除? 删除后无法恢复!', function(r) {
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'处理中,请耐心等待...'
                });
                $.ajax({
                    url: '/ticket/ticket/delTicket.jhtml',
                    type: 'post',
                    dataType: 'json',
                    data: {
                        "ticId": id
                    },
                    success: function(result) {
                        if (result.success) {
                            show_msg("删除成功！");
                            $("#show_dg").datagrid('load', {});
                        } else {
                            show_msg("删除船票失败");
                        }
                        $.messager.progress('close');
                    },
                    error: function() {
                        $.messager.progress('close');
                        $.messager.alert("提示", "操作失败,稍候重试!", 'error');
                    }
                });
            }
        });
    },
    delStoreTicket: function (id) {
        $.ajax({
            url: '/ticket/ticket/delTicket.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                "ticId": id
            },
            success: function(data) {
                var result = eval('(' + data + ')');
                if (data.success) {
                    show_msg("删除成功！");
                    $("#storehouse_dg").datagrid('load', {});
//						$("#show_dg").datagrid('load',{});
//						var param = "?ticketId="+data.tId;
//						parent.window.showGuide(4, true,param);
                } else {
                    show_msg("删除船票失败");
                }
//					$("#sce_div_area").hide();
//					$("#sce_span_area").html(data.fullPath);
                $.messager.progress('close');
            },
            error: function() {
                $.messager.progress('close');
                $.messager.alert("提示", "操作失败,稍候重试!", 'error');
            }
        });
    },
    editTicket: function (id) {
        var url = "/ticket/ticket/sailboatAddWizard.jhtml?ticketId=" + id;
        window.location.href = url;
    }
};
$(function () {
    TicketList.initTickeListData();
    TicketList.initCombox();

});
