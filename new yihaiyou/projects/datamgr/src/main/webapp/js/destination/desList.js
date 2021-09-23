/**
 * Created by zzl on 2016/1/23.
 */
$(function () {
    // 初始化省份城市选择器
    initProvince();
    // 构建表格
    $('#qryResult').datagrid({
        fit: true,
        //title: "目的地列表",
        thread: '',
        url: '/destination/tbAreaMgr/list.jhtml',
        border: true,
        singleSelect: false,
        striped: true,
        pagination: true,
        remoteSort: false,
        multiSort: true,
        pageSize: BizConstants.PAGESIZE,
        pageList: BizConstants.PAGELIST,
        columns: [[{
            field: 'id',
            title: 'ID',
            sortable: true,
            width: 250,
            align: "center"
        }, {
            field: 'name',
            title: '名称',
            width: 370,
            align: 'center'
        },  {
            field: 'cityCode',
            title: '城市编码',
            width: 230
        }, {
            field: 'fullPath',
            title: '全名',
            sortable: true,
            width: 280,
            align: "center"
        }, {
            field: 'cover',
            title: '封面图片',
            align: 'center',
            formatter: coverFormatter,
            width: 250
        }, {
            field: "OPT",
            title: "操作",
            align: "center",
            width: 350,
            formatter: optFormatter
        }
        ]],
        toolbar: '#toolbar',
        onBeforeLoad: function (data) {   // 查询参数
            data.name = $("#qry_name").textbox("getValue");
            var regionCode = $('#qry_cityCode').val();
            if (regionCode && regionCode!= "") {
                data.cityCode = regionCode;
            }
            data.isChina = $("#qry_isChina").val();
            //var cityCode = $('#qry_city').combobox('getValue');
            //if (cityCode == null || cityCode == '') {
            //    cityCode = $('#qry_province').combobox('getValue');
            //    if (cityCode != null &&　cityCode != '') {
            //        data.cityCode = cityCode.substr(0, 2);
            //    }
            //}
            //else {
            //    data.cityCode = cityCode.substr(0, 4);
            //}
            var condition = $('#qry_condition').combobox('getValue');
            switch (condition) {
                case '1':
                    data.hasCover = 1;
                    break;
                case '2':
                    data.hasCover = 0;
                    break;
                case '3':
                    data.hasBestVisitTime = 0;
                    break;
                case '4':
                    data.hasAdviceTime = 0;
                    break;
                case '5':
                    data.hasAbs = 0;
                    break;
            }
        }
    });

});
// 查询
function query() {
    $("#qryResult").datagrid("load", {});
}
// 重置
function reset() {
    $('#qryCondition').form('clear');
}
// 编辑
function edit(id) {
    window.parent.addTab('编辑目的地', "/destination/tbAreaMgr/edit.jhtml?id=" + id);
}
function coverFormatter(value, rowData, rowIndex) {
    if (rowData.tbAreaExtend.cover != undefined && rowData.tbAreaExtend.cover != '' && rowData.tbAreaExtend.cover != null) {
        return '<img src="' + BizConstants.QINIU_DOMAIN + rowData.tbAreaExtend.cover + '?imageView2/1/w/100/h/80/q/75' + '"/>';
    }
    return '<span style="color: red">无</span>';
}
function optFormatter(value, rowData, rowIndex) {
    var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='edit(" + rowData.id + ")'>编辑</a>";
    return btnEdit;
}