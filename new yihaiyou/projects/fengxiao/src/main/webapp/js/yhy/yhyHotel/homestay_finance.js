/**
 * Created by dy on 2016/11/19.
 */
var HotelFinance = {
    orderSummaryTable: null,
    init: function() {
        HotelFinance.initPageComm();
        HotelFinance.initOrderSummaryListTable();
    },

    initPageComm: function() {
        $("#start_billSummaryDate").datetimepicker({
            minView: "month", //选择日期后，不会再跳转去选择时分秒
            language:  'zh-CN',
            format: 'yyyy-mm-dd'
        });
        $("#end_billSummaryDate").datetimepicker({
            minView: "month", //选择日期后，不会再跳转去选择时分秒
            language:  'zh-CN',
            format: 'yyyy-mm-dd'
        });
    },

    selectDateRange: function(type) {



        if (type == 'week') {
            var day = moment().weekday();
            var startDate = moment().subtract(day, 'days');
            $("#start_billSummaryDate").val(moment(startDate).format("YYYY-MM-DD"));
            var endDate = startDate.add(6, 'days');
            $("#end_billSummaryDate").val(moment(endDate).format("YYYY-MM-DD"));
        } else {
            var today = moment();
            var firstDate = today.startOf("month");
            var endDate = today.endOf("month");
            $("#start_billSummaryDate").val(today.startOf("month").format("YYYY-MM-DD"));
            console.log(today.startOf("month"));
            $("#end_billSummaryDate").val(today.endOf("month").format("YYYY-MM-DD"));
            console.log(today.endOf("month"));
        }
    },

    initOrderSummaryListTable: function() {

        HotelFinance.orderSummaryTable = $("#orderFinanceId").DataTable( {
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            "stateSave": true,
            "lengthMenu": [ 10, 50, 200, 600, 1000 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                if($("#billOrderSummaryId").val()) {
                    data['orderBillSummary.billNo'] = $("#billOrderSummaryId").val();
                }

                if($("#start_billSummaryDate").val()) {
                    data['orderBillSummary.billDateStartStr'] = $("#start_billSummaryDate").val();
                }

                if($("#end_billSummaryDate").val()) {
                    data['orderBillSummary.billDateEndStr'] = $("#end_billSummaryDate").val();
                }

                if($("#confirmStatus").val()) {
                    data['orderBillSummary.confirmStatus'] = $("#confirmStatus").val();
                }

                //if($("#productId").val()) {
                //    data['orderBillSummary.productId'] = $("#productId").val();
                //}

                $.ajax({
                    url: '/order/orderBill/orderSummaryList.jhtml',
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
                { "title": "对账单号", data:'billNo' },
                { "title": "出账日期", data:'billSummaryDate', render: function(data, type, row, meta) {
                    var billSummaryDate = moment(data).format("YYYY-MM-DD");
                    return '<span>'+ billSummaryDate +'</span>'
                }},
                { "title": "结算周期", data:'billType', "class": "center" , render: function(data, type, row, meta) {
                    if (data == 'T0' || data == 'T1' || data == 'TN') {
                        return 'T+' + row.billDays;
                    } else if (data == 'month') {
                        return "每月" + row.billDays + "日";
                    } else if (data == 'week') {
                        var weekday = '';
                        switch(row.billDays) {
                            case 1:
                                weekday = '日';
                                break;
                            case 2:
                                weekday = '一';
                                break;
                            case 3:
                                weekday = '二';
                                break;
                            case 4:
                                weekday = '三';
                                break;
                            case 5:
                                weekday = '四';
                                break;
                            case 6:
                                weekday = '五';
                                break;
                            case 7:
                                weekday = '六';
                                break;
                            default:;
                        }
                        return "每周" + weekday;    // 星期日为1
                    } else if (data == 'D0' || data == 'D1' || data == 'DN') {
                        return 'D+' + row.billDays;
                    } else {
                        return '';
                    }
                }},
                { "title": "订单数", data:'orderNum'},
                { "title": "当期收款", data:'totalBillPrice'},
                { "title": "当期退款", data:'refundPrice'},
                { "title": "实际收款", data:'alreadyBillPrice', render: function(data, type, row, meta) {
                    if (data == 0) {
                        return row.notBillPrice;
                    } else {
                        return data;
                    }
                }},
                { "title": "账单状态", data:'confirmStatus', render: function(data, type, row, meta) {
                    if (data == 0) {
                        return '<span>未确认</span>';
                    } else {
                        return '<span>已确认</span>';
                    }
                }},
                { "title": "操作", data:null, render: function(data, type, row, meta) {
                    return '<a onclick="HotelFinance.orderBillSummaryDetail('+ row.id +')">账单明细</a>';
                }}
            ]
        });
    },

    getHotelOrderSummaryList: function(sSource, aoData, fnCallback) {
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

        if($("#billOrderSummaryId").val()) {
            data['orderBillSummary.id'] = $("#billOrderSummaryId").val();
        }

        if($("#start_billSummaryDate").val()) {
            data['orderBillSummary.billDateStartStr'] = $("#start_billSummaryDate").val();
        }

        if($("#end_billSummaryDate").val()) {
            data['orderBillSummary.billDateEndStr'] = $("#end_billSummaryDate").val();
        }

        if($("#confirmStatus").val()) {
            data['orderBillSummary.confirmStatus'] = $("#confirmStatus").val();
        }

        if($("#productId").val()) {
            data['orderBillSummary.productId'] = $("#productId").val();
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
        HotelFinance.orderSummaryTable.ajax.reload();
    },
    orderBillSummaryDetail: function(id) {
        window.location.href = "/yhy/yhyMain/orderBillSummaryDetail.jhtml?orderBillSummary.id="+id;
    }

}

$(function() {
    HotelFinance.init();
});
