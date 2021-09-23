<#-- head start -->

<#-- head end -->
<#-- top start -->
<div class="main cl mt10">
    <div class="w1000">
        <#--n_title-->
        <p class="n_title h40 lh40">
            您在这里：&nbsp;
            <#--<a href="${DESTINATION_PATH}">目的地</a>-->
            <#--&nbsp;&gt;&nbsp;<a target="_blank" href="${DESTINATION_PATH}/city_${recplan.city.id}.html">${recplan.city.name}</a>-->
            <a href="${RECOMMENDPLAN_PATH}">游记攻略</a>
            &nbsp;&gt;&nbsp;<a target="_blank" href="${RECOMMENDPLAN_PATH}/guide_list.html?cityIds=${recplan.city.id}">${recplan.city.name}游记攻略</a>
            &nbsp;&gt;&nbsp;${recplan.planName}
        </p>
            <input type="hidden" id="recplanId" value="${recplan.id}"/>
            <input type="hidden" id="targetId" value="${recplan.id}" seftType="recplan"/>
        <#--detail_top-->
        <div class="detail_top posiR">
            <p class="img fl">
                <#if recplan.user.head != null && recplan.user.head != ''>
                    <#if recplan.user.head?starts_with("http")>
                        <img src="${recplan.user.head}" alt="头像"/>
                    <#else>
                        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.user.head}" alt="头像"/>
                    </#if>
                <#else>
                    <img src="/images/toux.PNG" alt="头像"/>
                </#if>
            </p>
            <div class="fl ml10 nr">
                <b class="name">${recplan.user.nickName}</b>
                <p class="time">
                    <span class="ml10">线路${recplan.days}天 ${(recplan.startTime?string("yyyy.MM.dd"))!''}</span>
                </p>
            </div>
            <#--<a href="javascript:;" class="d_stroke choose fr mt5"><i></i>复制线路<br/>-->
                <#--<span id="top_quote_num">获取中</span>-->
                <#--<div class="posiR disn" style="display: block;">-->
                    <#--<div class="posiA tishi2" style="display: block;">-->
                        <#--<p class="tishi_p2  fs13 cl posiR"><s></s>复制线路后，添加机票、酒店即可生成自己的特色线路。 </p>-->
                    <#--</div>-->
                <#--</div>-->
            <#--</a>-->
            <#--<a href="javascript:;" class="d_collect choose fr mt5 mr20 favorite"-->
               <#--data-favorite-id="${recplan.id}" data-favorite-type="recplan"><i></i>收藏<br/></a>-->
        </div>
        <#--d_banner-->
        <div class="d_banner posiR mb10">
            <#if recplan.coverPath?starts_with("http")>
                <img src="${recplan.coverPath}" style="width:100%" alt="${recplan.planName}"/>
            <#else>
                <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/1000/h/400" style="width:100%; height: 100%" alt="${recplan.planName}"/>
            </#if>
            <div class="d_banner_d posiA">
                <p class="name ff_yh">${recplan.planName}</p>
                <ul class="service_u mb10">
                    <li class="checked"><i></i>浏览：<span id="view_num">获取中...</span></li>
                    <li class="collectNum"><i class="ico3"></i>收藏：<span>获取中...</span></li>
                    <li><i class="ico2"></i>引用：<span id="quote_num">获取中...</span></li>
                </ul>
                <div class="cl lh20" style="width: 640px">
                    ${recplan.description}
                </div>
            </div>
        </div>
    </div>
