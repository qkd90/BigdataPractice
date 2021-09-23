/**
 * Created by dy on 2017/3/3.
 */
var PandectOrder= {
    init: function() {
        PandectOrder.getStatisticsData();
        setInterval("PandectOrder.getStatisticsData()", 30000);//1000为1秒钟
    },
    // 获取统计数据
    getStatisticsData : function() {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.ajax({
            url:"/yhyorder/yhyOrder/statisticsData.jhtml",
            type:'post',
            dataType:'json',
            data: {},
            success: function(result) {
                $.messager.progress("close");
                if (result && result.success) {
                    // 数量
                    $("#sailboatCount").html(result.sailboatCount);
                    $("#yachtCount").html(result.yachtCount);
                    $("#huanguyouCount").html(result.huanguyouCount);
                    $("#hotelCount").html(result.hotelCount);
                    $("#planCount").html(result.planCount);
                    // 帆船条目
                    var sailboatOrdersHtml = '';
                    $.each(result.sailboatOrders, function(i, item) {
                        sailboatOrdersHtml += '<div class="detail"><div class="detail_info1">'
                            + '<span class="order_no">' + item.orderNo + '</span><span class="order_date">' + BgmgrUtil.dateTimeFmt2(item.createTime, 'yyyy-MM-dd HH:mm') + '</span>'
                            + '<p class="clr_float"></p></div><div class="detail_info2">'
                            + '<p class="order_name" title="' + item.name + '">' + item.name + '</p><span class="order_status">' + item.statusStr + '</span>'
                            + '<p class="clr_float"></p></div></div>';
                    });
                    if (!sailboatOrdersHtml) { // 为空时默认显示
                        sailboatOrdersHtml = '<div class="no_detail"><p>没有最新订单数据</p></div>';
                    }
                    $('#sailboatOrders').html(sailboatOrdersHtml);
                    // 游艇条目
                    var yachtOrdersHtml = '';
                    $.each(result.yachtOrders, function(i, item) {
                        yachtOrdersHtml += '<div class="detail"><div class="detail_info1">'
                            + '<span class="order_no">' + item.orderNo + '</span><span class="order_date">' + BgmgrUtil.dateTimeFmt2(item.createTime, 'yyyy-MM-dd HH:mm') + '</span>'
                            + '<p class="clr_float"></p></div><div class="detail_info2">'
                            + '<p class="order_name" title="' + item.name + '">' + item.name + '</p><span class="order_status">' + item.statusStr + '</span>'
                            + '<p class="clr_float"></p></div></div>';
                    });
                    if (!yachtOrdersHtml) { // 为空时默认显示
                        yachtOrdersHtml = '<div class="no_detail"><p>没有最新订单数据</p></div>';
                    }
                    $('#yachtOrders').html(yachtOrdersHtml);
                    // 鹭岛游条目
                    var huanguyouOrdersHtml = '';
                    $.each(result.huanguyouOrders, function(i, item) {
                        huanguyouOrdersHtml += '<div class="detail"><div class="detail_info1">'
                            + '<span class="order_no">' + item.orderNo + '</span><span class="order_date">' + BgmgrUtil.dateTimeFmt2(item.createTime, 'yyyy-MM-dd HH:mm') + '</span>'
                            + '<p class="clr_float"></p></div><div class="detail_info2">'
                            + '<p class="order_name" title="' + item.name + '">' + item.name + '</p><span class="order_status">' + item.statusStr + '</span>'
                            + '<p class="clr_float"></p></div></div>';
                    });
                    if (!huanguyouOrdersHtml) { // 为空时默认显示
                        huanguyouOrdersHtml = '<div class="no_detail"><p>没有最新订单数据</p></div>';
                    }
                    $('#huanguyouOrders').html(huanguyouOrdersHtml);
                    // 酒店民宿条目
                    var hotelOrdersHtml = '';
                    $.each(result.hotelOrders, function(i, item) {
                        hotelOrdersHtml += '<div class="detail"><div class="detail_info1">'
                            + '<span class="order_no">' + item.orderNo + '</span><span class="order_date">' + BgmgrUtil.dateTimeFmt2(item.createTime, 'yyyy-MM-dd HH:mm') + '</span>'
                            + '<p class="clr_float"></p></div><div class="detail_info2">'
                            + '<p class="order_name" title="' + item.name + '">' + item.name + '</p><span class="order_status">' + item.statusStr + '</span>'
                            + '<p class="clr_float"></p></div></div>';
                    });
                    if (!hotelOrdersHtml) { // 为空时默认显示
                        hotelOrdersHtml = '<div class="no_detail"><p>没有最新订单数据</p></div>';
                    }
                    $('#hotelOrders').html(hotelOrdersHtml);
                    // 行程规划条目
                    var planOrdersHtml = '';
                    $.each(result.planOrders, function(i, item) {
                        planOrdersHtml += '<div class="detail"><div class="detail_info1">'
                            + '<span class="order_no">' + item.orderNo + '</span><span class="order_date">' + BgmgrUtil.dateTimeFmt2(item.createTime, 'yyyy-MM-dd HH:mm') + '</span>'
                            + '<p class="clr_float"></p></div><div class="detail_info2">'
                            + '<p class="order_name" title="' + item.name + '">' + item.name + '</p><span class="order_status">' + item.statusStr + '</span>'
                            + '<p class="clr_float"></p></div></div>';
                    });
                    if (!planOrdersHtml) { // 为空时默认显示
                        planOrdersHtml = '<div class="no_detail"><p>没有最新订单数据</p></div>';
                    }
                    $('#planOrders').html(planOrdersHtml);

                } else {
                    show_msg("数据加载失败！")
                }
            },
            error: function() {
                $.messager.progress("close");
                show_msg("数据加载失败！")
            }
        });
    },
    goSailboatOrderList: function() {
        parent.outclick("/yhyorder/yhyOrder/sailboatIndex.jhtml");
    },
    goHotelOrderList: function() {
        parent.outclick("/yhyorder/yhyOrder/hotelIndex.jhtml");
    },
    goPlanOrderList: function() {
        parent.outclick("/yhyorder/yhyOrder/planIndex.jhtml");
    }
};

$(function() {
    PandectOrder.init();
});
