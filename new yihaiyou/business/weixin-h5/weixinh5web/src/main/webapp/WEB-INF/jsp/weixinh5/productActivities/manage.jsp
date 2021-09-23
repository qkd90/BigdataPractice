<%--
  Created by IntelliJ IDEA.
  User: dy
  Date: 2016/2/16
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="../../common/common141.jsp"%>
  <script type="text/javascript" src="/js/weixinh5/productActivities/productActivity.js"></script>
    <title></title>
</head>
<body>
  <div id="activity_tab" class="easyui-tabs" fit="true" style="width:100%;height:100%;">
    <div id="activity_list" title="活动列表">
      <div class="easyui-layout" data-options="fit:true"
           style="width:100%;height:100%;">
        <div data-options="region:'north',border:false" style="height:50px;padding:10px 20px;">
          <form id="searchForm_activity">
            <div style="float: left;margin-right: 50px;">
              <label style="float: left;margin-right: 5px;">活动名称：</label>
              <input id="ipt_name" class="easyui-textbox" style="width:200px;"/>
            </div>
            <div style="float: left;margin-right: 50px;">
              <label style="float: left;margin-right: 5px;">活动类型：</label>
              <input id="com_type" class="easyui-combobox" style="width:200px;" data-options="
                valueField: 'id',
                textField: 'value',
                data: [{
                    id: 'coupon',
                    value: '优惠券'
                    },{
                    id: 'flashsale',
                    value: '限时抢购'
                }]" />
            </div>
            <div style="float: left;margin-right: 100px;">
              <label style="float: left;margin-right: 5px;">状态：</label>
              <input id="com_status" class="easyui-combobox" style="width:200px;" data-options="
                valueField: 'id',
                textField: 'value',
                data: [{
                    id: 'UP',
                    value: '正在进行中'
                    },{
                    id: 'DOWN',
                    value: '已下架'
                }]" />
            </div>
            <a style="" href="javascript:void(0)" class="easyui-linkbutton"  onclick="ProductActivity.doSearch()">查询</a>
            <a style="" href="javascript:void(0)" class="easyui-linkbutton"  onclick="ProductActivity.clearForm()">清空</a>
          </form>
        </div>
        <div data-options="region:'center',border:false">
          <div id="tool_activity" style="margin-bottom: 0px;margin-top: 2px;background-color:white;" >
            <a style="margin: 3px;" href="javascript:void(0)" class="easyui-linkbutton"  onclick="ProductActivity.openAddActivity()">新增活动</a>
            <a style="margin: 3px;" href="javascript:void(0)" class="easyui-linkbutton"  onclick="ProductActivity.deletAllActivityBtn()">批量删除</a>
            <a style="margin: 3px;" href="javascript:void(0)" class="easyui-linkbutton"  onclick="ProductActivity.upOrDownAllActivityBtn('UP')">批量上架</a>
            <a style="margin: 3px;" href="javascript:void(0)" class="easyui-linkbutton"  onclick="ProductActivity.upOrDownAllActivityBtn('DOWN')">批量下架</a>

          </div>
          <table id="dg_activity"></table>
        </div>
      </div>
    </div>
  </div>

  <div id="editPanel" class="easyui-dialog" title="新增活动" data-options="fit:true,width:400,height:500,resizable:false,modal:true,closed:true,collapsible:false,shadow:false">
    <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;border-width:0;"></iframe>
  </div>
</body>
</html>
