var BuyNowOrder = {
    touristTemplate: 'touristTemplate',
    touristListPanel: $("#tourist-list-panel"),
    datePrice: {},
    init: function () {
        $(".order-count-panel").each(function () {
            var panel = $(this);
            var counter = panel.find(".num");
            panel.find(".minus").click(function () {
                if (parseInt(counter.val()) > 0) {
                    counter.val(parseInt(counter.val()) - 1);
                }
                BuyNowOrder.fixTourist();
                BuyNowOrder.calculateDetail();
            });
            panel.find(".plus").click(function () {
                counter.val(parseInt(counter.val()) + 1);
                BuyNowOrder.fixTourist();
                BuyNowOrder.calculateDetail();
            });
            panel.find(".num").keyup(function () {
                BuyNowOrder.fixTourist();
                BuyNowOrder.calculateDetail();
            });

        });

        $(".form-submit").click(function () {
            BuyNowOrder.submit();
        });

        $(".main-wrap").scroll(function () {
            console.log("1");
        });

        $("#qd").find(".total").click(function () {
            $(".buylist").slideToggle(400);
            $(this).toggleClass("inverse");
        });

        BuyNowOrder.fixTourist();
        BuyNowOrder.calculateDetail();
    },
    calculateDetail: function () {
        var totalPrice = 0;
        $(".order-panel").each(function () {
            var panel = $(this);
            var priceId = panel.find(".price-id").val();
            var detailPanel = $("#price-detail-" + priceId);
            detailPanel.find(".adult-count").text(panel.find(".adult-num").val());
            detailPanel.find(".adult-total-price").text(detailPanel.find(".adult-price").text() * detailPanel.find(".adult-count").text());
            if (panel.find(".child-num").length > 0) {
                detailPanel.find(".child-count").text(panel.find(".child-num").val());
                detailPanel.find(".child-total-price").text(detailPanel.find(".child-price").text() * detailPanel.find(".child-count").text());
                totalPrice = totalPrice + parseFloat(detailPanel.find(".adult-total-price").text()) + parseFloat(detailPanel.find(".child-total-price").text());
            } else {
                totalPrice = totalPrice + parseFloat(detailPanel.find(".adult-total-price").text());
            }
        });
        $("#total-price").text(totalPrice);
        //$("#final-price").text(totalPrice);
    },
    fixTourist: function () {
        var totalCount = 0;
        $(".price-list-panel .num").each(function () {
            totalCount += parseInt($(this).val());
        });
        var touristList = BuyNowOrder.touristListPanel.find(".price-list");
        if (touristList.length == totalCount) {
            return;
        }
        if (touristList.length < totalCount) {
            var count = totalCount - touristList.length;
            for (var i = 0; i < count; i++) {
                BuyNowOrder.touristListPanel.append($('#' + BuyNowOrder.touristTemplate).html());
            }
            return;
        }
        var count = touristList.length - totalCount;
        for (var i = 0; i < count; i++) {
            if (touristList.length > 1) {
                touristList.eq(touristList.length - 1 - i).remove();
            }
        }
    },
    submit: function () {
        var data = {};

        data["playDate"] = $("#start-date").text();

        //订单信息
        data['order.remark'] = $("#contactRemark").val();
        data['order.recName'] = $("#contactName").val();
        data['order.mobile'] = $("#contactMobile").val();

        //订单星系信息
        var productId = $("#productId").val();
        var productType = $("#productType").val();
        var orderDetailIndex = 0;
        $(".order-count-panel").each(function () {
            var panel = $(this);
            data['orderDetails[' + orderDetailIndex + '].product.id'] = productId;
            data['orderDetails[' + orderDetailIndex + '].product.proType'] = productType;
            data['orderDetails[' + orderDetailIndex + '].unitPrice'] = parseFloat(panel.find(".price-amount").text());
            data['orderDetails[' + orderDetailIndex + '].num'] = panel.find(".num").val();
            data['orderDetails[' + orderDetailIndex + '].costName'] = panel.parents(".order-panel").children(".price-name").text();
            data['orderDetails[' + orderDetailIndex + '].costId'] = panel.parents(".order-panel").find(".price-date-id").val();
            data['orderDetails[' + orderDetailIndex + '].costType'] = panel.parents(".order-panel").find(".price-cost-type").val();
            data['orderDetails[' + orderDetailIndex + '].priceType'] = panel.find(".price-type").val();
            orderDetailIndex++;
        });

        //游客信息
        var touristIndex = 0;
        $("#tourist-list-panel").find(".price-list").each(function () {
            var panel = $(this);
            data['tourists[' + touristIndex + '].name'] = panel.find(".tourist-name").val();
            data['tourists[' + touristIndex + '].tel'] = panel.find(".tourist-tel").val();
            data['tourists[' + touristIndex + '].idType'] = panel.find(".tourist-idType").val();
            data['tourists[' + touristIndex + '].idNumber'] = panel.find(".tourist-idNumber").val();
            data['tourists[' + touristIndex + '].remark'] = panel.find(".tourist-remark").val();
            touristIndex++;
        });

        //联系人信息
        data['orderContact.name'] = $("#contactName").val();
        data['orderContact.mobile'] = $("#contactMobile").val();
        data['orderContact.tel'] = $("#contactTel").val();
        data['orderContact.fax'] = $("#contactFax").val();
        data['orderContact.remark'] = $("#contactRemark").val();


        console.log(data);
        $.post("/shopcart/order/create.jhtml", data, function (result) {
            console.log(result);
            if (result.success) {
                window.location = "/pay/payMobile/confirmOrder.jhtml?orderId=" + result.id;
            } else {
                alert("下单失败");
            }
        });
    }
};

BuyNowOrder.init();