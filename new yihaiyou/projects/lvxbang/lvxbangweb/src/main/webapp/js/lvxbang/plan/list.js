function PlanList() {
    var planList = this;
    this.pager;
    this.data = {};
    this.init = function () {
        planList.pager = new Pager({
            countUrl: "/lvxbang/plan/getTotalPage.jhtml",
            resultCountFn: function (result) {
                return parseInt(result[0]);
            },
            pageRenderFn: function (pageNo, pageSize) {
                $("#plan").empty();
                $("#loading").show();
                scroll(0, 0);
                planList.data.pageNo = pageNo;
                planList.data.pageSize = pageSize;

                $.post("/lvxbang/plan/getPlanList.jhtml", planList.data, function (result) {
                    $("#plan").empty();
                    $("#loading").hide();
                    for (var i = 0; i < result.length; i++) {
                        var html = $(template("tpl-plan-item", result[i]));
                        $('#plan').append(html);
                    }
                    collect(".collect2", false, ".collectNum");
                    $("img").lazyload({
                        effect: "fadeIn"
                    });
                }, "json");
            }
        });
    }
    this.refreshList = function (type) {
        planList.data = {};
        var name = $("#planName").val();
        if (!isNull(name)) {
            planList.data["searchRequest.name"] = name;
        }
        if (!isNull($("#month").find(".checked").attr("monthRange"))) {
            var monthRange = $("#month").find(".checked").attr("monthRange").split("-");
            planList.data["searchRequest.monthRange[0]"] = monthRange[0];
            planList.data["searchRequest.monthRange[1]"] = monthRange[1];
        }
        if (!isNull($("#day").find(".checked").attr("dayRange"))) {
            var dayRange = $("#day").find(".checked").attr("dayRange").split("-");
            planList.data["searchRequest.dayRange[0]"] = dayRange[0];
            planList.data["searchRequest.dayRange[1]"] = dayRange[1];
        }
        if (!isNull($("#cost").find(".checked").attr("costRange"))) {
            var costRange = $("#cost").find(".checked").attr("costRange").split("-");
            planList.data["searchRequest.costRange[0]"] = costRange[0];
            planList.data["searchRequest.costRange[1]"] = costRange[1];
        }
        var n = 0;
        $("#destination .checkbox").each(function () {
            if ($(this).hasClass("checked")) {
                planList.data["searchRequest.cityIds[" + n + "]"] = $(this).attr("data-id");
                n++;
            }
        });
        if (isNull(name) || type == 1) {
            if ($("#sort").find(".checked").length < 1) {
                $("#sort").find("li").eq(0).addClass("checked");
            }
            planList.data["searchRequest.orderColumn"] = $("#sort").find(".checked").attr("orderColumn");
            planList.data["searchRequest.orderType"] = $("#sort").find(".checked").attr("orderType");
        } else {
            $("#sort").find(".checked").removeClass("checked");
        }
        planList.pager.init(planList.data);
    }
}


$(document).ready(function () {
    var planList = new PlanList();
    planList.init();
    planList.refreshList();

    $("#search-button").click(function () {
        planList.refreshList();
    });

    $("#month").on("click", "li a", function () {
        $("#month").find(".checked").removeClass("checked");
        $(this).addClass("checked");
        planList.refreshList();
    })

    $("#day").on("click", "li a", function () {
        $("#day").find(".checked").removeClass("checked");
        $(this).addClass("checked");
        planList.refreshList();
    });

    $("#cost").on("click", "li a", function () {
        $("#cost").find(".checked").removeClass("checked");
        $(this).addClass("checked");
        planList.refreshList();
    });

    $("#sort").on("click", "li", function () {
        orderClick(this);
        planList.refreshList(1);
    });

    CitySelector.withOption({
        selectedFn: function () {
            planList.refreshList();
        }
    });

    //checkbox
    $("#destination").delegate(".checkbox .checkbox_i", "click", function () {
        if ($(this).parent("div").hasClass("checked")) {
            $(this).parent("div").removeClass("checked");
        }
        else {
            $(this).parent("div").addClass("checked");
        }
        planList.refreshList();
    });

    //回车键进行搜索
    $(function(){
        $('#planName').bind('keypress',function(event){
            if(event.keyCode == "13")
            {
                if(!isNull($('#planName').val())){
                    planList.refreshList();
                    $('#planName').next().hide();
                }
            }
        });
    });
});