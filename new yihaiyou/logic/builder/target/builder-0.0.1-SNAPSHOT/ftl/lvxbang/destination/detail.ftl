
<div class=" w1000 cl">
    <p class="n_title" style="color: #999;padding-top: 23px;height: 43px;">
        您在这里：&nbsp;
        <a href="${DESTINATION_PATH}">目的地</a>
    <#--<#if (area.father??) && area.father.id != 100000>-->
    <#if area.level == 2>
        &nbsp;&gt;&nbsp;
        <a href="${DESTINATION_PATH}/province_${area.father.cityCode}.html">${area.father.name}旅游</a>
    </#if>
    <#if area.level gte 3>
        &nbsp;&gt;&nbsp;
        <a href="${DESTINATION_PATH}/city_${area.father.cityCode}.html">${area.father.name}旅游</a>
    </#if>
    <#--&nbsp;&gt;&nbsp;-->
    <#--${area.name}-->
    <#--<#else>-->
        &nbsp;&gt;&nbsp;
    ${area.name}旅游

    <#--</#if>-->
    </p>

    <!-- cover -->
    <div style="width: 720px;height: 350px;overflow: hidden;margin:0 auto;position:relative;float: left;">
        <img style="width: 720px;height: 350px;overflow: hidden;"
             src="http://7u2inn.com2.z0.glb.qiniucdn.com/${area.tbAreaExtend.cover}?imageView2/1/w/720/h/350" alt="${area.name}"/>
    </div>
    <!-- cover end -->

    <div class="Destination_cs_t fr">
        <p class="title b ff_yh">${area.name}</p>
        <ul>
            <li><b>城市简介：</b>
            <#if area.tbAreaExtend>
                <#if ((destination.abs?trim)?length > 34)>
                ${(destination.abs?trim)?substring(0,30)}...
                    <div class="posiA disn" style="max-height: 300px;overflow: auto">
                        <b>城市简介：</b>
                    ${destination.abs}
                    </div>
                <#else>
                ${(destination.abs?trim)}
                </#if>
            </#if>
            </li>
            <li><b>最佳游玩季节：</b>
            <#if area.tbAreaExtend>
                <#if ((destination.bestVisitTime?trim)?length > 34)>
                ${(destination.bestVisitTime?trim)?substring(0,30)}...
                    <div class="posiA disn" style="max-height: 300px;overflow: auto">
                        <b>最佳游玩季节：</b>
                    ${destination.bestVisitTime}
                    </div>
                <#else>
                ${(destination.bestVisitTime?trim)}
                </#if>
            </#if>
            </li>
            <li><b>推荐游玩时间：</b>
            <#if area.tbAreaExtend>
                <#if ((destination.adviceTime?trim)?length > 34)>
                ${(destination.adviceTime?trim)?substring(0,30)}...
                    <div class="posiA disn" style="max-height: 300px;overflow: auto">
                        <b>推荐游玩时间：</b>
                    ${area.tbAreaExtend.adviceTime}
                    </div>
                <#else>
                ${destination.adviceTime}
                </#if>
            </#if>
            </li>
        <#if destination.otherName>
            <li>
                <b>${destination.otherName}：</b>
                <#if ((destination.simpleOther?trim)?length > 34)>
                ${(destination.simpleOther?trim)?substring(0,30)}
                    <div class="posiA disn" style="max-height: 300px;overflow: auto">
                        <b>${destination.otherName}：</b>
                    ${destination.other}
                    </div>
                <#else>
                ${(destination.simpleOther?trim)}
                </#if>
            </li>
        </#if>
        </ul>
    </div>
    <p class="cl"></p>

    <div class="Destination_cs_c cl">
    <#if (sceTopList?size>0) >
        <div class="d_cs_c_div  d_cs_c_div1 fl">
            <div class="title posiR">
                <i></i><label>${area.name}旅游景点</label>
                <a href="javaScript:;" cityName="${area.name}" cityCode="${area.cityCode}"
                   url="${SCENIC_PATH}/scenic_list.html" onclick="more(this)">更多&nbsp;&gt;&gt;</a>
            </div>
            <dl>
                <#list sceTopList as scenic>
                    <#if scenic_index = 0>
                    <dd class="checked">
                    <#else >
                    <dd>
                    </#if>
                    <#if scenic.scenicOther>
                        <a href="${SCENIC_PATH}/scenic_detail_${scenic.id}.html" style="color: #999;">
                            <span class="ico fl <#if scenic_index = 0>first<#elseif scenic_index = 1>second<#else>third</#if>">${scenic_index+1}</span>

                            <div class="fl lh20 d_cs_c_div_dd">
                                <label class="name">
                                	<#if scenic.name>
                                        <#assign scename = scenic.name?replace("<.*?>","","r")
                                        ?replace("&nbsp;","")
                                        ?replace(" ","")
                                        ?trim>

                                        <#if (scename?length > 6)>
                                        	${scename?substring(0,6)}...
                                        <#else>
                                        	${scenic.name}
                                        </#if>

                                    </#if>
                                
                                </label>

                                <p class="fr">
                                    <#if scenic.scenicOther.description>
                                        <#assign sceShort = scenic.scenicOther.description?replace("<.*?>","","r")
                                        ?replace("&nbsp;","")
                                        ?replace(" ","")
                                        ?trim>
                                        <#if (sceShort?length > 8)>
                                        ${sceShort?substring(0,8)}...
                                        <#else>
                                        ${sceShort}
                                        </#if>
                                    </#if>
                                </p>
                            </div>
                            <div class="d_cs_c_div_dd2 disn">
                                <div class="img fl">
                                    <#if scenic.cover>
                                        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${scenic.cover}?imageView2/1/w/100/h/100/q/75" alt="${scenic.name}"/>
                                    <#else>
                                        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/jingdian.png?imageView2/1/w/100/h/100/q/75" alt="${scenic.name}"/>
                                    </#if>

                                </div>
                                <div class="nr fr">
                                    <p class="name" style="height: initial;width: 154px;">
                                            	${scenic.name}
                                    </p>

                                    <p class="js">
                                    
                                        <#if scenic.scenicOther.description>
                                            <#assign sceLong = scenic.scenicOther.description?replace("<.*?>","","r")
                                            ?replace("&nbsp;","")
                                            ?replace(" ","")
                                            ?trim>

                                            <#if (sceLong?length > 36)>
                                            ${sceLong?substring(0,36)}...
                                            <#else>
                                            ${sceLong}
                                            </#if>

                                        </#if>


                                    </p>
                                </div>
                            </div>
                        </a>
                    </#if>

                </dd>
                </#list>
            </dl>
        </div>
    <#else >
        <div class="d_cs_c_div  d_cs_c_div1 fl">
            <div class="title posiR" style="border-bottom-color: #ffffff;">
                <i></i><label>${area.name}旅游景点</label>
            </div>
            <div style="background-color: #F5F5F5;width: 280px;height: 334px;text-align:center;">
                <img src="/images/sleep.png" style="margin-top:50px;"/>
                <img src="/images/zi.png" style="margin-top:20px;"/>
            </div>
        </div>
    </#if>
    <#if (deliTopList?size>0) >
        <div class="d_cs_c_div  d_cs_c_div2 fl">
            <div class="title posiR">
                <i></i><label>${area.name}旅游美食</label>
                <a href="javaScript:;" cityName="${area.name}" cityCode="${area.cityCode}"
                   url="${DELICACY_PATH}/food_list.html" onclick="more(this)">更多&nbsp;&gt;&gt;</a>
            </div>
            <dl>
                <#list deliTopList as delicacy>
                    <#if delicacy_index = 0>
                    <dd class="checked">
                    <#else >
                    <dd>
                    </#if>

                    <#if delicacy.extend>
                        <a href="${DELICACY_PATH}/food_detail_${delicacy.id}.html" style="color: #999;">
                            <span class="ico fl <#if delicacy_index = 0>first<#elseif delicacy_index = 1>second<#else>third</#if>">${delicacy_index+1}</span>

                            <div class="fl lh20 d_cs_c_div_dd">
                                <label class="name">
                                	<#if delicacy.name>
                                        <#assign delname = delicacy.name?replace("<.*?>","","r")
                                        ?replace("&nbsp;","")
                                        ?replace(" ","")
                                        ?trim>

                                        <#if (delname?length > 6)>
                                        	${delname?substring(0,6)}...
                                        <#else>
                                        	${delicacy.name}
                                        </#if>
                                    </#if>
                                	
                                </label>

                                <p class="fr">

                                    <#if delicacy.extend.introduction>
                                        <#assign delShort = delicacy.extend.introduction?replace("<.*?>","","r")
                                        ?replace("&nbsp;","")
                                        ?replace(" ","")
                                        ?trim>
                                        <#if (delShort?length > 8)>
                                        
                                        ${delShort?substring(0,8)}...
                                        <#else>
                                        ${delShort}
                                        </#if>
                                    </#if>

                                </p>
                            </div>
                            <div class="d_cs_c_div_dd2 disn">
                                <div class="img fl">
                                    <#if delicacy.cover>
                                        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${delicacy.cover}?imageView2/1/w/100/h/100/q/75" alt="${delicacy.name}"/>
                                    <#else>
                                        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/meishi.png?imageView2/1/w/100/h/100/q/75" alt="${delicacy.name}"/>
                                    </#if>

                                </div>
                                <div class="nr fr">
                                    <p class="name" style="height: initial;width: 154px;">${delicacy.name}</p>

                                    <p class="js">
                                        <#if delicacy.extend.introduction>
                                            <#assign delLong = delicacy.extend.introduction?replace("<.*?>","","r")
                                            ?replace("&nbsp;","")
                                            ?replace(" ","")
                                            ?trim>
                                            <#if (delLong?length > 36)>
                                            ${delLong?substring(0,36)}...
                                            <#else>
                                            ${delLong}
                                            </#if>
                                        </#if>

                                    </p>
                                </div>
                            </div>
                        </a>
                    </#if>
                </dd>
                </#list>
            </dl>
        </div>
    <#else >
        <div class="d_cs_c_div  d_cs_c_div2 fl">
            <div class="title posiR" style="border-bottom-color: #ffffff;">
                <i></i><label>${area.name}旅游美食</label>
            </div>
            <div style="background-color: #F5F5F5;width: 280px;height: 334px;text-align:center;">
                <img src="/images/sleep.png" style="margin-top:50px;"/>
                <img src="/images/zi.png" style="margin-top:20px;"/>
            </div>
        </div>
    </#if>
    <#if (hotelTopList?size>0) >
        <div class="d_cs_c_div  d_cs_c_div3 fl">
            <div class="title posiR">
                <i></i><label>${area.name}旅游酒店</label>
                <a href="javaScript:;" cityName="${area.name}" cityCode="${area.cityCode}"
                   url="${HOTEL_PATH}/hotel_list.html" onclick="more(this)">更多&nbsp;&gt;&gt;</a>
            </div>
            <dl>
                <#list hotelTopList as hotel>
                    <#if hotel_index = 0>
                    <dd class="checked">
                    <#else >
                    <dd>
                    </#if>
                    <#if hotel.extend>
                        <a href="${HOTEL_PATH}/hotel_detail_${hotel.id}.html" style="color: #999;">
                            <span class="ico fl <#if hotel_index = 0>first<#elseif hotel_index = 1>second<#else>third</#if>">${hotel_index+1}</span>

                            <div class="fl lh20 d_cs_c_div_dd">
                                <label class="name">
                                	<#if hotel.name>
                                        <#assign hotname = hotel.name?replace("<.*?>","","r")
                                        ?replace("&nbsp;","","r")
                                        ?replace(" ","")
                                        ?trim>

                                        <#if (hotname?length > 6)>
                                        	${hotname?substring(0,6)}...
                                        <#else>
                                        	${hotel.name}
                                        </#if>
                                    </#if>
                                </label>

                                <p class="fr">
                                    <#if hotel.extend.description>
                                        <#assign hotShort = hotel.extend.description?replace("<.*?>","","r")
                                        ?replace("&nbsp;","")
                                        ?replace(" ","")
                                        ?trim>
                                        <#if (hotShort?length > 8)>
                                        ${hotShort?substring(0,8)}...
                                        <#else>
                                        ${hotShort}
                                        </#if>
                                    </#if>

                                </p>
                            </div>
                            <div class="d_cs_c_div_dd2 disn">
                                <div class="img fl">
                                    <#if hotel.cover>
                                        <img src="${hotel.cover}" alt="${hotel.name}"/>
                                    <#else>
                                        <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/jiudian.png?imageView2/1/w/100/h/100/q/75" alt="${hotel.name}"/>
                                    </#if>
                                </div>
                                <div class="nr fr">
                                    <p class="name" style="height: initial;width: 154px;">${hotel.name}</p>

                                    <p class="js">
                                        <#if hotel.extend.description>
                                            <#assign hotLong = hotel.extend.description?replace("<.*?>","","r")
                                            ?replace("&nbsp;","")
                                            ?replace(" ","")
                                            ?trim>
                                            <#if (hotLong?length > 36)>
                                            ${hotLong?substring(0,36)}...
                                            <#else>
                                            ${hotLong}
                                            </#if>
                                        </#if>
                                    </p>
                                </div>
                            </div>
                        </a>
                    </#if>
                </dd>
                </#list>
            </dl>
        </div>
    <#else>
        <div class="d_cs_c_div  d_cs_c_div3 fl">
            <div class="title posiR" style="border-bottom-color: #ffffff;">
                <i></i><label>${area.name}旅游酒店</label>
            </div>
            <div style="background-color: #F5F5F5;width: 280px;height: 334px;text-align:center;">
                <img src="/images/sleep.png" style="margin-top:50px;"/>
                <img src="/images/zi.png" style="margin-top:20px;"/>
            </div>
        </div>
    </#if>

        <p class="cl"></p>
    </div>
