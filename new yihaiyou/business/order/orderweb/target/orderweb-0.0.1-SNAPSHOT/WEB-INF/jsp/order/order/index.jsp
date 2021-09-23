<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2015/10/13
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单页面</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" href="/css/order.index.css"/>
</head>
<body>
<div class="main-wrap">
    <%--<div class="easyui-tabs main-wrap">--%>
    <div title="所有订单" data-options="fit:true,border:false" style="width:100%;height:100%;"
         class="easyui-layout order-table" id="total-order-table-wrap">
        <div id="total-order-searcher" style="padding:3px">
            <span>
                <select id="search-type">
                    <option value="orderForm.order.recName">联系人</option>
                    <option value="orderForm.order.mobile">联系电话</option>
                    <option value="orderForm.order.orderNo">订单号</option>
                </select>
            </span>
            <input id="search-content" placeholder="输入查询内容"
                   style="line-height:26px;border:1px solid #ccc; padding: 0 5px;">
            <span>按订单状态搜索：</span>
             <span>
                <select id="search-status">
                    <option value="">所有状态</option>
                    <option value="WAIT">待支付</option>
                    <option value="UNCONFIRMED">待确认</option>
                    <option value="SUCCESS">预订成功</option>
                    <option value="CANCELED">已取消</option>
                    <option value="UNCONFIRMED">待确认</option>
                    <option value="FAILED">预订失败</option>
                    <option value="REFUND">已退款</option>
                    <option value="CANCELED">已取消</option>
                    <option value="INVALID">无效订单</option>
                    <option value="PARTIAL_FAILED">部分失败</option>
                </select>
            </span>
            <span>
                <select id="search-sort-property">
                    <option value="createTime">下单时间</option>
                </select>
            </span>
            <span>
                <select id="search-sort-type">
                    <option value="false">倒序</option>
                    <option value="true">升序</option>
                </select>
            </span>
            <a href="#" class="easyui-linkbutton" onclick="Orders.doSearch()">查询</a>
        </div>
        <div data-options="region:'center', border:false, title:'订单列表'">
            <table id="total-order-table"></table>
        </div>
    </div>


</div>
<div class="easyui-dialog easyui-layout panel-scroll" id="detail-panel" closed="true" onClose="alert(111)"
     style="width:100%;height: 100%;overflow-y: auto;">
    <div class="detail-panel">
        <strong class="title">订单信息</strong>
        <table>
            <tr>
                <td>下单时间:</td>
                <td class="order-time"></td>
            </tr>
            <tr>
                <td>联 系 人：</td>
                <td class="name"></td>
            </tr>
            <tr>
                <td>手机号码：</td>
                <td class="mobile"></td>
            </tr>
        </table>
    </div>
    <div class="detail-panel">
        <strong class="title">旅客信息</strong>
        <table>
            <tr>
                <td style="padding-right: 10px;">是否包含老人:</td>
                <td class="order-old"></td>
            </tr>
            <tr>
                <td style="padding-right: 10px;">是否包含外籍人员:</td>
                <td class="order-foreign"></td>

            </tr>
        </table>
    </div>
    <div class="detail-panel">
        <strong class="title">发票信息</strong>

        <div id="no_invoice" class="mt15 disa">暂无发票信息!</div>
        <table class="order-detail-table mt15" id="table_invoice">
            <thead>
            <tr>
                <td>发票抬头</td>
                <td>发票明细</td>
                <td>发票金额(元)</td>
                <td>联系电话</td>
                <td>寄送地址</td>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div class="detail-panel">
        <strong class="title">保险信息</strong>

        <div id="no_insurance" class="mt15 disa">暂无保险信息!</div>
        <table class="order-detail-table mt15" id="table_insurance">
            <thead>
            <tr>
                <td>保险名称</td>
                <td>保险分类</td>
                <td>保险价格</td>
                <td>保险公司</td>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
    <div class="detail-panel">
        <strong class="title">预定信息</strong>
        <table id="order-detail-table-a" class="order-detail-table mt15">
            <thead>
            <tr>
                <td> 详情ID</td>
                <td> 预定产品</td>
                <td> 产品类型</td>
                <td> 价格类型</td>
                <td> 出发日期</td>
                <td> 价格(元)</td>
                <td> 预订人数</td>
                <td> 单房差(元)</td>
                <td> 优惠券优惠(元)</td>
                <td> 其他优惠(元)</td>
                <td> 状态</td>
                <td> 接口订单ID</td>
                <td> 预定结果</td>
                <td> 操作</td>
                <td> 订单日志</td>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>

    </div>
    <div class="detail-panel">
        <strong class="title">支付信息</strong>
        <table>
            <tr>
                <td width="80">订单总金额：</td>
                <td width="250" class="total-price"></td>
            </tr>
        </table>
    </div>
