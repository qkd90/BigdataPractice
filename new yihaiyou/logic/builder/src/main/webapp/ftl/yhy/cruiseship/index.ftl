<div class="banner">
    <div class="banner-search" style="z-index:14">
        <div class="banner-search-wrap">
            <div class="banner-search-title">
                <span></span>
                <i>邮轮</i>
                <span></span>
            </div>
            <div class="banner-search-form">
                <form id="search-form-cruiseship" method="post" action="/yhypc/cruiseShip/list.jhtml">
                    <div class="form-group">
                        <label for="">邮轮航线：</label>
                        <input id="search-cruiseship-line" type="hidden" name="cruiseShipSearchRequest.route" value="">
                        <input type="text" placeholder="不限" id="location" readonly>
                        <span class="routPlaceholder">不限</span>
                        <ul class="datalist" id="location-list">
                            <li data-id="">不限</li>
                            <#list cruiseshipLines as cruiseshipLine >
                                <li data-id="${cruiseshipLine.id}">${cruiseshipLine.name}</li>
                            </#list>
                        </ul>
                    </div>
                    <div class="form-group">
                        <label for="">邮轮品牌：</label>
                        <input id="search-cruiseship-brand" type="hidden" name="cruiseShipSearchRequest.brand" value="">
                        <input type="text" placeholder="不限" id="category" readonly>
                        <span class="brandPlaceholder">不限</span>
                        <ul class="datalist" id="category-list">
                            <li data-id="">不限</li>
                            <#list categorieList as categore>
                                <li data-id="${categore.id}">${categore.name}</li>
                            </#list>
                        </ul>
                    </div>
                    <div class="form-group">
                        <label for="">开航日期：</label>
                        <input readonly type="text" name="cruiseShipSearchRequest.date" id="departureDate" onclick="WdatePicker({doubleCalendar:true, readOnly:true, minDate:'%y-%M-%d'})">
                    </div>
                    <a class="btn-search" href="javascript:void(0);" onclick="$('#search-form-cruiseship').submit()">
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
            当季热门
        </div>
        <div class="recommend-right pull-right">
            <a href="/yhypc/cruiseShip/list.jhtml">更多>></a>
        </div>
    </div>
    <div class="recommend-content">
        <div class="recommend-content-section clearfix">
            <div class="aside-wrap pull-left">
                <div class="aside">
                    <div class="category">
                        <div class="aside-title"></div>
                        <span>邮轮航线</span>
                    </div>

                    <#list cruiseshipLines as cruiseshipLine >
                        <#if (cruiseshipLine_index < 3) >
                            <div class="loaction-port">
                                <form id="form-cruseship-line_${cruiseshipLine_index}" method="post" action="/yhypc/cruiseShip/list.jhtml">
                                    <input type="hidden" name="cruiseShipSearchRequest.route" value="${cruiseshipLine.id}">
                                    <a href="javascript:void(0);" onclick="$('#form-cruseship-line_${cruiseshipLine_index}').submit()">${cruiseshipLine.name}</a>
                                </form>
                            </div>
                        </#if>
                    </#list>
                </div>
            </div>
            <div class="recommend-content-middle pull-left">
                <#list hotSeasonCruiseShipList as hotSeasonCruiseShip>
                    <#if (hotSeasonCruiseShip_index == 0)>
                        <div class="content-middle-imgbox">
                            <a href="/cruiseship_detail_${hotSeasonCruiseShip.dateId}.html">
                                <#if (hotSeasonCruiseShip.coverImage != null) >
                                    <img src="${QINIU_BUCKET_URL}${hotSeasonCruiseShip.coverImage}?imageView2/1/w/358/h/287/q/75">
                                <#else>
                                    <img src="/image/cruises-columb-product-img-big.png">
                                </#if>
                            </a>
                        </div>
                        <div class="content-middle-textbox">
                            <h3>
                                <a href="/cruiseship_detail_${hotSeasonCruiseShip.dateId}.html" data-id="${hotSeasonCruiseShip.id}">
                                    <#if (hotSeasonCruiseShip.name)?? && ((hotSeasonCruiseShip.name)?length > 16) >
                                    ${hotSeasonCruiseShip.name?substring(0,16)}..
                                    <#else>
                                    ${(hotSeasonCruiseShip.name)!''}
                                    </#if>
                                </a>
                            </h3>
                            <#--<p>处女星号邮轮沿途停靠各港口时，您将可自由选择您喜爱的岸上观光，而我们将非常乐意为您安排... ...-->
                            </p>
                            <div class="content-middle-info clearfix">
                                <a class="pull-left clearfix" href="/cruiseship_detail_${hotSeasonCruiseShip.dateId}.html"><i class="first-i pull-left">${hotSeasonCruiseShip.startDate?string("MM月dd日")}</i><i class="second-i pull-right">${hotSeasonCruiseShip.startCity}出发</i></a>
                                <span class="pull-right"><sub>￥</sub>${hotSeasonCruiseShip.price}<sub>起</sub></span>
                            </div>
                        </div>
                    </#if>
                </#list>

            </div>
            <div class="recommend-content-right pull-right">
                <ul class="clearfix">
                    <#list hotSeasonCruiseShipList as hotSeasonCruiseShip>
                        <#if (hotSeasonCruiseShip_index > 0)>
                            <li>
                                <div class="cotent-right-imgbox">
                                    <a href="/cruiseship_detail_${hotSeasonCruiseShip.dateId}.html">
                                        <#if (hotSeasonCruiseShip.coverImage != null) >
                                            <img src="${QINIU_BUCKET_URL}${hotSeasonCruiseShip.coverImage}?imageView2/1/w/246/h/150/q/75">
                                        <#else>
                                            <img src="/image/cruises-columb-product-img-big.png">
                                        </#if>
                                    </a>
                                </div>
                                <div class="content-right-textbox">
                                    <h3>
                                        <a href="/cruiseship_detail_${hotSeasonCruiseShip.dateId}.html">
                                            <#if (hotSeasonCruiseShip.name)?? && ((hotSeasonCruiseShip.name)?length > 15) >
                                            ${hotSeasonCruiseShip.name?substring(0,15)}..
                                            <#else>
                                            ${(hotSeasonCruiseShip.name)!''}
                                            </#if>
                                        </a>
                                    </h3>
                                    <div class="content-right-info clearfix">
                                        <a href="/cruiseship_detail_${hotSeasonCruiseShip.dateId}.html" class="pull-left clearfix"><i class="first-i pull-left">${hotSeasonCruiseShip.startDate?string("MM月dd日")}</i><i class="second-i pull-right">${hotSeasonCruiseShip.startCity}出发</i></a>
                                        <span class="pull-right"><sub>￥</sub>${hotSeasonCruiseShip.price}<sub>起</sub></span>
                                    </div>
                                </div>
                            </li>
                        </#if>
                    </#list>
                </ul>
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
            <a href="/yhypc/cruiseShip/list.jhtml">更多>></a>
        </div>
    </div>
    <div class="recommend-content">
        <div class="recommend-content-section clearfix">
            <div class="aside-wrap pull-left">
                <div class="aside">
                    <div class="category">
                        <div class="aside-title"></div>
                        <span>邮轮品牌</span>
                    </div>
                    <#list categorieList as categore>
                        <#if (categore_index < 3)>
                            <div class="loaction-port">
                                <form id="form-cruseship-brand_${categore_index}" method="post" action="/yhypc/cruiseShip/list.jhtml">
                                    <input type="hidden" name="cruiseShipSearchRequest.brand" value="${categore.id}">
                                    <a href="javascript:void(0);" onclick="$('#form-cruseship-brand_${categore_index}').submit()">${categore.name}</a>
                                </form>
                            </div>
                        </#if>
                    </#list>

                    <#--<div class="loaction-port">
                        <a href="#">歌诗达邮轮</a>
                    </div>
                    <div class="loaction-port">
                        <a href="#">海达路德邮轮</a>
                    </div>-->
                </div>
            </div>
            <div class="recommend-content-middle pull-left">
                <#list hotRecCruiseShipList as hotRecCruiseShip>
                    <#if (hotRecCruiseShip_index == 0)>
                        <div class="content-middle-imgbox">

                            <a href="/cruiseship_detail_${hotRecCruiseShip.dateId}.html">
                                <#if (hotRecCruiseShip.coverImage != null) >
                                        <img src="${QINIU_BUCKET_URL}${hotRecCruiseShip.coverImage}?imageView2/1/w/358/h/287/q/75">
                                    <#else>
                                        <img src="/image/cruises-columb-product-img-big.png">
                                </#if>
                            </a>
                        </div>
                        <div class="content-middle-textbox">
                            <h3>
                                <a href="/cruiseship_detail_${hotRecCruiseShip.dateId}.html" data-id="${hotRecCruiseShip.id}">
                                    <#if (hotRecCruiseShip.name)?? && ((hotRecCruiseShip.name)?length > 16) >
                                    ${hotRecCruiseShip.name?substring(0,16)}..
                                    <#else>
                                    ${(hotRecCruiseShip.name)!''}
                                    </#if>
                                </a>
                            </h3>
                        <#--<p>处女星号邮轮沿途停靠各港口时，您将可自由选择您喜爱的岸上观光，而我们将非常乐意为您安排... ...
                        </p>-->
                            <#--描述直接写在P里面  没有内容也不要删除P标签-->
                            <p></p>
                            <div class="content-middle-info clearfix">
                                <a class="pull-left clearfix" href="/cruiseship_detail_${hotRecCruiseShip.dateId}.html"><i class="first-i pull-left">${hotRecCruiseShip.startDate?string("MM月dd日")}</i><i class="second-i pull-right">${hotRecCruiseShip.startCity}出发</i></a>
                                <span class="pull-right"><sub>￥</sub>${hotRecCruiseShip.price}<sub>起</sub></span>
                            </div>
                        </div>
                    </#if>
                </#list>

            </div>
            <div class="recommend-content-right pull-right">
                <ul class="clearfix">

                    <#list hotRecCruiseShipList as hotRecCruiseShip>
                        <#if (hotRecCruiseShip_index > 0)>
                            <li>
                                <div class="cotent-right-imgbox">
                                    <a href="/cruiseship_detail_${hotRecCruiseShip.dateId}.html">
                                        <#if (hotRecCruiseShip.coverImage != null) >
                                            <img src="${QINIU_BUCKET_URL}${hotRecCruiseShip.coverImage}?imageView2/1/w/246/h/150/q/75">
                                        <#else>
                                            <img src="/image/cruises-columb-product-img-small.png">
                                        </#if>
                                    </a>
                                </div>
                                <div class="content-right-textbox">
                                    <h3>
                                        <a href="/cruiseship_detail_${hotRecCruiseShip.dateId}.html">
                                            <#if (hotRecCruiseShip.name)?? && ((hotRecCruiseShip.name)?length > 15) >
                                            ${hotRecCruiseShip.name?substring(0,15)}..
                                            <#else>
                                            ${(hotRecCruiseShip.name)!''}
                                            </#if>
                                        </a>
                                    </h3>
                                    <div class="content-right-info clearfix">
                                        <a href="/cruiseship_detail_${hotRecCruiseShip.dateId}.html" class="pull-left clearfix"><i class="first-i pull-left">${hotRecCruiseShip.startDate?string("MM月dd日")}</i><i class="second-i pull-right">${hotRecCruiseShip.startCity}出发</i></a>
                                        <span class="pull-right"><sub>￥</sub>${hotRecCruiseShip.price}<sub>起</sub></span>
                                    </div>
                                </div>
                            </li>
                        </#if>
                    </#list>
                </ul>
            </div>
        </div>
    </div>
</div>