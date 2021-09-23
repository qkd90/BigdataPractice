
<div class="main cl mt10">
    <div class="w1000">
        <!--n_title-->
        <p class="n_title h40 lh40">
            您在这里：&nbsp;
            <#--<a href="${DESTINATION_PATH}">目的地</a>-->
            <#--&nbsp;&gt;&nbsp;-->
        <#if scenicInfo.city.level gte 3>
            <#--<a href="${DESTINATION_PATH}/city_${scenicInfo.city.father.cityCode}.html">-->
                <#--<span>${scenicInfo.city.father.name}</span>-->
            <#--</a>-->
            <a href="${SCENIC_PATH}">景点</a>
            &nbsp;&gt;&nbsp;
            <a href="/scenic_list.html?cityIdStr=${scenicInfo.city.father.id}">
                <span>${scenicInfo.city.father.name}</span>景点
            </a>
        <#else>
            <#--<a href="${DESTINATION_PATH}/city_${scenicInfo.city.cityCode}.html">-->
                <#--<span>${scenicInfo.city.name}</span>-->
            <#--</a>-->
            <a href="${SCENIC_PATH}">景点</a>
            &nbsp;&gt;&nbsp;
            <a href="/scenic_list.html?cityIdStr=${scenicInfo.city.id}">
                <span>${scenicInfo.city.name}</span>景点
            </a>
        </#if>
            &nbsp;&gt;&nbsp;${scenicInfo.name}
        </p>

        <form id="toList" method="post" action="${SCENIC_PATH}/scenic_list.html">
            <input type="hidden" id="citiesStr" name="citiesStr" value="">
        </form>
        <input type="hidden" id="scenicId" value="${scenicInfo.id?c}"/>
        <input type="hidden" id="targetId" value="${scenicInfo.id?c}" seftType="scenic"/>
        <!--detail_top-->
        <div class="detail_top posiR Food_d_t Hotels_d_t Attractions_d_t scenic-node" data-id="${scenicInfo.id}">
            <div class="pikachoose fl">
            <#if scenicInfo.scenicGalleryList>
                <#list scenicInfo.scenicGalleryList as gallery>
                    <#if gallery_index gt 4><#break ></#if>
                    <#if gallery_index=0>
                        <div class="mailTablePlan2"><img class="scenic-node-img" alt="${scenicInfo.name}"
                                                         src="http://7u2inn.com2.z0.glb.qiniucdn.com/${gallery.imgUrl}?imageView2/1/w/565/h/300"/>
                        </div>
                    <#else >
                        <div class="mailTablePlan2 disn"><img class="scenic-node-img" alt="${scenicInfo.name}"
                                                              src="http://7u2inn.com2.z0.glb.qiniucdn.com/${gallery.imgUrl}?imageView2/1/w/565/h/300"/>
                        </div>
                    </#if>
                </#list>
                <ul class="mailTab2">
                    <#list scenicInfo.scenicGalleryList as gallery>
                        <#if gallery_index gt 4><#break ></#if>
                        <#if gallery_index=0>
                            <li class="checked"><img alt="${scenicInfo.name}"
                                    src="http://7u2inn.com2.z0.glb.qiniucdn.com/${gallery.imgUrl}?imageView2/1/w/105/h/65"/>

                                <#--<p class="posiA">查看相册</p></li>-->
                        <#else >
                            <li><img alt="${scenicInfo.name}"
                                    src="http://7u2inn.com2.z0.glb.qiniucdn.com/${gallery.imgUrl}?imageView2/1/w/105/h/65"/>

                                <#--<p class="posiA">查看相册</p></li>-->
                        </#if>
                    </#list>
                </ul>
            <#else>
                <div class="mailTablePlan2"><img class="scenic-node-img" alt="${scenicInfo.name}"
                                                 <#if scenicInfo.cover?index_of("http") == 0>
                                                 src="${scenicInfo.cover}"
                                                 <#else>
                                                 src="http://7u2inn.com2.z0.glb.qiniucdn.com/${scenicInfo.cover}?imageView2/1/w/565/h/300"
                                                 </#if>
                        />
                </div>
                <ul class="mailTab2">
                    <li class="checked"><img alt="${scenicInfo.name}"
                            <#if scenicInfo.cover?index_of("http") == 0>
                            src="${scenicInfo.cover}"
                            <#else>
                            src="http://7u2inn.com2.z0.glb.qiniucdn.com/${scenicInfo.cover}?imageView2/1/w/105/h/65"
                            </#if>
                            />

                        <#--<p class="posiA">查看相册</p></li>-->
                </ul>
            </#if>
            </div>

            <div class="fr nr posiR">
                <p class="tianjiaxc b fs14 posiA stroke_open stroke" style="display:none"><i></i>
                    <spa>+添加至线路</spa>
                </p>
                <p class="name mb5"><b>${scenicInfo.name}</b></p>
            <#if scenicInfo.level?length gt 0>
                <span class="orange_bg">${scenicInfo.level}景区</span>
            </#if>

                <p class="h20 cl"></p>

                <div class="fs14 Hotels_d_t_d mb15">
                    <label class="color9 fl mr5">景点地址:</label>

                    <p class="add mr10 fl<#if scenicInfo.scenicOther.address?length gt 14> is_hover</#if>">${scenicInfo.scenicOther.address}</p>
                    <a href="javaScript:;" class="fl add_a">查看地图</a>
                </div>
                <div class="jianj lh20" style="height: 35px;"><label class="fl mr5">开放时间:</label>

                    <p class="food_p <#if scenicInfo.scenicOther.openTime?length gt 25>fl is_hover</#if>">${scenicInfo.scenicOther.openTime}</p>

                    <p class="h15 cl"></p>
                </div>

            <#if scenicInfo.scenicOther.adviceTimeDesc>
                <div class="fs14 Hotels_d_t_d mb15">
                    <label class="color9 fl mr5">建议游玩:</label>

                    <p<#if scenicInfo.scenicOther.adviceTimeDesc?replace("建议","")?length gt 20>
                            class="fl is_hover"</#if>>
                    ${scenicInfo.scenicOther.adviceTimeDesc?replace("建议","")}</p>
                </div>
            </#if>

                <p class="fraction cl mb10"><b class="fs16">${scenicInfo.score / 20}</b>/5分
                    <a href="#comment">
                        <span class="ml30" hidden="hidden">
                            <#--源自<span id="cc"></span>人点评-->
                        </span>
                    </a>
                </p>
            <#--${scenicInfo.scenicCommentList?size}-->
                <div class="posiA Hotels_nr">
                    <div class="dianp posiR cl">
                        <i class="portrai"></i>

                        <div>
                            <label class="b cl fs13 color6 mt5 mb5 disB">小帮点评：</label>

                            <p class="synopsis posiR">
                                <#if scenicInfo.scenicOther.recommendReason?length gt 40>
                                    <span class="fl is_hover" style="max-width: 82%;">
                                        ${scenicInfo.scenicOther.recommendReason?substring(0,38)}<span class='ellipsis'>...<br/></span>${scenicInfo.scenicOther.recommendReason?substring(38)}
                                    </span>
                                <#else>
                                    <span>${scenicInfo.scenicOther.recommendReason}</span>
                                </#if><i></i>
                            </p>
                        </div>
                        <p class="cl"></p>
                    </div>
                </div>
            </div>
            <p class="cl h15"></p>
        </div>
    </div>

    <!--搜索-->
    <div class="h50 mt30" id="nav">
        <div class="nav d_select">
            <div class="w1000 posiR">
                <dl class="d_select_d fl fs16 b">
                <#assign i=1>
                    <dd class="span${i}" style="display: none">门票</dd><#assign i=i+1>
                    <dd class="span${i} checked">景点简介</dd><#assign i=i+1>
                <#if childrenScenicInfo?size gt 0>
                    <dd class="span${i}">子景点</dd><#assign i=i+1>
                </#if>
                    <dd class="span${i}">交通指南</dd><#assign i=i+1>
                    <dd class="span${i}">景点点评</dd>
                </dl>
                <a href="javascript:;" class="but fr mt5 oval4
                <#--add_comment-->
                add_line">添加至线路</a>
                <a href="javascript:;" class="d_collect choose fr mt10 mr20 favorite"
                   data-favorite-id="${scenicInfo.id?c}" data-favorite-type="scenic"><i></i>收藏<br/></a>
            </div>
        </div>
    </div>

    <!--门票-->
