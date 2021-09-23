/**
 * Created by dy on 2016/11/23.
 */

var SailboatOrderDetailCancel = {
    orderId: $("#orderId").val(),
    orderDetailId: $("#orderDetailId").val(),
    getHotelOrderDetailUrl: '/yhy/yhySailBoat/getOrderDetail.jhtml',
    init: function() {
        SailboatOrderDetailCancel.initPageData();
        SailboatOrderDetailCancel.initJsp();
    },

    initJsp: function() {
        $.each($(".idNumber"), function(i, perIdNum) {
            if ($(perIdNum).html().length > 0) {
                $(perIdNum).html($(perIdNum).html().substr(0, 10) + "****" + $(perIdNum).html().substr(14, $(perIdNum).html().length));
            }
        });
    },

    initPageData: function() {
        var data = {orderId: SailboatOrderDetailCancel.orderId, orderDetailId: SailboatOrderDetailCancel.orderDetailId};

        $.ajax({
            url: SailboatOrderDetailCancel.getHotelOrderDetailUrl,
            data: data,
            progress: true,
            success: function(result) {
                if (result.success) {
                    if (result.orderDetails) {
                        SailboatOrderDetailCancel.orderDetailInfo(result.orderDetails);
                    }
                }
            },
            error: function() {}
        });
    },
    orderDetailInfo: function(orderDetailList) {
        $.each(orderDetailList, function(i, perValue) {
            //if (perValue.productType == 'sailboat' || perValue.productType == 'yacht' || perValue.productType == 'scenic' || perValue.productType == 'huanguyou') {
            SailboatOrderDetailCancel.bulidOrderDetailTable(perValue);
            //}

        })
    },

    bulidOrderDetailTable: function(orderDetail) {
        var tr = "";
        tr += '<tr class="headTr headTr_sec">';
        if (orderDetail.seatType) {
            tr += ' <td class="productMess">房型：'+ orderDetail.seatType +'</td>';
        } else {
            tr += ' <td class="productMess">房型：无</td>';
        }

        if (orderDetail.remark) {
            tr += ' <td class="reason">'+ orderDetail.remark +'</td>';
        } else {
            tr += ' <td class="reason">无</td>';
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
    SailboatOrderDetailCancel.init();
});