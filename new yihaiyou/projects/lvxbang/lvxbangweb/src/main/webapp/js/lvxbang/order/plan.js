/**
 * Created by vacuity on 16/1/10.
 */

var onOff=true;
var PlanOrder = {

    init: function () {

        PlanOrder.fillTourist();
        PlanOrder.initCopyCheck();
        //门票日期固定
        //PlanOrder.initDate();
        //PlanOrder.selected();
        PlanOrder.initCalendar();
        PlanOrder.initAllConditions();
        //checkbox
        $(".free_exercise_div .checkbox").click(function () {
            var input = $(this).attr("input");
            var myStaute = $(this).attr("data-staute");
            if (!myStaute) {
                if (input == "selectall") {
                    $(this).addClass("checked").attr("data-staute", "1").parents('.free_e_fl_c').find(".free_e_fl_lm .checkbox").addClass("checked").attr("data-staute", "1");
                }
                else if (input == "selectall_group") {
                    $(this).addClass("checked").attr("data-staute", "1").parent('.free_e_fl_c_title').next(".free_e_fl_lm_div").find("ul .checkbox").addClass("checked").attr("data-staute", "1");
                }
                else {
                    $(this).addClass("checked").attr("data-staute", "1");
                }
            }
            else {
                if (input == "selectall") {
                    $(this).parents('.free_e_fl_c').find(".free_e_fl_lm .checkbox").removeClass("checked").removeAttr("data-staute");
                } else if (input == "selectall_group") {
                    $(this).removeClass("checked").removeAttr("data-staute").parent('.free_e_fl_c_title').next(".free_e_fl_lm_div").find("ul .checkbox").removeClass("checked").removeAttr("data-staute");
                    $(this).parents('.free_e_fl_c').find(".free_e_fl_c_ut .checkbox").removeClass("checked").removeAttr("data-staute");

                }
                $(this).removeClass("checked").removeAttr("data-staute");
                $(this).parents('.free_e_fl_lm_div').prev('.free_e_fl_c_title').find(".checkbox").removeClass("checked").removeAttr("data-staute");
                $(this).parents('.free_e_fl_c').find(".free_e_fl_c_ut .checkbox").removeClass("checked").removeAttr("data-staute");
            }

            //
            PlanOrder.selected();
        });

        $('.redPacketLi .checkbox').unbind();
        $('.redPacketLi .checkbox').click(function(){


            if($(this).hasClass('checked')){
                $(this).removeClass('checked');
                PlanOrder.selected();
                return;
            }

            if( !(($('#right-flight').hasClass('display-none') || $('#right-train').hasClass('display-none')) &&
                $('#right-ticket').hasClass('display-none') && $('#right-hotel').hasClass('display-none'))){
                promptWarn("订单必须包含交通,酒店,门票",1500);
                return;
            }

            var li = $(this).parents('li');
            var checkedLi = $(this).parents('ul').find('.checked').parents('li');

            var useCondition = Number(li.attr('useCondition'));
            var faceValue = Number(checkedLi.attr('faceValue'));
            if(isNaN(faceValue)){
                faceValue = 0;
            }
            var rightSumCost = Number($.trim($('#rightSumCost').html().replace("¥","")));
            if ((faceValue + rightSumCost) >= useCondition) {
                $(this).parents('ul').find('.checked').removeClass('checked');
                $(this).addClass('checked');
                PlanOrder.selected();
            }else{
                promptWarn("金额必须满"+useCondition+"元才能抵用",1500);
            }
        });

        //radio
        $(".tong .radio").click(function () {
            var myStaute = $(this).parent(".tong").attr("data-staute");
            if (!myStaute) {
                $(this).parent(".tong").addClass("checked").attr("data-staute", "1");
                $(this).parents(".free_e_fl_lm_ul_div").next().css("display","none");
                $(this).parents(".free_e_fl_lm_ul_div").find(".quantity").attr("disabled",true);

            } else {
                $(this).parent(".tong").removeClass("checked").removeAttr("data-staute");
                $(this).parents(".free_e_fl_lm_ul_div").next().css("display","block");
                $(this).parents(".free_e_fl_lm_ul_div").find(".quantity").attr("disabled",false);
            }
        });

        //下拉框
        $(".sfz .name,.sfz i").click(function () {
            if($(this).siblings(".sfzp").css("display")=="none"){
              $(this).siblings(".sfzp").show();
            }else{
                $(this).siblings(".sfzp").hide();
            }
        });

        $(".sfzp a").click(function () {
            var label = $(this).text();
            $(this).parent(".sfzp").hide();
            $(this).parent(".sfzp").siblings(".name").text(label);
        });

        $(document).on("click", function () {
            changeFocus();
        });

        $(".jingdian .long-name.is_hover").hover(function () {
            $(this).siblings(".long-name-hover").show();
        }, function () {
            $(this).siblings(".long-name-hover").hide();
        })
    },

    initCopyCheck: function () {
        var flightCount = Number($("#flight-count").val());

        if (flightCount > 0) {
            //
            $("#flight-copy-0").addClass("display-none");
            $("#flight-copy-0").html("");
        } else {
            // 火车
            var trainCount = Number($("#train-count").val());
            if (trainCount > 0) {
                $("#train-copy-0").addClass("display-none");
                $("#train-copy-0").html("");
            } else {
                // 酒店
                var hotelCount = Number($("#hotel-count").val());
                if (hotelCount > 0) {
                    $("#hotel-copy-0").addClass("display-none");
                    $("#hotel-copy-0").html("");
                } else {
                    // 景点
                    var ticketCount = Number($("#ticket-count").val());
                    if (ticketCount > 0) {
                        $("#ticket-copy-0").addClass("display-none");
                        $("#ticket-copy-0").html("");
                    }
                }
            }
        }


    },


    changePrice: function (num, type, id) {

        //if(id==0 && type=='flight'){
        //    onOff = false;
        //}
        //if(type=='ticket'){
        //    if(onOff && $('#ticket-copy-'+id).hasClass('checked')){
        //        return;
        //    }
        //}
        //if(type=='flight'){
        //    if(onOff && $('#flight-copy-'+id).hasClass('checked')){
        //        return;
        //    }
        //}
        //if(type=='train'){
        //    if(onOff && $('#train-copy-'+id).hasClass('checked')){
        //        return;
        //    }
        //}

        // 人数改变时改变总金额和游客信息输入框的数量
        var passengerNumId = type + "-" + "passengerNum-" + id;
        var priceId = type + "-" + "price-" + id;
        var sumnCostId = type + "-" + "sumCost-" + id;
        var contactsId = type + "-" + "contacts-" + id;
        var touristId = type + "-" + "tourist-" + id;
        //var passengerNumId = type + "-" + "passengerNum-" + id;
        var page = "plan";

        var rightPanel = "right-" + type + "-" + id;
        Order.changePrice(num, passengerNumId, priceId, sumnCostId, contactsId, touristId, rightPanel, page);


        PlanOrder.selected();

    },
    addOne : function(){
        var flightcount = $('#flight-count').val();
        var traincount = $('#train-count').val();
        var hotelcount = $('#hotel-count').val();
        var ticketcount = $('#ticket-count').val();
        for(var i=1; i<flightcount; i++){
            if($('#flight-copy-'+i).hasClass('checked') ) {
                PlanOrder.copyTouristEx('flight', i);
            }
        }
        for(var i=0; i<traincount; i++){
            if($('#train-copy-'+i).hasClass('checked')) {
                PlanOrder.copyTouristEx('train', i);
            }
        }
        for(var i=0;i<hotelcount;i++){
            if($('#hotel-copy-'+i).hasClass('checked')) {
                PlanOrder.copyTouristEx('hotel', i);
            }
        }

        for(var i=0;i<ticketcount;i++){
            if($('#ticket-copy-'+i).hasClass('checked')) {
                PlanOrder.copyTouristEx('ticket', i);
            }
        }
        //$(".tong .radio").each(function(e){
        //    if($('#flight-copy-'+(e+1)).hasClass('checked') ){
        //        //PlanOrder.copyTouristEx('flight', e);
        //        PlanOrder.copyTouristEx('flight', e+1);
        //    }
        //});
        //$(".tong .radio").each(function(e){
        //    if($('#train-copy-'+e).hasClass('checked')){
        //        PlanOrder.copyTouristEx('train', e);
        //        PlanOrder.copyTouristEx('train', e+1);
        //    }
        //});
        //$(".tong .radio").each(function(e){
        //    if($('#hotel-copy-'+e).hasClass('checked')){
        //        PlanOrder.copyTouristEx('hotel', e);
        //        PlanOrder.copyTouristEx('hotel', e+1);
        //    }
        //});
        //$(".tong .radio").each(function(e){
        //    if($('#ticket-copy-'+e).hasClass('checked')){
        //        PlanOrder.copyTouristEx('ticket', e);
        //        PlanOrder.copyTouristEx('ticket', e+1);
        //    }
        //});
    },
    fillTourist: function () {
        // 飞机
        var flightCount = Number($("#flight-count").val());
        for (var i = 0; i < flightCount; i++) {
            PlanOrder.fillEx('flight', i);
        }
        // 火车
        var trainCount = Number($("#train-count").val());
        for (var i = 0; i < trainCount; i++) {
            PlanOrder.fillEx('train', i);
        }
        // 酒店
        var hotelCount = Number($("#hotel-count").val());
        for (var i = 0; i < hotelCount; i++) {
            PlanOrder.fillEx('hotel', i);
        }
        // 景点
        var ticketCount = Number($("#ticket-count").val());
        for (var i = 0; i < ticketCount; i++) {
            PlanOrder.fillEx('ticket', i);
        }
    },

    fillEx: function (type, index) {
        var contacts = type + "-contacts-" + index;
        var tourist = type + "-tourist-" + index;
        Order.fillTourist(contacts, tourist);
    },

    copyTourist: function (type, index) {
        var check = type + "-copy-" + index;
        if (!$("#" + check).hasClass("checked")) {
            PlanOrder.copyTouristEx(type, index);
        }
    },

    copyTouristEx: function (type, index) {
        //
        var flightCount = Number($("#flight-count").val());
        var trainCount = Number($("#train-count").val());
        var hotelCount = Number($("#hotel-count").val());
        var ticketCount = Number($("#ticket-count").val());
        var toContacts = type + "-contacts-" + index;
        var toTourist = type + "-tourist-" + index;
        var passengerNum = type + "-passengerNum-" + index;

        var fromContacts = "";
        var fromTourist = "";
        if (index == 0) {
            switch (type) {
                case "train":
                    fromContacts = "flight-contacts-" + (flightCount - 1);
                    fromTourist = "flight-tourist-" + (flightCount - 1);
                    break;
                case "hotel":
                    if (trainCount > 0) {
                        //
                        fromContacts = "train-contacts-" + (trainCount - 1);
                        fromTourist = "train-tourist-" + (trainCount - 1);
                    } else {
                        fromContacts = "flight-contacts-" + (flightCount - 1);
                        fromTourist = "flight-tourist-" + (flightCount - 1);
                    }
                    break;
                case "ticket":
                    if (hotelCount > 0) {
                        fromContacts = "hotel-contacts-" + (hotelCount - 1);
                        fromTourist = "hotel-tourist-" + (hotelCount - 1);
                    } else if (trainCount > 0) {
                        fromContacts = "train-contacts-" + (trainCount - 1);
                        fromTourist = "train-tourist-" + (trainCount - 1);
                    } else {
                        fromContacts = "flight-contacts-" + (flightCount - 1);
                        fromTourist = "flight-tourist-" + (flightCount - 1);
                    }
            }
        } else {
            fromContacts = type + "-contacts-" + (index - 1);
            fromTourist = type + "-tourist-" + (index - 1);
        }
        Order.copyTourist(fromContacts, fromTourist, toContacts, toTourist, passengerNum, "plan", type, index);
    },

    selected: function () {
        var flightCount = Number($("#flight-count").val());
        var trainCount = Number($("#train-count").val());
        var hotelCount = Number($("#hotel-count").val());
        var ticketCount = Number($("#ticket-count").val());

        var sumCost = 0;

        var rightFlightFlag = false;
        for (var i = 0; i < flightCount; i++) {
            if ($("#flight-check-" + i).hasClass("checked")) {
                //
                rightFlightFlag = true;
                var num = Number($("#flight-passengerNum-" + i).val());
                var price = Number($("#flight-price-" + i).val());
                var additionalFuelTaxPrice = Number($("#flight-price-" + i).attr("additionalFuelTaxPrice"));
                var airportBuildFeePrice = Number($("#flight-price-" + i).attr("airportBuildFeePrice"));
                var cost = num * (price + additionalFuelTaxPrice + airportBuildFeePrice) ;
                sumCost += cost;
                $("#right-flight-" + i).removeClass("display-none");
            } else {
                $("#right-flight-" + i).addClass("display-none");
            }
        }
        if (rightFlightFlag) {
            $("#right-flight").removeClass("display-none");
        } else {
            $("#right-flight").addClass("display-none");
        }

        var rightTrainFlag = false;
        for (var i = 0; i < trainCount; i++) {
            if ($("#train-check-" + i).hasClass("checked")) {
                //
                rightTrainFlag = true;
                var num = Number($("#train-passengerNum-" + i).val());
                var price = Number($("#train-price-" + i).val());
                var cost = num * price;
                sumCost += cost;
                $("#right-train-" + i).removeClass("display-none");
            } else {
                $("#right-train-" + i).addClass("display-none");
            }
        }
        if (rightTrainFlag) {
            $("#right-train").removeClass("display-none");
        } else {
            $("#right-train").addClass("display-none");
        }

        var rightHotelFlag = false;
        var ddzj = sumCost;
        var jddf = 0;

        if(PlanOrder.needCreditcard()){
           $('#hotelPriceStatus').next().show();
        }else{
            $('#hotelPriceStatus').next().hide();
        }
        for (var i = 0; i < hotelCount; i++) {
            if ($("#hotel-check-" + i).hasClass("checked")) {
                //
                rightHotelFlag = true;
                var num = Number($("#hotel-passengerNum-" + i).val());
                var price = Number($("#hotel-price-" + i).val());
                if(!isNull($('#hotel-price-' + i).attr("days"))){
                    var days = Number($('#hotel-price-' + i).attr("days"));
                    var cost = num * price * days;
                } else{
                    var cost = num * price;
                }
                if($('#hotel-price-' + i).attr("source") != 'ELONG'){
                  sumCost += cost;
                }
                ddzj += cost;
                jddf += cost;
                $("#right-hotel-" + i).removeClass("display-none");
            } else {
                $("#right-hotel-" + i).addClass("display-none");
            }
        }
        if (rightHotelFlag) {
            $("#right-hotel").removeClass("display-none");
        } else {
            $("#right-hotel").addClass("display-none");
        }

        var rightTicketFlag = false;
        for (var i = 0; i < ticketCount; i++) {
            if ($("#ticket-check-" + i).hasClass("checked")) {
                //
                rightTicketFlag = true;
                var num = Number($("#ticket-passengerNum-" + i).val());
                var price = Number($("#ticket-price-" + i).val());
                var cost = num * price;
                sumCost += cost;
                ddzj += cost;
                $("#right-ticket-" + i).removeClass("display-none");
            } else {
                $("#right-ticket-" + i).addClass("display-none");
            }
        }
        if (rightTicketFlag) {
            $("#right-ticket").removeClass("display-none");
        } else {
            $("#right-ticket").addClass("display-none");
        }

        if($('.redPacketLi .checked').length > 0){
            var li = $('.redPacketLi .checked').parents('li');
            var useCondition = Number(li.attr('useCondition'));
            if(sumCost >= useCondition){
                sumCost = sumCost - Number(li.attr("faceValue"));
            }else{
                $('.redPacketLi .checked').removeClass('checked');
            }
        }
        $("#sumCost").text("¥" + sumCost + "");
        $("#rightSumCost").text("¥" + sumCost);
        $(".ddzj").text("¥" + ddzj);
        $(".jddf").text("-" + jddf);
    },

    initDate: function () {
        var date = new Date();
        date.setDate(date.getDate() + 1);
        var dateString = date.format("yyyy-MM-dd");
        var ticketCount = Number($("#ticket-count").val());
        for (var i = 0; i < ticketCount; i++) {
            $("#ticket-date-" + i).val(dateString);
        }

    },

    changeDate: function (index) {
        var ticketPriceId = $("#ticket-priceId-" + index).val();
        var date = $("#ticket-date-" + index).val();
        //
        $.post("/lvxbang/order/getTicketDatePrice.jhtml",
            {
                ticketDate: date,
                ticketPriceId: ticketPriceId

            }, function (result) {
                if (result.success == true) {
                    $("#ticket-printPrice-" + index).text(result.price);
                    $("#ticket-price-" + index).val(result.price);
                    PlanOrder.changeCost(index);
                } else {
                    $("#ticket-printPrice-" + index).text("-");
                    $("#ticket-price-" + index).val(0);
                    promptWarn("选定日期没有价格数据");
                    PlanOrder.changeCost(index);
                }
            }
            ,"json");
    },
    changeCost: function (index) {
        var num = Number($("#ticket-passengerNum-" + index).val());
        var price = Number($("#ticket-price-" + index).val());
        var cost = num * price;
        if (cost > 0) {
            $("#ticket-sumCost-" + index).text(cost);
            $("#right-ticket-cost-" + index).text("" + cost);
        } else {
            $("#ticket-sumCost-" + index).text("-");
            $("#right-ticket-cost-" + index).text("-");
        }

        PlanOrder.selected();
    },

    createOrderData: function () {
        //
        var checkFlag = false;

        var orderType = "plan";

        var orderId = $("#orderId").val();

        var data = {};
        if (orderId != null && orderId != "") {
            data["orderId"] = orderId;
        }
        data["contactsName"] = $("#contactsName").val();
        data["contactsTelphone"] = $("#contactsTel").val();
        data["name"] = $("#planName").val();
        data["orderType"] = orderType;
        var detailList = new Array();

        var flightCount = Number($("#flight-count").val());
        var trainCount = Number($("#train-count").val());
        var hotelCount = Number($("#hotel-count").val());
        var ticketCount = Number($("#ticket-count").val());

        for (var i = 0; i < flightCount; i++) {
            if ($("#flight-check-" + i).hasClass("checked")) {
                //
                checkFlag = true;
                var detail = {};
                detail["id"] = $("#flight-id-" + i).val();
                detail["priceId"] = $("#flight-priceId-" + i).val();
                detail["startTime"] = $("#flight-date-" + i).val();
                detail["num"] = $("#flight-passengerNum-" + i).val();
                detail["type"] = 'flight';
                detail["seatType"] = $("#flight-seatType-" + i).val();
                var touristList = Order.getTourist("flight-tourist-" + i);
                detail["tourist"] = touristList;
                detailList.push(detail);
            }
        }

        for (var i = 0; i < trainCount; i++) {
            if ($("#train-check-" + i).hasClass("checked")) {
                //
                checkFlag = true;
                var detail = {};
                detail["id"] = $("#train-id-" + i).val();
                detail["priceId"] = $("#train-priceId-" + i).val();
                detail["startTime"] = $("#train-date-" + i).val();
                detail["num"] = $("#train-passengerNum-" + i).val();
                detail["type"] = 'train';
                detail["seatType"] = $("#train-seatType-" + i).val();
                var touristList = Order.getTourist("train-tourist-" + i);
                detail["tourist"] = touristList;
                detailList.push(detail);
            }
        }

        for (var i = 0; i < hotelCount; i++) {
            if ($("#hotel-check-" + i).hasClass("checked")) {
                //
                checkFlag = true;
                var detail = {};
                detail["id"] = $("#hotel-id-" + i).val();
                detail["startTime"] = $("#hotel-date-" + i).val();
                detail["endTime"] = $("#hotel-leaveDate-" + i).val();
                detail["num"] = $("#hotel-passengerNum-" + i).val();
                detail["priceId"] = $("#hotel-priceId-" + i).val();
                detail["price"] = $("#hotel-price-" + i).val();
                detail["type"] = "hotel";
                detail["seatType"] = $("#hotel-seatType-" + i).val();
                var touristList = Order.getTourist("hotel-tourist-" + i);
                detail["tourist"] = touristList;


                //酒店担保时信用卡信息
                var creditCard = {};
                if($("#hotelPriceStatus-" + i).val() == 'GUARANTEE'){
                    creditCard['status'] = true;
                    creditCard['cardNum'] = $.trim($('#cardNum').val());
                    creditCard['cvv'] = $.trim($('#cvv').val());
                    creditCard['expirationYear'] = $.trim($('#expirationYear').html().replace(/[^0-9]/ig,""));
                    creditCard['expirationMonth'] = $.trim($('#expirationMonth').html().replace(/[^0-9]/ig,""));
                    creditCard['holderName'] = $.trim($('#holderName').val());
                    var creditCardIdType = $.trim($('#creditCardIdType').html());
                    if(creditCardIdType == '身份证'){
                        creditCardIdType = 'IdentityCard';
                    }else {
                        creditCardIdType = 'Passport';
                    }
                    creditCard['creditCardIdType'] = creditCardIdType;
                    creditCard['idNo'] = $.trim($('#idNo').val());
                }else {
                    creditCard['status'] = false;
                }
                detail['creditCard'] = creditCard;
                detailList.push(detail);
            }
        }


        for (var i = 0; i < ticketCount; i++) {
            if ($("#ticket-check-" + i).hasClass("checked")) {
                checkFlag = true;
                //
                if ($("#ticket-price-" + i).val() == "0") {
                    promptWarn("第" + (i + 1) + "个门票在所选日期没有门票价格数据，请重新选择日期");
                    return null;
                }
                var detail = {};
                var price = $("#ticket-price-" + i).val();
                var playDate = $("#ticket-date-" + i).val();
                if (playDate == null || playDate == "") {
                    promptWarn("请选择门票使用日期");
                    return null;
                }
                if (price == null || price == 0) {
                    promptWarn("所选日期没有门票价格数据,请重新选择门票使用日期");
                    return null;
                }
                detail["id"] = $("#ticket-id-" + i).val();
                detail["priceId"] = $("#ticket-priceId-" + i).val();
                detail["price"] = $("#ticket-price-" + i).val();
                detail["startTime"] = $("#ticket-date-" + i).val();
                detail["num"] = $("#ticket-passengerNum-" + i).val();
                detail["type"] = "scenic";
                detail["seatType"] = "成人票";
                detail["cityId"] = $("#ticket-cityId-" + i).val();
                detail["day"] = $("#ticket-day-" + i).val();
                var touristList = Order.getTourist("ticket-tourist-" + i);
                detail["tourist"] = touristList;
                detailList.push(detail);
            }
        }

        if (!checkFlag) {
            promptWarn("请至少选择一件商品");
            return null;
        }


        data["detail"] = detailList;

        var json = JSON.stringify(data);
        return json;
    },
    needCreditcard: function (){
        var hotelCount = Number($("#hotel-count").val());
        for (var i = 0; i < hotelCount; i++) {
            if ($("#hotel-check-" + i).hasClass("checked")) {
                if($("#hotelPriceStatus-" + i).val() == 'GUARANTEE'){
                    return true;
                }
            }
        }
    },
    submitOrder: function () {
        //提交前进行旅客信息复制
        var flightc = $('#flight-count').val();
        var trainc = $('#train-count').val();
        var hotelc = $('#hotel-count').val();
        var ticketc = $('#ticket-count').val();
        for(var i=1; i<flightc; i++){
            if($('#flight-copy-'+i).hasClass('checked') ) {
                PlanOrder.copyTouristEx('flight', i);
            }
        }
        for(var i=0; i<trainc; i++){
            if($('#train-copy-'+i).hasClass('checked')) {
                PlanOrder.copyTouristEx('train', i);
            }
        }
        for(var i=0;i<hotelc;i++){
            if($('#hotel-copy-'+i).hasClass('checked')) {
                PlanOrder.copyTouristEx('hotel', i);
            }
        }

        for(var i=0;i<ticketc;i++){
            if($('#ticket-copy-'+i).hasClass('checked')) {
                PlanOrder.copyTouristEx('ticket', i);
            }
        }

        var validateMsg = Order.checkValidate();

        //酒店担保时验证信用卡号及填写内容
        var flag = true;
        if(PlanOrder.needCreditcard()){

            if($("#hotelPriceStatus").val() == 'GUARANTEE') {

                var cardNum = $.trim($('#cardNum').val());
                var cvv = $.trim($('#cvv').val());
                var holderName = $.trim($('#holderName').val());
                var idNo = $.trim($('#idNo').val());
                var expirationYear = $.trim($('#expirationYear').html().replace(/[^0-9]/ig,""));
                var expirationMonth = $.trim($('#expirationMonth').html().replace(/[^0-9]/ig,""));


                if(isNull(cardNum) || isNull(cvv) || isNull(holderName) || isNull(idNo) ){
                    promptWarn("酒店担保信息未填完整",1000);
                    return;
                }
                var reg1 = /^[0-9]*$/;
                if (!cardNum.match(reg1)) {
                    promptWarn("信用卡号有误",1000);
                    return;
                }
                if(isNull(expirationYear) || isNull(expirationMonth)){
                    promptWarn("信用卡有效至期未选",1000);
                    return;
                }
                if (!idNo.match(reg1)) {
                    promptWarn("身份证卡号有误",1000);
                    return;
                }
                var reg2 = /^[0-9]{3}$/;
                if(!cvv.match(reg2)){
                    promptWarn("信用卡验证码有误",1000);
                    return;
                }
                var reg3 = /^[\u4e00-\u9fa5]+$/;
                if(!holderName.match(reg3)){
                    promptWarn("持卡人姓名必须是全汉字",1000);
                    return;
                }


                $.ajax({
                    url: "/lvxbang/order/validateCreditCart.jhtml",
                    type: "post",
                    async: false,
                    dataType: 'json',
                    data: {'cardNum': $('#cardNum').val()},
                    success: function (data) {
                        if (!data.success) {
                            promptWarn("信用卡号不存在",1000);
                            $('#cardNum').click().focus();
                            flag = false;
                        }else{
                            window.console.log("可以创建订单信息了");
                        }
                    },
                    error: function () {
                        window.console.log("error");
                    }
                });

            }

        }
        if(!flag){
            return;
        }

        if (validateMsg == "ok") {
            //
            var json = PlanOrder.createOrderData();
            var userCouponId = $('.redPacketLi .checked').parents('li').attr('couponid');
            if (json != null) {
                $("#submitFlag").val("invalid");
                loadingBegin();
                $.post("/lvxbang/order/createOrder.jhtml",
                    {
                        data: json,
                        userCouponId:userCouponId
                    }, function (result) {
                        loadingEnd();
                        if (result.success == true) {
                            //Order.popMsg("下单成功");
                            promptMessage("下单成功",3000);
                            var random = parseInt(Math.random() * 10000);
                            window.location.href = "/lvxbang/lxbPay/request.jhtml?orderId=" + result.orderId + "&random=" + random;
                        } else {
                            $("#submitFlag").val("ok");
                            promptWarn("下单失败");
                        }
                    }
                    ,"json");
            }
        } else {
            //
            promptWarn(validateMsg);
        }
    },


    initCalendar: function () {
        var ticketCount = Number($("#ticket-count").val());
        for (var i = 0; i < ticketCount; i++) {
            //$("#ticket-date-" + i).val(dateString);


            PlanOrder.initCalenderDiv(i);

        }

    },

    initCalenderDiv: function (i) {

        var defaultDate = new Date();
        // 日历
        $('#priceCalendar-' + i).fullCalendar({
            header: {
                left: 'prev',
                center: 'title',
                right: 'next'
            },
            theme: true,
            defaultDate: defaultDate,
            lang: 'zh-cn',
            buttonIcons: false, // show the prev/next text
            weekNumbers: false,
            fixedWeekCount: false,
            editable: true,
            //selectable: true,
            eventLimit: true, // allow "more" link when too many events
            //events: '/line/linetypepricedate/findTypePriceDate.jhtml?dateStart='+dateStart+'&linetypepriceId='+priceId+'&cIndex=1'

            monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
            dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
            dayNamesShort: ["日", "一", "二", "三", "四", "五", "六"],
            today: ["今天"],
            //height: 225,
            //contentHeight: 300,
            aspectRatio: 1.0,
            eventClick: function (calEvent, jsEvent, view) {
                PlanOrder.eventClick(calEvent, i);
            },
            dayClick: function (date, allDay, jsEvent, view) {
                PlanOrder.changeCalendarDate(date, i);
            }
        });

        PlanOrder.initPriceCalender(i);
    },

    //
    initPriceCalender: function (i) {
        var ticketPriceId = $("#ticket-priceId-" + i).val();
        $.post("/lvxbang/order/getTicketPriceList.jhtml", {
            ticketPriceId: ticketPriceId
        }, function (result) {

            //
            if (result.length < 1) {
                promptWarn("门票价格信息不存在");
                PlanOrder.disalbeCalendar();
                return;
            }
            var data = [];
            for (var j = 0; j < result.length; j++) {
                //
                var datePrice = result[j];
                var priceDate = datePrice.huiDate.substring(0, 10);
                data.push({
                    id: datePrice.id,
                    price: datePrice.priPrice,
                    date: priceDate,
                    title: '¥' + datePrice.priPrice,
                    start: priceDate
                });
            }
            //data.push({id:11,discountPrice:1,rebate:2,title:'成人'+1+"("+2+")",start:'2016-03-22'});

            var filter = function (event) {
                return tempId.indexOf(event._id) > -1;
            };
            $('#priceCalendar-' + i).fullCalendar('removeEvents', filter);
            $('#priceCalendar-' + i).fullCalendar('addEventSource', data);
            //disalbeCalendar();
        },"json");
    },


    // 点击有价格的日历
    changeCalendarDate: function (date, i) {

        date = date.format();
        $('#priceCalendar-' + i).fullCalendar('clientEvents', function (event) {
            if (event.start.format() == date) {
                var price = event.price;
                var eventDate = event.date;
                $("#ticket-date-" + i).val(eventDate);

                price = price.toFixed(1);
                $("#ticket-printPrice-" + i).text(price);
                $("#ticket-price-" + i).val(price);
                PlanOrder.changeCost(i);
                PlanOrder.disalbeCalendar();
            }
        });


    },

    eventClick: function (calEvent, i) {
        var price = calEvent.price;
        var date = calEvent.date;
        $("#ticket-date-" + i).val(date);

        price = price.toFixed(1);
        $("#ticket-printPrice-" + i).text(price);
        $("#ticket-price-" + i).val(price);
        PlanOrder.changeCost(i);
        PlanOrder.disalbeCalendar();
    },

// 显示日历
    enalbeCalendar: function (i) {
        $("#priceCalendar-" + i).removeClass("visibility-hidden");
    },

// 隐藏日历
    disalbeCalendar: function () {
        $(".fullcalendar").addClass("visibility-hidden");
    },

    //进入订单页，初始化默认全选
    initAllConditions: function () {
        var flightcount = $('#flight-count').val();
        var traincount = $('#train-count').val();
        var hotelcount = $('#hotel-count').val();
        var ticketcount = $('#ticket-count').val();
        var thiz = $(".free_exercise_div .checkbox").eq(0);
        $(thiz).addClass("checked").attr("data-staute", "1").parents('.free_e_fl_c').find(".free_e_fl_lm .checkbox").addClass("checked").attr("data-staute", "1");
        PlanOrder.selected();

        $(".tong .radio").each(function(e){
            $(this).parent(".tong").addClass("checked").attr("data-staute", "1");
            $(this).parents(".free_e_fl_lm_ul_div").next().css("display","none");

            $('#flight-passengerNum-'+(e+1)).attr('disabled',true);

            $('#train-passengerNum-'+e).attr('disabled',true);
            $('#train-passengerNum-'+(e+1)).attr('disabled',true);
            $('#hotel-passengerNum-'+e).attr('disabled',true);
            $('#hotel-passengerNum-'+(e+1)).attr('disabled',true);
            $('#ticket-passengerNum-'+e).attr('disabled',true);
            $('#ticket-passengerNum-'+(e+1)).attr('disabled',true);

            if(flightcount==0){
                $('#train-passengerNum-0').attr('disabled',false);
            }
            if(flightcount == 0 && traincount == 0){
                $('#hotel-passengerNum-0').attr('disabled',false);
            }
            if(flightcount == 0 && traincount == 0 && hotelcount == 0){
                $('#ticket-passengerNum-0').attr('disabled',true);
            }
        });

        //$('.ticketAddOne').click(function(){
        //    PlanOrder.addOne();
        //});
    }

};


$(function () {
    PlanOrder.init();

});


function changeDate(index) {
    PlanOrder.changeDate(index);
};

// 当时间框和日历失去焦点后隐藏日历
function changeFocus() {
    var focusE = document.activeElement;
    if (focusE.className.indexOf("calendar-input") < 0 && focusE.className.indexOf("ui-state-default") < 0) {
        PlanOrder.disalbeCalendar();
    }
}

