/**
 * Created by zzl on 2016/11/24.
 */
var HotelPriceList = {
    HotelPriceStatus: {UP: "已上架", DOWN: "已下架", UP_CHECKING: "上架中", REFUSE: "已拒绝"},
    valuationModels: {commissionModel: "佣金模式", fixedModel: "固定价模式", lowPriceModel: "底价模式"},
    valuationInfo: {},
    HotelPriceListTable: null,
    init: function() {
        HotelPriceList.initComp();
        HotelPriceList.getYhyHotelPriceList();
        HotelPriceList.initEvt();
    },
    initComp: function() {
        $('#hotelPriceStatusSel').btComboBox();
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
            startDate:  moment().add(1, "days").format('YYYY-MM-DD'),
            //endDate: moment().add(6, "months").format('YYYY-MM-DD')
        });
    },
    initEvt: function() {
        // search btn
        $('#priceSearchBtn').on('click', function (event) {
            event.stopPropagation();
            HotelPriceList.doResearchPrice();
        });
        // add btn
        $('#addMorePriceBtn').on('click', function(event) {
            event.stopPropagation();
            if ($('#productId').val() != null && $('#productId').val().length > 0) {
                window.location.href = "/yhy/yhyHotelPrice/toHotelRoomType.jhtml";
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
            HotelPriceList.setPriceData();
        });
        $('#clearPriceInfoBtn').on('click', function(event) {
            HotelPriceList.clearAllPriceData();
        });
        // save price info btn
        $('#savePriceBtn').on('click', function(event) {
            HotelPriceList.saveHotelPriceCalendar();
        });


        $(".up").on("click", function(event) {
            $("#table-sort").val("desc");
            event.stopPropagation();
            HotelPriceList.doResearchPrice();
        });
        $(".down").on("click", function(event) {
            $("#table-sort").val("asc");
            event.stopPropagation();
            HotelPriceList.doResearchPrice();
        });

        // table click
        //$('#yhyHotelPriceList tbody').on('click', 'tr', function (event) {
        //    if ($(this).hasClass('selected')) {
        //        $(this).removeClass('selected');
        //    } else {
        //        $('#yhyHotelPriceList tbody tr').removeClass('selected');
        //        $(this).addClass('selected');
        //    }
        //});
    },
    getYhyHotelPriceList: function() {
        HotelPriceList.HotelPriceListTable = $('#yhyHotelPriceList').DataTable({
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
                data['hotelPrice.hotel.id'] = $('#productId').val();
                data['hotelPrice.status'] = $('input[name = "hotelPrice.status"]').val();
                data['hotelPrice.roomName'] = $('#searchRoomName').val();
                if ($("#table-sort").val()) {
                    data['order'] = $("#table-sort").val();
                    data['sort'] = 'showOrder';
                }
                $.ajax({
                    url: '/yhy/yhyHotelPrice/getYhyHotelPriceList.jhtml',
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
                {"data": "roomName", 'defaultContent': "-"},
                {"data": "roomDescription", 'defaultContent': "-"},
                {"data": "roomAmount", 'defaultContent': "-"},
                {"data": "roomNum", 'defaultContent': "-"},
                {"data": "capacity", 'defaultContent': "-", 'render': function(value, type, row, meta){
                    return value + "人";
                }
                },
                {"data": "status", 'defaultContent': "-", "render": function (value, type, row, meta) {
                    return HotelPriceList.HotelPriceStatus[value];
                }
                },
                {'data': null, 'defaultContent': "-", 'render': HotelPriceList.getOpt},
                {'data': "showOrder", 'defaultContent': "-", 'render': HotelPriceList.sorting}
            ]
        });
        YhyCommon.Table = HotelPriceList.HotelPriceListTable;
    },
    doSearchPrice: function() {
        HotelPriceList.HotelPriceListTable.ajax.reload(function(result){}, false);
    },
    doResearchPrice: function() {
        HotelPriceList.HotelPriceListTable.ajax.reload(function(result){}, true);
    },
    getOpt: function(value, type, row, meta) {
        var status = row.status;
        var optHtml = "";
        var priceId = row.originId && row.originId != "" ? row.originId : row.id;
        var calendarOpt = "<a onclick='HotelPriceList.editPriceCalendar("+ priceId +")' class='link-span'>价格日历</a>";
        var editOpt =  "<a onclick='HotelPriceList.editHotelPrice("+ row.id +")' class='link-span'>编辑</a>";
        var detailOpt = "<a onclick='HotelPriceList.hotelPriceDetail("+ row.id +")' class='link-span'>详情</a>";
        var downOpt = "<a onclick='HotelPriceList.downHotelPrice("+ row.id +")' class='link-span'>下架</a>";
        var revokeOpt = "<a onclick='HotelPriceList.revokeHotelPrice("+ row.id +")' class='link-span'>撤销</a>";
        var delOpt = "<a onclick='HotelPriceList.delHotelPrice("+ row.id +")' class='link-span'>删除</a>";
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
        var sortId = "<span onclick='HotelPriceList.sortEdite("+ row.id +")' class='rank-span' id='rank-show-"+ row.id +"'>"+ value +"</span>";  //非编辑状态
        var sortBox = "<span id='rank-edit-"+ row.id +"' style='display: none;'><input class='rank-input' id='rank-input-value-"+ row.id +"' value='"+ value +"'/>" +
            "<span class='rank-bottn' onclick='HotelPriceList.saveSortEdit("+ row.id +")'></span>" +
            "<span class='rank-bottn rank-error' onclick='HotelPriceList.cancelSort("+ row.id +")' id='rank-cancel-edit-"+ row.id +"'></span></span>";
        var sortHtml = sortId + sortBox;   //编辑状态
        return sortHtml;
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
        var url = '/hotel/hotel/doEditTypeShowOrder.jhtml';

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
                typePriceId: id,
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

    editHotelPrice: function(id) {
        window.location.href = "/yhy/yhyHotelPrice/toHotelRoomType.jhtml?id=" + id;
    },
    hotelPriceDetail: function(id) {
        window.location.href = "/yhy/yhyHotelPrice/toHotelRoomTypeDetail.jhtml?id=" + id;
    },
    revokeHotelPrice: function(id) {
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
                            url: '/yhy/yhyHotelPrice/revokeHotelPrice.jhtml',
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
                                            HotelPriceList.doSearchPrice();
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
    downHotelPrice: function(id) {
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
                            url: '/yhy/yhyHotelPrice/downHotelPrice.jhtml',
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
                                            HotelPriceList.doSearchPrice();
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
    delHotelPrice: function(id) {
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
                            url: '/yhy/yhyHotelPrice/delHotelPrice.jhtml',
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
                                            HotelPriceList.doSearchPrice();
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
    saveHotelPriceCalendar: function() {
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
                if (c.member) {
                    existPriceObj.member = c.member;
                }
                if (c.cost) {
                    existPriceObj.cost = c.cost;
                }
                if (c.inventory) {
                    existPriceObj.inventory = c.inventory;
                }
            } else {
                existPriceObj = {};
                existPriceObj['date'] = key;
                if (c.member) {
                    existPriceObj.member = c.member;
                }
                if (c.cost) {
                    existPriceObj.cost = c.cost;
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
        formData['hotelPriceId'] = $('#hotelPriceId').val();
        $.ajax({
            url: '/yhy/yhyHotelPrice/saveTypePriceDate.jhtml',
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
    editPriceCalendar: function(id) {
        // set default date range
        $('#startDate').val(moment().add(1, 'days').format('YYYY-MM-DD'));
        $('#endDate').val(moment().add(6, 'months').format('YYYY-MM-DD'));
        $('#hotelPriceId').val(id);
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        // show modal for calendar
        $('#price_calendar_modal').on('hide.bs.modal', function(event) {
            if($(event.target).hasClass('modal')) {
                $.form.reset({formId: '#priceCalendarForm'});
                HotelPriceList.clearAllPriceData();
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
        // get hotel price data
        $.ajax({
            url: '/yhy/yhyHotelPrice/getTypePriceDate.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                hotelPriceId: id,
                startDate: startDate,
                endDate: endDate
            },
            success: function(result) {
                if (result.success) {
                    // valuation models
                    HotelPriceList.valuationInfo['valuationModels'] = result.valuationModels;
                    HotelPriceList.valuationInfo['valuationValue'] = result.valuationValue;
                    if (result.valuationModels !== "lowPriceModel") {
                        var unit = result.valuationModels === "commissionModel" ? "%" : "元";
                        $('#costPrice').prop("readonly", true);
                        $('#valuationModels').val(HotelPriceList.valuationModels[result.valuationModels] + "(" + result.valuationValue + unit + ")");
                    } else {
                        $('#costPrice').prop("readonly", false);
                        $('#valuationModels').val(HotelPriceList.valuationModels[result.valuationModels]);
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
    setPriceData: function () {
        var startDateStr = $('#startDate').val();
        var endDateStr = $('#endDate').val();
        var memberPrice = $('#memberPrice').val();
        var costPrice = $('#costPrice').val();
        var inventory = $("#inventory").val();
        var priceReg = /^[0-9]+([.]{1}[0-9]{1,2})?$/;
        var numReg = /^[0-9]*[1-9][0-9]*$/;
        if (!memberPrice.match(priceReg) || memberPrice <= 0) {
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
        var valuationModels =  HotelPriceList.valuationInfo['valuationModels'];
        var valuationValue = Number(HotelPriceList.valuationInfo['valuationValue']);
        memberPrice = Number(memberPrice);
        if (valuationModels === "commissionModel") {
            costPrice = memberPrice - memberPrice * valuationValue / 100;
            if (costPrice < 0.1 &&  costPrice > 0.01) {
                costPrice = costPrice.toFixed(2);
            } else if (costPrice < 0.01) {
                costPrice = costPrice.toFixed(3);
            }
            $('#costPrice').val(costPrice);
        } else if(valuationModels === "fixedModel") {
            if (valuationValue > memberPrice) {
                $.messager.show({
                    msg: "销售价不能低于" + valuationValue + "元",
                    type: 'error'
                });
                return;
            } else {
                $('#costPrice').val(memberPrice - valuationValue);
            }
        } else if (valuationModels === "lowPriceModel") {
            if (costPrice > memberPrice) {
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
        HotelPriceList.setEventSource(startDateStr, endDateStr, weekStr, memberPrice, $('#costPrice').val(), inventory);
    },
    clearAllPriceData: function() {
        $('#price_calendar').fullCalendar('removeEvents');
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
                            data.push(HotelPriceList.buildMemberPrice(date, member));
                        }
                        if (cost) {
                            data.push(HotelPriceList.buildCostprice(date, cost));
                        }
                        if (inventory){
                            data.push(HotelPriceList.buildInventory(date, inventory));
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
    buildMemberPrice : function(date, member) {
        var data = null;
        if (member) {
            var vid = "1" + date.millisecond();
            data = {
                id: vid,
                member: member,
                title: '销售价：' + member,
                start: date.format('YYYY-MM-DD')
            };
        }
        return data;
    },
    // 设置结算价
    buildCostprice : function(date, cost) {
        var data = null;
        if (cost) {
            var vid = "2" + date.millisecond();
            data = {
                id: vid,
                cost: cost,
                title: '结算价：'+cost,
                start: date.format('YYYY-MM-DD')
            };
        }
        return data;
    },
    // 设置库存
    buildInventory : function(date, inventory) {
        var data = null;
        if (inventory) {
            var vid = "3" + date.millisecond();
            data = {
                id: vid,
                inventory: inventory,
                title:'库存：'+inventory,
                start: date.format('YYYY-MM-DD')
            };
        }
        return data;
    }
};
$(function() {
    HotelPriceList.init();
});