<div class="posiA Addmore" style="top: 50px;">
    <i class="close"></i>
    <#--<div class="Addmore_d" id="searchHistoryTxt">-->
        <#--搜索历史：-->
    <#--</div>-->
    <dl class="Addmore_dl" id="dest_addmore_dl">
        <#--<dt>-->
        <#--<div class="Addmore_nr add_more_tab">-->
            <#--<ul>-->
                <#--<li><a data-label="A-E" href="javaScript:;">A-E</a></li>-->
                <#--<li><a data-label="F-J" href="javaScript:;">F-J</a></li>-->
                <#--<li><a data-label="K-P" href="javaScript:;">K-P</a></li>-->
                <#--<li><a data-label="Q-W" href="javaScript:;">Q-W</a></li>-->
                <#--<li><a data-label="X-Z" href="javaScript:;">X-Z</a></li>-->
            <#--</ul>-->
        <#--</div>-->
        <#--</dt>-->
        <#--<dd data-label="hot">-->
            <#--<!--<label>A</label>&ndash;&gt;-->
            <#--<div class="Addmore_nr">-->
                <#--<ul>-->
                <#--<#list hot as aHot>-->
                    <#--<li>-->
                        <#--<a href="javascript:" data-id="${aHot.id}" data-name="${aHot.name}">${aHot.name}</a>-->
                    <#--</li>-->
                <#--</#list>-->
                <#--</ul>-->
            <#--</div>-->
        <#--</dd>-->
    <#list areaMaps as areaMap>
        <dd data-label="${areaMap.name}">
            <label>${areaMap.name}</label>

            <div class="Addmore_nr">
                <ul>
                    <#list areaMap.list as area>
                        <li>
                            <a href="${HANDDRAW_PATH}/map_${area.id}.html"
                               data-id="${area.id}" data-name="${area.name}">${area.name}</a>
                        </li>
                    </#list>
                </ul>
            </div>
        </dd>
    </#list>

    </dl>

    <p class="cl"></p>

    <div class="Addmore_shadow"></div>

</div>
<script type="text/html" id="tpl-city-radio">
</script>