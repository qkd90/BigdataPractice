$(document).ready(function(){
    var countTimer = null;
	// 按钮事件
	$('.nb_p_prevbtn').click(function() {
		btnEvent(this);
	});
    // 定制类型单选事件
    $('#J_NBType li.flex_item').click(function() {
        $(this).siblings('li').removeClass('nb_form_checked');
        $(this).addClass('nb_form_checked');
        // 设置出游人数提示信息
        var customType = $(this).attr('data-customType');
        if (customType == 'company') {
            $('.J_TwoPage .J_MoreTen').css('display', 'block');
            $('.J_TwoPage .J_MTten').css('display', 'none');
        } else {
            $('.J_TwoPage .J_MoreTen').css('display', 'none');
            $('.J_TwoPage .J_MTten').css('display', 'block');
        }
        // 设置成人儿童人数
        if (customType == 'company') {
            $('#J_Adult').val('10');
        } else if (customType == 'home') {
            $('#J_Adult').val('3');
        } else if (customType == 'personal') {
            $('#J_Adult').val('1');
        }
        statusOneNext();
        statusTwoNext();
    });
    // 出发地事件
    $('#J_StartCityId').change( function() {
        statusOneNext();
    });
    // 目的地
    $('#J_Destion').change( function() {
        // 控制样式换行，防止地城市面板超出部分被遮挡
        var destions = $('.J_GoWhere');
        if (destions.length >= 3) {
            $('.J_OnePage .J_Destions').css('display', 'block');
        }
        statusOneNext();
    });
    // 目的地关闭事件
    $(".J_Destions").delegate("a", "click", function () {
        var destions = $('.J_GoWhere');
        if (destions.length >= 5) {
            $('.J_OnePage .J_PTips').slideUp();
        }
        // 控制样式换行，防止地城市面板超出部分被遮挡
        if (destions.length <= 3) {
            $('.J_OnePage .J_Destions').css('display', 'inline');
        }
        $(this).parent().remove();
        statusOneNext();
    });
    // 天数加减事件
    $('#J_NBDays .nb_form_minus').click(function() {
        var day = $('#J_NBDays .J_Number').val();
        day = handleInt(day, 1, null);
        if (day >= 20) {
            $('.J_OnePage .J_PTips').slideUp();   // 提示信息
        }
        if (day > 1) {
            $('#J_NBDays .J_Number').val(day - 1);
        } else {
            $('#J_NBDays .J_Number').val(1);
        }
        statusOneNext();
    });
    $('#J_NBDays .nb_form_plus').click(function() {
        var day = $('#J_NBDays .J_Number').val();
        day = handleInt(day, 1, null);
        if (day < 20) {
            $('#J_NBDays .J_Number').val(day + 1);
        } else {
            $('.J_OnePage .J_PTips').slideDown();
            $('.J_OnePage .J_PTips .nb_page_tips').html('不要出去玩太久噢，20天就好了~');
        }
        statusOneNext();
    });
    // 天数校验
    $('#J_NBDays .J_Number').keyup(function() {
        var day = $(this).val();
        day = handleInt(day, 1, null);
        if (day > 20) {
            $(this).val(20);
            $('.J_OnePage .J_PTips').slideDown();
            $('.J_OnePage .J_PTips .nb_page_tips').html('不要出去玩太久噢，20天就好了~');
        } else {
            $(this).val(day);
        }
        statusOneNext();
    });
    // 可根据事件情况调整复选框
    $('.J_OnePage .J_Adjustment').click(function() {
        $(this).children('i').toggleClass('nb_icon_select');
    });

    // 行程安排、人均预算单选事件
    $('#J_NBTwoType li.flex_item,#J_NBTwoMethod li.flex_item').click(function() {
        $(this).siblings('li').removeClass('nb_form_checked');
        $(this).addClass('nb_form_checked');
        statusTwoNext();
    });
    // 成人数加减事件
    $('#J_NBTwoAdult .nb_form_minus').click(function() {
        // 取定制类型以限制最小人数：公司定制最小为10
        var minAdult = 1;
        var customType = null;
        var customTypeChked = $('#J_NBType .nb_form_checked');
        if (customTypeChked && customTypeChked.length > 0) {
            customType = customTypeChked.attr('data-customType');
            if (customType == 'company') {
                minAdult = 10;
            }
        }
        var adult = $('#J_NBTwoAdult .J_Number').val();
        adult = handleInt(adult, minAdult, null);
        if (adult > minAdult) {
            $('#J_NBTwoAdult .J_Number').val(adult - 1);
        } else {
            $('#J_NBTwoAdult .J_Number').val(minAdult);
        }
        statusTwoNext();
    });
    $('#J_NBTwoAdult .nb_form_plus').click(function() {
        // 取定制类型以限制最小人数：公司定制最小为10
        var minAdult = 1;
        var customType = null;
        var customTypeChked = $('#J_NBType .nb_form_checked');
        if (customTypeChked && customTypeChked.length > 0) {
            customType = customTypeChked.attr('data-customType');
            if (customType == 'company') {
                minAdult = 10;
            }
        }
        var adult = $('#J_NBTwoAdult .J_Number').val();
        adult = handleInt(adult, minAdult, null);
        $('#J_NBTwoAdult .J_Number').val(adult + 1);
        statusTwoNext();
    });
    // 成人数校验
    $('#J_NBTwoAdult .J_Number').keyup(function() {
        // 取定制类型以限制最小人数：公司定制最小为10
        var minAdult = 1;
        var customType = null;
        var customTypeChked = $('#J_NBType .nb_form_checked');
        if (customTypeChked && customTypeChked.length > 0) {
            customType = customTypeChked.attr('data-customType');
            if (customType == 'company') {
                minAdult = 10;
            }
        }
        var adult = $('#J_NBTwoAdult .J_Number').val();
        adult = handleInt(adult, minAdult, null);
        $(this).val(adult);
        statusOneNext();
    });
    // 儿童数加减事件
    $('#J_NBTwoChild .nb_form_minus').click(function() {
        var minChild = 0;
        var child = $('#J_NBTwoChild .J_Number').val();
        child = handleInt(child, minChild, null);
        if (child > minChild) {
            $('#J_NBTwoChild .J_Number').val(child - 1);
        } else {
            $('#J_NBTwoChild .J_Number').val(minChild);
        }
        statusTwoNext();
    });
    $('#J_NBTwoChild .nb_form_plus').click(function() {
        var minChild = 0;
        var child = $('#J_NBTwoChild .J_Number').val();
        child = handleInt(child, minChild, null);
        $('#J_NBTwoChild .J_Number').val(child + 1);
        statusTwoNext();
    });
    // 儿童数失去焦点是校验
    $('#J_NBTwoChild .J_Number').blur(function() {
        var minChild = 0;
        var child = $('#J_NBTwoChild .J_Number').val();
        child = handleInt(child, minChild, null);
        $(this).val(child);
        statusOneNext();
    });

    // 姓名
    $('#J_Name').keyup( function() {
        statusFiveNext();
    });
    // 手机
    $('#J_Phone').keyup( function() {
        var phone = $(this).val();
        if (checkPhone(phone)) {    // 设置获取按钮可用
            $('.J_FvGetSms').removeClass('nb_rem_disabled');    // 获取按钮可用
            $('.J_FvGetSms').removeAttr('disabled');
            $('.J_FivePage .J_PTips').slideUp();   // 提示信息
        } else {  // 设置短信验证码框、获取按钮、提示发送秒数、提示信息
            $('.J_FvSms').attr('disabled', 'disabled'); // 短信验证码框
            $('.J_FvSms').val('');
            $('.J_FvGetSms').addClass('nb_rem_disabled');   // 获取按钮
            $('.J_FvGetSms').attr('disabled', 'disabled');
            $('.J_FvTimeP').addClass('nb_hide');    // 计数器
            if (countTimer != null) {
                window.clearInterval(countTimer);
                countTimer = null;
            }
            $('.J_FivePage .J_PTips').slideDown();   // 提示信息
            $('.J_FivePage .J_PTips .nb_page_tips').html('请输入正确的手机号');
        }
        statusFiveNext();
    });
    // 手机验证码获取
    $('.J_FvGetSms').click(function() {
        var phone = $('#J_Phone').val();
       if (checkPhone(phone)) {
           //  发送验证码
           $.get("/lvxbang/customRequire/sendSms.jhtml", {phone: phone}, function (result) {
               if (result && result.success) {
                   // 设置按钮状态
                   $('.J_FvSms').removeAttr('disabled'); // 短信验证码框
                   $('.J_FvTime').text(60);   // 计数器
                   $('.J_FvTimeP').removeClass('nb_hide');
                   $('.J_FvGetSms').addClass('nb_rem_disabled');   // 获取按钮
                   $('.J_FvGetSms').attr('disabled', 'disabled');
                   countTimer = window.setInterval('countDown();', 1000);
               } else {
                   if (result && result.errMsg) {
                       $('.J_FivePage .J_PTips .nb_page_tips').html(errMsg);
                   } else {
                       $('.J_FivePage .J_PTips .nb_page_tips').html('操作失败');
                   }
               }
           });
       } else {
           $('.J_FivePage .J_PTips').slideDown();
           $('.J_FivePage .J_PTips .nb_page_tips').html('请输入正确的手机号');
       }
    });
    // 手机验证码
    $('.J_FvSms').keyup( function() {
        var code = $(this).val();
        if (checkCode(code)) {
            $('.J_FivePage .J_PTips').slideUp();   // 提示信息
        } else {
            $('.J_FivePage .J_PTips').slideDown();
            $('.J_FivePage .J_PTips .nb_page_tips').html('请输入正确的6位数字验证码');
        }
        statusFiveNext();
    });
    // 邮箱
    $('#J_Email').keyup( function() {
        var email = $(this).val();
        if (!email || checkEmail(email)) {
            $('.J_FivePage .J_PTips').slideUp();   // 提示信息
        } else {
            $('.J_FivePage .J_PTips').slideDown();
            $('.J_FivePage .J_PTips .nb_page_tips').html('请输入您的常用邮箱');
        }
        statusFiveNext();
    });

    // 根据定制类型，初始成人儿童人数
    var customTypeChecked = $('#J_NBType li.nb_form_checked');
    if (customTypeChecked) {
        // 设置出游人数提示信息
        var customType = customTypeChecked.attr('data-customType');
        if (customType == 'company') {
            $('.J_TwoPage .J_MoreTen').css('display', 'block');
            $('.J_TwoPage .J_MTten').css('display', 'none');
        } else {
            $('.J_TwoPage .J_MoreTen').css('display', 'none');
            $('.J_TwoPage .J_MTten').css('display', 'block');
        }
        // 设置成人儿童人数
        if (customType == 'company') {
            $('#J_Adult').val('10');
        } else if (customType == 'home') {
            $('#J_Adult').val('3');
        } else if (customType == 'personal') {
            $('#J_Adult').val('1');
        }
    }
    // 默认状态设置
    statusOneNext();
    statusTwoNext();
});

