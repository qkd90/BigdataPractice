var MyPlan = {
    pager: null,
    init: function () {
        YhyUser.checkLogin(function(result) {
            if (result && result.success) {
                MyPlan.initComp();
                MyPlan.initEvt();
                MyPlan.createPager();
                MyPlan.initData();
            } else {
                YhyUser.goLogin();
            }
        });
    },
    initComp: function () {},
    initEvt: function () {},
    createPager: function() {
        var page = {
            countUrl: '/yhypc/personal/countMyPlan.jhtml',
            resultCountFn: function(result) {
                if (result.success) {
                    return parseInt(result.count);
                } else {
                    if (result.code == "req_login") {YhyUser.goLogin();}
                    return 0;
                }
            },
            pageRenderFn: function(pageNo, pageSize, data) {
                data.pageNo = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url: '/yhypc/personal/getMyPlan.jhtml',
                    data: data,
                    progress: true,
                    success: function(result) {
                        if (result.success) {
                            $('#plan_content').empty();
                            $.each(result.data, function(i, p) {
                                var planItem = template('plan_item', p);
                                $('#plan_content').append(planItem);
                            });
                            scroll(0, 480);
                        }
                    }
                });
            }
        };
        MyPlan.pager = new Pager(page);
    },
    initData: function() {
        MyPlan.getMyPlan();
    },
    getMyPlan: function() {
        var searchRequest = {};
        MyPlan.pager.init(searchRequest);
    }
};
$(function() {
    MyPlan.init();
});
