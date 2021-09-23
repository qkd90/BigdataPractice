var Order = {
    totalCount: 0,
    pageIndex: $("#page-index").val(),
    init: function () {
        Order.initData(Order.pageIndex);
        //$.getJSON("/user/home/countOrder.jhtml", {
        //}, function (result) {
        //    Order.initPage(result);
        //});
    },
    initPage: function() {
        $("#page-list").pagination(parseInt(Order.totalCount), {
            link_to : "javascript:;",
            items_per_page : 10,
            prev_text: "&laquo;上一页",
            next_text: "下一页&raquo;",
            current_page : parseInt(Order.pageIndex),
            num_display_entries : 5,
            prev_show_always: false,
            next_show_always: false,
            callback: function(pageIndex){
                Order.initData(pageIndex);
            }
        });

    },
    initData: function(pageIndex) {
        Order.pageIndex = pageIndex;
        var data = {
            'page.pageIndex': parseInt(pageIndex) + 1,
            'page.pageSize': 10
        };
        $.getJSON("/user/home/listOrder.jhtml", data, function (result) {
            var html = "";
            for (var i=0;i<result.list.length;i++) {
                var data = result.list[i];
                data.type = Order.renderType(data);
                data.orderName = Order.renderOrderName(data);
                data.statusStr = Order.renderStatus(data.status);
                data.status = data.status;
                data.count = Order.renderCount(data);
                html += $("#order-template").render(data);
            }
            $("#order-list-wrap").html(html);
            if (result.totalCount!= Order.totalCount) {
                Order.totalCount = result.totalCount;
                Order.initPage();
            }
        });
        Order.changeLink(data);

    },
    renderType: function (data) {
        var types = '';
        for (var i = 0; i < data.orderDetails.length; i++) {
            var detail = data.orderDetails[i];
            if (types.indexOf(detail.product.proType) >= 0) {
                continue;
            }
            if (types != "") {
                types += "/";
            }
            types += detail.product.proType;
        }
        types = types.replace("line", "线路").replace("scenic", "景点").replace("restaurant", "餐厅").replace("hotel", "酒店");
        return types;
    },
    renderOrderName: function (data) {
        if (data.orderDetails == null) {
            return "";
        }
        var name = "";
        for (var i = 0; i < data.orderDetails.length; i++) {
            if (name != "") {
                name += "/";
            }
            name += data.orderDetails[i].product.name;
        }
        return name;
    },
    renderStatus: function (value) {
        if (value == 'WAIT') {
            return '待支付';
        } else if (value == 'PAYED') {
            return '<span class="success">交易完成</span>';
        } else if (value == 'REFUND') {
            return '已退款';
        } else if (value == 'CLOSED') {
            return '<span class="cancel">已关闭</span>';
        } else if (value == 'UNCONFIRMED') {
            return '未确认';
        } else if (value == 'DELETED') {
            return '已删除';
        } else {
            return '无效订单';
        }
    },
    renderCount: function (data) {
        var count = 0;
        for (var i = 0;i<data.orderDetails.length;i++) {
            count+=parseInt(data.orderDetails[i].num);
        }
        return count;
    },
    changeLink : function(data){
        var json={time:new Date().getTime()};
        data['page.pageIndex']  = data['page.pageIndex']-1;
        window.history.pushState(json,"",'/user/home/order.jhtml?' + $.param(data));

    }
};

Order.init();