<#assign j=1>
    <#if j%2 == 1>
    <div class="bg" id="id${j}" style="display: none">
    <#else>
    <div class="w1000 brief  pt30" id="id${j}">
    </#if><#assign j=j+1>
    <div class="w1000 Hotels_ss Attractions_ss">
        <div class="Hotels_lb cl posiR">
            <div class="Hotels_lb_top">
                <p class="w1">名称</p>

                <p class="w2">提前预定时间</p>

                <p class="w3">原价</p>

                <p class="w4">小帮价</p>
            </div>
            <div class="Hotels_lb_cen" id="ticketList">

            </div>
        </div>
    </div>
</div>

    <!--景点简介-->
<#if j%2 == 1>
<div class="bg" id="id${j}" style="padding-bottom: 0px">
<#else>
<div class="w1000 brief  pt30" id="id${j}">
</#if><#assign j=j+1>
    <div class="w1000">
        <p class="travel_t brief_t posiR mb15 fs16 b"><i class="d_ico disB posiA d_jj mr5"></i>景点简介</p>

        <div class="brief_div Attractions_div" style="text-indent:2em">

            <p class="mt20 t2">${scenicInfo.scenicOther.description}</p>

            <p class="h50"></p>
        </div>
    </div>
</div>

<#if childrenScenicInfo?size gt 0>
    <!--子景点-->
    <#if j%2 == 1>
    <div class="bg" id="id${j}">
    <#else>
    <div class="w1000 brief  pt30" id="id${j}">
    </#if><#assign j=j+1>
    <div class="w1000 ">
        <form id="childList" method="post" action="${SCENIC_PATH}/scenic_list.html">
            <input type="hidden" name="father" value="${scenicInfo.id}">
            <input type="hidden" name="fatherName" value="${scenicInfo.name}">
        </form>
        <p class="travel_t brief_t posiR mb10 fs16 b">
            <i class="d_ico disB posiA d_jidd mr5"></i>子景点
            <#if childrenScenicCount gt 4>
                <a href="javaScript:;" onclick="$('#childList').submit();" class="fr">查看更多&gt;</a>
            </#if>
        </p>
        <ul class="history_ul">
            <#list childrenScenicInfo as child>
                <li>
                    <a href="${SCENIC_PATH}/scenic_detail_${child.id}.html"><i class="posiA"
                                                                                   style="display: none;"></i>

                        <p class="img">
                            <#if child.cover?? && child.cover!="">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${child.cover}?imageView2/1/w/235/h/226/q/75" style="display: inline;" alt="${child.name}">
                            <#else>
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/jingdian.png?imageView2/1/w/235/h/226/q/75" style="display: inline;" alt="${child.name}">
                            </#if>
                        </p>

                        <div class="nr mt5">
                            <label class="name">${child.name}</label>
                        </div>
                    </a>
                </li>
            </#list>
        </ul>
    </div>
