<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <div class="here">您的位置：<a href="/">首页</a>&gt;<a href="/mall/line/list.jhtml">线路列表</a>&gt;<a href="/mall/line/detail.jhtml?id=${line.id?c}">线路详情</a>
            </div>
        </div>
        <div class="col-xs-5">
            <div class="xl-pic">
                <span class="label-title">
                <#if line.productAttr=='gentuan'>
                    跟团游
                <#elseif line.productAttr=='ziyou'>
                    自游行
                <#elseif line.productAttr=='zijia'>
                    自驾游
                <#else>
                    独立包团
                </#if>
                </span><img src="<#if line.lineImageUrl!=''>${imguriPreffix}${line.lineImageUrl}</#if>" class="img-responsive">
            </div>
            <div class="xl-rl">
                <div id="calendar"></div>
            </div>
        </div>
        <div class="col-xs-7">
            <form action="/shopcart/shopcart/order.jhtml" method="post">
                <div class="clearfix" id="info">
                    <h1>${line.name}</h1>
                    <input type="hidden" value="${line.id?c}" id="lineId" name="id"/>
                    <input type="hidden" value="line" id="lineId" name="proType"/>

                    <div class="info-content clearfix">
                        <!--属性-->
                        <div class="info-sx clearfix">
                            <ul>
                                <li class="col-xs-6"><span>出 发 地：</span>${startCity.name}</li>
                                <li class="col-xs-6 "><span>线路天数：</span>${(lineExplain.linedays?size)!}天</li>
                                <li class="col-xs-6">
                                    <span>去&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;程：</span>
                                <#if line.goWay=='plane'>
                                    飞机
                                <#elseif line.goWay=='train'>
                                    火车
                                <#elseif line.goWay=='bus'>
                                    公交
                                <#elseif line.goWay=='ship'>
                                    轮船
                                <#elseif line.goWay=='dongche'>
                                    动车
                                <#elseif line.goWay=='gaotie'>
                                    高铁
                                <#else>
                                    徒步
                                </#if>
                                </li>
                                <li class="col-xs-6 ">
                                    <span>返&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;程：</span>
                                <#if line.goWay=='plane'>
                                    飞机
                                <#elseif line.goWay=='train'>
                                    火车
                                <#elseif line.goWay=='bus'>
                                    公交
                                <#elseif line.goWay=='ship'>
                                    轮船
                                <#elseif line.goWay=='dongche'>
                                    动车
                                <#elseif line.goWay=='gaotie'>
                                    高铁
                                <#else>
                                    徒步
                                </#if>
                                </li>
                                <li class="col-xs-6">
                                    <span>目 的 地：</span>
                                <#list lineDayList as lineDay>
                                    <#list lineDay.linedaysplan as dayPlan>
                                    ${dayPlan.typeName}
                                        <#if dayPlan_has_next>、</#if>
                                    </#list>

                                </#list>
                                </li>
                                <li class="col-xs-6"><span>提前预定：</span>${line.preOrderDay}天</li>

                            </ul>
                        </div>

                    <#if (priceTypeList?size>0)>
                        <div class="info-lx clearfix">
                            <input type="hidden" name="priceId" id="priceId" value="${priceTypeList[0].id?c}"/>
                            <dl>
                                <dt class="pull-left">预定类型：</dt>
                                <dd class="pull-left info-lx-list">
                                    <#list priceTypeList as priceType>
                                        <a href="javascript:void(0)" class="<#if priceType_index==0>curr</#if>" data-id="${priceType.id?c}">${priceType.quoteName}</a>
                                    </#list>
                                </dd>
                            </dl>
                        </div>
                    </#if>

                        <!--属性-->
                        <!--价格阶梯-->
                        <div class="info-price-list text-center clearfix">
                            <table class="table table-striped table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th style="text-align: center">价格类型</th>
                                    <th style="text-align: center">销售价</th>
                                    <th class="commission-hide" style="text-align: center">利润</th>
                                </tr>
                                </thead>
                                <tbody id="price-panel">

                                </tbody>
                            </table>
                        </div>
                        <!--价格阶梯-->
                        <div class="info-sx clearfix">
                            <ul>
                                <li class="col-xs-6">
                                    <span>游玩日期:</span>&nbsp;&nbsp;<input id="startDate" name="startDate" type="text" value="">
                                </li>
                                <li class="col-xs-6 text-right">
                                <span class="order-count">
                                <span>预订人数（成人）:</span>
                                <input id="min" class="minus" name="" type="button" value="-"/>
                                <input id="num" class="num" name="adultCount" type="text" value="1"/>
                                <input id="add" class="plus" type="button" value="+"/>
                                    </span>
                                    <br><br>
                                <span class="order-count">
                                <span>预订人数（儿童）:</span>
                                <input id="min1" class="minus" name="" type="button" value="-"/>
                                <input id="num1" class="num" name="childCount" type="text" value="0"/>
                                <input id="add1" class="plus" name="" type="button" value="+"/>
                                </span>
                                </li>

                            </ul>
                        </div>
                        <div class="buy-bt clearfix text-center">
                            <input class="buy-now" type="submit" value="">
                            <#--<input class="addtocart" type="button">-->
                            <a href="/mall/line/agent.jhtml?productId=${line.id}">
                                <input class="update-now commission-hide" type="button">
                            </a>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <!--详细信息-->
        <div class="col-xs-12">
            <div id="detail">
                <h5>
                    <a href="#n1" class="curr">参考线路</a><a href="#n2">费用说明</a><a href="#n3">预订须知</a><a href="#n4">特别提醒</a>
                    <#--<a href="#n5">分销商点评(1025)</a>-->
                </h5>

                <div class="clearfix detail-content">
                    <h4><span class="subtitle"><span>产品特色</span></span></h4>

                    <div class="clearfix subcontent">
                    ${(lineExplain.lineTeshe)!"暂缺"}
                    </div>
                    <h4><span class="subtitle"><span><a name="n1"></a>线路安排</span></span></h4>


                <#list lineDayList as lineDay>

                    <div class="clearfix subcontent" id="xcap">
                        <h3>
                            <span class="one">第${lineDay.lineDay}天</span><span class="xc-title">${lineDay.dayDesc}</span>
                        </h3>

                        <div class="xcbh clearfix">
                        	<#if lineDay.meals != null>
                            <p class="yc">
                                <span class="ico">用餐</span><span>${lineDay.meals?replace("breakfast", "早餐")?replace("lunch", "午餐")?replace("supper", "晚餐")?replace(",", "，")}</span>
                            </p>
                            </#if>

                        <#--<p class="jt"><span class="ico">交通</span><span>汽车</span></p>-->

                            <p class="jd">
                                <span class="ico">途径景点</span>
                                <span>
                                    <#list lineDay.linedaysplan as dayPlan>
                                    ${dayPlan.typeName}
                                        <#if dayPlan_has_next>、</#if>
                                    </#list>
                                </span>
                            </p>
                        </div>
                    ${lineDay.arrange}
                    </div>
                </#list>


                    <h4><span class="subtitle"><span><a name="n2"></a>费用说明</span></span></h4>

                    <div class="clearfix subcontent" id="feiyong">
                    <#if priceTypeList?size!=1>
                        <#list priceTypeList as priceType>
                            <h3>${priceType.quoteName}</h3>
                            <br>
                        ${priceType.quoteDesc
                        ?replace("费用包含：", " <h6><span>费用包含</span></h6>","f")
                        ?replace("费用不包含：","<h6><span>费用不包含</span></h6>","f")
                        ?replace("*","<p style='color:#ffa127;'>*" )}

                        </#list>
                    </#if>
                    <#if priceTypeList?size==1>
                    ${priceTypeList[0].quoteDesc
                    ?replace("费用包含：", " <h6><span>费用包含</span></h6>","f")
                    ?replace("费用不包含：","<h6><span>费用不包含</span></h6>","f")
                    ?replace("*","<p style='color:#ffa127;'>*" )}
                    </#if>
                    </div>
                    <h4><span class="subtitle"><span><a name="n3"></a>预定须知</span></span></h4>

                    <div class="clearfix subcontent">
                    ${(lineExplain.orderKnow)!"暂缺"}
                        <br/><br/>
                    ${(lineExplain.orderContext)!"暂缺"}
                    </div>
                    <h4><span class="subtitle"><span><a name="n4"></a>特别提醒</span></span></h4>

                    <div class="clearfix subcontent">
                    ${(lineExplain.tip)!"暂缺"}
                        <br/><br/>
                    ${(lineExplain.tipContext)!"暂缺"}
                    </div>
                    <#--<h4><span class="subtitle"><span><a name="n5"></a>分销商点评</span></span></h4>-->

                    <#--<div class="dianpin clearfix subcontent">-->
                        <#--<dl class="clearfix">-->
                            <#--<dt><span class="content">非常满意</span><span class="name pull-right">xm123</span></dt>-->
                            <#--<dd class="clearfix">2015.10.21 12:21</dd>-->
                        <#--</dl>-->
                        <#--<dl class="clearfix">-->
                            <#--<dt><span class="content">非常满意</span><span class="name pull-right">xm123</span></dt>-->
                            <#--<dd class="clearfix">2015.10.21 12:21</dd>-->
                        <#--</dl>-->
                        <#--<dl class="clearfix">-->
                            <#--<dt><span class="content">非常满意</span><span class="name pull-right">xm123</span></dt>-->
                            <#--<dd class="clearfix">2015.10.21 12:21</dd>-->
                        <#--</dl>-->
                        <#--<dl class="clearfix">-->
                            <#--<dt><span class="content">非常满意</span><span class="name pull-right">xm123</span></dt>-->
                            <#--<dd class="clearfix">2015.10.21 12:21</dd>-->
                        <#--</dl>-->
                        <#--<dl class="clearfix">-->
                            <#--<dt><span class="content">非常满意</span><span class="name pull-right">xm123</span></dt>-->
                            <#--<dd class="clearfix">2015.10.21 12:21</dd>-->
                        <#--</dl>-->
                        <#--<dl class="clearfix">-->
                            <#--<dt><span class="content">非常满意</span><span class="name pull-right">xm123</span></dt>-->
                            <#--<dd class="clearfix">2015.10.21 12:21</dd>-->
                        <#--</dl>-->
                    <#--</div>-->
                </div>
            </div>
        </div>
        <!--详细信息-->
    </div>
</div>