/**
 * Created by dy on 2016/11/23.
 */

var HotelOrderDetailCancel = {
    orderId:$("#orderId").val(),
    detailId:$("#detailId").val(),
    init: function() {
        HotelOrderDetailCancel.initPageData();
        HotelOrderDetailCancel.initOrderStatus();
        HotelOrderDetailCancel.initJsp();
    },
    initOrderStatus: function() {
        var status = $(".orderState").attr("data-status");
        if (status == 'WAIT') {
            $(".confirm").hide();
        }
    },

    initJsp: function() {
        $.each($(".idNumber"), function(i, perIdNum) {
            if ($(perIdNum).html().length > 0) {
                $(perIdNum).html($(perIdNum).html().substr(0, 10) + "****" + $(perIdNum).html().substr(14, $(perIdNum).html().length));
            }
        });
    },

    initPageData: function() {
        var url = "/yhy/yhyHotel/getHotelOrderDetail.jhtml";
        var data = {orderId: HotelOrderDetailCancel.orderId, detailId: HotelOrderDetailCancel.detailId};
        $.ajax({
            url: url,
            data: data,
            progress: true,
            success: function(result) {
                if (result.success) {
                    if (result.orderDetails) {
                        HotelOrderDetailCancel.orderDetailInfo(result.orderDetails);
                    }
                }
            },
            error: function() {}
        });
    },
    orderDetailInfo: function(orderDetailList) {
        $.each(orderDetailList, function(i, perValue) {
            if (perValue.productType == 'hotel') {
                HotelOrderDetailCancel.bulidOrderDetailTable(perValue);
            }

        })
    },

    bulidOrderDetailTable: function(orderDetail) {

        /*<tr class="headTr headTr_sec">
        <td class="productMess">豪华海景房</td>
        <td class="reason">地方结果房价都看过韩国反对韩国进口的换个角度看回家看过很多附加快递费</td>
        <td class="finalPrice">160</td>
        <td class="sailPrice">160</td>
        <td class="operator">张小虎</td>*/

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
    HotelOrderDetailCancel.init();
});