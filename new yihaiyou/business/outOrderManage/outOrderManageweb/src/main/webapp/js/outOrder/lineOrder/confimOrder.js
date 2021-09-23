/**
 * Created by dy on 2016/3/15.
 */
var ConfimOrder = {

    init: function() {
        ConfimOrder.initConfimOrderDetails();
    },

    initConfimOrderDetails: function() {

        var orderId = $("#confim_orderId").val();

        var url = "/outOrder/jszxLineOrder/getNoConfimOrderDetails.jhtml?outOrderId="+orderId;
        $('#dg_orderDetails').datagrid({
            url:url,
            fit:true,
            width: '540px',
            height: '220px',
            columns:[[
                //{field:'id',checkbox:true,title:'',width:'1%'},
                {field:'ticketNo',title:'类型编号',width:'20%'},
                {field:'ticketName',title:'线路类型',width:'25%'},
                {field:'startTime',title:'出发时间',width:'15%',
                    formatter: function(value, rowData, index) {
                        var startTime = value.substring(0, 10);
                        //var endTime = rowData.endTime;
                        //endTime = endTime.substring(0, 10);
                        return startTime;
                    }
                },
                {field:'price',title:'单价',width:'10%'},
                {field:'actualPay',title:'实际价格',width:'10%'},
                {field:'count',title:'数量',width:'10%'},
                {field:'actualPay',title:'小计',width:'10%'}
            ]],
            onLoadSuccess: function() {
                ConfimOrder.mergeGridColCells($(this), "ticketName");
                ConfimOrder.mergeGridColCells($(this), "startTime");
            }
        });


    },
    mergeGridColCells: function(grid, rowFildName) {
        var rows=grid.datagrid('getRows' );
        var startIndex=0;
        var endIndex=0;
        if(rows.length< 1)
        {
            return;
        }
        $.each(rows, function(i,row){
            if(row[rowFildName]==rows[startIndex][rowFildName])
            {
                endIndex=i;
            }
            else
            {
                grid.datagrid( 'mergeCells',{
                    index: startIndex,
                    field: rowFildName,
                    rowspan: endIndex -startIndex+1
                });
                startIndex=i;
                endIndex=i;
            }

        });
        grid.datagrid( 'mergeCells',{
            index: startIndex,
            field: rowFildName,
            rowspan: endIndex -startIndex+1
        });
    }


}


$(function() {
    ConfimOrder.init();
});
