<div class="advert">
    <div class="swiper-container">
        <div class="swiper-wrapper">
        <#list adses as ads>
            <div class="swiper-slide">
                <#if ads.imgPath != null>
                    <img src="${QINIU_BUCKET_URL}${ads.imgPath}" alt="${ads.adTitle}">
                <#else>
                    <img src="/image/index_banner01.png">
                </#if>
            </div>
        </#list>
        </div>
        <div class="swipe-pagenavi swiper-pagination"></div>
    </div>
    <div class="outadvertBox" style="position:absolute;left:0;top:0">
        <div class="advertBox">
            <div class="dateBox clearfix">
                <div class="entrancelist">
                    <ul>
                        <li class="en_active">船票</li>
                        <li>酒店民宿</li>
                        <li>邮轮</li>
                        <li>海上休闲</li>
                        <li>门票</li>
                    </ul>
                </div>
                <div class="entrancecontian" style="display:block">
                    <div class="con_head"><span>鼓浪屿船票</span></div>
                    <div class="con_body">
                        <form id="form-index-ferry" action="/yhypc/ferry/list.jhtml" method="post">
                            <div class="aline clearfix">
                                <span class="line">航线：</span>
                                <div class="lineget">
                                    <div class="input">
                                        <span id="ferry-name-span">邮轮中心-三丘田码头（热门航线）</span>
                                        <input type="hidden" name="flightLineId" id="ferry-number" value="9F3A7072E74142D7A41D999318818922">
                                        <div class="linelist" id="lineList">
                                            <ul>
                                                <li data-departPort="邮轮中心厦鼓码头" data-arrivePort="三丘田码头" data-number="9F3A7072E74142D7A41D999318818922">邮轮中心-三丘田码头（热门航线）</li>
                                                <li data-departPort="邮轮中心厦鼓码头" data-arrivePort="内厝奥码头" data-number="4A3BA22E8D6A4CD3B54DCD52A7E1E258">邮轮中心-内厝澳码头(普通)</li>
                                                <li data-departPort="邮轮中心厦鼓码头" data-arrivePort="内厝奥码头" data-number="167B6542C83A4F94871B54DADCE049E9">邮轮中心-内厝澳码头（豪华）</li>
                                                <li data-departPort="嵩鼓码头（嵩屿）" data-arrivePort="内厝澳码头" data-number="8ADC5A482FE04AE1A9B8613179005090">嵩鼓码头-内厝澳码头（海沧出发）</li>
                                                <li data-departPort="厦门轮渡码头" data-arrivePort="三丘田码头" data-number="2BB589AD46C04BCE88B74AEBC387F1D3">厦门轮渡码头-三丘田码头（夜航）</li>
                                                <#--<li>邮轮中心厦鼓码头 - 三丘田码头</li>
                                                <li style="font-size:13px">厦门邮轮码头 - 三丘田码头（夜航）</li>
                                                <li>嵩鼓码头（嵩屿） - 内厝澳码头</li>-->
                                            </ul>
                                        </div>
                                    </div>
                                    <span class="btn down" id="line"></span>
                                </div>
                            </div>
                            <div class="aline clearfix">
                                <span class="line">出发日期：</span>
                                <div class="lineget">
                                    <div class="date_d" id="ferry-start-date-div">
                                        <input type="text" id="ferry-start-date" name="date" autocomplete="off" onclick="WdatePicker({doubleCalendar:true, readOnly:true, minDate:'%y-%M-%d'})"/>
                                    </div>
                                    <span class="btn date ferry-start-date" onclick="WdatePicker({el:'ferry-start-date', doubleCalendar:true, readOnly:true, minDate:'%y-%M-%d'})"></span>
                                </div>
                            </div>
                            <div class="search marchtwenty" onclick="$('#form-index-ferry').submit()">搜索</div>
                        </form>
                    </div>
                </div>
                <div class="entrancecontian">
                    <div class="con_head"><span>酒店民宿</span></div>
                    <div class="con_body">
                        <form id="form-index-hotel" method="post" action="/yhypc/hotel/list.jhtml">
                            <div class="aline clearfix">
                                <span class="line">入住信息：</span>
                                <div class="lineget">
                                    <div class="date_d" id="startDateDiv">
                                        <input type="text" id="startDate" value="" name="startDate" autocomplete="off" onclick="WdatePicker({doubleCalendar:true, readOnly:true, minDate:'%y-%M-{%d}', onpicked:YhyIndex.onFoucsEndTime()})">

                                    </div>
                                    <span class="btn date startDate" onclick="WdatePicker({el:'startDate', doubleCalendar:true, readOnly:true, minDate:'%y-%M-{%d}', onpicked:YhyIndex.onFoucsEndTime()})"></span>
                                <#--<div class="input">
                                    2016-12-24 <span class="week">星期六(Sat)</span>
                                </div>
                                <span class="btn date"></span>-->
                                </div>
                            </div>
                            <div class="aline clearfix">
                                <span class="line">离店日期：</span>
                                <div class="lineget">
                                    <div class="date_d" id="endDateDiv">
                                        <input type="text" id="endDate" value="" name="endDate" autocomplete="off" onclick="WdatePicker({doubleCalendar:true, readOnly:true, minDate:'#F{$dp.$D(\'startDate\',{d:1})}'})">
                                    </div>
                                    <span class="btn date endDate" onclick="WdatePicker({el:'endDate', doubleCalendar:true, readOnly:true, minDate:'#F{$dp.$D(\'startDate\',{d:1})}'})"></span>
                                <#--<div class="input">
                                    2016-12-26 <span class="week">星期一(Mon)</span>
                                </div>
                                <span class="btn date"></span>-->
                                </div>
                            </div>
                            <div class="aline clearfix">
                                <span class="line">关键词：</span>
                                <div class="lineget key">
                                    <input type="hidden" name="searchWord" id="hid-hotel-keyword">
                                    <input type="text" id="hotel-keyword"  placeholder="鼓浪屿"/>
                                </div>
                            </div>
                            <div class="search searchhotel" id="goHotelListBtn" onclick="$('#form-index-hotel').submit()">搜索</div>
                        </form>

                    </div>
                </div>
                <div class="entrancecontian">
                    <div class="con_head"><span>邮轮旅游</span></div>
                    <div class="con_body">
                        <form id="form-index-cruiseship" method="post" action="/yhypc/cruiseShip/list.jhtml">
                            <div class="aline clearfix">
                                <span class="line">邮轮航线：</span>
                                <div class="lineget">
                                    <div class="input">
                                        <input id="search-cruiseship-line" type="hidden" name="cruiseShipSearchRequest.route" value="<#if (cruiseshipLines?size > 0) >${cruiseshipLines[0].id}</#if>">
                                        <span id="cruiseship-line-name"><#if (cruiseshipLines?size > 0) >${cruiseshipLines[0].name}</#if></span>
                                        <div class="linelist" id="cruiseList">
                                            <ul>
                                            <#list cruiseshipLines as cruiseshipLine >
                                                <li data-id="${cruiseshipLine.id}" data-name="${cruiseshipLine.name}">${cruiseshipLine.name}</li>
                                            </#list>
                                            </ul>
                                        </div>
                                    </div>
                                    <span class="btn down" id="cruiseLine"></span>
                                </div>
                            </div>
                            <div class="aline clearfix">
                                <span class="line">邮轮品牌：</span>
                                <div class="lineget">
                                    <div class="input">
                                        <input id="search-cruiseship-brand" type="hidden" name="cruiseShipSearchRequest.brand" value="<#if (categorieList?size > 0) >${categorieList[0].id}</#if>">
                                        <span id="cruiseship-brand-name"><#if (categorieList?size > 0) >${categorieList[0].name}</#if></span>
                                        <div class="linelist" id="cruiseBrandList">
                                            <ul>
                                            <#list categorieList as categore>
                                                <li data-id="${categore.id}" data-name="${categore.name}">${categore.name}</li>
                                            </#list>
                                            </ul>
                                        </div>
                                    </div>
                                    <span class="btn down" id="cruisBrand"></span>
                                </div>
                            </div>
                            <div class="aline clearfix">
                                <span class="line">出发日期：</span>
                                <div class="lineget">
                                    <div class="date_d" id="cruiseship-startDate-div">
                                        <input type="text" id="cruiseship-startDate" value="" name="cruiseShipSearchRequest.date" autocomplete="off" onclick="WdatePicker({doubleCalendar:true, readOnly:true, minDate:'%y-%M-%d'})">
                                    </div>
                                    <span class="btn date cruiseship-startDate" onclick="WdatePicker({el:'cruiseship-startDate', doubleCalendar:true, readOnly:true, minDate:'%y-%M-%d'})"></span>
                                </div>
                            </div>
                            <div class="search" onclick="$('#form-index-cruiseship').submit()">搜索</div>
                        </form>

                    </div>
                </div>
                <div class="entrancecontian">
                    <div class="con_head"><span>海上休闲</span></div>
                    <div class="con_body">
                        <form id="form-index-sailboat" method="post" action="/yhypc/sailboat/list.jhtml">
                            <div class="aline clearfix">
                                <span class="line">登船地点：</span>
                                <div class="lineget">
                                    <div class="input">
                                        <span id="sailboat-scenic-name">${scenicInfos[0].name}</span>
                                        <input type="hidden" id="sailboat-scenic-id" name="ticketSearchRequest.scenicId" value="${scenicInfos[0].id}">
                                        <div class="linelist" id="sailList">
                                            <ul>
                                            <#list scenicInfos as scenicInfo>
                                                <li data-id="${scenicInfo.id}" data-name="${scenicInfo.name}">${scenicInfo.name}</li>
                                            </#list>
                                            </ul>
                                        </div>
                                    </div>
                                    <span class="btn down" id="sail"></span>
                                </div>
                            </div>
                            <div class="aline clearfix">
                                <span class="line">类型：</span>
                                <div class="lineget">
                                    <div class="input">
                                        <span id="sailboat-type-name">游艇</span>
                                        <input type="hidden" id="sailboat-type-id" name="ticketSearchRequest.ticketType" value="yacht">
                                        <div class="linelist" id="sailTypeList">
                                            <ul>
                                                <li data-id="yacht" data-name="游艇">游艇</li>
                                                <li data-id="sailboat" data-name="帆船">帆船</li>
                                                <li data-id="huanguyou" data-name="鹭岛游">鹭岛游</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <span class="btn down" id="sailType"></span>
                                </div>
                            </div>
                            <div class="search marchtwenty" onclick="$('#form-index-sailboat').submit()">搜索</div>
                        </form>
                    </div>
                </div>
                <div class="entrancecontian">
                    <form id="form-index-scenic" method="post" action="/yhypc/scenic/list.jhtml">
                        <div class="con_head"><span>景点门票</span></div>
                        <div class="con_body">
                            <div class="scenice_entance">
                                <input type="text" name="searchWord" id="form-ipt-scenic" placeholder="景点名称"/>
                            </div>
                            <div class="hotPlace">
                                <#list scenicInfoList as scenicInfo>
                                    <#if scenicInfo_index < 4>
                                        <span>${scenicInfo.name}</span>
                                    </#if>
                                </#list>
                                <#--<span>中山路</span>-->
                                <#--<span>厦大白城</span>-->
                                <#--<span>曾厝垵</span>-->
                            </div>
                            <div class="search marchtwenty" onclick="$('#form-index-scenic').submit()">搜索</div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="total">
    <div class="sectionTitle">
        <div class="super">
            <span>当季热销</span>
        </div>
        <div class="sub">
            <#--<span class="more">更多</span>-->
        </div>
    </div>
    <div class="seasonPopular">
    <#list seasonHotProductList as seasonHotProduct>
        <#if (seasonHotProduct_index == 0) >
            <#if (seasonHotProduct.targetType=='SCENIC') >
                <a href="/scenic_detail_${seasonHotProduct.targetId}.html">
            </#if>
            <#if (seasonHotProduct.targetType=='HOTEL') >
            <a href="/hotel_detail_${seasonHotProduct.targetId}.html">
            </#if>
            <#if (seasonHotProduct.targetType=='SAILBOAT') >
            <a href="/sailboat_detail_${seasonHotProduct.targetId}.html">
            </#if>
            <#if (seasonHotProduct.targetType=='CRUISESHIP') >
            <a href="/cruiseship_detail_${seasonHotProduct.targetId}.html">
            </#if>
                <div class="season_left">
                    <#if seasonHotProduct.targetSource != 'CTRIP' && seasonHotProduct.targetSource != 'ELONG' || seasonHotProduct.targetType=='SCENIC'>
                        <img src="${QINIU_BUCKET_URL}${seasonHotProduct.targetCover}?imageView2/1/w/386/h/400/q/75" alt="${seasonHotProduct.targetName}">
                    <#else>
                        <img src="${seasonHotProduct.targetCover}" alt="${seasonHotProduct.targetName}">
                    </#if>
                    <div class="picshadow">
                        <div class="product">
                            <p class="scenicline"><span></span></p>
                            <p class="scenicarea">
                                <#if (seasonHotProduct.targetName)?? && ((seasonHotProduct.targetName)?length > 12) >
                                ${seasonHotProduct.targetName?substring(0,12)}..
                                <#else>
                                ${(seasonHotProduct.targetName)!''}
                                </#if>
                            </p>
                            <p class="scenicpri"><span class="rmb">¥</span><span class="num">${seasonHotProduct.targetPrice}</span><span class="qi">起</span></p>
                        </div>
                    </div>
                </div>
            </a>
        </#if>
    </#list>


        <div class="season_center">
            <#list seasonHotProductList as seasonHotProduct>
                <#if (seasonHotProduct_index == 1) >
                    <#if (seasonHotProduct.targetType=='SCENIC') >
                    <a href="/scenic_detail_${seasonHotProduct.targetId}.html">
                    </#if>
                    <#if (seasonHotProduct.targetType=='HOTEL') >
                    <a href="/hotel_detail_${seasonHotProduct.targetId}.html">
                    </#if>
                    <#if (seasonHotProduct.targetType=='SAILBOAT') >
                    <a href="/sailboat_detail_${seasonHotProduct.targetId}.html">
                    </#if>
                    <#if (seasonHotProduct.targetType=='CRUISESHIP') >
                    <a href="/cruiseship_detail_${seasonHotProduct.targetId}.html">
                    </#if>
                        <div class="center_top">
                            <#if seasonHotProduct.targetSource != 'CTRIP' && seasonHotProduct.targetSource != 'ELONG' || seasonHotProduct.targetType=='SCENIC'>
                                <img src="${QINIU_BUCKET_URL}${seasonHotProduct.targetCover}?imageView2/1/w/386/h/190/q/75" alt="${seasonHotProduct.targetName}">
                            <#else>
                                <img src="${seasonHotProduct.targetCover}" alt="${seasonHotProduct.targetName}">
                            </#if>
                            <div class="picshadow">
                                <div class="product">
                                    <p class="scenicline"><span></span></p>
                                    <p class="scenicarea">
                                        <#if (seasonHotProduct.targetName)?? && ((seasonHotProduct.targetName)?length > 12) >
                                        ${seasonHotProduct.targetName?substring(0,12)}..
                                        <#else>
                                        ${(seasonHotProduct.targetName)!''}
                                        </#if>
                                    </p>
                                    <p class="scenicpri"><span class="rmb">¥</span><span class="num">${seasonHotProduct.targetPrice}</span><span class="qi">起</span></p>
                                </div>
                            </div>
                        </div>
                    </a>
                </#if>
                <#if (seasonHotProduct_index == 2) >
                    <#if (seasonHotProduct.targetType=='SCENIC') >
                    <a href="/scenic_detail_${seasonHotProduct.targetId}.html">
                    </#if>
                    <#if (seasonHotProduct.targetType=='HOTEL') >
                    <a href="/hotel_detail_${seasonHotProduct.targetId}.html">
                    </#if>
                    <#if (seasonHotProduct.targetType=='SAILBOAT') >
                    <a href="/sailboat_detail_${seasonHotProduct.targetId}.html">
                    </#if>
                    <#if (seasonHotProduct.targetType=='CRUISESHIP') >
                    <a href="/cruiseship_detail_${seasonHotProduct.targetId}.html">
                    </#if>
                        <div class="center_bottom">
                            <#if seasonHotProduct.targetSource != 'CTRIP' && seasonHotProduct.targetSource != 'ELONG' || seasonHotProduct.targetType=='SCENIC'>
                                <img src="${QINIU_BUCKET_URL}${seasonHotProduct.targetCover}?imageView2/1/w/386/h/190/q/75" alt="${seasonHotProduct.targetName}">
                            <#else>
                                <img src="${seasonHotProduct.targetCover}" alt="${seasonHotProduct.targetName}">
                            </#if>
                            <div class="picshadow">
                                <div class="product">
                                    <p class="scenicline"><span></span></p>
                                    <p class="scenicarea">
                                    <#if (seasonHotProduct.targetName)?? && ((seasonHotProduct.targetName)?length > 12) >
                                    ${seasonHotProduct.targetName?substring(0,12)}..
                                    <#else>
                                    ${(seasonHotProduct.targetName)!''}
                                    </#if>
                                    </p>
                                    <p class="scenicpri"><span class="rmb">¥</span><span class="num">${seasonHotProduct.targetPrice}</span><span class="qi">起</span></p>
                                </div>
                            </div>
                        </div>
                    </a>
                </#if>

            </#list>
        </div>

    <#list seasonHotProductList as seasonHotProduct>
        <#if (seasonHotProduct_index == 3) >
            <#if (seasonHotProduct.targetType=='SCENIC') >
            <a href="/scenic_detail_${seasonHotProduct.targetId}.html">
            </#if>
            <#if (seasonHotProduct.targetType=='HOTEL') >
            <a href="/hotel_detail_${seasonHotProduct.targetId}.html">
            </#if>
            <#if (seasonHotProduct.targetType=='SAILBOAT') >
            <a href="/sailboat_detail_${seasonHotProduct.targetId}.html">
            </#if>
            <#if (seasonHotProduct.targetType=='CRUISESHIP') >
            <a href="/cruiseship_detail_${seasonHotProduct.targetId}.html">
            </#if>
                <div class="season_right">
                    <#if seasonHotProduct.targetSource != 'CTRIP' && seasonHotProduct.targetSource != 'ELONG' || seasonHotProduct.targetType=='SCENIC'>
                        <img src="${QINIU_BUCKET_URL}${seasonHotProduct.targetCover}?imageView2/1/w/386/h/400/q/75" alt="${seasonHotProduct.targetName}">
                    <#else>
                        <img src="${seasonHotProduct.targetCover}" alt="${seasonHotProduct.targetName}">
                    </#if>
                    <div class="picshadow">
                        <div class="product">
                            <p class="scenicline"><span></span></p>
                            <p class="scenicarea">
                            <#if (seasonHotProduct.targetName)?? && ((seasonHotProduct.targetName)?length > 12) >
                            ${seasonHotProduct.targetName?substring(0,12)}..
                            <#else>
                            ${(seasonHotProduct.targetName)!''}
                            </#if>
                            </p>
                            <p class="scenicpri"><span class="rmb">¥</span><span class="num">${seasonHotProduct.targetPrice}</span><span class="qi">起</span></p>
                        </div>
                    </div>
                </div>
            </a>
        </#if>
    </#list>

    </div>
    <div class="sectionTitle">
        <div class="super">
            <span>热门景点</span>
        </div>
        <div class="sub">
            <a href="/yhypc/scenic/list.jhtml">
                <span class="more">更多</span>
            </a>

        </div>
    </div>
    <div class="hotScenic">
        <div class="hot_left">

            <div class="sleft">
            <#list scenicThemes as scenicTheme>
                <#if (scenicTheme_index < 3 && scenicTheme_index == 0) >
                    <form id="scenic-form-${scenicTheme.id}" method="post" action="/yhypc/scenic/list.jhtml">
                        <input type="hidden" name="labelId" value="${scenicTheme.id}">
                        <a href="javascript:;" onclick="$('#scenic-form-${scenicTheme.id}').submit();" data-id="${scenicTheme.id}">
                            <span class="smal">${scenicTheme.alias}</span>
                        </a>
                    </form>
                </#if>
                <#if (scenicTheme_index < 3 && scenicTheme_index == 1) >
                    <form id="scenic-form-${scenicTheme.id}" method="post" action="/yhypc/scenic/list.jhtml">
                        <input type="hidden" name="labelId" value="${scenicTheme.id}">
                        <a href="javascript:;" onclick="$('#scenic-form-${scenicTheme.id}').submit();" data-id="${scenicTheme.id}">
                            <span class="midle">${scenicTheme.alias}</span>
                        </a>
                    </form>
                </#if>
                <#if (scenicTheme_index < 3 && scenicTheme_index == 2) >
                    <form id="scenic-form-${scenicTheme.id}" method="post" action="/yhypc/scenic/list.jhtml">
                        <input type="hidden" name="labelId" value="${scenicTheme.id}">
                        <a href="javascript:;" onclick="$('#scenic-form-${scenicTheme.id}').submit();" data-id="${scenicTheme.id}">
                            <span class="long b">${scenicTheme.alias}</span>
                        </a>
                    </form>
                </#if>
            </#list>
            </div>
            <div class="sright">
            <#list scenicThemes as scenicTheme>
                <#if (scenicTheme_index >= 3 && scenicTheme_index < 6 && scenicTheme_index == 3) >
                    <form id="scenic-form-${scenicTheme.id}" method="post" action="/yhypc/scenic/list.jhtml">
                        <input type="hidden" name="labelId" value="${scenicTheme.id}">
                        <a href="javascript:;" onclick="$('#scenic-form-${scenicTheme.id}').submit();" data-id="${scenicTheme.id}">
                            <span class="long r">${scenicTheme.alias}</span>
                        </a>
                    </form>

                </#if>
                <#if (scenicTheme_index >= 3 && scenicTheme_index < 6 && scenicTheme_index == 4) >
                    <form id="scenic-form-${scenicTheme.id}" method="post" action="/yhypc/scenic/list.jhtml">
                        <input type="hidden" name="labelId" value="${scenicTheme.id}">
                        <a href="javascript:;" onclick="$('#scenic-form-${scenicTheme.id}').submit();" data-id="${scenicTheme.id}">
                            <span class="long g">${scenicTheme.alias}</span>
                        </a>
                    </form>

                </#if>
                <#if (scenicTheme_index >= 3 && scenicTheme_index < 6 && scenicTheme_index == 5) >
                    <form id="scenic-form-${scenicTheme.id}" method="post" action="/yhypc/scenic/list.jhtml">
                        <input type="hidden" name="labelId" value="${scenicTheme.id}">
                        <a href="javascript:;" onclick="$('#scenic-form-${scenicTheme.id}').submit();" data-id="${scenicTheme.id}">
                            <span class="smaller">${scenicTheme.alias}</span>
                        </a>
                    </form>
                </#if>
            </#list>

            </div>
        </div>
        <div class="hot_right">
            <div class="right_l">
                <#list scenicInfoList as scenicInfo>
                    <#if (scenicInfo_index < 2) >
                        <a href="/scenic_detail_${scenicInfo.id}.html">
                            <span>
                                <#if scenicInfo.cover != null>
                                    <img src="${QINIU_BUCKET_URL}${scenicInfo.cover}?imageView2/1/w/236/h/200/q/75" alt="${scenicInfo.name}">
                                <#else>
                                    <img src="/image/hot_min.png">
                                </#if>
                                <div class="scenicHotBox">
                                    <#if scenicInfo.price!=0 >
                                        <p class="scen_name"><label>${scenicInfo.name}</label></p>
                                    <#else >
                                        <p class="scen_name" style="padding: 25px 0 6px 0!important;"><label>${scenicInfo.name}</label></p>
                                    </#if>
                                    <p class="scen_price">
                                        <#if scenicInfo.price!=0 >
                                            ¥${scenicInfo.price}<label class="qi">起</label>
                                        </#if>
                                    </p>
                                    <p class="scen_satisfy">评分${scenicInfo.score}</p>
                                </div>
                            </span>
                        </a>
                    </#if>
                    <#if (scenicInfo_index >= 2 && scenicInfo_index < 3) >
                        <a href="/scenic_detail_${scenicInfo.id}.html">
                            <span class="maxspan">
                                <#if scenicInfo.cover != null>
                                    <img src="${QINIU_BUCKET_URL}${scenicInfo.cover}?imageView2/1/w/472/h/200/q/75" alt="${scenicInfo.name}">
                                <#else>
                                    <img src="/image/hot_max.png">
                                </#if>
                                <div class="scenicHotBox">
                                    <#if scenicInfo.price!=0 >
                                        <p class="scen_name"><label>${scenicInfo.name}</label></p>
                                    <#else >
                                        <p class="scen_name" style="padding: 25px 0 6px 0!important;"><label>${scenicInfo.name}</label></p>
                                    </#if>
                                    <p class="scen_price">
                                        <#if scenicInfo.price!=0 >
                                            ¥${scenicInfo.price}<label class="qi">起</label>
                                        </#if>
                                    </p>
                                    <p class="scen_satisfy">评分${scenicInfo.score}</p>
                                </div>
                            </span>
                        </a>
                    </#if>
                </#list>
            </div>
            <div class="right_r">

                <#list scenicInfoList as scenicInfo>
                    <#if (scenicInfo_index >= 3 && scenicInfo_index < 4) >
                        <a href="/scenic_detail_${scenicInfo.id}.html">
                            <span class="maxspan">
                                <#if scenicInfo.cover != null>
                                    <img src="${QINIU_BUCKET_URL}${scenicInfo.cover}?imageView2/1/w/472/h/200/q/75" alt="${scenicInfo.name}">
                                <#else>
                                    <img src="/image/hot_max.png">
                                </#if>
                                <div class="scenicHotBox">
                                    <#if scenicInfo.price!=0 >
                                        <p class="scen_name"><label>${scenicInfo.name}</label></p>
                                    <#else >
                                        <p class="scen_name" style="padding: 25px 0 6px 0!important;"><label>${scenicInfo.name}</label></p>
                                    </#if>
                                    <p class="scen_price">
                                        <#if scenicInfo.price!=0 >
                                            ¥${scenicInfo.price}<label class="qi">起</label>
                                        </#if>
                                    </p>
                                    <p class="scen_satisfy">评分${scenicInfo.score}</p>
                                </div>
                            </span>
                        </a>
                    </#if>
                    <#if (scenicInfo_index >= 4) >
                        <a href="/scenic_detail_${scenicInfo.id}.html">
                            <span>
                                <#if scenicInfo.cover != null>
                                    <img src="${QINIU_BUCKET_URL}${scenicInfo.cover}?imageView2/1/w/236/h/200/q/75" alt="${scenicInfo.name}">
                                <#else>
                                    <img src="/image/hot_min.png">
                                </#if>
                                <div class="scenicHotBox">
                                    <#if scenicInfo.price!=0 >
                                        <p class="scen_name"><label>${scenicInfo.name}</label></p>
                                        <#else >
                                        <p class="scen_name" style="padding: 25px 0 6px 0!important;"><label>${scenicInfo.name}</label></p>
                                    </#if>

                                    <p class="scen_price">
                                        <#if scenicInfo.price!=0 >
                                            ¥${scenicInfo.price}<label class="qi">起</label>
                                        </#if>
                                    </p>
                                    <p class="scen_satisfy">评分${scenicInfo.score}</p>
                                </div>
                            </span>
                        </a>
                    </#if>
                </#list>
            </div>
        </div>
    </div>
    <div class="sectionTitle">
        <div class="super">
            <span>酒店民宿</span>
        </div>
        <div class="sub">
            <a href="/yhypc/hotel/list.jhtml" data-id="">
                <span class="more">更多</span>
            </a>
        </div>
    </div>
    <div class="hotelHomestay">
        <div class="hot_left">
            <div class="hotelposition">
                <div class="pos_title">
                    <span class="pos_word">酒店商圈</span>
                    <span class="pos_line"></span>
                </div>
                <div class="xiamenarea">
                    <ul class="clearfix">
                    <#list regions as region>
                        <#if (region_index < 7)>
                            <form id="hotel-region-${region.id}" method="post" action="/yhypc/hotel/list.jhtml">
                                <input name="regionId" type="hidden" value="${region.id}">
                                <a href="javascript:;" onclick="$('#hotel-region-${region.id}').submit()">
                                    <li>${region.name}</li>
                                </a>
                            </form>
                        </#if>
                    </#list>
                    </ul>
                </div>
                <div class="pos_title">
                    <span class="pos_word">酒店星级</span>
                    <span class="pos_line pos_line2"></span>
                </div>
                <div class="xiamenbrand">
                    <ul class="clearfix">
                        <li>
                            <form id="hotel-star-5" method="post" action="/yhypc/hotel/list.jhtml">
                                <input name="star" type="hidden" value="5">
                                <a href="javascript:;" onclick="$('#hotel-star-5').submit()">
                                    <span>五星级</span>
                                </a>
                            </form>
                        </li>
                        <li>
                            <form id="hotel-star-4" method="post" action="/yhypc/hotel/list.jhtml">
                                <input name="star" type="hidden" value="4">
                                <a href="javascript:;" onclick="$('#hotel-star-4').submit()">
                                    <span>四星级</span>
                                </a>
                            </form>
                        </li>
                        <li>
                            <form id="hotel-star-3" method="post" action="/yhypc/hotel/list.jhtml">
                                <input name="star" type="hidden" value="3">
                                <a href="javascript:;" onclick="$('#hotel-star-3').submit()">
                                    <span>三星级</span>
                                </a>
                            </form>
                        </li>
                        <li>
                            <form id="hotel-star-2" method="post" action="/yhypc/hotel/list.jhtml">
                                <input name="star" type="hidden" value="2">
                                <a href="javascript:;" onclick="$('#hotel-star-2').submit()">
                                    <span>二星级</span>
                                </a>
                            </form>
                        </li>
                        <li>
                            <form id="hotel-star-1" method="post" action="/yhypc/hotel/list.jhtml">
                                <input name="star" type="hidden" value="1">
                                <a href="javascript:;" onclick="$('#hotel-star-1').submit()">
                                    <span>一星级</span>
                                </a>
                            </form>

                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="hot_right">
            <ul class="clearfix">
                <#list hotels as hotel>
                    <li>
                        <a href="/hotel_detail_${hotel.id}.html">
                            <div class="pic">
                                <#if hotel.sourceStr != 'ELONG'>
                                    <img src="${QINIU_BUCKET_URL}${hotel.cover}?imageView2/1/w/300/h/200/q/75" alt="${hotel.name}">
                                <#else>
                                    <img src="${hotel.cover}" alt="${hotel.name}">
                                </#if>
                                <div class="priceBaner">
                                    <span class="h_name">
                                    <#if (hotel.name)?? && ((hotel.name)?length > 12) >
                                    ${hotel.name?substring(0,12)}..
                                    <#else>
                                    ${(hotel.name)!''}
                                    </#if>
                                    </span>
                                    <span class="h_price"><span>¥<#if hotel.price != 0>${hotel.price}</span>元</span> <#else></span>暂无价格</span></#if>
                                </div>
                            </div>
                            <div class="address">地址：
                                <#if (hotel.address)?? && ((hotel.address)?length > 19) >
                                ${hotel.address?substring(0,19)}..
                                <#else>
                                ${(hotel.address)!''}
                                </#if>
                            </div>
                        </a>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
    <div class="sectionTitle">
        <div class="super">
            <span>海上休闲</span>
        </div>
        <div class="sub">
            <a href="/yhypc/sailboat/list.jhtml">
                <span class="more">更多</span>
            </a>
        </div>
    </div>
    <div class="saillingboat">
        <div class="hot_left">
            <div class="hotelposition sailposition">
                <div class="pos_title">
                    <span class="pos_word">登艇地点</span>
                    <span class="pos_line"></span>
                </div>
                <div class="xiamenarea">
                    <ul class="clearfix">
                        <#list scenicInfos as scenicInfo>
                            <#if (scenicInfo_index < 7) >
                                <form id="placeId_${scenicInfo_index}" method="post" action="/yhypc/sailboat/list.jhtml">
                                    <input name="ticketSearchRequest.scenicId" type="hidden" value="${scenicInfo.id}">
                                    <a href="javascript:;" onclick="$('#placeId_${scenicInfo_index}').submit()" data-id="${scenicInfo.id}">
                                        <li>${scenicInfo.name}</li>
                                    </a>
                                </form>
                            </#if>
                        </#list>
                    </ul>
                </div>
                <div class="pos_title">
                    <span class="pos_word">玩乐类型</span>
                    <span class="pos_line"></span>
                </div>
                <div class="xiamenarea xiamentype">
                    <ul class="clearfix">
                        <li>
                            <form id="ticketType_1" method="post" action="/yhypc/sailboat/list.jhtml">
                                <input name="ticketSearchRequest.ticketType" type="hidden" value="yacht">
                                <span><a href="javascript:void(0)" onclick="$('#ticketType_1').submit()">游艇</a></span>
                            </form>
                        </li>
                        <li>
                            <form id="ticketType_2" method="post" action="/yhypc/sailboat/list.jhtml">
                                <input name="ticketSearchRequest.ticketType" type="hidden" value="sailboat">
                                <span><a href="javascript:void(0)" onclick="$('#ticketType_2').submit()">帆船</a></span>
                            </form>
                        </li>
                        <li>
                            <form id="ticketType_3" method="post" action="/yhypc/sailboat/list.jhtml">
                                <input name="ticketSearchRequest.ticketType" type="hidden" value="huanguyou">
                                <span><a href="javascript:void(0)" onclick="$('#ticketType_3').submit()">鹭岛游</a></span>
                            </form>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="hot_right">
            <ul class="clearfix">
                <#list sailboatList as sailboat>
                    <#if (sailboat_index < 2)>
                        <li>
                            <div class="pic">
                                <a href="/sailboat_detail_${sailboat.id}.html">
                                    <#if sailboat.imgUrl != null>
                                        <img src="${QINIU_BUCKET_URL}${sailboat.imgUrl}?imageView2/1/w/452/h/190/q/75" alt="${sailboat.name}">
                                    <#else>
                                        <img src="/image/sailboat01.png" alt="${sailboat.name}">
                                    </#if>
                                    <span class="name">
                                    <#if (sailboat.name)?? && ((sailboat.name)?length > 18) >
                                    ${sailboat.name?substring(0,18)}..
                                    <#else>
                                    ${(sailboat.name)!''}
                                    </#if></span>
                                </a>
                            </div>
                            <div class="price">
								<span class="price_span">
									<span class="rmb">¥</span><span class="num"><#if (sailboat.price != 0) && (sailboat.price != null)>${sailboat.price}</span><span class="qi">起</span><#else></span><span class="qi">暂无价格</span></#if>
								</span>
                                <a href="/sailboat_detail_${sailboat.id}.html">
                                    <span class="book">立即预定</span>
                                </a>
                            </div>
                        </li>
                    <#else>
                        <li>
                            <div class="pic">
                                <a href="/sailboat_detail_${sailboat.id}.html">
                                    <#if sailboat.imgUrl != null>
                                        <img src="${QINIU_BUCKET_URL}${sailboat.imgUrl}?imageView2/1/w/294/h/190/q/75" alt="${sailboat.name}">
                                    <#else>
                                        <img src="/image/sailboat02.png" alt="${sailboat.name}">
                                    </#if>
                                    <span class="name"><#if (sailboat.name)?? && ((sailboat.name)?length > 15) >
                                    ${sailboat.name?substring(0,15)}..
                                    <#else>
                                    ${(sailboat.name)!''}
                                    </#if></span>
                                </a>
                            </div>
                            <div class="price">
								<span class="price_span">
									<span class="rmb">¥</span>
                                    <#if (sailboat.price != 0) && (sailboat.price != null)>
                                        <span class="num">
                                        ${sailboat.price}
                                        </span>
                                    <span class="qi">起</span>
                                    <#else>
                                        <span class="num">
                                        </span>
                                        <span class="qi">暂无价格</span>
                                    </#if>

								</span>
                                <a href="/sailboat_detail_${sailboat.id}.html">
                                    <span class="book">立即预定</span>
                                </a>
                            </div>
                        </li>
                    </#if>
                </#list>
            </ul>
        </div>
    </div>
    <div class="sectionTitle">
        <div class="super">
            <span>邮轮旅游</span>
        </div>
        <div class="sub">
            <a href="/yhypc/cruiseShip/list.jhtml">
                <span class="more">更多</span>
            </a>
        </div>
    </div>
    <div class="cruiseboat">
        <div class="hot_left">
            <div class="hotelposition cruiseposition">
                <div class="pos_title">
                    <span class="pos_word">邮轮航线</span>
                    <span class="pos_line"></span>
                </div>
                <div class="xiamenarea">
                    <ul class="clearfix">
                    <#list cruiseshipLines as cruiseshipLine >
                        <#if (cruiseshipLine_index < 4) >
                            <form id="form-cruseship-line_${cruiseshipLine_index}" method="post" action="/yhypc/cruiseShip/list.jhtml">
                                <input type="hidden" name="cruiseShipSearchRequest.route" value="${cruiseshipLine.id}">
                                <a href="javascript:;" data-id="${cruiseshipLine.id}" onclick="$('#form-cruseship-line_${cruiseshipLine_index}').submit()">
                                    <li>${cruiseshipLine.name}</li>
                                </a>
                            </form>
                        </#if>
                    </#list>
                    </ul>
                </div>
                <div class="pos_title">
                    <span class="pos_word">邮轮品牌</span>
                    <span class="pos_line"></span>
                </div>
                <div class="xiamenarea">
                    <ul class="clearfix">
                        <#list categorieList as category>
                            <#if (category_index < 4) >
                                <form id="form-cruseship-brand_${category_index}" method="post" action="/yhypc/cruiseShip/list.jhtml">
                                    <input type="hidden" name="cruiseShipSearchRequest.brand" value="${category.id}">
                                    <a href="javascript:;" data-id="${category.id}" onclick="$('#form-cruseship-brand_${category_index}').submit()">
                                        <li>${category.name}</li>
                                    </a>
                                </form>
                            </#if>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
        <div class="hot_right">
            <div class="cruise_r_left">
                <#list cruiseShipList as cruiseShip>
                    <#if (cruiseShip_index == 0) >
                        <a href="/cruiseship_detail_${cruiseShip.dateId}.html">
                            <div class="pic">
                                <div class="innerpic">
                                    <#if cruiseShip.coverImage != null>
                                        <img src="${QINIU_BUCKET_URL}${cruiseShip.coverImage}?imageView2/1/w/392/h/331/q/75" alt="${cruiseShip.name}">
                                    <#else>
                                        <img src="/image/cruise01.png" alt="${cruiseShip.name}">
                                    </#if>
                                </div>
                            </div>
                            <div class="mess">
                                <span class="name">【
                                    <#if (cruiseShip.name)?? && ((cruiseShip.name)?length > 15) >
                                    ${cruiseShip.name?substring(0,15)}..
                                    <#else>
                                    ${(cruiseShip.name)!''}
                                    </#if>
                                    】</span>
                                <span class="around"><#--2017年1月30日广州-八重山诸岛-冲绳-广州5晚6天--><p></p> </span>
                                <span class="go_time">${cruiseShip.startDate?string("MM月dd日")} <label>${cruiseShip.startCity}出发</label></span>
                                <span class="pri"><span class="rmb">¥</span><span class="num"><#if cruiseShip.price != 0 && cruiseShip.price != null>${cruiseShip.price}</span><span class="qi">起</span><#else></span><span class="qi">暂无价格</span></#if></span>
                            </div>
                        </a>
                    </#if>
                </#list>

            </div>
            <div class="cruise_r_right">
                <ul>
                <#list cruiseShipList as cruiseShip>
                    <#if (cruiseShip_index > 0) >

                            <li>
                                <a href="/cruiseship_detail_${cruiseShip.dateId}.html">
                                    <div class="pic">
                                        <div class="innerpic">
                                            <#if cruiseShip.coverImage != null>
                                                <img src="${QINIU_BUCKET_URL}${cruiseShip.coverImage}?imageView2/1/w/251/h/150/q/75" alt="${cruiseShip.name}">
                                            <#else>
                                                <img src="/image/cruise02.png" alt="${cruiseShip.name}">
                                            </#if>
                                        </div>
                                    </div>
                                    <div class="mess">
                                        <span class="name">【
                                            <#if (cruiseShip.name)?? && ((cruiseShip.name)?length > 14) >
                                            ${cruiseShip.name?substring(0,14)}..
                                            <#else>
                                            ${(cruiseShip.name)!''}
                                            </#if>
                                            】</span>
                                        <span class="go_time">${cruiseShip.startDate?string("MM月dd日")} <label>${cruiseShip.startCity}出发</label></span>
                                        <span class="pri"><span class="rmb">¥</span><span class="num"><#if cruiseShip.price != 0 && cruiseShip.price != null>${cruiseShip.price}</span><span class="qi">起</span><#else></span><span class="qi">暂无价格</span></#if></span>
                                    </div>
                                </a>
                            </li>

                    </#if>
                </#list>
                </ul>
            </div>
        </div>
    </div>
    <div class="sectionTitle">
        <div class="super">
            <span>游记攻略</span>
        </div>
        <div class="sub">
            <a href="/yhypc/recommendPlan/list.jhtml">
                <span class="more">更多</span>
            </a>
        </div>
    </div>
    <div class="travelNote">
        <#list recommendPlans as recommendPlan>
            <#if recommendPlan_index == 0>
                <div class="maxpic">
                    <a href="/recommendplan_detail_${recommendPlan.id}.html">
                        <#if recommendPlan.coverPath != null>
                            <#if recommendPlan.dataSource != null>
                                <img src="${recommendPlan.coverPath}" alt="${recommendPlan.planName}">
                            <#else >
                                <img src="${QINIU_BUCKET_URL}${recommendPlan.coverPath}?imageView2/1/w/383/h/305/q/75" alt="${recommendPlan.planName}">
                            </#if>
                            <#else>
                                <img src="/image/travelnote01.png">
                        </#if>

                        <div class="picshadow">
                            <p class="travelNum">
                                <#if (recommendPlan.planName)?? && ((recommendPlan.planName)?length > 13) >
                                ${recommendPlan.planName?substring(0,13)}..
                                <#else>
                                ${(recommendPlan.planName)!''}
                                </#if>
                            </p>
                            <span class="word"><#if (recommendPlan.description)?? && ((recommendPlan.description)?length > 54) >
                            ${recommendPlan.description?substring(0,54)}..
                            <#else>
                            ${(recommendPlan.description)!''}
                            </#if></span>
                            <span class="browse">浏览：${(recommendPlan.viewNum)!"0"}</span>
                            <span class="colected">收藏：${(recommendPlan.collectNum)!"0"}</span>
                        </div>
                    </a>
                </div>
            <#else>
                <div class="minpic">
                    <a href="/recommendplan_detail_${recommendPlan.id}.html">
                        <span class="pic">
                            <#if recommendPlan.coverPath != null>
                                <#if recommendPlan.dataSource != null>
                                    <img src="${recommendPlan.coverPath}" alt="${recommendPlan.planName}">
                                <#else >
                                    <img src="${QINIU_BUCKET_URL}${recommendPlan.coverPath}?imageView2/1/w/253/h/200/q/75" alt="${recommendPlan.planName}">
                                </#if>
                            <#else>
                                <img src="/image/travelnote02.png">
                            </#if>
                            <span>
                            <#if (recommendPlan.planName)?? && ((recommendPlan.planName)?length > 6) >
                            ${recommendPlan.planName?substring(0,6)}..
                            <#else>
                            ${(recommendPlan.planName)!''}
                            </#if>
                            </span>
                        </span>
                        <span class="word"><#if (recommendPlan.description)?? && ((recommendPlan.description)?length > 58) >
                        ${recommendPlan.description?substring(0,58)}..
                        <#else>
                        ${(recommendPlan.description)!''}
                        </#if></span>
                        <span class="browse">浏览：${(recommendPlan.viewNum)!"0"}</span>
                        <span class="browse colected">收藏：${(recommendPlan.collectNum)!"0"}</span>
                    </a>
                </div>
            </#if>
        </#list>
    </div>
    <div class="pro_position">
        <ul>
            <li class="pos_now" id="seasonPopular"><span></span>当季热销<i></i></li>
            <li id="hotScenic"><span></span>热门景点<i></i></li>
            <li id="hotelHomestay"><span></span>酒店民宿<i></i></li>
            <li id="saillingboat"><span></span>海上休闲<i></i></li>
            <li id="cruiseboat"><span></span>邮轮旅游<i></i></li>
            <li id="travelNote"><span></span>游记攻略<i></i></li>
        </ul>
    </div>
</div>