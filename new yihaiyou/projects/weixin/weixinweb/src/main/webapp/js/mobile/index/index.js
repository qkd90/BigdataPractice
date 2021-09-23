$(function () {
    $(".city-wrap").find(".city-type-name").click(function () {
        $(".city-wrap").find(".city-type-name").removeClass("active");
        $(this).addClass("active");
        $(".city-list").find("ul").addClass("hide");
        $(".city-list").find("ul").eq($(this).index()).removeClass("hide");

    })
});