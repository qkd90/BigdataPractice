
<div class="main cl mt10">
    <div class="w1000">
        <!--n_title-->
        <p class="n_title h40 lh40">
            您在这里：&nbsp;
            <#--<a href="${DESTINATION_PATH}">目的地</a>-->
            <#--&nbsp;&gt;&nbsp;<a href="${DESTINATION_PATH}/city_${city.cityCode}.html">${city.name}</a>-->
            <a href="${DELICACY_PATH}">美食</a>
            &nbsp;&gt;&nbsp;<a href="javaScript:toDelicacyCity();">${city.name}特色美食</a>&nbsp;&gt;&nbsp;${delicacy.name}
        </p>

        <form action="${DELICACY_PATH}/food_list.html" method="post" id="formid">
            <input type="hidden" name="cityName" id="cityName" value="${city.name}"/>
            <input type="hidden" name="cityCode" id="cityCode" value="${city.cityCode}"/>
        </form>
        <input type="hidden" id="delicacyId" value="${delicacy.id}"/>
        <input type="hidden" id="targetId" value="${delicacy.id}" seftType="delicacy"/>
        <!--detail_top-->
        <div class="detail_top posiR Food_d_t">
            <p class="img fl mr10">
            <#if delicacy.cover>
                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${delicacy.cover}" alt="${delicacy.name}"/>
            <#else>
                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/meishi.png" alt="${delicacy.name}"/>
            </#if>

            </p>

            <div class="fr  nr">
                <div class="name mb10">
                    <b>${delicacy.name}</b>
                    <span>本地特色</span>
                <#if delicacy.taste>
                    <label class="fr"><font>口感</font><i>${delicacy.taste}</i></label>
                </#if>
                <#if delicacy.price>
                    <label class="fr mr10"><font>均价</font><i>${delicacy.price}</i></label>
                </#if>
                <#if delicacy.cuisine>
                    <label class="fr mr10"><font>菜系</font><i>${delicacy.cuisine}</i></label>
                </#if>
                </div>
                <div class="jianj mb10 lh20"><label class="fl">简介:</label>
                <#if delicacyExtend.introduction?length gt 90>
                    <div class="food_p fl is_hover">${delicacyExtend.introduction?substring(0,88)}<span class='ellipsis'>...<br/></span>${delicacyExtend.introduction?substring(87)}</div>
                <#else>
                    <div class="food_p">${delicacyExtend.introduction}</div>
                </#if>
                    <p class="cl"></p>
                </div>

                <div class="dianp posiR cl">
                    <i class="portrai"></i>

                    <div>
                        <label class="b cl fs13 color6 mt5 mb5 disB">小帮点评：</label>
                        <p class="synopsis posiR">
                        <#if delicacyExtend.recommendReason?trim?length gt 80>
                            <span class="fl is_hover">
                                ${delicacyExtend.recommendReason?trim?substring(0,78)}<span class='ellipsis'>...<br/></span>${delicacyExtend.recommendReason?trim?substring(78)}
                            </span>
                        <#else>
                            <span>${delicacyExtend.recommendReason?trim}</span>
                        </#if><i></i>
                        </p>
                    </div>
                    <p class="cl"></p>
                </div>
            </div>
            <p class="cl h10"></p>
        </div>
    </div>

    <!--相关饭店-->
    <div class="h50" id="nav">
        <div class="nav d_select">
            <div class="w1000 posiR">
                <dl class="d_select_d fl mr40 fs16 b">
                    <dd class="span1 checked">相关饭店</dd>
                    <dd class="span2">美食点评</dd>
                </dl>
                <a href="javascript:;" class="but fr mt5 oval4 add_comment ">我要点评</a>
                <a href="javascript:;" class="d_collect choose fr mt10 mr20 favorite"
                   data-favorite-id="${delicacy.id}" data-favorite-type="delicacy"><i></i>收藏<br/></a>
            </div>
        </div>
    </div>

    <!--相关饭店-->
    <div class="bg2">
    <div class="w1000 pt10">
    <#if restaurants>
        <!--相关饭店-->
        <ul class="ft_list" id="id1">



            <#list restaurants as r>

            <li>
                <p class="img fl">
	                <a>
	                	<#if r.restaurant.cover>
	                		<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${r.restaurant.cover}?imageView2/1/w/220/h/160" alt="${r.restaurant.name}">
	                	<#else>
	                		<img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/canting.png?imageView2/1/w/220/h/160" alt="${r.restaurant.name}">
	                	</#if>

	                </a>
                </p>

                <div class="nr fr lh30 posiR">
                    <a href="javaScript:;" class="posiA disB d_add_a look_map"
                       data-restaurant-baiduLng="${r.restaurant.geoInfo.baiduLng}"
                       data-restaurant-baiduLat="${r.restaurant.geoInfo.baiduLat}"
                       data-restaurant-name="${r.restaurant.name}">
                        <i class="d_ico disB d_add m0auto"></i>到这里去</a>

                    <p class="name b fl">${r.restaurant.name}</p>

                    <#--<p class="js cl"><label>价&nbsp;&nbsp;&nbsp;格：</label><span>:${restaurant.price}</span></p>-->

                    <#--<p class="js cl"><label>评&nbsp;&nbsp;&nbsp;分：</label><span>:${restaurant.score}</span></p>-->

                    <p class="js cl"><label>地&nbsp;&nbsp;址：</label><span>${r.restaurant.extend.address}</span></p>

                    <#if r.restaurant.extend.telephone = null>
                    <p class="js cl"><label>电&nbsp;&nbsp;话：</label><span>暂无</span>
                    </#if>
                    <#if r.restaurant.extend.telephone != null>
                    <p class="js cl"><label>电&nbsp;&nbsp;话：</label><span>${r.restaurant.extend.telephone}</span>
                    </#if>
                    </p>
                    <#if r.scenicList>
                        <p class="js cl"><label>周边景点：</label>
                    <span>
                        <#list r.scenicList as scenic>
                            <a href="${SCENIC_PATH}/scenic_detail_${scenic.id}.html">${scenic.name}</a>
                         </#list>
                        <#--<a href="#">中山路</a><a href="#">厦门大学</a><a href="#">南普陀寺</a>-->
                    </span></p>
                    </#if>
                    <div class="food_js cl"><label class="fs13">推荐理由：</label>
                        <#if r.restaurant.extend.shortComment = null>
                            <div class="food_p">值得一试</div>
                        </#if>
                        <#if r.restaurant.extend.shortComment != null>
                            <#if r.restaurant.extend.shortComment?length gt 82>
                            <div class="food_p fl is_hover">
                                ${r.restaurant.extend.shortComment?substring(0,80)}<span class='ellipsis'>...<br/></span>${r.restaurant.extend.shortComment?substring(80)}
                            </div>
                            <#else>
                            <div class="food_p">${r.restaurant.extend.shortComment}</div>
                            </#if>
                        </#if>
                    </div>
                </div>
                <p class="cl"></p>
            </li>
        </#list>


        </ul>
        <a href="javaScript:;" class="cl f_more h50 textC fs14 color6 disB m0auto mb30"
           id="more_restaurant">查看更多餐厅<i></i></a>
    <#else>
        <div style="text-align: center" id="id1"><img src="/images/nomeishi.png"></div>
        <p class="h50 cl" style="border-bottom:1px dashed #ccc;"></p>
    </#if>
        <!--美食点评-->
        <div class="travel_d_l_dp" id="id2">
            <p class="travel_t posiR mb20"><i class="d_ico disB posiA d_ms"></i>美食点评</p>
            <ul class="travel_d_ul" id="comment_ul">

            </ul>
            <!-- #BeginLibraryItem "/lbi/paging.lbi" -->
            <!--pager-->
            <p class="cl h30"></p>
            <img src="/images/food2.jpg" class="fr foodxx" style="margin-right:73px;"/>

            <div class="m-pager st cl">

            </div>
            <!--pager end-->
            <p class="cl h30"></p><!-- #EndLibraryItem -->
            <hr class="hr"/>
            <a href="javaScript:;" onclick="goRecommendPlanList(${delicacy.id},'delicacy','${delicacy.name}')"
               class="m0auto disB textC oval4 view mb20 cl">查看相关游记</a>

            <!--蔡林记热干面馆-->
            <div class="message2" id="add_comment">
                <h3 class=" ff_yh mb15">${delicacy.name}<span class="Orange ml10"></span><span></span></h3>
                <ul class="message2_ul" id="commentScoreType_ul">
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
                        <label class="fl name color6 fs14 disB w100 textR mr10"><span
                                class="Orange mr5">*</span>评&nbsp;&nbsp;价:</label>

                        <div class="fl w860">
                            <div class="message  cl mt15">
                                <textarea name="comment" id="comment" maxlength="1000" mname="1000"
                                          class="mb10 textarea" placeholder="详细客观评价，以130字为最佳！"></textarea>

                                <p class="fl color6 lh20">还能输入<strong class="orange word">1000</strong>字</p>
                            </div>
                        </div>
                    </li>
                    <li>
                        <p class="cl h20"></p>
                        <label class="fl name color6 fs14 disB w100 textR mr10 mt40">上传照片:</label>

                    <#--<div class="fl w860" id="image_div">-->
                    <#--<a href="javaScript:;"  class="file disB"/></a>-->
                    <#--<a href="javaScript:;" class=" mt15 but oval4" onclick="saveComment();">提交点评</a>-->
                    <#--</div>-->


                        <div class="fl w860">
                            <div class="fl" id="image_div">
                                <a href="javaScript:;"  class="file disB"/></a>
                            </div>

                        <#--<div class="img fl tup posiR ml10"><span class="oval4 disn">删除</span><img src="images/p_img.jpg" /></div>-->

                            <p class="cl"></p>
                            <a href="javaScript:;" onclick="saveComment();" class=" mt15 but oval4">提交点评</a>
                        </div>

                    </li>
                </ul>
            </div>
            <div>

            </div>
        </div>

        <p class="cl h50"></p>
    </div>
  </div>
</div>

