<!--banner-->
<div class="Yqybanner" >
    <div class="search_bg posiA">
        <!--酒店-->
        <div class="mailTablePlan">
            <!--tray-->
            <div class="tray in_hote ">
                <p class="textC tray_title_p"><span class="tray_title ff_yh">搜索酒店</span></p>
                <div class="onepass fake2">
                    <form action="${HOTEL_PATH}/hotel_list.html" method="post" id="hotelSearchForm">
                        <input type="hidden" name="hotelCityId" id="hotel_cityId"/>
                        <dl class="list fl posiR hotel_des required">
                            <dt class="list_tit">目 的 地</dt>
                            <dd class="list_con">
                                <input type="text" id="ipCity" value="" name="cities" class="input input01 clickinput" maxlength="20" data-areaId="" data-url="${DESTINATION_PATH}/lvxbang/destination/getSeachAreaList.jhtml" autoComplete="Off"><!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                                <!--目的地 clickinput input01 input-->
                                <div class="posiA Addmore categories_Addmore2">
                                    <i class="close"></i>
                                    <!--<div class="Addmore_d">
                                        搜索历史：<span>厦门</span>
                                    </div>-->
                                    <dl class="Addmore_dl">
                            <dt>
                            <div class="Addmore_nr">
                                <ul>
                                    <li class="checked"><a href="javaScript:;">热门</a></li>
                                    <li><a href="javaScript:;">A-E</a></li>
                                    <li><a href="javaScript:;">F-J</a></li>
                                    <li><a href="javaScript:;">K-P</a></li>
                                    <li><a href="javaScript:;">Q-W</a></li>
                                    <li><a href="javaScript:;">X-Z</a></li>
                                </ul>
                            </div>
                            </dt>
                            <dd>
                                <label></label>
                                <div class="Addmore_nr">
                                    <ul>
                                    <#list hot as aArea>
                                        <li data-id="${aArea.id}">
                                            <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                        </li>
                                    </#list>
                                    </ul>
                                </div>
                            </dd>
                        <#list letterSortAreas as letterSortArea>
                            <dd class="disn">
                                <#list letterSortArea.letterRange as lrArea>
                                    <label>${lrArea.name}</label>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <#list lrArea.list as aArea>
                                                <li data-id="${aArea.id}">
                                                    <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                </li>
                                            </#list>
                                        </ul>
                                    </div>
                                </#list>
                            </dd>
                        </#list>
                        </dl>
                        <p class="cl"></p>
                </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                <!--关键字提示 clickinput input input01-->
                <div class="posiA categories_div  KeywordTips">
                    <ul>

                    </ul>
                </div>
                <!--错误-->
                <div class="posiA categories_div cuowu textL">
                    <p class="cl">抱歉未找到相关的结果！</p>
                </div><!-- #EndLibraryItem -->
                </dd>
                </dl>
                <dl class="list fr required">
                    <dt class="list_tit">入住日期</dt>
                    <dd class="list_con posiR"><i class="time_ico in_time"></i>
                        <input type="text" value="" name="startDate" class="input01  in_time" maxlength="20"
                               placeholder="" id="lDate-3"
                               readOnly="true" value=""
                               onFocus="WdatePicker({minDate:'%y-%M-{%d}',onpicked:function(){$(this).change()}});">
                        <!-- #BeginLibraryItem "/lbi/time.lbi" -->
                    </dd>
                </dl>
                <dl class="list fl">
                    <dt class="list_tit">酒店级别</dt>
                    <dd class="list_con hotellevel posiR">
                        <i class="ico3"></i>
                        <input type="hidden" name="star" value="0" class="hotelStar">
                        <input type="text" placeholder="不限" value="" class="input" readonly="readonly">
                        <!--关键字提示-->
                        <div class="posiA hotellevel_div">
                            <ul>
                                <li value="5"> <label>五星级/豪华</label></li>
                                <li value="4"><label>四星级/高档</label> </li>
                                <li value="3"><label>三星级/舒适</label></li>
                                <li value="2"><label>二星级以下/经济</label></li>
                            </ul>
                        </div>
                    </dd>
                </dl>
                <dl class="list fr required">
                    <dt class="list_tit">退房日期</dt>
                    <dd class="list_con posiR"><i class="time_ico in_time"></i>
                        <input type="text" value="" name="endDate" class="input01 in_time" maxlength="20"
                               placeholder="" id="rDate-3"
                               readOnly="true"
                               onFocus="WdatePicker({minDate:'#F{$dp.$D(\'lDate-3\',{d:1})}',maxDate:'#F{$dp.$D(\'lDate-3\',{d:30})}',onpicked:function(){$(this).change()}});">
                        <!-- #BeginLibraryItem "/lbi/time.lbi" -->
                    </dd>
                </dl>
                <dl class="list fl posiR">
                    <dt class="list_tit">关 键 词</dt>
                    <dd class="list_con">
                        <input type="text" id="gjc" value="" name="name" placeholder="酒店名/地标/商圈"  class="input input01 clickinput" maxlength="20" data-areaId="" data-url="${HOTEL_PATH}/lvxbang/hotel/suggest.jhtml" autoComplete="Off"><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                        <!--关键字提示 clickinput input input01-->
                        <div class="posiA categories_div  KeywordTips">
                            <ul>

                            </ul>
                        </div>

                        <!--错误-->
                        <div class="posiA categories_div cuowu textL">
                            <p class="cl">抱歉未找到相关的结果！</p>
                        </div><!-- #EndLibraryItem --></dd>
                </dl>
                </form>
                <p class="tray_p cl"><input type="button" class="submitSearch ff_yh fr toSearch" value="开始搜索" onclick="SearcherBtn.btnHotelSeach()"/></p>

            </div>
        </div>
        <!--tray end-->
    </div>
    <!--酒店 end-->
    <p class="cl"></p>
