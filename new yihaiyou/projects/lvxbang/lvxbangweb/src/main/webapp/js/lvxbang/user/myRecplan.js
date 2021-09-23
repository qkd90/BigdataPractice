/**
 * Created by vacuity on 16/1/18.
 */


/**
 * Created by vacuity on 16/1/18.
 */


var RecplanList = {
    pager: null,
    data: {},
    started: true,
    init: function () {
        RecplanList.pager = new Pager(
            {
                pageSize: 6,
                countUrl: "/lvxbang/user/myRecplanCount.jhtml",
                searchData: {},
                pageRenderFn: function (pageNo, pageSize) {
                    RecplanList.data.pageNo = pageNo;
                    RecplanList.data.pageSize = pageSize;
                    $.post("/lvxbang/user/getMyRecplan.jhtml", RecplanList.data, function (result) {
                        var html = "";
                        $.each(result, function (i, data) {
                            html += template("tpl-recplan-list-item", data);
                        });
                        $("#my-recplan").html(html);
                        //图片延迟加载
                        $("img").lazyload({
                            effect: "fadeIn"
                        });

                    },"json");
                }
            }
        );
        RecplanList.refreshList();
    },

    refreshList: function () {
        RecplanList.data = {};

        RecplanList.data["started"] = RecplanList.started;

        RecplanList.pager.init(RecplanList.data);
    },

    changeCategory: function (num) {
        if (Number(num) == 1) {
            $("#started-btn").addClass("checked");
            $("#not-start-btn").removeClass("checked");
            RecplanList.started = true;
        } else {
            $("#started-btn").removeClass("checked");
            $("#not-start-btn").addClass("checked");
            RecplanList.started = false;
        }
        RecplanList.refreshList();
    },


    delSelected: function (id) {
        deleteWarn("确定删除该游记？", function (id) {
            $.post("/lvxbang/user/delRecplan.jhtml",
                {
                    id: id
                }, function (result) {
                    if (result.success) {
                        //
                        RecplanList.refreshList();
                    } else {
                        promptWarn("删除失败");
                    }
                },"json");
        }, id)

    },

    initPanel: function () {
        $(".personal").removeClass("checked");
        $("#recplan-panel").addClass("checked");
    },


}


$(function () {
    RecplanList.initPanel();
    RecplanList.init();
});
