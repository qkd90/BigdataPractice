/**
 * Created by dy on 2016/2/23.
 */
var CheckLineDetail = {
    init: function() {
        CheckLineDetail.initTicketType();

        CheckLineDetail.initJsp();
    },




    initJsp: function() {

        var status = $("#ipt_status").val();

        var isconfim = $("#hipt_isconfim").val();


        var sum = $("#btn_status").val();

        if (sum == "0") {
            $('#btn_msgCount').linkbutton('disable');
            $("#btn_applyCancel").css("display", "none");
        } else {
            $("#btn_applyCancel").css("display", "");
        }

        if (status == "CANCELED") {
            $("#l_status").html("已取消");
            $("#btn_confimOrder").hide();
            $("#btn_applyCancel").hide();
            $("#btn_msgAgain").hide();
            $("#btn_msgCount").hide();
        } else if (status == "PAYED") {
            $("#l_status").html("已支付");
            if (isconfim) {
                $("#btn_confimOrder").hide();
                $("#btn_applyCancel").show();
                $("#btn_msgCount").show();
            } else {
                $("#btn_confimOrder").hide();
                $("#btn_applyCancel").show();
                $("#btn_msgCount").show();
            }
        } else if (status == "UNPAY") {
            $("#l_status").html("待支付");
            if (isconfim) {
                $("#btn_confimOrder").hide();
                $("#btn_applyCancel").hide();
                $("#btn_msgAgain").hide();
                $("#btn_msgCount").hide();
            } else {
                $("#btn_confimOrder").hide();
                $("#btn_applyCancel").hide();
                $("#btn_msgAgain").hide();
                $("#btn_msgCount").hide();
            }
        } else if (status == "WAITING") {
            $("#l_status").html("待确认");
            $("#refund_layout").css("display", "none");
            if (isconfim) {
                $("#btn_confimOrder").show();
                $("#btn_applyCancel").hide();
                $("#btn_msgAgain").hide();
                $("#btn_msgCount").show();
            } else {
                $("#btn_applyCancel").show();
                $("#btn_confimOrder").hide();
                $("#btn_msgAgain").hide();
                $("#btn_msgCount").show();
            }

            //$("#btn_applyCancel").hide();
        }



        var refund = $("#btn_refund_status").val();

        if (refund == "0") {

            $("#refund_layout").css("display", "none");
        } else {
            $("#refund_layout").css("display", "");
            CheckLineDetail.ticketStatus();
        }

        $("#dialog_cancelOrder").dialog("close");

        var payDate = $("#payDateId").attr("data-value");

        if (payDate.length > 2) {
            payDate = payDate.substr(0, payDate.length - 2);
        }

        $("#payDateId").html(payDate);

        var desc_source = $("#la_descId").attr("data-value");

        if (desc_source.length > 0) {
            $("#desc_source").show();
            $("#la_descId").html(desc_source);
            //desc_source = desc_source.substr(0, desc_source.length - 1);
        } else {
            $("#desc_source").hide();
        }








    },


    sendMsgAgain: function() {


        var url = "/outOrder/jszxLineOrder/sendLineMsgAgain.jhtml";

        $.post(url, {outOrderId:$("#ipt_id").val()},
            function(result){

                var msgCount = $("#msgCount").html();
                msgCount = Number(msgCount);

                if (result.success) {
                    var ticketMsgCount = result.ticketMsgCount;
                    ticketMsgCount = Number(ticketMsgCount);
                    msgCount = ticketMsgCount;
                }
                $("#msgCount").html(msgCount);
            }
        );


    },


    confimOrder: function() {

        var orderId = $("#ipt_id").val();

        var ifr = $("#confim_order").children()[0];
        var url = "/outOrder/jszxLineOrder/confimOrder.jhtml?outOrderId=" + orderId ;
        $(ifr).attr("src", url);
        $("#confim_order").dialog({
            title: '确认订单并支付',
            width: 600,
            height: 450,
            resizable:true,
            closed:true,
            cache: false,
            modal: true,
            buttons:[{
                text:'确认',
                handler:function(){
                    CheckLineDetail.payOrder(orderId);
                }
            },{
                text:'取消',
                handler:function(){
                    $("#confim_order").dialog("close");
                }
            }]

        });
        $("#confim_order").dialog("open");
    },


    payOrder: function(orderId) {

        var url = "/outOrder/jszxLineOrder/confimLineOrder.jhtml";
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post(url, {outOrderId:orderId},
            function(result){
                $.messager.progress("close");
                $.messager.alert('支付提示','订单支付成功！','info');
                window.parent.$("#editPanel").dialog("close");
                window.parent.location.reload();
                //show_msg("订单详情请请前往直销订单查看！")
                //window.parent.location.href = "/outOrder/outOrder/manage.jhtml";
            }
        );

    },

    backParent: function() {
        window.parent.$("#editPanel").dialog("close");
        //window.parent.location.reload();
        window.parent.$("#activity_tab").tabs("select", "线路订单");
        window.parent.$("#dg_line").datagrid("reload");

    },


    applyCancelDialog: function() {


        $("#dialog_cancelOrder").dialog({
            title: '取消订单',
            width: 700,
            height: 400,
            //zIndex: 50,
            closed: false,
            cache: false,
            modal: true,
            buttons: [{
                text:'提交',
                iconCls:'icon-ok',
                handler:function(){

                    var rows = $('#dg_cancelOrder').datagrid("getChecked");

                    if (rows.length > 0) {

                        //alert("尚未完成！");

                        var otIds = "";

                        $.each(rows, function(i, perValue) {

                            otIds = otIds + perValue.id + ",";

                        });

                        if (otIds.length > 0 ) {
                            otIds = otIds.substr(0, otIds.length - 1);
                        }

                        var data = {
                            otIds:otIds,
                            outOrderId:$("#ipt_id").val(),
                            desc:$("#ipt_desc").val()
                        }
                        $.messager.progress({
                            title:'温馨提示',
                            text:'数据处理中,请耐心等待...'
                        });

                        var url = "/outOrder/jszxLineOrder/cancelOutOrderTickets.jhtml";
                        $.post(url, data,
                            function(result) {
                                if (result.success) {
                                    window.location.reload();
                                    $.messager.progress("close");
                                } else {
                                    $.messager.progress("close");
                                    show_msg(result.errorMsg);
                                    $("#dialog_cancelOrder").dialog("close");
                                }
                            }
                        );

                    } else {
                        show_msg("请选择需要取消的票号！");
                    }
                }
            },{
                text:'取消',
                iconCls:'icon-cancel',
                handler:function(){
                    $("#dialog_cancelOrder").dialog("close");
                }
            }]
        });
        CheckLineDetail.dgCancelOrder();
        $("#dialog_cancelOrder").dialog("open");
    },

    dgCancelOrder: function() {


        var url = "/outOrder/jszxLineOrder/getUnusedTicketStatusList.jhtml?outOrderId="+$("#ipt_id").val();
        $('#dg_cancelOrder').datagrid({
            url:url,
            fit:true,
            width: '100%',
            height: 200,
            columns:[[
                {field:'id',checkbox:true,title:'',width:'1%'},
                {field:'ticketName',title:'线路类型名称',width:'40%'},
                {field:'ticketNo',title:'类型编号',width:'25%'},
                {field:'type',title:'类型',width:'10%',
                    formatter: function(value, rowData, index) {
                        //'adult','student','child','oldman','taopiao','other','team'
                        if (value == "adult") {
                            return "成人";
                        }
                        if (value == "student") {
                            return "学生票";
                        }
                        if (value == "child") {
                            return "儿童";
                        }
                        if (value == "taopiao") {
                            return "套票";
                        }
                        if (value == "team") {
                            return "团体";
                        }
                        if (value == "oldman") {
                            return "老人";
                        }
                        if (value == "other") {
                            return "其他";
                        }
                    }
                },
                {field:'useStatus',title:'状态',width:'20%',
                    formatter:function(value, rowData, index) {
                        if (value == "UNUSED") {
                            return "未使用";
                        }
                        if (value == "CANCEL") {
                            $("#").datagrid('hideColumn','id')
                            return "已取消";
                        }
                        if (value == "REFUNDING") {
                            return "退款中";
                        }
                        if (value == "USED") {
                            return "已使用";
                        }
                    }
                }
            ]],
            onLoadSuccess: function() {
                CheckLineDetail.mergeGridColCells($(this), "ticketName")
            }
        });

    },

    initTicketType: function() {

        var url = "/outOrder/jszxLineOrder/getConfimOrderDetails.jhtml?outOrderId="+$("#ipt_id").val();
        $('#dg_ticketType').datagrid({
            url:url,
            //fit:true,
            singleSelect:true,
            width:'90%',
            height:'250px',
            columns:[[
                {field:'ticketNo',title:'线路类型编号',width:'15%'},
                {field:'ticketName',title:'线路类型名称',width:'20%'},
                {field:'type',title:'类型',width:'10%',
                    formatter: function(value, rowData, index) {
                        //'adult','student','child','oldman','taopiao','other','team'
                        if (value == "adult") {
                            return "成人";
                        }
                        if (value == "student") {
                            return "学生票";
                        }
                        if (value == "child") {
                            return "儿童";
                        }
                        if (value == "taopiao") {
                            return "套票";
                        }
                        if (value == "team") {
                            return "团体";
                        }
                        if (value == "oldman") {
                            return "老人";
                        }
                        if (value == "other") {
                            return "其他";
                        }
                    }
                },
                {field:'startTime',title:'出发日期',width:'10%',
                    formatter: function(value, rowData, index) {
                        var startTime = value.substring(0, 10);
                        //var endTime = rowData.endTime;
                        //endTime = endTime.substring(0, 10);
                        //return startTime + "至" + endTime;
                        return startTime;
                    }
                },
                {field:'useStatus',title:'状态',width:'10%',
                    formatter:function(value, rowData, index) {
                        var status = $("#ipt_status").val();

                        if (status == "UNPAY") {
                            return "待支付";
                        } else {
                            if (value == "UNUSED") {
                                return "未使用";
                            }
                            if (value == "CANCEL") {
                                return "已取消";
                            }
                            if (value == "REFUNDING") {
                                return "退款中";
                            }
                            if (value == "USED") {
                                return "已使用";
                            }
                        }
                    }
                },
                {field:'price',title:'单价',width:'10%'},
                {field:'actualPay',title:'实际支出',width:'10%'},
                {field:'count',title:'数量',width:'10%'}
                //{field:'totalPrice',title:'小计',width:'10%'}
            ]],
            onLoadSuccess: function() {
                CheckLineDetail.mergeGridColCells($(this), "ticketName");
                CheckLineDetail.mergeGridColCells($(this), "type");
                CheckLineDetail.mergeGridColCells($(this), "startTime");
                CheckLineDetail.mergeGridColCells($(this), "useStatus");
                CheckLineDetail.mergeGridColCells($(this), "price");
            }
        });

    },


    ticketStatus: function() {

        var url = "/outOrder/jszxLineOrder/getTicketStatusList.jhtml?outOrderId="+$("#ipt_id").val();
        $('#dg_ticketStatus').datagrid({
            url:url,
            //fit:true,
            title:"订单退款状态",
            width:'90%',
            height:'200px',
            singleSelect:true,
            columns:[[
                {field:'ticketNo',title:'类型编号',width:'15%'},
                {field:'ticketName',title:'线路类型名称',width:'30%'},
                {field:'useStatus',title:'状态',width:'10%',
                    formatter:function(value, rowData, index) {
                        if (value == "UNUSED") {
                            return "未使用";
                        }
                        if (value == "CANCEL") {
                            return "已取消";
                        }
                        if (value == "REFUNDING") {
                            return "退款中";
                        }
                        if (value == "USED") {
                            return "已使用";
                        }
                    }
                },
                {field:'useTime',title:'使用时间',width:'10%',
                    formatter:function(value, rowData, index) {
                        if (!value) {

                            if (rowData.useStatus == "CANCEL") {
                                return "已取消";
                            }
                            if (rowData.useStatus == "UNUSED") {
                                return "未使用";
                            }
                            if (rowData.useStatus == "REFUNDING") {
                                return "退款中";
                            }
                        }
                        return value;
                    }
                },
                {field:'refundCount',title:'数量',width:'10%'},
                {field:'description',title:'备注',width:'15%'}
            ]],
            onLoadSuccess: function() {
            CheckLineDetail.mergeGridColCells($(this), "ticketName")
            }
        }
        );

    },

    confirmRefundOrder: function(id) {

        var data = {

            orderTicketId: id

        };

        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });

        var url = "/outOrder/jszxLineOrder/confirmOrderDetail.jhtml";
        $.post(url, data,
            function(result) {
                if (result.success) {
                    window.location.reload();
                    $.messager.progress("close");
                } else {
                    $.messager.progress("close");
                    show_msg(result.errorMsg);
                    //$("#dialog_cancelOrder").dialog("close");
                }
            }
        );

    },

    mergeGridColCells: function(grid, rowFildName) {
        var rows=grid.datagrid('getRows' );
        var startIndex=0;
        var endIndex=0;
        if(rows.length< 1)
        {
            return;
        }
        $.each(rows, function(i,row){
            if(row[rowFildName]==rows[startIndex][rowFildName])
            {
                endIndex=i;
            }
            else
            {
                grid.datagrid( 'mergeCells',{
                    index: startIndex,
                    field: rowFildName,
                    rowspan: endIndex -startIndex+1
                });
                startIndex=i;
                endIndex=i;
            }

        });
        grid.datagrid( 'mergeCells',{
            index: startIndex,
            field: rowFildName,
            rowspan: endIndex -startIndex+1
        });
    }
}

$(function(){
    CheckLineDetail.init();
})