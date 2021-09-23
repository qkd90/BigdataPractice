/**
 * Created by huangpeijie on 2016-12-02,0002.
 */
var SailboatListIndex = {
    SailboatType: {sailboat: "帆船", yacht: "游艇", huanguyou: "鹭岛游"},
    SailboatStatus: {UP: "已上架", DOWN: "已下架", UP_CHECKING: "上架中", DOWN_CHECKING: "下架中", REFUSE: "已拒绝"},
    SailboatListTable: null,
    init: function () {
        SailboatListIndex.getYhySailboatList();
        SailboatListIndex.initComp();
    },
    initComp: function() {
        $('#sailboatStatusSel').btComboBox();
        $('#sailboatSearchBtn').on('click', function(event) {
            event.stopPropagation();
            SailboatListIndex.doReSearchSailboat();
        });
    },
    getYhySailboatList: function () {
        SailboatListIndex.SailboatListTable = $('#yhySailboatList').DataTable({
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
                data['ticket.status'] = $('input[name = "ticket.status"]').val();
                //data['ticket.ticketName'] = $('#searchProductName').val();
                data['ticket.name'] = $('#searchProductName').val();
                $.ajax({
                    url: '/yhy/yhySailboatInfo/getYhySailBoatList.jhtml',
                    data: data,
                    type: 'post',
                    dataType: 'json',
                    progress: true,
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
                {'data': 'ticketType', 'defaultContent': "-",
                    'render': function(value, type, row, meta) {
                        return SailboatListIndex.SailboatType[value];
                    }
                },
                {'data': 'address', 'defaultContent': "-"},
                {'data': 'contactName', 'defaultContent': "-"},
                {'data': 'telephone', 'defaultContent': "-"},
                {'data': 'status', 'defaultContent': "-",
                    'render': function(value, type, row, meta) {
                        return SailboatListIndex.SailboatStatus[value];
                    }
                },
                {'data': null, 'defaultContent': "-", 'render': SailboatListIndex.getOpt}
            ]
        });
        YhyCommon.Table = SailboatListIndex.SailboatListTable;
    },
    getOpt: function(value, type, row, meta) {
        var status = row.status;
        var optHtml = "";
        var editOpt = "<a onclick='SailboatListIndex.editTicket("+ row.id +")' class='link-span'>编辑</a>";
        var detailOpt = "<a onclick='SailboatListIndex.ticketDetail("+ row.id +")' class='link-span'>详情</a>";
        var downOpt = "<a onclick='SailboatListIndex.downTicket("+ row.id +")' class='link-span'>申请下架</a>";
        var revokeOpt = "<a onclick='SailboatListIndex.revokeTicket("+ row.id +")' class='link-span'>撤销</a>";
        var delOpt = "<a onclick='SailboatListIndex.delTicket("+ row.id +")' class='link-span'>删除</a>";
        var picOpt = "<a onclick='SailboatListIndex.pictureSorting("+ row.id +")' class='link-span'>图片排序</a>";

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
    doSearchSailboat: function() {
        SailboatListIndex.SailboatListTable.ajax.reload(function(result){}, false);
    },
    doReSearchSailboat: function() {
        SailboatListIndex.SailboatListTable.ajax.reload(function(result){}, false);
    },
    editTicket: function(id) {
        window.location.href = "/yhy/yhySailboatInfo/toSailboatInfo.jhtml?id=" + id;
    },
    ticketDetail: function(id) {
        window.location.href = "/yhy/yhySailboatInfo/toSailboatInfoDetail.jhtml?id=" + id;
    },
    downTicket: function(id) {
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
                            url: '/yhy/yhySailboatInfo/downTicket.jhtml',
                            type: 'post',
                            dataType: 'json',
                            progress: true,
                            data: {id: id},
                            success: function(result) {
                                if (result.success) {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "success",
                                        afterClosed: function() {
                                            SailboatListIndex.doSearchSailboat();
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
    revokeTicket: function(id) {
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
                            url: '/yhy/yhySailboatInfo/revokeTicket.jhtml',
                            type: 'post',
                            dataType: 'json',
                            progress: true,
                            data: {id: id},
                            success: function(result) {
                                if (result.success) {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "success",
                                        afterClosed: function() {
                                            SailboatListIndex.doSearchSailboat();
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
    delTicket: function(id) {
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
                            url: '/yhy/yhySailboatInfo/delTicket.jhtml',
                            type: 'post',
                            dataType: 'json',
                            progress: true,
                            data: {id: id},
                            success: function(result) {
                                if (result.success) {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "success",
                                        afterClosed: function() {
                                            SailboatListIndex.doSearchSailboat();
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
        window.location.href = "/yhy/yhyMain/pictrueSorting.jhtml?productId=" + id + "&type=sailboat";;
    }
};
$(function() {
    SailboatListIndex.init();
});