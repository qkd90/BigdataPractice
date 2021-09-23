<div class="advert">
    <div class="swiper-container" id="scenic_index_top_banner">
        <div class="swiper-wrapper">
            <#list adses as ad>
                <div class="swiper-slide"><a href="${ad.url}"><img src="${QINIU_BUCKET_URL}${ad.imgPath}"></a></div>
            </#list>
        </div>
        <div class="swipe-pagenavi swiper-pagination" id="scenic_index_top_page"></div>
    </div>
    <div class="outadvertBox" style="position:absolute;left:0;top:0">
        <div class="advertBox">
            <div class="dateBox">
                <ul>
                    <li class="dateTitle clearfix">
                        <span class="line_l"></span>
                        <span class="line_t">景点门票</span>
                        <span class="line_l"></span>
                    </li>
                </ul>
                <input type="text" class="searchdizhi" placeholder="景点名称" id="searchWord">
                <div class="clear">
                    <ul class="hotjd">
                        <#list hotScenics as scenic>
                            <#if scenic_index &lt; 5>
                                <a target="_blank" href="/scenic_detail_${scenic.id}.html"><li title="${scenic.name}"><#if scenic.name?length &lt; 5>${scenic.name}<#else>${scenic.name?substring(0, 4)}...</#if></li></a>
                            </#if>
                        </#list>
                    </ul>
                    <div style="clear: both;"></div>
                </div>
                <div class="search_button" id="searchBtn">搜索</div>
            </div>
        </div>
    </div>
</div>
<div class="jd_content">
    <div class="remenjd" >
        <div class="remenheader">
            <span class="header_title">热门景点</span> <span> <a target="_blank" href="/yhypc/scenic/list.jhtml">更多 &gt;&gt; </a></span>
        </div>
        <div class="remennr">
            <div class="remennr_left">
                <div class="ulleft">
                    <ul>
                        <a target="_blank" href="/yhypc/scenic/list.jhtml?cityId=350203"><li class="left1">思明区</li></a>
                        <a target="_blank" href="/yhypc/scenic/list.jhtml?cityId=350205"><li class="left2">海沧区</li></a>
                        <a target="_blank" href="/yhypc/scenic/list.jhtml?cityId=350212"><li class="left3">同安区</li></a>
                    </ul>
                </div>
                <div class="ulright">
                    <ul>
                        <a target="_blank" href="/yhypc/scenic/list.jhtml?cityId=350206"><li class="right1">湖里区</li></a>
                        <a target="_blank" href="/yhypc/scenic/list.jhtml?cityId=350211"><li class="right2">集美区</li></a>
                        <a target="_blank" href="/yhypc/scenic/list.jhtml?cityId=350213"><li class="right3">翔安区</li></a>
                    </ul>
                </div>
            </div>
            <div class="remennr_right">
                <div class="right_up">
                    <ul>
                        <#list hotScenics as scenic>
                            <#if scenic_index &gt; 2><#break></#if>
                            <#macro getWidth idx><#if idx == 0 || idx == 1><#local width="215"><#else><#local width="450"></#if>${width}</#macro>
                            <li class="rightup_jd${scenic_index + 1}">
                                <a target="_blank" href="/scenic_detail_${scenic.id}.html"><div class="img">
                                    <#if scenic.cover?starts_with("http://")>
                                        <#if scenic.cover?contains("${QINIU_BUCKET_URL}")>
                                            <img src="${scenic.cover}?imageView2/2/w/<@getWidth idx=scenic_index/>/h/140">
                                        <#else><img src="${scenic.cover}">
                                        </#if>
                                        <#else><img src="${QINIU_BUCKET_URL}${scenic.cover}?imageView2/2/w/<@getWidth idx=scenic_index/>/h/140">
                                    </#if>
                                </div></a>
                                <div class="titleprice">
                                    <a target="_blank" href="/scenic_detail_${scenic.id}.html"><span class="jdbiaoti">${scenic.name}</span></a>
                                    <p class="jdjiage"><#if scenic.level?? && scenic.level?length &gt; 0><span class="manyidu">${scenic.level}</span></#if>
                                        <span class="jiage"><span class="rmb_fuhao">￥</span><span class="price_number">${scenic.price}</span>起</span>
                                    </p>
                                </div>
                            </li>
                        </#list>
                    </ul>
                </div>
                <div class="right_down">
                    <ul>
                    <#list hotScenics as scenic>
                        <#if scenic_index &gt; 2>
                        <#macro getWidth idx><#if idx == 4 || idx == 5><#local width="215"><#else><#local width="450"></#if>${width}</#macro>
                        <li class="rightdown_jd${scenic_index - 2}">
                            <a target="_blank" href="/scenic_detail_${scenic.id}.html"><div class="img">
                                <#if scenic.cover?starts_with("http://")>
                                    <#if scenic.cover?contains("${QINIU_BUCKET_URL}")>
                                        <img src="${scenic.cover}?imageView2/2/w/<@getWidth idx=scenic_index/>/h/140">
                                    <#else><img src="${scenic.cover}">
                                    </#if>
                                <#else><img src="${QINIU_BUCKET_URL}${scenic.cover}?imageView2/2/w/<@getWidth idx=scenic_index/>/h/140">
                                </#if>
                            </div></a>
                            <div class="titleprice">
                                <a target="_blank" href="/scenic_detail_${scenic.id}.html"><span class="jdbiaoti">${scenic.name}</span></a>
                                <p class="jdjiage"><#if scenic.level?? && scenic.level?length &gt; 0><span class="manyidu">${scenic.level}</span></#if>
                                    <span class="jiage"><span class="rmb_fuhao">￥</span><span class="price_number">${scenic.price}</span>起</span>
                                </p>
                            </div>
                        </li>
                        </#if>
                    </#list>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div style="clear: both;"></div>
