/**
 * Created by zzl on 2016/1/6.
 */

//相关游记公共跳转方法
function goRecommendPlanList(relatedId, type, name) {
    if (relatedId == null || relatedId == '' ||
        type == null || type == '' || name == null || name == '') {
        return;
    } else {
        name = encodeURI(encodeURI(name));
        window.location.href = $('#recommendplan_path').val() + "/guide_list.html?relatedId=" + relatedId + "&type=" + type + "&name=" + name;
    }

}

// 通用详情页跳转方法
function goDetail(type, targetId) {
    if (type == 1) {
        window.open('/scenic_detail_' + targetId + ".html");
    } else if (type == 2) {
        window.open('/food_detail_' + targetId + ".html");
    } else if (type == 3) {
        window.open('/hotel_detail_' + targetId + ".html");
    } else if (type == 4) {
        // 交通
    } else if (type == 5) {
        // 其他
    }
}

// 城市名搜索城市id,type为1表示直辖市取省级
function getCityId(nameStr, type) {
    var cityId = 0;
    if (isNull(nameStr)) {
        return cityId;
    }
    // if (type == 1 && nameStr.charAt(nameStr.length - 1) == "站") {
    //     nameStr = nameStr.substring(0, nameStr.length - 1);
    // }
    $.ajax({
        url: "/lvxbang/destination/getAreaIds.jhtml",
        data: {"nameStr": nameStr, "type": type},
        async: false,
        type: "post",
        dataType: "json",
        success: function (data) {
            if (data[0].length > 0) {
                cityId = data[0][0];
            }
        }
    });
    return cityId;
}

// 城市名搜索城市id和站点id，type为1表示火车，为2表示飞机
function getCityAndPortId(nameStr, type) {
    var result = [];
    result[0] = 0;
    result[1] = "";
    if (isNull(nameStr)) {
        return result;
    }
    result[0] = getCityId(nameStr);
    if (result[0] != 0) {
        return result;
    }
    // if (type == 1 && nameStr.charAt(nameStr.length - 1) == "站") {
    //     nameStr = nameStr.substring(0, nameStr.length - 1);
    // }
    if (type == 2) {
        nameStr = nameStr.replace(/\s+/g, ' ');
    }
    $.ajax({
        url: "/lvxbang/traffic/getTransPort.jhtml",
        data: {"nameStr": nameStr, "type": type},
        async: false,
        type: "post",
        success: function (data) {
            if (data.length > 0) {
                result[0] = data[0];
                result[1] = data[1];
            }
        }
    });
    return result;
}

