<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 16/1/7
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>预订路线</title>
  <meta name="keywords" content="index"/>
  <meta name="description" content="index"/>
  <link href="/css/tBase.css" rel="stylesheet" type="text/css">
  <link href="/css/announcement.css" rel="stylesheet" type="text/css">
  <link href="/css/float.css" rel="stylesheet" type="text/css">
  <link href="/css/order2.css" rel="stylesheet" type="text/css">
  <link rel="stylesheet" href="/css/line/dingdan/zijia_common.css" type="text/css">
  <link rel="stylesheet" href="/css/line/dingdan/reservations_product_info.css" type="text/css">
  <link rel="stylesheet" href="/css/line/dingdan/reset.css" type="text/css">
  <link rel="stylesheet" href="/css/line/dingdan/style.css" type="text/css">
</head>
<body class="fill_orders Fill_orders_tk" onload="pageReload();">
<jsp:useBean id="now" class="java.util.Date" scope="session"/>
<input type="hidden" value="0" id="page_reload"/>
<!-- #BeginLibraryItem "/lbi/head.lbi" -->

<!--head-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/header.jsp"></jsp:include>
<!--head  end--><!-- #EndLibraryItem -->


<!-- #BeginLibraryItem "/lbi/head.lbi" -->
<!-- #EndLibraryItem -->
<!--banner-->
<div class="wrapper">
  <input id="orderId" type="hidden" value="${order.id}"/>
  <input id="orderDetailId" type="hidden" value="${orderDetailId}"/>
  <input id="returnOrderDetailId" type="hidden" value="${returnOrderDetailId}"/>
  <input id="lineId" type="hidden" value="${linetypeprice.line.id}"/>
  <input id="linetypepriceId" type="hidden" value="${linetypeprice.id}"/>
  <input id="price" type="hidden" value="${linetypepricedate.discountPrice + linetypepricedate.rebate}"/>
  <input id="childPrice" type="hidden" value="${linetypepricedate.childPrice + linetypepricedate.childRebate}"/>
  <input id="lineDay" type="hidden" value="${linetypeprice.line.playDay}"/>
  <input id="submitFlag" type="hidden" value="ok" autocomplete="off">
  <input id="provisionCheck" type="hidden" class="checked"/>
  <div class="content clearfix">
    <!--order box start-->
    <div class="order_box" id="order_box">
      <h1 class="order_title">&lt;${linetypeprice.line.name}&gt;${linetypeprice.line.appendTitle}<span class="number"> 编号${linetypeprice.line.productNo}</span></h1>
      <div class="order_form">
        <c:if test="${startCity != null}">
        <div class="field J_departCity">
          <label>出发城市：</label>
          <div class="input_select w_200">
            <ul class="s_clist" style="display: none;">
              <li citycode="602">${startCity.name}</li>                </ul>

            <div class="s_cnum  s_cnum_onlyone">
              <p class="s_ccon" citycode="602">${startCity.name}</p>
            </div>
          </div>
          <!--            <span class="tip"><span class="icon info_icon mr5"></span>出发城市已变更，请重新确认航班</span>-->
        </div>
        </c:if>

        <!-- 归来城市 -->

        <div class="field J_departDate">
          <label>出发日期：</label>
          <div class="input_select w_350">
            <ul class="s_clist" style="display: none;" id="selectDate">
            </ul>
            <div class="s_cnum" id="resultDate">
              <span class="sc_arrow"></span>

              <p class="s_ccon" data-date="${linetypepricedate.day}"
                 data-adult-price="${linetypepricedate.discountPrice + linetypepricedate.rebate}"
                 data-child-price="${linetypepricedate.childPrice + linetypepricedate.childRebate}"
                 data-single-price="${linetypepricedate.oasiaHotel}"></p>
            </div>
          </div>
          <!--            <span class="tip"><span class="icon info_icon mr5"></span>出发日期已变更，请重新确认上车点，优惠，升级方案，单房差，保险</span>-->
        </div>
        <div class="field J_returnDate">
          <label>归来日期：</label>
          <span></span>
        </div>
        <div class="field J_touristCount">
          <label>出游人数：</label>
          <div class="input_number J_adultCount">
            <a class="tn_fontface minus" onclick="LineOrder.changePrice(-1)"></a><input type="text" class="value" readonly="" value="${adultNum}" id="passengerNum"><a class="tn_fontface plus" onclick="LineOrder.changePrice(1)"></a>
            <label>成人</label>
          </div>
          <div class="input_number ml20  J_childCount">
            <a class="tn_fontface minus" onclick="LineOrder.changeChildPrice(-1)"></a><input type="text" class="value" readonly="" value="${childNum}" id="passengerChildNum"><a class="tn_fontface plus" onclick="LineOrder.changeChildPrice(1)"></a>
            <label>儿童
                    <c:if test="${lineexplain.childStandardType != 'none'}">
                    <span class="child_criterion">（标准▼）
                        <div class="tip">
                          <span class="icon arrow_up_icon"></span>
                          <p>
                            <c:if test="${lineexplain.childStandardType == 'desc'}">
                              ${lineexplain.childLongRemark}
                            </c:if>
                            <c:if test="${lineexplain.childStandardType == 'height'}">
                              身高${lineexplain.childStartNum}~${lineexplain.childEndNum}米（不含），${lineexplain.childBed}，${lineexplain.childOtherRemark}。
                            </c:if>
                            <c:if test="${lineexplain.childStandardType == 'age'}">
                              年龄${lineexplain.childStartNum}~${lineexplain.childEndNum}周岁（不含），${lineexplain.childBed}，${lineexplain.childOtherRemark}。
                            </c:if>
                          </p>
                        </div>
                    </span>
                    </c:if>
            </label>
          </div>
        </div>

      </div>
      <div class="order_more">
        <h3>出游人确认</h3>
        <ul class="question_list">
          <li class="question_item clearfix J_hasOld">
            <div class="item_text">出游人中是否有${lineexplain.tripAgeMin}（含）${lineexplain.tripAgeMax}（不含）周岁的老人？</div>
            <div class="item_answer">
              <label class="input_checkbox" value="1"><span class="tn_fontface"></span>有</label>
              <label class="input_checkbox enabled ml20" value="0"><span class="tn_fontface"></span>没有</label>
            </div>
          </li>
          <li class="question_item clearfix J_hasForeigner">
            <div class="item_text">出游人中是否有外籍友人？</div>
            <div class="item_answer">
              <label class="input_checkbox" value="1"><span class="tn_fontface"></span>有</label>
              <label class="input_checkbox enabled ml20" value="0"><span class="tn_fontface"></span>没有</label>
            </div>
          </li>
        </ul>
        <ul class="tip_list">
          <li>本产品不支持孕妇
            或者${lineexplain.tripAgeMax}周岁(含)以上的老人                出游：18周岁以下的未成年人必须有成年人陪同出游，敬请谅解！
          </li>
        </ul>
      </div>
    </div>
    <!--order box end-->

    <!--airplane box start-->
    <!--airplane box end-->
    <!--bus box start-->
    <!--bus box end-->

    <!--hotel boxes start-->
    <!--hotel boxes end-->

    <!--contact box start-->
    <div class="contact panel mt20" id="contact_box">
      <h2><span class="icon contact_icon mr10 vm"></span><span class="vm">联系人信息</span></h2>
      <div class="panel_body">
        <form class="J_form">
          <div class="field J_name">
            <label class="field_label">姓名：</label>
            <input class="field_input" type="text" value="${order.recName==null?user.userName:order.recName}" id="contactsName">
          </div>
          <div class="field J_mobile mt15">
            <label class="field_label">联系电话：</label>
            <input class="field_input internation_code_box" type="text" placeholder="国际区号" readonly="" data-telcountryid="" value="0086">-
            <input class="field_input mobile_box" type="text" placeholder="手机号码" value="${order.mobile==null?user.telephone:order.mobile}" id="contactsTel">
          </div>
          <div class="field J_phone mt15">
            <label class="field_label"></label>
            <input class="field_input area_code" type="text" placeholder="固话区号"><span class="dash">-</span><input class="field_input phone_code" type="text" placeholder="固话号码">
          </div>
          <div class="field J_email mt15">
            <label class="field_label">电子邮箱：</label>
            <input class="field_input" type="text" value="">
          </div>
          <div class="field email_tips mt15">
            <p>付款完成后,您的邮箱将收到加盖公章的合同,您也可以在个人中心查看和下载您的合同。</p>
          </div>
          <!--<div class="field J_remember mt15 mb40">
                <label class="field_label"></label>
                <label class="input_checkbox"><span class="tn_fontface">&#xe618;</span>设为常用联系人</label>
            </div>-->
        </form>
      </div>
    </div>

    <c:if test="${needGuarantee}">
      <div class="contact panel mt20" id="guarantee_box">
        <h2><span class="icon contact_icon mr10 vm"></span><span class="vm">酒店担保信息(<span class="red">*</span>为必填)</span>
        </h2>

        <div class="fill_top_div_js">
          <div class="nr" style="padding:0;border: none;">
            <ul style="margin-top: 20px;">
              <li style="margin-left: 150px;margin-bottom: 10px;">
                <p class="fl" style="width: 96px"><span class="Orange mr5">*</span>信用卡卡号</p>

                <div class="oval4 fl " style="padding-top: 0;"><input id="cardNum" type="text" style="margin-top:5px;"
                                                                      placeholder="请输入信用卡卡号" value="" class="input"/>
                </div>
              </li>
              <li style="margin-left: 150px;margin-bottom: 10px;">
                <p class="fl" style="width: 96px"><span class="Orange mr5">*</span>持卡人姓名</p>

                <div class="oval4 fl" style="padding-top: 0;"><input id="holderName" type="text" style="margin-top:5px;"
                                                                     placeholder="请输入持卡人姓名" value="" class="input"/>
                </div>
              </li>
              <li style="margin-left: 150px;margin-bottom: 10px;overflow: visible;">
                <p class="fl" style="width: 96px"><span class="Orange mr5">*</span>信用卡有效期至</p>

                <div class="sfz fl posiR" style="width:70px;padding-top: 0;">
                  <p class="name " id="expirationYear">年</p>

                  <i style="width: 9px;height: 6px;background: url(/images/ico.png) 0 -582px no-repeat;display: block;position: absolute;top: 12px;right: 8px;"></i>

                  <p class="sfzp disn xinyongka"
                     style="position: absolute;background: #fff;top: 28px;border: 1px solid #ccc;border-top: 0;width: 100%;left: -1px;z-index: 9;">

                    <c:forEach begin="0" end="9" var="y">
                      <a href="javaScript:;"
                         style="display: block;line-height: 30px;clear: both;color: #666;padding-left: 10px;">
                          ${sessionScope.now.year + 1900 + y}年
                      </a>
                    </c:forEach>

                  </p>
                </div>

                <div class="sfz fl posiR" style="width:70px;padding-top: 0;margin-left: 52px;">
                  <p class="name " id="expirationMonth">月</p>

                  <i style="width: 9px;height: 6px;background: url(/images/ico.png) 0 -582px no-repeat;display: block;position: absolute;top: 12px;right: 8px;"></i>

                  <p class="sfzp disn xinyongka"
                     style="position: absolute;background: #fff;top: 28px;border: 1px solid #ccc;border-top: 0;width: 100%;left: -1px;z-index: 9;">
                    <c:forEach begin="1" end="12" var="yue">
                      <a href="javaScript:;"
                         style="display: block;line-height: 30px;clear: both;color: #666;padding-left: 10px;">
                          ${yue}月</a>
                    </c:forEach>

                  </p>
                </div>
              </li>
              <li style="margin-left: 150px;margin-bottom: 10px;">
                <p class="fl" style="width: 96px"><span class="Orange mr5">*</span>身份证</p>

                <div class="oval4 fl" style="padding-top: 0;"><input id="idNo" type="text" style="margin-top:5px;"
                                                                     placeholder="请输入联系人身份证号" value="" class="input"/>
                </div>
              </li>
              <li style="margin-left: 150px;margin-bottom: 10px;">
                <p class="fl" style="width: 96px"><span class="Orange mr5">*</span>信用卡验证码</p>

                <div class="oval4 fl" style="padding-top: 0;"><input id="cvv" type="text" style="margin-top:5px;"
                                                                     placeholder="卡背后末三位数" value="" class="input"/>
                </div>
              </li>
            </ul>
          </div>
      </div>
    </div>
    </c:if>
    <!-- 用户登录模块 start -->
    <!-- 用户登录模块结束 --><!--contact box end-->

    <!--contactInfo box start-->
    <style>
      /* 解决错误提示信息为多行的情况下的显示问题 */
      .tourist_info .J_name{
        overflow: hidden;
      }

      .tourist_info .J_name label{
        float: left;
      }

      .tourist_info .J_name input{
        float: left;
      }

      .tourist_info .J_name .error{
        float: left;
        width: 233px;
      }
      /**/
    </style>
    <!--order box end-->
    <a name="tourist"></a>
    <div class="panel tourist mt20" id="tourist_box">
      <h2>
        <span class="icon people_icon vm mr10"></span><span class="vm">出游人信息</span>
        <span class="no_write_detail_switch hide vm" id="J_tourist_detail_switch">展开</span>
        <span class="no_write_tips hide vm" id="J_no_write_tips">本次不保存您所填写的出游人信息，您可以稍后前往我的订单添加出游人</span>

      </h2>
      <div class="panel_body" id="J_tourist_detail">
        <form>
          <div class="info_box">
            <ul>
              <li>·&nbsp;为了保障您的合法权益，请准确、完整的填写游客证件信息，错误的身份信息可能造成额外的退、改费用。</li>
              <li>·&nbsp;儿童游客如无相关证件，证件类型请选择"其他"，并填写出生日期。</li>
              <li>·&nbsp;出游人信息不完整会延误你的正常出游；因信息不完整、不准确造成的保险拒赔等问题，我司不承担相应责任。</li>
            </ul>
          </div>
          <h3 style="display: none;">常用出游人</h3>
          <ul class="tourist_list J_tourist_list" id="J_PassFavors" style="display: none;"></ul>
          <a class="tourist_more" style="display: none;">更多常用游客</a>
          <div class="cb"></div>
          <div class="fill_div_nr">
            <p class="title b fs14">常用出行人:</p>
            <div class="fill_name" id="contacts">
              <c:forEach items="${touristList}" var="tourist">
                                        <span class="oval4" style="margin-bottom:5px;">
                                            ${tourist.name}
                                            <input type="hidden" class="id" value="${tourist.id}"/>
                                            <input type="hidden" class="name" value="${tourist.name}"/>
                                            <input type="hidden" class="peopleType" value="${tourist.peopleType}"/>
                                            <input type="hidden" class="idType" value="${tourist.idType}"/>
                                            <input type="hidden" class="idNumber" value="${tourist.idNumber}"/>
                                            <input type="hidden" class="tel" value="${tourist.tel}"/>
                                        </span>
              </c:forEach>
              <p class="cl"></p>
            </div>
          </div>
          <div class="J_tourist_info" id="touristList1"></div>
          <div class="J_tourist_info" id="touristList2"></div>
          <%--<div class="tourist_no_write" id="J_no_write">暂不填写</div>--%>
          <div class="clear_both"></div>
        </form>

      </div>
    </div>
    <div class="tip-waiting hide">
      <span class="icon-waiting-loading"></span>
      <span class="tip-waiting-desc">订单占位中，我们会在<span class="tip-waiting-remaining"></span>秒内反馈结果</span>
    </div>
    <div class="tip-occupy-fail hide">
      <div class="tip-occupy-fail-content">
        <span class="icon-occupy-fail icon-sorry"></span>
        <span class="tip-occupy-fail-desc">抱歉，仍未完成占位，您不必等待，占位成功后我们会以短信的形式提醒您，届时您可以前往我的订单付款。</span>
      </div>
      <div class="tip-btns"><div class="tip-btn tip-btn-confirm">确定</div></div>
    </div>

    <!--update box start-->
    <div class="update panel mt20" id="update_box" style="display: none;">
      <h2>
        <a class="toggle_btn fr J_toggle_btn">收起<span class="icon up_icon ml5"></span></a>
        <span class="icon update_icon mr10 vm"></span>
        <span class="vm">升级方案</span>
      </h2>
      <div class="panel_body">
        <ul class="list">
          <!-- 升级方案模板展示 -->
        </ul>
      </div>
    </div>
    <!--update box end-->

    <!--dfc box start-->
    <div class="dfc panel mt20" id="dfc_box" style="display: none;">
      <!-- 单房差模板展示 -->
    </div>
    <!--dfc box end-->

    <!-- visa box start -->
    <div class="visa panel mt20 hide" id="visa_box" style="display: none;"></div>
    <!-- visa box end -->
    <!-- 签证材料确认开始 -->
    <div id="visa_material_box" class="panel mt20" style="display: none;"></div>
    <!-- 签证材料确认结束 -->

    <!--bus place box start-->
    <c:if test="${fn:length(lineDeparture.lineDepartureInfos) > 0}">
    <div class="bus_place panel mt20 hide" id="bus_place" style="display: block;">
      <h2><span class="icon place_icon mr10 vm"></span><span class="vm">上车地点</span></h2>
      <div class="panel_body">
        <ul class="list">
          <li class="list_header">
            <span class="col5 fr">选择</span>
            <span class="col1">发车时间</span>
            <span class="col2">发车地点</span>
            <span class="col3">返回地点</span>
            <span class="col4">备注</span>
          </li>

          <c:forEach items="${lineDeparture.lineDepartureInfos}" var="info">
          <li class="list_row">
            <span class="col5 fr"><label class="input_checkbox enabled" data-id="${info.id}"><span class="tn_fontface"></span></label></span>
            <span class="col1"><fmt:formatDate value="${info.departureDate}" pattern="HH:mm:ss"/></span>
            <span class="col2">${info.originStation}</span>
            <span class="col3">${info.returnPlace}</span>
            <span class="col4">${info.remark}</span>
          </li>
          </c:forEach>

        </ul>

      </div>
    </div>
    </c:if>
    <!--bus place box end-->

    <!--receive box start-->
    <div class="receive panel mt20" id="receive" style="display: none;">
      <h2><span class="icon bus_icon mr10 vm"></span><span class="vm">接送信息</span></h2>
      <div class="panel_body">

        <form>
          <div class="field J_receipt_head">
            <div class="field_radio">
              <input type="radio" name="receipt" checked="checked">
              <label>不需要接送机</label>
            </div>
            <div class="field_radio">
              <input type="radio" data="need_receipt" name="receipt">
              <label>需要接送机</label>
            </div>
          </div>
          <div class="hidden_form hide">
            <div class="field mt15">
              <label>抵达时间：</label>
              <input class="J_Date arrive_time" type="text">
            </div>
            <div class="field mt15">
              <label>抵达班次：</label>
              <input class="J_arrive_num" type="text">
            </div>
            <div class="field mt15">
              <label>离开时间：</label>
              <input class="J_Date depart_time" type="text">
            </div>
            <div class="field mt15">
              <label>离开班次：</label>
              <input class="J_depart_num" type="text">
            </div>
          </div>
        </form>
      </div>
    </div>
    <!--receive box end-->

    <!--insurance box start-->
    <!--insurance box start-->
    <c:if test="${fn:length(insuranceList) > 0}">
    <div class="insurance panel mt20" id="insurance_box">
      <h2>
        <span class="icon security_icon mr10 vm"></span><span class="vm">保险方案</span>
        <span class="sub_title" style="display: none;">购买保险可以为您的旅途提供意外风险保障，建议您至少购买一项旅游保险</span>
      </h2>
      <div class="panel_body">
        <ul class="list">
          <li class="list_header">
            <span class="col2" style="padding-left:25px;width:15%;">险种</span>
            <span class="col1" style="width:50%;">名称</span>
            <span class="col4" style="width:10%;">单价</span>
            <span class="col5" style="width:10%;">选择</span>
          </li>


          <c:forEach items="${insuranceList}" var="insurance" varStatus="status">
          <li class="list_row <c:if test="${status.index > 0}">hide</c:if>">
            <span class="col2" style="padding-left:25px;width:15%;">${insurance.category.name}</span>
            <span class="col1" style="width:50%;"><label class="text" title="${insurance.name}">${insurance.name}</label><label class="tn_fontface"></label></span>
            <span class="col4" style="width:10%;">￥${insurance.price}/人</span>
            <span class="col5" style="width:10%;"><label
                    class="input_checkbox <c:if test="${status.index == 0}">enabled</c:if>" data-id="${insurance.id}"
                    data-name="${insurance.name}" data-price="${insurance.price}"><span
                    class="tn_fontface"></span></label></span>
          </li>
          <li class="list_detail hide">
            <span class="icon arrow_up_icon" style="left:260px;"></span>
            <p class="mt20"></p>
            <dl class="mt10">
              <dt>保险类型</dt>
              <dd class="mt10">${insurance.category.name}</dd>
            </dl>
            <dl class="mt10">
              <dt>保险描述</dt>
              <dd class="mt10">${insurance.description}</dd>
            </dl>
            <dl class="mt10">
              <dt>投保须知</dt>
              <dd class="mt10">${insurance.notice}</dd>
            </dl>

            <dl class="mt10 mt10-float">
              <dt>详细信息请阅读</dt>
              <dd><a href="javascript:;" target="_blank">保险条款</a></dd>
            </dl>

          </li>
          </c:forEach>
        </ul>

        <a class="show_more J_more_btn" href="javascript:;">更多保险</a>

      </div>
    </div>
    </c:if>
    <!--insurance box end-->
    <!-- 保险 start -->
    <!-- 保险 end --><!--insurance box end-->

    <!--favourable box start-->
    <%--<div class="favourable panel mt20" id="favourable_box">--%>
      <%--<h2><span class="icon favourable_icon mr10 vm"></span><span class="vm">优惠方案</span><span class="sub_title"></span></h2>--%>
      <%--<div class="panel_body"><ul class="list J_list">--%>
        <%--<li class="list_header">--%>
          <%--<span class="col1">优惠类型</span>--%>
          <%--<span class="col2">优惠活动</span>--%>
          <%--<span class="col3">优惠金额</span>--%>
          <%--<span class="col4">选择</span>--%>
        <%--</li>--%>



        <%--<li class="list_coupon_form">--%>
          <%--<span class="col1">优惠券</span>--%>
                 <%--<span class="col2 coupon_add_form">--%>
                    <%--<input class="coupon_add_inp" type="text" placeholder="请输入优惠券券号">--%>
                    <%--<a class="coupon_add_btn J_addPromotionCode" href="javascript:;">添加</a>--%>
                <%--</span>--%>
        <%--</li>--%>






        <%--<li class="list_row ">--%>
          <%--<span class="col1">套餐优惠</span>--%>
          <%--<span class="col2">三亚专享2人立减1000<label class="tn_fontface"></label></span>--%>
          <%--<span class="col3">-￥1000</span>--%>
        <%--<span class="col4">--%>
            <%--<label class="input_checkbox   ">--%>
              <%--<span class="tn_fontface"></span>--%>
            <%--</label>--%>
        <%--</span>--%>
        <%--</li>--%>

        <%--<li class="list_detail hide">--%>
          <%--<span class="icon arrow_up_icon"></span>--%>
          <%--<p></p><p>活动名称：<span style="color: rgb(255, 0, 0);"><strong>牛牛"大放血"，2人立减1000</strong></span></p><p>1、预订该线路指定活动团期（非活动团期不可参加此活动），2人即可享受立减1000，提交订单时，勾选“立减优惠”即可扣减相应金额，保险不含。</p><p>2、儿童同行不享受优惠政策（儿童标准以途牛旅游网具体产品线路“费用说明”中公布为准）。</p><p>3、本次活动按双人出行共用一间房核算单人价格，最终成行价格将根据所选出发日期、出行人数、住宿房型、交通以及所选附加服务的不同而有所不同，以客服与您确认需求后核算价格为准。</p><p>4、本活动名额有限，售完即止，不与途牛旅游网其他任何优惠活动同享（旅游券除外）。</p><p>5、途牛旅游网在法律允许的范围内保留对本次活动的变更权，包括但不限于参加资格、消费时间及奖励方式、暂停或取消本活动等。</p><p></p>--%>
        <%--</li>--%>

        <%--<li class="list_row  ">--%>
          <%--<span class="col1">通用优惠</span>--%>
          <%--<span class="col2">抵用券<label class="tn_fontface"></label></span>--%>
          <%--<span class="col3">￥0</span>--%>
                    <%--<span class="col4">--%>
                        <%--<label class="input_checkbox  ">--%>
                          <%--<span class="tn_fontface"></span>--%>
                        <%--</label>--%>
                    <%--</span>--%>
        <%--</li>--%>
        <%--<li class="list_detail hide">--%>
          <%--<span class="icon arrow_up_icon"></span>--%>
          <%--<p>途牛五星会员享受任意时间预订产品时抵用券双倍使用、双倍赠送特权。<a href="http://www.tuniu.com/static/yh/" target="_blank">什么是抵用劵?</a></p>--%>
        <%--</li>--%>

        <%--<li class="list_row coupon_row">--%>
          <%--<span class="col1"></span>--%>
          <%--<span class="col2">旅游券 账户总余额：￥0<label class="tn_fontface"></label></span>--%>
          <%--<span class="col3">本次可用：¥0</span>--%>
          <%--<span class="col4"><label>本次使用：<input type="text" class="J_travel_coupon" value=""></label></span>--%>
        <%--</li>--%>

        <%--<li class="list_row ">--%>
          <%--<span class="col1">返券优惠</span>--%>
          <%--<span class="col2">定国内游享低价流量<label class="tn_fontface"></label></span>--%>
          <%--<span class="col3">￥15</span>--%>
                <%--<span class="col4">--%>
                    <%----%>
                <%--</span>--%>
        <%--</li>--%>

        <%--<li class="list_detail hide">--%>
          <%--<span class="icon arrow_up_icon"></span>--%>
          <%--<p>预定国内游，特价享流量<br>活动时间：6月6日-6月15日<br>用券时间：6月6日-6月15日<br>活动规则：<br>（1） 下单任意国内游（周边游不包含在内），您会收到一张价值15元的流量券。<br>（2） 该券只限购买移动2GB全国流量包时抵用15元<br>（3） 充值流量2天之内到账，用户可拨打10086查询相关套餐情况。<br>（4） 流量当月有效。<br></p><p>（5） 该券不与其他活动同时使用。</p><p>（6） 如果任何问题，请咨询途牛客服。</p><p>（7） 该券仅限APP使用，使用方式：登录途牛APP——选择通信*WIFI入口——点击充值流量即可使用。<br></p><p></p>--%>
        <%--</li>--%>

      <%--</ul>--%>
      <%--</div>--%>
    <%--</div>--%>
    <!--favourable box end-->

    <!--receipt box start-->
    <div class="receipt panel mt20" id="receipt_box">
      <h2>
        <span class="icon receipt_icon mr10 vm"></span><span class="vm">发票信息</span>
      </h2>
      <div class="panel_body">
        <form>
          <div class="hide">
            <!--<div class="more fr J_more_btn">
                    <label class="input_checkbox">我需要开具多张发票<span class="tn_fontface ml5">&#xe618;</span></label>
                </div>-->
            <div class="tip"><span class="icon info_icon mr5"></span>您的发票将于您出游归来后的5个工作日开具并由快递寄出，请注意查收。</div>
          </div>
          <!--<div class = "hide mt20 tip_for_more_receipt">由于您开具了多张发票，请在提交订单后，前往”会员中心“为当前订单填写发票信息</div>-->
          <div class="field J_receipt_head">
            <div class="field_radio">
              <input type="radio" name="receipt" checked="checked">
              <label>不需要发票</label>
            </div>
            <div class="field_radio">
              <input type="radio" data="need_receipt" name="receipt">
              <label>需要发票</label>
            </div>
          </div>
          <div class="field hide J_amount"><label class="field_label">发票金额：</label><span class="rmb"></span>
            （<span class="J_insurance" style="display: inline;">保险费用￥<span class="J_insurance_price"></span>，自动开具保险专用发票；</span>
            抵用券、旅游券支付的发票不开具发票）
          </div>
          <div class="field hide"><label class="field_label">发票明细：</label>旅游费</div>
          <div class="field hide J_title"><label class="field_label">发票抬头：</label><input class="field_input w_220" type="text" id="invoiceTitle" value="${invoice.title}"><span class="grey ml10">请填写个人姓名或公司名称</span></div>

          <div class="J_receipt_view hide">
            <!-- 常用配送地址展示 -->
          </div>

          <div class="field mt20 J_name hide"><label class="field_label">收件人：</label><input data-vtype="cn_name" class="field_input w_220" type="text" id="invoiceName" value="${invoice.name}"></div>
          <div class="field mt15 J_phone hide"><label class="field_label">联系电话：</label><input data-vtype="mobile_number" class="field_input w_220" type="text" id="invoiceTelephone" value="${invoice.telephone}"></div>
          <div class="field mt15 J_provice_city hide">
            <label class="field_label">邮寄地址：</label><div class="input_select w_105 mr10 J_provice">
            <div class="s_cnum">
              <span class="sc_arrow"></span>
              <p class="s_ccon invoiceAddress" title="">请选择</p>
            </div>
            <ul class="s_clist hide" id="province_list">
            </ul>
          </div>
            <div class="input_select w_105 hide J_city">
              <div class="s_cnum">
                <span class="sc_arrow"></span>
                <p class="s_ccon invoiceAddress" title=""></p>
              </div>
              <ul class="s_clist J_city_option hide" id="city_list">
                <li title="">请选择省份</li>
                <!-- 联动城市模板展示 -->
              </ul>
            </div>
          </div>
          <div class="field hide J_detail_address">
            <label class="field_label"></label><textarea data-vtype="address" class="field_input w_220 invoiceAddress" type="text" style="width:318px;height:76px;"></textarea>
          </div>
        </form>
      </div>
    </div>
    <!--receipt box end-->


    <!--order to next start-->
    <div class="order_to_next mt20">
      <a class="back" href="/line_detail_${linetypeprice.line.id}.html" target="_self">&lt;返回上一步</a>
      <div class="order_sum" id="sum-cost"><label class="vm">订单金额：</label><span class="sign vm">￥</span><span class="number vm">6588</span></div>
      <input type="hidden" id="orderProcess" value="0">
      <a class="next vm J_save_order" onclick="LineOrder.submitOrder()">我已阅读预订须知，提交订单</a>
    </div>
    <!--order to next end-->

    <!--notice box start-->
    <div class="notice panel mt20">
      <h2><span class="icon notice_icon mr10 vm"></span><span class="vm">预订须知</span></h2>
      <div class="panel_body">
        <dl>
            <dt>${lineexplain.orderKnow}</dt>
            <dd>${lineexplain.orderContext}</dd>
            <dt>${lineexplain.tip}</dt>
            <dd>${lineexplain.tipContext}</dd>
        </dl>
      </div>
    </div>
    <!--notice box end-->
  </div>

  <!--summary box start-->
  <div class="summary" style="top: 50px; left: 0px; position: relative;">
    <div class="top"></div>
    <div class="inner">
      <div id="cost_summary">
        <h2>结算信息</h2>
        <dl class="cost">
          <dt>出游团费</dt>

          <dd class="mt5 clearfix" id="right-adult">
            <span class="number"></span>成人 x <span class="price"></span>
            <span class="amount"></span>
          </dd>
          <dd class="clearfix" id="right-child">
            <span class="number"></span>儿童 x <span class="price"></span>
            <span class="amount"></span>
          </dd>

          <div id="single-price">
            <dt class="mt20">单房差费用</dt>
            <dd><span class="amount">￥420</span></dd>
          </div>

          <div id="insurance-price">
          <dt class="mt20">保险费用</dt>

          <dd class="insurance ">

            <p title="新华国内旅行意外保险15款(适用于出游4~5天)">
              新华国内旅行意外保险15款(适用于出游4~5天)
            </p>
            <span class="number">2人</span> x <span class="price">￥40</span>
            <span class="amount">￥80</span>
          </dd>

          <dd class="insurance  last ">

            <p title="太平洋旅程取消保险升级款计划4">
              太平洋旅程取消保险升级款计划4
            </p>
            <span class="number">2人</span> x <span class="price">￥45</span>
            <span class="amount">￥90</span>
          </dd>
          </div>




          <%--<dt class="mt20">优惠金额</dt>--%>

          <%--<dd class="" style="line-height:13px;">--%>
            <%--<p>--%>
              <%--定国内游享低价流量--%>
            <%--</p>--%>
          <%--</dd>--%>
          <%--<dd class="mt5">--%>
            <%--<span class="number">&nbsp;</span><span class="price">&nbsp;</span><span class="amount">--%>