</div>
</#if>

    <!--交通指南-->
    <input id="baiduLng" type="hidden" value="${scenicInfo.scenicGeoinfo.baiduLng}"/>
    <input id="baiduLat" type="hidden" value="${scenicInfo.scenicGeoinfo.baiduLat}"/>
    <input id="scenicName" type="hidden" value="${scenicInfo.name}"/>

<#if j%2 == 1>
<div class="bg" id="id${j}" style="background-color: white">
<#else>
<div class="w1000 brief  pt30" id="id${j}">
</#if><#assign j=j+1>
    <div class="w1000">
        <p class="travel_t brief_t posiR mb15 fs16 b"><i class="d_ico disB posiA d_gt mr5"></i>交通指南</p>

        <div class="mb10" style="width:998px;height:400px;
        /*border:#ccc solid 1px;*/
        " id="mapContent"></div>
    <#if scenicInfo.scenicOther.trafficGuide?trim?length gt 0>
        <div class="color6 guide_div fs14">
            <b>交通信息</b>

            <p id="bus" style="white-space: pre-wrap; word-wrap: break-word;">${scenicInfo.scenicOther.trafficGuide}</p>
        </div>
    </#if>
        <p class="h50 cl" style="border-bottom:1px dashed #ccc;"></p>
    </div>
