/**
 * Created by dy on 2016/5/10.
 */

var StatisticsManage = {

    init: function() {
        StatisticsManage.initTicketJsp();
        StatisticsManage.initJsp();
        StatisticsManage.initSalesTicket();
        $('.datagrid-header .datagrid-cell').css('text-align', 'center');   // 修改表头对齐方式
    },


    initJsp: function() {

        //$("input",$("#sea_ticket_productName").next("span")).click(function(){
        //});

        $("#sea_ticket_productName").combobox({
            valueField: 'id',
            textField: 'account',
            onShowPanel:function() {
                var url = "/wechat/wechatAccount/getComboList.jhtml";
                $("#sea_ticket_buyUnit").combobox("setValue", "");
                $.post(url,
                    function(result){
                        if (result) {
                            var resultData = result.rows;
                            var loadData = [];
                            if (resultData.length >0) {
                                $.each(resultData, function(i, perValue) {
                                    var data = {
                                        id:perValue.id,
                                        account:perValue.account
                                    };
                                    loadData.push(data);
                                });
                            }

                            $("#sea_ticket_productName").combobox('loadData', loadData);
                        }
                    }
                );
            }

        });


        $("#sea_ticket_buyUnit").combobox({
            valueField: 'id',
            textField: 'name',
            panelHeight: 'auto',
            onShowPanel:function() {
                var account = $("#sea_ticket_productName").combobox("getValue");
                if (account) {
                    var url = "/wechat/wechatQrcode/getQrcodeList.jhtml";
                    $.post(url, {accountId:account},
                        function(result){
                            if (result) {
                                var resultData = result.rows;
                                var loadData = [];
                                if (resultData.length >0) {
                                    $.each(resultData, function(i, perValue) {
                                        var data = {
                                            id:perValue.id,
                                            name:perValue.name
                                        };
                                        loadData.push(data);
                                    });
                                }

                                $("#sea_ticket_buyUnit").combobox('loadData', loadData);
                            }
                        }
                    );

                } else {
                    show_msg("请先选择公众号！");
                    return ;
                }
            }
        });

    },


    initTicketJsp: function() {

        var initDay = $(":input[name='check_ticket_date']:checked").val();

        var startDate = StatisticsManage.calculateDate(initDay, null);
        $("#start_ticket_date").val(startDate);
        $("#start_ticket_date").focus(function () {
            WdatePicker({minDate:'#F{$dp.$D(\'end_ticket_date\',{M:-3, d:-1})}', maxDate: '#F{$dp.$D(\'end_ticket_date\')}'});
        });

        var endDate = StatisticsManage.calculateDate(null, null);
        $("#end_ticket_date").val(endDate);
        $("#end_ticket_date").focus(function () {
            WdatePicker({maxDate: '' + endDate + ''})
        });


        $.each($(":input[name='check_ticket_date']"), function(i, perValue){

           $(perValue).click(function() {
               var pValue = $(perValue).val();
               var startDate = "";
               if (pValue == 3) {
                   startDate = StatisticsManage.calculateDate(null, pValue);
               } else {
                   startDate = StatisticsManage.calculateDate(pValue, null);
               }

               $("#start_ticket_date").val(startDate);
               $("#end_ticket_date").val(endDate);
           });

        });
    },

    initLineJsp: function() {

        var initDay = $(":input[name='check_line_date']:checked").val();

        var startDate = StatisticsManage.calculateDate(initDay, null);
        $("#start_line_date").val(startDate);
        $("#start_line_date").focus(function () {
            WdatePicker({minDate:'#F{$dp.$D(\'end_line_date\',{M:-3, d:-1})}', maxDate: '#F{$dp.$D(\'end_ticket_date\')}'});
        });

        var endDate = StatisticsManage.calculateDate(null, null);
        $("#end_line_date").val(endDate);
        $("#end_line_date").focus(function () {
            WdatePicker({maxDate: '' + endDate + ''})
        });


        $.each($(":input[name='check_line_date']"), function(i, perValue){

            $(perValue).click(function() {
                var pValue = $(perValue).val();
                var startDate = "";
                if (pValue == 3) {
                    startDate = StatisticsManage.calculateDate(null, pValue);
                } else {
                    startDate = StatisticsManage.calculateDate(pValue, null);
                }

                $("#start_line_date").val(startDate);
                $("#end_line_date").val(endDate);
            });

        });



    },

    /**
     * 日期计算
     * @param argDay
     * @param argMonth
     * @returns {string}
     */
    calculateDate: function(argDay, argMonth) {
        var date = new Date();

        if (argDay) {
            date = date.valueOf();
            date = date - argDay * 24 * 60 * 60 * 1000;
            date = new Date(date);
        }

        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        if (month < 10) {
            if (argMonth) {
                month = month - argMonth;
            }
            month = "0" + month;
            day = day + 1;
        }
        day = day - 1;
        if (day < 10) {
            day = "0" + day;
        }
        return year + "-" + month + "-" + day;
    },

    initSalesTicket: function() {
        $('#sales_ticket').datagrid({
            //url: url,
            data:[],
            fit: true,
            showFooter:true,
            pagination: true,
            border:false,
            pageList: [10, 20, 50],
            rownumbers: true,
            singleSelect: true,
            striped: true,//斑马线
            columns: [[
                {field: 'account', title: '公众号', width: 100},
                {field: 'name', title: '二维码名称', width: 160},
                {field: 'sceneStr', title: '参数', width: 100},
                {field: 'subCount', title: '关注数', width: 100,align:'right'},
                {field: 'subLastTime', title: '最新关注时间', width: 130},
                {field: 'unsubCount', title: '取消关注数', width: 100,align:'right'},
                {field: 'unsubLastTime', title: '最新取消时间', width: 130},
                {field: 'hadSubCount', title: '关注数量', width: 100,align:'right',
                    formatter : function(value, rowData, rowIndex) {
                        return rowData.subCount-rowData.unsubCount;
                    }
                }
            ]],
            toolbar: '#search_ticket_tool',
            onBeforeLoad: function(data) {
                data.seaStartTime=$("#start_ticket_date").val();
                data.seaEndTime=$("#end_ticket_date").val();
                data.seaUnit=$("#sea_ticket_buyUnit").combobox("getValue");
                data.seaProName=$("#sea_ticket_productName").combobox("getValue");
            },
            onLoadSuccess: function() {
                //$('#sales_ticket').datagrid('statistics');
                $('.datagrid-header .datagrid-cell').css('text-align', 'center');   // 修改表头对齐方式
            }
        });

    },

    doTicketSearch: function() {
        //if (!$("#start_ticket_date").val()) {
        //    show_msg("开始查询日期不能为空！");
        //} else if (!$("#end_ticket_date").val()) {
        //    show_msg("结束查询日期不能为空！");
        //} else {
        var accountId = $("#sea_ticket_productName").combobox("getValue");
        if (!accountId) {
            show_msg("公众号不能为空！");
            return;
        }
        var url = "/wechat/wechatQrcodeStatistics/getStatisticsQrcodeList.jhtml";
        $('#sales_ticket').datagrid({url:url});
        //}
    },

    doTicketClear: function() {
        $('#sale_ticket_searchForm').form('reset');
        StatisticsManage.initTicketJsp();
    }

};

$(function() {
    StatisticsManage.init();
});
