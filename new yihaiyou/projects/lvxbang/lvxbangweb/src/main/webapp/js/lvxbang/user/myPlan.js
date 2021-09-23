/**
 * Created by vacuity on 16/1/18.
 */


var PlanList = {
    pager: null,
    data: {},
    started: true,

    // 构建分页列表
    buildPage : function(url, type, params) {
        PlanList.pager = new Pager({
            //pageNo: pageCurrent,         //页码
            pageSize: 6,                //每页显示条数
            mode: 2,                    //类型：1：分页计数分开，2：分页计数合并
            //container: "#favorites_pager_"+favoriteType,      //容器选择器
            pageRenderFn: function(pageNo, pageSize) {
                params.pageNo = pageNo;
                params.pageSize = pageSize;
                $.post(url, params, function (result) {
                    var html = "";
                    var data = result.data;
                    var total = result.totalCount;
                    if (type == 'plan') {
                        $.each(data, function (i, item) {
                            html += template("tpl-plan-list-item", item);
                        });
                    } else if (type == 'require') {
                        $.each(data, function (i, item) {
                            html += template("tpl-require-list-item", item);
                        });
                    }
                    $("#myplan").html(html);
                    //图片延迟加载
                    $("img").lazyload({
                        effect: "fadeIn"
                    });
                    PlanList.pager.renderPageNav({pageNo:pageNo, pageSize: pageSize, total: total});
                },"json");
            }
        });
        PlanList.pager.init();
    },

    refreshList: function () {
        PlanList.data = {};

        PlanList.data["started"] = PlanList.started;

        PlanList.pager.init(PlanList.data);
    },

    changeCategory: function (type) {
        if (type == 'plan') {
            $("#started-btn").addClass("checked");
            $("#not-start-btn").removeClass("checked");
            // 按钮
            $('#createPlan').show();
            $('#createRequire').hide();

            var url = '/lvxbang/user/page.jhtml';
            PlanList.buildPage(url, type, {});
        } else if (type == 'require') {
            $("#started-btn").removeClass("checked");
            $("#not-start-btn").addClass("checked");
            // 按钮
            $('#createPlan').hide();
            $('#createRequire').show();

            var url = '/lvxbang/customRequire/page.jhtml';
            PlanList.buildPage(url, type, {});
        }
    },


    delSelected: function (id) {
        deleteWarn("确定删除该线路？", function (id) {
            $.post("/lvxbang/user/delPlans.jhtml",
                {
                    id: id
                }, function (result) {
                    if (result.success) {
                        //
                        PlanList.refreshList();
                    } else {
                        promptWarn("删除失败");
                    }
                },"json");
        }, id);
    },

    initPanel: function () {
        $(".personal").removeClass("checked");
        $("#plan-panel").addClass("checked");
    },

    toRecplan: function (id) {

        $.post("/lvxbang/plan/exportRecommend.jhtml", {
            planId: id
        }, function (result) {
            if (result.success) {
                //
                window.location.href = "/lvxbang/recommendPlan/edit.jhtml?recplanId=" + result.id;
            } else {
                promptWarn("生成游记失败");
            }
        },"json");
    }


}


$(function () {
    PlanList.initPanel();
    //PlanList.init();
    unConfirmed();
});
//判断是进入已完成或者未完成
function unConfirmed(){
    (function ($) {
        $.getUrlParam = function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
    })(jQuery);
    var off = $.getUrlParam('save');
    if(off=='ok'){
        $('#not-start-btn').click();
    } else {
        $('#started-btn').click();
    }
}