</div>
<#if hotRecommendPlan>
<div class="main cl">
    <div class="w1000">

        <!--热门景点-->
        <h2 class="title">自助游/当季热门</h2>

        <div class="travel main_div ff_yh tile">
            <ul class="tile_ul">

                <#list hotRecommendPlan as plan>
                    <li>
                        <div class="posiR  w323_d ">
                            <a href="${RECOMMENDPLAN_PATH}/guide_detail_${plan.id}.html">
                                <#if plan.coverPath?starts_with("http")>
                                    <img src="${plan.coverPath}" style="width:100%" alt="${plan.planName}"/>
                                <#else>
                                    <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${plan.coverPath}?imageView2/1/w/323/h/267/q/75"
                                         style="width:100%; height: 100%" alt="${plan.planName}"/>
                                </#if>
                                <p class="posiA">
                                    <label>${plan.planName}</label>
	                                    <span class="textC disB">
	                                    	${plan.days}日游
	                                    </span>
	                                    <span class="textC disB" style="margin-top: 5px;">
                                        ${plan.startTimeStr}
                                        </span>
                                </p>
	                            <span class="title posiA">
                                    <#if ((plan.planName?trim)?length > 14)>
                                    ${(plan.planName?trim)?substring(0,14)}...
                                    <#else>
                                    ${(plan.planName?trim)}
                                    </#if>

                                </span>
                            </a>
                        </div>
                    </li>
                </#list>
            </ul>
            <p class="cl h20"></p>
        </div>
        <p class="cl h30"></p>
    </div>
</div>
</#if>

<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->