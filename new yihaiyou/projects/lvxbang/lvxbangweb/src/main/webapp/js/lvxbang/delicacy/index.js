$(document).ready(function(){
    //选项卡
    $(".food_div .mailTab li").click(function(){
        $(this).addClass("checked").siblings().removeClass("checked");
        $(this).parents(".food_div").find(".mailTablePlan").eq($(this).index()).show().siblings(".mailTablePlan").hide();
        $(this).parents(".food_div").find(".mailTablePlan").eq($(this).index()).find("img").lazyload({
            effect: "fadeIn"
        });
    });
    $(".food_div .mailTab li").parents(".food_div").find(".mailTablePlan").eq(0).find("img").lazyload({
        effect: "fadeIn"
    });

    //栏目加浮动
    var navbar = function(){
        var navbar_top = $(window).scrollTop();

        if(navbar_top >=680){
            $(".nav").addClass("fixed");
        }
        if(navbar_top <=680){
            $(".nav").removeClass("fixed");
        }}
    $(window).bind("scroll", navbar);


});

function toDelicacyList(){
    if($('#delicacyName').val() == $('#delicacyName').attr('placeholder')){
        $('#delicacyName').val('');
    }
    $("#searchForm").submit();
}
$(function(){
    //inputChange();
    toDelicacyCity();
});

//function inputChange() {
//    $("#delicacyName").bind("input propertychange", function () {
//        var name = $("#delicacyName").val();
//        $.post("/lvxbang/delicacy/suggestList.jhtml",
//            {
//                'delicacyRequest.name': name
//            },
//            function (data) {
//                $("#selectDelicacyId").empty();
//                for (var i = 0; i < data.length; i++) {
//                    var s = data[i].name;
//                    var n = s.indexOf(name);
//                    var s1 = "";
//                    var s2 = "";
//                    if (n < 0) {
//                        return;
//                    }
//                    if (n == 0) {
//                        s2 = s.substring(name.length, s.length);
//                    }
//                    else if (n == s.length) {
//                        s1 = s.substring(0, n);
//                    }
//                    else {
//                        var str = s.split(name);
//                        s1 = str[0];
//                        s2 = str[1];
//                    }
//                    $("#selectDelicacyId").append('<li><label delicacy="'+s+'" class="fl">'+ s1 +'<strong>' + name + '</strong>' + s2 + '</label></li>');
//                }
//
//                $("#selectDelicacyId").on("click", "li label", function () {
//                    var delicacy = $(this).attr("delicacy");
//                    $("#delicacyName").val(delicacy);
//                });
//            });
//    });
//}

function toDelicacyCity(){
    $("#delicacyCity").on("click", "dd a", function () {
        var cityName = $(this).attr("cityName");
        $('#cityName').val(cityName);
        var cityCode = $(this).attr("cityCode");
        $('#cityCode').val(cityCode);
        $('#formid').submit();
    });

}


