$(window).ready(function () {
    orderStatus();
    orderType();
});

function orderStatus() {
    var state = $('.headbox li');
    state.click(function () {
        $(this).addClass('li_active');
        $(this).siblings().removeClass('li_active');
    })
}

function orderType() {
    var type = $('.mainbox_nav p span');
    type.on('click', function () {
        var hasClass = $(this).hasClass('s_active');
        if (hasClass == false) {
            $(this).addClass('s_active');
            $(this).siblings().removeClass('s_active');
        }
        //for (k = 0; k < $('.table_body .fir_ul').length; k++) {
        //    var con_id = $('.table_body .fir_ul').eq(k).attr('id');
        //    var ul = $('.table_body .fir_ul');
        //    console.info(con_id)
        //    if ($(this).index() == 1) {
        //        ul.show();
        //    }
        //    if (con_id == $(this).index() - 1) {
        //        ul.hide();
        //        ul.eq(k).show();
        //    }
        //}
    });
}