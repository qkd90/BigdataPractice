//选项卡
$(".mailTab2 li").click(function () {
    $(this).addClass("checked").siblings().removeClass("checked");
    $(this).closest("div").find(".mailTablePlan2").eq($(this).index()).show().siblings(".mailTablePlan2").hide();
})

//图片遮罩层
//$(".mailTab2 li").hover(function () {
//        if ($(this).hasClass("checked")) {
//            $("p.posiA", this).hide();
//        } else {
//            $("p.posiA", this).show();
//        }
//    },
//    function () {
//        $("p.posiA", this).hide();
//    });
//字数
$(function () {
    $(".textarea").keyup(function () {
        var numx = $(this).attr("mname")
        var len = $(this).val().length;
        if (len > numx - 1) {
            $(this).val($(this).val().substring(0, numx));
        }
        var num = numx - len;
        $(this).next().find(".word").text(num);
    });
});