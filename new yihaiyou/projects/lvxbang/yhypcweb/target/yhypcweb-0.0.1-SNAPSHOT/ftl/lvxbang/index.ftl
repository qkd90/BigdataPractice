<div class="Yqybanner" xmlns="http://www.w3.org/1999/html">
    <div class=" w1000 lxb_main">
        <div class="lxb_hearder_catalog">
            <div class="lxb_catalog clearfix">

                <!-- 左侧一级导航 -->
                <div class="catalog_first J_SearchTabs">

                    <div class="J_SearchTab catalog_first_section c_f_selected" data-rel="srdz">
                        <a>
                            <i class="c_f_two catalog_icon"></i>
                            私人定制 </a>
                    </div>
                    <div class="J_SearchTab catalog_first_section " data-rel="mbdzjp">
                        <a>
                            <i class="c_f_one catalog_icon"></i>
                            定制精品 </a>
                    </div>
                    <div class="J_SearchTab catalog_first_section " data-rel="dzxq">
                        <a>
                            <i class="c_f_seven catalog_icon"></i>
                            定制需求 </a>
                    </div>

                    <div class="J_SearchTab catalog_first_section " data-rel="zzzj">
                        <a>
                            <i class="c_f_five catalog_icon"></i>
                            自助自驾 </a>
                    </div>
                    <div class="J_SearchTab catalog_first_section " data-rel="gty">
                        <a>
                            <i class="c_f_three catalog_icon"></i>
                            跟团游 </a>
                    </div>
                    <div class="J_SearchTab catalog_first_section " data-rel="yl">
                        <a>
                            <i class="c_f_six catalog_icon"></i>
                            邮轮 </a>
                    </div>
                    <div class="J_SearchTab catalog_first_section " data-rel="jd">
                        <a>
                            <i class="c_f_four catalog_icon"></i>
                            酒店 </a>
                    </div>
                </div>

                <!-- 定制精品 -->
                <div class="J_CatalogSeconds catalog_second lxb_none J_panel" id="mbdzjp">
                <#list mbdzjp?keys as label>
                    <div class="J_CatalogSecond catalog_second_section clearfix" data-rel="cat3">
                        <div class="c_s_section">
                            <div class="c_s_title">
                                <a href="javascript:;"
                                   class="title_name">${label}</a>
                                <#--<i class="c_s_arrow catalog_icon"></i>-->
                            </div>
                            <p class="c_s_nom">
                                <#list mbdzjp[label] as city>
                                <a href="${CUSTOM_PATH}/custom_made_${city.id}.html" target="_blank">${city.name}</a>
                                </#list>
                            </p>
                        </div>
                        <#--<i class="section_arrow catalog_icon "></i>-->
                    </div>
                </#list>
                </div>

                <!-- 跟团游分类二级导航 -->
                <div class="J_CatalogSeconds catalog_second lxb_none J_panel" id="gty">
                <#list mbdzjp?keys as label>
                    <div class="J_CatalogSecond catalog_second_section clearfix" data-rel="cat3">
                        <div class="c_s_section">
                            <div class="c_s_title">
                                <a href="javascript:;"
                                   class="title_name">${label}</a>
                                <#--<i class="c_s_arrow catalog_icon"></i>-->
                            </div>
                            <p class="c_s_nom">
                                <#list mbdzjp[label] as city>
                                    <a href="${GENTUAN_PATH}/group_tour_${city.id}.html" target="_blank">${city.name}</a>
                                </#list>
                            </p>
                        </div>
                        <#--<i class="section_arrow catalog_icon "></i>-->
                    </div>
                </#list>
                </div>

                <!-- 查询条件二级导航 -->
                <div class="J_CatalogRegular catalog_regular J_panel">
                    <a class="J_catalogClose catalog_close catalog_icon"></a>

                    <div class="diy_search_form" style="width: 415px;padding-right: 140px;">
                        <!-- 私人定制二级菜单 -->
                        <!--smart dingzhi S-->

                        <!-- 私人定制 -->
                        <div class="J_panel" id="srdz">
                            <!--标题位-->
                            <div class="dingzhi_title">
                                <h1 class="fs36 ff_yh" style="margin-bottom: 15px;">你只需决定出发</h1>
                                <span class="stroke_s" style="">
                                    <a href="javaScript:;">如何定制旅游线路</a>
             	                </span>
                                <img src="/images/wenhao.jpg">
                            </div>
                            <!-- dest-->
                            <div class="J_SearchCitiesRow search_row">
                                <!--input-->
                                <div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_hotel_dest search_dingzhi_dest"
                                     style="overflow: initial;">
                                    <div class="search_ctrl_inp_label">目的地&nbsp;|&nbsp;</div>
                                <#--<div class="search_ctrl_inp_placeholder search_hotel_holder">（多城市之间用&nbsp;;&nbsp;隔开）</div>-->
                                    <input class="search_ctrl_inp_input search_hotel_in des_into clickinput" data-multi="true"
                                           name="" placeholder="（多城市之间用 ; 隔开）" type="text" id="input_planId"
                                           data-url="/lvxbang/destination/getSeachAreaList.jhtml" autoComplete="Off"/>
                                    <!--目的地 clickinput input01 input-->
                                    <div class="posiA des_main" style="display: none;top: 20px;">
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
                                    </div>
                                    <!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                                    <!--关键字提示 clickinput input input01-->
                                    <div class="posiA categories_div  KeywordTips" style="top:30px;">
                                        <ul>

                                        </ul>
                                    </div>

                                    <!--错误-->
                                    <div class="posiA categories_div cuowu textL" style="top:30px;">
                                        <p class="cl">抱歉未找到相关的结果！</p>
                                    </div>
                                </div>
                            </div>
                            <!--按钮区域-->
                            <div class=" search_row">
                                <div>
                                </div>
                                <div class="dingzhi_left_btn search_ctrl search_submit">
                                    <a href="javascript:;" class="search_submit_btn  "
                                       onclick="SearcherBtn.recPlanByDest()">复制线路</a>
                                </div>
                                <div class="dingzhi_right_btn search_ctrl search_submit">
                                    <a href="javascript:;" class="search_submit_btn"
                                       onclick="SearcherBtn.scenicByDest()">原创线路</a>
                                </div>

                                <!--备注文字-->
                                <div>
                                    <div class="J_SearchStart search_ctrl search_ctrl_inp search_ctrl_city  dingzhi_intro_left">
                                        复制后调整成自己的特色线路
                                    </div>
                                    <div class="J_SearchEnd search_ctrl search_ctrl_inp search_ctrl_city 	 dingzhi_intro_right">
                                        创建一条属于自己的线路
                                    </div>
                                </div>
                            </div>
                            <!--热门目的地-->
                            <div class="J_PackageLeague league_column lxb_mt20 ">
                                <div class="league_title">
                                    <span class="line"></span>热门目的地
                                </div>
                                <div class="league_item" id="srdzHot">
                                <#list hot as aArea>
                                    <em>
                                        <a href="javascript:;">${aArea.name}</a>
                                    </em>
                                </#list>
                                </div>
                            </div>

                            <!--底部广告位-->
                            <div class="J_HotelLeague league_bottom">
                                <a href="${PLAN_PATH}" target="_blank">
                                    <img src="/images/index/cjzyx.jpg" alt=""></a>
                            </div>
                        </div>
                        <!--smart dingzhi E-->
                        <!-- 精品定制二级菜单 同跟团游-->

                        <!-- 定制需求二级菜单 -->
                        <!--定制需求 S-->
                        <div class="J_panel lxb_none" id="dzxq">

                            <!-- 定制需求 -->

                            <!--标题位-->
                            <div class="dingzhi_title ">
                                <h1 class="fs36 ff_yh">旅游定制专家来帮您...</h1>
                            </div>
                            <!--按钮区域-->
                            <div class=" search_row">
                                <div>
                                </div>
                                <div class="dingzhi_left_btn  search_ctrl search_submit">
                                    <a href="${REQUIRE_PATH}?customType=personal" target="_blank"
                                       class="search_submit_btn">个人定制</a>
                                </div>
                                <div class=" dingzhi_right_btn search_ctrl search_submit ">
                                    <a href="${REQUIRE_PATH}?customType=company" target="_blank"
                                       class="search_submit_btn">公司定制</a>
                                </div>

                            </div>
                            <!--热门目的地-->
                            <div class=" league_column lxb_mt20 ">
                                <div class="league_title">
                                    <span class="line"></span>主题定制
                                </div>
                                <div class="league_item">
                                    <#list mbztdz as label>
                                    <em>
                                        <a class="a_hot">${label.alias}</a>
                                    </em>
                                    </#list>
                                </div>
                                <!--图片广告位-->
                                <div class="J_HotelLeague league_adv_img ">
                                    <#--<a href="" target="_blank" rel="nofollow">-->
                                        <img src="/images/index/jd_2.jpg" alt="" title="">
                                    <#--</a>-->

                                </div>

                            </div>


                            <div class=" league_bottom">
                                <a href="${REQUIRE_PATH}" target="_blank">
                                    <img src="/images/index/cjzyx.jpg" alt=""></a>
                            </div>

                        </div>
                        <!--定制需求 E-->

                        <!-- 自助自驾二级菜单 -->
                        <!--package S-->
                        <div class="J_SearchPackage search_group search_pkg lxb_mt10 lxb_none J_panel" id="zzzj">
                            <!--出发城市+出发时间-->
                            <div class="search_row">
                                <!--出发城市-->
                                <div class="search_pkg_depart">
                                    <div class="J_DepartCity search_ctrl search_ctrl_inp search_ctrl_city">
                                        <div class="search_ctrl_inp_label">出发地&nbsp;|&nbsp;</div>
                                        <input class="search_ctrl_inp_input search_ctrl_inp_in clickinput input" name="depart"
                                               type="text" autocomplete="off" id="selfStartCity"
                                               data-url="/lvxbang/destination/getSeachAreaList.jhtml">
                                        <div class="posiA Addmore categories_Addmore2" style="display: none;top: 30px;">
                                            <i class="close"></i>
                                            <dl class="Addmore_dl">
                                                <dt>
                                                <div class="Addmore_nr">
                                                    <ul>
                                                        <li class="checked"><a href="javaScript:;">热门</a></li>
                                                        <li><a href="javaScript:;">A-E</a></li>
                                                        <li><a href="javaScript:;">F-J</a></li>
                                                        <li><a href="javaScript:;">K-P</a></li>
                                                        <li><a href="javaScript:;">Q-W</a></li>
                                                        <li><a href="javaScript:;">X-Z</a></li>
                                                    </ul>
                                                </div>
                                                </dt>
                                                <!--热门-->
                                                <dd style="display: block;">
                                                    <div class="Addmore_nr">
                                                        <ul>
                                                        <#list hot as aArea>
                                                            <li data-id="${aArea.id}">
                                                                <a href="javaScript:;">${aArea.name}</a>
                                                            </li>
                                                        </#list>
                                                        </ul>
                                                    </div>
                                                </dd>
                                                <!--A-E-->
                                            <#list letterSortAreas as letterSortArea>
                                                <dd class="disn">
                                                    <#list letterSortArea.letterRange as lrArea>
                                                        <label>${lrArea.name}</label>

                                                        <div class="Addmore_nr">
                                                            <ul>
                                                                <#list lrArea.list as aArea>
                                                                    <li data-id="${aArea.id}">
                                                                        <a href="javaScript:;">${aArea.name}</a>
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
                                        <!--关键字提示 clickinput input input01-->
                                        <div class="posiA categories_div  KeywordTips" style="top: 30px;">
                                            <ul>

                                            </ul>
                                        </div>

                                        <!--错误-->
                                        <div class="posiA categories_div cuowu textL" style="top: 30px;">
                                            <p class="cl">抱歉未找到相关的结果！</p>
                                        </div>
                                    </div>
                                </div>
                                <!--出发时间-->
                                <div class="search_pkg_start">
                                    <div class="J_DepartDate search_ctrl search_ctrl_inp search_ctrl_city">
                                        <div class="search_ctrl_inp_label">出发时间&nbsp;|&nbsp;</div>
                                        <div class="search_ctrl_inp_placeholder"></div>
                                        <input id="selfStartDate" class="search_ctrl_inp_input" name="" type="text" value="" readonly="" onfocus="WdatePicker({minDate:'%y-%M-{%d+1}',onpicked:function(){$(this).change();changeDates();}});">
                                    </div>
                                </div>
                            </div>
                            <!--目的地-->
                            <div class="J_SearchDestGroup search_pkg_dests">
                                <div class="J_SearchDestRow search_pkg_dest">
                                    <!--目的地-->
                                    <div class="J_SearchCity search_ctrl search_ctrl_inp search_ctrl_city" style="overflow:inherit;">
                                        <div class="search_ctrl_inp_label">目的地&nbsp;|&nbsp;</div>
                                        <input class="search_ctrl_inp_input search_ctrl_in clickinput input" name="" type="text"
                                               id="selfArriveCity" autocomplete="off"
                                               data-url="/lvxbang/destination/getSeachAreaList.jhtml">
                                        <div class="posiA Addmore categories_Addmore2" style="display: none;top: 30px;">
                                            <i class="close"></i>
                                            <dl class="Addmore_dl">
                                                <dt>
                                                <div class="Addmore_nr">
                                                    <ul>
                                                        <li class="checked"><a href="javaScript:;">热门</a></li>
                                                        <li><a href="javaScript:;">A-E</a></li>
                                                        <li><a href="javaScript:;">F-J</a></li>
                                                        <li><a href="javaScript:;">K-P</a></li>
                                                        <li><a href="javaScript:;">Q-W</a></li>
                                                        <li><a href="javaScript:;">X-Z</a></li>
                                                    </ul>
                                                </div>
                                                </dt>
                                                <!--热门-->
                                                <dd style="display: block;">
                                                    <div class="Addmore_nr">
                                                        <ul>
                                                        <#list hot as aArea>
                                                            <li data-id="${aArea.id}">
                                                                <a href="javaScript:;">${aArea.name}</a>
                                                            </li>
                                                        </#list>
                                                        </ul>
                                                    </div>
                                                </dd>
                                                <!--A-E-->
                                            <#list letterSortAreas as letterSortArea>
                                                <dd class="disn">
                                                    <#list letterSortArea.letterRange as lrArea>
                                                        <label>${lrArea.name}</label>

                                                        <div class="Addmore_nr">
                                                            <ul>
                                                                <#list lrArea.list as aArea>
                                                                    <li data-id="${aArea.id}">
                                                                        <a href="javaScript:;">${aArea.name}</a>
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
                                        <!--关键字提示 clickinput input input01-->
                                        <div class="posiA categories_div  KeywordTips" style="top: 30px;">
                                            <ul>

                                            </ul>
                                        </div>

                                        <!--错误-->
                                        <div class="posiA categories_div cuowu textL" style="top: 30px;">
                                            <p class="cl">抱歉未找到相关的结果！</p>
                                        </div>
                                    </div>
                                    <#--<div class="search_pkg_dest_date">2016-01-06<span>至</span>2016-01-08</div>-->
                                    <!--天数选择-->
                                    <#--<div class="J_SearchNumber search_ctrl search_number">-->
                                        <#--<a href="javascript:;" class="search_number_btn search_number_minus">-->
                                            <#--<i class="search_number_btn_icon">-</i>-->
                                        <#--</a>-->
                                        <#--<a href="javascript:;" class="search_number_btn search_number_plus">-->
                                            <#--<i class="search_number_btn_icon">+</i>-->
                                        <#--</a>-->
                                        <#--<input class="search_number_input" type="text" value="3天">-->
                                    <#--</div>-->
                                    <div class="search_dest_delete search_row_delete">
                                        <i class="search_icon icon_search_delete"></i>
                                    </div>
                                </div>
                            </div>

                            <!--人数选择-->
                            <div class="J_SearchStuffRow search_row">
                                <!--成人-->
                                <div class="search_pkg_adult">
                                    <div class="J_SearchAdult search_ctrl search_ctrl_select" style="border: 0;">
                                        <#--<div class="search_ctrl_select_placeholder">成人</div>-->
                                        <#--<div class="search_ctrl_select_btn">-->
                                            <#--<i class="search_ctrl_select_arrow"></i>-->
                                        <#--</div>-->
                                        <#--<input class="search_ctrl_select_input" readonly="">-->
                                    </div>
                                </div>
                                <!--儿童-->
                                <div class="search_pkg_child">
                                    <div class="J_SearchChild search_ctrl search_ctrl_select" style="border: 0;">
                                        <#--<div class="search_ctrl_select_placeholder">儿童（2~12周岁）</div>-->
                                        <#--<div class="search_ctrl_select_btn">-->
                                            <#--<i class="search_ctrl_select_arrow"></i>-->
                                        <#--</div>-->
                                        <#--<input class="search_ctrl_select_input" readonly="">-->

                                        <#--<div class="search_ctrl_select_options" style="display: none;">-->
                                            <#--<a class="search_ctrl_select_option" data-value="0"-->
                                               <#--href="javascript:;">0</a>-->
                                        <#--</div>-->
                                    </div>
                                </div>
                                <!--submit-->
                                <div class="J_SearchSubmit search_ctrl search_submit search_pkg_submit">
                                    <a href="javascript:;" class="search_submit_btn" onclick="SearcherBtn.selfLinelist();">搜索</a>
                                </div>
                            </div>

                            <!-- 下方分类 -->

                            <div class="J_PackageLeague league_column lxb_mt20">
                                <div class="league_title">
                                    <span class="line"></span>热门目的地
                                </div>
                                <div class="league_item">
                                    <#list hot as area>
                                    <em>
                                        <a class="a_hot" href="/self_tour_${area.id}.html"
                                           target="_blank">${area.name}</a>
                                    </em>
                                    </#list>
                                </div>
                            </div>

                            <div class="J_PackageLeague league_column lxb_mt30">
                                <div class="league_title">
                                    <span class="line"></span>线路特色
                                </div>
                                <div class="league_item">
                                    <#list mbxlts as label>
                                    <em>
                                        <#--<a href="http://super.tuniu.com/pkg/pid/579" target="_blank">-->
                                        ${label.alias}
                                        <#--</a>-->
                                    </em>
                                    </#list>
                                </div>
                            </div>

                            <div class="J_PackageLeague league_bottom ">
                                <a href="${ZIZHU_PATH}" target="_blank" rel="nofollow"><img
                                        src="/images/index/cjzyx.jpg" alt=""></a></div>
                        </div>
                        <!--package E-->
                        <!-- 邮轮二级菜单 -->
                        <!--cruise S-->
                        <div class="J_SearchCruise search_group search_cruise lxb_none J_panel" id="yl">
                            <div style="margin-top: 160px;width: 100%;text-align: center;font-size: 45px;color: #999;">
                            <span>即将上线</span>
                            </div>
                            <#--<div class="J_SearchSwitch search_switch ">-->
                                <#--<a href="javascript:;" class="switch_btn switch_selected" data-value="cruise"-->
                                   <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_Tab_邮轮__'])">邮轮</a>-->
                                <#--<a href="javascript:;" class="switch_btn" data-value="visa"-->
                                   <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_Tab_签证__'])">更多私人定制旅游&gt;</a>-->
                                <#--<a href="javascript:;" class="switch_btn" style="display:none;" data-value="visa"-->
                                   <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_Tab_签证__'])">签证</a>-->
                            <#--</div>-->
                            <#--<!-- 邮轮 &ndash;&gt;-->
                            <#--<div class="J_Cruise ">-->
                                <#--<!--departure & dest&ndash;&gt;-->
                                <#--<div class="J_SearchCitiesRow search_row">-->
                                    <#--<!--input&ndash;&gt;-->
                                    <#--<div class="J_SearchType search_ctrl search_ctrl_inp search_ctrl_city search_cruise_dest search_ctrl_select_expanded">-->
                                        <#--<div class="search_ctrl_inp_label">出发地&nbsp;|&nbsp;</div>-->
                                        <#--<input class="search_ctrl_inp_input search_cruise_in" name="" type="text">-->

                                        <#--<div class="search_ctrl_select_btn">-->
                                            <#--<i class="search_ctrl_select_arrow"></i>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                    <#--<!--input&ndash;&gt;-->
                                    <#--<div class="J_SearchRoute search_ctrl search_ctrl_inp search_ctrl_city search_cruise_path">-->
                                        <#--<div class="search_ctrl_inp_label">邮轮航线&nbsp;|&nbsp;</div>-->
                                        <#--<input class="search_ctrl_inp_input search_cruise_inp" name="" type="text">-->

                                        <#--<div class="search_ctrl_select_btn">-->
                                            <#--<i class="search_ctrl_select_arrow"></i>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <#--<!--start & end&ndash;&gt;-->
                                <#--<div class="J_SearchCruiseRow search_row">-->
                                    <#--<div class="search_cruise_brand">-->
                                        <#--<div class="J_SearchBrand search_ctrl search_ctrl_select">-->
                                            <#--<div class="search_ctrl_inp_label">邮轮品牌&nbsp;|&nbsp;</div>-->
                                            <#--<input class="search_ctrl_select_input search_cruise_inp search_w200"-->
                                                   <#--value="不限" readonly="">-->

                                            <#--<div class="search_ctrl_select_btn">-->
                                                <#--<i class="search_ctrl_select_arrow"></i>-->
                                            <#--</div>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                    <#--<div class="J_SearchSubmit search_ctrl search_submit search_pkg_submit">-->
                                        <#--<a href="javascript:;" class="search_submit_btn"-->
                                           <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_搜索__'])">搜索</a>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <#--<div class="J_CruiseLeague league_column lxb_mt20">-->
                                    <#--<div class="league_title">-->
                                        <#--<span class="line"></span>热门航线玩法-->
                                    <#--</div>-->
                                    <#--<div class="league_item">-->
                                        <#--<em>-->
                                            <#--<a class="a_hot"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_1-包船专享_'])"-->
                                               <#--href="http://www.tuniu.com/zt/new-baochuan/" target="_blank"-->
                                               <#--rel="nofollow">包船专享</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a class="a_hot"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_2-日韩_'])"-->
                                               <#--href="http://youlun.tuniu.com/d1-all-all/" target="_blank"-->
                                               <#--rel="nofollow">日韩</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_3-经典欧洲地中海_'])"-->
                                               <#--href="http://youlun.tuniu.com/d34-all-all/" target="_blank"-->
                                               <#--rel="nofollow">经典欧洲地中海</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_4-加勒比海度假_'])"-->
                                               <#--href="http://youlun.tuniu.com/d42-all-all/" target="_blank"-->
                                               <#--rel="nofollow">加勒比海度假</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_5-阿拉斯加冰川猎奇_'])"-->
                                               <#--href="http://youlun.tuniu.com/d40-all-all/" target="_blank"-->
                                               <#--rel="nofollow">阿拉斯加冰川猎奇</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_6-北欧童话波罗的海_'])"-->
                                               <#--href="http://youlun.tuniu.com/d35-all-all/" target="_blank"-->
                                               <#--rel="nofollow">北欧童话波罗的海</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_7-北极摄影之旅_'])"-->
                                               <#--href="http://youlun.tuniu.com/d48-all-all/" target="_blank"-->
                                               <#--rel="nofollow">北极摄影之旅</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_8-探索南极_'])"-->
                                               <#--href="http://youlun.tuniu.com/d47-all-all/" target="_blank"-->
                                               <#--rel="nofollow">探索南极</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_9-圆梦环球_'])"-->
                                               <#--href="http://youlun.tuniu.com/d10-all-all/" target="_blank"-->
                                               <#--rel="nofollow">圆梦环球</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_10-风情东南亚_'])"-->
                                               <#--href="http://youlun.tuniu.com/d6-all-all/" target="_blank"-->
                                               <#--rel="nofollow">风情东南亚</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_11-三峡_'])"-->
                                               <#--href="http://youlun.tuniu.com/d9-all-all/" target="_blank"-->
                                               <#--rel="nofollow">三峡</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门航线玩法_12-台湾_'])"-->
                                               <#--href="http://youlun.tuniu.com/d33-all-all/" target="_blank"-->
                                               <#--rel="nofollow">台湾</a>-->
                                        <#--</em>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <#--<div class="J_CruiseLeague league_column lxb_mt30">-->
                                    <#--<div class="league_title">-->
                                        <#--<span class="line"></span>热门活动-->
                                    <#--</div>-->
                                    <#--<div class="league_item">-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门活动_1-暑期酷玩邮轮_'])"-->
                                               <#--href="http://www.tuniu.com/szt/youlun/200.html" target="_blank"-->
                                               <#--rel="nofollow">暑期酷玩邮轮</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门活动_2-邮轮全年预售_'])"-->
                                               <#--href="http://www.tuniu.com/zt/youlun_2016/" target="_blank"-->
                                               <#--rel="nofollow">邮轮全年预售</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_邮轮_热门活动_3-2016量子号预售_'])"-->
                                               <#--href="http://www.tuniu.com/zt/youlun_yushou/" target="_blank"-->
                                               <#--rel="nofollow">2016量子号预售</a>-->
                                        <#--</em>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <#--<div class="J_CruiseLeague league_bottom">-->
                                    <#--<a href="http://youlun.tuniu.com/" target="_blank" rel="nofollow"><img-->
                                            <#--src="/images/index/cjzyx.jpg" alt=""></a></div>-->
                            <#--</div>-->
                            <#--<!-- 签证 &ndash;&gt;-->
                            <#--<div class="J_Visa lxb_none search_visa">-->
                                <#--<div class="J_SearchCitiesRow search_row">-->
                                    <#--<!--input&ndash;&gt;-->
                                    <#--<div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_visa_dest">-->
                                        <#--<div class="search_ctrl_inp_label">国家</div>-->
                                        <#--<div class="search_ctrl_inp_placeholder search_hotel_holder">请填写签证国家</div>-->
                                        <#--<input class="search_ctrl_inp_input search_visa_in" name="" type="text"-->
                                               <#--value="">-->
                                    <#--</div>-->
                                    <#--<!--input&ndash;&gt;-->
                                <#--</div>-->
                                <#--<!--start & end&ndash;&gt;-->
                                <#--<div class="J_SearchVisaRow search_row">-->
                                    <#--<div class="J_SearchSubmit search_ctrl search_submit search_visa_submit">-->
                                        <#--<a href="javascript:;" class="search_submit_btn"-->
                                           <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_搜索__'])">搜索</a>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <#--<div class="league_column lxb_mt20">-->
                                    <#--<div class="league_title">-->
                                        <#--<span class="line"></span> <a href="javascript:;" target="_blank" rel="nofollow"-->
                                                                      <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_n-热门活动_默认标题_邮轮'])">热门签证目的地</a>-->
                                    <#--</div>-->
                                    <#--<div class="league_item">-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_3738/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_1-美国_'])">美国</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_3905/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_2-日本_'])">日本</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_3904/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_3-韩国_'])">韩国</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_3604/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_4-法国_'])">法国</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_3910/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_5-泰国_'])">泰国</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_3911/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_6-马来西亚_'])">马来西亚</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_4102/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_7-澳大利亚_'])">澳大利亚</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_3608/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_8-英国_'])">英国</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_3912/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_9-新加坡_'])">新加坡</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_3402_3710/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_10-加拿大_'])">加拿大</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_3616/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_11-德国_'])">德国</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_3645/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_12-意大利_'])">意大利</a>-->
                                        <#--</em>-->
                                        <#--<em>-->
                                            <#--<a href="http://www.tuniu.com/visa/country_602_2900/" target="_blank"-->
                                               <#--rel="nofollow"-->
                                               <#--onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-邮轮签证_签证_热门活动_13-台湾_'])">台湾</a>-->
                                        <#--</em>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <#--<div class="league_bottom">-->
                                    <#--<a href="http://www.tuniu.com/visa/" target="_blank" rel="nofollow"><img-->
                                            <#--src="http://img2.tuniucdn.com/img/201601141600/index_v4/qianzhen.jpg"-->
                                            <#--alt=""></a></div>-->
                            <#--</div>-->
                        </div>
                        <!--cruise E-->
                        <!-- 机票二级菜单 -->
                        <!--flight S-->
                        <div class="J_SearchFlight search_group search_flight lxb_none J_panel">
                            <div class="search_switch J_SearchSwitch ">
                                <a href="javascript:;" class="switch_btn switch_selected" data-value="internal"
                                   onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-机票_Tab_国内机票'])">国内机票</a>
                                <a href="javascript:;" class="switch_btn" style="display:none;"
                                   data-value="international"
                                   onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-机票_Tab_国际机票'])">国际机票</a>
                                <a href="javascript:;" class="switch_btn" data-value="international"
                                   onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-机票_Tab_国际机票'])">更多定制游套餐&gt;</a>
                            </div>
                            <!-- 暂时只展示国内 -->
                            <!--departure & dest-->
                            <div class="J_InternalFlight ">
                                <!-- 单程返程 -->
                                <div class="search_switch_type J_SearchSwitchRadio">
                                    <ul>
                                        <li>
                                            <label class="lxb_switch" data-value="single">
                                                <i class="radio_icon catalog_icon"></i>
                                                <span class="radio_text">单程</span>
                                            </label>
                                        </li>
                                        <li>
                                            <label class="" data-value="round">
                                                <i class="radio_icon catalog_icon"></i>
                                                <span class="radio_text">往返</span>
                                            </label>
                                        </li>
                                    </ul>
                                </div>
                                <!-- 出发地目的地 往返 -->
                                <div class="J_SearchCitiesRow search_row " style="display: none;">
                                    <!--input-->
                                    <div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_flight_depart">
                                        <div class="search_ctrl_inp_label">出发地&nbsp;|&nbsp;</div>
                                        <div class="search_ctrl_inp_placeholder search_holder">请填写出发地</div>
                                        <input class="search_ctrl_inp_input search_flight_in" name="" type="text">
                                    </div>
                                    <div class="J_SearchChange search_ctrl search_flight_change">
                                        <i class="icon_search_change catalog_icon"></i>
                                    </div>
                                    <!--input-->
                                    <div class="J_SearchDest search_ctrl search_ctrl_inp search_ctrl_city search_flight_dest">
                                        <div class="search_ctrl_inp_label">目的地&nbsp;|&nbsp;</div>
                                        <div class="search_ctrl_inp_placeholder search_holder">请填写目的地</div>
                                        <input class="search_ctrl_inp_input search_flight_in" name="" type="text">
                                    </div>
                                </div>
                                <!--start & end 出发返回时间 -->
                                <div class="J_SearchDateRow search_row " style="display: none;">
                                    <div class="J_SearchStart search_ctrl search_ctrl_inp search_ctrl_city search_flight_start">
                                        <div class="search_ctrl_inp_label">出发&nbsp;|&nbsp;</div>
                                    <#--<div class="search_ctrl_inp_placeholder">周四</div>-->
                                        <input class="search_ctrl_inp_input" name="" type="text" value="2015-08-31">
                                    </div>
                                    <div class="J_SearchEnd search_ctrl search_ctrl_inp search_ctrl_city search_flight_end">
                                        <div class="search_ctrl_inp_label">返回&nbsp;|&nbsp;</div>
                                    <#--<div class="search_ctrl_inp_placeholder">周四</div>-->
                                        <input class="search_ctrl_inp_input" name="" type="text" value="2015-08-31">
                                    </div>
                                </div>

                                <!--depart & dest & date 单程 -->
                                <div class="J_SearchSingle search_rows" style="display: block;">
                                    <div class="search_row">
                                        <div class="J_SearchCities search_city_group">
                                            <!--input-->
                                            <div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_flight_depart">
                                                <div class="search_ctrl_inp_label">出发地&nbsp;|&nbsp;</div>
                                                <div class="search_ctrl_inp_placeholder search_holder">请填写出发地</div>
                                                <input class="search_ctrl_inp_input search_flight_in" name=""
                                                       type="text">
                                            </div>
                                            <div class="J_SearchChange search_ctrl search_flight_single_change">
                                                <i class="icon_search_change catalog_icon"></i>
                                            </div>
                                            <!--input-->
                                            <div class="J_SearchDest search_ctrl search_ctrl_inp search_ctrl_city search_flight_dest">
                                                <div class="search_ctrl_inp_label">目的地&nbsp;|&nbsp;</div>
                                                <div class="search_ctrl_inp_placeholder search_holder">请填写目的地</div>
                                                <input class="search_ctrl_inp_input search_flight_in" name=""
                                                       type="text">
                                            </div>
                                        </div>
                                        <div class="J_SearchDate search_ctrl search_ctrl_inp search_ctrl_city search_flight_start">
                                            <div class="search_ctrl_inp_label">出发&nbsp;|&nbsp;</div>
                                        <#--<div class="search_ctrl_inp_placeholder">周四</div>-->
                                            <input class="search_ctrl_inp_input" name="" type="text" value="2015-08-31">
                                        </div>
                                    </div>
                                </div>

                                <!--人数选择-->
                                <div class="J_SearchStuffRow search_row">
                                    <!--成人-->
                                    <div class="search_flight_adult">
                                        <div class="J_SearchAdult search_ctrl search_ctrl_select search_ctrl_select_expanded">
                                            <div class="search_ctrl_select_placeholder">成人</div>
                                            <div class="search_ctrl_select_btn">
                                                <i class="search_ctrl_select_arrow"></i>
                                            </div>
                                            <input class="search_ctrl_select_input">
                                        </div>
                                    </div>
                                    <!--儿童-->
                                    <div class="search_flight_child">
                                        <div class="J_SearchChild search_ctrl search_ctrl_select">
                                            <div class="search_ctrl_select_placeholder">儿童（2~12周岁）</div>
                                            <div class="search_ctrl_select_btn">
                                                <i class="search_ctrl_select_arrow"></i>
                                            </div>
                                            <input class="search_ctrl_select_input">
                                        </div>
                                    </div>
                                    <!--submit-->
                                    <div class="J_SearchSubmit search_ctrl search_submit search_flight_submit">
                                        <a href="javascript:;" class="search_submit_btn"
                                           onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-机票_国内机票_开始查找__'])">搜索</a>
                                    </div>
                                </div>
                                <div class="J_FlightLeague league_adv_img">
                                    <a href="javascript:;" target="_blank" rel="nofollow">
                                        <img src="./img/jp.jpg" alt="" title="">
                                    </a>
                                </div>

                                <div class="J_FlightLeague league_bottom"
                                     onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-机票_更多特价机票__'])">
                                    <a href="javascript:;" target="_blank" rel="nofollow"><img
                                            src="/images/index/cjzyx.jpg" alt=""></a></div>
                            </div>
                            <!-- international S -->
                            <div class="J_InternationalFlight lxb_none">
                                <div class="search_switch_type J_SearchSwitchRadio">
                                    <ul>
                                        <li>
                                            <label class="lxb_switch" data-value="single">
                                                <i class="radio_icon catalog_icon"></i>
                                                <span class="radio_text">单程</span>
                                            </label>
                                        </li>
                                        <li>
                                            <label class="" data-value="round">
                                                <i class="radio_icon catalog_icon"></i>
                                                <span class="radio_text">往返</span>
                                            </label>
                                        </li>
                                    </ul>
                                </div>
                                <div class="J_SearchCitiesRow search_row ">
                                    <!--input-->
                                    <div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_flight_depart">
                                        <div class="search_ctrl_inp_label">出发地&nbsp;|&nbsp;</div>
                                        <div class="search_ctrl_inp_placeholder search_holder">请填写出发地</div>
                                        <input onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录n-机票_国际机票_出发地_选择框_广州'])"
                                               class="search_ctrl_inp_input search_flight_in" name="" type="text">
                                    </div>
                                    <div class="J_SearchChange search_ctrl search_flight_change">
                                        <i class="icon_search_change catalog_icon"></i>
                                    </div>
                                    <!--input-->
                                    <div class="J_SearchDest search_ctrl search_ctrl_inp search_ctrl_city search_flight_dest">
                                        <div class="search_ctrl_inp_label">目的地&nbsp;|&nbsp;</div>
                                        <div class="search_ctrl_inp_placeholder search_holder">请填写目的地</div>
                                        <input onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录n-机票_国际机票_目的地_选择框_广州'])"
                                               class="search_ctrl_inp_input search_flight_in" name="" type="text">
                                    </div>
                                </div>
                                <!--start & end-->
                                <div class="J_SearchDateRow search_row">
                                    <div class="J_SearchStart search_ctrl search_ctrl_inp search_ctrl_city search_flight_start">
                                        <div class="search_ctrl_inp_label">出发&nbsp;|&nbsp;</div>
                                    <#--<div class="search_ctrl_inp_placeholder">周四</div>-->
                                        <input class="search_ctrl_inp_input" name="" type="text" value="2015-08-31">
                                    </div>
                                    <div class="J_SearchEnd search_ctrl search_ctrl_inp search_ctrl_city search_flight_end">
                                        <div class="search_ctrl_inp_label">返回&nbsp;|&nbsp;</div>
                                    <#--<div class="search_ctrl_inp_placeholder">周四</div>-->
                                        <input class="search_ctrl_inp_input" name="" type="text" value="2015-08-31">
                                    </div>
                                </div>
                                <!--depart & dest & date-->
                                <div class="J_SearchSingle search_rows lxb_none">
                                    <div class="search_row">
                                        <div class="J_SearchCities search_city_group">
                                            <!--input-->
                                            <div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_flight_depart">
                                                <div class="search_ctrl_inp_label">出发地&nbsp;|&nbsp;</div>
                                                <div class="search_ctrl_inp_placeholder search_holder">请填写出发地</div>
                                                <input class="search_ctrl_inp_input search_flight_in" name=""
                                                       type="text">
                                            </div>
                                            <div class="J_SearchChange search_ctrl search_flight_single_change">
                                                <i class="icon_search_change catalog_icon"></i>
                                            </div>
                                            <!--input-->
                                            <div class="J_SearchDest search_ctrl search_ctrl_inp search_ctrl_city search_flight_dest">
                                                <div class="search_ctrl_inp_label">目的地&nbsp;|&nbsp;</div>
                                                <div class="search_ctrl_inp_placeholder search_holder">请填写目的地</div>
                                                <input class="search_ctrl_inp_input search_flight_in" name=""
                                                       type="text">
                                            </div>
                                        </div>
                                        <div class="J_SearchDate search_ctrl search_ctrl_inp search_ctrl_city search_flight_start">
                                            <div class="search_ctrl_inp_label">出发&nbsp;|&nbsp;</div>
                                        <#--<div class="search_ctrl_inp_placeholder">周四</div>-->
                                            <input class="search_ctrl_inp_input" name="" type="text" value="2015-08-31">
                                        </div>
                                    </div>
                                </div>
                                <!--人数选择-->
                                <div class="J_SearchStuffRow search_row">
                                    <!--成人-->
                                    <div class="search_flight_adult">
                                        <div class="J_SearchAdult search_ctrl search_ctrl_select search_ctrl_select_expanded">
                                            <div class="search_ctrl_select_placeholder">成人</div>
                                            <div class="search_ctrl_select_btn">
                                                <i class="search_ctrl_select_arrow"></i>
                                            </div>
                                            <input class="search_ctrl_select_input">
                                        </div>
                                    </div>
                                    <!--儿童-->
                                    <div class="search_flight_child">
                                        <div class="J_SearchChild search_ctrl search_ctrl_select">
                                            <div class="search_ctrl_select_placeholder">儿童（2~12周岁）</div>
                                            <div class="search_ctrl_select_btn">
                                                <i class="search_ctrl_select_arrow"></i>
                                            </div>
                                            <input class="search_ctrl_select_input">
                                        </div>
                                    </div>
                                    <!--submit-->
                                    <div class="J_SearchSubmit search_ctrl search_submit search_flight_submit">
                                        <a href="javascript:;" class="search_submit_btn"
                                           onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-机票_国际机票_开始查找__'])">搜索</a>
                                    </div>
                                </div>
                                <div class="J_FlightLeague league_adv_img">
                                    <a href="javascript:;" target="_blank" rel="nofollow">
                                        <img src="http://img3.tuniucdn.com/u/mainpic/daohang/415105/jp.jpg" alt=""
                                             title="">
                                    </a></div>
                                <div class="J_FlightLeague league_bottom"
                                     onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-机票_国际机票'])">
                                    <a href="javascript:;" target="_blank" rel="nofollow"><img
                                            src="http://img.tuniucdn.com/img/201601141600/index_v4/jipiao.jpg"
                                            alt=""></a></div>
                            </div>
                            <!-- international E-->
                        </div>
                        <!--flight E-->

                        <!-- 酒店二级菜单 -->
                        <!--hotel S-->
                        <div class="J_SearchHotel search_group search_hotel lxb_none J_panel" id="jd">
                            <div class="search_switch J_SearchSwitch ">
                                <a href="javascript:;" class="switch_btn switch_selected" data-value="internal">酒店</a>
                                <a href="javascript:;" class="switch_btn" data-href="/self_tour_cityId.html" id="toSelfTour">更多自由行套餐&gt;</a>
                            </div>
                            <!-- 酒店 -->
                            <form action="${HOTEL_PATH}/hotel_list.html" method="post" id="hotelSearchForm">
                                <input type="hidden" name="hotelCityId" id="hotel_cityId"/>

                                <div class="J_InternalHotel ">
                                    <!--departure & dest-->
                                    <div class="J_SearchCitiesRow search_row">
                                        <!--input-->
                                        <div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_hotel_dest"
                                             style="overflow: inherit;">
                                            <div class="search_ctrl_inp_label">目的地&nbsp;|&nbsp;</div>
                                            <input class="search_ctrl_inp_input search_hotel_in clickinput input"
                                                   name="cities" type="text" autoComplete="Off" id="hotelLeaveCity"
                                                   data-url="/lvxbang/destination/getSeachAreaList.jhtml"
                                                   placeholder="请选择目的地">
                                            <!--目的地 clickinput input01 input-->
                                            <div class="posiA Addmore categories_Addmore2" style="display: none;top: 30px;">
                                                <i class="close"></i>
                                                <dl class="Addmore_dl">
                                                    <dt>
                                                    <div class="Addmore_nr">
                                                        <ul>
                                                            <li class="checked"><a href="javaScript:;">热门</a></li>
                                                            <li><a href="javaScript:;">A-E</a></li>
                                                            <li><a href="javaScript:;">F-J</a></li>
                                                            <li><a href="javaScript:;">K-P</a></li>
                                                            <li><a href="javaScript:;">Q-W</a></li>
                                                            <li><a href="javaScript:;">X-Z</a></li>
                                                        </ul>
                                                    </div>
                                                    </dt>
                                                    <!--热门-->
                                                    <dd style="display: block;">
                                                        <div class="Addmore_nr">
                                                            <ul>
                                                            <#list hot as aArea>
                                                                <li data-id="${aArea.id}">
                                                                    <a href="javaScript:;">${aArea.name}</a>
                                                                </li>
                                                            </#list>
                                                            </ul>
                                                        </div>
                                                    </dd>
                                                    <!--A-E-->
                                                <#list letterSortAreas as letterSortArea>
                                                    <dd class="disn">
                                                        <#list letterSortArea.letterRange as lrArea>
                                                            <label>${lrArea.name}</label>

                                                            <div class="Addmore_nr">
                                                                <ul>
                                                                    <#list lrArea.list as aArea>
                                                                        <li data-id="${aArea.id}">
                                                                            <a href="javaScript:;">${aArea.name}</a>
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
                                            <!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                                            <!--关键字提示 clickinput input input01-->
                                            <div class="posiA categories_div  KeywordTips" style="top: 30px;">
                                                <ul>

                                                </ul>
                                            </div>

                                            <!--错误-->
                                            <div class="posiA categories_div cuowu textL" style="top: 30px;">
                                                <p class="cl">抱歉未找到相关的结果！</p>
                                            </div>
                                        </div>
                                        <!--input-->
                                        <div class="J_SearchKeyWord search_ctrl search_ctrl_inp search_ctrl_city search_hotel_keyword"
                                             style="overflow: inherit;">
                                            <div class="search_ctrl_inp_label">关键词&nbsp;|&nbsp;</div>
                                            <input id="sygjc"
                                                   class="search_ctrl_inp_input search_hotel_in input input01 clickinput"
                                                   name="name" type="text" placeholder="酒店名/位置/品牌" autoComplete="Off"
                                                   data-url="${HOTEL_PATH}/lvxbang/hotel/suggest.jhtml" style="width: 138px;">
                                            <!--关键字提示 clickinput input input01-->
                                            <div class="posiA categories_div  KeywordTips" style="top: 30px;">
                                                <ul>
                                                </ul>
                                            </div>

                                            <!--错误-->
                                            <div class="posiA categories_div cuowu textL" style="top: 30px;">
                                                <p class="cl">抱歉未找到相关的结果！</p>
                                            </div>
                                        </div>
                                    </div>
                                    <!--start & end-->
                                    <div class="J_SearchDateRow search_row">
                                        <div class="J_SearchStart search_ctrl search_ctrl_inp search_ctrl_city search_hotel_start">
                                            <div class="search_ctrl_inp_label">入住日期&nbsp;|&nbsp;</div>
                                        <#--<div class="search_ctrl_inp_placeholder">周四</div>-->
                                            <input class="search_ctrl_inp_input search_hotel_inp" id="hotelStartDate"
                                                   name="startDate" type="text"
                                                   onfocus="WdatePicker({minDate:'%y-%M-{%d+1}',onpicked:function(){$(this).change();changeDates();}});">
                                        </div>
                                        <div class="J_SearchEnd search_ctrl search_ctrl_inp search_ctrl_city search_hotel_end">
                                            <div class="search_ctrl_inp_label">离店日期&nbsp;|&nbsp;</div>
                                        <#--<div class="search_ctrl_inp_placeholder">周四</div>-->
                                            <input class="search_ctrl_inp_input search_hotel_inp" id="hotelEndDate"
                                                   name="endDate" type="text"
                                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'hotelStartDate\',{d:1})}',onpicked:function(){$(this).change()}})">
                                        </div>
                                    </div>
                                    <div class="J_SearchHotelRow search_row">
                                        <div class="J_SearchSubmit search_ctrl search_submit search_hotel_submit">
                                            <a href="javascript:;" class="search_submit_btn"
                                               onclick="SearcherBtn.btnHotelSeach()">搜索</a>
                                        </div>
                                    </div>
                                    <div class="J_HotelLeague league_adv_img">
                                        <#--<a href="http://www.tuniu.com/zt/pcjdgzwxlhb100/" target="_blank"-->
                                           <#--rel="nofollow">-->
                                            <img src="/images/index/jd_2.jpg" alt="" title="">
                                        <#--</a>-->
                                    </div>
                                    <div class="J_HotelLeague league_bottom">
                                        <a href="${HOTEL_PATH}" target="_blank" rel="nofollow"><img
                                                src="/images/index/cjzyx.jpg" alt=""></a></div>
                                </div>
                            </form>
                            <!-- 门票 -->
                            <div class="J_Entrance lxb_none">
                                <!--departure & dest-->
                                <div class="J_SearchCitiesRow search_row">
                                    <!--input-->
                                    <div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_ticket_dest">
                                        <div class="search_ctrl_inp_label">关键词&nbsp;|&nbsp;</div>
                                        <div class="search_ctrl_inp_placeholder search_hotel_holder">请填写关键词</div>
                                        <input class="search_ctrl_inp_input search_ticket_in" name="" type="text"
                                               value="">
                                    </div>
                                    <!--input-->
                                </div>
                                <!--start & end-->
                                <div class="J_SearchTicketRow search_row">
                                    <div class="J_SearchSubmit search_ctrl search_submit search_ticket_submit">
                                        <a href="javascript:;" class="search_submit_btn"
                                           onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐WiFi_门票_搜索__'])">搜索</a>
                                    </div>
                                </div>
                                <div class="J_LocalLeague league_column lxb_mt20">
                                    <div class="league_title">
                                        <span class="line"></span>热门目的地
                                    </div>
                                    <div class="league_item">
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_1-北京_'])"
                                               href="http://menpiao.tuniu.com/cat_200_0_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">北京</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_2-上海_'])"
                                               href="http://menpiao.tuniu.com/cat_2500_0_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">上海</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_3-杭州_'])"
                                               href="http://menpiao.tuniu.com/cat_3400_3402_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">杭州</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_4-南京_'])"
                                               href="http://menpiao.tuniu.com/cat_1600_1602_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">南京</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_5-厦门_'])"
                                               href="http://menpiao.tuniu.com/cat_400_414_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">厦门</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_6-广州_'])"
                                               href="http://menpiao.tuniu.com/cat_600_602_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">广州</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_7-深圳_'])"
                                               href="http://menpiao.tuniu.com/cat_600_619_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">深圳</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_8-武汉_'])"
                                               href="http://menpiao.tuniu.com/cat_1400_1402_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">武汉</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_9-长沙_'])"
                                               href="http://menpiao.tuniu.com/cat_1500_1502_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">长沙</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_10-天津_'])"
                                               href="http://menpiao.tuniu.com/cat_3000_0_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">天津</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_11-苏州_'])"
                                               href="http://menpiao.tuniu.com/cat_1600_1615_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">苏州</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_12-成都_'])"
                                               href="http://menpiao.tuniu.com/cat_2800_2802_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">成都</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_13-香港_'])"
                                               href="http://menpiao.tuniu.com/cat_1300_0_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">香港</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_14-新加坡_'])"
                                               href="http://menpiao.tuniu.com/cat_3900_3912_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">新加坡</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_15-韩国_'])"
                                               href="http://menpiao.tuniu.com/cat_3900_3904_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">韩国</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_16-日本_'])"
                                               href="http://menpiao.tuniu.com/cat_3900_3905_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">日本</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_17-泰国_'])"
                                               href="http://menpiao.tuniu.com/cat_3900_3910_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">泰国</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_18-澳大利亚_'])"
                                               href="http://menpiao.tuniu.com/cat_4100_4102_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">澳大利亚</a>
                                        </em>
                                    </div>
                                </div>
                                <div class="J_LocalLeague league_column lxb_mt30">
                                    <div class="league_title">
                                        <span class="line"></span>游玩主题
                                    </div>
                                    <div class="league_item">
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_游玩主题_1-水上乐园_'])"
                                               href="http://menpiao.tuniu.com/cat_0_0_32_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">水上乐园</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_游玩主题_2-主题乐园_'])"
                                               href="http://menpiao.tuniu.com/cat_0_0_7_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">主题乐园</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_游玩主题_3-家庭亲子_'])"
                                               href="http://menpiao.tuniu.com/cat_0_0_26_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">家庭亲子</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_游玩主题_4-古镇园林_'])"
                                               href="http://menpiao.tuniu.com/cat_0_0_8_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">古镇园林</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_游玩主题_5-名胜风光_'])"
                                               href="http://menpiao.tuniu.com/cat_0_0_6_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">名胜风光</a>
                                        </em>
                                    </div>
                                </div>
                                <div class="J_LocalLeague league_bottom">
                                    <a href="http://menpiao.tuniu.com/" target="_blank" rel="nofollow"><img
                                            src="/images/index/cjzyx.jpg" alt=""></a></div>
                            </div>
                            <!-- 国际酒店 -->
                            <div class="J_InternationalHotel lxb_none">
                                <!--departure & dest-->
                                <div class="J_SearchCitiesRow search_row">
                                    <!--input-->
                                    <div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_hotel_dest">
                                        <div class="search_ctrl_inp_label">目的地&nbsp;|&nbsp;</div>
                                        <div class="search_ctrl_inp_placeholder search_hotel_holder">请选择目的地</div>
                                        <input class="search_ctrl_inp_input search_hotel_in" name="" type="text">
                                    </div>
                                    <!--input
							<div class="J_SearchKeyWord search_ctrl search_ctrl_inp search_ctrl_city search_hotel_keyword">
								<div class="search_ctrl_inp_label">关键词&nbsp;|&nbsp;</div>
								<input class="search_ctrl_inp_input search_hotel_in" name="" type="text" placeholder="酒店名/位置/品牌"/>
							</div>-->
                                </div>
                                <!--start & end-->
                                <div class="J_SearchDateRow search_row">
                                    <div class="J_SearchStart search_ctrl search_ctrl_inp search_ctrl_city search_hotel_start">
                                        <div class="search_ctrl_inp_label">入住日期&nbsp;|&nbsp;</div>
                                    <#--<div class="search_ctrl_inp_placeholder">周四</div>-->
                                        <input class="search_ctrl_inp_input search_hotel_inp" name="" type="text"
                                               value="2015-08-31">
                                    </div>
                                    <div class="J_SearchEnd search_ctrl search_ctrl_inp search_ctrl_city search_hotel_end">
                                        <div class="search_ctrl_inp_label">离店日期&nbsp;|&nbsp;</div>
                                    <#--<div class="search_ctrl_inp_placeholder">周四</div>-->
                                        <input class="search_ctrl_inp_input search_hotel_inp" name="" type="text"
                                               value="2015-08-31">
                                    </div>
                                </div>
                                <div class="J_SearchHotelRow search_row">
                                    <div class="J_SearchSubmit search_ctrl search_submit search_hotel_submit">
                                        <a href="javascript:;" class="search_submit_btn"
                                           onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-酒店_海外酒店_开始查找__'])"
                                        "="">搜索</a>
                                    </div>
                                </div>
                                <div class="J_HotelLeague league_adv_img">
                                    <a href="javascript:;" target="_blank" rel="nofollow">
                                        <img src="http://img4.tuniucdn.com/u/mainpic/daohang/415105/jd_gj0427.jpg"
                                             alt="" title="">
                                    </a></div>
                                <div class="J_HotelLeague league_bottom">
                                    <a href="http://globalhotel.tuniu.com/" target="_blank" rel="nofollow"><img
                                            src="http://img2.tuniucdn.com/img/201601141600/index_v4/jiudian.jpg" alt=""></a>
                                </div>
                            </div>
                        </div>
                        <!--hotel E-->

                        <!-- 火车票	二级菜单 -->
                        <!--train S-->
                        <div class="J_SearchTicket search_group search_train lxb_none J_panel">
                            <div class=" J_SearchSwitch search_switch ">
                                <a href="javascript:;" class="switch_btn switch_selected" data-value="train"
                                   onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-火车汽车票_Tab_火车票__'])">火车票</a>
                                <a href="javascript:;" class="switch_btn" data-value="bus">更多私人定制旅游&gt;</a>
                                <!--<a href="javascript:;" class="switch_btn" data-value="bus">汽车票</a>-->
                            </div>
                            <!-- 火车票 -->
                            <div class="J_Train ">
                                <div class="J_SearchSwitchRadio search_switch_type ">
                                    <ul>
                                        <li>
                                            <label class="lxb_switch" data-value="domestic">
                                                <i class="radio_icon catalog_icon"></i>
                                                <span class="radio_text">国内</span>
                                            </label>
                                        </li>
                                        <!-- <li><label data-value="european"><i class="radio_icon catalog_icon"></i><span class="radio_text">欧铁</span></label></li> -->
                                    </ul>
                                    <a class="search_high_rail J_SearchRail">
                                        <i class="high_icon catalog_icon"></i>
                                        <span class="only_high">仅搜索 高铁/动车</span>
                                    </a>
                                </div>
                                <!--departure & dest-->
                                <div class="J_SearchCitiesRow search_row">
                                    <!--input-->
                                    <div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_train_depart">
                                        <div class="search_ctrl_inp_label">出发站&nbsp;|&nbsp;</div>
                                        <div class="search_ctrl_inp_placeholder search_hotel_holder">请选择出发站</div>
                                        <input class="search_ctrl_inp_input search_train_in" name="" type="text">
                                    </div>
                                    <div class="J_SearchChange search_ctrl search_flight_change">
                                        <i class="icon_search_change catalog_icon"></i>
                                    </div>
                                    <!--input-->
                                    <div class="J_SearchDest search_ctrl search_ctrl_inp search_ctrl_city search_train_dest">
                                        <div class="search_ctrl_inp_label ">到达站&nbsp;|&nbsp;</div>
                                        <div class="search_ctrl_inp_placeholder search_hotel_holder">请选择到达站</div>
                                        <input class="search_ctrl_inp_input search_train_in" name="" type="text">
                                    </div>
                                </div>
                                <!--start & end-->
                                <div class="J_SearchDateRow search_row">
                                    <div class="search_train_start">
                                        <div class="J_DepartDate search_ctrl search_ctrl_select">
                                            <div class="search_ctrl_inp_label">出发时间&nbsp;|&nbsp;</div>
                                            <div class="search_ctrl_inp_placeholder"></div>
                                            <input class="search_ctrl_inp_input search_train_inp" type="text">
                                        </div>
                                    </div>

                                    <!--submit-->
                                    <div class="J_SearchSubmit search_ctrl search_submit search_flight_submit">
                                        <a href="javascript:;" class="search_submit_btn"
                                           onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-火车票_火车票_开始查找__'])">搜索</a>
                                    </div>
                                </div>
                                <div class="J_SearchSingle search_rows lxb_none">
                                    <div class="search_row">
                                        <div class="J_SearchCities search_city_group">
                                            <div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_train_depart">
                                                <div class="search_ctrl_inp_label">出发站&nbsp;|&nbsp;</div>
                                                <input class="search_ctrl_inp_input search_train_in" name=""
                                                       type="text">
                                            </div>
                                            <div class="J_SearchChange search_ctrl search_flight_change">
                                                <i class="icon_search_change catalog_icon"></i>
                                            </div>
                                            <div class="J_SearchDest search_ctrl search_ctrl_inp search_ctrl_city search_train_dest">
                                                <div class="search_ctrl_inp_label ">到达站&nbsp;|&nbsp;</div>
                                                <input class="search_ctrl_inp_input search_train_in" name=""
                                                       type="text">
                                            </div>
                                        </div>
                                        <div class="search_train_start">
                                            <div class="J_SearchDate search_ctrl search_ctrl_inp search_ctrl_city">
                                                <div class="search_ctrl_inp_label">出发&nbsp;|&nbsp;</div>
                                            <#--<div class="search_ctrl_inp_placeholder">周四</div>-->
                                                <input class="search_ctrl_inp_input" name="" type="text"
                                                       value="2015-08-31">
                                            </div>
                                        </div>
                                        <div class="J_SearchSubmit search_ctrl search_submit search_flight_submit">
                                            <a href="javascript:;" class="search_submit_btn"
                                               onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-火车汽车票_gz_搜索__火车票'])">搜索</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="J_TrainLeague league_adv_img">
                                    <a href="http://huoche.tuniu.com/trainpromotion_bonus" target="_blank"
                                       rel="nofollow">
                                        <img src="./img/jp.jpg" alt="" title="">
                                    </a>
                                </div>
                                <div class="J_TrainLeague league_bottom">
                                    <a href="http://huoche.tuniu.com/" target="_blank" rel="nofollow"><img
                                            src="/images/index/cjzyx.jpg" alt=""></a></div>
                            </div>
                            <!-- bus start 汽车票暂时隐藏 -->
                            <!-- bus end 汽车票暂时隐藏 -->
                        </div>
                        <!--train E-->


                        <!-- 门票二级菜单 -->
                        <!--local S-->
                        <div class="J_SearchLocal search_group search_ticket lxb_none J_panel">
                            <div class="J_SearchSwitch search_switch ">
                                <a href="javascript:;" class="switch_btn switch_selected" data-value="entrance"
                                   onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐WiFi_Tab_门票__'])">门票</a>
                                <a href="javascript:;" class="switch_btn" data-value="rental">更多门票+酒店&gt;</a>
                            </div>
                            <!-- 门票 -->
                            <div class="J_Entrance ">
                                <!--departure & dest-->
                                <div class="J_SearchCitiesRow search_row">
                                    <!--input-->
                                    <div class="J_SearchDepart search_ctrl search_ctrl_inp search_ctrl_city search_ticket_dest">
                                        <div class="search_ctrl_inp_label">关键词&nbsp;|&nbsp;</div>
                                        <div class="search_ctrl_inp_placeholder search_hotel_holder">请填写关键词</div>
                                        <input class="search_ctrl_inp_input search_ticket_in" name="" type="text"
                                               value="">
                                    </div>
                                    <!--input-->
                                </div>
                                <!--start & end-->
                                <div class="J_SearchTicketRow search_row">
                                    <div class="J_SearchSubmit search_ctrl search_submit search_ticket_submit">
                                        <a href="javascript:;" class="search_submit_btn"
                                           onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐WiFi_门票_搜索__'])">搜索</a>
                                    </div>
                                </div>
                                <div class="J_LocalLeague league_column lxb_mt20">
                                    <div class="league_title">
                                        <span class="line"></span>热门目的地
                                    </div>
                                    <div class="league_item">
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_1-北京_'])"
                                               href="http://menpiao.tuniu.com/cat_200_0_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">北京</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_2-上海_'])"
                                               href="http://menpiao.tuniu.com/cat_2500_0_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">上海</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_3-杭州_'])"
                                               href="http://menpiao.tuniu.com/cat_3400_3402_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">杭州</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_4-南京_'])"
                                               href="http://menpiao.tuniu.com/cat_1600_1602_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">南京</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_5-厦门_'])"
                                               href="http://menpiao.tuniu.com/cat_400_414_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">厦门</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_6-广州_'])"
                                               href="http://menpiao.tuniu.com/cat_600_602_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">广州</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_7-深圳_'])"
                                               href="http://menpiao.tuniu.com/cat_600_619_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">深圳</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_8-武汉_'])"
                                               href="http://menpiao.tuniu.com/cat_1400_1402_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">武汉</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_9-长沙_'])"
                                               href="http://menpiao.tuniu.com/cat_1500_1502_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">长沙</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_10-天津_'])"
                                               href="http://menpiao.tuniu.com/cat_3000_0_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">天津</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_11-苏州_'])"
                                               href="http://menpiao.tuniu.com/cat_1600_1615_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">苏州</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_12-成都_'])"
                                               href="http://menpiao.tuniu.com/cat_2800_2802_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">成都</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_13-香港_'])"
                                               href="http://menpiao.tuniu.com/cat_1300_0_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">香港</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_14-新加坡_'])"
                                               href="http://menpiao.tuniu.com/cat_3900_3912_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">新加坡</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_15-韩国_'])"
                                               href="http://menpiao.tuniu.com/cat_3900_3904_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">韩国</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_16-日本_'])"
                                               href="http://menpiao.tuniu.com/cat_3900_3905_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">日本</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_17-泰国_'])"
                                               href="http://menpiao.tuniu.com/cat_3900_3910_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">泰国</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_热门目的地_18-澳大利亚_'])"
                                               href="http://menpiao.tuniu.com/cat_4100_4102_0_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">澳大利亚</a>
                                        </em>
                                    </div>
                                </div>
                                <div class="J_LocalLeague league_column lxb_mt30">
                                    <div class="league_title">
                                        <span class="line"></span>游玩主题
                                    </div>
                                    <div class="league_item">
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_游玩主题_1-水上乐园_'])"
                                               href="http://menpiao.tuniu.com/cat_0_0_32_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">水上乐园</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_游玩主题_2-主题乐园_'])"
                                               href="http://menpiao.tuniu.com/cat_0_0_7_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">主题乐园</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_游玩主题_3-家庭亲子_'])"
                                               href="http://menpiao.tuniu.com/cat_0_0_26_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">家庭亲子</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_游玩主题_4-古镇园林_'])"
                                               href="http://menpiao.tuniu.com/cat_0_0_8_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">古镇园林</a>
                                        </em>
                                        <em>
                                            <a onclick="_gaq.push(['_trackEvent','首页_gz','点击','分类目录-门票玩乐_门票_游玩主题_5-名胜风光_'])"
                                               href="http://menpiao.tuniu.com/cat_0_0_6_0_0_0_1_1_1.html"
                                               target="_blank" rel="nofollow">名胜风光</a>
                                        </em>
                                    </div>
                                </div>
                                <div class="J_LocalLeague league_bottom">
                                    <a href="http://menpiao.tuniu.com/" target="_blank" rel="nofollow"><img
                                            src="/images/index/cjzyx.jpg" alt=""></a></div>
                            </div>

                        </div>
                        <!--local E-->
                    </div>
                </div>
            </div>
        </div>
        <div class="lxb_banner">
            <!--通栏广告位开始-->
            <div class="lxb_activity_slide slide">
                <div class="activity_slide" id="focus">
                    <ul class="activity_images">
                    <#list adses as ad>
                        <li class="" data-title="${ad.adTitle}">
                            <div style="left:0;top:0;width: 100%; height: 460px;">
                                <a href="${ad.url}" style="background:url(/static${ad.imgPath}) no-repeat center; background-size:cover;">
                                    <!--<img src="/static${ad.imgPath}" height="460" width="1903">-->
                                </a>
                            </div>
                        </li>
                    </#list>
                    </ul>
                </div>
            </div>
            <!--通栏广告位结束-->
            <#--<div class="lxb_banner_center">-->
                <#--<div class="lxb_bc_center">-->
                    <!--通栏广告title开始-->
                    <!--通栏广告title结束-->
                    <!--手风琴广告位开始-->

                    <#--<ul class="subject_images clearfix">-->
                        <#--<li class="current" style="">-->
                            <#--<a href="http://www.tuniu.com/niuren/" target="_blank" rel="nofollow"-->
                               <#--onclick="_gaq.push(['_trackEvent', '首页_xm', '点击', '手风琴广告_1-小帮专线0323']);">-->
                                <#--<img src="./img/xbzx2.jpg" style="">-->
                            <#--</a>-->
                        <#--</li>-->
                        <#--<li class="current" style="">-->
                            <#--<a href="http://www.tuniu.com/theme/haidao/" target="_blank" rel="nofollow"-->
                               <#--onclick="_gaq.push(['_trackEvent', '首页_xm', '点击', '手风琴广告_2-海岛游-新版']);">-->
                                <#--<img src="./img/island1.jpg" style="">-->
                            <#--</a>-->
                        <#--</li>-->
                        <#--<li class="current" style="">-->
                            <#--<a href="http://temai.tuniu.com/" target="_blank" rel="nofollow"-->
                               <#--onclick="_gaq.push(['_trackEvent', '首页_xm', '点击', '手风琴广告_3-限时特卖-新版']);">-->
                                <#--<img src="./img/Temai2.jpg" style="">-->
                            <#--</a>-->
                        <#--</li>-->
                        <#--<li class="current">-->
                            <#--<a href="http://youlun.tuniu.com/" target="_blank" rel="nofollow"-->
                               <#--onclick="_gaq.push(['_trackEvent', '首页_xm', '点击', '手风琴广告_4-邮轮-新版']);">-->
                                <#--<img src="./img/youlun1.jpg">-->
                            <#--</a>-->
                        <#--</li>-->
                        <#--<li class="current" style="">-->
                            <#--<a href="http://www.tuniu.com/drive/" target="_blank" rel="nofollow"-->
                               <#--onclick="_gaq.push(['_trackEvent', '首页_xm', '点击', '手风琴广告_6-自驾频道页']);">-->
                                <#--<img src="./img/zijia0419.jpg" style="">-->
                            <#--</a>-->
                        <#--</li>-->
                        <#--<li class="current" style="">-->
                            <#--<a href="http://www.tuniu.com/drive/" target="_blank" rel="nofollow"-->
                               <#--onclick="_gaq.push(['_trackEvent', '首页_xm', '点击', '手风琴广告_6-自驾频道页']);">-->
                                <#--<img src="./img/zijia0419.jpg" style="">-->
                            <#--</a>-->
                        <#--</li>-->
                        <#--<li>-->
                        <#--</li>-->
                    <#--</ul>-->
                <#--</div>-->
                <!--手风琴广告位结束-->
                <!--通栏箭头开始-->
                <#--<div class="slide_icon icon_prev" style="display: none;"></div>-->
                <#--<div class="slide_icon icon_next" style="display: none;"></div>-->
                <#--<!--通栏箭头结束&ndash;&gt;-->
                <#--<a href="http://www.tuniu.com/szt/shucu_guonei/" target="_blank"-->
                   <#--onclick="_gaq.push(['_trackEvent', '首页_xm', '点击', '轮播广告_4-暑促-国内游___']);" id="activityLink"-->
                   <#--class="activity_link">-->
                <#--</a>-->
            <#--</div>-->
            <!--顶部右侧边栏start-->
            <div class="lxb_bc_right disn">
                <div class="aside_summary">
                    <div class="statics clearfix">
                        <div class="hot-point" data-hot="96" style="background-position: 1px -6650px;">
                            <span class="hot-num" style="font-size:24px;right:24px;">96</span>
                            <span class="hot-du">%</span>
                            <span class="hot-txt">满意度</span>
                        </div>
                        <p class="item_common first_item">出游人数：<em>30800000+</em></p>

                        <p class="item_common">点评人数：<em>2500000+</em></p>
                    </div>

                    <div class="latest_order">
                        <ul class="order_list">
                            <li style="top: -63px;">
                                <p class="order_title">
                                    <a rel="nofollow" href="http://xm.tuniu.com/tours/20136223"
                                       title="[端午]<曼谷-芭堤雅5晚6日游>厦门往返，自由活动畅享泼水狂欢，电影同款，0自费" target="_blank"
                                       onclick="_gaq.push(['_trackEvent', '首页_xm','点击','品牌专区_预定列表_[端午]<曼谷-芭堤雅5晚6日游>厦门往返，自由活动畅享泼水狂欢，电影同款，0自费__']);">[端午]&lt;曼谷-芭堤雅5晚6日游&gt;厦门往返，自由活动畅享泼水狂欢，电影同款，0自费</a>
                                </p>

                                <p class="order_info">
                                    <span class="order_info_user">用户***288</span>
                                    <span class="order_info_item">7天前预订</span>
                                </p>
                            </li>
                            <li style="top: -63px;">
                                <p class="order_title">
                                    <a rel="nofollow" href="http://xm.tuniu.com/tours/38183617"
                                       title="<西欧八国全景13日游>广州往返，含服务费，西欧全景，香槟古堡，滴滴湖，最高立减2000/人" target="_blank"
                                       onclick="_gaq.push(['_trackEvent', '首页_xm','点击','品牌专区_预定列表_<西欧八国全景13日游>广州往返，含服务费，西欧全景，香槟古堡，滴滴湖，最高立减2000/人__']);">
                                        &lt;西欧八国全景13日游&gt;广州往返，含服务费，西欧全景，香槟古堡，滴滴湖，最高立减2000/人</a>
                                </p>

                                <p class="order_info">
                                    <span class="order_info_user">用户***296</span>
                                    <span class="order_info_item">8天前预订</span>
                                </p>
                            </li>
                            <li style="top: -63px;">
                                <p class="order_title">
                                    <a rel="nofollow" href="http://xm.tuniu.com/tours/20136223"
                                       title="[端午]<曼谷-芭堤雅5晚6日游>厦门往返，自由活动畅享泼水狂欢，电影同款，0自费" target="_blank"
                                       onclick="_gaq.push(['_trackEvent', '首页_xm','点击','品牌专区_预定列表_[端午]<曼谷-芭堤雅5晚6日游>厦门往返，自由活动畅享泼水狂欢，电影同款，0自费__']);">[端午]&lt;曼谷-芭堤雅5晚6日游&gt;厦门往返，自由活动畅享泼水狂欢，电影同款，0自费</a>
                                </p>

                                <p class="order_info">
                                    <span class="order_info_user">用户***aby</span>
                                    <span class="order_info_item">8天前预订</span>
                                </p>
                            </li>
                            <li style="top: -63px;">
                                <p class="order_title">
                                    <a rel="nofollow" href="http://xm.tuniu.com/tours/20136223"
                                       title="[端午]<曼谷-芭堤雅5晚6日游>厦门往返，自由活动畅享泼水狂欢，电影同款，0自费" target="_blank"
                                       onclick="_gaq.push(['_trackEvent', '首页_xm','点击','品牌专区_预定列表_[端午]<曼谷-芭堤雅5晚6日游>厦门往返，自由活动畅享泼水狂欢，电影同款，0自费__']);">[端午]&lt;曼谷-芭堤雅5晚6日游&gt;厦门往返，自由活动畅享泼水狂欢，电影同款，0自费</a>
                                </p>

                                <p class="order_info">
                                    <span class="order_info_user">用户***919</span>
                                    <span class="order_info_item">8天前预订</span>
                                </p>
                            </li>
                            <li style="top: -63px;">
                                <p class="order_title">
                                    <a rel="nofollow" href="http://xm.tuniu.com/tours/29214666"
                                       title="[端午]<曼谷-芭堤雅6晚7日游>厦门起止， 萨瓦迪卡，唐人街弄喜你，电影同款" target="_blank"
                                       onclick="_gaq.push(['_trackEvent', '首页_xm','点击','品牌专区_预定列表_[端午]<曼谷-芭堤雅6晚7日游>厦门起止， 萨瓦迪卡，唐人街弄喜你，电影同款__']);">[端午]&lt;曼谷-芭堤雅6晚7日游&gt;厦门起止，
                                        萨瓦迪卡，唐人街弄喜你，电影同款</a>
                                </p>

                                <p class="order_info">
                                    <span class="order_info_user">用户***021</span>
                                    <span class="order_info_item">8天前预订</span>
                                </p>
                            </li>
                            <li style="top: -63px;">
                                <p class="order_title">
                                    <a rel="nofollow" href="http://xm.tuniu.com/tours/20136223"
                                       title="[端午]<曼谷-芭堤雅5晚6日游>厦门往返，自由活动畅享泼水狂欢，电影同款，0自费" target="_blank"
                                       onclick="_gaq.push(['_trackEvent', '首页_xm','点击','品牌专区_预定列表_[端午]<曼谷-芭堤雅5晚6日游>厦门往返，自由活动畅享泼水狂欢，电影同款，0自费__']);">[端午]&lt;曼谷-芭堤雅5晚6日游&gt;厦门往返，自由活动畅享泼水狂欢，电影同款，0自费</a>
                                </p>

                                <p class="order_info">
                                    <span class="order_info_user">用户***668</span>
                                    <span class="order_info_item">8天前预订</span>
                                </p>
                            </li>
                            <li style="top: -63px;">
                                <p class="order_title">
                                    <a rel="nofollow" href="http://xm.tuniu.com/tours/36084781"
                                       title="<德法意瑞4国11或12天游>新天鹅堡 部分团期安排科莫湖 厦门出发 五星航空海南航空" target="_blank"
                                       onclick="_gaq.push(['_trackEvent', '首页_xm','点击','品牌专区_预定列表_<德法意瑞4国11或12天游>新天鹅堡 部分团期安排科莫湖 厦门出发 五星航空海南航空__']);">
                                        &lt;德法意瑞4国11或12天游&gt;新天鹅堡 部分团期安排科莫湖 厦门出发 五星航空海南航空</a>
                                </p>

                                <p class="order_info">
                                    <span class="order_info_user">用户***189</span>
                                    <span class="order_info_item">9天前预订</span>
                                </p>
                            </li>
                            <li style="top: -63px;">
                                <p class="order_title">
                                    <a rel="nofollow" href="http://xm.tuniu.com/tours/37540648"
                                       title="<宿雾-杜马盖地4晚5日自助游>厦门包机，直飞宿务，全程自由活动" target="_blank"
                                       onclick="_gaq.push(['_trackEvent', '首页_xm','点击','品牌专区_预定列表_<宿雾-杜马盖地4晚5日自助游>厦门包机，直飞宿务，全程自由活动__']);">
                                        &lt;宿雾-杜马盖地4晚5日自助游&gt;厦门包机，直飞宿务，全程自由活动</a>
                                </p>

                                <p class="order_info">
                                    <span class="order_info_user">用户***981</span>
                                    <span class="order_info_item">3天前预订</span>
                                </p>
                            </li>
                            <li style="top: -63px;">
                                <p class="order_title">
                                    <a rel="nofollow" href="http://xm.tuniu.com/tours/20136223"
                                       title="[端午]<曼谷-芭堤雅5晚6日游>厦门往返，自由活动畅享泼水狂欢，电影同款，0自费" target="_blank"
                                       onclick="_gaq.push(['_trackEvent', '首页_xm','点击','品牌专区_预定列表_[端午]<曼谷-芭堤雅5晚6日游>厦门往返，自由活动畅享泼水狂欢，电影同款，0自费__']);">[端午]&lt;曼谷-芭堤雅5晚6日游&gt;厦门往返，自由活动畅享泼水狂欢，电影同款，0自费</a>
                                </p>

                                <p class="order_info">
                                    <span class="order_info_user">用户***009</span>
                                    <span class="order_info_item">6天前预订</span>
                                </p>
                            </li>
                            <li style="top: -63px;">
                                <p class="order_title">
                                    <a rel="nofollow" href="http://xm.tuniu.com/tours/27116721"
                                       title="<帕劳太平洋香港直飞买机票送酒店5晚6自助游>百悦大饭店" target="_blank"
                                       onclick="_gaq.push(['_trackEvent', '首页_xm','点击','品牌专区_预定列表_<帕劳太平洋香港直飞买机票送酒店5晚6自助游>百悦大饭店__']);">
                                        &lt;帕劳太平洋香港直飞买机票送酒店5晚6自助游&gt;百悦大饭店</a>
                                </p>

                                <p class="order_info">
                                    <span class="order_info_user">用户***137</span>
                                    <span class="order_info_item">7天前预订</span>
                                </p>
                            </li>
                        </ul>
                    </div>

                    <div class="ad_cooperation">
                        <a href="javascript:;" target="_blank" rel="nofollow"
                           onclick="_gaq.push(['_trackEvent','首页_xm','点击','品牌专区_三大阳光保证20160530__']);">
                            <img src="http://img3.tuniucdn.com/u/mainpic/index/SunEnsure0526.jpg">
                        </a>
                    </div>
                    <!--当前热点图文广告位start-->
                    <div class="current_hot">
                        <div class="current_hot_img">
                            <a href="javascript:;" target="_blank" rel="nofollow"
                               onclick="_gaq.push(['_trackEvent','首页_xm','点击','品牌专区_出游服务_图片-当前热点-首航__']);">
                                <img src="http://m.tuniucdn.com/fb2/t1/G1/M00/E1/13/Cii9EFdGsG2IZ1WyAAAeG0eJZ8QAAGLjAD65PkAAB4z35.jpeg"
                                     width="230" height="56" alt="当前热点-首航">
                            </a>
                        </div>
                        <div class="current_hot_title">
                            <span>出游服务</span>
                        </div>
                        <div class="current_hot_main clearfix">
                            <div class="current_hot_main_l">
                                <a href="javascript:;"
                                   onclick="_gaq.push(['_trackEvent','首页_xm','点击','品牌专区_出游服务_图片-签 证__']);">
                                    <img src="http://m.tuniucdn.com/fb2/t1/G1/M00/B3/66/Cii9EFc5iqOIAFG_AAACEVDm6kAAAFUVAP__dcAAAIp250.png"><span>签 证</span>
                                </a>
                                <a href="javascript:;"
                                   onclick="_gaq.push(['_trackEvent','首页_xm','点击','品牌专区_出游服务_图片-全球WIFI__']);">
                                    <img src="http://m.tuniucdn.com/fb2/t1/G1/M00/C8/11/Cii9EVc5i7KIT4M8AAACg1gwM_YAAFzUwP__WUAAAKb810.png"><span>全球WIFI</span>
                                </a>
                                <a href="javascript:;"
                                   onclick="_gaq.push(['_trackEvent','首页_xm','点击','品牌专区_出游服务_图片-途牛向导__']);">
                                    <img src="http://m.tuniucdn.com/fb2/t1/G1/M00/C8/5C/Cii9EFc5jEeIAng8AAACE7eqhoAAAFzfQP__dUAAAIr903.png"><span>途牛向导</span>
                                </a>
                            </div>
                            <div class="current_hot_main_r">
                                <a href="javascript:;"
                                   onclick="_gaq.push(['_trackEvent','首页_xm','点击','品牌专区_出游服务_图片-当地玩乐__']);">
                                    <img src="http://m.tuniucdn.com/fb2/t1/G1/M00/C5/84/Cii9EFc5i2qIKxR5AAAB_1-13GUAAFv4gP__ekAAAIX727.png"><span>当地玩乐</span>
                                </a>
                                <a href="http://zuche.tuniu.com/"
                                   onclick="_gaq.push(['_trackEvent','首页_xm','点击','品牌专区_出游服务_图片-租车用车__']);">
                                    <img src="http://m.tuniucdn.com/fb2/t1/G1/M00/C5/5E/Cii9EFc5i_aIewqOAAACFeMfKCoAAFvXwP__dMAAAIt939.png"><span>租车用车</span>
                                </a>
                                <a href="http://super.tuniu.com/"
                                   onclick="_gaq.push(['_trackEvent','首页_xm','点击','品牌专区_出游服务_图片-机酒套餐__']);">
                                    <img src="http://m.tuniucdn.com/fb2/t1/G1/M00/C3/7A/Cii9EFc5jHSIELIGAAACmpjKHscAAFrWAP__U4AAAKy560.png"><span>机酒套餐</span>
                                </a>
                            </div>
                        </div>
                    </div>
                    <!--当前热点图文广告位end-->

                </div>
            </div>
            <!--顶部右侧边栏end-->
        </div>
    </div>
    <div class="slide-main disn" id="touchMain">
        <a class="prev" href="javascript:;" stat="prev1001" style="display: none;"></a>

        <div class="slide-box textC" id="slideContent">
            <div class="slide">
                <img src="./私人定制_定制旅游_自由行-旅行帮_files/shouye.png" alt="" style="width:1903px;height:460px;">
            </div>
        </div>
        <a class="next" href="javascript:;" stat="next1002" style="display: none;"></a>

        <div class="item" style="display: none;">
            <a class="cur" stat="item1001" href="javascript:;"></a>
        </div>
    </div>
