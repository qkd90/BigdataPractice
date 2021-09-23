<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2016-05-13,0013
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <title>我的红包</title>
  <meta name="keywords" content="index"/>
  <meta name="description" content="index"/>
  <link href="/css/tBase.css" rel="stylesheet" type="text/css">
  <link href="/css/announcement.css" rel="stylesheet" type="text/css">
  <link href="/css/float.css" rel="stylesheet" type="text/css">
  <link href="/css/member.css" rel="stylesheet" type="text/css">
  <link href="/js/lib/webuploader/webuploader.css" rel="stylesheet" type="text/css">
</head>
<body myname="Member" class="member_xx member_grxx" id="nav"><!-- #BeginLibraryItem "/lbi/head.lbi" -->
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
        <a href="javascript:;" class="checked not"><i></i>我的红包</a>
      </div>


      <div class="m_right fr my_red_packet">

        <div style="margin-top: 20px;margin-left: 10px;">
          <ul style="font-size: 17px;">
            <li style="float:left;margin-right: 25px;cursor: pointer;" class="redPacket redPackedList" status="unused" >未使用红包</li>
            <li style="float:left;margin-right: 25px;cursor: pointer;" class="redPackedList" status="used">已使用红包</li>
            <li style="float:left;cursor: pointer;" class="redPackedList" status="expired">已过期红包</li>
            <%--used("已使用"), unused("未使用"), expired("已失效"), unavailable("不可用");--%>
          </ul>
          <a href="javascript:;" class="useRule" style="float: right;color: #01af63;">红包使用规则</a>
        </div>
        <br/>
        <div style="margin-top: 13px;background-color: #eeeff5;height: 30px;border-top: solid 1px #C9B7B7;">
          <ul style="font-size: 15px;padding-top: 8px;padding-left: 10px;">
            <li style="float:left;margin-right: 60px;">红包名称</li>
            <li style="float:left;margin-right: 50px;">红包金额</li>
            <li style="float:left;margin-right: 80px;">使用范围</li>
            <li style="float:left;margin-right: 60px;">有效期</li>
            <li style="float:left;">优惠说明</li>
          </ul>
        </div>
        <div style="">
          <ul style="font-size: 13px;padding-top: 8px;padding-left: 10px;color: #666;" id="redPacketUl">
          </ul>
        </div>
        <p class="cl"></p>

        <div class="m-pager st cl" style="margin-top: 20px;">

        </div>
      </div>

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
<script src="/js/lib/Time/WdatePicker.js" type="text/javascript"></script>
<script src="/js/lib/jquery-form.js" type="text/javascript"></script>
<script src="/js/lvxbang/user/myCoupon.js" type="text/javascript"></script>
<script type="text/html" id="tpl-red-packet-item">
  <li style="border-bottom: dashed 1px #ccc;height: 42px;margin-top: 10px;">
    <div style="float:left;margin-top: 6px;width:120px;">{{coupon.name}}</div>
    <div style="float:left;width:100px;color:red;margin-top: 6px;">
      <span style="border:solid 1px red;padding: 2px;margin-right: 2px;">立减</span><span>￥{{coupon.faceValue}}</span>
    </div>
    {{if coupon.limitProductTypes>10}}
    <div style="float:left;width:120px;">{{coupon.limitProductTypes}}</div>
    {{else}}
    <div style="float:left;width:100px;margin-top: 6px;margin-left: 10px;">{{coupon.limitProductTypes}}</div>
    {{/if}}

    <div style="float:left;width: 100px;margin-left: 30px;margin-right: 14px;">
                                <span>
                                    {{validStart}}至{{validEnd}}
                                </span>
    </div>
    <div style="float:left;width:150px;height: auto;padding-bottom: 20px;" class="moreknow">
      <%--<c:if test="${fn:length(onebeans.info)>100 }">${ fn:substring( onebeans.info ,0,100)} ...</c:if>--%>
      {{subRepacket coupon.instructions}}
      {{if coupon.instructions.length>10}}
                                    <span style="display: none;border: 1px dashed #34bf82;
                                                 float:right; background-color:#fff;
                                                 width:auto;padding: 10px;
                                                 margin-top: 5px;max-width: 300px;" class="posiA">
                                        {{coupon.instructions}}
                                    </span>
      {{/if}}
    </div>
  </li>
</script>

