<div id="banner" class="carousel slide" data-ride="carousel">
    <ol class="carousel-indicators">
        <#list topAds as ad>
            <li data-target="#banner" data-slide-to="${ad_index}" class="<#if ad_index==0>active</#if>"></li>
        </#list>
    </ol>
    <div class="carousel-inner" role="listbox">
    <#list topAds as ad>
        <div class="item <#if ad_index==0>active</#if>"><a href="#"><img src="${imguriPreffix}${ad.imgPath}" style="width: 1920px; height: 450px"></a></div>
    </#list>
    </div>
</div>
<div class="clearfix"></div>