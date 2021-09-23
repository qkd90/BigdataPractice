<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/18
  Time: 09:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>我的线路</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/member.css" rel="stylesheet" type="text/css">
</head>
<body myname="Member" class="member_xx member_xc" id="nav"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--Float_w-->
<!--Float_w end-->

<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->
<div class="main cl">
    <jsp:include page="/WEB-INF/jsp/lvxbang/user/personalHeader.jsp"></jsp:include>

    <div class=" cl mc_nr">
        <div class="w1000 mc_nr_bg">
            <div class="m_left fl">
                <a href="javaScript:;" class="m_left_c checked" id="started-btn"
                   onclick="PlanList.changeCategory('plan')"><i></i>自主设计</a>
                <a href="javaScript:;" class="m_left_c" id="not-start-btn"
                   onclick="PlanList.changeCategory('require')"><i></i>定制需求</a>
            </div>

            <div class="m_right fr m_dp_line">
                <div style="text-align: right;">
                    <a id="createPlan" href="${PLAN_PATH}" class="butopt oval4 posiR cl disB"><i class="disB"></i>创建线路</a>
                    <a id="createRequire" href="/lvxbang/customRequire/fill.jhtml" class="butopt oval4 posiR cl disB" style="display: none;"><i class="disB"></i>创建需求单</a>
                </div>
                <div class="center">
                    <ul class="m_dp_line_ul" id="myplan">
                        <%--行程--%>

                    </ul>
                    <p class="cl"></p>
                </div>
                <!-- #BeginLibraryItem "/lbi/paging.lbi" -->
                <!--pager-->
                <p class="cl"></p>
                <img src="/images/food2.png" class="fr foodxx" style="margin-right:73px;"/>
                <div class="m-pager st cl">

                </div>
                <!--pager end-->
                <p class="cl h30"></p><!-- #EndLibraryItem --></div>
            <p class="cl"></p>
        </div>
    </div>

</div>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/user/myPlan.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //选项卡
        $(".m_left .m_left_c").click(function () {
            $(this).addClass("checked").siblings(".m_left_c").removeClass("checked");
            $(".m_right").find("div.center").eq($(this).index()).show().siblings("div.center").hide();
        })

        //全部删除添加的乘客
        $('.m_dp_line .del').click(function () {
            if ($(".m_dp_line .center .checkbox").hasClass('checked')) {
                $(".m_dp_line .center .checkbox.checked").parent("li").remove();
            }

        });

        //checkbox
        $('.m_dp_line').delegate('.checkbox', 'click', function () {
            var input = $(this).attr("input");
            var myStaute = $(this).attr("data-staute");
            if (!myStaute) {
                if (input == "selectall") {
                    $(this).addClass("checked").attr("data-staute", "1").closest('.m_dp_line').find(".checkbox").addClass("checked").attr("data-staute", "1");
                } else {
                    $(this).addClass("checked").attr("data-staute", "1");
                }
            }
            else {
                if (input == "selectall") {
                    $(this).removeClass("checked").removeAttr("data-staute").closest('.m_dp_line').find(".checkbox").removeClass("checked").removeAttr("data-staute");
                } else {
                    $(this).removeClass("checked").removeAttr("data-staute");
                    $(this).parents('.m_dp_line').find(".top .checkbox").removeClass("checked").removeAttr("data-staute");
                }

            }
        });

    });
</script>


<script type="text/html" id="tpl-plan-list-item">

    <li class="posiR">
        <div class="m_dp_line_nr">
            <p class="img">
                <a href="${PLAN_PATH}/plan_detail_{{id}}.html">
                    <img data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{cover}}"/>
                </a>
            </p>
            <div class="m_dp_line_js fr ">
                <p class="name fs14 b">{{name}}
                    {{if started == true && (recplanId == null || recplanId < 1)}}
                    <span class="itag">已完成</span>
                    {{/if}}
                    {{if started == false}}
                    <span class="itaged">未完成</span>
                    {{/if}}
                </p>
                <p class="dianp fs13">
                    {{each citys as city i}}
                    <%--<li>索引 {{i + 1}} ：{{value}}</li>--%>
                    {{city}}&nbsp
                    {{/each}}
                </p>
                <p class="time posiR">{{days}}天/{{cost}}元/{{date}}</p>
                {{if started == true && (recplanId == null || recplanId < 1)}}
                <a href="javaScript:;" class="but disB m0auto oval4" onclick="PlanList.toRecplan({{id}})">生成游记</a>
                {{/if}}
                {{if started == false}}
                <a href="${PLAN_PATH}/lvxbang/plan/edit.jhtml?planId={{id}}" class="but disB m0auto oval4">编辑线路</a>
                {{/if}}

                <i class="m_del posiA oval4" onclick="PlanList.delSelected({{id}})">删除</i>
                <p class="cl"></p>
            </div>
        </div>
        <p class="cl"></p>
    </li>

</script>
<!-- 定制需求 -->
<script type="text/html" id="tpl-require-list-item">
    <li class="ft_list require_List">
        <div class="idetails">
            <p class="itext">
                从{{startCityName}}出发，想去
                {{each customRequireDestinationVos}}
                    {{if $value.level == 1}}
                    <a href="${DESTINATION_PATH}/province_{{$value.cityId}}.html" target="_blank">{{$value.cityName}}</a>
                    {{else}}
                    <a href="${DESTINATION_PATH}/city_{{$value.cityId}}.html" target="_blank">{{$value.cityName}}</a>
                    {{/if}}
                {{/each}}
                玩，{{day}}天，一共{{adult+child}}个人出行(其中{{adult}}个成人，{{child}}个儿童)，想要{{customTypeName}}定制，人均预算{{budget}}。
                {{if status == "handling"}}
                <span class="itaging">处理中</span>
                {{/if}}
                {{if status == "handled"}}
                <span class="itag">已处理</span>
                {{/if}}
                {{if status == "cancel"}}
                <span class="itaged">已取消</span>
                {{/if}}
            </p>
            <p class="control" style="padding-top: 10px;">
                <span>{{createTime}}</span>
            </p>
        </div>
    </li>
</script>

