/**
 * Created by zzl on 2016/11/24.
 */
var HotelListIndex = {
    HotelStatus: {UP: "已上架", DOWN: "已下架", UP_CHECKING: "上架中", DOWN_CHECKING: "下架中", REFUSE: "已拒绝"},
    HotelListTable: null,
    init: function() {
        HotelListIndex.initComp();
        HotelListIndex.initEvt();
        HotelListIndex.getYhyHotelList();
    },
    initComp: function() {
        $('#hotelStatusSel').btComboBox();

    },
    initEvt: function() {
        $('#hotelSearchBtn').on('click', function(event) {
            event.stopPropagation();
            HotelListIndex.doReSearchHotel();
        });
        //$('#yhyHotelList tbody').on('click', 'tr', function () {
        //    if ($(this).hasClass('selected')) {
        //        $(this).removeClass('selected');
        //    } else {
        //        $('#yhyHotelList tbody tr').removeClass('selected');
        //        $(this).addClass('selected');
        //    }
        //});
    },
    getYhyHotelList: function() {
        HotelListIndex.HotelListTable = $('#yhyHotelList').DataTable({
            //"processing": true,
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            "stateSave": true,
            "lengthMenu": [ 10, 50, 200, 600, 1000 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                data['hotel.status'] = $('input[name = "hotel.status"]').val();
                data['hotel.name'] = $('#searchProductName').val();
                $.ajax({
                    url: '/yhy/yhyHotelInfo/getYhyHotelList.jhtml',
                    data: data,
                    type: 'post',
                    progress: true,
                    dataType: 'json',
                    success: function(result) {
                        callback(result);
                    },
                    error: function() {
                        $.messager.show({
                            msg: "数据加载错误, 请稍候重试!",
                            type: "error"
                        });
                    }
                });
            },
            columns: [
                {'data': 'name', 'defaultContent': "-"},
                {'data': 'extend.address', 'defaultContent': "-"},
                {'data': 'contactName', 'defaultContent': "-"},
                {'data': 'telephone', 'defaultContent': "-"},
                {'data': 'status', 'defaultContent': "-",
                    'render': function(value, type, row, meta) {
                        return HotelListIndex.HotelStatus[value];
                    }
                },
                {'data': null, 'defaultContent': "-", 'render': HotelListIndex.getOpt}
            ]
        });
        YhyCommon.Table = HotelListIndex.HotelListTable;
    },
    doSearchHotel: function() {
        HotelListIndex.HotelListTable.ajax.reload(function(result){}, false);
    },
    doReSearchHotel: function() {
        HotelListIndex.HotelListTable.ajax.reload(function(result){}, true);
    },
    getOpt: function(value, type, row, meta) {
        var status = row.status;
        var optHtml = "";
        var editOpt = "<a onclick='HotelListIndex.editHotel("+ row.id +")' class='link-span'>编辑</a>";
        var detailOpt = "<a onclick='HotelListIndex.hotelDetail("+ row.id +")' class='link-span'>详情</a>";
        var downOpt = "<a onclick='HotelListIndex.downHotel("+ row.id +")' class='link-span'>申请下架</a>";
        var revokeOpt = "<a onclick='HotelListIndex.revokeHotel("+ row.id +")' class='link-span'>撤销</a>";
        var delOpt = "<a onclick='HotelListIndex.delHotel("+ row.id +")' class='link-span'>删除</a>";
        var picOpt = "<a onclick='HotelListIndex.pictureSorting("+ row.id +")' class='link-span'>图片排序</a>";
        if (row.imageTotalCount) {
            if (status == "UP") {
                optHtml = editOpt + detailOpt + downOpt + picOpt;
            } else if (status == "DOWN") {
                optHtml = editOpt + detailOpt + delOpt + picOpt;
            } else if (status == "UP_CHECKING") {
                optHtml = detailOpt + revokeOpt + picOpt;
            } else if (status == "DOWN_CHECKING") {
                //optHtml = detailOpt + revokeOpt;
                optHtml = detailOpt + picOpt;
            } else if (status == "REFUSE") {
                optHtml = editOpt + detailOpt + revokeOpt + picOpt;
            }
        } else {
            if (status == "UP") {
                optHtml = editOpt + detailOpt + downOpt;
            } else if (status == "DOWN") {
                optHtml = editOpt + detailOpt + delOpt;
            } else if (status == "UP_CHECKING") {
                optHtml = detailOpt + revokeOpt;
            } else if (status == "DOWN_CHECKING") {
                //optHtml = detailOpt + revokeOpt;
                optHtml = detailOpt;
            } else if (status == "REFUSE") {
                optHtml = editOpt + detailOpt + revokeOpt;
            }
        }

        return optHtml;
    },
    editHotel: function(id) {
        window.location.href = "/yhy/yhyHotelInfo/toHotelInfo.jhtml?id=" + id;
    },
    hotelDetail: function(id) {
        window.location.href = "/yhy/yhyHotelInfo/toHotelInfoDetail.jhtml?id=" + id;
    },
    revokeHotel: function(id) {
        $.messager.show({
            msg: '确定要撤销?',
            iconCls: 'containBody',
            btns: [
                {
                    btnText: '确定',
                    btnCls: 'btn-info',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                        $.ajax({
                            url: '/yhy/yhyHotelInfo/revokeHotel.jhtml',
                            type: 'post',
                            dataType: 'json',
                            progress: true,
                            data: {
                                id: id
                            },
                            success: function(result) {
                                if (result.success) {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "success",
                                        afterClosed: function() {
                                            HotelListIndex.doSearchHotel();
                                        }
                                    });
                                } else {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "error"
                                    });
                                }
                            },
                            error: function() {
                                $.messager.show({
                                    msg: "操作失败! 请稍后重试!",
                                    type: "error"
                                });
                            }
                        });
                    }
                },
                {
                    btnText: '取消',
                    btnCls: 'btn-default',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                    }
                }
            ]
        });
    },
    downHotel: function(id) {
        $.messager.show({
            msg: '确定要下架?',
            iconCls: 'containBody',
            btns: [
                {
                    btnText: '确定',
                    btnCls: 'btn-info',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                        $.ajax({
                            url: '/yhy/yhyHotelInfo/downHotel.jhtml',
                            type: 'post',
                            dataType: 'json',
                            progress: true,
                            data: {
                                id: id
                            },
                            success: function(result) {
                                if (result.success) {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "success",
                                        afterClosed: function() {
                                            HotelListIndex.doSearchHotel();
                                        }
                                    });
                                } else {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "error"
                                    });
                                }
                            },
                            error: function() {
                                $.messager.show({
                                    msg: "操作失败! 请稍后重试!",
                                    type: "error"
                                });
                            }
                        });
                    }
                },
                {
                    btnText: '取消',
                    btnCls: 'btn-default',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                    }
                }
            ]
        });
    },
    delHotel: function(id) {
        $.messager.show({
            msg: '确定要删除?',
            iconCls: 'containBody',
            btns: [
                {
                    btnText: '确定',
                    btnCls: 'btn-info',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                        $.ajax({
                            url: '/yhy/yhyHotelInfo/delHotel.jhtml',
                            type: 'post',
                            dataType: 'json',
                            progress: true,
                            data: {
                                id: id
                            },
                            success: function(result) {
                                if (result.success) {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "success",
                                        afterClosed: function() {
                                            HotelListIndex.doSearchHotel();
                                        }
                                    });
                                } else {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "error"
                                    });
                                }
                            },
                            error: function() {
                                $.messager.show({
                                    msg: "操作失败! 请稍后重试!",
                                    type: "error"
                                });
                            }
                        });
                    }
                },
                {
                    btnText: '取消',
                    btnCls: 'btn-default',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                    }
                }
            ]
        });
    },
    pictureSorting: function(id){
        window.location.href = "/yhy/yhyMain/pictrueSorting.jhtml?productId="+id + "&type=hotel";
    }
};
$(function() {
    HotelListIndex.init();
});