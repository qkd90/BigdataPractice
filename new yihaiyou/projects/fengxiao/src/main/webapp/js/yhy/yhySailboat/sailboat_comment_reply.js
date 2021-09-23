/**
 * Created by dy on 2016/11/16.
 */
$(function () {
    SailboatCommentReply.init();
});

var SailboatCommentReply = {
    commentListTable: null,
    init: function() {
        SailboatCommentReply.initPage();
        SailboatCommentReply.initCommentList();
    },
    initPage: function() {
       $("#checkBtn").click(function() {
           SailboatCommentReply.search();
       });
        $("#returnBtn").click(function() {
            history.go(-1);
        });


    },
    initCommentList: function() {
        SailboatCommentReply.commentListTable = $(".table-bordered").DataTable( {
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            "stateSave": true,
            "lengthMenu": [ 10, 50, 200, 600, 1000 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                if ($("#content").val()) {
                    data['comment.content'] = $("#content").val();
                }

                if ($("#productId").val() && $("#productId").val().length > 0) {
                    data['comment.targetId'] = $("#productId").val();
                }

                if ($("#hipt_priceTypeId").val() && $("#hipt_priceTypeId").val().length > 0) {
                    data['comment.priceId'] = $("#hipt_priceTypeId").val();
                }

                if ($("#replyStatus").val() && $("#replyStatus").val().length > 0) {
                    data['comment.replyStatus'] = $("#replyStatus").val();
                }
                $.ajax({
                    url: "/yhy/yhyComment/commentList.jhtml",
                    data: data,
                    type: 'post',
                    dataType: 'json',
                    progress: true,
                    success: function(result) {
                        callback(result);
                        var $tr = $(".table>tbody>tr");
                        for (var i = 0; i < $tr.length; i++) {
                            var aTd1 = $tr[i].getElementsByTagName('td')[1];
                            $aTd1 = $(aTd1);
                            $aTd1.hover(function () {
                                var data = $(this).find("p").html();
                                var $tooltips = $('<div class="tooltips"><div class="tooltip-content">' + data + '</div><div class="tooltip-direct"></div></div>');
                                $(this).append($tooltips);
                            }, function () {
                                $('.tooltips').remove();
                            });
                        };

                        for (var i = 0; i < $tr.length; i++) {
                            var aTd2 = $tr[i].getElementsByTagName('td')[2];
                            $aTd2 = $(aTd2);
                            $aTd2.hover(function () {
                                if ($(this).find("p").length == 1) {
                                    var data = $(this).find("p").html();
                                    var $tooltips = $('<div class="tooltips"><div class="tooltip-content">' + data + '</div><div class="tooltip-direct"></div></div>');
                                    $(this).append($tooltips);
                                }
                            }, function () {
                                $('.tooltips').remove();
                            });
                        };
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
                { "title": "评星", data:null,
                    render: function(data, type, row, meta) {
                        if (row.commentScores.length > 0) {
                            var totalSocre = 0;
                            $.each(row.commentScores, function(i, scores) {
                                totalSocre += scores.score;
                            });
                            totalSocre = totalSocre/20;
                            if (totalSocre <= 0) {
                                return '<span class="star star0"></span>';
                            }
                            if (totalSocre > 0 && totalSocre <= 1) {
                                return '<span class="star star1"></span>';
                            }
                            if (totalSocre > 1 && totalSocre <= 2) {
                                return '<span class="star star2"></span>';
                            }
                            if (totalSocre > 2 && totalSocre <= 3) {
                                return '<span class="star star3"></span>';
                            }
                            if (totalSocre > 3 && totalSocre <= 4) {
                                return '<span class="star star4"></span>';
                            }
                            if (totalSocre > 4 && totalSocre <= 5) {
                                return '<span class="star star5"></span>';
                            }
                        } else {
                            return '<span class="star star0"></span>';
                        }
                    }
                },
                { "title": "评价内容", data:'content', class:'tooltipsParent',
                    render: function(data, type, row, meta) {

                        console.log($(this));
                        console.log(type);
                        console.log(meta);

                        var replyContent = '<p class="tool-tips-td2">'+ data +'</p>';

                        return replyContent;
                    }
                },
                { "title": "回复内容", data: null, class: 'text-center dialogParent',
                    render: function(data, type, row, meta) {
                        var replyContent = '';
                        if (row.comments.length > 0) {
                            $.each(row.comments, function(i, reply) {
                                replyContent += '<p class="tool-tips-td3">'+ reply.content +'</p>';
                            });

                        } else {
                            replyContent = '<button class="btn btn-warning dialog-btn" onclick="SailboatCommentReply.openReplayDialog(this, '+ row.id +')">回复</button>';
                        }
                        return replyContent;
                    }
                },
                { "title": "订单号", data:'orderNo'},
                { "title": "评价人", data:'user.userName'},
                { "title": "状态", data:'replyStatus', render: function(data, type, row, meta) {
                    if (data == 1) {
                        return '已回复';
                    } else {
                        return '未回复';
                    }
                }},
                /*{ "title": "操作", data:null, class: 'text-center', render: function(data, type, row, meta) {
                    return '<a href="/yhy/yhyMain/toSailboatCommentList.jhtml?priceTypeId='+ row.targetId +'">评价详情</a>';
                }},*/
            ]
        });
    },

    getCommentList: function(sSource, aoData, fnCallback) {

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

       if ($("#content").val()) {
            data['comment.content'] = $("#content").val();
        }

        if ($("#productId").val() && $("#productId").val().length > 0) {
            data['comment.productId'] = $("#productId").val();
        }

        if ($("#hipt_priceTypeId").val() && $("#hipt_priceTypeId").val().length > 0) {
            data['comment.targetId'] = $("#hipt_priceTypeId").val();
        }

        if ($("#replyStatus").val().length > 0) {
            data['comment.replyStatus'] = $("#replyStatus").val();
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
        SailboatCommentReply.commentListTable.ajax.reload();
    },

    openReplayDialog: function(target, id) {
        $(".dialog").remove();
        //创建dialog标签
        var $dialog = $('<div class="dialog"><div class="dialog-content"><span class="closing"></span> <span class="font-number">0/500</span> <textarea id="replyContent" class="form-control" rows="5"></textarea> <button class="btn btn-primary pull-right" onclick="SailboatCommentReply.doReplyComment('+ id +')">提交回复</button> </div> <div class="dialog-direct"></div> </div>');


        $(target).closest('.dialogParent').append($dialog);

        //closing按钮关闭功能
        $('.closing').on('click',function(){
            $dialog.detach();
        });

        //输入框功能
        $dialog.find('textarea').bind('input propertychange',function(ev){
            //event对象兼容
            var ev = ev || event;
            //console.log($(this).val().length);、
            var fontNumber = $(this).val().length;
            $('.font-number').html(fontNumber + '/500');

            //限制字数
            if(fontNumber > 500){
                fontNumber = 500;
                $('.font-number').html(fontNumber + '/500');
                $(this).val( $(this).val().substring(0,500) );
            }
        });
    },

    doReplyComment: function(id) {
        var url = "/yhy/yhyComment/doReplyComment.jhtml";
        var data = {'comment.repliedId': id, 'comment.content': $("#replyContent").val()};
        $.post(url, data, function(result) {
            if (result.success) {
                $.messager.show({
                    msg: "回复成功",
                    type: 'success',
                    afterClosed: SailboatCommentReply.search()
                });
            } else {
                $.messager.show({
                    msg: "回复失败",
                    type: 'error',
                    afterClosed: SailboatCommentReply.search()
                });
            }
        });

    }


};


//****************************************************************
/*添加页面JS，2016-12-15  郭阳辉*/
/**
 * Created by HMLY on 2016-11-29.
 */

$(function(){
//-------------
//----------------
//查询按钮点击样式变化
    $("#checkBtn").mousedown(function(){
        $(this).addClass('click');
    });

    $("#checkBtn").mouseup(function(){
        $(this).removeClass('click');
    });

    $("#returnBtn").mousedown(function(){
        $(this).addClass('click');
    });

    $("#returnBtn").mouseup(function(){
        $(this).removeClass('click');
    });

//--------------------------
//表单中的伪select标签
    $("#selectBtn").hide();
    $("#dropDown").on("click",function(){

        $("#selectBtn").show();

        $("#selectBtn").find('li').mouseenter(function(){
            $(this).siblings().removeClass('active');
            $(this).addClass('active');
        });

        $("#selectBtn").find('li').click(function(){
            var res = $(this).html();
            $("#selectBtn").hide();
            $("#dropDown").html(res);
            if(res == '全部'){
                $("#replyStatus").val('');
            }else if(res == '已回复'){
                $("#replyStatus").val('1');
            }else if(res == '未回复'){
                $("#replyStatus").val('0');
            }
        });

        $("#selectBtn").mouseleave(function(){
            $("#selectBtn").hide();
        });
    });

    //***********************
    var $tr = $(".table>tbody>tr");
    for (var i = 0; i < $tr.length; i++) {
        var aTd1 = $tr[i].getElementsByTagName('td')[1];
        $aTd1 = $(aTd1);
        $aTd1.hover(function () {
            var data = $(this).find("p").html();
            var $tooltips = $('<div class="tooltips"><div class="tooltip-content">' + data + '</div><div class="tooltip-direct"></div></div>');
            $(this).append($tooltips);
        }, function () {
            $('.tooltips').remove();
        });
    };

    for (var i = 0; i < $tr.length; i++) {
        var aTd2 = $tr[i].getElementsByTagName('td')[2];
        $aTd2 = $(aTd2);
        $aTd2.hover(function () {
            if ($(this).find("p").length == 1) {
                var data = $(this).find("p").html();
                var $tooltips = $('<div class="tooltips"><div class="tooltip-content">' + data + '</div><div class="tooltip-direct"></div></div>');
                $(this).append($tooltips);
            }
        }, function () {
            $('.tooltips').remove();
        });
    };




});