// 第一步按钮状态及值设置 J_BtnOneNext
function statusOneNext() {
    // 显示面板
    var $NBMenuList = $('#J_NBMenuList').children('div');
    // 定制类型
    var customType = null;
    var customTypeChked = $('#J_NBType .nb_form_checked');
    if (customTypeChked && customTypeChked.length > 0) {
        customType = customTypeChked.attr('data-customType');
        var customTypeName = customTypeChked.children('a').text();
        $NBMenuList.eq(0).find('.nb_menu_item_con').html('<div class="flex_item nb_menu_item_res">' + customTypeName + '</div>');
    } else {
        $NBMenuList.eq(0).find('.nb_menu_item_con').html('');
    }
    // 出发地
    var startCityId = $('#J_StartCityId').attr('data-areaId');
    if (startCityId) {
        var startCityName = $('#J_StartCityId').val();
        $NBMenuList.eq(1).find('.nb_menu_item_con').html('<div class="flex_item nb_menu_item_res">' + startCityName + '</div>');
    } else {
        $NBMenuList.eq(1).find('.nb_menu_item_con').html('');
    }
    // 目的地
    var destions = [];
    var destionItems = $('.J_Destions .J_GoWhere');
    $NBMenuList.eq(2).find('.nb_menu_item_con').html('');
    if (destionItems && destionItems.length > 0) {
        for (var i = 0; i < destionItems.length; i++) {
            var areaName = $(destionItems[i]).children('span').text();
            destions.push({areaId:$(destionItems[i]).attr('data-areaId'),areaName:areaName});
            $NBMenuList.eq(2).find('.nb_menu_item_con').append('<div class="flex_item nb_menu_item_res">' + areaName + '</div>');
        }
    }
    // 天数
    var day = $('#J_Day').val();
    if (day) {
        $NBMenuList.eq(3).find('.nb_menu_item_con').html('<div class="flex_item nb_menu_item_res">' + day + '天</div>');
    } else {
        $NBMenuList.eq(3).find('.nb_menu_item_con').html('');
    }
    // 出发日期
    var startDate = $('#J_StartDate').val();
    if (startDate) {
        $NBMenuList.eq(4).find('.nb_menu_item_con').html('<div class="flex_item nb_menu_item_res">' + startDate + '</div>');
    } else {
        $NBMenuList.eq(4).find('.nb_menu_item_con').html('');
    }
    // 设置按钮状态
    if (customType && startCityId && destions.length > 0 && day && startDate) {
        $('#J_BtnOneNext').removeClass('nb_unable');
        $('#J_BtnOneNext').removeAttr('disabled');
    } else {
        $('#J_BtnOneNext').addClass('nb_unable');
        $('#J_BtnOneNext').attr('disabled', 'disabled');
    }
}

