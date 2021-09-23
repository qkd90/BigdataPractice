var PlanEditor = {
    optimizing: false,
    optimizeResult: null,
    failedScenic: null,
    filledOptimizeResult: null,
    plan: {},
    recycleBin: {},
    filledRecycleBin: {},
    init: function () {
        PlanEditor.bindEvent();

        PlanEditor.getCookie();

        var planId = $("#planId").val();
        if (!isNull(planId)) {
            PlanEditor.fillWithPlan();
        } else {
            if (!isNull(PlanEditor.optimizeResult)) {
                PlanEditor.renderPage();
            }
        }
        PlanEditor.intDragable();
        LvxbangMap.init();
    },

    renderPage: function () {
        $.post("/lvxbang/plan/fillDetail.jhtml", {json: JSON.stringify(PlanEditor.optimizeResult)}, function (result) {
            PlanEditor.filledOptimizeResult = result;
            PlanEditor.renderCity().renderAdded().renderRemoved().renderFailed().renderData().sortable();
            if (PlanEditor.plan.status == 2) {
                if (FloatEditor.plan.city) {
                    FloatEditor.editor.find(".cart_stock_ul").html("");
                    var cityIds = [];
                    for (var cityId in FloatEditor.plan.city) {
                        cityIds.push(cityId);
                    }
                    if (cityIds.length == 0) {
                        return;
                    }
                    $.getJSON("/lvxbang/area/getAreaByIds.jhtml", {idStr: cityIds.join(",")}, function (result) {

                        var cityList = [];
                        var totalCount = 0;
                        var cityIds = "";
                        $.each(result, function (index, city) {
                            city.count = parseInt(PlanEditor.plan.city[city.id]);
                            cityList.push(city);
                            totalCount += city.count;
                            cityIds += city.id + ",";
                        });
                        $(".Travel_e_c").html(template("tpl-optimized-city-panel", {"cityList": cityList, "total": totalCount}));
                        cityIds = cityIds.substr(0, cityIds.length - 1);
                        return this;
                    });
                }
            }
        },"json");
    },

    bindEvent: function () {
        $("img").lazyload({
            effect: "fadeIn"
        });

        //搜索框
        $(".categories .input").click(function () {
            $(this).next(".categories_div").show();
        });

        $(".categories_div li").click(function () {
            var label = $("label", this).text();
            $(this).closest(".categories").children(".input").val(label);
        });

        $('.categories  .input').on('click', function (event) {
            // 阻止冒泡
            if (event.stopPropagation) {    // standard
                event.stopPropagation();
            } else {
                // IE6-8
                event.cancelBubble = true;
            }
        });
        $(document).on("click", function () {
            $(".categories_div").hide();
        });

        //加减
        $(".Travel_e_c").delegate(".minus", "click", function () {
            if(PlanEditor.optimizing) {
                return;
            }
            var n = $(this).siblings().children(".quantity").val();
            var nn = $.trim(n);
            var num = parseInt(n) - 1;
            if (num == 0) {
                if ($(this).parents(".Travel_e_c_ul").find("li:visible").length == 1) {
                    promptWarn("线路仅剩一天，无法删除！");
                    return;
                }
                var msg = "减少天数将删除该天，确定删除？";
                deleteWarn(msg ,function(node) {
                    var parnet = node.parents("li");
                    var scenicList = [];
                    $.each(PlanEditor.optimizeResult.data, function (i, data) {
                        if (parnet.data("id") != data.city.id) {
                            $.each(data.tripList, function (j, trip) {
                                var tripNode = {};
                                tripNode.id = trip.id;
                                scenicList.push(tripNode);
                            });
                        }
                    });
                    PlanEditor.plan.scenicList = scenicList;
                    parnet.remove();
                    PlanEditor.optimize();
                }, $(this));
            } else {
                if (nn == "" || nn == null) {
                    num = 1;
                }
                $(this).siblings().children(".quantity").val(num);
                PlanEditor.optimize();
            }
        }).delegate(".plus", "click", function () {
            if(PlanEditor.optimizing) {
                return;
            }
            var n = $(this).siblings().children(".quantity").val();
            var nn = $.trim(n);
            var num = parseInt(n) + 1;
            if (num == 0 || nn == "" || nn == null) {
                num = 1;
            }
            $(this).siblings().children(".quantity").val(num);
            PlanEditor.optimize();
        }).delegate(".quantity", "blur", function () {
            if(PlanEditor.optimizing) {
                return;
            }
            if ($(this).val() == "") {
                $(this).val(1);
            }
            PlanEditor.optimize();
        });
        $(".save-plan").click(function () {
            PlanEditor.savePlan();
        });
        $(".booking-plan").click(function () {
            $("#plan-booking-loading-cover").show();
            var plan = {};
            plan.planDayList = [];
            var cityCount = {};
            $.each(PlanEditor.filledOptimizeResult.data, function (index, day) {
                if (day.tripList.length == 0) {
                    return;
                }
                if (cityCount[day.city.id] == null){
                    cityCount[day.city.id] = 0;
                }
                cityCount[day.city.id]++;
            });
            $.each(PlanEditor.filledOptimizeResult.data, function (index, day) {
                if (day.tripList.length==0) {
                    return;
                }
                var planDay = {};
                planDay.city = {
                    id: day.city.id,
                    count: cityCount[day.city.id],
                    name: day.city.name
                };
                planDay.planTripList = [];
                $.each(day.tripList, function (tripIndex, trip) {
                    var planTrip = {
                        tripType: trip.type,
                        scenicInfo: {
                            id: trip.id,
                            ranking: trip.ranking,
                            score: trip.score,
                            name: trip.name
                        }
                    };
                    planDay.planTripList.push(planTrip);
                });
                plan.planDayList.push(planDay);
            });
            PlanEditor.saveCookie();
            $("#booking-form-json").val(JSON.stringify(plan));
            $("#booking-form").submit();
        });
        //删除按钮
        $(".Travel_exercise_div_b").delegate(".Travel_exe_d_d .close", "mousedown", function (e) {
            $(this).parents("ul").sortable("disable");
            if (e.stopPropagation) {    // standard
                e.stopPropagation();
            } else {
                // IE6-8
                e.cancelBubble = true;
            }
            $(this).mouseout(function () {
                $(this).parents("ul").sortable("enable");
            });
        });
        $(".Travel_exercise_div_b").delegate(".Travel_exe_d_d .close", "click", function () {
            var node = $(this).parent();
            //整个行程只剩一个景点时提示不能删除
            if ($(".Travel_exercise_div_b").find("li:visible").length == 1) {
                promptWarn("最后一个景点无法删除！");
                return;
            }
            var flag = false;
            $(".Travel_e_b_nr_p a").each(function () {
                if (node.attr("tid") == $(this).attr("data-id")) {
                    $(this).click();
                    flag = true;
                }
            });
            if (!flag) {
                //删除该天最后一个景点时提示是否删除
                if ($(this).parents(".Travel_exe_d_d").find("li:visible").length == 1) {
                    var msg = "删除该景点后当天行程为空！是否删除该景点？";
                    deleteWarn(msg, deleteScenic, node);
                } else {
                    deleteScenic(node);
                }
            }

            function deleteScenic(node){
                $("#removed-scenic-panel .Travel_e_b_nr_p").append("<a href='javascript:;' data-id='" + node.attr("tid") + "' style='display: none;'><i class='close'></i>" + node.attr("tname") + "</a>");
                $("#removed-scenic-panel").show();
                $("#removed-scenic-panel .Travel_e_b_nr_p a:last").click().show();
            }
        });
        //景点隐藏显示
        $(".Travel_e_b").delegate('a', 'click', function () {
            var scenicId = $(this).data("id");
            var status = $(this).data("status");
            //智能排序删除的景点
            if ($(this).hasClass("auto_removed")) {
                var toIds = "";
                $.each(PlanEditor.optimizeResult.data, function (dayIndex, day) {
                    $.each(day.tripList, function(i, trip) {
                        if (toIds.length > 0) {
                            toIds += ","
                        }
                        toIds += trip.id;
                    });
                });
                $.post("/lvxbang/scenic/getNearScenic.jhtml", {fromId: scenicId, toIds: toIds}, function(result) {
                    var flag = false;
                    $.each(PlanEditor.optimizeResult.data, function (dayIndex, day) {
                        $.each(day.tripList, function(i, trip) {
                            if (trip.id == result[0].id) {
                                var newTrip = {};
                                newTrip.id = scenicId;
                                day.tripList.splice(i + 1, 0, newTrip);
                                var newFilledTrip = result[1];
                                PlanEditor.filledOptimizeResult.data[dayIndex].tripList.splice(i + 1, 0, newFilledTrip);
                                var dayDiv = $(".Travel_exercise_div_b").find(".Travel_exe_d").eq(dayIndex);
                                var node = dayDiv.find("li").eq(i);
                                var newNode = $(template("tpl-optimized-result-trip", result[1]));
                                node.after(newNode);
                                if (dayDiv.find("li:visible").length == 0){
                                    dayDiv.show();
                                }
                                flag = true;
                                return false;
                            }
                        });
                        if (flag) {
                            return false;
                        }
                    });
                }, "json");
                $(this).remove();
                if ($("#removed-scenic-panel .Travel_e_b_nr_p").children().length == 0){
                    $("#removed-scenic-panel").hide();
                }
            }
            //添加
            else if (status == 1) {
                var trash = PlanEditor.recycleBin[scenicId];
                $.each(PlanEditor.optimizeResult.data, function (dayIndex, day) {
                    if (dayIndex != trash.day) {
                        return;
                    }
                    day.tripList.splice(trash.index, 0, trash.trip);
                    PlanEditor.recycleBin[scenicId] = null;
                    var filledTrash = PlanEditor.filledRecycleBin[scenicId];
                    PlanEditor.filledOptimizeResult.data[dayIndex].tripList.splice(trash.index, 0, filledTrash.trip);
                    PlanEditor.filledRecycleBin[scenicId] = null;
                    //添加的景点所在天原本是隐藏的情况
                    var dayDiv = $(".Travel_exercise_div_b").find(".Travel_exe_d").eq(dayIndex);
                    if (dayDiv.find("li:visible").length == 0){
                        dayDiv.show();
                        var cityId = dayDiv.attr("data-city-id");
                        $(".Travel_e_c_ul li").each(function() {
                            if ($(this).attr("data-id") == cityId) {
                                var quantity = $(this).find(".quantity");
                                quantity.val(parseInt(quantity.val()) + 1);
                                $(this).show();
                            }
                        });
                        //修改该天之后的天数
                        var next = dayDiv.next();
                        while(next.length != 0){
                            if (next.attr("data-city-id") == cityId) {
                                var number = parseInt(next.find(".Travel_exe_d_f").attr("data-day")) + 1;
                                next.find(".Travel_exe_d_f").attr("data-day", number);
                                next.find(".Travel_exe_d_f span").eq(0).text(number);
                            }
                            next = next.next();
                        }
                        //总天数和城市显示
                        var total = $("#total");
                        total.text(parseInt(total.text()) + 1);
                        var cityNames = "";
                        $(".Travel_e_c_ul li:visible").each(function() {
                            if (cityNames.length > 0) {
                                cityNames += "、";
                            }
                            cityNames += $(this).find(".name").text();
                        });
                        $("#cityNames").text(cityNames);
                    }
                    //显示添加的景点
                    dayDiv.find("li").each(function() {
                        if ($(this).attr("tid") == scenicId) {
                            $(this).show();
                        }
                    });
                });
                if($(this).parents("#removed-scenic-panel").length > 0){
                    $(this).remove();
                } else {
                    $(this).removeClass("checked").data("status", 0);
                }
                if ($("#removed-scenic-panel .Travel_e_b_nr_p").children().length == 0){
                    $("#removed-scenic-panel").hide();
                }
            } else { //删除
                //整个行程只剩一个景点时提示不能删除
                if ($(".Travel_exercise_div_b").find("li:visible").length == 1) {
                    promptWarn("最后一个景点无法删除");
                    return;
                }
                //当天最后一个景点删除是弹窗提示
                if ($(this).parents("#added-scenic-panel").length > 0 && $(".Travel_exercise_div_b").find(".Travel_exe_d li[tid="+ scenicId +"]").parent().find("li:visible").length == 1){
                    deleteWarn("删除该景点后当天行程为空！是否删除该景点？", deleteScenic, $(this));
                } else {
                    deleteScenic($(this));
                }
            }

            function deleteScenic(thiz) {
                var scenicId = thiz.data("id");
                $.each(PlanEditor.optimizeResult.data, function (dayIndex, day) {
                    $.each(day.tripList, function(tripIndex, trip) {
                        if (trip.id == scenicId) {
                            var trash = {};
                            trash.trip = trip;
                            trash.day = dayIndex;
                            trash.index = tripIndex;
                            PlanEditor.recycleBin[scenicId] = trash;
                            day.tripList.splice(tripIndex, 1);
                            var filledTrash = {};
                            filledTrash.day = dayIndex;
                            filledTrash.index = tripIndex;
                            filledTrash.trip = PlanEditor.filledOptimizeResult.data[dayIndex].tripList[tripIndex];
                            PlanEditor.filledRecycleBin[scenicId] = filledTrash;
                            PlanEditor.filledOptimizeResult.data[dayIndex].tripList.splice(tripIndex, 1);
                            var dayDiv = $(".Travel_exercise_div_b").find(".Travel_exe_d").eq(dayIndex);
                            //隐藏删除的景点
                            dayDiv.find("li").each(function() {
                                if ($(this).attr("tid") == scenicId) {
                                    $(this).hide();
                                }
                            });
                            //删除景点后当天景点为空
                            if (dayDiv.find("li:visible").length == 0){
                                dayDiv.hide();
                                var cityId = dayDiv.attr("data-city-id");
                                //对应城市天数修改
                                $(".Travel_e_c_ul li").each(function() {
                                    if ($(this).attr("data-id") == cityId) {
                                        var quantity = $(this).find(".quantity");
                                        quantity.val(parseInt(quantity.val()) - 1);
                                        if (parseInt(quantity.val()) == 0) {
                                            $(this).hide();
                                            var scenicList = [];
                                            $.each(PlanEditor.optimizeResult.data, function (i, data) {
                                                if (cityId != data.city.id) {
                                                    $.each(data.tripList, function (j, trip) {
                                                        var tripNode = {};
                                                        tripNode.id = trip.id;
                                                        scenicList.push(tripNode);
                                                    });
                                                }
                                            });
                                            PlanEditor.plan.scenicList = scenicList;
                                        }
                                    }
                                });
                                //修改该天之后的天数
                                var next = dayDiv.next();
                                while(next.length != 0){
                                    if (next.attr("data-city-id") == cityId) {
                                        var number = parseInt(next.find(".Travel_exe_d_f").attr("data-day")) - 1;
                                        next.find(".Travel_exe_d_f").attr("data-day", number);
                                        next.find(".Travel_exe_d_f span").eq(0).text(number);
                                    }
                                    next = next.next();
                                }
                                //总天数和城市显示
                                var total = $("#total");
                                total.text(parseInt(total.text()) - 1);
                                var cityNames = "";
                                $(".Travel_e_c_ul li:visible").each(function() {
                                    if (cityNames.length > 0) {
                                        cityNames += "、";
                                    }
                                    cityNames += $(this).find(".name").text();
                                });
                                $("#cityNames").text(cityNames);
                            }
                            return false;
                        }
                    })
                });
                thiz.addClass("checked").data("status", 1);
                PlanEditor.initChangeDay();
                PlanEditor.initMap();
            }
            PlanEditor.initChangeDay();
            PlanEditor.initMap();
        });
        //添加更多景点时保存行程编辑状态
        $(".Travel_e_c").delegate(".Travel_e_b_nr_a", "click", function () {
            var cityIdStr = "";
            var cityCount = {};
            var scenicList = [];
            var k = 0;
            $.each(PlanEditor.optimizeResult.data, function (i, day) {
                if (day.tripList.length == 0) {
                    return;
                }
                if (cityCount[day.city.id] == null){
                    cityCount[day.city.id] = 0;
                }
                cityCount[day.city.id]++;
                $.each(day.tripList, function (j, trip) {
                    var scenic = {};
                    scenic.id = trip.id;
                    scenicList[k] = scenic;
                    k++;
                });
            });
            for (var city in cityCount) {
                if (cityIdStr.length > 0) {
                    cityIdStr += ",";
                }
                cityIdStr += city;
            }
            FloatEditor.plan.city = cityCount;
            FloatEditor.plan.scenicList = scenicList;
            FloatEditor.optimizeResult = PlanEditor.optimizeResult;
            FloatEditor.failedScenic = null;
            FloatEditor.saveCookie();
            window.location.href = $(this).data("url") + cityIdStr;
        });
    },

    renderCity: function () {
        var cityList = [];
        var totalCount = PlanEditor.filledOptimizeResult.data.length;
        $.each(PlanEditor.filledOptimizeResult.data,function (i, data) {
        //PlanEditor.filledOptimizeResult.data.forEach(function (data) {
            var added = false;
            //cityList.forEach(function (city) {
            $.each(cityList,function (i, city) {
                if (city.id == data.city.id) {
                    city.count += 1;
                    added = true;
                }
            });
            if (!added) {
                var newCity = data.city;
                newCity.count = 1;
                cityList.push(newCity);
            }
        });
        var planId = $("#planId").val();
        $(".Travel_e_c").html(template("tpl-optimized-city-panel", {"cityList": cityList, "total": totalCount, "planId":planId}));
        var cityIds = "";
        $.each(cityList, function (index, data) {
            cityIds += data.id + ",";
        });
        cityIds = cityIds.substr(0, cityIds.length - 1);
        return this;
    },

    renderAdded: function () {
        if (PlanEditor.filledOptimizeResult.addNodes.length != 0) {
            var html = "";
            //PlanEditor.filledOptimizeResult.addNodes.forEach(function (data) {
            $.each(PlanEditor.filledOptimizeResult.addNodes,function (i,data) {
                html += template("tpl-optimized-added-item", data);
            });
            $("#added-scenic-panel").show().find(".Travel_e_b_nr_p").html(html);
        } else {
            $("#added-scenic-panel").hide();
        }
        return this;
    },

    renderRemoved: function () {
        if (PlanEditor.filledOptimizeResult.removedNode.length != 0) {
            var html = "";
            //PlanEditor.filledOptimizeResult.removedNode.forEach(function (data) {
            $.each(PlanEditor.filledOptimizeResult.removedNode,function (i,data) {
                html += template("tpl-optimized-removed-item", data);
            });
            $("#removed-scenic-panel").show().find(".Travel_e_b_nr_p").html(html);
        } else {
            $("#removed-scenic-panel").hide();
        }
        return this;
    },

    renderFailed: function () {
        if(PlanEditor.failedScenic != null && PlanEditor.failedScenic.length != 0) {
            var html = "";
            //PlanEditor.failedScenic.forEach(function (data) {
            $.each(PlanEditor.failedScenic,function (i, data) {
               html += data + "；   ";
            });
            $("#failed-scenic-panel").find("label").text("此" + PlanEditor.failedScenic.length + "项不匹配，暂不能复制：");
            $("#failed-scenic-panel").show().find(".Travel_e_f_nr").html(html);
        } else {
            $("#failed-scenic-panel").hide();
        }
        return this;
    },

    renderData: function () {
        var html = "";
        $("#edit-tip").show();
        //PlanEditor.filledOptimizeResult.data.forEach(function (data) {
        $.each(PlanEditor.filledOptimizeResult.data, function (i, data) {
            html += template("tpl-optimized-result-day", data);
        });
        $(".Travel_exercise_div_b").html(html).find("img").lazyload({
            effect: "fadeIn"
        });

        $(".save-plan,.booking-plan").show();

        $(".Travel_exe_d_f_up").on("click", function (e) {
            if (e.stopPropagation) {    // standard
                e.stopPropagation();
            } else {
                // IE6-8
                e.cancelBubble = true;
            }
            var dayNode1 = $(this).parents(".Travel_exe_d");
            var index = dayNode1.index();
            var dayNode2 = dayNode1.prev();
            var i = 1;
            while (dayNode2.is(":hidden")) {
                dayNode2 = dayNode2.prev();
                i++;
            }
            if (dayNode1.data("city-id") != dayNode2.data("city-id")) {
                return;
            }
            var tripList1 = PlanEditor.optimizeResult.data[index - i].tripList;
            PlanEditor.optimizeResult.data[index - i].tripList = PlanEditor.optimizeResult.data[index].tripList;
            PlanEditor.optimizeResult.data[index].tripList = tripList1;
            var tripList2 = PlanEditor.filledOptimizeResult.data[index - i].tripList;
            PlanEditor.filledOptimizeResult.data[index - i].tripList = PlanEditor.filledOptimizeResult.data[index].tripList;
            PlanEditor.filledOptimizeResult.data[index].tripList = tripList2;
            var html1 = dayNode1.find(".Travel_exe_d_d").html();
            var html2 = dayNode2.find(".Travel_exe_d_d").html();
            dayNode2.find(".Travel_exe_d_d").html(html1);
            dayNode1.find(".Travel_exe_d_d").html(html2);
            dayNode1.find("li:hidden").each(function () {
                PlanEditor.recycleBin[$(this).attr("tid")].day = dayNode1.index();
                PlanEditor.filledRecycleBin[$(this).attr("tid")].day = dayNode1.index();
            });
            dayNode2.find("li:hidden").each(function () {
                PlanEditor.recycleBin[$(this).attr("tid")].day = dayNode2.index();
                PlanEditor.filledRecycleBin[$(this).attr("tid")].day = dayNode2.index();
            });
            PlanEditor.initChangeDay();
            PlanEditor.initMap();
            PlanEditor.sortable();
        });
        $(".Travel_exe_d_f_down").on("click", function (e) {
            if (e.stopPropagation) {    // standard
                e.stopPropagation();
            } else {
                // IE6-8
                e.cancelBubble = true;
            }
            var dayNode1 = $(this).parents(".Travel_exe_d");
            var index = dayNode1.index();
            var dayNode2 = dayNode1.next();
            var i = 1;
            while (dayNode2.is(":hidden")) {
                dayNode2 = dayNode2.next();
                i++;
            }
            if (dayNode1.data("city-id") != dayNode2.data("city-id")) {
                return;
            }
            var tripList1 = PlanEditor.optimizeResult.data[index + i].tripList;
            PlanEditor.optimizeResult.data[index + i].tripList = PlanEditor.optimizeResult.data[index].tripList;
            PlanEditor.optimizeResult.data[index].tripList = tripList1;
            var tripList2 = PlanEditor.filledOptimizeResult.data[index + i].tripList;
            PlanEditor.filledOptimizeResult.data[index + i].tripList = PlanEditor.filledOptimizeResult.data[index].tripList;
            PlanEditor.filledOptimizeResult.data[index].tripList = tripList2;
            var html1 = dayNode1.find(".Travel_exe_d_d").html();
            var html2 = dayNode2.find(".Travel_exe_d_d").html();
            dayNode2.find(".Travel_exe_d_d").html(html1);
            dayNode1.find(".Travel_exe_d_d").html(html2);
            dayNode1.find("li:hidden").each(function () {
                PlanEditor.recycleBin[$(this).attr("tid")].day = dayNode1.index();
                PlanEditor.filledRecycleBin[$(this).attr("tid")].day = dayNode1.index();
            });
            dayNode2.find("li:hidden").each(function () {
                PlanEditor.recycleBin[$(this).attr("tid")].day = dayNode2.index();
                PlanEditor.filledRecycleBin[$(this).attr("tid")].day = dayNode2.index();
            });
            PlanEditor.initChangeDay();
            PlanEditor.initMap();
            PlanEditor.sortable();
        });
        PlanEditor.initChangeDay();
        PlanEditor.initMap();
        return this;
    },

    disableChangeDay: function () {
        PlanEditor.optimizing = true;
        loadingBegin();
        $(".minus").addClass("disable");
        $(".plus").addClass("disable");
        $(".quantity").prop("disabled", "disabled");
    },
    enableChangeDay: function () {
        PlanEditor.optimizing = false;
        loadingEnd();
        $(".minus").removeClass("disable");
        $(".plus").removeClass("disable");
        $(".quantity").prop("disabled", "");
    },

    optimize: function () {
        PlanEditor.disableChangeDay();
        var optimizeRequest = {};
        optimizeRequest.type = 2;
        optimizeRequest.hour = PlanEditor.plan.hour;
        if (isNaN(optimizeRequest.hour) || optimizeRequest.hour < 1 || optimizeRequest.hour > 3) {
            optimizeRequest.hour = 2;
        }
        optimizeRequest.cityDays = {};
        optimizeRequest.day = 0;
        PlanEditor.plan.city = {};
        $(".Travel_e_c_ul").find("li").each(function () {
            var id = $(this).data("id") + "";
            PlanEditor.plan.city[id] = $(this).find(".quantity").val();
            if (!isNull(id) && parseInt(id) < 1000000) {
                id = id.substr(0, 4);
            }
            var count = parseInt($(this).find(".quantity").val());
            optimizeRequest.cityDays[id] = count;
            optimizeRequest.day += count;
        });
        optimizeRequest.scenicList = [];
        $.each(PlanEditor.plan.scenicList, function (i, data) {
            var tripNode = {};
            tripNode.id = data.id;
            tripNode.type = 1;
            optimizeRequest.scenicList.push(tripNode);
        });
        PlanEditor.clearCookie();
        $.post("/lvxbang/plan/optimize.jhtml", {json: JSON.stringify(optimizeRequest)}, function (result) {
            if(window.console && console.log) {
                window.console.log(result);
            }
            if (!isNull(PlanEditor.optimizeResult.id)) {
                result.id = PlanEditor.optimizeResult.id;
            }
            PlanEditor.optimizeResult = result;
            PlanEditor.saveCookie();
            PlanEditor.renderPage();
            PlanEditor.enableChangeDay();
        },"json");
        PlanEditor.saveCookie();
    },

    savePlan: function (gotoFn) {
        var planUpdateRequest = {};
        planUpdateRequest.id = PlanEditor.optimizeResult.id;
        planUpdateRequest.days = [];
        $.each(PlanEditor.optimizeResult.data, function (dayIndex, day) {
            if (day.tripList.length==0) {
                return;
            }
            var planDay = {};
            planDay.cityId = day.city.id;
            planDay.trips = [];
            $.each(day.tripList, function (i, tripNode) {
                var trip = {};
                trip.scenicId = tripNode.id;
                trip.type = tripNode.type;
                planDay.trips.push(trip);
            });
            planUpdateRequest.days.push(planDay);
        });
        //PlanEditor.clearCookie();
        //if (has_no_User()) {
        //    return false;
        //}
        if (has_no_User(function () {
                PlanEditor.savePlan(gotoFn)
            })) {
            return;
        }
        $.post("/lvxbang/plan/save.jhtml", {json: JSON.stringify(planUpdateRequest)}, function (result) {
            if (result.success) {
                PlanEditor.optimizeResult.id = result.id;
                PlanEditor.saveCookie();
                $("#planId").val(result.id);
                if (gotoFn) {
                    setTimeout(function () {
                        gotoFn();
                    }, 1000);
                } else {
                    promptMessage("保存成功");
                    setTimeout(function () {
                        var random = parseInt(Math.random() * 10000);
                        window.location.href = $('#index_path').val() + "/lvxbang/user/plan.jhtml?save=ok&random=" + random ;
                    }, 1000);
                }
                PlanEditor.clearCookie();
            }
        },"json");
        PlanEditor.saveCookie();
    },

    clearCookie: function () {
        delCookie("plan-data");
        delCookie("plan-optimized");
    },

    saveCookie: function () {
        setUnCodedCookie("plan-data", JSON.stringify(PlanEditor.plan));
        PlanEditor.simpleOptimizeResult();
        setUnCodedCookie("plan-optimized", JSON.stringify(PlanEditor.optimizeResult));
        setCookie("plan-failed", JSON.stringify(FloatEditor.failedScenic));
    },

    getCookie: function () {
        PlanEditor.plan = JSON.parse(getUnCodedCookie("plan-data"));
        PlanEditor.optimizeResult = JSON.parse(getUnCodedCookie("plan-optimized"));
        PlanEditor.failedScenic = JSON.parse(getCookie("plan-failed"));
        delCookie("plan-failed");
    },

    simpleOptimizeResult: function () {
        if (PlanEditor.optimizeResult == null) {
            return;
        }
        if (PlanEditor.optimizeResult.addNodes != null) {
            $.each(PlanEditor.optimizeResult.addNodes, function (i, node) {
                var newNode = {};
                newNode.id = node.id;
                newNode.type = node.type;
                PlanEditor.optimizeResult.addNodes.splice(i, 1);
                PlanEditor.optimizeResult.addNodes.splice(i, 0, newNode);
            });
        }
        if (PlanEditor.optimizeResult.removedNodes != null) {
            $.each(PlanEditor.optimizeResult.removedNodes, function (i, node) {
                var newNode = {};
                newNode.id = node.id;
                newNode.type = node.type;
                PlanEditor.optimizeResult.removedNodes.splice(i, 1);
                PlanEditor.optimizeResult.removedNodes.splice(i, 0, newNode);
            });
        }
        $.each(PlanEditor.optimizeResult.data, function (i, data) {
            var newCity = {};
            newCity.id = data.city.id;
            data.city = newCity;
            $.each(data.tripList, function (j, trip) {
                var newTrip = {};
                newTrip.id = trip.id;
                newTrip.type = trip.type;
                data.tripList.splice(j, 1);
                data.tripList.splice(j, 0, newTrip);
            });
        });
    },

    fillWithPlan: function () {
        $.getJSON("/lvxbang/plan/getPlanResponse.jhtml", {planId: $("#planId").val()}, function (result) {
            PlanEditor.optimizeResult = result;
            PlanEditor.renderPage();
        });
    },

    intDragable: function () {
        $('.Travel_exe_d_d ul li:last-child').addClass('last');

        $(".Travel_exe_d_d ul li").hover(function () {
            $(this).removeClass('last');
            $('.Travel_exe_d_d ul li:last-child').addClass('last');
        });

        $(".Travel_exercise_div_b").delegate(".Travel_exe_d_d li", "mousedown", function () {
            var node = $(this);
            var dayNode1 = $(this).parents(".Travel_exe_d");
            var startIndex = node.prevAll("li:visible").length;
            if(window.console && console.log) {
                window.console.log("start in " + dayNode1.index() + ":" + node.index());
            }
            node.mouseout(function () {
                var dayNode2 = node.parents(".Travel_exe_d");
                var endIndex = node.prevAll("li:visible").length;
                if(window.console && console.log) {
                    window.console.log("end in " + dayNode2.index() + ":" + node.index());
                }
                var data;
                $.each(PlanEditor.optimizeResult.data, function (dayIndex, day) {
                    if (dayIndex != dayNode1.index()) {
                        return;
                    }
                    data = day.tripList.splice(startIndex, 1);
                });
                $.each(PlanEditor.filledOptimizeResult.data, function (dayIndex, day) {
                    if (dayIndex != dayNode1.index()) {
                        return;
                    }
                    data = day.tripList.splice(startIndex, 1);
                });
                $.each(PlanEditor.optimizeResult.data, function (dayIndex, day) {
                    if (dayIndex != dayNode2.index()) {
                        return;
                    }
                    day.tripList.splice(endIndex, 0, data[0]);
                });
                $.each(PlanEditor.filledOptimizeResult.data, function (dayIndex, day) {
                    if (dayIndex != dayNode2.index()) {
                        return;
                    }
                    day.tripList.splice(endIndex, 0, data[0]);
                });
                //移动后该天没有景点
                if (dayNode1.find("li:visible").length == 0){
                    dayNode1.hide();
                    var cityId = dayNode1.attr("data-city-id");
                    //对应城市天数修改
                    $(".Travel_e_c_ul li").each(function() {
                        if ($(this).attr("data-id") == cityId) {
                            var quantity = $(this).find(".quantity");
                            quantity.val(parseInt(quantity.val()) - 1);
                            if (parseInt(quantity.val()) == 0) {
                                $(this).hide();
                            }
                        }
                    });
                    //修改该天之后的天数
                    var next = dayNode1.next();
                    while(next.length != 0){
                        if (next.attr("data-city-id") == cityId) {
                            var number = parseInt(next.find(".Travel_exe_d_f").attr("data-day")) - 1;
                            next.find(".Travel_exe_d_f").attr("data-day", number);
                            next.find(".Travel_exe_d_f span").eq(0).text(number);
                        }
                        next = next.next();
                    }
                    //总天数和城市显示
                    var total = $("#total");
                    total.text(parseInt(total.text()) - 1);
                }
                PlanEditor.initChangeDay();
                PlanEditor.initMap();
                node.unbind("mouseout");
            });
        });

        $(".Travel_exercise_div_b").delegate(".Travel_exe_d_d li .name", "click", function () {
           window.open($(this).data("url"));
        });
    },
    initMap: function () {
        LvxbangMap.clear();
        $("#edit-map-day-panel").html("");
        $(".Travel_exe_d:visible").each(function (day) {
            var planDay = {};
            planDay.day = day + 1;
            planDay.cityName = $(this).data("city-name");
            planDay.trips = [];
            var index = 1;
            $(this).find("li").each(function () {
                if ($(this).css("display") == "none") {
                    return;
                }
                var scenic = {};
                scenic.day = day + 1;
                scenic.index = index;
                scenic.lng = $(this).attr("lng");
                scenic.lat = $(this).attr("lat");
                scenic.id = $(this).attr("tid");
                scenic.name = $(this).attr("tname");
                scenic.address = $(this).attr("taddress");
                scenic.price = $(this).attr("tprice");
                scenic.comment = $(this).attr("tcomment");
                scenic.cover = $(this).attr("tcover");
                scenic.star = $(this).attr("tstar");
                LvxbangMap.addAScenic(scenic, day);
                if (index == 1) {
                    LvxbangMap.location(scenic.lng, scenic.lat, 13)
                }
                planDay.trips.push(scenic);
                index += 1;
            });
            $("#edit-map-day-panel").append(template("tpl-plan-map-day", planDay));
        });
        LvxbangMap.drawLines();
        var dayPanel = $(".Travel_exercise_div_b");
        dayPanel.find(".Travel_exe_d_f").click(function () {
            if(window.console && console.log) {
                window.console.log("123");
            }
            LvxbangMap.toBigger();
            dayPanel2.find("li").eq(parseInt($(this).parent().index())).click();
        });
        $("#allTravel").click(function () {
            LvxbangMap.toBigger();
            dayPanel2.find(".alltravel").click();
        });
        var dayPanel2 = $(".hqxqdtx_fr");
        dayPanel2.find(".alltravel").click(function () {
            dayPanel2.find("li").removeClass("checked");
            dayPanel2.find(".alltravel").addClass("checked");
            LvxbangMap.showAllLine();
        });
        dayPanel2.find("li").click(function () {
            dayPanel2.find(".alltravel").removeClass("checked");
            dayPanel2.find("li").removeClass("checked");
            $(this).addClass("checked");
            LvxbangMap.showDay($(this).index() + 1);
        });
        dayPanel2.find(".plan-map-scenic").click(function () {
            var id = $(this).data("id");
            LvxbangMap.showAScenic(id);
        });
        LvxbangMap.bindEvent();
    },

    initChangeDay: function () {
        var dayNodes = $(".Travel_exe_d:visible .Travel_exe_d_f");
        dayNodes.each(function (i) {
            var day = dayNodes.eq(i);
            if (i > 0) {
                var prev = dayNodes.eq(i - 1);
                if (prev.parents(".Travel_exe_d").data("city-id") == day.parents(".Travel_exe_d").data("city-id")) {
                    day.find(".Travel_exe_d_f_up").addClass("changeable");
                }
            }
            if (i < dayNodes.length - 1) {
                var next = dayNodes.eq(i + 1);
                if (next.parents(".Travel_exe_d").data("city-id") == day.parents(".Travel_exe_d").data("city-id")) {
                    day.find(".Travel_exe_d_f_down").addClass("changeable");
                }
            }
        });
    },

    sortable: function () {
        $('.Travel_exe_d ul').unbind();
        var city = "";
        $('.Travel_exe_d').each(function() {
            if (city == $(this).data("city-id")) {
                return;
            }
            city = $(this).data("city-id");
            $(this).parent().find("[data-city-id = " + city + "] ul").sortable({connectWith:".Travel_exe_d[data-city-id = " + city + "] ul"});
        });
    }
}
;
$(function () {
    PlanEditor.init();
});

