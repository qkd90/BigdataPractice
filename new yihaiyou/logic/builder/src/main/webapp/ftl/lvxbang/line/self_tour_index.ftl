<div class="bodybg">
    <div class="w1000 cl section">


    <#--自助游首页顶部左侧图片 start-->
    <#if stiTopLeftLogoAd??>
        <img width="320" style="float: left;"
             data-original="/static${stiTopLeftLogoAd.imgPath}" alt="${stiTopLeftLogoAd.adTitle}"/>
    <#else>
        <img width="320" style="float: left;" data-original="/images/zizhupindaoLogo.jpg"/>
    </#if>
    <#--自助游首页顶部左侧图片 start-->

    <#--自助游首页右侧数据 满意度,关注人数,点评数 start-->
        <#--<div class="top-states-h">-->
            <#--<div class="sati-rate">-->
                <#--<span class="icon-sati"></span>-->
                <#--<span>满意度</span>-->
                <#--<em class="orange">100</em><span class="orange">%</span>-->
            <#--</div>-->
            <#--<div class="grayline">-->

            <#--</div>-->
            <#--<div class="fans-count">-->
                <#--<span class="icon-fans"></span>已关注人数：<em>2779361人</em>-->
            <#--</div>-->
            <#--<div class="grayline">-->
            <#--</div>-->
            <#--<div class="comments-count">-->
                <#--<span class="icon-comments"></span>已有点评数：<em>180974条</em>-->
            <#--</div>-->
            <#--<div class="promotion">-->
                <#--<p class="gticon tip">网上预订优惠 点评还可返现</p>-->
            <#--</div>-->
        <#--</div>-->
    <#--自助游首页右侧数据 满意度,关注人数,点评数 end-->

    <#--自助游图片幻灯左侧导航 start-->
        <div id="block_707305" class="topcon an_mo fl">
            <div id="block" class="block clearfix"><!-- txtModule txtMself start -->
                <!--左侧导航 start-->
                <div class="navi">
                    <ul>
                    <#list topNavLabelList as topLabel>
                        <li>
                            <h4><i class="i${topLabel_index + 1}"></i>${topLabel.alias}</h4>
                            <#--<span class="arrow tn_fontface"></span>-->
                            <#list stiTopNavData[topLabel.name + "_fst_area"] as fstArea>
                                <a href="/self_tour_${fstArea.cityCode}.html" target="_blank">${fstArea.name}</a>
                            </#list>
                            <#if stiTopNavData[topLabel.name + "_snd_label"]?size &gt; 0>
                                <div class="outer outer${topLabel_index + 1}">
                                    <div class="outerbox">
                                    <#list stiTopNavData[topLabel.name + "_snd_label"] as sndLabel>
                                        <dl class="clearfix">
                                            <dd><span>${sndLabel.alias}：</span></dd>
                                            <dt>
                                            <#list stiTopNavData[sndLabel.name + "_trd_area"] as trdArea>
                                                <a href="/self_tour_${trdArea.cityCode}.html"
                                                   target="_blank">${trdArea.name}</a>|
                                            </#list>
                                            </dt>
                                        </dl>
                                    </#list>
                                    </div>
                                </div>
                            </#if>
                        </li>
                    </#list>
                    </ul>
                </div>
                <!--左侧导航 end-->
                <!-- txtModule txtMself end -->
            </div>
        </div>
    <#--自助游图片幻灯左侧导航 end-->

    <#--自助游图片幻灯 start -->
        <div class="slide">
            <div id="focus">
                <ul>
                    <#list topAds as topAd>
                        <li>
                            <div style="left:0;top:0;">
                                <a href="${PLAN_PATH}${topAd.url}">
                                    <img width="800" height="367" src="/static${topAd.imgPath}"/>
                                </a>
                            </div>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
    <#--自助游图片幻灯 start -->

    </div>


