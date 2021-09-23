
var HotelOrderDetail = {
    orderId:$("#orderId").val(),
    detailId:$("#detailId").val(),
    init: function() {
        HotelOrderDetail.initPageData();
        HotelOrderDetail.initOrderStatus();
        HotelOrderDetail.initJsp();
    },

    initJsp: function() {
        $.each($(".idNumber"), function(i, perIdNum) {
            if ($(perIdNum).html().length > 0) {
                $(perIdNum).html($(perIdNum).html().substr(0, 10) + "****" + $(perIdNum).html().substr(14, $(perIdNum).html().length));
            }
        });
    },

    initOrderStatus: function() {
        var status = $(".orderState").attr("data-status");
        if (status == 'UNCONFIRMED') {
            $(".confirm").show();
            $(".cancel").show();
            $(".payedconfirm").hide();
        } else if (status == 'PAYED') {
            $(".payedconfirm").show();
            $(".confirm").hide();
            $(".cancel").show();
        } else {
            $(".payedconfirm").hide();
            $(".confirm").hide();
            $(".cancel").hide();
        }
    },

    confirmOrder: function() {
        var url = "/yhyorder/yhyOrder/doConfirmOrder.jhtml";
        var data = {orderId: HotelOrderDetail.orderId, orderDetailId:HotelOrderDetail.detailId};
        //$.each($(":input[name='orderDetailId']"), function(i, per) {
        //    data['orderDetailIds['+i+']'] = per.value;
        //});

        $.ajax({
            url: url,
            data: data,
            progress: true,
            success: function(result) {
                if (result.success) {
                    $.messager.show({
                        msg: "确认成功",
                        type: "success",
                        timeout: 3000,
                        afterClosed: function() {
                            window.location.reload();
                        }
                    });
                } else {
                    $.messager.show({
                        msg: result.errorMsg,
                        type: "error",
                        timeout: 2000,
                        afterClosed: function() {
                            //window.location.reload();
                        }
                    });
                }
            },
            error: function() {}
        });

    },

    doPayedConfirm: function() {

        var data = {orderId: HotelOrderDetail.orderId, orderDetailId:HotelOrderDetail.detailId};
        //$.each($(":input[name='orderDetailId']"), function(i, per) {
        //    data['orderDetailIds['+i+']'] = per.value;
        //});
        $.messager.show({
            msg: "订单确认后需为房客预留房间，是否确认订单？",
            iconCls: "containBody",
            btns: [
                {
                    btnText:"确定",
                    btnCls:'btn-info',
                    callback: function(event) {
                        var url = "/balance/balance/doPayedConfirm.jhtml";

                        $.ajax({
                            url: url,
                            data: data,
                            progress: true,
                            success: function(result) {
                                if (result.success) {
                                    $.messager.show({
                                        msg: "确认成功",
                                        type: "success",
                                        timeout: 2000,
                                        afterClosed: function() {
                                            window.location.reload();
                                        }
                                    });
                                } else {
                                    $.messager.show({
                                        msg: result.errorMsg,
                                        type: "error",
                                        timeout: 2000,
                                        afterClosed: function() {
                                            //window.location.reload();
                                        }
                                    });
                                }
                            },
                            error: function() {}
                        });

                    }
                },
                {
                    btnText:"取消",
                    btnCls:'btn-default',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                    }
                }
            ]
        });


    },

    initPageData: function() {
        var url = "/yhy/yhyHotel/getHotelOrderDetail.jhtml";
        var data = {orderId: HotelOrderDetail.orderId, detailId: HotelOrderDetail.detailId};
        $.ajax({
            url: url,
            data: data,
            progress: true,
            success: function(result) {
                if (result.success) {
                    if (result.orderDetails) {
                        HotelOrderDetail.orderDetailInfo(result.orderDetails);
                    }
                }
            },
            error: function() {}
        });
    },
    orderDetailInfo: function(orderDetailList) {
        $.each(orderDetailList, function(i, perValue) {
            if (perValue.productType == 'hotel') {
                HotelOrderDetail.bulidOrderDetailTable(perValue);
            }
        })
    },
    selCancelReason: function(target) {
        $("#operationDesc").val($(target).val());
    },
    cancelOrder: function(){
        var url = "/balance/balance/doFailOrderRefund.jhtml";
        var data = {orderId: HotelOrderDetail.orderId, orderDetailId: HotelOrderDetail.detailId};
        //$.each($(":input[name='orderDetailId']"), function(i, per) {
        //    data['orderDetailIds['+i+']'] = per.value;
        //});
        if ($("#operationDesc").val()) {
            data['remark']=$("#operationDesc").val();
        } else {
            $.messager.show({
                msg: "请完善拒绝理由",
                type: "error",
                timeout: 2000
            });
            return;
        }
        $.messager.show({
            msg: "订单取消后房费将退回至房客，是否取消订单？",
            iconCls: "containBody",
            btns: [
                {
                    btnText:"确定",
                    btnCls:'btn-info',
                    callback: function(event) {


                        $.ajax({
                            url: url,
                            data: data,
                            progress: true,
                            success: function(result) {
                                if (result.flag) {
                                    $.messager.show({
                                        msg: "取消成功",
                                        type: "success",
                                        timeout: 2000,
                                        afterClosed: function() {
                                            window.location.reload();
                                        }
                                    });
                                } else {
                                    $.messager.show({
                                        msg: result.reMsg,
                                        type: "error",
                                        timeout: 2000,
                                        afterClosed: function() {
                                            //window.location.reload();
                                        }
                                    });
                                }
                            },
                            error: function() {}
                        });
                    }
                },
                {
                    btnText:"取消",
                    btnCls:'btn-default',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                    }
                }
            ]
        });


    },
    bulidOrderDetailTable: function(orderDetail) {
        var tr = "";
        tr += '<tr class="headTr headTr_sec">';
        tr += '<input type="hidden" name="orderDetailId" value="'+ orderDetail.id +'">';
        if (orderDetail.seatType) {
            tr += ' <td class="productMess">房型：'+ orderDetail.seatType +'</td>';
        } else {
            tr += ' <td class="productMess">房型：无</td>';
        }
        if (orderDetail.orderBillPrice) {
            tr += ' <td class="finalPrice">'+ orderDetail.orderBillPrice +'</td>';
        } else {
            tr += ' <td class="finalPrice">0</td>';
        }
        if (orderDetail.finalPrice) {
            tr += ' <td class="sailPrice">'+ orderDetail.finalPrice +'</td>';
        } else {
            tr += ' <td class="sailPrice">160</td>';
        }
        if (orderDetail.operator && orderDetail.operator.userName) {
            tr += ' <td class="operator">'+ orderDetail.operator.userName +'</td>';
        } else {
            tr += ' <td class="operator">无</td>';
        }
        tr += '</tr>';
        $(".table-striped").append(tr);
    },
    goback: function() {
        history.go(-1);
    }


};
$(function() {
    HotelOrderDetail.init();
});