/**
 * Created by vacuity on 16/1/27.
 */


var TrafficCookie = {
    flightCookieName: "flightCookie",
    trainCookieName: "trainCookie",

    saveTrafficCookie: function (module, type) {
        var trafficSearch = {
            searchType: null,
            searchTime: null,
            stayTime: null,
            leaveDate: null,
            returnDate: null,
            leaveCity: null,
            leaveCityName: null,
            arriveCity: null,
            arriveCityName: null,
            transitCity: null,
            transitCityName: null
        };
        trafficSearch.searchType = module;
        trafficSearch.searchTime = new Date().getTime();
        trafficSearch.leaveDate = $("#leaveDate-" + module).val();
        trafficSearch.returnDate = $("#returnDate-" + module).val();
        trafficSearch.leaveCity = $("#leaveCity-" + module).val();
        trafficSearch.leaveCityName = $("#leaveCityName-" + module).val();
        trafficSearch.arriveCity = $("#arriveCity-" + module).val();
        trafficSearch.arriveCityName = $("#arriveCityName-" + module).val();
        trafficSearch.transitCity = $("#transitCity").val();
        trafficSearch.transitCityName = $("#transitCityName").val();


        var trafficString = getCookie(type);
        var trafficList = JSON.parse(decodeURI(trafficString));
        if (trafficList == null) {
            trafficList = new Array();
        }
        var length = trafficList.length;
        if (length > 3) {
            trafficList.splice(0, length - 3);
        }
        trafficList.push(trafficSearch);
        trafficString = JSON.stringify(trafficList);
        if (!isNull(trafficSearch.leaveCity) && !isNull(trafficSearch.arriveCity) && !isNull(trafficSearch.leaveDate)) {
            setCookie(type, encodeURI(trafficString));
        }
    },

    getTrafficCookie: function (type, id) {
        var trafficString = getCookie(type);
        var trafficList = JSON.parse(decodeURI(trafficString));
        if (trafficList == null)
            return;
        var length = trafficList.length;
        var date = new Date();
        var html = "";
        if (length < 2) {
            return;
        }
        for (var i = length - 2; i >= 0; i--) {
            //
            var search = trafficList[i];
            search.index = i;
            var time = date.getTime() - search.searchTime;
            var day = Math.floor(time / (24 * 60 * 60 * 1000));
            time -= day * (24 * 60 * 60 * 1000);
            var hour = Math.floor(time / (60 * 60 * 1000));
            time -= hour * (60 * 60 * 1000);
            var minute = Math.floor(time / (60 * 1000));
            var stayTime = "";
            if (day > 0) {
                stayTime += day + "天";
            } else {
                if (hour > 0) {
                    stayTime += hour + "小时";
                } else {
                    if (minute > 0) {
                        stayTime += minute + "分钟";
                    }
                }
            }
            if (stayTime == "") {
                stayTime = "刚刚查询";
            } else {
                stayTime += "前查询";
            }
            search.stayTime = stayTime;


            html += template("tpl-traffic-search", search);

        }
        $("#" + id).html(html);
    },

    historySearch: function (index) {
        $("#history-form-" + index).submit();
    }
};
