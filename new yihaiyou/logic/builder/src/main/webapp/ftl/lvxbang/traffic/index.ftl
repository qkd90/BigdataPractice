<#--<div class=" w1000 cl">-->
    <div class="Yqybanner traffic_div">
        <div class="search_bg posiA">
            <ul class="mailTab fl">
                <li id="li_train">火车</li>
                <li id="li_flight">机票</li>
            </ul>


<!--火车-->
<div class="mailTablePlan disn">
    <!--tray-->
    <div class="tray  mt15">
        <ul class="iflight_rad fs14" id="trainType">
            <li class="list_tit b fs16">线路类型：</li>
            <li><i class="radio danchen2 checked" value="1" name="trainType"></i><span class="sp_input04">单程</span> </li>
            <li><i class="radio wanfan2 " value="2" name="trainType"></i><span class="sp_input04">往返</span> </li>
            <li><i class="radio lianchen2 " value="3" name="trainType"></i><span class="sp_input04">中转</span></li>
        </ul>
        <div class="onepass fake2">
            <i class="huan_bot"></i>
            <a href="javaScript:;" class="huan2">换</a>
            <form id="trainSearchForm" action="${TRAFFIC_PATH}/lvxbang/traffic/toTrain.jhtml" method="post">
                <dl class="list fl posiR zone">
                    <dt class="list_tit">出 发 站</dt>
                    <dd class="list_con">
                        <input type="hidden" name="leaveCity" value="">
                        <input type="hidden" name="leavePort" value="">
                        <input id="trainLeaveCity" type="text" value="" class="input01 input portinput" name="leaveCityName" maxlength="20"
                               data-areaId="" data-url="/lvxbang/traffic/listTrainPort.jhtml" autoComplete="Off">
                        <!--目的地 portinput input01 input-->
                        <div class="posiA Addmore categories_Addmore2">
                            <i class="close"></i>
                            <!--<div class="Addmore_d">
                                搜索历史：<span>厦门</span>
                            </div>-->
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
                    <dd>
                        <label></label>
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
                    <#list trainLetterSortAreas as letterSortArea>
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
        </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                        <!--关键字提示 portinput input input01-->
        <div class="posiA categories_div  KeywordTips">
            <ul>

            </ul>
        </div>

        <!--错误-->
        <div class="posiA categories_div cuowu textL">
            <p class="cl">抱歉未找到相关的结果！</p>
        </div><!-- #EndLibraryItem -->
        </dd>
        </dl>
        <dl class="list fr ">
            <dt class="list_tit">出发日期</dt>
            <dd class="list_con posiR zone"><i class="time_ico in_time"></i>
                <input  type="text" value="" id="hccf" class="input01 in_time" name="leaveDate" maxlength="20"
                       placeholder=""
                       readOnly="true"
                       onFocus="WdatePicker({minDate:'%y-%M-{%d}',onpicked:function(){$(this).change(),dateChange1()}})">
            </dd>
        </dl>
        <dl class="list fl posiR rdzs">
            <dt class="list_tit">到 达 站</dt>
            <dd class="list_con">
                <input type="hidden" name="arriveCity" value="" class="hideValue">
                <input type="hidden" name="arrivePort" value="" class="hideValue">
                <input type="text" value="" class="input01 input portinput" name="arriveCityName" maxlength="20" placeholder=" "
                       data-areaId="" data-url="/lvxbang/traffic/listTrainPort.jhtml" autoComplete="Off">
                <!--目的地 portinput input01 input-->
                <div class="posiA Addmore categories_Addmore2">
                    <i class="close"></i>
                    <!--<div class="Addmore_d">
                        搜索历史：<span>厦门</span>
                    </div>-->
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
            <dd>
                <label></label>
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
            <#list trainLetterSortAreas as letterSortArea>
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
    </div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                <!--关键字提示 portinput input input01-->
    <div class="posiA categories_div  KeywordTips">
        <ul>

        </ul>
    </div>

    <!--错误-->
    <div class="posiA categories_div cuowu textL">
        <p class="cl">抱歉未找到相关的结果！</p>
    </div><!-- #EndLibraryItem -->
    </dd>
    </dl>

    <dl class="list fr fan2">
        <dt class="list_tit">返程日期</dt>
        <dd class="list_con posiR"><i class="time_ico in_time"></i>
            <input type="text" value="" id="hcfc" class="input01 in_time" name="returnDate" maxlength="20"
                   placeholder=""
                   readOnly="true"
                   onFocus="WdatePicker({minDate:'#F{$dp.$D(\'hccf\')}',onpicked:function(){$(this).change()}})">
            <!-- #BeginLibraryItem "/lbi/time.lbi" -->
        </dd>
    </dl>

    <dl class="list fr transit posiR">
        <dt class="list_tit">中 转 站</dt>
        <dd class="list_con">
            <input type="hidden" name="transitCity" value="" class="hideValue">
            <input type="hidden" name="transitPort" value="" class="hideValue">
            <input type="text" value="" class="input01 input portinput" name="transitCityName" maxlength="20" placeholder=" "
                   data-areaId="" data-url="/lvxbang/traffic/listTrainPort.jhtml" autoComplete="Off">
            <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
            <!--目的地 portinput input01 input-->
            <div class="posiA Addmore categories_Addmore2">
                <i class="close"></i>
                <!--<div class="Addmore_d">
                    搜索历史：<span>厦门</span>
                </div>-->
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
        <dd>
            <label></label>
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
        <#list trainLetterSortAreas as letterSortArea>
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
</div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/lbi/categories.lbi" -->
            <!--关键字提示 portinput input input01-->
