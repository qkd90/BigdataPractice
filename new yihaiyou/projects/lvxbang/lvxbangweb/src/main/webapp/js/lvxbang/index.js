function initInputs() {
    // $('#li_plan').click(function () {
    //     $('#scenic-form dl').removeClass('hid').addClass('sho');
    // });
    // $('#li_destination').click(function () {
    //     $('#ipt-destination').removeClass('hid').addClass('sho');
    // });
    $('#li_flight').click(function () {
        $('#flightSearchForm dl').removeClass('hid').addClass('sho');
        $(".fan,.zzcs,.zzrq,.zzrq2,.ddcs").removeClass('sho').addClass('hid');
        // 清空当前输入框数据，防止输入框有数据，但隐藏值验证为空
        $(this).parent().parent().next().find('input[type="text"]').val('');
        $(this).parent().parent().next().find('input[type="text"]').attr('data-areaId', '');
        bindInputs("flightSearchForm");
    });
    $('#li_train').click(function () {
        $('#trainSearchForm dl').removeClass('hid').addClass('sho');
        $(".fan2,.transit").removeClass('sho').addClass('hid');
        // 清空当前输入框数据，防止输入框有数据，但隐藏值验证为空
        $(this).parent().parent().next().find('input[type="text"]').val('');
        $(this).parent().parent().next().find('input[type="text"]').attr('data-areaId', '');
        bindInputs("trainSearchForm");
    });
    $('#li_hotel').click(function () {
        $('#hotelSearchForm .required').addClass('sho');
        bindInputs("hotelSearchForm");
    });
    // $('#li_ticket').click(function () {
    //     $('#scenicSearchForm dl').removeClass('hid').addClass('sho');
    // });
    // $('#input_planId').click(function(){
    //     $('#hotelSearchForm dl').removeClass('hid').addClass('sho');
    // });
}
function bindInputs(id) {

    //console.info('bind inputs:' + id);
    $('.sho input').unbind('change');
    $('#' + id + ' .sho input:not(:hidden)').change(function () {
        if ($('.KeywordTips').hasClass('checked')) {
            //window.console.info('KeywordTips showing');
            return;
        }
        var allClear = true;
        //console.info(allClear + '~' + $(this).attr('name') + ':' + $(this).val() + "," + $(this).attr('type'));
        var inputs = $('#' + id + ' .fl.sho input:not(:hidden)');
        inputs.each(function () {
            if (allClear && ($(this).val() == undefined || $(this).val() == '')) {
                //console.info(allClear + '~' + $(this).attr('name') + ':' + $(this).val() + "," + $(this).attr('type'));
                $(this).click();
                $(this).focus();
                allClear = false;
                return;
            }
        });
        var inputs2 = $('#' + id + ' .fr.sho input:not(:hidden)');
        inputs2.each(function () {
            if (allClear && ($(this).val() == undefined || $(this).val() == '')) {
                //console.info(allClear + '~' + $(this).attr('name') + ':' + $(this).val() + "," + $(this).attr('type'));
                $(this).click();
                $(this).focus();
                allClear = false;
                return;
            }
        });
    });
}
$(document).ready(function () {
    initInputs();

    //广告
    $(".stroke_s").click(function () {
        $(".nextpage").animate({"margin-top": "0"}, 300);
        $(".head").css("overflow", "hidden").animate({"height": "0"}, 300);
        /*setTimeout(function(){
         $(".nextpage").animate({"height":"0","padding":"0"},1200,function(){
         $(".nextpage").hide();
         });
         },10000);//延迟1秒*/
        $(".close").show();
    });
    $(".nextpage .close").click(function () {
        $(".nextpage").animate({"margin-top": "-658px"}, 300);
        $(".head").css("overflow", "visible").animate({"height": "150px"}, 300);
        $(this).hide();
    });

    $(".iflight_rad .radio").click(function () {
        $(this).closest(".iflight_rad").find(".radio").removeClass("checked");
        $(this).addClass('checked');
    });

    var moveXXX = function () {
        var myTop = $(document).height() - $(window).height() - $(window).scrollTop();
        if (myTop >= 1550 || myTop <= 657) {
            $("div.clearhistory").removeClass("checked");
        } else {
            $("div.clearhistory").addClass("checked");
        }
    }
    $(window).bind("scroll", moveXXX);

    //机票
    $(".danchen").click(function () {
        $('#flightSearchForm dl').removeClass('hid').addClass('sho');
        $(".fan,.zzcs,.zzrq,.zzrq2,.ddcs").removeClass('sho').addClass('hid');
        $(".huan_bot,.huan").removeClass('hid');
        // 清空当前输入框数据，防止输入框有数据，但隐藏值验证为空
        //$(this).parent().parent().next().find('input[type="text"]').val('');
        //$(this).parent().parent().next().find('input[type="text"]').attr('data-areaId', '');
        bindInputs("flightSearchForm");
    });
    $(".wanfan").click(function () {
        $('#flightSearchForm dl').removeClass('hid').addClass('sho');
        $(".zzcs,.zzrq,.zzrq2,.ddcs").removeClass('sho').addClass('hid');
        $(".huan_bot,.huan").removeClass('hid');
        $(this).parents(".tray").find(".unselected").removeClass('sho').addClass('hid');
        // 清空当前输入框数据，防止输入框有数据，但隐藏值验证为空
        //$(this).parent().parent().next().find('input[type="text"]').val('');
        //$(this).parent().parent().next().find('input[type="text"]').attr('data-areaId', '');
        bindInputs("flightSearchForm");
    });
    $(".lianchen").click(function () {
        $('#flightSearchForm dl').removeClass('hid').addClass('sho');
        $(".csrq,.fan,.outset").removeClass('sho').addClass('hid');

        $(this).parents(".tray").find(".unselected").removeClass('sho').addClass('hid');
        // “换”显示隐藏
        $(".huan_bot,.huan").addClass('hid');
        // 清空当前输入框数据，防止输入框有数据，但隐藏值验证为空
        //$(this).parent().parent().next().find('input[type="text"]').val('');
        //$(this).parent().parent().next().find('input[type="text"]').attr('data-areaId', '');
        bindInputs("flightSearchForm");
    });
    $(".huan").click(function () {
        var flightType = $('#flightType li i.checked').attr('value');
        if (flightType == '1') {    // 单程
            var leaveCityName = $('#flightType').next().find('[name=leaveCityName]').val();
            var leaveCity = $('#flightType').next().find('[name=leaveCityName]').attr('data-areaId');
            var arriveCityName = $('#flightType').next().find('[name=arriveCityName]').val();
            var arriveCity = $('#flightType').next().find('[name=arriveCityName]').attr('data-areaId');
            // 出发
            $('#flightType').next().find('[name=leaveCityName]').val(arriveCityName);
            $('#flightType').next().find('[name=leaveCityName]').attr('data-areaId', arriveCity);
            // 到达
            $('#flightType').next().find('[name=arriveCityName]').val(leaveCityName);
            $('#flightType').next().find('[name=arriveCityName]').attr('data-areaId', leaveCity);
        } else if (flightType == '2') { // 往返
            var leaveCityName = $('#flightType').next().find('[name=leaveCityName]').val();
            var leaveCity = $('#flightType').next().find('[name=leaveCityName]').attr('data-areaId');
            var arriveCityName = $('#flightType').next().find('[name=arriveCityName]').val();
            var arriveCity = $('#flightType').next().find('[name=arriveCityName]').attr('data-areaId');
            // 出发
            $('#flightType').next().find('[name=leaveCityName]').val(arriveCityName);
            $('#flightType').next().find('[name=leaveCityName]').attr('data-areaId', arriveCity);
            // 到达
            $('#flightType').next().find('[name=arriveCityName]').val(leaveCityName);
            $('#flightType').next().find('[name=arriveCityName]').attr('data-areaId', leaveCity);
        }
    });

    //火车
    $(".danchen2").click(function () {
        $('#trainSearchForm dl').removeClass('hid').addClass('sho');
        $(".fan2,.transit").removeClass('sho').addClass('hid');
        $(".huan_bot,.huan").removeClass('hid');
        // 清空当前输入框数据，防止输入框有数据，但隐藏值验证为空
        //$(this).parent().parent().next().find('input[type="text"]').val('');
        //$(this).parent().parent().next().find('input[type="text"]').attr('data-areaId', '');
        bindInputs("trainSearchForm");
    });
    $(".wanfan2").click(function () {
        $('#trainSearchForm dl').removeClass('hid').addClass('sho');
        $(".transit").removeClass('sho').addClass('hid');
        $(".huan_bot,.huan").removeClass('hid');
        $(this).parents(".tray").find(".unselected").removeClass('sho').addClass('hid');
        // 清空当前输入框数据，防止输入框有数据，但隐藏值验证为空
        //$(this).parent().parent().next().find('input[type="text"]').val('');
        //$(this).parent().parent().next().find('input[type="text"]').attr('data-areaId', '');
        bindInputs("trainSearchForm");
    });
    $(".lianchen2").click(function () {
        $('#trainSearchForm dl').removeClass('hid').addClass('sho');
        $(this).parents(".tray").find(".unselected").removeClass('sho').addClass('hid');
        $(".huan_bot,.huan").removeClass('hid');
        $(".fan2").removeClass('sho').addClass('hid');
        // 清空当前输入框数据，防止输入框有数据，但隐藏值验证为空
        //$(this).parent().parent().next().find('input[type="text"]').val('');
        //$(this).parent().parent().next().find('input[type="text"]').attr('data-areaId', '');
        bindInputs("trainSearchForm");
    });
    $(".huan2").click(function () {
        //var trainType = $('#trainType li i.checked').attr('value');
        var leaveCityName = $('#trainType').next().find('[name=leaveCityName]').val();
        var leaveCity = $('#trainType').next().find('[name=leaveCityName]').attr('data-areaId');
        var arriveCityName = $('#trainType').next().find('[name=arriveCityName]').val();
        var arriveCity = $('#trainType').next().find('[name=arriveCityName]').attr('data-areaId');
        // 出发
        $('#trainType').next().find('[name=leaveCityName]').val(arriveCityName);
        $('#trainType').next().find('[name=leaveCityName]').attr('data-areaId', arriveCity);
        // 到达
        $('#trainType').next().find('[name=arriveCityName]').val(leaveCityName);
        $('#trainType').next().find('[name=arriveCityName]').attr('data-areaId', leaveCity);
    });

    ////图片遮罩层
    //$(".main_div div.posiR").hover(function(){
    //$("span.posiA",this).hide();
    //$("p.posiA",this).fadeIn();
    //},
    //function(){
    //$("p.posiA",this).fadeOut();
    //$("span.posiA",this).fadeIn();
    //});
    //
    //$("span.posiA",this).show();
    //$("p.posiA",this).hide();

    //图片遮罩层
    $(".main_div div.posiR").hover(function () {
            var obj = $(this);
            $("span.posiA", obj).fadeOut(200, function () {
                $("p.posiA", obj).animate({"top": "0"}, 200);
            });
        },
        function () {
            var obj = $(this);
            setTimeout(function () {
                $("p.posiA", obj).animate({"top": "100%"}, 200, function () {
                    $("span.posiA", obj).fadeIn(200);
                });
            }, 200);
        });

    //浏览历史
    $(".history_ul li").hover(function () {
            $("i.posiA", this).show();
        },
        function () {
            $("i.posiA", this).hide();
        });
    //注释：清除浏览历史点取消也会跟清空，所以先注释掉
    //$(".dinner").click(function(){
    //$(this).next("ul").remove();
    //});
    //
    //取消
    $(".dinnerx .cancel").click(function () {
        $(".mask,.dinnerx").hide();
    });

    //确定
    $(".dinnerx .determine").click(function () {
        $(".mask,.dinnerx").hide();
        $(".dinner").next("ul").remove();
    });


    $(".Destination_cs_t li").hover(function () {
            $("div.posiA", this).show();
        },
        function () {
            $("div.posiA", this).hide();
        });

    $(".d_cs_c_div dd").hover(function () {
            $(this).parent("dl").find("dd").removeClass("checked");
            $(this).addClass("checked");
        },
        function () {
            $(this).removeClass("checked");
            $(this).parent("dl").find("dd:eq(0)").addClass("checked");
        });

    $('.ico3').click(function () {
        var dis = $('.ico3').parent().children(':eq(3)').css('display');
        if (dis == "none") {
            $('.ico3').parent().children(':eq(3)').css('display', 'block');
            $(this).css('background-position', '-172px 0');
        } else {
            $('.ico3').parent().children(':eq(3)').css('display', 'none');
            $(this).css('background-position', '-154px 0');
        }
        // 阻止冒泡
        if (event.stopPropagation) {    // standard
            event.stopPropagation();
        } else {
            // IE6-8
            event.cancelBubble = true;
        }
    });

    //搜索历史
    var searchHistoryTxt = $("#searchHistoryTxt");
    var searchHistory = JSON.parse(getCookie("search_history"));
    if (!isNull(searchHistory) && searchHistoryTxt.length > 0) {
        $.each(searchHistory, function (i, data) {
            var span = "<a href='javaScript:;'><span>" + data + "</span></a> ";
            searchHistoryTxt.append(span);
        });
    }

    searchHistoryTxt.delegate("a", "click", function () {
        var $input = $(this).parents(".Addmore").hide().prev(".clickinput");
        var tempValue = $input.val();
        tempValue = SearcherBtn.replaceChar(tempValue);
        var tempArr = tempValue.split("；");
        var newArr = SearcherBtn.unique(SearcherBtn.trimArr(tempArr));
        var span = $(this).find("span").text();
        var flag = false;
        $.each(newArr, function (i, data) {
            if (data == span) {
                flag = true;
                return false;
            }
        });
        if (flag) {
            return;
        }
        var str = addSemicolon($input.val(), span);
        $input.val(str);
    });

});