<%--返￥15优惠券--%>

<%--</span>--%>
          <%--</dd>--%>


        </dl>

        <div class="action" id="right-cost">
          <label>订单金额：</label><sup class="sign"></sup><span class="number">￥6588</span><i class="icon-question" style="display:none;"></i>
          <div><a class="btn J_save_order" onclick="LineOrder.submitOrder()">下一步</a></div>
        </div>
      </div>
    </div>
    <div class="bottom"></div>
    <div class="float-tip float-tip-child-price hide">
      <span class="small-arrow">◆</span>
      <span class="big-arrow">◆</span>
      <div class="float-tip-content">由于儿童价实时更新，儿童的火车票价格暂时按成人价计算。订单金额以最终付款页面的金额为准。</div>
    </div>
  </div>
  <!--summary box end-->

</div>

<!--float airplane box start-->


<!--choose bus box start-->
<!--choose bus box end-->

<!--float airplane box end-->
<div class="mask J_mask"></div>
<!--change-train start-->
<div id="change-train" class="hide" data-role="hook-once"></div>
<!--change-train end-->
<!--change-hotel start-->
<div id="change-hotel" class="hide" data-role="hook-once"></div>
<!--change-hotel end-->

<!-- 旅游券验证模块 start -->
<!-- 旅游券验证模板 end -->
<!-- 航班信息模板 start  -->
<!-- 航班信息模板 end  -->
<!-- 更改航班,套票模板  start -->
<!-- 更改航班,套票模板  end -->
<!-- 更改航班,散客票模板  start -->
<!-- 更改航班,散客票模板  end -->
<!-- 优惠方案模板 start -->
<!-- 优惠方案模板 end -->
<!-- 优惠方案 绑定联名卡模板 strat-->
<!-- 优惠方案 绑定联名卡模板 end-->

