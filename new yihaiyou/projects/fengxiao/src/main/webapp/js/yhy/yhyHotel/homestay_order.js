$(function() {
    HotelOrder.init();
});
var HotelOrder = {
    orderTable: null,
    init: function() {
        HotelOrder.initPage();
        HotelOrder.initOrderListTable();
        //console.log($("#productId").val());
    },
    initPage: function() {
        $("#createTime_start").datetimepicker({
            minView: "month", //选择日期后，不会再跳转去选择时分秒
            language:  'zh-CN',
            format: 'yyyy-mm-dd'
        });
        $("#createTime_end").datetimepicker({
            minView: "month", //选择日期后，不会再跳转去选择时分秒
            language:  'zh-CN',
            format: 'yyyy-mm-dd'
        });
        $("#playDate").datetimepicker({
            minView: "month", //选择日期后，不会再跳转去选择时分秒
            language:  'zh-CN',
            format: 'yyyy-mm-dd'
        });
        $("#leaveDate").datetimepicker({
            minView: "month", //选择日期后，不会再跳转去选择时分秒
            language:  'zh-CN',
            format: 'yyyy-mm-dd'
        });
    },
    initOrderListTable: function() {

        HotelOrder.orderTable = $("#hotelOrderListId").DataTable( {


            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            "stateSave": true,
            "lengthMenu": [ 10, 50, 200, 600, 1000 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                if ($("#keyword").val()) {
                    data['orderDataFormVo.order.searchKeyword'] = $("#keyword").val();
                }

                if ($("#createTime_start").val()) {
                    data['orderDataFormVo.startTime'] = $("#createTime_start").val();
                }
                if ($("#createTime_end").val()) {
                    data['orderDataFormVo.qryEndTime'] = $("#createTime_end").val();
                }

                if ($("#playDate").val()) {
                    data['orderDataFormVo.playDateStartTime'] = $("#playDate").val();
                }
                if ($("#leaveDate").val()) {
                    data['orderDataFormVo.playDateEndTime'] = $("#leaveDate").val();
                }


                if ($("#orderStatus").val() && $("#orderStatus").val().length > 0) {
                    data['orderDataFormVo.orderDetail.status'] = $("#orderStatus").val();
                }
                if ($("#productId").val() && $("#productId").val().length > 0) {
                    data['orderDataFormVo.orderDetail.product.id'] = $("#productId").val();
                }

                $.ajax({
                    url: '/yhy/yhyHotel/getHotelOrderList.jhtml?orderDataFormVo.orderDetail.productType=hotel',
                    data: data,
                    type: 'post',
                    dataType: 'json',
                    progress: true,
                    success: function(result) {
                        callback(result);
                    },
                    error: function() {
                        $.messager.show({
                            msg: "数据加载错误, 请稍候重试!",
                            type: "error"
                        });
                    }
                });
            },
            columns: [
                { "data": "order.orderNo" , defaultContent:"-", title:"订单号"},
                //{ "data": "order.recName" , defaultContent:"-", title:"预订人"},
                { "data": "order.mobile" , defaultContent:"-", title:"联系电话"},
                { "data": "order.createTime", defaultContent:"-", title:"下单时间"},
                { "data": "playDate" , title:"入住日期", defaultContent:"-", render: function(data, type, row, meta) {
                    var playDate = moment(data).format("YYYY-MM-DD");
                    return '<span class="orderDate">'+ playDate +'</span>';
                }},
                { "data": "leaveDate", title:"离店日期", defaultContent:"-", render: function(data, type, row, meta) {
                    var leaveDate = moment(data).format("YYYY-MM-DD");
                    return '<span class="leaveDate">'+ leaveDate +'</span>';
                }},
                { "data":  "seatType", title:"房型", defaultContent:"-" },
                { "data":  "finalPrice", title:"金额", defaultContent:"-" },
                { "data": "status", title:"订单状态", defaultContent:"-",  "render": function(data, type, row, meta) {
                    if (data == 'WAITING' ) {
                        return '<span class="orderState">待支付</span>';
                    }
                    if (data == 'PAYED' ) {
                        return '<span class="orderState">已支付</span>';
                    }
                    if (data == 'SUCCESS' ) {
                        return '<span class="orderState">预订成功</span>';
                    }
                    if (data == 'CANCELED' ) {
                        return '<span class="orderState">已取消</span>';
                    }
                    if (data == 'FAILED' ) {
                        return '<span class="orderState">预订失败</span>';
                    }
                    if (data == 'CHECKIN' ) {
                        return '<span class="orderState">已入住</span>';
                    }
                    if (data == 'CHECKOUT' ) {
                        return '<span class="orderState">已退房</span>';
                    }
                    if (data == 'UNCONFIRMED' ) {
                        return '<span class="orderState">待确认</span>';
                    }
                    if (data == 'REFUNDED' ) {
                        return '<span class="orderState">已退款</span>';
                    }
                    if (data == 'INVALID' ) {
                        return '<span class="orderState">无效订单</span>';
                    }
                }},
                { "data": null, title:"操作人", defaultContent:"-", "render": function(data, type, row, meta) {
                    if (row.operator != null && row.operator.userName != null && row.operator.userName.length > 0) {
                        return '<span class="orderState">'+ row.operator.userName +'</span>';
                    } else {
                        return  '---';
                    }
                }},
                { "data": null, title:"操作", "render": function(data, type, row, meta) {
                    return '<a href="#" onclick="HotelOrder.openOrderDetail('+ row.id +',' + row.order.id + ')">详情</a>';
                } }
            ]
        });
    },

    getHotelOrderList: function(sSource, aoData, fnCallback) {

        var data = {};
        $.each(aoData, function(i, perData) {
            if (perData['name'] == 'sEcho') {
                data['sEcho'] = perData['value'];
            }
            if (perData['name'] == 'iDisplayStart') {
                data['iDisplayStart'] = perData['value'];
            }
            if (perData['name'] == 'iDisplayLength') {
                data['iDisplayLength'] = perData['value'];
            }
        });

        if ($("#keyword").val()) {
            data['orderDataFormVo.order.searchKeyword'] = $("#keyword").val();
        }

        if ($("#createTime_start").val()) {
            data['orderDataFormVo.startTime'] = $("#createTime_start").val();
        }
        if ($("#createTime_end").val()) {
            data['orderDataFormVo.qryEndTime'] = $("#createTime_end").val();
        }

        if ($("#playDate").val()) {
            data['orderDataFormVo.playDateStartTime'] = $("#playDate").val();
        }
        if ($("#leaveDate").val()) {
            data['orderDataFormVo.playDateEndTime'] = $("#leaveDate").val();
        }


        if ($("#orderStatus").val() && $("#orderStatus").val().length > 0) {
            data['orderDataFormVo.orderDetail.status'] = $("#orderStatus").val();
        }
        if ($("#productId").val() && $("#productId").val().length > 0) {
            data['orderDataFormVo.orderDetail.product.id'] = $("#productId").val();
        }

        $.ajax({
            url : sSource, //这个就是请求地址对应sAjaxSource
            data : data, //这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
            type : 'post',
            dataType : 'json',
            async : false,
            success : function(result) {
                fnCallback(result); //把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
            },
            error : function(msg) {
            }
        });
    },

    search: function() {
        HotelOrder.orderTable.ajax.reload(function(result){}, false);
    },
    openOrderDetail: function(detailId, id) {
        window.location.href = "/yhy/yhyMain/toHomeOrderDetail.jhtml?orderId="+id + "&detailId=" + detailId;
    }
};