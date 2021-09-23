/**
 * Created by huangpeijie on 2016-01-18,0018.
 */
function orderClick(sort) {
    if ($(sort).hasClass("checked")) {
        if ($(sort).attr("orderType") == "desc") {
            $(sort).attr("orderType", "asc").removeClass("desc").addClass("asc");
        }
        else {
            $(sort).attr("orderType", "desc").removeClass("asc").addClass("desc");
        }
    }
    else {
        $(".select_list").find(".checked").removeClass("checked");
        $(sort).addClass("checked");
    }
}