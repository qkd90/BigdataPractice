$(document).ready(function(){
    $('#hotelSearchForm .required').addClass('sho');
    bindInputs("hotelSearchForm");
    //选项卡
    $(".hotels_fl .mailTab li").click(function(){
        $(this).addClass("checked").siblings().removeClass("checked");
        $(this).parents(".hotels_fl").find(".mailTablePlan").eq($(this).index()).show().siblings(".mailTablePlan").hide();
    });

    //栏目加浮动
    var navbar = function () {
        var navbar_top = $(window).scrollTop();
        var height = $("#nav").offset().top;
        //window.console.info('nav scroll navbar_top='+navbar_top +'\theight'+height);
        if (navbar_top >= height) {
            $("#history").addClass("fixed");
        }
        if (navbar_top <= height) {
            $("#history").removeClass("fixed");
        }
    };
    $(window).bind("scroll", navbar);

    $.getJSON("/other/visitHistory/listTop.jhtml",
        {
            resType: "hotel",
            page: 1,
            limit: 5
        },
        function (result) {
            $.each(result, function (i, data) {
                $(".hotels_fr_ul").append(template("tpl-visited-hotel", data));
            });
            $(".hotels_fr_ul").find("img").lazyload({
                effect : "fadeIn"
            });
    });
    lookMap();

    $('.ico3').click(function(){
        if($('.hotellevel_div').css("display") == "none"){
            //$('.hotellevel_div').css("display","block");
            $('.ico3').css('background-position','-172px 0')

        } else {
           // $('.hotellevel_div').css("display","none");
            $('.ico3').css('background-position','-154px 0')
        }

        // 阻止冒泡
        if (event.stopPropagation) {    // standard
            event.stopPropagation();
        } else {
            // IE6-8
            event.cancelBubble = true;
        }
    });

    //后退的时候判断hash有没有值，有则显示在相应的框内
    if(!isNull(getFromHash("areaId"))){
        var cityId_input = $('#hotelSearchForm').find('.input').eq(0);
        cityId_input.attr('data-areaid', getFromHash("areaId"));
        $('#hotel_cityId').val(Number(cityId_input.attr('data-areaid')));
        cityId_input.val(getFromHash("areaName"));
        $('#lDate-3').val(getFromHash("lDate-3"));
        $('#rDate-3').val(getFromHash("rDate-3"));
        $('#gjc').val(getFromHash("gjc"));
    }

    //刚进去酒店首页时给定默认城市

        $.ajax({
            url: "/lvxbang/destination/getIpCity.jhtml",
            type: "post",
            dataType: "json",
            data: {},
            success: function (data) {
                //console.log(data);
                var name = $('#ipCity').val();
                if (isNull(name)) {
                    $('#ipCity').val(data.name);
                    $('#ipCity').attr("data-areaid", data.id);
                }
                var date = new Date();
                date.setDate(date.getDate());
                if (isNull($('#lDate-3').val())) {
                    $('#lDate-3').val(date.format("yyyy-MM-dd"));
                }
                date.setDate(date.getDate() + 1);
                if (isNull($('#rDate-3').val())) {
                    $('#rDate-3').val(date.format("yyyy-MM-dd"));
                }
            },
            error: function (data) {
                //console.log(data);
            }
        });

    $("#lDate-3").change(function(){
        var lDate = new Date(Date.parse($('#lDate-3').val().replace("-", "/")));
        var rDate = new Date(Date.parse($('#rDate-3').val().replace("-", "/")));
        if (rDate.getTime() - lDate.getTime() < 24 * 60 * 60 * 1000) {
            lDate.setDate(lDate.getDate() + 1);
            $('#rDate-3').val(lDate.format("yyyy-MM-dd"));
        }
        $('#rDate-3').focus().click();
    });


});

//点击酒店入住日期时清空退房日期
function cleanrdate(){
    $('#rDate-3').val('');
}

$(function(){
    $('.mailTablePlan input').bind('keypress',function(event){
        if(event.keyCode == "13")
        {
            SearcherBtn.btnHotelSeach();
        }
    });
});