<#-- head start -->

<#-- head end -->
<#-- top start -->
<div class="section w1000 cl">
    <#-- left panel start -->
    <div class="section_l fl lh30">
        <dl class="section_dl">
            <dt><i></i>毕业旅游</dt>
            <dd>
                <#list seasonSelectRecplans as recplan>
                    <a target="_blank" href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html">${recplan.planName}</a>
                </#list>
            </dd>
        </dl>
        <dl class="section_dl">
            <dt><i class="ico2"></i>穷游</dt>
            <dd>
                <#list hotRecplans as recplan>
                    <a target="_blank" href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html">${recplan.planName}</a>
                </#list>
            </dd>
        </dl>
        <dl class="section_dl" style="display: none">
            <dt><i class="ico2"></i>热门线路</dt>
            <dd>

            </dd>
        </dl>
    </div>
    <#-- left panel end -->
    <#-- banner start-->
    <div class="Yqybanner section_fr">
        <div class="section_s">
            <form action="${RECOMMENDPLAN_PATH}/guide_list.html" method="post">
                <div class="posiR categories fl" data-url="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/suggest.jhtml">
                    <input type="text" placeholder="输入游记" value="" class="input" name="recommendPlanSearchRequest.name">
                    <div class="posiA categories_div KeywordTips">
                        <ul>
                        </ul>
                    </div>
                    <!--错误-->
                    <div class="posiA categories_div cuowu textL" style="width:91.8%">
                        <p class="cl">抱歉未找到相关的结果！</p>
                    </div>
                </div>
                <input type="submit" class="but fr" value="">
                <a href="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/edit.jhtml"  onclick="return has_no_User2('${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/edit.jhtml')" class="but2 oval4 fr"><i></i>写游记</a>
            </form>

        </div>

        <div class="slide-main" id="touchMain">
            <div class="slide-box textC" id="slideContent">
                <#list adses as ad>
                    <div class="slide">
                        <a stat="sslink-1" href="${RECOMMENDPLAN_PATH}${ad.url}" target="_blank">
                            <img src ="/static${ad.imgPath}" alt="${ad.adTitle}"/>
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
    <#--banner end-->
