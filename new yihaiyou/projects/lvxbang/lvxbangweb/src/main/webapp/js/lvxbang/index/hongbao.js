// JavaScript Document
$(document).ready(function () {
    $(".home-hb").click(function () {
        $(".hb-link").attr("href", "hongbao.html");
        $(".home-hb").hide();
        $(".hb-bg").animate({width: "100%"});
        $(".hb-pic").animate({width: "100%"});
    });
    $(".hb-close").click(function () {
        $(".hb-link").attr("href", "javascript:;");
        $(".hb-bg").animate({width: "0"});
        $(".hb-pic").animate({width: "0"}, function () {
            $(".home-hb").show();
        });
    });
});