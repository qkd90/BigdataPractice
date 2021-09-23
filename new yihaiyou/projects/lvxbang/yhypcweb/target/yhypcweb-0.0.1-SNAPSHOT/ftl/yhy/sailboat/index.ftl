<div class="banner">
    <div class="banner-search">
        <div class="banner-search-wrap" style="z-index:14">
            <div class="banner-search-title">
                <span></span>
                <i>海上休闲</i>
                <span></span>
            </div>
            <div class="banner-search-form">
                <form id="banner_search" method="post" action="/yhypc/sailboat/list.jhtml">
                    <div class="form-group clearfix">
                        <label for="">登船地点：</label>
                        <input id="location_scenicId" type="hidden" name="ticketSearchRequest.scenicId" value="">
                        <input type="text" name="location" placeholder="不限" id="location" readonly>
                        <span class="locationPlaceholder">不限</span>
                        <ul class="datalist" id="location-list">
                            <li data-id="">不限</li>
                            <#list scenicInfos as scenicInfo>
                                <li data-id="${scenicInfo.id}">${scenicInfo.name}</li>
                            </#list>
                        </ul>
                    </div>
                    <div class="form-group clearfix">
                        <label for="">类型：</label>
                        <input id="location_ticketType" type="hidden" name="ticketSearchRequest.ticketType" value="">
                        <input type="text" name="category" placeholder="不限" id="category" readonly>
                        <span class="categoryPlaceholder">不限</span>
                        <ul class="datalist" id="category-list">
                            <li data-id="">不限</li>
                            <li data-id="sailboat">帆船</li>
                            <li data-id="yacht">游艇</li>
                            <li data-id="huanguyou">鹭岛游</li>
                        </ul>
                    </div>
                    <a href="javascript:void(0);" class="btn-search" onclick="$('#banner_search').submit()">
                        <span class="icon-search">搜&nbsp;索</span>
                    </a>
                </form>
            </div>
        </div>
    </div>
    <div class="carousel-wrap">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <#list adses as ads>
                    <div class="swiper-slide">
                        <#if ads.imgPath != null>
                            <a href="${ads.url}">
                                <img src="${QINIU_BUCKET_URL}${ads.imgPath}" alt="${ads.adTitle}">
                            </a>
                            <#else>
                                <a href="#">
                                    <img src="/image/banner-img.png" alt="广告">
                                </a>
                        </#if>
                    </div>
                </#list>
            </div>
            <!-- 如果需要分页器 -->
            <div class="swiper-pagination clearfix">
            </div>
        </div>
    </div>
</div>
<div class="recommend">
    <div class="recommend-title clearfix">
        <div class="recommend-left pull-left">
            热门推荐
        </div>
        <div class="recommend-right pull-right">
            <a href="/yhypc/sailboat/list.jhtml">更多>></a>
        </div>
    </div>
    <ul class="recommend-content">
        <li class="recommend-content-section clearfix">
            <div class="aside1 pull-left">
                <div class="category">
                    <div class="aside-title"></div>
                    <span>登艇地点</span>
                </div>
                <#list scenicInfos as scenicInfo>
                    <#if (scenicInfo_index <= 2)>
                        <div class="loaction-port">
                            <form id="placeId_${scenicInfo_index}" method="post" action="/yhypc/sailboat/list.jhtml">
                                <input name="ticketSearchRequest.scenicId" type="hidden" value="${scenicInfo.id}">
                                <a href="javascript:void(0);" onclick="$('#placeId_${scenicInfo_index}').submit()">${scenicInfo.name}</a>
                            </form>
                        </div>
                    </#if>
                </#list>
            </div>
            <ul class="item1 pull-left clearfix">

                <#list sailboatList as sailboat>
                    <#if (sailboat_index <= 1)>
                        <li>
                            <a href="/sailboat_detail_${sailboat.id}.html">
                                <#if sailboat.imgUrl != null>
                                    <img src="${QINIU_BUCKET_URL}${sailboat.imgUrl}" alt="${sailboat.name}">
                                <#else >
                                    <img src="/image/recommend-product-big.png" alt="${sailboat.name}">
                                </#if>
                            </a>
                            <p><a href="/sailboat_detail_${sailboat.id}.html">
                                <#if (sailboat.name)?? && ((sailboat.name)?length > 25) >
                                ${sailboat.name?substring(0,25)}..
                                <#else>
                                ${(sailboat.name)!''}
                                </#if></a></p>
                            <div class="order-wrap clearfix">
                        <span class="pull-left">
                            <sup>￥</sup>
                            <#if sailboat.price != null && sailboat.price != 0>
                                ${sailboat.price}<sub>起</sub>
                            <#else >
                                <sub>暂无价格</sub>
                            </#if>

                        </span>
                                <a href="/sailboat_detail_${sailboat.id}.html" class="pull-right">立即预定</a>
                            </div>
                        </li>
                    </#if>
                </#list>
            </ul>
        </li>
        <li class="recommend-content-section clearfix">
            <div class="aside2 pull-left">
                <div class="category">
                    <div class="aside-title"></div>
                    <span>玩乐类型</span>
                </div>
                <div class="loaction-port">
                    <form id="ticketType_1" method="post" action="/yhypc/sailboat/list.jhtml">
                        <input name="ticketSearchRequest.ticketType" type="hidden" value="yacht">
                        <a href="javascript:void(0)" onclick="$('#ticketType_1').submit()">游艇</a>
                    </form>

                </div>
                <div class="loaction-port">
                    <form id="ticketType_2" method="post" action="/yhypc/sailboat/list.jhtml">
                        <input name="ticketSearchRequest.ticketType" type="hidden" value="sailboat">
                        <a href="javascript:void(0)" onclick="$('#ticketType_2').submit()">帆船</a>
                    </form>
                </div>
                <div class="loaction-port">
                    <form id="ticketType_3" method="post" action="/yhypc/sailboat/list.jhtml">
                        <input name="ticketSearchRequest.ticketType" type="hidden" value="huanguyou">
                        <a href="javascript:void(0)" onclick="$('#ticketType_3').submit()">鹭岛游</a>
                    </form>
                </div>
            </div>
            <ul class="item2 pull-left clearfix">

            <#list sailboatList as sailboat>
                <#if (sailboat_index > 1) >
                    <li>
                        <a href="/sailboat_detail_${sailboat.id}.html">
                            <#if sailboat.imgUrl != null>
                                <img src="${QINIU_BUCKET_URL}${sailboat.imgUrl}" alt="${sailboat.name}">
                            <#else >
                                <img src="/image/recommend-product-big.png" alt="尚博游艇帆船出海体验">
                            </#if>
                        </a>
                        <p><a href="/sailboat_detail_${sailboat.id}.html"><#if (sailboat.name)?? && ((sailboat.name)?length > 15) >
                        ${sailboat.name?substring(0,15)}..
                        <#else>
                        ${(sailboat.name)!''}
                        </#if></a></p>
                        <div class="order-wrap clearfix">
                        <span class="pull-left">
                            <sup style="margin-left: 0px;">￥</sup><#if sailboat.price != null><span>${sailboat.price}</span><sub>起</sub><#else><sub>暂无价格</sub></#if>
                        </span>
                            <a href="/sailboat_detail_${sailboat.id}.html" class="pull-right">立即预定</a>
                        </div>
                    </li>
                </#if>
            </#list>
            </ul>
        </li>
    </ul>
</div>