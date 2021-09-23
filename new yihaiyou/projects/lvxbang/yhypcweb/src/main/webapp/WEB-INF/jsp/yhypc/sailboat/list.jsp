<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>游艇帆船</title>
    <link rel="stylesheet" href="/css/public/pager.css">
    <link rel="stylesheet" href="/css/sailboat/list.css">

</head>
<body class="sailBoat">
<div class="hotelIndex">
<%@include file="../../yhypc/public/nav_header.jsp"%>
<!--内容-->
<div class="wrap">
    <div class="container-box">
        <h3>您在这里：<a href="/yhypc/index/index.jhtml">首页</a>><a href="/yhypc/sailboat/index.jhtml">海上休闲</a>> 海上休闲产品</h3>
        <div class="form-container">
            <form>
                <div class="form-group clearfix">
                    <i>类型：</i>
                    <label class="checkbox-mask">
                        <input type="radio" name="category" value="" <s:if test="ticketSearchRequest.ticketType == null || ticketSearchRequest.ticketType == ''">checked</s:if>>
                        <div class="mask">
                            <span class="mask-bg"></span>不限
                        </div>
                    </label>
                    <div class="radio-mask-wrap">
                        <label class="radio-mask">
                        <input type="radio" name="category" data-name="帆船" <s:if test="ticketSearchRequest.ticketType=='sailboat'">checked</s:if> value="sailboat">
                        <div class="mask">
                            <span class="mask-bg"></span>帆船
                        </div>
                    </label>

                        <label class="radio-mask">
                        <input type="radio" name="category" data-name="游艇" <s:if test="ticketSearchRequest.ticketType=='yacht'">checked</s:if> value="yacht" id="yacht">
                        <div for="sailboat" class="mask">
                            <span class="mask-bg"></span>游艇
                        </div>
                    </label>

                        <label class="radio-mask">
                        <input type="radio" name="category" data-name="鹭岛游" <s:if test="ticketSearchRequest.ticketType=='huanguyou'">checked</s:if> value="huanguyou" id="huanguyou">
                        <div for="sailboat" class="mask">
                            <span class="mask-bg"></span>鹭岛游
                        </div>
                    </label>
                    </div>
                </div>
                <div class="form-group clearfix">
                    <i>登艇地点：</i>
                    <label class="checkbox-mask">
                        <input type="radio" name="location" value="" <s:if test="ticketSearchRequest.scenicId == null || ticketSearchRequest.scenicId == ''">checked</s:if>>
                        <div class="mask">
                            <span class="mask-bg"></span>不限
                        </div>
                    </label>
                    <div class="radio-mask-wrap">
                    <s:iterator value="scenicInfos" var="sceInfo">
                        <label class="radio-mask">
                            <input type="radio" name="location" data-name="<s:property value="#sceInfo.name"/>" <s:if test="#sceInfo.id==ticketSearchRequest.scenicId">checked</s:if> value="<s:property value="#sceInfo.id"/>">
                            <div class="mask">
                                <span class="mask-bg"></span><s:property value="#sceInfo.name"/>
                            </div>
                        </label>
                    </s:iterator>
                        </div>
                </div>
                <div class="form-group clearfix">
                    <i>价格区间：</i>
                    <label class="checkbox-mask">
                        <input type="radio" name="price" value="不限" checked>
                        <div class="mask">
                            <span class="mask-bg"></span>不限
                        </div>
                    </label>
                    <div class="radio-mask-wrap">
                    <label class="radio-mask">
                        <input type="radio" name="price" min="0" max="100">
                        <div class="mask">
                            <span class="mask-bg"></span>0-100元
                        </div>
                    </label>

                    <label class="radio-mask">
                        <input type="radio" name="price" min="100" max="300">
                        <div class="mask">
                            <span class="mask-bg"></span>100-300元
                        </div>
                    </label>

                    <label class="radio-mask">
                        <input type="radio" name="price"  min="300" max="500">
                        <div class="mask">
                            <span class="mask-bg"></span>300-500元
                        </div>
                    </label>

                    <label class="radio-mask">
                        <input type="radio" name="price" min="500" max="1000">
                        <div class="mask">
                            <span class="mask-bg"></span>500-1000元
                        </div>
                    </label>
                        </div>
                </div>
            </form>
            <div class="result">
                <p>
                    为您找到<span id="totalProduct">0</span>个产品
                    <button class="sel-label sel-ticketType hide" data-name="category">帆船<span class="close"></span></button>
                    <button class="sel-label sel-scenic hide" data-name="location">五缘湾游艇港码头<span class="close"></span></button>
                    <button class="sel-label sel-price hide" data-name="price">100-300元<span class="close"></span></button>
                    <a class="clear-all-sel-label hide">清除条件</a>
                </p>

            </div>
        </div>
    </div>
    <div class="content-container clearfix">
        <ul class="content-nav clearfix select-sort">
            <li class="li-active" data-order-column="showOrder" data-order-type="asc">编辑推荐</li>
            <li data-order-column="productScore" data-order-type="desc">评分<span class="desc"></span></li>
            <li data-order-column="disCountPrice" data-order-type="desc">价格<span class="desc"></span></li>
            <li data-order-column="orderNum" data-order-type="desc">销量<span class="desc"></span></li>
        </ul>
    </div>
    <ul class="tablelist" id="sailboatList">
    </ul>
    <div class="paging m-pager">
    </div>