<!-- 升级方案 start -->
<!-- 升级方案 end -->
<!-- 单房差 start -->
<!-- 单房差 end -->


<!-- 签证模版 start -->
<!-- 签证模版 end -->


<!-- 结算信息模板 start -->
<!-- 结算信息模板 end -->
<!-- 常用配送地址 start -->
<!-- 常用配送地址 end -->
<!-- 城市联动,城市模板 start -->
<!-- 城市联动,城市模板 end -->
<!-- loading模板 start -->
<!-- loading模板 end -->
<!-- 接送服务 start -->
<!-- 接送服务 end -->
<!-- 上车服务 start -->
<!-- 上车服务 end -->
<!-- 重单验证 -->

<!-- 签证材料确认模板开始 -->
<!-- 签证材料确认模板结束 -->

<!-- 门市详情弹出框开始 -->
<!-- 门市详情弹出框结束 -->
<div class="hotel-tips loading"><i class="icon"></i><div class="hotel-tips-box">
</div>
</div>

<!-- #BeginLibraryItem "/lbi/foot2.lbi" -->
<!--foot-->
<jsp:include page="/WEB-INF/jsp/lvxbang/common/footer.jsp"></jsp:include>
<!--foot end--><!-- #EndLibraryItem --></body>
</html>

<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lvxbang/order/order_util.js" type="text/javascript"></script>
<script src="/js/lvxbang/order/line.js" type="text/javascript"></script>
<script src="/js/lib/common_util.js" type="text/javascript"></script>

