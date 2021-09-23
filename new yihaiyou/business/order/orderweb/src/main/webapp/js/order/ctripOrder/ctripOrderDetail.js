/**
 * Created by dy on 2016/10/19.
 */
var CliendOrderDetail = {

    init: function() {
        CliendOrderDetail.initCom();
        CliendOrderDetail.initIframeHeight();
    },

    initCom: function() {
        //$(".detail-status").append(CliendOrderDetail.getOrderDetailStatus($("#detailStatus").val()));
        //$(".pro-type").append(CliendOrderDetail.getOrderProType($("#detailProType").val()));
        //$(".detail-opt").append(CliendOrderDetail.returnBtn($("#detailStatus").val()));

    },

    initIframeHeight: function() {
        var rows = $("#ipt_detail_length").val();
        var ifr = window.parent.$("#editPanel").children()[0];
        var height = 0;
        $.each($(".detail-panel"), function(i, perdiv) {
            height += $(perdiv).height() + 40;
            console.log($(perdiv).height());
        });
        var dialogHeight = window.parent.$("#editPanel").height();
        console.log("height:" + height);
        console.log("dialogHeight:" + dialogHeight);
        if (dialogHeight > height) {
            $(ifr).css("height", (dialogHeight + "px"));
        } else {
            $(ifr).css("height", (height + "px"));
        }

    },

    getLog: function(orderId, orderDetailId) {
        $.messager.progress({
            msg: '正在读取日志,请稍候...'
        });
        $("#logDg").datagrid({
            url: '/order/orderLog/getOrderLog.jhtml',
            fit: true,
            rownumbers:false,
            pagination:true,
            nowrap: false,
            pageList:[20,30,50],
            queryParams: {
                'orderIdStr': orderId,
                'orderDetailIdStr': orderDetailId
            },
            columns: [[
                {
                    field: 'id',
                    title: 'ID',
                    width: 65,
                    align: 'center'
                },
                {
                    field: 'logContent',
                    title: '日志内容',
                    width: 300,
                    align: 'center'
                },
                {
                    field: 'operator.userName',
                    title: '操作者',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'recordTime',
                    title: '记录时间',
                    width: 140,
                    align: 'center'
                }
            ]],
            onLoadSuccess: function(data) {
                $("#log-panel").dialog({
                    title: '查看订单日志',
                    //top: 20,
                    modal: true
                });
                $.messager.progress('close');
                $("#log-panel").dialog("open");
            }
        });
    },
    // 未支付订单取消
    doCancel: function(id){
        $.messager.confirm('温馨提示', '确认执行取消操作？', function(r){
            if (r) {
                var requestData = {
                    'orderDetailId': id,
                    'orderId': $("#ipt_id").val()
                };
                $.messager.progress();
                $.post("/yhyorder/yhyOrder/doCancelOrder.jhtml", requestData,
                    function (data) {
                        $.messager.progress('close');
                        if (data.success) {
                            show_msg("取消成功");
                            CliendOrderDetail.refeshPage();
                        } else {
                            show_msg(data.errorMsg);
                        }
                    }
                );
            }
        });
    },
    // 订单退款（用户已支付，订单不是成功状态）
    doFailOrderRefund: function(id) {
        $.messager.confirm('温馨提示', '确认执行退款操作？', function(r) {
            if (r) {
                var requestData = {
                    'orderDetailId': id,
                    'orderId': $("#ipt_id").val()
                };
                $.messager.progress();
                $.post("/balance/balance/doFailOrderRefund.jhtml", requestData,
                    function (data) {
                        $.messager.progress('close');
                        if (data.flag) {
                            show_msg("退款成功");
                            CliendOrderDetail.refeshPage();
                        } else {
                            show_msg(data.reMsg);
                        }
                    }
                );
            }
        });
    },
    // 订单退款（用户已支付，订单是成功状态） - 携程门票
    doRefundOtaCtrip: function(id) {
        $.messager.confirm('温馨提示', '确认执行退款操作？', function(r) {
            if (r) {
                var requestData = {
                    'orderDetailId': id
                };
                $.messager.progress();
                $.post("/apidata/apiOrderCancel/cancelForCtrip.jhtml", requestData,
                    function (data) {
                        $.messager.progress('close');
                        if (data && data.success) {
                            show_msg("退款成功");
                            CliendOrderDetail.refeshPage();
                        } else {
                            show_msg(data.errorMsg);
                        }
                    }
                );
            }
        });
    },

    refeshPage: function() {
        location.reload(true);
    },

    getOrderDetailStatus: function (status) {

        if (status == "BOOKING") {
            return "预定中";
        } else if (status == "SUCCESS") {
            return "预定成功";
        } else if (status == "WAITING") {
            return "等待支付";
        } else if (status == "PAYED") {
            return "已支付";
        } else if (status == "CANCELED") {
            return "已取消";
        } else if (status == "CANCELING") {
            return "取消中";
        } else if (status == "PARTIAL_FAILED") {
            return "<span style='color: #f55'>部分失败</span>";
        } else if (status == "FAILED") {
            return "<span style='color: #f55'>交易失败</span>";
        } else if (status == "RETRY") {
            return "<span style='color: #ff6600'>重试</span>";
        }  else if (status == "CHECKIN") {
            return "已入住";
        } else if (status == "CHECKOUT") {
            return "已退房";
        } else if (status == "CONFIRMED") {
            return "已确认";
        } else if (status == "UNCONFIRMED") {
            return "待确认";
        } else if (status == "REFUSE") {
            return "已拒绝";
        }else {
            return "-"
        }
    },

    getOrderProType: function(type) {
        if (type == "scenic") {
            return "门票";
        } else if (type == "restaurant") {
            return "餐厅";
        } else if (type == "hotel") {
            return "酒店";
        } else if (type == "sailboat") {
            return "帆船";
        } else if (type == "yacht") {
            return "游艇";
        } else if (type == "line") {
            return "线路";
        } else if (type == "train") {
            return "火车票";
        } else if (type == "flight") {
            return "飞机票";
        } else if (type == "plan") {
            return "行程";
        }  else if (type == "delicacy") {
            return "美食";
        } else if (type == "recplan") {
            return "游记";
        } else if (type == "cruiseship") {
            return "游轮";
        } else if (type == "insurance") {
            return "保险";
        } else {
            return "其他"
        }
    }


};

$(function() {
   CliendOrderDetail.init();
});