</div>

    <!--景点点评-->
    <div class="travel_d_l_dp w1000 pt30" id="id${j}">
        <p class="travel_t posiR mb20 fs16 b"><i class="d_ico disB posiA d_ms"></i>景点点评</p>
        <ul class="travel_d_ul" id="comment_ul">
        </ul>
        <!-- #BeginLibraryItem "/lbi/paging.lbi" -->
        <!--pager-->
        <p class="cl"></p>
        <img src="/images/food2.jpg" class="fr foodxx" style="margin-right:73px;"/>

        <div class="m-pager st cl">

        </div>
        <!--pager end-->
        <p class="cl h30"></p><!-- #EndLibraryItem -->
        <hr class="hr"/>
        <a href="javaScript:;" onclick="goRecommendPlanList(${scenicInfo.id},'scenic','${scenicInfo.name}')"
           class="m0auto disB textC oval4 view mb20 cl">查看相关游记</a>
        <div class="message2" id="add_comment">
            <h3 class=" ff_yh mb15">${scenicInfo.name}<span class="Orange ml10"></span><span></span></h3>
            <ul class="message2_ul">

            <#list commentScoreTypes as commentScoreType>
                <#if commentScoreType_index=0>
                    <li id="commentScoreType_li">
                        <label class="fl name color6 fs14 disB w100 textR mr10"><span
                                class="Orange mr5">*</span>${commentScoreType.name}:</label>

                        <div class="fl w860">
                            <div class="xzw_starSys mt10">
                                <div class="xzw_starBox">
                                    <ul class="star" mname="35">
                                        <li><a href="javascript:void(0)" title="1" class="one-star">1</a></li>
                                        <li><a href="javascript:void(0)" title="2" class="two-stars">2</a></li>
                                        <li><a href="javascript:void(0)" title="3" class="three-stars">3</a></li>
                                        <li><a href="javascript:void(0)" title="4" class="four-stars">4</a></li>
                                        <li><a href="javascript:void(0)" title="5" class="five-stars">5</a></li>
                                    </ul>
                                    <div class="current-rating" id="commentScoretypeId${commentScoreType_index+1}"
                                         commentScoreTypeId="${commentScoreType.id}"></div>

                                </div>
                                <!--评价文字-->
                                <div class="description">点击星星评分（必填）</div>
                            </div>
                        </div>
                    </li>
                </#if>
                <#if commentScoreType_index !=0>
                    <li>
                        <label class="fl name color6 fs14 disB w100 textR mr10"><span
                                class="Orange mr5">*</span>${commentScoreType.name}:</label>

                        <div class="fl w860">
                            <div class="xzw_starSys xzw_starSys2 mt10">
                                <div class="xzw_starBox">
                                    <ul class="star" mname="20">
                                        <li><a href="javascript:void(0)" title="1" class="one-star">1</a></li>
                                        <li><a href="javascript:void(0)" title="2" class="two-stars">2</a></li>
                                        <li><a href="javascript:void(0)" title="3" class="three-stars">3</a></li>
                                        <li><a href="javascript:void(0)" title="4" class="four-stars">4</a></li>
                                        <li><a href="javascript:void(0)" title="5" class="five-stars">5</a></li>
                                    </ul>
                                    <div class="current-rating" id="commentScoretypeId${commentScoreType_index+1}"
                                         commentScoreTypeId="${commentScoreType.id}"></div>
                                </div>
                                <!--评价文字-->

                            </div>
                        </div>
                    </li>
                </#if>
            </#list>

                <li>
                    <label class="fl name color6 fs14 disB w100 textR mr10"><span class="Orange mr5">*</span>评&nbsp;&nbsp;价:</label>

                    <div class="fl w860">
                        <div class="message  cl mt15">
                            <textarea name="comment" id="comment" maxlength="1000" mname="1000" class="mb10 textarea"
                                      placeholder="详细客观评价，以130字为最佳！"></textarea>

                            <p class="fl color6 lh20">您还可以输入<strong class="orange word" style="font-weight: normal">1000</strong>个字</p>
                        </div>
                    </div>
                </li>
                <li>
                    <p class="cl h20"></p>
                    <label class="fl name color6 fs14 disB w100 textR mr10 mt40">上传照片:</label>

                    <div class="fl w860">
                        <div class="fl" id="image_div">
                            <a href="javaScript:;"  class="file disB"/></a>
                        </div>

                        <p class="cl"></p>
                        <a href="javaScript:;" onclick="saveComment();" class=" mt15 but oval4">提交点评</a>
                    </div>
                </li>
            </ul>
        </div>
        <p class="cl h50"></p>
    </div>
</div>