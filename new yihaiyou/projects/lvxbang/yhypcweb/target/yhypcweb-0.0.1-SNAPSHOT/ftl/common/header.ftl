<div class="subcity" style="display:none;">
    <h5>热门出发城市</h5>

    <div class="hotcity"><a href="#">北京</a><a href="#">上海</a><a href="#">广州</a><a href="#">成都</a><a
            href="#">杭州</a><a href="#">南京</a><a href="#" class="red">深圳</a><a href="#">武汉</a><a
            href="#">重庆</a><a href="#">济南</a><a href="#">西安</a><a href="#">郑州</a><a href="#"
                                                                                    class="red">厦门</a><a
            href="#">沈阳</a><a href="#">长沙</a><a href="#">桂林</a><a href="#">三亚</a><a href="#">哈尔滨</a></div>
    <div class="startcity">输入出发城市
        <input name="" type="text">
    </div>
    <div id="othercity">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#A" role="tab" data-toggle="tab">ABCDEF</a></li>
            <li role="presentation"><a href="#G" role="tab" data-toggle="tab">GHIJKL</a></li>
            <li role="presentation"><a href="#M" role="tab" data-toggle="tab">MNOPQR</a></li>
            <li role="presentation"><a href="#S" role="tab" data-toggle="tab">STWXYZ</a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
        <#list areaList as areaSubList>
            <div role="tabpanel" class="tab-pane <#if areaSubList_index==0>active</#if>" id="${areaSubList[0].index}">
                <#list areaSubList as area>
                    <div class="citylist <#if area_index%2==1>tab-bg</#if>">
                        <span>${area.index}</span>
                        <#list area.list as city>
                            <a href="#">${city.name}</a>
                        </#list>
                    </div>
                </#list>
            </div>
        </#list>
        </div>
    </div>
</div>