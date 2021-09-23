<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <c:choose>
        <c:when test="${recommendPlan.id > 0}">
            <title>编辑我的游记</title>
        </c:when>
        <c:otherwise>
            <title>新建游记</title>
        </c:otherwise>
    </c:choose>
    <meta name="keywords" content="编辑游记,新建游记,旅行帮"/>
    <meta name="description" content="编辑游记,新建游记,旅行帮"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/detail.css" rel="stylesheet" type="text/css">
    <link href="/js/lib/umeditor/themes/default/css/umeditor.css" rel="stylesheet" type="text/css">
</head>
<body myname="Upload" class="Upload write_recommendPlan"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--head-->
<%@include file="../common/header.jsp" %>
<!--head  end-->
<!-- #EndLibraryItem -->
<%@include file="../common/upload.jsp"%>
<form id="recommend_plan_form" action="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/doEdit.jhtml" method="post" enctype="multipart/form-data">
    <div class="main cl pt10">
        <div class="w1000">
            <!--n_title-->
            <p class="n_title h40 lh40">
                <%--<a href="/lvxbang/index/index.jhtml">旅行帮</a>--%>
                您在这里：&nbsp;<a target="_blank" href="${INDEX_PATH}/lvxbang/user/plan.jhtml">个人中心</a>
                &nbsp;&gt;&nbsp;<a target="_blank" href="${INDEX_PATH}/lvxbang/user/recplan.jhtml">我的游记</a>
                <c:choose>
                    <c:when test="${recommendPlan.id > 0}">
                        &nbsp;&gt;&nbsp;编辑我的游记&nbsp;&gt;&nbsp;${recommendPlan.planName}
                    </c:when>
                    <c:otherwise>
                        &nbsp;&gt;&nbsp;新建游记
                    </c:otherwise>
                </c:choose>
            </p>
            <!--detail_top-->
            <div class="detail_top posiR">
                <p class="img fl">
                    <c:choose>
                        <c:when test="${CURRENT_LOGIN_USER_MEMBER.head != null && CURRENT_LOGIN_USER_MEMBER.head != ''}">
                            <c:choose>
                                <c:when test="${fn: startsWith(CURRENT_LOGIN_USER_MEMBER.head, 'http')}">
                                    <img src="${CURRENT_LOGIN_USER_MEMBER.head}"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/${CURRENT_LOGIN_USER_MEMBER.head}"/>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <img src="/images/toux.PNG" id="change_avatar" style="width: 100%; height: 100%"/>
                        </c:otherwise>
                    </c:choose>
                </p>

                <div class="fl ml10 nr">
                    <b class="name">${CURRENT_LOGIN_USER_MEMBER.nickName}</b>
                </div>
            </div>

            <!--SetTravels-->
            <div class="SetTravels">
                <!--更改游记封面-->
                <div id="coverUpload" class="iupc_ico">
                    <a class="a_cover" href="javaScript:;" />
                    <i class="mr10 fl"></i><p class="fl">更改封面</p></a>
                </div>
                <div style="display: none" class="shangchuan textC fs20 ff_yh cl">
                    <span id="cover_up_info">正在上传封面</span>
                    <p class="m0auto oval4">
                        <span id="cover_progress" style="width:0%;"></span>
                    </p>
                </div>
                <div class="SetTravels_c">
                    <p class="SetTravels_c_p bg">
                        <input type="text" id="planName" name="recommendPlan.planName" value="${recommendPlan.planName}"
                               placeholder="来个响亮的游记标题!" class="input bg"></p>
                    <input type="hidden" id="coverPath" name="recommendPlan.coverPath"
                           value="${recommendPlan.coverPath}">
                    <ul class="SetTravels_ul cl">
                        <li class="w1 bg">
                            <input type="text" id="description" name="recommendPlan.description"
                                   value="${recommendPlan.description}" placeholder="一句话描述这次旅行的心情" class="input bg">
                        </li>
                        <li class="bg disn">
                            <input type="hidden" disabled  id="t_days" value="${recommendPlan.days}" placeholder="旅行天数"
                                   onkeyup="value=value.replace(/[^\d]/g,'')" class="input bg">
                            <input type="hidden" id="days" name="recommendPlan.days" value="${recommendPlan.days}" class="bg">
                        </li>
                        <li class="bg time">
                            <input type="datetime" id="startTime" name="startTime"
                                   value="<fmt:formatDate value="${recommendPlan.startTime}" pattern="yyyy-MM-dd" />" placeholder="出发日期"
                                   class="input bg" onclick="WdatePicker({doubleCalendar:false,dateFmt:'yyyy-MM-dd',errDealMode:1,onpicked:startTimeEvent(),maxDate:'%y-%M-%d'})">
                        </li>
                    </ul>
                </div>
            </div>

        </div>

        <div class="nav d_select mb20">
            <div class="w1000 posiR">
                <div id="add_day">
                    <a href="javaScript:addOneDay();"  isuse="0" class="Added fr posiR"><i></i>新增一天</a>
                </div>
            </div>
        </div>

        <div class="w1000" id="nav">
            <!--左侧-->
            <div class="travel_d_l Upload_div fl" id="left_recommendPlan_list">
                <div id="left_day_list">

                </div>
                <div class="Identity cl" id="user_tag">
                    <div class="Identity_t">
                        <span class="Orange">*&nbsp;</span>选择你的身份，让线路更完美
                        <b class="Orange">(你还没有选择身份)</b>
                    </div>
                    <div class="Identity_c">
                        <ul>
                            正在加载......
                        </ul>
                        <p class="cl"></p>
                    </div>
                </div>
            </div>

            <!--右侧-->
            <div class="travel_d_r fr" style="display: none">
                <div class="nav">
                    <p class="title fs20 b">游记目录</p>

                    <div class="travel_d_r_div" id="right_recommendPlan_index">
                    </div>
                </div>
            </div>
            <p class="cl h20"></p>

            <p class="cl h50"></p>
        </div>
    </div>
    <input type="hidden" id="recplanId" name="recommendPlan.id" value="${recommendPlan.id}"/>
    <input type="hidden" id="cityId" name="recommendPlan.city.id" value="${recommendPlan.cityId}">
    <input type="hidden" id="cityIds" name="recommendPlan.cityIds" value="${recommendPlan.cityIds}">
    <input type="hidden" id="scenics" name="recommendPlan.scenics" value="${recommendPlan.scenics}">
    <input type="hidden" id="status" name="recommendPlan.status" value="${recommendPlan.status}">
    <a target="_blank" href="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/preview.jhtml?recplanId=${recommendPlan.id}" class="disn" id="go_preview"></a>
    <a target="_blank" onclick="window.open()" class="disn" id="go_preview2"></a>
    <div id="label_items">
    </div>
