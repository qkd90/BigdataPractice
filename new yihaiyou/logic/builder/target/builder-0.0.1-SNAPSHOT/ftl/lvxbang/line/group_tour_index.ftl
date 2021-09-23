<div class="bodybg">
<div class="w1000 cl section">
    <#--跟团游首页顶部左侧图片 start-->
    <#if gtiTopLeftLogoAd??>
        <img width="320" style="float: left;"
             data-original="/static${gtiTopLeftLogoAd.imgPath}" alt="${gtiTopLeftLogoAd.adTitle}"/>
    <#else>
        <img width="320" style="float: left;" data-original="/images/head_top2.jpg"/>
    </#if>
    <#--跟团游首页顶部左侧图片 end-->


    <#--跟团游首页右侧数据 满意度,关注人数,点评数 start-->
    <#--<div class="top-states-h">-->
        <#--<div class="sati-rate">-->
            <#--<span class="icon-sati"></span>-->
            <#--<span>满意度</span>-->
            <#--<em class="orange">100</em><span class="orange">%</span>-->
        <#--</div>-->
        <#--<div class="grayline">-->
        <#--</div>-->
        <#--<div class="fans-count">-->
            <#--<span class="icon-fans"></span>已关注人数：<em>12095894人</em>-->
        <#--</div>-->
        <#--<div class="grayline">-->
        <#--</div>-->
        <#--<div class="comments-count">-->
            <#--<span class="icon-comments"></span>已有点评数：<em>1136279条</em>-->
        <#--</div>-->
        <#--<div class="promotion">-->
            <#--<p class="gticon tip">网上预订优惠 点评还可返现</p>-->
        <#--</div>-->
    <#--</div>-->
    <#--跟团游首页右侧数据 满意度,关注人数,点评数 end-->


    <#--跟团游图片幻灯左侧导航 start-->
    <div id="block_183452" class="topcon an_mo fl">
        <div id="block" class="block clearfix"><#-- txtModule txtMself start -->
            <div class="navi">
                <ul>
                    <#list topNavLabelList as topLabel>
                        <li>
                            <h4><i class="i${topLabel_index + 1}"></i>${topLabel.alias}</h4>
                            <#--<span class="arrow tn_fontface"></span>-->
                            <#list gtiTopNavData[topLabel.name + "_fst_area"] as fstArea>
                                <a href="/group_tour_${fstArea.cityCode}.html" target="_blank">${fstArea.name}</a>
                            </#list>
                            <#if gtiTopNavData[topLabel.name + "_snd_label"]?size &gt; 0>
                                <div class="outer outer${topLabel_index + 1}">
                                    <div class="outerbox">
                                        <#list gtiTopNavData[topLabel.name + "_snd_label"] as sndLabel>
                                            <dl class="clearfix">
                                                <dd><span>${sndLabel.alias}：</span></dd>
                                                <dt>
                                                <#list gtiTopNavData[sndLabel.name + "_trd_area"] as trdArea>
                                                    <a href="/group_tour_${trdArea.cityCode}.html" target="_blank">${trdArea.name}</a>|
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
            <#-- txtModule txtMself end -->
        </div>
    </div>
    <#--跟团游图片幻灯左侧导航 end-->


    <#--跟团游图片幻灯 start -->
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
    <#--跟团游图片幻灯 end -->
</div>


