<div class="youji_detail">
    <div class="dahangde">
        <p class="dahang">您在这里：&nbsp;<a href="/yhypc/index/index.jhtml" class="shouye">首页</a> >  <a href="/yhypc/recommendPlan/index.jhtml" class="menu1">游记攻略首页</a> >  <a href="/yhypc/recommendPlan/list.jhtml" class="menu1">游记攻略列表</a> ><span class="menu2">${recommendPlan.planName}</span>  </p>
    </div>
    <div class="mainpic">
    <#if recommendPlan.dataSource != null>
        <img src="${recommendPlan.coverPath}" alt="${recommendPlan.planName}">
    <#else >
        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${recommendPlan.coverPath}?imageView2/1/w/1200/h/360/q/75" alt="${recommendPlan.planName}">
    </#if>
        <div class="seacrhbar">
            <p class="detail_title">${recommendPlan.planName}</p>
        </div>
    </div>
    <div class="detail_content">
        <div class="content_left">
            <div class="userinfo">
                <div class="txinfo">
                    <div class="touxiangpic">
                        <img src="${recommendPlan.user.head}">
                        <input type="hidden" id="recommendPlanId" value="${recommendPlan.id}">
                    </div>
                    <p class="pinfo"> <span class="nickname">${recommendPlan.user.userName}</span>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<span class="dengji">资深用户</span><span class="love" id="collection">.</span></p>
                </div>
                <div class="pinglun">
                    <ul>
                        <li>
                            <div class="liulan"></div>
                            <p>浏览：<span class="llnum">${(recommendPlan.viewNum)!"0"}</span></p>
                        </li>
                        <li>
                            <div class="shoucang"></div>
                            <p>收藏：<span class="scnum">${(recommendPlan.collectNum)!"0"}</span></p>
                        </li>
                        <li>
                            <div class="pingluns"></div>
                            <p>评论：<span class="plnum">${recommendPlan.recommendPlanCommentList?size}</span></p>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="youjiarc">
                <div class="days">
                    <#list recommendPlanDays as recommendPlanDay>
                        <div class="jd_one">
                            <p class="dijitian">第${recommendPlanDay.day}天
                                <span class="tubiao"><span class="tianshu">1</span></span>
                                <span class="yidian"></span>
                            </p>
                            <#list recommendPlanDay.recommendPlanTrips as recommendPlanTrip>
                                <p class="didian">${recommendPlanTrip.scenicName}</p>
                                <p class="youjiwz">${recommendPlanTrip.tripDesc}</p>
                                <div class="tupian">
                                    <#list recommendPlanTrip.recommendPlanPhotos as recommendPlanPhoto>
                                        <#if (recommendPlan.dataSource??) >
                                            <img src="${recommendPlanPhoto.photoLarge}">
                                        <#else >
                                            <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${recommendPlanPhoto.photoLarge}">
                                        </#if>
                                        <#--<p class="youjiwz">${recommendPlanPhoto.description}</p>-->
                                    </#list>
                                </div>
                            </#list>
                        </div>
                    </#list>
                </div>
            </div>

            <h3 id="comment-target"><i></i>评价详情</h3>
            <div class="comment-details">
                <input type="hidden" id="hid-recommendPlan-id" value="${recommendPlan.id}">
                <div class="comment-details-header clearfix">
                </div>
                <ul class="comment-details-body">

                </ul>
                <div class="paging m-pager">
                </div>
            </div>

            <div class="tianjiapl">
                <div>
                    <h3 id="comment-target"><i></i>评价详情
                        <span class="red" id="cost_tpl_title_length"><span class="sl">0</span> / <span class="sy"> 300</span></span>
                    </h3>

                    <textarea  class="tianxiepl" placeholder="发表个神评论吧" maxlength="300" onkeyup="javascript:setShowLength(this, 300, 'cost_tpl_title_length');"></textarea>


                </div>
                <button class="tijiaopl">提  交</button>
            </div>


        </div>
        <div class="content_right">
            <div class="right_top">
                <div class="more_title">更多游记</div>
            </div>
            <div class="moreyouji">
                <ul>
                    <#list recommendPlanList as recommendPlan1>
                        <li>
                            <a href="/recommendplan_detail_${recommendPlan1.id}.html">
                                <div class="lipic">
                                    <#if (recommendPlan1.dataSource??) >
                                        <img src="${recommendPlan1.coverPath}">
                                    <#else >
                                        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${recommendPlan1.coverPath}">
                                    </#if>
                                </div>
                                <div class="youji_jj">
                                    <p class="youjititle">${recommendPlan1.planName}</p>
                                    <p class="youjitiem">
                                        <span class="date">${recommendPlan1.startTime?string("yyyy-MM-dd")}</span> /
                                        <span class="youwanddays">游玩${recommendPlan1.days}天</span>
                                    </p>
                                </div>
                                <div style="clear: both;"></div>
                            </a>
                        </li>
                    </#list>
                </ul>
            </div>
        </div>
        <div style="clear: both;"></div>
    </div>
</div>