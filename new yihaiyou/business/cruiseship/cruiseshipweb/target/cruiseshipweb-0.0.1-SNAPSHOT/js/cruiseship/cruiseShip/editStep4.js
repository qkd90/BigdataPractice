var editStep4 = {
    today: null,        // 价格日历开始时间
    selectDateId: null,    // 选中的时间标识
    editIndex:null,     // 当前编辑行
    // 初始化
    init: function () {
        editStep4.today = $('#today').val();
        editStep4.initComp();
        editStep4.initListener();
        editStep4.initData();
    },
    // 初始控件
    initComp: function () {
        // 日历控件
        $('#calendar').fullCalendar({
            theme: true,
            header: {
                left: 'prev',
                center: 'title',
                right: 'next'
            },
            defaultDate: editStep4.today,
            lang: 'zh-cn',
            buttonIcons: false, // show the prev/next text
            weekNumbers: false,
            fixedWeekCount: false,
            editable: true,
            eventLimit: false, // allow "more" link when too many events
            eventBackgroundColor: 'transparent',
            eventBorderColor: 'transparent',
            eventTextColor: '#FC6600',
            dayClick: function(date, jsEvent, view) {
                var dateStr = date.format() + "";
                if (dateStr <= editStep4.today) {    // 选择的时间在开始时间之前
                    return ;
                }
                var eventSource = editStep4.findEventSource('cal_'+dateStr);
                if (!eventSource) {
                    return ;
                }
                // 设置选中样式
                editStep4.selectedCal(dateStr, eventSource.dateid);
            },
            eventClick: function(calEvent, jsEvent, view) {
                if (calEvent.compType) {    // 如果是按钮直接返回
                    return ;
                }
                var dateStr = calEvent.start._i;
                if (dateStr <= editStep4.today) {    // 选择的时间在开始时间之前
                    return ;
                }
                // 设置选中样式
                editStep4.selectedCal(dateStr, calEvent.dateid);
            },
            eventRender: function(event, element) {
                if (event.compType == 'button') {   // 按钮渲染
                    //element.find('div.fc-content').css({paddingTop:0});
                    if (event.btnType == 'addDate') {   // 新增发团日期
                        element.find('div.fc-content').html('<a class="calbtn" href="javascript:editStep4.addDate(\''+event.start._i+'\')">新增发团</a>');
                    } else {    // 取消发团日期
                        element.find('div.fc-content').html('<a class="calbtn" href="javascript:editStep4.delDate(\''+event.start._i+'\','+event.dateid+')">取消发团</a>');
                    }
                }
            }
            //events: '/line/linetypepricedate/findTypePriceDate.jhtml?dateStart='+dateStart+'&linetypepriceId='+priceId+'&cIndex=1'
        });
        // 房型信息表格
        $("#roomDateGrid").datagrid({
            //fit:true,
            //fitColumns:true,
            //url:'/cruiseship/cruiseShipRoomDate/list.jhtml',
            data: [],
            pagination:false,
            singleSelect:true,
            striped:true,//斑马线
            ctrlSelect:true,// 组合键选取多条数据：ctrl+鼠标左键
            idField: 'roomId',
            columns:[[
                //{ field: 'ck', checkbox: true },
                { field: 'roomId', title: '编号', width: 60},
                { field: 'roomName', title: '名称', width: 140},
                { field: 'roomType', title: '类型', width: 80, align: 'center', codeType: 'roomType', formatter: CruiseShipUtil.codeFmt},
                { field: 'discountPrice', title: '分销价', width: 100,editor:{type:'numberbox',options:{required:true,precision:2,min:0,max:99999}}},
                { field: 'salePrice', title: '销售价', width: 100,editor:{type:'numberbox',options:{required:true,precision:2,min:0,max:99999}}},
                { field: 'marketPrice', title: '市场价', width: 100,editor:{type:'numberbox',options:{required:true,precision:2,min:0,max:99999}}},
                { field: 'inventory', title: '库存', width: 100,editor:{type:'numberbox',options:{required:true,min:0,max:99999}}},
                { field: 'opt', title: '操作', width: 80, align: 'center',
                    formatter : function(value, rowData, rowIndex) {
                        var editedBtn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep4.beginEdit(" + rowIndex + "," + rowData.roomId + ")'>编辑</a>";
                        var clearBtn = "&nbsp;<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep4.clearData(" + rowIndex + "," + rowData.roomId + ")'>清除</a>";
                        var editingBtn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep4.saveEdit(" + rowIndex + "," + rowData.roomId + ")'>保存</a>"
                            + "&nbsp;<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='editStep4.cancelEdit(" + rowIndex + "," + rowData.roomId + ")'>取消</a>";
                        if (rowData.id != null && rowData.id != "") {
                            editedBtn = editedBtn + clearBtn;
                        }
                        if (value == 'editing') {
                            return editingBtn;
                        } else {
                            return editedBtn;
                        }
                    }
                }
            ]],
            onBeforeLoad : function(data) {
                //data.productId = $('#productId').val();
            }
        });
    },
    // 监听器
    initListener: function() {

    },
    // 初始化数据
    initData : function() {
        // 日历数据
        var today = editStep4.today;
        var productId = $('#productId').val();
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        $.post('/cruiseship/cruiseShipDate/listForCalendar.jhtml?today='+today+'&cruiseShipId='+productId, function(result) {
            $.messager.progress("close");
            $('#calendar').fullCalendar('addEventSource', result);
        });
    },
    // 根据id查找日历数据
    findEventSource: function(id) {
        var res = $('#calendar').fullCalendar('clientEvents');
        for (var i = 0; i < res.length; i++) {
            if (res[i].id && res[i].id == id) {
                return res[i];
            }
        }
        return null;
    },
    // 设置选中
    selectedCal : function(date, dateid) {
        $('.fc-day[data-date!='+editStep4.today+']').css("background","transparent");
        $('.fc-day[data-date='+date+']').css("background","rgb(255,226,220)");
        editStep4.selectDateId = dateid;
        $('#roomDateTitle').html(date + '房型价格');
        // 查询价格信息
        editStep4.doGridSearch(dateid);
    },
    // 取消选中
    unselectedCal : function(date) {
        $('.fc-day[data-date!='+editStep4.today+']').css("background","transparent");
        editStep4.selectDateId = null;
        $('#roomDateTitle').html('房型价格');
        // 清除价格信息
        editStep4.doClearGrid();
    },
    // 新增发团
    addDate: function(date) {

        //var url = "/contract/contract/isHasContract.jhtml";
        //$.post(url, {proId: $("#productId").val()}, function(data) {
        //    if (!data.isHas) {
        //        show_msg(data.reMsg);
        //        return ;
        //    } else {
                // 删除新增按钮数据
                var filter = function(event) {
                    return date.indexOf(event.start._i) > -1;
                };

                $('#calendar').fullCalendar('removeEvents', filter);
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post('/cruiseship/cruiseShipDate/save.jhtml',
                    {cruiseShipId : $('#productId').val(), date : date},
                    function(result) {
                        $.messager.progress("close");
                        if (result.success) {
                            // 界面渲染
                            var data = [];
                            data.push({id:'cal_'+date,title:'0元起',start:date,dateid:result.dateid}); // dateid后台回调
                            data.push({compType:'button',btnType:'delDate',start:date,dateid:result.dateid});// 按钮数据
                            $('#calendar').fullCalendar('addEventSource', data);
                            editStep4.selectedCal(date, result.dateid);
                        } else {
                            show_msg("操作失败");
                        }
                    }
                );
            //}
        //});


    },
    // 取消发团
    delDate: function(date, id) {
        $.messager.confirm('温馨提示', '取消发团将删除该日期下的价格信息，您确认执行此操作？', function(r){
            if (r) {
                // 删除取消按钮数据
                var filter = function(event) {
                    return date.indexOf(event.start._i) > -1;
                };
                $('#calendar').fullCalendar('removeEvents', filter);
                $.messager.progress({
                    title:'温馨提示',
                    text:'数据处理中,请耐心等待...'
                });
                $.post('/cruiseship/cruiseShipDate/del.jhtml',
                    {dateId : id},
                    function(result) {
                        $.messager.progress("close");
                        if (result.success) {
                            // 界面渲染
                            var data = [];
                            data.push({compType:'button',btnType:'addDate',start:date});// 按钮数据
                            $('#calendar').fullCalendar('addEventSource', data);
                            editStep4.unselectedCal(date);
                        } else {
                            show_msg("操作失败");
                        }
                    }
                );
            }
        });
    },
    // 价格信息查询
    doGridSearch: function(dateid) {
        $('#roomDateGrid').datagrid({url:'/cruiseship/cruiseShipRoomDate/list.jhtml?dateId='+dateid});
    },
    // 清除表格数据
    doClearGrid: function() {
        $('#roomDateGrid').datagrid('loadData', []);
    },
    // 表格编辑-编辑
    beginEdit: function(index, roomId) {
        if (editStep4.editIndex != index) {
            var row = editStep4.getRow(index);
            row.opt = 'editing';    // 更新操作按钮
            $('#roomDateGrid').datagrid('refreshRow', index);
            $('#roomDateGrid').datagrid('beginEdit', index);
            editStep4.editIndex = index;
        }
        $('#roomDateGrid').datagrid('selectRow', index);
    },
    // 表格编辑-清除
    clearData: function(index, roomId) {
        $.messager.progress({
            title:'温馨提示',
            text:'数据处理中,请耐心等待...'
        });
        var row = editStep4.getRow(index);
        if (row.id == null || row.id == "") {
            $.messager.progress("close");
            $.messager.alert('提示', '当天该房型没有报价信息! 无需清除!', 'alert');
            return;
        }
        $.post('/cruiseship/cruiseShipRoomDate/del.jhtml',
            {roomDateId : row.id},
            function(result) {
                $.messager.progress("close");
                if (result.success) {
                    // 删除取消按钮数据
                    var filter = function(event) {
                        return row.dateStr.indexOf(event.start._i) > -1;
                    };
                    $('#calendar').fullCalendar('removeEvents', filter);
                    // 界面渲染
                    var data = [];
                    data.push({id:'cal_'+row.dateStr,title:result.minDiscountPrice+'元起',start:row.dateStr,dateid:row.dateId}); // dateid后台回调
                    data.push({compType:'button',btnType:'delDate',start:row.dateStr,dateid:row.dateId});// 按钮数据
                    $('#calendar').fullCalendar('addEventSource', data);
                    //$('#roomDateGrid').datagrid('deleteRow', index);
                    editStep4.selectedCal(row.dateStr, row.dateId);
                } else {
                    show_msg("操作失败");
                }
            }
        );
    },
    // 表格编辑-保存
    saveEdit: function(index, roomId) {
        if ($('#roomDateGrid').datagrid('validateRow', index)) {
            var discountPriceEditor = $('#roomDateGrid').datagrid('getEditor', {index:index,field:'discountPrice'});
            var discountPrice = $(discountPriceEditor.target).numberbox('getValue');
            var salePriceEditor = $('#roomDateGrid').datagrid('getEditor', {index:index,field:'salePrice'});
            var salePrice = $(salePriceEditor.target).numberbox('getValue');
            var marketPriceEditor = $('#roomDateGrid').datagrid('getEditor', {index:index,field:'marketPrice'});
            var marketPrice = $(marketPriceEditor.target).numberbox('getValue');
            var inventoryEditor = $('#roomDateGrid').datagrid('getEditor', {index:index,field:'inventory'});
            var inventory = $(inventoryEditor.target).numberbox('getValue');
            var row = editStep4.getRow(index);
            // 异步保存价格信息
            $.messager.progress({
                title:'温馨提示',
                text:'数据处理中,请耐心等待...'
            });
            $.post('/cruiseship/cruiseShipRoomDate/save.jhtml',
                {id:row.id, dateId:editStep4.selectDateId, roomId:roomId, discountPrice:discountPrice, salePrice:salePrice, marketPrice:marketPrice, inventory:inventory},
                function(result) {
                    $.messager.progress("close");
                    if (result.success) {
                        // 删除取消按钮数据
                        var filter = function(event) {
                            return result.date.indexOf(event.start._i) > -1;
                        };
                        $('#calendar').fullCalendar('removeEvents', filter);
                        // 界面渲染
                        var data = [];
                        data.push({id:'cal_'+result.date,title:result.minDiscountPrice+'元起',start:result.date,dateid:result.dateid}); // dateid后台回调
                        data.push({compType:'button',btnType:'delDate',start:result.date,dateid:result.dateid});// 按钮数据
                        $('#calendar').fullCalendar('addEventSource', data);
                        editStep4.selectedCal(result.date, result.dateid);

                        CruiseShipUtil.buildCruiseship(result.dateid);
                    } else {
                        show_msg("操作失败");
                    }
                }
            );
            editStep4.endEdit(index, roomId);
        }
    },
    // 表格编辑-结束
    endEdit: function(index, roomId) {
        $('#roomDateGrid').datagrid('endEdit', index);
        var row = editStep4.getRow(index);
        row.opt = 'edited';    // 更新操作按钮
        $('#roomDateGrid').datagrid('refreshRow', index);
        editStep4.editIndex = null;
    },
    // 表格编辑-取消
    cancelEdit: function(index, roomId) {
        $('#roomDateGrid').datagrid('cancelEdit', index);
        var row = editStep4.getRow(index);
        row.opt = 'edited';    // 更新操作按钮
        $('#roomDateGrid').datagrid('refreshRow', index);
        editStep4.editIndex = null;
    },
    // 获取表格行数据
    getRow: function(index) {
        var rows = $('#roomDateGrid').datagrid('getRows');
        return rows[index];
    },

    // 下一步
    nextGuide: function (winId) {
        parent.window.showGuide(winId, true);
    }
};

//返回本页面数据
function getIfrData(){
	var data = {};
	return data;
}	

$(function(){
    editStep4.init();
});