</div>
<#-- top end -->
<#-- main start -->
<div class="main cl">
    <div class="w1000">
        <#--发现好游记 开始-->
      <h2 class="title">自助游/发现好游记</h2>
        <div class="find main_div ff_yh">
          <ul>
              <li>
            <#list goodRecplans as recplan>
                <#if recplan_index == 0>
                 <div class="posiR  fl w630_d">
                   <a target="_blank" href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html">
                    <#if recplan.coverPath?starts_with("http")>
                        <img width="100%" data-original="${recplan.coverPath}" alt="${recplan.planName}"/>
                    <#else >
                        <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/630/h/268/q/75" alt="${recplan.planName}"/>
                    </#if>
                       <p class="posiA">
                         <label>${recplan.planName}</label>
                         <span>${recplan.days}天/${recplan.quoteNum}引用<br/>
                             <#if recplan.description?length &gt; 30>
                                ${recplan.description?substring(0,30)}......
                             <#else>
                                ${recplan.description}......
                             </#if>
                         </span>
                       </p>
                       <span class="title posiA">${recplan.planName}</span>
                   </a>
                 </div>
                <#elseif recplan_index == 1>
                    <div class="posiR  fr w355_d">
                        <a target="_blank" href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html">
                            <#if recplan.coverPath?starts_with("http")>
                                <img width="100%" data-original="${recplan.coverPath}" alt="${recplan.planName}"/>
                            <#else >
                                <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/355/h/268/q/75" alt="${recplan.planName}"/>
                            </#if>
                            <#--<img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/355/h/268/q/75" alt="${recplan.planName}"/>-->
                            <p class="posiA">
                                <label>${recplan.planName}</label>
                                <span>${recplan.days}天/${recplan.quoteNum}引用<br/>
                                    <#if recplan.description?length &gt; 30>
                                        ${recplan.description?substring(0,30)}......
                                    <#else>
                                        ${recplan.description}......
                                    </#if>
                                </span>
                            </p>
                            <span class="title posiA">${recplan.planName}</span>
                        </a>
                    </div>
                </#if>
            </#list>
              </li>
              <li>
             <#list goodRecplans as recplan>
                <#if recplan_index == 2>
                    <div class="posiR w355_d fl">
                        <a target="_blank" href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html">
                            <#if recplan.coverPath?starts_with("http")>
                                <img width="100%" data-original="${recplan.coverPath}" alt="${recplan.planName}"/>
                            <#else >
                                <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/355/h/268/q/75" alt="${recplan.planName}"/>
                            </#if>
                            <#--<img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/355/h/268/q/75" alt="${recplan.planName}"/>-->
                            <p class="posiA">
                                <label>${recplan.planName}</label>
                                <span>${recplan.days}天/${recplan.quoteNum}引用<br/>
                                    <#if recplan.description?length &gt; 30>
                                        ${recplan.description?substring(0,30)}......
                                    <#else>
                                        ${recplan.description}......
                                    </#if>
                                </span>
                            </p>
                            <span class="title posiA">${recplan.planName}</span>
                        </a>
                    </div>
                <#elseif recplan_index == 3>
                    <div class="posiR w630_d fr">
                        <a target="_blank" href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html">
                            <#if recplan.coverPath?starts_with("http")>
                                <img width="100%" data-original="${recplan.coverPath}" alt="${recplan.planName}"/>
                            <#else >
                                <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/630/h/268/q/75" alt="${recplan.planName}"/>
                            </#if>
                            <#--<img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/630/h/268/q/75" alt="${recplan.planName}"/>-->
                            <p class="posiA">
                                <label>${recplan.planName}</label>
                                <span>${recplan.days}天/${recplan.quoteNum}引用<br/>
                                    <#if recplan.description?length &gt; 30>
                                        ${recplan.description?substring(0,30)}......
                                    <#else>
                                        ${recplan.description}......
                                    </#if>
                                </span>
                            </p>
                            <span class="title posiA">${recplan.planName}</span>
                        </a>
                    </div>
                </#if>
              </#list>
             </li>
          </ul>
            <p class="cl h20"></p>
        </div>
        <#-- 发现好游记 结束 -->
        <#--主题推荐 开始-->
        <h2 class="title">主题游记推荐</h2>
        <div class="travel main_div ff_yh">
            <ul>
            <li class="fl w660">
              <#list themeRecplans as recplan>
                <#if recplan_index == 0 || recplan_index == 2>
                  <div class="posiR  fl w323_d mb15">
                    <a target="_blank" href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html">
                        <#if recplan.coverPath?starts_with("http")>
                            <img width="100%" data-original="${recplan.coverPath}" alt="${recplan.planName}"/>
                        <#else >
                            <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/323/h/267/q/75" alt="${recplan.planName}"/>
                        </#if>
                       <#--<img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/323/h/267/q/75" alt="${recplan.planName}"/>-->
                       <p class="posiA">
                          <label>
                              ${recplan.planName}
                           <#--${recplan.planName?substring(0,10)}-->
                          </label>
                          <span class="textL disB">
                              <#if recplan.description?length &gt; 30>
                                ${recplan.description?substring(0,30)}......
                              <#else>
                                ${recplan.description}......
                              </#if>
                          </span>
                       </p>
                          <span class="title posiA">

                              <#if recplan.planName?length &gt; 10>
                              ${recplan.planName?substring(0,10)}
                              <#else>
                              ${recplan.planName}
                              </#if>

                          </span>
                    </a>
                  </div>
                    <#elseif recplan_index == 1 || recplan_index == 3>
                        <div class="posiR  fr w323_d mb15">
                            <a target="_blank" href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html">
                                <#if recplan.coverPath?starts_with("http")>
                                    <img width="100%" data-original="${recplan.coverPath}" alt="${recplan.planName}"/>
                                <#else >
                                    <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/323/h/267/q/75" alt="${recplan.planName}"/>
                                </#if>
                                <#--<img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/323/h/267/q/75" alt="${recplan.planName}"/>-->
                                <p class="posiA">
                                    <label>${recplan.planName}</label>
                                    <span class="textL disB">
                                        <#if recplan.description?length &gt; 30>
                                            ${recplan.description?substring(0,30)}......
                                        <#else>
                                            ${recplan.description}......
                                        </#if>
                                    </span>
                                </p>
                                <span class="title posiA">

                                    <#if recplan.planName?length &gt; 10>
                                    ${recplan.planName?substring(0,10)}
                                    <#else>
                                    ${recplan.planName}
                                    </#if>

                                </span>
                            </a>
                        </div>
                 </#if>
              </#list>
            </li>
                <li class="fr w323">
                  <#list themeRecplans as recplan>
                  <#if recplan_index == 4 || recplan_index == 5 || recplan_index == 6>
                    <div class="posiR  fr w323_d mb15">
                        <a target="_blank" href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html">
                            <#if recplan.coverPath?starts_with("http")>
                                <img width="100%" data-original="${recplan.coverPath}" alt="${recplan.planName}"/>
                            <#else >
                                <img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/323/h/173/q/75" alt="${recplan.planName}"/>
                            </#if>
                            <#--<img width="100%" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/323/h/173/q/75" alt="${recplan.planName}"/>-->
                            <p class="posiA">
                                <label>${recplan.planName}</label>
                                <span class="textL disB">
                                    <#if recplan.description?length &gt; 30>
                                        ${recplan.description?substring(0,30)}......
                                    <#else>
                                        ${recplan.description}......
                                    </#if>
                                </span>
                            </p>
                            <span class="title posiA">
                            <#--${recplan.planName}-->
                                <#if recplan.planName?length &gt; 10>
                                ${recplan.planName?substring(0,10)}
                                <#else>
                                ${recplan.planName}
                                </#if>
                            </span>
                        </a>
                    </div>
                  </#if>
             </#list>
             </li>
            </ul>
            <p class="cl h20"></p>
        </div>
        <#-- 主题推荐结束 -->
        <p class="cl h50 mt5"></p>
    </div>
</div>
<#-- main end -->

<#-- footer start -->

<#-- footer end -->
