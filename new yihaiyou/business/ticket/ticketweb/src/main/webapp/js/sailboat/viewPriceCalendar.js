/**
 * Created by dy on 2016/11/24.
 */
var ViewPriceCalendar = {
    valuationModels: {commissionModel: "佣金模式", fixedModel: "固定价模式", lowPriceModel: "底价模式"},
    valuationInfo: {},
    init: function() {
        ViewPriceCalendar.initComm();
        ViewPriceCalendar.initPriceCalendar();
    },
    
    initComm: function() {

        /*$('#startDate, #endDate').datetimepicker({
            format: 'yyyy-mm-dd',
            autoclose: 'true',
            language: 'zh-CN',
            minView: 'month'
        });*/

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
            ViewPriceCalendar.setPriceData();
        });
        // clear price info btn
        $('#clearPriceInfoBtn').on('click', function(event) {
            ViewPriceCalendar.clearAllPriceData();
        });
        // save price info btn
        $('#savePriceBtn').on('click', function(event) {
            ViewPriceCalendar.saveTicketPriceCalendar();
        });
        $('#cancelPrice').on('click', function(event) {
            parent.$("#editPanel_1").dialog("close");
        });
    },
    
    initPriceCalendar: function() {
        $('#startDate').val(moment().add(1, 'days').format('YYYY-MM-DD'));
        $('#endDate').val(moment().add(6, 'months').format('YYYY-MM-DD'));
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();

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
            url: '/yhy/yhySailboatPrice/getTicketDatePrice.jhtml',
            type: 'post',
            dataType: 'json',
            data: {
                ticketPriceId: $('#ticketPriceId').val(),
                startDate: startDate,
                endDate: endDate
            },
            success: function(result) {
                if (result.success) {
                    // valuation models
                    ViewPriceCalendar.valuationInfo['valuationModels'] = result.valuationModels;
                    ViewPriceCalendar.valuationInfo['valuationValue'] = result.valuationValue;
                    if (result.valuationModels !== "lowPriceModel") {
                        $('#costPrice').prop("readonly", true);
                        $('#valuationModels').val(ViewPriceCalendar.valuationModels[result.valuationModels] + "(" + result.valuationValue +")");
                    } else {
                        $('#costPrice').prop("readonly", false);
                        $('#valuationModels').val(ViewPriceCalendar.valuationModels[result.valuationModels]);
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
                            parent.$("#editPanel_1").dialog("close");
                        }
                    });
                }
            },
            error: function() {
                $.messager.show({
                    msg: "获取价格数据错误, 请稍后重试!",
                    type: 'error',
                    afterClosed: function() {
                        parent.$("#editPanel_1").dialog("close");
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
        if (!priPrice.match(priceReg)) {
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
        var valuationModels =  ViewPriceCalendar.valuationInfo['valuationModels'];
        var valuationValue = Number(ViewPriceCalendar.valuationInfo['valuationValue']);
        priPrice = Number(priPrice);
        if (valuationModels === "commissionModel") {
            $('#price').val(priPrice - priPrice * valuationValue / 100);
        } else if(valuationModels === "fixedModel") {
            if (valuationValue > priPrice) {
                $.messager.show({
                    msg: "销售价不能低于" + valuationValue + "元",
                    type: 'error'
                });
                return;
            } else {
                $('#costPrice').val(priPrice - valuationValue);
            }
        } else if (valuationModels === "lowPriceModel") {
            if (!price.match(priceReg)) {
                $.messager.show({
                    msg: "结算价填写错误!",
                    type: "error"
                });
                return;
            }
            if (price > priPrice) {
                $.messager.show({
                    msg: "结算价不能高于销售价",
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
        ViewPriceCalendar.setEventSource(startDateStr, endDateStr, weekStr, priPrice, price, inventory);
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
                            data.push(ViewPriceCalendar.buildPriPrice(date, member));
                        }
                        if (cost) {
                            data.push(ViewPriceCalendar.buildPrice(date, cost));
                        }
                        if (inventory){
                            data.push(ViewPriceCalendar.buildInventory(date, inventory));
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
        //formData['productId'] = $('#productId').val();
        formData['ticketPriceId'] = $('#ticketPriceId').val();
        $.ajax({
            url: '/ticket/ticket/saveTicketDatePrice.jhtml',
            type: 'post',
            dataType: 'json',
            data: formData,
            success: function(result) {
                if (result.success) {
                    $.messager.show({
                        msg: "价格信息保存成功!",
                        type: 'warn',
                        afterClosed: function() {
                            parent.$("#editPanel_1").dialog("close");
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
    }
};

$(function() {
    ViewPriceCalendar.init();
})
