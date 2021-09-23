/**
 * Created by zzl on 2017/1/24.
 */
var YhyUserOrder = {
    pager: null,
    init: function() {
        YhyUser.checkLogin(function(result) {
            if (result && result.success) {
                YhyUserOrder.initComp();
                YhyUserOrder.initEvt();
                YhyUserOrder.createPager();
                YhyUserOrder.getOrderData();
            } else {
                YhyUser.goLogin()
            }
        });
    },
    initComp: function() {},
    initEvt: function() {
        $('#status_sel li').on("click", function(event) {
            event.stopPropagation();
            YhyUserOrder.getOrderData();
        });
        $('#type_sel span').on('click', function(event) {
            event.stopPropagation();
            YhyUserOrder.getOrderData();
        });
        $(document).on('click', 'a.delOrder', function(event) {
            event.stopPropagation();
            YhyUserOrder.preDelOrder($(this).attr("data-order-id"), $(this).attr("data-order-type"));
        });
    },
    createPager: function() {
        var pageOpt = {
            countUrl: "/yhypc/personal/countMyOrder.jhtml",
            resultCountFn: function(result) {
                if (result.success) {
                    $('#status_sel li[data-order-status = "SUCCESS"] span').html(null).html(result.successCount);
                    $('#status_sel li[data-order-status = "WAIT"] span').html(null).html(result.waitPayCount);
                    $('#status_sel li[data-order-status = "CANCELED"] span').html(null).html(result.canceledCount);
                    $('#status_sel li[data-order-status = "NOCOMMENT"] span').html(null).html(result.noCommentCount);
                    $('#status_sel li[data-order-status = "COMMENT"] span').html(null).html(result.commentCount);
                    return Number(result.totalCount);
                } else {
                    if (result.code == "req_login") {YhyUser.goLogin();}
                    return 0;
                }
            },
            pageRenderFn: function(pageNo, pageSize, data) {
                scroll(0, 480);
                data.pageNo = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url: "/yhypc/personal/getMyOrder.jhtml",
                    data: data,
                    progress: true,
                    success: function(result) {
                        if (result.success) {
                            $('#order_info_content').empty();
                            $.each(result.data, function(i, o) {
                                var order_item = template("order_info_tpl", o);
                                $('#order_info_content').append(order_item);
                            });
                        }
                    },
                    error: function() {}
                });
            }
        };
        YhyUserOrder.pager = new Pager(pageOpt);
    },
    getOrderData: function() {
        var searchRequest = {};
        var orderStatus = $('#status_sel li.li_active').attr("data-order-status");
        var orderTypes = $('#type_sel span.s_active').attr("data-order-type");
        if (orderStatus == "NOCOMMENT") {
            searchRequest["hasComment"] = false;
        } else if (orderStatus == "COMMENT") {
            searchRequest["hasComment"] = true;
        } else {
            searchRequest["orderStatus"] = orderStatus;
        }
        searchRequest["orderTypes"] = orderTypes;
        YhyUserOrder.pager.init(searchRequest);
    },
    preDelOrder: function(id, type) {
        $.message.confirm({
            info: '确认要删除订单?',
            yes: function() {
                YhyUser.checkLogin(function(result) {
                    if (result && result.success) {
                        YhyUserOrder.doDelOrder(id, type);
                    } else {
                        YhyUser.goLogin(function() {
                            YhyUserOrder.doDelOrder(id, type);
                        });
                    }
                });
            },
            no: function() {
                return;
            }
        });
    },
    doDelOrder: function(id, type) {
        $.ajax({
            url: '/yhypc/personal/doDelOrder.jhtml',
            data: {id: id, type: type},
            progress: true,
            success: function(result) {
                if (result.success) {
                    $.message.alert({info: '删除成功!', afterClosed: function() {
                        YhyUserOrder.getOrderData();
                    }});
                } else {
                    if (result.code == "req_login") {
                        YhyUser.goLogin(function() {
                            YhyUserOrder.doDelOrder(id);
                        });
                    } else {
                        alert(result.msg);
                    }
                }
            },
            error: function() {
                $.message.alert({info: '删除错误, 请稍后重试!'});
            }
        });
    }
};

$(function() {
    YhyUserOrder.init();
});