</div>
<%--门票订单修改日期对话框--%>
<div class="easyui-dialog c_dialog" id="edit_date_panel" closed="true" style="width:500px;top: 80px;">
    <form id="edit_date_form" method="post" enctype="multipart/form-data" action="">
        <input type="hidden" name="orderId">
        <input type="hidden" name="orderDetailId">
        <input name="targetDate" type="hidden">
        <input name="costId" type="hidden">
        <ul>
            <li style="text-align: center;">
                   <span style="color: #FF2F2F; font-weight: bold;
                        font-size: 24px;">请选择日期</span>
            </li>
            <li class="">
                <label>更改日期：</label>
                <span id="showDate" style="color: #117700; font-weight: bold;
                        font-size: 16px;"></span>
            </li>
            <li style="width: 100%">
                <div id="ticket_price_calendar" style="width: 380px; margin: 0 auto"></div>
            </li>

        </ul>
        <div style="text-align:center;padding:5px; clear: both;">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="commitEditOrderDetail('edit_date_form', 'edit_date_panel', '/outOrder/outOrderEdit/editOrderDetailDate.jhtml')">确认修改</a>
        </div>
    </form>
</div>
<%--门票订单修改旅客信息对话框--%>
<div class="easyui-dialog c_dialog" id="edit_info_panel" closed="true" style="width:520px;top: 80px;">
    <form id="edit_info_form" method="post" enctype="multipart/form-data" action="">
        <input type="hidden" name="orderId">
        <input type="hidden" name="orderDetailId">
        <ul>
            <li>
                <label>订单联系人：</label>
                <input type="text" name="recName" class="easyui-textbox" data-options="required:true">
            </li>
            <li>
                <label>订单联系电话：</label>
                <input type="text" name="mobile" class="easyui-textbox" data-options="required:true">
                <input type="hidden" name="origin_mobile">
                <label id="resend_msg_la">
                    <input type="checkbox" name="reSendMsg" onclick="checkResendMsg()"
                           style="width: auto; vertical-align: middle" value="true">&nbsp;重发验票短信
                </label>

            </li>
            <li style="text-align: center;">
                   <span style="color: #FF2F2F; font-weight: bold;
                        font-size: 16px;">订单旅客列表</span>
                <div id="order_tourist_info"></div>
            </li>
        </ul>
        <div style="text-align:center;padding:5px; clear: both;">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="commitEditOrderDetail('edit_info_form', 'edit_info_panel', '/outOrder/outOrderEdit/editOrderDetailInfo.jhtml')">确认修改</a>
        </div>
    </form>
</div>
<%--订单日志框--%>
<div class="easyui-dialog easyui-layout panel-scroll" id="log-panel" closed="true"
     style="width:620px;height: 500px;overflow-y: auto;">
    <div data-options="region:'center',border:false">
        <table id="logDg"></table>
    </div>
</div>
<script type="text/javascript" src="/js/order/order/order.js"></script>
<link rel='stylesheet' href='/fullcalendar-2.4.0/lib/cupertino/jquery-ui.min.css' />
<link href='/fullcalendar-2.4.0/fullcalendar.css' rel='stylesheet' />
<link href='/fullcalendar-2.4.0/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='/fullcalendar-2.4.0/lib/moment.min.js'></script>
<script src='/fullcalendar-2.4.0/fullcalendar.min.js'></script>
<script src='/fullcalendar-2.4.0/lang-all.js'></script>
</body>
</html>
