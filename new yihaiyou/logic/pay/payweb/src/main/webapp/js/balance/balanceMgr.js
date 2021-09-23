var balanceMgr = {
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
		balanceMgr.initComp();
        $('.datagrid-header .datagrid-cell').css('text-align', 'center');   // 修改表头对齐方式
	},
	// 初始状态
	initStatus: function() {
		
	},
	// 表格查询
	doSearch: function() {
		$('#dg').datagrid('load', {});
	},
    // 线下重置
    doOutlinerc: function(){
        balanceMgr.doOpenBalanceDg('outlinerc');
    },
    // 提现
    doWithdraw: function(){
        balanceMgr.doOpenBalanceDg('outlinewd');
    },
	// 打开资金操作窗口
	doOpenBalanceDg: function(accountType) {
        balanceMgr.resetForm();
        $('#frm_type').val(accountType);
		$('#balanceDg').dialog('open');
	},
    // 重置表单数据
    resetForm: function() {
        $('#frm_companyId').combobox('setValue', '');
        $('#balance').html('');
        $('#frm_amount').numberbox('setValue', '100');
    },
	// 确认资金操作
    doOptBalance: function() {
        var validate = $('#balanceForm').form('validate');
        if (!validate) {
            return;
        }
        var type = $('#frm_type').val();
        var companyUnitId = $('#frm_companyId').combobox('getValue');
        var amount = $('#frm_amount').numberbox('getValue');
        $.post("/balance/balance/optBalance.jhtml",
            {type: type, companyUnitId: companyUnitId, amount: amount},
            function(data){
                if (data && data.success) {
                    $('#balanceDg').dialog('close');
                    balanceMgr.doSearch();
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
    // 通过
    doPass: function(id) {
        $.messager.confirm('温馨提示', '确认通过提现申请？', function(r){
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post("/balance/balance/pass.jhtml",
                    {id : id},
                    function(data){
                        $.messager.progress("close");
                        if (data && data.success) {
                            balanceMgr.doSearch();
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
            }
        });
    },
    // 拒绝
    doReject : function(id) {
        var id = $('#accountLogId').val();
        var reason = $('#reason').textbox('getValue');
        $.post("/balance/balance/reject.jhtml",
            {id: id, reason: reason},
            function(data){
                if (data && data.success) {
                    $('#reasonDg').dialog('close');
                    balanceMgr.doSearch();
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
    // 打开审核窗口
    doOpenReasonDg: function(id) {
        $('#accountLogId').val(id);
        $('#reason').textbox('setValue', '');
        $('#reasonDg').dialog('open');
    },
	// 初始化控件
	initComp: function() {
        // 状态
        $('#qry_status').combobox({
            data:[{'id':'','text':'全部'},{'id':'submit','text':'提交'},{'id':'reject','text':'拒绝'},{'id':'normal','text':'正常'}],
            valueField:'id',
            textField:'text'
        });
        // 公司下拉框
        $('#qry_companyId').combobox({
            loader: function(param,success,error){
                var q = param.q || '';
                if (q.length < 2) {return false;}
                $.ajax({
                    url: '/sys/sysUnit/listCompanys.jhtml',
                    dataType: 'json',
                    type: 'POST',
                    data: {q: q},
                    success: function(data){
                        success(data);
                    },
                    error: function(){
                        //error.apply(this, arguments);
                    }
                });
            },
            valueField:"unitId",
            textField:"unitName",
            mode: 'remote'
        });
        // 表单公司下拉框
        $('#frm_companyId').combobox({
            loader: function(param,success,error){
                var q = param.q || '';
                if (q.length < 2) {return false;}
                $.ajax({
                    url: '/sys/sysUnit/listCompanys.jhtml',
                    dataType: 'json',
                    type: 'POST',
                    data: {q: q},
                    success: function(data){
                        success(data);
                    },
                    error: function(){
                        //error.apply(this, arguments);
                    }
                });
            },
            valueField:"unitId",
            textField:"unitName",
            mode: 'remote',
            onSelect: function(record) {    // 获取余额信息
                if (record && record.unitId) {
                    $.post("/balance/balance/findBalance.jhtml", {companyUnitId: record.unitId},
                        function(data){
                            if (data && data.success) {
                                $('#balance').html(data.balance);
                            } else {
                                $('#balance').html('无法获取余额信息');
                            }
                        },
                        'json'
                    );
                } else {
                    $('#balance').html('');
                }
            }
        });

		// 初始化表格数据
		$("#dg").datagrid({
			fit:true,
			//title:'线路列表',
			//height:400,
			url:'/balance/balance/findBalanceMgrList.jhtml',
			pagination:true,
			pageList:[20,30,50],
			rownumbers:true,
			//fitColumns:true,
			singleSelect:true,
			striped:true,//斑马线
            columns:[[
                {field:'typeStr',title:'账目类型',width:60,align:'center'},
                {field:'moneyStr',title:'金额',width:150,align:'right',
                    formatter : function(value, rowData, rowIndex) {
                        var accountTypeCfg = balanceMgr.AccountType[rowData.type]
                        if (rowData.status == 'normal' && accountTypeCfg) {
                            return '<strong style="color:'+accountTypeCfg.color+'">'+accountTypeCfg.symbol+value+'</strong>';
                        } else {
                            return value;
                        }
                    }
                },
                {field:'balanceStr',title:'余额',width:150,align:'right'},
                {field:'orderNo',title:'订单号',width:220},
                {field:'companyUnitName',title:'公司名称',width:230},
                {field:'user.account',title:'操作人',width:150},
                {field:'createTime',title:'时间',width:230},
                {field:'statusStr',title:'状态',width:110,align:'center'},
                {field:'auditTime',title:'审核时间',width:230},
                {field:'rejectReason',title:'拒绝原因',width:260},
                {field:'opt',title:'操作',width:130,align:'center',
                    formatter : function(value, rowData, rowIndex) {
                        if (rowData.status == 'submit') {
                            var btnPass = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='balanceMgr.doPass("+rowData.id+")'>通过</a>";
                            var btnReject = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='balanceMgr.doOpenReasonDg("+rowData.id+")'>拒绝</a>";
                            return btnPass+"&nbsp;"+btnReject;
                        } else {
                            return "";
                        }
                    }
                }
            ]],
            toolbar: '#tb', 
			onBeforeLoad : function(data) {   // 查询参数 
		        data.dateStart = $("#qry_dateStart").datebox("getValue");
		        data.dateEnd = $("#qry_dateEnd").datebox("getValue");
		        data.companyId = $("#qry_companyId").combobox("getValue");
                data.status = $('#qry_status').combobox("getValue");
			}
		});
	}
};

$(function(){
	balanceMgr.init();
});

