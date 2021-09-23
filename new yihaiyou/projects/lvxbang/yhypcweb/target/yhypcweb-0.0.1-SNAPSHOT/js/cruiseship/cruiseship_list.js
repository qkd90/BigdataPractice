/**
 * Created by HMLY on 2016-12-20.
 */

var CruiseShipList = {
    pager: null,
    activeObj: $(".li-active").attr("data-order-column"),
    init: function() {
        CruiseShipList.IE8Css();
        $(".triangle-down").siblings(".collapse-wrap").hide();
        CruiseShipList.collapseFn($(".triangle-down"));
        CruiseShipList.createPager();
        //CruiseShipList.getMonthRange(5);
        CruiseShipList.initJsp();
        CruiseShipList.getCruiseshipList();
    },

    initJsp: function() {

        $(".select-sort").find("li").click(function() {
            if (CruiseShipList.activeObj == $(this).attr("data-order-column")) {
                if ($(this).attr("data-order-column") != "satisfaction") {
                    if ($(this).attr("data-order-type") == "desc") {
                        $(this).attr("data-order-type", "asc");
                        $(this).find("span").removeClass("desc-down");
                        $(this).find("span").addClass("desc-up");
                    } else {
                        $(this).attr("data-order-type", "desc");
                        $(this).find("span").removeClass("desc-up");
                        $(this).find("span").addClass("desc-down");
                    }
                }
            }
            $(".select-sort").children().removeClass("li-active");
            $(this).addClass("li-active");
            CruiseShipList.activeObj = $(this).attr("data-order-column");
            CruiseShipList.getCruiseshipList();     //查询游轮列表数据
        });




        if ($(":input[name='route']:checked").attr("data-name")) {
            var routeBtn = '<button id="btn-route" type="button" data-name="route" onclick="CruiseShipList.delCondition(this)">'+ $(":input[name='route']:checked").attr("data-name") +'</button>';
            $("#form-sum-condition").append(routeBtn);
            $(".clear-all-sel-label").show();
        }

        if ($(":input[name='brand']:checked").attr("data-name")) {
            var routeBtn = '<button id="btn-brand" type="button" data-name="brand" onclick="CruiseShipList.delCondition(this)">'+ $(":input[name='brand']:checked").attr("data-name") +'</button>';
            $("#form-sum-condition").append(routeBtn);
            $(".clear-all-sel-label").show();
        }

        if ($(":input[name='date']").val()) {
            var routeBtn = '<button id="btn-date-range" type="button" data-name="date" onclick="CruiseShipList.delCondition(this)">'+ $(":input[name='date']").val() +'</button>';
            $("#form-sum-condition").append(routeBtn);
            $(".clear-all-sel-label").show();
        }

        if ($(":input[name='duration']:checked").attr("data-name")) {
            var routeBtn = '<button id="btn-duration-range" type="button" data-name="duration" onclick="CruiseShipList.delCondition(this)">'+ $(":input[name='duration']:checked").attr("data-name") +'</button>';
            $("#form-sum-condition").append(routeBtn);
            $(".clear-all-sel-label").show();
        }


        $(".clear-all-sel-label").click(function() {
            $(":input[name='route']").first().prop("checked", "checked");
            $(":input[name='brand']").first().prop("checked", "checked");
            $(":input[name='duration']").first().prop("checked", "checked");
            $(":input[name='date']").val("");
            $("#form-sum-condition").empty();
            $(".clear-all-sel-label").hide();
            CruiseShipList.getCruiseshipList();     //查询游轮列表数据
        });


        $(":input[name='route']").click(function() {
            $("#btn-route").remove();
            if (!$(this).attr("data-name")) {
                if ($("#form-sum-condition").children().length == 0) {
                    $(".clear-all-sel-label").hide();
                }
            } else {
                var routeBtn = '<button id="btn-route" type="button" data-name="route" onclick="CruiseShipList.delCondition(this)">'+ $(this).attr("data-name") +'</button>';
                $("#form-sum-condition").append(routeBtn);
                $(".clear-all-sel-label").show();
            }
            CruiseShipList.getCruiseshipList();     //查询游轮列表数据
        });
        $(":input[name='brand']").click(function() {
            $("#btn-brand").remove();
            if (!$(this).attr("data-name")) {
                if ($("#form-sum-condition").children().length == 0) {
                    $(".clear-all-sel-label").hide();
                }
            } else {
                var routeBtn = '<button id="btn-brand" type="button" data-name="brand" onclick="CruiseShipList.delCondition(this)">'+ $(this).attr("data-name") +'</button>';
                $("#form-sum-condition").append(routeBtn);
                $(".clear-all-sel-label").show();
            }
            CruiseShipList.getCruiseshipList();     //查询游轮列表数据
        });
        //$(":input[name='date']").click(function() {
        //    $("#btn-date-range").remove();
        //    if (!$(this).attr("data-name")) {
        //        if ($("#form-sum-condition").children().length == 0) {
        //            $(".clear-all-sel-label").hide();
        //        }
        //    } else {
        //        var routeBtn = '<button id="btn-date-range" type="button" data-name="date" onclick="CruiseShipList.delCondition(this)">'+ $(this).attr("data-name") +'</button>';
        //        $("#form-sum-condition").append(routeBtn);
        //        $(".clear-all-sel-label").show();
        //    }
        //    CruiseShipList.getCruiseshipList();     //查询游轮列表数据
        //});
        $(":input[name='duration']").click(function() {
            $("#btn-duration-range").remove();
            if (!$(this).attr("data-name")) {
                if ($("#form-sum-condition").children().length == 0) {
                    $(".clear-all-sel-label").hide();
                }
            } else {
                var routeBtn = '<button id="btn-duration-range" type="button" data-name="duration" onclick="CruiseShipList.delCondition(this)">'+ $(this).attr("data-name") +'</button>';
                $("#form-sum-condition").append(routeBtn);
                $(".clear-all-sel-label").show();
            }
            CruiseShipList.getCruiseshipList();     //查询游轮列表数据
        });



    },

    delCondition: function(target) {
        if ($(target).attr("data-name") == "date") {
            $(":input[name='"+ $(target).attr("data-name") +"']").val("");
        } else {
            $(":input[name='"+ $(target).attr("data-name") +"']:first").prop("checked", "checked");
        }

        $(target).remove();
        if ($("#form-sum-condition").children().length == 0) {
            $(".clear-all-sel-label").hide();
        }
        CruiseShipList.getCruiseshipList();     //查询游轮列表数据
    },

    createPager: function() {
        var options = {
            countUrl: "/yhypc/cruiseShip/getTotalPage.jhtml",
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
                    url: '/yhypc/cruiseShip/getCruiseshipList.jhtml',
                    progress: true,
                    data: data,
                    success: function(data) {
                        $('.tablelist').empty();
                        //$("#loading").hide();
                        $("#totalProduct").html(data.page.totalCount);
                        for (var i = 0; i < data.cruiseShipResponses.length; i++) {
                            var s = data.cruiseShipResponses[i];
                            if(parseInt(s.id) < 2000433){
                                s.shortComment = formatString(s.shortComment);
                            }
                            var result = $(template("tpl-cruiseship-list-item", s));
                            $('.tablelist').append(result);
                            result.data("cruiseship", s);
                        }
                    },
                    error: function() {}
                });
            }
        };
        CruiseShipList.pager = new Pager(options);
    },

    getCruiseshipList: function() {
        var search = {};

        if ($(":input[name='route']:checked").val()) {
            search['cruiseShipSearchRequest.route'] = $(":input[name='route']:checked").val();
        }

        if ($(":input[name='brand']:checked").val()) {
            search['cruiseShipSearchRequest.brand'] = $(":input[name='brand']:checked").val();
        }

        if ($(":input[name='date']").val()) {
            search['cruiseShipSearchRequest.dateRange[0]'] = $(":input[name='date']").val();
            //search['cruiseShipSearchRequest.dateRange[1]'] = $(":input[name='date']:checked").attr("data-max-date");

        }

        if ($(":input[name='duration']:checked").attr("data-min-day") && $(":input[name='duration']:checked").attr("data-max-day")) {
            search['cruiseShipSearchRequest.dayRange[0]'] = $(":input[name='duration']:checked").attr("data-min-day");
            search['cruiseShipSearchRequest.dayRange[1]'] = $(":input[name='duration']:checked").attr("data-max-day");

        }
        search['cruiseShipSearchRequest.orderColumn'] = $(".content-nav .li-active").attr("data-order-column");
        search['cruiseShipSearchRequest.orderType'] = $(".content-nav .li-active").attr("data-order-type");
        //console.log(search);
        CruiseShipList.pager.init(search);
    },


    collapseFn: function (obj){
        obj.click(function(){
            if(!$(this).is(".active")){
                obj.siblings(".collapse-wrap").slideUp("1000");
                obj.removeClass("active");
                $(this).siblings(".collapse-wrap").slideDown("1000");
                $(this).addClass("active");
            }else{
                $(this).removeClass("active");
                $(this).siblings(".collapse-wrap").slideUp("1000");
            }
        });
    },

    getMonthRange: function (rangeLength){
        var dateList = [];
        if (!rangeLength) {
            return;
        }
        for (var i = 1; i <= rangeLength; i++) {
            var year = moment().get('year');
            var month = moment().get('month') + i;  // 0 to 11
            var firstDate = year + '-' + month + '-' + "01";
            var  day = new Date(year, month,0);
            var lastdate = year + '-' + month + '-' + day.getDate();//获取当月最后一天日期
            var dateObj = {
                'minDate':firstDate,
                'maxDate':lastdate,
                'text': year + "年" + month + "月"
            };
            dateList[i-1] = dateObj;
        }

        $.each(dateList, function(i, per) {
            var html = '';
            html += '<label class="radio-mask">';
            html += '   <input type="radio" name="date" data-name="'+ per.text +'" data-min-date="'+ per.minDate +'" data-max-date="'+ per.maxDate +'">';
            html += '   <div class="mask">';
            html += '       <span class="mask-bg"></span>'+ per.text +'';
            html += '   </div>';
            html += '</label>';
            $(".form-month-range").append(html);
        });
    },
    IE8Css:function(){
        var explorer = window.navigator.userAgent ;
        if (explorer.indexOf("MSIE 8.0") >= 0) {
            $('.mask-bg').css({'filter':'Mask(Color=#f3f8fe)'});
        }
    }
};

$(function(){
    CruiseShipList.init();
});