// 第二步按钮状态及值设置 J_BtnTwoNext
function statusTwoNext() {
    // 显示面板
    var $NBMenuList = $('#J_NBMenuList').children('div');
    // 行程安排
    var arrange = null;
    var arrangeChked = $('#J_NBTwoType .nb_form_checked');
    if (arrangeChked && arrangeChked.length > 0) {
        arrange = arrangeChked.attr('data-arrange');
        var arrangeName = arrangeChked.children('a').text();
        $NBMenuList.eq(5).find('.nb_menu_item_con').html('<div class="flex_item nb_menu_item_res">' + arrangeName + '</div>');
    } else {
        $NBMenuList.eq(5).find('.nb_menu_item_con').html('');
    }
    // 出游人数
    var adult = $('#J_Adult').val();
    var child = $('#J_Child').val();
    var personHtml = '';
    if (adult) {
        personHtml = adult + '成人';
        if (child && child > 0) {
            personHtml = personHtml + child + '儿童';
        }
    }
    if (personHtml) {
        $NBMenuList.eq(6).find('.nb_menu_item_con').html('<div class="flex_item nb_menu_item_res">' + personHtml + '</div>');
    } else {
        $NBMenuList.eq(6).find('.nb_menu_item_con').html('');
    }
    // 人均预算
    var budget = null;
    var budgetChked = $('#J_NBTwoMethod .nb_form_checked');
    if (budgetChked && budgetChked.length > 0) {
        budget = budgetChked.children('a').text();
        $NBMenuList.eq(7).find('.nb_menu_item_con').html('<div class="flex_item nb_menu_item_res">' + budget + '</div>');
    } else {
        $NBMenuList.eq(7).find('.nb_menu_item_con').html('');
    }
    // 设置按钮状态
    if (arrange && adult && budget) {
        $('#J_BtnTwoNext').removeClass('nb_unable');
        $('#J_BtnTwoNext').removeAttr('disabled');
    } else {
        $('#J_BtnTwoNext').addClass('nb_unable');
        $('#J_BtnTwoNext').attr('disabled', 'disabled');
    }
}

