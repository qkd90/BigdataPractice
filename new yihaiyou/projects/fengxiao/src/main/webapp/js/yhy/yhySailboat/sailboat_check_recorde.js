/**
 * Created by dy on 2016/11/16.
 */
$(function () {
    SailboatCheckRecorde.init();
});

var SailboatCheckRecorde = {
    init: function() {
        SailboatCheckRecorde.initPage();
        SailboatCheckRecorde.initList();
    },
    initPage: function() {
        $("#sailboatSearchBtn").click(function() {
            SailboatCheckRecorde.search();
        });
    },

    initList: function() {
        $(".table-striped").DataTable( {
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            "stateSave": true,
            "lengthMenu": [ 10, 25, 50, 100, 150 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                data['proValidCode.used'] = 1;

                if ($("#query_keyword").val()) {
                    data['proValidCode.searchKeyword'] = $("#query_keyword").val();
                }
                /*
                if ($("#createTime_start").val()) {
                    data['orderDataFormVo.startTime'] = $("#createTime_start").val();
                }
                if ($("#createTime_end").val()) {
                    data['orderDataFormVo.qryEndTime'] = $("#createTime_end").val();
                }

                if ($("#orderStatus").btComboBox('value') && $("#orderStatus").btComboBox('value').length > 0) {
                    data['orderDataFormVo.orderDetail.status'] = $("#orderStatus").btComboBox('value');
                }

                if ($("#productId").val() && $("#productId").val().length > 0) {
                    data['orderDataFormVo.orderDetail.product.id'] = $("#productId").val();
                }*/

                $.ajax({
                    url: '/yhy/yhySailBoat/getValidCodeList.jhtml',
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
            "columns": [
                { "title": "票型名称", data:'productTypeName' },
                { "title": "订单号", data:'orderNo'},
                { "title": "验证时间", data:'updateTime'},
                { "title": "验证码", data:'code'},
                { "title": "取票人", data:'buyerName'},
                { "title": "取票人手机号", data:'buyerMobile'}
            ]
        });
    },
    search: function() {
        $(".table-striped").DataTable().ajax.reload();
    }


};
