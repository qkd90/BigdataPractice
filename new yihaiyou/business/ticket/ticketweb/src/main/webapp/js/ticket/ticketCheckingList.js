/**门票管理*/
var TicketList = {


    addTicket: function () {
        var url = "/ticket/ticket/addWizard.jhtml";
        window.location.href = url;
    },

    // 表格查询
    doStorehouseSearch: function () {
        var data = {};
        //data['ticket.category'] = $("#store_category").combotree("getValue");
        //data['ticket.sourceStr'] = $("#store_ticketSource").combobox("getValue");
        //data['ticket.name'] = $("#store_name").textbox("getValue");
        $('#storehouse_dg').datagrid("load", data);
    },


    reloadStore: function () {

        //$("#store_category").combotree("clear");
        //$("#store_category").combotree("setValue", "");
        $("#store_status").combobox("setValue", "");
        $("#store_name").textbox("setValue", "");
        $('#storehouse_dg').datagrid("load");
    },


    showCategoryPanel: true,
    initCombox: function () {
        $("#search_category").combotree({
            url: '/goods/goods/getComboCatgoryData.jhtml?type=service',
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
            url: '/goods/goods/getComboCatgoryData.jhtml?type=service',
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
            onHidePanel: function (data) {
                if (TicketList.showCategoryPanel) {
                    $('#store_category').combotree('showPanel');
                }
            },
            onShowPanel: function () {
                TicketList.showCategoryPanel = false;
            },
            onLoadSuccess: function (node, data) {
                var city = $('#startCityIdHidden').val();
                if (city) {
                    var pro = city.substr(0, 2) + '0000';
                    $("#yc_proNameId").combobox('setValue', pro);
                }
                $.each(data, function (i, node) {
                    if (node.children && node.children.length > 0) {
                        $('#' + node.domId).css('cursor', 'not-allowed');
                    }
                });
            },
        });
    },
    viewTicket: function (id, name) {
        window.parent.addTab(name, '/ticket/ticket/ticketView.jhtml?ticketId=' + id, '');//增加tab
    },

    initTickeStoreHouseData: function () {
        // 构建表格
        $('#storehouse_dg').datagrid({
//			title:"门票列表",
            data: [],
			url:'/ticket/ticket/checkingSearch.jhtml',
            border: true,
            singleSelect: true,
            striped: true,
            pagination: true,
            fit: true,
            pageList: [10, 20, 30],
            //rownumbers: true,
            fitColumns: true,
            columns: [[
                {field: 'id', title: '产品ID', align: 'center', width: 120},
                {field: 'name', title: '产品标题', align: 'center', width: 350},
                {field: 'cateName', title: '门票分类', align: 'center', width: 170},
                {field: 'ticketTypeName', title: '门票类型', align: 'center', width: 170},
                {field: 'companyUnitName', title: '供应商名称', align: 'center', width: 250},
                {
                    field: 'status',
                    title: '状态',
                    align: 'center',
                    width: 90,
                    codeType: 'productStatus',
                    formatter: TicketUtil.codeFmt
                },
                {field: 'popCounts', title: '人气', align: "right", align: 'center', width: 150},
                {field: 'orderCounts', title: '订单', align: "right", align: 'center', width: 150},
                {field: 'updateTime', title: '操作时间', align: "right", align: 'center', width: 250},
                {
                    field: "OPT", title: "操作", align: 'center', width: 350,
                    formatter: function (value, rowData, rowIndex) {
                        var btnView = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.viewTicket(" + rowData.id + ",\"" + rowData.name + "\")'>详情</a>";
                        var checkedUp = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.doCheckedUp(" + rowData.id +")'>通过</a>";
                        var checkedFail = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.doCheckedFail(" + rowData.id +")'>拒绝</a>";
                        if (rowData.status == 'UP' || rowData.status == 'DOWN' || rowData.status == 'FAIL' || rowData.status == 'REFUSE') {
                            return btnView;
                        } else {
                            return btnView + "&nbsp;&nbsp;" + checkedUp + "&nbsp;&nbsp;" + checkedFail;
                        }


                    }
                }]],
            toolbar: '#store_userTool',
            onBeforeLoad: function (data) {   // 查询参数
                //data['ticket.ticketType'] = $("#store_ticketType").combobox("getValue");
                //data['ticket.category'] = $("#store_category").combotree("getValue");
                data['ticket.sourceStr'] = 'LXB';
                //data['ticket.status'] = 'CHECKING';
                data['ticket.name'] = $("#store_name").textbox("getValue");
                data['ticket.status'] = $("#store_status").combobox("getValue");
                data.agentTicket = "agent";
                data['ticket.includeTicketTypeList[0]'] = "scenic";
                data['ticket.includeTicketTypeList[1]'] = "shows";
            },
            onLoadSuccess: function (data) {

            }
        });


    },

    doCheckedUp: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doChecked.jhtml';
        $.post(url, {ticketId: id, 'ticket.status': 'UP'}, function(data){
            if (data.success) {
                show_msg("审核成功！");
                $('#storehouse_dg').datagrid('load', {});
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
        var url = '/ticket/ticket/doChecked.jhtml';
        $.post(url, {ticketId: id, 'ticket.status': 'FAIL'}, function(data){
            if (data.success) {
                show_msg("操作成功！");
                $('#storehouse_dg').datagrid('load', {});
            } else {
                show_msg("操作失败！")
            }
            $.messager.progress('close');
        });

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
            success: function(data) {
                if (data.success) {
                    show_msg(data.temp);
                    $("#show_dg").datagrid('load', {});
                } else {
                    show_msg("删除门票失败");
                }
                $.messager.progress('close');
            },
            error: function(data) {
                $.messager.progress('close');
                $.messager.alert("提示", "操作失败,稍候重试!", 'error');
            }
        });
    },

    delTicket: function (id) {
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
            success: function(data) {
                if (data.success) {
                    show_msg("删除成功！");
                    $("#show_dg").datagrid('load', {});
                } else {
                    show_msg("删除门票失败");
                }
                $.messager.progress('close');
            },
            error: function(data) {
                $.messager.progress('close');
                $.messager.alert("提示", "操作失败,稍候重试!", 'error');
            }
        });
    },

    delStoreTicket: function (id) {
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
            success: function(data) {
                var result = eval('(' + data + ')');
                if (data.success) {
                    show_msg("删除成功！");
                    $("#storehouse_dg").datagrid('load', {});
//						$("#show_dg").datagrid('load',{});
//						var param = "?ticketId="+data.tId;
//						parent.window.showGuide(4, true,param);
                } else {
                    show_msg("删除门票失败");
                }
                $.messager.progress('close');
            },
            error: function() {
                $.messager.progress('close');
                $.messager.alert("提示", "操作失败,稍候重试!", 'error');
            }
        })
        $.post("/ticket/ticket/delTicket.jhtml",
            {"ticId": id},
            function (data) {

//					var result = eval('(' + data + ')');
                if (data.success) {
                    show_msg("删除成功！");
                    $("#storehouse_dg").datagrid('load', {});
//						$("#show_dg").datagrid('load',{});
//						var param = "?ticketId="+data.tId;
//						parent.window.showGuide(4, true,param);
                } else {
                    show_msg("删除门票失败");
                }

//					$("#sce_div_area").hide();
//					$("#sce_span_area").html(data.fullPath);


            }, 'json'
        );
    },

    editTicket: function (id) {
        var url = "/ticket/ticket/addWizard.jhtml?ticketId=" + id;
        window.location.href = url;
    }



};

$(function () {
    TicketList.initCombox();
    TicketList.initTickeStoreHouseData();
});
