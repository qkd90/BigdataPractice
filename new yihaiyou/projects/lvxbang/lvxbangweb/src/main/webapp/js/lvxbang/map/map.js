var LvxbangMap = {
    defaults: {
        container: "baidu-map-main",
        scenicMark: "tpl-map-plan-trip",
        outerContainer: ".map-container"
    },
    container: null,
    outerContainer: null,
    markerTemplate: null,
    map: null,
    mask: "#map-mask",
    centerPoint: new BMap.Point(0, 0),
    locationLevel: {
        big: 13,
        small: 13
    },
    markerColor: ['green', 'indigo', 'blue', 'purple', 'pink', 'red', 'orange'],
    lineColor: ['#00bf6b', '#12cfd6', '#57b3ff', '#b184f1', '#fd5cbd', '#ff6471', '#fd9d5d'],
    markers: {},
    lines: {},
    controllers: [
        new BMap.NavigationControl(),   // 添加平移缩放控件
        new BMap.ScaleControl(),        // 添加比例尺控件
        new BMap.OverviewMapControl()   //添加缩略地图控件
    ],
    init: function (options) {
        options = $.extend(LvxbangMap.defaults, options);
        LvxbangMap.container = $("#" + options.container);
        LvxbangMap.outerContainer = $(options.outerContainer);
        LvxbangMap.markerTemplate = options.scenicMark;
        LvxbangMap.map = new BMap.Map(options.container,{enableMapClick:false});
        var point = new BMap.Point(0, 0);//定义一个中心点坐标
        LvxbangMap.map.centerAndZoom(point, 17);
        LvxbangMap.map.enableScrollWheelZoom();
        LvxbangMap.outerContainer.find(".to-bigger-button").click(function () {
            LvxbangMap.toBigger();
        }).show();
        LvxbangMap.outerContainer.find(".close").click(function () {
            LvxbangMap.toSmaller();
        });
        LvxbangMap.map.setMapStyle({
            styleJson:[
                {
                    "featureType": "poi",
                    "elementType": "all",
                    "stylers": {
                        "color": "#ffffff",
                        "visibility": "off"
                    }
                },
                {
                    "featureType": "administrative",
                    "elementType": "all",
                    "stylers": {
                        "color": "#ffffff",
                        "visibility": "off"
                    }
                }
            ]
        });
    },
    clear: function () {
        LvxbangMap.map.clearOverlays();
        LvxbangMap.markers = [];
        LvxbangMap.lines = [];
    },
    bindEvent: function () {
        LvxbangMap.container.find(".location").click(function () {
            if (!$(this).parents("#baidu-map-main").hasClass("bigger")) {
                return;
            }
            LvxbangMap.container.find(".Pop_ups").hide();
            LvxbangMap.container.find(".location").removeClass("on");
            LvxbangMap.container.find(".map_plan_day").each(function () {
                var marker = $(this).parent();
                if (parseInt(marker.css("z-index")) > 0) {
                    marker.css("z-index", -parseInt(marker.css("z-index")));
                }
            });
            $(this).addClass("on");
            $(this).parent().find('.Pop_ups').show();
            $(this).parents(".map_plan_day").parent().css("z-index", -parseInt($(this).parents(".map_plan_day").parent().css("z-index")));
        });
        LvxbangMap.container.find(".Pop_ups .closex").click(function () {
            $(this).closest('.Pop_ups').hide();
            $(this).removeClass("on");
        });
        LvxbangMap.container.find(".map_plan_day").each(function () {
            var panel = $(this);
            panel.find(".jumpTo").click(function () {
                $("#jumpToUrl").attr("href", panel.data("url")).get(0).click();
            });
        });
    },
    withOption: function (option) {
        return this;
    },
    addAScenic: function (scenic, day) {
        if (!day) {
            scenic.color = [LvxbangMap.markerColor[0]];
        }
        scenic.color = LvxbangMap.markerColor[day % 7];
        var label = template(LvxbangMap.markerTemplate, scenic);
        var marker = new BMapLib.RichMarker(label, new BMap.Point(scenic.lng, scenic.lat),
            {
                "anchor": new BMap.Size(-15, -35)
            }
        );

        if (!LvxbangMap.markers[day]) {
            LvxbangMap.markers[day] = [];
        }
        LvxbangMap.markers[day].push(marker);
        LvxbangMap.map.addOverlay(marker);
        label = $(marker.getContent());
        label.find(".location").click(function () {
            label.find('.Pop_ups').show();
        });
        label.find(".Pop_ups .closex").click( function () {
            label.find('.Pop_ups').hide();
        });
    },
    showAScenic: function (id) {
        $(".location_" + id).click();
    },
    drawLines: function () {
        $.each(LvxbangMap.markers, function (day) {
            LvxbangMap.drawALine(day);
        });
        //LvxbangMap.container.find("div>div>div").eq(3).children().css("position", "relative");
    },
    showDay: function (day) {
        $(".map_plan_day").hide();
        $(".map_plan_day_" + day).show();
        var dayIndex = day - 1;
        for (var i in LvxbangMap.lines) {
            if (i == dayIndex) {
                $.each(LvxbangMap.lines[i], function (index, line) {
                    LvxbangMap.map.addOverlay(line);
                });
                continue;
            }
            $.each(LvxbangMap.lines[i], function (index, line) {
                LvxbangMap.map.removeOverlay(line);
            });
        }
        LvxbangMap.outerContainer.removeClass("whole-map").addClass("day-map");
        LvxbangMap.locationToDay(dayIndex);
    },
    showAllLine: function () {
        for (var day in LvxbangMap.lines) {
            $.each(LvxbangMap.lines[day], function (index, line) {
                LvxbangMap.map.addOverlay(line);
            });
        }
        $(".map_plan_day").show();
        var lng = 0;
        var lat = 0;
        var maxLng = 0;
        var minLng = 150;
        var maxLat = 0;
        var minLat = 90;
        for (var dayIndex in LvxbangMap.markers) {
            $.each(LvxbangMap.markers[dayIndex], function (index, data) {
                var point = data.getPosition();
                if (maxLng < point.lng) {
                    maxLng = point.lng;
                }
                if (minLng > point.lng) {
                    minLng = point.lng;
                }
                if (maxLat < point.lat) {
                    maxLat = point.lat;
                }
                if (minLat > point.lat) {
                    minLat = point.lat;
                }
                lng += point.lng;
                lat += point.lat;
            });
        }
        LvxbangMap.centerPoint = new BMap.Point((maxLng + minLng) / 2, (maxLat + minLat) / 2);
        var bigZoom = parseInt(Math.max((maxLng - minLng) / 0.34, (maxLat - minLat) / (0.17)));
        var smallZoom = parseInt(Math.max((maxLng - minLng) / 0.085, (maxLat - minLat) / (0.1)));
        if (bigZoom>2) {
            bigZoom = 1 + parseInt(Math.sqrt(bigZoom));
        }
        if (smallZoom>2) {
            smallZoom = 1 + parseInt(Math.sqrt(smallZoom));
        }
        LvxbangMap.locationLevel.big = 13 - bigZoom;
        LvxbangMap.locationLevel.small = 13 - smallZoom;
        LvxbangMap.outerContainer.removeClass("day-map").addClass("whole-map");
        if (LvxbangMap.outerContainer.hasClass("bigger")) {
            LvxbangMap.locationBig();
        } else {
            LvxbangMap.locationSmall();
        }
    },
    drawALine: function (day) {
        var points = [];
        $.each(LvxbangMap.markers[day], function (index, data) {
            points.push(data.getPosition());
        });
        var line = new BMap.Polyline(points, {strokeColor: LvxbangMap.lineColor[day], strokeWeight: 2, strokeOpacity: 1});
        if (!LvxbangMap.lines[day]) {
            LvxbangMap.lines[day] = [];
        }
        LvxbangMap.lines[day].push(line);
        LvxbangMap.map.addOverlay(line);
    },
    location: function (lng, lat, level) {
        if (lng && lat) {
            LvxbangMap.centerPoint = new BMap.Point(lng, lat);
        }
        if (LvxbangMap.outerContainer.hasClass("bigger")) {
            if (level) {
                LvxbangMap.locationLevel.big = level;
            }
            LvxbangMap.locationBig(lng, lat, level);
        } else {
            if (level) {
                LvxbangMap.locationLevel.small = level;
            }
            LvxbangMap.locationSmall(lng, lat, level);
        }

    },
    locationBig: function () {
        LvxbangMap.map.centerAndZoom(LvxbangMap.centerPoint, LvxbangMap.locationLevel.big);
    },
    locationSmall: function () {
        LvxbangMap.map.centerAndZoom(LvxbangMap.centerPoint, LvxbangMap.locationLevel.small);
    },
    locationToDay: function (dayIndex) {
        var lng = 0;
        var lat = 0;
        var maxLng = 0;
        var minLng = 150;
        var maxLat = 0;
        var minLat = 90;
        $.each(LvxbangMap.markers[dayIndex], function (index, data) {
            var point = data.getPosition();
            if (maxLng < point.lng) {
                maxLng = point.lng;
            }
            if (minLng > point.lng) {
                minLng = point.lng;
            }
            if (maxLat < point.lat) {
                maxLat = point.lat;
            }
            if (minLat > point.lat) {
                minLat = point.lat;
            }
            lng += point.lng;
            lat += point.lat;
        });
        LvxbangMap.centerPoint = new BMap.Point((maxLng + minLng) / 2, (maxLat + minLat) / 2);
        LvxbangMap.locationLevel.big = 13 - parseInt(Math.max((maxLng - minLng) / 0.34, (maxLat - minLat) / (0.17)));
        LvxbangMap.locationLevel.small = 13 - parseInt(Math.max((maxLng - minLng) / 0.085, (maxLat - minLat) / (0.1)));
        if (LvxbangMap.outerContainer.hasClass("bigger")) {
            LvxbangMap.locationBig();
        } else {
            LvxbangMap.locationSmall();
        }
    },
    toBigger: function () {
        $(LvxbangMap.mask).hide();
        LvxbangMap.outerContainer.fadeOut(1, function () {
            $(this).addClass("bigger").fadeIn(500, function () {
                LvxbangMap.location();
            });
            LvxbangMap.container.addClass("bigger");
            $("#msg_box").addClass("open");
            $.each(LvxbangMap.controllers, function (index, controller) {
                LvxbangMap.map.addControl(controller);
            });
        });
    },
    toSmaller: function () {
        LvxbangMap.outerContainer.fadeOut(500, function () {
            $(this).removeClass("bigger").fadeIn(500, function () {
                LvxbangMap.location();
            });
            LvxbangMap.container.find(".Pop_ups").hide();
            LvxbangMap.container.find(".location").removeClass("on");
            LvxbangMap.container.removeClass("bigger");
            $("#msg_box").removeClass("open");
        });
        $.each(LvxbangMap.controllers, function (index, controller) {
            LvxbangMap.map.removeControl(controller);
        });

    }
};