/**
 * Created by dy on 2016/5/30.
 */
var RefundManage = {

    init: function() {

        RefundManage.initDataGrid();

    },

    initDataGrid: function() {

        $('#dg').datagrid({
            url:'/refundlog/refundLog/refundList.jhtml',
            pagination: true,
            pageList: [10, 20],
            rownumbers: true,
            border:false,
            selectOnCheck:true,
            fitColumns: true,
            fit:true,
            columns:[[
                {field:'id', checkbox:true, width:100},
                {field:'orderNo',title:'订单编号',width:120},
                {field:'orderName',title:'订单名称',width:100},
                {
                    field:'channel',title:'退款渠道',width:50, align:'center',
                    formatter:function(value, row, index) {
                        if (value == "weixin") {
                            return "微信";
                        } else if (value == 'taobao') {
                            return "支付宝";
                        } else {
                            return "其他";
                        }
                    }
                },
                {field:'result',title:'退款状态',width:60, align:'center',
                    formatter:function(value, row, index) {
                        if (value == "FAIL") {
                            return "失败";
                        } else if (value == "WAITING") {
                            return "待审核";
                        } else if (value == "SUCCESS") {
                            return "退款成功";
                        } else if (value == "PROCESSING") {
                            return "正在退款中";
                        }
                    }
                },
                {field:'totalRefund',title:'退款金额',align:'center',width:50},
                {field:'createTime',title:'申请退款时间',width:110},
                {field:'updateTime',title:'退款处理时间',width:110,
                    formatter:function(value, row, index){
                        if (row.result == "FAIL") {
                            return "处理失败";
                        } else if (row.result == "WAITING") {
                            return "待审核";
                        } else {
                            return value;
                        }
                } },
                {field:'retrunMsg',title:'退款结果信息',width:150,
                    formatter:function(value, row, index){
                        if (row.result == "FAIL") {
                            return value;
                        } else if (row.result == "WAITING") {
                            if (row.channel == "weixin") {
                                return "请前往微信商户平台退款";
                            } else {
                                return "待审核";
                            }
                        } else {
                            return value;
                        }
                    }
                },
            ]],
            toolbar:'#searchForm',
            onCheck: function(rowIndex,rowData) {
                if (rowData.result == "WAITING" || rowData.result == "PROCESSING") {
                    $('#dg').datagrid("checkRow", rowIndex);
                } else {
                    $('#dg').datagrid("uncheckRow", rowIndex);
                }
            },
            onCheckAll: function(rows) {
                $.each(rows,
                    function(i, perValue) {
                        if (perValue.result == "WAITING" || perValue.result == "PROCESSING") {
                            $('#dg').datagrid("checkRow", i);
                        } else {
                            $('#dg').datagrid("uncheckRow", i);
                        }
                    }
                );
            },
            onLoadSuccess: function() {
                //RefundManage.doSearch();
            }
        });

    },

    fmtSearParam: function() {
        var data = {
            'orderNo':$("#sea-orderNo").textbox("getValue"),
            'orderName':$("#sea-orderName").textbox("getValue"),
            'channel':$("#sea-channel").combobox("getValue"),
            'result':$("#sea-status").combobox("getValue"),
            'rStartTime':$("#sea-rStartTime").datetimebox("getValue"),
            'rEndTime':$("#sea-rEndTime").datetimebox("getValue")
        }
        return data;
    },

    doSearch: function() {
        $('#dg').datagrid("load", RefundManage.fmtSearParam());
    },
    doClear: function() {
        $("#sea-orderNo").textbox("setValue", "");
        $("#sea-orderName").textbox("setValue", "");
        $("#sea-channel").combobox("setValue", "");
        $("#sea-status").combobox("setValue", "");
        $("#sea-rStartTime").datetimebox("setValue", "");
        $("#sea-rEndTime").datetimebox("setValue", "");
        $('#dg').datagrid("load", {});
    },


    doWxRefund: function() {
        var rows = $('#dg').datagrid("getChecked");
        if (rows.length <= 0) {
            show_msg("请选择需要处理退款记录！");
            return;
        } else if (rows.length > 1) {
            show_msg("只能选择一条退款记录提交！");
            return;
        } else if (rows[0].channel == 'taobao') {
            show_msg("只能选择微信退款渠道！");
            return;
        }
        var url = "/refundlog/refundLog/wechatRefund.jhtml";
        var data = {
            'refundLog.id': rows[0].id
        }
        $.messager.progress({
            text:'正在处理中......'
        });

        $.post(url, data,
            function(result){
                if (result.success) {
                    show_msg(result.errorMsg);
                } else {
                    show_msg(result.errorMsg);
                }
                $.messager.progress('close');
                $('#dg').datagrid("load", {});
            }
        );

    },

    doRefund: function() {
        var rows = $('#dg').datagrid("getChecked");
        if (rows.length <= 0) {
            show_msg("请选择待审核的退款记录！");
            return;
        }

        var arrIds = [];
        $.each(rows,
            function(i, perValue){
                if (perValue.result != "SUCCESS" && perValue.channel == "taobao") {
                    arrIds.push(perValue.id);
                }
            }
        );
        if (arrIds.length <= 0) {
            return;
        }

        var url = "/refundlog/refundLog/subAliRefund.jhtml";
        var data = {
            idStr: arrIds.join(",")
        }

        $.post(url, data,
            function(result){
                if (result.success) {

                    var batchNum = result.batchNum;
                    var detailData = result.detailData;
                    var paraStr = "batchNum=" + batchNum + "&detailData=" + detailData;
                    window.location.href="/refundlog/refundLog/refundAliRequest.jhtml?"+paraStr;
                } else {
                    show_msg(result.errorMsg);
                }

            }
        );


    }

};

$(function() {
   RefundManage.init();
});