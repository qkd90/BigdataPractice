<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/11
  Time: 22:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>我的订单</title>
    <meta name="keywords" content="index"/>
    <meta name="description" content="index"/>
    <link href="/css/tBase.css" rel="stylesheet" type="text/css">
    <link href="/css/announcement.css" rel="stylesheet" type="text/css">
    <link href="/css/float.css" rel="stylesheet" type="text/css">
    <link href="/css/member.css" rel="stylesheet" type="text/css">
</head>
<body myname="Member" class="member_xx member_dd" id="nav"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
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
                <a href="javaScript:;" class="checked " id="category-0"
                   onclick="OrderList.changeCategory(0)"><i></i>全部<span class="not ml5" id="num-0">0</span></a>
                <a href="javaScript:;" id="category-1" onclick="OrderList.changeCategory(1)"><i></i>未出行<span
                        class="not ml5" id="num-1">0</span></a>
                <a href="javaScript:;" id="category-2" onclick="OrderList.changeCategory(2)"><i></i>待付款<span
                        class="not ml5" id="num-2">0</span></a>
                <a href="javaScript:;" id="category-3" onclick="OrderList.changeCategory(3)"><i></i>退款<span
                        class="not ml5" id="num-3">0</span></a>
                <a href="javaScript:;" id="category-4" onclick="OrderList.changeCategory(4)"><i></i>待评价<span
                        class="not ml5" id="num-4">0</span></a>
            </div>

            <div class="m_right fr dd_m_dp_line">
                <div class="dd_search">
                    <dl class="fl">
                        <dd>
                            <label class="fl ml10 mr10">订单号</label>
                            <div class="fl nr"><i class="ico ico2"></i><input type="text" placeholder="" value=""
                                                                              class="input" id="orderNo"></div>
                        </dd>
                        <dd>
                            <label class="fl ml10 mr10">类型</label>
                            <div class="fl nr">
                                <div class="posiR m_hide">
                                    <span class="name" id="orderType">全部</span>
                                    <i class="ico ico_type"></i>
                                    <div class="posiA m_hide_hi">
                                        <ul>
                                            <li>全部</li>
                                            <li>飞机</li>
                                            <li>火车</li>
                                            <li>酒店</li>
                                            <li>门票</li>
                                            <li>行程</li>
                                            <li>线路</li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </dd>
                        <dd>
                            <label class="fl ml10 mr10">预订日期</label>
                            <div class="fl nr posiR"><i class="ico2 ico_calendar"></i><input type="text" placeholder="起始日期" value=""
                                                                                             readOnly="true"
                                                                                             onFocus="WdatePicker()"
                                                                                             class="input"
                                                                                             id="startTime">
                            </div>
                        </dd>
                        <dd>
                            <label class="fl mr3">-</label>
                            <div class="fl nr posiR"><i class="ico2 ico_calendar"></i><input type="text" placeholder="结束日期" value=""
                                                                                             readOnly="true"
                                                                                             onFocus="WdatePicker()"
                                                                                             class="input"
                                                                                             id="endTime">
                            </div>
                        </dd>
                    </dl>
                    <a href="javaScript:;" class="but fr oval4 fs13" onclick="OrderList.search()">搜索</a>
                    <p class="cl"></p>
                </div>
                <div class="top mb10">
                    <span class="checkbox disB fl mr10" input="selectall"><i></i></span>
                    <span class="fl mr30">全选</span>
                    <a href="javaScript:;" class="del fl" onclick="OrderList.delSelected()">删除</a>
                    <div class="w9 fr textC">操作</div>
                    <div class="w8 fr textC">订单状态</div>
                    <div class="w7 fr textC">总金额</div>
                    <div class="w6 fr textC">线路/有效期</div>
                    <div class="w5 fr textC">旅客</div>
                </div>

                <div class="center cl">
                    <ul id="myorder">
                        <%--我的订单--%>
                    </ul>
                </div>

            </div>
            <p class="cl"></p>
            <div class="fy_bottom pt20"><!-- #BeginLibraryItem "/lbi/paging.lbi" -->
                <!--pager-->
                <p class="cl"></p>
                <img src="/images/food2.png" class="fr foodxx" style="margin-right:73px;"/>
                <div class="m-pager st cl">
                </div>
                <!--pager end-->
                <p class="cl h30"></p><!-- #EndLibraryItem --></div>
        </div>

    </div>

