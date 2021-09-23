/**门票管理*/
var TicketView = {

    init: function () {
        TicketView.initComp();
        TicketView.initXiangqing();
        TicketView.initProCommbox();
    },
    initArea: function (index) {
        var city_code = $("#ticket_area").val();
        var data = {
            'cityCode': city_code
        };
        $.post("/ticket/ticket/getAreaByCitycode.jhtml",
            data,
            function (data) {
                $("#span_area" + index).html(data.fullPath);
            }, 'json'
        );
    },
    initProCommbox: function () {
        var categoryId = $("#categoryId").val();
        var data = {
            'categoryId': categoryId
        };
        $.post("/ticket/ticket/getCategorg.jhtml",
            data,
            function (data) {
                if (data) {
                    $("#ticket_category").html(data.name);
                }
            }, 'json'
        );
    }, initXiangqing: function () {
        //富文本产品详情
        var editorXiangqing;
        KindEditor.ready(function (K) {
            editorXiangqing = K.create('#kindXiangqing', {
                resizeType: 1,
                allowPreviewEmoticons: false,
                items: [],
                disabled: true,
                readonlyMode: true
            });
            //editorXiangqing.html(K('#hidden_xiangq').val());
            var xiangqing = '${ticketExplain.proInfo}';
            editorXiangqing.insertHtml(xiangqing);
            // K('#div_kindXiangqing').children('.ke-container').children('.ke-edit').click(function () {
            //     editorXiangqing.html('');
            // });
        });

    },
    initComp: function () {
        var type = $("#type_ticket").val();
        if (type == "scenic") {
            TicketView.sleTicCategory(1);
            TicketView.initArea(1);
        } else if (type == "shows") {
            TicketView.sleTicCategory(2);
            TicketView.initArea(2);
        } else if (type == "boat") {
            TicketView.sleTicCategory(3);
            TicketView.initArea(3);
        }

        var url = "";
        var flag = $("#ticket_agentId").val();
        if (flag == "true") {
            url = '/ticket/ticketPrice/getDatePricelist.jhtml?prop=rebate&agent=T';
        } else {
            url = '/ticket/ticketPrice/getDatePricelist.jhtml?prop=rebate&agent=F';
        }

        // 构建表格
        $('#qryResult').datagrid({
            title: "",
            data: [],
            url: url,
            rownumbers: false,
            border: true,
            singleSelect: true,
            striped: true,
            pagination: false,
            columns: [[{
                field: 'name',
                title: '门票类型名称',
                align: 'center',
                width: 220,
                formatter: function (value, rowData, rowIndex) {
                    var ticketId = $("#ticketId").val();
                    if (ticketId != rowData.ticket.id) {
                        return value + "<span style='color:green;'>[代理]</span>";
                    } else {
                        return value;
                    }
                }
            }, {
                field: 'discountPrice',
                title: '分销价(元起)',
                align: 'center',
                width: 100
            }, {
                field: 'rebate',
                title: '佣金(元起)',
                align: 'center',
                width: 95
            }, {
                field: 'type',
                title: '票型',
                align: 'center',
                width: 95,
                codeType: 'type',
                formatter: TicketUtil.codeFmt
            }, {
                field: 'startTime', title: '价格日历', width: 60, sortable: false,
                formatter: function (value, rowData, index) {
                    var hipt = '<input type="button" class="calender_class"  readonly="readonly" id="startTime_' + rowData.id + '" onclick="TicketView.onclickStartTime(' + index + ',' + rowData.id + ')">';
                    return hipt;
                }
            }]],
            onBeforeLoad: function (data) {   // 查询参数
                data.ticketId = $("#ticketId").val();
            },
        });
    },
    tabClose: function (id) {
        console.info('close tab');
        window.parent.$('.tabs-selected .tabs-close').click();
        var url = '/ticket/ticket/ticketList.jhtml';
        window.location.href = url
    },
    onclickStartTime: function (index, id) {
        TicketView.selectStartTime(index, id);
    },
    selectStartTime: function (index, id) {
        var ifr = $("#sel_startTime").children()[0];
        var url = "/ticket/ticket/selectDatePrice.jhtml?ticketPriceId=" + id + "&ticketId=" + $("#ticketId").val();
        $(ifr).attr("src", url);
        $("#sel_startTime").dialog({
            title: '选择日历价格',
            width: 350,
            height: 430,
            closed: false,
            cache: false,
            modal: true
        });
        $("#sel_startTime").dialog("open");
    },

    //选择门票类别
    sleTicCategory: function (selId) {
        if (selId == 1) {
            $("#ticke_type").val("scenic");
            TicketView.show_jingdian();
            TicketView.hide_yanchu();
            TicketView.hide_chuanzhi();
        } else if (selId == 2) {
            $("#ticke_type").val("shows");
            TicketView.hide_jingdian();
            TicketView.show_yanchu();
            TicketView.hide_chuanzhi();
        } else if (selId == 3) {
            $("#ticke_type").val("boat");
            TicketView.hide_jingdian();
            TicketView.hide_yanchu();
            TicketView.show_chuanzhi();
        }
    },

    //隐藏景点类别信息
    hide_jingdian: function () {
        $("#secTicket").css("background", "#efefef");
        $("#div_jd_name").hide();
        $("#div_jd_area").hide();
        $("#div_jd_aji").hide();
        $("#div_jd_address").hide();
    },
    //显示景点类别信息
    show_jingdian: function () {
        $("#secTicket").css("background", "#FE7700");
        $("#div_jd_name").show();
        $("#div_jd_area").show();
        $("#div_jd_aji").show();
        $("#div_jd_address").show();
    },
    //隐藏门票类别信息
    hide_yanchu: function () {
        $("#perTicket").css("background", "#efefef");
        $("#div_yc_name").hide();
        $("#div_yc_area").hide();
        $("#div_yc_time").hide();
        $("#div_yc_address").hide();
    },
    //显示门票类别信息
    show_yanchu: function () {
        $("#perTicket").css("background", "#FE7700");
        $("#div_yc_name").show();
        $("#div_yc_area").show();
        $("#div_yc_time").show();
        $("#div_yc_address").show();
    },
    //隐藏船票类别信息
    hide_chuanzhi: function () {
        $("#boatTicket").css("background", "#efefef");
        $("#div_cz_name").hide();
        $("#div_cz_area").hide();
        $("#div_cz_time").hide();

    },
    //显示船票类别信息
    show_chuanzhi: function () {
        $("#boatTicket").css("background", "#FE7700");
        $("#div_cz_name").show();
        $("#div_cz_area").show();
        $("#div_cz_time").show();

    },

};

$(function () {
    TicketView.init();
});

