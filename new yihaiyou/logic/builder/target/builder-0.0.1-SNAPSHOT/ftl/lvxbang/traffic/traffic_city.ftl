<dl class="Addmore_dl">
    <dt>
    <div class="Addmore_nr">
        <ul>
            <li class="checked"><a href="javaScript:;">热门</a></li>
            <li><a href="javaScript:;">A-E</a></li>
            <li><a href="javaScript:;">F-J</a></li>
            <li><a href="javaScript:;">K-P</a></li>
            <li><a href="javaScript:;">Q-W</a></li>
            <li><a href="javaScript:;">X-Z</a></li>
        </ul>
    </div>
    </dt>
    <dd>
        <label></label>
        <div class="Addmore_nr">
            <ul>
            <#list hot as aArea>
                <li data-id="${aArea.id}">
                    <a href="javaScript:;" title="${aArea.name}" data-name="${aArea.name}"
                       data-id="${aArea.id}">${aArea.name}</a>
                </li>
            </#list>
            </ul>
        </div>
    </dd>
<#list flightLetterSortAreas as letterSortArea>
    <dd class="disn">
        <#list letterSortArea.letterRange as lrArea>
            <label>${lrArea.name}</label>
            <div class="Addmore_nr">
                <ul>
                    <#list lrArea.list as aArea>
                        <li data-id="${aArea.id}">
                            <a href="javaScript:;" title="${aArea.name}" data-name="${aArea.name}"
                               data-id="${aArea.id}">${aArea.name}</a>
                        </li>
                    </#list>
                </ul>
            </div>
        </#list>
    </dd>
</#list>
</dl>