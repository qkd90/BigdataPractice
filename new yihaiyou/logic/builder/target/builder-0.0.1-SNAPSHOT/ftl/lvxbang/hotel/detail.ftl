
<div class="w1000">
    <!--n_title-->
    <p class="n_title h40 lh40">
        您在这里：&nbsp;
        <#--<a href="${DESTINATION_PATH}">目的地</a>-->
        <#--&nbsp;&gt;&nbsp;<a href="${DESTINATION_PATH}/city_${city.cityCode}.html">${city.name}</a>-->
        <a href="${HOTEL_PATH}">酒店</a>
        &nbsp;&gt;&nbsp;<a href="javascript:;"
                           onclick="selectCities(this);"><span>${city.name}</span>酒店预订</a>&nbsp;&gt;&nbsp;${hotel.name}
    </p>

    <form id="toList" method="post" action="${HOTEL_PATH}/hotel_list.html">
        <input type="hidden" id="cities" name="cities" value="">
        <input type="hidden" id="cityId" name="hotelCityId" value="${city.cityCode}">
    </form>
    <input type="hidden" id="hotelId" value="${hotel.id}"/>
    <input type="hidden" id="targetId" value="${hotel.id}" seftType="hotel"/>
    <!--detail_top-->
    <div class="detail_top posiR Food_d_t Hotels_d_t">
        <div class="pikachoose fl">
        <#list hotel.productimage as image>
            <div class="mailTablePlan2 <#if image_index!=0>disn</#if>"><img src="${image.path}" alt="${hotel.name}"/></div>
        </#list>
            <ul class="mailTab2 fr">
            <#list hotel.productimage as image>
                <li class="<#if image_index==0>checked</#if>"><img src="${image.path}" alt="${hotel.name}"/>

                    <p class="posiA">查看相册</p></li>
            </#list>
            </ul>
        </div>

        <div class="fr nr posiR">
            <p class="name"><b>${hotel.name}</b></p>
            <span class="hstar s_star2 mb10"><i style="width: ${16 * hotel.star}px"></i></span>

            <div class="fs14 Hotels_d_t_d mb10">
                <label class="color9 fl mr5">景点地址:</label>

                <p class="add mr10 fl<#if hotel.extend.address?length gt 14> is_hover</#if>">${hotel.extend.address}</p>
                <a href="javaScript:;" class="fl add_a">查看地图</a>
            </div>
            <p class="fraction cl mb10"><b class="fs16">${(hotel.score/20)?string('#.#')}</b>/5分<span
                    class="ml30" hidden="hidden">源自<span id="cc"></span>人点评</span></p>

            <div class="posiA Hotels_nr">
                <div class="dianp posiR cl">
                    <i class="portrai"></i>

                    <div>
                        <label class="b cl fs13 color6 mt5 mb5 disB">小帮点评：</label>

                        <p class="synopsis posiR">
                        <#if hotel.shortDesc?length gt 45>
                            <span class="fl is_hover">
                                ${hotel.shortDesc?substring(0,43)}<span class='ellipsis'>...<br/></span>${hotel.shortDesc?substring(43)}
                            </span>
                        <#else>
                            <span>${hotel.shortDesc}</span>
                        </#if><i></i></p>
                    </div>
                    <p class="cl"></p>
                </div>
            </div>
        </div>
        <p class="cl h10"></p>
    </div>
</div>

<!--搜索-->
<div class="h50" id="nav">
    <div class="nav d_select">
        <div class="w1000 posiR">
            <dl class="d_select_d fl mr40 fs16 b">
                <dd class="span1 checked">酒店房型</dd>
                <dd class="span2 ">酒店简介</dd>
                <dd class="span3 ">交通指南</dd>
                <dd class="span4">酒店点评</dd>
            </dl>
            <a href="javascript:;" class="but fr mt5 oval4 add_comment">我要点评</a>
            <a href="javascript:;" class="d_collect choose fr mt10 mr20 favorite"
               data-favorite-id="${hotel.id}" data-favorite-type="${hotel.proType}"><i></i>收藏<br/></a>
        </div>
    </div>
</div>