</div>
<div class="zhutituijian">
    <div class="remenjd" >
        <div class="remenheader">
            <span class="header_title">主题推荐</span><span> <a target="_blank" href="/yhypc/scenic/list.jhtml">更多 &gt;&gt; </a></span>
        </div>
        <div class="remennr">
            <div class="remennr_left">
                <div class="ulleft">
                    <ul>
                        <#list themeLabels as label>
                            <#if label_index &lt;= 2>
                            <a target="_blank" href="/yhypc/scenic/list.jhtml?labelId=${label.id}">
                                <li data-label-id="${label.id}" class="left${label_index + 1}">${label.alias}</li>
                            </ata>
                            </#if>
                        </#list>
                    </ul>
                </div>
                <div class="ulright">
                    <ul>
                    <#list themeLabels as label>
                        <#if label_index &gt; 2>
                        <a target="_blank" href="/yhypc/scenic/list.jhtml?labelId=${label.id}">
                            <li data-label-id="${label.id}" class="right${label_index - 2}">${label.alias}</li>
                        </a>
                        </#if>
                    </#list>
                    </ul>
                </div>
            </div>
            <div class="remennr_right">
                <div class="right_up">
                    <ul>
                        <#list themeScenics as scenic>
                            <#if scenic_index &gt; 2><#break></#if>
                            <#macro getWidth idx><#if idx == 0 || idx == 1><#local width="215"><#else><#local width="450"></#if>${width}</#macro>
                            <li class="rightup_jd${scenic_index + 1}">
                                <a target="_blank" href="/scenic_detail_${scenic.id}.html"><div class="img"><div class="img">
                                    <#if scenic.cover?starts_with("http://")>
                                        <#if scenic.cover?contains("${QINIU_BUCKET_URL}")>
                                            <img src="${scenic.cover}?imageView2/2/w/<@getWidth idx=scenic_index/>/h/140">
                                        <#else><img src="${scenic.cover}">
                                        </#if>
                                    <#else><img src="${QINIU_BUCKET_URL}${scenic.cover}?imageView2/2/w/<@getWidth idx=scenic_index/>/h/140">
                                    </#if>
                                </div></a>
                                <div class="titleprice">
                                    <a target="_blank" href="/scenic_detail_${scenic.id}.html"><span class="jdbiaoti">${scenic.name}</span></a>
                                    <p class="jdjiage"><#if scenic.level?? && scenic.level?length &gt; 0><span class="manyidu">${scenic.level}</span></#if>
                                        <span class="jiage"> <span class="rmb_fuhao">￥</span><span class="price_number">${scenic.price}</span>起</span></p>
                                </div>
                            </li>
                        </#list>
                    </ul>
                </div>
                <div class="right_down">
                    <ul>
                        <#list themeScenics as scenic>
                            <#if scenic_index &gt; 2>
                                <#macro getWidth idx><#if idx == 4 || idx == 5><#local width="215"><#else><#local width="450"></#if>${width}</#macro>
                                <li class="rightdown_jd${scenic_index - 2}">
                                    <a target="_blank" href="/scenic_detail_${scenic.id}.html"><div class="img"><div class="img">
                                        <#if scenic.cover?starts_with("http://")>
                                            <#if scenic.cover?contains("${QINIU_BUCKET_URL}")>
                                                <img src="${scenic.cover}?imageView2/2/w/<@getWidth idx=scenic_index/>/h/140">
                                            <#else><img src="${scenic.cover}">
                                            </#if>
                                        <#else><img src="${QINIU_BUCKET_URL}${scenic.cover}?imageView2/2/w/<@getWidth idx=scenic_index/>/h/140">
                                        </#if>
                                    </div></a>
                                    <div class="titleprice">
                                        <a target="_blank" href="/scenic_detail_${scenic.id}.html"><span class="jdbiaoti">${scenic.name}</span></a>
                                        <p class="jdjiage"><#if scenic.level?? && scenic.level?length &gt; 0><span class="manyidu">${scenic.level}</span></#if>
                                            <span class="jiage"> <span class="rmb_fuhao">￥</span><span class="price_number">${scenic.price}</span>起</span>
                                        </p>
                                    </div>
                                </li>
                            </#if>
                        </#list>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div style="clear: both;"></div>
</div>