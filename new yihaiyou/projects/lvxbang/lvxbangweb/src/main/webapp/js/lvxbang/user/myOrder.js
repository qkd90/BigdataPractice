/**
 * Created by vacuity on 16/1/13.
 */


var OrderList = {
    pager: null,
    data: {},
    categoryNum: 0,
    init: function () {
        OrderList.pager = new Pager(
            {
                pageSize: 5,
                countUrl: "/lvxbang/user/myOrderCount.jhtml",
                searchData: {},
                pageRenderFn: function (pageNo, pageSize) {
                    OrderList.data.pageNo = pageNo;
                    OrderList.data.pageSize = pageSize;
                    $.post("/lvxbang/user/getMyOrders.jhtml", OrderList.data, function (result) {
                        var html = "";
                        $.each(result, function (i, data) {
                            if (data.orderType == 'line') { // 线路渲染
                                html += template("tpl-order-list-item-line", data);
                            } else if (data.orderType == "hotel") {
                                switch (data.status) {
                                    case "交易完成":
                                        data.status = "预订成功";
                                        break;
                                    case "交易失败":
                                        data.status = "预订失败";
                                        break;
                                    case "等待中":
                                        data.status = "确认中";
                                        break;
                                    case "已支付":
                                        data.status = "确认中";
                                        break;
                                    case "预订中":
                                        data.status = "确认中";
                                        break;
                                    case "等待支付":
                                        data.status = "确认中";
                                        break;
                                    default :
                                        break;
                                }
                                html += template("tpl-order-list-item", data);
                            } else {
                                html += template("tpl-order-list-item", data);
                            }
                        });
                        $("#myorder").html(html);

                        $(".w3.is_hover").hover(function() {
                            $(this).siblings(".w3_hover").show();
                        }, function() {
                            $(this).siblings(".w3_hover").hide();
                        });
                    },"json");
                }
            }
        );
        OrderList.refreshList();
    },

    refreshList: function () {
        OrderList.data = {};

        var orderNo = $("#orderNo").val();
        if (orderNo != null && orderNo != "") {
            OrderList.data["orderNo"] = orderNo;
        }
        var orderType = "";
        var type = $("#orderType").text();
        switch (type) {
            case "飞机":
                orderType = "flight";
                break;
            case "火车":
                orderType = "train";
                break;
            case "酒店":
                orderType = "hotel";
                break;
            case "门票":
                orderType = "ticket";
                break;
            case "行程":
                orderType = "plan";
                break;
            case "线路":
                orderType = "line";
                break;
            default:
                break;
        }
        if (orderType != "") {
            OrderList.data["orderType"] = orderType;
        }

        var startTime = $("#startTime").val();
        if (startTime != null && startTime != "") {
            OrderList.data["startTime"] = startTime;
        }
        var endTime = $("#endTime").val();
        if (endTime != null && endTime != "") {
            OrderList.data["endTime"] = endTime;
        }
        OrderList.data["orderCategory"] = OrderList.categoryNum;


        OrderList.pager.init(OrderList.data);
    },

    changeCategory: function (num) {
        for (var i = 0; i < 5; i++) {
            if (i == num) {
                $("#category-" + i).addClass("checked");
                OrderList.categoryNum = i;
            } else {
                $("#category-" + i).removeClass("checked");
            }
        }
        OrderList.refreshList();
    },

    updateCategoryNum: function () {
        $.post("/lvxbang/user/getCategoryCount.jhtml", OrderList.data, function (result) {
            if (result.success) {
                //
                $("#num-0").text(result.zero);
                $("#num-1").text(result.one);
                $("#num-2").text(result.two);
                $("#num-3").text(result.three);
                $("#num-4").text(result.four);
            } else {
                promptWarn("获取分类数失败");
            }
        },"json");
    },

    search: function () {
        OrderList.refreshList();
        OrderList.updateCategoryNum();
    },

    deleteByIds: function (ids) {
        //
        deleteWarn("确定删除该订单？", function () {
            $.post("/lvxbang/user/delOrders.jhtml",
                {
                    delIds: ids
                },
                function (result) {
                    if (result.success) {
                        //
                        OrderList.search();
                    } else {
                        promptWarn("删除失败");
                    }
                },"json");
        });
    },

    delSelected: function () {
        var ids = "";
        var checkList = $(".myorder .checkbox");
        for (var i = 0; i < checkList.size(); i++) {
            var check = checkList.eq(i);
            if (check.hasClass("checked")) {
                ids += check.parent(".myorder").children(".orderId").val() + ",";
            }
        }
        if (ids == "") {
            promptWarn("请至少选择一项进行删除");
        }
        OrderList.deleteByIds(ids);
    },

    initPanel: function () {
        $(".personal").removeClass("checked");
        $("#order-panel").addClass("checked");
    },

    addClick: function () {
        //条件下拉选和日历框事件补充
        $('.ico_calendar').click(function(){
            $(this).next().focus();
        });
        $('#orderType').click(function(){
            $('.ico_type').click();
        });
        $('.ico_type').click(function(){
            var status = $(this).next().css("display");
            if(status == "none"){
                $(this).next().css("display", "block");
                $(this).css('background-position','-42px -92px');
            }else{
                $(this).next().css("display", "none");
                $(this).css('background-position','-20px -92px');
            }
            // 阻止冒泡
            if (event.stopPropagation) {    // standard
                event.stopPropagation();
            } else {
                // IE6-8
                event.cancelBubble = true;
            }
        });
    }
}


$(function () {
    OrderList.initPanel();
    OrderList.init();
    OrderList.updateCategoryNum();
    OrderList.addClick();
});
