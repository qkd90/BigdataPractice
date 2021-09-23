var Booking = {
    planes: [],
    hotels: [],
    trains: [],
    totalAmount: 0,
    init: function () {

        FloatEditor.getCookie();
        $("img").lazyload({
            effect: "fadeIn"
        });

        //栏目加浮动
        var navbar = function () {
            var navbar_top = $(window).scrollTop();
            var height = $("#nav").offset().top;
            if (navbar_top >= height) {
                $(".nav").addClass("fixed");
            }
            if (navbar_top <= height) {
                $(".nav").removeClass("fixed");
            }
        };
        $(window).bind("scroll", navbar);

        //搜索框
        $(".categories .input").click(function () {
            $(".categories_div").hide();
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


        $('.hotel-start-date').hover(function(){
            $(this).css('border-color','green');
        },function(){
            $(this).css('border-color','');
        });
        $('.hotel-end-date').hover(function(){
            $(this).css('border-color','green');
        },function(){
            $(this).css('border-color','');
        });

        $(document).on("click", function () {
            $(".categories_div").hide();
        });

        $("img").lazyload({
            effect: "fadeIn"
        });

        $('.ico3').click(function(){
            if($(this).next().next().css("display") == "none"){
                $(this).next().next().css("display","block");
                $(this).css('background-position','-172px 0');
            } else {
                $(this).next().next().css("display","none");
                $(this).css('background-position','-154px 0');
            }
            // 阻止冒泡
            if (event.stopPropagation) {    // standard
                event.stopPropagation();
            } else {
                // IE6-8
                event.cancelBubble = true;
            }
        });

        //删除按钮
        $(".Free_exe_d_d .close").click(function () {
            $(this).parent('li').fadeOut(500, function () {
                $(this).parent('li').remove();
            });
        });

        //展开更多
        $(".main").delegate(".free_e_fl_lm_ul_div_poen .but", "click", function () {
            var myStaute = $(this).attr("data-staute");
            if (!myStaute) {
                $(this).html("收起<i></i>");
                var el = $(this).parents('.free_e_fl_lm_ul_div_poen');
                el.find("dl").find(".booking").css({height: 40, overflow: 'visible'});
                var curHeight = el.height(),
                    autoHeight = el.css('height', 'auto').height();
                el.height(curHeight).animate({height: autoHeight}, 1000);
                $(this).addClass("checked").attr("data-staute", "1");
            } else {
                $(this).html("展开<i></i>");
                $(this).parents(".free_e_fl_lm_ul_div_poen").animate({"height": "35px"}, 1000, function () {
                    var selected = false;
                    var rooms = $(this).find("dl").find(".booking");
                    rooms.each(function (i) {
                        if (i == 0) {
                            return;
                        }
                        if ($(this).find(".checkbox").hasClass("checked")) {
                            selected = true;
                        } else {
                            $(this).css({height: 0, overflow: 'hidden'});
                        }
                    });
                    if (selected) {
                        rooms.eq(0).css({height: 0, overflow: 'hidden'});
                    }
                });

                $(this).removeClass("checked").removeAttr("data-staute");
            }
        });

        $(".main").delegate(".free_e_fl_botm", "click", function () {
            var name = $(this).find('.more_scenic').attr("scenic_name");
            var myStaute = $(this).attr("data-staute");
            if (!myStaute) {
                $(this).siblings(".free_e_fl_open").fadeIn(1000);
                $(this).addClass("checked").attr("data-staute", "1");
                $(this).find('.more_scenic').html(name.replace("展开","收起"));
            } else {
                $(this).siblings(".free_e_fl_open").fadeOut(500);
                $(this).removeClass("checked").removeAttr("data-staute");
                $(this).find('.more_scenic').html(name);
            }
        });

        //checkbox
        $(".main").delegate(".free_exercise_div .checkbox", "click", function () {
            //console.log("common");
            var input = $(this).attr("input");
            var myStaute = $(this).attr("data-staute");
            if (!myStaute) {
                if ($(this).hasClass("go")) {
                    $(this).parents(".booking-info").find(".go").removeClass("checked").removeAttr("data-staute");
                }
                if ($(this).hasClass("return")) {
                    $(this).parents(".booking-info").find(".return").removeClass("checked").removeAttr("data-staute");
                }
                if (input == "selectall") {
                    $(this).addClass("checked").attr("data-staute", "1");
                    $(this).parents('.free_e_fl_c').find(".free_e_fl_lm .free_e_fl_c_title").eq(0).find(".checkbox").click();
                }
                else if (input == "selectall_group") {
                    if (!$(this).parent().next().hasClass('hotel-booking-info')) {
                        $(this).parents(".booking-info").find(".free_e_fl_lm .free_e_fl_c_title").each(function () {
                            if (!$(this).next().hasClass('hotel-booking-info')) {
                                $(this).find(".checkbox").removeClass("checked").removeAttr("data-staute").parent('.free_e_fl_c_title').next(".free_e_fl_lm_div").find("ul .checkbox").removeClass("checked").removeAttr("data-staute");
                            }
                        });
                    }
                    $(this).addClass("checked").attr("data-staute", "1").parent('.free_e_fl_c_title').next(".free_e_fl_lm_div").find("ul .checkbox").addClass("checked").attr("data-staute", "1");
                }
                else {
                    $(this).addClass("checked").attr("data-staute", "1");
                    if ($(this).hasClass('room-checkbox')) {
                        changeRoom.call(this,true);
                    }
                }
            }
            else {
                if (input == "selectall") {
                    $(this).parents('.free_e_fl_c').find(".free_e_fl_lm .checkbox").removeClass("checked").removeAttr("data-staute");
                } else if (input == "selectall_group") {
                    $(this).removeClass("checked").removeAttr("data-staute").parent('.free_e_fl_c_title').next(".free_e_fl_lm_div").find("ul .checkbox").removeClass("checked").removeAttr("data-staute");
                    $(this).parents('.free_e_fl_c').find(".free_e_fl_c_ut .checkbox").removeClass("checked").removeAttr("data-staute");

                } else {
                    if ($(this).hasClass('room-checkbox')) {
                        changeRoom.call(this,false);
                    }
                }
                $(this).removeClass("checked").removeAttr("data-staute");
                $(this).parents('.free_e_fl_lm_div').prev('.free_e_fl_c_title').find(".checkbox").removeClass("checked").removeAttr("data-staute");
                $(this).parents('.free_e_fl_c').find(".free_e_fl_c_ut .checkbox").removeClass("checked").removeAttr("data-staute");
            }

            //复选框点击判断是否全选

                var off = $(this).parents('li').hasClass("booking");
                var off2 = true;
                if(off){
                    $(this).parents('ul').find('.checkbox').each(function(){
                        if(!$(this).hasClass('checked')){
                            off2 = false;
                        }
                    });
                    if(off2){
                        $(this).parents('.free_e_fl_lm_div').prev().find(".checkbox").click();
                    }
                }

            Booking.renderAmount();
        });

        function changeRoom(checked) {
            var panel = $(this).parents(".free_e_fl_lm_div");
            if (checked) {
                panel.find(".free_e_fl_lm_ul_div").find(".default-price").hide();
                panel.find(".free_e_fl_lm_ul_div").find(".selected-price").text($(this).parents(".booking").find(".price").text()).show();
            } else {
                //panel.find(".free_e_fl_lm_ul_div").find(".default-price").show();
                //panel.find(".free_e_fl_lm_ul_div").find(".selected-price").hide();
            }
            panel.find(".room-checkbox").removeClass("checked").removeAttr("data-staute");
            if (checked) {
                $(this).addClass("checked").attr("data-staute", 1);
            }
            Booking.renderAmount();
        };

        $(".booking-button").click(function () {
            Booking.submit();
        });
        $(".save-button").click(function () {
            Booking.savePlan();
        });

        $(".recommend-booking").click(function () {
            Booking.saveChecked();
            if ($(this).hasClass("first_day")) {
                var bookings = $(".recommend-booking");
                var isLast = false;
                $("#startTime").val($("#from-date-0").val());
                bookings.each(function (i) {
                    if (i > 0) {
                        var panel = $(this).parents(".booking-panel").prev().prev();
                        var day = parseInt(panel.find(".city-days").val());
                        var fromDateStr = panel.find(".from-date").val();
                        var fromDate = new Date(Date.parse(fromDateStr.replace("-", "/")));
                        fromDate.setDate(fromDate.getDate() + day + 1);
                        $(this).parents(".booking-panel").find(".from-date").val(fromDate.format("yyyy-MM-dd"));
                    }
                    if (bookings.length == (i + 1)) {
                        isLast = true;
                    }
                    Booking.recommend.call(this, isLast);
                });
            } else {
                Booking.recommend.call(this);
            }

        });

        $(".hotel-star").find(".categories_div li").click(function () {
            if (isNull($(this).data("value"))) {
                $(this).parents(".hotel-star").data("value", null);
            } else {
                $(this).parents(".hotel-star").data("value", $(this).data("value"));
            }
        });

        $(".traffic-type").find(".categories_div li").click(function () {
            $(this).parents(".traffic-type").data("value", $(this).data("value")?$(this).data("value"):null);
        });

        Booking.getChecked();
        $('.moren').each(function(){
            if(!$(this).hasClass('checked')){
                $(this).click();
            }

        });

        $("#startTime").val($("#from-date-0").val());
    },

    renderAmount: function () {
        Booking.planes = [];
        Booking.hotels = [];
        Booking.trains = [];
        var json = {};
        var totalSummary = 0;
        $(".booking-info").each(function () {
            var title = $(this).data("title");
            var reverseTitle = $.trim($(this).data("title-reverse").split("—")[0]) + "—" + $("#booking-from-city-0").val();
            var cityData = {};
            cityData.traffic = [];
            $(this).find(".train-booking-info").find(".booking").each(function () {
                if (!$(this).find(".checkbox").hasClass("checked")) {
                    return;
                }
                var data = {};
                if ($(this).index() == 1) {
                    data.name = reverseTitle;
                } else {
                    data.name = title;
                }
                data.id = $(this).data("id");
                data.price = Number($(this).find(".price").text());
                Booking.trains.push(data);
                var traffic = {};
                traffic.priceHash = $(this).data("price-hash") ;
                traffic.trafficHash = $(this).data("traffic-hash") ;
                if ($(this).find(".checkbox").hasClass("return")) {
                    traffic.key = $(this).data("from-city") + "##" + $("#booking-from-city-0").data("areaid") + "##" +  $(this).data("type") + "##" +  $(this).data("date");
                } else {
                    traffic.key = $(this).data("from-city") + "##" + $(this).data("to-city") + "##" + $(this).data("type") + "##" + $(this).data("date");
                }
                cityData.traffic.push(traffic);
            });
            $(this).find(".plane-booking-info").find(".booking").each(function () {
                if (!$(this).find(".checkbox").hasClass("checked")) {
                    return;
                }
                var data = {};
                if ($(this).index() == 1) {
                    data.name = reverseTitle;
                } else {
                    data.name = title;
                }
                data.id = $(this).data("id");
                data.price = Number($(this).find(".price").text());
                data.additionalFuelTax = Number($(this).find(".additionalFuelTax").val());
                data.airportBuildFee = Number($(this).find(".airportBuildFee").val());

                Booking.planes.push(data);
                var traffic = {};
                traffic.priceHash = $(this).data("price-hash") ;
                traffic.trafficHash = $(this).data("traffic-hash") ;
                if ($(this).find(".checkbox").hasClass("return")) {
                    traffic.key = $(this).data("from-city") + "##" + $("#booking-from-city-0").data("areaid") + "##" + $(this).data("type") + "##" + $(this).data("date");
                } else {
                    traffic.key = $(this).data("from-city") + "##" + $(this).data("to-city") + "##" + $(this).data("type") + "##" + $(this).data("date");
                }
                cityData.traffic.push(traffic);
            });
            $(this).find(".hotel-booking-info").find(".booking").each(function () {
                if (!$(this).find(".checkbox").hasClass("checked")) {
                    return;
                }
                var data = {};
                data.id = $(this).data("id");
                data.name = $(this).parents("li").find(".jd_name b").text();
                data.startDate = $(this).parents(".hotel-booking-info").find(".hotel-start-date").val();
                data.endDate = $(this).parents(".hotel-booking-info").find(".hotel-end-date").val();
                //var startDate = new Date(Date.parse(data.startDate.replace("-", "/")));
                //var endDate = new Date(Date.parse(data.endDate.replace("-", "/")));
                //$(this).data("days", endDate.getDate() - startDate.getDate());
                $(this).data("days", getDays(data.endDate, data.startDate));
                data.days = $(this).data("days");
                data.hotelId = $(this).data("hotel-id");
                data.source = $(this).data("hotel-source");
                data.price = Number($(this).find(".price").text()) * Number($(this).data("days"));
                //data.startDate = $("#hotel-start-date").val();
                //data.endDate = $("#hotel-end-date").val();
                Booking.hotels.push(data);
                cityData.hotel = data.id;
            });
            json[$(this).prev().find(".to-city-id").val()] = cityData;
        });
        $("#plan-trans-and-hotel").val(JSON.stringify(json));
        $(".plane-summary").find(".summary-panel").html("");
        if($('.plane-summary').find('.price').size() == 0){
            $('.plane-summary').hide();
        }
        //Booking.planes.forEach(function (data) {
        $.each(Booking.planes,function (i, data) {
            totalSummary += Number(data.price) + Number(data.additionalFuelTax) + Number(data.airportBuildFee);
            $(".plane-summary").show();
            $(".plane-summary").find(".summary-panel").append(template("tpl-traffic-summary2", data));
    });
        $(".train-summary").find(".summary-panel").html("");
        if($('.train-summary').find('.price').size() == 0){
            $('.train-summary').hide();
        }

        //Booking.trains.forEach(function (data) {
        $.each(Booking.trains, function (i, data) {
            totalSummary += Number(data.price);
            $(".train-summary").show();
            $(".train-summary").find(".summary-panel").append(template("tpl-traffic-summary", data));
        });
        $(".hotel-summary").find(".summary-panel").html("");
        if($('.hotel-summary').find('.price').size() == 0){
            $('.hotel-summary').hide();
        }
        var ddzj = totalSummary;
        var jddf = 0;
        //Booking.hotels.forEach(function (data) {
        $.each(Booking.hotels, function (i, data) {
            ddzj += parseInt(data.price * 100) / 100;
            jddf += parseInt(data.price * 100) / 100;
            if (data.source != 'ELONG') {
                totalSummary += Number(data.price);
            }
            $(".hotel-summary").show();
            $(".hotel-summary").find(".summary-panel").append(template("tpl-hotel-summary", data));
        });
        totalSummary = parseInt(totalSummary * 100) / 100;
        $('.ddzj').text("¥" + ddzj);
        $('.jddf').text("-" + jddf);
        $(".total-summary").text("¥" + totalSummary);
        var data = {};
        data.traffic = "";
        $.each(Booking.trains, function (index, traffic) {
            if (data.traffic!="") {
                data.traffic += ",";
            }
            data.traffic += traffic.id;
        });
        $.each(Booking.planes, function (index, traffic) {
            if (data.traffic!="") {
                data.traffic += ",";
            }
            data.traffic += traffic.id;
        });
        data.hotel = [];
        //Booking.hotels.forEach(function (hotel) {
        $.each(Booking.hotels,function (i,hotel) {
            var hotelPrice = {};
            hotelPrice.name = hotel.name;
            hotelPrice.hotelId = hotel.hotelId;
            hotelPrice.priceId = hotel.id;
            hotelPrice.startDate = hotel.startDate;
            hotelPrice.leaveDate = hotel.endDate;
            data.hotel.push(hotelPrice);
        });
        $("#json-data").val(JSON.stringify(data));
    },

    submit: function () {
        if (isNull(FloatEditor.optimizeResult)) {
            promptWarn("行程中没有景点，请添加!");
            return;
        }
        var planUpdateRequest = {};
        planUpdateRequest.id = FloatEditor.optimizeResult.id;
        planUpdateRequest.days = [];
        planUpdateRequest.cityId = $('#booking-from-city-0').data('areaid');
        planUpdateRequest.startTime = $("#startTime").val();
        $.each(FloatEditor.optimizeResult.data, function (i, day) {
            var planDay = {};
            planDay.cityId = day.city.id;
            planDay.trips = [];
            //day.tripList.forEach(function (tripNode) {
            $.each(day.tripList,function (i,tripNode) {
                var trip = {};
                trip.scenicId = tripNode.id;
                trip.type = tripNode.type;
                planDay.trips.push(trip);
            });
            planUpdateRequest.days.push(planDay);
        });
        if (has_no_User(function () {
                Booking.submit();
            })) {
            return;
        }
        loadingBegin("正在加载...");
        $.post("/lvxbang/plan/save.jhtml", {json: JSON.stringify(planUpdateRequest)}, function (result) {
            if (result.success) {
                FloatEditor.optimizeResult.id = result.id;
                $(".planId").val(result.id);
                //FloatEditor.emptyEditor();
                //FloatEditor.clearCookie();
                var json = $("#plan-trans-and-hotel").val();
                $.post("/lvxbang/plan/saveTransAndHotel.jhtml", {json: json, planId: result.id}, function(result) {
                    loadingEnd();
                    if (!result.success) {
                        promptWarn("保存失败，继续进入预订");
                    }
                    promptMessage("保存成功");
                    var json = JSON.parse($("#json-data").val());
                    var traffic = "";
                    $.each(result.list, function (index, data) {
                        if (traffic.length > 0) {
                            traffic += ",";
                        }
                        traffic += data;
                    });
                    json.traffic = traffic;
                    $("#json-data").val(JSON.stringify(json));
                    $("#booking-order").submit();
                },"json");
                Booking.deleteChecked();
            } else {
                promptWarn("保存失败");
                loadingEnd();
            }
        },"json");
        FloatEditor.saveCookie();
    },

    recommend: function (isLast) {
        var panel = $(this).parents(".booking-panel");
        var request = {};
        request.planId = $("#planId").val();
        request.days = panel.find(".city-days").val();
        request.toCityId = panel.find(".to-city-id").val();
        request.fromCityId = panel.find(".destination").data("areaid");
        request.prevFromCityId = $("#booking-from-city-0").data("areaid");
        request.fromDateStr = panel.find(".from-date").val();
        request.coreScenic = panel.find(".core-scenic").val();
        request.hotelStar = panel.find(".hotel-star").data("value");
        request.trafficType = panel.find(".traffic-type").data("value");
        var firstCityId = panel.find("#booking-from-city-0").data("areaid");
        loadingBegin();
        $.post("/lvxbang/plan/bookingByCity.jhtml", {json: JSON.stringify(request), firstCityId: firstCityId, isReturn: $(this).data("is-return")}, function (result) {
            //panel.next().remove();
            var transAndHotelPanel = panel.next();
            transAndHotelPanel.find('.free_e_fl_open').prevAll().remove();

            if(request.trafficType == "TRAIN") {
                result.noPlane = true;
            } else if(request.trafficType == "AIRPLANE"){
                result.noTrain = true;
            }
            if (result.hotels.length == 0) {
                result.hotelEmpty = true;
            }
            if (result.trains.length == 0) {
                result.trainEmpty = true;
            }
            if (result.planes.length == 0) {
                result.planeEmpty = true;
            }
            if (result.fromDate) {
                result.fromDateStr =  new Date(result.fromDate.time).format("yyyyMMdd");
                result.fromDate = new Date(result.fromDate.time).format("yyyy-MM-dd");
            }
            if (result.toDate) {
                result.toDateStr =  new Date(result.toDate.time).format("yyyyMMdd");
                result.toDate = new Date(result.toDate.time).format("yyyy-MM-dd");
            }
            //panel.after(template("tpl-booking-info-item2", result));
            result.panelIndex = panel.find(".title .ff_yh").text();
            transAndHotelPanel.find('.free_e_fl_open').before(template("tpl-booking-info-item2", result));

            $('.hotel-start-date').hover(function(){
                $(this).css('border-color','green');
            },function(){
                $(this).css('border-color','');
            });
            $('.hotel-end-date').hover(function(){
                $(this).css('border-color','green');
            },function(){
                $(this).css('border-color','');
            });

            panel.find(".fromCity").text(result.fromCityName);
            var cityNames = panel.find(".fromCity").parent().text().split(">")[1].split("至");
            transAndHotelPanel.data("title",cityNames[0]+"-"+cityNames[1]);
            transAndHotelPanel.data("title-reverse",cityNames[1]+"-"+cityNames[0]);
            transAndHotelPanel.attr("data-title",cityNames[0]+"-"+cityNames[1]);
            transAndHotelPanel.attr("data-title-reverse",cityNames[1]+"-"+cityNames[0]);
            //$(".plane-summary").find(".summary-panel").html("");
            Booking.renderAmount();
            Booking.getChecked();
            if (isLast == null || isLast) {
                loadingEnd();
            }
            //$('.moren').each(function(){
            $('.moren').each(function(){
                if(!$(this).hasClass('checked')){
                    $(this).click();
                }
            });
        },"json")
    },
    savePlan: function () {
        var planUpdateRequest = {};
        planUpdateRequest.id = FloatEditor.optimizeResult.id;
        planUpdateRequest.days = [];
        //planUpdateRequest.startCityName = $('#booking-from-city-0').val();
        planUpdateRequest.cityId = $('#booking-from-city-0').data('areaid');
        $.each(FloatEditor.optimizeResult.data, function (i, day) {
            if (day.tripList.length==0) {
                return;
            }
            var planDay = {};
            planDay.cityId = day.city.id;
            planDay.trips = [];
            $.each(day.tripList, function (j, tripNode) {
                var trip = {};
                trip.scenicId = tripNode.id;
                trip.type = tripNode.type;
                planDay.trips.push(trip);
            });
            planUpdateRequest.days.push(planDay);
        });
        planUpdateRequest.startTime = $("#startTime").val();
        if (has_no_User(function () {
                Booking.savePlan()
            })) {
            return;
        }
        loadingBegin("线路生成中...");
        $.post("/lvxbang/plan/save.jhtml", {json: JSON.stringify(planUpdateRequest)}, function (result) {
            if (result.success) {
                FloatEditor.optimizeResult.id = result.id;
                $("#planId").val(result.id);
                promptMessage("保存成功");
                //FloatEditor.emptyEditor();
                //FloatEditor.clearCookie();
                var json = $("#plan-trans-and-hotel").val();
                $.post("/lvxbang/plan/saveTransAndHotel.jhtml", {json: json, planId: result.id}, function (result) {
                    loadingEnd();
                    if (result.success) {
                        Booking.deleteChecked();
                        window.location.href = "/lvxbang/plan/detail.jhtml?planId=" +  $("#planId").val();
                    } else {
                        promptWarn("保存失败");
                    }
                },"json")
            }
        },"json");
        FloatEditor.saveCookie();
     },
    initChangeTraffic: function () {
        $(document).delegate(".change-plane", "click", function () {
            var panel = $(this).parents(".booking");
            var form = $("#change-plane-form");
            if ($(this).parents(".free_e_fl_lm_ul_div").find(".checkbox").hasClass("return")) {
                var city = panel.parents(".booking-info").data("title-reverse").split("-");
                form.find(".leaveCity").val(panel.data("from-city"));
                form.find(".arriveCity").val($("#booking-from-city-0").data("areaid"));
                form.find(".leaveCityName").val(city[0]);
                form.find(".arriveCityName").val($("#booking-from-city-0").val());
            } else {
                var city = panel.parents(".booking-info").data("title").split("-");
                form.find(".leaveCity").val(panel.data("from-city"));
                form.find(".arriveCity").val(panel.data("to-city"));
                form.find(".leaveCityName").val(city[0]);
                form.find(".arriveCityName").val(city[1]);
            }
            var date = panel.data("date")+"";
            var prettyDate = date.substr(0, 4) + "-" + date.substr(4, 2) + "-" + date.substr(6, 2);
            form.find(".leaveDate").val(prettyDate);
            form.find(".firstLeaveDate").val($.trim($("#from-date-0").val()));
            Booking.saveChecked();
            form.submit();
        });
        $(document).delegate(".change-train", "click", function () {
            var panel = $(this).parents(".booking");
            var form = $("#change-train-form");
            if ($(this).parents(".free_e_fl_lm_ul_div").find(".checkbox").hasClass("return")) {
                var city = panel.parents(".booking-info").data("title-reverse").split("-");
                form.find(".leaveCity").val(panel.data("from-city"));
                form.find(".arriveCity").val($("#booking-from-city-0").data("areaid"));
                form.find(".leaveCityName").val(city[0]);
                form.find(".arriveCityName").val($("#booking-from-city-0").val());
            } else {
                var city = panel.parents(".booking-info").data("title").split("-");
                form.find(".leaveCity").val(panel.data("from-city"));
                form.find(".arriveCity").val(panel.data("to-city"));
                form.find(".leaveCityName").val(city[0]);
                form.find(".arriveCityName").val(city[1]);
            }
            var date = panel.data("date")+"";
            var prettyDate = date.substr(0, 4) + "-" + date.substr(4, 2) + "-" + date.substr(6, 2);
            form.find(".leaveDate").val(prettyDate);
            form.find(".firstLeaveDate").val($.trim($("#from-date-0").val()));
            Booking.saveChecked();
            form.submit();
        });
        $(document).delegate(".change-hotel", "click", function () {
            var panel = $(this).parents(".hotel-node");
            var form = $("#change-hotel-form");
            form.find(".cityId").val($(this).parents(".booking-info").data("city-id"));

            var startDate = panel.find('input').eq(0).val();
            var endDate = panel.find('input').eq(1).val();
            var minDate = panel.find('input').eq(2).val();
            var maxDate = panel.find('input').eq(3).val();
            //form.find(".startDate").val($(this).parents(".booking-panel").find("input.from-date").val());
            form.find(".star").val($(".hotel-star").data("value"));
            form.find(".startDate").val(startDate);
            form.find(".endDate").val(endDate);
            form.find(".minDate").val(minDate);
            form.find(".maxDate").val(maxDate);
            form.find(".firstLeaveDate").val($.trim($("#from-date-0").val()));
            form.find(".coreScenic").val($(this).parents(".booking-info").prev().find(".core-scenic").val());
            Booking.saveChecked();
            form.submit();
        });
    },

    saveChecked: function () {
        var checked = [];
        $(".booking .checkbox.checked").parents(".booking").each(function () {
            if (this.tagName == "LI") {
                checked.push($(this).data("price-hash"));
            }
            if (this.tagName == "DD") {
                checked.push($(this).data("id"));
            }
        });
        setUnCodedCookie("booking-checked", JSON.stringify(checked));
    },

    getChecked: function () {
        var checked = JSON.parse(getUnCodedCookie("booking-checked"));
        if (isNull(checked) || checked.length < 1) {
            return;
        }
        $(".booking").each(function () {
            var thiz = this;
            $.each(checked, function (i, code) {
                if ((thiz.tagName == "LI" && $(thiz).data("price-hash") == code) || (thiz.tagName == "DD" && $(thiz).data("id") == code)) {
                    if (!$(thiz).find(".checkbox").hasClass("checked")) {
                        $(thiz).find(".checkbox").click();
                    }
                }
            });
        });
    },

    deleteChecked: function () {
        delCookie("booking-checked");
    }
};
$(document).ready(function () {
    Booking.init();
    Booking.initChangeTraffic();
    Booking.renderAmount();
});
$(function(){
    //日历按钮点击完善
    $('.ico2').click(function(){
        //$(this).next().click();
        $(this).next().focus();
    });
    //页面加载完后隐藏右边小计中没有价格的模块

    if($('.plane-summary').find('.price').size() == 0){
        $('.plane-summary').hide();
    }
    if($('.train-summary').find('.price').size() == 0){
        $('.train-summary').hide();
    }
    if($('.hotel-summary').find('.price').size() == 0){
        $('.hotel-summary').hide();
    }
    //日期改变后改变酒店几晚数
    $('body').delegate('.hotel-start-date,.hotel-end-date','change',function(){
        var thiz = $(this).parents('.hotel-node').find('.checkbox').eq(0);
        if(thiz.hasClass('checked')){
            thiz.click();
            thiz.click();
        }
        window.console.log(123);
    });
    //页面加载完后如果出现交通空的情况则重新推介,从编辑页过来才进行操作
    //if($('#newOne').val()=='true'){
    //    $('#newOne').val("");
    //    rerecommend();
    //}
});

function getDays(strDateStart,strDateEnd){
    var strSeparator = "-"; //日期分隔符
    var oDate1;
    var oDate2;
    var iDays;
    oDate1= strDateStart.split(strSeparator);
    oDate2= strDateEnd.split(strSeparator);
    var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
    var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
    iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数
    return iDays ;
}
function rerecommend() {
    $('.booking-panel').each(function () {
        if ($(this).next().find(".free_null2").length > 0) {
            $(this).find(".traffic-type li").eq(2).click();
        }

    });
    $(".booking-panel").eq(0).find(".first_day").click();
}