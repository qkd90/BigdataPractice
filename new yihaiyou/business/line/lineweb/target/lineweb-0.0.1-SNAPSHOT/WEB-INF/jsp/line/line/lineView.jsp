<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:property value="lineDisplay.name"/></title>
    <%@ include file="../../common/common141.jsp" %>
    <script type="text/javascript">
        var FG_DOMAIN = '<s:property value="fgDomain"/>';
    </script>
    <script type="text/javascript" src="/js/line/line/lineUtil.js"></script>
    <link rel="stylesheet" type="text/css" href="/js/kindeditor/themes/default/default.css">
    <script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/line/line/addWizard.css">
    <script type="text/javascript" src="/js/line/line/editStep1.js"></script>
    <script type="text/javascript" src="/js/line/line/editStep21.js"></script>
    <script type="text/javascript" src="/js/line/line/editStep2.js"></script>
    <script type="text/javascript" src="/js/line/line/editStep3.js"></script>
    <script type="text/javascript" src="/js/line/line/editStep4.js"></script>
    <style type="text/css">
        .textbox-text-readonly {
            color: gray;
        }
    </style>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <div data-options="region:'center',border:false" style="padding:10px 20px 10px 20px">
        <input id="productId" name="productId" type="hidden" value="<s:property value="lineDisplay.id"/>"/>
        <table>
            <tr>
                <td class="label">产品编号:</td>
                <td>
                    <s:property value="lineDisplay.productNo"/>
                    <span class="tip"></span>
                </td>
            </tr>
            <tr>
                <td class="label"><font color="red">*&nbsp;</font>线路类型:</td>
                <td>
                    <s:if test="lineDisplay.lineType=='city'">厦门游</s:if>
                    <s:if test="lineDisplay.lineType=='around'">周边游</s:if>
                    <s:if test="lineDisplay.lineType=='china'">国内游</s:if>
                </td>
            </tr>
            <tr>
                <td class="label"><font color="red">*&nbsp;</font>产品性质:</td>
                <td>
                    <s:if test="lineDisplay.productAttr==@com.data.data.hmly.service.line.entity.enums.ProductAttr@gentuan">跟团游</s:if>
                    <s:if test="lineDisplay.productAttr==@com.data.data.hmly.service.line.entity.enums.ProductAttr@ziyou">自由行</s:if>
                    <s:if test="lineDisplay.productAttr==@com.data.data.hmly.service.line.entity.enums.ProductAttr@zijia">自驾游</s:if>
                    <s:if test="lineDisplay.productAttr==@com.data.data.hmly.service.line.entity.enums.ProductAttr@dulibaotuan">独立包团</s:if>
                </td>
            </tr>
            <tr>
                <td class="label"><font color="red">*&nbsp;</font>线路名称:</td>
                <td>
                    <s:property value="lineDisplay.name"/>
                </td>
            </tr>
            <tr>
                <td class="label">线路分类:</td>
                <td>
                    <c:forEach items="${linecategorgs}" var="categorg">
                        <c:if test="${categorg.id == lineDisplay.category}">${categorg.name}</c:if>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td class="label"><font color="red">*&nbsp;</font>出发城市:</td>
                <td id="city_name">
                    <input type="hidden" id="cityId" value="<s:property value="lineDisplay.startCityId"/>">
                    <%--<input class="easyui-combobox" id="province" name="province" required="true" style="width:100px;"--%>
                    <%--<s:if test="lineDisplay.agentFlag==true">readonly="true"</s:if>/>--%>
                    <%--<input class="easyui-combobox" id="startCityId" name="startCityId"--%>
                    <%--value="<s:property value="lineDisplay.startCityId"/>" required="true" style="width:80px;"--%>
                    <%--<s:if test="lineDisplay.agentFlag==true">readonly="true"</s:if>/>--%>
                </td>
            </tr>
            <tr>
                <td class="label"><font color="red">*&nbsp;</font>游客需提前:</td>
                <td>
                    <s:property value="lineDisplay.preOrderDay"/>
                    <span style="line-height: 18px;">天预订&nbsp;-&nbsp;建议在</span>
                    <s:property value="lineDisplay.suggestOrderHour"/>
                    <span style="line-height: 18px;">点前预定&nbsp;&nbsp;</span>
                    <div style="display: none;">
                        指定购买日期
                        <s:property value="lineDisplay.validOrderDay"/>
                        <span style="line-height: 18px;">天内有效</span>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="label"><font color="red">*&nbsp;</font>购物与自费:</td>
                <td>
                    <s:if test="lineDisplay.buypay==@com.data.data.hmly.service.line.entity.enums.Buypay@noBuyNoPay">无购物无自费</s:if>
                    <s:if test="lineDisplay.buypay==@com.data.data.hmly.service.line.entity.enums.Buypay@noBuyPay">无购物有自费</s:if>
                    <s:if test="lineDisplay.buypay==@com.data.data.hmly.service.line.entity.enums.Buypay@buyNoPay">有购物无自费</s:if>
                    <s:if test="lineDisplay.buypay==@com.data.data.hmly.service.line.entity.enums.Buypay@buyPay">有购物有自费</s:if>
                </td>
            </tr>
            <tr>
                <td class="label">交通方式:</td>
                <td>
                    <input disabled="disabled" class="easyui-combobox" name="goWay"
                           value="<s:property value="lineDisplay.goWay"/>"
                           <s:if test="lineDisplay.agentFlag==true">readonly="true"</s:if>
                           data-options="valueField:'id',textField:'text',data:[{id:'',text:'--去程--'},{id:'plane',text:'飞机'},{id:'car',text:'火车'},{id:'train',text:'汽车'},
			   		{id:'ship',text:'轮船'},{id:'dongche',text:'动车'},{id:'gaotie',text:'高铁'}]" style="width:80px;"/>
                    <input disabled="disabled" class="easyui-combobox" name="backWay"
                           value="<s:property value="lineDisplay.backWay"/>"
                           <s:if test="lineDisplay.agentFlag==true">readonly="true"</s:if>
                           data-options="valueField:'id',textField:'text',data:[{id:'',text:'--返程--'},{id:'plane',text:'飞机'},{id:'car',text:'火车'},{id:'train',text:'汽车'},
			   		{id:'ship',text:'轮船'},{id:'dongche',text:'动车'},{id:'gaotie',text:'高铁'}]" style="width:80px;"/>
                    <s:property value="lineDisplay.wayDesc"/>
                </td>
            </tr>
            <tr>
                <td class="label">排序:</td>
                <td>
                    <s:property value="lineDisplay.showOrder"/>
                </td>
            </tr>
            <tr>
                <td class="label">支付设置:</td>
                <td>
                    <s:if test="lineDisplay.paySet==@com.data.data.hmly.service.line.entity.enums.PaySet@close">关闭支付</s:if>
                    <s:if test="lineDisplay.paySet==@com.data.data.hmly.service.line.entity.enums.PaySet@earnest">需订金预订</s:if>
                    <s:if test="lineDisplay.paySet==@com.data.data.hmly.service.line.entity.enums.PaySet@allpay">需全额支付</s:if>
                </td>
            </tr>
            <tr>
                <td class="label">在线预订:</td>
                <td>
                    <s:if test="lineDisplay.confirmAndPay=='confirm'">需要确认才能付款</s:if>
                    <s:if test="lineDisplay.confirmAndPay=='noconfirm'">本产品在线预订无需确认</s:if>
                </td>
            </tr>
            <tr>
                <td class="label">产品备注</td>
                <td>
                    <%--<div class="remark">--%>
                        <%--<div><span class="tip">（仅自己可见）</span></div>--%>
                    <%--<div>--%>
                            <s:property value="lineDisplay.productRemark"/>
                    <%--</div>--%>
                    <%--</div>--%>
                </td>
            </tr>
            <tr>
                <td class="label">积分兑换:</td>
                <td>
                    <s:if test="lineDisplay.scoreExchange==@com.data.data.hmly.service.line.entity.enums.ScoreExchange@participation">参加积分兑换</s:if>
                    <s:if test="lineDisplay.scoreExchange==@com.data.data.hmly.service.line.entity.enums.ScoreExchange@no">不参加</s:if>
                </td>
            </tr>
            <tr>
                <td class="label">图片:</td>
                <td>
                    <div id="imgView" style="<s:if
                            test="filePath == null || filePath == ''">display:none;</s:if>">
                        <img src="/static<s:property value="filePath"/>" width="240" height="160"
                             style="padding:5px; border: 1px dashed #E3E3E3;"><br>
                    </div>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td class="label">价格类型:</td>
                <td>
                    <table>
                        <thead>
                        <tr>
                            <td width="140">类型名称</td>
                            <td width="110">成人价</td>
                            <td width="110">佣金</td>
                            <td width="80">状态</td>
                            <td width="80">操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="linetypepricesDisplay" status="stuts">
                            <s:if test="#stuts.odd == true">
                                <tr class="odd">
                            </s:if>
                            <s:else>
                                <tr class="even">
                            </s:else>
                            <td><s:property value="quoteName"/></td>
                            <td><span class="orange-bold"><span class="minDiscountPrice"
                                                                linetypepriceId="<s:property value="id"/>">0</span></span>元起
                            </td>
                            <td><span class="orange-bold"><span class="minRatePrice"
                                                                linetypepriceId="<s:property value="id"/>">0</span></span>元起
                            </td>
                            <td>
                                <s:if test='status == "enable"'>
                                    正常
                                </s:if>
                                <s:else>
                                    异常
                                </s:else>
                            </td>
                            <td align="center">
                                <a href='javascript:void(0)' style='color:blue;text-decoration:none;'
                                   onclick='editStep21.doViewPrice(<s:property value="id"/>)'>详细报价</a>
                            </td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </td>
            </tr>
        </table>


        <div class="row_hd">线路特色</div>
        <div style="overflow:auto;">
            <table>
                <tr>
                    <td class="label">主题标签:</td>
                    <td>
                        <div style="width: 560px;">
                            <s:iterator value="playtitles" status="stuts" var="pt">
                                <s:iterator value="lineplaytitlesDisplay" var="lpt">
                                    <s:if test="#lpt.playTitleId==#pt.id"><s:property value="name"/></span></s:if>
                                </s:iterator>
                            </s:iterator>
                        </div>
                        <div class="clr-float">
                            <s:property value="lineexplainDisplay.defineTag"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="label">行程亮点:</td>
                    <td>
                        ${lineexplainDisplay.lineLightPoint}
                        <%--<s:property value="lineexplainDisplay.lineLightPoint"/>--%>
                    </td>
                </tr>
                <tr>
                    <td class="label">&nbsp;</td>
                    <td>
                    </td>
                </tr>
            </table>
        </div>
        <div class="row_hd">行程内容</div>
        <div style="overflow:auto;">
            <div id="lineDayDiv">
                <s:iterator value="lineexplainDisplay.linedays" status="ldstatus" var="ld">
                    <table class="lineDay" id="lineDay_<s:property value="#ld.lineDay"/>">
                        <tr>
                            <td class="label bold-size18">第<span class="orange-bold bold-size18"><s:property
                                    value="#ld.lineDay"/></span>天:
                            </td>
                            <td>
                                <s:property value="#ld.dayDesc"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="label"><font color="red">*&nbsp;</font>行程安排:</td>
                            <td>
                                    ${ld.arrange}
                            </td>
                        </tr>
                        <tr>
                            <td class="label">用餐:</td>
                            <td>
                                <s:if test="#ld.meals!=null && #ld.meals.contains('breakfast')">早餐</s:if>
                                <s:if test="#ld.meals!=null && #ld.meals.contains('lunch')">中餐</s:if>
                                <s:if test="#ld.meals!=null && #ld.meals.contains('supper')">晚餐</s:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="label">住宿:</td>
                            <td><s:property value="#ld.hotelName"/></td>
                        </tr>
                        <tr>
                            <td class="label">行程景点:</td>
                            <td>
                                <div class="lineDayPlanDiv">
                                    <s:iterator value="#ld.linedaysplan" status="ldpstatus" var="ldp">
                                        <s:property value="#ldp.typeName"/>&nbsp;&nbsp;
                                    </s:iterator>
                                </div>
                            </td>
                        </tr>
                    </table>
                </s:iterator>
            </div>
        </div>
        <div class="row_hd">预订指南</div>
        <div style="overflow:auto;">
            <table>
                <tr>
                    <td class="label">预订须知:</td>
                    <td>
                        <s:property value="lineexplainDisplay.orderKnow"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">内容:</td>
                    <td>
                        ${lineexplainDisplay.orderContext}
                        <%--<s:property value="lineexplainDisplay.orderContext"/>--%>
                    </td>
                </tr>
                <tr>
                    <td class="label">温馨提示:</td>
                    <td>
                        <s:property value="lineexplainDisplay.tip"/>
                    </td>
                </tr>
                <tr>
                    <td class="label">内容:</td>
                    <td>
                        ${lineexplainDisplay.tipContext}
                        <%--<s:property value="lineexplainDisplay.tipContext"/>--%>
                    </td>
                </tr>
                <tr>
                    <td class="label">&nbsp;</td>
                    <td>
                        <!--<input type="button"
                               style="width: 130px;" onclick="editStep1.tabClose()" value="完成">-->
                    </td>
                </tr>
            </table>
        </div>
    </div>

</div>
</body>
</html>