</div>
<div class="main cl">
    <div class="w1000">
        <div id="dzjp" class="horizontal guoneizizhu clearfix">
            <h2>定制精品</h2>
            <ul class="clearfix mytab nav_tabs">
                <#list dzjp?keys as label>
                <li<#if label_index == 0> class="on"</#if>><a href="javascript:void(0)">${label}</a></li>
                </#list>
            </ul>
            <div class="line-con tab_infos">
            <#list dzjp?keys as label>
                <div class="linebox an_mo<#if label_index == 0> now</#if>">
                    <div class="block clearfix">
                        <!-- paihangModule phMleft start -->
                        <div class="guanzhu_new">
                            <p class="title">热门目的地</p>
                            <#list dzjp[label] as list>
                            <#if list_index == 0>
                                <#list list as city>
                                    <a href="/custom_made_${city.id}.html" target="_blank">
                                        <dl>
                                            <dd title="${city.name}">${city.name}</dd>
                                            <#--<dt><i class="lxb_fontface"></i>881348</dt>-->
                                        </dl>
                                    </a>
                                </#list>
                            </#if>
                            </#list>
                        </div>
                        <!-- paihangModule phMleft end -->
                        <!-- adModule adMleftad1 start -->
                        <div class="leftpic leftpic2">
                            <a target="_blank" href="javascript:;"><img src="/images/index/left_pic1.png" title=""></a>
                        </div>
                        <!-- adModule adMleftad1 end -->
                        <!-- proRecomModule prdMblack start -->
                <#list dzjp[label] as list>
                    <#if list_index == 1>
                        <#list list as line>
                            <#if line_index == 6><#break></#if>
                            <div class="block_item">
                                <div class="pic">
                                    <a href="/line_detail_${line.id}.html" target="_blank"><img
                                            data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/220/h/125/q/75"></a>
                                    <a href="/line_detail_${line.id}.html" target="_blank"
                                       title="<${line.name}>${line.appendTitle}" class="picmask"></a>
                                    <a href="/line_detail_${line.id}.html" target="_blank" class="words">

                                    </a>
                                </div>
                                <div class="price">
                                <#--<#if line.price gt 0>-->
                                <#--<span>¥</span>${line.price}<span>元起</span>-->
                                <#--</#if>-->
                                    <span class="myd">满意度：<em>${line.satisfaction}%</em></span>
                                </div>
                                <div class="name">
                                    <a href="/line_detail_${line.id}.html" target="_blank"
                                       title="<${line.name}>${line.appendTitle}">&lt;${line.name}
                                        &gt;${line.appendTitle}</a>
                                </div>
                            </div>
                        </#list>
                    </#if>
                </#list>
                        <!-- proRecomModule prdMblack end -->
                        <!-- txtModule txtMself start -->
                        <a href="${CUSTOM_PATH}" target="_blank" class="more">查看更多&gt;</a>
                        <!-- txtModule txtMself end --></div></div>
            </#list>
            </div>
        </div>
        <div id="zbly" class="horizontal guoneizizhu clearfix">
            <h2>周边旅游</h2>
            <ul class="clearfix mytab nav_tabs">
            <#list zbly?keys as label>
                <li<#if label_index == 0> class="on"</#if>><a href="javascript:void(0)">${label}</a></li>
            </#list>
            </ul>
            <div class="line-con tab_infos">
            <#list zbly?keys as label>
                <div class="linebox an_mo<#if label_index == 0> now</#if>">
                    <div class="block clearfix">
                        <!-- paihangModule phMleft start -->
                        <div class="guanzhu_new">
                            <p class="title">热门目的地</p>
                            <#list zbly[label] as list>
                                <#if list_index == 0>
                                    <#list list as city>
                                        <a href="/group_tour_${city.id}.html" target="_blank">
                                            <dl>
                                                <dd title="${city.name}">${city.name}</dd>
                                                <#--<dt><i class="lxb_fontface"></i>881348</dt>-->
                                            </dl>
                                        </a>
                                    </#list>
                                </#if>
                            </#list>
                        </div>
                        <!-- paihangModule phMleft end -->
                        <!-- adModule adMleftad1 start -->
                        <div class="leftpic leftpic2">
                            <a target="_blank" href="javascript:;"><img src="/images/index/left_pic2.png" title=""></a>
                        </div>
                        <!-- adModule adMleftad1 end -->
                        <!-- proRecomModule prdMblack start -->
                        <#list zbly[label] as list>
                            <#if list_index == 1>
                                <#list list as line>
                                    <#if line_index == 6><#break></#if>
                                    <div class="block_item">
                                        <div class="pic">
                                            <a href="/line_detail_${line.id}.html" target="_blank"><img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/220/h/125/q/75"></a>
                                            <a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}" class="picmask"></a>
                                            <a href="/line_detail_${line.id}.html" target="_blank" class="words">

                                            </a>
                                        </div>
                                        <div class="price">
                                            <#if line.price gt 0>
                                                <span>¥</span>${line.price}<span>元起</span>
                                            </#if>
                                            <span class="myd">满意度：<em>${line.satisfaction}%</em></span>
                                        </div>
                                        <div class="name">
                                            <a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}">&lt;${line.name}&gt;${line.appendTitle}</a>
                                        </div>
                                    </div>
                                </#list>
                            </#if>
                        </#list>
                        <!-- proRecomModule prdMblack end -->
                        <!-- txtModule txtMself start -->
                        <a href="${GENTUAN_PATH}" target="_blank" class="more">查看更多&gt;</a>
                        <!-- txtModule txtMself end --></div></div>
            </#list>
            </div>
        </div>
        <div id="gnly" class="horizontal guoneizizhu clearfix">
            <h2>国内旅游</h2>
            <ul class="clearfix mytab nav_tabs">
            <#list gnly?keys as label>
                <li<#if label_index == 0> class="on"</#if>><a href="javascript:void(0)">${label}</a></li>
            </#list>
            </ul>
            <div class="line-con tab_infos">
            <#list gnly?keys as label>
                <div class="linebox an_mo<#if label_index == 0> now</#if>">
                    <div class="block clearfix">
                        <!-- paihangModule phMleft start -->
                        <div class="guanzhu_new">
                            <p class="title">热门目的地</p>
                            <#list gnly[label] as list>
                                <#if list_index == 0>
                                    <#list list as city>
                                        <a href="/group_tour_${city.id}.html" target="_blank">
                                            <dl>
                                                <dd title="${city.name}">${city.name}</dd>
                                                <#--<dt><i class="lxb_fontface"></i>881348</dt>-->
                                            </dl>
                                        </a>
                                    </#list>
                                </#if>
                            </#list>
                        </div>
                        <!-- paihangModule phMleft end -->
                        <!-- adModule adMleftad1 start -->
                        <div class="leftpic leftpic2">
                            <a target="_blank" href="javascript:;"><img src="/images/index/left_pic3.png" title=""></a>
                        </div>
                        <!-- adModule adMleftad1 end -->
                        <!-- proRecomModule prdMblack start -->
                        <#list gnly[label] as list>
                            <#if list_index == 1>
                                <#list list as line>
                                    <#if line_index == 6><#break></#if>
                                    <div class="block_item">
                                        <div class="pic">
                                            <a href="/line_detail_${line.id}.html" target="_blank"><img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/220/h/125/q/75"></a>
                                            <a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}" class="picmask"></a>
                                            <a href="/line_detail_${line.id}.html" target="_blank" class="words">

                                            </a>
                                        </div>
                                        <div class="price">
                                            <#if line.price gt 0>
                                                <span>¥</span>${line.price}<span>元起</span>
                                            </#if>
                                            <span class="myd">满意度：<em>${line.satisfaction}%</em></span>
                                        </div>
                                        <div class="name">
                                            <a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}">&lt;${line.name}&gt;${line.appendTitle}</a>
                                        </div>
                                    </div>
                                </#list>
                            </#if>
                        </#list>
                        <!-- proRecomModule prdMblack end -->
                        <!-- txtModule txtMself start -->
                        <a href="${GENTUAN_PATH}" target="_blank" class="more">查看更多&gt;</a>
                        <!-- txtModule txtMself end --></div></div>
            </#list>
            </div>
        </div>
        <#--<div id="cjdx" class="horizontal guoneizizhu clearfix">-->
            <#--<h2>出境短线</h2>-->
            <#--<ul class="clearfix mytab nav_tabs">-->
            <#--<#list cjdx?keys as label>-->
                <#--<li<#if label_index == 0> class="on"</#if>><a href="javascript:void(0)">${label}</a></li>-->
            <#--</#list>-->
            <#--</ul>-->
            <#--<div class="line-con tab_infos">-->
            <#--<#list cjdx?keys as label>-->
                <#--<div class="linebox an_mo<#if label_index == 0> now</#if>">-->
                    <#--<div class="block clearfix">-->
                        <#--<!-- paihangModule phMleft start &ndash;&gt;-->
                        <#--<div class="guanzhu_new">-->
                            <#--<p class="title">热门目的地</p>-->
                            <#--<#list cjdx[label] as list>-->
                                <#--<#if list_index == 0>-->
                                    <#--<#list list as city>-->
                                        <#--<a href="/city_${city.id}.html" target="_blank">-->
                                            <#--<dl>-->
                                                <#--<dd title="${city.name}">${city.name}</dd>-->
                                                <#--&lt;#&ndash;<dt><i class="lxb_fontface"></i>881348</dt>&ndash;&gt;-->
                                            <#--</dl>-->
                                        <#--</a>-->
                                    <#--</#list>-->
                                <#--</#if>-->
                            <#--</#list>-->
                        <#--</div>-->
                        <#--<!-- paihangModule phMleft end &ndash;&gt;-->
                        <#--<!-- adModule adMleftad1 start &ndash;&gt;-->
                        <#--<div class="leftpic leftpic2">-->
                            <#--<a target="_blank" href="javascript:;"><img src="/images/index/left_pic.png" title=""></a>-->
                        <#--</div>-->
                        <#--<!-- adModule adMleftad1 end &ndash;&gt;-->
                        <#--<!-- proRecomModule prdMblack start &ndash;&gt;-->
                        <#--<#list cjdx[label] as list>-->
                            <#--<#if list_index == 1>-->
                                <#--<#list list as line>-->
                                    <#--<#if line_index == 6><#break></#if>-->
                                    <#--<div class="block_item">-->
                                        <#--<div class="pic">-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank"><img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/220/h/125/q/75"></a>-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}" class="picmask"></a>-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank" class="words">-->

                                            <#--</a>-->
                                        <#--</div>-->
                                        <#--<div class="price">-->
                                            <#--<span>¥</span>${line.price}<span>元起</span><span class="myd">满意度：<em>${line.satisfaction}%</em></span>-->
                                        <#--</div>-->
                                        <#--<div class="name">-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}">&lt;${line.name}&gt;${line.appendTitle}</a>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                <#--</#list>-->
                            <#--</#if>-->
                        <#--</#list>-->
                        <#--<!-- proRecomModule prdMblack end &ndash;&gt;-->
                        <#--<!-- txtModule txtMself start &ndash;&gt;-->
                        <#--<a href="javascript:;" target="_blank" class="more">查看更多&gt;</a>-->
                        <#--<!-- txtModule txtMself end &ndash;&gt;</div></div>-->
            <#--</#list>-->
            <#--</div>-->
        <#--</div>-->
        <#--<div id="cjcx" class="horizontal guoneizizhu clearfix">-->
            <#--<h2>出境长线</h2>-->
            <#--<ul class="clearfix mytab nav_tabs">-->
            <#--<#list cjcx?keys as label>-->
                <#--<li<#if label_index == 0> class="on"</#if>><a href="javascript:void(0)">${label}</a></li>-->
            <#--</#list>-->
            <#--</ul>-->
            <#--<div class="line-con tab_infos">-->
            <#--<#list cjcx?keys as label>-->
                <#--<div class="linebox an_mo<#if label_index == 0> now</#if>">-->
                    <#--<div class="block clearfix">-->
                        <#--<!-- paihangModule phMleft start &ndash;&gt;-->
                        <#--<div class="guanzhu_new">-->
                            <#--<p class="title">热门目的地</p>-->
                            <#--<#list cjcx[label] as list>-->
                                <#--<#if list_index == 0>-->
                                    <#--<#list list as city>-->
                                        <#--<a href="/city_${city.id}.html" target="_blank">-->
                                            <#--<dl>-->
                                                <#--<dd title="${city.name}">${city.name}</dd>-->
                                                <#--&lt;#&ndash;<dt><i class="lxb_fontface"></i>881348</dt>&ndash;&gt;-->
                                            <#--</dl>-->
                                        <#--</a>-->
                                    <#--</#list>-->
                                <#--</#if>-->
                            <#--</#list>-->
                        <#--</div>-->
                        <#--<!-- paihangModule phMleft end &ndash;&gt;-->
                        <#--<!-- adModule adMleftad1 start &ndash;&gt;-->
                        <#--<div class="leftpic leftpic2">-->
                            <#--<a target="_blank" href="javascript:;"><img src="/images/index/left_pic.png" title=""></a>-->
                        <#--</div>-->
                        <#--<!-- adModule adMleftad1 end &ndash;&gt;-->
                        <#--<!-- proRecomModule prdMblack start &ndash;&gt;-->
                        <#--<#list cjcx[label] as list>-->
                            <#--<#if list_index == 1>-->
                                <#--<#list list as line>-->
                                    <#--<#if line_index == 6><#break></#if>-->
                                    <#--<div class="block_item">-->
                                        <#--<div class="pic">-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank"><img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/220/h/125/q/75"></a>-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}" class="picmask"></a>-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank" class="words">-->

                                            <#--</a>-->
                                        <#--</div>-->
                                        <#--<div class="price">-->
                                            <#--<span>¥</span>${line.price}<span>元起</span><span class="myd">满意度：<em>${line.satisfaction}%</em></span>-->
                                        <#--</div>-->
                                        <#--<div class="name">-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}">&lt;${line.name}&gt;${line.appendTitle}</a>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                <#--</#list>-->
                            <#--</#if>-->
                        <#--</#list>-->
                        <#--<!-- proRecomModule prdMblack end &ndash;&gt;-->
                        <#--<!-- txtModule txtMself start &ndash;&gt;-->
                        <#--<a href="javascript:;" target="_blank" class="more">查看更多&gt;</a>-->
                        <#--<!-- txtModule txtMself end &ndash;&gt;</div></div>-->
            <#--</#list>-->
            <#--</div>-->
        <#--</div>-->
        <#--<div id="yltm" class="horizontal guoneizizhu clearfix">-->
            <#--<h2>游轮特卖</h2>-->
            <#--<ul class="clearfix mytab nav_tabs">-->
            <#--<#list yltm?keys as label>-->
                <#--<li<#if label_index == 0> class="on"</#if>><a href="javascript:void(0)">${label}</a></li>-->
            <#--</#list>-->
            <#--</ul>-->
            <#--<div class="line-con tab_infos">-->
            <#--<#list yltm?keys as label>-->
                <#--<div class="linebox an_mo<#if label_index == 0> now</#if>">-->
                    <#--<div class="block clearfix">-->
                        <#--<!-- paihangModule phMleft start &ndash;&gt;-->
                        <#--<div class="guanzhu_new">-->
                            <#--<p class="title">热门目的地</p>-->
                            <#--<#list yltm[label] as list>-->
                                <#--<#if list_index == 0>-->
                                    <#--<#list list as city>-->
                                        <#--<a href="/city_${city.id}.html" target="_blank">-->
                                            <#--<dl>-->
                                                <#--<dd title="${city.name}">${city.name}</dd>-->
                                                <#--&lt;#&ndash;<dt><i class="lxb_fontface"></i>881348</dt>&ndash;&gt;-->
                                            <#--</dl>-->
                                        <#--</a>-->
                                    <#--</#list>-->
                                <#--</#if>-->
                            <#--</#list>-->
                        <#--</div>-->
                        <#--<!-- paihangModule phMleft end &ndash;&gt;-->
                        <#--<!-- adModule adMleftad1 start &ndash;&gt;-->
                        <#--<div class="leftpic leftpic2">-->
                            <#--<a target="_blank" href="javascript:;"><img src="/images/index/left_pic.png" title=""></a>-->
                        <#--</div>-->
                        <#--<!-- adModule adMleftad1 end &ndash;&gt;-->
                        <#--<!-- proRecomModule prdMblack start &ndash;&gt;-->
                        <#--<#list yltm[label] as list>-->
                            <#--<#if list_index == 1>-->
                                <#--<#list list as line>-->
                                    <#--<#if line_index == 6><#break></#if>-->
                                    <#--<div class="block_item">-->
                                        <#--<div class="pic">-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank"><img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/220/h/125/q/75"></a>-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}" class="picmask"></a>-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank" class="words">-->

                                            <#--</a>-->
                                        <#--</div>-->
                                        <#--<div class="price">-->
                                            <#--<span>¥</span>${line.price}<span>元起</span><span class="myd">满意度：<em>${line.satisfaction}%</em></span>-->
                                        <#--</div>-->
                                        <#--<div class="name">-->
                                            <#--<a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}">&lt;${line.name}&gt;${line.appendTitle}</a>-->
                                        <#--</div>-->
                                    <#--</div>-->
                                <#--</#list>-->
                            <#--</#if>-->
                        <#--</#list>-->
                        <#--<!-- proRecomModule prdMblack end &ndash;&gt;-->
                        <#--<!-- txtModule txtMself start &ndash;&gt;-->
                        <#--<a href="javascript:;" target="_blank" class="more">查看更多&gt;</a>-->
                        <#--<!-- txtModule txtMself end &ndash;&gt;</div></div>-->
            <#--</#list>-->
            <#--</div>-->
        <#--</div>-->
        <div id="zjly" class="horizontal guoneizizhu clearfix">
            <h2>自驾旅游</h2>
            <ul class="clearfix mytab nav_tabs">
            <#list zjly?keys as label>
                <li<#if label_index == 0> class="on"</#if>><a href="javascript:void(0)">${label}</a></li>
            </#list>
            </ul>
            <div class="line-con tab_infos">
            <#list zjly?keys as label>
                <div class="linebox an_mo<#if label_index == 0> now</#if>">
                    <div class="block clearfix">
                        <!-- paihangModule phMleft start -->
                        <div class="guanzhu_new">
                            <p class="title">热门目的地</p>
                            <#list zjly[label] as list>
                                <#if list_index == 0>
                                    <#list list as city>
                                        <a href="/self_driver_${city.id}.html" target="_blank">
                                            <dl>
                                                <dd title="${city.name}">${city.name}</dd>
                                                <#--<dt><i class="lxb_fontface"></i>881348</dt>-->
                                            </dl>
                                        </a>
                                    </#list>
                                </#if>
                            </#list>
                        </div>
                        <!-- paihangModule phMleft end -->
                        <!-- adModule adMleftad1 start -->
                        <div class="leftpic leftpic2">
                            <a target="_blank" href="javascript:;"><img src="/images/index/left_pic1.png" title=""></a>
                        </div>
                        <!-- adModule adMleftad1 end -->
                        <!-- proRecomModule prdMblack start -->
                        <#list zjly[label] as list>
                            <#if list_index == 1>
                                <#list list as line>
                                    <#if line_index == 6><#break></#if>
                                    <div class="block_item">
                                        <div class="pic">
                                            <a href="/line_detail_${line.id}.html" target="_blank"><img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/220/h/125/q/75"></a>
                                            <a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}" class="picmask"></a>
                                            <a href="/line_detail_${line.id}.html" target="_blank" class="words">

                                            </a>
                                        </div>
                                        <div class="price">
                                            <#if line.price gt 0>
                                                <span>¥</span>${line.price}<span>元起</span>
                                            </#if>
                                            <span class="myd">满意度：<em>${line.satisfaction}%</em></span>
                                        </div>
                                        <div class="name">
                                            <a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}">&lt;${line.name}&gt;${line.appendTitle}</a>
                                        </div>
                                    </div>
                                </#list>
                            </#if>
                        </#list>
                        <!-- proRecomModule prdMblack end -->
                        <!-- txtModule txtMself start -->
                        <a href="${ZIZHU_PATH}" target="_blank" class="more">查看更多&gt;</a>
                        <!-- txtModule txtMself end --></div></div>
            </#list>
            </div>
        </div>
        <div id="zzly" class="horizontal guoneizizhu clearfix">
            <h2>自助旅游</h2>
            <ul class="clearfix mytab nav_tabs">
            <#list zzly?keys as label>
                <li<#if label_index == 0> class="on"</#if>><a href="javascript:void(0)">${label}</a></li>
            </#list>
            </ul>
            <div class="line-con tab_infos">
            <#list zzly?keys as label>
                <div class="linebox an_mo<#if label_index == 0> now</#if>">
                    <div class="block clearfix">
                        <!-- paihangModule phMleft start -->
                        <div class="guanzhu_new">
                            <p class="title">热门目的地</p>
                            <#list zzly[label] as list>
                                <#if list_index == 0>
                                    <#list list as city>
                                        <a href="/self_tour_${city.id}.html" target="_blank">
                                            <dl>
                                                <dd title="${city.name}">${city.name}</dd>
                                                <#--<dt><i class="lxb_fontface"></i>881348</dt>-->
                                            </dl>
                                        </a>
                                    </#list>
                                </#if>
                            </#list>
                        </div>
                        <!-- paihangModule phMleft end -->
                        <!-- adModule adMleftad1 start -->
                        <div class="leftpic leftpic2">
                            <a target="_blank" href="javascript:;"><img src="/images/index/left_pic2.png" title=""></a>
                        </div>
                        <!-- adModule adMleftad1 end -->
                        <!-- proRecomModule prdMblack start -->
                        <#list zzly[label] as list>
                            <#if list_index == 1>
                                <#list list as line>
                                <#if line_index == 6><#break></#if>
                                    <div class="block_item">
                                        <div class="pic">
                                            <a href="/line_detail_${line.id}.html" target="_blank"><img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${line.cover}?imageView2/1/w/220/h/125/q/75"></a>
                                            <a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}" class="picmask"></a>
                                            <a href="/line_detail_${line.id}.html" target="_blank" class="words">

                                            </a>
                                        </div>
                                        <div class="price">
                                            <#if line.price gt 0>
                                                <span>¥</span>${line.price}<span>元起</span>
                                            </#if>
                                            <span class="myd">满意度：<em>${line.satisfaction}%</em></span>
                                        </div>
                                        <div class="name">
                                            <a href="/line_detail_${line.id}.html" target="_blank" title="<${line.name}>${line.appendTitle}">&lt;${line.name}&gt;${line.appendTitle}</a>
                                        </div>
                                    </div>
                                </#list>
                            </#if>
                        </#list>
                        <!-- proRecomModule prdMblack end -->
                        <!-- txtModule txtMself start -->
                        <a href="${ZIZHU_PATH}" target="_blank" class="more">查看更多&gt;</a>
                        <!-- txtModule txtMself end --></div></div>
            </#list>
            </div>
        </div>
        <div id="jdth" class="horizontal guoneizizhu clearfix">
            <h2>酒店特惠</h2>
            <ul class="clearfix mytab nav_tabs">
            <#list jdth?keys as label>
                <li<#if label_index == 0> class="on"</#if>><a href="javascript:void(0)">${label}</a></li>
            </#list>
            </ul>
            <div class="line-con tab_infos">
            <#list jdth?keys as label>
                <div class="linebox an_mo<#if label_index == 0> now</#if>">
                    <div class="block clearfix">
                        <!-- paihangModule phMleft start -->
                        <div class="guanzhu_new">
                            <p class="title">热门目的地</p>
                            <#list jdth[label] as list>
                                <#if list_index == 0>
                                    <#list list as city>
                                        <a href="${HOTEL_PATH}/hotel_list.html?cityId=${city.id}" target="_blank">
                                            <dl>
                                                <dd title="${city.name}">${city.name}</dd>
                                                <#--<dt><i class="lxb_fontface"></i>881348</dt>-->
                                            </dl>
                                        </a>
                                    </#list>
                                </#if>
                            </#list>
                        </div>
                        <!-- paihangModule phMleft end -->
                        <!-- adModule adMleftad1 start -->
                        <div class="leftpic leftpic2">
                            <a target="_blank" href="javascript:;"><img src="/images/index/left_pic3.png" title=""></a>
                        </div>
                        <!-- adModule adMleftad1 end -->
                        <!-- proRecomModule prdMblack start -->
                        <#list jdth[label] as list>
                            <#if list_index == 1>
                                <#list list as hotel>
                                    <#if hotel_index == 6><#break></#if>
                                    <div class="block_item">
                                        <div class="pic">
                                            <a href="/hotel_detail_${hotel.id}.html" target="_blank"><img data-original="${hotel.cover}"></a>
                                            <a href="/hotel_detail_${hotel.id}.html" target="_blank" title="${hotel.name}" class="picmask"></a>
                                            <a href="/hotel_detail_${hotel.id}.html" target="_blank" class="words">

                                            </a>
                                        </div>
                                        <div class="price">
                                            <span>¥</span>${hotel.price}<span>元起</span>
                                        </div>
                                        <div class="name">
                                            <a href="${HOTEL_PATH}/hotel_detail_${hotel.id}.html" target="_blank" title="${hotel.name}">${hotel.name}</a>
                                        </div>
                                    </div>
                                </#list>
                            </#if>
                        </#list>
                        <!-- proRecomModule prdMblack end -->
                        <!-- txtModule txtMself start -->
                        <a href="${HOTEL_PATH}" target="_blank" class="more">查看更多&gt;</a>
                        <!-- txtModule txtMself end --></div></div>
            </#list>
            </div>
        </div>
        <div id="mptj" class="horizontal guoneizizhu clearfix">
            <h2>门票特价</h2>
            <ul class="clearfix mytab nav_tabs">
            <#list mptj?keys as label>
                <li<#if label_index == 0> class="on"</#if>><a href="javascript:void(0)">${label}</a></li>
            </#list>
            </ul>
            <div class="line-con tab_infos">
            <#list mptj?keys as label>
                <div class="linebox an_mo<#if label_index == 0> now</#if>">
                    <div class="block clearfix">
                        <!-- paihangModule phMleft start -->
                        <div class="guanzhu_new">
                            <p class="title">热门目的地</p>
                            <#list mptj[label] as list>
                                <#if list_index == 0>
                                    <#list list as city>
                                        <a href="${SCENIC_PATH}/scenic_list.html?cityCode=${city.id}" target="_blank">
                                            <dl>
                                                <dd title="${city.name}">${city.name}</dd>
                                                <#--<dt><i class="lxb_fontface"></i>881348</dt>-->
                                            </dl>
                                        </a>
                                    </#list>
                                </#if>
                            </#list>
                        </div>
                        <!-- paihangModule phMleft end -->
                        <!-- adModule adMleftad1 start -->
                        <div class="leftpic leftpic2">
                            <a target="_blank" href="javascript:;"><img src="/images/index/left_pic1.png" title=""></a>
                        </div>
                        <!-- adModule adMleftad1 end -->
                        <!-- proRecomModule prdMblack start -->
                        <#list mptj[label] as list>
                            <#if list_index == 1>
                                <#list list as scenic>
                                    <#if scenic_index == 6><#break></#if>
                                    <div class="block_item">
                                        <div class="pic">
                                            <a href="/scenic_detail_${scenic.id}.html" target="_blank"><img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${scenic.cover}?imageView2/1/w/220/h/125/q/75"></a>
                                            <a href="/scenic_detail_${scenic.id}.html" target="_blank" title="${scenic.name}" class="picmask"></a>
                                            <a href="/scenic_detail_${scenic.id}.html" target="_blank" class="words">

                                            </a>
                                        </div>
                                        <div class="price">
                                            <span>¥</span>${scenic.price}<span>元起</span>
                                        </div>
                                        <div class="name">
                                            <a href="${SCENIC_PATH}/scenic_detail_${scenic.id}.html" target="_blank" title="${scenic.name}">${scenic.name}</a>
                                        </div>
                                    </div>
                                </#list>
                            </#if>
                        </#list>
                        <!-- proRecomModule prdMblack end -->
                        <!-- txtModule txtMself start -->
                        <a href="${SCENIC_PATH}" target="_blank" class="more">查看更多&gt;</a>
                        <!-- txtModule txtMself end --></div></div>
            </#list>
            </div>
        </div>
        <div id="yjgl" class="square youjigonglue an_mo">
            <h2>游记攻略</h2>
            <div class="block clearfix">
                <!-- adModule adMleftad3 start -->
                <div class="leftad leftad_3">
                    <a target="_blank">
                        <img title="" src="/images/index/gonglue.png"></a>
                </div>
                <!-- adModule adMleftad3 end --><!-- poiModule poiMpaihang start -->
                <#list goodRecommendPlans as recplan>
                <#if recplan_index == 3><#break></#if>
                <div class="glitem">
                    <div class="pic">
                        <a href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html" target="_blank"><img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${recplan.coverPath}?imageView2/1/w/250/h/187/q/75"></a>
                    </div>
                    <div class="name">
                        <a href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html" target="_blank" title="${recplan.planName}">${recplan.planName}</a>
                    </div>
                    <div class="det">
                        <a href="${RECOMMENDPLAN_PATH}/guide_detail_${recplan.id}.html" target="_blank">${recplan.description}
                        </a>
                    </div>
                </div>
                </#list>
                <!-- poiModule poiMpaihang end -->
            </div>
        </div>
    </div>
</div>