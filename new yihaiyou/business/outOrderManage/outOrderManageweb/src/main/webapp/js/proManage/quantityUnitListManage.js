/**
 * Created by dy on 2016/3/14.
 */

var QuantityUnitPro = {

    init: function () {
        QuantityUnitPro.initJsp();

        QuantityUnitPro.initTicket_dg();
    },

    initJsp: function () {


        $('#tabs').tabs({
            onSelect: function (title, index) {
                if (index == 0) {
                    QuantityUnitPro.initTicket_dg();
                } else if (index == 1) {
                    QuantityUnitPro.initLine_dg();
                }

            }
        });

        $('#sconsume_keyword').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#sconsume_keyword").textbox("setValue", _trim);
            }
        });
        $('#sear_ticketName').textbox({
            onChange: function(value){
                var _trim = $.trim(value);
                $("#sear_ticketName").textbox("setValue", _trim);
            }
        });

    },
    viewline: function (id, name) {
        window.parent.addTab(name, '/line/line/lineView.jhtml?productId=' + id, '');//增加tab
    },
    initLine_dg: function () {

        var url = "/proManage/productManage/getProListByQuantityNum.jhtml?type=line";

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
                    field: 'name', title: '产品名称', width: '50%',
                    formatter: function (value, rowData, index) {
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="QuantityUnitPro.viewline(' + rowData.id + ',\'' + value + '\')">' + value + '</a>';
                        return btn;
                    }
                },
                {field: 'playDay', title: '游玩天数', width: '15%'},
                {field: 'companyUnitName', title: '合作商', width: '15%'},
                {
                    field: 'opt', title: '操作', width: '15%', align: 'center',
                    formatter: function (value, rowData, index) {
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="QuantityUnitPro.openAddLineOrder(' + rowData.id + ')">购买商品</a>';
                        return btn;
                    }
                }
            ]],
            toolbar: '#line_tool'
        });

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


    doSearcLine: function () {
        $('#line_dg').datagrid('load', {
            ticketName: $("#sconsume_keyword").textbox("getValue")
        });
    },

    clearLine: function () {

        $("#consume_form").form("reset");

        $('#line_dg').datagrid('load', {});
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
        $('#ticket_dg').datagrid('load', {});
    },
    initTicket_dg: function () {

        var url = "/proManage/productManage/getProListByQuantityNum.jhtml?type=scenic";

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
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="QuantityUnitPro.viewTicket(' + rowData.id + ',\'' + value + '\')">' + value + '</a>';
                        return btn;
                    }
                },
                {
                    field: 'ticketType', title: '类型', width: '18%',
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
                    field: 'companyUnitName', title: '合作商', width: '18%'},
                {
                    field: 'opt', title: '操作', width: '12%', align: 'center',
                    formatter: function (value, rowData, index) {
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="QuantityUnitPro.openAddTicketOrder(' + rowData.id + ')">购买商品</a>';

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
    QuantityUnitPro.init();
})
