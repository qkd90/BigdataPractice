<div class="Yqybanner" >
    <div class="search_bg posiA">
        <!--行程规划-->
        <div class="mailTablePlan ">
            <div class="stroke textC">
                <h1 class="fs36 ff_yh" style="margin-bottom: 20px;">你只需决定出发</h1><br/>
                <span class="stroke_s" style="text-align: center;color:#34bf82;margin-top:5px;"><a href="javaScript:;" style="color:#34bf82;">如何定制旅游线路</a></span>
                <img src="../images/wenhao.jpg" style="padding-left: 4px;margin-top: -5px;position: absolute;"/>
                <form action="${SCENIC_PATH}/scenic_list.html" method="post" id="scenic-form">
                <div class="posiR">
                    <input id="citiesStr" type="text" name="citiesStr" value="" style="display:none" placeholder=" ">
                    <input id="input_planId" data-multi="true" type="text" placeholder="输入目的地 (添加多城市用分号 ；隔开)"  value=""  class="input m0auto mt15 clickinput des_into" data-url="${DESTINATION_PATH}/lvxbang/destination/getSeachAreaList.jhtml" autoComplete="Off"><!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                    <!--目的地 clickinput input01 input-->
                    <div class="posiA des_main" style="display: none;">
                        <div class="abroad" id="internal">
                            <ul>
                                <li class="des_color">国内</li>
                                <li>境外</li>
                                <span class="des_close" id="des_close"><img src="/images/index/close.png"></span>
                            </ul>
                        </div>
                        <div class="seach_history" id="des_history">
                            <ul class="selected_city">
                                <li class="history_title">搜索历史：</li>
                            </ul>
                            <ul  class="selected_city" style="display:none">
                                <li class="history_title">搜索历史：</li>
                            </ul>
                        </div>
                        <div class="des_place" id="des_place">
                            <!--国内-->
                            <div class="city_change clearfix tank des_width1" style="display:block">
                                <ul class="guide guide_internation">
                                    <li class="bg_color">热门</li>
                                    <li>A-E</li>
                                    <li>F-J</li>
                                    <li>K-P</li>
                                    <li>Q-W</li>
                                    <li>X-Z</li>
                                </ul>
                                <div class="smaller_city_change">
                                    <ul class="follower_city">
                                    <#list hot as aArea>
                                        <li data-id="${aArea.id}">${aArea.name}</li>
                                    </#list>
                                    </ul>
                                </div>
                            <#list letterSortAreas as letterSortArea>
                                <div class="smaller_city_change" style="display:none">
                                    <#list letterSortArea.letterRange as lrArea>
                                        <span>${lrArea.name}</span>
                                        <ul class="follower_city">
                                            <#list lrArea.list as aArea>
                                                <li data-id="${aArea.id}">${aArea.name}</li>
                                            </#list>
                                        </ul>
                                    </#list>
                                </div>
                            </#list>
                            </div>
                            <!--境外-->
                            <div class="city_change clearfix des_width2" style="display:none">
                                <ul class="internation guide_internation">
                                <#list abroadAreas?keys as key>
                                    <li>${key}</li>
                                </#list>
                                </ul>
                                <!--港澳台-->
                            <#list abroadAreas?keys as first>
                                <div class="smaller_city_change2" <#if first_index gt 0>style="display:none"</#if>>
                                    <ul class="follower_city2">
                                        <#list abroadAreas[first] as aArea>
                                            <li data-id="${aArea.id}">${aArea.name}</li>
                                        </#list>
                                    </ul>
                                </div>
                            </#list>
                            </div>

                        </div>
                    </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                    <!--关键字提示 clickinput input input01-->
                    <div class="posiA categories_div  KeywordTips">
                        <ul>

                        </ul>
                    </div>

                    <!--错误-->
                    <div class="posiA categories_div cuowu textL">
                        <p class="cl">抱歉未找到相关的结果！</p>
                    </div><!-- #EndLibraryItem -->
                </div>
                </form>
                <#--<p class="stroke_p fs14 mt15 textL">-->
                    <#--&lt;#&ndash;<span>添加多城市用分号"；"隔开</span>&ndash;&gt;-->
                    <#--&lt;#&ndash;<span class="stroke_s fr"><a href="javaScript:;">快速规划旅行的秘诀</a>?</span>&ndash;&gt;-->
                <#--</p>-->
                <p class="but ff_yh">
                    <a href="javaScript:;" class="fl ml50" onclick="SearcherBtn.recPlanByDest()">复制线路</a>
                    <a href="javaScript:;" class="fr mr50" onclick="SearcherBtn.scenicByDest()">原创线路</a>
                </p>
                <p style="margin-top: 18%;color: #aaaaaa;line-height:1.5">
                    <span class="fl" style="margin-left: 13%;">复制后调整成自己的特色线路</span>
                    <span class="fl" style="margin-left: 23%;">创建一条属于自己的线路</span>
                </p>
            </div>
        </div>
        <!--行程规划 end-->
        <p class="cl"></p>
    </div>

    <div class="slide-main" id="touchMain">
        <a class="prev" href="javascript:;" stat="prev1001"></a>
        <div class="slide-box textC" id="slideContent">
        <#list adses as ads>
            <div class="slide">
                <#if ads.openType != "NONE"><a stat="sslink-1" href="${ads.url}" target="<#if ads.openType == "SELF">_self<#else>_blank</#if>"></#if>
                <img src ="/static${ads.imgPath}" style="width:1900px;height:460px;" alt="${ads.adTitle}"/>
                <#if ads.openType != "NONE"></a></#if>
            </div>
        </#list>
        </div>
        <a class="next" href="javascript:;" stat="next1002"></a>
        <div class="item">
            <#list adses as ads><a class="<#if ads_index==0>cur</#if>" stat="item${(1001+ads_index)?c}" href="javascript:;"></a></#list>
        </div>
    </div>