<script type="text/html" id="tpl-select-date-item">
  <li data-date="{{day}}" data-adult-price="{{discountPrice + rebate}}" data-child-price="{{childPrice + childRebate}}"
      data-single-price="{{oasiaHotel}}">
    {{showDate}}({{showWeek}}) {{discountPrice + rebate}}元/人 {{if childPrice > 0}}{{childPrice +
    childRebate}}元/儿童{{/if}} {{if oasiaHotel > 0}}(单房差{{oasiaHotel}}元/人){{/if}}
  </li>
</script>
<script type="text/html" id="tpl-tourist-list-item1">
    <div class="tourist_info mt20" data-rel-uid="" data-type="adult">
      <input type="hidden" class="touristId" value="{{id}}"/>
      <div class="fl-panel">
        <div class="top">
          <input class="field_input touristType" type="hidden">
          第<span class="num">{{index}}</span>位
        </div>
        <div class="bottom ">
          成人
        </div>
      </div>
      <div class="fr-panel">
        <input type="hidden" name="isChild" value="">
        <input type="hidden" name="isFreeChild" value="">
        <div class="field J_pid" style="display:none;"><label class="field_label">出游人编号：</label><input class="field_input" type="hidden"></div>
        <div class="field J_name"><label class="field_label">中文姓名：</label><input class="field_input touristName" type="text" value="{{name}}" data-name=""></div>

        <div class="field J_en_name hide"><label class="field_label">英文姓名：</label><input class="field_input J_en_last_name" type="text" value="" placeholder="拼音姓"><input class="field_input en_first_name J_en_first_name" type="text" value="" placeholder="拼音名"><div class="error_tips"><div class="first_name_tip_wrap"></div><div class="last_name_tip_wrap"></div></div></div>

        <div class="field J_card_type"><label class="field_label">证件类型：</label><select class="field_select touristIdType">



          <option value="1" selected="">身份证</option>





          <option value="2">护照</option>





          <option value="3">军官证</option>





          <option value="4">港澳通行证</option>





          <option value="7">台胞证</option>



        </select>
        </div>
        <div class="field J_card_no"><label class="field_label">证件号码：</label><input class="field_input touristIdNum" type="text" value="{{idNumber}}"></div>
        <div class="field J_card_valid_date hide">
          <label class="field_label">证件有效期：</label>
          <select class="field_select_date mr12" data-year="">

            <option selected="selected">2016</option>

            <option>2017</option>

            <option>2018</option>

            <option>2019</option>

            <option>2020</option>

            <option>2021</option>

            <option>2022</option>

            <option>2023</option>

            <option>2024</option>

            <option>2025</option>

            <option>2026</option>

            <option>2027</option>

            <option>2028</option>

            <option>2029</option>

          </select>
          <select class="field_select_date mr12" data-month="">

            <option>1</option>

            <option>2</option>

            <option>3</option>

            <option>4</option>

            <option>5</option>

            <option>6</option>

            <option>7</option>

            <option>8</option>

            <option>9</option>

            <option>10</option>

            <option>11</option>

            <option selected="selected">12</option>

          </select>
          <select class="field_select_date" data-day="">

            <option>1</option>

            <option>2</option>

            <option>3</option>

            <option>4</option>

            <option>5</option>

            <option>6</option>

            <option>7</option>

            <option>8</option>

            <option>9</option>

            <option>10</option>

            <option>11</option>

            <option selected="selected">12</option>

            <option>13</option>

            <option>14</option>

            <option>15</option>

            <option>16</option>

            <option>17</option>

            <option>18</option>

            <option>19</option>

            <option>20</option>

            <option>21</option>

            <option>22</option>

            <option>23</option>

            <option>24</option>

            <option>25</option>

            <option>26</option>

            <option>27</option>

            <option>28</option>

            <option>29</option>

            <option>30</option>

            <option>31</option>

          </select>
        </div>
        <div class="field J_birth_date hide"><label class="field_label">出生日期：</label>
          <select class="field_select_date mr12" data-year="">


            <option class="ad">1920</option>

            <option class="ad">1921</option>

            <option class="ad">1922</option>

            <option class="ad">1923</option>

            <option class="ad">1924</option>

            <option class="ad">1925</option>

            <option class="ad">1926</option>

            <option class="ad">1927</option>

            <option class="ad">1928</option>

            <option class="ad">1929</option>

            <option class="ad">1930</option>

            <option class="ad">1931</option>

            <option class="ad">1932</option>

            <option class="ad">1933</option>

            <option class="ad">1934</option>

            <option class="ad">1935</option>

            <option class="ad">1936</option>

            <option class="ad">1937</option>

            <option class="ad">1938</option>

            <option class="ad">1939</option>

            <option class="ad">1940</option>

            <option class="ad">1941</option>

            <option class="ad">1942</option>

            <option class="ad">1943</option>

            <option class="ad">1944</option>

            <option class="ad">1945</option>

            <option class="ad">1946</option>

            <option class="ad">1947</option>

            <option class="ad">1948</option>

            <option class="ad">1949</option>

            <option class="ad">1950</option>

            <option class="ad">1951</option>

            <option class="ad">1952</option>

            <option class="ad">1953</option>

            <option class="ad">1954</option>

            <option class="ad">1955</option>

            <option class="ad">1956</option>

            <option class="ad">1957</option>

            <option class="ad">1958</option>

            <option class="ad">1959</option>

            <option class="ad">1960</option>

            <option class="ad">1961</option>

            <option class="ad">1962</option>

            <option class="ad">1963</option>

            <option class="ad">1964</option>

            <option class="ad">1965</option>

            <option class="ad">1966</option>

            <option class="ad">1967</option>

            <option class="ad">1968</option>

            <option class="ad">1969</option>

            <option class="ad">1970</option>

            <option class="ad">1971</option>

            <option class="ad">1972</option>

            <option class="ad">1973</option>

            <option class="ad">1974</option>

            <option class="ad">1975</option>

            <option class="ad">1976</option>

            <option class="ad">1977</option>

            <option class="ad">1978</option>

            <option class="ad">1979</option>

            <option class="ad">1980</option>

            <option class="ad">1981</option>

            <option class="ad">1982</option>

            <option class="ad">1983</option>

            <option class="ad">1984</option>

            <option selected="selected" class="ad">1985</option>

            <option class="ad">1986</option>

            <option class="ad">1987</option>

            <option class="ad">1988</option>

            <option class="ad">1989</option>

            <option class="ad">1990</option>

            <option class="ad">1991</option>

            <option class="ad">1992</option>

            <option class="ad">1993</option>

            <option class="ad">1994</option>

            <option class="ad">1995</option>

            <option class="ad">1996</option>

            <option class="ad">1997</option>

            <option class="ad">1998</option>

            <option class="ad">1999</option>

            <option class="ad">2000</option>

            <option class="ad">2001</option>

            <option class="ad">2002</option>

            <option class="ad">2003</option>

            <option class="ad">2004</option>

            <option class="ad">2005</option>

            <option class="ad">2006</option>

            <option class="ad">2007</option>

            <option class="ad">2008</option>

            <option class="ad">2009</option>

            <option class="ad">2010</option>

            <option class="ad">2011</option>

            <option class="ad">2012</option>

            <option class="ad">2013</option>

            <option class="ad">2014</option>

            <option class="ad">2015</option>

            <option class="ad">2016</option>


          </select>
          <select class="field_select_date mr12" data-month="">

            <option>1</option>

            <option>2</option>

            <option>3</option>

            <option>4</option>

            <option>5</option>

            <option>6</option>

            <option>7</option>

            <option>8</option>

            <option>9</option>

            <option>10</option>

            <option>11</option>

            <option>12</option>

          </select>
          <select class="field_select_date" data-day="">

            <option>1</option>

            <option>2</option>

            <option>3</option>

            <option>4</option>

            <option>5</option>

            <option>6</option>

            <option>7</option>

            <option>8</option>

            <option>9</option>

            <option>10</option>

            <option>11</option>

            <option>12</option>

            <option>13</option>

            <option>14</option>

            <option>15</option>

            <option>16</option>

            <option>17</option>

            <option>18</option>

            <option>19</option>

            <option>20</option>

            <option>21</option>

            <option>22</option>

            <option>23</option>

            <option>24</option>

            <option>25</option>

            <option>26</option>

            <option>27</option>

            <option>28</option>

            <option>29</option>

            <option>30</option>

            <option>31</option>

          </select>
        </div>
        <div class="field J_sex hide"><label class="field_label">性别：</label><select class="field_select">
          <option value="0">女</option>
          <option value="1">男</option>
        </select>
        </div>

        <div class="field J_mobile_no "><label class="field_label">手机号码：</label><input class="field_input internation_code_box" placeholder="国际区号" type="text" value="0086" data-telcountryid="40" readonly="">-<input class="field_input mobile_box touristTel" type="text" value="{{tel}}"></div>
      </div>
    </div>
