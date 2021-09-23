
    <div class="full-screen-slider mb30">
        <ul class="slides" id="slides2">
        <#list adses as ads>
            <li>
                <a href="${ads.url}" target="_blank">
                    <img data-original="/static/${ads.imgPath}"  />
                </a>
            </li>
        </#list>
        </ul>
    </div>
