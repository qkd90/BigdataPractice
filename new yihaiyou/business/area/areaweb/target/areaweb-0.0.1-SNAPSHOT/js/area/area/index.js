$(function () {
    $(".city").each(function () {
        if ($(this).hasClass("selected")) {
            $(this).parents(".city-list-wrap").slideDown();
        }
    });
    $(".province-section").each(function () {
        var province = $(this);
        province.find(".province-title>label").click(function() {
            province.find(".city-list-wrap").slideToggle();
        });
        province.find(".province-title>input").click(function () {
            if ($(this).prop("checked")) {
                province.find(".city-list-wrap").find("input").attr("checked", true);
                province.find(".city-list-wrap").find(".city").addClass("selected");
            } else {
                province.find(".city-list-wrap").find("input").attr("checked", null);
                province.find(".city-list-wrap").find(".city").removeClass("selected");
            }
            province.find(".city-list-wrap").slideDown();
        });
        province.find(".city-list-wrap").find("input").click(function () {
            $(this).parents(".city").toggleClass("selected");

            var flag = true;
            province.find(".city-list-wrap").find("input").each(function () {
                if (!$(this).prop("checked")) {
                    flag = false;
                    return false;
                }
            });
            if (flag) {
                province.find(".province-title>input").attr("checked", true);
            } else {
                province.find(".province-title>input").attr("checked", null);
            }
        });
    });
});