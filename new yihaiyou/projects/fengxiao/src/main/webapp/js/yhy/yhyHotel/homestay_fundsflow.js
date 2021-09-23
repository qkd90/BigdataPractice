/**
 * Created by dy on 2016/11/19.
 */
var HotelFundsFlow = {
    fundFlowTable: null,
    init: function() {
        HotelFundsFlow.initPageData();
        HotelFundsFlow.initPageComm();
        HotelFundsFlow.initFundFlowListTable();
    },

    initPageComm: function() {
        $("#orderTime_1").datetimepicker({
            minView: "month", //选择日期后，不会再跳转去选择时分秒
            language:  'zh-CN',
            format: 'yyyy-mm-dd'
        });
        $("#orderTime_2").datetimepicker({
            minView: "month", //选择日期后，不会再跳转去选择时分秒
            language:  'zh-CN',
            format: 'yyyy-mm-dd'
        });

        $("#tixian_form").bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                money: {
                    validators: {
                        notEmpty: {
                            message: '金额不能为空'
                        },
                        numeric: {
                            message: '只能输入数值'
                        },
                        callback: {
                            message: '金额小于1且不能大于' + $("#hid_totalBalance").val(),
                            //message: '请输入合适的金额'+ $("#hid_totalBalance").val(),
                            callback: function(value, validator) {
                                if ($("#hid_totalBalance").val() == 0) {
                                    return false;
                                }
                                if (value < 1) {
                                    return false;
                                }
                                if (value > Number($("#hid_totalBalance").val())) {
                                   $("small[data-bv-validator='callback']").html('金额小于1且不能大于' + $("#hid_totalBalance").val());
                                    return false;
                                }
                                return true;
                            }
                        }
                    }
                }
            },
            submitHandler: function (validator, form, submitButton) {
                HotelFundsFlow.doWithdraw();
            }
        });
    },

    doWithdraw: function() {
        $.post("/balance/balance/applyBankWithdraw.jhtml",
            {amount: $("#money").val()},
            function(data){
                if (data && data.success) {
                    $.messager.show({
                        msg:'申请提现提交成功！',
                        type: "success",
                        timeout:2000,
                        afterClosed: function() {
                            $("#money").val("");
                            $('.shadow').hide();
                            $('.countbox').hide();
                            HotelFundsFlow.initPageData();
                            HotelFundsFlow.search();
                        }
                    });
                } else {
                    if (data && data.errorMsg) {
                        $.messager.show({
                            type: "error",
                            msg: data.errorMsg,
                            timeout:2000
                        });
                    } else {
                        $.messager.show({
                            type: "error",
                            msg: "操作失败",
                            timeout:2000
                        });
                    }
                }
                $("#tixian_form").bootstrapValidator('resetForm', true);
            },
            'json'
        );
    },

    initPageData: function() {
        var url = '/balance/balance/getFundsFlowInof.jhtml';
        $.post(
            url,
            function(result) {
                if (result.success) {
                    $.loadData({
                        scopeId: '.permess',
                        data: result
                    });
                }
            }
        );
    },

    initFundFlowListTable: function() {

        HotelFundsFlow.fundFlowTable = $(".table-striped").DataTable( {
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            "stateSave": true,
            "lengthMenu": [ 10, 50, 200, 600, 1000 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                if($("#orderTime_1").val()) {
                    data['dateStart'] = $("#orderTime_1").val();
                }
                if($("#orderTime_2").val()) {
                    data['dateEnd'] = $("#orderTime_2").val();
                }

                if($("#sel_status").val()) {
                    data['accountLog.status'] = $("#sel_status").val();
                }
                if($("#sel_type").val()) {
                    data['accountLog.type'] = $("#sel_type").val();
                }

                $.ajax({
                    url: '/balance/balance/yhyBalanceMgrList.jhtml',
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
                { "title": "时间", data:'createTime', render: function(data, type, row, meta) {
                    /*var billSummaryDate = moment(data).format("YYYY-MM-DD");
                     return '<span>'+ billSummaryDate +'</span>'*/
                    return '<span>'+ data +'</span>'
                } },
                { "title": "金额", data:'money'},
                { "title": "类型", data:'type', "class": "center" , render: function(data, type, row, meta) {
                    //consume("消费"), recharge("充值"), refund("退款"), in("入账"), out("出账"), outlinerc("充值"), outlinewd("提现"), withdraw("提现"), running("流水");

                    if (data == "running") {
                        return "流水";
                    }

                    if (data == "out") {
                        return "出账";
                    }
                    if (data == "outlinerc") {
                        return "充值";
                    }
                    if (data == "outlinewd") {
                        return "提现";
                    }
                    if (data == "withdraw") {
                        return "提现";
                    }

                    if (data == "consume") {
                        return "消费";
                    }
                    if (data == "recharge") {
                        return "充值";
                    }
                    if (data == "refund") {
                        return "退款";
                    }
                    if (data == "in") {
                        return "入账";
                    }
                }},
                { "title": "余额", data:'balance'},
                { "title": "进度", data:'status', render: function(data, type, row, meta) {
                    //submit("提交"), reject("拒绝"), normal("正常"), processing("处理中"), fail("失败");
                    if (data == "submit") {
                        return "提交";
                    }
                    if (data == "reject") {
                        return "拒绝";
                    }
                    if (data == "normal") {
                        return "正常";
                    }
                    if (data == "processing") {
                        return "处理中";
                    }
                    if (data == "fail") {
                        return "失败";
                    }
                }}
            ]
        });
    },

    getFundFlowList: function(sSource, aoData, fnCallback) {
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

        if($("#orderTime_1").val()) {
            data['dateStart'] = $("#orderTime_1").val();
        }
        if($("#orderTime_2").val()) {
            data['dateEnd'] = $("#orderTime_2").val();
        }

        if($("#sel_status").val()) {
            data['accountLog.status'] = $("#sel_status").val();
        }
        if($("#sel_type").val()) {
            data['accountLog.type'] = $("#sel_type").val();
        }
        /*
        if($("#confirmStatus").val()) {
            data['orderBillSummary.confirmStatus'] = $("#confirmStatus").val();
        }

        if($("#productId").val()) {
            data['orderBillSummary.productId'] = $("#productId").val();
        }*/

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
        HotelFundsFlow.fundFlowTable.ajax.reload();
    },
    orderBillSummaryDetail: function(id) {
        window.location.href = "/yhy/yhyMain/orderBillSummaryDetail.jhtml?orderBillSummary.id="+id;
    }

}

$(function() {
    HotelFundsFlow.init();
});