</script>
<script type="text/html" id="tpl-tourist-list-item2">
    <div class="tourist_info mt20" data-rel-uid="" data-type="adult">
      <input type="hidden" class="touristId" value="{{id}}"/>
      <div class="fl-panel">
        <div class="top">
          <input class="field_input touristType" type="hidden">
          第<span class="num">{{index}}</span>位
        </div>
        <div class="bottom is_child">
          儿童
        </div>
      </div>
      <div class="fr-panel">
        <input type="hidden" name="isChild" value="">
        <input type="hidden" name="isFreeChild" value="">
        <div class="field J_pid" style="display:none;"><label class="field_label">出游人编号：</label><input class="field_input" type="hidden"></div>
        <div class="field J_name"><label class="field_label">中文姓名：</label><input class="field_input touristName" type="text" value="{{name}}" data-name=""></div>

        <div class="field J_en_name hide"><label class="field_label">英文姓名：</label><input class="field_input J_en_last_name" type="text" value="" placeholder="拼音姓"><input class="field_input en_first_name J_en_first_name" type="text" value="" placeholder="拼音名"><div class="error_tips"><div class="first_name_tip_wrap"></div><div class="last_name_tip_wrap"></div></div></div>

        <div class="field J_card_type"><label class="field_label">证件类型：</label><select class="field_select touristIdType">



          <option value="1" selected="">身份证</option>





          <option value="2">护照</option>





          <option value="3">军官证</option>





          <option value="4">港澳通行证</option>





          <option value="7">台胞证</option>



        </select>
        </div>
        <div class="field J_card_no"><label class="field_label">证件号码：</label><input class="field_input touristIdNum" type="text" value="{{idNumber}}"></div>
        <div class="field J_card_valid_date hide">
          <label class="field_label">证件有效期：</label>
          <select class="field_select_date mr12" data-year="">

            <option selected="selected">2016</option>

            <option>2017</option>

            <option>2018</option>

            <option>2019</option>

            <option>2020</option>

            <option>2021</option>

            <option>2022</option>

            <option>2023</option>

            <option>2024</option>

            <option>2025</option>

            <option>2026</option>

            <option>2027</option>

            <option>2028</option>

            <option>2029</option>

          </select>
          <select class="field_select_date mr12" data-month="">

            <option>1</option>

            <option>2</option>

            <option>3</option>

            <option>4</option>

            <option>5</option>

            <option>6</option>

            <option>7</option>

            <option>8</option>

            <option>9</option>

            <option>10</option>

            <option>11</option>

            <option selected="selected">12</option>

          </select>
          <select class="field_select_date" data-day="">

            <option>1</option>

            <option>2</option>

            <option>3</option>

            <option>4</option>

            <option>5</option>

            <option>6</option>

            <option>7</option>

            <option>8</option>

            <option>9</option>

            <option>10</option>

            <option>11</option>

            <option selected="selected">12</option>

            <option>13</option>

            <option>14</option>

            <option>15</option>

            <option>16</option>

            <option>17</option>

            <option>18</option>

            <option>19</option>

            <option>20</option>

            <option>21</option>

            <option>22</option>

            <option>23</option>

            <option>24</option>

            <option>25</option>

            <option>26</option>

            <option>27</option>

            <option>28</option>

            <option>29</option>

            <option>30</option>

            <option>31</option>

          </select>
        </div>
        <div class="field J_birth_date hide"><label class="field_label">出生日期：</label>
          <select class="field_select_date mr12" data-year="">


            <option class="ad">1920</option>

            <option class="ad">1921</option>

            <option class="ad">1922</option>

            <option class="ad">1923</option>

            <option class="ad">1924</option>

            <option class="ad">1925</option>

            <option class="ad">1926</option>

            <option class="ad">1927</option>

            <option class="ad">1928</option>

            <option class="ad">1929</option>

            <option class="ad">1930</option>

            <option class="ad">1931</option>

            <option class="ad">1932</option>

            <option class="ad">1933</option>

            <option class="ad">1934</option>

            <option class="ad">1935</option>

            <option class="ad">1936</option>

            <option class="ad">1937</option>

            <option class="ad">1938</option>

            <option class="ad">1939</option>

            <option class="ad">1940</option>

            <option class="ad">1941</option>

            <option class="ad">1942</option>

            <option class="ad">1943</option>

            <option class="ad">1944</option>

            <option class="ad">1945</option>

            <option class="ad">1946</option>

            <option class="ad">1947</option>

            <option class="ad">1948</option>

            <option class="ad">1949</option>

            <option class="ad">1950</option>

            <option class="ad">1951</option>

            <option class="ad">1952</option>

            <option class="ad">1953</option>

            <option class="ad">1954</option>

            <option class="ad">1955</option>

            <option class="ad">1956</option>

            <option class="ad">1957</option>

            <option class="ad">1958</option>

            <option class="ad">1959</option>

            <option class="ad">1960</option>

            <option class="ad">1961</option>

            <option class="ad">1962</option>

            <option class="ad">1963</option>

            <option class="ad">1964</option>

            <option class="ad">1965</option>

            <option class="ad">1966</option>

            <option class="ad">1967</option>

            <option class="ad">1968</option>

            <option class="ad">1969</option>

            <option class="ad">1970</option>

            <option class="ad">1971</option>

            <option class="ad">1972</option>

            <option class="ad">1973</option>

            <option class="ad">1974</option>

            <option class="ad">1975</option>

            <option class="ad">1976</option>

            <option class="ad">1977</option>

            <option class="ad">1978</option>

            <option class="ad">1979</option>

            <option class="ad">1980</option>

            <option class="ad">1981</option>

            <option class="ad">1982</option>

            <option class="ad">1983</option>

            <option class="ad">1984</option>

            <option selected="selected" class="ad">1985</option>

            <option class="ad">1986</option>

            <option class="ad">1987</option>

            <option class="ad">1988</option>

            <option class="ad">1989</option>

            <option class="ad">1990</option>

            <option class="ad">1991</option>

            <option class="ad">1992</option>

            <option class="ad">1993</option>

            <option class="ad">1994</option>

            <option class="ad">1995</option>

            <option class="ad">1996</option>

            <option class="ad">1997</option>

            <option class="ad">1998</option>

            <option class="ad">1999</option>

            <option class="ad">2000</option>

            <option class="ad">2001</option>

            <option class="ad">2002</option>

            <option class="ad">2003</option>

            <option class="ad">2004</option>

            <option class="ad">2005</option>

            <option class="ad">2006</option>

            <option class="ad">2007</option>

            <option class="ad">2008</option>

            <option class="ad">2009</option>

            <option class="ad">2010</option>

            <option class="ad">2011</option>

            <option class="ad">2012</option>

            <option class="ad">2013</option>

            <option class="ad">2014</option>

            <option class="ad">2015</option>

            <option class="ad">2016</option>


          </select>
          <select class="field_select_date mr12" data-month="">

            <option>1</option>

            <option>2</option>

            <option>3</option>

            <option>4</option>

            <option>5</option>

            <option>6</option>

            <option>7</option>

            <option>8</option>

            <option>9</option>

            <option>10</option>

            <option>11</option>

            <option>12</option>

          </select>
          <select class="field_select_date" data-day="">

            <option>1</option>

            <option>2</option>

            <option>3</option>

            <option>4</option>

            <option>5</option>

            <option>6</option>

            <option>7</option>

            <option>8</option>

            <option>9</option>

            <option>10</option>

            <option>11</option>

            <option>12</option>

            <option>13</option>

            <option>14</option>

            <option>15</option>

            <option>16</option>

            <option>17</option>

            <option>18</option>

            <option>19</option>

            <option>20</option>

            <option>21</option>

            <option>22</option>

            <option>23</option>

            <option>24</option>

            <option>25</option>

            <option>26</option>

            <option>27</option>

            <option>28</option>

            <option>29</option>

            <option>30</option>

            <option>31</option>

          </select>
        </div>
        <div class="field J_sex hide"><label class="field_label">性别：</label><select class="field_select">
          <option value="0">女</option>
          <option value="1">男</option>
        </select>
        </div>
      </div>
    </div>
</script>
<script type="text/html" id="tpl-insurance-price-item">
  {{if index == 0}}<dt class="mt20">保险费用</dt>{{/if}}

  <dd class="insurance" data-id="{{id}}" data-price="{{num * price}}">
    <p title="{{name}}">
      {{name}}
    </p>
    <span class="number">{{num}}人</span> x <span class="price">￥{{price}}</span>
    <span class="amount">￥{{num * price}}</span>
  </dd>
</script>
<script type="text/html" id="tpl-province-city-item">
  <li title="{{name}}" data-id="{{id}}">{{name}}</li>
</script>