<#-- 各个主版块 start-->
    <div class="main cl">
        <div class="w1000">
            <div id="group_1727" class="horizontal zhutizijia clearfix"><h2>周边自助自驾</h2>
                <ul class="clearfix mytab">
                    <#list stiMainZhoubianLabelList as label>
                        <li id="block_l${label.id}" class="<#if label_index == 0>on</#if>"><a href="javascript:void(0)" rel="nofollow">${label.alias}</a></li>
                    </#list>
                </ul>
                <div id="tabs" class="line-con">
                    <#list stiMainZhoubianLabelList as label>
                    <div id="block_1113980" class="linebox an_mo <#if label_index == 0>now</#if>">
                        <div id="block" class="block clearfix"><!-- paihangModule phMleft start -->
                            <div class="guanzhu">
                                <#list stiZhoubianData[label.name + "_area"] as area>
                                <a href="/self_tour_${area.cityCode}.html" target="_blank">
                                    <dl><dd title="${area.fullPath}">${area.name}</dd>
                                    <dt><i class="tn_fontface"></i></dt></dl>
                                </a>
                                </#list>
                            </div>
                            <!-- paihangModule phMleft end --><!-- adModule adMleftad2 start -->

                            <div class="leftad leftad_2">
                                <a target="_blank"><img width="220" data-original="/images/shanshuilouceng.png"></a>
                            </div>
                            <!-- adModule adMleftad2 end --><!-- proRecomModule prdMblack start -->
                            <#list stiZhoubianData[label.name + "_line"] as line>
                                <div class="item">
                                    <div class="pic">
                                        <a href="/line_detail_${line.id}.html" target="_blank">
                                            <#if line.cover?starts_with("http://")>
                                                <img width="100%" data-original="${line.cover}" alt="${line.coverDesc}"/>
                                            <#else >
                                                <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/250/h/188/q/80" alt="${line.coverDesc}"/>
                                            </#if>
                                        <a href="/line_detail_${line.id}.html" target="_blank"
                                           title="${line.appendTitle}" class="picmask"></a>
                                        <a href="/line_detail_${line.id}.html" target="_blank" class="words">
                                        ${line.appendTitle}</a>
                                    </div>
                                    <div class="price">
                                        <#if line.minPrice != "暂无报价">
                                            <span>¥</span>${line.minPrice}<span>元起</span>
                                        </#if>
                                        <span class="myd">满意度：<em>${line.satisfaction}%</em></span>
                                    </div>
                                    <div class="name">
                                        <a href="/line_detail_${line.id}.html" target="_blank"
                                           title="${line.appendTitle}"> &lt;${line.name}&gt;${line.appendTitle}</a>
                                    </div>
                                </div>
                            </#list>
                            <!-- proRecomModule prdMblack end --><!-- txtModule txtMself start -->
                            <a href="/lvxbang/line/stiMore.jhtml?w=${label.alias}" target="_blank" class="more">查看更多&gt;</a>
                            <!-- txtModule txtMself end -->
                        </div>
                    </div>
                    </#list>
                </div>
            </div>



            <div id="group_1727" class="horizontal zhutizijia clearfix"><h2>国内自助自驾</h2>
                <ul class="clearfix mytab">
                <#list stiMainGuoneiLabelList as label>
                    <li id="block_l${label.id}" class="<#if label_index == 0>on</#if>"><a href="javascript:void(0)" rel="nofollow">${label.alias}</a></li>
                </#list>
                </ul>
                <div id="tabs" class="line-con">
                <#list stiMainGuoneiLabelList as label>
                    <div id="block_1113980" class="linebox an_mo <#if label_index == 0>now</#if>">
                        <div id="block" class="block clearfix"><!-- paihangModule phMleft start -->
                            <div class="guanzhu">
                                <#list stiGuoneiData[label.name + "_area"] as area>
                                    <a href="/self_tour_${area.cityCode}.html" target="_blank">
                                        <dl>
                                            <dd title="${area.fullPath}">${area.name}</dd>
                                            <dt><i class="tn_fontface"></i></dt>
                                        </dl>
                                    </a>
                                </#list>
                            </div>
                            <!-- paihangModule phMleft end --><!-- adModule adMleftad2 start -->

                            <div class="leftad leftad_2">
                                <a target="_blank"><img width="220" data-original="/images/zhutizijia.png"></a>

                            </div>
                            <!-- adModule adMleftad2 end --><!-- proRecomModule prdMblack start -->
                            <#list stiGuoneiData[label.name + "_line"] as line>
                                <div class="item">
                                    <div class="pic">
                                        <a href="/line_detail_${line.id}.html" target="_blank">
                                            <#if line.cover?starts_with("http://")>
                                                <img width="100%" data-original="${line.cover}" alt="${line.coverDesc}"/>
                                            <#else >
                                                <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/250/h/188/q/80" alt="${line.coverDesc}"/>
                                            </#if>
                                        <a href="/line_detail_${line.id}.html" target="_blank"
                                           title="${line.appendTitle}" class="picmask"></a>
                                        <a href="/line_detail_${line.id}.html" target="_blank" class="words">
                                            ${line.appendTitle}</a>
                                    </div>
                                    <div class="price">
                                        <#if line.minPrice != "暂无报价">
                                            <span>¥</span>${line.minPrice}<span>元起</span>
                                        </#if>
                                        <span class="myd">满意度：<em>${line.satisfaction}%</em></span>
                                    </div>
                                    <div class="name">
                                        <a href="/line_detail_${line.id}.html" target="_blank"
                                           title="${line.appendTitle}">&lt;${line.name}&gt;${line.appendTitle}</a>
                                    </div>
                                </div>
                            </#list>
                            <!-- proRecomModule prdMblack end --><!-- txtModule txtMself start -->
                            <a href="/lvxbang/line/stiMore.jhtml?w=${label.alias}" target="_blank" class="more">查看更多&gt;</a>
                            <!-- txtModule txtMself end -->
                        </div>
                    </div>
                </#list>
                </div>
            </div>





            <#--<div id="group_1727" class="horizontal zhutizijia clearfix"><h2>出境自助自驾</h2>-->
                <#--<ul class="clearfix mytab">-->
                <#--<#list stiMainChujingLabelList as label>-->
                    <#--<li id="block_l${label.id}" class="<#if label_index == 0>on</#if>"><a href="javascript:void(0)" rel="nofollow">${label.alias}</a></li>-->
                <#--</#list>-->
                <#--</ul>-->
                <#--<div id="tabs" class="line-con">-->
                <#--<#list stiMainChujingLabelList as label>-->
                    <#--<div id="block_1113980" class="linebox an_mo <#if label_index == 0>now</#if>">-->
                        <#--<div id="block" class="block clearfix"><!-- paihangModule phMleft start &ndash;&gt;-->
                            <#--<div class="guanzhu">-->
                                <#--<#list stiChujingData[label.name + "_area"] as area>-->
                                    <#--<a href="/self_tour_${area.cityCode}.html" target="_blank">-->
                                        <#--<dl><dd title="${area.fullPath}">${area.name}</dd>-->
                                            <#--<dt><i class="tn_fontface"></i></dt></dl>-->
                                    <#--</a>-->
                                <#--</#list>-->
                            <#--</div>-->
                            <#--<!-- paihangModule phMleft end &ndash;&gt;<!-- adModule adMleftad2 start &ndash;&gt;-->

                            <#--<div class="leftad leftad_2">-->
                                <#--<a target="_blank"><img width="220" data-original="/images/zuche.png"></a>-->
                            <#--</div>-->
                            <#--<!-- adModule adMleftad2 end &ndash;&gt;<!-- proRecomModule prdMblack start &ndash;&gt;-->
                            <#--<#list stiChujingData[label.name + "_line"] as line>-->
                                <#--<div class="item">-->
                                    <#--<div class="pic">-->
                                        <#--<a href="/line_detail_${line.id}.html" target="_blank">-->
                                            <#--<#if line.cover?starts_with("http://")>-->
                                                <#--<img width="100%" data-original="${line.cover}" alt="${line.coverDesc}"/>-->
                                            <#--<#else >-->
                                                <#--<img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/250/h/188/q/80" alt="${line.coverDesc}"/>-->
                                            <#--</#if>-->
                                        <#--<a href="/line_detail_${line.id}.html" target="_blank"-->
                                           <#--title="${line.appendTitle}" class="picmask"></a>-->
                                        <#--<a href="/line_detail_${line.id}.html" target="_blank" class="words">-->
                                            <#--${line.appendTitle}</a>-->
                                    <#--</div>-->
                                    <#--<div class="price">-->
                                        <#--<span>¥</span>${line.minPrice}<span>元起</span>-->
                                        <#--&lt;#&ndash;<span class="myd">满意度：<em>100%</em></span>&ndash;&gt;-->
                                    <#--</div>-->
                                    <#--<div class="name">-->
                                        <#--<a href="/line_detail_${line.id}.html" target="_blank"-->
                                           <#--title="${line.appendTitle}">${line.name}</a>-->
                                    <#--</div>-->
                                <#--</div>-->
                            <#--</#list>-->
                            <#--<!-- proRecomModule prdMblack end &ndash;&gt;<!-- txtModule txtMself start &ndash;&gt;-->
                            <#--<a href="/lvxbang/line/stiMore.jhtml?w=${label.alias}" target="_blank" class="more">查看更多&gt;</a>-->
                            <#--<!-- txtModule txtMself end &ndash;&gt;-->
                        <#--</div>-->
                    <#--</div>-->
                <#--</#list>-->
                <#--</div>-->
            <#--</div>-->

            <div id="block_707347" class="square youjigonglue an_mo"><h2>游记攻略</h2>
                <div id="block" class="block clearfix"><!-- adModule adMleftad3 start -->
                    <div class="leftad leftad_3">
                        <a target="_blank">
                            <img width="200" data-original="/images/index/gonglue.png"></a>
                    </div>
                    <!-- adModule adMleftad3 end --><!-- poiModule poiMpaihang start -->
                    <#list mainYoujigonglue as youji>
                        <div class="glitem">
                            <div class="pic">
                                <a href="${RECOMMENDPLAN_PATH}/guide_detail_${youji.id}.html" target="_blank">
                                    <#if youji.coverPath?starts_with("http")>
                                        <img width="100%" data-original="${youji.coverPath}" alt="${youji.planName}"/>
                                    <#else >
                                        <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${youji.coverPath}?imageView2/1/w/250/h/188/q/80" alt="${youji.planName}"/>
                                    </#if>
                                </a>
                            </div>
                            <div class="name">
                                <a href="${RECOMMENDPLAN_PATH}/guide_detail_${youji.id}.html" target="_blank">${youji.planName}</a>
                            </div>
                            <div class="det">
                                <a href="${RECOMMENDPLAN_PATH}/guide_detail_${youji.id}.html" target="_blank">
                                    <#if youji.description?length &gt; 22>
                                        ${youji.description?substring(0,22)}......
                                    <#else>
                                        ${youji.description}
                                    </#if>
                                </a>
                            </div>
                        </div>
                    </#list>
                    <!-- poiModule poiMpaihang end -->
                </div>
            </div>

            <div id="block_707345" class="square rexiaopaihangbang an_mo"><h2>热销排行</h2>
                <div id="block" class="block clearfix"><!-- proRecomModule prdMpaihang start -->
                <#list rexiaoLine as line>
                    <#if line_index &lt; 3>
                        <div class="item_">
                            <div class="pic">
                                <div class="gticon no no${line_index + 1}">${line_index + 1}</div>
                                <a href="/line_detail_${line.id}.html" target="_blank">
                                    <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/220/h/165/q/85"></a>
                            </div>
                            <div class="price">
                                <#if line.minPrice != "暂无报价">
                                    <span>¥</span>${line.minPrice}<span>元起</span>
                                </#if>
                                <span class="myd">满意度：<em>${line.satisfaction}%</em></span>
                            </div>
                            <div class="name">
                                <a href="/line_detail_${line.id}.html" target="_blank"
                                   title="${line.name}">&lt;${line.name}&gt;${line.appendTitle}</a>
                            </div>
                        </div>
                    </#if>
                </#list>
                <div class="horbox">
                <#list rexiaoLine as line>
                    <#if line_index &gt; 2>
                        <div class="horitem clearfix">
                            <div class="pic">
                                <div class="gticon no no${line_index + 1}">${line_index + 1}</div>
                                <a href="/line_detail_${line.id}.html" target="_blank" title="title="${line.name}">
                                    <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/220/h/165/q/85"></a>
                            </div>
                            <div class="det">
                                <div class="price clearfix">
                                    <#if line.minPrice != "暂无报价">
                                        <span class="pp"><em>¥</em>${line.minPrice}<em>起</em></span>
                                    </#if>
                                    <span class="mm">满意度：<em>${line.satisfaction}%</em></span>
                                </div>
                                <a href="/line_detail_${line.id}.html" target="_blank" class="name"
                                   title="${line.name}">&lt;${line.name}&gt;${line.appendTitle}</a>
                            </div>
                        </div>
                    </#if>
                </#list>
                </div>
                    <!-- proRecomModule prdMpaihang end -->
                </div>
            </div>
        </div>
<#-- 各个主版块 end-->
</div>