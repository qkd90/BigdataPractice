/**
 * Created by vacuity on 15/11/2.
 */


$(function() {
    CustomerBalance.initComponent();
    CustomerBalance.init();
});

var CustomerBalance={
    table: $("#memberDg"),
    searcher: $("#member-searcher"),

    initComponent: function() {
        //$("#edit_user_gender").add("#add_user_gender").combobox({
        //    data: this.gender,
        //    valueField: 'id',
        //    textField: 'text',
        //    panelHeight: 'auto'
        //});
        //$("#edit_user_status").add("#add_user_status").combobox({
        //    data: this.status,
        //    valueField: 'id',
        //    textField: 'text',
        //    panelHeight: 'auto'
        //});
        //$("#edit_member_birthday").add("#edit_member_birthday").datebox({});
    },
    init: function(){
        $("#memberDg").datagrid({
            fit:true,
            url:'/customerBalance/customerBalance/withdrawList.jhtml',
            pagination:true,
            pageList:[20,30,50],
            rownumbers:false,
            //fitColumns:true,
            singleSelect:false,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            columns:[[
                //{field: 'ck', checkbox: true },
                {field: 'orderNo', title: '编号', width: 180, align: 'center'},
                {field:'price',title:'金额',width:90, align: 'center'},
                {field: 'user.userName', title: '用户名', width: 120, align: 'center'},
                {field:'user.gender',title:'性别',width:50, align: 'center'},
                {field:'status',title:'状态',width:80, align: 'center',
                    formatter: function(value, rowData, rowIndex) {
                        if (rowData.status == "DELETED") {
                            return "已删除";
                        } else if (rowData.status == "SUCCESS") {
                            return "提现成功";
                        } else if (rowData.status == "FAILED") {
                            return "提现失败";
                        } else if (rowData.status == "WAIT") {
                            return "待审核";
                        } else if (rowData.status == "CANCELED") {
                            return "已取消";
                        } else if (rowData.status == "CLOSED") {
                            return "交易关闭";
                        }
                    }
                },
                {field:'remark',title:'备注',width:200},
                {field:'createTime',title:'提交时间',width:150, align: 'center'},
                {field:'modifyTime',title:'审核时间',width:150, align: 'center'},
                {field:'opt',title:'操作',width:150, align: 'center',
                    formatter: function(value, rowData, rowIndex) {
                        if (rowData.status == "DELETED" || rowData.status == "SUCCESS" || rowData.status == "CANCELED") {
                            return "";
                        } else {
                            var withdrawBtn = '<a href="#" style="color: #0000cc" class="easyui-linkbutton" onclick="CustomerBalance.doWithdraw('+ rowData.id +')">通过</a>';
                            var cancelBtn = '<a href="#" style="color: #0000cc" class="easyui-linkbutton" onclick="CustomerBalance.doClose('+ rowData.id +')">取消</a>';
                            return withdrawBtn + "&nbsp;&nbsp;" + cancelBtn;
                        }
                    }
                }
            ]],
            toolbar: '#member-searcher'
        });
    },

    // 表格查询
    doSearch: function(){
        var searchForm = {};

        searchForm['order.orderNo'] = $("#search-orderNo").textbox("getValue");
        searchForm['order.user.userName'] = $("#search-userName").textbox("getValue");
        searchForm['order.priceStart'] = $("#search-priceStart").numberbox("getValue");
        searchForm['order.priceEnd'] = $("#search-priceEnd").numberbox("getValue");
        //searchForm['order.user.gender'] = $("#search-gender").combobox("getValue");
        searchForm['order.status'] = $("#search-status").combobox("getValue");

        $("#memberDg").datagrid('load', searchForm);
    },

    clearSearch: function() {
        $("#search-orderNo").textbox("setValue", "");
        $("#search-userName").textbox("setValue", "");
        $("#search-priceStart").numberbox("setValue", "");
        $("#search-priceEnd").numberbox("setValue", "");
        //$("#search-gender").combobox("setValue", "");
        $("#search-status").combobox("setValue", "");

        $("#memberDg").datagrid('load', {});
    },

    doWithdraw: function(id) {
        var url = "/customerBalance/customerBalance/doTransfers.jhtml";
        var data = {
            orderId:id
        };
        $.messager.progress({
            msg:'正在处理中，请稍候。。。。'
        });
        $.post(
            url,
            data,
            function(result) {
                $.messager.progress('close');
                if (result.success) {
                    $("#memberDg").datagrid('load', {});
                } else {
                    show_msg(result.errorMsg);
                    $("#memberDg").datagrid('load', {});
                }
            }
        );
    },
    doClose: function(id) {
        var url = "/customerBalance/customerBalance/closeTransers.jhtml";
        var data = {
            orderId:id
        };
        $.messager.progress({
            msg:'正在处理中，请稍候。。。。'
        });
        $.post(
            url,
            data,
            function(result) {
                $.messager.progress('close');
                if (result.success) {
                    $("#memberDg").datagrid('load', {});
                } else {
                    show_msg(result.errorMsg);
                    $("#memberDg").datagrid('load', {});
                }
            }
        );
    }
}