</form>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<%@include file="../common/footer.jsp" %>
<%--左侧列表 新增一天数据模板--%>
<script type="text/html" id="left_edit_recommendPlan_day_item">
    <div id="day_{{day_index}}">
        <div class="title posiR">
            <p class="b ff_yh">第{{day}}天</p>
            <%--<p class="Upload_hr oval4 cl"></p>--%>
            <%--<p class="hr oval4"></p>--%>
            <a href="javaScript:;" class="close" d-id="{{day_id}}" day="{{day_index + 1}}" scenics="{{scenics}}"></a>
        </div>
        <%--<div class="Upload_te cl">--%>
            <%--<textarea id="left_day_{{day_index}}_description"--%>
                      <%--name="recommendPlan.recommendPlanDays[{{day_index}}].description"--%>
                      <%--class="mb10 textarea" placeholder="说说这一天过的咋样......">{{description}}</textarea>--%>
        <%--</div>--%>
        <input type="hidden" id="left_day_{{day_index}}_day"
               name="recommendPlan.recommendPlanDays[{{day_index}}].day"
               value="{{day}}">
        <input type="hidden" id="left_day_{{day_index}}_citys"
                name="recommendPlan.recommendPlanDays[{{day_index}}].citys"
                value="{{citys}}">
        <input type="hidden" id="left_day_{{day_index}}_scenics"
               name="recommendPlan.recommendPlanDays[{{day_index}}].scenics"
               value="{{scenics}}">
        <div id="left_day_{{day_index}}_trip_list">
        </div>
        <p id="left_day_{{day_index}}_p" class="Upload_add cl">
            <a href="javaScript:;" isuse="0" onclick="addOneTrip({{day_index}}, {{day_id}})" class="fr posiR"><i></i>新增一个景点/美食/酒店/交通</a>
        </p>
    </div>
