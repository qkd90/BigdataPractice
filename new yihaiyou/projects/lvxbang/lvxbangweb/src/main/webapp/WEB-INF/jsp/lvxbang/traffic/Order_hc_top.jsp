<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="Order_hc_top">
    <div class="w1225">
        <p class="n_title">
            您在这里：&nbsp;
            <a href="${TRAFFIC_PATH}">交通</a>&gt;
            <%--&nbsp;&gt;&nbsp;火车票&nbsp;&gt;&nbsp;--%>
            ${leaveCityName}到${arriveCityName}火车票</p>
        <div class="o_select cl">
            <form id="searchForm-1" action="toTrain.jhtml" method="post" class="trainForm">
                <div class="o_select_fl fl">
                    <div class="categories o_select_input order_cat posiR fl w1">
                        <i class="ico3"></i>
                        <input type="text" placeholder="单程" value="" class="input" readonly="readonly"
                               style="color:#333333">
                        <div class="posiA categories_div">
                            <ul>
                                <li><a href="javaScript:;" onclick="changeModule(1)">单程</a></li>
                                <li><a href="javaScript:;" onclick="changeModule(2)">往返</a></li>
                                <li><a href="javaScript:;" onclick="changeModule(3)">联程</a></li>
                            </ul>
                        </div>
                    </div>


                    <div class="departure o_select_input fl posiR">

                        <input type="hidden" id="leaveCity-1" name="leaveCity"
                               value="${leaveCity}" class="hideValue">
                        <input type="hidden" id="leavePort-1" name="leavePort"
                               value="${leavePort}" class="hideValue">
                        <input type="text" name="leaveCityName" placeholder="出发城市" value="${leaveCityName}"
                               <%--autocomplete="off"--%>
                               class="input portinput" data-areaId="${leaveCity}" data-portId="${leavePort}"
                               data-url="/lvxbang/traffic/listTrainPort.jhtml" id="leaveCityName-1">

                        <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                        <!--目的地 portinput input01 input-->
                        <div class="posiA Addmore categories_Addmore2">
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
                                <dd>
                                    <label></label>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <c:forEach items="${hotDestinations}" var="aArea">
                                                <li data-id="${aArea.id}">
                                                    <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </dd>
                                <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                    <dd class="disn">
                                        <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                            <label>${lrArea.name}</label>
                                            <div class="Addmore_nr">
                                                <ul>
                                                    <c:forEach items="${lrArea.list}" var="aArea">
                                                        <li data-id="${aArea.id}">
                                                            <a href="javaScript:;"
                                                               title="${aArea.name}">${aArea.name}</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:forEach>
                                    </dd>
                                </c:forEach>
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
                    </div>
                    <i class="huan fl"></i>

                    <div class="departure o_select_input fl posiR">

                        <input type="hidden" id="arriveCity-1" name="arriveCity"
                               value="${arriveCity}" class="hideValue">
                        <input type="hidden" id="arrivePort-1" name="arrivePort"
                               value="${arrivePort}" class="hideValue">
                        <input type="text" name="arriveCityName" placeholder="到达城市" value="${arriveCityName}"
                               autocomplete="off"
                               class="input portinput" data-areaId="${arriveCity}" data-portId="${arrivePort}"
                               data-url="/lvxbang/traffic/listTrainPort.jhtml" id="arriveCityName-1">

                        <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                        <!--目的地 portinput input01 input-->
                        <div class="posiA Addmore categories_Addmore2">
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
                                <dd>
                                    <label></label>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <c:forEach items="${hotDestinations}" var="aArea">
                                                <li data-id="${aArea.id}">
                                                    <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </dd>
                                <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                    <dd class="disn">
                                        <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                            <label>${lrArea.name}</label>
                                            <div class="Addmore_nr">
                                                <ul>
                                                    <c:forEach items="${lrArea.list}" var="aArea">
                                                        <li data-id="${aArea.id}">
                                                            <a href="javaScript:;"
                                                               title="${aArea.name}">${aArea.name}</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:forEach>
                                    </dd>
                                </c:forEach>
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
                    </div>
                    <div class="time o_select_input fl" style="margin-left: 26px;"><i class="time_ico in_time"></i>
                        <input type="text" placeholder="出发时间" name="leaveDate" id="leaveDate-1" value="${leaveDate}"
                               readOnly="true" onFocus="WdatePicker({minDate:'%y-%M-{%d}'})"
                               class="input">
                    </div>
                    <div class="time o_select_input fl fan fan-background-color" style="background-color: #cfd4de"><i
                            class="time_ico in_time"></i>
                        <input type="text" placeholder="返回时间" name="returnDate" value=""
                               class="input fan-background-color" readonly="true" style="background-color: #cfd4de">
                    </div>
                </div>
                <input class="submitSearch but fr" type="button" value="重新搜索" onclick="submitTrafficForm(1)"/>
            </form>
            <form id="searchForm-2" action="toTrain.jhtml" method="post" class="trainForm display-none">
                <div class="o_select_fl fl">
                    <div class="categories o_select_input order_cat posiR fl w1">
                        <i class="ico3"></i>
                        <input type="text" placeholder="往返" value="" class="input" readonly="readonly"
                               style="color:#333333">
                        <div class="posiA categories_div">
                            <ul>
                                <li><a href="javaScript:;" onclick="changeModule(1)">单程</a></li>
                                <li><a href="javaScript:;" onclick="changeModule(2)">往返</a></li>
                                <li><a href="javaScript:;" onclick="changeModule(3)">联程</a></li>
                            </ul>
                        </div>
                    </div>

                    <div class="departure o_select_input fl posiR">

                        <input type="hidden" id="leaveCity-2" name="leaveCity"
                               value="${leaveCity}" class="hideValue">
                        <input type="hidden" id="leavePort-2" name="leavePort"
                               value="${leavePort}" class="hideValue">
                        <input type="text" name="leaveCityName" placeholder="出发城市" value="${leaveCityName}"
                               autocomplete="off"
                               class="input portinput" data-areaId="${leaveCity}" data-portId="${leavePort}"
                               data-url="/lvxbang/traffic/listTrainPort.jhtml" id="leaveCityName-2">

                        <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                        <!--目的地 portinput input01 input-->
                        <div class="posiA Addmore categories_Addmore2">
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
                                <dd>
                                    <label></label>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <c:forEach items="${hotDestinations}" var="aArea">
                                                <li data-id="${aArea.id}">
                                                    <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </dd>
                                <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                    <dd class="disn">
                                        <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                            <label>${lrArea.name}</label>
                                            <div class="Addmore_nr">
                                                <ul>
                                                    <c:forEach items="${lrArea.list}" var="aArea">
                                                        <li data-id="${aArea.id}">
                                                            <a href="javaScript:;"
                                                               title="${aArea.name}">${aArea.name}</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:forEach>
                                    </dd>
                                </c:forEach>
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
                    </div>
                    <i class="huan fl"></i>

                    <div class="departure o_select_input fl posiR">

                        <input type="hidden" id="arriveCity-2" name="arriveCity"
                               value="${arriveCity}" class="hideValue">
                        <input type="hidden" id="arrivePort-2" name="arrivePort"
                               value="${arrivePort}" class="hideValue">
                        <input type="text" name="arriveCityName" placeholder="到达城市" value="${arriveCityName}"
                               class="input portinput" data-areaId="${arriveCity}" data-portId="${arrivePort}"
                               data-url="/lvxbang/traffic/listTrainPort.jhtml" id="arriveCityName-2">

                        <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                        <!--目的地 portinput input01 input-->
                        <div class="posiA Addmore categories_Addmore2">
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
                                <dd>
                                    <label></label>
                                    <div class="Addmore_nr">
                                        <ul>
                                            <c:forEach items="${hotDestinations}" var="aArea">
                                                <li data-id="${aArea.id}">
                                                    <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </dd>
                                <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                    <dd class="disn">
                                        <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                            <label>${lrArea.name}</label>
                                            <div class="Addmore_nr">
                                                <ul>
                                                    <c:forEach items="${lrArea.list}" var="aArea">
                                                        <li data-id="${aArea.id}">
                                                            <a href="javaScript:;"
                                                               title="${aArea.name}">${aArea.name}</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:forEach>
                                    </dd>
                                </c:forEach>
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
                    </div>
                    <div class="time o_select_input fl" style="margin-left: 26px;"><i class="time_ico in_time"></i>
                        <input type="text" placeholder="出发时间" name="leaveDate" id="leaveDate-2" value="${leaveDate}"
                               readOnly="true"
                               onFocus="WdatePicker({minDate:'%y-%M-{%d}', maxDate:'#F{$dp.$D(\'returnDate-2\')}'})"
                               class="input">
                    </div>
                    <div class="time o_select_input fl"><i class="time_ico in_time"></i>
                        <input type="text" placeholder="返回时间" name="returnDate" id="returnDate-2"
                               value="${returnDate}"
                               readOnly="true" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'leaveDate-2\')}'})"
                               class="input">
                    </div>
                </div>
                <input class="submitSearch but fr" value="重新搜索" onclick="submitTrafficForm(2)"/>
            </form>
            <form id="searchForm-3" action="toTrain.jhtml" method="post" class="trainForm display-none">
                <div class="o_select_fl fl">
                    <div class="categories o_select_input order_cat posiR fl w1">
                        <i class="ico3"></i>
                        <input type="text" placeholder="联程" value="" class="input" readonly="readonly"
                               style="color:#333333">
                        <div class="posiA categories_div">
                            <ul>
                                <li><a href="javaScript:;" onclick="changeModule(1)">单程</a></li>
                                <li><a href="javaScript:;" onclick="changeModule(2)">往返</a></li>
                                <li><a href="javaScript:;" onclick="changeModule(3)">联程</a></li>
                            </ul>
                        </div>
                    </div>

                    <div class="departure o_select_input fl posiR">

                        <input type="hidden" id="leaveCity-3" name="leaveCity"
                               value="${leaveCity}" class="hideValue">
                        <input type="hidden" id="leavePort-3" name="leavePort"
                               value="${leavePort}" class="hideValue">
                        <input type="text" name="leaveCityName" placeholder="出发城市" value="${leaveCityName}"
                               autocomplete="off"
                               class="input portinput" data-areaId="${leaveCity}" data-portId="${leavePort}"
                               data-url="/lvxbang/traffic/listTrainPort.jhtml" id="leaveCityName-3">

                        <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                        <!--目的地 portinput input01 input-->
                        <div class="posiA Addmore categories_Addmore2">
                            <i class="close"></i>
                            <%--<div class="Addmore_d">--%>
                            <%--搜索历史：<span>厦门</span>--%>
                            <%--</div>--%>
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
                                            <c:forEach items="${hotDestinations}" var="aArea">
                                                <li data-id="${aArea.id}">
                                                    <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </dd>
                                <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                    <dd class="disn">
                                        <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                            <label>${lrArea.name}</label>
                                            <div class="Addmore_nr">
                                                <ul>
                                                    <c:forEach items="${lrArea.list}" var="aArea">
                                                        <li data-id="${aArea.id}">
                                                            <a href="javaScript:;"
                                                               title="${aArea.name}">${aArea.name}</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:forEach>
                                    </dd>
                                </c:forEach>
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
                    </div>
                    <i class="huan fl"></i>

                    <div class="departure o_select_input fl posiR">

                        <input type="hidden" id="arriveCity-3" name="arriveCity"
                               value="${arriveCity}" class="hideValue">
                        <input type="hidden" id="arrivePort-3" name="arrivePort"
                               value="${arrivePort}" class="hideValue">
                        <input type="text" name="arriveCityName" placeholder="到达城市" value="${arriveCityName}"
                               class="input portinput" data-areaId="${arriveCity}" data-portId="${arrivePort}"
                               data-url="/lvxbang/traffic/listTrainPort.jhtml" id="arriveCityName-3">

                        <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                        <!--目的地 portinput input01 input-->
                        <div class="posiA Addmore categories_Addmore2">
                            <i class="close"></i>
                            <%--<div class="Addmore_d">--%>
                            <%--搜索历史：<span>厦门</span>--%>
                            <%--</div>--%>
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
                                            <c:forEach items="${hotDestinations}" var="aArea">
                                                <li data-id="${aArea.id}">
                                                    <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </dd>
                                <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                    <dd class="disn">
                                        <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                            <label>${lrArea.name}</label>
                                            <div class="Addmore_nr">
                                                <ul>
                                                    <c:forEach items="${lrArea.list}" var="aArea">
                                                        <li data-id="${aArea.id}">
                                                            <a href="javaScript:;"
                                                               title="${aArea.name}">${aArea.name}</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:forEach>
                                    </dd>
                                </c:forEach>
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
                    </div>
                    <div class="time o_select_input fl" style="margin-left: 26px;"><i class="time_ico in_time"></i>
                        <input type="text" placeholder="出发时间" name="leaveDate" id="leaveDate-3" value="${leaveDate}"
                               readOnly="true" onFocus="WdatePicker({minDate:'%y-%M-{%d}'})"
                               class="input">
                    </div>

                    <input type="hidden" name="returnDate" id="returnDate-3"
                           value="${leaveDate}"
                           readOnly="true" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'leaveDate-3\')}'})"
                           class="input">
                    <%--<div class="arrivals o_select_input fl select_area"--%>
                    <%--data-url="/lvxbang/destination/getTrainAreaList.jhtml">--%>
                    <%--<input type="text" name="arriveCityName" id="transitCityName" placeholder="中转城市"--%>
                    <%--value="${transitCityName}" class="input displayName">--%>
                    <%--<input type="hidden" id="transitCity" name="transitCity"--%>
                    <%--value="${transitCity}" class="hideValue">--%>
                    <%--<div class="list_citys_div" style="position: absolute;">--%>
                    <%--<ul></ul>--%>
                    <%--</div>--%>
                    <%--</div>--%>

                    <div class="departure o_select_input fl posiR">

                        <input type="hidden" id="transitCity" name="transitCity"
                               value="${transitCity}" class="hideValue">
                        <input type="hidden" id="transitPort" name="transitPort"
                               value="${transitPort}" class="hideValue">
                        <input type="text" name="transitCityName" placeholder="中转城市" value="${transitCityName}"
                               class="input portinput" data-areaId="${transitCity}"
                               data-url="/lvxbang/traffic/listTrainPort.jhtml" id="transitCityName">

                        <!-- #BeginLibraryItem "/lbi/destination.lbi" -->
                        <!--目的地 portinput input01 input-->
                        <div class="posiA Addmore categories_Addmore2">
                            <i class="close"></i>
                            <%--<div class="Addmore_d">--%>
                            <%--搜索历史：<span>厦门</span>--%>
                            <%--</div>--%>
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
                                            <c:forEach items="${hotDestinations}" var="aArea">
                                                <li data-id="${aArea.id}">
                                                    <a href="javaScript:;" title="${aArea.name}">${aArea.name}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </dd>
                                <c:forEach items="${trafficLetterSortAreas}" var="letterSortArea">
                                    <dd class="disn">
                                        <c:forEach items="${letterSortArea.letterRange}" var="lrArea">
                                            <label>${lrArea.name}</label>
                                            <div class="Addmore_nr">
                                                <ul>
                                                    <c:forEach items="${lrArea.list}" var="aArea">
                                                        <li data-id="${aArea.id}">
                                                            <a href="javaScript:;"
                                                               title="${aArea.name}">${aArea.name}</a>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:forEach>
                                    </dd>
                                </c:forEach>
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
                    </div>
                </div>
                <input id="flightSearch" class="submitSearch but fr" value="重新搜索" onclick="submitTrafficForm(3)"/>
            </form>
        </div>
        <p class="cl"></p>
    </div>
</div>