<#-- 各个主版块 start-->
<div class="main cl">
<div class="w1000">


    <#--周边跟团游 start-->
    <div id="group_497" class="horizontal zhoubiangentuanyou clearfix"><h2>周边跟团游</h2>
        <ul class="clearfix mytab">
            <#list gtiMainZhoubianLabelList as label>
                <li id="block_l${label.id}" class="<#if label_index == 0>on</#if>"><a href="javascript:void(0)">${label.alias}</a></li>
            </#list>
        </ul>
        <div id="tabs" class="line-con">
            <#list gtiMainZhoubianLabelList as label>
                <div id="block_l${label.id}" class="linebox an_mo <#if label_index == 0>now</#if>">
                    <div id="block" class="block clearfix"><#-- paihangModule phMleft start -->
                        <div class="guanzhu">
                            <#list gtiZhoubianData[label.name + "_area"] as area>
                                <a href="/group_tour_${area.cityCode}.html" target="_blank">
                                    <dl style="left: 0px;">
                                        <dd title="${area.fullName}">${area.name}</dd>
                                        <dt><i class="tn_fontface"></</i></dt>
                                    </dl>
                                </a>
                            </#list>
                        </div>
                        <#-- paihangModule phMleft end --><#-- adModule adMleftad2 start -->
                        <div class="leftad leftad_2">
                            <a target="_blank">
                                <img width="220" data-original="/images/zhoubian.png"></a>
                        </div>
                        <#-- adModule adMleftad2 end --><#-- proRecomModule prdMblack start -->
                        <#list gtiZhoubianData[label.name + "_line"] as line>
                            <div class="item">
                                <div class="pic">
                                    <a href="/line_detail_${line.id}.html" target="_blank">
                                        <#if line.cover?starts_with("http://")>
                                            <img width="100%" data-original="${line.cover}" alt="${line.coverDesc}"/>
                                        <#else >
                                            <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/250/h/188/q/80" alt="${line.coverDesc}"/>
                                        </#if>
                                    </a>
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
                                       title="${line.appendTitle}"> &lt;${line.name}&gt; ${line.appendTitle}</a>
                                </div>
                            </div>
                        </#list>
                        <#-- proRecomModule prdMblack end --><#-- txtModule txtMself start -->
                        <a href="/lvxbang/line/gtiMore.jhtml?w=${label.alias}" target="_blank" class="more">查看更多&gt;</a>
                        <#-- txtModule txtMself end -->
                    </div>
                </div>
            </#list>
        </div>
    </div>
    <#--周边跟团游 end-->


    <#-- 国内跟团游 start-->
    <div id="group_496" class="horizontal guoneigentuanyou clearfix"><h2>国内跟团游</h2>
        <ul class="clearfix mytab">
            <#list gtiMainGuoneiLabelList as label>
                <li id="block_l${label.id}" class="<#if label_index == 0>on</#if>"><a href="javascript:void(0)">${label.alias}</a></li>
            </#list>
        </ul>
        <div id="tabs" class="line-con">
            <#list gtiMainGuoneiLabelList as label>
                <div id="block_936876" class="linebox an_mo <#if label_index == 0>now</#if>">
                    <div id="block" class="block clearfix"><#-- paihangModule phMleft start -->
                        <div class="guanzhu">
                            <#list gtiGuoneiData[label.name + "_area"] as area>
                                <a href="/group_tour_${area.cityCode}.html" target="_blank">
                                    <dl>
                                        <dd title="${area.fullPath}">${area.name}</dd>
                                        <dt><i class="tn_fontface"></</i></dt>
                                    </dl>
                                </a>
                            </#list>
                        </div>
                        <#-- paihangModule phMleft end --><#-- adModule adMleftad2 start -->
                        <div class="leftad leftad_2">
                            <a target="_blank">
                                <img width="220" data-original="/images/guonei.png"></a>
                        </div>
                        <#-- adModule adMleftad2 end --><#-- proRecomModule prdMblack start -->
                        <#list gtiGuoneiData[label.name + "_line"] as line>
                            <div class="item">
                                <div class="pic">
                                    <a href="/line_detail_${line.id}.html" target="_blank">
                                        <#if line.cover?starts_with("http://")>
                                            <img width="100%" data-original="${line.cover}" alt="${line.coverDesc}"/>
                                        <#else >
                                            <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/250/h/188/q/80" alt="${line.coverDesc}"/>
                                        </#if>
                                    </a>
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
                                       title="${line.appendTitle}"> &lt;${line.name}&gt; ${line.appendTitle}</a>
                                </div>
                            </div>
                        </#list>
                        <#-- proRecomModule prdMblack end --><#-- txtModule txtMself start -->
                        <a href="/lvxbang/line/gtiMore.jhtml?w=${label.alias}" target="_blank" class="more">查看更多&gt;</a>
                        <#-- txtModule txtMself end -->
                    </div>
                </div>
            </#list>
        </div>
    </div>
    <#--国内跟团游 end-->

    <#--出境短线 start-->
    <#--<div id="group_495" class="horizontal chujingduanxian clearfix"><h2>出境短线</h2>-->
        <#--<ul class="clearfix mytab">-->
            <#--<#list gtiMainChujingDLabelList as label>-->
                <#--<li id="block_l${label.id}" class="<#if label_index == 0>on</#if>"><a href="javascript:void(0)">${label.alias}</a></li>-->
            <#--</#list>-->
        <#--</ul>-->
        <#--<div id="tabs" class="line-con">-->
            <#--<#list gtiMainChujingDLabelList as label>-->
            <#--<div id="block_297524" class="linebox an_mo <#if label_index == 0>now</#if>">-->
                <#--<div id="block" class="block clearfix">&lt;#&ndash; paihangModule phMleft start &ndash;&gt;-->
                    <#--<div class="guanzhu">-->
                        <#--<#list gtiChujingDData[label.name + "_area"] as area>-->
                            <#--<a href="/group_tour_${area.cityCode}.html" target="_blank">-->
                                <#--<dl>-->
                                    <#--<dd title="${area.fullPath}">${area.name}</dd>-->
                                    <#--<dt><i class="tn_fontface"></</i></dt>-->
                                <#--</dl>-->
                            <#--</a>-->
                        <#--</#list>-->
                    <#--</div>-->
                    <#--&lt;#&ndash; paihangModule phMleft end &ndash;&gt;&lt;#&ndash; adModule adMleftad2 start &ndash;&gt;-->
                    <#--<div class="leftad leftad_2">-->
                        <#--<a target="_blank">-->
                            <#--<img width="220" data-original="/images/duanxian.png"></a>-->
                    <#--</div>-->
                    <#--&lt;#&ndash; adModule adMleftad2 end &ndash;&gt;&lt;#&ndash; proRecomModule prdMblack start &ndash;&gt;-->
                    <#--<#list gtiChujingDData[label.name + "_line"] as line>-->
                        <#--<div class="item">-->
                            <#--<div class="pic">-->
                                <#--<a href="/line_detail_${line.id}.html" target="_blank">-->
                                    <#--<#if line.cover?starts_with("http://")>-->
                                        <#--<img width="100%" data-original="${line.cover}" alt="${line.coverDesc}"/>-->
                                    <#--<#else >-->
                                        <#--<img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/250/h/188/q/80" alt="${line.coverDesc}"/>-->
                                    <#--</#if>-->
                                <#--</a>-->
                                <#--<a href="/line_detail_${line.id}.html" target="_blank"-->
                                   <#--title="${line.appendTitle}" class="picmask"></a>-->
                                <#--<a href="/line_detail_${line.id}.html" target="_blank" class="words">-->
                                <#--${line.appendTitle}</a>-->
                            <#--</div>-->
                            <#--<div class="price">-->
                                <#--<span>¥</span>${line.minPrice}<span>元起</span>-->
                                <#--&lt;#&ndash;<span class="myd">满意度：<em>95%</em></span>&ndash;&gt;-->
                            <#--</div>-->
                            <#--<div class="name">-->
                                <#--<a href="/line_detail_${line.id}.html" target="_blank"-->
                                   <#--title="${line.appendTitle}">${line.name}</a>-->
                            <#--</div>-->
                        <#--</div>-->
                    <#--</#list>-->
                    <#--&lt;#&ndash; proRecomModule prdMblack end &ndash;&gt;&lt;#&ndash; txtModule txtMself start &ndash;&gt;-->
                    <#--<a href="/lvxbang/line/gtiMore.jhtml?w=${label.alias}" target="_blank" class="more">查看更多&gt;</a>-->
                <#--&lt;#&ndash; txtModule txtMself end &ndash;&gt;-->
                <#--</div>-->
            <#--</div>-->
            <#--</#list>-->
        <#--</div>-->
    <#--</div>-->
    <#--出境短线 end-->


    <#--出境长线 start-->
    <#--<div id="group_494" class="horizontal chujingchangxian clearfix">-->
        <#--<h2>出境长线</h2>-->
        <#--<ul class="clearfix mytab">-->
            <#--<#list gtiMainChujingCLabelList as label>-->
                <#--<li id="block_l${label.id}" class="<#if label_index == 0>on</#if>"><a href="javascript:void(0)">${label.alias}</a></li>-->
            <#--</#list>-->
        <#--</ul>-->
        <#--<div id="tabs" class="line-con">-->
            <#--<#list gtiMainChujingCLabelList as label>-->
            <#--<div id="block_183472" class="linebox an_mo <#if label_index == 0>now</#if>">-->
                <#--<div id="block" class="block clearfix">&lt;#&ndash; paihangModule phMleft start &ndash;&gt;-->
                    <#--<div class="guanzhu">-->
                        <#--<#list gtiChujingCData[label.name + "_area"] as area>-->
                            <#--<a href="/group_tour_${area.cityCode}.html" target="_blank">-->
                                <#--<dl>-->
                                    <#--<dd title="${area.fullPath}">${area.name}</dd>-->
                                    <#--<dt><i class="tn_fontface"></i></dt>-->
                                <#--</dl>-->
                            <#--</a>-->
                        <#--</#list>-->
                    <#--</div>-->
                    <#--&lt;#&ndash; paihangModule phMleft end &ndash;&gt;&lt;#&ndash; adModule adMleftad2 start &ndash;&gt;-->
                    <#--<div class="leftad leftad_2">-->
                        <#--<a target="_blank">-->
                            <#--<img width="220" data-original="/images/changxian.png"></a>-->
                    <#--</div>-->
                    <#--&lt;#&ndash; adModule adMleftad2 end &ndash;&gt;&lt;#&ndash; proRecomModule prdMblack start &ndash;&gt;-->
                    <#--<#list gtiChujingCData[label.name + "_line"] as line>-->
                        <#--<div class="item">-->
                            <#--<div class="pic">-->
                                <#--<a href="/line_detail_${line.id}.html" target="_blank">-->
                                    <#--<#if line.cover?starts_with("http://")>-->
                                        <#--<img width="100%" data-original="${line.cover}" alt="${line.coverDesc}"/>-->
                                    <#--<#else >-->
                                        <#--<img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/250/h/188/q/80" alt="${line.coverDesc}"/>-->
                                    <#--</#if>-->
                                <#--</a>-->
                                <#--<a href="/line_detail_${line.id}.html" target="_blank"-->
                                   <#--title="${line.appendTitle}" class="picmask"></a>-->
                                <#--<a href="/line_detail_${line.id}.html" target="_blank" class="words">-->
                                <#--${line.appendTitle}</a>-->
                            <#--</div>-->
                            <#--<div class="price">-->
                                <#--<span>¥</span>${line.minPrice}<span>元起</span>-->
                                <#--&lt;#&ndash;<span class="myd">满意度：<em>95%</em></span>&ndash;&gt;-->
                            <#--</div>-->
                            <#--<div class="name">-->
                                <#--<a href="/line_detail_${line.id}.html" target="_blank"-->
                                   <#--title="${line.appendTitle}">${line.name}</a>-->
                            <#--</div>-->
                        <#--</div>-->
                    <#--</#list>-->
                    <#--&lt;#&ndash; proRecomModule prdMblack end &ndash;&gt;&lt;#&ndash; txtModule txtMself start &ndash;&gt;-->
                    <#--<a href="/lvxbang/line/gtiMore.jhtml?w=${label.alias}" target="_blank" class="more">查看更多&gt;</a>-->
                <#--&lt;#&ndash; txtModule txtMself end &ndash;&gt;-->
                <#--</div>-->
            <#--</div>-->
            <#--</#list>-->
        <#--</div>-->
    <#--</div>-->
    <#--出境长线 end-->


    <#--游记攻略 start-->
    <div id="block_183536" class="square youjigonglue an_mo"><h2>游记攻略</h2>
        <div id="block" class="block clearfix"><#-- adModule adMleftad3 start -->
            <div class="leftad leftad_3">
                <a target="_blank">
                    <img width="200" data-original="/images/index/gonglue.png"></a>
            </div>
            <#-- adModule adMleftad3 end --><#-- poiModule poiMpaihang start -->
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
            <#-- poiModule poiMpaihang end -->
        </div>
    </div>
    <#--游记攻略 end-->

    <#--热销排行榜 start-->
    <div id="block_183544" class="square rexiaopaihangbang an_mo"><h2>热销排行榜</h2>
        <div id="block" class="block clearfix"><#-- proRecomModule prdMpaihang start -->
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
                                <span class="pp pp_po"><em>¥</em>${line.minPrice}<em>起</em></span>
                            </#if>
                            <span class="mm myd_po">满意度：<em>${line.satisfaction}%</em></span>
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
                            <div class="gticon no no4">4
                            </div>
                            <a href="/line_detail_${line.id}.html" target="_blank" title="${line.name}">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/220/h/165/q/85"></a>
                        </div>
                        <div class="det">
                            <div class="price clearfix">
                                <#if line.minPrice != "暂无报价">
                                    <span class="pp"><em>¥</em>${line.minPrice}<em>起</em></span>
                                </#if>
                                <#--<span class="mm">满意度：<em>96%</em></span>-->
                            </div>
                            <a href="/line_detail_${line.id}.html" target="_blank" class="name"
                               title="${line.name}">&lt;${line.name}&gt;${line.appendTitle}</a>
                        </div>
                    </div>
                </#if>
            </#list>
            </div>
            <#-- proRecomModule prdMpaihang end -->
        </div>
    </div>
    <#--热销排行榜 end-->

</div>
</div>
<#-- 各个主版块 end-->
</div>