// 清除浏览历史
function clearHistory() {
    $.post("/other/visitHistory/batchClearHistory.jhtml",
        {resType: 'scenic'},
        function (result) {
            if (result.success == true) {
                $('.historyUL').html('');	// 清空页面标签
                $('.dinner').remove();
            } else {
                promptWarn("清除浏览历史失败");
                //alert("清除浏览历史失败");
            }
        }
    ,"json");
}

// 搜索框的公用方法
var SearcherBtn = {

    // 首页目的地搜索按钮
    btnDistSeach: function () {
        var destId = getCityId($("#ipt-destination").val(), 1);
        if (destId > 0) {
            window.location.href = $("#destination_path").val() + "/city_" + destId + ".html";
        } else {

            if(isNull($('#ipt-destination').val())){
                $(".categories_Addmore2").show();
            }else {
                var size = $('#ipt-destination').siblings('.KeywordTips').find('li').size();
                if(size>0){
                    $('#destinationScenicName').val($('#ipt-destination').val());
                    $('#destinationScenicSearch').submit();
                }else{
                    $('#txtSearch').val($('#ipt-destination').val()).focus();
                    deleteWarn("匹配不到关键字，是否全局搜索",headerSearch,$('#ipt-destination').val());
                    //$('#txtSearch').val('');
                }

            }//$(".categories_Addmore2").show();

        }
    },

    // 首页机票查询搜索按钮
    btnFlightSeach: function () {
        var allClear = true;
        var inputs = $('#flightSearchForm .sho .portinput');
        inputs.each(function () {
            if (allClear && ($(this).val() == undefined || $(this).val() == '')) {
                //window.console.info($(this).attr('name') + ':' + $(this).val() + "," + $(this).attr('type'));
                $(this).click();
                $(this).focus();
                allClear = false;
                return;
            }
        });
        var leaveCity = getCityAndPortId($('#flightSearchForm input[name="leaveCityName"]').val(), 2);
        if (leaveCity[0] > 0) {
            $('#flightSearchForm input[name="leaveCity"]').val(leaveCity[0]);
            $('#flightSearchForm input[name="leavePort"]').val(leaveCity[1]);
        } else {
            return;
        }
        var arriveCity = getCityAndPortId($('#flightSearchForm input[name="arriveCityName"]').val(), 2);
        if (arriveCity[0] > 0) {
            $('#flightSearchForm input[name="arriveCity"]').val(arriveCity[0]);
            $('#flightSearchForm input[name="arrivePort"]').val(arriveCity[1]);
        }
        var transitCity = getCityAndPortId($('#flightSearchForm input[name="transitCityName"]').val(), 2);
        if (transitCity[0] > 0) {
            $('#flightSearchForm input[name="transitCity"]').val(transitCity[0]);
            $('#flightSearchForm input[name="transitPort"]').val(transitCity[1]);
        }
        var arriveCity2 = getCityAndPortId($('#flightSearchForm input[name="arriveCityName2"]').val(), 2);
        if (arriveCity2[0] > 0) {
            $('#flightSearchForm input[name="arriveCity2"]').val(arriveCity2[0]);
            $('#flightSearchForm input[name="arrivePort2"]').val(arriveCity2[1]);
        }
        var inputs2 = $('#flightSearchForm .sho .portinput');
        inputs2.each(function () {
            if (allClear && ($(this).val() == undefined || $(this).val() == '')) {
                promptWarn("请选择正确的城市");
                allClear = false;
                return;
            }
        });
        if (allClear) {
            $('#flightSearchForm').submit();
        }
    },

    // 首页火车查询搜索按钮
    btnTrainSeach: function () {
        var allClear = true;
        var inputs = $('#trainSearchForm .sho .portinput');
        inputs.each(function () {
            if (allClear && ($(this).val() == undefined || $(this).val() == '')) {
                $(this).click();
                $(this).focus();
                allClear = false;
                return;
            }
        });
        var leaveCity = getCityAndPortId($('#trainSearchForm input[name="leaveCityName"]').val(), 1);
        if (leaveCity[0] > 0) {
            $('#trainSearchForm input[name="leaveCity"]').val(leaveCity[0]);
            $('#trainSearchForm input[name="leavePort"]').val(leaveCity[1]);
        } else {
            return;
        }
        var arriveCity = getCityAndPortId($('#trainSearchForm input[name="arriveCityName"]').val(), 1);
        if (arriveCity[0] > 0) {
            $('#trainSearchForm input[name="arriveCity"]').val(arriveCity[0]);
            $('#trainSearchForm input[name="arrivePort"]').val(arriveCity[1]);
        }
        var transitCity = getCityAndPortId($('#trainSearchForm input[name="transitCityName"]').val(), 1);
        if (transitCity[0] > 0) {
            $('#trainSearchForm input[name="transitCity"]').val(transitCity[0]);
            $('#trainSearchForm input[name="transitPort"]').val(transitCity[1]);
        }
        var arriveCity2 = getCityAndPortId($('#trainSearchForm input[name="arriveCityName2"]').val(), 1);
        if (arriveCity2[0] > 0) {
            $('#trainSearchForm input[name="arriveCity2"]').val(arriveCity2[0]);
            $('#trainSearchForm input[name="arrivePort2"]').val(arriveCity2[1]);
        }
        var inputs2 = $('#trainSearchForm .sho .portinput');
        inputs2.each(function () {
            if (allClear && ($(this).val() == undefined || $(this).val() == '')) {
                promptWarn("请选择正确的城市" + $(this).attr('name'));
                allClear = false;
                return;
            }
        });
        if (allClear) {
            $('#trainSearchForm').submit();
        }
    },

    // 首页酒店查询搜索按钮
    btnHotelSeach: function () {
        var cityId_input = $('#hotelSearchForm').find('.input').eq(0);
        var cityId = getCityId(cityId_input.val());
        if (!cityId) {
            $(".hotel_des").children(".list_con").children(".categories_Addmore2").show();
            return;
        } else {
            $('#hotelSearchForm input[name="cities"]').attr('data-areaId', cityId);
            $('#hotel_cityId').val(cityId);
        }
        var startDate = $('#hotelSearchForm input[name="startDate"]').val(); // 返程日期
        if (!startDate) {
            $('#hotelSearchForm input[name="startDate"]').focus();
//			promptMessage("请选择入住日期！");
            return;
        }
        var endDate = $('#hotelSearchForm input[name="endDate"]').val(); // 返程日期
        if (!endDate) {
//			onClick="WdatePicker()"
            $('#hotelSearchForm input[name="endDate"]').focus();
//			promptMessage("请选择离开日期！");
            return;
        }
        if ($("body").hasClass("Hotels")) {
            setToHash("areaId", cityId);
            setToHash("areaName", cityId_input.val());
            setToHash("lDate-3", startDate);
            setToHash("rDate-3", endDate);
            setToHash("gjc", $('#gjc').val());
        }
        $('#hotelSearchForm').submit();
    },

    // 首页门票景点查询搜索按钮
    btnScenicSeach: function () {
        $('#scenicSearchForm').submit();
    },

    // 推荐行程
    recPlanByDest: function () {
        var tempValue = $("#input_planId").val();
        if (tempValue) {
            var tempValue = $("#input_planId").val();
            tempValue = SearcherBtn.replaceChar(tempValue);
            var tempArr = tempValue.split("；");
            //去数组除空元素
            var newArr = SearcherBtn.trimArr(tempArr);
            //判断一下是否有重复
            newArr = SearcherBtn.unique(newArr);
            if (newArr.length > 0) {
                var url = "/lvxbang/destination/getAreaIdsWithAbroad.jhtml";
                $.post(url, {"nameStr": newArr.join(",")},
                    function (data) {
                        if (data.success) {
                            var internalSearchHistory = JSON.parse(getCookie("internal_search_history"));
                            internalSearchHistory = SearcherBtn.unique(data.internal[1].concat(internalSearchHistory));
                            if (internalSearchHistory.length > 5) {
                                internalSearchHistory = internalSearchHistory.slice(0, 5);
                            }
                            setCookie("internal_search_history", JSON.stringify(internalSearchHistory));
                            var abroadSearchHistory = JSON.parse(getCookie("abroad_search_history"));
                            abroadSearchHistory = SearcherBtn.unique(data.abroad[1].concat(abroadSearchHistory));
                            if (abroadSearchHistory.length > 5) {
                                abroadSearchHistory = abroadSearchHistory.slice(0, 5);
                            }
                            setCookie("abroad_search_history", JSON.stringify(abroadSearchHistory));
                            var ids = data.internal[0].join(",");
                            if (data.internal[0].length > 0 && data.abroad[0].length > 0) {
                                ids += "," ;
                            }
                            ids += data.abroad[0].join(",")
                            window.location.href = $("#plan_path").val() + "/plan_list.html?cityIdStr=" + ids;
                        } else {
                            promptWarn("无此目的地，请更换！");
                        }
                    },"json");
            } else {
                $(".categories_Addmore2").show();
                promptWarn("请输入目的地！");
                $("#input_planId").val("");
            }

        } else {
            $(".categories_Addmore2").show();
            promptMessage("请输入目的地！");

        }

    },

    // 自主设计
    scenicByDest: function () {
        //每次自主设计前清空右边我的行程
        FloatEditor.emptyEditor();
        //首页自主设计入口
        var tempValue = $("#input_planId").val();
        if (tempValue) {
            tempValue = SearcherBtn.replaceChar(tempValue);
            var tempArr = tempValue.split("；");
            var newArr = SearcherBtn.trimArr(tempArr);
            //判断一下是否有重复
            newArr = SearcherBtn.unique(newArr);
            //console.log("已处理：" + newArr);
            if (newArr.length > 0) {
                var url = "/lvxbang/destination/getAreaIdsWithAbroad.jhtml";
                $.post(url, {"nameStr": newArr.join(",")},
                    function (data) {
                        if (data.success) {
                            var internalSearchHistory = JSON.parse(getCookie("internal_search_history"));
                            internalSearchHistory = SearcherBtn.unique(data.internal[1].concat(internalSearchHistory));
                            if (internalSearchHistory.length > 5) {
                                internalSearchHistory = internalSearchHistory.slice(0, 5);
                            }
                            setCookie("internal_search_history", JSON.stringify(internalSearchHistory));
                            var abroadSearchHistory = JSON.parse(getCookie("abroad_search_history"));
                            abroadSearchHistory = SearcherBtn.unique(data.abroad[1].concat(abroadSearchHistory));
                            if (abroadSearchHistory.length > 5) {
                                abroadSearchHistory = abroadSearchHistory.slice(0, 5);
                            }
                            setCookie("abroad_search_history", JSON.stringify(abroadSearchHistory));
                            var ids = data.internal[0].join(",");
                            if (data.internal[0].length > 0 && data.abroad[0].length > 0) {
                                ids += "," ;
                            }
                            ids += data.abroad[0].join(",")
                            window.location.href = $("#scenic_path").val() + "/scenic_list.html?cityIdStr=" + ids;
                        } else {
                            promptWarn("无此目的地，请更换！");
                        }
                    },"json");
            } else {
                promptWarn("请输入目的地！");
                $(".categories_Addmore2").show();
                $("#input_planId").val("");
            }
        } else {
            $(".categories_Addmore2").show();
            promptWarn("请输入目的地！");
        }

    },

    selfLinelist: function () {
        var startCityId = getCityId($("#selfStartCity").val());
        var arriveCityId = getCityId($("#selfArriveCity").val());
        var startDate = $("#selfStartDate").val();
        if (arriveCityId > 0) {
            location.href = $("#zizhu_path").val() + "/self_tour_" + arriveCityId + ".html?startCityId=" + startCityId + "&startDate=" + startDate;
        } else {
            promptWarn("请输入目的地！");
        }
    },

    //删除元素
    deleteArr: function (arr, index) {
        if (isNaN(arr) || arr > this.length) {
            return false;
        }
        return this.splice(arr, 1);
    },

    //增加数组元素
    addArr: function (arr, newItem) {
        return arr.concat(newItem);
    },
    //去除数组重复项
    unique: function (arr) {
        var result = [], hash = {};
        for (var i = 0, elem; (elem = arr[i]) != null; i++) {
            if (!hash[elem]) {
                result.push(elem);
                hash[elem] = true;
            }
            if (result[i] == "" || typeof (result[i]) == "undefined") {
                result.splice(i, 1);
            }
        }
        return result;
    },
    //去除数组空值项
    trimArr: function (arr) {
        var result = [], hash = {};
        result = $.grep(arr, function () {
            return this != '';
        });
        return result;
    },
    //js去除字符串空格
    trim: function (str) {
        return str.replace(/(^\s*)|(\s*$)/g, "");
    },

    //中英文分号处理
    replaceChar: function (str) {
        str = str.replace(/\;/g, "；");
        //str = str.replace(/\市/g , "；");
        return str;
    }
}
