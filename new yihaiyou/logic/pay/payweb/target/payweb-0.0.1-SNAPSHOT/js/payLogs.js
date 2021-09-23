/**
 * Created by vacuity on 15/10/22.
 */


$(function() {
    payLogs.init();
});

var payLogs={

    searchparam: $("#searchparam"),

    init: function(){
        $('#logsDg').datagrid({
            url:'/pay/pay/getList.jhtml',
            fit:true,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            pagination: true,
            pageList: [10, 20],
            rownumbers: true,
            fitColumns: true,
            columns:[[
                {field:'id',title:'编号',width:100,sortable: true},
                {field:'order.id',title:'订单编号',width:100,sortable: true},
                {field:'order.recName',title:'用户名',width:100,sortable: true},
                {field:'action',title:'日志类型',width:100,sortable: true,formatter:function(value,row,index){
                    if(row.action!=null && row.action=="search"){
                        return "<font style='color:blue'>查询</font>";
                    }else if(row.action!=null && row.action=="pay"){
                        return "<font style='color:red'>支付请求</font>";
                    }else if(row.action!=null && row.action=="requestback"){
                        return "<font style='color:forestgreen'>支付回调</font>";
                    }
                } },
                {field:'payAccount',title:'付款账户',width:100,sortable: true},
                {field:'cost',title:'金额',width:100,sortable: true},
                {field:'requestTime',title:'记录时间',width:100,sortable: true},
                {field:'tongdao',title:'支付方式',width:100,sortable: true,formatter:function(value,row,index){
                    if(row.tongdao=="weixin"){
                        return "<font>微信</font>";
                    }else{
                        return "<font>支付宝</font>";
                    }
                } },
            ]],
            toolbar: '#searchparam'
        });
    },

    doSearch: function () {
        var searchForm = {};
        searchForm['orderId'] = $("#orderId").val();
        searchForm['name'] = $("#name").val();
        searchForm['mobile'] = $("#mobile").val();
        searchForm['begin'] = $("input[name='sTime']").val();
        searchForm['end'] = $("input[name='eTime']").val();
        $('#logsDg').datagrid('load', searchForm);
    }

}


