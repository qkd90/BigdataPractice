/**
 * Created by zzl on 2016/11/9.
 */
var YhyCommon = {
    Table: null,
    ProDropMenuItemTpl: "<li data-product-id role='presentation'><a role='menuitem' tabindex='-1' href='#'></a></li>",
    LoadingItem: "<div class='progress-shadow'><img class='loading-img' src='/images/yhy/loading.gif'><span class='loading-text'>请稍候...</span></div>",
    init: function() {
        YhyCommon.initAjax();
        YhyCommon.initEvt();
        YhyCommon.getProData();
    },
    initAjax: function() {
        var $loading = $(YhyCommon.LoadingItem).clone();
        $(document).ajaxSend(function(event, xhr, options) {
            if (options && options.progress === true) {
                if (options.loadingText && options.loadingText != "") {
                    var width = options.loadingText.length * 45 + 50;
                    $loading.css('width', width);
                    $loading.find('span.loading-text').html(null).html(options.loadingText);
                }
                $('body').append($loading);
            }
        });
        $(document).ajaxComplete(function (event, xhr, options) {
            if (options && options.progress) {
                $loading.remove();
            }
        });
    },
    getProData: function() {
        $.ajax({
            url: '/yhy/yhyMain/getProducts.jhtml',
            type: 'post',
            dataType: 'json',
            data: {},
            success: function(result) {
                if (result.success) {
                    var productList = result.productList;
                    // set top product menu
                    YhyCommon.addProductDropMenu({id: null, name: "全部"});
                    $.each(productList, function(i, product) {
                        YhyCommon.addProductDropMenu(product);
                    });
                } else {
                    $.messager.show({
                        msg: '产品列表加载失败!请重新登录尝试',
                        type: 'error'
                    });
                }
            },
            error: function() {
                $.messager.show({
                    msg: '产品列表加载失败!请重新登录尝试',
                    type: 'error'
                })
            }
        })
    },
    initEvt: function() {
        // top dropmenu select
        $('#product-drop-menu').on('click', 'li', function(event) {
            event.stopPropagation();
            var selId = $(this).attr('data-product-id');
            $.ajax({
                url: '/yhy/yhyMain/selTopDropProduct.jhtml',
                type: 'post',
                dataType: 'json',
                data: {productId: selId},
                success: function(result) {
                    if (result.success) {
                        // refresh page
                        if (YhyCommon.Table && YhyCommon.Table.state) {
                            YhyCommon.Table.state.clear();
                        }
                        window.location.reload();
                    } else {
                        $.messager.show({
                            msg: result.msg,
                            type: "error",
                            afterClosed: function() {
                                window.location.reload();
                            }
                        });
                    }
                },
                error: function() {
                    $.messager.show({
                        msg: "查询产品信息失败,请尝试重新选择!",
                        type: "error"
                    });
                }
            });
        });
    },
    addProductDropMenu: function(product) {
        var $menuItem = $(YhyCommon.ProDropMenuItemTpl);
        var selProductId = $('#productId').val();
        $menuItem.attr('data-product-id', product.id);
        $menuItem.children('a').html(product.name);
        $menuItem.children('a').prop('title', product.name);
        $('#product-drop-menu').append($menuItem);
        // set select style
        if (selProductId == product.id || (product.name === "全部" && product.id == null)) {
            $menuItem.siblings().removeClass('select');
            $menuItem.addClass('select');
        }
    }
};
$(function() {
    YhyCommon.init();
});
