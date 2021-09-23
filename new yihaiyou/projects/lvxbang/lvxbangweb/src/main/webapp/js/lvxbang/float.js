var FloatEditor = {
    notSupportCity: [],
    editor: $(".plan_editor"),
    editorPanel: $(".plan_editor").find(".Add_attractions_u"),
    plan: null,
    scenicDetail: {},
    optimizeResult: null,
    failedScenic: null,
    init: function () {
        if ($("body").hasClass("HandDrawing")) {
            $(".Float_w_l_b ul li").eq(0).hide();
        }
        var planId = $("#planId").val();
        if (!isNull(planId) && !$("body").hasClass("fill_orders")) {
            FloatEditor.fillWithPlan();
        } else {
            FloatEditor.getCookie();
            if (FloatEditor.plan) {
                FloatEditor.fillCity();
                FloatEditor.fillScenicData();
            }
        }
        FloatEditor.bindEvent();
        //if ( $("#destination").find(".checkbox.checked").length>0) {
        //    FloatEditor.plan = {
        //        status: 0, //0: not optimized, 1: optimized, 2: saved
        //        scenicList: [], //not optimized plan, all scenic stay here
        //        city: {} //passed city id and days
        //    };
        //    //FloatEditor.renderCity();
        //}
        if (isNull(FloatEditor.plan)) {
            FloatEditor.plan = {
                status: 0, //0: not optimized, 1: optimized, 2: faled
                scenicList: [], //not optimized plan, all scenic stay here
                city: {} //passed city id and days
            };
        }
        FloatEditor.renderEditor();
        FloatEditor.markScenic();
        if (FloatEditor.plan.scenicList.length > 0) {
            $(".Float_w_r").attr("open_k", "1");
            //$(".Add_attractions").show();
            //$(".Myitinerary").addClass("checked").attr("data-staute", "1");  //我的行程打开
            //$(".Itineraryfk").hide();
        }
        FloatEditor.resize();
    },
    emptyEditor: function () {
        FloatEditor.editorPanel.find("li").fadeOut(500, function () {
            FloatEditor.editorPanel.find("li").remove();
            $(".scenic-node").find(".stroke").each(function () {
                if ($(this).hasClass("checked")) {
                    $(this).removeClass("checked").removeAttr("data-staute");
                    //var spa = $(this).find("spa");
                    //spa.html("+" + spa.html());
                }
            });
            if ($("body").hasClass("Attractions_Detail")) {
                $(".add_line").css('background-color', '#ff4800');
            }
        });
        FloatEditor.plan = {
            city: {},
            status: 0, //0: not optimized, 1: optimized, 2: saved
            scenicList: [], //not optimized plan, all scenic stay here
            detail: [] //optimized plan, split scenic in days
        };
        FloatEditor.optimizeResult = {
            addNodes: {},
            data: {},
            id: 0,
            removedNode: {}
        };
        delCookie("plan-data");
        delCookie("plan-optimized");
        FloatEditor.fillCity();
        $(".Add_attractions").hide();
        $(".Itineraryfk").show();
    },
    closeFloat: function () {
        $(".Itineraryfk").hide();
        $(".Float_w_r").animate({"width": "0px"}, 300);
        FloatEditor.editor.find(".Myitinerary").removeClass("checked").removeAttr("data-staute");
    },
    openFloat: function () {
        var Float_w_r = $(".Float_w_r").attr("open_k");
        $(".Float_w_r").css("width", "1px").animate({"width": "240px"}, 300);
        if (!Float_w_r) {
            $(".Itineraryfk").show();
        } else if (Float_w_r == 2) {
            $(".Itineraryfk,.Add_attractions").hide();
            $(".EditStroke").show();
        } else {
            $(".Itineraryfk").hide();
            $(".Add_attractions").show();
        }
        FloatEditor.editor.find(".Myitinerary").addClass("checked").attr("data-staute", "1");
    },
    deleteAScenic: function () {
        var id = $(this).data("id");
        var city_id = $(this).data("city-id");

        $(".scenic-node").each(function () {
            var node = $(this);
            var scenicId = node.data("id");
            if (id == scenicId) {
                node.find(".stroke").removeClass("checked").removeAttr("data-staute");
                if ($("body").hasClass("Attractions_Detail")) {
                    $(".add_line").css('background-color', '#ff4800');
                }
                var spa = node.find(".stroke spa");
                spa.html("+" + spa.html());
            }
        });
        var cityId = FloatEditor.formatCityId($(this).data("city-id"));
        $(this).parent('li').fadeOut(500, function () {
            $(this).remove();
            FloatEditor.calculateCountTime(city_id);
            FloatEditor.removeACity(parseInt(cityId));
        });
        for (var i = 0; i < FloatEditor.plan.scenicList.length; i++) {
            if (FloatEditor.plan.scenicList[i].id == id) {
                FloatEditor.plan.scenicList.splice(i, 1);
                FloatEditor.saveCookie();
                break;
            }
        }
        if (FloatEditor.plan.scenicList.length < 1) {
            $(".Add_attractions").hide();
            $(".Itineraryfk").show();
        }
    }, bindEvent: function () {
//返回顶部鼠标经过
        FloatEditor.editor.find(".Float_w_l_b").find("li").hover(function () {
                if ($(this).index() == 1) {
                    $(".Float_w_l_b_d", this).animate({"width": "140px"}, 300);
                } else {
                    $(".Float_w_l_b_d", this).animate({"width": "51px"}, 300);
                }
            },
            function () {
                $(".Float_w_l_b_d", this).animate({"width": "0px"}, 300);
            });

        //li 鼠标经过
        FloatEditor.editorPanel.delegate("li", "mouseenter", function () {
            $(this).addClass("checked");
        }).delegate("li", "mouseleave", function () {
            $(this).removeClass("checked");
        });

        //删除按钮
        FloatEditor.editorPanel.delegate('.close', 'click', function () {
            FloatEditor.deleteAScenic.call(this);
        });

        //清空
        FloatEditor.editor.find(".Add_attractions_C .close").click(function () {
            deleteWarn("确定清空景点？", FloatEditor.emptyEditor);
        });

        //单选按钮
        FloatEditor.editor.find(".Add_attractions_B li").click(function () {
            FloatEditor.editor.find(".Add_attractions_B .radio").attr("checked", false);
            $(".radio", this).prop("checked", "checked");
            FloatEditor.editor.find(".Add_attractions_B li").removeClass("checked");
            $(this).addClass("checked");
            FloatEditor.plan.hour = $(this).index() + 1;
            FloatEditor.saveCookie();
        });

        //我的行程打开
        FloatEditor.editor.find(".Myitinerary").click(function () {
            var Myitinerary = $(this).attr("data-staute");
            if (!Myitinerary) {
                FloatEditor.openFloat();
            } else {
                FloatEditor.closeFloat();
            }
        });

        FloatEditor.editor.find(".Itinerary_title b.posiA").click(function () {
            FloatEditor.closeFloat();
        });

        //点击加入行程
        $(document).delegate(".stroke_open", "click", function (event) {
            if ($(this).hasClass("stroke_handDraw")) {
                return;
            }
            var stroke = $(this).attr("data-staute");
            if (!stroke) {
                $(".Itineraryfk").hide();
                if (FloatEditor.plan.scenicList.length == 0) {
                    $(".Float_w_r").animate({"width": "240px"}, 300).attr("open_k", "1"); 	//右侧打开
                }
                $(".Add_attractions").show();//添加景点
                $(".Myitinerary").addClass("checked").attr("data-staute", "1");  //我的行程打开

                //判断浏览器版本
                var userAgent = navigator.userAgent.toLowerCase();
                jQuery.browser = {
                    version: (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1],
                    safari: /webkit/.test( userAgent ),
                    opera: /opera/.test( userAgent ),
                    msie: /msie/.test( userAgent ) && !/opera/.test( userAgent ),
                    mozilla: /mozilla/.test(userAgent)&&!/(compatible|webkit)/.test(userAgent)
                };

                //IE的话就不执行抛物线
                if(!jQuery.browser.msie){
                    //alert(jQuery.browser.version);
                    //alert(123);
                     FloatEditor.flyIntoCart.apply(this, [event]);  //运行抛物线
                }


                $(this).addClass("checked").attr("data-staute", "1");  //加选中样式
                var spa = $(this).find("spa");
                if (!isNull(spa.html()) && spa.html().indexOf("+") == 0) {
                    spa.html(spa.html().substring(1, spa.html().length));
                }
                var scenic = $(this).parents(".scenic-node").data("scenic");
                FloatEditor.addIntoCart(scenic);
            } else {
                var id = $(this).parents(".scenic-node").data("id");
                $(".Add_attractions_u .close").each(function () {
                    if ($(this).data("id") == id) {
                        $(this).click();
                        return false;
                    }
                });
            }
        });

        //智能排序
        FloatEditor.editor.find(".Add_attractions_But").click(function () {
            var notSupport = "";
            $(".city-in-editor").each(function () {
                if (FloatEditor.notSupportCity.indexOf($(this).data("id")) >= 0) {
                    if (notSupport.length > 0) {
                        notSupport += "、";
                    }
                    notSupport += $(this).find("label").text();
                    return false;
                }
            });
            if (notSupport.length > 0) {
                promptWarn(notSupport + "不支持智能排序，请重新规划");
                return;
            }
            $(".Add_attractions").hide();//添加景点
            $(".EditStroke").fadeIn(1000);//添加景点
            $(".Float_w_r").addClass("checked").attr("open_k", "2");
            FloatEditor.optimize();
        });

        //编辑行程>
        FloatEditor.editor.find(".EditStroke_c_a").click(function () {
            $(".EditStroke").hide();//添加景点
            $(".Add_attractions").fadeIn(1000);//添加景点
            $(".Float_w_r").addClass("checked").attr("open_k", "1");
        });

        //加减
        FloatEditor.editor.find(".cart_stock_ul").delegate(".minus", "click", function () {
            var n = $(this).siblings().children(".quantity").val();
            var nn = $.trim(n);
            var num = parseInt(n) - 1;
            if (num == 0 || nn == "" || nn == null) {
                num = 1;
            }
            $(this).siblings().children(".quantity").val(num);
        }).delegate(".plus", "click", function () {
            var n = $(this).siblings().children(".quantity").val();
            var nn = $.trim(n);
            var num = parseInt(n) + 1;
            if (num == 0 || nn == "" || nn == null) {
                num = 1;
            }
            $(this).siblings().children(".quantity").val(num);
        });

        FloatEditor.editor.find(".save-plan").click(function () {
            FloatEditor.savePlan();
        });
    },
    flyIntoCart: function (event, img) {
        var fk_img = $(this).closest(".scenic-node").find('.scenic-node-img').attr("src");
        if (img) {
            fk_img = img;
        }
        var offset = $('#end').offset(), flyer = $('<img class="u-flyer" src="' + fk_img + '"/>');
        var scrollTop = $(document.body).scrollTop();
        flyer.fly({
            start: {
                left: event.pageX,
                top: $(this).offset().top - scrollTop
            },
            end: {
                left: offset.left,
                top: 234,
                width: 50,
                height: 50
            }
        });
        setTimeout("$('.u-flyer').hide()", 1000);//隐藏图片
    },
    addIntoCart: function (data) {
        var scenic = {};
        scenic.id = data.id;
        FloatEditor.plan.scenicList.push(scenic);
        var detail = {};
        detail.id = data.id;
        detail.name = data.name;
        detail.cover = data.cover;
        detail.score = data.score;
        detail.cityId = data.cityId;
        detail.adviceMinute = data.adviceMinute;
        FloatEditor.scenicDetail[scenic.id] = detail;
        var cityId = data.cityId;
        cityId = FloatEditor.formatCityId(cityId, 0);
        if (!FloatEditor.plan.city[cityId]) {
            FloatEditor.plan.city[cityId] = 1;
            FloatEditor.fillACity(cityId);
        }
        FloatEditor.saveCookie();
        FloatEditor.renderEditor();
        if (!isNull($('#cityId_' + FloatEditor.formatCityId(data.cityId)))) {
            FloatEditor.calculateCountTime(data.cityId);
        }
    },
    renderCity: function () {
        var temp = {};
        var panel = $(".cart_stock_ul");
        panel.find(".city-in-editor").each(function () {
            temp[$(this).data("id")] = $(this).find(".quantity").val();
        });
        $("#destination").find(".checkbox.checked").each(function () {
            var data = {};
            data.id = $(this).data("id");
            data.name = $(this).attr("destination");
            if (!temp[data.id]) {
                FloatEditor.addACityInFloat(data);
            }
        });
    },
    renderEditor: function () {
        FloatEditor.editorPanel.html("");
        if (FloatEditor)
            if (FloatEditor.plan.scenicList) {
                $.each(FloatEditor.plan.scenicList, function (i, scenic) {
                    FloatEditor.editorPanel.append(template("tpl-scenic-in-editor", FloatEditor.scenicDetail[scenic.id]));
                });
            }
    },
    saveCookie: function () {
        setUnCodedCookie("plan-data", JSON.stringify(FloatEditor.plan));
        FloatEditor.simpleOptimizeResult();
        setUnCodedCookie("plan-optimized", JSON.stringify(FloatEditor.optimizeResult));
        setCookie("plan-failed", JSON.stringify(FloatEditor.failedScenic));
    },

    getCookie: function () {
        FloatEditor.plan = JSON.parse(getUnCodedCookie("plan-data"));
        FloatEditor.optimizeResult = JSON.parse(getUnCodedCookie("plan-optimized"));
        FloatEditor.failedScenic = JSON.parse(getCookie("plan-failed"));
    },

    simpleOptimizeResult: function () {
        if (FloatEditor.optimizeResult == null) {
            return;
        }
        if (FloatEditor.optimizeResult.addNodes != null) {
            $.each(FloatEditor.optimizeResult.addNodes, function (i, node) {
                var newNode = {};
                newNode.id = node.id;
                newNode.type = node.type;
                FloatEditor.optimizeResult.addNodes.splice(i, 1);
                FloatEditor.optimizeResult.addNodes.splice(i, 0, newNode);
            });
        }
        if (FloatEditor.optimizeResult.removedNodes != null) {
            $.each(FloatEditor.optimizeResult.removedNodes, function (i, node) {
                var newNode = {};
                newNode.id = node.id;
                newNode.type = node.type;
                FloatEditor.optimizeResult.removedNodes.splice(i, 1);
                FloatEditor.optimizeResult.removedNodes.splice(i, 0, newNode);
            });
        }
        $.each(FloatEditor.optimizeResult.data, function (i, data) {
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

    markScenic: function () {
        $(".scenic-node").each(function () {
            var node = $(this);
            var scenicId = node.data("id");
            if (FloatEditor.plan.scenicList) {
                $.each(FloatEditor.plan.scenicList, function (i, data) {
                    if (data.id == scenicId) {
                        node.find(".stroke").addClass("checked").attr("data-staute", 1);
                        var spa = node.find("spa");
                        if (!isNull(spa.html()) && spa.html().indexOf("+") == 0) {
                            spa.html(spa.html().substring(1, spa.html().length));
                        }
                    }
                });
            }
        })
    },

    markAScenic: function (scenicId) {
        if (FloatEditor.plan.scenicList) {
            $.each(FloatEditor.plan.scenicList, function (i, data) {
                if (data.id == scenicId) {
                    $(".stroke_open").addClass("checked").attr("data-staute", 1);
                    $(".stroke_open").text("√已加入线路");
                }
            });
        }
    },

    clearCookie: function () {
        delCookie("plan-data");
        delCookie("plan-optimized");
    },
    optimize: function () {
        var optimizeRequest = {};
        optimizeRequest.type = 2;
        FloatEditor.editor.find(".cart_stock_ul").find("li").each(function () {

        });
        optimizeRequest.day = 0;
        $(".city-in-editor").each(function () {
            optimizeRequest.day += parseInt($(this).find(".quantity").val());
        });
        optimizeRequest.hour = $(".Add_attractions_B").find("li.checked").index() + 1;
        if (isNaN(optimizeRequest.hour) || optimizeRequest.hour < 1 || optimizeRequest.hour > 3) {
            optimizeRequest.hour = 2;
        }
        FloatEditor.plan.hour = optimizeRequest.hour;
        optimizeRequest.cityDays = {};
        FloatEditor.plan.city = {};
        FloatEditor.editor.find(".city-in-editor").each(function () {
            var cityId = $(this).data("id");
            optimizeRequest.cityDays[FloatEditor.formatCityId(cityId)] = $(this).find(".quantity").val();
            FloatEditor.plan.city[cityId] = $(this).find(".quantity").val();
        });
        optimizeRequest.scenicList = [];
        if (FloatEditor.plan.scenicList) {
            $.each(FloatEditor.plan.scenicList, function (i, data) {
                var tripNode = {};
                tripNode.id = data.id;
                tripNode.type = 1;
                optimizeRequest.scenicList.push(tripNode);
            });
        }
        var planId = null;
        if (!isNull(FloatEditor.optimizeResult)) {
            planId = FloatEditor.optimizeResult.id;
        }
        FloatEditor.clearCookie();
        $.post("/lvxbang/plan/optimize.jhtml", {json: JSON.stringify(optimizeRequest)}, function (result) {
            if (!result.success) {
                $(".EditStroke").hide();
                $(".Add_attractions").show();
                return;
            }
            FloatEditor.optimizeResult = result;
            FloatEditor.optimizeResult.id = planId;
            if (!result.success) {
                FloatEditor.plan.status = 2;
            } else {
                FloatEditor.plan.status = 1;
            }
            FloatEditor.saveCookie();
            window.location.href = $('#plan_path').val() + '/lvxbang/plan/edit.jhtml';
            //FloatEditor.renderOptimizeResult();
        },"json");
        FloatEditor.saveCookie();
    },
    renderOptimizeResult: function () {
        $.post("/lvxbang/plan/fillDetail.jhtml", {json: JSON.stringify(FloatEditor.optimizeResult)}, function (result) {
            var removedPanel = FloatEditor.editor.find(".EditStroke_s");
            removedPanel.html("");
            if (result.removeNode) {
                $.each(result.removedNode, function (i, node) {
                    removedPanel.append("<s>" + node.name + "</s>");
                });
            }
            var optimizedPanel = $(".EditStroke_b");
            optimizedPanel.html("");
            if (result.data) {
                $.each(result.data, function (i, day) {
                    optimizedPanel.append(template("tpl-optimized-day", day));
                });
            }
        },"json");
    },

    savePlan: function () {
        if (has_no_User(function () {
                FloatEditor.savePlan()
            })) {
            return;
        }
        var planUpdateRequest = {};
        planUpdateRequest.id = FloatEditor.optimizeResult.id;
        planUpdateRequest.days = [];
        if (FloatEditor.optimizeResult.data) {
            $.each(FloatEditor.optimizeResult.data, function (i, day) {
                var planDay = {};
                planDay.cityId = day.city.id;
                planDay.trips = [];
                if (day.tripList) {
                    $.each(day.tripList, function (j, tripNode) {
                        var trip = {};
                        trip.scenicId = tripNode.id;
                        trip.type = tripNode.type;
                        planDay.trips.push(trip);
                    });
                }
                planUpdateRequest.days.push(planDay);
            });
        }

        $.post("/lvxbang/plan/save.jhtml", {json: JSON.stringify(planUpdateRequest)}, function (result) {
            if (result == "nologin") {
                window.location.href = $('#plan_path').val() + "/lvxbang/login/login.jhtml";
            }
            if (result == "success") {
                if (result.success) {
                    FloatEditor.optimizeResult.id = result.id;
                    FloatEditor.saveCookie();
                    promptMessage("保存成功");
                    //alert("保存成功");
                }
            }
        },"json");
    },
    getPlanData: function () {
        return FloatEditor.plan;
    },
    renderPage: function (jumpUrl) {
        $.post("/lvxbang/plan/fillDetail.jhtml", {json: JSON.stringify(FloatEditor.optimizeResult)}, function (result) {
            FloatEditor.filledOptimizeResult = result;
            FloatEditor.fillPlanList(jumpUrl);
        },"json");
    },
    fillWithPlan: function () {
        $.getJSON("/lvxbang/plan/getPlanResponse.jhtml", {planId: $("#planId").val()}, function (result) {
            FloatEditor.optimizeResult = result;
            FloatEditor.renderPage();
        });
    },
    fillPlanList: function (jumpUrl) {
        if (!FloatEditor.filledOptimizeResult || !FloatEditor.filledOptimizeResult.data) {
            return;
        }
        FloatEditor.plan.city = {};
        FloatEditor.plan.scenicList = [];
        $.each(FloatEditor.filledOptimizeResult.data, function (i, day) {
            if (FloatEditor.plan.city[day.city.id]) {
                FloatEditor.plan.city[day.city.id] += 1;
            } else {
                FloatEditor.plan.city[day.city.id] = 1;
            }
            $.each(day.tripList, function (j, trip) {
                var scenic = {};
                scenic.id = trip.id;
                FloatEditor.plan.scenicList.push(scenic);
                var detail = {};
                detail.id = trip.id;
                detail.name = trip.name;
                detail.cover = trip.cover;
                detail.score = trip.score;
                FloatEditor.scenicDetail[scenic.id] = detail;
            });
            FloatEditor.saveCookie();
            FloatEditor.renderEditor();
            if (FloatEditor.plan.scenicList.length > 0) {
                $(".Float_w_r").attr("open_k", "1");
                //$(".Add_attractions").show();
                //$(".Myitinerary").addClass("checked").attr("data-staute", "1");  //我的行程打开
                //$(".Itineraryfk").hide();

            }
        });
        FloatEditor.fillCity();
        if (jumpUrl) {
            window.location.href = jumpUrl;
        }
    },
    fillACity: function (cityId) {
        $.getJSON("/lvxbang/area/getAreaByIds.jhtml", {idStr: cityId}, function (result) {
            $.each(result, function (index, city) {
                FloatEditor.addACityInFloat(city);
            });
            FloatEditor.resize();
        });
    },
    removeACity: function (cityId) {
        var removable = true;
        $(".Add_attractions_u").find(">li>a").each(function () {
            if (FloatEditor.formatCityId($(this).data("city-id")) == cityId) {
                removable = false;
                return false;
            }
        });
        if (!removable) {
            return;
        }
        $(".city-in-editor").each(function () {
            if (FloatEditor.formatCityId($(this).data("id")) == cityId) {
                $(this).remove();
                return false;
            }
        });
        if (FloatEditor.plan.city[FloatEditor.parseCityId(cityId)]) {
            delete  FloatEditor.plan.city[FloatEditor.parseCityId(cityId)];
        }
        FloatEditor.saveCookie();
    },

    addACityInFloat: function (city) {
        if (FloatEditor.notSupportCity.indexOf(city.id) >= 0) {
            city.notSupport = true;
        }
        FloatEditor.editor.find(".cart_stock_ul").append(template("tpl-city-in-editor", city));
        if (!isNull($('.adviceMinute_' + FloatEditor.formatCityId(city.id)).eq(0).html())) {
            FloatEditor.calculateCountTime(city.id);
        }
    },
    fillCity: function () {
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
                $.each(result, function (index, city) {
                    city.count = FloatEditor.plan.city[city.id];
                    FloatEditor.addACityInFloat(city);
                });
                FloatEditor.resize();
                if ($("body").hasClass("Attractions_List") && $("#planId").val()) {
                    CitySelector.initFloatCity();
                }
            });
        }
    },
    resize: function () {
        FloatEditor.editorPanel.height($(window).height() - 37 * ($(".city-in-editor").length) - 266);
    },
    fillScenicData: function () {
        var ids = [];
        $.each(FloatEditor.plan.scenicList, function (i, scenic) {
            ids.push(scenic.id);
        });
        $.getJSON("/lvxbang/scenic/getScenicBriefData.jhtml", {json: JSON.stringify(ids)}, function (result) {
            $.each(FloatEditor.plan.scenicList, function (i, scenic) {
                FloatEditor.scenicDetail[scenic.id] = result[scenic.id];
            });
            FloatEditor.renderEditor();
        })
    },
    calculateCountTime: function (cityId) {
        var id = FloatEditor.formatCityId(cityId);
        var day = $('#cityId_' + id).val();
        var newDay = 0;
        var countTime = 0;
        $('.adviceMinute_' + id).each(function (e) {
            countTime = countTime + Number($(this).html());
        });
        var scenicCount = $('.adviceMinute_' + id).size();
        //if ((countTime % 6) > 0) {
        //    newDay = Math.floor((countTime/6)) + 1;
        //} else {
        //    newDay = Math.floor((countTime/6));
        //}
        newDay = Math.ceil((countTime / 8));
        if (newDay == 0) {
            newDay = 1;
        }
        if (day != newDay) {
            if (newDay > scenicCount) {
                newDay = scenicCount;
            }
            FloatEditor.plan.city[id + "00"] = newDay;
            $('#cityId_' + id).val(newDay);
        }
        FloatEditor.saveCookie();
    },
    //城市id，type!=0国内返回城市id前4位
    formatCityId: function (cityId, type) {
        var jingwaiCityStartIndex = 1000000;
        if (parseInt(cityId) < jingwaiCityStartIndex) {
            if (type == 0) {
                return parseInt(cityId / 100) * 100;
            } else {
                return parseInt(cityId / 100);
            }
        } else {
            return parseInt(cityId);
        }
    },
    parseCityId: function (cityId) {
        if (parseInt(cityId) < 10000) {
            return parseInt(cityId) * 100;
        } else {
            return parseInt(cityId);
        }
    }
};
$(document).ready(function () {
    FloatEditor.init();
});