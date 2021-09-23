/**
 * Created by dy on 2017/2/21.
 */
var Tourist = {
    init: function() {
        YhyUser.checkLogin(function(result) {
            if (result && result.success) {
                Tourist.initComp();
                Tourist.initEvt();
            } else {
                YhyUser.goLogin();
            }
        });

    },
    initComp: function () {
        $('#birthDateSel').datetimepicker({
            closeOnDateSelect: true,
            scrollInput: false,
            todayButton: false,
            timepicker: false,
            format: "YYYY-MM-DD",
            formatDate: "YYYY-MM-DD",
            maxDate: 0
        });
    },
    initEvt: function() {
        // tourist item sel
        $('span.index').on('click', function (event) {
            event.stopPropagation();
            var state = $(this).hasClass('check');
            if (state == false) {
                $(this).addClass('check');
            } else {
                $(this).removeClass('check');
            }
        });

        // sex sel
        $('#sexSel span').on('click', function (event) {
            event.stopPropagation();
            $(this).addClass('checksex').siblings().removeClass('checksex');
            $('#tourist_form').find('input[name = "tourist.gender"]').val($(this).attr('data-sex-value'));
        });
        // people type sel
        $('#peopleTypeSel span').on('click', function (event) {
            event.stopPropagation();
            $(this).addClass('checksex').siblings().removeClass('checksex');
            $('#tourist_form').find('input[name = "tourist.peopleType"]').val($(this).attr('data-peopleType-value'));
        });
        // id type sel
        $('#idTypeSel span').on('click', function (event) {
            event.stopPropagation();
            $(this).addClass('checksex').siblings().removeClass('checksex');
            $('#tourist_form').find('input[name = "tourist.idType"]').val($(this).attr('data-idType-value'));
        });

        // cancel
        $('#cancelTBTtn').on('click', function(event) {
            event.stopPropagation();
            Popup.closeDialog();
            Tourist.clearTouristInfoForm();
        });
        // close
        $('#psword_close').on('click', function(event) {
            event.stopPropagation();
            Popup.closeDialog();
            Tourist.clearTouristInfoForm();
        });
    },
    clearTouristInfoForm: function() {
        $.form.reset({formId: '#tourist_form'});
        $('#sexSel span').removeClass('checksex');
        $('#sexSel span').first().addClass('checksex');
        $("input[name='tourist.gender']").val("male");
        $('#idTypeSel span').removeClass('checksex');
        $('#idTypeSel span').first().addClass('checksex');
        $("input[name='tourist.idType']").val("IDCARD");
        $('#peopleTypeSel span').removeClass('checksex');
        $('#peopleTypeSel span').first().addClass('checksex');
        $("input[name='tourist.peopleType']").val("ADULT");
        $('#save_msg').html(null);
    },
    doCheckTouristInfoForm: function() {
        var name = $('#tourist_form').find('input[name = "tourist.name"]').val();
        var mobile = $('#tourist_form').find('input[name = "tourist.tel"]').val();
        var email = $('#tourist_form').find('input[name = "tourist.email"]').val();
        var idNumber = $('#tourist_form').find('input[name = "tourist.idNumber"]').val();
        if (!name.match(Reg.cnNameReg)) {
            $('#save_msg').html(null).html("姓名格式不正确");
            return false;
        }
        if (!idNumber || idNumber == "") {
            $('#save_msg').html(null).html("证件号格式不正确");
            return false;
        }
        if (!mobile.match(Reg.telephoneReg)) {
            $('#save_msg').html(null).html("手机号格式不正确");
            return false;
        }
        if (!email.match(Reg.emailReg)) {
            $('#save_msg').html(null).html("邮箱格式不正确");
            return false;
        }
        return true;
    },
    submitLvji: function () {
        YhyUser.checkLogin(function (result) {
            if (result.success) {
                submit();
            } else {
                YhyUser.goLogin(submit);
            }
        });

        function submit() {

            var contactName = $("#userName").val();
            var contactTel = $("#mobile").val();
            if (contactName == null || contactName == "") {
                $.message.alert({
                    title: "提示",
                    info: "请填写联系人姓名"
                });
                return;
            }
            if (!contactName.match(Reg.nameReg) || contactName.length > 10) {
                $.message.alert({
                    title: "提示",
                    info: "联系人姓名有误"
                });
                return;
            }

            if (contactTel == null || contactTel == "") {
                $.message.alert({
                    title: "提示",
                    info: "请填写联系人手机"
                });
                return;
            }
            if (!contactTel.match(Reg.telephoneReg)) {
                $.message.alert({
                    title: "提示",
                    info: "联系人手机有误"
                });
                return;
            }
            var jsonObj = {
                name: $("#userName").val(),
                mobile: $("#mobile").val(),
                productId : $("#productId").val()
            };
            saveOrder();
            function saveOrder() {

                $.post("/yhypc/ferry/ljfillOk.jhtml", {
                    contactJson: JSON.stringify(jsonObj)
                }, function (data) {
                    if (data.success) {
                        location.href = "/yhypc/ferry/orderPay.jhtml?ljOrderId=" + data.orderId;
                    }
                });
            }
        }
    }
    };

$(function(){
    Tourist.init();
});

