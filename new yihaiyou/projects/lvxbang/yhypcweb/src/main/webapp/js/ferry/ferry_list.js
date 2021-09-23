/**
 * Created by HMLY on 2016-12-20.
 */

var FerryList = {
    pager: null,
    init: function() {
        FerryList.initJsp();
        FerryList.detailPop();
        var status = $("#status").val();
        if (status == "backLogin") {
            Popup.openFerryLogin(function() {});
        } else if (status == "realname") {
            Popup.openRealname(function() {});
        }
    },
    /*其他票型   详情   弹窗*/
    detailPop:function(){
        $(".else-details").on("click",function(){
            $(this).siblings(".detail-pop").show();
            $("body,html").css({"height":"100%","overflow":"hidden"});
        });
        $(".pop-close").on("click",function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $(this).closest(".detail-pop").hide();
            $("body,html").css({"height":"auto","overflow":"auto"});
        });
        $(".detail-pop").on("click",function(){
            $(this).hide();
            $("body,html").css({"height":"auto","overflow":"auto"});
        });
        $(".detail-popWrap").on("click",function(ev){
            var ev = ev || event;
            ev.stopPropagation();
            $(this).siblings(".detail-pop").show();
            $("body,html").css({"height":"100%","overflow":"hidden"});
        });
    },
    initJsp: function() {

        //$('.date-input').datetimepicker({
        //    closeOnDateSelect: true,
        //    scrollInput: false,
        //    todayButton: false,
        //    timepicker: false,
        //    format: "YYYY-MM-DD",
        //    formatDate: "YYYY-MM-DD",
        //    //value: new Date(),
        //    minDate: 0
        //});
        if (!$('.date-input').val()) {
            $('.date-input').val(moment().format("YYYY-MM-DD"));
        }

        if ($("#flight-number").attr("form-data-flight-line-id")) {
            $("#flight-number").val($("#flight-number").attr("form-data-flight-line-id"));
            $.each($(".route-datalist").children(), function(i, perRoute) {
                if ($(perRoute).attr("data-number") == $("#flight-number").attr("form-data-flight-line-id")) {
                    $("#routeInputBtn").val($(perRoute).html());
                }
            });
        }

        FerryList.initDateArr();

        $(".search-btn").click(function() {
            var initDate = $('.date-input').val();
            $("#sel-date").val(initDate);
            FerryList.initDateArr();
            FerryList.getFerryList();
        });

        FerryList.createPager();
        FerryList.getFerryList();
    },

    createPager: function() {
        var options = {
            countUrl: "/yhypc/ferry/getTotalFerryPage.jhtml",
            resultCountFn: function (result) {
                return parseInt(result[0]);
            },
            pageRenderFn: function (pageNo, pageSize, data) {
                $('.tablelist').empty();
                //$("#loading").show();
                scroll(0, 0);
                data.pageIndex = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url: '/yhypc/ferry/ferryList.jhtml',
                    data: data,
                    progress: true,
                    success: function (result) {
                        $('.tablelist').empty();
                        //$("#loading").hide();
                        //$("#totalProduct").html(data.page.totalCount);

                        if (result.flightList.length > 0) {
                            $(".none-data").addClass("hidden");
                        } else {
                            $(".none-data").removeClass("hidden");
                            return;
                        }
                        for (var i = 0; i < result.flightList.length; i++) {
                            var s = result.flightList[i];
                            result.flightList[i]['index'] = i + 1;
                            if(parseInt(s.id) < 2000433){
                                s.shortComment = formatString(s.shortComment);
                            }
                            s.flightLineName = $("#routeInputBtn").val();
                            var $f = $(template("tpl-ferry-list-item", s));
                            $('.tablelist').append($f);
                            $f.data("ferry", s);
                        }
                    }
                });
            }
        };
        FerryList.pager = new Pager(options);
    },

    getFerryList: function() {
        var search = {};
        if ($("#sel-date").val()) {
            search['date'] = $("#sel-date").val();
        }
        if ($("#flight-number").val()) {
            search['flightLineId'] = $("#flight-number").val();
        }
        FerryList.pager.init(search);
    },

    //初始化日历导航条
    initDateArr: function() {
        var initDate = $('.date-input').val();
        $("#sel-date").val(initDate);
        $(".date-content").empty();
        var dateContendLi = '';
        for(var i=0; i < 9; i++) {
            var perDate = moment(initDate).add(i, "days");
            var perDateFt = perDate.format("MM-DD");
            var perFullDateFt = perDate.format("YYYY-MM-DD");
            var perDateWeek = perDate.day();
            if (i == 0) {
                dateContendLi += '<li class="active" data-date="'+ perFullDateFt +'">'+ perDateFt +'&nbsp;'+ FerryList.getWeek(perDateWeek) +'</li>';
            } else {
                dateContendLi += '<li data-date="'+ perFullDateFt +'">'+ perDateFt +'&nbsp;'+ FerryList.getWeek(perDateWeek) +'</li>';
            }
        }
        $(".date-content").append(dateContendLi);

        FerryList.isFirstDate();
        FerryList.checkDate();
        FerryList.selectDateLi();
    },

    //日历导航条-加处理
    addDateArr: function() {
        var dateLiArr = $(".date-content").children();
        $(".date-content").empty();
        $.each(dateLiArr, function(i, perDateLi) {
            if (i > 0) {
                $(".date-content").append(perDateLi);
                //tempDateLiArr.push(perDateLi);
            }
        });
        var lastDateLi = $(dateLiArr.last()).attr("data-date");
        var perDate = moment(lastDateLi).add(1, "days");
        var perDateFt = perDate.format("MM-DD");
        var perFullDateFt = perDate.format("YYYY-MM-DD");
        var perDateWeek = perDate.day();
        var dateContendLi = '<li data-date="'+ perFullDateFt +'">'+ perDateFt +'&nbsp;'+ FerryList.getWeek(perDateWeek) +'</li>';
        $(".date-content").append(dateContendLi);

        FerryList.isFirstDate();
        FerryList.checkDate();
        FerryList.selectDateLi();
    },

    //日历导航条-减处理
    subDateArr: function() {
        if (FerryList.isFirstDate() < 0) {
            return;
        }
        var dateLiArr = $(".date-content").children();
        var firstDateLi = $(dateLiArr.first()).attr("data-date");
        $(".date-content").empty();
        $.each(dateLiArr, function(i, perDateLi) {
            if (i < dateLiArr.length - 1) {
                $(".date-content").append(perDateLi);
            }
        });

        var perDate = moment(firstDateLi).subtract(1, "days");
        var perDateFt = perDate.format("MM-DD");
        var perFullDateFt = perDate.format("YYYY-MM-DD");
        var perDateWeek = perDate.day();
        var dateContendLi = '<li data-date="'+ perFullDateFt +'">'+ perDateFt +'&nbsp;'+ FerryList.getWeek(perDateWeek) +'</li>';
        $($(".date-content").children().first()).before(dateContendLi);

        
        FerryList.isFirstDate();
        FerryList.checkDate();
        FerryList.selectDateLi();
    },

    //控制当前天之前不可选
    isFirstDate: function() {
        var dateLiArr = $(".date-content").children();
        var firstDateLi = $(dateLiArr.first()).attr("data-date");
        var today = new Date();
        if ((moment(firstDateLi) - moment(today)) <= 0) {
            $(".date-prev").addClass("not-allowed");
            return -1;
        } else {
            $(".date-prev").removeClass("not-allowed");
            return 1;
        }
    },

    //选择日历导航条
    selectDateLi: function() {
        $.each($(".date-content").children(), function(i, perLi) {
            $(perLi).click(function() {
                $(".date-content").children().removeClass("active");
                $(this).addClass("active");
                $('#sel-date').val($(this).attr("data-date"));
                $('.date-input').val($(this).attr("data-date"));
                FerryList.getFerryList();
            });
        });
    },

    //检查日历
    checkDate: function() {
        var dateLiArr = $(".date-content").children();
        var initDate = $('#sel-date').val();
        $.each(dateLiArr, function(i, perDateLi) {
            var perDate = $(perDateLi).attr("data-date");
            console.log((moment(initDate) - moment(perDate)));
            if ((moment(initDate) - moment(perDate)) == 0) {
                $(perDateLi).addClass("active");
            }
        });
    },

    //星期处理
    getWeek: function(perDateWeek) {
        if (perDateWeek == 1) {
            return '周一';
        } else if (perDateWeek == 2) {
            return '周二';
        } else if (perDateWeek == 3) {
            return '周三';
        } else if (perDateWeek == 4) {
            return '周四';
        } else if (perDateWeek == 5) {
            return '周五';
        } else if (perDateWeek == 6) {
            return '周六';
        } else {
            return '周日';
        }
    },

    nextOrder: function (id) {
        YhyUser.checkLogin(function (result) {
            if (result.success) {
                next();
            } else {
                YhyUser.goLogin(next);
            }
        });

        function next() {
            $.ajax({
                url: "/yhypc/ferry/checkReal.jhtml",
                success: function (data) {
                    if (data.success) {
                        $("#" + id).submit();
                    } else {
                        Popup.openRealname(function () {
                            $("#" + id).submit();
                        });
                    }
                }
            });
        }
    },

    //驴迹
    glyOrder: function () {
        YhyUser.checkLogin(function (result) {
            if (result.success) {


            } else {
                YhyUser.goLogin(next);
            }
        });

    }
};

$(function(){
    FerryList.init();
    $("#routeDatalist").hide();
    /*列表展开*/
    datalist($("#flight-number"), $("#routeInputBtn"), $("#routeDatalist"));
});

/*列表 展开 收缩*/
function datalist(formObj, obj, target){
    obj.click(function(ev){
        var ev = ev || event;
        ev.stopPropagation();
        if(target[0].style.display == 'none'){
            target.slideDown("800");
        }else{
            target.slideUp("800");
        }
    });
    target.find("li").click(function(){
        var inputVal = $(this).html();
        var flightLineId = $(this).attr("data-number");
        obj.val(inputVal);
        formObj.val(flightLineId);
        target.slideUp();
    });
    $("body").click(function(){
        target.slideUp();
    });
}
