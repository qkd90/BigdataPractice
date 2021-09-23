/**
 * Created by dy on 2016/3/14.
 */

var AgentProduct = {

    init: function() {
        AgentProduct.initJsp();

        AgentProduct.initTicket_dg();
        AgentProduct.initLine_dg();
    },


    initJsp: function() {


        $('#tabs').tabs({
            onSelect:function(title, index){
                if (index == 0) {
                    AgentProduct.initTicket_dg();
                } else if (index == 1) {
                    AgentProduct.initLine_dg();
                }

            }
        });

        //var hipt_userBalanceStr = $("#hipt_userBalance").val();
        //var html = '';
        //html += '<div style="height: 24px;padding: 1px;right: 20px;border-bottom:1px solid #95B8E7; ">';
        //if (hipt_userBalanceStr) {
        //    html += '   <label style="font-size: 15px;">账户余额￥：<span style="color: red; font-weight: 700; font-size: 15px;">'+hipt_userBalanceStr+'</span>元</label>';
        //} else {
        //    html += '   <label style="font-size: 15px;">账户余额￥：<span style="color: red; font-weight: 700; font-size: 15px;">0</span>元</label>';
        //}
        //html += '</div>';
        //$(".tabs-wrap").css("width","85%");
        //$(".tabs-wrap").css("float","left");

        //$(".tabs-wrap").after(html);





    },

    doSearchLine: function() {
        $('#line_dg').datagrid('load', {
            lineName : $("#line_keyword").textbox("getValue")
        });
    },

    clearLine: function() {

        $("#line_form").form("reset");
        $('#line_dg').datagrid('load');
    },

    initLine_dg: function() {

        var url = "/proManage/productManage/getProLineList.jhtml";

        $('#line_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:true,
            striped:true,//斑马线
            columns:[[
                {field:'id', checkbox: true },
                {field:'name',title:'产品名称',width:'30%'},
                {field:'playDay',title:'游玩天数',width:'10%'},
                {field:'adultprice',title:'价格（成人）',width:'10%'},
                {field:'companyName',title:'供应商',width:'10%'},
                {field:'companyPhone',title:'联系电话',width:'10%'},
                {field:'companyQQ',title:'QQ',width:'10%'},
                {field:'opt',title:'操作',width:'10%', align:'center',
                    formatter: function(value, rowData, index) {
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="AgentProduct.agentLineDialog('+rowData.id+')">代理</a>';
                        return btn;
                    }
                }
            ]],
            toolbar: '#line_tool'
        });

    },



    agentLineDialog: function(id) {



        var url = "/proManage/productManage/checkAgentLine.jhtml";
        var data = {
            lineId:id
        };

        $.post(url,
            data,
            function(result) {
                if (result.success) {

                    var ifr = $("#editPanel").children()[0];
                    url = "/proManage/productManage/agentLineDialog.jhtml?lineId="+id;
                    $(ifr).attr("src", url);
                    $("#editPanel").dialog({
                        title: '代理线路',
                        width: 400,
                        height: 400,
                        closed: true,
                        closable:false,
                        cache: false,
                        modal: true
                    });
                    $("#editPanel").dialog("open");


                } else {
                    show_msg("该线路已代理！请重新选择。");
                }
            }
        );

    },

    agentTicketDialog: function(id) {

        var url = "/proManage/productManage/checkAgentTicket.jhtml";
        var data = {
            ticketId:id
        };

        $.post(url,
            data,
            function(result) {
                if (result.success) {

                    var ifr = $("#editPanel").children()[0];
                    url = "/proManage/productManage/agentTicketDialog.jhtml?ticketId="+id;
                    $(ifr).attr("src", url);
                    $("#editPanel").dialog({
                        title: '代理门票',
                        width: 400,
                        height: 400,
                        closed: true,
                        closable:false,
                        cache: false,
                        modal: true
                    });
                    $("#editPanel").dialog("open");
                } else {
                    show_msg("该门票已代理！请重新选择。");
                }
            }
        );

    },


    openAddLineOrder: function(id) {


        var ifr = $("#editPanel").children()[0];
        var url = "/outOrder/jszxLineOrder/addLineOrder.jhtml?lineId="+id;
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title: '录入订单',
            width: 400,
            height: 400,
            closed: true,
            closable:false,
            cache: false,
            modal: true
        });
        $("#editPanel").dialog("open");
    },


    /****************************
     门票
     ******************************/

    doSearchTicket: function() {
        $('#ticket_dg').datagrid('load', {
            ticketName : $("#sear_ticketName").textbox("getValue"),
            ticketType : $("#com_type").combobox("getValue")
        });
    },

    clearTicket: function() {

        $("#ticket_form").form("reset");

        $('#ticket_dg').datagrid('load', {
        });
    },
    initTicket_dg: function() {

        var url = "/proManage/productManage/getTicketList.jhtml";

        $('#ticket_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:true,
            striped:true,//斑马线
            columns:[[
                { field:'ck', checkbox: true },
                {field:'name',title:'产品名称',width:'50%'},
                {field:'price',title:'价格（起）',width:'20%'},
                {field:'type',title:'类型',width:'15%',
                    formatter: function(value, rowData, index) {
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
                {field:'opt',title:'操作',width:'10%', align:'center',
                    formatter: function(value, rowData, index) {
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="AgentProduct.agentTicketDialog('+rowData.id+')">代理</a>';

                        return btn;
                    }
                }
            ]],
            toolbar: '#ticket_tool'
        });

    },

    openAddTicketOrder: function(id) {


        var ifr = $("#editPanel").children()[0];
        var url = "/outOrder/outOrder/addTicketOrder.jhtml?ticketId="+id;
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title: '录入订单',
            width: 400,
            height: 400,
            closed: true,
            closable:false,
            cache: false,
            modal: true
        });
        $("#editPanel").dialog("open");


    }





}

$(function() {
    AgentProduct.init();
})
