/**
 * Created by vacuity on 15/11/2.
 */


$(function() {
    Customer.initComponent();
    Customer.init();
});

var Customer={
    table: $("#memberDg"),
    searcher: $("#member-searcher"),
    gender: [
        {'id': 'male', 'text': '男'},
        {'id': 'female', 'text': '女'},
        {'id': 'secret', 'text': '保密'}
    ],
    status: [
        {'id': 'activity', 'text': '激活'},
        {'id': 'lock', 'text': '锁定'},
        {'id': 'del', 'text': '删除'},
        {'id': 'blacklist', 'text': '黑名单'}
    ],
    initComponent: function() {
        $("#edit_user_gender").add("#add_user_gender").combobox({
            data: this.gender,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto'
        });
        $("#edit_user_status").add("#add_user_status").combobox({
            data: this.status,
            valueField: 'id',
            textField: 'text',
            panelHeight: 'auto'
        });
        $("#edit_member_birthday").add("#edit_member_birthday").datebox({});
    },
    init: function(){
        this.table.datagrid({
            fit:true,
            url:'/customer/customer/getMemberList.jhtml',
            pagination:true,
            pageList:[20,30,50],
            rownumbers:false,
            //fitColumns:true,
            singleSelect:false,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            queryParams: {
                'orderProperty': "createdTime",
                'orderType': "desc"
            },
            columns:[[
                //{field: 'ck', checkbox: true },
                {field: 'id', title: 'ID', width: 110, align: 'center'},

                {field: 'head', title: '用户头像', width: 150, align: 'center',
                    formatter : function(value, row, rowIndex){
                        if (value != null && value != undefined && value != '') {
                            if (value.substring(0, 4) == "http") {
                                return '<img src="' + value + '" width="100" height="100"/>';
                            } else {
                                return '<img src="' + QINIU_BUCKET_URL + value + '"width="100" height="100"/>';
                            }
                        }
                        return '<span style="color: red; font-weight: bold">暂无头像</span>';
                    }
                },
                {field:'account',title:'账户',width:290, align: 'center', formatter:Customer.isWeixinUser},
                {field:'userName',title:'用户名',width:170, align: 'center'},
                {field:'nickName',title:'昵称',width:170, align: 'center'},
                {field:'gender',title:'性别',width:130, align: 'center', formatter: Customer.formatGender},
                //{field:'mobile',title:'手机号码',width:150, align: 'center'},
                {field:'status',title:'状态',width:130, align: 'center', formatter: Customer.formatStatus},
                {field:'telephone',title:'电话',width:150, align: 'center'},
                {field:'email',title:'邮箱',width:200, align: 'center'},
                {field:'balance',title:'余额',width:130, align: 'center'},
                {field:'end',title:'操作',width:200, align: 'center',
                    formatter: function (value, rowData, index) {
                        var editClick = " ";
                        var detailbtn = "<a style='color: blue;text-decoration: underline;margin-right:15px;' href='#' onClick='Customer.detailForm(" + rowData.id + ")' >编辑</a>";

                        var rechargeBtn = "<a style='color: blue;text-decoration: underline; margin-right:15px;' href='#' onClick='Customer.doRecharge(" + rowData.id + ", "+ rowData.balance +")' >充值</a>"

                        var doViewAccountLog = "<a style='color: blue;text-decoration: underline;' href='#' onClick='Customer.doViewAccountLog(" + rowData.id + ")' >充值记录</a>"

                        return detailbtn + rechargeBtn + doViewAccountLog;
                    }
                }
                //{field:'address',title:'地址',width:200, align: 'center'}
                //{field:'userType',title:'账户类型',width:100,formatter:function(value, row, index){
                //    if(row.userType!=null && row.userType=="AllSiteManage"){
                //        return "<span style='color:blue'>网站管理员</span>";
                //    }else if(row.userType!=null && row.userType=="SiteManage"){
                //        return "<span style='color:red'>站点管理员</span>";
                //    }else if(row.userType!=null && row.userType=="ADMIN"){
                //        return "<span style='color:forestgreen'>部门管理员</span>";
                //    }else {
                //        return "<span style='color:forestgreen'>普通用户</span>";
                //    }
                //} },
            ]],
            toolbar: '#member-searcher',
            onDblClickCell: function(index, field, value){
                var rows = Customer.table.datagrid('getSelections');
                var userId = rows[0].id;
                Customer.detailForm(userId);
            }
        });
    },

    formatGender: function(gender) {
        if (gender == "male") {
            return "男";
        } else if (gender == "female") {
            return "女";
        } else if (gender == "secret") {
            return "保密";
        } else {
            return "保密"
        }
    },

    formatStatus: function(status) {
        if (status == "activity") {
            return "激活";
        } else if (status == "lock") {
            return "锁定";
        } else if (status == "del") {
            return "删除";
        } else if (status == "blacklist")  {
            return "黑名单"
        } else {
            return "-";
        }
    },

    isWeixinUser: function(value, rowData, index) {
        if (rowData.thirdPartyUserType != null && rowData.thirdPartyUserType == "weixin") {
            return "微信用户";
        } else {
            return value;
        }
    },

    // 表格查询
    doSearch: function(){
        var searchForm = {};

        var searchType = this.searcher.find("#search-type").combobox("getValue");
        searchForm[searchType] = this.searcher.find("#search-content").textbox("getValue");
        searchForm['member.status'] = this.searcher.find("#search-status").combobox("getValue");
        //searchForm['orderProperty'] = this.searcher.find("#search-sort-property").val();
        //searchForm['orderType'] = this.searcher.find("#search-sort-type").val();
        searchForm['member.gender'] = this.searcher.find("#search-gender").combobox("getValue");
        //searchForm['member.minJifen'] = this.searcher.find("#minJifen").val();
        //searchForm['member.maxJifen'] = this.searcher.find("#maxJifen").val();
        this.table.datagrid('load', searchForm);
    },

    doRecharge: function(id, balance) {
        $("#balance").html(balance);
        $("#recharge_panel").dialog({
            title:'充值金额',
            modal:true,
            buttons:[{
                text:'提交',
                handler:function(){
                    if ($("#recharge-balance").numberbox("getValue")) {
                        var url = "/customerBalance/customerBalance/doCustomerRecharge.jhtml?id=" + id;
                        $.post(url, {'member.balance': $("#recharge-balance").numberbox("getValue")}, function(data) {
                            if (data.success) {
                                showMsgPlus("充值提醒", "充值成功！");
                                $("#recharge_panel").dialog("close");
                                Customer.doSearch();
                            } else {
                                showMsgPlus("充值提醒", "充值失败！");
                            }
                        });
                    } else {
                        showMsgPlus("充值提醒", "充值金额需大于0！");
                    }
                }
            },{
                text:'取消',
                handler:function(){
                    $("#recharge_panel").dialog("close");
                }
            }],
            onClose:function(){
                $("#balance").html(0);
                $("#recharge-balance").numberbox("setValue", 0);
            }
        });
        $("#recharge_panel").dialog("open");
    },

    doViewAccountLog: function(id) {
        $("#accountLog_panel").dialog({
            title:'充值记录',
            modal:true,
            onOpen:function(){
                $("#accoungLogDg").datagrid({
                    fit:true,
                    url:'/customerBalance/customerBalance/getAccountLogList.jhtml',
                    pagination:true,
                    pageList:[10,20,30],
                    rownumbers:false,
                    fitColumns:true,
                    singleSelect:false,
                    striped:true,//斑马线
                    ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
                    queryParams: {
                        'accountLog.accountUser.id': id,
                        'accountLog.type': "recharge",
                        'accountLog.orderType': "recharge"
                    },
                    columns:[[
                        {field: 'orderNo', title: '充值编号', width: 170, align: 'center'},
                        {field: 'money', title: '金额', width: 100, align: 'center'},
                        {field:'accountUser.account',title:'账户',width:250, align: 'center'},
                        {field:'balance',title:'余额',width:130, align: 'center'},
                        {field:'status',title:'状态',width:130, align: 'center', formatter:Customer.fmtAccountLog},
                        {field:'user.account',title:'操作人',width:130, align: 'center'}
                    ]],
                    onLoadSuccess: function(data) {
                        if (!data.rows.length > 0) {
                            showMsgPlus("提醒", "暂无充值记录！");
                            $("#accountLog_panel").dialog("close");
                        }
                    }
                });
            }
        });
        $("#accountLog_panel").dialog("open");
    },

    fmtAccountLog: function(status) {
        if (status == "submit") {
            return "提交";
        } else if (status == "reject") {
            return "拒绝";
        } else if (status == "normal") {
            return "正常";
        } else if (status == "processing") {
            return "处理中";
        } else if (status == "fail") {
            return "失败";
        } else {
            return "-";
        }
    },

    detailForm: function(id){
        var editUrl="/customer/customer/memberDetail.jhtml?id=" + id;
        $("#edit_form").form('load', editUrl);
        //Detail.init(id);
        $("#edit_panel").dialog({
            title:'客户详情',
            modal:true,
            onClose:function(){
                $("#edit_form").form('clear');
            }
        });
        $("#edit_panel").dialog("open");
    },
    commitForm: function(formName, panelName) {
        $("#" + formName).form('submit', {
            url: '/customer/customer/saveMember.jhtml',
            success: function (data) {
                data = eval('(' + data + ')');
                if (data.success) {
                    $("#" + panelName).dialog("close");
                    showMsgPlus("客户管理", data.msg);
                    Customer.table.datagrid('reload');
                } else if (!data.success) {
                    $.messager.alert('客户管理', data.msg, 'error');
                }
            },
            error: function() {
                $.messager.alert('客户管理', "操作失败,请重试!", 'error');
            }
        });
    }
}