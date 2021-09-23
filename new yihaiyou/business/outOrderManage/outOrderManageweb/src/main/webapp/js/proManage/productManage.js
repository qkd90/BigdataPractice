/**
 * Created by dy on 2016/3/14.
 */

var ProductManage = {

    init: function () {
        ProductManage.initJsp();

        ProductManage.initTicket_dg();
    },


    initJsp: function () {


        $('#tabs').tabs({
            onSelect: function (title, index) {
                if (index == 0) {
                    ProductManage.initTicket_dg();
                } else if (index == 1) {
                    ProductManage.initLine_dg();
                }

            }
        });
    },
    viewline: function (id, name) {
        window.parent.addTab(name, '/line/line/lineView.jhtml?productId=' + id, '');//增加tab
    },
    initLine_dg: function () {

        var url = "/proManage/productManage/getProLineList.jhtml";

        $('#line_dg').datagrid({
            url: url,
            fit: true,
            pagination: true,
            pageList: [10, 20, 50],
            rownumbers: true,
            singleSelect: false,
            striped: true,//斑马线
            columns: [[
                {field: 'id', checkbox: true},
                {
                    field: 'name', title: '产品名称', width: '30%',
                    formatter: function (value, rowData, index) {
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="ProductManage.viewline(' + rowData.id + ',\'' + value + '\')">' + value + '</a>';
                        return btn;
                    }
                },
                {field: 'playDay', title: '游玩天数', width: '10%'},
                {field: 'adultprice', title: '价格（成人）', width: '10%'},
                {field: 'companyName', title: '供应商', width: '10%'},
                {field: 'companyPhone', title: '联系电话', width: '10%'},
                {field: 'companyQQ', title: 'QQ', width: '10%'},
                {
                    field: 'opt', title: '操作', width: '10%', align: 'center',
                    formatter: function (value, rowData, index) {
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="ProductManage.openAddLineOrder(' + rowData.id + ')">购买商品</a>';
                        return btn;
                    }
                }
            ]],
            toolbar: '#line_tool',
            onBeforeLoad : function(data) {   // 查询参数
                data.lineName = $("#sconsume_keyword").textbox("getValue");
            }
        });

    },
    // 查询线路
    doSearchLine : function() {
        $('#line_dg').datagrid('load');
    },

    openAddLineOrder: function (id) {


        var ifr = $("#editPanel").children()[0];
        var url = "/outOrder/jszxLineOrder/addLineOrder.jhtml?lineId=" + id;
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title: '录入订单',
            width: 400,
            height: 400,
            closed: true,
            closable: false,
            cache: false,
            modal: true
        });
        $("#editPanel").dialog("open");
    },


    /****************************
     门票
     ******************************/

    doSearchTicket: function () {
        $('#ticket_dg').datagrid('load', {
            ticketName: $("#sear_ticketName").textbox("getValue"),
            ticketType: $("#com_type").combobox("getValue")
        });
    },
    viewTicket: function (id, name) {
        window.parent.addTab(name, '/ticket/ticket/ticketView.jhtml?ticketId=' + id, '');//增加tab
    },
    clearTicket: function () {

        $("#ticket_form").form("reset");

        $('#ticket_dg').datagrid('reload', {});
    },
    initTicket_dg: function () {

        var url = "/proManage/productManage/getTicketList.jhtml";

        $('#ticket_dg').datagrid({
            url: url,
            fit: true,
            pagination: true,
            pageList: [10, 20, 50],
            rownumbers: true,
            singleSelect: false,
            striped: true,//斑马线
            columns: [[
                {field: 'ck', checkbox: true},
                {
                    field: 'name', title: '产品名称', width: '50%',
                    formatter: function (value, rowData, index) {
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="ProductManage.viewTicket(' + rowData.id + ',\'' + value + '\')">' + value + '</a>';
                        return btn;
                    }
                },
                {field: 'price', title: '价格（起）', width: '22%'},
                {
                    field: 'type', title: '类型', width: '17%',
                    formatter: function (value, rowData, index) {
                        if (value == "scenic") {
                            return "景点门票";
                        }
                        if (value == "shows") {
                            return "演唱会门票";
                        }
                        if (value == "boat") {
                            return "船票";
                        }
                    }
                },
                {
                    field: 'opt', title: '操作', width: '11%', align: 'center',
                    formatter: function (value, rowData, index) {
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="ProductManage.openAddTicketOrder(' + rowData.id + ')">购买商品</a>';

                        return btn;
                    }
                }
            ]],
            toolbar: '#ticket_tool'
        });

    },

    openAddTicketOrder: function (id) {


        var ifr = $("#editPanel").children()[0];
        var url = "/outOrder/outOrder/addTicketOrder.jhtml?ticketId=" + id;
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title: '录入订单',
            width: 400,
            height: 400,
            closed: true,
            closable: false,
            cache: false,
            modal: true
        });
        $("#editPanel").dialog("open");


    }


}

$(function () {
    ProductManage.init();
})
