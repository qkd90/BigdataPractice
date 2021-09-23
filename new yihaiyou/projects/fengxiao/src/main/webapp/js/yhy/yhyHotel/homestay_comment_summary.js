/**
 * Created by dy on 2016/11/16.
 */
$(function () {
    SailboatCommentSummary.init();
});

var SailboatCommentSummary = {
    commentListTable: null,
    init: function() {
        SailboatCommentSummary.initPage();
        SailboatCommentSummary.initOrderList();
    },
    initPage: function() {
       $("#checkBtn").click(function() {
           SailboatCommentSummary.search();
       });
    },
    initOrderList: function() {
        SailboatCommentSummary.commentListTable = $(".table-bordered").DataTable( {

            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            "stateSave": true,
            "lengthMenu": [ 10, 50, 200, 600, 1000 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                data['comment.type'] = 'hotel';
                if ($("#ipt_targetName").val()) {
                    data['comment.targetName'] = $("#ipt_targetName").val();
                }
                if ($("#productId").val() && $("#productId").val().length > 0) {
                    data['comment.targetId'] = $("#productId").val();
                }
                $.ajax({
                    url: "/yhy/yhyComment/commentSummaryList.jhtml",
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
                { "title": "评星", data:'score',
                    render: function(data, type, row, meta) {
                        data = data/20;
                        if (data <= 0) {
                            return '<span class="star star0"></span>';
                        }
                        if (data > 0 && data <= 1) {
                            return '<span class="star star1"></span>';
                        }
                        if (data > 1 && data <= 2) {
                            return '<span class="star star2"></span>';
                        }
                        if (data > 2 && data <= 3) {
                            return '<span class="star star3"></span>';
                        }
                        if (data > 3 && data <= 4) {
                            return '<span class="star star4"></span>';
                        }
                        if (data > 4 && data <= 5) {
                            return '<span class="star star5"></span>';
                        }
                    }
                },
                { "title": "房型名称", data:'targetName'},
                { "title": "评价数", data:'count', class: 'text-center'},
                { "title": "操作", data:null, class: 'text-center', render: function(data, type, row, meta) {
                    return '<a href="/yhy/yhyMain/toHotelCommentList.jhtml?priceTypeId='+ row.priceId +'">评价详情</a>';
                }},
            ]
        });
    },

    getCommentSummaryList: function(sSource, aoData, fnCallback) {

        var data = {};
        $.each(aoData, function(i, perData) {
            if (perData['name'] == 'sEcho') {
                data['sEcho'] = perData['value'];
            }
            if (perData['name'] == 'iDisplayStart') {
                data['iDisplayStart'] = perData['value'];
            }
            if (perData['name'] == 'iDisplayLength') {
                data['iDisplayLength'] = perData['value'];
            }
        });

        data['comment.type'] = 'sailboat';
       if ($("#ipt_targetName").val()) {
            data['comment.targetName'] = $("#ipt_targetName").val();
        }
        if ($("#productId").val() && $("#productId").val().length > 0) {
            data['comment.productId'] = $("#productId").val();
        }
        $.ajax({
            url : sSource, //这个就是请求地址对应sAjaxSource
            data : data, //这个是把datatable的一些基本数据传给后台,比如起始位置,每页显示的行数
            type : 'post',
            dataType : 'json',
            async : false,
            success : function(result) {
                fnCallback(result); //把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
            },
            error : function(msg) {
            }
        });
    },

    search: function() {
        SailboatCommentSummary.commentListTable.ajax.reload();
    }
};
