<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/2/17
  Time: 13:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="../../common/common141.jsp"%>
  <script type="text/javascript" src="/js/weixinh5/productActivities/editActivity.js"></script>
    <title>新增活动</title>
  <style type="text/css">
    body {
      font-size: 14px;
      padding: 0px;
    }
    .row_hd {
      height: 27px;
      line-height: 27px;
      border-bottom: 1px solid #ddd;
      background: #f4f4f4;
      margin: 5px 15px 10px 15px;
      padding-left: 10px;
      font-weight: 700;
      color: #666;
    }
    .label_class {
      width: 80px;
      float: left;
      text-align: right;
      margin-right: 15px;
    }
    .coupon_class {
      margin-top: 5px;
    }
    .botton_class {
      margin-top: 10px;
      margin-left: 150px;
    }
    .botton_class a{
      margin-right: 20px;
    }
  </style>
</head>
<body style="background-color: white;">
<div class="row_hd">活动基本信息</div>

    <div>
      <label class="label_class">优惠券名称:</label>
      <input type="hidden" value="${activities.id}" name="id" id="ipt_lastId"/>
      <input id="ipt_atype" type="hidden" value="${activities.type}"/>
      <input id="com_activityName" class="easyui-combobox"  style="width:200px;" data-options="
		valueField: 'label',
		textField: 'value',
		data: [{
			label: 'coupon',
			value: '优惠券',
			selected:true
            },{
			label: 'flashsale',
			value: '限时抢购'
		}]" />
    </div>
  <form id="bodyForm">
    <div class="coupon_class limit">
      <label class="label_class">活动名称:</label>
      <input type="text" id="ipt_name" value="${activities.name}" class="easyui-textbox" name="name" data-options="" style="width:300px">
    </div>
    <div class="coupon_class">
      <label class="label_class">面值:</label>
      <input type="text" id="ipt_faceValue"  value="${activities.faceValue}" class="easyui-numberbox " name="faceValue" data-options="" style="width:300px">
    </div>
    <div class="coupon_class">
      <label class="label_class">发行总量:</label>
      <input type="text" id="ipt_number"  value="${activities.number}" class="easyui-numberbox "  name="number" style="width:300px" data-options="min:0"/>
    </div>
    <div class="coupon_class limit">
      <label class="label_class">有效时间:</label>
      <input class="easyui-datetimebox" id="ipt_startTime"  value="${activities.startTime}" name="startTime" data-options="showSeconds:true" style="width:200px"/>
      -
      <input class="easyui-datetimebox" id="ipt_endTime"  value="${activities.endTime}" name="endTime" data-options="showSeconds:true" style="width:200px">
    </div>
    <div class="coupon_class">
      <label class="label_class">使用条件:</label>
      <input type="radio" id="radio_lowprice_yes" name="opt_radio" onclick="EditActivity.selCondition('yes')"/>
      满
      <input type="text" id="ipt_lowPrice" class="easyui-numberbox"  value="${activities.lowestPrice}" name="lowestPrice" data-options="" style="width:50px"/>元使用
      <input type="radio" id="radio_lowprice_no" checked="checked" name="opt_radio" style="margin-left: 30px;" onclick="EditActivity.selCondition('no')"/> 无条件使用
    </div>
    <div class="coupon_class limit">
      <label class="label_class">每人限领:</label>
      <input type="radio" id="radio_limit_no"  checked="checked" onclick="EditActivity.selLimitGet('no')" name="opt_limit"/>无条件限领

      <input type="radio" id="radio_limit_yes" name="opt_limit" onclick="EditActivity.selLimitGet('yes')" style="margin-left: 30px;"/>限领
      <input type="text" id="perLimitId" class="easyui-numberbox"  value="${activities.perCounts}" name="perCounts" data-options="" style="width:50px"/>张
    </div>
    <div class="coupon_class limit">
      <label class="label_class">商品分类:</label>
      <select class="easyui-combobox" id="ipt_productType" value="${activities.productType}" name="productType" style="width:200px;">
        <option value="line">线路</option>
      </select>
    </div>
    <div class="coupon_class limit">
      <label class="label_class">可使用商品:</label>
      <input type="radio" id="radio_product_all" name="radio_product" value="allProduct" onclick="EditActivity.operaProduct('allProduct')" checked="checked"/>全部商品
      <input type="radio" id="radio_product_some" name="radio_product" value="someProduct" onclick="EditActivity.operaProduct('someProduct')" style="margin-left: 30px;"/>部份商品
    </div>
    <div id="product_list" style="margin-left: 95px">
      <div class="easyui-panel" title="产品列表"
           style="width:500px;height:320px;background:#fafafa;"
           data-options="closable:false">
        <div class="easyui-layout" data-options="fit:true">
          <div data-options="region:'north',border:false" style="height:35px;">
            <form >
              <div style="float: left;margin-left: 15px;margin-top: 3px;">
                <label style="float: left;margin-left: 3px;">线路分类:</label>
                <select class="easyui-combobox" id="ipt_proType" name="proType" style="width:80px;">
                  <option value="line">路线</option>
                </select>
              </div>
              <div style="float: left;margin-left: 20px;margin-right: 10px;margin-top: 3px;">
                <label style="float: left;margin-left: 3px;">产品名称:</label>
                <input class="easyui-textbox" id="ipt_productName"  name="productName" style="width:150px">
              </div>
              <div style="margin-top: 3px;">
                <a class="easyui-linkbutton" onclick="EditActivity.doSearch()" >搜索</a>
              </div>
            </form>
          </div>
          <div data-options="region:'center',border:false">
            <table id="dg_product"></table>
          </div>
        </div>
      </div>
    </div>

    <div class="coupon_class limit">
      <label class="label_class">使用说明:</label>
      <input id="ipt_hidden_instructions" type="hidden" value="${activities.instructions}">
      <input class="easyui-textbox" id="ipt_instructions" name="instructions" value="${activities.instructions}" data-options="multiline:true" style="width:300px;height: 100px;">
    </div>
    <div class="row_hd info">优惠券推广信息</div>
    <div class="coupon_class">
      <label class="label_class">推广方式:</label>
      <input id="ipt_promotway" type="hidden" value="${activities.promotway}">
      <input type="radio" id="radio_promotway_buyer" name="promotway" onclick="EditActivity.selectTuig('com_buyer')" value="buyerget" checked="checked"/>买家领取
      <input type="radio" id="radio_promotway_saler" name="promotway" onclick="EditActivity.selectTuig('com_saler')" value="sellersend" style="margin-left: 30px;"/>卖家发放
    </div>
    <div class="coupon_class">
      <label class="label_class">领取场景:</label>
      <input id="ipt_sceneType" type="hidden" value="${activities.sceneType}">
      <div id="com_buyer">
        <select  class="easyui-combobox" id="slt_buyerget" name="sceneType"  style="width:200px;">
          <option value="index_self_get">网店首页自主领取</option>
          <option value="comment_product">点评商品</option>
          <option value="user_register">新用户注册</option>
          <option value="buy_product">购买商品</option>
        </select>
      </div>
      <div id="com_saler">
        <select  class="easyui-combobox" id="slt_sellersend" name="sceneType" style="width:200px;">
          <option value="customer_no_limit">客户类型无限制</option>
          <option value="ctrip_vip">携程会员</option>
          <option value="old_buyer">购买过本店商品的客户</option>
          <option value="attention_shop">关注过本店的客户</option>
        </select>
      </div>
    </div>
  </form>
  <div class="botton_class">
    <a class="easyui-linkbutton" onclick="EditActivity.saveActivity()" >保存</a>
    <a class="easyui-linkbutton" onclick="EditActivity.cancelSave()" >取消</a>
  </div>

</body>
</html>
