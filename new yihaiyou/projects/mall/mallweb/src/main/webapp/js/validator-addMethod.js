jQuery.validator.addMethod("phone", function (value, element) {
    var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
    return this.optional(element) || (tel.test(value));
}, $.validator.messages["phone"]);
jQuery.validator.addMethod("mobile", function (value, element) {
    var length = value.length;
    var mobile = /^((\(\d{2,3}\))|(\d{3}\-))?1[3|4|5|8][0-9]\d{4,8}$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, $.validator.messages["mobile"]);