</div>
<%@include file="../../yhypc/public/nav_footer.jsp"%>
</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>

<script type="text/html" id="tpl-sailboat-list-item">
    <li class="tablelist-every">
        <div class="product clearfix">
            <div class="product-info clearfix">
                <div class="product-img">
                    {{if (productImg.length > 0)}}
                        <img src="<%=com.zuipin.util.QiniuUtil.URL%>{{productImg}}?imageView2/1/w/211/h/135/q/75" alt="{{name}}">
                    {{else}}
                        <img src="/image/list-product.png" alt="{{name}}">
                    {{/if}}
                </div>
                <div class="product-txt">
                    <h3><a href="/sailboat_detail_{{id}}.html">{{name}}</a></h3>
                    <p>地址：{{address}}</p>
                    <em><span>{{productScore}}&nbsp;/&nbsp;5分</span>
                        {{if (productScore > 0 && productScore <= 1)}}
                            <i class="star1"></i>
                        {{/if}}
                        {{if (productScore > 1 && productScore <= 2)}}
                            <i class="star2"></i>
                        {{/if}}
                        {{if (productScore > 2 && productScore <= 3)}}
                            <i class="star3"></i>
                        {{/if}}
                        {{if (productScore > 3 && productScore <= 4)}}
                            <i class="star4"></i>
                        {{/if}}
                        {{if (productScore > 4 && productScore <= 5)}}
                            <i class="star5"></i>
                        {{/if}}
                        {{if (productScore = 0)}}
                            <i class="star0"></i>
                        {{/if}}
                        {{if orderNum!=0}}
                            (<mark>{{orderNum}}</mark>人购买）</em>
                        {{else}}
                            </em>
                        {{/if}}

                </div>
            </div>
            <div class="product-price">
                {{if discountPrice != null && discountPrice != 0}}
                    <h3><sup>￥</sup>{{discountPrice}}<sub>起</sub></h3>
                    <a href="/sailboat_detail_{{id}}.html">查看详情</a>
                {{else}}
                    <a href="/sailboat_detail_{{id}}.html" style="margin-top: 47px;">查看详情</a>
                {{/if}}

            </div>
        </div>
        {{if priceList.length > 0}}
            <div class="ticket">
                <div class="ticket-title">
                    <span class="ticket-title-span1">票型名称</span>
                    <span>销售价</span>
                </div>
                <ul class="ticket-list" id="ticket-list-{{id}}" data-price-length="{{priceList.length}}">
                </ul>
                <div class="ticket-list-unflod clearfix" id="ticket-list-unflod-{{id}}">
                    <a class="pull-right" data-flag="0" style="cursor: pointer;" onclick="SailboatList.checkMore(this, '{{id}}')"><span id="html-content-{{id}}">展开全部票型</span>（{{priceList.length}}）<i class="down"></i></a>
                </div>
            </div>
        {{/if}}
    </li>
</script>

<script type="text/html" id="tpl-sailboat-list-type-item">
    <div class="point"></div>
    <div class="ticket-discription">
        {{each priceTypeExtendList as typeExtend i}}
            {{if typeExtend.secondTitle.length > 0}}
            <h5>{{typeExtend.secondTitle}}：</h5>
            {{/if}}
            <p>{{typeExtend.content}}</p>
        {{/each}}
    </div>
</script>

<script type="text/html" id="tpl-sailboat-list-pricetype-item">
    <li class="clearfix" style="background: #f3f8fe;">
        <div class="clearfix" style="background: #fff">
            <a class="collapse" href="javascript:;" onclick="SailboatList.datalist('{{id}}', '{{i}}', event)">{{name}}<i></i></a>
            <span class="price-span"><sub>￥</sub>{{discountPrice}}</span>
            <a href="/yhypc/order/orderSailboatWrite.jhtml?ticketPriceId={{id}}" class="buynow">立即订购</a>
            </div>
        <div id="ticket-category-{{id}}-{{i}}" class="ticket-discription-wrap" data-flag="0" style="display: none;">
        </div>
    </li>
</script>
<script type="text/javascript" src="/lib/util/pager.js"></script>
<script src="/js/sailboat/sailboat_list.js"></script>
</html>