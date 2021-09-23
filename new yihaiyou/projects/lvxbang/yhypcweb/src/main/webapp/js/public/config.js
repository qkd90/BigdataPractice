/**
 * Created by zzl on 2016/12/29.
 */
var loadingItem = "<div class='progress-shadow'><span class='loading-text'>请稍候...</span></div>";
$(function() {
    // datetimepicker config
    $.datetimepicker.setLocale("zh");
    $.datetimepicker.setDateFormatter({
        parseDate: function(date, format) {
            var d = moment(date, format);
            return d.isValid() ? d.toDate() : false;
        },
        formatDate: function(date, format) {
            return moment(date).format(format);
        }
    });
    // ajax config
    $.ajaxSetup({type: 'post', dataType: 'json'});
    // ajax loading config
    var $loading = $(loadingItem).clone();
    $(document).ajaxSend(function(event, xhr, options) {
        if (options && options.progress === true) {
            if (options.loadingText && options.loadingText != "") {
                //var width = options.loadingText.length * 30;
                //$loading.css('width', width);
                $loading.find('span.loading-text').html(null).html(options.loadingText);
            }
            $('body').append($loading);
        }
    });
    $(document).ajaxComplete(function (event, xhr, options) {
        if (options && options.progress) {
            $loading.remove();
        }
    });
});

var Reg = {
    idCardReg: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
    junNumberReg: /南字第(\d{8})号|北字第(\d{8})号|沈字第(\d{8})号|兰字第(\d{8})号|成字第(\d{8})号|济字第(\d{8})号|广字第(\d{8})号|海字第(\d{8})号|空字第(\d{8})号|参字第(\d{8})号|政字第(\d{8})号|后字第(\d{8})号|装字第(\d{8})号/,
    telephoneReg: /^1[34578]\d{9}$/,
    nameReg: /^[\u4E00-\u9FA5]+$/,
    cnNameReg: /^\s*[\u4e00-\u9fa5]{1,}[\u4e00-\u9fa5.·]{0,2}[\u4e00-\u9fa5]{1,}\s*$/,
    nickNameReg: /^[\u4E00-\u9FA5a-zA-Z0-9_\s]{4,16}$/,
    emailReg: /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/,
    bankNoReg: /^\d{15}|\d{16}|\d{19}$/,
    passwordReg: /^[0-9a-zA-Z]{6,}$/
};