</div>

<div class="main cl">
    <div class="w1000">
        <!--热门行程-->
        <h2 class="title">自由行/热门线路</h2>

        <div class="Popular main_div ff_yh">
            <ul>
                <li class="w630 fl">
                <#list plans as plan>
                    <#if (plan_index<3)>
                        <div class="<#if plan_index==0>posiR mb10 w630_d</#if><#if plan_index==1>posiR w308_d fl</#if><#if plan_index==2>posiR w308_d fr</#if>">
                            <a href="${PLAN_PATH}/plan_detail_${plan.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${plan.coverPath}?imageView2/1<#if plan_index==0>/w/630/h/320</#if><#if (plan_index>0)>/w/308/h/215</#if>/q/75" alt="${plan.name}"/>
                                <p class="posiA">
                                    <label>${plan.city.name}${plan.planDays}日游</label>
                              <span>${plan.planDays}日/${plan.planStatistic.quoteNum}人引用<br/>
                              ${plan.user.userName}/${plan.name}</span>
                                </p>
                                <span class="title posiA">${plan.city.name}${plan.planDays}日游</span>
                            </a>
                        </div>
                    </#if>
                </#list>
                </li>
                <li class="w355 fr">
                <#list plans as plan>
                    <#if (plan_index>=3 && plan_index<5 )>
                        <div class="<#if plan_index==3>posiR mb10 w355_d</#if><#if plan_index==4>posiR w355_d</#if>">
                            <a href="${PLAN_PATH}/plan_detail_${plan.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${plan.coverPath}?imageView2/1/w/355/h/268/q/75" alt="${plan.name}"/>

                                <p class="posiA">
                                    <label>${plan.city.name}${plan.planDays}日游</label>
                              <span>${plan.planDays}日/${plan.planStatistic.quoteNum}人引用<br/>
                              ${plan.user.userName}/${plan.name}</span>
                                </p>
                                <span class="title posiA">${plan.city.name}${plan.planDays}日游</span>
                            </a>
                        </div>
                    </#if>
                </#list>
                </li>
            </ul>
            <p class="cl h20"></p>
        </div>

        <!--旅行目的地-->
        <h2 class="title">自由行/旅行目的地</h2>

        <div class="travel main_div ff_yh">
            <ul>
                <li class="fl w660">
                <#list destinations as destination>
                    <#if destination_index<4>
                        <div class="posiR <#if destination_index%2=0 >fl<#else >fr</#if> w323_d mb15">
                            <a href="${DESTINATION_PATH}/city_${destination.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${destination.tbAreaExtend.cover}?imageView2/1/w/323/h/267/q/75" alt="${destination.name}"/>

                                <p class="posiA">
                                    <label>${destination.name}</label><br/>
                                    <span class="textL disB">
                                <#if destination.tbAreaExtend>
                                <#if destination.tbAreaExtend.abs?length gt 58>
                                ${destination.tbAreaExtend.abs?substring(0,58)}...
                                <#else>
                                ${destination.tbAreaExtend.abs}
                                </#if>
                                </#if>
                                    </span>
                                </p>
                                <span class="title posiA">${destination.name}</span>
                            </a>
                        </div>
                    </#if>
                </#list>

                </li>
                <li class="fr w323">
                <#list destinations as destination>
                    <#if (destination_index>=4) && (destination_index<7)>
                        <div class="posiR  fr w323_d mb15">
                            <a href="${DESTINATION_PATH}/city_${destination.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${destination.tbAreaExtend.cover}?imageView2/1/w/323/h/173/q/75" alt="${destination.name}"/>

                                <p class="posiA">
                                    <label>${destination.name}</label>
                                    <span class="textL disB">
                                    <#if destination.tbAreaExtend>
                                    <#if destination.tbAreaExtend.abs?length gt 58>
                                    ${destination.tbAreaExtend.abs?substring(0,58)}...
                                    <#else>
                                    ${destination.tbAreaExtend.abs}
                                    </#if>
                                    </#if>
                                    </span>
                                </p>
                                <span class="title posiA">${destination.name}</span>
                            </a>
                        </div>
                    </#if>
                </#list>
                </li>
            </ul>
            <p class="cl h20"></p>
        </div>
        <p class="cl h50 mt5"></p>
    </div>
</div>