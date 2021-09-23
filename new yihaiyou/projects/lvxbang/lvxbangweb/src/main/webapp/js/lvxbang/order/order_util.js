/**
 * Created by vacuity on 16/1/4.
 */


var Order = {

    // 根据人数实时改变总价格
    changePrice: function (clickNum, passengerNumIdName, priceIdName, sumCostIdName, contactsListIdName, touristListIdname, rightPanelName, pageName) {
        //
        clickNum = Number(clickNum);
        var passengerNumE = $("#" + passengerNumIdName);
        var passengerNum = passengerNumE.val();
        passengerNum = Number(passengerNum);
        var priceE = $("#" + priceIdName);
        var price = Number(priceE.val());
        if (passengerNum == null) {
            passengerNum = 0;
        } else {
            passengerNum += clickNum;
            if (passengerNum <= 0) {
                passengerNum = 1;
            }
            //限制买票数量操作（pageName可以判断类型）
            //if (passengerNum >=5) {
            //    passengerNum = 5;
            //}
        }
        passengerNumE.val(passengerNum);
        var cost = passengerNum * price;

        if(!isNull($("#days").val())){
            cost = cost * Number($("#days").val());
        }
        if(!isNull($("#" + rightPanelName).attr('days'))){
            cost = cost * Number($("#" + rightPanelName).attr('days'));
        }
        cost = parseInt(cost * 100) / 100;

        //console.log(priceIdName.split("-")[0]);
        //var priceType = priceIdName.split("-")[0];
        //if(priceType == 'flight'){
        //    $("#" + sumCostIdName).text(cost);
        //    $(".jpj").text("" + cost);
        //} else {
        //    $("#" + sumCostIdName).text(cost);
        //}

        //window.console.log(priceIdName.split("-")[0]);
        var priceType = priceIdName.split("-")[0];
        Order.changeNum(passengerNum, touristListIdname, contactsListIdName);

        var rightPanel = $("#" + rightPanelName);
        var additionalFuelTaxCost = passengerNum * Number(rightPanel.find('.s_additionalFuelTaxPrice').attr("additionalFuelTax"));
        var airportBuildFeeCost = passengerNum * Number(rightPanel.find('.s_airportBuildFeePrice').attr("airportBuildFee"));

        var additionalFuelTaxCost2 = passengerNum * Number($('.r_additionalFuelTaxPrice').attr("additionalFuelTax"));
        var airportBuildFeeCost2 = passengerNum * Number($('.r_airportBuildFeePrice').attr("airportBuildFee"));

        var planAdditionalFuelTaxCost = passengerNum * Number(rightPanel.find('.additionalFuelTaxPrice').val());
        var planAirportBuildFeeCost = passengerNum * Number(rightPanel.find('.airportBuildFeePrice').val());

        rightPanel.find(".rightPanelTouristNum").text("" + passengerNum + "x");

        if(priceType == 'flight'){
            rightPanel.find(".rightPanelCost").text("¥" + (cost + planAdditionalFuelTaxCost + planAirportBuildFeeCost));
            rightPanel.find(".jpj").text("" + cost);
        } else if(priceType == "singleFlightPrice"){
            if(isNaN(additionalFuelTaxCost)){
                additionalFuelTaxCost = 0;
                airportBuildFeeCost = 0;
            }
            rightPanel.find(".rightPanelCost").text("¥" + (cost + additionalFuelTaxCost + airportBuildFeeCost));
            rightPanel.find(".jpj").text("" + cost);
        } else if(priceType == "returnFlightPrice"){
            if(isNaN(additionalFuelTaxCost2)){
                additionalFuelTaxCost2 = 0;
                airportBuildFeeCost2 = 0;
            }
            rightPanel.find(".rightPanelCost").text("¥" + (cost + additionalFuelTaxCost2 + airportBuildFeeCost2));
            rightPanel.find(".jpj").text("" + cost);
        }
        else {
            rightPanel.find(".rightPanelCost").text("¥" + cost);
        }


        rightPanel.find('.s_additionalFuelTaxPrice').text(additionalFuelTaxCost);
        rightPanel.find('.s_airportBuildFeePrice').text(airportBuildFeeCost);
        rightPanel.find('.r_additionalFuelTaxPrice').text(additionalFuelTaxCost2);
        rightPanel.find('.r_airportBuildFeePrice').text(airportBuildFeeCost2);
        rightPanel.find('.additionalFuelTax').text(planAdditionalFuelTaxCost);
        rightPanel.find('.airportBuildFee').text(planAirportBuildFeeCost);


        if(priceType == 'flight'){
            $("#" + sumCostIdName).text(parseInt((cost + planAdditionalFuelTaxCost + planAirportBuildFeeCost) * 100) / 100);
        }else if(priceType == "singleFlightPrice"){
            if(isNaN(additionalFuelTaxCost)){
                additionalFuelTaxCost = 0;
                airportBuildFeeCost = 0;
            }
            $("#" + sumCostIdName).text(parseInt((cost + additionalFuelTaxCost + airportBuildFeeCost) * 100) / 100);
        }else if(priceType == "returnFlightPrice"){
            if(isNaN(additionalFuelTaxCost2)){
                additionalFuelTaxCost2 = 0;
                airportBuildFeeCost2 = 0;
            }
            $("#" + sumCostIdName).text(parseInt((cost + additionalFuelTaxCost2 + airportBuildFeeCost2) * 100) / 100);
        }
        else {
            $("#" + sumCostIdName).text(parseInt(cost * 100) / 100);
        }

        if (pageName == "sf") {
            SingleFlight.totalCost(cost);
        } else if (pageName == "rf") {
            ReturnFlight.totalCost();
        } else if (pageName == "hotel") {
            HotelOrder.changeByNum(cost);
        } else if (pageName == "ticket") {
            TicketOrder.totalCost(cost);
        } else if (pageName == "line") {
            LineOrder.changeCost();
        }
    },

    // 根据人数增删游客信息输入框的数量
    changeNum: function (num, idName, contactsListIdName) {
        //
        var singleTouristFirst = "<div class='nr cl touristClass'> <input type='hidden' class='touristId'/> <b class='fl num disB fs16'>";
        var singleTouristSecond = "</b> <div class='fl fill_div_nr_fr'> <div class='fill_w1 cl'> <div class='DebitCard_d2 fl oval4'> <input type='text' placeholder='姓名(请确保与证件保持一致)' value='' class='input touristName'> </div> " +
            //"<div class='sfz fl posiR oval4'> <p class='name touristPeopleType'>成人</p><i></i> <p class='sfzp disn'> <a href='javaScript:;'>成人</a> <a href='javaScript:;'>儿童</a> </p> </div>" +
            " </div> <div class='fill_w2 cl oval4'> <div class='sfz fl posiR'> <p class='name touristIdType'>身份证</p><i></i> <p class='sfzp disn'> <a href='javaScript:;'>身份证</a> <a href='javaScript:;'>护照</a> </p> </div> <div class='DebitCard_d2 fl'> <input type='text' placeholder='请输入您的证件号' value='' class='input touristIdNum'> </div> </div> <div class='fill_w3 cl oval4'> <div class='DebitCard_d2 '> <input type='text' placeholder='请输入手机号' value='' class='input touristTel'> </div> </div> </div> </div>";

        var parent = $("#" + idName);
        //var touristList = $("#singleFlightTourist").find(".touristClass");
        var touristList = parent.find(".touristClass");
        var touristListSize = touristList.size();
        if (num > touristListSize) {
            //
            for (var i = touristListSize; i < num; i++) {
                var index = i + 1;
                var indexString = singleTouristFirst + index + singleTouristSecond;
                parent.append($(indexString));
            }


        } else if (num < touristListSize) {
            //
            var c = touristListSize - num;
            for(var re=0;re<c;re++){
                touristList.eq(num+re).remove();
            }
            var deleteIdString = ",";
            for (var i = num; i < touristListSize; i++) {
                //
                var inputTourist = touristList.eq(i);
                var id = inputTourist.find(".touristId").val();
                if (id != null && id != "") {
                    deleteIdString = deleteIdString + id + ",";
                }
            }
            var contactList = $("#" + contactsListIdName).find("span");
            for (var i = 0; i < contactList.size(); i++) {
                var contacts = contactList.eq(i);
                var contactsId = contacts.find(".id").val();
                if (deleteIdString.indexOf(contactsId) > 0) {
                    contacts.removeClass("checked");
                }
            }
            touristList.splice(num, touristListSize - num);

            //parent.html("");
            //parent.append(touristList);
        }

        //下拉事件
        $(".sfz .name,.sfz i").click(function () {
            $(this).siblings(".sfzp").show();
        });
        $(".sfzp a").click(function () {
            var label = $(this).text();
            $(this).parent(".sfzp").hide();
            $(this).parent(".sfzp").siblings(".name").text(label);
        });
    },

    fillTourist: function (contactListIdName, touristListIdname, touristListIdname2, age) {


        //

        $("#" + contactListIdName).on("click", 'span', function () {
            var parent = $("#" + touristListIdname);
            if (age != null && touristListIdname2 != null) {
                var selectIdNum = $(this).find(".idNumber").val();
                var selectTime = Number(selectIdNum.substr(6, 8));
                var nowTime = Number(new Date().format("yyyyMMdd"));
                if (nowTime - selectTime < age * 10000) {
                    parent = $("#" + touristListIdname2);
                }
            }
            var touristList = parent.find(".touristClass");
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
                            inputTourist.find(".touristIdType").text("身份证");
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
                    if (id == "" && name == "") {
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

    getTourist: function (name) {
        //
        var parent = $("#" + name);
        var touristList = parent.find(".touristClass");
        var size = touristList.size();

        var touristDataList = new Array();
        for (var i = 0; i < size; i++) {
            //
            var inputTourist = touristList.eq(i);

            var tourist = {};
            tourist["name"] = inputTourist.find(".touristName").val();
            var peopleType = inputTourist.find(".touristPeopleType").text();
            if (peopleType == "成人") {
                tourist["peopleType"] = "ADULT";
            } else {
                tourist["peopleType"] = "KID";
            }
            var idType = inputTourist.find(".touristIdType").text();
            if (idType == "身份证") {
                tourist["idType"] = "IDCARD";
            } else if (idType == "护照") {
                tourist["idType"] = "PASSPORT";
            }
            tourist["idNum"] = inputTourist.find(".touristIdNum").val();
            tourist["phone"] = inputTourist.find(".touristTel").val();
            touristDataList.push(tourist);
        }
        return touristDataList;
    },

    copyTourist: function(fromContactsListParentId, fromTouristParentId, toContactsListParentId, toTouristParentId, passengerNumIdName, pageName, type, index) {
        //
        var fromContactsParent = $("#" + fromContactsListParentId);
        var toContactsParent = $("#" + toContactsListParentId);
        var fromTouristParent = $("#" + fromTouristParentId);
        var toTouristParent = $("#" + toTouristParentId);

        var fromContactsList = fromContactsParent.find("span");
        var toContactsList = toContactsParent.find("span");
        var size = fromContactsList.size();

        // 选中状态
        for (var i = 0; i < size; i++) {
            if (fromContactsList.eq(i).hasClass("checked")) {
                toContactsList.eq(i).addClass("checked");
            } else {
                toContactsList.eq(i).removeClass("checked");
            }
        }
        // 旅客列表
        var fromTouristList = fromTouristParent.find(".touristClass");
        var fromSize = fromTouristList.size();
        $("#" + passengerNumIdName).val(fromSize);
        if (pageName == "rf") {
            ReturnFlight.changePrice(0, "back");
        } else if (pageName == "plan") {
            //
            PlanOrder.changePrice(0, type, index);
        }
        var toTouristList = toTouristParent.find(".touristClass");
        for (var i = 0; i < fromSize; i++) {
            //
            var fromTourist = fromTouristList.eq(i);
            var toTourist = toTouristList.eq(i);

            toTourist.find(".touristId").val(fromTourist.find(".touristId").val());
            toTourist.find(".touristName").val(fromTourist.find(".touristName").val());
            toTourist.find(".touristPeopleType").text(fromTourist.find(".touristPeopleType").text());
            toTourist.find(".touristIdType").text(fromTourist.find(".touristIdType").text());
            toTourist.find(".touristIdNum").val(fromTourist.find(".touristIdNum").val());
            toTourist.find(".touristTel").val(fromTourist.find(".touristTel").val());
        }

        //toContactsParent.html("");
        //toContactsParent.append(to);
        //
        //toTouristParent.html("");
        //toTouristParent.append(fromTouristList.html());

    },

    // 验证页面上的所有游客信息和联系人信息是否满足要求
    checkValidate: function() {
        //
        var mobile = /^((1[3,5,8,7,4]{1})\d{9})$/;
        var nameList = $(".touristName");
        var idNumList = $(".touristIdNum");
        var telList = $(".touristTel");
        for (var i = 0; i < nameList.size(); i++) {
            var checkbox = nameList.eq(i).closest("li").find(".free_e_fl_lm_ul_div .checkbox");
            if (checkbox.size() > 0) {
                if (!checkbox.hasClass("checked")) {
                    continue;
                }
            }
            if (nameList.eq(i).val() == null || nameList.eq(i).val() == "") {
                nameList.eq(i).focus();
                nameList.eq(i).parent().css("border-color","red");
                return "出行人信息有误，请填写正确信息";
            }
        }
        for (var i = 0; i < idNumList.size(); i++) {

            var checkbox = idNumList.eq(i).closest("li").find(".free_e_fl_lm_ul_div .checkbox");
            if (checkbox.size() > 0) {
                if (!checkbox.hasClass("checked")) {
                    continue;
                }
            }
            if (idNumList.eq(i).val() == null || idNumList.eq(i).val() == "") {
                idNumList.eq(i).focus();
                idNumList.eq(i).parent().css("border-color","red");
                if(idNumList.eq(i).hasClass('touristIdNum')){
                    idNumList.eq(i).parent().parent().css("border-color","red");
                }
                return "页面内游客证件信息未填写完整";
            }
            //var idNo = /^(\d{18})$/;
            var idNo = /^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/;
            if (!idNumList.eq(i).val().match(idNo)) {
                idNumList.eq(i).focus();
                idNumList.eq(i).parent().parent().css("border-color","red");
                return "证件信息有误";
            }

        }
        for (var i = 0; i < telList.size(); i++) {
            //if (!telList.eq(i).closest("li").find(".free_e_fl_lm_ul_div .checkbox").hasClass("checked")) {
            //    continue;
            //}
            var checkbox = telList.eq(i).closest("li").find(".free_e_fl_lm_ul_div .checkbox");
            if (checkbox.size() > 0) {
                if (!checkbox.hasClass("checked")) {
                    continue;
                }
            }
            var tel = telList.eq(i).val();
            if (tel == null || tel == "" || !tel.match(mobile)) {
                telList.eq(i).focus();
                telList.eq(i).parent().parent().css("border-color","red");
                return "页面内游客手机信息未填写或者手机号有误";
            }
        }
        var contactsName = $("#contactsName").val();
        if (contactsName == null || contactsName == "") {
            $("#contactsName").focus();
            $("#contactsName").parent().css("border-color","red");
            return "联系人信息请填写完整";
        }
        var contactsTel = $("#contactsTel").val();
        if (contactsTel == null || contactsTel == "" || !contactsTel.match(mobile)) {
            $("#contactsTel").focus();
            $("#contactsTel").parent().css("border-color","red");
            return "联系人电话未填写或者有误";
        }
        var provisionCheck = $("#" + "provisionCheck");
        if (!provisionCheck.hasClass("checked")) {
            return "请接受协议条款";
        }
        if ($("#submitFlag").val() != "ok") {
            return "您提交的订单正在处理中，请不要重复提交";
        }
        return "ok";
    },

    popMsg: function (msg) {
        $(".mask").show();
        $(".cg_prompt").fadeIn().find("span").text(msg);
        setTimeout(function () {
            $(".mask").hide();
            $(".cg_prompt").fadeOut();
        }, 3000);//延迟1秒
    }
}

$(document).ready(function () {
    $("body").delegate(".protocol", "click", function () {
        $.ajax({
            url:"/lvxbang/help/dataListBykeyword.jhtml",
            type:"post",
            dataType:"json",
            data:{
                'keyword':"我已阅读并接受协议条款、补充条款及其他所有内容"
            },
            success:function(data){
                protocolPop(data.title, data.content);
            }
        });
    });

    $(".moreknow").hover(function () {
        $(this).find("span").css("display", "block");
    }, function () {
        $(this).find("span").css("display", "none");
    });

    $("body").delegate("input", "blur", function () {
        $(this).parent().css('border-color','#ccc');
        if($(this).parent().parent().css('border-color')=='rgb(255, 0, 0)'){
            $(this).parent().parent().css('border-color','#ccc');
        }
    });
});