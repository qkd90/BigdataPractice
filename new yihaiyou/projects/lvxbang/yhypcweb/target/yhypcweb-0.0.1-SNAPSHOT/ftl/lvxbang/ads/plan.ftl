<div class="slide-main" id="touchMain">
    <a class="prev" href="javascript:;" stat="prev1001"></a>

    <div class="slide-box textC" id="slideContent">
    <#list adses as ads>
        <div class="slide">
            <a stat="sslink-${ads_index+1}" href="${ads.url}" target="_blank">
                <img data-original="/static/${ads.imgPath}"/>
            </a>
        </div>
    </#list>
    </div>
    <a class="next" href="javascript:;" stat="next1002"></a>

    <div class="item">
    <#list adses as ads><a class="<#if ads_index==0>cur</#if>" stat="item${(1001+ads_index)?c}" href="javascript:;"></a></#list>
    </div>
</div>