/**
 * Created by dy on 2016/3/14.
 */

var TicketLineManage = {

    init: function() {
        TicketLineManage.initJsp();

        TicketLineManage.initTicket_dg();
        TicketLineManage.initLine_dg();
    },


    initJsp: function() {


        $('#tabs').tabs({
            onSelect:function(title, index){
                if (index == 0) {
                    TicketLineManage.initTicket_dg();
                } else if (index == 1) {
                    TicketLineManage.initLine_dg();
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

    initLine_dg: function() {

        var url = "/proManage/productManage/agentLineList.jhtml";

        $('#agentLine_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:false,
            striped:true,//斑马线
            columns:[[
                {field:'id', checkbox: true },
                {field:'name',title:'产品名称',width:'30%'},
                {field:'status',title:'状态',width:'15%',
                    formatter: function(value, rowData, index) {
                        if (value == "UP") {
                            return "上架";
                        }
                        if (value == "DOWN") {
                            return "下架";
                        }
                    }
                },
                {field:'suplierName',title:'供应商',width:'10%'},
                {field:'companyUnitName',title:'经销商',width:'10%'},
                //{field:'companyPhone',title:'联系电话',width:'10%'},
                //{field:'companyQQ',title:'QQ',width:'10%'},
                {field:'opt',title:'操作',width:'10%', align:'center',
                    formatter: function(value, rowData, index) {
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="TicketLineManage.checkAgentLineDialog('+rowData.id+')">查看</a>';
                        var btnDown = '<a href="javascript:void(0)" style="color:blue;" onclick="TicketLineManage.doSetHide('+rowData.id+');" class="easyui-linkbutton">下架</a>';

                        var btnUp = '<a href="javascript:void(0)" style="color:blue;" onclick="TicketLineManage.doSetShow('+rowData.id+');" class="easyui-linkbutton">上架</a>';
                        var btnDel = '<a href="javascript:void(0)" style="color:blue;" onclick="TicketLineManage.doDelLine('+rowData.id+');" class="easyui-linkbutton">删除</a>';
                        if (rowData.status == "UP") {
                            return btnDown + "&nbsp;&nbsp;" +btn;
                        } else {
                            return btnUp + "&nbsp;&nbsp;" +btn+ "&nbsp;&nbsp;" +btnDel;
                        }

                    }
                }
            ]],
            toolbar: '#line_tool'
        });

    },


    doDelLine: function(id) {

        var idsArray = [];
        idsArray.push(id);
        var ids = idsArray.join(',');
        $.messager.confirm('温馨提示', '您确认删除该条记录？', function(r){
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post("/line/line/delBatch.jhtml",
                    {ids : ids},
                    function(result) {
                        $.messager.progress("close");
                        if(result.success==true){
                            show_msg("线路删除成功");
                            $("#agentLine_dg").datagrid('reload');
                        }else{
                            show_msg("线路删除失败");
                        }
                    }
                );
            }
        });

    },

    doSetShow: function(id) {

        var idsArray = [];
        idsArray.push(id);
        var ids = idsArray.join(',');
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post("/line/line/showBatch.jhtml",
            {ids : ids},
            function(result) {
                $.messager.progress("close");
                if(result.success==true){
                    show_msg("线路上架成功");
                    $("#agentLine_dg").datagrid('reload');
                }else{
                    show_msg(result.errorMsg);
                }
            }
        );

    },

    doSetHide: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        var idsArray = [];
        idsArray.push(id);
        var ids = idsArray.join(',');
        $.post("/line/line/hideBatch.jhtml",
            {ids : ids},
            function(result) {
                $.messager.progress("close");
                if(result.success==true){
                    show_msg("线路上架成功！");
                    $("#agentLine_dg").datagrid('reload');
                }else{
                    show_msg("操作失败！");
                }
            });
    },

    checkAgentLineDialog: function(id) {

        var ifr = $("#editPanel").children()[0];
        var url = "/proManage/productManage/checkAgentLineDialog.jhtml?lineId="+id;
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title: '代理线路查询',
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
        $('#ticket_dg').datagrid('reload', {
            ticketName : $("#sear_ticketName").textbox("getValue"),
            ticketType : $("#com_type").combobox("getValue")
        });
    },

    clearTicket: function() {

        $("#ticket_form").form("reset");

        $('#ticket_dg').datagrid('reload', {
        });
    },
    initTicket_dg: function() {

        var url = "/proManage/productManage/agentTicketList.jhtml";

        $('#ticket_dg').datagrid({
            url:url,
            fit:true,
            pagination:true,
            pageList:[10,20,50],
            rownumbers:true,
            singleSelect:false,
            striped:true,//斑马线
            columns:[[
                { field:'ck', checkbox: true },
                {field:'name',title:'产品名称',width:'30%'},
                //{field:'price',title:'价格（起）',width:'20%'},
                {field:'status',title:'状态',width:'15%',
                    formatter: function(value, rowData, index) {
                        if (value == "UP") {
                            return "上架";
                        }
                        if (value == "DOWN") {
                            return "下架";
                        }
                    }
                },
                {field:'ticketType',title:'类型',width:'15%',
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
                {field:'suplierName',title:'供应商',width:'10%'},
                {field:'companyUnitName',title:'经销商',width:'10%'},
                {field:'opt',title:'操作',width:'10%', align:'center',
                    formatter: function(value, rowData, index) {

                        var btnShow = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='TicketLineManage.showOrHide(" + rowData.id + ")'>下架</a>&nbsp;";
                        var btnHide = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='TicketLineManage.showOrHide(" + rowData.id + ")'>上架</a>";
                        var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="TicketLineManage.checkAgentTicketDialog('+rowData.id+')">查看</a>';
                        var btnDel = "<a href='javascript:void(0)' class='delRecplanDay' style='color:blue;text-decoration: underline;' onclick='TicketLineManage.delStoreTicket(" + rowData.id + ")'>删除</a>&nbsp;";
                        if (rowData.status == "UP") {
                            return btnShow + "&nbsp;&nbsp;" +btn;
                        } else {
                            return btnHide + "&nbsp;&nbsp;" +btn+ "&nbsp;&nbsp;" +btnDel;
                        }


                    }
                }
            ]],
            toolbar: '#ticket_tool'
        });

    },

    delStoreTicket: function (id) {

        $.messager.confirm('温馨提示', '您确认删除该条记录？', function(r){
            if (r) {

                $.messager.progress({
                    title: '温馨提示',
                    text: '数据处理中,请耐心等待...'
                });
                $.post("/ticket/ticket/delTicket.jhtml",
                    {"ticId": id},
                    function (data) {
                        if (data.success) {
                            $.messager.progress("close");
                            show_msg("删除成功！");
                            $("#ticket_dg").datagrid('reload');
                        } else {
                            $.messager.progress("close");
                            show_msg("操作失败！");
                        }
                    }, 'json'
                );
            }
        });
    },
    showOrHide: function (id) {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });

        $.post("/ticket/ticket/showOrHide.jhtml",
            {"ticId": id},
            function (data) {

                if (data.success) {
                    $.messager.progress("close");
                    show_msg(data.temp);
                    $("#ticket_dg").datagrid('reload');
                } else {
                    $.messager.progress("close");
                    show_msg("操作失败！");
                }
            }, 'json'
        );

    },

    checkAgentTicketDialog: function(id) {

        var ifr = $("#editPanel").children()[0];
        var url = "/proManage/productManage/checkAgentTicketDialog.jhtml?ticketId="+id;
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title: '代理门票查询',
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
    TicketLineManage.init();
})
