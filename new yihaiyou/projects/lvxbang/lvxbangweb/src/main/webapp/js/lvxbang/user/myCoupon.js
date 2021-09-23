var pager;
var CouponList = {
    initPanel: function () {
        $(".personal").removeClass("checked");
        $("#coupon-panel").addClass("checked");
    },

    //红包列表分页初始化
    redPacketinit: function() {
        var options={
            countUrl: "/lvxbang/user/getCouponCount.jhtml",
            resultCountFn: function(result) {return parseInt(result[0])},
            pageRenderFn: function(pageNo, pageSize, data) {
                //$("#loading").show();
                data.pageNo = pageNo;
                data.pageSize = pageSize;
                $.ajax({
                    url:"/lvxbang/user/getMyCoupon.jhtml",
                    type:"post",
                    dataType:"json",
                    data:data,
                    success:function(data){
                        window.console.log(data);
                        $('#redPacketUl').empty();
                        var html="";
                        $.each(data,function(i,s){
                            s.coupon.instructions = $.trim(s.coupon.instructions);
                            if(isNull(s.coupon.limitProductTypes)){
                                s.coupon.limitProductTypes="线路/酒店/门票/飞机/火车"
                            }else{
                                s.coupon.limitProductTypes = s.coupon.limitProductTypes.replace("plan","线路").replace("hotel","酒店")
                                    .replace("scenic","门票").replace("flight","飞机").replace("train","火车").replace(",","/");
                            }
                            s.validStart = s.validStart.substr(0,10);
                            s.validEnd = s.validEnd.substr(0,10);
                            html += template("tpl-red-packet-item", s);
                        });
                        $('#redPacketUl').html(html);

                        //鼠标移动上去显示更多优惠说明
                        $('.moreknow').hover(function(){
                            $(this).find('span').css('display','block');
                        },function(){
                            $(this).find('span').css('display','none');
                        });

                    },
                    error:function(){
                        window.console.log("error");
                    }
                });
            }
        };
        pager = new Pager(options);
    },

    redPacketList: function(status){
        pager.init({'userCouponStatus':status});
    }
};

$(function () {
    CouponList.initPanel();
    CouponList.redPacketinit();
    CouponList.redPacketList('unused');
    $('.redPackedList').click(function(){
        $(this).siblings('li').removeClass('redPacket');
        $(this).addClass('redPacket');
        CouponList.redPacketList($(this).attr('status'));
    });
    $(".useRule").click(function() {
        $.ajax({
            url:"/lvxbang/help/dataListBykeyword.jhtml",
            type:"post",
            dataType:"json",
            data:{
                'keyword':"红包使用规则"
            },
            success:function(data){
                protocolPop(data.title, data.content);
            }
        });
    });
});