<div class="posiA categories_div  KeywordTips">
    <ul>

    </ul>
</div>

<!--错误-->
<div class="posiA categories_div cuowu textL">
    <p class="cl">抱歉未找到相关的结果！</p>
</div><!-- #EndLibraryItem -->
</dd>
</dl>
</form>

<p class="tray_p cl" style="bottom:16px;"><input type="button" class="submitSearch ff_yh fr toSearch" value="开始搜索"
                            onclick="SearcherBtn.btnTrainSeach()"/></p>
</div>
</div>
<!--tray end-->
</div>
<!--火车 end-->

            <!--机票-->
            <div class="mailTablePlan">
                <#--<div style="margin-top: 70px;width: 100%;text-align: center;font-size: 45px;color: #999;">-->
                    <#--<span>即将上线</span>-->
                <#--</div>-->
                <!--tray-->
                <div class="tray mt15">
                    <ul class="iflight_rad fs14" id="flightType">
                        <li class="list_tit b fs16">航程类型：</li>
                        <li><i class="radio danchen checked" value="1" name="flightType"></i><span
                                class="sp_input04">单程</span></li>
                        <li><i class="radio wanfan " value="2" name="flightType"></i><span class="sp_input04">往返</span>
                        </li>
                        <li><i class="radio lianchen " value="3" name="flightType"></i><span
                                class="sp_input04">联程</span></li>
                    </ul>
                    <div class="onepass fake2">
                        <i class="huan_bot"></i>
                        <a href="javaScript:;" class="huan">换</a>
                        <form id="flightSearchForm" action="${TRAFFIC_PATH}/lvxbang/traffic/toFlight.jhtml"
                              method="post">
                            <dl class="list fl posiR zone">
                                <dt class="list_tit">出发城市</dt>
                                <dd class="list_con">
                                    <input type="hidden" name="leaveCity" value="">
                                    <input type="hidden" name="leavePort" value="">
                                    <input id="flightLeaveCity" type="text" value=""
                                           class="input input01 Departure portinput"
                                           name="leaveCityName" maxlength="20" data-areaId=""
                                           data-url="/lvxbang/traffic/listFlightPort.jhtml" autoComplete="Off">
                                    <!--目的地 portinput input01 input-->
                                    <div class="posiA Addmore categories_Addmore2">
                                        <i class="close"></i>
                                        <!--<div class="Addmore_d">
                                            搜索历史：<span>厦门</span>
                                        </div>-->
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
                                            <dd>
                                                <label></label>
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
                                        <#list flightLetterSortAreas as letterSortArea>
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
                                    </div><!-- #EndLibraryItem &ndash;&gt;<!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                                    <!--关键字提示 portinput input input01-->
                                    <div class="posiA categories_div  KeywordTips">
                                        <ul>

                                        </ul>
                                    </div>

                                    <!--错误-->
                                    <div class="posiA categories_div cuowu textL">
                                        <p class="cl">抱歉未找到相关的结果！</p>
                                    </div><!-- #EndLibraryItem -->
                                </dd>
                            </dl>
                            <dl class="list fr outset">
                                <dt class="list_tit">出发日期</dt>
                                <dd class="list_con posiR zone"><i class="time_ico in_time"></i>
                                    <input  type="text" value="" id="fjcf" class="input01 in_time"
                                           name="leaveDate"
                                           maxlength="20"
                                           placeholder=""
                                           readOnly="true"
                                           onFocus="WdatePicker({minDate:'%y-%M-{%d}',onpicked:function(){$(this).change(),dateChange2()}})">
                                </dd>
                            </dl>
                            <dl class="list fr zzrq">
                                <dt class="list_tit">第一程日期</dt>
                                <dd class="list_con posiR"><i class="time_ico in_time"></i>
                                    <input type="text" value="" id="fjd1" class="input01 in_time" name="leaveDate1"
                                           maxlength="20"
                                           placeholder=""
                                           readOnly="true"
                                           onFocus="WdatePicker({minDate:'%y-%M-{%d}',onpicked:function(){$(this).change(),dateChange3()}})">
                                </dd>
                            </dl>
                            <dl class="list fl zzcs posiR">
                                <dt class="list_tit">中转城市</dt>
                                <dd class="list_con">
                                    <input type="hidden" name="transitCity" value="">
                                    <input type="hidden" name="transitPort" value="">
                                    <input type="text" value="" class="input input01 portinput" name="transitCityName"
                                           maxlength="20" data-areaId=""
                                           data-url="/lvxbang/traffic/listFlightPort.jhtml"
                                           autoComplete="Off">
                                    <!--目的地 portinput input01 input-->
                                    <div class="posiA Addmore categories_Addmore2">
                                        <i class="close"></i>
                                        <!--<div class="Addmore_d">
                                            搜索历史：<span>厦门</span>
                                        </div>-->
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
                                            <dd>
                                                <label></label>
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
                                        <#list flightLetterSortAreas as letterSortArea>
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
                                    </div><!-- #EndLibraryItem &ndash;&gt;<!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                                    <!--关键字提示 portinput input input01-->
                                    <div class="posiA categories_div  KeywordTips">
                                        <ul>

                                        </ul>
                                    </div>

                                    <!--错误-->
                                    <div class="posiA categories_div cuowu textL">
                                        <p class="cl">抱歉未找到相关的结果！</p>
                                    </div><!-- #EndLibraryItem -->
                                </dd>
                            </dl>
                            <dl class="list fl csrq posiR">
                                <dt class="list_tit">到达城市</dt>
                                <dd class="list_con">
                                    <input type="hidden" name="arriveCity" value="">
                                    <input type="hidden" name="arrivePort" value="">
                                    <input type="text" value="" class="input input01 Reach portinput" placeholder=" "
                                           name="arriveCityName"
                                           maxlength="20" data-areaId=""
                                           data-url="/lvxbang/traffic/listFlightPort.jhtml"
                                           autoComplete="Off">
                                    <!--目的地 portinput input01 input-->
                                    <div class="posiA Addmore categories_Addmore2">
                                        <i class="close"></i>
                                        <!--<div class="Addmore_d">
                                            搜索历史：<span>厦门</span>
                                        </div>-->
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
                                            <dd>
                                                <label></label>
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
                                        <#list flightLetterSortAreas as letterSortArea>
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
                                    </div><!-- #EndLibraryItem &ndash;&gt;<!-- #BeginLibraryItem "/lbi/categories.lbi" -->
                                    <!--关键字提示 portinput input input01-->
                                    <div class="posiA categories_div  KeywordTips">
                                        <ul>

                                        </ul>
                                    </div>

                                    <!--错误-->
                                    <div class="posiA categories_div cuowu textL">
                                        <p class="cl">抱歉未找到相关的结果！</p>
                                    </div><!-- #EndLibraryItem -->
                                </dd>
                            </dl>
                            <dl class="list fr fan">
                                <dt class="list_tit">返程日期</dt>
                                <dd class="list_con posiR"><i class="time_ico in_time"></i>
                                    <input type="text" value="" id="fjfc" class="input01 in_time" name="returnDate"
                                           maxlength="20"
                                           placeholder=""
                                           readOnly="true"
                                           onFocus="WdatePicker({minDate:'#F{$dp.$D(\'fjcf\')}',onpicked:function(){$(this).change()}})">
                                </dd>
                            </dl>
                            <dl class="list fr zzrq2">
                                <dt class="list_tit">第二程日期</dt>
                                <dd class="list_con posiR"><i class="time_ico in_time"></i>
                                    <input type="text" value="" id="fjd2" class="input01 in_time" name="leaveDate2"
                                           maxlength="20"
                                           placeholder="" id="fjd2"
                                           readOnly="true"
                                           onFocus="WdatePicker({minDate:'#F{$dp.$D(\'fjd1\')}',onpicked:function(){$(this).change()}})">
                                </dd>
                            </dl>
                            <dl class="list fl ddcs posiR">
                                <dt class="list_tit">到达城市</dt>
                                <dd class="list_con">
                                    <input type="hidden" name="arriveCity2" value="">
                                    <input type="hidden" name="arrivePort2" value="">
                                    <input type="text" value="" class="input input01 Reach portinput" placeholder=" "
                                           name="arriveCityName2"
                                           maxlength="20" data-areaId="" data-url="/lvxbang/traffic/listTrainPort.jhtml"
                                           autoComplete="Off">
                                    <!--目的地 portinput input01 input-->
                                    <div class="posiA Addmore categories_Addmore2">
                                        <i class="close"></i>
                                        <!--<div class="Addmore_d">
                                            搜索历史：<span>厦门</span>
                                        </div>-->
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
                                            <dd>
                                                <label></label>
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
                                        <#list flightLetterSortAreas as letterSortArea>
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
                                    <!--关键字提示 portinput input input01-->
                                    <div class="posiA categories_div  KeywordTips">
                                        <ul>

                                        </ul>
                                    </div>

                                    <!--错误-->
                                    <div class="posiA categories_div cuowu textL">
                                        <p class="cl">抱歉未找到相关的结果！</p>
                                    </div><!-- #EndLibraryItem -->
                                </dd>
                            </dl>
                        </form>
                        <p class="tray_p cl" style="bottom:16px;"><input type="button" class="submitSearch ff_yh fr toSearch"
                                                                         value="开始搜索"
                                                                         onclick="SearcherBtn.btnFlightSeach()"/></p>
                    </div>
                <#--</div>-->
                    <!--tray end-->
                </div>
            </div>
            <!--机票 end-->


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
                <a class="cur" stat="item1001" href="javascript:;"></a><a href="javascript:;" stat="item1002"></a>
            </div>
        </div>

    <#--<jx:include fileAttr="${LVXBANG_TRAFFIC_BANNER}"></jx:include>-->
    </div>
