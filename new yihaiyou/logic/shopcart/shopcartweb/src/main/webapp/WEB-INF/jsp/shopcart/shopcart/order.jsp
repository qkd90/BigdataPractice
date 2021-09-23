<%@ page import="com.data.data.hmly.service.entity.Member" %>
<%@ page import="com.data.data.hmly.service.entity.SysUser" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="jx" uri="/includeTag" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>提交订单-个人</title>
    <link href="${mallConfig.resourcePath}/css/bootstrap.min.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/base.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <link href="${mallConfig.resourcePath}/css/order.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <!--日期控件css-->
    <link href="${mallConfig.resourcePath}/css/date/default.css?v=${mallConfig.resourceVersion}" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!--头部开始-->
<%@include file="/WEB-INF/jsp/mall/common/header.jsp"%>
<!--头部结束-->

<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="clearfix here">您的位置：<a href="/">首页</a> &gt; <a href="#">产品预定</a></div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-9">
            <p><img src="/images/order1.jpg" class="img-responsive"></p>
            <!--订单信息-->
            <div class="clearfix" id="orderlist">
                <input type="hidden" value="${productInfo.product.id}" id="productId"/>
                <input type="hidden" value="${productInfo.product.proType}" id="productType"/>

                <h1 class="title"><span>${productInfo.product.name}</span><span><a href="#"><img src="/images/jsqr.jpg"></a></span>
                </h1>

                <div class="info clearfix">
                    <span>发团日期：<input id="datepicker1" type="text" data-zdp_direction="1" value="${productInfo.startDay}"></span>
                    <c:if test="${productInfo.endDay != null}">
                        <span>返程日期：${productInfo.endDay}</span>
                    </c:if>
                    <span>出发地：${productInfo.startCity.name}</span>
                    <span>供应商：${productInfo.product.user.sysUnit.name}</span>
                </div>
                <div class="box-order clearfix price-list-panel">
                    <h4>报名信息</h4>

                    <div class="price-list row box-order-content">
                        <span class="col-xs-4">价格名称</span>
                        <span class="col-xs-8">
                            <% if (CURRENT_USER == null || CURRENT_USER instanceof Member) {%>
                                <span class="col-xs-4">价格类型</span>
                                <span class="col-xs-3 text-center">分销价</span>
                                <span class="col-xs-5 text-center">人数</span>
                            <%} else {%>
                                <span class="col-xs-3">价格类型</span>
                                <span class="col-xs-2 text-center">分销价</span>
                                <span class="col-xs-3 text-center">利润</span>
                                <span class="col-xs-4 text-center">人数</span>
                            <%}%>
                            <%--<span class="col-xs-3 text-center">剩余库存</span>--%>
                        </span>

                    </div>
                    <div class="price-list clearfix box-order-content order-panel">
                        <input type="hidden" value="${productInfo.priceInfo.priceId}" class="price-id"/>
                        <input type="hidden" value="${productInfo.priceInfo.dateId}" class="price-date-id"/>
                        <input type="hidden" value="${productInfo.priceInfo.costType}" class="price-cost-type"/>
                        <span class="col-xs-4 price-name">${productInfo.priceInfo.costName}</span>
                        <span class="col-xs-8">
                            <span class="clearfix order-count-panel">
                                <% if (CURRENT_USER == null || CURRENT_USER instanceof Member) {%>
                                <span class="col-xs-4 price-name">成人价</span>
                                <span class="price col-xs-3 text-center price-amount adult-price">${productInfo.priceInfo.discountPrice}</span>
                                <span class="col-xs-5 text-center">
                                    <input name="" class="minus" type="button" value="-"/>
                                    <input name="" class="num adult-num" type="text" value="${adultCount>1?adultCount:1}"/>
                                    <input name="" class="plus" type="button" value="+"/>
                                    <input type="hidden" value="ADULT" class="price-type" }/>
                                </span>
                            <%} else {%>
                                <span class="col-xs-3 price-name">成人价</span>
                                <span class="price col-xs-2 text-center price-amount adult-price">${productInfo.priceInfo.discountPrice}</span>
                                <span class="col-xs-3 text-center">${productInfo.priceInfo.rebate}</span>
                                <span class="col-xs-4 text-center">
                                    <input name="" class="minus" type="button" value="-"/>
                                    <input name="" class="num adult-num" type="text" value="${adultCount>1?adultCount:1}"/>
                                    <input name="" class="plus" type="button" value="+"/>
                                    <input type="hidden" value="ADULT" class="price-type" }/>
                                </span>
                            <%}%>
                                <%--<span class="col-xs-3 text-center">300（没处理）</span>--%>
                            </span>
                            <c:if test="${productInfo.priceInfo.childPrice!=null}">
                            <span class="clearfix order-count-panel">
                                 <% if (CURRENT_USER == null || CURRENT_USER instanceof Member) {%>
                               <span class="col-xs-4 price-name">儿童价</span>
                                <span class="price col-xs-3 text-center price-amount child-price">${productInfo.priceInfo.childPrice}</span>
                                <span class="col-xs-5 text-center">
                                    <input name="" class="minus" type="button" value="-"/>
                                    <input name="" class="num child-num" type="text" value="${childCount}"/>
                                    <input name="" class="plus" type="button" value="+"/>
                                    <input type="hidden" value="CHILD" class="price-type"/>
                                </span>
                            <%} else {%>
                               <span class="col-xs-3 price-name">儿童价</span>
                                <span class="price col-xs-2 text-center price-amount child-price">${productInfo.priceInfo.childPrice}</span>
                                <span class="col-xs-3 text-center">${productInfo.priceInfo.childRebate}</span>
                                <span class="col-xs-4 text-center">
                                    <input name="" class="minus" type="button" value="-"/>
                                    <input name="" class="num child-num" type="text" value="${childCount}"/>
                                    <input name="" class="plus" type="button" value="+"/>
                                    <input type="hidden" value="CHILD" class="price-type"/>
                                </span>
                            <%}%>

                                <%--<span class="col-xs-3 text-center">300（没处理）</span>--%>
                            </span>
                            </c:if>
                        </span>

                    </div>

                </div>
                <div class="box-order clearfix" id="tourist-list-panel">
                    <h4>游客信息
                        <small>（供应商确认订单后方可看到游客联系方式，请放心填写） <!--<a href="#">批量导入</a>--></small>
                    </h4>
                    <div class="price-list box-order-content">
                        <form class="form-inline" id="kehu">
                            <div class="form-group">
                                <label><i>*</i>您的姓名：</label>
                                <input type="text" class="form-control tourist-name">
                            </div>
                            <div class="form-group">
                                <label><i>*</i>联系电话：</label>
                                <input type="text" class="form-control tourist-tel">
                            </div>
                            <div class="form-group">
                                <label>证件类型：</label>
                                <select class="form-control tourist-idType">
                                    <option value="IDCARD">身份证</option>
                                    <option value="OTHER">驾驶证</option>
                                </select>
                                <input type="text" class="form-control tourist-idNumber">
                            </div>
                            <div class="form-group">
                                <label>备注：</label>
                                <input type="text" class="form-control tourist-remark">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!--订单信息-->
            <!--业务联系信息-->
            <% if(CURRENT_USER != null && CURRENT_USER instanceof SysUser) {%>
            <div class="box-order clearfix">
                <h4>业务联系信息</h4>

                <div class="yewu row box-order-content">
                    <form class="form-inline">
                        <div class="col-xs-12">
                            <div class="form-group">
                                <label><i>*</i>&nbsp;&nbsp;经办人：</label>
                                <input type="text" class="form-control" id="contactName" value="${member.userName}">
                            </div>
                            <div class="form-group">
                                <label><i>*</i>&nbsp;&nbsp;手机：</label>
                                <input type="text" class="form-control" id="contactMobile" value="${member.mobile}">
                            </div>
                            <br><br>

                            <div class="form-group">
                                <label class="text-right">联系电话：</label>
                                <input type="text" class="form-control" id="contactTel" value="${member.telephone}">
                            </div>
                            <div class="form-group">
                                <label class="text-right">&nbsp;&nbsp;&nbsp;&nbsp;传真：</label>
                                <input type="text" class="form-control" id="contactFax">
                            </div>
                            <br><br>

                            <div class="form-group1">
                                <label class="text-right">订单备注：</label>
                                <textarea id="contactRemark" class="form-control" rows="6" placeholder="对游客情况进行进一步说明，或者填写团号，便于供应商帮您提供更优质的服务。" style="width:90%"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <% }%>
            <!--业务联系信息-->
            <div class="clearfix tijiao text-center">
                <input id="form-submit" name="" type="button" class="btn btn-warning form-submit" value="提交订单"><span>（若价格变动，请在提交订单后联系供应商改价）</span>
            </div>
            <!--温馨提醒-->
            <div class="box-order clearfix">
                <h4>温馨提示</h4>

                <div class="tixing box-order-content">
                    1. 平台对交易资金进行担保，在线支付后款项托管到平台，交易完成后再付款至供应商帐号，全面保障您的资金安全；<br>
                    2. 预定即时确认产品，订单提交后即可直接在线支付；预定二次确认后方可支付；<br>
                    3. 分销商下单后，在订单确认前，分销商可以取消订单。<br>
                </div>
            </div>
            <!--温馨提醒-->
        </div>
        <div class="col-xs-3" id="order-right">
            <!--费用清单-->
            <div id="qd">
                <h4 class="row">费用清单</h4>


                <div class="buylist row" id="price-detail-${productInfo.priceInfo.priceId}">
                    <h5 class="col-xs-12">${productInfo.priceInfo.costName}</h5>
                    <c:if test="${productInfo.priceInfo.childPrice!=null}">
                        <span class="col-xs-4">成人价</span>
                        <span class="col-xs-4 text-center">￥<span class="adult-price">${productInfo.priceInfo.discountPrice}</span>x<span class="adult-count">1</span></span>
                        <span class="col-xs-4 text-center price">￥<span class="adult-total-price">${productInfo.priceInfo.discountPrice}</span></span>
                        <span class="col-xs-4">儿童价</span>
                        <span class="col-xs-4 text-center">￥<span class="child-price">${productInfo.priceInfo.childPrice}</span>x<span class="child-count">0</span></span>
                        <span class="col-xs-4 text-center price">￥<span class="child-total-price">0</span></span>
                    </c:if>
                    <c:if test="${productInfo.priceInfo.childPrice==null}">
                        <span class="col-xs-4">价格</span>
                        <span class="col-xs-4 text-center">￥<span class="adult-price">${productInfo.priceInfo.discountPrice}</span>x<span class="adult-count">1</span></span>
                        <span class="col-xs-4 text-center price">￥<span class="adult-total-price">${productInfo.priceInfo.discountPrice}</span></span>
                    </c:if>
                </div>

                <div class="row text-right">
                    <div class="total">
                        <span class="all">订单总额：￥<span id="total-price">${productInfo.priceInfo.discountPrice}</span></span>
                        <span class="buytotal">应付总额：<b>￥<span id="final-price">${productInfo.priceInfo.discountPrice}</span></b></span>
                    </div>
                </div>
                <div class="row text-center">
                    <div class="total-sm"><input value="提交订单" type="button" class="btn btn-warning form-submit"></div>
                </div>
            </div>
            <!--费用清单-->
        </div>
    </div>
