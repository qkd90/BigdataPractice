
<!--主体部分-->
<div class="wrap-box">
    <div class="wrap">
        <div class="bread-nav clearfix">
            <span class="pull-left">您在这里：</span>
            <i class="pull-left"> &gt; </i>
            <a class="pull-left" href="#">${cruiseShip.name}</a>
            <#--<i class="pull-left"> &gt; </i>-->
            <#--<a class="pull-left" href="#">新世纪号预订</a>-->
            <#--<i class="pull-left"> &gt; </i>-->
            <#--<a class="pull-left" href="#">日本航线（上海上船） 5天4晚 福冈</a>-->
        </div>
        <div class="product-order" style="margin-bottom: 0px;">
            <div class="order-header clearfix">
                <h5 class="pull-left">${cruiseShip.name}</h5>
                <#--<span class="pull-right">供应商：厦门旅客旅行社</span>-->
            </div>
            <div class="order-body clearfix">
                <div class="order-swiper pull-left">
                    <div class="views">
                        <div class="swiper-wrapper">
                            <#list productimages as productimage>
                            <div class="swiper-slide swiper-slide-visible swiper-slide-active">
                                 <img src="${QINIU_BUCKET_URL}${productimage.path}">
                          </div>
                          </#list>
                      </div>
                    </div>
                    <div class="previews-wrap">
                        <a href="javascript:void(0)" class="btn-prev"><i class="icon-prev"></i></a>
                        <div class="preview">
                            <div class="swiper-wrapper" style="width: 560px; height: 40px;">
                            <#list productimages as productimage>
                                <div class="swiper-slide active swiper-slide-visible swiper-slide-active" style="width: 80px; height: 40px;">
                                    <img src="${QINIU_BUCKET_URL}${productimage.path}">
                             </div>
                         </#list>
                            </div>
                        </div>
                        <a href="javascript:void(0)" class="btn-next"><i class="icon-next"></i></a>
                    </div>
                </div>
                <div class="order-content pull-right">
                    <div class="content-group content-color-blue clearfix">
                        <label class="pull-left">产品编号：</label>
                        <span class="pull-left" id ="cruiseShipId">${cruiseShip.id}</span>
                    </div>
                    <div class="content-group clearfix">
                        <label class="pull-left">途径城市：</label>
                        <span class="pull-left">${(cruiseShip.startCity)}-${(cruiseShip.arriveCity)}</span>
                    </div>
                    <div class="content-port clearfix">
                        <div class="port-group pull-left clearfix">
                            <label class="pull-left">出发日期：</label>
                            <span class="pull-left">${(cruiseShip.startDate?string("yyyy-MM-dd"))}</span>
                        </div>
                        <div class="port-group pull-left clearfix">
                            <label class="pull-left">返回日期：</label>
                            <span class="pull-left">${(cruiseShip.endDate?string("yyyy-MM-dd"))}</span>
                        </div>
                        <div class="port-group pull-left clearfix">
                            <label class="pull-left">登船港口：</label>
                            <span class="pull-left">厦门港</span>
                        </div>
                        <div class="port-group pull-left clearfix">
                            <label class="pull-left">离船港口：</label>
                            <span class="pull-left">厦门港</span>
                        </div>
                    </div>
                    <div class="content-group clearfix">
                        <label class="pull-left">行程天数：</label>
                        <span class="pull-left">${cruiseShip.playDay}</span>
                    </div>
                    <div class="price clearfix">
                        <label class="pull-left">价格：</label>
                        <span class="cost pull-left"><sub>¥</sub>${cruiseShipDate.minSalePrice}<sub>起</sub></span>
                        <div class="original-price pull-left">
                            <span>¥${cruiseShipDate.minMarketPrice}</span>
                            <i class="line"></i>
                        </div>
                    </div>
                    <div class="buy  clearfix">
                        <form id="order-cruiseship-room" method="post" action="/yhypc/cruiseShip/chooseCabin.jhtml">
                            <input id="cruiseship-date-id" name="dateId" type="hidden" value="${cruiseShip.dateId}">
                            <input id="cruiseship-id" name="cruiseShipId" type="hidden" value="${cruiseShip.id}">
                        <a href="/yhypc/cruiseShip/chooseCabin.jhtml?dateId=${cruiseShip.dateId}&cruiseShipId=${cruiseShip.id}" class="buy-btn pull-left" onclick="$('#order-cruiseship-room').submit();">立即预订</a>
                            </form>
                        <#--<div class="consult-tel pull-left">-->
                            <#--<p>资讯热线：</p>-->
                            <#--<p class="color-yellow">400-651-7544</p>-->
                        <#--</div>-->
                    </div>
                </div>
            </div>
        </div>
        <ul class="product-nav clearfix">
            <li class="pull-left active">邮轮介绍</li>
            <li class="pull-left">邮轮航线</li>
            <li class="pull-left">行程安排</li>
            <li class="pull-left">费用说明</li>
            <li class="pull-left">预订须知</li>
            <li class="pull-left">取消政策</li>
            <li class="pull-left">温馨提示</li>
        </ul>
        <div class="product-details">
            <div class="product-details-group cruise-about">
                <div class="about-header clearfix">
                    <i class="pull-left"></i>
                    <span class="pull-left">${cruiseShip.name}</span>
                    <i class="pull-left"></i>
                </div>
                <div class="about-txt">
                    ${cruiseShip.services}<a href="/yhypc/cruiseShip/cruiseDetails.jhtml?cruiseShipId=${cruiseShip.id}">了解更多 &gt; </a>
                </div>
                <ul class="about-nav clearfix">
                    <li class="pull-left active" data-classify-id="">
                        <span class="roomNum">舱房介绍（）</span>
                        <i class="icon-triangle"></i>
                    </li>
                    <li class="pull-left">
                        <span class="foodNum">海上美食（）</span>
                        <i class="icon-triangle"></i>
                    </li>
                    <li class="pull-left">
                        <span class="entainmentNum">邮轮玩乐（）</span>
                        <i class="icon-triangle"></i>
                    </li>
                    <li class="pull-left">
                        <span class="serviceNum">邮轮服务（）</span>
                        <i class="icon-triangle"></i>
                    </li>
                </ul>
                <div class="about-content">
                    <!--舱房轮播-->
                    <div class="about-content-group cabin">
                        <a href="javascript:void(0)" class="about-content-prev"><i></i></a>
                        <div class="content-swiper">
                            <div class="swiper-wrapper" id="partOfRoom" style="width: 1641px; height: 251px;">

                            </div>
                        </div>
                        <a href="javascript:void(0)" class="about-content-next"><i></i></a>
                    </div>
                    <!--海上美食轮播-->
                    <div class="about-content-group onsea" style="display: none">
                        <a href="javascript:void(0)" class="about-content-prev"><i></i></a>
                        <div class="content-swiper">
                            <div class="swiper-wrapper"id="partOfFood" style="width: 1641px; height: 251px;">

                            </div>
                        </div>
                        <a href="javascript:void(0)" class="about-content-next"><i></i></a>
                    </div>
                    <!--邮轮玩乐轮播-->
                    <div class="about-content-group cruise-play" style="display: none">
                        <a href="javascript:void(0)" class="about-content-prev"><i></i></a>
                        <div class="content-swiper">
                            <div class="swiper-wrapper" id="partOfEntainment" style="width: 1641px; height: 251px;">

                            </div>
                        </div>
                        <a href="javascript:void(0)" class="about-content-next"><i></i></a>
                    </div>
                    <!--邮轮服务轮播-->
                    <div class="about-content-group cruise-server" style="display: none">
                        <a href="javascript:void(0)" class="about-content-prev"><i></i></a>
                        <div class="content-swiper">
                            <div class="swiper-wrapper" id="partOfService" style="width: 1641px; height: 251px;">

                            </div>
                        </div>
                        <a href="javascript:void(0)" class="about-content-next"><i></i></a>
                    </div>
                </div>
                <!--常见问题-->
                <div class="cabin-question">
                    <i class="icon-question"></i>常见问题
                </div>
            </div>
            <#--<div class="product-details-group cruise-route clearfix">-->
                <#--<div class="route-img pull-left">-->
                    <#--<img src="/image/product_route_img.png">-->
                <#--</div>-->
                <#--<div class="route-table pull-right">-->
                    <#--<div class="route-table-wrap">-->
                        <#--<table border="0" cellspacing="0">-->
                            <#--<thead>-->
                            <#--<tr>-->
                                <#--<th>日期</th>-->
                                <#--<th>国家/城市</th>-->
                                <#--<th>抵达<sub>当地时间</sub></th>-->
                                <#--<th>启程<sub>当地时间</sub></th>-->
                            <#--</tr>-->
                            <#--</thead>-->
                            <#--<tbody>-->
                            <#--<#list cruiseShip.cruiseShipPlans?sort_by("day") as cruiseShipPlan>-->
                            <#--<tr>-->
                                <#--<td>第${cruiseShipPlan.day}天</td>-->
                                <#--<td>中国</td>-->
                                <#--<td></td>-->
                                <#--<td>${cruiseShipPlan.arriveTime}</td>-->
                            <#--</tr>-->
                            <#--</#list>-->
                            <#--</tbody>-->
                        <#--</table>-->
                    <#--</div>-->
                    <#--<p class="table-tips"><i>*</i>以上行程日期和时间均为当地时间，可能会因为天气、路况等原因做相应调整，请以最终的行程安排为准。</p>-->
                <#--</div>-->
            <#--</div>-->
            <div class="product-details-group cruise-everyday clearfix">
                <ul class="everyday-nav pull-left" style="margin-top: 0px;">
                    <#list cruiseShip.cruiseShipPlans?sort_by("day") as cruiseShipPlan>
                    <li class="active"><span>第${cruiseShipPlan.day}天</span></li>
                </#list>
                </ul>
                <div class="everyday-content pull-right">

                    <#list cruiseShipPlanList as planList>
                    <div class="everyday-group">
                        <div class="group-header clearfix">
                            <div class="header-content-date pull-left clearfix">
                                <i class="pull-left">${planList.beginDay?string("dd")}日</i>
                                <div class="content-date pull-left">
                                    <span>${planList.beginDay?string("MM")}月</span>
                                    <span>${planList.beginDay?string("yyyy")}年</span>
                                </div>
                            </div>
                            <div class="header-content-day pull-left">
                                <span>DAY</span>
                                <span>${planList.day}</span>
                            </div>
                            <div class="header-content-port pull-left">
                                港口：${planList.dayDesc}
                            </div>
                        </div>
                        <div class="group-body clearfix">
                            <div class="boat-time pull-left">
                                <span>${planList.leaveTime}</span>
                                <i>启程</i>
                                <span>${planList.leaveTime}</span>
                                <i>停止登船</i>
                            </div>
                            <div class="boat-icon pull-left"></div>
                            <div class="boat-txt pull-left">
                            ${planList.arrange}
                            </div>
                        </div>
                    </div>
                    </#list>
                </div>
            </div>
            <div class="product-details-group cruise-cost">
                <div class="cost-header clearfix">
                    <i class="line pull-left"></i>
                    <span class="pull-left">费用说明</span>
                    <i class="line pull-right"></i>
                </div>
                <div class="cost-content clearfix">
                    <label class="pull-left">费用包含</label>
                    <div class="cost-txt pull-left">
                        <ol>
                        ${cruiseShip.cruiseShipExtend.quoteContainDesc}
                        </ol>
                    </div>
                </div>
                <div class="cost-content clearfix">
                    <label class="pull-left">费用不包含</label>
                    <div class="cost-txt pull-left">
                        <ol>
                        ${cruiseShip.cruiseShipExtend.quoteNoContainDesc}
                        </ol>
                    </div>
                </div>
            </div>
            <div class="product-details-group cruise-order">
                <div class="order-header clearfix">
                    <i class="line pull-left"></i>
                    <span class="pull-left">预订说明</span>
                    <i class="line pull-right"></i>
                </div>
                <div class="order-body clearfix">
                    <ul class="order-nav pull-left">
                        <li class="active">预订须知</li>
                    <#if cruiseShip.cruiseShipExtend.howToOrder?? && cruiseShip.cruiseShipExtend.howToOrder != "">
                        <li>如何预订</li>
                    </#if>
                    <#if cruiseShip.cruiseShipExtend.signWay?? && cruiseShip.cruiseShipExtend.signWay != "">
                        <li>签约方式</li>
                    </#if>
                    <#if cruiseShip.cruiseShipExtend.payWay?? && cruiseShip.cruiseShipExtend.payWay != "">
                        <li>付款方式</li>
                    </#if>
                    <#if cruiseShip.cruiseShipExtend.visaInfo?? && cruiseShip.cruiseShipExtend.visaInfo != "">
                        <li>签证信息</li>
                    </#if>
                    </ul>
                    <div class="order-txt pull-left">
                        <div class="order-txt-group">
                        ${cruiseShip.cruiseShipExtend.orderKnow}
                        </div>
                    <#if cruiseShip.cruiseShipExtend.howToOrder?? && cruiseShip.cruiseShipExtend.howToOrder != "">
                        <div class="order-txt-group" style="display: none;">
                        ${cruiseShip.cruiseShipExtend.howToOrder}
                        </div>
                    </#if>
                    <#if cruiseShip.cruiseShipExtend.signWay?? && cruiseShip.cruiseShipExtend.signWay != "">
                        <div class="order-txt-group" style="display: none">
                        ${cruiseShip.cruiseShipExtend.signWay}
                        </div>
                    </#if>
                    <#if cruiseShip.cruiseShipExtend.payWay?? && cruiseShip.cruiseShipExtend.payWay != "">
                        <div class="order-txt-group" style="display: none">
                        ${cruiseShip.cruiseShipExtend.payWay}
                        </div>
                    </#if>
                    <#if cruiseShip.cruiseShipExtend.visaInfo?? && cruiseShip.cruiseShipExtend.visaInfo != "">
                        <div class="order-txt-group" style="display: none">
                        ${cruiseShip.cruiseShipExtend.visaInfo}
                        </div>
                    </#if>
                    </div>
                </div>
            </div>
            <div class="product-details-group cruise-policy">
                <div class="policy-header clearfix">
                    <i class="line pull-left"></i>
                    <span class="pull-left">取消政策</span>
                    <i class="line pull-right"></i>
                </div>
                <div class="policy-txt">
                    启航前60天以上扣3500/人<br>
                    启航前45-60天取消行程扣全款55%<br>
                    启航前45-30天取消行程扣全款75%<br>
                    启航前30天内取消行程扣全款100%<br>
                    所有罚责日期如遇周末及国家假日自动提前至可工作日<br>
                </div>
            </div>
            <div class="product-details-group cruise-policy">
                <div class="policy-header clearfix">
                    <i class="line pull-left"></i>
                    <span class="pull-left">温馨提示</span>
                    <i class="line pull-right"></i>
                </div>
                <div class="policy-txt">
                    报名时请提供准确的名字（汉字及拼音）、出生日期、性别信息及分房名单。<br>
                    游轮公司不接受不满6个月的婴儿、怀孕24周以上的孕妇登船。<br>
                    21周岁以下游客必须与21周岁以上游客同住一间客舱，若非与父母同行，需提供监护人的授权书、委托书及出生证明。<br>
                    70周岁及以上、80 周岁以下游客需提供3个月内三甲医院开具的健康证明。80周岁以上游客需同时有家属陪同。<br>
                    若遇不可抗拒因素（如台风、疫情、地震等自然灾害，以及罢工、战争等政治因素等），游轮公司有权更改行程或缩短游览时间等，游客应积极配合并接受对行程的合理调整，在调整过程中发生的额外费用，由游客承担！<br>
                    我社保留根据具体情况更改岸上观光行程的权利。<br>
                    游客报名后，若遇游轮公司船票、燃油税等调价，我公司根据实际差额向游客多退少补。<br>
                    持非中国人民共和国护照的游客请务必自行确认证件在旅行结束后能再次入境中国。<br>
                    注：船舱数量有限，我公司以确认时为准。若遇不可抗拒因素（如：遇台风等），游轮公司有权改变行程及缩短景点游览时间，所产生的损失我司及游轮公司概不负责！我社保留根据具体情况更改行程的权利，以上行程仅供参考，请以出发行程为准。<br>
                </div>
            </div>
        </div>
    </div>
</div>

