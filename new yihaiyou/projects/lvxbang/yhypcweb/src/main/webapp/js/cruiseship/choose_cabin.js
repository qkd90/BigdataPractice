/**
 * Created by Administrator on 2017/6/5.
 */
var chooseCabin = {
    init:function(){
        chooseCabin.getTotalInfo();
        chooseCabin.personScreen();
        chooseCabin.favorableScreen();
        chooseCabin.favorableClear();
        chooseCabin.minusPlus();
        chooseCabin.priceDetails($(".last-price-icon"),$(".last-price-views"));
        chooseCabin.priceDetails($(".tips-icon"),$(".tips-details"));
        chooseCabin.achorFn();
        chooseCabin.detailsFn();
    },
    //表单提交
    doSubmit: function() {
        var resultData = {};
        var orderTotalPrice = 0;
        $.each($(".cabin-group"), function(i, input) {
            var num = 0;
            orderTotalPrice = parseFloat($(input).find(".allOfPrice").attr("totalPrice"));
            num = parseInt($(input).find(".roomNum").val());
            if(num != 0){
                var formInput = '';
                formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].id" value="'+ $(input).attr("data-room-id") +'">';
                formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].roomName" value="'+ $(input).find(".roomName").attr("name") +'">';
                formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].price" value="'+ $(input).attr("price") +'">';
                formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].adultNum" value="'+ $(input).find(".adult").val() +'">';
                formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].childNum" value="'+ $(input).find(".children").val() +'">';
                formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].roomNum" value="'+ $(input).find(".roomNum").val() +'">';
                formInput += '<input type="hidden"  name="cruiseshipOrderRoomRequests['+ i +'].totalPrice" value="'+ parseFloat($(input).attr("totalprice"))  +'">';
                $("#sel-room-form").append(formInput);
            }

        });

        if ($(".cabin-group").length <= 0) {
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
    },
    /*筛选人数*/
    personScreen:function(){
        $(".person").hover(function(){
            $(".person-list").show();
            $(".person-list").find("li").click(function(){
                var num = $(this).attr("value");
                if(num == 0){
                    $(".cabin-category").show();
                    $(".cabin-group").show();
                }
                else{
                $(".cabin-group").hide();
                $(".cabin-group[peopleNum=" + num +"]").show();
                $(".cabin-category").show();
                if ($(".cabin-category").find(".cabin-group:visible").length == 0) {
                    $(".cabin-category").hide();

                    }
                }
                var html = $(this).html();
                $(".person-show").find("span").html(html);
                $(".person-list").hide();
            });
        },function(){
            $(".person-list").hide();
        });
    },
    //计算总人数，价格
    getTotalInfo:function(){

        var total = 0;
        var totalPeople = 0;
        var totalRoomNum = 0;
        var averageOfPrice = 0;
        var num = 0;
        var html = "";
        $(".cabin-group").each(function(){

            num = parseInt($(this).find(".adult").val()) + parseInt($(this).find(".children").val());
            total += num * parseFloat($(this).attr("price"));
            totalPeople += num;
            totalRoomNum += parseInt($(this).find(".roomNum").val());

            var roomName = $(this).find(".roomName").attr("name");
            var roomNum = $(this).find(".roomNum").val();
            var eachOfPrice = parseFloat(num * parseFloat($(this).attr("price")));
            $(this).attr("totalprice", eachOfPrice);
            if(num > 0){
               html +=('<tr>' + '<td>'+ roomName + '(' + num +'人)' + '</td>'+'            '+'<td>'+'x' + roomNum + '</td>'+'                 '+'<td>'+'￥'+ eachOfPrice +'</td>'+'</tr>');
            }
        });
        $(".allOfPrice").attr("totalPrice",total);
        $(".orderDetails tbody").html(html);
        $("#roomNum").html("房间：" + totalRoomNum + "间");
        $("#totalPrice").html("总价：<span><sub>¥</sub>" + total + "</span>");
        $(".totalPerson").html("入住人数：" + totalPeople + "人");
        if(totalPeople == 0){
            average = 0;
        }
        else{
            averageOfPrice = parseInt(total / totalPeople) ;
        }

        $(".price-average").html("人均：¥" + averageOfPrice );
    },
    /*优惠政策筛选*/
    favorableScreen:function(){
        $(".favorable-group").click(function(){
            if($(this).hasClass('active')){
                $(this).removeClass("active");
            }else{
                $(this).addClass("active");
            };
            for(var i=0;i<$(".favorable-group").length;i++){
                if($(".favorable-group").eq(i).hasClass("active")){
                    $(".person-favorable-clear").show();
                    break;
                }else{
                    $(".person-favorable-clear").hide();
                }
            };
        });
    },
    /*优惠政策清除*/
    favorableClear:function(){
        /*初始化状态*/
        for(var i=0;i<$(".favorable-group").length;i++){
            if($(".favorable-group").eq(i).hasClass("active")){
                $(".person-favorable-clear").show();
                break;
            }else{
                $(".person-favorable-clear").hide();
            }
        };
        $(".person-favorable-clear").click(function(){
                $(this).hide();
                for(var i=0;i<$(".favorable-group").length;i++){
                    $(".favorable-group").eq(i).removeClass("active");
                }
            }
        );
    },
    /*加减按钮事件*/
    minusPlus:function(){
        /*初始化*/
        $(".minus").click(function(){
            var value = parseInt( $(this).siblings('input').val() );
            if(value <= 0){
                $(this).siblings("input").val(0);
            }else{
                $(this).siblings("input").val(value-1);
            }

            var group = $(this).parents(".cabin-group");
            var totalPerson = parseInt(group.find(".adult").val()) + parseInt(group.find(".children").val());
            var peoNum = parseInt($(".cabin-group").attr("peopleNum"));
            var roomNum = Math.ceil(totalPerson / peoNum);
            group.find(".roomNum").val(roomNum);
            var eachOfPrice = totalPerson * (parseInt($(".cabin-group").attr("price")));


            /*if(totalPerson > 0){
                $('.orderDetails tbody').append("<tr>" + "<td>"+ roomName + "(" + totalPerson +"人)" + "</td>"+"            "+"<td>"+"x" + roomNum + "</td>"+"                 "+"<td>"+"￥"+ eachOfPrice +"</td>"+"</tr>");
            }*/

            chooseCabin.getTotalInfo();
        });
        $(".plus").click(function(){
            var value = parseInt( $(this).siblings('input').val() );
            $(this).siblings("input").val(value+1);

            var group = $(this).parents(".cabin-group");
            var totalPerson = parseInt(group.find(".adult").val()) + parseInt(group.find(".children").val());
            var peoNum = parseInt($(".cabin-group").attr("peopleNum"));
            var roomNum = Math.ceil(totalPerson / peoNum);
            group.find(".roomNum").val(roomNum);
            var eachOfPrice = totalPerson * (parseInt($(".cabin-group").attr("price")));
            var roomName = group.find(".roomName").attr("name");

            /*if(totalPerson > 0){
                $('.orderDetails tbody').append("<tr>" + "<td>"+ roomName + "(" + totalPerson +"人)" + "</td>"+"            "+"<td>"+"x" + roomNum + "</td>"+"                 "+"<td>"+"￥"+ eachOfPrice +"</td>"+"</tr>");
            }*/


            chooseCabin.getTotalInfo();
        });

    },
    /*正常显示价格状态下  显示价格明细*/
    priceDetails:function(obj,target){
        obj.hover(function(){
            target.show();
        },function(){
            target.hide();
        });
    },
    /*锚点事件*/
    achorFn:function(){
        /*获取触发导航定位的最小高度*/
        var minHeight = $(".product-select").offset().top;
        /*获取导航的高度*/
        var navHeight = $(".product-select").outerHeight(true);
        /*获取锚点的offsetTop值*/
        var archorArray = [];
        for(var i=0;i<$(".cabin-category").length;i++){
            archorArray.push($(".cabin-category").eq(i).offset().top);
        };
        /*鼠标滚动事件*/
        $(window).scroll(function(){
            /*获取滚动条的滚动距离*/
            var scrollHeight = $(window).scrollTop();
            if(scrollHeight >= minHeight){
                $(".product-select").addClass('fixed');
                $(".product").css("margin-bottom",navHeight + 28);
            }else{
                $(".product-select").removeClass("fixed");
                $(".product").css("margin-bottom",20);
            };
            for(var i=0;i<archorArray.length;i++){
                if(scrollHeight + navHeight >= archorArray[i]){
                    $(".select-nav").find("li").eq(i).addClass("active").siblings().removeClass("active");
                }
            }
        });
        /*鼠标点击事件*/
        $(".select-nav").find("li").click(function(){
            var index  = $(this).index();
            $("body,html").animate({"scrollTop":archorArray[index] -navHeight },200);
        });
    },
    /*在有订单数据的情况下  点击明细*/
    detailsFn:function(){
        $(".all-details-btn").click(function(){
            if(!$(this).hasClass("active")){
                $(this).addClass("active");
                $(this).find("span").html("收起");
                $(".price-details").show();
                $(".select-data").addClass("active");
            }else{
                $(this).removeClass("active");
                $(this).find("span").html("明细");
                $(".price-details").hide();
                $(".select-data").removeClass("active");
            }
        });
    }
};
$(function(){
    chooseCabin.init();
});