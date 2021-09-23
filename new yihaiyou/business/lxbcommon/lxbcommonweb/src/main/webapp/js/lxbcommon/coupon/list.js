/**
 * Created by zzl on 2016/5/9.
 */

$(function () {
    CouponMgr.init();
    CouponMgr.initComponent();
});

var CouponMgr = {
    table: $("#couponDg"),
    searcher: $("#coupon-searcher"),
    couponDiscountType: [
        {id: 'money', text: '抵扣型'},
        {id: 'discount', text: '打折型'}
    ],
    couponValidType: [
        {id: 'range', text: '固定范围'},
        {id: 'days', text: '自领取日起'},
        {id: 'forever', text: '永久有效'}
    ],
    useCondition: [
        {id: 'full', text: '满减'},
        {id: 'none', text: '无限制'}
    ],
    couponReceiveLimitType: [
        {id: 'code', text: '仅可通过优惠券码兑换'},
        {id: 'num', text: '通过领取数量限制'},
        {id: 'none', text: '无领取数量限制'}
    ],
    limitProductTypes: [
        {id: '', text: '全部'},
        {id: 'plan', text: '线路'},
        {id: 'scenic', text: '门票'},
        {id: 'hotel', text: '酒店'},
        {id: 'flight', text: '机票'},
        {id: 'train', text: '火车票'}
    ],
    status: [
        {id: 'open', text: '领取中'},
        {id: 'expired', text: '已过期'},
        {id: 'closed', text: '关闭'}
    ],
    initComponent: function () {
        //
        $("#couponDiscountType").combobox({
            data: this.couponDiscountType,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
            onSelect: function (record) {
                if (record.id == "discount") {
                    $("#max_discount_div").show();
                    $("#maxDiscount").textbox({required : true});
                } else if (record.id == "money") {
                    $("#max_discount_div").hide();
                    $("#maxDiscount").textbox({required : false});
                    $("input[name = 'coupon.maxDiscount']").val(null);
                }
            },
            onLoadSuccess: function () {
                $("#couponDiscountType").combobox('select', 'money');
            }
        });
        //
        $("#couponValidType").combobox({
            data: this.couponValidType,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
            onSelect: function (record) {
                if (record.id == "range") {
                    $("#valid_type_range").show();
                    $("#valid_type_days").hide();
                    $("input[name = 'coupon.validDays']").val(null);
                    $("#validDays").textbox({required: false});
                } else if (record.id == "days") {
                    $("#valid_type_range").show();
                    $("#valid_type_days").show();
                    $("#validDays").textbox({required: true});
                } else if (record.id == "forever") {
                    $("#valid_type_range").hide();
                    $("#valid_type_days").hide();
                    $("input[name = 'validStart']").val(null);
                    $("input[name = 'validEnd']").val(null);
                    $("input[name = 'coupon.validDays']").val(null);
                    $("#validDays").textbox({required: false});
                }
            },
            onLoadSuccess: function () {
                $("#couponValidType").combobox('select', 'range');
            }
        });
        //
        $("#use_condition_combo").combobox({
            data: this.useCondition,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
            onSelect: function(record) {
                if (record.id == "full") {
                    $("#use_condition").show();
                } else if (record.id == 'none') {
                    $("#use_condition").hide();
                    $("input[name = 'coupon.useCondition']").val(null);
                }
            },
            onLoadSuccess: function () {
                $("#use_condition_combo").combobox('select', 'full');
            }
        });
        //
        $("#couponReceiveLimitType").combobox({
            data: this.couponReceiveLimitType,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
            onSelect: function(record) {
                if (record.id == "code" || record.id == "none") {
                    $("#receive_type_num").hide();
                    $("input[name = 'coupon.receiveLimit']").val(null);
                    $("#receiveLimit").textbox({required: false});
                } else if (record.id == "num") {
                    $("#receive_type_num").show();
                    $("input[name = 'coupon.receiveLimit']").val(1)
                    $("#receiveLimit").textbox({required: true});
                }
            },
            onLoadSuccess: function () {
                $("#couponReceiveLimitType").combobox('select', 'num');
            }
        });
        //
        $("#limitProductTypes").combobox({
            data: this.limitProductTypes,
            valueField: 'id',
            textField: 'text',
            multiple: true,
            panelHeight: 'auto',
            onSelect: function (record) {
                if (record.id == null || record.id == '') {
                    //
                } else {
                    //
                }
            }
        });
        //
        //$("#limitTargetIds").combogrid({});
        //
        $("#couponStatus").combobox({
            data: this.status,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
            onSelect: function(record) {
                //
            },
            onLoadSuccess: function () {
                $("#couponStatus").combobox('select', 'open');
            }
        });
    },
    init: function() {
        this.table.datagrid({
            url: '/lxbcommon/coupon/getCouponList.jhtml',
            fit: true,
            pagination:true,
            pageList:[20,30,50],
            rownumbers:false,
            queryParams: {
                'orderProperty': "createTime",
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
                    field: 'status',
                    title: '状态',
                    width: 80,
                    align: 'center',
                    formatter: CouponMgr.statusFormat
                },
                {
                    field: 'OPT',
                    title: '操作',
                    width: '120',
                    align: 'center',
                    formatter: CouponMgr.optFormat
                },
                {
                    field: 'couponCode',
                    title: '优惠券码',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'name',
                    title: '优惠券名称',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'couponDiscountType',
                    title: '优惠类型',
                    width: 80,
                    align: 'center',
                    formatter: CouponMgr.couponDiscountTypeFormat
                },
                {
                    field: 'faceValue',
                    title: '面值(元/折)',
                    width: 80,
                    align: 'center',
                    formatter: CouponMgr.faceValueFormat
                },
                {
                    field: 'useCondition',
                    title: '使用条件',
                    width: 80,
                    align: 'center',
                    formatter: CouponMgr.useConditionFormat
                },
                {
                    field: 'maxDiscount',
                    title: '最高减免',
                    width: '80',
                    align: 'center',
                    formatter: CouponMgr.maxDiscountFormat
                },
                {
                    field: 'circulation',
                    title: '发行量',
                    width: 80,
                    align: 'center',
                    formatter: CouponMgr.circulationFormat
                },
                {
                    field: 'couponReceiveLimitType',
                    title: '领取限制',
                    width: 120,
                    align: 'center',
                    formatter: CouponMgr.couponReceiveLimitTypeFormat
                },
                {
                    field: 'availableNum',
                    title: '可领数量',
                    width: 80,
                    align: 'center',
                    formatter: CouponMgr.availableNumFormat
                },
                {
                    field: 'receivedNum',
                    title: '已领数量',
                    width: 80,
                    align: 'center',
                    formatter: CouponMgr.receivedNumFormat
                },
                {
                    field: 'receivedPersonNum',
                    title: '已领人次',
                    width: 80,
                    align: 'center',
                    formatter: CouponMgr.receivedPersonNumFormat
                },
                {
                    field: 'couponValidType',
                    title: '有效期类型',
                    width: 80,
                    align: 'center',
                    formatter: CouponMgr.couponValidTypeFormat
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
                    field: 'validDays',
                    title: '有效天数',
                    width: 80,
                    align: 'center',
                    formatter: CouponMgr.validDaysFormat
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    width: 140,
                    align: 'center',
                }
            ]],
            toolbar: this.searcher
        });
    },
    doSearch: function () {
        var searchForm = {};
        var searchType = this.searcher.find("#search-type").val();
        searchForm[searchType] = this.searcher.find("#search-content").val();
        searchForm['coupon.status'] = this.searcher.find("#search-status").val();
        searchForm['orderProperty'] = this.searcher.find("#search-sort-property").val();
        searchForm['orderType'] = this.searcher.find("#search-sort-type").val();
        this.table.datagrid('load', searchForm);
    },
    statusFormat: function(value, rowData, rowIndex) {
        if (value == "open") {
            return "<span style='color: darkgreen'>领取中</span>";
        } else if (value == "closed") {
            return "<span style='color: #f52000'>已下架</span>";
        } else if (value == "expired") {
            return "<span style='color: #8F8F8F'>已过期</span>";
        }
    },
    optFormat: function (value, rowData, rowIndex) {
        var btn = "";
        var closeClick = " onClick='CouponMgr.closeCoupon(" + rowData.id + ")'";
        var openClick = " onClick='CouponMgr.openCoupon(" + rowData.id + ")'";
        var editClick = " onClick='CouponMgr.editCoupon(" + rowData.id + ")'";
        btn += "<div class='opt' >";
        if (rowData.status == "open") {
            btn += "<a class='ena' href='#'" + closeClick + ">关闭领取</a>";
            btn += "&nbsp;&nbsp;";
        } else if (rowData.status == "closed") {
            btn += "<a class='ena' href='#'" + openClick + ">打开领取</a>";
            btn += "&nbsp;&nbsp;";
        }
        btn += "<a class='ena' href='#'" + editClick + ">编辑/查看</a>"
        btn += "&nbsp;&nbsp;";
        btn += "</div>";
        return btn;
    },
    couponDiscountTypeFormat: function (value, rowData, rowIndex) {
        if (value == "discount") {
            return "打折型优惠券";
        } else if (value == "money") {
            return "金额抵扣型优惠券";
        }else {
            return "其他";
        }
    },
    faceValueFormat: function (value, rowData, rowIndex) {
        if (rowData.couponDiscountType == 'discount') {
            return value + "折";
        } else if (rowData.couponDiscountType == 'money') {
            return value + "元";
        }
    },
    useConditionFormat: function (value, rowData, rowIndex) {
        if (value != null && value != "") {
            return "满" + value + "元使用";
        } else if (value == null || value == "") {
            return "无条件使用";
        }
    },
    maxDiscountFormat: function (value, rowData, rowIndex) {
        if (value != null && value != "") {
            return "最高抵扣" + value + "元";
        } else if (value == null || value == "") {
            return "-";
        }
    },
    circulationFormat: function (value, rowData, rowIndex) {
        if (value != null && value != "") {
            return value + "张";
        } else if (value == null || value == "") {
            return "-";
        }
    },
    couponValidTypeFormat: function (value, rowData, rowIndex) {
        if (value == "range") {
            return "固定有效期";
        } else if (value == "days") {
            return "领取后固定天数";
        } else if (value == "forever") {
            return "永久有效";
        }
    },
    validDaysFormat: function (value, rowData, rowIndex) {
        if (value != null && value != "") {
            return value + "天";
        } else if (value == null || value == "") {
            return "-";
        }
    },
    couponReceiveLimitTypeFormat: function (value, rowData, rowIndex) {
        if (value == "code") {
            return "仅可通过优惠券码兑换";
        } else if (value == "num") {
            return "每人限领" + rowData.receiveLimit + "张";
        } else if (value == "none") {
            return "无领取限制";
        }
    },
    availableNumFormat: function (value, rowData, rowIndex) {
        if (value != null && value != "") {
            return value + "张";
        } else if (value == null || value == "") {
            return "-";
        }
    },
    receivedNumFormat: function (value, rowData, rowIndex) {
        if (value != null && value != "" || (value == 0)) {
            return value + "张";
        } else if (value == null || value == "") {
            return "-";
        }
    },
    receivedPersonNumFormat: function (value, rowData, rowIndex) {
        if (value != null && value != "" || (value == 0)) {
            return value + "人";
        } else if (value == null || value == "") {
            return "-";
        }
    },
    addCoupon: function () {
        $("#coupon_panel").dialog({
            title: '添加优惠券',
            modal: true,
            shadow:false,
            top: 0,
            onClose: function () {
                $("#coupon_form").form('clear');
            }
        });
        CouponMgr.initComponent();
        $("#coupon_panel").dialog('open');
    },
    detailForm: function (id) {
        var detailUrl = "/lxbcommon/coupon/detailCoupon.jhtml?id=" + id;
        $("#coupon_form").form({
            onLoadSuccess: function (data) {
                // 处理组件显示
                if (data['coupon.couponDiscountType'] == "discount") {
                    $("#max_discount_div").show();
                } else if (data['coupon.couponDiscountType'] == "money") {
                    $("#max_discount_div").hide();
                }
                if (data['coupon.couponValidType'] == "days") {
                    $("#valid_type_range").show();
                    $("#valid_type_days").show();
                } else if (data['coupon.couponValidType'] == "range") {
                    $("#valid_type_range").show();
                    $("#valid_type_days").hide();
                } else if (data['coupon.couponValidType'] == "forever") {
                    $("#valid_type_range").hide();
                    $("#valid_type_days").hide();
                }
                if (data['coupon.couponReceiveLimitType'] == "code" || data['coupon.couponReceiveLimitType'] == "none") {
                    $("#receive_type_num").hide();
                } else if (data['coupon.couponReceiveLimitType'] == "num") {
                    $("#receive_type_num").show();
                }
                // 处理状态
                if (data['coupon.status'] == 'expired') {
                    $("#couponStatus").combobox('disable');
                } else {
                    $("#couponStatus").combobox('enable');
                }
            }
        });
        $("#coupon_form").form('load', detailUrl);
        $("#coupon_panel").dialog({
            title: '查看/编辑优惠券',
            modal: true,
            shadow:false,
            top: 0,
            onClose: function () {
                $("#coupon_form").form('clear');
            }
        });
        $("#coupon_panel").dialog('open');

    },
    checkValidDate: function() {
        var validDateType = $("#couponValidType").combobox("getValue");
        var validStart = $("#validStart").val();
        var validEnd = $("#validEnd").val();
        if (validDateType == "range" || validDateType == "days") {
            if (validStart == null || validStart == "" || validEnd == null || validEnd == "") {
                return false;
            }
        }
        return true;
    },
    commitForm: function (formName, panelName) {
        // 检查有效期范围
        if (!CouponMgr.checkValidDate()) {
            showMsgPlus("优惠券管理", "<span style='color: #ff2222'>请输入优惠券有效期范围!</span>", 2000);
            return;
        }
        $("#" + formName).form('submit', {
            url: '/lxbcommon/coupon/commitCoupon.jhtml',
            success: function (result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("优惠券管理", result.msg);
                    CouponMgr.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('优惠券管理', result.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('优惠券管理', "操作失败,请重试!", 'error');
            }
        });
    },
    closeCoupon: function (id) {
        $.ajax({
            url: '/lxbcommon/coupon/closeCoupon.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function (result) {
                $.messager.progress('close');
                if (result.success) {
                    showMsgPlus("优惠券管理", result.msg);
                    CouponMgr.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('优惠券管理', result.msg, 'error');
                }
            },
            error: function () {
                $.messager.progress('close');
                $.messager.alert('优惠券管理', "操作失败,请重试!", 'error');
            },
            beforeSend: function (data) {
                $.messager.progress({
                   msg: '操作正在进行,请稍候...'
                });
            }

        });
    },
    openCoupon: function (id) {
        $.ajax({
            url: '/lxbcommon/coupon/openCoupon.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function (result) {
                $.messager.progress('close');
                if (result.success) {
                    showMsgPlus("优惠券管理", result.msg);
                    CouponMgr.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('优惠券管理', result.msg, 'error');
                }
            },
            error: function () {
                $.messager.progress('close');
                $.messager.alert('优惠券管理', "操作失败,请重试!", 'error');
            },
            beforeSend: function (data) {
                $.messager.progress({
                    msg: '操作正在进行,请稍候...'
                });
            }
        });
    },
    editCoupon: function (id) {
        CouponMgr.detailForm(id);
    }
};