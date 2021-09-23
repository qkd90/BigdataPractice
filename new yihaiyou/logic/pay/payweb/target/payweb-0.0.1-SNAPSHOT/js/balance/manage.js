/**
 * Created by dy on 2016/3/8.
 */
var Balance = {
    AccountType : {
        consume:{symbol:'-',desc:'消费',color:'green'},
        recharge:{symbol:'+',desc:'充值',color:'orange'},
        refund:{symbol:'+',desc:'退款',color:'orange'},
        in:{symbol:'+',desc:'入账',color:'orange'},
        out:{symbol:'-',desc:'出账',color:'green'},
        outlinerc:{symbol:'+',desc:'充值',color:'orange'},
        outlinewd:{symbol:'-',desc:'提现',color:'green'},
        withdraw:{symbol:'-',desc:'提现',color:'green'},
        running:{symbol:'',desc:'流水',color:''}},

    init: function() {
        Balance.initTabs();
        $("#tabs").tabs("select", 0)
        Balance.initRecharge_dg();
    },
    // 修改表头对齐方式
    alignTitle: function() {
        $('.datagrid-header .datagrid-cell').css('text-align', 'center');
    },
    initTabs: function() {

        $('#tabs').tabs({
            onSelect:function(title, index){
                if (index == 0) {
                    Balance.initRecharge_dg();
                } else if (index == 1) {
                    Balance.initWithdraw_dg();
                } else if (index == 2) {
                    Balance.initConsume_dg();
                } else if (index == 3) {
                    Balance.initBalance__dg();
                }
            }
        });
    },

    applyRecharge: function() {
        var url = "/balance/balance/createOrder.jhtml";
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog("open");
    },
    // 打开申请窗口
    doOpenApplyWithdrawDg: function() {
        $('#frm_balance').html('');
        // 异步获取余额
        $.post("/balance/balance/findBalance.jhtml", {},
            function(data){
                if (data && data.success) {
                    $('#frm_balance').html(data.balance);
                } else {
                    $('#frm_balance').html('无法获取余额信息');
                }
            },
            'json'
        );
        $('#frm_amount').numberbox('setValue', '');
        $('#applyWithdrawDg').dialog('open');
    },
    // 申请提现
    doApplyWithdraw : function() {
        var validate = $('#applyWithdrawForm').form('validate');
        if (!validate) {
            return;
        }
        var balance = $('#frm_balance').html();
        var amount = $('#frm_amount').numberbox('getValue');
        if (balance && parseFloat(balance) < amount) {
            show_msg("申请提现金额超出余额");
            return;
        }
        $.post("/balance/balance/applyWithdraw.jhtml",
            {amount: amount},
            function(data){
                if (data && data.success) {
                    $('#applyWithdrawDg').dialog('close');
                    $('#withdraw_dg').datagrid('load', {});
                } else {
                    if (data && data.errorMsg) {
                        show_msg(data.errorMsg);
                    } else {
                        show_msg("操作失败");
                    }
                }
            },
            'json'
        );
    },
    initRecharge_dg: function() {
        //var url = "/balance/balance/getRechargeList.jhtml";
        $('#recharge_dg').datagrid({
            url:'/balance/balance/findBalanceMgrList.jhtml?inType=recharge,outlinerc',
            fit:true,
            pagination:true,
            pageList:[20,30,50],
            rownumbers:true,
            singleSelect:true,
            striped:true,//斑马线
            columns:[[
                {field:'moneyStr',title:'金额',width:200,align:'right'},
                {field:'balanceStr',title:'余额',width:300,align:'right'},
                {field:'orderNo',title:'订单号',width:220,align:'center'},
                {field:'companyUnitName',title:'公司名称',width:280,align:'center'},
                {field:'statusStr',title:'状态',width:90,align:'center'},
                {field:'user.account',title:'操作人',width:150,align:'center'},
                {field:'createTime',title:'时间',width:230,align:'center'}
            ]],
            toolbar: '#recharge_tool',
            onLoadSuccess: function() {
                Balance.alignTitle();
            }
        });

    },
    // 提现记录列表
    initWithdraw_dg: function() {
        $('#withdraw_dg').datagrid({
            url:'/balance/balance/findBalanceMgrList.jhtml?inType=outlinewd,withdraw',
            fit:true,
            pagination:true,
            pageList:[20,30,50],
            rownumbers:true,
            singleSelect:true,
            striped:true,//斑马线
            columns:[[
                {field:'moneyStr',title:'金额',width:200,align:'center'},
                {field:'balanceStr',title:'余额',width:300,align:'center'},
                {field:'user.account',title:'操作人',width:150,align:'center'},
                {field:'companyUnitName',title:'公司名称',width:280,align:'center'},
                {field:'createTime',title:'时间',width:230,align:'center'},
                {field:'statusStr',title:'状态',width:90,align:'center'},
                {field:'auditTime',title:'审核时间',width:230,align:'center'},
                {field:'rejectReason',title:'拒绝原因',width:160,align:'center'}
            ]],
            toolbar: '#withdraw_tool',
            onLoadSuccess: function() {
                Balance.alignTitle();
            }
        });
    },

    initBalance__dg: function() {
        //var url = "/balance/balance/getBalanceDataList.jhtml";
        $('#balance_result_dg').datagrid({
            url:'/balance/balance/findBalanceMgrList.jhtml',
            fit:true,
            pagination:true,
            pageList:[20,30,50],
            rownumbers:true,
            singleSelect:true,
            striped:true,//斑马线
            columns:[[
                {field:'typeStr',title:'账目类型',width:100,align:'center'},
                {field:'moneyStr',title:'金额',width:200,align:'center',
                    formatter : function(value, rowData, rowIndex) {
                        var accountTypeCfg = Balance.AccountType[rowData.type]
                        if (rowData.status == 'normal' && accountTypeCfg) {
                            return '<strong style="color:'+accountTypeCfg.color+'">'+accountTypeCfg.symbol+value+'</strong>';
                        } else {
                            return value;
                        }
                    }
                },
                {field:'balanceStr',title:'余额',width:300,align:'center'},
                {field:'orderNo',title:'订单号',width:220,align:'center'},
                {field:'companyUnitName',title:'公司名称',width:280,align:'center'},
                {field:'user.account',title:'操作人',width:150,align:'center'},
                {field:'createTime',title:'时间',width:230,align:'center'},
                {field:'statusStr',title:'状态',width:90,align:'center'},
                {field:'auditTime',title:'审核时间',width:230,align:'center'},
                {field:'rejectReason',title:'拒绝原因',width:160,align:'center'}
            ]],
            toolbar: '#balance_result_tool',
            onLoadSuccess: function() {
                Balance.alignTitle();
            }
        });

    },

    initConsume_dg: function() {
        //var url = "/balance/balance/getConsumeList.jhtml";
        $('#consume_dg').datagrid({
            url:'/balance/balance/findBalanceMgrList.jhtml?inType=consume',
            fit:true,
            pagination:true,
            pageList:[20,30,50],
            rownumbers:true,
            singleSelect:true,
            striped:true,//斑马线
            columns:[[
                {field:'moneyStr',title:'金额',width:200,align:'center'},
                {field:'balanceStr',title:'余额',width:300,align:'center'},
                {field:'orderNo',title:'订单号',width:220,align:'center'},
                {field:'companyUnitName',title:'公司名称',width:280,align:'center'},
                {field:'user.account',title:'操作人',width:150,align:'center'},
                {field:'createTime',title:'时间',width:230,align:'center'}
            ]],
            toolbar: '#consume_tool',
            onLoadSuccess: function() {
                Balance.alignTitle();
            }
        });



    }


};

$(function() {
    Balance.init();
});
