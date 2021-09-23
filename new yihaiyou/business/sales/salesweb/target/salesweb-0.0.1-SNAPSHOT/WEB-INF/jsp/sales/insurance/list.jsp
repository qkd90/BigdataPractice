<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/7/6
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>产品列表</title>
    <%@ include file="../../common/common141.jsp" %>
    <style>
        .opt a.ena {
            color: #0000cc;
            text-decoration: underline;
        }
        .insurance_dialog {
            padding: 0 10px;
        }
        .fl {
            float: left;
        }
        .mt8 {
            margin-top: 8px;
        }
        .ml5 {
            margin-left: 5px;
        }
    </style>
</head>
<body>
<div title="保险管理" data-options="fit:true,border:false"
     style="width:100%;height:100%;" class="easyui-layout" id="content">
    <div id="coupon-searcher" style="padding:3px">
        <span>
            <select id="search-type">
                <option value="insurance.name" selected>保险名称</option>
                <option value="insurance.company">保险公司</option>
            </select>
        </span>
        <input id="search-content" placeholder="输入查询内容"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">
        <span>
            <select id="search-status">
                <option value="" selected>所有状态</option>
                <option value="up">上架</option>
                <option value="down">下架</option>
                <%--<option value=""></option>--%>
            </select>
        </span>
        <span>
            <label>保险分类：</label>
            <input id="search-insurance-type" class="easyui-combotree" name="category">
        </span>
        <span>
            <label>按</label>
            <select id="search-sort-property">
                <option value="createTime" selected>创建时间</option>
                <option value="updateTime">更新时间</option>
                <option value="price">保险价格</option>
            </select>
        </span>
        <span>
            <select id="search-sort-type">
                <option value="desc" selected>降序</option>
                <option value="asc">升序</option>
            </select>
        </span>
        <span>
            <label>价格范围：</label>
            <input style="width: 60px; line-height:22px;border:1px solid #ccc; padding: 0 3px;"
                   id="minPrice" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="最低价格">
            <label>-</label>
            <input style="width: 60px; line-height:22px;border:1px solid #ccc; padding: 0 3px;"
                   id="maxPrice" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="最高价格">
        </span>
        <a href="#" class="easyui-linkbutton" style="width: 80px;"
            onclick="InsuranceMgr.doSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" style="width: 80px;"
            onclick="InsuranceMgr.clearSearch()">重置</a>
        <a href="#" class="easyui-linkbutton" style="width: 90px;"
            onclick="InsuranceMgr.addInsurance()">新增保险</a>
    </div>
    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="insuranceDg"></table>
    </div>
    <!-- 数据表格 终-->
    <%--保险标的 开始--%>
    <div class="easyui-dialog insurance_dialog" id="insurance_panel" closed="true" style="width:900px;">
        <form id="insurance_form" method="post" enctype="multipart/form-data" action="">
            <input type="hidden" name="insurance.id">
            <ul>
                <li class="fl mt8">
                    <label>保险名称：</label>
                    <input type="text" name="insurance.name" style="width: 188px;"
                           class="easyui-textbox" data-options="required:true">
                </li>
                <li class="fl mt8 ml5">
                    <label>保险分类：</label>
                    <input class="easyui-combotree" data-options="required:true" style="width: 188px;"
                           name="insurance.category.id" id="insurance_category">
                </li>
                <li class="fl mt8 ml5">
                    <label>保险价格：</label>
                    <input name="insurance.price" class="easyui-numberbox" style="width: 188px;"
                           data-options="required:true" precision="2">
                </li>
                <li class="fl mt8">
                    <label>保险状态：</label>
                    <input class="easyui-combobox" data-options="required:true" style="width: 188px;"
                           name="insurance.status" id="insurance_status">
                </li>
                <li class="fl mt8 ml5">
                    <label>保险公司：</label>
                    <input type="text" name="insurance.company" style="width: 442px;"
                           class="easyui-textbox" data-options="required:true">
                </li>
                <li class="fl mt8">
                    <label>保险描述：</label>
                    <textarea class="vam" name="insurance.description" style="width: 320px; height: 130px;"></textarea>
                </li>
                <li class="fl mt8 ml5">
                    <label>投保须知：</label>
                    <textarea class="vam" name="insurance.notice" style="width: 320px; height: 130px;"></textarea>
                </li>
                <li class="fl mt8">
                    <label>保险条例：</label>
                    <textarea class="vam" name="insurance.terms" style="width: 320px; height: 130px;"></textarea>
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a id="submit_insurance" href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="InsuranceMgr.commitForm('insurance_form', 'insurance_panel')">保存这个保险</a>
            </div>
        </form>
    </div>
    <%--保险表单 终--%>
</div>
<script src="/js/sales/insurance/list.js" type="text/javascript"></script>
<script src="/js/xheditor-1.2.2/xheditor-1.2.2.min.js" type="text/javascript"></script>
<script src="/js/xheditor-1.2.2/xheditor_lang/zh-cn.js" type="text/javascript"></script>
</body>
</html>
