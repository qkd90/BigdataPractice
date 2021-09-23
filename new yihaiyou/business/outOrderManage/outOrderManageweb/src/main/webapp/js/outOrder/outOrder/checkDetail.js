/**
 * Created by dy on 2016/2/23.
 */
var CheckDetail = {
    init: function() {
        CheckDetail.initTicketType();

        CheckDetail.initJsp();
    },


    sendMsgAgain: function() {


        var url = "/outOrder/outOrder/sendTicketMsgAgain.jhtml";

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


    initJsp: function() {

        var status = $("#ipt_status").val();


        var sum = $("#btn_status").val();

        if (sum == "0") {
            $('#btn_msgAgain').linkbutton('disable');
            $("#btn_applyCancel").css("display", "none");
        } else {
            $("#btn_applyCancel").css("display", "");
        }

        if (status == "CANCELED") {
            $("#l_status").html("已取消");
            //$("#layout_status").css("display","");
            $("#btn_confimOrder").hide();
            $("#btn_msgAgain").hide();
        } else if (status == "PAYED") {
            $("#l_status").html("已支付");
            //$("#layout_status").css("display","none");
            $("#btn_confimOrder").hide();
        } else if (status == "UNPAY") {
            $("#l_status").html("待支付");
            $("#btn_applyCancel").hide();
            $("#btn_msgAgain").hide();
            //$("#layout_status").css("display", "none");
        } else if (status == "WAITING") {
            $("#btn_msgAgain").hide();
        }



        var refund = $("#btn_refund_status").val();

        if (refund == "0") {
            $("#refund_layout").css("display", "none");
        } else {
            $("#refund_layout").css("display", "");
            CheckDetail.ticketStatus();
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


    confimOrder: function() {

        var orderId = $("#ipt_id").val();

        var ifr = $("#confim_order").children()[0];
        var url = "/outOrder/outOrder/confimOrder.jhtml?outOrderId=" + orderId ;
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
                text:'确认支付',
                handler:function(){
                    CheckDetail.payOrder(orderId);
                }
            },{
                text:'取消支付',
                handler:function(){
                    $("#confim_order").dialog("close");
                }
            }]

        });
        $("#confim_order").dialog("open");
    },


    payOrder: function(orderId) {

        var url = "/outOrder/outOrder/payOutOrder.jhtml";
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
        window.parent.$("#editPanel1").dialog("close");
        window.parent.$("#activity_tab").tabs("select", "门票订单");
        window.parent.$("#dg_outOrder").datagrid("reload");
        //window.parent.location.reload();
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

                        var url = "/outOrder/outOrder/cancelOutOrderTickets.jhtml";
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
        CheckDetail.dgCancelOrder();
        $("#dialog_cancelOrder").dialog("open");
    },

    dgCancelOrder: function() {


        var url = "/outOrder/outOrder/getUnusedTicketStatusList.jhtml?outOrderId="+$("#ipt_id").val();
        $('#dg_cancelOrder').datagrid({
            url:url,
            fit:true,
            width: '100%',
            height: 200,
            columns:[[
                {field:'id',checkbox:true,title:'',width:'1%'},
                {field:'ticketName',title:'门票类型',width:'50%'},
                {field:'ticketNo',title:'门票号',width:'25%'},
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
            ]]
        });

    },

    initTicketType: function() {

        var url = "/outOrder/outOrder/getConfimOrderDetails.jhtml?outOrderId="+$("#ipt_id").val();
        $('#dg_ticketType').datagrid({
            url:url,
            fit:true,
            singleSelect:true,
            columns:[[
                {field:'ticketName',title:'门票类型',width:'20%'},
                {field:'type',title:'类型',width:'5%',
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
                {field:'startTime',title:'有效期',width:'15%',
                    formatter: function(value, rowData, index) {
                        var startTime = value.substring(0, 10);
                        var endTime = rowData.endTime;
                        endTime = endTime.substring(0, 10);
                        return startTime + "至" + endTime;
                    }
                },
                {field:'code',title:'验证码',width:'10%',
                    formatter: function(value, rowData, index) {

                        var status = $("#ipt_status").val();

                        if (status == "UNPAY") {
                            return "待支付";
                        }
                        return value;
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
                {field:'detailStatus',title:'使用状态',width:'15%'},
                {field:'price',title:'单价',width:'5%'},
                {field:'count',title:'数量',width:'5%'},
                {field:'totalPrice',title:'小计',width:'5%'},
                {field:'actualPay',title:'实际支出',width:'5%'}
            ]]
        });

    },


    ticketStatus: function() {

        var url = "/outOrder/outOrder/getTicketStatusList.jhtml?outOrderId="+$("#ipt_id").val();
        $('#dg_ticketStatus').datagrid({
            url:url,
            fit:true,
            title:"门票状态",
            singleSelect:true,
            columns:[[
                {field:'ticketName',title:'门票类型',width:'20%'},
                {field:'ticketNo',title:'门票号',width:'15%'},
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
                {field:'useTime',title:'使用时间',width:'15%',
                    formatter:function(value, rowData, index) {
                        if (!value) {

                            if (rowData.useStatus == "CANCEL") {
                                return "已取消";
                            }
                            if (rowData.useStatus == "UNUSED") {
                                return "未使用";
                            }
                            if (rowData.useStatus == "REFUNDING") {
                                return "未使用";
                            }
                        }
                        return value;
                    }
                },
                {field:'refundCount',title:'剩余数量',width:'5%',
                    formatter:function(value, rowData, index) {

                        value = rowData.count - value ;

                        return value;
                    }
                },
                {field:'detailStatus',title:'使用状态',width:'15%'},
                {field:'count',title:'备注',width:'15%',
                    formatter: function(value, rowData, index) {

                        if (value == rowData.refundCount) {
                            return "全部退款";
                        } else if (rowData.refundCount >= 1) {
                            return "已退"+rowData.refundCount+"张票";
                        } else {
                            return "无";
                        }
                    }
                }
            ]]
        });

    }
}

$(function(){
    CheckDetail.init();
})