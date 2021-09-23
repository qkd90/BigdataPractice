/**
 * Created by zzl on 2016/2/23.
 */
var recplanId = $("#recplanId").val();
$(function() {
    promptMessage("正在加载游记,请稍候", null, false);
    $("#travel_list").html('');
    $("#travel_index").html('');
    $.ajax({
        url: "/lvxbang/recommendPlan/previewRecommendPlan.jhtml",
        type: "post",
        dataType: "json",
        data: {
            'recplanId': recplanId
        },
        success: function(result) {
            if (result.result != null && result.result == "nologin") {
                changeMsg("请先登录!");
                setTimeout(function() {
                    return window.location.href = $('#index_path').val() + "/lvxbang/login/login.jhtml";
                },2000);
            } else if (result.result != null && result.result == "user_not_match") {
                changeMsg("只可以预览自己的游记!");
                setTimeout(function() {
                    return window.location.href = "/lvxbang/recommendPlan/index.jhtml";
                },3000);
            } else if (result.result != null && result.result == "not_exists") {
                changeMsg("没有找到这篇游记!");
                setTimeout(function() {
                    return window.location.href = "/lvxbang/recommendPlan/index.jhtml";
                },3000);
            } else {
                loadData(result);
                TravelNote.bindScrollEvent();
            }
        },
        error: function(result) {
            // 发送错误时候关闭窗口
            changeMsg("没有找到这篇游记!");
            setTimeout(function() {
                var userAgent = navigator.userAgent;
                if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Presto") != -1) {
                    window.location.replace("about:blank");
                } else {
                    window.opener = null;
                    window.open("", "_self");
                    window.close();
                }
            },3000);
        }
    });
});

function loadData(data) {
    $.each(data.recommendPlanDays, function(day_index, day) {
        day['day_index'] = day_index;
        var left_day_item = template("left_day_item", day);
        var right_day_item = template("right_day_item", day);
        $("#travel_list").append(left_day_item);
        $("#travel_index").append(right_day_item);
        $.each(day.recommendPlanTrips, function(trip_index, trip) {
            trip['day_index'] = day_index;
            trip['trip_index'] = trip_index;
            var left_trip_item = template("left_trip_item" ,trip);
            var right_trip_item = template("right_trip_item", trip);
            $("#left_d" + day_index + "_trip_list").append(left_trip_item);
            $("#right_d" + day_index + "_trip_list").append(right_trip_item);
            // 加载照片
            $.each(trip.photos, function (i, photo) {
                var coverImg = trip.coverImg;
                if (coverImg != photo.photoLarge) {
                    var photo_data = {
                        imgAddr: photo.photoLarge
                    };
                    if (photo.width > 0 && photo.height > 0) {
                        photo_data['height'] = (680 / photo.width) * photo.height;
                    }
                    var left_photo_item = template("left_photo_item", photo_data);
                    $("#left_d" + day_index + "t" + trip_index + "_photo_list").append(left_photo_item);
                } else {
                    // 加载封面
                    var cover_data = {
                        imgAddr: coverImg
                    };
                    var left_cover_item = template("left_photo_item", cover_data);
                    $("#left_d" + day_index + "t" + trip_index + "_photo_list").prepend(left_cover_item);
                }
                // 应用图片懒加载
                $(".img").lazyload({
                    effect: "fadeIn"
                });
            });
        });
    });
    closeMsgBox();
}
