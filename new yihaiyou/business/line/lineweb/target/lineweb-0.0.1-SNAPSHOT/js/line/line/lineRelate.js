var LineRelate = {
    lineId: null,
    linedaysId : null,
	init : function() {
        LineRelate.lineId = $("#productId").val();
        // 遍历初始表格
        var priceDgList = $('.priceDg');
        if (priceDgList && priceDgList.length > 0) {
            for (var i = 0; i < priceDgList.length; i++) {
                var linedaysId = $(priceDgList[i]).attr('linedaysid');
                LineRelate.buildHotelPriceGrid(linedaysId);
                LineRelate.buildTicketPriceGrid(linedaysId);
            }
        }
        // 初始控件
        LineRelate.initHotelPriceSelectGrid();
        LineRelate.initTicketPriceSelectGrid();
	},
    // 初始化酒店房型选择表格
    initHotelPriceSelectGrid : function() {
        $("#hotelPriceSelectGrid").datagrid({
            fit:true,
            data: [],
            //title:'酒店组合',
            //url:'/line/linedaysProductPrice/listLineProduct.jhtml',
            pagination:true,
            pageList:[20],
            singleSelect:false,
            striped:true,//斑马线
            columns: [[
                { field: 'ck', checkbox: true },
                { field: 'hotelId', title: '产品编号', width: 160},
                { field: 'hotelName', title: '产品名称', width: 240},
                { field: 'roomName', title: '酒店房型', width: 280},
                { field: 'price', title: '价格(均)', width: 150}
            ]],
            toolbar: '#hotelPriceSelectGridTb',
            onBeforeLoad : function(data) {   // 查询参数
                data.hotelId = $('#qryHotelId').numberbox('getValue');
                data.hotelName = $('#qryHotelName').textbox('getValue');
                data.linedaysId = LineRelate.linedaysId;
            }
        });
    },
    // 初始化景点门票选择表格
    initTicketPriceSelectGrid : function() {
        $("#ticketPriceSelectGrid").datagrid({
            fit:true,
            data: [],
            //title:'酒店组合',
            //url:'/line/linedaysProductPrice/listLineProduct.jhtml',
            pagination:true,
            pageList:[20],
            singleSelect:false,
            striped:true,//斑马线
            columns: [[
                { field: 'ck', checkbox: true },
                { field: 'scenicId', title: '景点编号', width: 160},
                { field: 'ticketName', title: '产品名称', width: 240},
                { field: 'name', title: '门票类型', width: 280},
                { field: 'minDiscountPrice', title: '价格(起)', width: 150}
            ]],
            toolbar: '#ticketPriceSelectGridTb',
            onBeforeLoad : function(data) {   // 查询参数
                data.scenicId = $('#qryScenicId').combobox('getValue');
                data.ticketName = $('#qryTicketName').textbox('getValue');
                data.linedaysId = LineRelate.linedaysId;
            }
        });
    },
    // 初始化酒店价格表格
    buildHotelPriceGrid : function(linedaysId) {
        $("#hotelPrice_dg_" + linedaysId).datagrid({
            //fit:true,
            title:'酒店组合',
            //height:400,
            url:'/line/linedaysProductPrice/listLineProduct.jhtml',
            pagination:false,
            rownumbers:true,
            //fitColumns:true,
            singleSelect:true,
            striped:true,//斑马线
            columns: [[
                { field: 'productId', title: '产品编号', width: 160},
                { field: 'productName', title: '产品名称', width: 240},
                { field: 'priceName', title: '酒店房型', width: 280},
                { field: 'createTime', title: '添加时间', width: 150},
                { field: 'opt', title: '操作', width: 60, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btnUnRelate = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='LineRelate.doUnRelateHotel("+rowData.id+","+linedaysId+")'>取消组合</a>";
                        return btnUnRelate;
                    }}
            ]],
            tools: '#hotelPrice_dg_tools_' + linedaysId,
            onBeforeLoad : function(data) {   // 查询参数
                data.linedaysId = linedaysId;
                data.productType = 'hotel';
            }
        });
    },
    // 初始化门票价格表格
    buildTicketPriceGrid : function(linedaysId) {
        $("#ticketPrice_dg_" + linedaysId).datagrid({
            //fit:true,
            title:'门票组合',
            url:'/line/linedaysProductPrice/listLineProduct.jhtml',
            pagination:false,
            rownumbers:true,
            //fitColumns:true,
            singleSelect:true,
            striped:true,//斑马线
            columns: [[
                { field: 'productId', title: '产品编号', width: 160},
                { field: 'productName', title: '产品名称', width: 240},
                { field: 'priceName', title: '门票类型', width: 280},
                { field: 'createTime', title: '添加时间', width: 150},
                { field: 'opt', title: '操作', width: 60, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var btnUnRelate = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='LineRelate.doUnRelateTicket("+rowData.id+","+linedaysId+")'>取消组合</a>";
                        return btnUnRelate;
                    }}
            ]],
            tools: '#ticketPrice_dg_tools_' + linedaysId,
            onBeforeLoad : function(data) {   // 查询参数
                data.linedaysId = linedaysId;
                data.productType = 'scenic';
            }
        });
    },
    // 打开酒店房型列表窗口
    openHotelPriceSelectDg : function(linedaysId) {
        LineRelate.linedaysId = linedaysId;
        $("#hotelPriceSelectDg").dialog("open");
    },
    // 查询
    searchHotelPrice : function() {
        $('#hotelPriceSelectGrid').datagrid({url:'/hotel/hotelPrice/listHotelPriceForLine.jhtml'});
    },
    // 确认组合
    doRelateHotel : function() {
        var rows = $('#hotelPriceSelectGrid').datagrid('getSelections');
        if (rows.length < 1) {
            show_msg("请选择记录");
            return ;
        }
        var idsArray = [];
        for (var i = 0; i < rows.length; i++){
            var row = rows[i];
            idsArray.push(row.id);
        }
        var ids = idsArray.join(',');
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post("/line/linedaysProductPrice/relateProduct.jhtml",
            {ids: ids, productType: 'hotel', linedaysId: LineRelate.linedaysId, lineId: LineRelate.lineId},
            function(result) {
                $.messager.progress("close");
                if (result.success == true) {
                    $("#hotelPriceSelectDg").dialog("close");
                    $("#hotelPrice_dg_" + LineRelate.linedaysId).datagrid('load');
                } else {
                    show_msg("组合失败");
                }
            }
        );
    },
    // 取消酒店组合
    doUnRelateHotel : function(linedaysProductPriceId, linedaysId) {
        $.messager.confirm('温馨提示', '您确认取消选中记录组合？', function(r){
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post("/line/linedaysProductPrice/unRelateProduct.jhtml",
                    {ids : linedaysProductPriceId},
                    function(result) {
                        $.messager.progress("close");
                        if (result.success == true) {
                            $("#hotelPrice_dg_" + linedaysId).datagrid('load');
                        } else {
                            show_msg("取消组合失败");
                        }
                    }
                );
            }
        });
    },
    // 关闭窗口事件处理函数
    hotelPriceSelectDgClose : function() {
        $('#qryHotelId').numberbox('setValue', '');
        $('#qryHotelName').textbox('setValue', '');
        $('#hotelPriceSelectGrid').datagrid('loadData', []);
    },
    // 打开门票列表窗口
    openTicketPriceSelectDg : function(linedaysId) {
        LineRelate.linedaysId = linedaysId;
        $("#ticketPriceSelectDg").dialog("open");
    },
    // 查询
    searchTicketPrice : function() {
        $('#ticketPriceSelectGrid').datagrid({url:'/ticket/ticketPrice/listHotelPriceForLine.jhtml'});
    },
    // 确认组合
    doRelateTicket : function() {
        var rows = $('#ticketPriceSelectGrid').datagrid('getSelections');
        if (rows.length < 1) {
            show_msg("请选择记录");
            return ;
        }
        var idsArray = [];
        for (var i = 0; i < rows.length; i++){
            var row = rows[i];
            idsArray.push(row.id);
        }
        var ids = idsArray.join(',');
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post("/line/linedaysProductPrice/relateProduct.jhtml",
            {ids: ids, productType: 'scenic', linedaysId: LineRelate.linedaysId, lineId: LineRelate.lineId},
            function(result) {
                $.messager.progress("close");
                if (result.success == true) {
                    $("#ticketPriceSelectDg").dialog("close");
                    $("#ticketPrice_dg_" + LineRelate.linedaysId).datagrid('load');
                } else {
                    show_msg("组合失败");
                }
            }
        );
    },
    // 取消门票组合
    doUnRelateTicket : function(linedaysProductPriceId, linedaysId) {
        $.messager.confirm('温馨提示', '您确认取消选中记录组合？', function(r){
            if (r) {
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post("/line/linedaysProductPrice/unRelateProduct.jhtml",
                    {ids : linedaysProductPriceId},
                    function(result) {
                        $.messager.progress("close");
                        if (result.success == true) {
                            $("#ticketPrice_dg_" + linedaysId).datagrid('load');
                        } else {
                            show_msg("取消组合失败");
                        }
                    }
                );
            }
        });
    },
    // 关闭窗口事件处理函数
    ticketPriceSelectDgClose : function() {
        $('#qryScenicId').combobox('setValue', '');
        $('#qryTicketName').textbox('setValue', '');
        $('#ticketPriceSelectGrid').datagrid('loadData', []);
    },
    // 查询景点
    scenicLoader:function(param, success, error) {
        var q = param.q || '';
        if (q.length <= 1) {
            return false
        }
        $.ajax({
            url : '/ticket/ticket/getScenicList.jhtml',
            dataType : 'json',
            type : 'POST',
            data : {
                maxRows : 20,
                name_startsWith : q
            },
            success : function(data) {
                var items = $.map(data, function(item) {
                    return {
                        id : item.id,
                        name : item.name+"-"+item.fatherName,
                        ticketName : item.name,
                        city_code : item.city_code,
                        address : item.address,
                        star : item.star

                    };
                });
                success(items);
            },
            error : function() {
                error.apply(this, arguments);
            }
        });
    },
	// 关闭
	closeChildPanel : function() {
		parent.window.Line.closeEditPanel(true);
	}
};

$(function() {
	LineRelate.init();
});