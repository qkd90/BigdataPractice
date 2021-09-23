/**船票管理*/
var TicketList = {
    init: function() {
        TicketList.initTabs();
    },
    initTabs:function() {
        var tab = $('#tt').tabs('getSelected');
        var index = $('#tt').tabs('getTabIndex',tab);
        if (index == 0) {
            TicketList.initTickeListData();
        } else {
            TicketList.initTickeTypeListData();
        }

        $('#tt').tabs({
            onSelect: function(title, index){
                if (index == 0) {
                    TicketList.initTickeListData();
                } else {
                    TicketList.initTickeTypeListData();
                }
            }
        });
    },

    // 表格查询
    doShowSearch: function () {
        $('#show_dg').datagrid("load", {});
    },

    // 表格查询
    doShowTypeSearch: function () {
        $('#show_type_dg').datagrid("load", {});
    },


    reload: function () {
        $("#search_ticketType").combobox("setValue", "");
        $("#search_ticketStatus").combobox("setValue", "");
        $("#search_name").textbox("setValue", "");
        TicketList.doShowSearch();
    },

    reloadType: function () {
        $("#search_type_ticketType").combobox("setValue", "");
        $("#search_type_ticketStatus").combobox("setValue", "");
        $("#search_ticket_name").textbox("setValue", "");
        $("#search_type_name").textbox("setValue", "");
        TicketList.doShowTypeSearch();
    },
    showCategoryPanel: true,

    initTickeListData: function () {

        var index = -1;
        // 构建表格
        $('#show_dg').datagrid({
            url: '/ticket/ticket/checkingSearch.jhtml',
            pagination: true,
            pageList: [10, 20, 30],
            singleSelect: true,
            striped: true,
            fit: true,
			//rownumbers:true,
			//fitColumns:true,
            columns: [[
                {field: 'id', title: '产品ID', align: 'center', width: 80},
                {field: 'name', title: '产品名称', width: 220},
                {field: 'ticketTypeName', title: '类型', align: 'center'},
                {field: 'address', title: '地址',  width: 180},
                {field: 'supplierName', title: '联系人', align: 'center', width: 80},
                {field: 'supplierMobile', title: '联系电话', align: 'center', width: 130},
                {field: 'status', title: '状态', align: "center", width: 60,  codeType: 'productStatus', formatter: TicketUtil.codeFmt},
                {field: 'updateTime', title: '更新时间', width: 180, align: 'center', datePattern: 'yyyy-MM-dd HH:mm:ss', formatter: TicketUtil.dateTimeFmt},
                {
                    field: "OPT", title: "操作", align: 'center', width: 220,
                    formatter: function (value, rowData, rowIndex) {
                        var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.doEdit("+rowData.id+")'>编辑</a>";
                        var checkedUpEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.doCheckSailboatInfoUp("+rowData.id+")'>通过</a>";
                        var checkedDownEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.doCheckSailboatInfoDown("+rowData.id+")'>通过</a>";
                        var checkedRefuseDownEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.openSailboatInfoRefuseDown("+rowData.id+")'>拒绝</a>";
                        var checkedFailEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.openSailboatInfoRefusePanelUp("+rowData.id+")'>拒绝</a>";
                        var btnHideEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='TicketList.doSailboatInfoDown("+ rowData.id +")'>下架</a>";


                        var btnReturnEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='TicketList.doSailboatInfoReturn("+ rowData.id +")'>撤销</a>";
                        var btnDelEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='TicketList.doSailboatInfoDel("+ rowData.id +")'>删除</a>";

                        var imgSortEdit = "<a href='javascript:void(0)' style='color:blue; text-decoration: underline;' onclick='TicketList.doSailboatInfoImageSort("+ rowData.id +")'>图片排序</a>";


                        if (rowData.imageTotalCount) {
                            if (rowData.status == 'UP_CHECKING') {
                                return btnEdit + "&nbsp;&nbsp;" + checkedUpEdit + "&nbsp;&nbsp;" + checkedFailEdit + "&nbsp;&nbsp;" + btnHideEdit + "&nbsp;&nbsp;" + imgSortEdit;
                            } else if (rowData.status == 'UP') {
                                return btnEdit + "&nbsp;&nbsp;" + btnHideEdit+ "&nbsp;&nbsp;" + imgSortEdit;
                            } else if (rowData.status == 'DOWN_CHECKING') {
                                return btnEdit + "&nbsp;&nbsp;" + checkedDownEdit + "&nbsp;&nbsp;" + checkedRefuseDownEdit + "&nbsp;&nbsp;" + imgSortEdit;
                            } else if (rowData.status == 'DOWN') {
                                return btnEdit + "&nbsp;&nbsp;" + imgSortEdit;
                            } else if (rowData.status == 'REFUSE') {
                                if (rowData.originId) {
                                    return btnEdit + "&nbsp;&nbsp;" + btnReturnEdit+ "&nbsp;&nbsp;" + imgSortEdit;
                                } else {
                                    return btnEdit + "&nbsp;&nbsp;" + btnDelEdit + "&nbsp;&nbsp;" + imgSortEdit;
                                }
                            }
                        } else {
                            if (rowData.status == 'UP_CHECKING') {
                                return btnEdit + "&nbsp;&nbsp;" + checkedUpEdit + "&nbsp;&nbsp;" + checkedFailEdit + "&nbsp;&nbsp;" + btnHideEdit;
                            } else if (rowData.status == 'UP') {
                                return btnEdit + "&nbsp;&nbsp;" + btnHideEdit;
                            } else if (rowData.status == 'DOWN_CHECKING') {
                                return btnEdit + "&nbsp;&nbsp;" + checkedDownEdit + "&nbsp;&nbsp;" + checkedRefuseDownEdit;
                            } else if (rowData.status == 'DOWN') {
                                return btnEdit;
                            } else if (rowData.status == 'REFUSE') {
                                if (rowData.originId) {
                                    return btnEdit + "&nbsp;&nbsp;" + btnReturnEdit;
                                } else {
                                    return btnEdit + "&nbsp;&nbsp;" + btnDelEdit;
                                }
                            }
                        }



                    }
                },
                {field: 'showOrder', title: '排序', width: 140, align: 'center', sortable:'true', formatter: function(value, rowData, rowIndex) {
                    var editBtn = '<div id="div-show-order-edit-'+ rowData.id +'"><span id="span-show-order-edit-'+ rowData.id +'">'+ value + '</span>' +
                        '<a href="javascript:void(0)" class="datagrid-showorder-btn" id="editShowOrder-'+  rowData.id +'" onclick="TicketList.doEditTicketShowOrder('+  rowData.id +', -1)"></a></div>';
                    var doEditBtn = '<div id="div-show-order-input-'+ rowData.id +'" style="display:none;"><input type="text" style="width: 100px; height: 30px;" id="inputShowOrder-'+  rowData.id +'" value="'+ value + '" data-id="'+ rowData.id +'"></div>'
                    return editBtn + doEditBtn;
                }}
            ]],
            toolbar: '#user_Tool',
            onBeforeLoad: function (data) {   // 查询参数
                data['ticket.showStatus'] = 'SHOW';
                data['ticket.source'] = 'LXB';
                data['ticket.name'] = $("#search_name").textbox("getValue");
                if ($("#search_ticketType").combobox("getValue") != null && $("#search_ticketType").combobox("getValue").length > 0 ) {
                    data['ticket.ticketType'] = $("#search_ticketType").combobox("getValue");
                } else {
                    data['ticket.includeTicketTypeList[0]'] = "yacht";
                    data['ticket.includeTicketTypeList[1]'] = "sailboat";
                    data['ticket.includeTicketTypeList[2]'] = "huanguyou";
                }
                data['ticket.status'] = $("#search_ticketStatus").combobox("getValue");

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
                                TicketList.doEditProductShowOrder($(e.data.target), $(e.data.target).attr("data-id"), 1);
                            }
                        },{
                            iconCls:'icon-cancel',
                            handler: function(e){
                                TicketList.doEditTicketShowOrder($(e.data.target).attr("data-id"), 1);
                            }
                        }]

                    });
                });

                for (var i = 0 ; i < data.total; i++) {

                }
            }

        });
    },

    doEditTicketShowOrder: function(id, opt) {
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
        var url = '/ticket/ticket/doEditProductShowOrder.jhtml';
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
                TicketList.doEditTicketShowOrder(id, opt);
                TicketUtil.buildSailboat(id);
            } else {
                TicketList.doEditTicketShowOrder(id, 1);
            }
            show_msg(data.errorMsg);
            $.messager.progress('close');
        });


    },
    doEdit: function(id) {
        if (!id) {
            return;
        }
        var url = "/ticket/ticket/sailboatCheckInfo.jhtml?productId=" + id;
        // 打开编辑窗口
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title:'编辑海上休闲基本信息'
        });
        $("#editPanel").dialog("open");
    },


    doSailboatInfoImageSort: function(id) {
        //var url = "/yhy/yhyHotelInfo/toHotelInfo.jhtml?id=" + id;
        var url = "/ticket/ticket/sailboatImages.jhtml?productId=" + id;
        // 打开编辑窗口
        var ifr = $("#editPanel").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel").dialog({
            title:'海上休闲图片（拖动图片可对图片进行排序）'
        });
        $("#editPanel").dialog("open");
    },


    doSailboatInfoDel: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doTicketInfoDel.jhtml';
        $.post(url, {productId: id}, function(data){
            if (data.success) {
                show_msg("删除成功！");
                $('#show_dg').datagrid('load', {});
            } else {
                show_msg("删除失败！")
            }
            $.messager.progress('close');
        });
    },

    /**
     * 撤销操作
     * @param id
     */
    doSailboatInfoReturn: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doTicketInfoReturn.jhtml';
        $.post(url, {productId: id}, function(data){
            if (data.success) {
                show_msg("撤销成功！");
                $('#show_dg').datagrid('load', {});
            } else {
                show_msg("撤销失败！")
            }
            $.messager.progress('close');
        });
    },

    /**
     * 下架操作
     */
    doSailboatInfoDown: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doCheckTicketInfoDown.jhtml';
        $.post(url, {productId: id}, function(data){
            if (data.success) {
                show_msg("下架成功！");
                $('#show_dg').datagrid('load', {});
            } else {
                show_msg("下架失败！")
            }
            $.messager.progress('close');
        });

    },


    /**
     * 游艇帆船基本信息审核通过操作
     * @param id
     */
    doCheckSailboatInfoUp: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doCheckTicketInfoUp.jhtml';
        $.post(url, {productId: id}, function(data){
            if (data.success) {
                show_msg("上架成功！");
                TicketUtil.buildSailboat(id);
                $('#show_dg').datagrid('load', {});
            } else {
                show_msg("上架失败！")
            }
            $.messager.progress('close');
        });
    },


    /**
     * 游艇帆船基本信息下架审核
     * @param id
     */
    doCheckSailboatInfoDown: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doCheckTicketInfoDown.jhtml';
        $.post(url, {productId: id}, function(data){
            if (data.success) {
                show_msg("下架成功！");
                $('#show_dg').datagrid('load', {});
            } else {
                show_msg("下架失败！")
            }
            $.messager.progress('close');
        });
    },

    /**
     * 拒绝下架，返回上架状态
     * @param id
     */
    doRefuseSailboatInfoDown: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doRefuseTicketInfoDown.jhtml';
        $.post(url, {productId: id, content: $("#refuseContentId").textbox("getValue")}, function(data){
            if (data.success) {
                $.messager.progress('close');
                show_msg("操作成功！");
                $('#show_dg').datagrid('load', {});
                $("#editRefusePanel").dialog("close");


            } else {
                $.messager.progress('close');
                show_msg("操作成功！")
            }
            $.messager.progress('close');
        });
    },

    openSailboatInfoRefuseDown: function(id) {
        $("#refuseContentId").textbox("setValue", "");
        $("#editRefusePanel").dialog({
            title:'拒绝理由',
            buttons:[
                {
                    text:'提交',
                    handler: function() {
                        if ($("#refuseContentId").val()) {
                            TicketList.doRefuseSailboatInfoDown(id);
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
     * 民宿基本信息上架审核拒绝操作
     * @param id
     */
    doRefuseSailboatInfoUp: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doRefuseTicketInfoUp.jhtml';
        $.post(url, {productId: id, content: $("#refuseContentId").textbox("getValue")}, function(data){
            if (data.success) {
                $.messager.progress('close');
                show_msg("操作成功！");
                $("#editRefusePanel").dialog("close");
                $('#show_dg').datagrid('load', {});

            } else {
                $.messager.progress('close');
                show_msg("操作成功！")
            }
            $.messager.progress('close');
        });
    },
    openSailboatInfoRefusePanelUp: function(id) {
        $("#refuseContentId").textbox("setValue", "");
        $("#editRefusePanel").dialog({
            title:'拒绝理由',
            buttons:[
                {
                    text:'提交',
                    handler: function() {
                        if ($("#refuseContentId").val()) {
                            TicketList.doRefuseSailboatInfoUp(id);
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

    initTickeTypeListData: function () {
        // 构建表格
        $('#show_type_dg').datagrid({
            url: '/ticket/ticket/checkingTypePriceSearch.jhtml',
            pagination: true,
            pageList: [10, 20, 30],
            singleSelect: true,
            striped: true,
            fit: true,
			//rownumbers:true,
            fitColumns:true,
            columns: [[
                {field: 'id', title: '票型ID', align: 'center', width: 80},
                {field: 'name', title: '票型名称', width: 140},
                {field: 'ticket.name', title: '产品名称', width: 270},
                {field: 'ticket.ticketType', title: '类型', align: 'center', width: 80 , codeType: 'ticketType', formatter: TicketUtil.codeFmt},
                {field: 'isTodayValid', title: '今日可订',  align: 'center', width: 80, formatter: function(value, rowData) {
                    if (value) {
                        return "是";
                    } else {
                        return "否";
                    }
                }},
                {field: 'isConditionRefund', title: '条件退款', align: 'center', width: 80, formatter: function(value, rowData) {
                    if (value) {
                        return "是";
                    } else {
                        return "否";
                    }
                }},
                {field: 'status', title: '状态', align: "center", width: 80,  codeType: 'ticketPriceStatus', formatter: TicketUtil.codeFmt},
                {field: 'modifyTime', title: '更新时间', width: 150, align: 'center', datePattern: 'yyyy-MM-dd HH:mm:ss', formatter: TicketUtil.dateTimeFmt},
                {
                    field: "OPT", title: "操作", align: 'center', width: 180,
                    formatter: function (value, rowData, rowIndex) {

                        var priceCalendarBtn = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.viewPriceCalendar("+rowData.id+")'>价格日历</a>";

                        var btnEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.doPriceTypeEdit("+rowData.id+")'>编辑</a>";
                        var checkedUpEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.doCheckSailboatPriceInfoUp("+rowData.id+")'>通过</a>";
                        var checkedRefuseEdit = "<a href='javascript:void(0)' style='color:blue;text-decoration: underline;' onclick='TicketList.openSailboatPriceTypeInfoRefusePanelUp("+rowData.id+")'>拒绝</a>";
                        var btnHideEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='TicketList.doPriceTypeDown("+ rowData.id +")'>下架</a>";

                        var btnReturnEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='TicketList.doSailboatPriceInfoReturn("+ rowData.id +")'>撤销</a>";
                        var btnDelEdit = "<a href='javascript:void(0)' style='color:gray;text-decoration: underline;' onclick='TicketList.doSailboatPriceInfoDel("+ rowData.id +")'>删除</a>";

                        if (rowData.status == 'UP_CHECKING') {
                            return priceCalendarBtn + "&nbsp;&nbsp;" + btnEdit + "&nbsp;&nbsp;" + checkedUpEdit + "&nbsp;&nbsp;" + checkedRefuseEdit  + "&nbsp;&nbsp;" + btnHideEdit;
                        } else if (rowData.status == 'UP') {
                            return priceCalendarBtn + "&nbsp;&nbsp;" + btnEdit + "&nbsp;&nbsp;" + btnHideEdit;
                        } else if (rowData.status == 'DOWN') {
                            return priceCalendarBtn + "&nbsp;&nbsp;" + btnEdit;
                        } else if (rowData.status == 'REFUSE') {
                            if (rowData.originId) {
                                return priceCalendarBtn + "&nbsp;&nbsp;" + btnEdit + "&nbsp;&nbsp;" + btnReturnEdit;
                            } else {
                                return priceCalendarBtn + "&nbsp;&nbsp;" + btnEdit + "&nbsp;&nbsp;" + btnDelEdit;
                            }
                        }

                    }
                },
                {field: 'showOrder', title: '排序', width: 140, align: 'center', sortable:'true', formatter: function(value, rowData, rowIndex) {
                    var editBtn = '<div id="div-show-order-type-edit-'+ rowData.id +'"><span id="span-show-order-type-edit-'+ rowData.id +'">'+ value + '</span>' +
                        '<a href="javascript:void(0)" class="datagrid-showorder-btn" id="editShowOrder-type-'+  rowData.id +'" onclick="TicketList.doEditTicketTypeShowOrder('+  rowData.id +', -1)"></a></div>';
                    var doEditBtn = '<div id="div-show-order-type-input-'+ rowData.id +'" style="display:none;"><input type="text" style="width: 100px; height: 30px;" id="inputShowOrder-type-'+  rowData.id +'" value="'+ value + '" data-id="'+ rowData.id +'"></div>'
                    return editBtn + doEditBtn;
                }}
            ]],
            toolbar: '#user_type_Tool',
            onBeforeLoad: function (data) {   // 查询参数
                data['ticketPrice.showStatus'] = 'SHOW';
                data['ticketPrice.ticket.source'] = 'LXB';
                data['ticketPrice.ticketName'] = $("#search_ticket_name").textbox("getValue");
                data['ticketPrice.name'] = $("#search_type_name").textbox("getValue");
                if ($("#search_type_ticketType").combobox("getValue") != null && $("#search_type_ticketType").combobox("getValue").length > 0 ) {
                    data['ticketPrice.ticket.ticketType'] = $("#search_type_ticketType").combobox("getValue");
                } else {
                    data['ticketPrice.ticket.includeTicketTypeList[0]'] = "yacht";
                    data['ticketPrice.ticket.includeTicketTypeList[1]'] = "sailboat";
                    data['ticketPrice.ticket.includeTicketTypeList[2]'] = "huanguyou";
                }
                data['ticketPrice.status'] = $("#search_type_ticketStatus").combobox("getValue");

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
                                TicketList.doEditTypeShowOrder($(e.data.target), $(e.data.target).attr("data-id"), 1);
                            }
                        },{
                            iconCls:'icon-cancel',
                            handler: function(e){
                                TicketList.doEditTicketTypeShowOrder($(e.data.target).attr("data-id"), 1);
                            }
                        }]

                    });
                });

            }

        });
    },

    doEditTicketTypeShowOrder: function(id, opt) {
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
        var url = '/ticket/ticket/doEditTypeShowOrder.jhtml';
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        $.post(url,
            {
                ticketPriceId: id,
                showOrder: iptObj.numberbox("getValue")
            },
            function(data){
                if (data.success) {
                    $("#inputShowOrder-type-" + id + "").val(iptObj.numberbox("getValue"));
                    $("#span-show-order-type-edit-" + id + "").html(iptObj.numberbox("getValue"));
                    TicketList.doEditTicketTypeShowOrder(id, opt);
                    //TicketUtil.buildSailboat(id);
                } else {
                    TicketList.doEditTicketTypeShowOrder(id, 1);
                }
                show_msg(data.errorMsg);
                $.messager.progress('close');
            });


    },

    doPriceTypeEdit: function(id) {
        var url = "/ticket/ticket/checkPriceInfo.jhtml?ticketPriceId=" + id;
        // 打开编辑窗口
        var ifr = $("#editPanel_0").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel_0").dialog("open");
    },

    viewPriceCalendar: function(id) {
        var url = "/ticket/ticket/viewPrcieCalendar.jhtml?ticketPriceId=" + id;
        // 打开编辑窗口
        var ifr = $("#editPanel_1").children()[0];
        $(ifr).attr("src", url);
        $("#editPanel_1").dialog("open");

    },

    /**
     * 撤销票型，返回下架状态
     * @param id
     */
    doSailboatPriceInfoReturn: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doTicketPriceInfoReturn.jhtml';
        $.post(url, {ticketPriceId: id}, function(data){
            if (data.success) {
                show_msg("撤销成功！");
                $('#show_type_dg').datagrid('load', {});
            } else {
                show_msg("撤销失败！")
            }
            $.messager.progress('close');
        });
    },


    /**
     * 删除票型
     * @param id
     */
    doSailboatPriceInfoDel: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doTicketPriceInfoDel.jhtml';
        $.post(url, {ticketPriceId: id}, function(data){
            if (data.success) {
                show_msg("删除成功！");
                $('#show_type_dg').datagrid('load', {});
            } else {
                show_msg("删除失败！")
            }
            $.messager.progress('close');
        });
    },

    /**
     * 游艇帆船票型审核通过操作
     * @param id
     */
    doCheckSailboatPriceInfoUp: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doCheckTicketPriceInfoUp.jhtml';
        $.post(url, {ticketPriceId: id}, function(data){
            if (data.success) {
                show_msg("上架成功！");
                $('#show_type_dg').datagrid('load', {});
            } else {
                show_msg("上架失败！")
            }
            $.messager.progress('close');
        });
    },
    /**
     * 游艇帆船票型审核拒绝操作
     * @param id
     */
    doRefuseTicketPriceTypeInfoUp: function(id) {
        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doRefuseTicketPriceTypeInfoUp.jhtml';
        $.post(url, {ticketPriceId: id, content: $("#refuseContentId").textbox("getValue")}, function(data){
            if (data.success) {
                $.messager.progress('close');
                show_msg("操作成功！");
                $('#show_type_dg').datagrid('load', {});
                $("#editRefusePanel").dialog("close");
            } else {
                $.messager.progress('close');
                show_msg("操作失败！")
            }
            $.messager.progress('close');
        });
    },
    openSailboatPriceTypeInfoRefusePanelUp: function(id) {
        $("#refuseContentId").textbox("setValue", "");
        $("#editRefusePanel").dialog({
            title:'拒绝理由',
            buttons:[
                {
                    text:'提交',
                    handler: function() {
                        if ($("#refuseContentId").val()) {
                            TicketList.doRefuseTicketPriceTypeInfoUp(id);
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
    },

    doPriceTypeDown: function(id) {

        $.messager.progress({
            title:'温馨提示',
            text:'处理中,请耐心等待...'
        });
        var url = '/ticket/ticket/doTicketPriceTypeDown.jhtml';
        $.post(url, {ticketPriceId: id}, function(data){
            if (data.success) {
                show_msg("下架成功！");
                $('#show_type_dg').datagrid('load', {});
            } else {
                show_msg("下架失败！")
            }
            $.messager.progress('close');
        });

    }


};
$(function () {
    TicketList.init();
});
