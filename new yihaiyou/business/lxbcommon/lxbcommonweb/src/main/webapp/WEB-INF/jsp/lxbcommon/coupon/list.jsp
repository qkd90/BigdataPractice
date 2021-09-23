<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/5/9
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>优惠券管理</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" href="/css/coupon.list.css">
</head>
<body>
<div title="优惠券管理" data-options="fit:true,border:false"
     style="width:100%;height:100%;" class="easyui-layout" id="content">
    <div id="coupon-searcher" style="padding:3px">
        <span>
            <select id="search-type">
                <option value="coupon.name" selected>优惠券名称</option>
                <%--<option value=""></option>--%>
            </select>
        </span>
        <input id="search-content" placeholder="输入查询内容"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">
        <span>
            <select id="search-status">
                <option value="" selected>所有状态</option>
                <option value="open">领取中</option>
                <option value="closed">已关闭</option>
                <%--<option value=""></option>--%>
            </select>
        </span>
        <span>
            <label>按</label>
            <select id="search-sort-property">
                <option value="validEnd" selected>有效期</option>
                <option value="faceValue">优惠券面值</option>
            </select>
        </span>
        <span>
            <select id="search-sort-type">
                <option value="desc" selected>降序</option>
                <option value="asc">升序</option>
            </select>
        </span>
        <a href="#" class="easyui-linkbutton" style="width: 80px;"
            onclick="CouponMgr.doSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" style="width: 90px;"
           onclick="CouponMgr.addCoupon()">添加优惠券</a>
    </div>
    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="couponDg"></table>
    </div>
    <!-- 数据表格 终-->
    <div class="easyui-dialog coupon_dialog" id="coupon_panel" closed="true" style="width:550px;">
        <form id="coupon_form" method="post" enctype="multipart/form-data" action="">
            <input type="hidden" name="coupon.id">
            <ul>
                <li>
                    <label>优惠券名称：</label>
                    <input type="text" name="coupon.name" class="easyui-textbox" data-options="required:true">
                </li>
                <li>
                    <label>优&nbsp;惠&nbsp;类&nbsp;型：</label>
                    <input class="easyui-combobox" data-options="required:true"
                           name="coupon.couponDiscountType" id="couponDiscountType">
                </li>
                <li>
                    <div id="face_value_div">
                        <label>面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值：</label>
                        <input name="coupon.faceValue" class="easyui-numberbox"
                               data-options="required:true" precision="2">
                        <label>元/折</label>
                        <div id="max_discount_div" style="float: right; margin-right: 20px;">
                            <label>最高减免: </label>
                            <input id="maxDiscount" name="coupon.maxDiscount"
                                   class="easyui-numberbox" precision="2">
                            <label>元</label>
                        </div>
                    </div>
                </li>
                <li>
                    <label>发&nbsp;行&nbsp;数&nbsp;量：</label>
                    <input name="coupon.circulation" class="easyui-numberbox" data-options="required:true"
                           onkeyup="value=value.replace(/[^\d]/g,'')">
                    <label>张</label>
                    <div style="float: right; margin-right: 20px;">
                        <label>可领数量: </label>
                        <input id="availableNum" name="coupon.availableNum"
                               class="easyui-numberbox">
                        <label>张</label>
                    </div>.
                </li>
                <li>
                    <label>有效期类型：</label>
                    <input name="coupon.couponValidType" id="couponValidType"
                           class="easyui-combobox" data-options="required:true">
                    <div id="valid_type_days" style="float: right;">
                        <label>领&nbsp;取&nbsp;后&nbsp;</label>
                        <input id="validDays" name="coupon.validDays" class="easyui-textbox"
                               onkeyup="value=value.replace(/[^\d]/g,'')">
                        <label>天内使用有效</label>
                    </div>
                    <div id="valid_type_range" style="margin-top: 8px;">
                        <label>有效期范围：</label>
                        <input name="validStart" id="validStart" class="Wdate" data-options="required:true"
                               style="width: 150px;padding: 2px; font-size: 13px;"
                               onclick="WdatePicker({readOnly:true,isShowClear:false,
                               doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss',errDealMode:1,
                               maxDate: '#F{$dp.$D(\'validEnd\')}',
                               minDate: '%y-%M-%d {%H+1}:%m:%s',
                               })">
                        <label>-</label>
                        <input name="validEnd" id="validEnd" class="Wdate" data-options="required:true"
                               style="width: 150px;padding: 2px; font-size: 13px;"
                               onclick="WdatePicker({readOnly:true,isShowClear:false,
                               doubleCalendar:false,dateFmt:'yyyy-MM-dd HH:mm:ss',errDealMode:1,
                               minDate: '#F{$dp.$D(\'validStart\')||\'%y-%M-%d {%H+1}:%m:%s\'}'
                               })">
                    </div>
                </li>
                <li>
                    <label>使&nbsp;用&nbsp;条&nbsp;件： </label>
                    <input class="easyui-combobox" data-options="required:true"
                           name="coupon.couponUseConditionType" id="use_condition_combo">
                    <div id="use_condition" style="float: right; margin-right: 20px;">
                        <label>消&nbsp;费&nbsp;满：</label>
                        <input class="easyui-numberbox" precision="2"
                               data-options="required:true" name="coupon.useCondition">
                        <label>元使用</label>
                    </div>
                </li>
                <li>
                    <label>领&nbsp;取&nbsp;限&nbsp;制：</label>
                    <input class="easyui-combobox" data-options="required:true"
                           name="coupon.couponReceiveLimitType" id="couponReceiveLimitType">
                    <div id="receive_type_num" style="float: right; margin-right: 44px;">
                        <label>每人限领：</label>
                        <input id="receiveLimit" class="easyui-numberbox" value="1"
                               name="coupon.receiveLimit" onkeyup="value=value.replace(/[^\d]/g,'')">
                        <label>张</label>
                    </div>
                </li>
                <li>
                    <label>适&nbsp;用&nbsp;分&nbsp;类：</label>
                    <input class="easyui-combobox" data-options="required:true"
                           name="coupon.limitProductTypes" id="limitProductTypes">
                    <div style="float: right; margin-right: 60px;">
                        <label>状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态：</label>
                        <input class="easyu-combobox" data-options="required:true"
                               name="coupon.status" id="couponStatus">
                    </div>
                </li>
                <!-- TODO -->
                <li style="display: none;">
                    <label>适&nbsp;用&nbsp;商&nbsp;品：</label>
                    <input class="easyui-combogrid" name="coupon.limitTargetIds" id="limitTargetIds">
                </li>
                <li>
                    <label>使&nbsp;用&nbsp;说&nbsp;明：</label>
                    <textarea class="vam" name="coupon.instructions" style="width: 370px; height: 80px; background: #FFFFFF;"></textarea>
                </li>
                <li>
                    <label>限&nbsp;制&nbsp;说&nbsp;明：</label>
                    <textarea class="vam" name="coupon.limitInfo" style="width: 370px; height: 80px; background: #FFFFFF;"></textarea>
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="CouponMgr.commitForm('coupon_form', 'coupon_panel')">保存优惠券</a>
            </div>
        </form>
    </div>
</div>
<script src="/js/lxbcommon/coupon/list.js" type="text/javascript"></script>
</body>
</html>
