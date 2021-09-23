/**
 * Created by dy on 2016/11/16.
 */
$(function () {
    SailboatOrderDetail.init();
});

var SailboatOrderDetail = {
    orderId: $("#orderId").val(),
    orderDetailId: $("#orderDetailId").val(),
    getHotelOrderDetailUrl: '/yhy/yhySailBoat/getOrderDetail.jhtml',
    init: function() {
        SailboatOrderDetail.initPageData();
        SailboatOrderDetail.centerBox();
        SailboatOrderDetail.initOrderStatus();
        SailboatOrderDetail.initJsp();
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
            $(".sure").show();
            $(".console").show();
        } else {
            $(".sure").hide();
            $(".console").hide();
        }
    },
    initPageData: function() {
        var data = {orderId: SailboatOrderDetail.orderId, orderDetailId: SailboatOrderDetail.orderDetailId};
        $.ajax({
            url: SailboatOrderDetail.getHotelOrderDetailUrl,
            data: data,
            progress: true,
            success: function(result) {
                if (result.success) {
                    if (result.orderDetails) {
                        SailboatOrderDetail.orderDetailInfo(result.orderDetails);
                    }
                }
            },
            error: function() {}
        });

    },

    confirmOrder: function() {
        var url = "/yhyorder/yhyOrder/doConfirmOrder.jhtml";
        var data = {orderId: SailboatOrderDetail.orderId, orderDetailId:SailboatOrderDetail.orderDetailId};
        //$.each($(":input[name='orderDetailId']"), function(i, per) {
        //    data['orderDetailIds['+i+']'] = per.value;
        //});
        $.messager.show({
            msg: "是否确认订单？",
            type: "containBody",
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

    cancelOrder: function() {
        var url = "/yhyorder/yhyOrder/doCancelOrder.jhtml";
        var data = {orderId: SailboatOrderDetail.orderId, orderDetailId:SailboatOrderDetail.orderDetailId};
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
            msg: "是否取消订单？",
            type: "containBody",
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
                                if (result.success) {
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

                        //$.post(
                        //    url,
                        //    data,
                        //    function(result) {
                        //        if (result.success) {
                        //            $.messager.show({
                        //                msg: "取消成功",
                        //                type: "success",
                        //                timeout: 2000,
                        //                afterClosed: function() {
                        //                    window.location.reload();
                        //                }
                        //            });
                        //
                        //        }
                        //    }
                        //);
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

    orderDetailInfo: function(orderDetailList) {
        $.each(orderDetailList, function(i, perValue) {
            //if (perValue.productType == 'sailboat' || perValue.productType == 'yacht' || perValue.productType == 'scenic' || perValue.productType == 'huanguyou') {
            SailboatOrderDetail.bulidOrderDetailTable(perValue);
            //}
        })
    },

    selCancelReason: function(target) {
        $("#operationDesc").val($(target).val());
    },
    //cancelOrder: function(){
    //    var url = "/yhyorder/yhyOrder/doCancelOrder.jhtml";
    //    var data = {orderId: SailboatOrderDetail.orderId};
    //    $.each($(":input[name='orderDetailId']"), function(i, per) {
    //        data['orderDetailIds['+i+']'] = per.value;
    //    });
    //    if ($("#operationDesc").val()) {
    //        data['remark']=$("#operationDesc").val();
    //    }
    //    $.post(
    //        url,
    //        data,
    //        function(result) {
    //            if (result.success) {
    //                window.location.reload();
    //            }
    //        }
    //    );
    //},

    bulidOrderDetailTable: function(orderDetail) {


        var tr = "";
        tr += '<tr class="headTr headTr_sec">';
        tr += '<input type="hidden" name="orderDetailId" value="'+ orderDetail.id +'">';
        if (orderDetail.seatType) {
            tr += ' <td class="productMess">票型：'+ orderDetail.seatType +'</td>';
        } else {
            tr += ' <td class="productMess">票型：无</td>';
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
    },
    centerBox:function(){
        var left = (window.screen.width - $('.refuseReason').width())/2;
        var top = (window.screen.height - $('.refuseReason').height())/2 - 50;
        $('.refuseReason').css({'left':left,'top':top});
    }

};
