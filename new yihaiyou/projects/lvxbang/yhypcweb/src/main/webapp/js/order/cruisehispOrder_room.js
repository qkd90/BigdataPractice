
var CruiseshipOrderRoom = {
    init: function () {
        CruiseshipOrderRoom.initJsp();
        CruiseshipOrderRoom.getTableList();
        CruiseshipOrderRoom.accountPrice();
    },
    initJsp: function () {

        $.each($(":input[name='roomType']"), function (i, perInput) {
            /*滚动*/
            CruiseshipOrderRoom.switchNav($("#" + $(perInput).val() + "Btn"), $("#" + $(perInput).val() + ""), 'active');
            //CruiseshipOrderRoom.switchNav($("#cabinSeaBtn"),$("#cabinSea"),'active');
            //CruiseshipOrderRoom.switchNav($("#cabinViewBtn"),$("#cabinView"),'active');
        });

        /*加减*/
        //CruiseshipOrderRoom.subtract($(".subtract"));
        //CruiseshipOrderRoom.plus($(".plus"));
        CruiseshipOrderRoom.getCruiseshipRoomList();
        /*折叠*/
        //CruiseshipOrderRoom.collapse($(".collapse-btn"));
        /*详情*/
        CruiseshipOrderRoom.tips($(".details"));
    },

    /*//信息填写表格
    getTableList:function() {
        var adultNum = parseInt($("#adult").val());
        for(i=1;i<=adultNum;i++){
            var adult = template("adultInfo");
            $("#person-group div").append(adult);
        }
        var childNum = parseInt($("#children").val());
        for(j=1;j<=children;j++){
            var child = template("childrenInfo");
            $("#person-group div").append(child);
        }

    },*/

    //计算价格
    accountPrice: function(){
        var totalPrice = 0;
        totalPrice += parseInt($("#price").val * ($("#adult").val + $("#children").val));
        $(".pay-price").html("总计：<span><i>¥</i>" + totalPrice + "</span>");
    },


    getCruiseshipRoomList: function () {
        $.each($(":input[name='roomType']"), function (i, perInput) {
            var data = {
                'roomType': $(perInput).val(),
                'dateId': $("#hid_dateId").val()
            };
            var url = "/yhypc/cruiseShip/getCruiseshipRoomList.jhtml";
            $("#" + $(perInput).val() + "_roomList").empty();
            $.post(url, data, function (result) {
                $.each(result, function (j, roomObj) {
                    template.config("escape", false);
                    var result = $(template("tpl-order-cruiseship-room-item", roomObj));
                    $("#" + $(perInput).val() + "_roomList").append(result);
                });
            });

        });
    },

    /*collapse  展开*/
    collapse: function collapse(obj) {
        if (!$(obj).is(".active")) {
            $(".collapse-btn").removeClass('active');
            $(".collapse-btn").closest('ul').siblings('.collapse').slideUp(300);
            $(obj).addClass('active');
            $(obj).closest('ul').siblings('.collapse').slideDown(300);

        } else {
            $(obj).removeClass('active');
            $(obj).closest('ul').siblings('.collapse').slideUp(300);
        }
    },

    /*滚动*/
    switchNav: function (obj, targetObj, className) {
        obj.click(function (ev) {
            obj.addClass(className).siblings().removeClass(className);
            var ev = ev || event;
            var scrolltop = targetObj.offset().top;
            $('body,html').animate({scrollTop: scrolltop}, 1000);
        });
    },
    /*加减按钮  js*/
    /*减*/
    subtract: function (id, type) {
        var peopleNum = Number($("#peopleNum_" + id + "").html());
        var num = Number($("#" + type + "_" + id + "").html());
        var roomNum = Number($("#roomnum_" + id + "").html());
        if (num == 0) {
            return;
        }
        num--;
        $("#" + type + "_" + id + "").html(num);

        var totalNum = 0;
        if (type == 'adult') {
            totalNum = num + Number($("#child_" + id + "").html());
        } else if (type == 'child') {
            totalNum = num + Number($("#adult_" + id + "").html());
        }
        if ((totalNum % peopleNum) == 0) {
            roomNum--;
            $("#roomnum_" + id + "").html(roomNum);
        }
        CruiseshipOrderRoom.calculatePrice();
    },
    /*加*/
    plus: function plus(id, type) {
        var peopleNum = Number($("#peopleNum_" + id + "").html());
        var roomNum = Number($("#roomnum_" + id + "").html());
        var num = Number($("#" + type + "_" + id + "").html());
        var totalNum = 0;
        num++;
        if (type == 'adult') {
            totalNum = num + Number($("#child_" + id + "").html());
        } else if (type == 'child') {
            totalNum = num + Number($("#adult_" + id + "").html());
        }
        $("#" + type + "_" + id + "").html(num);
        if ((totalNum % peopleNum) != 0 && (totalNum % peopleNum) == 1) {
            roomNum++;
            $("#roomnum_" + id + "").html(roomNum);
        }
        CruiseshipOrderRoom.calculatePrice();
    },
    calculatePrice: function() {
        /*console.log($("span[id^='adult_']"));
         console.log($("span[id^='child_']"));*/
        var adultNum = 0;
        var childNum = 0;
        var adultTotalPrice = 0;
        var childTotalPrice = 0;
        var roomNum = 0;
        $.each($("span[id^='roomnum_']"), function(i, span) {
            var num = Number($(span).html());
            if (num != 0) {
                roomNum += num;
            }
        });

        $.each($("span[id^='adult_']"), function(i, span) {
            var num = Number($(span).html());
            if (num != 0) {
                adultNum += num;
                var perPrice = Number($("#perPrice_"+ $(span).attr("data-room-id") +"").val());
                adultTotalPrice += num * perPrice;
            }
        });

        $.each($("span[id^='child_']"), function(i, span) {
            var num = Number($(span).html());
            if (num != 0) {
                childNum += num;
                var perPrice = Number($("#perPrice_"+ $(span).attr("data-room-id") +"").val());
                childTotalPrice += num * perPrice;
            }
        });
        $("#total-people-num-ul").empty();
        if (adultNum != 0) {
            var li = '<li>成人<em>'+ adultNum +'</em>人</li>';
            $("#total-people-num-ul").append(li);
        }
        if (childNum != 0) {
            var li = '<li>儿童<em>'+ childNum +'</em>人</li>';
            $("#total-people-num-ul").append(li);
        }
        if (adultNum == 0 && childNum == 0) {
            var li = '<li><em>0</em>人</li>';
            $("#total-people-num-ul").append(li);
        }
        $("#total-roomnum-span").html(roomNum + "间");
        $("#total-price-span").html("<sub>￥</sub>" + (adultTotalPrice + childTotalPrice));
    },
    makeDetails: function() {
        var adultNum = 0;
        var childNum = 0;
        var adultTotalPrice = 0;
        var childTotalPrice = 0;
        var roomNum = 0;
        $.each($("span[id^='roomnum_']"), function(i, span) {
            var num = Number($(span).html());
            if (num != 0) {
                var roomName = $(span).attr("data-room-name");
                var perPrice = Number($("#perPrice_"+ $(span).attr("data-room-id") +"").val());
                var roomId = $(span).attr("data-room-id");
                adultNum = Number($("#adult_"+ $(span).attr("data-room-id") +"").html());
                childNum = Number($("#child_"+ $(span).attr("data-room-id") +"").html());
                var li = '<li class="clearfix" class="detail-item-li" data-room-id="'+ roomId +'" data-adult-num="'+ adultNum +'" data-child-num="'+ childNum +'" data-room-num="'+ num +'" data-totalprice="'+ (adultNum + childNum) * perPrice +'"><span>'+ roomName +'（'+ num +'间，'+ adultNum +'成人，'+ childNum +'儿童）</span><em>￥' + (adultNum + childNum) * perPrice + '</em></li>';
                $(".details-content").append(li);
            }
        });
    },



    tips: function (obj) {
        $(".details-tips-wrap").hide();
        obj.hover(function () {
            CruiseshipOrderRoom.makeDetails();
            if ($(".details-content").children().length > 0) {
                $(".details-tips-wrap").show();
            }
        }, function () {
            $(".details-content").empty();
            $(".details-tips-wrap").hide();
        });
    },

    makeFromData: function() {
        var adultNum = 0;
        var childNum = 0;
        $.each($("span[id^='roomnum_']"), function(i, span) {
            var num = Number($(span).html());
            if (num != 0) {
                var perPrice = Number($("#perPrice_"+ $(span).attr("data-room-id") +"").val());
                var roomId = $(span).attr("data-room-id");
                var roomName = $(span).attr("data-room-name");
                adultNum = Number($("#adult_"+ $(span).attr("data-room-id") +"").html());
                childNum = Number($("#child_"+ $(span).attr("data-room-id") +"").html());
                var roomTotalPrice = (adultNum + childNum) * perPrice;
                var hidInput = '<input type="hidden" class="detail-item-input" data-room-id="'+ roomId +'" data-room-name="'+ roomName +'" data-room-price="'+ perPrice +'" data-adult-num="'+ adultNum +'" data-child-num="'+ childNum +'" data-room-num="'+ num +'" data-totalprice="'+ roomTotalPrice +'"/>';
                $("#sel-room-form").append(hidInput);
            }
        });
    },

    doSubmit: function() {
        CruiseshipOrderRoom.makeFromData();
        var resultData = {};
        var orderTotalPrice = 0;
        $.each($(".detail-item-input"), function(i, input) {

            orderTotalPrice += Number($(input).attr("data-totalprice"));

            var formInput = '';
            formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].id" value="'+ $(input).attr("data-room-id") +'">';
            formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].roomName" value="'+ $(input).attr("data-room-name") +'">';
            formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].price" value="'+ $(input).attr("data-room-price") +'">';
            formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].adultNum" value="'+ $(input).attr("data-adult-num") +'">';
            formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].childNum" value="'+ $(input).attr("data-child-num") +'">';
            formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].roomNum" value="'+ $(input).attr("data-room-num") +'">';
            formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].totalPrice" value="'+ $(input).attr("data-totalprice") +'">';
            $("#sel-room-form").append(formInput);
        });

        if ($(".detail-item-input").length <= 0) {
            //TODO
            return;
        }
        resultData['dateId'] = $("#hid_dateId").val();
        resultData['productId'] = $("#hid_productId").val();
        $("#hid-form-product-id").val($("#hid_productId").val());
        $("#hid-form-date-id").val($("#hid_dateId").val());
        $("#hid-form-totalprice").val(orderTotalPrice);
        $("#hid-formdata").val(JSON.stringify(resultData));
        $("#sel-room-form").submit();
    }
}

$(function(){
    CruiseshipOrderRoom.init();
});



