<div class="index1200">
    <div class="wrap clearfix" id="wrapMain">
        <div class="search_nav">
            <#--<p class="crumbs">-->
                <#--<!-- use same breadCrumbs &ndash;&gt;-->
                <#--<a href="/domestic/">国内旅游</a>&gt;<a href="http://www.tuniu.com/guide/d-hainan-900/">海南旅游</a>&gt;<a href="http://www.tuniu.com/guide/d-sanya-906/">三亚旅游</a>&gt;<a href="http://www.tuniu.com/guide/d-tyq-1819025/">天涯区旅游</a>&gt;<a href="http://www.tuniu.com/guide/v-nanshanwenhuayuan-20037/">南山佛教文化苑旅游</a>                </p>-->
        </div>
        <div class="mainContent">
            <div class="main_top">
                <!--线路名称-->
                <div class="top_tit">
                    <h1>
                        &lt;${line.name}&gt;${line.appendTitle}
                    </h1>
                </div>
                <!--线路编号和服务-->
                <div class="tours-sub-info clearfix">
                    <div class="ser_sm fl">
                        <#if line.productAttr == "gentuan">
                            <span class="detail-gty-icon"></span>
                        <#elseif line.productAttr == "ziyou">
                            <span class="detail-zzy-icon"></span>
                        <#elseif line.productAttr == "zijia">
                            <span class="detail-zjy-icon"></span>
                        <#elseif line.productAttr == "custommade">
                            <span class="detail-dzjp-icon"></span>
                        </#if>
                        <span class="c_f80">编号${line.productNo}：</span>
                        <span>本产品由厦门帮旅国际旅行社及具有资质的合作旅行社提供相关服务</span>
                    </div>
                    <#--<div class="book_infor fr">-->
                        <#--<a href="http://help.tuniu.com/help/detail/29" rel="nofollow" target="_blank" title="国内旅游预订须知" class="f_4e9700 underline" onclick="_gaq.push(['_trackEvent', 'gtcp','title','国内旅游预订须知']);">国内旅游预订须知</a>                            |-->
                        <#--<a href="http://www.tuniu.com/static/orderstate/orderstate_longline.html" class="f_4e9700 underline" rel="nofollow" target="_blank" title="网上预订须知" onclick="_gaq.push(['_trackEvent', 'gtcp','title','网上预订须知']);">网上预订须知</a>-->
                    <#--</div>-->
                </div>
                <!--头部主要信息开始-->
                <div class="tour_main clearfix">
                    <div class="tour_left fl">
                        <div class="detail_top posiR Food_d_t">
                            <div class="pikachoose fl">
                            <#list lineImages as image>
                                <#if image_index gt 4><#break></#if>
                                <#if image_index=0>
                                    <div class="mailTablePlan2"><img class="scenic-node-img" src="http://7u2inn.com2.z0.glb.qiniucdn.com/${image.imageUrl}?imageView2/1/w/500/h/280"/>
                                    </div>
                                <#else >
                                    <div class="mailTablePlan2 disn"><img class="scenic-node-img" src="http://7u2inn.com2.z0.glb.qiniucdn.com/${image.imageUrl}?imageView2/1/w/500/h/280"/>
                                    </div>
                                </#if>
                            </#list>
                                <ul class="mailTab2">
                                <#list lineImages as image>
                                    <#if image_index gt 4><#break ></#if>
                                    <#if image_index=0>
                                    <li class="checked first_image"><img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${image.imageUrl}?imageView2/1/w/105/h/65"/>

                                    <#--<p class="posiA">查看相册</p></li>-->
                                    <#else >
                                    <li><img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${image.imageUrl}?imageView2/1/w/105/h/65"/>

                                    <#--<p class="posiA">查看相册</p></li>-->
                                    </#if>
                                </#list>
                                </ul>
                            </div>
                        </div>
                        <!--start calendar-->
                        <div class="tour_cal_wrap">
                            <div class="tour_calendar">
                                <div id="calendar" class="clearfix fc">
                                </div>
                            </div>
                        </div>
                        <!--end calendar--> </div>
                    <div class="tour_right fr">

                        <div class="tour_intro  ">
                            <dl class="c_price">
                                <dt>促销价：</dt>
                                <dd>
                                <#if linePrice>
                                    <span class="cx_price">
                                        ¥ <em id="minPrice">${linePrice}</em>
                                    </span>
                                    <span>起&nbsp;&nbsp;&nbsp;</span>
                                <#else>
                                    <span class="cx_price">
                                        ¥ <em id="minPrice">来电咨询</em>
                                    </span>
                                </#if>
                                <#--<span class="cx_sheng">(立省¥550)</span>-->
                                <#--<a id="youhuiLink" class="abhor" href="javascript:void(0)" onclick="_gaq.push(['_trackEvent', 'gtcp','price','优惠详情']);">优惠详情</a>-->
                                </dd>
                            </dl>
                            <div class="arrow2"></div>
                        </div>
                        <#--<dl>-->
                            <#--<dt>特色服务：</dt>-->
                            <#--<dd class="cat">-->
                                <#--<span class="comment page_tip"></span>-->
                                <#--<span class="youji page_tip"></span>-->
                                <#--<!--牛分期&ndash;&gt;-->
                                <#--<span class="fenqi page_tip"></span>-->
                                <#--<!-- 一元夺宝，国长赠送 &ndash;&gt;-->
                                <#--<span class="duobaobi page_tip"></span>-->


                            <#--</dd>-->
                        <#--</dl>-->
                        <ul class="line_des clearfix">
                            <li class="f_1">
                                <span>${line.satisfaction}%</span> <i>满意度</i>
                            </li>
                            <li class="f_2">
                                <span> 3279</span>
                                <i>点评数</i>
                            </li>
                            <li class="f_3">
                                <span id="collectNum">16552</span><i>关注数</i>
                            </li>
                        </ul>
                        <!-- todo -->

                        <!--预订信息-->
                        <div id="order_mess" class="order_mess">
                            <dl <#if priceTypeList?size lt 2>style="display: none;"</#if>>
                                <dt>预定类型：</dt>

                                <dd class="price_type">
                                <#list priceTypeList as priceType>
                                    <span class="<#if priceType_index==0>checked</#if>" data-id="${priceType.id?c}">${priceType.quoteName}</span>
                                </#list>
                                </dd>
                            </dl>
                            <dl>
                                <dt>出游日期：</dt>
                                <dd>
                                    <div class="select_box select_date" id="selectDepartDate">
                                        <div class="select_con">
                                            <p class="grey select_result">请选择出发日期</p>
                                        </div>
                                        <ul class="select_list">
                                        </ul></div>
                                </dd>
                            </dl>
                            <#if startCity>
                            <dl>
                                <dt class="select_city_title">
                                    出发城市：                                </dt>
                                <span class="hide sel_start_city"><span class="s_con" data="602"></span></span>
                                <dd>
                                    <div class="select_box select_city fl " id="selectDepartCity">
                                        <div class="select_con">
                                            <input type="hidden" id="departCityResult">
                                            <p class="select_result" style="float:left">
                                                ${startCity.name}										</p>
                                            <p style="float:right;margin-right:5px">
                                                (${startCityList?size}个可选城市)											</p>

                                        </div>
                                        <ul class="select_list">
                                            <#list startCityList?keys as city>
                                            <li style="border-bottom: 1px dashed #ddd;" class="boss3-departCity">
                                                <a style="overflow: hidden" href="/line_detail_${startCityList[city].id}.html" target="_self"><span style="float:left">${city}</span><div style="float:right;color:#f60">￥${startCityList[city].price}起</div></a>
                                            </li>
                                            </#list>
                                        </ul>
                                    </div>
                                </dd>
                            </dl>
                            </#if>

                            <!-- 归来城市 -->


                            <dl>
                                <dt>出游人数：</dt>
                                <dd>
                                    <div class="acquiesce fl">
                                        <div id="adA-le" class="num-lib num-left on">
                                            <span id="Aacquit" class="acquisitive"></span>
                                        </div>
                                        <input value="2" id="adultN" class="acute" readonly>
                                        <div id="adA-ri" class="num-lib num-right">
                                            <span class="addiction"></span>
                                        </div>
                                    </div>
                                    <span class="fl acqu_name">成人</span>
                                    <!-- INT-5665 -->
                                <#if line.combineType != "combine">
                                    <div class="acquiesce fl supportChild">
                                        <div id="adC-le" class="num-lib num-left">
                                            <span id="Cacquit" class="acquisitive"></span>
                                        </div>
                                        <input value="0" class="acute" id="childN" readonly>
                                        <div id="adC-ri" class="num-lib num-right">
                                            <span class="addiction"></span>
                                        </div>
                                    </div>

                                    <span class="fl acqu_name child_describe">儿童</span>
                                </#if>
                                    <#if lineExplain.childStandardType != "none">
                                    <div class="acerbity fl">
                                        <span class="adjutant">儿童标准</span>
                                    </div>
                                    </#if>
                                </dd>
                            </dl>

                            <!-- 定制游服务提示结束 -->
                            <div class="order_now clearfix">
                                <#--<div data-routeid="210061717" data-routetype="1" data-routediscounts="0" class="u_order_qrcode fl">-->
                                    <#--<div class="order_qrcode_btn  ">-->
                                        <#--<span>扫码预订</span>-->
                                        <#--<i class="icon_qrcode"></i>-->
                                        <#--<i class="icon_arrow"></i>-->
                                    <#--</div>-->
                                    <#--<div class="dropdown_panel">-->
                                        <#--<p class="order_info"></p>-->
                                        <#--<p class="order_info"></p>-->
                                        <#--<p class="order_note">途牛APP扫码预订</p>-->
                                        <#--<img src="http://www.tuniu.com/yii.php?r=detail/tourAjax/GetQxCode&amp;product_id=210061717&amp;mPartner=17639&amp;product_type=1" class="qrcode_img">-->
                                    <#--</div>-->
                                <#--</div>-->
                                <div class="order_btn fl" <#if line.lineStatus == "show">id="yuding"</#if>>立刻预订</div>
                                <form id="toOrder" method="post" action="/lvxbang/order/orderLine.jhtml">
                                    <input type="hidden" name="linetypepriceId">
                                    <input type="hidden" name="lineStartDate">
                                    <input type="hidden" name="adultNum">
                                    <input type="hidden" name="childNum">
                                </form>
                                <input type="hidden" id="phow_show" value="0">
                                <a id="favorite_id" class="add_to_favorite focus_bg bg_focus bg_focus_normal line_collect" href="javascript:void(0)" data-favorite-id="${line.id}" data-favorite-type="line">关注</a>
                                <#--<a id="addToComp" class="comicons compare focus_bg bg_compare bg_compare_normal" rel="nofollow" href="javascript:void(0)" onclick="_gaq.push(['_trackEvent', 'gtcp','price','对比']);">对比</a>-->
                            </div>

                            <!-- 收藏验证框 -->
                            </div>

                        <div class="u_order_tip">
                            <div class="tel_404">
                                <i class="icon_tel"></i>
                                <span>24h客服电话</span><em>400-0131-770</em>
                            </div>
                        </div>

                        <!-- 收藏验证框 -->
                        <#if line.productAttr != "ziyou">
                        <div class="essentials">
                            <span class="tit">行程概要：</span>
                            <span>
                                <#list lineDayList as lineDay>
                                    <#if lineDay_index gt 0>&gt;</#if>
                                    D${lineDay.lineDay}&gt;
                                    <#--<#list lineDay.linedaysplan as dayPlan>-->
                                        <#--<em title="${lineDay.dayDesc}">${lineDay.dayDesc}</em><#if dayPlan_has_next>&gt;</#if>-->
                                    <#--</#list>-->
                                    ${lineDay.dayDesc?replace("-", ">")}
                                    &gt;${lineDay.hotelName}
                                </#list>
                            <a href="#xcjs" class="abhor_a">详情》</a>
                            </span>
                        </div>
                        </#if>
                        <!--产品经理推荐-->
                        <#if line.shortDesc>
                        <div class="phy-book">
                            <div class="clearfix">
                                <p class="adoration fl">产品经理推荐</p>
                                <div id="recentOrders"><ul id="ub_wrap" class="ub_wrap ub_wrap_single"></ul></div>
                            </div>
                            <#--<div class="recmark">-->
                                <#--<span>机票可选</span>                      </div>-->

                            <div class="recombox" id="recombox" style="height: 96px;">
                                <div id="recomInnerBox" style="word-break: break-word;">${line.shortDesc}</div>
                            </div>
                            <div class="showallbtn">
                                <a class=""></a>
                            </div>
                        </div>
                        </#if>
                    </div>
                </div>
                <!--头部主要信息结束--> </div>
            <!--头部结束-->


            <!--general_infor开始-->
            <div class="general_infor" id="general_infor">
                <div id="pkg-detail-wrap" class="pkg-detail-wrap">
                    <div class="tabcon pkg-detail-tab clearfix" id="pkgDetailTab" style="position: static; top: 0px;">
                        <ul id="pkg-detail-tab-bd" class="tab clearfix pkg-detail-tab-bd fl">
                        <#if lineExplain.lineLightPoint>
                            <li rel="cpts" class="current">
                                <#if line.productAttr != "ziyou">
                                    <a href="javascript:void(0)">产品特色</a>
                                <#else>
                                    <a href="javascript:void(0)">产品详情</a>
                                </#if>
                            </li>
                            <li rel="xcjs" class="">
                        <#else>
                            <li rel="xcjs" class="current">
                        </#if>
                            <#if line.productAttr != "ziyou">
                                <a href="javascript:void(0)">行程介绍</a>
                            <#else>
                                <a href="javascript:void(0)">参考行程</a>
                            </#if>
                            </li>

                            <li rel="fysm" class="">
                                <a href="javascript:void(0)">费用说明</a>
                            </li>
                            <li rel="ydxz" class="">
                                <a href="javascript:void(0)">预订须知</a>
                            </li>
                            <#--<li rel="dpjl" class="">-->
                                <#--<a href="javascript:void(0)">游客点评 (3279)</a>-->
                            <#--</li>-->
                            <#--<li rel="zxwd" class="">-->
                                <#--<a href="javascript:void(0)">在线问答</a>-->
                            <#--</li>-->
                            <li rel="xgcp">
                                <a href="javascript:void(0)">相关产品</a>
                            </li>
                        </ul>
                        <div class="booknow fr">
                            <a <#if line.lineStatus == "show">id="now_yuding"</#if> href="javascript:void(0)">立刻预订</a>
                        </div>
                    </div>
                </div>
                <div class="pkg-detail-box">
                    <div class="pkg-detail-con">

                        <!--                     </div> -->
                        <!--pkg-detail_con end-->
                        <!--                     <div class="pkg-detail-con">-->
                        <!-- 产品特色 start -->
                    <#if lineExplain.lineLightPoint>
                        <div class="detail-box pkg-detail-infor cpts" id="cpts" tabrel="cpts">
                            <h2 class="detail-title">
                                <#if line.productAttr != "ziyou">
                                    <span>产品特色</span>
                                <#else>
                                    <span>产品详情</span>
                                </#if>
                            </h2>

                            <div>
                                <!--产品经理推荐 start-->
                                <!-- 不要背景色已经红色的框框 -->
                                <div class="manager_rec">
                                    <div class="mRec_con">
                                        <div class="mRec_cont">
                                            <div class="mRec_con_tit no_border_b clearfix">
                                                <span class="tt fl">${lineExplain.defineTag}</span>
                                            </div>
                                            <div class="mRec_con_con clearfix">
                                            ${lineExplain.lineLightPoint}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- 产品特色END -->
                            </div>
                        </div>
                    </#if>
                        <!-- 产品特色 end -->
                        <!--行程介绍 start-->
                        <div class="pkg-detail-infor line-detail-intro" id="xcjs" tabrel="xcjs">
                            <h2 class="detail-title">
                            <#if line.productAttr != "ziyou">
                                <span>行程介绍</span>
                            <#else>
                                <span class="detail-title-ziyou">参考行程</span>
                            </#if>
                            </h2>
                            <!--多行程开始-->
                            <!--多行程end-->

                            <div class="detailcontent">
                                <div class="tripdate">
                                <#if line.productAttr != "ziyou">
                                    <div class="tripItineraryDescription">
                                        <div class="hodometer">
                                            <!--start tour_date_list-->
                                            <div class="tour_date_list clearfix">
                                                <!--start tour_abstruct-->
                                                <div class="fl">
                                                    <p style="color:#f1661a;" id="tour_abstruct">

                                                    </p>

                                                    <p style="color:#f1661a;"> 本产品与其他旅行社联合发团。
                                                    </p>

                                                    <p style="color:#f1661a;"></p>

                                                </div>
                                                <!--end tour_abstruct-->
                                            </div>
                                            <!--end tour_date_list-->
                                        </div>
                                    </div>
                                </#if>
                                    <div id="tripcontent">
                                        <div class="tripcontent selectTag tourItem" id="tripcontent0">
                                            <#if lineDeparture && lineDeparture.lineDepartureInfos?size gt 0>
                                            <div class="mRec_con">
                                                <!--发车信息开始，通用模块-->
                                                <div class="mRec_con_tit no_border_b clearfix">
                                                    <span class="tt fl">发车信息</span>
                                                </div>
                                                <div class="mRec_con_con mRec_con_tab clearfix">
                                                    <table width="100%" border="0">
                                                        <thead>
                                                        <tr>
                                                            <th class="time" width="100">发车时间</th>
                                                            <th width="300">发车地点</th>
                                                            <th width="300">返回地点</th>
                                                            <th>备注</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody>
                                                        <#list lineDeparture.lineDepartureInfos as info>
                                                        <tr>
                                                            <td class="time">${info.departureDate?string("HH:mm:ss")}</td>
                                                            <td>${info.originStation}</td>
                                                            <td>${info.returnPlace}</td>
                                                            <td>${info.remark}</td>
                                                        </tr>
                                                        </#list>
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <!--发车信息结束-->
                                                <#if lineExplain.receiveStandard>
                                                <div class="mRec_con_tit clearfix">
                                                    <span class="tt fl">接待标准</span>
                                                </div>
                                                <div class="mRec_con_con clearfix">
                                                    ${lineExplain.receiveStandard}
                                                </div>
                                                </#if>
                                                <#if lineExplain.accrossScenic>
                                                <div class="mRec_con_tit clearfix">
                                                    <span class="tt">沿途景点</span>
                                                </div>
                                                <div class="mRec_con_con clearfix">
                                                    ${lineExplain.accrossScenic}
                                                </div>
                                                </#if>
                                            </div>
                                            </#if>
                                            <div id="tripall" class="tripall">
                                                <div class="sidebarPrv outfix">
                                                    <div class="sidebar">
                                                        <#list lineDayList as lineDay>
                                                        <a class="daybox <#if lineDay_index == 0>current</#if>" href="javascript:void(0)" rel="day${lineDay.lineDay}">第${lineDay.lineDay}天</a>
                                                        </#list>
                                                    </div>
                                                </div>
                                                <#list lineDayList as lineDay>
                                                    <div class="tripdays" id="day${lineDay.lineDay}">
                                                        <div class="tripdays_con">
                                                            <dl>
                                                                <dt>
                                                                    <span class="tripIcon tripIcon_day">D${lineDay.lineDay}</span>
                                                                </dt>
                                                                <dd>
                                                                    <div class="dayway_tit"> <b>第${lineDay.lineDay}天</b>
                                                                        <span class="daywayback">${lineDay.dayDesc}</span>
                                                                    </div>
                                                                </dd>
                                                            </dl>

                                                            <!--行程描述-->
                                                            <div class="line_border">
                                                                <dl>
                                                                    <dt>
                                                                        <span class="tripIcon tripIcon_xc"></span>
                                                                    </dt>
                                                                    <dd class="clearfix">
                                                                        <label class="fl">行程描述</label>
                                                                        <div class="fl tripday_des">
                                                                            <span class="_0000FF">${lineDay.arrange}</span>                                                                  	                                        </div>
                                                                    </dd>
                                                                </dl>
                                                            </div>

                                                            <!--行程详情-->
                                                            <#list lineDay.linedaysplan as dayPlan>
                                                                <#if dayPlan.timeDesc || dayPlan.planInfoList?size gt 0>
                                                                    <div class="line_border">
                                                                        <dl>
                                                                            <#if dayPlan.timeNode>
                                                                                <dt>
                                                                                    <span class="tripIcon tripIcon_timebg">
                                                                                        <span class="“_FF0000“">${dayPlan.timeNode}</span>
                                                                                    </span>
                                                                                    <span class="tripIcon tripIcon_time"></span>
                                                                                </dt>
                                                                            </#if>
                                                                            <dd>
                                                                                <div class="pp ">${dayPlan.timeDesc}</div>
                                                                                <#list dayPlan.planInfoList as planInfo>
                                                                                    <div class="pp ">
                                                                                        <#if planInfo.littleTitle>
                                                                                        <span class="_FF0000" style="font-size: 16px;font-weight: bold;">${planInfo.littleTitle}</span>
                                                                                        </#if>
                                                                                        <br>${planInfo.titleDesc}</div>
                                                                                </#list>
                                                                                <#if dayPlan.planInfoImagesList?size gt 0>
                                                                                    <div class="line_border">
                                                                                        <dl>
                                                                                            <dd>
                                                                                                <ul class="city_pic clearfix">
                                                                                                    <#list dayPlan.planInfoImagesList as infoImages>
                                                                                                        <li>
                                                                                                            <div class="city_pic_con">
                                                                                                                <img
                                                                                                                    <#if infoImages.imageUrl?starts_with("http")>
                                                                                                                            data-original="${infoImages.imageUrl}"
                                                                                                                    <#else>
                                                                                                                            data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${infoImages.imageUrl}"
                                                                                                                    </#if>
                                                                                                                            style="display: inline;">
                                                                                                            </div>
                                                                                                            <span>${infoImages.imageDesc}</span>
                                                                                                        </li>
                                                                                                    </#list>
                                                                                                </ul>
                                                                                            </dd>
                                                                                        </dl>
                                                                                    </div>
                                                                                </#if>
                                                                            </dd>
                                                                        </dl>
                                                                    </div>
                                                                </#if>
                                                            </#list>
                                                            <!--航班、三餐、住宿-->
                                                            <div class="line_border hb_des">
                                                                <#if lineDay.meals>
                                                                    <dl>
                                                                        <dt>
                                                                            <span class="tripIcon tripIcon_sc"></span>
                                                                        </dt>
                                                                        <dd class="clearfix">
                                                                            <label class="fl">三餐</label>

                                                                            <div class="fl tripday_des">${lineDay.meals}</div>
                                                                        </dd>
                                                                    </dl>
                                                                </#if>
                                                                <#if lineDay.hotelName>
                                                                    <dl>
                                                                        <dt>
                                                                            <span class="tripIcon tripIcon_zs"></span>
                                                                        </dt>
                                                                        <dd class="clearfix">
                                                                            <label class="fl">住宿</label>

                                                                            <div class="fl tripday_des">${lineDay.hotelName}</div>
                                                                        </dd>
                                                                    </dl>
                                                                </#if>
                                                            </div>
                                                            <!--航班、三餐、住宿 结束-->
                                                            <!-- 购物start -->
                                                            <!-- 购物 end-->
                                                        </div>
                                                    </div>
                                                </#list>
                                                <div class="tripIcon tripall_end"></div>
                                            </div>
                                        </div>
                                        <!--一整个A行程结束-->

                                        <!--一整个行程结束-->
                                    </div>

                                </div>
                            </div>
                            <!--行程介绍 end-->
                            <!--导游开始-->
                            <!--导游结束-->
                        </div>
                        <!--产品升级 start-->
                        <!--产品升级 end-->
                        <!--费用说明 start-->

                        <div class="pkg-detail-infor feiyong" id="fysm" tabrel="fysm">
                            <h2 class="detail-title">
                                <span>费用说明</span>
                            </h2>
                        <#list priceTypeList as priceType>
                            <div id="fysm${priceType.id}" <#if priceType_index gt 0>style="display: none;"</#if>
                                 class="fysm_div">
                                <#if priceType.quoteContainDesc>
                                    <div class="detail-sub-title">
                                        <i></i>
                                        费用包含
                                    </div>
                                    <div class="contract_con">
                                    ${priceType.quoteContainDesc}
                                    </div>
                                </#if>
                                <#if priceType.quoteNoContainDesc>
                                    <div class="detail-sub-title">
                                        <i></i>
                                        费用不包含
                                    </div>
                                    <div class="contract_con">
                                    ${priceType.quoteNoContainDesc}
                                    </div>
                                </#if>
                            </div>
                        </#list>
                        </div>
                        <!--费用说明 end-->
                        <!--预订须知 start-->
                        <div class="pkg-detail-infor importantnotice" id="ydxz" tabrel="ydxz">
                            <h2 class="detail-title">
                                <span>预订须知</span>
                            </h2>
                            <div class="xz_pic">
                                <img width="938" height="163" src="/images/line/ydxz.png" class="datalazyload">
                            </div>
                            <#if lineExplain.orderContext>
                                <div class="detail-sub-title">
                                    <i></i>
                                    预订须知
                                </div>
                                <div class="contract_con">
                                ${lineExplain.orderContext}
                                </div>
                            </#if>
                            <#if lineExplain.tripNotice>
                                <div class="detail-sub-title">
                                    <i></i>
                                    出行须知
                                </div>
                                <div class="contract_con">
                                ${lineExplain.tripNotice}
                                </div>
                            </#if>
                            <#if lineExplain.specialLimit>
                                <div class="detail-sub-title">
                                    <i></i>
                                    特殊限制
                                </div>
                                <div class="contract_con">
                                ${lineExplain.specialLimit}
                                </div>
                            </#if>
                            <#if lineExplain.signWay>
                                <div class="detail-sub-title">
                                    <i></i>
                                    签约方式
                                </div>
                                <div class="contract_con">
                                ${lineExplain.signWay}
                                </div>
                            </#if>
                            <#if lineExplain.payWay>
                                <div class="detail-sub-title">
                                    <i></i>
                                    付款方式
                                </div>
                                <div class="contract_con">
                                ${lineExplain.payWay}
                                </div>
                            </#if>
                            <#if lineExplain.breachTip>
                                <div class="detail-sub-title">
                                    <i></i>
                                    违约责任提示
                                </div>
                                <div class="contract_con">
                                ${lineExplain.breachTip}
                                </div>
                            </#if>
                            <div class="detail-sub-title hide deadlineDate" style="display: block;">
                                <i></i>
                                报名截止日期
                            </div>
                            <input type="hidden" value="${line.preOrderDay}" id="preOrderDay"/>
                            <input type="hidden" value="${line.suggestOrderHour}" id="suggestOrderHour"/>
                            <div id="deadlineDate" class="contract_con hide deadlineDate" style="display: block; height: 50px;"></div>
                            <span id="cdate_more" style="cursor: pointer;" class="f_4e9700 hide">&nbsp;&nbsp;&nbsp;查看更多</span>
                        </div>
                        <!--预订须知 end-->
                        <!--游客点评 start-->
                        <#--<div class="pkg-detail-infor common_remark remark_1000" id="dpjl" tabrel="dpjl">-->
                            <#--<h2 class="detail-title">-->
                                <#--<span>游客点评</span>-->
                            <#--</h2>-->

                            <#--<div class="common_remark" id="remarkFlag" style="width: 940px; padding-top: 10px;"><h2-->
                                    <#--id="dpjl" class="detail_h2"></h2>-->

                                <#--<div class="detail_infor">-->
                                    <#--<div class="comments_box">-->
                                        <#--<div class="three_cols clearfix">-->
                                            <#--<div class="col_1">-->
                                                <#--<div class="comment_num f_yh"><em>95%</em></div>-->
                                                <#--<input type="hidden" value="100" id="levelAll">-->

                                                <#--<p class="des">满意度</p></div>-->
                                            <#--<div class="col_2">-->
                                                <#--<div class="prod_dianping"><p>点评来自<em>3279</em>位游客真实旅游感受</p>-->

                                                    <#--<p>本产品共发放<em>39778</em>元点评现金</p></div>-->
                                            <#--</div>-->
                                            <#--<div class="col_3">-->
                                                <#--<div class="rep_comment"><p>出游回来可点评产品</p>-->

                                                    <#--<p class="btn"><a href="https://i.tuniu.com/list" target="_blank">发表点评</a>-->
                                                    <#--</p></div>-->
                                            <#--</div>-->
                                        <#--</div>-->
                                        <#--<div class="hot_style clearfix"><h3>热门出游方式: </h3>-->
                                            <#--<ul class="prod_classify">-->
                                                <#--<li class="traVlTypeSwitch" typid="3"-->
                                                    <#--onclick="traVlTypeSwitchClick(this);"><a href="javascript:;">情侣/朋友(1151)</a><i-->
                                                        <#--class="style_checkup"></i></li>-->
                                                <#--<li class="traVlTypeSwitch" typid="2"-->
                                                    <#--onclick="traVlTypeSwitchClick(this);"><a href="javascript:;">家庭出游(1761)</a><i-->
                                                        <#--class="style_checkup"></i></li>-->
                                                <#--<li class="traVlTypeSwitch" typid="5"-->
                                                    <#--onclick="traVlTypeSwitchClick(this);"><a href="javascript:;">代人预订(45)</a><i-->
                                                        <#--class="style_checkup"></i></li>-->
                                                <#--<li class="traVlTypeSwitch" typid="4"-->
                                                    <#--onclick="traVlTypeSwitchClick(this);"><a href="javascript:;">独自出游(76)</a><i-->
                                                        <#--class="style_checkup"></i></li>-->
                                            <#--</ul>-->
                                        <#--</div>-->
                                        <#--<ul class="fliter_comment clearfix">-->
                                            <#--<li class="remarkSwitch" onclick="remarkSwitchClick(this);"><span-->
                                                    <#--class="current">全部点评<em>(3279)</em></span>|-->
                                            <#--</li>-->
                                            <#--<li class="remarkSwitch" onclick="remarkSwitchClick(this);" grade="3"><span>满意<em>(3178)</em></span>|-->
                                            <#--</li>-->
                                            <#--<li class="remarkSwitch" onclick="remarkSwitchClick(this);" grade="2"><span>一般<em>(73)</em></span>|-->
                                            <#--</li>-->
                                            <#--<li class="remarkSwitch" onclick="remarkSwitchClick(this);" grade="0"><span>不满意<em>(28)</em></span>|-->
                                            <#--</li>-->
                                            <#--<li class="filter_by_pic"><input type="checkbox" class="" id="fliterByPic"-->
                                                                             <#--onclick="filterByPicClick(this);"><label-->
                                                    <#--for="fliterByPic">有照片</label></li>-->
                                        <#--</ul>-->
                                        <#--<div class="no_comment_to_rep"></div>-->
                                        <#--<ul class="comment_lists">-->
                                            <#--<li class="comment_li essence">-->
                                                <#--<div class="icon_essence"></div>-->
                                                <#--<dl class="clearfix">-->
                                                    <#--<dt>-->
                                                    <#--<div class="trav_pic">-->
                                                        <#--<div class="inner_trav_pic"><a-->
                                                                <#--href="http://www.tuniu.com/person/CEA9C74B6A374D8A20DC606C88C5E549"-->
                                                                <#--target="_blank"><img-->
                                                                <#--src="http://img3.tuniucdn.com/img/20140811/usercenter_lxl/default.gif"-->
                                                                <#--alt="8063621185"></a></div>-->
                                                        <#--<span class="trav_pic_level trav_pic_level4"></span></div>-->
                                                    <#--<p class="trav_name"><a-->
                                                            <#--href="http://www.tuniu.com/person/CEA9C74B6A374D8A20DC606C88C5E549"-->
                                                            <#--target="_blank">8063621185</a></p>-->

                                                    <#--<p class="trav_type">情侣/朋友</p></dt>-->
                                                    <#--<dd>-->
                                                        <#--<div class="clists_main_cont"-->
                                                             <#--data="[五一]<海南-三亚-红树林7日游>万人出游纯玩线，6晚五星红树林度假世界，5A景点送千古情，赠四大名菜">-->
                                                            <#--<p class="clists_words clearfix"><span-->
                                                                    <#--class="icon_manyi"></span><span>导游服务<em>满意</em></span><span>行程安排<em>满意</em></span><span>餐饮住宿<em>满意</em></span><span>旅行交通<em>满意</em></span>-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_detail">-->
                                                                <#--回来好久了，一直没时间点评。先说一下，这次的旅游让我非常开心。首先是导游阿宁，姜安宁他人非常好，脾气好，人也很亲和，总是为我们这帮团友考虑。行程安排也很好，玩的不累，同时玩的也很好，有更多的时间让我们了解三亚的人文历史，住宿就更加好了，红树林度假世界真的非常棒，可用高大尚来形容，如果有要来三亚玩的，个人建议就住红树林，真的很好，环境特别好，而且住在红树林度假世界吃饭，逛街，看电影，都不用离开酒店。总之这次玩的真的很开心。我是在途牛参团的，比携程或是去哪都便宜，尤其是在会员日更有惊喜。</p>-->
                                                        <#--</div>-->
                                                        <#--<dl class="clearfix comment_from">-->
                                                            <#--<dt><a href="/recall/16112901" target="_blank">2016-05-22-->
                                                                <#--10:33</a> &nbsp;&nbsp;来自<span class="from_app"><a-->
                                                                    <#--href="http://www.tuniu.com/static/mobile/"-->
                                                                    <#--target="_blank">途牛旅游APP</a></span></dt>-->
                                                        <#--</dl>-->
                                                    <#--</dd>-->
                                                <#--</dl>-->
                                            <#--</li>-->
                                            <#--<li class="comment_li essence">-->
                                                <#--<div class="icon_essence"></div>-->
                                                <#--<dl class="clearfix">-->
                                                    <#--<dt>-->
                                                    <#--<div class="trav_pic">-->
                                                        <#--<div class="inner_trav_pic"><a-->
                                                                <#--href="http://www.tuniu.com/person/94122F831A8D45EF6A9443BB99463AA2"-->
                                                                <#--target="_blank"><img-->
                                                                <#--src="http://m.tuniucdn.com/filebroker/cdn/prd/f4/56/f4569272a75c17f61aab349570c2b095_w90_h90_c1_t0.jpg"-->
                                                                <#--alt="6825820697"></a></div>-->
                                                        <#--<span class="trav_pic_level trav_pic_level3"></span></div>-->
                                                    <#--<p class="trav_name"><a-->
                                                            <#--href="http://www.tuniu.com/person/94122F831A8D45EF6A9443BB99463AA2"-->
                                                            <#--target="_blank">6825820697</a></p>-->

                                                    <#--<p class="trav_type">情侣/朋友</p></dt>-->
                                                    <#--<dd>-->
                                                        <#--<div class="clists_main_cont"-->
                                                             <#--data="<海南-三亚-红树林6日游>万人出游纯玩线，5晚五星红树林度假世界，5A景点送千古情，赠四大名菜"><p-->
                                                                <#--class="clists_words clearfix"><span-->
                                                                <#--class="icon_manyi"></span><span>导游服务<em>满意</em></span><span>行程安排<em>满意</em></span><span>餐饮住宿<em>满意</em></span><span>旅行交通<em>满意</em></span>-->
                                                        <#--</p>-->

                                                            <#--<p class="comment_detail">-->
                                                                <#--非常完美的一次海南之旅！一个团30几个人玩的很开心，相处很融洽，这个行程非常适合情侣蜜月、亲子、和带着爸妈出来玩，属于度假型的旅游，海南的几个精华景点都包含了，去三亚就要该这么玩！</p>-->

                                                            <#--<p class="comment_l"><span>导游服务： </span>导游胡姐（胡倩瑜），体贴细心，每天在车上一直介绍海南的景点和风土人情，每到一个景点都会告诉一些注意事项，路上还给我们每个人买水果吃，一直都从游客的角度为我们着想，不愧是途牛的十佳导游！32个赞！-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>行程安排： </span>行程安排非常轻松，非常合理，海南的主要景点都去到了，蜈支洲整整一天的时间，水暖沙白，不愧为“中国的马尔代夫”，建议办一个水上项目的套票，各种潜水、快艇、拖伞、岛上自助餐等等只要1399，单独报两个项目就差不多这个价格了，很划算！全程没有任何的购物店，充足的游玩时间，细细的品味三亚！千古情景区是不含表演的，自费了200多块钱去看表演，真的很值得一看，以前看过丽江千古情，这次的三亚千古情一样精彩！名副其实的“一生必看的演出”！-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>餐饮住宿： </span>住的是红树林度假世界，酒店很大气很豪华，有5星级标准，早餐很丰盛，有很多个泳池，而且有充足的时间可以享受在酒店的时光，酒店就在三亚湾附近，打车到三亚湾海边只要起步价，到大东海第一市场也只要10几块钱，交通很方便！餐饮方面，这次吃的都是景区的自助和传说中的海南“四大名菜”，还有一顿自费的海鲜大餐，我觉得在三亚这样一个高消费城市这个价格能吃到这些已经很不错了，不用怕自己出去被宰了，海鲜大餐很值得，满满一大桌，吃不完的！已上图！-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>旅行交通： </span>全程大巴车，导游每次集合时候都会给司机打电话提前打开空调！这点做的很棒！司机师傅也很热情，每天都有矿泉水送，赞！-->
                                                            <#--</p></div>-->
                                                        <#--<div class="pic_lists clearfix">-->
                                                            <#--<div class="sp_prev sp_grey"></div>-->
                                                            <#--<div class="sp_content">-->
                                                                <#--<ul class="slidy_pic clearfix"-->
                                                                    <#--timedata="2016-05-20 14:57" style="width: 891px;">-->
                                                                    <#--<li titledata="红树林度假世界" class="cur"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuIYpjQAAG_o33ONOoAAGGkAGR3QUAAb-7773_w90_h90_c1_t0_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuIYpjQAAG_o33ONOoAAGGkAGR3QUAAb-7773_w90_h90_c1_t0_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="海鲜大餐"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EFc-sWqICDi5AAIenNmdgxgAAGGkAGPvlEAAh60631_w90_h90_c1_t0_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EFc-sWqICDi5AAIenNmdgxgAAGGkAGPvlEAAh60631_w90_h90_c1_t0_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="三亚千古情表演"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EFc-sWqIU_uuAAGkFBLIPRAAAGGkAGOGiUAAaQs695_w90_h90_c1_t0_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EFc-sWqIU_uuAAGkFBLIPRAAAGGkAGOGiUAAaQs695_w90_h90_c1_t0_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="南山海上观音"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuIPttaAACEz3BpH4MAAGGkAGNlT4AAITn020_w90_h90_c1_t0_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuIPttaAACEz3BpH4MAAGGkAGNlT4AAITn020_w90_h90_c1_t0_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="蜈支洲岛"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuIavIpAADd-APqoJsAAGGkAGMty4AAN4Q295_w90_h90_c1_t0_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuIavIpAADd-APqoJsAAGGkAGMty4AAN4Q295_w90_h90_c1_t0_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="海鲜大餐"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EFc-sWqIG-T7AAHrnot_S38AAGGkAGKy3gAAeu2717_w90_h90_c1_t0_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EFc-sWqIG-T7AAHrnot_S38AAGGkAGKy3gAAeu2717_w90_h90_c1_t0_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="呀诺达自助餐"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuIUC84AAKmqCyPZMIAAGGkAGIJLgAAqbA766_w90_h90_c1_t0_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuIUC84AAKmqCyPZMIAAGGkAGIJLgAAqbA766_w90_h90_c1_t0_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="千古情景区"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EFc-sWqIOZHlAAFOxBXPs2gAAGGkAGG1dwAAU7c498_w90_h90_c1_t0_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EFc-sWqIOZHlAAFOxBXPs2gAAGGkAGG1dwAAU7c498_w90_h90_c1_t0_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="千古情表演"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EFc-sWqIb6DTAACpXfr8Ot0AAGGkAGGLGcAAKl1721_w90_h90_c1_t0_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EFc-sWqIb6DTAACpXfr8Ot0AAGGkAGGLGcAAKl1721_w90_h90_c1_t0_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="海鲜大餐"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuIBagsAAH_usKlbu4AAGGkAGELJUAAf_S773_w90_h90_c1_t0_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuIBagsAAH_usKlbu4AAGGkAGELJUAAf_S773_w90_h90_c1_t0_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="蜈支洲岛"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuISQhuAAHPr4Ym-qAAAGGkAGCXM4AAc_H216_w90_h90_c1_t0_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/D3/3E/Cii9EVc-sWuISQhuAAHPr4Ym-qAAAGGkAGCXM4AAc_H216_w90_h90_c1_t0_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                <#--</ul>-->
                                                            <#--</div>-->
                                                            <#--<div class="sp_next"></div>-->
                                                        <#--</div>-->
                                                        <#--<dl class="clearfix comment_from">-->
                                                            <#--<dt><a href="/recall/16230568" target="_blank">2016-05-20-->
                                                                <#--14:57</a> &nbsp;&nbsp;来自途牛电脑版-->
                                                            <#--</dt>-->
                                                        <#--</dl>-->
                                                        <#--<div class="comment_prec myorder_prec_box">-->
                                                            <#--<div class="comment_prec_box"><p class="cp_tt">点评赠送</p>-->

                                                                <#--<p class=""><span>返现</span> ¥5</p></div>-->
                                                        <#--</div>-->
                                                    <#--</dd>-->
                                                <#--</dl>-->
                                            <#--</li>-->
                                            <#--<li class="comment_li essence">-->
                                                <#--<div class="icon_essence"></div>-->
                                                <#--<dl class="clearfix">-->
                                                    <#--<dt>-->
                                                    <#--<div class="trav_pic">-->
                                                        <#--<div class="inner_trav_pic"><a-->
                                                                <#--href="http://www.tuniu.com/person/BB8C5C99359873D18EE23D0D057342B8"-->
                                                                <#--target="_blank"><img-->
                                                                <#--src="http://img3.tuniucdn.com/img/20140811/usercenter_lxl/default.gif"-->
                                                                <#--alt="tuniu518128"></a></div>-->
                                                        <#--<span class="trav_pic_level trav_pic_level3"></span></div>-->
                                                    <#--<p class="trav_name"><a-->
                                                            <#--href="http://www.tuniu.com/person/BB8C5C99359873D18EE23D0D057342B8"-->
                                                            <#--target="_blank">tuniu518128</a></p>-->

                                                    <#--<p class="trav_type">家庭出游</p></dt>-->
                                                    <#--<dd>-->
                                                        <#--<div class="clists_main_cont"-->
                                                             <#--data="[五一]<海南-三亚-红树林5日游>途牛设计纯玩线，4晚五星红树林度假世界，5A景点送千古情，赠四大名菜">-->
                                                            <#--<p class="clists_words clearfix"><span-->
                                                                    <#--class="icon_manyi"></span><span>导游服务<em>满意</em></span><span>行程安排<em>满意</em></span><span>餐饮住宿<em>满意</em></span><span>旅行交通<em>满意</em></span>-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_detail">-->
                                                                <#--三亚真是一个碧海蓝天的地方，美食美景，让人应接不暇！整个行程既有个人活动，又有团队出行，行程安排的非常合理！下次再去三亚，我还会选择这个路线。</p>-->

                                                            <#--<p class="comment_l"><span>导游服务： </span>王强导游是一个很有学问的人，每次转程在车上时，导游都会给我们讲三亚的历史和其他大家都很敢兴趣的话题，一路上都不休息，还会给我们介绍三亚哪里有好玩的地方，好吃的地方，在哪里可以买到正宗的三亚特产，很赞！-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>行程安排： </span>安排的很合理，第一天我们自由活动，可以好好适应一下三亚的气候，熟悉酒店。接下来的3天行程安排的很好，给我们足够的游玩时间，全程都未我们安排好车辆，跟团游很方便！-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>餐饮住宿： </span>三亚挖红树林度假酒店环境很好，有室外泳池，沙滩，酒店内有免费观光车，绿色植被面积很大，早餐厅也很丰盛，中西餐都有，牛奶和果汁都是新鲜瓶装的，很有档次！-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>旅行交通： </span>导游车司机师傅很好，开车很稳，每天车接车送，很方便，很准时！-->
                                                            <#--</p></div>-->
                                                        <#--<dl class="clearfix comment_from">-->
                                                            <#--<dt><a href="/recall/15963955" target="_blank">2016-04-25-->
                                                                <#--17:55</a> &nbsp;&nbsp;来自途牛电脑版-->
                                                            <#--</dt>-->
                                                        <#--</dl>-->
                                                        <#--<div class="comment_prec myorder_prec_box">-->
                                                            <#--<div class="comment_prec_box"><p class="cp_tt">点评赠送</p>-->

                                                                <#--<p class=""><span>返现</span> ¥20</p></div>-->
                                                        <#--</div>-->
                                                    <#--</dd>-->
                                                <#--</dl>-->
                                            <#--</li>-->
                                            <#--<li class="comment_li essence">-->
                                                <#--<div class="icon_essence"></div>-->
                                                <#--<dl class="clearfix">-->
                                                    <#--<dt>-->
                                                    <#--<div class="trav_pic">-->
                                                        <#--<div class="inner_trav_pic"><a-->
                                                                <#--href="http://www.tuniu.com/person/EFCAB0FCC67EE121771CCBD03C29D70F"-->
                                                                <#--target="_blank"><img-->
                                                                <#--src="http://img3.tuniucdn.com/img/20140811/usercenter_lxl/default.gif"-->
                                                                <#--alt="8066238528"></a></div>-->
                                                        <#--<span class="trav_pic_level trav_pic_level4"></span></div>-->
                                                    <#--<p class="trav_name"><a-->
                                                            <#--href="http://www.tuniu.com/person/EFCAB0FCC67EE121771CCBD03C29D70F"-->
                                                            <#--target="_blank">8066238528</a></p>-->

                                                    <#--<p class="trav_type">家庭出游</p></dt>-->
                                                    <#--<dd>-->
                                                        <#--<div class="clists_main_cont"-->
                                                             <#--data="<三亚双飞5日游>万人出游纯玩线，4晚五星红树林度假世界，5A景点送千古情，赠四大名菜"><p-->
                                                                <#--class="clists_words clearfix"><span-->
                                                                <#--class="icon_manyi"></span><span>导游服务<em>满意</em></span><span>行程安排<em>满意</em></span><span>餐饮住宿<em>满意</em></span><span>旅行交通<em>满意</em></span>-->
                                                        <#--</p>-->

                                                            <#--<p class="comment_detail">-->
                                                                <#--第一次在途牛网抱团出游，感觉很不错，阿擎导游一路讲解很负责任，消费自由，真的是纯玩团，酒店环境也很好，虽然自己带着孩子很多项目没去体验，但玩的还是很开心，远地出游下次还是会选择途牛</p>-->
                                                        <#--</div>-->
                                                        <#--<dl class="clearfix comment_from">-->
                                                            <#--<dt><a href="/recall/15952040" target="_blank">2016-04-24-->
                                                                <#--13:21</a> &nbsp;&nbsp;来自<span class="from_app"><a-->
                                                                    <#--href="http://www.tuniu.com/static/mobile/"-->
                                                                    <#--target="_blank">途牛旅游APP</a></span></dt>-->
                                                        <#--</dl>-->
                                                        <#--<div class="comment_prec myorder_prec_box">-->
                                                            <#--<div class="comment_prec_box"><p class="cp_tt">点评赠送</p>-->

                                                                <#--<p class=""><span>返现</span> ¥20</p></div>-->
                                                        <#--</div>-->
                                                    <#--</dd>-->
                                                <#--</dl>-->
                                            <#--</li>-->
                                            <#--<li class="comment_li essence">-->
                                                <#--<div class="icon_essence"></div>-->
                                                <#--<dl class="clearfix">-->
                                                    <#--<dt>-->
                                                    <#--<div class="trav_pic">-->
                                                        <#--<div class="inner_trav_pic"><a-->
                                                                <#--href="http://www.tuniu.com/person/C39B03735E40828EF21AA9641538FFBD"-->
                                                                <#--target="_blank"><img-->
                                                                <#--src="http://img3.tuniucdn.com/img/20140811/usercenter_lxl/default.gif"-->
                                                                <#--alt="8066171673"></a></div>-->
                                                        <#--<span class="trav_pic_level trav_pic_level4"></span></div>-->
                                                    <#--<p class="trav_name"><a-->
                                                            <#--href="http://www.tuniu.com/person/C39B03735E40828EF21AA9641538FFBD"-->
                                                            <#--target="_blank">8066171673</a></p>-->

                                                    <#--<p class="trav_type">家庭出游</p></dt>-->
                                                    <#--<dd>-->
                                                        <#--<div class="clists_main_cont"-->
                                                             <#--data="<三亚双飞5日游>万人出游纯玩线，4晚五星红树林度假世界，5A景点送千古情，赠四大名菜"><p-->
                                                                <#--class="clists_words clearfix"><span-->
                                                                <#--class="icon_manyi"></span><span>导游服务<em>满意</em></span><span>行程安排<em>满意</em></span><span>餐饮住宿<em>满意</em></span><span>旅行交通<em>满意</em></span>-->
                                                        <#--</p>-->

                                                            <#--<p class="comment_detail">-->
                                                                <#--16号在途牛上看的线路，17号出发，真的是说走就走，在外面看了其他旅行社价格比途牛高并且住宿条件也不好，16号晚上导游阿荣就已经跟我联系并且告知了接机的一些注意事项，并且告知了天气，一些注意携带的用品，导游阿荣服务很细心，在三亚的几天坐什么公交车，哪里吃海鲜比较靠谱，私下问导游，阿荣都一一回复了，给阿荣点赞下飞机就有工作人员负责接送，不得不说的是住宿，我们入住的是红树林度假酒店里的椰林酒店，酒店真的是高大上，服务一流，酒店卫生条件也很好，游泳池，儿童乐园都有，配套设施很多，我们晚上十一点的飞机，送机师傅接送也很及时，还帮我们提行李，整个行程很舒适，这次旅游很开心，第一次在途牛订团，以后还会继续在途牛跟团，已经推荐给朋友，为途牛点赞，为整个行程的工作人员点赞</p>-->
                                                        <#--</div>-->
                                                        <#--<dl class="clearfix comment_from">-->
                                                            <#--<dt><a href="/recall/15943091" target="_blank">2016-04-22-->
                                                                <#--18:33</a> &nbsp;&nbsp;来自<span class="from_app"><a-->
                                                                    <#--href="http://www.tuniu.com/static/mobile/"-->
                                                                    <#--target="_blank">途牛旅游APP</a></span></dt>-->
                                                        <#--</dl>-->
                                                        <#--<div class="comment_prec myorder_prec_box">-->
                                                            <#--<div class="comment_prec_box"><p class="cp_tt">点评赠送</p>-->

                                                                <#--<p class=""><span>返现</span> ¥40</p></div>-->
                                                        <#--</div>-->
                                                    <#--</dd>-->
                                                <#--</dl>-->
                                            <#--</li>-->
                                            <#--<li class="comment_li essence">-->
                                                <#--<div class="icon_essence"></div>-->
                                                <#--<dl class="clearfix">-->
                                                    <#--<dt>-->
                                                    <#--<div class="trav_pic">-->
                                                        <#--<div class="inner_trav_pic"><a-->
                                                                <#--href="http://www.tuniu.com/person/BFACFD44A1E8FFF436DF4BA879FD4502"-->
                                                                <#--target="_blank"><img-->
                                                                <#--src="http://img3.tuniucdn.com/img/20140811/usercenter_lxl/default.gif"-->
                                                                <#--alt="8061511997"></a></div>-->
                                                        <#--<span class="trav_pic_level trav_pic_level3"></span></div>-->
                                                    <#--<p class="trav_name"><a-->
                                                            <#--href="http://www.tuniu.com/person/BFACFD44A1E8FFF436DF4BA879FD4502"-->
                                                            <#--target="_blank">8061511997</a></p>-->

                                                    <#--<p class="trav_type">家庭出游</p></dt>-->
                                                    <#--<dd>-->
                                                        <#--<div class="clists_main_cont"-->
                                                             <#--data="[五一]<海南三亚-红树林5日游>途牛自组团纯玩0利润，4晚五星红树林度假世界，5A景点送千古情，赠四大名菜">-->
                                                            <#--<p class="clists_words clearfix"><span-->
                                                                    <#--class="icon_manyi"></span><span>导游服务<em>满意</em></span><span>行程安排<em>满意</em></span><span>餐饮住宿<em>满意</em></span><span>旅行交通<em>满意</em></span>-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_detail">-->
                                                                <#--总体来说比较满意，整个行程没有任何强制性消费，导游人很好，跟我一样是个90后，但很稳重，做事比较靠谱，很喜欢，而且途牛价格很实惠，跟其他团友对比咯价格，我是最便宜的。满意，下次有机会出玩还会选择途牛</p>-->
                                                        <#--</div>-->
                                                        <#--<dl class="clearfix comment_from">-->
                                                            <#--<dt><a href="/recall/15937165" target="_blank">2016-04-21-->
                                                                <#--21:17</a> &nbsp;&nbsp;来自<span class="from_app"><a-->
                                                                    <#--href="http://www.tuniu.com/static/mobile/"-->
                                                                    <#--target="_blank">途牛旅游APP</a></span></dt>-->
                                                        <#--</dl>-->
                                                        <#--<div class="comment_prec myorder_prec_box">-->
                                                            <#--<div class="comment_prec_box"><p class="cp_tt">点评赠送</p>-->

                                                                <#--<p class=""><span>返现</span> ¥40</p></div>-->
                                                        <#--</div>-->
                                                    <#--</dd>-->
                                                <#--</dl>-->
                                            <#--</li>-->
                                            <#--<li class="comment_li essence">-->
                                                <#--<div class="icon_essence"></div>-->
                                                <#--<dl class="clearfix">-->
                                                    <#--<dt>-->
                                                    <#--<div class="trav_pic">-->
                                                        <#--<div class="inner_trav_pic"><a-->
                                                                <#--href="http://www.tuniu.com/person/F73B4B119F66003C2A617FFE8971F3DC"-->
                                                                <#--target="_blank"><img-->
                                                                <#--src="http://img3.tuniucdn.com/img/20140811/usercenter_lxl/default.gif"-->
                                                                <#--alt="8061378669"></a></div>-->
                                                        <#--<span class="trav_pic_level trav_pic_level2"></span></div>-->
                                                    <#--<p class="trav_name"><a-->
                                                            <#--href="http://www.tuniu.com/person/F73B4B119F66003C2A617FFE8971F3DC"-->
                                                            <#--target="_blank">8061378669</a></p>-->

                                                    <#--<p class="trav_type">家庭出游</p></dt>-->
                                                    <#--<dd>-->
                                                        <#--<div class="clists_main_cont"-->
                                                             <#--data="[五一]<海南三亚-红树林5日游>途牛自组团纯玩0利润，4晚五星红树林度假世界，5A景点送千古情，赠四大名菜">-->
                                                            <#--<p class="clists_words clearfix"><span-->
                                                                    <#--class="icon_manyi"></span><span>导游服务<em>满意</em></span><span>行程安排<em>满意</em></span><span>餐饮住宿<em>满意</em></span><span>旅行交通<em>满意</em></span>-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_detail">-->
                                                                <#--此次旅行很满意，吃的住的比较好，还有真的是纯玩的，而且各个景点的导游讲的也很详细，服务态度好，途牛可以信任。暑假还会去北京，我会选择途牛。</p>-->

                                                            <#--<p class="comment_l"><span>导游服务： </span>我们的导游开朗大方，美丽善良，全程服务态度很好，而且很贴心，有耐性，之前还担心遇到不好的导游会受气，现在一下子让我改变了对导游的看法，很满意！-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>行程安排： </span>行程安排比较合理，全程该休息的休息，该玩的玩！-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>餐饮住宿： </span>这次旅行最满意的就是住的酒店了，绝对高大上的，和介绍的一样，一点也不夸张，酒店的早餐也不错，其它中餐晚餐吃的也挺好的。-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>旅行交通： </span>途牛很体贴，大巴都是直接到酒店门口接送的。-->
                                                            <#--</p></div>-->
                                                        <#--<div class="pic_lists clearfix">-->
                                                            <#--<div class="sp_prev sp_grey" style="display: none;"></div>-->
                                                            <#--<div class="sp_content">-->
                                                                <#--<ul class="slidy_pic clearfix"-->
                                                                    <#--timedata="2016-04-19 15:18" style="width: 324px;">-->
                                                                    <#--<li titledata="" class="cur"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/7C/9C/Cii9EFcV28WIOybEAAHOOfBWYFwAADf_wJqGv4AAc5R882_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/7C/9C/Cii9EFcV28WIOybEAAHOOfBWYFwAADf_wJqGv4AAc5R882_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata=""><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/7C/9C/Cii9EVcV28KIAhU6AAGUfM9QygEAADf_wJYWgAAAZSU085_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/7C/9C/Cii9EVcV28KIAhU6AAGUfM9QygEAADf_wJYWgAAAZSU085_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata=""><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/7C/9C/Cii9EVcV28KIG-ApAAFTvdQipxYAADf_wJRUGkAAVPV329_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/7C/9C/Cii9EVcV28KIG-ApAAFTvdQipxYAADf_wJRUGkAAVPV329_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata=""><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/7C/9C/Cii9EVcV28CIOHN-AAF56US9qccAADf_wJLWFgAAXoB008_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/7C/9C/Cii9EVcV28CIOHN-AAF56US9qccAADf_wJLWFgAAXoB008_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                <#--</ul>-->
                                                            <#--</div>-->
                                                            <#--<div class="sp_next" style="display: none;"></div>-->
                                                        <#--</div>-->
                                                        <#--<dl class="clearfix comment_from">-->
                                                            <#--<dt><a href="/recall/15921086" target="_blank">2016-04-19-->
                                                                <#--15:18</a> &nbsp;&nbsp;来自途牛电脑版-->
                                                            <#--</dt>-->
                                                        <#--</dl>-->
                                                    <#--</dd>-->
                                                <#--</dl>-->
                                            <#--</li>-->
                                            <#--<li class="comment_li essence">-->
                                                <#--<div class="icon_essence"></div>-->
                                                <#--<dl class="clearfix">-->
                                                    <#--<dt>-->
                                                    <#--<div class="trav_pic">-->
                                                        <#--<div class="inner_trav_pic"><a-->
                                                                <#--href="http://www.tuniu.com/person/D9C1631EC603CDD82B8AF226105EFD0D"-->
                                                                <#--target="_blank"><img-->
                                                                <#--src="http://img3.tuniucdn.com/img/20140811/usercenter_lxl/default.gif"-->
                                                                <#--alt="8055335370"></a></div>-->
                                                        <#--<span class="trav_pic_level trav_pic_level3"></span></div>-->
                                                    <#--<p class="trav_name"><a-->
                                                            <#--href="http://www.tuniu.com/person/D9C1631EC603CDD82B8AF226105EFD0D"-->
                                                            <#--target="_blank">8055335370</a></p>-->

                                                    <#--<p class="trav_type">家庭出游</p></dt>-->
                                                    <#--<dd>-->
                                                        <#--<div class="clists_main_cont"-->
                                                             <#--data="<海南三亚-红树林5日游>途牛自组团纯玩0利润，4晚五星红树林度假世界，5A景点送千古情，赠四大名菜">-->
                                                            <#--<p class="clists_words clearfix"><span-->
                                                                    <#--class="icon_manyi"></span><span>导游服务<em>满意</em></span><span>行程安排<em>满意</em></span><span>餐饮住宿<em>满意</em></span><span>旅行交通<em>满意</em></span>-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_detail">-->
                                                                <#--导游服务：赵纪发接待员，很幽默负责，经验丰富，还给我们讲了很多需要注意的地方，确实不错。-->
                                                                <#--行程安排：我们是五天四晚的行程，一天2-3个景点，时间很充裕，玩得很开心，特别是阿发推荐的千古情大型演出，-->
                                                                <#--非常值得一看，非常棒！-->
                                                                <#--餐饮住宿：住的是红树林的椰林酒店，五星级酒店，酒店很大，酒店园区很漂亮，是东南亚风格的，风景非常不错，住的很舒服，免费的大泳池，宝宝和老公玩得很开心，还有丰盛的自助早餐，特别好吃。-->
                                                                <#--旅行交通：全程的交通都是一辆小巴车，我们是个小团，人只有十个大人加一个我儿子，大家都是年轻人，都非常好相处，一路上都坐同一个位置，每人还有一瓶水，非常满意！-->
                                                                <#--总之，这次我们一家三口的生日之旅，非常感谢阿发和其他四个家庭一路的陪伴，我和老公还有宝宝玩的非常开心！</p>-->
                                                        <#--</div>-->
                                                        <#--<dl class="clearfix comment_from">-->
                                                            <#--<dt><a href="/recall/15746789" target="_blank">2016-04-19-->
                                                                <#--13:11</a> &nbsp;&nbsp;来自<span class="from_app"><a-->
                                                                    <#--href="http://www.tuniu.com/static/mobile/"-->
                                                                    <#--target="_blank">途牛旅游APP</a></span></dt>-->
                                                        <#--</dl>-->
                                                        <#--<div class="comment_prec myorder_prec_box">-->
                                                            <#--<div class="comment_prec_box"><p class="cp_tt">点评赠送</p>-->

                                                                <#--<p class=""><span>返现</span> ¥20</p></div>-->
                                                        <#--</div>-->
                                                    <#--</dd>-->
                                                <#--</dl>-->
                                            <#--</li>-->
                                            <#--<li class="comment_li essence">-->
                                                <#--<div class="icon_essence"></div>-->
                                                <#--<dl class="clearfix">-->
                                                    <#--<dt>-->
                                                    <#--<div class="trav_pic">-->
                                                        <#--<div class="inner_trav_pic"><a-->
                                                                <#--href="http://www.tuniu.com/person/520EFC2BAA95E0D75E73B6B7B9F00788"-->
                                                                <#--target="_blank"><img-->
                                                                <#--src="http://img3.tuniucdn.com/img/20140811/usercenter_lxl/default.gif"-->
                                                                <#--alt="8059852618"></a></div>-->
                                                        <#--<span class="trav_pic_level trav_pic_level3"></span></div>-->
                                                    <#--<p class="trav_name"><a-->
                                                            <#--href="http://www.tuniu.com/person/520EFC2BAA95E0D75E73B6B7B9F00788"-->
                                                            <#--target="_blank">8059852618</a></p>-->

                                                    <#--<p class="trav_type">家庭出游</p></dt>-->
                                                    <#--<dd>-->
                                                        <#--<div class="clists_main_cont"-->
                                                             <#--data="<海南三亚-红树林5日游>途牛自组团纯玩0利润，4晚五星红树林度假世界，5A景点送千古情，赠四大名菜">-->
                                                            <#--<p class="clists_words clearfix"><span-->
                                                                    <#--class="icon_manyi"></span><span>导游服务<em>满意</em></span><span>行程安排<em>满意</em></span><span>餐饮住宿<em>满意</em></span><span>旅行交通<em>满意</em></span>-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_detail">-->
                                                                <#--这是一次愉快的旅游，老人孩子都很开心。总体感受总结几点：1、行程不赶，选取的都是最具特点的景区。2、没有购物和乱推销乱购物的情况。3、团人数控制很好，整个团大家也很和睦和互相帮助。4、导游阿威帅哥提前把各种细节和注意事项，海南文化都说的非常好。</p>-->

                                                            <#--<p class="comment_l"><span>导游服务： </span>1、超级赞。2、我第一次全面了解海南历史文化风土人情，全靠阿威帅哥的精彩解说，比金牌老师还要厉害。3、我们闺女最后一天嚷着不要回北京，醒了还在说导游叔叔今天怎么不来接我们。4、对于带特产的问题，阿威都介绍我们他自己家用的很好的那种，放心。-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>行程安排： </span>行程不赶，选取的都是最具特点的景区。雨林，千古情，蜈支洲，南山，从山到海，从名俗到历史，从民间到佛教，堪称完美景区安排，严重满足了我本次希望孩子能除了风景之外，体验和认识更多风景内涵的需求。-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>餐饮住宿： </span>五星级酒店，老岳父大人说住的很舒服，小朋友也觉得酒店很好玩，除了可以逛酒店内景还可以玩各种孩子们喜欢的乐园。这次唯一不足的是因为时间的原因没有住上亚龙湾那边的红树林。-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>旅行交通： </span>全城旅游社的接机，送机，每天接送都很舒服，我们孩子特别容易晕车的都没有出现晕车情况，车开的很平稳安全。-->
                                                            <#--</p></div>-->
                                                        <#--<dl class="clearfix comment_from">-->
                                                            <#--<dt><a href="/recall/15731282" target="_blank">2016-04-02-->
                                                                <#--10:22</a> &nbsp;&nbsp;来自途牛电脑版-->
                                                            <#--</dt>-->
                                                        <#--</dl>-->
                                                    <#--</dd>-->
                                                <#--</dl>-->
                                            <#--</li>-->
                                            <#--<li class="comment_li essence">-->
                                                <#--<div class="icon_essence"></div>-->
                                                <#--<dl class="clearfix">-->
                                                    <#--<dt>-->
                                                    <#--<div class="trav_pic">-->
                                                        <#--<div class="inner_trav_pic"><a-->
                                                                <#--href="http://www.tuniu.com/person/5F1FA2494B55C02969EAA2F74543AC25"-->
                                                                <#--target="_blank"><img-->
                                                                <#--src="http://img3.tuniucdn.com/img/20140811/usercenter_lxl/default.gif"-->
                                                                <#--alt="user74645"></a></div>-->
                                                        <#--<span class="trav_pic_level trav_pic_level4"></span></div>-->
                                                    <#--<p class="trav_name"><a-->
                                                            <#--href="http://www.tuniu.com/person/5F1FA2494B55C02969EAA2F74543AC25"-->
                                                            <#--target="_blank">user74645</a></p>-->

                                                    <#--<p class="trav_type">家庭出游</p></dt>-->
                                                    <#--<dd>-->
                                                        <#--<div class="clists_main_cont"-->
                                                             <#--data="<海南三亚南山5日游>4晚五星红树林王国，8大景点0自费，好评如潮"><p-->
                                                                <#--class="clists_words clearfix"><span-->
                                                                <#--class="icon_manyi"></span><span>导游服务<em>满意</em></span><span>行程安排<em>满意</em></span><span>餐饮住宿<em>满意</em></span><span>旅行交通<em>满意</em></span>-->
                                                        <#--</p>-->

                                                            <#--<p class="comment_detail">-->
                                                                <#--这次旅行比较成功，行程上安排比较合理，导游服务的很周到，会教大家相关的游玩方法，即省力又可以游玩的比较尽兴（因为那边的天气对于北方的人来说有点热），把海南主要的景点在3天的行程逛完。这次旅行很放松，不会那么的赶行程。建议大家第一天到达后就自行座酒店的大吧车到亚龙湾海滩去玩上一趟，那里的海滩确实不错，人也没那么多，环境很好（躺椅还没收费），非常不错的一次“途牛之旅”</p>-->

                                                            <#--<p class="comment_l"><span>导游服务： </span>导游服务的很周到，会教给大家好多的方法，即方便了大家又节省了时间，态度很好，点赞！！-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>行程安排： </span>行程安排比较合理，每天差不多3个景点，不多不少，时间刚刚好，回到酒店还可以休闲一下！-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>餐饮住宿： </span>酒店四早餐是自助式的，行程中的团队餐，还可以说的过去，有肉有蔬菜，用导游的话说他替我们选的都是他认为相对还不错的一些地方。-->
                                                            <#--</p>-->

                                                            <#--<p class="comment_l"><span>旅行交通： </span>行程中每天有大巴接送，早上8：30集合，准时接大家开始一天的美好旅程。-->
                                                            <#--</p></div>-->
                                                        <#--<div class="pic_lists clearfix">-->
                                                            <#--<div class="sp_prev sp_grey" style="display: none;"></div>-->
                                                            <#--<div class="sp_content">-->
                                                                <#--<ul class="slidy_pic clearfix"-->
                                                                    <#--timedata="2016-03-31 13:51" style="width: 567px;">-->
                                                                    <#--<li titledata="蜈支洲私人订制外景地" class="cur"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EFb8uX-IGmNvAE85-t5OKfIAAC3KgGOj54ATzoS777_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EFb8uX-IGmNvAE85-t5OKfIAAC3KgGOj54ATzoS777_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="天涯海角"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EVb8uXyIIAQrAC4idxAmFSsAAC3KgFOnrYALiKP990_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EVb8uXyIIAQrAC4idxAmFSsAAC3KgFOnrYALiKP990_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="天涯海角"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EFb8uXqICNBtADi9WxUnAugAAC3KgEBgMYAOL1z110_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EFb8uXqICNBtADi9WxUnAugAAC3KgEBgMYAOL1z110_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="大小洞天"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EFb8uXiIf5KGAECz4WREEfEAAC3KgCzIWIAQLP5399_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EFb8uXiIf5KGAECz4WREEfEAAC3KgCzIWIAQLP5399_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="热带雨林非2外景地"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EVb8uXWIckzHAC7NikUiGigAAC3KgCEU8AALs2i360_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EVb8uXWIckzHAC7NikUiGigAAC3KgCEU8AALs2i360_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="热带雨林非2外景地"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EFb8uXGIIDdYADph1jx-E7AAAC3KgBJ8dIAOmHu513_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EFb8uXGIIDdYADph1jx-E7AAAC3KgBJ8dIAOmHu513_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                    <#--<li titledata="亚龙湾"><a-->
                                                                            <#--href="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EFb8uW6IRJkoAEnxuh6fhUQAAC3KgAAAAAASfHS699_w400_h300_c1_t0.jpg"><img-->
                                                                            <#--src="http://m.tuniucdn.com/fb2/t1/G1/M00/50/BF/Cii9EFb8uW6IRJkoAEnxuh6fhUQAAC3KgAAAAAASfHS699_w400_h300_c1_t0.jpg"-->
                                                                            <#--alt=""></a></li>-->
                                                                <#--</ul>-->
                                                            <#--</div>-->
                                                            <#--<div class="sp_next" style="display: none;"></div>-->
                                                        <#--</div>-->
                                                        <#--<dl class="clearfix comment_from">-->
                                                            <#--<dt><a href="/recall/14014773" target="_blank">2016-03-31-->
                                                                <#--13:51</a> &nbsp;&nbsp;来自途牛电脑版-->
                                                            <#--</dt>-->
                                                        <#--</dl>-->
                                                        <#--<div class="comment_prec myorder_prec_box">-->
                                                            <#--<div class="comment_prec_box"><p class="cp_tt">点评赠送</p>-->

                                                                <#--<p class=""><span>返现</span> ¥10</p></div>-->
                                                        <#--</div>-->
                                                    <#--</dd>-->
                                                <#--</dl>-->
                                            <#--</li>-->
                                        <#--</ul>-->
                                        <#--<div class="pagination" style="width:auto;">-->
                                            <#--<div class="page-bottom" id="remark_page"><span-->
                                                    <#--class="page-start">上一页</span><span class="page-cur">1</span><a-->
                                                    <#--href="javascript:void(0)" onclick="remarkPageData(2)">2</a><a-->
                                                    <#--href="javascript:void(0)" onclick="remarkPageData(3)">3</a><span-->
                                                    <#--class="page-break">...</span><a href="javascript:void(0)"-->
                                                                                    <#--onclick="remarkPageData(328)">328</a><a-->
                                                    <#--href="javascript:void(0)" onclick="remarkPageData(2)"-->
                                                    <#--class="page-next">下一页</a></div>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                <#--</div>-->
                            <#--</div>-->


                            <#--<!-- 点评图片放大显示&ndash;&gt;-->
                            <#--<div class="divMask hide"></div>-->
                            <#--<div class="pop_slidy hide" style="top: 178px;">-->
                                <#--<p class="ps_tt"><label>&lt;海南-三亚4日游&gt;万人出游纯玩线，3晚五星红树林度假世界，2人减1000，可升温德姆亲子套餐</label><em id="setNum"><!-- (<span>8</span>/207) &ndash;&gt;</em><span class="pop_close"></span></p>-->
                                <#--<div class="pop_img clearfix">-->
                                    <#--<div class="pop_left">-->
                                        <#--<a href="javascript:void(0)">-->
                                            <#--<img src="http://img4.tuniucdn.com/img/20130528/guide/index_img/qinglianghaibin.jpg" alt="">-->
                                        <#--</a>-->
                                        <#--<div class="pop_left_bar"></div>-->
                                        <#--<div class="pop_left_span">-->
                                            <#--<span class="bar_left"></span>-->
                                            <#--<span class="bar_right">发布时间：<span class="bar_right_time"></span></span>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                    <#--<div class="pop_right">-->
                                        <#--<div class="pop_prev pop_grey"></div>-->
                                        <#--<div class="pop_prod_lists">-->
                                            <#--<ul class="pop_lists">-->

                                            <#--</ul>-->
                                        <#--</div>-->

                                        <#--<div class="pop_next"></div>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <#--<div class="pop_detail">-->
                                    <#--<p class="clists_stars clearfix">-->
                                        <#--<span class="star"></span>-->
                                        <#--<span class="star"></span>-->
                                        <#--<span class="star"></span>-->
                                        <#--<span class="star"></span>-->
                                        <#--<span class="star grey_star"></span>-->
                                        <#--<span class="spec_comment">精华点评</span>-->
                                    <#--</p>-->
                                    <#--<p class="pop_word">-->
                                        <#--第一次在各种旅游网中选择了途牛，去的过程中很忐忑，不知道是不是值得！这次出游总体来说很满意！就是红眼班有点坑爹，春秋航空飞机太小了，回来的时候记得加点钱升级下位置，还不错。这个行程安排还是很满意的，地陪导游很热心，很多问题都能帮我们合理解决这次旅途让我很愉悦~~~-->
                                    <#--</p>-->
                                <#--</div>-->
                            <#--</div>-->


                        <#--</div>-->
                        <!--游客点评 end-->

                        <!-- 签证信息开始 -->
                        <!-- 签证信息结束 -->

                        <!--在线问答 start-->
                        <#--<div class="pkg-detail-infor onlineqa" id="zxwd" tabrel="zxwd">-->
                            <#--<h2 class="detail-title">-->
                                <#--<span>在线问答</span>-->
                            <#--</h2>-->
                            <#--<div class="detailcontent detail_infor">-->
                                <#--<input type="hidden" id="reg_type" value="">-->
                                <#--<input type="hidden" id="user_email" value="">-->
                                <#--<input id="online_ask_route_id" type="hidden" value="210061717">-->
                                <#--<div class="detail_infor ml_10">-->
                                    <#--<!--srart aq&ndash;&gt;-->
                                    <#--<!--srart aq search&ndash;&gt;-->
                                    <#--<div class="aq_search">-->

                                        <#--<div class="aq_list clearfix">-->
                                            <#--<span class="tt">问题分类：</span>-->
                                            <#--<a rel="nofollow" class="curr" href="javascript:void(0)" value="0">全部</a>            <span class="cj_pro">常见问题</span>-->
                                            <#--<a class="" href="javascript:void(0)" value="5" rel="nofollow">预订过程</a>-->
                                            <#--<input id="page_num" type="hidden" value="">-->
                                        <#--</div>-->

                                        <#--<div class="aq_searchbox clearfix">-->
                                            <#--<div class="searchbox_l">-->
                                                <#--<span class="tt">问 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;题：</span>-->
                                                <#--<input type="text" class="aq_search_input grey_color" id="aq_search_input" name="aq_search_input" value="请输入问题" onkeypress="if(event.keyCode==13) {getAskByType(1);return false;}">-->
                                                <#--<input id="aq_search_btn" name="aq_search_btn" class="aq_search_btn" type="submit" value="" onclick="getAskByType(1)">-->
                                            <#--</div>-->
                                            <#--<div class="searchbox_r">-->
                                                <#--<a href="#pagination" rel="nofollow"><input class="q_btn" type="button" value="我要提问"></a>-->
                                                <#--<a rel="nofollow" href="/u/ask" class="to_aq" target="_blank">查看我的提问</a>-->
                                            <#--</div>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                    <#--<!--end aq search&ndash;&gt;-->

                                    <#--<div class="AQContent AQContent_new" id="mark_question">-->
                                        <#--<div class="all_question">-->

                                            <#--<div class="cj_pro hide">-->
                                                <#--<ul class="AQ_ul">-->
                                                    <#--<li class="page_1">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 网上价格是否可以预订?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">您好，若网上团期显示有位置信息，此价格相对稳定。<br>-->
                                                                    <#--若网上团期只显示价格未显示位置信息，代表此团期价格需要现询，越临近出游日期，价格变动的可能性也越大，建议您提早预订。-->
                                                                <#--</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_1">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 网上的价格是否还可以优惠?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">您好，网上已经明确了售价以及促销优惠，此价格已经是最低价。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_1">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 日历上没有报价的日期是否能预订?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">您好，没有报价的日期不能进行预订，请您选择其它日期或者其它产品。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_1">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 抵用券是否可以全额抵用?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">您好，抵用券是有额度限制的，每条线路最高使用额度以网上呈现为准。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_2">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 前台无明确航班、车次，是否可以明确?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">团队票需统一出票，故无法提前得知车次、航班信息。具体的航班/车次信息以出团通知书为准。请谅解。如果您对航班时间有特殊要求，可以签约前主动向客服提出，为您核实是否可以安排，不过费用会增加。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_2">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 火车铺位?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">火车上/中/下铺都有可能，无法保证安排下铺的特殊需求，请谅解。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_2">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 能否拼房?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">拼房需要根据最终收客人数中是否有单男单女，以及到了当地后，需要拼房的两个人是否愿意同彼此拼房，所以事先确实无法确认一定可以拼房。即便是现在有单男单女，对方愿意拼房，也有可能到了当地以后，彼此改变主意，所以还是需要到了当地才能确认是否可以拼房。故单人出游建议预付单房差，当地若拼房成功，可导游现退单房差。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_2">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 可否安排大床房?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">除非行程中披露酒店标准为大床房，正常情况下，我们安排的是标准间，如果您有大床房的特殊要求，请预订时提出，在酒店条件允许的情况下，我们会尽量帮您安排。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_3">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 想知道具体的酒店名称?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">若行程中已经明确，酒店按照行程中承诺的标准安排。<br>-->
                                                                    <#--若行程中未明确，具体的酒店需要根据收客人数来确定，所以暂时无法告知您具体的酒店名称。-->
                                                                <#--</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_3">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 为什么不含餐，如何自行安排餐?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">考虑到游客的口味不同，且很多游客希望有时间尝试当地特色饮食，所以只包含XX正餐/不包含正餐。您可自行品尝当地小吃。餐厅的位置您可以根据当天的行程安排情况，咨询导游。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_3">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 儿童不占床，如何安排早餐?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">您可自行在酒店前台购买儿童早餐，或者在酒店周边自行用餐。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_3">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 若不成团怎么办?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">我们会努力收客，尽量保证成团，若最终确定不成团，我们将按照旅游合同约定，提前通知您，并为您提供变更或者退团等可选方案。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_4">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 购物点是否强制购物?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">您好，在与您签署的旅游合同中，明确规定不允许强制购物，我司对产品质量非常看重，对线路产品的上线有严格把关，不允许出现强制购物。您若当地购物请慎重考虑，把握好质量与价格，务必索要发票。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_4">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 如何网上支付?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">您好，登陆途牛旅游网主页。点击左上角【登录】按钮，输入用户名、密码进行登录。之后点击左上角【我的订单】，可看到预订线路，点击后面的【去付款】按钮进行签约和付款。付款成功，您可在会员中心自行下载合同和行程单，和纸质合同一样，具有法律效力。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_4">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 发票怎么开?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">发票内容是旅游费，抬头可以是公司的名称或个人的名字，预订线路后您可以在会员中心填写发票信息。我们会在出游归来五个工作日内为您快递。若您暂时不需要开取发票，发票也可在出游归来两个月内来电索要。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_4">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 出团通知什么时候发?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">您好，出团通知最迟出发前一天18点前发送，请您耐心等待。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_5">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 机票是否可以退改签?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">团队机票将统一出票，团队机票一经开出，不得更改、不得签转、不得退票。若更改、签转和退票只能退基建燃油税。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                    <#--<li class="page_5">-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left_ask">-->
                                                                <#--<span class="q_icon">咨询问题：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p> 签约后是否可以退团、更改线路、延期出游?</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                        <#--<dl class="clearfix">-->
                                                            <#--<dt class="AQ_left">-->
                                                                <#--<span class="a_icon">途牛客服：</span>-->
                                                            <#--</dt>-->
                                                            <#--<dd class="AQ_right">-->
                                                                <#--<p class="a_con">您好，签约后发生变更需要为您核实是否产生损失，一般损失较大，建议您签约前确认清楚。</p>-->
                                                            <#--</dd>-->
                                                        <#--</dl>-->
                                                    <#--</li>-->
                                                <#--</ul>-->
                                                <#--<div class="pagination cj_pro_page">-->
                                                    <#--<div class="page-bottom">-->
                                                        <#--<a href="javascript:void(0);" class="page_prev hide">上一页</a>-->
                                                        <#--<a href="javascript:void(0);" class="page_num page-cur">1</a>-->
                                                        <#--<a href="javascript:void(0);" class="page_num">2</a>-->
                                                        <#--<a href="javascript:void(0);" class="page_num">3</a>-->
                                                        <#--<a href="javascript:void(0);" class="page_num">4</a>-->
                                                        <#--<a href="javascript:void(0);" class="page_num">5</a>-->
                                                        <#--<a href="javascript:void(0);" class="page_next">下一页</a>-->
                                                    <#--</div>-->
                                                <#--</div>-->
                                            <#--</div>-->
                                            <#--<ul class="AQ_ul AQ_ques">-->
                                                <#--<li>-->
                                                    <#--<dl class="clearfix">-->
                                                        <#--<dt class="AQ_left_ask"><span class="q_icon">咨询问题：</span></dt>        <dd class="AQ_right"><p>-->
                                                        <#--<a class="a_qst" target="_blank" href="/asks/455676">-->
                                                            <#--你好，我想问下我预订左之后在哪里汇合啊？</a>-->
                                                        <#--<span class="a_time">2016-06-10 22:20:29</span></p></dd>-->
                                                    <#--</dl>-->
                                                    <#--<dl class="clearfix">-->
                                                        <#--<dt class="AQ_left"><span class="a_icon">途牛客服：</span></dt>-->
                                                        <#--<dd class="AQ_right"><p class="a_con">尊敬的途牛会员，您好！-->
                                                            <#--抱歉，查看不到您咨询的线路信息，请确定您的出游需求后致电我们的服务热线：4007-999999进行咨询。-->
                                                            <#--感谢您对途牛旅游网的关注与支持，谢谢！祝您愉快！</p></dd>-->
                                                    <#--</dl>-->
                                                <#--</li>-->
                                            <#--</ul>-->
                                            <#--<!--start pageration&ndash;&gt;-->
                                            <#--<div class="pagination" id="pagination" style="display:none">-->
                                                <#--<div class="page-bottom" id="consul_pager">-->
                                                    <#--<span class="page-cur">1</span>            </div>-->
                                            <#--</div>-->
                                            <#--<!--start pageration&ndash;&gt;-->
                                        <#--</div>-->
                                        <#--<div id="show_no_question" style="color: #999999;display:none;padding-left:10px">暂无该产品问答！对本产品有任何疑问，请在此进行提问。我们的工作人员将尽快回复您。</div>-->
                                    <#--</div><!--start aq_box&ndash;&gt;-->
                                    <#--<div class="aq_box aq_write">-->
                                        <#--<form id="newask">-->
                                            <#--<div class="pl_10">-->
                                                <#--<p class="q_person_new" id="q_person_new">途牛会员方能进行提问，请先<a href="/u/login" rel="nofollow">登录</a>。没有途牛账户？现在注册，即可获得200元-->
                                                    <#--<a href="http://www.tuniu.com/static/yh/" target="_blank">途牛抵用券</a>，直抵现金。-->
                                                    <#--<a href="http://www.tuniu.com/u/register" target="_blank">立即注册&gt;</a><br>-->
                                                <#--</p><div class="aq_style_box">-->
                                                <#--<dl id="aq_style" class="clearfix">-->
                                                    <#--<dt>提问类型：</dt>-->
                                                    <#--<dd name="aq_style_name"><input type="radio" name="aq_style" onchange="$(&quot;#choose_question&quot;).hide();" value="1">住宿</dd>-->
                                                    <#--<dd name="aq_style_name"><input type="radio" name="aq_style" onchange="$(&quot;#choose_question&quot;).hide();" value="2">交通</dd>-->
                                                    <#--<dd name="aq_style_name"><input type="radio" name="aq_style" onchange="$(&quot;#choose_question&quot;).hide();" value="3">导游</dd>-->
                                                    <#--<dd name="aq_style_name"><input type="radio" name="aq_style" onchange="$(&quot;#choose_question&quot;).hide();" value="4">行程</dd>-->
                                                    <#--<dd name="aq_style_name"><input type="radio" name="aq_style" onchange="$(&quot;#choose_question&quot;).hide();" value="5">预订过程</dd>-->
                                                    <#--<dd><span id="choose_question" style="display:none" class="f_f00">请选择问题分类</span></dd>-->
                                                <#--</dl>-->
                                                <#--<p class="con_tit">提问内容：</p>-->
                                            <#--</div>-->

                                                <#--<div class="q_textbox">-->
                                                    <#--<textarea name="ask_content" id="ask_content"></textarea>-->
                                                    <#--<div class="f_f00" id="write_question" style="display:none">请填写问题</div>-->
                                                <#--</div>-->
                                                <#--<div class="q_mail"><label>请填写邮箱，详细解答会同时发送至您的邮箱。</label>E-mail：<span>-->
    <#--<input name="email" type="text" id="email" value=""></span></div>-->
                                                <#--<div class="q_test"><label>验证码：</label>-->
	     <#--<span>-->
	         <#--<input name="identify" id="identify" type="text" onfocus="load_code();">-->
	     <#--</span>-->
		<#--<span class="test_code">-->
             <#--<img src="" id="identify_img" onclick="change_img();" alt="如验证码无法辨别，点击即可刷新。" width="81" height="25" style="cursor:pointer;display:none;">-->
	     <#--</span>-->
                                                    <#--<span class="error f_f00" id="write_identify" style="display:none">请填写验证码</span>-->
                                                <#--</div>-->
                                                <#--<div>-->
                                                    <#--<input id="sub_button" class="q_btn" type="button" onclick="check_data();" value="提 &nbsp; 问">-->
                                                <#--</div>-->
                                            <#--</div>-->
                                        <#--</form>-->
                                    <#--</div>-->
                                    <#--<!--end aq_box&ndash;&gt;-->
                                    <#--<!--end aq&ndash;&gt;-->
                                <#--</div>                         	<div style="display: none" id="consul_pager"></div>-->
                                <#--<!--srart aq&ndash;&gt;-->
                                <#--<!--srart aq search&ndash;&gt;-->
                                <#--<!--start aq_box&ndash;&gt;-->
                                <#--<!--end aq&ndash;&gt; </div>-->
                        <#--</div>-->
                        <!--在线问答 end-->
                        <!--相关产品推荐 start-->
                        <div class="pkg-detail-infor aroundpro" id="xgcp" tabrel="xgcp">
                            <h2 class="detail-title">
                                <span>相关产品推荐</span>
                            </h2>
                            <div id="poi_cross_recommend" class="detailcontent">
                                <div class="recommandpro destination">
                                    <div class="detail-sub-title clearfix"><i></i> ${arriveCity.name}${line.productAttr.desc}产品推荐<a
                                            href="http://www.tuniu.com/g20037/tours-gz0/" class="more" rel="nofollow"
                                            target="_blank">更多&gt;</a></div>
                                    <ul>
                                    <#list recommendLine as rLine>
                                        <#if rLine_index == 4><#break></#if>
                                        <li><a class="img" href="/line_detail_${rLine.id}.html"
                                               target="_blank">
                                            <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${rLine.cover}"
                                                 alt="<${rLine.name}>${rLine.appendTitle}">
                                        </a>

                                            <div class="probg"></div>
                                            <p class="proname clearfix"><span class="manyi">满意度96%</span></p>

                                            <p class="price"><i>¥</i> <b>${rLine.price}</b> 起</p>

                                            <p class="productName"
                                               style="margin-top:7px;line-height: 22px;word-break: break-all;word-wrap: break-word;overflow: hidden;">
                                                <a href="/line_detail_${rLine.id}.html" target="_blank">
                                                    &lt;${rLine.name}&gt;${rLine.appendTitle}</a>
                                            </p></li>
                                    </#list>
                                    </ul>
                                </div>
                                <div class="recommandpro">
                                    <div class="detail-sub-title clearfix"><i></i> ${arriveCity.name}景点门票产品推荐<a
                                            href="http://www.tuniu.com/g20037/ticket-gz0/" class="more" rel="nofollow"
                                            target="_blank">更多&gt;</a></div>
                                    <ul>
                                        <#list hotScenicInfo as scenic>
                                        <li><a class="img" href="${SCENIC_PATH}/scenic_detail_${scenic.id}.html" target="_blank">
                                            <#if scenic.cover?starts_with("http")>
                                                <img data-original="${scenic.cover}" alt="${scenic.name}">
                                            <#else>
                                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${scenic.cover}" alt="${scenic.name}">
                                            </#if>
                                            </a>
                                            <p class="proname">
                                                <a target="_blank" href="${SCENIC_PATH}/scenic_detail_${scenic.id}.html">
                                                    &lt;${scenic.name}&gt;</a>
                                            </p>

                                            <#if scenic.price gt 0><p class="price"><i>¥</i> <b>${scenic.price}</b> 起</p></li></#if>
                                        </#list>
                                    </ul>
                                </div>
                                <div class="recommandpro destination">
                                    <div class="detail-sub-title clearfix"><i></i> 更多从出发的热门目的地
                                        <#--<div class="morecity">-->
                                            <#--<a target="_blank" href="/around/" class="moreTravelType">周边旅游</a>-->
                                            <#--|-->
                                            <#--<a target="_blank" href="/static/zijiayou/" class="moreTravelType">自驾游</a>-->
                                            <#--|-->
                                            <#--<a target="_blank" href="/domestic/" class="moreTravelType">国内旅游</a>-->
                                            <#--|-->
                                            <#--<a target="_blank" href="/abroad/" class="moreTravelType">出境旅游</a>-->
                                        <#--</div>-->

                                    </div>
                                    <ul>
                                    <#list hotDestination as destination>
                                        <li style="margin-bottom: 15px;">
                                            <a class="img" target="_blank"
                                                <#if line.productAttr == "gentuan">
                                               href="/group_tour_${destination.id}.html"
                                                <#elseif line.productAttr == "ziyou">
                                               href="/self_tour_${destination.id}.html"
                                                <#elseif line.productAttr == "zijia">
                                               href="/self_driver_${destination.id}.html"
                                               <#elseif line.productAttr == "custommade">
                                               href="/custom_made_${destination.id}.html"
                                                </#if>>
                                                <img alt="${destination.name}"
                                                     data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${destination.tbAreaExtend.cover}?imageView2/1/w/220/h/124/q/75"></a>

                                            <div class="probg"></div>
                                            <p class="proname clearfix">
                                                <a href="/nanya/medf/">${destination.name}</a>
                                            </p>
                                        </li>
                                    </#list>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!--相关产品推荐 end-->
                    </div>
                </div>
                <!--general_infor结束--> </div>
        </div>
        <!--mainContent end-->
        <!-- start 右侧 -->
        <div class="sideContent fr">
            <!-- 列表页右侧区块 -->
            <!-- 旅游攻略 -->
            <!-- 旅游攻略 -->

            <!-- 相关游记 -->
            <div class="block hot_line">
                <h3>
                    ${arriveCity.name}            游记
                </h3>
                <div class="c">
                    <ul>
                        <#list hotRecommendPlan as recommendPlan>
                        <li class="clearfix ">
                            <div class="des">
                                <p class="name">
                                    <a target="_blank" class="note_item" href="${RECOMMENDPLAN_PATH}/guide_detail_${recommendPlan.id}.html">
                                        ${recommendPlan.planName}</a>
                                </p>
                            </div>
                            <div class="hot_num <#if recommendPlan_index gt 2>hot_num_grey</#if>">
                                ${recommendPlan_index + 1}</div>
                        </li>
                        </#list>
                        </ul>
                </div>
            </div>
            <!-- 相关游记end -->
            <div class="m_team_tour_3">
                <h5>团队定制游</h5>

                <p>此线路<span class="strong">10人</span>以上可选择独立成团定制服务</p>
                <a target="_blank" href="${REQUIRE_PATH}">立即定制</a>
            </div>
            <!-- 推荐线路start -->
            <div class="block hot_line showPic" id="tuijianRoute" style="display: block;">
                <div id="for_normal">
                    <h3>
                        推荐线路
                    </h3>
                    <div class="c">
                        <ul>
                            <#list hotLine as hLine>
                            <li class="clearfix  cur">
                                <div class="pic" style="width: 70px;">
                                    <a target="_blank" href="/line_detail_${hLine.id}.html">
                                        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${hLine.cover}" style="display: inline;"></a>
                                </div>
                                <div class="des" style="width: 82px; margin-left: 8px;">
                                    <p class="name">
                                        <a class="note_item" href="/line_detail_${hLine.id}.html" target="_blank">
                                            &lt;${hLine.name}&gt;${hLine.appendTitle}</a>
                                    </p>
                                    <p class="price"> <em>¥
                                        ${hLine.price}</em>
                                        起
                                    </p>
                                </div>
                                <div class="hot_num <#if hLine_index gt 2>hot_num_grey</#if>">
                                    ${hLine_index + 1}</div>
                            </li>
                            </#list>
                            </ul>
                    </div>
                </div>
            </div>
            <!-- 推荐线路end -->

            <!-- 浏览记录start -->
            <div class="block hot_line" id="routeViewHistory"><h3>
                浏览记录
            </h3>
                <div class="c">
                    <ul>

                    </ul>
                </div>
            </div>
            <!-- 浏览记录end -->

            <!-- 猜你喜欢start -->
            <div class="block hot_line" id="toursFavorite"></div>
            <!-- 猜你喜欢end -->
        </div>
        <!-- 右侧栏end -->

        <!--wrap end-->
    </div>
<div class="adjutant_hover mouse_hover" style="display: none; top: 520px; left: 1108px; z-index: 5000;">
    <div class="accolade">
        <div class="tit">儿童标准</div>
        <p>
            <#if lineExplain.childStandardType == "desc">
                ${lineExplain.childLongRemark}
            </#if>
            <#if lineExplain.childStandardType == "height">
                身高${lineExplain.childStartNum}~${lineExplain.childEndNum}米（不含），${lineExplain.childBed}，${lineExplain.childOtherRemark}。
            </#if>
            <#if lineExplain.childStandardType == "age">
                年龄${lineExplain.childStartNum}~${lineExplain.childEndNum}周岁（不含），${lineExplain.childBed}，${lineExplain.childOtherRemark}。
            </#if>
        </p>
    </div>
    <span class="z" style="left: 127px;">◆</span>
    <span class="y" style="left: 127px;">◆</span>
</div>
</div>