</div>
<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/user/myOrder.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {

        //下拉框
        $('.dd_search .m_hide .name,.dd_search .m_hide i').click(function () {
//            $(this).nextAll(".m_hide_hi").show();
            $(this).parents(".m_hide").addClass("checked");
        });

        $('.dd_search').delegate('.m_hide_hi li', 'click', function () {
            var label = $(this).text();
            $(this).parents(".m_hide").removeClass("checked").find(".name").text(label);
            $(this).closest(".m_hide_hi").hide();
            $(this).parents(".m_hide").prev(".m_name").html(label);

        });

        //checkbox
        $('.dd_m_dp_line').delegate('.checkbox', 'click', function () {
            var input = $(this).attr("input");
            var myStaute = $(this).attr("data-staute");
            if (!myStaute) {
                if (input == "selectall") {
                    $(this).addClass("checked").attr("data-staute", "1").parents('.dd_m_dp_line').find("li .checkbox").addClass("checked").attr("data-staute", "1");
                } else {
                    $(this).addClass("checked").attr("data-staute", "1");
                }
            }
            else {
                if (input == "selectall") {
                    $(this).removeClass("checked").removeAttr("data-staute").parents('.dd_m_dp_line').find("li .checkbox").removeClass("checked").removeAttr("data-staute");
                } else {
                    $(this).removeClass("checked").removeAttr("data-staute");
                    $(this).parents('.dd_m_dp_line').find(".top .checkbox").removeClass("checked").removeAttr("data-staute");
                }

            }
        });

    });
</script>

<script type="text/html" id="tpl-order-list-item">
    <li>
        <div class="dd_top myorder">
            <input class="orderId" type="hidden" value="{{id}}"/>
            <span class="checkbox disB fl mr10" input="options"><i></i></span>
            <span class="fl mr20">订单号：{{orderNo}}</span>
            <span class="fl">预订日期：{{orderDate}}</span>
            <a href="javaScript:;" class="m_del fr mr15" onclick="OrderList.deleteByIds({{id}})"></a>
        </div>
        {{each orderDetailList as detail i}}
        <%--<li>索引 {{i + 1}} ：{{value}}</li>--%>

        <div class="dd_bottom textC">
            <div class="w1 fl">{{detail.type}}</div>
            <div class="w2 fl textL">{{detail.name}}</div>
            <div class="w3 fl{{if detail.detailType.length > 6}} is_hover{{/if}}">{{detail.detailType}}</div>
            <div class="w3_hover fl"></div>
            <div class="w4 fl">{{detail.num}}</div>
            <div class="w5 fl">{{detail.tourist}}</div>
            <div class="w6 fl">{{detail.playDate}}</div>
            <div class="w7 fl">¥{{detail.cost}}</div>
            <div class="w8 fl">{{detail.status}}</div>
            <div class="w9 fr"><a href="{{detail.detailUrl}}">详情</a></div>
            <p class="cl"></p>
        </div>
        {{/each}}
        <p class="cl"></p>
    </li>
</script>

<!-- line模板 -->
<script type="text/html" id="tpl-order-list-item-line">
    <li>
        <div class="dd_top myorder">
            <input class="orderId" type="hidden" value="{{id}}"/>
            <span class="checkbox disB fl mr10" input="options"><i></i></span>
            <span class="fl mr20">订单号：{{orderNo}}</span>
            <span class="fl">预订日期：{{orderDate}}</span>
            <a href="javaScript:;" class="m_del fr mr15" onclick="OrderList.deleteByIds({{id}})"></a>
        </div>
        <div class="dd_bottom textC">
            <div class="w1 fl">{{orderTypeDesc}}</div>
            <div class="w2 fl textL">{{name}}</div>
            <table class="fl">
                {{each orderDetailList as detail i}}
                <tr>
                    <td>
                        <div class="w3 fl{{if detail.detailType.length > 6}} is_hover{{/if}}">{{detail.detailType}}</div>
                        <div class="w3_hover fl"></div>
                    </td>
                    <td>
                        <div class="w4 fl">{{detail.num}}</div>
                    </td>
                    <td>
                        <div class="w5 fl">{{detail.tourist}}</div>
                    </td>
                    <td>
                        <div class="w6 fl">{{detail.playDate}}</div>
                    </td>
                    <td>
                        <div class="w7 fl">¥{{detail.cost}}</div>
                    </td>
                </tr>
                {{/each}}
            </table>
            <div class="w8 fl">{{status}}</div>
            <div class="w9 fr"><a href="{{detailUrl}}">详情</a></div>
            <p class="cl"></p>
        </div>
        <p class="cl"></p>
    </li>
</script>
