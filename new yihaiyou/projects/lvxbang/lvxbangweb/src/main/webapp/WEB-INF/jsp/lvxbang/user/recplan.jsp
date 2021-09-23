<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/18
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>我的游记</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/member.css" rel="stylesheet" type="text/css">
</head>
<body myname="Member" class="member_xx member_xc member_yj" id="nav"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!--Float_w-->

<!--Float_w end-->

<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->
<div class="main cl">
    <jsp:include page="/WEB-INF/jsp/lvxbang/user/personalHeader.jsp"></jsp:include>

    <div class=" cl mc_nr m_yj_div">
        <div class="w1000 mc_nr_bg">
            <div class="m_left fl">
                <a href="javaScript:;" class="m_left_c checked " id="started-btn"
                   onclick="RecplanList.changeCategory(1)"><i></i>已发表</a>
                <a href="javaScript:;" class="m_left_c" id="not-start-btn"
                   onclick="RecplanList.changeCategory(2)"><i></i>草稿箱</a>

                <a target="_blank" href="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/edit.jhtml" class="but oval4 posiR cl disB"><i class="disB"></i>新建游记</a>
            </div>

            <div class="m_right fr m_dp_line">
                <div class="center member_yj_center">
                    <ul class="m_dp_line_ul" id="my-recplan">
                        <%--游记列表--%>
                    </ul>
                    <p class="cl"></p>
                </div>

                <%--<div class="center disn">--%>
                <%--<ul class="m_dp_line_ul">--%>
                <%--<li class="posiR">--%>
                <%--<dl>--%>
                <%--<dd class="checked"><i></i><span>264454</span></dd>--%>
                <%--<dd><i class="ico2"></i><span>64923</span></dd>--%>
                <%--<dd><i class="ico3"></i><span>3652</span></dd>--%>
                <%--</dl>--%>

                <%--<div class="m_dp_line_nr">--%>
                <%--<p class="img"><img data-original="images/fimg_07.jpg"/></p>--%>
                <%--<div class="m_dp_line_js fr ">--%>
                <%--<p class="name fs14 b">冬日北欧浪漫游</p>--%>
                <%--<p class="dianp fs13">厦门 北欧</p>--%>
                <%--<p class="time posiR">14天/1400元/2015-5-16</p>--%>
                <%--<a href="#" class="but disB m0auto oval4">编辑行程</a>--%>
                <%--<i class="m_del posiA oval4">删除</i>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--<p class="cl"></p>--%>
                <%--</li>--%>

                <%--</ul>--%>
                <%--<p class="cl"></p>--%>
                <%--</div>--%>
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
<script src="/js/lvxbang/user/myRecplan.js" type="text/javascript"></script>
<script src="/js/lib/template.helper.js" type="text/javascript"></script>
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

//        $('.m_dp_line .center').delegate('.m_del', 'click', function () {
//            $(this).parents("li").fadeOut(function () {
//                $(this).parents("li").remove();
//
//            });
//
//        });


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

<script type="text/html" id="tpl-recplan-list-item">

    <li class="posiR">
        <dl>
            <dd class="checked"><i></i><span>{{browsingNum}}</span></dd>
            <dd><i class="ico2"></i><span>{{quoteNum}}</span></dd>
            <dd><i class="ico3"></i><span>{{colloctNum}}</span></dd>
        </dl>

        <div class="m_dp_line_nr">
            <p class="img">
                {{if started == false}}
                    <a target="_blank" href="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/preview.jhtml?recplanId={{id}}">
                        <img data-original="{{cover | recommendPlanUserCenterImg}}"/>
                    </a>
                {{else}}
                    <a target="_blank" href="${RECOMMENDPLAN_PATH}/guide_detail_{{id}}.html">
                        <img data-original="{{cover | recommendPlanUserCenterImg}}"/>
                    </a>
                {{/if}}
            </p>
            <div class="m_dp_line_js fr ">
                <p class="name fs14 b">{{name}}</p>
                <p class="dianp fs13">
                    {{each citys as city i}}
                    <%--<li>索引 {{i + 1}} ：{{value}}</li>--%>
                    {{city}}&nbsp
                    {{/each}}
                </p>
                <p class="time posiR">{{days}}天/{{date}}</p>
                <%--{{if started == false}}--%>
                <a href="${RECOMMENDPLAN_PATH}/lvxbang/recommendPlan/edit.jhtml?recplanId={{id}}" target="_blank"
                   class="but disB m0auto oval4">编辑游记</a>
                <%--{{/if}}--%>
                <i class="m_del posiA oval4" onclick="RecplanList.delSelected({{id}})">删除</i>
                <p class="cl"></p>
            </div>
        </div>
        <p class="cl"></p>
    </li>

</script>

