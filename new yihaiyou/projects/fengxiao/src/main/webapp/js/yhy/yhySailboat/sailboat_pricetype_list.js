/**
 * Created by huangpeijie on 2016-12-02,0002.
 */
var SailboatPriceList = {
    SailboatPriceType: {sailboat: "帆船", yacht: "游艇", huanguyou: "鹭岛游"},
    SailboatPriceStatus: {UP: "已上架", DOWN: "已下架", UP_CHECKING: "上架中", DOWN_CHECKING: "下架中", REFUSE: "已拒绝"},
    valuationModels: {commissionModel: "佣金模式", fixedModel: "固定价模式", lowPriceModel: "底价模式"},
    valuationInfo: {},
    SailboatPriceListTable: null,
    init: function () {
        SailboatPriceList.getYhySailboatPriceList();
        SailboatPriceList.initComp();
        SailboatPriceList.initEvt();
    },
    initComp: function() {
        $('#sailboatPriceStatusSel').btComboBox();
        if ($('#productId').val() == null || $('#productId').val().length <= 0) {
            $('#addMoreDiv').hide();
        }
        $('#startDate').datetimepicker({
            format: 'yyyy-mm-dd',
            autoclose: 'true',
            language: 'zh-CN',
            minView: 'month',
            startDate:  moment().format('YYYY-MM-DD')
        }).on('changeDate', function(e) {
            var startDate = e.date;
            $("#endDate").datetimepicker('setStartDate', startDate);
            //$("#endDate").datetimepicker('setEndDate', moment(startDate).add(6, "months").format('YYYY-MM-DD'));
        });
        $('#endDate').datetimepicker({
            format: 'yyyy-mm-dd',
            autoclose: 'true',
            language: 'zh-CN',
            minView: 'month',
            startDate:  moment().add(1, "days").format('YYYY-MM-DD')
            //endDate: moment().add(6, "months").format('YYYY-MM-DD')
        });


    },
    initEvt: function() {
        $('#sailboatPriceSearchBtn').on('click', function(event) {
            event.stopPropagation();
            SailboatPriceList.doReSearchSailboatPrice();
        });
        $('#addMoreTypeBtn').on('click', function(event) {
            event.stopPropagation();
            if ($('#productId').val() != null && $('#productId').val().length > 0) {
                window.location.href = "/yhy/yhySailboatPrice/toSailboatPriceType.jhtml"
            } else {
                $.messager.show({
                    msg: "无效操作!",
                    type: 'error'
                });
            }
        });
        // week sel
        $('#allWeekCheck').on('click', function(event) {
            event.stopPropagation();
            $('.week-area').find('input[type = "checkbox"]').prop('checked', $(this).is(':checked'));
        });
        $('.week-area').find('input[type = "checkbox"]:gt(0)').on('click', function(event) {
            event.stopPropagation();
            var checkAll = true;
            if (!$(this).is(':checked')) {
                $('#allWeekCheck').prop('checked', false);
            } else {
                $.each($('.week-area').find('input[type = "checkbox"]:gt(0)'), function(i, ck) {
                    checkAll = checkAll & $(ck).is(':checked');
                });
                if (checkAll) {
                    $('#allWeekCheck').prop('checked', true);
                }
            }
        });

        // add price info btn
        $('#addPriceInfoBtn').on('click', function(event) {
            event.stopPropagation();
            SailboatPriceList.setPriceData();
        });
        // clear price info btn
        $('#clearPriceInfoBtn').on('click', function(event) {
            SailboatPriceList.clearAllPriceData();
        });
        // save price info btn
        $('#savePriceBtn').on('click', function(event) {
            SailboatPriceList.saveTicketPriceCalendar();
        });

        $(".up").on("click", function(event) {
            $("#table-sort").val("desc");
            event.stopPropagation();
            SailboatPriceList.doReSearchSailboatPrice();
        });
        $(".down").on("click", function(event) {
            $("#table-sort").val("asc");
            event.stopPropagation();
            SailboatPriceList.doReSearchSailboatPrice();
        });



        // table click
        //$('#yhySailboatPriceList tbody').on('click', 'tr', function (event) {
        //    if ($(this).hasClass('selected')) {
        //        $(this).removeClass('selected');
        //    } else {
        //        $('#yhySailboatPriceList tbody tr').removeClass('selected');
        //        $(this).addClass('selected');
        //    }
        //});
    },
    getYhySailboatPriceList: function () {
        SailboatPriceList.SailboatPriceListTable = $('#yhySailboatPriceList').DataTable({
            //"processing": true,
            "serverSide": true,
            "searching": false,
            "ordering": false,
            "pagingType": "full_numbers",
            "info": false,
            "stateSave": true,
            "lengthMenu": [ 10, 50, 200, 600, 1000 ],
            "language":{ "url":"/lib/datatables-1.10.12/js/chinese.json"},
            "ajax": function(data, callback, settings) {
                data['ticketPrice.ticket.id'] = $('#productId').val();
                data['ticketPrice.status'] = $('input[name = "ticketPrice.status"]').val();
                data['ticketPrice.name'] = $('#searchProductName').val();
                if ($("#table-sort").val()) {
                    data['order'] = $("#table-sort").val();
                    data['sort'] = 'showOrder';
                }
                $.ajax({
                    url: '/yhy/yhySailboatPrice/getYhySailBoatPriceList.jhtml',
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
            columns: [
                {'data': 'name', 'defaultContent': "-"},
                {'data': 'ticket.ticketType', 'defaultContent': "-",
                    'render': function(value, type, row, meta) {
                        return SailboatPriceList.SailboatPriceType[value];
                    }
                },
                {'data': 'isTodayValid', 'defaultContent': "-",
                    'render': function(value, type, row, meta) {
                        return value?"是":"否";
                    }
                },
                {'data': 'isConditionRefund', 'defaultContent': "-",
                    'render': function(value, type, row, meta) {
                        return value?"是":"否";
                    }
                },
                {'data': 'status', 'defaultContent': "-",
                    'render': function(value, type, row, meta) {
                        return SailboatPriceList.SailboatPriceStatus[value];
                    }
                },
                {'data': null, 'defaultContent': "-", 'render': SailboatPriceList.getOpt},
                {'data': "showOrder", 'defaultContent': "-", 'render': SailboatPriceList.sorting}
            ]
        });
        YhyCommon.Table = SailboatPriceList.SailboatPriceListTable;
    },
    getOpt: function(value, type, row, meta) {
        var status = row.status;
        var optHtml = "";
        var priceId = row.originId && row.originId != "" ? row.originId : row.id;
        var calendarOpt = "<a onclick='SailboatPriceList.editPriceCalendar("+ priceId +")' class='link-span'>价格日历</a>";
        var editOpt = "<a onclick='SailboatPriceList.editTicketPrice("+ row.id +")' class='link-span'>编辑</a>";
        var detailOpt = "<a onclick='SailboatPriceList.ticketPriceDetail("+ row.id +")' class='link-span'>详情</a>";
        var downOpt = "<a onclick='SailboatPriceList.downTicketPrice("+ row.id +")' class='link-span'>申请下架</a>";
        var revokeOpt = "<a onclick='SailboatPriceList.revokeTicketPrice("+ row.id +")' class='link-span'>撤销</a>";
        var delOpt = "<a onclick='SailboatPriceList.delTicketPrice("+ row.id +")' class='link-span'>删除</a>";
        if (status == "UP") {
            optHtml = calendarOpt + editOpt + detailOpt + downOpt;
        } else if (status == "DOWN") {
            optHtml = calendarOpt + editOpt + detailOpt + delOpt;
        } else if (status == "UP_CHECKING") {
            optHtml = detailOpt + revokeOpt;
        } else if (status == "REFUSE") {
            optHtml = editOpt + detailOpt + revokeOpt;
        }
        return optHtml;
    },
    sorting: function(value, type, row, meta){

        var sortId = "<span onclick='SailboatPriceList.sortEdite("+ row.id +")' class='rank-span' id='rank-show-"+ row.id +"'>"+ value +"</span>";  //非编辑状态
        var sortBox = "<span id='rank-edit-"+ row.id +"' style='display: none;'><input class='rank-input' id='rank-input-value-"+ row.id +"' value='"+ value +"'/><span class='rank-bottn' onclick='SailboatPriceList.saveSortEdit("+ row.id +")'></span>" +
            "<span class='rank-bottn rank-error' onclick='SailboatPriceList.cancelSort("+ row.id +")' id='rank-cancel-edit-"+ row.id +"'></span>" +
            "</span>";
        var sortHtml = sortId + sortBox;   //编辑状态
        return sortHtml;
    },
    doSearchSailboatPrice: function() {
        SailboatPriceList.SailboatPriceListTable.ajax.reload(function(result){}, false);
    },
    doReSearchSailboatPrice: function() {
        SailboatPriceList.SailboatPriceListTable.ajax.reload(function(result){}, false);
    },
    editTicketPrice: function(id) {
        window.location.href = "/yhy/yhySailboatPrice/toSailboatPriceType.jhtml?id=" + id;
    },
    ticketPriceDetail: function(id) {
        window.location.href = "/yhy/yhySailboatPrice/toTicketPriceDetail.jhtml?id=" + id;
    },

    sortEdite: function(id){
        $("#rank-edit-"+ id +"").show();
        $("#rank-show-"+ id +"").hide();
        $("#rank-input-value-"+ id +"").keyup(function() {
            if (isNaN(this.value)) {
                $.messager.show({
                    msg: "包含非数字!",
                    type: "error"
                });
                this.value = 1;
            } else if (this.value > 999) {
                $.messager.show({
                    msg: "需要小于1000的数字!",
                    type: "error"
                });
                this.value = 999;
            }
        });
    },

    cancelSort: function(id) {
        $("#rank-edit-"+ id +"").hide();
        $("#rank-show-"+ id +"").show();
        $("#rank-input-value-"+ id +"").val($("#rank-show-"+ id +"").html());
    },

    saveSortEdit: function(id) {
        var url = '/ticket/ticket/doEditTypeShowOrder.jhtml';

        if (!$("#rank-input-value-"+ id +"").val()) {
            $.messager.show({
                msg: "不能为空!",
                type: "error"
            });
            return;
        } else if ($("#rank-input-value-"+ id +"").val() <= 0) {
            $.messager.show({
                msg: "需要大于0的数字!",
                type: "error"
            });
            return;
        }

        $.post(url,
            {
                ticketPriceId: id,
                showOrder: $("#rank-input-value-"+ id +"").val()
            },
            function(data){
                if (data.success) {
                    $("#inputShowOrder-type-" + id + "").val($("#rank-input-value-"+ id +"").val());
                    $("#rank-show-"+ id +"").html($("#rank-input-value-"+ id +"").val());
                    $("#rank-edit-"+ id +"").hide();
                    $("#rank-show-"+ id +"").show();
                }
            });
    },
    downTicketPrice: function(id) {
        $.messager.show({
            msg: '确定要下架?',
            iconCls: 'containBody',
            btns: [
                {
                    btnText: '确定',
                    btnCls: 'btn-info',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                        $.ajax({
                            url: '/yhy/yhySailboatPrice/downTicketPrice.jhtml',
                            type: 'post',
                            dataType: 'json',
                            progress: true,
                            data: {
                                id: id
                            },
                            success: function(result) {
                                if (result.success) {
                                    $.messager.show({
                                        msg: result.msg,

                                        type: "success",
                                        afterClosed: function() {
                                            SailboatPriceList.doSearchSailboatPrice();
                                        }
                                    });
                                } else {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "error"
                                    });
                                }
                            },
                            error: function() {
                                $.messager.show({
                                    msg: "操作失败! 请稍后重试!",
                                    type: "error"
                                });
                            }
                        });
                    }
                },
                {
                    btnText: '取消',
                    btnCls: 'btn-default',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                    }
                }
            ]
        });
    },
    revokeTicketPrice: function(id) {
        $.messager.show({
            msg: '确定要撤销?',
            iconCls: 'containBody',
            btns: [
                {
                    btnText: '确定',
                    btnCls: 'btn-info',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                        $.ajax({
                            url: '/yhy/yhySailboatPrice/revokeTicketPrice.jhtml',
                            type: 'post',
                            dataType: 'json',
                            progress: true,
                            data: {
                                id: id
                            },
                            success: function(result) {
                                if (result.success) {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "success",
                                        afterClosed: function() {
                                            SailboatPriceList.doSearchSailboatPrice();
                                        }
                                    });
                                } else {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "error"
                                    });
                                }
                            },
                            error: function() {
                                $.messager.show({
                                    msg: "操作失败! 请稍后重试!",
                                    type: "error"
                                });
                            }
                        });
                    }
                },
                {
                    btnText: '取消',
                    btnCls: 'btn-default',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                    }
                }
            ]
        });
    },
    delTicketPrice: function(id) {
        $.messager.show({
            msg: '确定要删除?',
            iconCls: 'containBody',
            btns: [
                {
                    btnText: '确定',
                    btnCls: 'btn-info',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                        $.ajax({
                            url: '/yhy/yhySailboatPrice/delTicketPrice.jhtml',
                            type: 'post',
                            dataType: 'json',
                            progress: true,
                            data: {
                                id: id
                            },
                            success: function(result) {
                                if (result.success) {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "success",
                                        afterClosed: function() {
                                            SailboatPriceList.doSearchSailboatPrice();
                                        }
                                    });
                                } else {
                                    $.messager.show({
                                        msg: result.msg,
                                        type: "error"
                                    });
                                }
                            },
                            error: function() {
                                $.messager.show({
                                    msg: "操作失败! 请稍后重试!",
                                    type: "error"
                                });
                            }
                        });
                    }
                },
                {
                    btnText: '取消',
                    btnCls: 'btn-default',
                    callback: function(event) {
                        $(event.data.dialog).modal('hide');
                    }
                }
            ]
        });
    },
    editPriceCalendar: function(id) {
        // set default date range
        $('#startDate').val(moment().add(1, 'days').format('YYYY-MM-DD'));
        $('#endDate').val(moment().add(6, 'months').format('YYYY-MM-DD'));
        $('#ticketPriceId').val(id);
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        // show modal for calendar
        $('#price_calendar_modal').on('hide.bs.modal', function(event) {
            if($(event.target).hasClass('modal')) {
                $.form.reset({formId: '#priceCalendarForm'});
                SailboatPriceList.clearAllPriceData();
            }
        });
        $('#price_calendar_modal').modal('show');
        $('#price_calendar').fullCalendar({
            theme: true,
            header: {
                left: 'prev',
                center: 'title',
                right: 'next'
            },
            height: 400,
            eventOrder: 'id',
            defaultDate: startDate,
            lang: 'zh-cn',
            buttonIcons: false, // show the prev/next text
            weekNumbers: false,
            fixedWeekCount: false,
            editable: false,
            eventLimit: false // allow "more" link when too many events
        });
        // get ticket price data
        $.ajax({
            url: '/yhy/yhySailboatPrice/getTicketDatePrice.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                ticketPriceId: id,
                startDate: startDate,
                endDate: endDate
            },
            success: function(result) {
                if (result.success) {
                    // valuation models
                    SailboatPriceList.valuationInfo['valuationModels'] = result.valuationModels;
                    SailboatPriceList.valuationInfo['valuationValue'] = result.valuationValue;
                    if (result.valuationModels !== "lowPriceModel") {
                        $('#price').prop("readonly", true);
                        $('#valuationModels').val(SailboatPriceList.valuationModels[result.valuationModels] + "(" + result.valuationValue +")");
                    } else {
                        $('#price').prop("readonly", false);
                        $('#valuationModels').val(SailboatPriceList.valuationModels[result.valuationModels]);
                    }
                    if (result.data.length > 0) {
                        // fill price data
                        $('#price_calendar').fullCalendar('removeEvents');
                        $('#price_calendar').fullCalendar('addEventSource', result.data);
                    } else {
                        $.messager.show({
                            msg: "暂无价格数据!",
                            type: 'warn'
                        });
                    }
                } else {
                    $.messager.show({
                        msg: result.msg,
                        type: 'warn',
                        afterClosed: function() {
                            $('#price_calendar_modal').modal('hide');
                        }
                    });
                }
            },
            error: function() {
                $.messager.show({
                    msg: "获取价格数据错误, 请稍后重试!",
                    type: 'error',
                    afterClosed: function() {
                        $('#price_calendar_modal').modal('hide');
                    }
                });
            }
        });

    },
    setPriceData: function() {
        var startDateStr = $('#startDate').val();
        var endDateStr = $('#endDate').val();
        var priPrice = $('#priPrice').val();
        var price = $('#price').val();
        var inventory = $("#inventory").val();
        var priceReg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
        var numReg = /^[0-9]*[1-9][0-9]*$/;
        if (!priPrice.match(priceReg) || priPrice <= 0) {
            $.messager.show({
                msg: "销售价填写错误!",
                type: "error"
            });
            return;
        }
        if (!inventory.match(numReg)) {
            $.messager.show({
                msg: "库存填写错误!",
                type: "error"
            });
            return;
        }
        // valuation info
        var valuationModels = SailboatPriceList.valuationInfo['valuationModels'];
        var valuationValue = Number(SailboatPriceList.valuationInfo['valuationValue']);
        priPrice = Number(priPrice);
        if (valuationModels === "commissionModel") {
            price = priPrice - priPrice * valuationValue / 100;
            if (price < 0.1 &&  price > 0.01) {
                price = price.toFixed(2);
            } else if (price < 0.01) {
                price = price.toFixed(3);
            }
            $('#price').val(price);
        } else if(valuationModels === "fixedModel") {
            if (valuationValue > priPrice) {
                $.messager.show({
                    msg: "销售价不能低于" + valuationValue + "元",
                    type: 'error'
                });
                return;
            } else {
                $('#price').val(priPrice - valuationValue);
            }
        } else if (valuationModels === "lowPriceModel") {
            if (!price.match(priceReg)) {
                $.messager.show({
                    msg: "底价填写错误!",
                    type: "error"
                });
                return;
            }
            if (price > priPrice) {
                $.messager.show({
                    msg: "结算底价不能高于销售价",
                    type: 'error'
                });
                return;
            }
        }
        // week sel
        var weekStr = "";
        $.each($('.week-area').find('input[type = "checkbox"]:gt(0)'), function(i, ck) {
            var $ck = $(ck);
            if($ck.is(':checked')) {
                weekStr += $ck.val() + ",";
            };
        });
        if (weekStr.length <= 0) {
            $.messager.show({
                msg: "请选择星期范围",
                type: 'error'
            });
            return;
        }
        // set full calendar event source
        SailboatPriceList.setEventSource(startDateStr, endDateStr, weekStr, priPrice, $('#price').val(), inventory);
    },
    setEventSource: function(startDateStr, endDateStr, weekStr, member, cost, inventory) {
        var startDate = moment(startDateStr, "YYYY-MM-DD");
        var endDate = moment(endDateStr, "YYYY-MM-DD");
        var startDateMon = startDate.month();
        var endDateMon = endDate.month();
        var offset = (endDateMon-startDateMon + 12) % 12 + 1;
        var date = startDate;
        var dateMon = startDateMon;
        for (var i = 1; i <= offset; i++) {
            var data = [];
            var tempId = [];
            while (date <= endDate) {
                var tempDateMon = date.month();
                if (tempDateMon != dateMon) {	// 超过当前月，跳出循环后继续遍历
                    dateMon = tempDateMon;
                    break;
                } else {
                    var dateDay = date.day();	// 星期几
                    var dateStr = moment(date).format('YYYY-MM-DD');
                    if (weekStr.indexOf(dateDay) > -1) {	// 包含在选中的星期内
                        if (member) {
                            data.push(SailboatPriceList.buildPriPrice(date, member));
                        }
                        if (cost) {
                            data.push(SailboatPriceList.buildPrice(date, cost));
                        }
                        if (inventory){
                            data.push(SailboatPriceList.buildInventory(date, inventory));
                        }
                        if (tempId.indexOf(dateStr) <= -1) {
                            tempId.push(dateStr);
                        }
                    }
                    // 变量增加
                    //var dateTime = date.getTime() + 24 * 60 * 60 * 1000;	// 往前+1天
                    //date = new Date(dateTime);
                    date.add(1, 'days');
                }
            }
            var filter = function(event) {
                return tempId.indexOf(event.start._i) > -1;
            };
            $('#price_calendar').fullCalendar('removeEvents', filter);
            $('#price_calendar').fullCalendar('addEventSource', data);
        }
    },
    // 设置销售价
    buildPriPrice: function (date, priPrice) {
        var data = null;
        if (priPrice) {
            var vid = "1" + date.millisecond();
            data = {
                id: vid,
                priPrice: priPrice,
                title: '销售价：' + priPrice,
                start: date.format('YYYY-MM-DD')
            };
        }
        return data;
    },
    // 设置结算价
    buildPrice: function(date, price) {
        var data = null;
        if (price) {
            var vid = "2" + date.millisecond();
            data = {
                id: vid,
                price: price,
                title: '结算价：' + price,
                start: date.format('YYYY-MM-DD')
            };
        }
        return data;
    },
    // 设置库存
    buildInventory: function(date, inventory) {
        var data = null;
        if (inventory) {
            var vid = "3" + date.millisecond();
            data = {
                id: vid,
                inventory: inventory,
                title:'库存：' + inventory,
                start: date.format('YYYY-MM-DD')
            };
        }
        return data;
    },
    saveTicketPriceCalendar: function() {
        var priceCalendarArray = $('#price_calendar').fullCalendar('clientEvents');
        var priceDataArray = [];
        // build calendar data
        $.each(priceCalendarArray, function(i, c) {
            var key = c.start.format();
            var existPriceObj = $.grep(priceDataArray, function(obj, i) {
                return obj.date === key;
            });
            if (existPriceObj && existPriceObj.length > 0) {
                existPriceObj = existPriceObj[0];
                if (c.priPrice) {
                    existPriceObj.priPrice = c.priPrice;
                }
                if (c.price) {
                    existPriceObj.price = c.price;
                }
                if (c.inventory) {
                    existPriceObj.inventory = c.inventory;
                }
            } else {
                existPriceObj = {};
                existPriceObj['date'] = key;
                if (c.priPrice) {
                    existPriceObj.priPrice = c.priPrice;
                }
                if (c.price) {
                    existPriceObj.price = c.price;
                }
                if (c.inventory) {
                    existPriceObj.inventory = c.inventory;
                }
                priceDataArray.push(existPriceObj);
            }
        });
        // build form data
        var formData= {};
        $.each(priceDataArray, function(i, price) {
            $.each(price, function(n, v) {
                formData['priceCalendarVos['+ i +'].' + n] = v;
            });
        });
        formData['productId'] = $('#productId').val();
        formData['ticketPriceId'] = $('#ticketPriceId').val();
        $.ajax({
            url: '/yhy/yhySailboatPrice/saveTicketDatePrice.jhtml',
            type: 'post',
            dataType: 'json',
            data: formData,
            success: function(result) {
                if (result.success) {
                    $.messager.show({
                        msg: "价格信息保存成功!",
                        type: 'warn',
                        afterClosed: function() {
                            $('#price_calendar_modal').modal('hide');
                        }
                    });
                } else {
                    $.messager.show({
                        msg: result.msg,
                        type: 'warn'
                    });
                }
            },
            error: function() {
                $.messager.show({
                    msg: "价格信息保存错误, 请稍后重试!",
                    type: 'warn'
                });
            }
        });
    },
    clearAllPriceData: function() {
        $('#price_calendar').fullCalendar('removeEvents');
    }
};
$(function () {
    SailboatPriceList.init();
});