</script>
<%--左侧列表 增加一个行程节点数据模板--%>
<script type="text/html" id="left_edit_recommendPlan_trip_item">
<div id="day_{{day_index}}_trip_{{trip_index}}">
    <div class="Upload_i posiR">
        <div class="Upload_i_div posiR fl" id="day_{{day_index}}_trip_{{trip_index}}_typeDiv">
            <label class="posiA"></label>
            <div class="type_sel">
                <b class="posiA"></b>
                <span id="day_{{day_index}}_trip_{{trip_index}}_typeSpan" class="span_u type{{if !tripType > 0}}1{{else}}{{tripType}}{{/if}}"></span>
                <ul class="posiA Upload_add_ul" day-index="{{day_index}}" trip-index="{{trip_index}}">
                    <li data-type="1"><s></s>景点</li>
                    <li data-type="2" class="s3"><s></s>美食</li>
                    <li data-type="3" class="s2"><s></s>酒店</li>
                    <li data-type="4" class="s4"><s></s>交通</li>
                    <li data-type="5" class="s5"><s></s>其他</li>
                </ul>
            </div>
        </div>
        <div class="section_s fl posiR">
            <div class="posiR categories">
                <p>
                    <input type="text" id="left_day_{{day_index}}_trip_{{trip_index}}_scenicName"
                           name="recommendPlan.recommendPlanDays[{{day_index}}].recommendPlanTrips[{{trip_index}}].scenicName"
                           value="{{scenicName}}" placeholder="输入名称" class="input validate_scenicName"
                           onkeyup="complexSearch({{day_index}},{{trip_index}})">
                    <input type="hidden" id="left_day_{{day_index}}_trip_{{trip_index}}_cityCode"
                           name="recommendPlan.recommendPlanDays[{{day_index}}].recommendPlanTrips[{{trip_index}}].cityCode"
                           value="{{cityCode}}">
                    <input type="hidden" id="left_day_{{day_index}}_trip_{{trip_index}}_tripType"
                           name="recommendPlan.recommendPlanDays[{{day_index}}].recommendPlanTrips[{{trip_index}}].tripType"
                           value="{{if !tripType > 0}}1{{else}}{{tripType}}{{/if}}" class="validate_tripType">
                    <input type="hidden" id="left_day_{{day_index}}_trip_{{trip_index}}_scenicId"
                           name="recommendPlan.recommendPlanDays[{{day_index}}].recommendPlanTrips[{{trip_index}}].scenicId"
                           value="{{scenicId}}">
                    <input type="hidden" id="left_day_{{day_index}}_trip_{{trip_index}}_delicacyId"
                           name="recommendPlan.recommendPlanDays[{{day_index}}].recommendPlanTrips[{{trip_index}}].delicacy.id"
                           value="{{if delicacy != null}}{{delicacy.id}}{{/if}}">
                </p>
                <div style="top: 42px;" class="posiA categories_div clx_search_div"
                     id="day_{{day_index}}_trip_{{trip_index}}_complex_name_list">
                    <ul>
                    </ul>
                </div>
            </div>
        </div>
        <div class="close posiA">
            <a href="javaScript:;" d-id="{{day_id}}" t-id="{{trip_id}}" sort="{{sort}}">删除节点</a>
            <%--<span>（节点内标题、照片、文字会一并删除）</span>--%>
        </div>
    </div>
    <div class="Upload_te cl">
        <div>
            <textarea id="left_day_{{day_index}}_trip_{{trip_index}}_exa"
                      name="recommendPlan.recommendPlanDays[{{day_index}}].recommendPlanTrips[{{trip_index}}].exa"
                      class="mb10 textarea" placeholder="游记写的屌，旅行才完美...">{{exa}}</textarea>
        </div>
    </div>
    <div id="day_{{day_index}}_trip_{{trip_index}}_img_area" class="up_img">
        <div id="day_{{day_index}}_trip_{{trip_index}}_img_list">

        </div>
        <div id="day_{{day_index}}_trip_{{trip_index}}_tripImg_picker" class="Upload_f">
            <a href="javaScript:;" class="file disB" day-index="{{day_index}}" trip-index="{{trip_index}}"
               r-id="{{r_id}}" d-id="{{day_id}}" t-id="{{trip_id}}"><b class="posiA">添加照片</b></a>
        </div>
        <p class="cl"></p>
    </div>
    <input type="hidden" id="left_day_{{day_index}}_trip_{{trip_index}}_coverImg"
           name="recommendPlan.recommendPlanDays[{{day_index}}].recommendPlanTrips[{{trip_index}}].coverImg"
           value="{{coverImg}}">
    <input type="hidden" id="left_day_{{day_index}}_trip_{{trip_index}}_sort"
            name="recommendPlan.recommendPlanDays[{{day_index}}].recommendPlanTrips[{{trip_index}}].sort"
            value="{{sort}}">
</div>
</script>
<%--左侧列表图片区域模板--%>
<script type="text/html" id="left_img_area">
    <div class="img posiR">
        <img data-original="{{imgAddr | recommendPlanTripImg}}" width="214" height="256"/>
        <%--<a href="javaScript:;" data-id="{{p_id}}" day-index="{{day_index}}" trip-index="{{trip_index}}" class="setcover oval4">设为封面</a>--%>
        <a href="javaScript:;" data-id="{{p_id}}" day-index="{{day_index}}" trip-index="{{trip_index}}" class="close oval4">删除</a>
    </div>
