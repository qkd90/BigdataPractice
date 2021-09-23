var LineOrder = {
    weekString: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
    init: function () {
        var orderDetailId = $("#orderDetailId").val();
        if (orderDetailId != null && orderDetailId != "") {
            var returnOrderDetailId = $("#returnOrderDetailId").val();
            LineOrder.initEdit(orderDetailId, returnOrderDetailId);
        }
        var p = $("#resultDate p.s_ccon");
        var date = new Date(p.data("date"));
        var text = date.format("MM-dd") + "(" + LineOrder.weekString[date.getDay()] + ") " + p.data("adult-price") + "元/人  ";
        if (Number(p.data("child-price")) > 0) {
            text += p.data("child-price") + "元/儿童";
        }
        if (Number(p.data("single-price")) > 0) {
            text += "  (单房差" + Number(p.data("single-price")) + "元/人)";
        }
        p.text(text);
        var day = parseInt($("#lineDay").val());
        date.setDate(date.getDate() + day);
        $("#order_box .J_returnDate span").text(date.format("yyyy-MM-dd"));
        $("#single-price .amount").data("price", p.data("single-price")).text("￥" + p.data("single-price"));
        LineOrder.selectDate();
        LineOrder.bindEvent();
        LineOrder.changePrice(0);
        LineOrder.changeChildPrice(0);
        LineOrder.fillTourist();
        LineOrder.initCity();
    },
    changePrice: function (num) {
        num = Number(num);
        var passengerNumE = $("#passengerNum");
        var passengerNum = Number(passengerNumE.val());
        passengerNum += num;
        if (passengerNum < 1) {
            passengerNum = 1;
            return;
        }
        passengerNumE.val(passengerNum);

        //旅客列表
        var parent = $("#touristList1");
        var touristList = parent.find(".tourist_info");
        var length = touristList.length;
        if (length < passengerNum) {
            for (var i = length; i < passengerNum; i++) {
                var data = {
                    index: i + 1
                };
                var html = template("tpl-tourist-list-item1", data);
                parent.append(html);
            }
        } else if (length > passengerNum) {
            for (var i = passengerNum; i < length; i++) {
                $("#contacts .checked").each(function () {
                    if ($(this).find(".id").val() == touristList.eq(i).find(".touristId").val()) {
                        $(this).removeClass("checked");
                        return false;
                    }
                });
                touristList.eq(i).remove();
            }
        }

        //右侧栏
        var price = Number($("#resultDate p.s_ccon").data("adult-price"));
        var amount = passengerNum * price;
        var rightPanel = $("#right-adult");
        rightPanel.find(".number").text(passengerNum);
        rightPanel.find(".price").text("￥" + price);
        rightPanel.find(".amount").text("￥" + amount);

        //单房差
        var sigleCount = $("#single-price");
        var dataPrice = $("#resultDate p.s_ccon").data("single-price");
        if (passengerNum % 2 == 0 || isNaN(dataPrice) || Number(dataPrice) <= 0) {
            sigleCount.addClass("hide");
        } else {
            sigleCount.removeClass("hide");
        }

        LineOrder.changeInsurance();
    },

    changeChildPrice: function (num) {
        var dataPrice = $("#resultDate p.s_ccon").data("child-price");
        //if (isNaN(dataPrice) || Number(dataPrice) <= 0) {
        //    return;
        //}
        num = Number(num);
        var passengerNumE = $("#passengerChildNum");
        var passengerNum = Number(passengerNumE.val());
        passengerNum += num;
        if (passengerNum < 0 || isNaN(dataPrice) || Number(dataPrice) <= 0) {
            passengerNum = 0;
        }
        passengerNumE.val(passengerNum);

        //游客列表
        var parent = $("#touristList2");
        var touristList = parent.find(".tourist_info");
        var length = touristList.length;
        if (length < passengerNum) {
            for (var i = length; i < passengerNum; i++) {
                var data = {
                    index: i + 1
                };
                var html = template("tpl-tourist-list-item2", data);
                parent.append(html);
            }
        } else if (length > passengerNum) {
            for (var i = passengerNum; i < length; i++) {
                $("#contacts .checked").each(function () {
                    if ($(this).find(".id").val() == touristList.eq(i).find(".touristId").val()) {
                        $(this).removeClass("checked");
                        return false;
                    }
                });
                touristList.eq(i).remove();
            }
        }

        //右侧栏
        var price = Number($("#resultDate p.s_ccon").data("child-price"));
        var amount = passengerNum * price;
        var rightPanel = $("#right-child");
        if (passengerNum < 1) {
            rightPanel.addClass("hide");
        } else {
            rightPanel.removeClass("hide");
            rightPanel.find(".number").text(passengerNum);
            rightPanel.find(".price").text("￥" + price);
            rightPanel.find(".amount").text("￥" + amount);
        }

        LineOrder.changeInsurance();
    },

    fillTourist: function () {
        $("#contacts").on("click", 'span', function () {
            var parent = $("#touristList1");
            var selectIdNum = $(this).find(".idNumber").val();
            var selectTime = Number(selectIdNum.substr(6, 8));
            var nowTime = Number(new Date().format("yyyyMMdd"));
            if (nowTime - selectTime < 12 * 10000) {
                parent = $("#touristList2");
            }
            var touristList = parent.find(".tourist_info");
            var size = touristList.size();

            if ($(this).hasClass("checked")) {
                $(this).removeClass("checked");
                var selectId = $(this).find(".id").val();
                for (var i = 0; i < size; i++) {
                    var inputTourist = touristList.eq(i);
                    var id = inputTourist.find(".touristId").val();
                    if (id != null) {

                        if (id == selectId) {
                            inputTourist.find(".touristId").val("");
                            inputTourist.find(".touristName").val("");
                            inputTourist.find(".touristPeopleType").text("成人");
                            //inputTourist.find(".touristIdType").text("身份证");
                            inputTourist.find(".touristIdNum").val("");
                            inputTourist.find(".touristTel").val("");
                            break;
                        }
                    }
                }
            } else {

                var selectId = $(this).find(".id").val();
                var selectName = $(this).find(".name").val();
                var selectPeopleType = $(this).find(".peopleType").val();
                var selectIdType = $(this).find(".idType").val();
                var selectIdNum = $(this).find(".idNumber").val();
                var selectTel = $(this).find(".tel").val();

                var flag = false;
                for (var i = 0; i < size; i++) {
                    var inputTourist = touristList.eq(i);
                    var id = inputTourist.find(".touristId").val();
                    var name = inputTourist.find(".touristName").val();
                    if (isNull(id) && isNull(name)) {
                        //
                        inputTourist.find(".touristId").val(selectId);
                        inputTourist.find(".touristName").val(selectName);
                        if (selectPeopleType == "KID") {
                            inputTourist.find(".touristPeopleType").text("儿童");
                        }
                        if (selectIdType == "PASSPORT") {
                            inputTourist.find(".touristIdType").text("护照");
                        }
                        inputTourist.find(".touristIdNum").val(selectIdNum);
                        inputTourist.find(".touristTel").val(selectTel);
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    $(this).addClass("checked");
                }
            }
        })
    },

    changeInsurance: function () {
        $("#insurance-price").empty();
        var num = Number($("#passengerNum").val()) + Number($("#passengerChildNum").val());
        $("#insurance_box .input_checkbox.enabled").each(function (i) {
            var insurance = {};
            insurance.id = $(this).data("id");
            insurance.name = $(this).data("name");
            insurance.price = $(this).data("price");
            insurance.num = num;
            insurance.index = i;
            var html = template("tpl-insurance-price-item", insurance);
            $("#insurance-price").append(html);
        });
        LineOrder.changeCost();
    },

    changeCost: function () {
        var num = Number($("#passengerNum").val());
        var price = Number($("#resultDate .s_ccon").data("adult-price"));
        var cost1 = num * price;
        var cost2 = 0;
        if ($("#passengerChildNum").length > 0) {
            var childNum = Number($("#passengerChildNum").val());
            var childPrice = Number($("#resultDate .s_ccon").data("child-price"));
            cost2 = childNum * childPrice;
        }
        var singleCost = 0;
        if ($("#single-price").is(":visible")) {
            singleCost = Number($("#resultDate .s_ccon").data("single-price"));
        }
        var cost3 = parseInt((cost1 + cost2 + singleCost) * 100) / 100;

        var insuranceCost = 0;

        $("#insurance-price .insurance").each(function () {
            insuranceCost += Number($(this).data("price"));
        });
        insuranceCost = parseInt(insuranceCost * 100) / 100;
        var cost = parseInt((cost3 + insuranceCost) * 100) / 100;

        //if($('.redPacketLi .checked').length>0){
        //    //cost = cost.toFixed(1);
        //    var li = $('.redPacketLi .checked').parents('li');
        //    var useCondition = Number(li.attr('useCondition'));
        //    if(cost >= useCondition){
        //        cost = cost - Number(li.attr("faceValue"));
        //    }else{
        //        $('.redPacketLi .checked').removeClass('checked');
        //    }
        //}
        $("#right-cost .number").text("￥" + cost);
        $("#sum-cost .number").text(cost);
        $("#receipt_box .J_amount .rmb").text(cost3);
        if (insuranceCost > 0) {
            $(".J_insurance_price").text(insuranceCost);
            $(".J_insurance").show();
        } else {
            $(".J_insurance").hide();
        }
    },

    changeDate: function () {
        var linetypepriceId = $("#linetypepriceId").val();
        var date = $("#startDate").val();


        //
        $.post("/lvxbang/order/getLineDatePriceList.jhtml",
            {
                ticketDate: $("#startDate").val(),
                linetypepriceId: linetypepriceId
            }, function (result) {
                if (result.success == true) {
                    $("#singlePrice").text(result.price);
                    $("#price").val(result.price);
                    LineOrder.changeCost();

                    $('.shiyong').click().removeClass("shiyong");
                } else {
                    //$("#price").val(0);
                    //promptWarn("查询价格失败");
                }
            }
            , "json");
    },

    checkLine: function () {
        if ($("#guarantee_box").length > 0) {
            var cardNum = $.trim($('#cardNum').val());
            var cvv = $.trim($('#cvv').val());
            var holderName = $.trim($('#holderName').val());
            var idNo = $.trim($('#idNo').val());
            var expirationYear = $.trim($('#expirationYear').html().replace(/[^0-9]/ig, ""));
            var expirationMonth = $.trim($('#expirationMonth').html().replace(/[^0-9]/ig, ""));


            if (isNull(cardNum) || isNull(cvv) || isNull(holderName) || isNull(idNo)) {
                return "酒店担保信息未填完整";
            }
            var reg1 = /^[0-9]*$/;
            if (!cardNum.match(reg1)) {
                return "信用卡号有误";
            }
            if (isNull(expirationYear) || isNull(expirationMonth)) {
                return "信用卡有效至期未选";
            }
            if (!idNo.match(reg1)) {
                return "身份证卡号有误";
            }
            var reg2 = /^[0-9]{3}$/;
            if (!cvv.match(reg2)) {
                return "信用卡验证码有误";
            }
            var reg3 = /^[\u4e00-\u9fa5]+$/;
            if (!holderName.match(reg3)) {
                return "持卡人姓名必须是全汉字";
            }
        }
        return "ok";
    },

    createOrderData: function () {
        //
        var orderType = "line";

        var orderId = $("#orderId").val();


        var data = {};
        if (orderId != null && orderId != "") {
            data["orderId"] = orderId;
        }
        data["contactsName"] = $("#contactsName").val();
        data["contactsTelphone"] = $("#contactsTel").val();
        data["orderType"] = orderType;
        data["hasOld"] = $(".J_hasOld .input_checkbox.enabled").index() == 0;
        data["hasForeigner"] = $(".J_hasForeigner .input_checkbox.enabled").index() == 0;
        data["departureInfoId"] = $("#bus_place .input_checkbox.enabled").data("id");

        var selectDate = $("#resultDate p.s_ccon");

        var id = $("#lineId").val();
        var priceId = $("#linetypepriceId").val();
        var startTime = selectDate.data("date").substr(0, 10);

        var detailList = new Array();
        var detail1 = {};
        detail1["id"] = id;
        detail1["priceId"] = priceId;
        detail1["price"] = selectDate.data("adult-price");
        detail1["startTime"] = startTime;
        detail1["endTime"] = startTime;
        detail1["num"] = $("#passengerNum").val();
        detail1["type"] = "line";
        detail1["seatType"] = "成人";
        if ($("#single-price").is(":visible")) {
            detail1["singleRoomPrice"] = Number($("#single-price .amount").data("price"));
        }
        var touristList1 = LineOrder.getTourist("touristList1", true);
        detail1["tourist"] = touristList1;

        if ($("#guarantee_box").length > 0) {
            var creditCard = {};
            creditCard['status'] = true;
            creditCard['cardNum'] = $.trim($('#cardNum').val());
            creditCard['cvv'] = $.trim($('#cvv').val());
            creditCard['expirationYear'] = $.trim($('#expirationYear').html().replace(/[^0-9]/ig, ""));
            creditCard['expirationMonth'] = $.trim($('#expirationMonth').html().replace(/[^0-9]/ig, ""));
            creditCard['holderName'] = $.trim($('#holderName').val());
            creditCard['creditCardIdType'] = 'IdentityCard';
            creditCard['idNo'] = $.trim($('#idNo').val());

            data["creditCard"] = creditCard;
        }
        detailList.push(detail1);

        if ($("#touristList2 .tourist_info").length > 0) {
            var detail2 = {};
            detail2["id"] = id;
            detail2["priceId"] = priceId;
            detail2["price"] = $("#childPrice").val();
            detail2["startTime"] = startTime;
            detail2["endTime"] = startTime;
            detail2["num"] = $("#passengerChildNum").val();
            detail2["type"] = "line";
            detail2["seatType"] = "儿童";
            var touristList2 = LineOrder.getTourist("touristList2", false);
            detail2["tourist"] = touristList2;
            detailList.push(detail2);
        }
        data["detail"] = detailList;

        var insurances = new Array();
        $("#insurance-price .insurance").each(function () {
            insurances.push($(this).data("id"));
        });
        data["insurances"] = insurances;

        if ($("#receipt_box .panel_body .J_receipt_head input[name=receipt]:checked").parent().index() == 1) {
            var invoice = {};
            invoice["name"] = $("#invoiceName").val();
            invoice["title"] = $("#invoiceTitle").val();
            invoice["telephone"] = $("#invoiceTelephone").val();
            var address = $("#receipt_box .invoiceAddress");
            invoice["address"] = address.eq(0).text() + address.eq(1).text() + address.eq(2).val();

            if (isNull(invoice.name) || isNull(invoice.title) || isNull(invoice.telephone) || isNull(invoice.address) || invoice.address.indexOf("请选择") > 0) {
                promptWarn("请正确输入发票信息！");
                return null;
            }

            data["invoice"] = invoice;
        }

        return JSON.stringify(data);
        ;
    },

    getTourist: function (name, isAdult) {
        //
        var parent = $("#" + name);
        var touristList = parent.find(".tourist_info");
        var size = touristList.size();

        var touristDataList = new Array();
        for (var i = 0; i < size; i++) {
            //
            var inputTourist = touristList.eq(i);

            var tourist = {};
            tourist["name"] = inputTourist.find(".touristName").val();
            if (isAdult) {
                tourist["peopleType"] = "ADULT";
            } else {
                tourist["peopleType"] = "CHILD";
            }
            var idType = inputTourist.find(".touristIdType").val();
            switch (idType) {
                case "1" :
                    tourist["idType"] = "IDCARD";
                    break;
                case "2" :
                    tourist["idType"] = "PASSPORT";
                    break;
            }
            tourist["idNum"] = inputTourist.find(".touristIdNum").val();
            tourist["phone"] = inputTourist.find(".touristTel").val();
            touristDataList.push(tourist);
        }
        return touristDataList;
    },

    submitOrder: function () {
        var validateMsg = Order.checkValidate();
        if (validateMsg == "ok") {
            var lineMsg = LineOrder.checkLine();
            if (lineMsg == "ok") {
                //
                var json = LineOrder.createOrderData();
                if (isNull(json)) {
                    return;
                }
                var userCouponId = $('.redPacketLi .checked').parents('li').attr('couponid');
                $("#submitFlag").val("invalid");
                loadingBegin();
                $.post("/lvxbang/order/createOrder.jhtml",
                    {
                        data: json,
                        userCouponId: userCouponId
                    }, function (result) {
                        loadingEnd();
                        if (result.success == true) {
                            //Order.popMsg("下单成功");
                            promptMessage("下单成功", 3000);
                            var random = parseInt(Math.random() * 10000);
                            window.location.href = "/lvxbang/lxbPay/request.jhtml?orderId=" + result.orderId + "&random=" + random;
                        } else {
                            $("#submitFlag").val("ok");
                            promptWarn("下单失败");
                        }
                    }
                    , "json");
            } else {
                promptWarn(lineMsg);
            }
        } else {
            //
            promptWarn(validateMsg);
        }
    },

    initEdit: function (orderDetailId, returnOrderDetailId) {
        $.post("/lvxbang/order/getOrderInfo.jhtml",
            {
                orderDetailId: orderDetailId,
                returnOrderDetailId: returnOrderDetailId
            }, function (result) {
                if (result.success) {
                    //
                    $("#passengerNum").val(result.num);
                    $("#startDate").val(result.date);
                    LineOrder.changePrice(0);
                    LineOrder.changeDate();
                    var html = "";
                    var num = 1;
                    $.each(result.tourist, function (i, data) {
                        data.index = num;
                        $("#contacts span.oval4").each(function () {
                            var thiz = $(this);
                            if (thiz.find(".idNumber").val() == data.idNumber) {
                                thiz.addClass("checked");
                                data.id = thiz.find(".id").val();
                            }
                        });
                        html += template("tpl-tourist-list-item1", data);
                        num++;
                    });
                    $("#touristList1 .tourist_info").remove();
                    $("#touristList1").append(html);

                    $("#passengerChildNum").val(result.returnNum);
                    LineOrder.changeChildPrice(0);
                    var childHtml = "";
                    var childNum = 1;
                    $.each(result.returnTourist, function (i, data) {
                        data.index = childNum;
                        $("#contacts span.oval4").each(function () {
                            var thiz = $(this);
                            if (thiz.find(".idNumber").val() == data.idNumber) {
                                thiz.addClass("checked");
                                data.id = thiz.find(".id").val();
                            }
                        });
                        childHtml += template("tpl-tourist-list-item2", data);
                        childNum++;
                    });
                    $("#touristList2 .tourist_info").remove();
                    $("#touristList2").append(childHtml);
                } else {
                    promptWarn("获取订单信息失败");
                }
            }
            , "json");
    },
    selectDate: function () {
        $.post("/lvxbang/order/getLineDatePriceList.jhtml", {
            linetypepriceId: $("#linetypepriceId").val()
        }, function (result) {
            //
            if (result.length < 1) {
                promptWarn("门票价格信息不存在");
                return;
            }
            for (var i = 0; i < result.length; i++) {
                //
                var datePrice = result[i];
                var date = new Date(datePrice.day);
                datePrice.showDate = date.format("MM-dd");
                datePrice.showWeek = LineOrder.weekString[date.getDay()];
                var html = template("tpl-select-date-item", datePrice);
                $("#selectDate").append(html);
            }
        }, "json");
    },
    bindEvent: function () {
        //出发日期
        $("#resultDate").click(function (e) {
            // 阻止冒泡
            if (e.stopPropagation) {    // standard
                e.stopPropagation();
            } else {
                // IE6-8
                e.cancelBubble = true;
            }
            $("#selectDate").css("display", "block");
        });
        $("body").click(function () {
            $("#selectDate").css("display", "none");
            $("#receipt_box .J_provice_city .s_clist").css("display", "none");
            $(".sfz .name").siblings(".sfzp").hide();
        });
        $("#selectDate").delegate("li", "click", function () {
            $("#selectDate").css("display", "none");
            $("#resultDate p.s_ccon").text($(this).text()).data("date", $(this).data("date")).data("adult-price", $(this).data("adult-price")).data("child-price", $(this).data("child-price"));
            var date = new Date($(this).data("date"));
            var day = parseInt($("#lineDay").val());
            date.setDate(date.getDate() + day);
            $("#order_box .J_returnDate span").text(date.format("yyyy-MM-dd"));
            $("#single-price .amount").data("price", $(this).data("single-price")).text("￥" + $(this).data("single-price"));
            LineOrder.changePrice(0);
            LineOrder.changeChildPrice(0);
        });
        //酒店担保
        // 下拉事件
        $(".sfz .name,.sfz i").click(function (event) {
            if ($(this).siblings(".sfzp").css("display") == 'none') {
                $(this).siblings(".sfzp").show();
            } else {
                $(this).siblings(".sfzp").hide();
            }

            // 阻止冒泡
            if (event.stopPropagation) {    // standard
                event.stopPropagation();
            } else {
                // IE6-8
                event.cancelBubble = true;
            }
        });
        $(".sfzp a").click(function () {
            var label = $(this).text();
            $(this).parent(".sfzp").hide();
            $(this).parent(".sfzp").siblings(".name").text(label);
        });
        //出游人确认
        $("#order_box .item_answer .input_checkbox").click(function () {
            $(this).addClass("enabled").siblings().removeClass("enabled");
        });
        //上车地点
        $("#bus_place .input_checkbox").click(function () {
            $("#bus_place .input_checkbox").removeClass("enabled");
            $(this).addClass("enabled");
        });
        //保险
        $(".list_row .col1").click(function () {
            $(this).parents(".list_row").next().slideToggle();
        });
        $(".show_more").click(function () {
            $(".list_detail").slideUp();
            $(".list_row:gt(0)").slideToggle();
        });
        $("#insurance_box .input_checkbox").click(function () {
            if ($(this).hasClass("enabled")) {
                $(this).removeClass("enabled");
            } else {
                $(this).addClass("enabled");
            }
            LineOrder.changeInsurance();
        });
        //发票
        $("#receipt_box .panel_body .J_receipt_head input[type=radio]").click(function () {
            var divs = $(this).parents(".J_receipt_head").siblings("div");
            if ($(this).parent().index() == 0) {
                divs.slideUp();
            } else {
                divs.slideDown("hide");
            }
        });
        $("#receipt_box .panel_body .J_receipt_head label").click(function () {
            $(this).siblings("input[type=radio]").click();
        });
        $("#receipt_box .J_provice_city .s_cnum").click(function (e) {
            // 阻止冒泡
            if (e.stopPropagation) {    // standard
                e.stopPropagation();
            } else {
                // IE6-8
                e.cancelBubble = true;
            }
            $(this).next().css("display", "block");
        });
        //右侧价格
        $(window).bind("scroll", function () {
            var scrollTop = $(window).scrollTop();
            var navTop = $(".content").offset().top;
            var left = $(".content").width() + $(".content").offset().left + 6;
            if (scrollTop > navTop) {
                $(".summary").css("position", "fixed").css("top", "0px").css("left", left + "px");
            } else {
                $(".summary").css("position", "relative").css("top", "50px").css("left", "0px");
            }
        });
    },
    initCity: function () {
        $.post("/lvxbang/destination/getAllProvince.jhtml", function (data) {
            $.each(data, function (i, pro) {
                var html = template("tpl-province-city-item", pro);
                $("#province_list").append(html);
            });
            $("#province_list li").click(function () {
                $("#province_list").css("display", "none").prev().find(".s_ccon").text($(this).text());
                $.post("/lvxbang/destination/getCityByProvince.jhtml", {
                    provinceId: $(this).data("id")
                }, function (data) {
                    $("#city_list").empty();
                    $.each(data, function (i, city) {
                        var html = template("tpl-province-city-item", city);
                        $("#city_list").append(html);
                    });
                    $("#city_list li").click(function () {
                        $("#city_list").css("display", "none").prev().find(".s_ccon").text($(this).text());
                    });
                });
            });
        });
    }
};


$(function () {
    LineOrder.init();
});
//事件绑定方法
function bindEvent() {
    $('.redPacketLi .checkbox').click(function () {

        if ($(this).hasClass('checked')) {
            $(this).removeClass('checked');
            LineOrder.changePrice(0);
            return;
        }
        var li = $(this).parents('li');
        var checkedLi = $(this).parents('ul').find('.checked').parents('li');

        var useCondition = Number(li.attr('useCondition'));
        var faceValue = Number(checkedLi.attr('faceValue'));
        if (isNaN(faceValue)) {
            faceValue = 0;
        }
        var rightTotalCost = Number($.trim($('#rightTotalCost').html().replace("¥", "")));
        if ((faceValue + rightTotalCost) >= useCondition) {
            $(this).parents('ul').find('.checked').removeClass('checked');
            $(this).addClass('checked');
            LineOrder.changePrice(0);
        } else {
            promptWarn("金额必须满" + useCondition + "元才能抵用", 1500);
        }


    });
}