/**
 * Created by zzl on 2016/10/31.
 */
$(function() {
    ApiMonitor.init();
});
var ApiMonitor = {
    init: function() {
        $('#dg').datagrid({
            url: '/apidata/apiMonitor/list.jhtml',
            fit: true,
            pagination: false,
            rownumbers: true,
            singleSelect:true,
            striped:true,//斑马线
            columns: [[
                {
                    field: 'id',
                    title: '标识',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'name',
                    title: '接口说明',
                    width: 180,
                    align: 'center'
                },
                {
                    field: 'testTime',
                    title: '测试时间',
                    width: 150,
                    align: 'center',
                    datePattern: 'yyyy-MM-dd HH:mm:ss',
                    formatter: BgmgrUtil.dateTimeFmt
                },
                {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'remark',
                    title: '说明',
                    width: 160,
                    align: 'center'
                },
                { field: 'opt', title: '操作', width: 100, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='ApiMonitor.doReTest(\"" + rowData.id + "\")'>重新测试</a>";
                        return btn;
                    }
                }
            ]],
            onBeforeLoad : function(data) {   // 查询参数

            }
        });
    },
    // 查询
    doSearch: function() {
        $('#dg').datagrid('load', {});
    },
    // 重新测试
    doReTest: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post("/apidata/apiMonitor/reTest.jhtml",
            {id: id},
            function(data) {
                $.messager.progress("close");
                if (data && data.success) {
                    ApiMonitor.doSearch();
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
};
