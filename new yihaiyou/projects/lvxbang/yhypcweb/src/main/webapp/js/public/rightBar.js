/**
 * Created by huangpeijie on 2017-01-12,0012.
 */
var RightBar = {
    init: function () {
        RightBar.bindEvent();
        RightBar.initData();
        RightBar.syncScenicList();
    },

    initData: function () {
        RightBar.initDemand();
        RightBar.initScenicList();
    },

    initDemand: function () {
        var planDemand = RightBar.getPlanDemand();
        var rightPlayDate = $("#rightPlayDate");
        rightPlayDate.val(planDemand.playDate);
        var playDayNum = $(".rightBar .select_contain span.num");
        var playDaySub = $(".rightBar .select_contain span.sub");
        var playDayAdd = $(".rightBar .select_contain span.add");
        playDayNum.text(planDemand.playDay);
        playDaySub.on("click", function () {
            var playDay = parseInt(playDayNum.text());
            playDay--;
            if (playDay <= 1) {
                playDay = 1;
            }
            playDayNum.text(playDay);
            var demand = RightBar.getPlanDemand();
            demand.playDay = playDay;
            RightBar.setPlanDemand(demand);
        });
        playDayAdd.on("click", function () {
            var playDay = parseInt(playDayNum.text());
            playDay++;
            playDayNum.text(playDay);
            var demand = RightBar.getPlanDemand();
            demand.playDay = playDay;
            RightBar.setPlanDemand(demand);
        });

        var plan = $(".rightBar .buildMarch ul li");
        plan.removeClass("checkit").eq(planDemand.hour - 1).addClass("checkit");
        plan.on("click", function () {
            if (!$(this).hasClass('checkit')) {
                $(this).addClass('checkit').siblings().removeClass('checkit');
                var hour = $(this).data("hour");
                var demand = RightBar.getPlanDemand();
                demand.hour = hour;
                RightBar.setPlanDemand(demand);
            }
        });
    },

    setDate: function() {
        var demand = RightBar.getPlanDemand();
        demand.playDate = moment($("#rightPlayDate").val()).format("YYYY-MM-DD");
        RightBar.setPlanDemand(demand);
    },

    initScenicList: function () {
        var selectedScenic = RightBar.getSelectedScenic();
        var scenicList = $(".select_contain .selected_list");
        scenicList.empty();
        var html = "";
        for (var id in selectedScenic) {
            var scenic = selectedScenic[id];
            scenic.id = id;
            html += template("right_scenic_item", scenic);
        }
        scenicList.append(html);
        if (scenicList.find("li").length > 0) {
            $('.march_atention').hide();
        } else {
            $('.march_atention').show();
        }
        if (typeof(PlanScenicDetail) != "undefined") {
            PlanScenicDetail.initSelected();
        }
    },

    setSelectedScenic: function (selectedScenic) {
        setUnCodedCookie("selectedScenic", JSON.stringify(selectedScenic));
    },

    getSelectedScenic: function () {
        var selectedScenicStr = getUnCodedCookie("selectedScenic");
        var selectedScenic = {};
        if (selectedScenicStr != null && selectedScenicStr != "" && selectedScenicStr != "null" && selectedScenicStr != "undefined") {
            selectedScenic = JSON.parse(selectedScenicStr);
        }
        return selectedScenic;
    },

    delSelectedScenic: function () {
        delCookie("selectedScenic");
    },

    setPlanDemand: function (planDemand) {
        setUnCodedCookie("planDemand", JSON.stringify(planDemand));
    },

    getPlanDemand: function () {
        var planDemandStr = getUnCodedCookie("planDemand");
        var planDemand;
        if (planDemandStr != null && planDemandStr != "") {
            planDemand = JSON.parse(planDemandStr);
        } else {
            planDemand = {
                playDate: moment(new Date).format("YYYY-MM-DD"),
                playDay: 1,
                hour: 2
            };
        }
        return planDemand;
    },

    delPlanDemand: function () {
        delCookie("planDemand");
    },

    setSelectedFerry: function (selectedFerry) {
        setUnCodedCookie("selectedFerry", JSON.stringify(selectedFerry));
    },

    getSelectedFerry: function () {
        var selectedFerryStr = getUnCodedCookie("selectedFerry");
        var selectedFerry = {};
        if (selectedFerryStr != null && selectedFerryStr != "") {
            selectedFerry = JSON.parse(selectedFerryStr);
        }
        return selectedFerry;
    },

    delSelectedFerry: function () {
        delCookie("selectedFerry");
    },

    setDaysRequest: function (daysRequest) {
        setUnCodedCookie("daysRequest", JSON.stringify(daysRequest));
    },

    getDaysRequest: function () {
        var daysRequestStr = getUnCodedCookie("daysRequest");
        var daysRequest = {};
        if (daysRequestStr != null && daysRequestStr != "") {
            daysRequest = JSON.parse(daysRequestStr);
        }
        return daysRequest;
    },

    delDaysRequest: function () {
        delCookie("daysRequest");
    },

    setSelectedTourist: function (selectedTourist) {
        setUnCodedCookie("selectedTourist", JSON.stringify(selectedTourist));
    },

    getSelectedTourist: function () {
        var selectedTouristStr = getUnCodedCookie("selectedTourist");
        var selectedTourist = {};
        if (selectedTouristStr != null && selectedTouristStr != "") {
            selectedTourist = JSON.parse(selectedTouristStr);
        }
        return selectedTourist;
    },

    delSelectedTourist: function () {
        delCookie("selectedTourist");
    },

    setPlanDetail: function (planDetail) {
        setLocalStorage("planDetail", JSON.stringify(planDetail));
    },

    getPlanDetail: function () {
        var planDetailStr = getLocalStorage("planDetail");
        var planDetail = null;
        if (planDetailStr != null && planDetailStr != "") {
            planDetail = JSON.parse(planDetailStr);
        }
        return planDetail;
    },

    delPlanDetail: function () {
        delLocalStorage("planDetail");
    },

    bindEvent: function () {
        RightBar.rightBar();
        RightBar.rightMove();
        RightBar.rightList();
        RightBar.optimize();
        RightBar.deleteScenic();
        RightBar.deleteAllScenic();
        RightBar.hideBtn();
    },

    // 侧栏
    rightBar: function () {
        var winHeight = $(window).height();
        $('.rightBar').css({'height': winHeight});
    },

    rightMove: function () {
        var Li = $('.rightBar ul.ulleft li');
        Li.hover(function () {
                if ($(this).is(":animated")) {
                    $(this).stop(true);
                }
                $(this).addClass('active');
                $(this).animate({'width': '120px'}, 350, function () {
                    $(this).find('.word').fadeIn(20);
                });
            }, function () {
                if ($(this).is(":animated")) {
                    $(this).stop(true);
                }
                $(this).find('.word').fadeOut(20);
                $(this).animate({'width': '40px'}, 250, function () {
                    $(this).removeClass('active');
                });

            }
        );
    },

//我的行程显示隐藏
    rightList: function () {
        var march = $('.myMarch');
        var toTop = $('.toTop');
        var browser = window.navigator;
        march.on("click", function () {
            RightBar.showRightBar();
        });
        toTop.on("click", function () {
            if (browser.userAgent.indexOf("Chrome") >= 0) {
                $('body').animate({'scrollTop': 0}, 300);
            } else {
                scroll(0, 0);
            }
        });
    },

    showRightBar: function (show) {
        var rightBar = $('.rightBar');
        if (show == true) {
            rightBar.animate({'right': '0px'}, 100);
        } else if (show == false) {
            rightBar.animate({'right': '-265px'}, 100);
        } else {
            if (rightBar.css('right') == '0px') {
                rightBar.animate({'right': '-265px'}, 100);
            } else {
                rightBar.animate({'right': '0px'}, 100);
            }
        }
    },

    hideBtn: function(){
        var rightBar = $('.rightBar');
        var hidebtn =  $('.select_contain .con_title .hidebtn');
            hidebtn.on('click',function(){
                rightBar.animate({'right': '-265px'}, 100);
            })
        },

    optimize: function () {
        $("#optimize").on("click", function () {
            if ($.isEmptyObject(RightBar.getSelectedScenic())) {
                alert("请选择景点");
                return;
            }
            YhyUser.checkLogin(function (result) {
                if (result.success) {
                    toOptimize();
                } else {
                    YhyUser.goLogin(toOptimize);
                }
            });
        });

        function toOptimize() {
            RightBar.delPlanDetail();
            location.href = "/yhypc/plan/detail.jhtml";
        }
    },

    deleteScenic: function () {
        $("#rightScenicList").delegate("li .delete", "click", function () {
            var selectedScenic = RightBar.getSelectedScenic();
            var id = $(this).data("id");
            delete selectedScenic[id];
            RightBar.setSelectedScenic(selectedScenic);
            RightBar.initScenicList();
        });
    },

    deleteAllScenic: function () {
        $(".rightBar .clearall").on("click", function () {
            RightBar.delSelectedScenic();
            RightBar.initScenicList();
        });
    },

    syncScenicList: function () {
        setInterval(function () {
            RightBar.initScenicList();
        }, 5000);
    }
};

$(document).ready(function () {
    RightBar.init();
});