<!--酒店房型-->
<div class="bg" id="id1">
    <div class="w1000 Hotels_ss">
        <dl class="n_select_d fl mr20 ml10">
            <dd><label class="fl mr10">入住</label><i class="ico ico2"></i><input id="check-in-date" type="text"
                                                                                placeholder="入住时间" value=""
                                                                                readOnly="true"
                                                                                onFocus="WdatePicker({minDate:'%y-%M-{%d}',onpicked:function(){$(this).change()}});"
                                                                                <#--cleanCheckoutdate();-->
                                                                                class="input fl" ></dd>
            <dd><label class="fl mr10">退房</label><i class="ico ico2"></i><input id="check-out-date" type="text"
                                                                                placeholder="退房时间" value=""
                                                                                readOnly="true"
                                                                                onFocus="WdatePicker({minDate:'#F{$dp.$D(\'check-in-date\',{d:1})}',maxDate:'#F{$dp.$D(\'check-in-date\',{d:30})}'})"
                                                                                class="input">
            </dd>
        </dl>
        <a href="javascript:hotelPrices();" class="oval4 l_line fl" id="hotel-searcher" style="margin-left:10px;">搜索</a>

        <div class="Hotels_lb cl">
            <div class="Hotels_lb_top">
                <p class="w1">房型</p>

                <p class="w2 posiR zaocan"style="text-align: right;">
                    <span class="checkbox fl "><i></i></span>
                    只含早餐
                </p>

                <p class="w3">退订政策</p>

                <p class="w4">日均价
                    <#--<i class="ml5"></i>-->
                </p>
            </div>
            <div class="Hotels_lb_cen">

                <p class="cl" id="hotel_house"></p>
                <p class="more textC "><a href="javaScript:;">查看更多房型<i></i></a></p>
            </div>


        </div>


    </div>
</div>

<!--酒店简介-->
<div class="w1000 brief  pt30" id="id2">
    <p class="travel_t brief_t posiR mb10 fs16 b"><i class="d_ico disB posiA d_jj mr5"></i>酒店简介</p>

    <div class="brief_div">
        <p class="t2">${hotel.extend.description}</p>

        <#--<p class="cl">-->
        <#--todo:填充优待政策-->
            <#--<b>优待政策</b><br/>-->
            <#--这个不知道是什么-->
            <#--电话：0592-3332999<br/>-->
            <#--传真：0592-3332939</p>-->

        <p class="h50"></p>
    </div>
</div>


<!--交通指南-->
<div class="bg" id="id3">
    <div class="w1000 guide">
        <p class="travel_t brief_t posiR mb10 fs16 b"><i class="d_ico disB posiA d_gt mr5"></i>交通指南</p>
        <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.1&services=true"></script>
        <div class="mb10" style="width:998px;height:400px;"
             id="mapContainer" lng="${hotel.extend.longitude}" lat="${hotel.extend.latitude}"
             scenic-name="${hotel.name}"></div>
        <!--地图-->
        <div class="color6 guide_div fs14">
            <b>交通信息</b>

            <p class="fs13" id="bus"></p>
        </div>
        <p class="h30 cl"></p>
    </div>
</div>


<!--酒店点评-->
<div class="travel_d_l_dp w1000 pt30" id="id4" name="comment">
    <p class="travel_t posiR mb20 fs16 b"><i class="d_ico disB posiA d_ms"></i>酒店点评</p>
    <ul class="travel_d_ul" id="comment_ul">

    </ul>
    <!-- #BeginLibraryItem "/lbi/paging.lbi" -->
    <!--pager-->
    <p class="cl"></p>
    <img src="/images/food2.jpg" class="fr foodxx" style="margin-right:73px;"/>

    <div class="m-pager st cl">
        <label class="total">共20页/3526条</label>
        <span>&lt;&nbsp;上一页</span>
        <a href="#">1</a>
        <a href="#">3</a>
        <a href="#">4</a>
        <a href="#" class="pageCurrent">5</a>
        <a href="#">6</a>
        <a href="#">7</a>
        <a href="#">8</a>
        <span class="dotStyle">...</span>
        <a href="#">下一页&nbsp;&gt;</a>
        <a href="#">末页</a>
    </div>
    <!--pager end-->
    <p class="cl h30"></p><!-- #EndLibraryItem -->
    <hr class="hr"/>
    <a href="javaScript:;" onclick="goRecommendPlanList(${hotel.id},'hotel','${hotel.name}')"
       class="m0auto disB textC oval4 view mb20 cl">查看相关游记</a>
    <!--厦门翔鹭国际大酒店-->
    <div class="message2" id="add_comment">
        <h3 class=" ff_yh mb15">${hotel.name}<span class="Orange ml10"></span><span></span></h3>
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
                <label class="fl name color6 fs14 disB w100 textR mr10"><span class="Orange mr5">*</span>评价:</label>

                <div class="fl w860">
                    <div class="message  cl mt15">
                        <textarea name="comment" id="comment" maxlength="1000" mname="1000" class="mb10 textarea"
                                  placeholder="详细客观评价，以130字为最佳！"></textarea>

                        <p class="fl color6 lh20">您还可以输入<strong class="orange word">1000</strong>个字</p>
                    </div>
                </div>
            </li>
            <li>
                <p class="cl h20"></p>
                <label class="fl name color6 fs14 disB w100 textR mr10 mt40">上传照片:</label>

            <#--<div class="fl w860">-->
            <#--<a href="javaScript:;" onclick="path.click()" class="file disB"/></a>-->
            <#--<input type="file" id="path" style="display:none" onchange="upfile.value=this.value">-->
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
</div>

<p class="cl h50"></p>
</div>