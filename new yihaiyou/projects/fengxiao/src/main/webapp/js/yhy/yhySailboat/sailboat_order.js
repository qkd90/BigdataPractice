/**
 * Created by dy on 2016/11/16.
 */
$(function () {
    SailboatOrder.init();
});

var SailboatOrder = {
    orderListTable: null,
    init: function() {
        SailboatOrder.initPage();
        SailboatOrder.initOrderList();
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
        $('#orderStatus').btComboBox();
    },

    initOrderList: function() {
        SailboatOrder.orderListTable = $("#orderList").DataTable( {


            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            "stateSave": true,
            "lengthMenu": [ 10, 50, 200, 600, 1000 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                data['orderDataFormVo.order.includeOrderTypes[0]'] = 'sailboat';
                data['orderDataFormVo.order.includeOrderTypes[1]'] = 'yacht';
                data['orderDataFormVo.order.includeOrderTypes[2]'] = 'huanguyou';

                if ($("#keyword").val()) {
                    data['orderDataFormVo.order.searchKeyword'] = $("#keyword").val();
                    //data['orderDataFormVo.orderDetail.seatType'] = $("#keyword").val();
                }

                if ($("#createTime_start").val()) {
                    data['orderDataFormVo.startTime'] = $("#createTime_start").val();
                }
                if ($("#createTime_end").val()) {
                    data['orderDataFormVo.qryEndTime'] = $("#createTime_end").val();
                }

                if ($("#orderStatus").btComboBox('value') && $("#orderStatus").btComboBox('value').length > 0) {
                    data['orderDataFormVo.orderDetail.status'] = $("#orderStatus").btComboBox('value');
                }

                if ($("#productId").val() && $("#productId").val().length > 0) {
                    data['orderDataFormVo.orderDetail.product.id'] = $("#productId").val();
                }

                $.ajax({
                    url: '/yhy/yhySailBoat/orderList.jhtml',
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
            "columns": [
                { "title": "订单号", data:'order.orderNo' },
                { "title": "票型", data:'seatType'},
                { "title": "张数", data:'num'},
                { "title": "取票人", data:'order.recName'},
                { "title": "取票人手机号", data:'order.mobile'},
                { "title": "下单时间", data:'order.createTime',
                    render: function(data, type, row, meta) {
                        return data;
                    }
                },
                { "title": "单价", data:'unitPrice'},
                { "title": "订单总价", data:'totalPrice'},
                { "title": "状态", data:'status',
                    render: function(data, type, row, meta) {
                        if (data == 'WAITING' ) {
                            return '<span class="operator">待支付</span>';
                        } else
                        if (data == 'PAYED' ) {
                            return '<span class="operator">已支付</span>';
                        } else
                        if (data == 'SUCCESS' ) {
                            return '<span class="operator">预订成功</span>';
                        } else if (data == 'CANCELED' ) {
                            return '<span class="operator">已取消</span>';
                        } else
                        if (data == 'FAILED' ) {
                            return '<span class="operator">预订失败</span>';
                        } else
                        if (data == 'UNCONFIRMED' ) {
                            return '<span class="operator">待确认</span>';
                        } else
                        if (data == 'REFUNDED' ) {
                            return '<span class="operator">已退款</span>';
                        }  else
                        if (data == 'INVALID' ) {
                            return '<span class="operator">无效订单</span>';
                        } else {
                            return "---";
                        }
                    }
                },
                { "title": "操作", data:null, render: function(data, type, row, meta) {
                    return '<a href="#" onclick="SailboatOrder.openOrderDetail('+ row.id +',' + row.order.id + ')">详情</a>';
                }},
            ]
        });
    },

    getOrderList: function(sSource, aoData, fnCallback) {

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


        data['orderDataFormVo.order.includeOrderTypes[0]'] = 'sailboat';
        data['orderDataFormVo.order.includeOrderTypes[1]'] = 'yacht';


        if ($("#keyword").val()) {
            data['orderDataFormVo.order.searchKeyword'] = $("#keyword").val();
            //data['orderDataFormVo.orderDetail.seatType'] = $("#keyword").val();
        }

        if ($("#createTime_start").val()) {
            data['orderDataFormVo.startTime'] = $("#createTime_start").val();
        }
        if ($("#createTime_end").val()) {
            data['orderDataFormVo.qryEndTime'] = $("#createTime_end").val();
        }

        if ($("#orderStatus").btComboBox('value') && $("#orderStatus").btComboBox('value').length > 0) {
            data['orderDataFormVo.orderDetail.status'] = $("#orderStatus").btComboBox('value');
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
        SailboatOrder.orderListTable.ajax.reload();
    },

    openOrderDetail: function(detailId, id) {
        window.location.href = "/yhy/yhyMain/toSailboatOrderDetail.jhtml?orderId="+id + "&orderDetailId=" + detailId;
    }
};
