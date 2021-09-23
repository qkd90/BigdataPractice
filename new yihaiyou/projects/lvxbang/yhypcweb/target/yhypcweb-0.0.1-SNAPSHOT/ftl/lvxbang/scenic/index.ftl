<div class="section w1000 cl">
    <div class="section_l fl lh30">
        <dl class="section_dl">
            <dt><i></i>当季景点门票</dt>
            <dd>
            <#list seasonRecommendScenic as scenic>
                <a href="${SCENIC_PATH}/scenic_detail_${scenic.id?c}.html" target="_blank">${scenic.name}</a>
            </#list>
            </dd>
        </dl>
        <dl class="section_dl">
            <dt><i class="ico2"></i>热门景点门票</dt>
            <dd>
            <#list recommendScenic as scenic>
                <a href="${SCENIC_PATH}/scenic_detail_${scenic.id?c}.html" target="_blank">${scenic.name}</a>
            </#list>
            </dd>
        </dl>
        <dl class="section_dl">
            <dt><i class="ico3"></i>游玩主题</dt>
            <dd id="scenicTheme">
            <form id="searchTheme" method="post" action="${SCENIC_PATH}/scenic_list.html">
                <input id="theme" name="theme" type="hidden" value="">
            </form>
            <#list scenicTheme as theme>
                <a href="javaScript:;" theme="${theme.name}">${theme.name}</a>
            </#list>
            </dd>
        </dl>
    </div>
    <div class="Yqybanner section_fr">
        <div class="section_s">
            <div class="posiR categories fl" data-url="${SCENIC_PATH}/lvxbang/scenic/suggest.jhtml">
                <form id="search" method="post" action="${SCENIC_PATH}/scenic_list.html">
                    <input id="scenicName" name="scenicName" type="text" placeholder="输入景点" value="" class="input" autocomplete="off">
                </form>
                <div class="posiA categories_div KeywordTips">
                    <ul>
                    </ul>
                </div>
                <!--错误-->
                <div class="posiA categories_div cuowu textL">
                    <p class="cl">抱歉未找到相关的结果！</p>
                </div>
            </div>
            <a href="javaScript:;" onclick="$('#search').submit()" class="but fr"></a>

        </div>
        <div class="slide-main" id="touchMain">
            <div class="slide-box textC" id="slideContent">
            <#list adses as ads>
                <div class="slide">
                    <a stat="sslink-${ads_index + 1}" href="${SCENIC_PATH}${ads.url}" target="_blank">
                        <img src ="/static/${ads.imgPath}?imageView2/1/w/753/h/306/q/75" alt="${ads.adTitle}"/>
                    </a>
                </div>
            </#list>
            </div>
            <div class="item">
            <#list adses as ads>
                <#if ads_index = 0>
                    <a class="cur" stat="item100${ads_index + 1}" href="javascript:;"></a>
                <#else>
                    <a href="javascript:;" stat="item100${ads_index + 1}"></a>
                </#if>
            </#list>

            </div>
        </div>
    </div>
</div>
<div class="main cl">
    <div class="w1000">
        <h2 class="title">热门景点门票</h2>

        <div class="travel main_div ff_yh tile">
            <ul class="tile_ul">
            <#list recommendScenicWithImg as scenic>
                <li>
                    <div class="posiR  w323_d "><a href="${SCENIC_PATH}/scenic_detail_${scenic.id?c}.html" target="_blank">
                    	<#if scenic.cover>
                    		<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${scenic.cover}?imageView2/1/w/323/h/267/q/75" alt="${scenic.name}"/>
                    	<#else>
                    		<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jingdian.png?imageView2/1/w/323/h/267/q/75" alt="${scenic.name}"/>
                    	</#if>
                        <p class="posiA">
                            <label>${scenic.name}</label>
                            <#if scenic.scenicOther??>
                                <#if scenic.scenicOther.recommendReason?length gt 98>
                                    <span class="textL disB">${scenic.scenicOther.recommendReason?substring(0,98)}
                                        ...</span>
                                <#else >
                                    <span class="textL disB">${scenic.scenicOther.recommendReason}</span>
                                </#if>
                            </#if>
                        </p>
                        <span class="title posiA">${scenic.name}</span>
                    </a>
                    </div>
                </li>
            </#list>
            </ul>
            <p class="cl h20"></p>
        </div>
        <p class="cl h50 mt5"></p>
    </div>
</div>