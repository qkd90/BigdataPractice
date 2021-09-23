<div class="container">
    <div class="row" id="jingdian">
        <div class="col-xs-6">
            <h1 class="title">${ticket.ticketName} <img src="${picPath}/images/tuijian.jpg">
                <img src="${picPath}/images/ico5.jpg">
                <small>景点地址：${ticket.address}<a href="#" class="map">查看地图</a></small>
            </h1>
        </div>
        <div class="col-xs-6">
            <div class="buyaction text-right">
                <span class="price">销售价：<i>￥</i><b>${lowestPrice}</b>起</span>
                <a href="#j1" class="btn btn-warning">立即预定</a>
                <input class="btn btn-warning" type="button" value="加入购物车">
            </div>
        </div>
        <div class="col-xs-12">
            <div class="media" id="jd-info">
                <div class="media-left"><img src="${picPath}/images/xianlu/4.jpg" class="img-responsive"></div>
                <div class="media-body">
                    <h2 class="title">${ticket.name} <img src="${picPath}/images/rybz.jpg"></h2>

                    <div class="content">景点门票</div>
                    <div class="ydlc">预定流程：1.选票 > 2.提交订单 > 3.旅行社确认/付款 > 4.预定成功</div>
                    <div class="clearfix share">
                        <!-- JiaThis Button BEGIN -->
                        <div class="jiathis_style_24x24" style="margin-right:100px;">
                            <span class="jiathis_txt" style="font-size:14px;">分享到：</span>
                            <a class="jiathis_button_qzone"></a>
                            <a class="jiathis_button_tsina"></a>
                            <a class="jiathis_button_tqq"></a>
                            <a class="jiathis_button_weixin"></a>
                            <a class="jiathis_button_renren"></a>
                            <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
                        </div>
                        <!-- JiaThis Button END -->
                        <div class="sc"><img src="${picPath}/images/sc.jpg"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-12">
            <div id="j1" class="jd-box clearfix">
                <h5 class="title">
                    <a href="#j1" class="curr">门票预订</a><a href="#j2">服务详情</a><a href="#j3">景点介绍</a><a href="#j4">交通指南</a><a href="#j5">在线咨询</a>
                </h5>
                <table width="100%">
                    <tr class="table-top">
                        <td colspan="2" align="center">票型</td>
                        <td align="center">销售价</td>
                        <td align="center" class="commission-hide">利润空间</td>
                        <td align="center">兑票方式</td>
                        <td align="center">支付方式</td>
                        <td align="center">使用日期</td>
                        <td align="center">操作</td>
                    </tr>
                    <tbody>

                    <#list priceList as priceType>
                        <#list priceType.list as ticketPrice>

                        <tr class="ticket-price-row">

                            <#if ticketPrice_index==0>
                                <td rowspan="${priceType.list?size}" class="border-right">
                                    <#if priceType.type=='adult'>
                                        成人票
                                    <#elseif priceType.type=='student'>
                                        学生票
                                    <#elseif priceType.type=='child'>
                                        儿童票
                                    <#elseif priceType.type=='oldman'>
                                        老人票
                                    <#elseif priceType.type=='taopiao'>
                                        套票
                                    <#elseif priceType.type=='team'>
                                        团体票
                                    <#else>
                                        其他
                                    </#if>
                                </td>
                            </#if>
                            <td>${ticketPrice.name}</td>
                            <td align="center" class="salePrice">¥<span></span></td>
                            <td align="center" class="commission-hide commission">¥<span></span></td>
                            <td align="center">

                                <#if ticketPrice.getGetTicket()=='messageget'>
                                    短信
                                <#elseif ticketPrice.getGetTicket()=='scenicget'>
                                    景区取票
                                <#elseif ticketPrice.getGetTicket()=='teamget'>
                                    团队签单
                                <#elseif ticketPrice.getGetTicket()=='sendget'>
                                    送票上门
                                <#elseif ticketPrice.getGetTicket()=='selfget'>
                                    门市自取
                                <#else>
                                    其他
                                </#if>
                            </td>
                            <td align="center">
                                <#if ticketPrice.ticket.payway=='scenicpay'>
                                    景区支付
                                <#elseif ticketPrice.ticket.payway=='offlinepay'>
                                    线下支付
                                <#elseif ticketPrice.ticket.payway=='allpay'>
                                    全额支付
                                <#elseif ticketPrice.ticket.payway=='nopay'>
                                    未定义
                                </#if>
                            </td>
                            <td align="center">
                                <form action="/shopcart/shopcart/order.jhtml" method="post">
                                    <input type="hidden" value="${ticket.id?c}" name="id" id="ticketId"/>
                                    <input type="hidden" value="scenic" id="lineId" name="proType"/>
                                    <input id="num" class="num" name="adultCount" type="hidden" value="1"/>
                                    <input name="priceId" type="hidden" value="${ticketPrice.id?c}" class="ticketPriceId"/>
                                    <input name="startDate" class="date-picker" type="text" value="">
                                </form>
                            </td>
                            <td align="center">
                                <input type="submit" class="btn btn-warning order-now" id="button" value="立即预定">
                                <input type="submit" class="btn btn-danger distribute" id="button2" value="我要分销">
                            </td>
                        </tr>

                        </#list>


                    </#list>

                    </tbody>
                </table>
            </div>
            <!---------------->
            <div id="j2" class="jd-box clearfix">
                <h4>服务详情</h4>

                <div class="jd-box-content">
                    <h6><span class="subtitle"><span>入园说明</span></span></h6>

                    <div class="clearfix">
                    ${ticketExplain.enterDesc}
                    </div>
                    <h6><span class="subtitle"><span>退改规则</span></span></h6>

                    <div class="clearfix">
                    ${ticketExplain.rule}
                    </div>
                    <h6><span class="subtitle"><span>优惠政策</span></span></h6>

                    <div class="clearfix">
                    ${ticketExplain.privilege}
                    </div>
                    <h6><span class="subtitle"><span>产品详情</span></span></h6>

                    <div class="clearfix">
                    ${ticketExplain.proInfo}
                    </div>
                </div>
            </div>
            <!---------------->
            <div id="j3" class="jd-box clearfix">
                <h4>景点介绍</h4>

                <div class="jd-box-content">
                <#if ticket.scenicInfo!=null && ticket.scenicInfo.scenicOther!=null>
                ${ticket.scenicInfo.scenicOther.introduction}
                </#if>
                </div>
            </div>
            <!---------------->
            <div id="j4" class="jd-box clearfix">
                <h4>交通指南</h4>

                <div class="jd-box-content">
                    <img src="/images/xianlu/map.jpg">这里应该是一张地图<br><br>
                <#if ticket.scenicInfo!=null && ticket.scenicInfo.scenicOther!=null>
                ${ticket.scenicInfo.scenicOther.guide}
                </#if>
                </div>
            </div>
            <!---------------->
        </div>
    </div>
</div>

