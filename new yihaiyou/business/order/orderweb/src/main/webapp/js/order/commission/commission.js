var Commissions = {
    table: $("#commission-table"),
    init: function() {
        this.initTable();
    },
    initTable: function () {
        this.table.datagrid({
            url: '/order/commission/list.jhtml',
            queryParams: {

            },
            pagination: true,
            pageList: [10, 30, 50],
            rownumbers: true,
            fitColumns: true,
            singleSelect: false,
            striped: true,//斑马线
            ctrlSelect: true,// 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                {field: 'orderNo', title: '订单号', width: 180, sortable: true},
                {field: 'product', title: '商品名称', width: 120, sortable: true, formatter: function (value, row) {
                    return value.name;
                }},
                {field: 'user', title: '订单来源', width: 150, sortable: true, formatter: function (value, row) {
                    return value.userName;
                }},
                {field: 'money', title: '佣金', width: 120, sortable: true},
                {field: 'level', title: '分成来源', width: 350, sortable: true, formatter: function (value, row) {
                    if (value == 'SELLER') {
                        return "卖出";
                    }else if (value == 'LEVEL1') {
                        return "直属分销商卖出";
                    } else if (value == 'LEVEL2') {
                        return "次级分销商卖出";
                    }  else if (value == 'TOP') {
                        return "分销商卖出";
                    }
                }
                }
            ]],
            toolbar: this.searcher_toolbar_name
        });
    }


};
Commissions.init();