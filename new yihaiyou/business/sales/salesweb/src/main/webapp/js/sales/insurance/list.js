/**
 * Created by zzl on 2016/7/6.
 */
$(function () {
    InsuranceMgr.initComponent();
    InsuranceMgr.initEditor();
    InsuranceMgr.initData();
});

var InsuranceMgr = {
    table: $("#insuranceDg"),
    searcher: $("#coupon-searcher"),
    status: [
        {'id': 'up', 'text': '上架'},
        {'id': 'down', 'text': '下架'},
    ],
    initComponent: function () {
        this.initTypeCombo({comboId: "search-insurance-type"});
    },
    initTypeCombo: function (options) {
        $("#" + options.comboId).combotree({
            url: '/goods/goods/getComboCatgoryData.jhtml?type=insurance',
            onLoadSuccess: options.onLoadSuccess
        });
    },
    initEditor: function() {
        $.each($("#insurance_form").find('textarea'), function (i, item) {
            $(this).xheditor({
                tools: 'FontSize,|,Removeformat,|,Preview',
                skin: 'default',
                submitID: 'submit_insurance'
            });
        });
    },
    initStatus: function() {
        $("#insurance_status").combobox({
            data: this.status,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto',
            onLoadSuccess: function() {
                $('#insurance_status').combobox('select', 'up');
            }
        });
    },
    initData: function () {
        this.table.datagrid({
            url: '/sales/insurance/getInsuranceList.jhtml',
            fit: true,
            pagination: true,
            pageList: [20, 30, 50],
            rownumbers: false,
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
                    field: 'OPT',
                    title: '操作',
                    width: 120,
                    align: 'center',
                    formatter: InsuranceMgr.optFormat
                },
                {
                    field: 'name',
                    title: '保险名称',
                    width: 300,
                    align: 'center'
                },
                {
                    field: 'status',
                    title: '状态',
                    width: 80,
                    align: 'center',
                    formatter: InsuranceMgr.statusFormat
                },
                {
                    field: 'company',
                    title: '保险公司',
                    width: 250,
                    align: 'center'
                },
                {
                    field: 'price',
                    title: '价格',
                    width: '100',
                    align: 'center'
                },
                {
                    field: 'category.name',
                    title: '保险分类',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    width: 140,
                    align: 'center'
                }
            ]],
            toolbar: this.searcher
        });
    },
    statusFormat: function (value, rowData, rowIndex) {
        if (value == "up") {
            return "<span style='color:#3c763d;'>上架</span>";
        } else if (value == "down") {
            return "<span style='color:#F30;'>下架</span>";
        } else {
            return "-";
        }
    },
    optFormat: function(value, rowData, rowIndx) {
        var btn = "";
        var upClick = " onClick='InsuranceMgr.upInsurance(" + rowData.id + ")'"
        var downClick = " onClick='InsuranceMgr.downInsurance(" + rowData.id + ")'"
        var editClick = " onClick='InsuranceMgr.editInsurance(" + rowData.id + ")'";
        btn += "<div class='opt' >";
        if (rowData.status == "up") {
            btn += "<a class='ena' href='#'" + downClick + ">下架</a>"
            btn += "&nbsp;&nbsp;";
        } else if (rowData.status == "down") {
            btn += "<a class='ena' href='#'" + upClick + ">上架</a>"
            btn += "&nbsp;&nbsp;";
        }
        btn += "<a class='ena' href='#'" + editClick + ">编辑/查看</a>"
        btn += "</div>";
        return btn;
    },
    doSearch: function () {
        var searchForm = {};
        var searchType = this.searcher.find("#search-type").val();
        searchForm[searchType] = this.searcher.find("#search-content").val();
        searchForm['insurance.status'] = this.searcher.find("#search-status").val();
        searchForm['insurance.category.id'] = this.searcher.find("#search-insurance-type").combobox("getValue");
        searchForm['orderProperty'] = this.searcher.find("#search-sort-property").val();
        searchForm['orderType'] = this.searcher.find("#search-sort-type").val();
        searchForm['insurance.minPrice'] = this.searcher.find("#minPrice").val();
        searchForm['insurance.maxPrice'] = this.searcher.find("#maxPrice").val();
        this.table.datagrid('load', searchForm);
    },
    clearSearch: function() {
        this.searcher.form('clear');
    },
    upInsurance: function (id) {
        $.ajax({
            url: '/sales/insurance/upInsurance.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function (result) {
                $.messager.progress('close');
                if (result.success) {
                    showMsgPlus("保险管理", result.msg);
                    InsuranceMgr.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('保险管理', result.msg, 'error');
                }
            },
            error: function () {
                $.messager.progress('close');
                $.messager.alert('保险管理', "操作失败,请重试!", 'error');
            },
            beforeSend: function (data) {
                $.messager.progress({
                    msg: '操作正在进行,请稍候...'
                });
            }
        });
    },
    downInsurance: function (id) {
        $.ajax({
            url: '/sales/insurance/downInsurance.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                'id': id
            },
            success: function (result) {
                $.messager.progress('close');
                if (result.success) {
                    showMsgPlus("保险管理", result.msg);
                    InsuranceMgr.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('保险管理', result.msg, 'error');
                }
            },
            error: function () {
                $.messager.progress('close');
                $.messager.alert('保险管理', "操作失败,请重试!", 'error');
            },
            beforeSend: function (data) {
                $.messager.progress({
                    msg: '操作正在进行,请稍候...'
                });
            }
        });
    },
    addInsurance: function () {
        $("#insurance_panel").dialog({
            title: '添加新保险',
            modal: true,
            shadow:false,
            top: 0,
            onClose: function () {
                $("#insurance_form").form('clear');
            }
        });
        InsuranceMgr.initTypeCombo({
            comboId: "insurance_category",
            onLoadSuccess: function() {
                $("#insurance_panel").dialog('open');
            }
        });
        InsuranceMgr.initStatus();
    },
    editInsurance: function (id) {
        var detailUrl = "/sales/insurance/detailInsurance.jhtml?id=" + id;
        InsuranceMgr.initStatus();
        InsuranceMgr.initTypeCombo({
            comboId: "insurance_category",
            onLoadSuccess: function() {
                $("#insurance_form").form('load', detailUrl);
            }
        });
        $("#insurance_form").form({
            onLoadSuccess: function(result) {
                $("#insurance_panel").dialog('open');
            }
        });
        $("#insurance_panel").dialog({
            title: '查看/编辑保险',
            modal: true,
            shadow:false,
            top: 0,
            onClose: function () {
                $("#insurance_form").form('clear');
            }
        });
    },
    commitForm: function(formName, panelName) {
        $("#" + formName).form('submit', {
            url: '/sales/insurance/commitInsurance.jhtml',
            success: function (result) {
                result = eval('(' + result + ')');
                if (result.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("保险管理", result.msg);
                    InsuranceMgr.table.datagrid('reload');
                } else if (!result.success) {
                    $.messager.alert('保险管理', result.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('保险管理', "操作失败,请重试!", 'error');
            }
        });
    }
};