// 第五步按钮状态及值设置 J_BtnFiveNext
function statusFiveNext() {
    // 显示面板
    var $NBMenuList = $('#J_NBMenuList').children('div');
    // 姓名
    var name = $('#J_Name').val();
    if (name) {
        $NBMenuList.eq(8).find('.nb_menu_item_con').html('<div class="flex_item nb_menu_item_res">' + name + '</div>');
    } else {
        $NBMenuList.eq(8).find('.nb_menu_item_con').html('');
    }
    // 手机
    var phone = $('#J_Phone').val();
    if (phone) {
        $NBMenuList.eq(9).find('.nb_menu_item_con').html('<div class="flex_item nb_menu_item_res">' + phone + '</div>');
    } else {
        $NBMenuList.eq(9).find('.nb_menu_item_con').html('');
    }
    // 手机验证码
    var code = $('.J_FvSms').val();
    // 邮箱
    var email = $('#J_Email').val();
    if (email) {
        $NBMenuList.eq(10).find('.nb_menu_item_con').html('<div class="flex_item nb_menu_item_res">' + email + '</div>');
    } else {
        $NBMenuList.eq(10).find('.nb_menu_item_con').html('');
    }
    // 设置按钮状态
    if (name && checkPhone(phone) && checkCode(code) && (!email || checkEmail(email))) {
        $('#J_BtnFiveNext').removeClass('nb_unable');
        $('#J_BtnFiveNext').removeAttr('disabled');
    } else {
        $('#J_BtnFiveNext').addClass('nb_unable');
        $('#J_BtnFiveNext').attr('disabled', 'disabled');
    }
}