</script>
<%--左侧列表封面图片区域模板--%>
<script type="text/html" id="left_cover_area">
    <div class="img posiR">
        <img data-original="{{imgAddr | recommendPlanTripImg}}" width="214" height="256"/>
        <%--<a href="javaScript:;" id="coverflag{{day_index}}_{{trip_index}}" class="coverflag oval4">封面</a>--%>
        <%--<a href="javaScript:;" data-id="{{p_id}}" day-index="{{day_index}}" trip-index="{{trip_index}}" class="setcover oval4">设为封面</a>--%>
        <a href="javaScript:;" data-id="{{p_id}}" day-index="{{day_index}}" trip-index="{{trip_index}}" class="close oval4">删除</a>
    </div>
</script>
<%--左侧列表身份选择模板--%>
<script type="text/html" id="left_identity_input">
    <input type="hidden" name="labelItems[{{index}}].label.id" value="{{lid}}">
    <input type="hidden" name="labelItems[{{index}}].targetId" value="{{recplanId}}">
    <input type="hidden" name="labelItems[{{index}}].targetType" value="RECOMMEND_PLAN">
</script>
<%--左侧列表 按钮区域--%>
<script type="text/html" id="left_btn_area">
    <p class="cl h30"></p>
    <p class="Upload_hr oval4 cl mb15"/></p>
    <div class="Upload_hr_bot fs16">
        <%--<b class="fl orange  mt10">保存草稿</b>--%>
        <a href="javascript:;" onclick="saveRecommendPlan();" class="fr oval4 b Upload_but">发表游记</a>
        <a href="javascript:;" onclick="saveDraftRecommendPlan()" class="fr oval4 b Upload_but">保存草稿</a>
        <a href="javascript:;" onclick="previewRecommendPlan()" class="fr oval4 Upload_but">预览</a>
    </div>
</script>
<%--右侧目录 新增一天数据模板--%>
<script type="text/html" id="right_edit_recommendPlan_day_item">
    <dl class="travel_d_r_dl checked">
        <dt>第{{day}}天（<span>{{if scenics != null}}{{scenics}}{{else}}0{{/if}}</span>）
            <i class="d_ico disB posiA"></i>
        </dt>
        <dd class="disB" day="{{day}}" id="right_day_{{day_index}}_dd">
        </dd>
    </dl>
</script>
<%--右侧目录 新增一个节点数据模板--%>
<script type="text/html" id="right_edit_recommendPlan_trip_item">
    <div class="travel_d_r_dl_d">
        <a href="javascript:;"><i class="d_ico d_jt"></i>
            {{if tripType == 1 || tripType == 3}}
            <i class="d_ico disB fl d_dx"></i>
            {{else if tripType == 2}}
            <i class="d_ico disB fl d_cy"></i>
            {{else if tripType == 4}}
            <i class="d_ico disB fl d_hc"></i>
            {{/if}}
            <div class="fl nr">
                <p class="name">{{scenicName}}</p>
                <%--<p class="js">共有2篇游记提及</p>--%>
                <span class="hstar cl">
                    {{if tripType == 1 || tripType == 2}}
                        {{if score >=1 && score < 25}}
                            <i class="star1"></i>
                        {{else if score >= 25 && score < 50}}
                            <i class="star2"></i>
                        {{else if score >= 50 && score < 75}}
                            <i class="star3"></i>
                        {{else if score >= 75 && score < 90}}
                            <i class="star4"></i>
                        {{else if score >= 90 && score <= 100}}
                            <i class="star5"></i>
                        {{/if}}
                    {{/if}}
                </span>
            </div>
        </a>
    </div>
</script>
<%--综合搜索条目模板--%>
<script type="text/html" id="complex_search_item">
    <li data-id="{{id}}" data-type="{{type}}" data-name="{{name}}" data-city="{{cityId}}">
        <label class="fl">{{#keyword}}</label>
        <span class="fr" style="display: block">&nbsp;{{typeName}}</span>
        <span class="fr" style="display: block">{{city}}</span>
    </li>
</script>
<script src="/js/lib/template.helper.js" type="text/javascript"></script>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lib/webuploader/webuploader.min.js" type="text/javascript"></script>
<script src="/js/lib/umeditor/umeditor.config.js" type="text/javascript"></script>
<script src="/js/lib/umeditor/umeditor.min.js" type="text/javascript"></script>
<script src="/js/lib/umeditor/lang/zh-cn/zh-cn.js" type="text/javascript"></script>
<%--<script src="/js/lvxbang/upload.js" type="text/javascript"></script>--%>
<script src="/js/lvxbang/recommendPlan/upload.cover.js" type="text/javascript"></script>
<script src="/js/lvxbang/recommendPlan/upload.tripimg.js" type="text/javascript"></script>
<script src="/js/lvxbang/recommendPlan/recommendPlan.js" type="text/javascript"></script>
<script src="/js/lvxbang/recommendPlan/recommendPlanEdit.js" type="text/javascript"></script>
<!--foot end--><!-- #EndLibraryItem -->
</body>
</html>

