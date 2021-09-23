<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/1/23
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>目的地列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="../common/common141.jsp" %>
    <script type="text/javascript" src="/js/common/areaSelectDg.js"></script>
    <script type="text/javascript" src="/js/area.js"></script>
    <script type="text/javascript" src="/js/destination/desList.js"></script>
</head>
<body>
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
        <div id="toolbar" style="padding:10px 0 5px 10px">
            <table id="qryCondition">
                <tr>
                    <td width="80" align="right">城市:</td>
                    <td>
                        <input type="hidden" id="qry_cityCode">
                        <input type="hidden" id="qry_isChina">
                        <input id="qryCity" class="easyui-textbox"
                               data-options="buttonText:'清空',editable:false,prompt:'点击选择城市'"
                               style="width:200px" data-country="" data-province="" data-city="">
                        <%--<select class="easyui-combobox" name="province" id="qry_province" style="width:120px">--%>

                        <%--</select>--%>
                        <%--<select class="easyui-combobox" name="city" id="qry_city" style="width:120px">--%>

                        <%--</select>--%>
                    </td>
                    <td width="80" align="right">筛选:</td>
                    <td>
                        <select class="easyui-combobox" name="condition" id="qry_condition" style="width: 80px;">
                            <option value="0" selected="selected">全部</option>
                            <option value="1">有封面</option>
                            <option value="2">无封面</option>
                            <option value="3">无季节</option>
                            <option value="4">无游玩时间</option>
                            <option value="5">无简介</option>
                        </select>
                    </td>
                    <td width="80" align="right">目的地名称:</td>
                    <td><input class="easyui-textbox" id="qry_name" name="name"
                               data-options="prompt:'请输目的地名称或拼音'" style="width:150px;"></td>
                    <td>
                        <a id="queryBtn" href="javascript:void(0)" class="easyui-linkbutton"
                           style="width:80px; margin-left: 12px;"
                           onClick="query()">查询</a>
                    </td>
                    <td>
                        <a id="resetBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:120px; margin-left: 12px;"
                           onClick="reset()">重置</a>
                    </td>
                </tr>
            </table>
        </div>
    <div data-options="region:'center',border:false">
        <div id="regionCodes"></div>
        <table id="qryResult"></table>
    </div>
</div>
</body>
</html>
