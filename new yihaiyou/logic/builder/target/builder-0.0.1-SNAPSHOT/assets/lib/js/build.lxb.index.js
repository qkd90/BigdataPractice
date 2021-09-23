$(function () {
    $(".build").click(function () {
        var button = $(this);
        if (button.data("unavailable") == 1) {
            alert("已经在处理中了");
            return;
        }
        if (button.hasClass("hotelDetail")) {
            var hotelId = $("#hotelId").val();
            if (hotelId == "") {
                alert("请先填写ID");
                button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
                return;
            }
            $.get(button.attr("data-url"), {hotelId: hotelId}, function (result) {
                if (result == "success") {
                    button.removeClass("btn-info").addClass("btn-success").text("build成功").removeClass("disabled");
                } else {
                    button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
                }
                button.hover(function () {
                    button.removeClass("btn-success").removeClass("btn-danger").addClass("btn-primary").text(button.attr("data-title")).unbind("hover");
                });
            });
            return;
        }
        if (button.hasClass("build-a-recommend")) {
            var planId = $("#recplanId").val();
            var cityIds = $("#cityIds").val();
            var recplanStartId = $("#recplanStartId").val();
            var recplanEndId = $("#recplanEndId").val();
            if (planId == "" && cityIds == ''&& recplanStartId == '' && recplanEndId == '') {
                alert("请先填写行程或城市ID或ID范围");
                button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
                return;
            }
            $.get(button.attr("data-url"), {
                recplanId: planId,
                cityIds: cityIds,
                recplanStartId: recplanStartId,
                recplanEndId: recplanEndId
            }, function (result) {
                if (result == "success") {
                    button.removeClass("btn-info").addClass("btn-success").text("build成功").removeClass("disabled");
                } else {
                    button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
                }
                button.hover(function () {
                    button.removeClass("btn-success").removeClass("btn-danger").addClass("btn-primary").text(button.attr("data-title")).unbind("hover");

                });
            });
            return;
        }
        if (button.hasClass("destinationDetail")) {
            $("#destination-detail").find(".count").text("0");
            var startId = $("#destination-start-id").val();
            var endId = $("#destination-end-id").val();
            $.get(button.attr("data-url"), {startId: startId, endId: endId}, function (result) {
                if (result == "success") {
                    button.removeClass("btn-info").addClass("btn-success").text("build成功").removeClass("disabled");
                } else {
                    button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
                }
                button.hover(function () {
                    button.removeClass("btn-success").removeClass("btn-danger").addClass("btn-primary").text(button.attr("data-title")).unbind("hover");

                });
            });
            return;
        }
        if (button.hasClass("scenicDetail")) {
            $("#scenic-detail").find(".count").text("0");
            var startId = $("#scenic-start-id").val();
            var endId = $("#scenic-end-id").val();
            $.get(button.attr("data-url"), {startId: startId, endId: endId}, function (result) {
                if (result == "success") {
                    button.removeClass("btn-info").addClass("btn-success").text("build成功").removeClass("disabled");
                } else {
                    button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
                }
                button.hover(function () {
                    button.removeClass("btn-success").removeClass("btn-danger").addClass("btn-primary").text(button.attr("data-title")).unbind("hover");

                });
            });
            return;
        }
        if (button.hasClass("lineDetail")) {
            var lineId = $("#lineId").val();
            if (lineId == "") {
                alert("请先填写ID");
                button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
                return;
            }
            $.get(button.attr("data-url"), {
                lineId: lineId
            }, function (result) {
                if (result == "success") {
                    button.removeClass("btn-info").addClass("btn-success").text("build成功").removeClass("disabled");
                } else {
                    button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
                }
                button.hover(function () {
                    button.removeClass("btn-success").removeClass("btn-danger").addClass("btn-primary").text(button.attr("data-title")).unbind("hover");

                });
            });
            return;
        }
        if (button.hasClass("cloneLine")) {
            var lineId = $("#lineId").val();
            if (lineId == "") {
                alert("请先填写ID");
                button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
                return;
            }
            var cloneNum = $("#cloneNum").val();
            if (cloneNum == "") {
                alert("请先填写复制数量");
                button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
                return;
            }
            $.get(button.attr("data-url"), {
                lineId: lineId,
                cloneNum: cloneNum
            }, function (result) {
                if (result == "success") {
                    button.removeClass("btn-info").addClass("btn-success").text("build成功").removeClass("disabled");
                } else {
                    button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
                }
                button.hover(function () {
                    button.removeClass("btn-success").removeClass("btn-danger").addClass("btn-primary").text(button.attr("data-title")).unbind("hover");

                });
            });
            return;
        }
        button.removeClass("btn-primary").addClass("btn-info").text("building").addClass("disabled");
        $.get(button.attr("data-url"), {}, function (result) {
            if (result == "success") {
                button.removeClass("btn-info").addClass("btn-success").text("build成功").removeClass("disabled");
            } else {
                button.removeClass("btn-info").addClass("btn-danger").text("build失败").removeClass("disabled");
            }
            button.hover(function () {
                button.removeClass("btn-success").removeClass("btn-danger").addClass("btn-primary").text(button.attr("data-title")).unbind("hover");

            });
        });
    });
    $.getJSON("/build/monitor/destination.jhtml", function (result) {
        if (result.status == "RUNNING") {
            $("#destination-detail").find(".status").text("运行中");
            $("#destination-detail").find(".id").text(result.id);
            $("#destination-detail").find(".count").text(result.count);
            $("#destination-detail").find(".build").data("unavailable", 1);
        }
    });
    setInterval(function () {
        $.getJSON("/build/monitor/destination.jhtml", function (result) {
            if (result.status == "RUNNING") {
                $("#destination-detail").find(".status").text("运行中");
                $("#destination-detail").find(".id").text(result.id);
                animateTo("#destination-detail .count", "destination", result.count);
                $("#destination-detail").find(".speed").text((result.count / result.time * 1000).toFixed(2) + "个/秒");
                $("#destination-detail").find(".build").data("unavailable", 1);
            }
            else {
                $("#destination-detail").find(".status").text("");
                $("#destination-detail").find(".build").data("unavailable", 0);
            }
        })
    }, 5000);
    $.getJSON("/build/monitor/scenic.jhtml", function (result) {
        if (result.status == "RUNNING") {
            $("#scenic-detail").find(".status").text("运行中");
            $("#scenic-detail").find(".id").text(result.id);
            $("#scenic-detail").find(".count").text(result.count);
            $("#scenic-detail").find(".build").data("unavailable", 1);
        }
    });
    setInterval(function () {
        $.getJSON("/build/monitor/scenic.jhtml", function (result) {
            if (result.status == "RUNNING") {
                $("#scenic-detail").find(".status").text("运行中");
                $("#scenic-detail").find(".id").text(result.id);
                animateTo("#scenic-detail .count", "scenic", result.count);
                $("#scenic-detail").find(".speed").text((result.count / result.time * 1000).toFixed(2) + "个/秒");
                $("#scenic-detail").find(".build").data("unavailable", 1);
            }
            else {
                $("#scenic-detail").find(".status").text("");
                $("#scenic-detail").find(".build").data("unavailable", 0);
            }
        })
    }, 5000);


});
var animation = {};
function animateTo(id, name, num) {
    clearInterval(animation[name]);
    var current = parseInt($(id).text());
    var count = num - current;
    animation[name] = setInterval(function () {
        if (current < num) {
            current += 1;
            $(id).text(current);
        } else {
            clearInterval(animation[name]);
        }
    }, 5000 / count);
}