/**
 * Created by zzl on 2016/5/11.
 */

$(function () {
    UserCouponMgr.init();
    UserCouponMgr.initComponent();
});

var UserCouponMgr = {
    table: $("#userCouponDg"),
    searcher: $("#user-coupon-searcher"),
    member_searcher: $("#member-searcher"),
    coupon_searcher: $("#coupon-searcher"),
    initComponent: function () {
        $("#couponIds_combogrid").combogrid({
            url: '/lxbcommon/coupon/getCouponComboData.jhtml',
            idField: 'id',
            textField: 'name',
            panelWidth: 500,
            loadMsg: '加载中,请稍候...',
            multiple: true,
            pagination:true,
            pageList:[200,300,600],
            rownumbers:false,
            columns:[[
                {field: 'id', title: 'ID', width: 50, align: 'center'},
                {field: 'name', title: '优惠券名称', width: 440, align: 'center'},
            ]],
            toolbar: this.coupon_searcher
        });
        $("#memberIds_combogrid").combogrid({
            url: '/customer/customer/getMemberCoboData.jhtml',
            idField:'id',
            textField:'userName',
            panelWidth: 500,
            loadMsg: '加载中,请稍候...',
            multiple: true,
            pagination:true,
            pageList:[200,300,600],
            rownumbers:false,
            columns:[[
                {field: 'id', title: 'ID', width:50, align: 'center'},
                {field:'userName', title: '用户名', width:140, align: 'center'},
                {field:'nickName', title: '昵称', width:140, align: 'center'},
                {field:'contact', title: '联系方式', width:140, align: 'center'}
            ]],
            toolbar: this.member_searcher
        });
    },
    init: function () {
        this.table.datagrid({
            url: '/lxbcommon/userCoupon/getUserCouponList.jhtml',
            fit: true,
            pagination:true,
            pageList:[20,30,50],
            rownumbers:false,
            queryParams: {
                'orderProperty': "validEnd",
                'orderType': "desc"
            },
            columns: [[
                {
                    field: 'id',
                    title: 'ID',
                    width: 55,
                    align: 'center'
                },
                {
                    field: 'OPT',
                    title: '操作',
                    width: '80',
                    align: 'center',
                    formatter: UserCouponMgr.optFormat
                },
                {
                    field: 'userName',
                    title: '用户名',
                    width: 80,
                    align: 'center'
                },
                {
                    field: 'contact',
                    title: '联系方式',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'couponName',
                    title: '优惠券名称',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'validStart',
                    title: '起始有效期',
                    width: 140,
                    align: 'center'
                },
                {
                    field: 'validEnd',
                    title: '终止有效期',
                    width: 140,
                    align: 'center'
                },
                {
                    field: 'status',
                    title: '使用状态',
                    width: 80,
                    align: 'center',
                    formatter: UserCouponMgr.statusFormat
                },
                {
                    field: 'useTime',
                    title: '使用时间',
                    width: 140,
                    align: 'center',
                    formatter: UserCouponMgr.useTimeFormat
                }

            ]],
            toolbar: this.searcher
        });
    },
    useTimeFormat: function(value, rowData, rowIndex) {
        if (value == null || value == "") {
            return "未使用";
        } else {
            return value;
        }
    },
    statusFormat: function (value, rowData, rowIndex) {
        if (value == "used") {
            return "<span style='color: #008800'>已使用</span>";
        } else if (value == "unused") {
            return "<span style='color: #ff2222'>未使用</span>";
        } else if (value == "expired") {
            return "<span style='color: #c0c0c0'>已过期</span>";
        } else if (value == "unavailable") {
            return "<span style='color: #f78f23'>不可用</span>";
        } else if (value == "del") {
            return "<span style='color: #3c3c3c'>已删除</span>";
        } else {
            return "-";
        }
    },
    optFormat: function (value, rowData, rowIndex) {
        var btn = "";
        var unavailableClick = " onClick='UserCouponMgr.setUnavailable(" + rowData.id + ")'";
        var availableClick = " onClick='UserCouponMgr.setAvailable(" + rowData.id + ")'";
        btn += "<div class='opt' '>";
        if (rowData.status == "unused") {
            btn += "<a class='ena' href='#'" + unavailableClick + ">禁用</a>";
        } else if(rowData.status == "unavailable") {
            btn += "<a class='ena' href='#'" + availableClick + ">启用</a>";
        } else {
            return "-";
        }
        btn += "</div>";
        return btn;
    },
    setUnavailable: function(id) {
        $.ajax({
            url: '/lxbcommon/userCoupon/setUnavailable.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function (result) {
                $.messager.progress('close');
                if (result.success) {
                    showMsgPlus("优惠券使用管理", result.msg);
                    UserCouponMgr.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('优惠券使用管理', result.msg, 'error');
                }
            },
            error: function () {
                $.messager.progress('close');
                $.messager.alert('优惠券使用管理', "操作失败,请重试!", 'error');
            },
            beforeSend: function (data) {
                $.messager.progress({
                    msg: '操作正在进行,请稍候...'
                });
            }

        });
    },
    setAvailable: function(id) {
        $.ajax({
            url: '/lxbcommon/userCoupon/setAvailable.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function (result) {
                $.messager.progress('close');
                if (result.success) {
                    showMsgPlus("优惠券使用管理", result.msg);
                    UserCouponMgr.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('优惠券使用管理', result.msg, 'error');
                }
            },
            error: function () {
                $.messager.progress('close');
                $.messager.alert('优惠券使用管理', "操作失败,请重试!", 'error');
            },
            beforeSend: function (data) {
                $.messager.progress({
                    msg: '操作正在进行,请稍候...'
                });
            }

        });
    },
    doSearch: function () {
        var searchForm = {};
        var searchType = this.searcher.find("#search-type").val();
        searchForm[searchType] = this.searcher.find("#search-content").val();
        searchForm['userCoupon.status'] = this.searcher.find("#search-status").val();
        searchForm['orderProperty'] = this.searcher.find("#search-sort-property").val();
        searchForm['orderType'] = this.searcher.find("#search-sort-type").val();
        this.table.datagrid('load', searchForm);
    },
    doMemberSearch: function () {
        var memberSearchForm = {};
        var searchType = this.member_searcher.find("#member-search-type").val();
        memberSearchForm[searchType] = this.member_searcher.find("#member-search-content").val();
        $("#memberIds_combogrid").combogrid('grid').datagrid('load', memberSearchForm);
    },
    doCouponSearch: function() {
        var couponSearchForm = {};
        var searchType = this.coupon_searcher.find("#coupon-search-type").val();
        couponSearchForm[searchType] = this.coupon_searcher.find("#coupon-search-content").val();
        $("#couponIds_combogrid").combogrid('grid').datagrid('load', couponSearchForm);
    },
    openSendCoupon: function () {
        $("#user_coupon_panel").dialog({
            title: '发放优惠券',
            modal: true,
            shadow:false,
            top: 30,
            onClose: function () {
                $("#user_coupon_form").form('clear');
            }
        });
        $("#user_coupon_panel").dialog('open');
    },
    commitForm: function (formName, panelName) {
        $("#" + formName).form('submit', {
            url: '/lxbcommon/userCoupon/sendCoupon.jhtml',
            success: function (result) {
                result = eval('(' + result + ')');
                $.messager.progress('close');
                if (result.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("优惠券发放", result.msg);
                    UserCouponMgr.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('优惠券发放', result.msg, 'error');
                }
            },
            error: function() {
                $.messager.progress('close');
                $.messager.alert('优惠券发放', "操作失败,请重试!", 'error');
            },
            onSubmit: function () {
                $.messager.progress({
                    msg: '优惠券发放中, 请稍候...'
                });
            }
        });
    }
};