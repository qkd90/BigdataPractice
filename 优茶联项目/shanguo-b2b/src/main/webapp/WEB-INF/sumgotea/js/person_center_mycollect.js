/**
 * Created by Administrator on 2017/6/17.
 */
var myCollect = {
    init:function(){
        myCollect.editorFn();
        myCollect.chooseFn();
        myCollect.completeFn();
        myCollect.silderDelete();
    },
    /*点击编辑*/
    editorFn:function(){
        $("#editorBtn").bind("touchstart",function(){
            for(var i=0;i<$("#productList").find("li").length;i++){
                $("#productList").find(".label-check").eq(i).removeClass("checkbox-active");
                $("#productList").find("li").eq(i).attr("data-slide","false");
                $("#productList").find("li").eq(i).css("margin-left",0);
            }
            $(this).hide();
            $(this).siblings("#completeBtn").show();
            $("#productList").find("li").addClass("li-left-active");
            $("#operationBtn").slideDown("100");
            $("#productList").find("li").bind("touchstart",function(ev){});
        });
    },
    /*选择按钮事件*/
    chooseFn:function(){
        $(".label-check").bind("touchstart",function(){
            if($(this).hasClass("checkbox-active")){
                $(this).removeClass("checkbox-active")
            }else{
                $(this).addClass("checkbox-active");
            };
        });
    },
    /*点击完成按钮*/
    completeFn:function(){
        $("#completeBtn").bind("touchstart",function(){
            for(var i=0;i<$("#productList").find(".label-check").length;i++){
                $("#productList").find(".label-check").eq(i).removeClass("checkbox-active");
                $("#productList").find("li").eq(i).attr("data-slide","true");
            }
            $(this).hide();
            $(this).siblings("#editorBtn").show();
            $("#productList").find("li").removeClass("li-left-active");
            $("#operationBtn").slideUp("100");
        });
    },
    /*拖动事件*/
    silderDelete:function(){
        /*比率*/
        $("#productList").on("touchstart",'li',function(ev){
            /*获取当前对象的Y轴范围*/
            var sildeRate = $(this).find(".delete-box").width();
            var ev = ev || event;
            var touchStart = ev.originalEvent.changedTouches[0];
            var disX = Number(touchStart.pageX);
            var disY = Number(touchStart.pageY);
            var isScrolling = 1;  //判断是否水平滑动   默认垂直滑动


            $(this).on("touchmove",function(ev){
                var ev = ev || event;
                var touchMove = ev.originalEvent.changedTouches[0];
                var moveX = Math.abs( Number( touchMove.pageX) - disX );
                var moveY = Math.abs( Number( touchMove.pageY) - disY );
                isScrolling = moveX < moveY ? 1:0;
                if(isScrolling === 0) {
                    ev.preventDefault();
                    /*手指一动距离*/
                    if ($(this).attr("data-slide") == "true") {
                        var x = Number(touchMove.pageX) - disX;
                        if (x <= 0 && x >= -sildeRate) {
                            $(this).css("margin-left", x);
                        };
                    } else if ($(this).attr("data-slide") == "false") {
                        $(this).css("margin-left", '0');
                    }
                }
            });
            if ($(this).attr("data-slide") == "true") {
                $(this).on("touchend", function (ev) {
                    var ev = ev || event;
                    var touchEnd = ev.originalEvent.changedTouches[0];
                    var x = Number( touchEnd.pageX ) - disX;
                    var endX = Math.abs( Number( touchEnd.pageX) - disX );
                    var endY = Math.abs( Number( touchEnd.pageY) - disY );
                    isScrolling = endX < endY ? 1:0;
                    if ( x + 30 <= -sildeRate && isScrolling === 0 ) {
                        ev.preventDefault();
                        $(this).css({"margin-left": -sildeRate});
                    } else {
                        $(this).css({"margin-left": 0});
                    }
                    $(this).unbind("touchmove");
                    $(this).unbind("touchend");
                });
            } else if ($(this).attr("data-slide") == "false") {
                $(this).css("margin-left", '0');
                $(this).on("touchend", function (ev) {
                    var ev = ev || event;
                    $(this).css({"margin-left": 0});
                    $(this).unbind("touchmove");
                    $(this).unbind("touchend");
                });
            }
        })
    },
};
