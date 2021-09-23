var editStep2 = {
    limitNum: 3000,	// 字数限制仅文本
    maxLimitNum: 4000,	// 字数限制包含html标签
    quoteDescK: null,

    init: function () {
        editStep2.initComp();
        editStep2.initListener();
        editStep2.initStatus();
    },
    // 初始化控件
    initComp: function () {
        // 费用说明 说明
        KindEditor.ready(function (K) {
            quoteDescK = K.create('#quoteDescK', {
                resizeType: 1,
                allowPreviewEmoticons: false,
                filePostName: 'resource',
                items: [],
                readonlyMode: true

            });
        });

        var priceId = $('#priceId').val();
        var dateStart = $('#dateStart').val();
        var dateEnd = $('#dateEnd').val();
        // 日历
        $('#calendar').fullCalendar({
            theme: true,
            header: {
                left: 'prev',
                center: 'title',
                right: 'next'
            },
            defaultDate: $('#dateStart').val(),
            lang: 'zh-cn',
            buttonIcons: false, // show the prev/next text
            weekNumbers: false,
            fixedWeekCount: false,
            editable: false,
            eventLimit: true // allow "more" link when too many events
            //events: '/line/linetypepricedate/findTypePriceDate.jhtml?dateStart='+dateStart+'&linetypepriceId='+priceId+'&cIndex=1'
        });

        var url = '/line/linetypepricedate/findTypePriceDate.jhtml?dateStart=' + dateStart + '&linetypepriceId=' + priceId + '&cIndex=1';

        if (priceId) {
            $.messager.progress({
                title: '温馨提示',
                text: '数据处理中,请耐心等待...'
            });
            $.post(url, function (result) {
                $('#calendar').fullCalendar('addEventSource', result);
                $.messager.progress("close");
            });
        }

        /*
         // 日历
         $('#calendar2').fullCalendar({
         header: {
         left: '',
         center: 'title',
         right: ''
         },
         defaultDate: $('#dateEnd').val(),
         lang: 'zh-cn',
         buttonIcons: false, // show the prev/next text
         weekNumbers: false,
         fixedWeekCount: false,
         editable: true,
         //selectable: true,
         eventLimit: true, // allow "more" link when too many events
         events: '/line/linetypepricedate/findTypePriceDate.jhtml?dateEnd='+dateEnd+'&linetypepriceId='+priceId+'&cIndex=2'
         });
         */
    },
    // 初始化事件
    initListener: function () {
        // 选团周期
        $(':checkbox[name=weekday]').each(function (index, element) {
            $(this).change(function () {
                if (index === 0) {	// 如果是“天天发团”监听全选和取消全选
                    var checked = $(this).attr('checked');
                    if (checked) {
                        $(':checkbox[name=weekday]').attr('checked', 'checked');
                    } else {
                        $(':checkbox[name=weekday]').removeAttr('checked');
                    }
                } else {	// 否则，设置对应“天天发团”状态
                    var checked = $(this).attr('checked');
                    if (!checked) {
                        $(':checkbox[name=weekday]').first().removeAttr('checked');
                    }
                }
            });
        });
    },
    // 初始状态
    initStatus: function () {
        $(':checkbox[name=weekday]').attr('checked', 'checked');
        //var lineInfo = parent.window.getIfrData('step1');
        //$('#productId').val(lineInfo.productId);
    },
    // 添加 - 报价时间
    doAddPriceDate: function () {
        var discountPrice = $('#discountPrice').val();
        if (!discountPrice) {
            show_msg("请填写成人价信息");
            $('#discountPrice').focus();
            return;
        }
        var rebate = $('#rebate').val();
        if (!rebate) {
            show_msg("请填写成人价信息");
            $('#rebate').focus();
            return;
        }
        var childPrice = $('#childPrice').val();
        var childRebate = $('#childRebate').val();
        if (!childRebate) {	// 默认佣金0
            childRebate = 0;
        }
        //$('#calendar1').fullCalendar('removeEvents');
        var startDateStr = $('#dateStart').val();
        var endDateStr = $('#dateEnd').val();
        var weeks = $(':checked[name=weekday]');
        var weekStr = "";
        for (var i = 0; i < weeks.length; i++) {
            weekStr = weekStr + weeks[i].value + ",";
        }
        editStep2.setEventSource(startDateStr, endDateStr, weekStr, discountPrice, rebate, childPrice, childRebate);
    },
    // 清除所有报价
    doClearPriceDate: function () {
        $('#calendar').fullCalendar('removeEvents');
        //$('#calendar2').fullCalendar('removeEvents');
    },
    // 获取日历安排事项
    setEventSource: function (startDateStr, endDateStr, weekStr, discountPrice, rebate, childPrice, childRebate) {
        var startDate = LineUtil.stringToDate(startDateStr);
        var endDate = LineUtil.stringToDate(endDateStr);
        var startDateMon = startDate.getMonth();
        var endDateMon = endDate.getMonth();
        var offset = (endDateMon - startDateMon + 12) % 12 + 1;
        var date = startDate;
        var dateMon = startDateMon;
        for (var i = 1; i <= offset; i++) {
            var data = [];
            var tempId = [];
            while (date <= endDate) {
                var tempDateMon = date.getMonth();
                if (tempDateMon != dateMon) {	// 超过当前月，跳出循环后继续遍历
                    dateMon = tempDateMon;
                    break;
                } else {
                    var dateDay = date.getDay();	// 星期几
                    if (weekStr.indexOf(dateDay) > -1) {	// 包含在选中的星期内

                        if (discountPrice) {	// 成人价
                            var vid = "1" + date.getTime();
                            data.push({
                                id: vid,
                                discountPrice: discountPrice,
                                rebate: rebate,
                                title: '成人' + discountPrice + "(" + rebate + ")",
                                start: LineUtil.dateToString(date, 'yyyy-MM-dd')
                            });
                            tempId.push(vid);
                        }
                        if (childPrice) {	// 儿童价
                            var vid = "2" + date.getTime();
                            data.push({
                                id: vid,
                                childPrice: childPrice,
                                childRebate: childRebate,
                                title: '儿童' + childPrice + "(" + childRebate + ")",
                                start: LineUtil.dateToString(date, 'yyyy-MM-dd')
                            });
                            tempId.push(vid);
                        }
                    }
                    // 变量增加
                    var dateTime = date.getTime() + 24 * 60 * 60 * 1000;	// 往前+1天
                    date = new Date(dateTime);
                }
            }
            var filter = function (event) {
                return tempId.indexOf(event._id) > -1;
            };
            $('#calendar').fullCalendar('removeEvents', filter);
            $('#calendar').fullCalendar('addEventSource', data);
        }
    },
    // 设置线路报价时间
    setTypePriceDate: function () {
        var htmlStr = "";
        var data = [];
        var res1 = $('#calendar1').fullCalendar('clientEvents');
        for (var i = 0; i < res1.length; i++) {
            var m = res1[i].start;
            var obj = LineUtil.getByKey(data, 'start', m.format());
            if (obj) {	// 已经包含该元素
                if (res1[i].discountPrice) {
                    obj.discountPrice = res1[i].discountPrice;
                    obj.rebate = res1[i].rebate;
                }
                if (res1[i].childPrice) {
                    obj.childPrice = res1[i].childPrice;
                    obj.childRebate = res1[i].childRebate;
                }
            } else {
                obj = {};
                obj.start = m.format();
                if (res1[i].discountPrice) {
                    obj.discountPrice = res1[i].discountPrice;
                    obj.rebate = res1[i].rebate;
                }
                if (res1[i].childPrice) {
                    obj.childPrice = res1[i].childPrice;
                    obj.childRebate = res1[i].childRebate;
                }
                data.push(obj);
            }
        }
        //var res2 = $('#calendar2').fullCalendar('clientEvents');
        //for (var i = 0; i < res2.length; i++) {
        //	var m = res2[i].start;
        //	var obj = LineUtil.getByKey(data, 'start', m.format());
        //	if (obj) {	// 已经包含该元素
        //		if (res2[i].discountPrice) {
        //			obj.discountPrice = res2[i].discountPrice;
        //			obj.rebate = res2[i].rebate;
        //		}
        //		if (res2[i].childPrice) {
        //			obj.childPrice = res2[i].childPrice;
        //			obj.childRebate = res2[i].childRebate;
        //		}
        //	} else {
        //		obj = {};
        //		obj.start = m.format();
        //		if (res2[i].discountPrice) {
        //			obj.discountPrice = res2[i].discountPrice;
        //			obj.rebate = res2[i].rebate;
        //		}
        //		if (res2[i].childPrice) {
        //			obj.childPrice = res2[i].childPrice;
        //			obj.childRebate = res2[i].childRebate;
        //		}
        //		data.push(obj);
        //	}
        //}
        if (data.length <= 0) {
            return false;
        }
        // 设置隐藏域
        for (var i = 0; i < data.length; i++) {
            htmlStr = htmlStr + '<input name="typePriceDate[' + i + '].start" value="' + data[i].start + ' "type="hidden"/>';
            if (data[i].discountPrice) {
                htmlStr = htmlStr + '<input name="typePriceDate[' + i + '].discountPrice" value="' + data[i].discountPrice + '" type="hidden"/>';
                htmlStr = htmlStr + '<input name="typePriceDate[' + i + '].rebate" value="' + data[i].rebate + '" type="hidden"/>';
            }
            if (data[i].childPrice) {
                htmlStr = htmlStr + '<input name="typePriceDate[' + i + '].childPrice" value="' + data[i].childPrice + '" type="hidden"/>';
                htmlStr = htmlStr + '<input name="typePriceDate[' + i + '].childRebate" value="' + data[i].childRebate + '" type="hidden"/>';
            }
        }
        $('#typePriceDate').html(htmlStr);
    },
    // 下一步
    nextGuide: function () {
        // 保存表单
        $('#editForm').form('submit', {
            url: "/line/linetypeprice/saveLinePrice.jhtml",
            onSubmit: function () {
                // 费用说明 字段验证
                var quoteDesc = $('textarea[name="quoteDesc"]').val();
                if (!quoteDesc) {
                    show_msg("费用说明不能为空");
                    return false;
                } else if (quoteDesc.length > editStep2.maxLimitNum) {
                    show_msg("费用说明html标签过多，请简化");
                    return false;
                }
//				$('#quoteDesc').val(quoteDesc);

                var flag = editStep2.getDateSource();

                if (!flag) {
                    show_msg("请完善价格日历信息。");
                    return false;
                }

                var isValid = $(this).form('validate');
                if (isValid) {
                    //isValid = editStep2.setTypePriceDate();
                    if (!isValid) {
                        show_msg("请填写报价");
                        return isValid;
                    }
                    $.messager.progress({
                        title: '温馨提示',
                        text: '数据处理中,请耐心等待...'
                    });
                } else {
                    show_msg("请完善当前页面数据");
                }
                return isValid;
            },
            success: function (result) {
                $.messager.progress("close");
                var result = eval('(' + result + ')');
                if (result.success == true) {
                    $('#priceId').val(result.id);
                    var productId = $('#productId').val();
                    LineUtil.buildLine(productId);
                    var url = "/line/line/editStep21.jhtml?productId=" + productId + "#loctop";
                    window.location.href = url;
                } else {
                    show_msg("保存线路失败");
                }
            }
        });

    },

    getDateSource: function () {

        var data = [];
        var res1 = $('#calendar').fullCalendar('clientEvents');
        for (var i = 0; i < res1.length; i++) {
            var m = res1[i].start;
            var obj = LineUtil.getByKey(data, 'start', m.format());
            if (obj) {	// 已经包含该元素
                if (res1[i].discountPrice) {
                    obj.discountPrice = res1[i].discountPrice;
                    obj.rebate = res1[i].rebate;
                }
                if (res1[i].childPrice) {
                    obj.childPrice = res1[i].childPrice;
                    obj.childRebate = res1[i].childRebate;
                }
            } else {
                obj = {};
                obj.start = m.format();
                if (res1[i].discountPrice) {
                    obj.discountPrice = res1[i].discountPrice;
                    obj.rebate = res1[i].rebate;
                }
                if (res1[i].childPrice) {
                    obj.childPrice = res1[i].childPrice;
                    obj.childRebate = res1[i].childRebate;
                }
                data.push(obj);
            }
        }

        if (data.length > 0) {
            var dataJson = JSON.stringify(data);
            $("#dateSourceId").val(dataJson);
            return true;
        } else {
            show_msg("请先完善价格日历数据！");
            $("#dateSourceId").val("");
            return false;
        }
    },

    // 返回价格列表
    doBackList: function () {
        var productId = $('#productId').val();
        var url = "/line/line/editStep21.jhtml?productId=" + productId + "#loctop";
        window.location.href = url;
    }
};

// 返回本页面数据
function getIfrData() {
    var data = {};
    return data;
}

$(function () {
    editStep2.init();
});