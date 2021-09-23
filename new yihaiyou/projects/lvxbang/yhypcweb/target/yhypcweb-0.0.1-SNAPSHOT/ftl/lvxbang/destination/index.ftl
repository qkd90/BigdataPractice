<!-- #EndLibraryItem -->
<div class="section w1000 cl">
    <div class="section_l fl lh30">
        <dl class="section_dl">
            <dt><i class="ico2"></i>热门旅游目的地</dt>
            <dd>
            <#list hotAreas as area>
                <a href="${DESTINATION_PATH}/city_${area.id}.html" target="_blank">${area.name}</a>
            </#list>
            </dd>
        </dl>
        <dl class="section_dl">
            <dt><i class="ico7"></i>全国旅游目的地</dt>
            <dd>
            <#list allAreas as area>
            <#--<#if (area_index<33) >-->
                <a href="${DESTINATION_PATH}/province_${area.id}.html" target="_blank">${area.name}</a>
            <#--</#if>-->
            </#list>
            </dd>
        </dl>
    </div>
    <!--banner-->
    <div class="section_fr Yqybanner">
        <div class="section_s">
            <div class="posiR fl ws100" >
                <input id="ipt-destination" type="text" placeholder="输入目的地" value="" class="input clickinput"
                       data-areaId="" data-url="${DESTINATION_PATH}/lvxbang/destination/getDestSeachAreaList.jhtml" autoComplete="Off"><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                <!--关键字提示 clickinput input input01-->
                <div class="posiA categories_div  KeywordTips">
                    <ul>

                    </ul>
                </div>

                <!--错误-->
                <div class="posiA categories_div cuowu textL" style="width:93.7%">
                    <p class="cl">抱歉未找到相关的结果！</p>
                </div>

                <!--目的地 clickinput input01 input-->
                <div class="posiA Addmore categories_Addmore2">
                    <i class="close"></i>
                    <!--<div class="Addmore_d">
                        搜索历史：<span>厦门</span>
                    </div>-->
                    <dl class="Addmore_dl">
                        <dt>
                        <div class="Addmore_nr">
                            <ul>
                                <li class="checked" ><a href="javaScript:;" >热门</a></li>
                                <li><a href="javaScript:;">A-E</a></li>
                                <li><a href="javaScript:;">F-J</a></li>
                                <li><a href="javaScript:;">K-P</a></li>
                                <li><a href="javaScript:;">Q-W</a></li>
                                <li><a href="javaScript:;">X-Z</a></li>
                            </ul>
                        </div>
                        </dt>
                        <dd>
                            <label></label>
                            <div class="Addmore_nr">
                                <ul>
                                <#list hot as aArea>
                                    <li data-id="${aArea.id}">
                                        <a href="${DESTINATION_PATH}/city_${aArea.id}.html" target="_blank">${aArea.name}</a>
                                    </li>
                                </#list>
                                </ul>
                            </div>
                        </dd>
                        <#list letterSortAreas as letterSortArea>
                        <dd class="disn">
                            <#list letterSortArea.letterRange as lrArea>
                                <label>${lrArea.name}</label>

                                <div class="Addmore_nr">
                                    <ul>
                                        <#list lrArea.list as aArea>
                                            <li data-id="${aArea.id}">
                                                <a href="${DESTINATION_PATH}/city_${aArea.id}.html" target="_blank">${aArea.name}</a>
                                            </li>
                                        </#list>
                                    </ul>
                                </div>
                            </#list>
                        </dd>
                        </#list>
                    </dl>
                    <p class="cl"></p>
                </div>
            </div>
                <a onclick="SearcherBtn.btnDistSeach()" class="but fr"></a>
        </div>

        <div class="slide-main" id="touchMain">
            <div class="slide-box textC" id="slideContent">
            <#list adses as ad>
                <div class="slide">
                    <a stat="sslink-1" href="${DESTINATION_PATH}${ad.url}" target="_blank">
                        <img src = "/static${ad.imgPath}?imageView2/1/w/753/h/306/q/75" alt="${ad.adTitle}"/>
                    </a>
                </div>
            </#list>
            </div>
            <div class="item">
            <#list adses as ad>
                <#if ad_index = 0>
                    <a class="cur" stat="item${ad_index+1001}" href="javascript:;"></a>
                <#else>
                    <a href="javascript:;" stat="item${ad_index+1001}"></a>
                </#if>
            </#list>
            </div>
        </div>
    </div>
    <!--banner end-->
</div>

<div class="main cl">
    <div class="w1000">

        <!--当季热门-->
        <h2 class="title">当季热门旅游目的地</h2>

        <div class="travel main_div ff_yh">
            <ul>
                <li class="fl w660">
                <#list seasonHotAreas as area>
                    <#if (area_index<4) >
                        <#if (area_index==0 || area_index==2 ) >
                            <div class="posiR  fl w323_d mb15" >
                                <a href="${DESTINATION_PATH}/city_${area.id}.html" target="_blank">
                                    <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${area.tbAreaExtend.cover}?imageView2/1/w/323/h/267/q/75" alt="${area.name}"/>

                                    <p class="posiA">
                                        <label>${area.name}</label>
		                                      <span class="textL disB">
                                                  <#if area.tbAreaExtend>
                                                      <#if area.tbAreaExtend.abs?length gt 118>
                                                      ${area.tbAreaExtend.abs?substring(0,118)}...
                                                      <#else>
                                                      ${area.tbAreaExtend.abs}
                                                      </#if>
									            	</#if>
                                              </span>
                                    </p>
                                    <span class="title posiA">${area.name}</span>
                                </a>
                            </div>
                        <#else>
                            <div class="posiR  fr w323_d mb15">
                                <a href="${DESTINATION_PATH}/city_${area.id}.html" target="_blank">
                                    <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${area.tbAreaExtend.cover}?imageView2/1/w/323/h/267/q/75" alt="${area.name}"/>

                                    <p class="posiA">
                                        <label>${area.name}</label>
		                                      <span class="textL disB">
                                                  <#if area.tbAreaExtend>
                                                      <#if area.tbAreaExtend.abs?length gt 118>
                                                      ${area.tbAreaExtend.abs?substring(0,118)}...
                                                      <#else>
                                                      ${area.tbAreaExtend.abs}
                                                      </#if>
								            	</#if>
                                              </span>
                                    </p>
                                    <span class="title posiA">${area.name}</span>
                                </a>
                            </div>
                        </#if>
                    </#if>
                </#list>
                </li>
                <li class="fr w323">

                <#list seasonHotAreas as area>
                    <#if (area_index>3 && area_index<7) >
                        <div class="posiR  fr w323_d mb15">
                            <a href="${DESTINATION_PATH}/city_${area.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${area.tbAreaExtend.cover}?imageView2/1/w/323/h/173/q/75" alt="${area.name}"/>

                                <p class="posiA">
                                    <label>${area.name}</label>
                                          <span class="textL disB">
                                              <#if area.tbAreaExtend>
                                                  <#if area.tbAreaExtend.abs?length gt 58>
                                                  ${area.tbAreaExtend.abs?substring(0,58)}...
                                                  <#else>
							            		${area.tbAreaExtend.abs}
                                                  </#if>
							            	</#if>
                                          </span>
                                </p>
                                <span class="title posiA">${area.name}</span>
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
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
