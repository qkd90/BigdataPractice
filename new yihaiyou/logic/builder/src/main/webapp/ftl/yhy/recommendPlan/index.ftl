<div class="youji_column">
    <div class="mainpic">
        <div class="swiper-container">
            <div class="swiper-wrapper">
            <#list adses as ads>
                <div class="swiper-slide">
                    <#if ads.imgPath != null>
                        <img src="${QINIU_BUCKET_URL}${ads.imgPath}?imageView2/1/w/975/h/500/q/75" alt="${ads.adTitle}">
                    <#else>
                        <img src="/image/index_banner01.png">
                    </#if>
                </div>
            </#list>
            </div>
            <div class="swipe-pagenavi swiper-pagination"></div>
        </div>
        <div class="seacrhbar">
            <div class="sousuo">
                <form id="form-recommendPlan-index" method="post" action="/yhypc/recommendPlan/list.jhtml">
                    <input type="text" name="recommendPlanSearchRequest.name" class="searchdizhi" placeholder="游记标题关键词">
                    <a class="search_button" href="javascript:void(0);" onclick="$('#form-recommendPlan-index').submit()">搜索</a>
                    <div style="clear: both;"></div>
                </form>
            </div>
        </div>
    </div>
    <div class="youji_content">
        <div class="faxian">
            <p><span class="niceyouji">发现好游记</span>  <a class="more" href="/yhypc/recommendPlan/list.jhtml">更多>></a></p>
            <span class="xiahuaxian"></span>
        </div>
        <div class="yjbankuai">
            <ul class="ul1">
                <#list recommendPlanList as recommendPlan>
                    <#if (recommendPlan_index < 2 && recommendPlan_index == 0) >

                        <li class="li1">
                            <a href="/recommendplan_detail_${recommendPlan.id}.html">
                                <#if (recommendPlan.dataSource != null) >
                                    <img src="${recommendPlan.coverPath}">
                                <#else >
                                    <img src="${QINIU_BUCKET_URL}${recommendPlan.coverPath}?imageView2/1/w/793/h/345/q/75">
                                </#if>
                                <div class="neirong">
                                    <div class="wenzi">
                                        <p class="title">${recommendPlan.planName}</p>
                                        <p class="suolve"><#if (recommendPlan.description)?? && ((recommendPlan.description)?length > 100) >
                                        ${recommendPlan.description?substring(0,100)}..
                                        <#else>
                                        ${(recommendPlan.description)!''}
                                        </#if></p>
                                        <p class="liulansc"> <span class="liulan">浏览：<span class="llnum">${recommendPlan.viewNum}</span>  </span>
                                            <span class="soucang">收藏:<span class="scnum">${recommendPlan.collectNum}</span> </span>
                                        </p>
                                    </div>
                                </div>
                                <div class="touxiang">
                                    <img src="${recommendPlan.user.head}">
                                </div>
                            </a>
                        </li>

                    </#if>
                    <#if (recommendPlan_index < 2 && recommendPlan_index == 1) >
                        <li class="li2">
                            <a href="/recommendplan_detail_${recommendPlan.id}.html">
                                <div class="picimg">
                                    <#if (recommendPlan.dataSource != null) >
                                        <img src="${recommendPlan.coverPath}">
                                    <#else >
                                        <img src="${QINIU_BUCKET_URL}${recommendPlan.coverPath}">
                                    </#if>
                                    <#--<img src="/image/kmp.png">-->
                                    <div class="zhengwen">
                                        <p><#if (recommendPlan.description)?? && ((recommendPlan.description)?length > 60) >
                                        ${recommendPlan.description?substring(0,60)}..
                                        <#else>
                                        ${(recommendPlan.description)!''}
                                        </#if></p>
                                        <p class="liulansc"> <span class="liulan">浏览：<span class="llnum">${recommendPlan.viewNum}</span>  </span>
                                            <span class="soucang">收藏:<span class="scnum">${recommendPlan.collectNum}</span> </span>
                                        </p>
                                        <div class="xiaotitle">
                                            <p>${recommendPlan.planName}</p>
                                            <div class="usertouxiang">
                                                <img src="${recommendPlan.user.head}">
                                                <#--<img src="/image/svxc.png">-->
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </a>
                        </li>
                    </#if>
                </#list>
                <li style="clear: both;"></li>
            </ul>
            <ul class="ul2">
                <#list recommendPlanList as recommendPlan>
                    <#if (recommendPlan_index > 1 && recommendPlan_index <4) >
                        <li class="">
                            <a href="/recommendplan_detail_${recommendPlan.id}.html">
                                <div class="picimg">
                                    <#if (recommendPlan.dataSource != null) >
                                        <img src="${recommendPlan.coverPath}">
                                    <#else >
                                        <img src="${QINIU_BUCKET_URL}${recommendPlan.coverPath}?imageView2/1/w/386/h/234/q/75">
                                    </#if>
                                    <div class="zhengwen">
                                        <p><#if (recommendPlan.description)?? && ((recommendPlan.description)?length > 60) >
                                        ${recommendPlan.description?substring(0,60)}..
                                        <#else>
                                        ${(recommendPlan.description)!''}
                                        </#if></p>
                                        <p class="liulansc"> <span class="liulan">浏览：<span class="llnum">${recommendPlan.viewNum}</span>  </span>
                                            <span class="soucang">收藏:<span class="scnum">${recommendPlan.collectNum}</span> </span>
                                        </p>
                                        <div class="xiaotitle">
                                            <p>${recommendPlan.planName}</p>
                                            <div class="usertouxiang">
                                                <img src="${recommendPlan.user.head}">
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </a>
                        </li>
                    </#if>
                    <#if (recommendPlan_index > 3 && recommendPlan_index == 4) >
                        <li class="" style="margin-right: 0px;">
                            <a href="/recommendplan_detail_${recommendPlan.id}.html">
                                <div class="picimg">
                                    <#if (recommendPlan.dataSource != null) >
                                        <img src="${recommendPlan.coverPath}">
                                    <#else >
                                        <img src="${QINIU_BUCKET_URL}${recommendPlan.coverPath}?imageView2/1/w/386/h/234/q/75">
                                    </#if>
                                    <div class="zhengwen">
                                        <p><#if (recommendPlan.description)?? && ((recommendPlan.description)?length > 60) >
                                        ${recommendPlan.description?substring(0,60)}..
                                        <#else>
                                        ${(recommendPlan.description)!''}
                                        </#if></p>
                                        <p class="liulansc"> <span class="liulan">浏览：<span class="llnum">${recommendPlan.viewNum}</span>  </span>
                                            <span class="soucang">收藏:<span class="scnum">${recommendPlan.collectNum}</span> </span>
                                        </p>
                                        <div class="xiaotitle">
                                            <p>${recommendPlan.planName}</p>
                                            <div class="usertouxiang">
                                                <img src="${recommendPlan.user.head}">
                                            </div>
                                        </div>

                                    </div>

                                </div>
                            </a>
                        </li>
                    </#if>
                </#list>
            </ul>
        </div>

    </div>
</div>