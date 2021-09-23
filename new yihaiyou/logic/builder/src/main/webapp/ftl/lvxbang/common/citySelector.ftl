    <div class="abroad" id="internal">
        <ul>
            <li class="des_color">国内</li>
            <li>境外</li>
            <span class="des_close" id="des_close"><img src="/images/index/close.png"></span>
        </ul>
    </div>
    <div class="seach_history" id="des_history">
        <ul class="selected_city">
            <li class="history_title">搜索历史：</li>
        </ul>
        <ul  class="selected_city" style="display:none">
            <li class="history_title">搜索历史：</li>
        </ul>
    </div>
    <div class="des_place" id="des_place">
        <!--国内-->
        <div class="city_change clearfix tank des_width1" style="display:block">
            <ul class="guide guide_internation">
                <li class="bg_color">热门</li>
                <li>A-E</li>
                <li>F-J</li>
                <li>K-P</li>
                <li>Q-W</li>
                <li>X-Z</li>
            </ul>
            <div class="smaller_city_change">
                <ul class="follower_city">
                <#list hot as aArea>
                    <li data-id="${aArea.id}" class="city-selector-button">${aArea.name}</li>
                </#list>
                </ul>
            </div>
        <#list letterSortAreas as letterSortArea>
            <div class="smaller_city_change" style="display:none">
                <#list letterSortArea.letterRange as lrArea>
                    <span>${lrArea.name}</span>
                    <ul class="follower_city">
                        <#list lrArea.list as aArea>
                            <li data-id="${aArea.id}" class="city-selector-button">${aArea.name}</li>
                        </#list>
                    </ul>
                </#list>
            </div>
        </#list>
        </div>
        <!--境外-->
        <div class="city_change clearfix des_width2" style="display:none">
            <ul class="internation guide_internation">
            <#list abroadAreas?keys as key>
                <li>${key}</li>
            </#list>
            </ul>
            <!--港澳台-->
        <#list abroadAreas?keys as first>
            <div class="smaller_city_change2" <#if first_index gt 0>style="display:none"</#if>>
                <ul class="follower_city2">
                    <#list abroadAreas[first] as aArea>
                        <li data-id="${aArea.id}" class="city-selector-button">${aArea.name}</li>
                    </#list>
                </ul>
            </div>
        </#list>
        </div>

    </div>