</div>

<!--底部-->
<jsp:include page="/WEB-INF/jsp/mall/common/footer.jsp"></jsp:include>
<!--底部-->

<script id="touristTemplate" type="text/x-jsrender">
<div class="price-list box-order-content">
    <form class="form-inline" id="kehu">
        <div class="form-group">
            <label >您的姓名：</label>
            <input type="text" class="form-control tourist-name">
        </div>
        <div class="form-group">
            <label >联系电话：</label>
            <input type="text" class="form-control tourist-tel">
        </div>
        <div class="form-group">
            <label >证件类型：</label>
            <select class="form-control tourist-idType">
                <option value="IDCARD">身份证</option>
                <option value="OTHER">驾驶证</option>
            </select>
            <input type="text" class="form-control tourist-idNumber">
        </div>
        <div class="form-group">
            <label >备注：</label>
            <input type="text" class="form-control tourist-remark">
        </div>
    </form>
</div>









</script>

<script src="${mallConfig.resourcePath}/js/jquery.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/jsrender.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/bootstrap.min.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/zebra_datepicker.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/jquery.pin.js?v=${mallConfig.resourceVersion}"></script>
<script src="${mallConfig.resourcePath}/js/shopcart/shopcart/order.js?v=${mallConfig.resourceVersion}"></script>
</body>
</html>
