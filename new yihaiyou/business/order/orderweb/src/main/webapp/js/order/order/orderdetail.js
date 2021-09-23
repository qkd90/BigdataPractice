/**
 * Created by zzl on 2016/4/1.
 */
$(function() {
    OrderDetail.initDataList();
})
var OrderDetail = {
    table: $("#abnormal-orderDetail-table"),
    productType: null,
    searcher: $("#abnormal-orderDaetail-searcher"),
    searcher_toolbar_name: $("#abnormal-orderDaetail-searcher"),
    initDataList: function () {
        this.table.datagrid({
            url: '/order/orderDetail/abnormalList.jhtml',
            queryParams: {
                'orderProperty': "order.createTime",
                'orderType': "desc",
                'orderDetail.neededStatuses[0]': 'FAILED',
                'orderDetail.filterProType[0]': 'recharge'
            },
            fit: true,
            pagination: true,
            pageList: [10, 30, 50],
            rownumbers: false,
            //fitColumns: true,
            singleSelect: false,
            striped: true,
            ctrlSelect: true,
            columns: [[
                {
                    field: 'id',
                    title: '订单详情ID',
                    width: 70
                },
                {
                    field: 'order.id',
                    title: '订单ID',
                    width: 50
                },
                {
                    field: 'OPT',
                    title: '操作',
                    width: 150,
                    formatter:OrderDetail.optFormat
                },
                {
                    field: 'order.orderNo',
                    title: '订单号',
                    width: 200
                },
                {
                    field: 'order.mobile',
                    title: '联系电话',
                    width: 150
                },
                //{
                //    field: 'orderTouristList',
                //    title: '旅客信息',
                //    width: 100,
                //    formatter:function(value, rowData, rowIndex) {
                //        var info = ""
                //        $.each(value, function (i, tourist) {
                //            var peopleType = getPeopleType(tourist.peopleType);
                //            if (i == value.length - 1) {
                //                info = tourist.name + "(" + peopleType + ")";
                //            } else {
                //                info = tourist.name + "(" + peopleType + ")" + "/";
                //            }
                //        });
                //        return info;
                //    }
                //},
                {
                    field: 'order.recName',
                    title: '联系人',
                    width: 100
                },
                {
                    field: 'order.createTime',
                    title: '下单时间',
                    width: 180
                },
                {
                    field: 'productType',
                    title: '类型',
                    width: 60,
                    formatter:function(value, rowData, rowIndex) {
                        return getProductType(value);
                    }
                },
                {
                    field: 'unitPrice',
                    title: '单价',
                    width: 60
                },
                {
                    field: 'num',
                    title: '数量',
                    width: 60
                },
                {
                    field: 'finalPrice',
                    title: '总价',
                    width: 50
                },
                {
                    field: 'playDate',
                    title: '入住/起飞/出发时间',
                    width: 180
                },
                {
                    field: 'leaveDate',
                    title: '退房时间(仅酒店)',
                    width: 140,
                    formatter:function(value, rowData, rowIndex) {
                        if (value != null && value != "") {
                            return value;
                        } else {
                            return "-";
                        }
                    }
                },
                {
                    field: 'seatType',
                    title: '房型/席位/舱位',
                    width: 100
                },
                {
                    field: 'status',
                    title: '状态',
                    width: 60,
                    formatter:function(value, rowData, rowIndex) {
                        return getOrderDetailStatus(value);
                    }
                },
                {
                    field: 'realOrderId',
                    title: '接口订单ID',
                    width: 180
                },
                {
                    field: 'apiResult',
                    title: '接口消息',
                    width: 500
                },
            ]],
            toolbar: this.searcher_toolbar_name
        });
    },
    optFormat: function(value, rowData, rowIndex) {
        return Orders.openLog(rowData.order.id, rowData.id);
    },
    doSearch: function() {
        var searchType = this.searcher.find("#search-type").val();
        var status = this.searcher.find("#search-status").val();
        var searchForm = {};
        searchForm[searchType] = this.searcher.find("#search-content").val();
        searchForm['orderDetail.productType'] = this.searcher.find("#search-productType").val();
        searchForm['orderProperty'] = this.searcher.find("#search-sort-property").val();
        searchForm['orderType'] = this.searcher.find("#search-sort-type").val();
        searchForm['orderDetail.filterProType[0]'] = 'recharge';
        if (status != "0") {
            searchForm['orderDetail.neededStatuses[0]'] = 'FAILED';
        }
        this.table.datagrid('load', searchForm);
    }
};
function getOrderDetailStatus(status) {
    if (status == "BOOKING") {
        return "预定中";
    } else if (status == "SUCCESS") {
        return "预定成功";
    } else if (status == "WAITING") {
        return "等待支付";
    } else if (status == "PAYED") {
        return "已支付";
    } else if (status == "CANCELED") {
        return "取消";
    } else if (status == "CANCELING") {
        return "取消中";
    } else if (status == "FAILED") {
        return "<span style='color: #f55'>交易失败</span>";
    } else if (status == "RETRY") {
        return "<span style='color: #ff6600'>重试</span>";
    } else {
        return "-"
    }
}
function getPeopleType(peopleType) {
    if (peopleType == "ADULT") {
        return "成";
    } else if (peopleType == "CHILD") {
        return "童";
    } else if (peopleType == "BABY") {
        return "婴";
    } else if (peopleType == "STUDENT") {
        return "学";
    } else {
        return "-";
    }
}