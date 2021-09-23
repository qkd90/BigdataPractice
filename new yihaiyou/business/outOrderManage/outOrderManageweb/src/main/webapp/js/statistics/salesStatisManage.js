/**
 * Created by dy on 2016/5/10.
 */

var SalesStatisManage = {

    init: function() {

        SalesStatisManage.initTicketJsp();
        SalesStatisManage.initLineJsp();
        SalesStatisManage.initSalesLine();
        SalesStatisManage.initSalesTicket();
    },


    initJsp: function() {



    },


    initTicketJsp: function() {

        var initDay = $(":input[name='check_ticket_date']:checked").val();

        var startDate = SalesStatisManage.calculateDate(initDay, null);
        $("#start_ticket_date").val(startDate);
        $("#start_ticket_date").focus(function () {
            WdatePicker({minDate:'#F{$dp.$D(\'end_ticket_date\',{M:-3, d:-1})}', maxDate: '#F{$dp.$D(\'end_ticket_date\')}'});
        });

        var endDate = SalesStatisManage.calculateDate(null, null);
        $("#end_ticket_date").val(endDate);
        $("#end_ticket_date").focus(function () {
            WdatePicker({maxDate: '' + endDate + ''})
        });


        $.each($(":input[name='check_ticket_date']"), function(i, perValue){

           $(perValue).click(function() {
               var pValue = $(perValue).val();
               var startDate = "";
               if (pValue == 3) {
                   startDate = SalesStatisManage.calculateDate(null, pValue);
               } else {
                   startDate = SalesStatisManage.calculateDate(pValue, null);
               }

               $("#start_ticket_date").val(startDate);
               $("#end_ticket_date").val(endDate);
           });

        });
    },

    initLineJsp: function() {

        var initDay = $(":input[name='check_line_date']:checked").val();

        var startDate = SalesStatisManage.calculateDate(initDay, null);
        $("#start_line_date").val(startDate);
        $("#start_line_date").focus(function () {
            WdatePicker({minDate:'#F{$dp.$D(\'end_line_date\',{M:-3, d:-1})}', maxDate: '#F{$dp.$D(\'end_ticket_date\')}'});
        });

        var endDate = SalesStatisManage.calculateDate(null, null);
        $("#end_line_date").val(endDate);
        $("#end_line_date").focus(function () {
            WdatePicker({maxDate: '' + endDate + ''})
        });


        $.each($(":input[name='check_line_date']"), function(i, perValue){

            $(perValue).click(function() {
                var pValue = $(perValue).val();
                var startDate = "";
                if (pValue == 3) {
                    startDate = SalesStatisManage.calculateDate(null, pValue);
                } else {
                    startDate = SalesStatisManage.calculateDate(pValue, null);
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
        var url = "/statistics/statistics/salesDatagrid.jhtml?type=scenic";
        $('#sales_ticket').datagrid({
            url: url,
            fit: true,
            pagination: true,
            border:false,
            pageList: [10, 20, 50],
            rownumbers: true,
            singleSelect: true,
            striped: true,//斑马线
            columns: [[
                {field: 'productId', checkbox: true},
                {field: 'name', title: '产品名称', width: '50%'},
                {field: 'count', title: '数量', width: '20%'},
                {field: 'totalPrice', title: '总额', width: '15%'},
                {
                    field: 'opt', title: '操作', width: '10%', align: 'center'
                    //formatter: function (value, rowData, index) {
                    //    var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="ProductManage.openAddTicketOrder(' + rowData.id + ')">购买商品</a>';
                    //    return btn;
                    //}
                }
            ]],
            toolbar: '#search_ticket_tool',
            onBeforeLoad: function(data) {
                data.seaStartTime=$("#start_ticket_date").val();
                data.seaEndTime=$("#end_ticket_date").val();
                data.seaUnit=$("#sea_ticket_buyUnit").val();
                data.seaProName=$("#sea_ticket_productName").val();
            }
        });

    },



    doTicketSearch: function() {

        var data = {
            seaStartTime:$("#start_ticket_date").val(),
            seaEndTime:$("#end_ticket_date").val(),
            seaUnit:$("#sea_ticket_buyUnit").val(),
            seaProName:$("#sea_ticket_productName").val()
        };
        if (!$("#start_ticket_date").val()) {
            show_msg("开始查询日期不能为空！");
        } else if (!$("#end_ticket_date").val()) {
            show_msg("结束查询日期不能为空！");
        } else {
            $('#sales_ticket').datagrid("load", data);
        }

    },

    doTicketClear: function() {
        $('#sale_ticket_searchForm').form('reset');
        SalesStatisManage.initTicketJsp();
        var data = {
            seaStartTime:$("#start_ticket_date").val(),
            seaEndTime:$("#end_ticket_date").val(),
            seaUnit:$("#sea_ticket_buyUnit").val(),
            seaProName:$("#sea_ticket_productName").val()
        };
        $('#sales_ticket').datagrid("load", data);

    },

    doLineSearch: function() {
        var data = {
            seaStartTime:$("#start_line_date").val(),
            seaEndTime:$("#end_line_date").val(),
            seaUnit:$("#sea_line_buyUnit").val(),
            seaProName:$("#sea_line_productName").val()
        };
        if (!$("#start_line_date").val()) {
            show_msg("开始查询日期不能为空！");
        } else if (!$("#end_line_date").val()) {
            show_msg("结束查询日期不能为空！");
        } else {
            $('#sales_line').datagrid("load", data);
        }

    },

    doLineClear: function() {
        $('#sale_line_searchForm').form('reset');
        SalesStatisManage.initLineJsp();
        var data = {
            seaStartTime:$("#start_line_date").val(),
            seaEndTime:$("#end_line_date").val(),
            seaUnit:$("#sea_line_buyUnit").val(),
            seaProName:$("#sea_line_productName").val()
        };
        $('#sales_line').datagrid("load", data);
    },
    doLoadTicketExcel: function() {

        var url = "/statistics/statistics/loadSalesExcel.jhtml?type=scenic";

        var data = {
            seaStartTime:$("#start_ticket_date").val(),
            seaEndTime:$("#end_ticket_date").val(),
            seaUnit:$("#sea_ticket_buyUnit").val(),
            seaProName:$("#sea_ticket_productName").val()
        };
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post(url, data,
            function(result){
                if (result.success) {
                    $.messager.progress("close");
                    window.location = "/static"+result.downloadUrl;
                    show_msg(result.info);
                } else {
                    $.messager.progress("close");
                    show_msg(result.info);
                }

            }
        );

    },

    doLoadLineExcel: function() {

        var url = "/statistics/statistics/loadSalesExcel.jhtml?type=line";

        var data = {
            seaStartTime:$("#start_line_date").val(),
            seaEndTime:$("#end_line_date").val(),
            seaUnit:$("#sea_line_buyUnit").val(),
            seaProName:$("#sea_line_productName").val()
        };
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post(url, data,
            function(result){
                if (result.success) {
                    $.messager.progress("close");
                    window.location = "/static"+result.downloadUrl;
                    show_msg(result.info);
                } else {
                    $.messager.progress("close");
                    show_msg(result.info);
                }

            }
        );

    },

    initSalesLine: function() {
        var url = "/statistics/statistics/salesDatagrid.jhtml?type=line";
        $('#sales_line').datagrid({
            url: url,
            fit: true,
            pagination: true,
            border:false,
            pageList: [10, 20, 50],
            rownumbers: true,
            singleSelect: true,
            striped: true,//斑马线
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'name', title: '产品名称', width: '60%'},
                {field: 'count', title: '数量', width: '20%'},
                {field: 'totalPrice', title: '总额', width: '15%'},
                {
                    field: 'opt', title: '操作', width: '5%', align: 'center'
                    //formatter: function (value, rowData, index) {
                    //    var btn = '<a href="javascript:void(0)" style="color:blue;" onclick="ProductManage.openAddTicketOrder(' + rowData.id + ')">购买商品</a>';
                    //    return btn;
                    //}
                }
            ]],
            toolbar: '#search_line_tool',
            onBeforeLoad: function(data) {
                data.seaStartTime=$("#start_line_date").val();
                data.seaEndTime=$("#end_line_date").val();
                data.seaUnit=$("#sea_line_buyUnit").val();
                data.seaProName=$("#sea_line_productName").val();
            }
        });

    }


};

$(function() {
    SalesStatisManage.init();
});