</div>

<div class="main cl">
    <div class="w1000">
        <!--热门行程-->
        <h2 class="title">热门旅游线路</h2>

        <div class="Popular main_div ff_yh">
            <ul>
                <li class="w630 fl">
                <#list recommendPlans as plan>
                    <#if (plan_index<3)>
                        <div class="<#if plan_index==0>posiR mb10 w630_d</#if><#if plan_index==1>posiR w308_d fl</#if><#if plan_index==2>posiR w308_d fr</#if>">
                            <a href="${RECOMMENDPLAN_PATH}/guide_detail_${plan.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${plan.coverPath}" alt="${plan.planName}"/>
                                <p class="posiA">
                                    <label>${plan.city.name}${plan.days}日游</label>
                              <span>${plan.days}日/${plan.quoteNum}人引用<br/>
                              ${plan.user.userName}/${plan.planName}</span>
                                </p>
                                <span class="title posiA">${plan.city.name}${plan.days}日游</span>
                            </a>
                        </div>
                    </#if>
                </#list>
                </li>
                <li class="w355 fr">
                <#list recommendPlans as plan>
                    <#if (plan_index>=3 && plan_index<5 )>
                        <div class="<#if plan_index==3>posiR mb10 w355_d</#if><#if plan_index==4>posiR w355_d</#if>">
                            <a href="${RECOMMENDPLAN_PATH}/guide_detail_${plan.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${plan.coverPath}" alt="${plan.planName}"/>

                                <p class="posiA">
                                    <label>${plan.city.name}${plan.days}日游</label>
                              <span>${plan.days}日/${plan.quoteNum}人引用<br/>
                              ${plan.user.userName}/${plan.planName}</span>
                                </p>
                                <span class="title posiA">${plan.city.name}${plan.days}日游</span>
                            </a>
                        </div>
                    </#if>
                </#list>
                </li>
            </ul>
            <p class="cl h20"></p>
        </div>

        <!--发现好游记-->
        <h2 class="title">自助游/发现好游记</h2>
        <div class="find main_div ff_yh">
            <ul>
            <#if (goodRecommendPlans?size>0)>
                <li>
                    <#list goodRecommendPlans as goodPlan>
                        <#if goodPlan_index==0>
                            <div class="posiR  fl w630_d"><a
                                    href="${RECOMMENDPLAN_PATH}/guide_detail_${goodPlan.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${goodPlan.coverPath}" alt="${goodPlan.planName}"/>
                                <p class="posiA">
                                    <label>${goodPlan.city.name}${goodPlan.days}日游</label>
	                          <span>${goodPlan.days}日/${goodPlan.quoteNum}人引用<br/>
                              ${goodPlan.user.userName}/${goodPlan.planName}</span>
                                </p>
                                <span class="title posiA">${goodPlan.city.name}${goodPlan.days}日游</span>
                            </a>
                            </div>
                        </#if>
                    </#list>
                    <#list goodRecommendPlans as goodPlan>
                        <#if goodPlan_index==1>
                            <div class="posiR w355_d fr"><a
                                    href="${RECOMMENDPLAN_PATH}/guide_detail_${goodPlan.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${goodPlan.coverPath}" alt="${goodPlan.planName}"/>
                                <p class="posiA">
                                    <label>${goodPlan.city.name}${goodPlan.days}日游</label>
	                          <span>${goodPlan.days}日/${goodPlan.quoteNum}人引用<br/>
                              ${goodPlan.user.userName}/${goodPlan.planName}</span>
                                </p>
                                <span class="title posiA">${goodPlan.city.name}${goodPlan.days}日游</span>
                            </a>
                            </div>
                        </#if>
                    </#list>
                </li>
            </#if>
            <#if (goodRecommendPlans?size>2)>
                <li>
                    <#list goodRecommendPlans as goodPlan>
                        <#if goodPlan_index==2>
                            <div class="posiR w355_d fl"><a
                                    href="${RECOMMENDPLAN_PATH}/guide_detail_${goodPlan.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${goodPlan.coverPath}" alt="${goodPlan.planName}"/>
                                <p class="posiA">
                                    <label>${goodPlan.city.name}${goodPlan.days}日游</label>
	                          <span>${goodPlan.days}日/${goodPlan.quoteNum}人引用<br/>
                              ${goodPlan.user.userName}/${goodPlan.planName}</span>
                                </p>
                                <span class="title posiA">${goodPlan.city.name}${goodPlan.days}日游</span></a>
                            </div>
                        </#if>
                    </#list>
                    <#list goodRecommendPlans as goodPlan>
                        <#if goodPlan_index==3>
                            <div class="posiR w630_d fr"><a
                                    href="${RECOMMENDPLAN_PATH}/guide_detail_${goodPlan.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${goodPlan.coverPath}" alt="${goodPlan.planName}"/>
                                <p class="posiA">
                                    <label>${goodPlan.city.name}${goodPlan.days}日游</label>
	                          <span>${goodPlan.days}日/${goodPlan.quoteNum}人引用<br/>
                              ${goodPlan.user.userName}/${goodPlan.planName}</span>
                                </p>
                                <span class="title posiA">${goodPlan.city.name}${goodPlan.days}日游</span></a>
                            </div>
                        </#if>
                    </#list>
                </li>
            </#if>
            </ul>
            <p class="cl h20"></p>
        </div>

        <!--旅行目的地-->
        <h2 class="title">自助游/旅行目的地</h2>

        <div class="travel main_div ff_yh">
            <ul>
                <li class="fl w660">
                <#list destinations as destination>
                    <#if destination_index<4>
                        <div class="posiR <#if destination_index%2=0 >fl<#else >fr</#if> w323_d mb15">
                            <a href="/city_${destination.id}.html" target="_blank">
                                <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/${destination.tbAreaExtend.cover}?imageView2/1/w/323/h/267/q/75" alt="${destination.name}"/>

                                <p class="posiA">
                                    <label>${destination.name}</label>
                                    <span class="textL disB">
                                        <#if destination.tbAreaExtend>
                                        <#if destination.tbAreaExtend.abs?length gt 118>
                                        ${destination.tbAreaExtend.abs?substring(0,118)}...
                                        <#else>
                                        ${destination.tbAreaExtend.abs}
                                        </#if>
                                    </#if></span>
                                </p>
                                <span class="title posiA">${destination.name}</span>
                            </a>
                        </div>
                    </#if>
                </#list>

                </li>
                <li class="fr w323">
                <#list destinations as destination>
                    <#if (destination_index>=4) && (destination_index < 7)>
                        <div class="posiR  fr w323_d mb15">
                            <a href="/city_${destination.id}.html" target="_blank">
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
                                    </#if></span>
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
        <p class="cl h30"></p>
    </div>
</div>