// 下一步、上一步按钮
function btnEvent(thiz) {
    var page = $(thiz).attr('data-page');
    if (page == 'J_EndPage') {  // 最后一个按钮，则提交表单
        // 获取提交数据
        var data = getFormData();
        if (data) {
            $('.J_FivePage .J_PTips').slideUp();   // 提示信息
        } else {
            $('.J_FivePage .J_PTips').slideDown();
            $('.J_FivePage .J_PTips .nb_page_tips').html('存在未填写或不合法数据，请检查');
            return;
        }

        // 提交保存
        $('#J_BtnFiveNext').addClass('nb_unable');
        $('#J_BtnFiveNext').attr('disabled', 'disabled');
        $('#J_BtnFiveNext').val('提交中...');
        $.post("/lvxbang/customRequire/saveInfo.jhtml", data, function (result) {
            if (result && result.success) {
                location.href = '/lvxbang/user/plan.jhtml?save=ok';
            } else {
                $('.J_FivePage .J_PTips').slideDown();
                if (result && result.errMsg) {
                    $('.J_FivePage .J_PTips .nb_page_tips').html(result.errMsg);
                } else {
                    $('.J_FivePage .J_PTips .nb_page_tips').html('操作失败');
                }
                $('#J_BtnFiveNext').removeClass('nb_unable');
                $('#J_BtnFiveNext').removeAttr('disabled');
                $('#J_BtnFiveNext').val('提 交');
            }
        });
    } else if (page) {
        // 面板页面切换
        $('.' + page).siblings('.nb_page').hide();  // 同级其他元素隐藏
        $('.' + page).show();
        // 标题部分
        $('#' + page + '_Title').siblings('.nb_step').children('i.nb_step_arrow').addClass('nb_hide');
        $('#' + page + '_Title').children('i.nb_step_arrow').removeClass('nb_hide');
    }
}

// 检查是否是整数，并返回整数值，非法值返回defMin
function handleInt(val, defMin, defMax) {
    var b =  /^\d+$/.test(val);
    if (b) {
        var v = parseInt(val);
        if (defMin && v <= defMin) {
            return defMin;
        }
        if (defMax && v >= defMax) {
            return defMax;
        }
        return v;
    }
    if (defMin) {
        return defMin;
    }
    return 0;
}

// 手机校验
function checkPhone(phone) {
    return /^1\d{10}$/.test(phone);
}

// 手机验证码校验
function checkCode(code) {
    return /^\d{6}$/.test(code);
}

// 倒计时
function countDown() {
    var time = $('.J_FvTime').text();
    if (time <= 0) {
        $('.J_FvTimeP').addClass('nb_hide');    // 提示发送秒数
        $('.J_FvGetSms').removeClass('nb_rem_disabled');    // 获取按钮
        $('.J_FvGetSms').removeAttr('disabled');
        return false;
    }
    time = parseInt(time) - 1;
    $('.J_FvTime').text(time);
}

