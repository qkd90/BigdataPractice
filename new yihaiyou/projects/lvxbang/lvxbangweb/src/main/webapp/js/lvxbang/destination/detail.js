/**
 * Created by huangpeijie on 2016-01-15,0015.
 */
function more(a) {
    $("#more-cityName").val($(a).attr("cityName"));
    $("#more-cityCode").val($(a).attr("cityCode"));
    $("#more-cityId").val($(a).attr("cityCode"));
    $("#more-form").attr("action", $(a).attr("url")).submit();
}

var Destination = {
    init: function () {

    }
};

$(function () {
    Destination.init();
});

