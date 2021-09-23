<!--head  end--><!-- #EndLibraryItem --><div class="section w1000 cl">
    <div class="section_l fl lh30 food_dl">
        <dl class="section_dl">
            <dt><i class="ico4"></i>推荐美食</dt>
            <dd>
            <#list recommendDelicacy as delicacy>
                <a href="${DELICACY_PATH}/food_detail_${delicacy.id}.html" target="_blank">${delicacy.name}</a>
            </#list>
            </dd>
        </dl>
        <dl class="section_dl" id="delicacyCity">
            <dt><i class="ico5"></i>美食城市</dt>
            <dd>
                <form action="${DELICACY_PATH}/food_list.html" method="post" id="formid">
                  <input type="hidden" name="cityName" id="cityName"/>
                    <input type="hidden" name="cityCode" id="cityCode"/>
                </form>
            <#list delicacyCity as city>
                <a href="javaScript:;"  cityCode="${city.cityCode}" cityName="${city.name}">${city.name}</a>
            </#list>
            </dd>
        </dl>
        <dl class="section_dl">
            <dt><i class="ico6"></i>美食之旅</dt>
            <dd>
            <#list delicacyRecommendPlan as recommendPlan>
                <a href="${RECOMMENDPLAN_PATH}/guide_detail_${recommendPlan.id}.html" target="_blank">${recommendPlan.planName}</a>
            </#list>
            </dd>
        </dl>
    </div>
    <!--banner-->
    <div class="Yqybanner section_fr" >
        <div class="section_s">
            <div class="posiR categories fl" data-url="${DELICACY_PATH}/lvxbang/delicacy/suggestList.jhtml">
                <form action="${DELICACY_PATH}/food_list.html" id ="searchForm" method="post">
                    <input type="text" placeholder="输入美食" id="delicacyName" name="delicacyRequest.name" value="" class="input">
                </form>
                <div class="posiA categories_div KeywordTips">
                    <ul id="selectDelicacyId">

                    </ul>
                </div>
                <!--错误-->
                <div class="posiA categories_div cuowu textL" style="width: 93.7%;">
                    <p class="cl">抱歉未找到相关的结果！</p>
                </div>
            </div>
            <a href="javaScript:toDelicacyList();" class="but fr"></a>
        </div>

        <div class="slide-main" id="touchMain">
            <div class="slide-box textC" id="slideContent">
            <#list adses as ad>
                <div class="slide"><a stat="sslink-1" href="${DELICACY_PATH}${ad.url}" target="_blank"><img src="/static/${ad.imgPath}?imageView2/1/w/753/h/306/q/75" alt="${ad.adTitle}"/></a></div>
            </#list>
            </div>
            <div class="item">
                <#list adses as ad>
                <a class="<#if ad_index=0>cur</#if>" stat="item${ad_index+1001}" href="javascript:;"></a>
                </#list>
            </div>
        </div>
    </div>
    <!--banner end-->

    <p class="cl"></p>
</div>

<div class="main cl mt10">
    <div class="w1000 food_div">
        <div style="height:56px;">
            <div class="nav posiR">
                <label class="fl ff_yh">特色美食</label>
                <ul class="mailTab fl ml15 b">
                    <#list featuredDelicacyCity as city>
                      <#if city_index=0>
                          <li class="checked">${city.name}</li>
                      </#if>
                      <#if city_index !=0>
                          <li>${city.name}</li>
                      </#if>
                    </#list>
                </ul>
                <#--<a href="Food_Detail.html" class="more b fl">更多</a>-->
                <span class="fr disB"><img data-original="/images/food.jpg" /></span>
            </div>
        </div>
        <!--厦门-->
            <#list featureDelicacyList as featureDelicacys>
                <#if featureDelicacys_index=0>
                    <div class="mailTablePlan">
                        <#list featureDelicacys as featureDelicacy>

                            <ul class="food_list">
                                <li>
                                    <p class="img">
	                                    <a href="${DELICACY_PATH}/food_detail_${featureDelicacy.id}.html" target="_blank">
	                                    	<#if featureDelicacy.cover>
			                            		<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${featureDelicacy.cover}?imageView2/1/w/236/h/234/q/75" alt="${featureDelicacy.name}"/>
			                            	<#else>
			                            		<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/meishi.png?imageView2/1/w/236/h/234/q/75" alt="${featureDelicacy.name}"/>
			                            	</#if>
	                                        
	                                       </a>
	                                  </p>
                                    <p class="name">${featureDelicacy.name}</p>
                                </li>
                            </ul>
                        </#list>
                    </div>
                </#if>

                <#if featureDelicacys_index!=0>
                    <div class="mailTablePlan disn">
                        <#list featureDelicacys as featureDelicacy>
                            <ul class="food_list">
                                <li>
                                    <p class="img">
                                        <a href="${DELICACY_PATH}/food_detail_${featureDelicacy.id}.html" target="_blank">
                                            <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${featureDelicacy.cover}?imageView2/1/w/236/h/234/q/75" alt="${featureDelicacy.name}"/></a></p>
                                    <p class="name">${featureDelicacy.name}</p>
                                </li>
                            </ul>
                        </#list>
                    </div>
                </#if>

            </#list>
        <p class="cl h70"></p>
    </div>
</div>