</div>

<div class="slide-main" id="touchMain">
    <a class="prev" href="javascript:;" stat="prev1001"></a>
    <div class="slide-box textC" id="slideContent">
    <#list adses as ads>
        <div class="slide">
            <#if ads.openType != "NONE"><a stat="sslink-1" href="${HOTEL_PATH}${ads.url}"
                                           target="<#if ads.openType == "SELF">_self<#else>_blank</#if>"></#if>
            <img src="/static${ads.imgPath}" style="width:1900px;height:460px;" alt="${ads.adTitle}"/>
            <#if ads.openType != "NONE"></a></#if>
        </div>
    </#list>
    </div>
    <a class="next" href="javascript:;" stat="next1002"></a>
    <div class="item"><#list adses as ads><a class="<#if ads_index==0>cur</#if>" stat="item${(1001+ads_index)?c}"
                                             href="javascript:;"></a></#list>
    </div>
</div>
</div>
<!--banner end-->



<div class="main cl">
    <div class="w1000 pt20 pb20">
        <!--hotel list start-->
        <div class="hotels_fl fl">
            <div class="h50" id="nav">
                <div class="nav posiR">
                    <label class="fl ff_yh">热门酒店</label>
                    <ul class="mailTab fl ml15 mt15 b">
                    <#list hotCityHotel as cityHotel>
                        <li class="<#if cityHotel_index==0>checked</#if>">${cityHotel.city.name}</li>
                    </#list>
                    </ul>
                <#--<a href="Hotels_Detail.html" class="more b posiA">更多</a>-->
                </div>
            </div>
            <!--厦门-->

        <#list hotCityHotel as cityHotel>
            <div class="mailTablePlan <#if cityHotel_index!=0>disn</#if>">
                <ul class="hotels_list">
                    <#list cityHotel.hotelList as hotel>
                        <li>
                            <p class="img fl">
                            <a href="${HOTEL_PATH}/hotel_detail_${hotel.id}.html" target="_blank">
                                <#if hotel.cover>
                                    <img data-original="${hotel.cover}" alt="${hotel.name}"/></a>
                                <#else>
                                    <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jiudian.png?imageView2/1/w/240/h/175/q/75" alt="${hotel.name}"/></a>
                                </#if>

                            </p>

                            <div class="nr posiR fr lh30">
                                <a href="${HOTEL_PATH}/hotel_detail_${hotel.id}.html" target="_blank">
                                    <div class="posiA price round">
                                        <span class="fs20">¥</span><span class="fs20 b">${hotel.price?string('#')}</span>起
                                        <p class="fs16 b">预订</p>
                                    </div>
                                    <p class="name b fl">${hotel.name}</p><span class="hstar fl"><i
                                        style="width: ${12*hotel.star}px"></i></span>
                                </a><br/>

                                <p class="add fl mr10<#if hotel.extend.address?length gt 16> simple is_hover</#if>">${hotel.extend.address}</p>
                                <a href="javaScript:;" class="fl add_a look_map" data-ditu-baiduLng="${hotel.extend.longitude}"
                                   data-ditu-baiduLat="${hotel.extend.latitude}" data-ditu-name="${hotel.name}">查看地图</a><br/>

                                <p class="fraction"><b class="fs16">${hotel.score/20}
                                    分</b>/5分
                                    <#--(来自<#if hotel.commentList>${hotel.commentList?size}<#else>0</#if>人点评)-->
                                </p>
                                <#if hotel.shortDesc !=null>
                                    <p class="synopsis posiR"><i></i><span
                                            class="fl<#if hotel.shortDesc?length gt 15> simple is_hover</#if>">${hotel.shortDesc}</span><i
                                            class="fl"></i>
                                    </p><br/>
                                </#if>
                                <#if hotel.recommendPlanId != null>
                                    <p class="synopsis2">相关游记：
                                        <a href="${RECOMMENDPLAN_PATH}/guide_detail_${hotel.recommendPlanId}.html"
                                           target="_blank"><span>${hotel.recommendPlanName}</span></a>
                                    </p>
                                </#if>
                            </div>
                            <p class="cl"></p>
                        </li>
                    </#list>
                </ul>
            </div>
        </#list>
        </div>
        <!--hotel list end-->

        <div id="history" class="hotels_fr fr">
            <!--hotel history start-->
        <#--<div class="full-screen-slider mb30">-->
        <#--<ul class="slides" id="slides2">-->
        <#--<#list sideAdses as ads>-->
        <#--<li>-->
        <#--<a href="${ads.url}" target="_blank">-->
        <#--<img data-original="/static/${ads.imgPath}"  />-->
        <#--</a>-->
        <#--</li>-->
        <#--</#list>-->
        <#--</ul>-->
        <#--</div>-->
            <!--hotel history end-->
            <h2 class="ff_yh fs20">我浏览过的酒店</h2>
            <ul class="hotels_fr_ul mt10">
            </ul>
        </div>
        <p class="cl h30"></p>
    </div>
</div>
