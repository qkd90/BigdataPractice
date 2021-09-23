

$(function(){
    messageCount();
    //全站input的禁用历史
    $('input').attr('autocomplete', 'off');
    //location.reload();


    //头部监听
    //setInterval(function () {
    //    if (isNull($('#userMessage').html())) {
    //        $.ajax({
    //            url:"/lvxbang/user/checkedLogin.jhtml",
    //            type:"post",
    //            success:function(data){
    //                if(!data[0].success){
    //
    //                } else {
    //                        if (isNull($('#userMessage').html())) {
    //                            $('#logoutStatus').remove();
    //                            var re = '<div class="Haslogged fr" id="loginStatus">'+
    //                                '<a class="type  fl posiR" href="/lvxbang/message/system.jhtml" id="myMessage">我的消息</a>'+
    //                                '<a href="/lvxbang/user/index.jhtml" class="name fl" id="userMessage"'+
    //                                'value="'+data[0].userName+'">'+data[0].userName+' </a>'+
    //                                '<a href="/lvxbang/login/exitLogin.jhtml" class="fr but">退出</a></div>'
    //                            var res = template("tpl-has-user-item", data[0].userName);
    //                            $('#head_p').after(re);
    //                        }
    //
    //                    }
    //
    //            },
    //            error:function(){
    //                console.log("error");
    //            }
    //        });
    //
    //    }
    //}, 500);

});

//当前页面退出
//function loginOut(){
//
//    $.ajax({
//        url:"http://localhost:8080/lvxbang/login/exitLogin.jhtml",
//        type:"post",
//        success:function(data){
//
//            $('#loginStatus').remove();
//            var result = template("tpl-no-user-item",null);
//            $('#head_p').after(result);
//
//        },
//        error:function(){
//            alert("error");
//        }
//    });
//}

//获取未读消息数
function messageCount(){

    $.ajax({
        url:"/lvxbang/message/noReadMessageCount.jhtml",
        type:"post",
        success:function(data){
            var s = data;
            $('#myMessage-i').remove();
            if(s !="0"){
                $('#myMessage').append('<i class="posiA oval4" id="myMessage-i">'+s+'</i>');
            }
        },
        error:function(){
            window.console.log("error");
        }
    });

}

//窗口第一次加载的时候判断session与cookie是否相等
function checked_session_cookie(){
    $.ajax({
        url: "/lvxbang/user/checkedLogin.jhtml",
        type: "post",
        dataType:"json",
        success: function (data) {
            var logintime = JSON.parse(getCookie("logintime"));
            if (data[0].success) {

                 if(logintime && data[0].userName != logintime.userName){
                     logintime.userName = data[0].userName;
                     setCookie("logintime", JSON.stringify(logintime));
                     $('#logoutStatus').remove();
                     $('#loginStatus').remove();
                 }

            }else{
                delCookie("logintime");
            }

            setInterval(function () {
                var logintime = JSON.parse(getCookie("logintime"));
                //console.log(logintime.userName+"---"+$('#userMessage').attr("value"));
                if(logintime && logintime.userName != $('#userMessage').attr("value")){
                    $('#logoutStatus').remove();
                    $('#loginStatus').remove();
                    var href = $('#index_path').val();
                    var re = '<div class="Haslogged fr" id="loginStatus">'+
                        '<a class="type  fl posiR" href="' + href + '/lvxbang/message/system.jhtml" id="myMessage">消息</a>'+
                        '<a href="' + href + '/lvxbang/user/index.jhtml" class="name fl" id="userMessage"'+
                        'value="'+logintime.userName+'">个人中心：'+logintime.userName+' </a>'+
                        '<a href="javascript:exitDelete();" class="fr but exit">退出</a></div>'
                    var res = template("tpl-has-user-item", logintime.userName);
                    $('#head_p').after(re);
                    //setInterval(function () {
                    //    delCookie("logintime");
                    //},10000);
                }

            }, 500);

        },
        error: function () {
            window.console.log("error");
        }
    });
}
window.onload=function(){

    var a = "editPlanOrder.jhtml";
    var b = "orderHotel.jhtml";
    var c = "orderPlan.jhtml";
    var d = "orderReturnFlight.jhtml";
    var e = "orderSingleFlight.jhtml";
    var f = "orderTicket.jhtml";
    var g = "lxbPay/request.jhtml";
    var h = "lxbPay/wechatPay.jhtml";
    var i = "plan/booking.jhtml";
    var url = window.location.pathname;
    if(url.indexOf(a) >=0 || url.indexOf(b) >=0
        || url.indexOf(c) >=0|| url.indexOf(d) >=0||url.indexOf(e) >=0
           ||url.indexOf(f) >=0 || url.indexOf(g)>0 || url.indexOf(h)>=0
              || url.indexOf(i) >= 0){

        if($('#page_reload').val()==0){
            $('#page_reload').val('1')
        }else{
            location.reload();
        }

    }else {
        checked_session_cookie();
    }
};