<#-- top end -->
<#-- main start -->
    <#--游记 nav start-->
    <div class="h50 cl" id="nav">
        <div class="nav d_select">
            <div class="w1000 posiR">
                <dl class="d_select_d fl mr40 fs16 b">
                    <dd class="span1 checked">游记</dd>
                    <dd class="span2">游记点评</dd>
                </dl>

                <a href="javascript:;" class="d_stroke choose fr mt5" style="color:#4cb7f5;"><i></i><b style="letter-spacing: 1pt;">复制线路</b><br/>
                    <span id="top_quote_num">获取中</span>
                    <div class="posiR disn" style="display: block;">
                        <div class="posiA tishi2" style="display: block;">
                            <p class="tishi_p2  fs13 cl posiR"><s></s>复制线路后，添加机票、酒店即可生成自己的特色线路。 </p>
                        </div>
                    </div>
                </a>
                <a href="javascript:;" class="d_collect choose fr mt5 mr20 favorite"
                   data-favorite-id="${recplan.id}" data-favorite-type="recplan"><i></i>收藏<br/></a>

                <#if recplan.lineId != null && recplan.lineId gt 0>
                <a href="${PLAN_PATH}/plan_detail_${recplan.lineId}.html" class="but2 b fl mt15 fs16">查看线路详情 &gt;></a>
                </#if>
                <#--<a href="#" class="but fr mt5 oval4 add_comment">我要点评</a>-->
            </div>
        </div>
    </div>
    <#--游记 nav end-->
    <!--游记 main start-->
    <div class="travel_main">
    <div class="w1000 cl" id="recplan_detail">
        <!--左侧 开始-->
        <div class="travel_d_l fl" id="id1">
            <!--游记 行程天 开始-->
            <#list recplan.recommendPlanDays as day>
                <div class="travel_d_l_yj" >
                    <div class="travel_title mb20">
                        <i class="d_ico d_ico_t mr20 disB fl"></i>
                        <p class="fl mt50 pt20">
                            <i class="d_ico fl d_ico_l disB"></i>
                            <span class="fl fs20 ff_yh">第${day.day}天</span>
                            <i class="d_ico fl d_ico_r disB"></i>
                        </p>
                    </div>
                        <div class="cl color6 fs14 lh25 mb20 tx_indent2">${day.description}</div>
                    <#-- 单天 景点/美食/酒店/交通 开始 -->
                    <#list day.recommendPlanTrips as trip>
                        <p class="travel_t posiR mb20 travel_sname">
                            <#if trip.tripType = 1>
                                <i class="d_ico disB fl "></i>
                                <#if trip.scenicId &gt; 0 >
                                    <a href="javascript:;" onclick="goDetail(${trip.tripType}, ${trip.scenicId});">${trip.scenicName}</a>
                                <#else >
                                    ${trip.scenicName}
                                </#if>
                            <#elseif trip.tripType = 2>
                                <i class="d_ico disB fl d_cany"></i>
                                <#if trip.delicacy?? && trip.delicacy.id >
                                    <a href="javascript:;" onclick="goDetail(${trip.tripType}, ${trip.delicacy.id});">${trip.scenicName}</a>
                                <#else >
                                    ${trip.scenicName}
                                </#if>
                            <#elseif trip.tripType = 3>
                                <i class="d_ico disB fl d_jdi"></i>
                                <#if trip.scenicId &gt; 0 >
                                    <span href="javascript:;"
                                       <#--onclick="goDetail(${trip.tripType}, ${trip.scenicId});"-->
                                            >${trip.scenicName}</span>
                                <#else >
                                    ${trip.scenicName}
                                </#if>
                            <#elseif trip.tripType = 4>
                                <i class="d_ico disB fl d_jtx"></i>
                                ${trip.scenicName}
                            <#elseif trip.tripType = 5>
                                <i class="d_ico disB fl d_qt"></i>
                                ${trip.scenicName}
                            </#if>

                        </p>
                        <div class="cl color6 fs14 lh25 mb20 tx_indent2">${trip.exa}</div>
                        <p class="cl mb20">
                            <#if trip.coverImg?? && trip.coverImg != null>
                                <#if trip.coverImg?starts_with("http")>
                                    <img src="${trip.coverImg}" class="img" alt="${trip.scenicName}"/>
                                <#else>
                                    <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${trip.coverImg}?imageView2/2/w/680" class="img" alt="${trip.scenicName}"/>
                                </#if>
                            </#if>
                        </p>
                        <#list trip.photos as photo>
                            <p class="cl mb20">
                                <#if photo.photoLarge != trip.coverImg >
                                    <#if photo.photoLarge?starts_with("http")>
                                        <#if photo.width &gt; 0 && photo.height &gt; 0>
                                            <img data-original="${photo.photoLarge}" class="img" height="${photo.height * (680/photo.width)}" alt="${trip.scenicName}"/>
                                        <#else>
                                            <img data-original="${photo.photoLarge}" class="img" alt="${trip.scenicName}"/>
                                        </#if>
                                    <#else>
                                        <#if photo.width &gt; 0 && photo.height &gt; 0>
                                        <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${photo.photoLarge}?imageView2/2/w/680" class="img"
                                             height="${photo.height * (680/photo.width)}" alt="${trip.scenicName}"/>
                                        <#else>
                                            <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${photo.photoLarge}?imageView2/2/w/680" class="img" alt="${trip.scenicName}"/>
                                        </#if>
                                    </#if>
                                </#if>
                            </p>
                        </#list>
                    <#-- 单天行程 结束 -->
                    </#list>
                </div>
            </#list>
            <!--游记 行程天 结束-->
            <hr class="hr"/>
            <!--游记点评 开始-->
            <div class="travel_d_l_dp"  id="id2">
                <p class="travel_t posiR mb20" style="margin-bottom: 25px;margin-top: 0px;"><i class="d_ico disB posiA d_sl"></i>游记点评</p>
                <ul class="travel_d_ul" id="comment_ul">

                </ul><!-- #BeginLibraryItem "/lbi/paging.lbi" -->
                <!--pager-->
                <p class="cl h30"></p>
                <img src="/images/food2.jpg" class="fr" style="margin-right:73px;" />
                <div class="m-pager st cl">

                </div>
                <!--pager end-->
                <p class="cl h30"></p><!-- #EndLibraryItem --><div class="travel_b">
                <div class="synopsis mb10" id="add_comment">
                    <p class="img fl mr10"><img data-original="/images/d_avatar.jpg" /></p>
                    <div class="fl nr mt5">
                        <span class="name mb15 disB">${recplan.planName}</span>
                        <div class="xzw_starSys ml10 mb20">
                            <div class="xzw_starBox">
                                <ul class="star" mname="35">
                                    <li><a href="javascript:void(0)" title="1" class="one-star">1</a></li>
                                    <li><a href="javascript:void(0)" title="2" class="two-stars">2</a></li>
                                    <li><a href="javascript:void(0)" title="3" class="three-stars">3</a></li>
                                    <li><a href="javascript:void(0)" title="4" class="four-stars">4</a></li>
                                    <li><a href="javascript:void(0)" title="5" class="five-stars">5</a></li>
                                </ul>
                             <#list commentScoreTypes as commentScoreType>
                                 <#if commentScoreType_index=0>
                                    <div class="current-rating" id="commentScoretypeId${commentScoreType_index+1}" commentScoreTypeId="${commentScoreType.id}"></div>
                                 </#if>
                             </#list>
                            </div>
                            <!--评价文字-->
                            <div class="description">点击星星评分（必填）</div>
                        </div>
                        <div class="message mb20 cl">
                            <textarea name="comment" id="comment" maxlength="1000" mname="1000"  class="mb10 textarea" placeholder="详细客观评价，以130字为最佳！" ></textarea>
                            <p class="fl color6">
                                您还可以输入<strong class="orange word">1000</strong>个字
                            </p>
                            <a href="javaScript:;" class="fr but oval4" onclick="saveComment();">发表评论</a>
                        </div>
                    </div>

                </div>
            </div>
                <div>
                </div>
            </div>
            <#-- 游记点评 结束 -->
        </div>
        <!-- 左侧 结束 -->
        <!--右侧 开始-->
        <div class="nav travel_d_r fr">
            <div class="r_nav">
                <p class="title fs20 b">游记目录</p>
                <div class="travel_d_r_div">
                    <#list recplan.recommendPlanDays as day>
                        <dl class="travel_d_r_dl">
                            <dt>第${day.day}天（<span>${day.scenics}</span>）<i class="d_ico disB posiA"></i></dt>
                            <dd class="disB">
                                <#list day.recommendPlanTrips as trip>
                                    <div class="travel_d_r_dl_d">
                                        <a><i class="d_ico d_jt"></i>
                                            <#if trip.tripType = 1>
                                                <i class="d_ico disB fl "></i>
                                            <#elseif trip.tripType = 2>
                                                <i class="d_ico disB fl d_cany"></i>
                                            <#elseif trip.tripType = 4>
                                                <i class="d_ico disB fl d_jtx"></i>
                                            <#elseif trip.tripType = 3>
                                                <i class="d_ico disB fl d_jdi"></i>
                                            <#elseif trip.tripType = 5>
                                                <i class="d_ico disB fl d_qt"></i>
                                            </#if>
                                            <div class="fl nr">
                                                <p class="name">${trip.scenicName}</p>
                                            <#--<p class="js">共有2篇游记提及</p>-->
                                                <#if trip.tripType = 1 || trip.tripType = 2>
                                                    <#if trip.score &gt;= 1 && trip.score &lt; 25>
                                                        <span class="hstar cl"><i class="star1"></i></span>
                                                    <#elseif trip.score &gt;= 25 && trip.score &lt; 50>
                                                        <span class="hstar cl"><i class="star2"></i></span>
                                                    <#elseif trip.score &gt;= 50 && trip.score &lt; 75>
                                                        <span class="hstar cl"><i class="star3"></i></span>
                                                    <#elseif trip.score &gt;= 75 && trip.score &lt; 100>
                                                        <span class="hstar cl"><i class="star4"></i></span>
                                                    <#elseif trip.score &gt;= 100 >
                                                        <span class="hstar cl"><i class="star5"></i></span>
                                                    </#if>
                                                </#if>
                                            </div>
                                        </a>
                                    </div>
                                </#list>
                            </dd>
                        </dl>
                    </#list>
                </div>
            </div>
        </div>
        <p class="cl h50"></p>
    </div>
    </div>
    <#-- 游记 main end -->
</div>
<#-- main end -->
<#-- footer start -->

<#-- footer end -->