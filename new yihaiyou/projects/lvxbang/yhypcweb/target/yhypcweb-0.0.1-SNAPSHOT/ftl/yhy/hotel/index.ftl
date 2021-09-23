<div class="advert">
    <div class="swiper-container" id="hotel_index_top_banner">
        <div class="swiper-wrapper">
            <#list adses as ad>
                <div class="swiper-slide"><a href="${ad.url}"><img src="${QINIU_BUCKET_URL}${ad.imgPath}"></a></div>
            </#list>
        </div>
        <div class="swipe-pagenavi swiper-pagination" id="hotel_index_top_page"></div>
    </div>
    <div class="outadvertBox" style="position:absolute;left:0;top:0">
        <form id="searchForm" action="" enctype="multipart/form-data">
            <div class="advertBox">
                <div class="dateBox">
                    <ul>
                        <li class="dateTitle clearfix">
                            <span class="line_l"></span>
                            <span class="line_t">酒店民宿</span>
                            <span class="line_l"></span>
                        </li>
                        <li class="interBox">
                            <label>入住日期：</label>
                            <div class="date_d" id="startDateDiv" onclick="WdatePicker({el:'startDate', doubleCalendar:true, readOnly:true, onpicked:HotelIndex.onFoucsEndTime(), minDate:'%y-%M-{%d}'})">
                                <input type="text" id="startDate" name="startDate" autocomplete="off" onclick="WdatePicker({doubleCalendar:true, readOnly:true, onpicked:HotelIndex.onFoucsEndTime(), minDate:'%y-%M-{%d}'})">
                            </div>
                        </li>
                        <li class="interBox">
                            <label>离店日期：</label>
                            <div class="date_d" id="endDateDiv" onclick="WdatePicker({el:'endDate', doubleCalendar:true, readOnly:true, minDate:'#F{$dp.$D(\'startDate\',{d:1})}'})">
                                <input type="text" id="endDate" name="endDate" autocomplete="off" onclick="WdatePicker({doubleCalendar:true, readOnly:true, minDate:'#F{$dp.$D(\'startDate\',{d:1})}'})">
                            </div>
                        </li>
                        <li class="interBox">
                            <label>关键词：</label>
                            <div class="date_d date_key">
                                <input type="text" placeholder="关键词" id="searchWord" name="searchWord">
                            </div>
                        </li>
                        <li class="interBox">
                            <div class="search">
                                <a href="javascript:;" id="goHotelListBtn">搜索</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </form>
    </div>
</div>
<div class="specailHotel">
    <h3>特色民宿</h3>
    <div class="specailList">
        <ul class="clearfix">
            <#list featuresHotelList as hotel>
                <li>
                    <div class="picture">
                        <a target="_blank" href="/hotel_detail_${hotel.id}.html">
                            <#if hotel.cover?starts_with("http://")>
                                <#if hotel.cover?contains("${QINIU_BUCKET_URL}")>
                                    <img src="${hotel.cover}?imageView2/2/w/220/h/136">
                                <#else><img src="${hotel.cover}">
                                </#if>
                            <#else><img src="${QINIU_BUCKET_URL}${hotel.cover}?imageView2/2/w/220/h/136">
                            </#if>
                        </a><span>${hotel.name}</span>
                    </div>
                    <div class="roomMess clearfix">
                        <div class="price">
                            <span class="rmb">¥</span><span class="num">${hotel.price}</span><span class="qi">起</span>
                        </div>
                        <div class="booking"><a target="_blank" href="/hotel_detail_${hotel.id}.html#booking">立即预订</a></div>
                    </div>
                </li>
            </#list>
        </ul>
    </div>
</div>
<div class="hotBrand">
    <h3>热门推荐</h3>
    <div class="hotelList">
        <ul class="clearfix">
            <#list recHotelList as hotel>
                <li>
                    <a target="_blank" href="/hotel_detail_${hotel.id}.html">
                        <img src="${hotel.cover}?imageView2/2/w/386/h/240"></a>
                    <div class="roomMess">
                        <span class="hotelName">${hotel.name}</span>
                        <div class="price">
                            <span class="rmb">¥</span><span class="num">${hotel.price}</span><span class="qi">起</span>
                        </div>
                        <div class="address">${hotel.address}</div>
                    </div>
                    <div class="shadow">
                        <a target="_blank" href="/hotel_detail_${hotel.id}.html"><span>立即查看</span></a>
                    </div>
                </li>
            </#list>
        </ul>
    </div>
    <div class="tackMore"><a target="_blank" href="/yhypc/hotel/list.jhtml">查看更多</a></div>
</div>
<div class="hotBrand hotHotel">
    <h3>热门住宿</h3>
    <div class="brandList">
        <p class="brandTitle" id="region_sel">
            <#list hotHotelData["region"] as region>
                <span data-region-id="${region.id}" class="<#if region_index==0>active</#if>">${region.name}</span>
            </#list>
        </p>
    </div>
    <div class="hotelList" id="hot_hotel_area">
        <#list hotHotelData["region"] as region>
            <ul data-region-id="${region.id}" class="clearfix" style="display: none">
            <#list hotHotelData["region_" + region.id] as hotel>
                <li>
                    <img src="${hotel.cover}?imageView2/2/w/386/h/240">
                    <div class="roomMess">
                        <span class="hotelName">${hotel.name}</span>
                        <div class="price">
                            <span class="rmb">¥</span><span class="num">${hotel.price}</span><span class="qi">起</span>
                        </div>
                        <div class="address">${hotel.address}</div>
                    </div>
                    <div class="shadow">
                        <a target="_blank" href="/hotel_detail_${hotel.id}.html"><span>立即查看</span></a>
                    </div>
                </li>
            </#list>
            </ul>
        </#list>
    </div>
    <div class="tackMore"><a target="_blank" href="/yhypc/hotel/list.jhtml">查看更多</a></div>
</div>
