function HDInfoWindow() {

}

HDInfoWindow.prototype = new BMap.Overlay();

HDInfoWindow.prototype.initialize = function (map) {
    var div = document.createElement("div");
    div.setAttribute("class", "sceInfobox hdoutline-box");
    div.setAttribute("id", "J_sceInfobox");

    this._div = div;
    this._map = map;
    $(div).click(function () {
        return false;
    });

    map.getPanes().labelPane.appendChild(div);

    return div;
};

HDInfoWindow.prototype.open = function (point, scenicId, coordinate) {
    if (!scenicId) {
        return;
    }
    $('#img_hover_' + scenicId).addClass('shineImga');
    //window.console.info('point:' + point);
    var position = this._map.pointToOverlayPixel(point);
    //window.console.info('position:' + position);
    var div = this._div;

    // 查询景点数据
    $.getJSON("/lvxbang/handDraw/getAScenic.jhtml", {scenicId: scenicId}, function (result) {
        div.innerHTML = template("tpl-hd-outline", result);
        $(div).find(".scenic-node").data("scenic", result);
        FloatEditor.markAScenic(result.id);
        $(div).undelegate(".stroke_open", "click");
        $(div).delegate(".stroke_open", "click", function (event) {
            var stroke = $(this).attr("data-staute");
            if (!stroke) {
                var num = $(this).attr("num");
                var fk_img2 = $(this).closest("li").find('.img img').attr("src");
                $(".Itineraryfk").hide();
                $(".Float_w_r").animate({"width": "240px"}, 300).attr("open_k", "1"); 	//右侧打开
                $(".Add_attractions").show();//添加景点
                $(".Myitinerary").addClass("checked").attr("data-staute", "1");  //我的行程打开

                //判断浏览器版本
                var userAgent = navigator.userAgent.toLowerCase();
                jQuery.browser = {
                    version: (userAgent.match( /.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1],
                    safari: /webkit/.test( userAgent ),
                    opera: /opera/.test( userAgent ),
                    msie: /msie/.test( userAgent ) && !/opera/.test( userAgent ),
                    mozilla: /mozilla/.test(userAgent)&&!/(compatible|webkit)/.test(userAgent)
                };

                //IE的话就不执行抛物线
                if(!jQuery.browser.msie){
                    //alert(jQuery.browser.version);
                    //alert(123);
                    //FloatEditor.flyIntoCart.apply(this, [event]);  //运行抛物线
                  FloatEditor.flyIntoCart.apply(this, [event, $(".Pop_ups_div").find("img").attr("src")]);  //运行抛物线
                }

                $(this).addClass("checked").attr("data-staute", "1");  //加选中样式
                $(this).text("√已加入线路");
                var scenic = $(this).parents(".scenic-node").data("scenic");
                FloatEditor.addIntoCart(scenic);
                $(div).removeClass("open");
            } else {
                var id = $(this).parents(".scenic-node").data("id");
                $(this).removeClass("checked").removeAttr("data-staute");
                $(this).text("+加入线路");
                $(".Add_attractions_u .close").each(function () {
                    if ($(this).data("id") == id) {
                        $(this).click();
                        return false;
                    }
                });
            }
        });
        $(div).find(".pript_a").click(function () {
            window.location.href = $(this).attr("href");
        });
        if (coordinate) {
            div.style.left = coordinate.left - 130 + "px";
            div.style.top = coordinate.top + "px";
        } else {
            div.style.left = position.x - 200 + "px";
            div.style.top = position.y - 510 + "px";
        }

        $(div).addClass("open");
        $(div).find(".close").click(function () {
            $(div).removeClass("open");
            $('#img_hover_' + scenicId).removeClass('shineImga');
        });
        // 加入计划
        $(div).find(".addtoplan").click(function () {
            addHpToTrip(scenicId);
            $(div).removeClass("open");
        });
        var Pop_ups_ul = $(".Pop_ups_ul li").length;
        var width2 = Pop_ups_ul * 350;
        $(".Pop_ups_ul").css("width", width2);
        var ta_list_div_d2 = $(".ta_list_div_d").width();
        var nowPage2 = $(".Pop_ups_ul").attr("num");

        $('.Pop_ups_div').mouseover(function () {
            $('.Pop_ups_div .left').show()
            $('.Pop_ups_div .right').show()
        });
        $('.Pop_ups_div').mouseout(function () {
            $('.Pop_ups_div .left').hide();
            $('.Pop_ups_div .right').hide();
        });

        $('.Pop_ups_div .left').click(function () {
            if (nowPage2 > 1) {
                $(".Pop_ups_ul").animate({"margin-left": "+=350px"}, 500);
                nowPage2--;
            }
        });
        $('.Pop_ups_div .right').click(function () {
            var zong2 = 350 * nowPage2;
            if (width2 > ta_list_div_d2 && zong2 < width2) {
                $(".Pop_ups_ul").animate({"margin-left": "-=350px"}, 500);
                nowPage2++;
            }
            $(".Pop_ups_ul").attr("num", nowPage2);
        });
        $(".Pop_ups_nr .jumpTo").click(function () {
            $("#jumpToUrl").attr("href", $(this).data("url")).get(0).click();
        });
    });

    var p_point = this._map.overlayPixelToPoint({x: position.x, y: (position.y - 160)});
    // 根据所点击的景点，地图移至中心点
    this._map.panTo(p_point);
};

HDInfoWindow.prototype.close = function () {
    $(this._div).removeClass("open");
    //this._div.style.display = "none";
};

HDInfoWindow.prototype.draw = function () {
};
//页面改变大小时重新载入图片
window.onresize = function(){
   window.location.reload();
}