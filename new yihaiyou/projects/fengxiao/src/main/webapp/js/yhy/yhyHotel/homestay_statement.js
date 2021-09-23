/**
 * Created by dy on 2016/11/16.
 */
$(function () {
    HotelStatement.init();
});

var HotelStatement = {
    orderBillTable: null,
    refundBillTable: null,
    orderBillListUrl:'/order/orderBill/orderBillList.jhtml',
    confirmOrderBillUrl:'/balance/balance/cfmOrderBillSummary.jhtml',
    init: function() {
        HotelStatement.initOrderBillListTable();
        //HotelStatement.getBack();
        HotelStatement.initJsp();
    },

    initJsp: function() {
        $("#totalBillPrice_id").on("click", function() {
            $('.statementList .statementTable').eq(1).hide();
            $('.statementList .statementTable').eq(0).show();
            if (!HotelStatement.orderBillTable) {
                HotelStatement.initOrderBillListTable();
            } else {
                HotelStatement.orderBillTable.ajax.reload(function(result){}, false);
            }
        });

        $("#refundPrice_id").on("click", function() {
            $('.statementList .statementTable').eq(1).show();
            $('.statementList .statementTable').eq(0).hide();
            if (!HotelStatement.refundBillTable) {
                HotelStatement.initRefundOrderBillListTable();
            } else {
                HotelStatement.refundBillTable.ajax.reload(function(result){}, false);
            }

        });

    },

    initOrderBillListTable: function() {
        HotelStatement.orderBillTable = $("#orderBillDetailDg").DataTable( {
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            "stateSave": true,
            "lengthMenu": [ 10, 50, 200, 600, 1000 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                if ($("#orderBillSummaryId").val()) {
                    data['orderBillSummary.id'] = $("#orderBillSummaryId").val();
                }
                $.ajax({
                    url: HotelStatement.orderBillListUrl,
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
                { "title": "订单号", data:'orderNo' },
                { "title": "产品名称", data:'productName', render: function(data, type, row, meta) {
                    return data;
                }},
                { "title": "订单总价", data:'totalPrice', "class": "center" , render: function(data, type, row, meta) {
                    return data;
                }},
                { "title": "当期收款", data:'orderBillPrice'}
            ]
        });
    },

    initRefundOrderBillListTable: function() {
        HotelStatement.refundBillTable = $("#refund-bill-order-detail").DataTable( {
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            "stateSave": true,
            "lengthMenu": [ 10, 50, 200, 600, 1000 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                if ($("#orderBillSummaryId").val()) {
                    data['orderBillSummary.id'] = $("#orderBillSummaryId").val();
                    data['orderBillSummary.orderDetailStatus'] = 'REFUNDED';
                }
                $.ajax({
                    url: HotelStatement.orderBillListUrl,
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
                { "title": "订单号", data:'orderNo' },
                { "title": "产品名称", data:'productName', render: function(data, type, row, meta) {
                    return data;
                }},
                { "title": "订单总价", data:'totalPrice', "class": "center" , render: function(data, type, row, meta) {
                    return data;
                }},
                { "title": "退还金额", data:'refundBillAmount'},
                { "title": "退还日期", data:'refundDate', render: function(data, type, row, meta) {
                    if (data && data.length > 0) {
                        return data.substr(0, 10);
                    }
                    return data;
                }}
            ]
        });
    },

    getBack: function(){
        var btn = $('.statementList  .statementGet .abox .aname');;
            btn.on('click',function(){
                var hasB = $(this).hasClass('bname');
                var hasC = $(this).hasClass('cname')
                $('.statementList .statementTable').hide();
                if(hasB == true){
                    $('.statementList .statementTable').eq(1).show();
                }else if(hasB == false && hasC == false){
                    $('.statementList .statementTable').eq(0).show();
                }
            })

    },
    getOrderBillList: function(sSource, aoData, fnCallback) {
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
        if ($("#orderBillSummaryId").val()) {
            data['orderBillSummary.id'] = $("#orderBillSummaryId").val();
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

    confirmOrderBill: function() {
        var data = {
            'billSummaryId':$("#orderBillSummaryId").val()
        };
        $.post(HotelStatement.confirmOrderBillUrl, data, function(result) {
            if (result.success) {
                $.messager.show({
                    msg: "账单确认成功",
                    type: "success",
                    timeout: 2000,
                    afterClosed: function() {
                        window.location.reload();
                    }
                });
            } else {
                $.messager.show({
                    msg: "账单确认失败",
                    type: "error",
                    timeout: 2000
                });
            }
        })
    },
    goback: function() {
        history.go(-1);
    }

};