// 邮箱校验
function checkEmail(email) {
    return /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(email);
}

// 获取提交数据
function getFormData() {
    var data = {};
    // 定制类型
    var customType = null;
    var customTypeChked = $('#J_NBType .nb_form_checked');
    if (customTypeChked && customTypeChked.length > 0) {
        customType = customTypeChked.attr('data-customType');
        data.customType = customType;
    } else {
        return null;
    }
    // 出发地
    var startCityId = $('#J_StartCityId').attr('data-areaId');
    if (startCityId) {
        data.startCityId = startCityId;
    } else {
        return null;
    }
    // 目的地
    var destions = [];
    var destionItems = $('.J_Destions .J_GoWhere');
    if (destionItems && destionItems.length > 0) {
        for (var i = 0; i < destionItems.length; i++) {
            var areaName = $(destionItems[i]).children('span').text();
            destions.push($(destionItems[i]).attr('data-areaId'));
        }
        data.destions = destions.join(',');
    } else {
        return null;
    }
    // 天数
    var day = $('#J_Day').val();
    if (day) {
        data.day = day;
    } else {
        return null;
    }
    // 出发日期
    var startDate = $('#J_StartDate').val();
    if (startDate) {
        data.startDate = startDate;
    } else {
        return null;
    }
    // 根据实际情况调整
    var adjustFlag = $('.J_OnePage .J_Adjustment i.nb_icon_select');
    if (adjustFlag) {
        data.adjustFlag = true;
    } else {
        data.adjustFlag = false;
    }
    // 行程安排
    var arrange = null;
    var arrangeChked = $('#J_NBTwoType .nb_form_checked');
    if (arrangeChked && arrangeChked.length > 0) {
        arrange = arrangeChked.attr('data-arrange');
        data.arrange = arrange;
    } else {
        return null;
    }
    // 出游人数
    var adult = $('#J_Adult').val();
    var child = $('#J_Child').val();
    var personHtml = '';
    if (adult) {
        data.adult = adult;
        if (child && child > 0) {
            data.child = child;
        }
    } else {
        return null;
    }
    // 人均预算
    var budget = null;
    var budgetChked = $('#J_NBTwoMethod .nb_form_checked');
    if (budgetChked && budgetChked.length > 0) {
        data.minPrice = budgetChked.attr('data-minPrice');
        data.maxPrice = budgetChked.attr('data-maxPrice');
    } else {
        return null;
    }
    // 姓名
    var name = $('#J_Name').val();
    if (name) {
        data.contactor = name;
    } else {
        return null;
    }
    // 手机
    var phone = $('#J_Phone').val();
    if (phone) {
        data.contactPhone = phone;
    } else {
        return null;
    }
    // 手机验证码
    var code = $('.J_FvSms').val();
    if (code) {
        data.code = code;
    } else {
        return null;
    }
    // 邮箱
    var email = $('#J_Email').val();
    if (email) {
        if (checkEmail(email)) {
            data.contactEmail = email;
        } else {
            return null;
        }
    }
    return data;
}

// 添加目的地
function addDestion(areaId, areaName) {
    var destions = $('.J_GoWhere');
    if (destions.length >= 5) {
        $('.J_OnePage .J_PTips').slideDown();
        $('.J_OnePage .J_PTips .nb_page_tips').html('目前最多只能选择5个目的地哦！');
        return;
    }
    var isExists = false;
    if (destions && destions.length > 0) {
        for (var i = 0; i < destions.length; i++) {
            var hasDestion = $(destions[i]).attr('data-areaId');
            if (hasDestion == areaId) {
                isExists = true;
                break;
            }
        }
    }
    if (!isExists) {
        var html = '<div class="flex_item nb_form_btn nb_form_selected J_GoWhere" data-areaId="' + areaId
            + '"><span>' + areaName + '</span><a class="" href="javascript:;"><i class="nb_icon J_FirDel"></i></a></div>';
        $('.J_Destions').append(html);
    }
}
