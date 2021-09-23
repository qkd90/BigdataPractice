<div class="header-wrap">
    <span class="logo"></span>

    <h1 class="pageTitle">线路列表</h1>
</div>
<div class="main-wrap">
    <div class="ad-wrap">
        <div id="banner" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <#list adsList as ad>
                    <li data-target="#banner" data-slide-to="${ad_index}" class="<#if ad_index==0>active</#if>}"></li>
                </#list>
            </ol>
            <div class="carousel-inner" role="listbox">
            <#list adsList as ad>
                <div class="item <#if ad_index==0>active</#if>">
                    <a href="#">
                        <img src="${ad.imgPath}">
                    </a>
                </div>
            </#list>
        </div>
    </div>
</div>
<div class="searcher-wrap cf">
    <form action="/mobile/line/index.jhtml" method="post">
        <div class="searcher-input fl">
            <input type="text" name="planName" placeholder="输入线路名称或关键字"/>
        </div>
        <div class="searcher-button fl">
            <input type="submit" value="搜索线路"/>
        </div>
    </form>
</div>
<div class="type-select-wrap cf">
    <div class="type-button fl">
        <a href="#"><img src="/images/guonei.jpg" alt="" width="100%"/></a>
    </div>
    <div class="type-button fl">
        <a href="#"><img src="/images/zhoubian.jpg" alt="" width="100%"/></a>
    </div>
    <div class="type-button fl">
        <a href="#"><img src="/images/abroad.jpg" alt="" width="100%"/></a>
    </div>
</div>
<div class="top-line-wrap">
<#list lineList as line>
    <#if line_index==0>
        <div class="first-line">
            <a href="/mobile/line/detail.jhtml?lineId=${line.id}">
                <div class="cover">
                    <img src="${line.lineImageUrl}" alt="${line.name}"/>
                </div>
                <span class="label"><span class="price">￥${line.price}</span>元起</span>
                <span class="title">${line.name}</span>
            </a>
        </div>
    <#else >
        <div class="top-line cf">
            <a href="/mobile/line/detail.jhtml?lineId=${line.id}">
                <span class="title">${line.name}</span>
                <span class="label"><span class="price">￥${line.price}</span>起</span>
            </a>
        </div>
    </#if>
</#list>
</div>
<div class="city-wrap">
    <div class="city-type">
        <ul class="cf">
            <li class="city-type-name active">国内长线</li>
            <li class="city-type-name">周边短线</li>
            <li class="city-type-name">出境线路</li>
        </ul>
    </div>
    <div class="city-list">
        <ul class="cf">
        <#list cityList as city>
            <li class="city <#if city.city.recommended>recommended</#if> ">
            <a href="/mobile/line/index.jhtml?supplierId=${supplierId}&cityNames=${city.city.name}">${city.city.name}</a>
            </li>
        </#list>
        </ul>
        <ul class="cf hide">
            <li class="city">
                <a href="">11</a>
            </li>
        </ul>
        <ul class="cf hide">
            <li class="city">
                <a href="">22</a></li>
        </ul>
    </div>
</div>
</div>