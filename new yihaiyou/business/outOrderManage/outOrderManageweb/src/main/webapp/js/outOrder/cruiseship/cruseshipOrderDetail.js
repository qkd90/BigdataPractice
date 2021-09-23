/**
 * Created by dy on 2016/2/23.
 */
var CruseshipOrderDetail = {
    init: function() {
        CruseshipOrderDetail.initTicketType();

        CruseshipOrderDetail.initJsp();
    },




    initJsp: function() {
        var status = $("#ipt_status").val();
        var isconfim = $("#hipt_isconfim").val();

        if (status == "CANCELED") {
            $("#l_status").html("已取消");
            $("#btn_confimOrder").hide();
            $("#btn_applyCancel").hide();
            $("#edit_actualPrice").hide();
        } else if (status == "PAYED") {
            $("#l_status").html("已支付");
            if (isconfim) {
                $("#btn_confimOrder").hide();
                $("#btn_applyCancel").show();
                $("#edit_actualPrice").hide();
            } else {
                $("#btn_confimOrder").hide();
                $("#btn_applyCancel").show();
                $("#edit_actualPrice").hide();
            }
        } else if (status == "UNPAY") {
            $("#l_status").html("待支付");
            if (isconfim) {
                $("#btn_confimOrder").hide();
                $("#btn_applyCancel").hide();
                $("#edit_actualPrice").hide();
            } else {
                $("#btn_confimOrder").hide();
                $("#btn_applyCancel").hide();
                $("#edit_actualPrice").hide();
            }
        } else if (status == "WAITING") {
            $("#l_status").html("待确认");
            $("#refund_layout").css("display", "none");
            if (isconfim) {
                $("#btn_confimOrder").show();
                $("#btn_applyCancel").hide();
                $("#edit_actualPrice").show();
            } else {
                $("#btn_applyCancel").show();
                $("#btn_confimOrder").hide();
                $("#edit_actualPrice").hide();
            }
        }

        var sum = $("#btn_status").val();

        if (sum == "0") {
            $("#btn_applyCancel").hide();
        } else {
            $("#btn_applyCancel").show();
        }


        var refund = $("#btn_refund_status").val();

        if (refund == "0") {

            $("#refund_layout").css("display", "none");
        } else {
            $("#refund_layout").css("display", "");
            CruseshipOrderDetail.ticketStatus();
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


    editActualPayPrice: function() {

        $("#editActualPriceDialog").dialog({
            title: '修改金额',
            width: 300,
            height: 200,
            resizable:true,
            closed:true,
            cache: false,
            modal: true,
            buttons:[{
                text:'确认',
                handler:function(){
                    var orderId = $("#ipt_id").val();
                    var actualPrice = $("#actual_price").numberbox("getValue");
                    var data = {
                        actualPrice:actualPrice,
                        outOrderId: orderId
                    }

                    var url = "/outOrder/jszxLineOrder/editActualPayPrice.jhtml";

                    $.messager.progress({
                        title:'温馨提示',
                        text:'数据处理中,请耐心等待...'
                    });

                    if (actualPrice != 0 && actualPrice != "") {
                        $.post(url, data,
                            function(result){
                                $.messager.progress("close");
                                show_msg("价格修改成功！");
                                $("#editActualPriceDialog").dialog("close");
                                window.location.reload();
                            }
                        );
                    } else {
                        $.messager.progress("close");
                        show_msg("实际支付金额不能为空或为0！");
                    }




                    //CruseshipOrderDetail.payOrder(orderId);
                }
            },{
                text:'取消',
                handler:function(){
                    $("#editActualPriceDialog").dialog("close");
                }
            }],
            onOpen: function() {

                var totalPrice = $("#totalPrice_label").html();
                totalPrice = parseFloat(totalPrice);
                var actualPrice = $("#actualPrice").html();
                actualPrice = parseFloat(actualPrice);

                $("#actual_price").numberbox({
                    max: totalPrice
                });
                $("#actual_price").numberbox("setValue", actualPrice);
            },
            onBeforeOpen: function() {
                var status = $("#ipt_status").val();


                var gridData = $('#dg_ticketType').datagrid("getData");

                var rows = gridData.rows;

                var flag = true;

                $.each(rows, function(i, perValue) {

                    if (perValue.useStatus != "UNUSED" ) {
                        flag = false;
                        return flag;
                    }

                });

                if (status == "WAITING" && flag) {
                    return true;
                } else {
                    show_msg("订单不处于未确认状态，不可修改价格！");
                    return false;
                }

            },
            onClose: function() {
                $("#actual_price").numberbox("setValue", "");
            }

        });
        $("#editActualPriceDialog").dialog("open");
    },

    confimOrder: function() {

        var orderId = $("#ipt_id").val();

        var ifr = $("#confim_order").children()[0];
        var url = "/outOrder/jszxLineOrder/confimOrder.jhtml?outOrderId=" + orderId ;
        $(ifr).attr("src", url);
        $("#confim_order").dialog({
            title: '确认订单并从经销商处扣款',
            width: 600,
            height: 450,
            resizable:true,
            closed:true,
            cache: false,
            modal: true,
            buttons:[{
                text:'确认',
                handler:function(){
                    CruseshipOrderDetail.payOrder(orderId);
                }
            },{
                text:'取消',
                handler:function(){
                    $("#confim_order").dialog("close");
                }
            }]

        });
        var url = "/outOrder/outOrder/checkBuyerBalance.jhtml";
        $.messager.progress({
            title:'温馨提示',
            text:'检测余额中,请耐心等待...'
        });
        $.post(url, {outOrderId:orderId},
            function(result){
                $.messager.progress("close");
                if (result.success) {
                    $("#confim_order").dialog("open");
                } else {
                    var rechargePrice = result.rechargePrice;
                    if (rechargePrice) {
                        show_msg(result.errorMsg);
                    } else {
                        show_msg(result.errorMsg);
                    }
                }
            }
        );
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
        window.parent.$("#editPanel1").dialog("close");
        window.parent.location.reload();
        window.parent.$("#activity_tab").tabs("select", 1);
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

                        var checkBalanceUrl = "/outOrder/jszxLineOrder/checkBalance.jhtml";

                        $.post(checkBalanceUrl, data,
                            function(result) {
                                if (result.success) {
                                    var url = "/outOrder/jszxLineOrder/cancelOutOrderTickets.jhtml";
                                    $.post(url, data,
                                        function(result) {
                                            if (result.success) {
                                                window.location.reload();
                                                show_msg(result.errorMsg);
                                                $.messager.progress("close");
                                            } else {
                                                $.messager.progress("close");
                                                show_msg(result.errorMsg);
                                                $("#dialog_cancelOrder").dialog("close");
                                            }
                                        }
                                    );

                                    //window.location.reload();
                                    //$.messager.progress("close");
                                } else {
                                    $.messager.progress("close");
                                    show_msg(result.errorMsg);

                                    var money = result.money;

                                    $.messager.confirm('余额不足', '退款余额还需充值：￥'+ money +'，您确定要充值吗？', function(r){
                                        if (r){
                                            CruseshipOrderDetail.onpenRechargeDialog(money);
                                        } else {
                                            show_msg("您已取消充值操作！");
                                        }
                                    });



                                    //$("#dialog_cancelOrder").dialog("close");
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
        CruseshipOrderDetail.dgCancelOrder();
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
                {field:'ticketName',title:'房型',width:'40%'},
                {field:'ticketNo',title:'房型编号',width:'25%'},
                {field:'type',title:'房型',width:'10%',
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
                CruseshipOrderDetail.mergeGridColCells($(this), "ticketName")
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
                {field:'ticketNo',title:'房型编号',width:'15%'},
                {field:'ticketName',title:'房型名称',width:'20%'},
                /*{field:'type',title:'房型',width:'10%',
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
                },*/
                {field:'startTime',title:'有效日期',width:'15%',
                    formatter: function(value, rowData, index) {
                        var startTime = value.substring(0, 10);
                        var endTime = rowData.endTime;
                        endTime = endTime.substring(0, 10);
                        return startTime + "至" + endTime;
                        //return startTime;
                    }
                },
                {field:'useStatus',title:'状态',width:'10%',
                    formatter:function(value, rowData, index) {
                        //var status = $("#ipt_status").val();
                        var status = value;

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
                {field:'actualPay',title:'实际支付金额',width:'10%'},
                {field:'count',title:'数量',width:'10%'}
                //{field:'totalPrice',title:'小计',width:'10%'}
            ]],
            onLoadSuccess: function() {
                CruseshipOrderDetail.mergeGridColCells($(this), "ticketName");
                CruseshipOrderDetail.mergeGridColCells($(this), "type");
                CruseshipOrderDetail.mergeGridColCells($(this), "startTime");
                //CruseshipOrderDetail.mergeGridColCells($(this), "useStatus");
                //CruseshipOrderDetail.mergeGridColCells($(this), "price");
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
                {field:'description',title:'备注',width:'15%'},
                {field:'opt',title:'操作',width:'10%',
                    formatter: function(value, rowData, index) {

                        var confirmRefund = '<a href="javascript:void(0)" style="color:blue;" onclick="CruseshipOrderDetail.confirmRefundOrder('+rowData.id+')">确认退款</a>';

                        if (rowData.useStatus == "REFUNDING") {
                            return confirmRefund;
                        }

                        return "";

                    }
                }

            ]],
            onLoadSuccess: function() {
            CruseshipOrderDetail.mergeGridColCells($(this), "ticketName")
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
                    show_msg(result.errorMsg);
                } else {
                    $.messager.progress("close");
                    //show_msg(result.errorMsg);

                    var money = result.money;

                    $.messager.confirm('余额不足', '退款余额还需充值：￥'+ money +'，您确定要充值吗？', function(r){
                        if (r){

                            CruseshipOrderDetail.onpenRechargeDialog(money);
                        } else {
                            show_msg("您已取消充值操作！");
                        }
                    });

                    //$("#dialog_cancelOrder").dialog("close");
                }
            }
        );

    },


    onpenRechargeDialog: function(money) {


        //var orderId = $("#ipt_id").val();

        var ifr = $("#editPanel").children()[0];
        var url = "/balance/balance/createOrder.jhtml?money=" + money ;
        $(ifr).attr("src", url);

        $("#editPanel").dialog(
            {
            title: '充值余额',
            width: 700,
            //fit:true,
            height: 400,
            closed: false,
            cache: false,
            modal: true
            }
        );
        $("#editPanel").dialog("open");

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
    CruseshipOrderDetail.init();
})