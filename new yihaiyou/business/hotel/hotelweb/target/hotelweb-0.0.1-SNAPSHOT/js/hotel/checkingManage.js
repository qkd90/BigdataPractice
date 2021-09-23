/**
 * Created by dy on 2016/6/2.
 */
var CheckHotelContants = {
    productStatus: [{id: 'UP_CHECKING', text: '上架中'}, {id: 'UP', text: '已上架'}, {id: 'REFUSE', text: '被拒绝'}, {id: 'DOWN_CHECKING', text: '下架中'}, {id: 'DOWN', text: '已下架'}],
    /*UP("上架"), DOWN("下架"), DEL("删除"), GUARANTEE("担保"),
    ADD_CHECKING("新增审核中"), UPDATE_CHECKING("更新审核中"), UP_CHECKING("上架审核中"),
    ADD_REFUSE("新增拒绝"), UPDATE_REFUSE("更新拒绝"), UP_REFUSE("上架拒绝");*/
    priceStatus: [{id: 'UP_CHECKING', text: '上架中'}, {id: 'UP', text: '已上架'}, {id: 'REFUSE', text: '被拒绝'}, {id: 'DOWN', text: '已下架'}],
    getConstants : function(code, filter) {
        var resultArray = [];
        var codeArray = CheckHotelContants[code];
        for (var i = 0; i < codeArray.length; i++) {
            var item = codeArray[i];
            if (filter && item.filter) {	// 过滤
                continue ;
            }
            resultArray.push(item);
        }
        return resultArray;
    },
    getCodeName : function(codeType, code) {
        if (codeType) {
            var codeArray = CheckHotelContants[codeType];
            for (var i = 0; i < codeArray.length; i++) {
                if (codeArray[i].id == code) {
                    return codeArray[i].text ;
                }
            }
        }
        return '';
    },
    // 表格代码转换
    codeFmt : function(value, rowData, rowIndex) {
        if (value && this.codeType) {
            return CheckHotelContants.getCodeName(this.codeType, value);
        }
        return '';
    }
};
var HotelManage = {

    init : function() {
        //HotelManage.initShow();

        HotelManage.initTabs();
        HotelManage.initCom();
    },
    initCom: function() {
        /*$("#storage_type_qry_source").combobox({
            onSelect: function(record) {
                $("#storage_dg").datagrid("load")
            }
        });
        $("#show_type_qry_source").combobox({
            onSelect: function(record) {
                $("#show_dg").datagrid("load")
            }
        });*/
    },
    initTabs:function() {
        var tab = $('#tt').tabs('getSelected');
        var index = $('#tt').tabs('getTabIndex',tab);

        if (index == 0) {
            HotelManage.initStorage();
        } else {
            HotelManage.initTypeStorage();
        }

        $('#tt').tabs({
            onSelect: function(title, index){
                if (index == 0) {
                    HotelManage.initStorage();
                } else {
                    HotelManage.initTypeStorage();
                }
            }
        });
    },

    doSearchStorage: function() {
        $("#storage_dg").datagrid("load", {});
    },
    doSearchStorageType: function() {

        $("#storage_type_dg").datagrid("load", {});
    },
    doClearStorage: function() {
        //$("#storage_qry_source").combobox("setValue", "");
        $("#storage_qry_name").textbox("setValue", "");
        $("#storage_qry_status").combobox("setValue", "");
        var data = {
        };
        $("#storage_dg").datagrid("load", data);
    },
    doClearStorageType: function() {
        //$("#storage_qry_source").combobox("setValue", "");
        $("#storage_type_qry_name").textbox("setValue", "");
        $("#storage_type_qry_status").combobox("setValue", "");
        var data = {
        };
        $("#storage_type_dg").datagrid("load", data);
    },



    viewDetail: function(id,  name) {
        var url = "/hotel/hotel/viewDetail.jhtml?productId=" + id;
        // 打开编辑窗口
        var ifr = $("#editDetailPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editDetailPanel").dialog({
            title:name,
            closed: false,
            closable:true,
            shadow:true
        });
        $("#editDetailPanel").dialog("open");
    },
    initStorage: function() {
        $("#storage_dg").datagrid({
            fit:true,
            fitColumns:true,
            url:'/hotel/hotel/getAllHotelList.jhtml',
            data: [],
            pagination:true,
            pageList:[10,20,50],
            //rownumbers:true,
            singleSelect:false,
            striped:true, //斑马线
            ctrlSelect:true, // 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                {field: 'id', title: '民宿ID', align: 'center', width: 80},
                { field: 'name', title: '民宿名称', width: 200},
                { field: 'extend.address', title: '民宿地址', width: 180},
                { field: 'supplier.userName', title: '联系人', width: 100},
                { field: 'supplier.mobile', title: '联系电话', width: 150},
                { field: 'status', title: '状态', width: 100, codeType: 'productStatus', formatter: CheckHotelContants.codeFmt},
                {field: 'updateTime', title: '更新时间', width: 180, align: 'center', datePattern: 'yyyy-MM-dd HH:mm:ss', formatter: CheckHotelContants.dateTimeFmt},
                { field: 'opt', title: '操作', width: 220, align: 'center',
                    formatter : function(value, rowData, rowIndex) {


                        var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='HotelManage.doEdit("+rowData.id+")'>编辑</a>";
                        var checkedUpEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='HotelManage.doCheckHotelInfoUp("+rowData.id+")'>通过</a>";
                        var checkedDownEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='HotelManage.doCheckHotelInfoDown("+rowData.id+")'>通过</a>";
                        var checkedRefuseDownEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='HotelManage.openHotelInfoRefuseDown("+rowData.id+")'>拒绝</a>";
                        var checkedFailEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='HotelManage.openHotelInfoRefusePanel("+rowData.id+")'>拒绝</a>";
                        var btnHideEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='HotelManage.doHotelInfoDown("+ rowData.id +")'>下架</a>";

                        var btnReturnEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='HotelManage.doHotelInfoReturn("+ rowData.id +")'>撤销</a>";
                        var btnDelEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='HotelManage.doHotelInfoDel("+ rowData.id +")'>删除</a>";

                        var imgSortEdit = "<a href='javascript:void(0)' style='color:blue; text-decoration: underline;' onclick='HotelManage.doHotelImageSort("+ rowData.id +")'>图片排序</a>";

                        if (rowData.imageTotalCount) {
                            if (rowData.status == 'UP_CHECKING') {
                                return btnEdit + "&nbsp;&nbsp;" + checkedUpEdit + "&nbsp;&nbsp;" + checkedFailEdit + "&nbsp;&nbsp;" + imgSortEdit;
                            } else if (rowData.status == 'UP') {
                                return btnEdit + "&nbsp;&nbsp;" + btnHideEdit + "&nbsp;&nbsp;" + imgSortEdit;
                            } else if (rowData.status == 'DOWN_CHECKING') {
                                return btnEdit + "&nbsp;&nbsp;" + checkedDownEdit + "&nbsp;&nbsp;" + checkedRefuseDownEdit + "&nbsp;&nbsp;" + imgSortEdit;
                            } else if (rowData.status == 'DOWN') {
                                return btnEdit + "&nbsp;&nbsp;" + imgSortEdit;
                            } else if (rowData.status == 'REFUSE') {
                                if (rowData.originId) {
                                    return btnEdit + "&nbsp;&nbsp;" + btnReturnEdit + "&nbsp;&nbsp;" + imgSortEdit;
                                } else {
                                    return btnEdit + "&nbsp;&nbsp;" + btnDelEdit + "&nbsp;&nbsp;" + imgSortEdit;
                                }
                            }
                        } else {
                            if (rowData.status == 'UP_CHECKING') {
                                return btnEdit + "&nbsp;&nbsp;" + checkedUpEdit + "&nbsp;&nbsp;" + checkedFailEdit ;
                            } else if (rowData.status == 'UP') {
                                return btnEdit + "&nbsp;&nbsp;" + btnHideEdit ;
                            } else if (rowData.status == 'DOWN_CHECKING') {
                                return btnEdit + "&nbsp;&nbsp;" + checkedDownEdit + "&nbsp;&nbsp;" + checkedRefuseDownEdit ;
                            } else if (rowData.status == 'DOWN') {
                                return btnEdit ;
                            } else if (rowData.status == 'REFUSE') {
                                if (rowData.originId) {
                                    return btnEdit + "&nbsp;&nbsp;" + btnReturnEdit ;
                                } else {
                                    return btnEdit + "&nbsp;&nbsp;" + btnDelEdit ;
                                }
                            }
                        }
                    }},
                {field: 'showOrder', title: '排序', width: 140, align: 'center', sortable:'true', formatter: function(value, rowData, rowIndex) {
                    var editBtn = '<div id="div-show-order-edit-'+ rowData.id +'"><span id="span-show-order-edit-'+ rowData.id +'">'+ value + '</span><a href="javascript:void(0)" class="datagrid-showorder-btn" id="editShowOrder-'+  rowData.id +'" onclick="HotelManage.doEditHotelShowOrder('+  rowData.id +', -1)"></a></div>';
                    var doEditBtn = '<div id="div-show-order-input-'+ rowData.id +'" style="display:none;"><input type="text" style="width: 100px; height: 30px;" id="inputShowOrder-'+  rowData.id +'" value="'+ value + '" data-id="'+ rowData.id +'"></div>'
                    return editBtn + doEditBtn;
                }}
            ]],
            toolbar: '#storage_tb',
            onBeforeLoad : function(data) {   // 查询参数
                if ($("#storage_qry_status").combobox("getValue").length > 0) {
                    data['hotel.status'] = $("#storage_qry_status").combobox("getValue");
                }
                if ($("#storage_qry_name").textbox("getValue").length > 0) {
                    data['hotel.name'] = $("#storage_qry_name").textbox("getValue");
                }
                data['hotel.showStatus'] = 'SHOW';
                data.source = 'LXB';
            },
            onSelect : function(rowIndex, rowData) {
            },
            onUnselect : function(rowIndex, rowData) {
            },
            onLoadSuccess: function(data) {
                $.each(data.rows, function(i, row) {
                    $("#editShowOrder-"+ row.id +"").linkbutton({
                        iconCls: 'icon-edit'
                    });
                    $("#inputShowOrder-"+ row.id +"").numberbox({
                        //required: true,
                        min:1,
                        max:999,
                        precision:0,
                        icons: [{
                            iconCls:'icon-ok',
                            handler: function(e){
                                HotelManage.doEditProductShowOrder($(e.data.target), $(e.data.target).attr("data-id"), 1);
                            }
                        },{
                            iconCls:'icon-cancel',
                            handler: function(e){
                                HotelManage.doEditHotelShowOrder($(e.data.target).attr("data-id"), 1);
                            }
                        }]

                    });
                });

            }
        });
    },

    doEditHotelShowOrder: function(id, opt) {
        if (opt == -1) {
            $('#div-show-order-edit-' + id + '').hide();
            $('#div-show-order-input-' + id + '').show();
        } else {
            $("#inputShowOrder-"+ id +"").numberbox("setValue", $("#span-show-order-edit-"+ id +"").html());
            $('#div-show-order-edit-' + id + '').show();
            $('#div-show-order-input-' + id + '').hide();
        }
    },

    doEditProductShowOrder: function(iptObj, id, opt) {
        var url = '/hotel/hotel/doEditProductShowOrder.jhtml';
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        $.post(url,
            {
                productId: id,
                showOrder: iptObj.numberbox("getValue")
            },
            function(data){
                if (data.success) {
                    $("#inputShowOrder-" + id + "").val(iptObj.numberbox("getValue"));
                    $("#span-show-order-edit-" + id + "").html(iptObj.numberbox("getValue"));
                    HotelManage.doEditHotelShowOrder(id, opt);
                    //TicketUtil.buildSailboat(id);
                } else {
                    HotelManage.doEditHotelShowOrder(id, 1);
                }
                show_msg(data.errorMsg);
                $.messager.progress('close');
            });
    },
    
    initTypeStorage: function() {
        $("#storage_type_dg").datagrid({
            fit:true,
            fitColumns:true,
            url:'/hotel/hotel/getHotelPriceList.jhtml',
            data: [],
            pagination:true,
            pageList:[10,20,50],
            //rownumbers:true,
            singleSelect:false,
            striped:true, //斑马线
            ctrlSelect:true, // 组合键选取多条数据：ctrl+鼠标左键
            columns: [[
                {field: 'id', title: '房型ID', align: 'center', width: 80},
                { field: 'roomName', title: '房型名称', width: 140},
                { field: 'name', title: '民宿名称', width: 220},
                { field: 'roomDescription', title: '房型描述', width: 260},
                { field: 'roomNum', title: '房号', width: 160, align: 'center'},
                { field: 'capacity', title: '可住', width: 80, align: 'center'},
                { field: 'status', title: '状态', width: 80, align: 'center', codeType: 'priceStatus', formatter: CheckHotelContants.codeFmt},
                {field: 'modifyTime', title: '更新时间', width: 150, align: 'center', datePattern: 'yyyy-MM-dd HH:mm:ss', formatter: CheckHotelContants.dateTimeFmt},
                { field: 'opt', title: '操作', width: 200, align: 'center',
                    formatter : function(value, rowData, rowIndex) {

                        var priceCalendarBtn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='HotelManage.viewPriceCalendar("+rowData.id+")'>价格日历</a>";

                        var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='HotelManage.doPriceTypeEdit("+rowData.id+")'>编辑</a>";
                        var checkedUpEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='HotelManage.doCheckHotelPriceInfoUp("+rowData.id+")'>通过</a>";
                        var checkedFailEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='HotelManage.openHotelPriceTypeInfoRefusePanel("+rowData.id+")'>拒绝</a>";
                        var btnHideEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='HotelManage.doPriceTypeDown("+ rowData.id +")'>下架</a>";

                        var btnReturnEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='HotelManage.doHotelPriceInfoReturn("+ rowData.id +")'>撤销</a>";
                        var btnDelEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='HotelManage.doHotelPriceDel("+ rowData.id +")'>删除</a>";

                        if (rowData.status == 'UP_CHECKING') {
                            return btnEdit + "&nbsp;&nbsp;" + checkedUpEdit + "&nbsp;&nbsp;" + checkedFailEdit;
                        } else if (rowData.status == 'UP') {
                            return priceCalendarBtn + "&nbsp;&nbsp;" + btnEdit + "&nbsp;&nbsp;" + btnHideEdit;
                        } else if (rowData.status == 'DOWN') {
                            return priceCalendarBtn + "&nbsp;&nbsp;" + btnEdit;
                        } else if (rowData.status == 'REFUSE') {
                            if (rowData.originId) {
                                return btnEdit + "&nbsp;&nbsp;" + btnReturnEdit;
                            } else {
                                return btnEdit + "&nbsp;&nbsp;" + btnDelEdit;
                            }
                        }
                    }
                },
                {field: 'showOrder', title: '排序', width: 140, align: 'center', sortable:'true', formatter: function(value, rowData, rowIndex) {
                    var editBtn = '<div id="div-show-order-type-edit-'+ rowData.id +'"><span id="span-show-order-type-edit-'+ rowData.id +'">'+ value + '</span>' +
                        '<a href="javascript:void(0)" class="datagrid-showorder-btn" id="editShowOrder-type-'+  rowData.id +'" onclick="HotelManage.doEditHotelTypeShowOrder('+  rowData.id +', -1)"></a></div>';
                    var doEditBtn = '<div id="div-show-order-type-input-'+ rowData.id +'" style="display:none;"><input type="text" style="width: 100px; height: 30px;" id="inputShowOrder-type-'+  rowData.id +'" value="'+ value + '" data-id="'+ rowData.id +'"></div>'
                    return editBtn + doEditBtn;
                }}
            ]],
            toolbar: '#storage_type_tb',
            onBeforeLoad : function(data) {   // 查询参数
                if ($("#storage_type_qry_status").combobox("getValue").length > 0) {
                    data['hotelPrice.status'] =$("#storage_type_qry_status").combobox("getValue");
                }
                if ($("#storage_type_qry_name").textbox("getValue").length > 0) {
                    data['hotelPrice.hotel.name'] =$("#storage_type_qry_name").textbox("getValue");
                }
                data['hotelPrice.showStatus'] = 'SHOW';
                data['hotelPrice.hotel.source'] = 'LXB';
            },
            onSelect : function(rowIndex, rowData) {
            },
            onUnselect : function(rowIndex, rowData) {
            },
            onLoadSuccess: function(data) {
                $.each(data.rows, function(i, row) {
                    $("#editShowOrder-type-"+ row.id +"").linkbutton({
                        iconCls: 'icon-edit'
                    });
                    $("#inputShowOrder-type-"+ row.id +"").numberbox({
                        //required: true,
                        min:1,
                        max:999,
                        precision:0,
                        icons: [{
                            iconCls:'icon-ok',
                            handler: function(e){
                                HotelManage.doEditTypeShowOrder($(e.data.target), $(e.data.target).attr("data-id"), 1);
                            }
                        },{
                            iconCls:'icon-cancel',
                            handler: function(e){
                                HotelManage.doEditHotelTypeShowOrder($(e.data.target).attr("data-id"), 1);
                            }
                        }]

                    });
                });
            }
        });
    },
    doEditHotelTypeShowOrder: function(id, opt) {
        if (opt == -1) {
            $('#div-show-order-type-edit-' + id + '').hide();
            $('#div-show-order-type-input-' + id + '').show();
        } else {
            $("#inputShowOrder-type-"+ id +"").numberbox("setValue", $("#span-show-order-type-edit-"+ id +"").html());
            $('#div-show-order-type-edit-' + id + '').show();
            $('#div-show-order-type-input-' + id + '').hide();
        }
    },

    doEditTypeShowOrder: function(iptObj, id, opt) {
        var url = '/hotel/hotel/doEditTypeShowOrder.jhtml';
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        $.post(url,
            {
                typePriceId: id,
                showOrder: iptObj.numberbox("getValue")
            },
            function(data){
                if (data.success) {
                    $("#inputShowOrder-type-" + id + "").val(iptObj.numberbox("getValue"));
                    $("#span-show-order-type-edit-" + id + "").html(iptObj.numberbox("getValue"));
                    HotelManage.doEditHotelTypeShowOrder(id, opt);
                    //TicketUtil.buildSailboat(id);
                } else {
                    HotelManage.doEditHotelTypeShowOrder(id, 1);
                }
                show_msg(data.errorMsg);
                $.messager.progress('close');
            });


    },


    viewPriceCalendar: function(id) {
        var url = "/hotel/hotel/viewPrcieCalendar.jhtml?typePriceId=" + id;
        // 打开编辑窗口
        var ifr = $("#editPanel_1").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel_1").dialog("open");

    },

    /**
     * “民宿”编辑上架中，被拒绝后撤销操作
     * @param id
     */
    doHotelInfoReturn: function(id) {

        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doHotelInfoReturn.jhtml';
        $.post(url, {productId: id}, function(data){
            if (data.success) {
                show_msg("撤销成功！");
                $('#storage_dg').datagrid('load', {});
            } else {
                show_msg("操作失败！")
            }
            $.messager.progress('close');
        });

    },

    /**
     * “民宿”新增上架中，被拒绝后删除操作
     * @param id
     */
    doHotelInfoDel: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doHotelInfoDel.jhtml';
        $.post(url, {productId: id}, function(data){
            if (data.success) {
                show_msg("删除成功！");
                $('#storage_dg').datagrid('load', {});
            } else {
                show_msg("操作失败！")
            }
            $.messager.progress('close');
        });
    },

    /**
     * “房型”编辑上架中，被拒绝后撤销操作
     * @param id
     */
    doHotelPriceInfoReturn: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doHotelPriceInfoReturn.jhtml';
        $.post(url, {typePriceId: id}, function(data){
            if (data.success) {
                show_msg("撤销成功！");
                $('#storage_type_dg').datagrid('load', {});
            } else {
                show_msg("操作失败！")
            }
            $.messager.progress('close');
        });
    },

    /**
     * “房型”新增上架中，被拒绝后删除操作
     * @param id
     */
    doHotelPriceDel: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doHotelPriceDel.jhtml';
        $.post(url, {typePriceId: id}, function(data){
            if (data.success) {
                show_msg("撤销成功！");
                $('#storage_type_dg').datagrid('load', {});
            } else {
                show_msg("操作失败！")
            }
            $.messager.progress('close');
        });
    },

    /**
     * 编辑民宿基本信息
     * @param id
     */
    doEdit: function(id) {
        //var url = "/yhy/yhyHotelInfo/toHotelInfo.jhtml?id=" + id;
        var url = "/hotel/hotel/checkHotelInfo.jhtml?productId=" + id;
        // 打开编辑窗口
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title:'编辑民宿基本信息'
        });
        $("#editPanel").dialog("open");
    },

    /**
     * 编辑民宿图片排序
     * @param id
     */
    doHotelImageSort: function(id) {
        //var url = "/yhy/yhyHotelInfo/toHotelInfo.jhtml?id=" + id;
        var url = "/hotel/hotel/hotelImages.jhtml?productId=" + id;
        // 打开编辑窗口
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title:'民宿图片（拖动图片可对图片进行排序）'
        });
        $("#editPanel").dialog("open");
    },

    /**
     * 编辑房型基本信息
     * @param id
     */
    doPriceTypeEdit: function(id) {
        var url = "/hotel/hotel/checkHotelPriceTypeInfo.jhtml?typePriceId=" + id;
        // 打开编辑窗口
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title:'房型信息编辑'
        });
        $("#editPanel").dialog("open");
    },


    doPriceTypeDown: function(id) {

        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doPriceTypeDown.jhtml';
        $.post(url, {typePriceId: id}, function(data){
            if (data.success) {
                show_msg("下架成功！");
                $('#storage_type_dg').datagrid('load', {});
            } else {
                show_msg("下架失败！")
            }
            $.messager.progress('close');
        });

    },

    /**
     * 下架操作
     */
    doHotelInfoDown: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doCheckHotelInfoDown.jhtml';
        $.post(url, {productId: id}, function(data){
            if (data.success) {
                show_msg("下架成功！");
                $('#storage_dg').datagrid('load', {});
            } else {
                show_msg("下架失败！")
            }
            $.messager.progress('close');
        });

    },

    /**
     * 民宿基本信息审核通过操作
     * @param id
     */
    doCheckHotelInfoUp: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doCheckHotelInfoUp.jhtml';
        $.post(url, {productId: id}, function(data){
            if (data.success) {
                show_msg("上架成功！");
                $('#storage_dg').datagrid('load', {});
            } else {
                show_msg("上架失败！")
            }
            $.messager.progress('close');
        });
    },

    doCheckHotelInfoDown: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doCheckHotelInfoDown.jhtml';
        $.post(url, {productId: id}, function(data){
            if (data.success) {
                show_msg("下架成功！");
                $('#storage_dg').datagrid('load', {});
            } else {
                show_msg("下架失败！")
            }
            $.messager.progress('close');
        });
    },

    /**
     * 民宿类型基本信息审核通过操作
     * @param id
     */
    doCheckHotelPriceInfoUp: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doCheckHotelPriceInfoUp.jhtml';
        $.post(url, {typePriceId: id}, function(data){
            if (data.success) {
                show_msg("上架成功！");
                $('#storage_type_dg').datagrid('load', {});
            } else {
                show_msg("上架失败！")
            }
            $.messager.progress('close');
        });
    },

    /**
     * 民宿基本信息审核拒绝操作
     * @param id
     */
    doRefuseHotelInfo: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doRefuseHotelInfoUp.jhtml';
        $.post(url, {productId: id, content: $("#refuseContentId").textbox("getValue")}, function(data){
            if (data.success) {
                $.messager.progress('close');
                show_msg("操作成功！");
                $("#editRefusePanel").dialog("close");
                $('#storage_dg').datagrid('load', {});

            } else {
                $.messager.progress('close');
                show_msg("操作成功！")
            }
            $.messager.progress('close');
        });
    },
    openHotelInfoRefusePanel: function(id) {
        $("#refuseContentId").textbox("setValue", "");
        $("#editRefusePanel").dialog({
            title:'拒绝理由',
            buttons:[
                {
                    text:'提交',
                    handler: function() {
                        if ($("#refuseContentId").val()) {
                            HotelManage.doRefuseHotelInfo(id);
                        } else {
                            show_msg("请完善拒绝理由");
                        }

                    }
                },
                {
                    text:'取消',
                    handler: function() {
                        $("#refuseContentId").textbox("setValue", "");
                        $("#editRefusePanel").dialog("close");
                    }
                }
            ],
            onClose: function() {
                $.messager.progress('close');
            }
        });
        $("#editRefusePanel").dialog("open");
    },

    /**
     * 拒绝下架，返回上架状态
     * @param id
     */
    doRefuseHotelInfoDown: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doRefuseHotelInfoDown.jhtml';
        $.post(url, {productId: id, content: $("#refuseContentId").textbox("getValue")}, function(data){
            if (data.success) {
                $.messager.progress('close');
                show_msg("操作成功！");
                $('#storage_dg').datagrid('load', {});
                $("#editRefusePanel").dialog("close");


            } else {
                $.messager.progress('close');
                show_msg("操作成功！")
            }
            $.messager.progress('close');
        });
    },

    openHotelInfoRefuseDown: function(id) {
        $("#refuseContentId").textbox("setValue", "");
        $("#editRefusePanel").dialog({
            title:'拒绝理由',
            buttons:[
                {
                    text:'提交',
                    handler: function() {
                        if ($("#refuseContentId").val()) {
                            HotelManage.doRefuseHotelInfoDown(id);
                        } else {
                            show_msg("请完善拒绝理由");
                        }

                    }
                },
                {
                    text:'取消',
                    handler: function() {
                        $("#refuseContentId").textbox("setValue", "");
                        $("#editRefusePanel").dialog("close");
                    }
                }
            ],
            onClose: function() {
                $.messager.progress('close');
            }
        });
        $("#editRefusePanel").dialog("open");
    },
    /**
     * 民宿房型基本信息审核拒绝操作
     * @param id
     */
    doRefuseHotelPriceTypeInfo: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/hotel/hotel/doRefuseHotelPriceTypeInfo.jhtml';
        $.post(url, {typePriceId: id, content: $("#refuseContentId").textbox("getValue")}, function(data){
            if (data.success) {
                $.messager.progress('close');
                show_msg("操作成功！");
                $('#storage_type_dg').datagrid('load', {});
                $("#editRefusePanel").dialog("close");
            } else {
                $.messager.progress('close');
                show_msg("操作失败！")
            }
            $.messager.progress('close');
        });
    },
    openHotelPriceTypeInfoRefusePanel: function(id) {
        $("#refuseContentId").textbox("setValue", "");
        $("#editRefusePanel").dialog({
            title:'拒绝理由',
            buttons:[
                {
                    text:'提交',
                    handler: function() {
                        if ($("#refuseContentId").val()) {
                            HotelManage.doRefuseHotelPriceTypeInfo(id);
                        } else {
                            show_msg("请完善拒绝理由");
                        }
                    }
                },
                {
                    text:'取消',
                    handler: function() {
                        $("#refuseContentId").textbox("setValue", "");
                        $("#editRefusePanel").dialog("close");
                    }
                }
            ],
            onClose: function() {
                $("#refuseContentId").textbox("setValue", "");
                $.messager.progress('close');
            }
        });
        $("#editRefusePanel").dialog("open");
    }

};
$(function() {
    HotelManage.init();
});
