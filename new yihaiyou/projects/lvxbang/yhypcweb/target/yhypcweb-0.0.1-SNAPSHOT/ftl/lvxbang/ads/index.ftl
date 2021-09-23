<div class="slide-main" id="touchMain">
    <a class="prev" href="javascript:;" stat="prev1001"></a>

    <div class="slide-box textC" id="slideContent">
    <#list adses as ads>
        <div class="slide">
            <a stat="sslink-1" href="${ads.url}" target="_blank">
                <img data-original="/static/${ads.imgPath}"/>
            </a>
        </div>
    </#list>
    </div>
    <a class="next" href="javascript:;" stat="next1002"></a>

    <div class="item">
    <#list adses as ads>
        <a class="<#if ads_index==1>cur</#if>" stat="item1001" href="javascript:;"></a>
    </#list>
    </div>
</div>