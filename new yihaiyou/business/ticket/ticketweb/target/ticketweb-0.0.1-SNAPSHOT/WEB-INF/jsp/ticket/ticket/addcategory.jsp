<%--
  Created by InteldivJ IDEA.
  User: vacuity
  Date: 15/10/14
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <%@ include file="../../common/common141.jsp" %>
<!--     <divnk rel="stylesheet" type="text/css" href="/css/addcategory.css"> -->
    <script type="text/javascript" src="/js/ticket/addcategory.js"></script>
    <link href="/css/ticket/addcategory.css" rel="stylesheet" type="text/css">

</head>
<body style="background-color: white;">
<div>
<!-- 	<div style="font-size: 16px;">新增服务分类</div> -->
<!--     <form id="cateCateFormId" class="c-form" action="/goods/goods/getForm.jhtml" method="post" enctype="multipart/form-data"> -->
    <form id="edit_categoryform" class="c-form" name="cateform" method="post" enctype="multipart/form-data">
        <ul>
            <input type="hidden" id="idValue" name="id">
            <li>
                <label for="edit_categoryname" class="keyzone">分类名称：<em>*</em></label>
                <div class="valuezone">
                    <input id="edit_categoryname" class="easyui-textbox" data-options="" name="categoryname" type="text" maxlength="10">
                    <span class="greyblock">(分类名称最多为十个汉字)</span>
                </div>
            </li>
            <li>
                <label class="keyzone">上级类别：</label>
                <div class="valuezone">
                    <input id="edit_parentcategory" class="easyui-combobox" name="parentcategory">
                    <p class="greyblock leftpadding">
                        一级分类中如果已有线路,此类别下添加二级分类,系统将默认清除线路属于该一级类别,请注意重新分类。
                    </p>
                </div>
            </li>
            <li id="edit_picture">
                <label class="keyzone">分类图片：</label>

                <div class="valuezone">
                    <input type="file" name="img">

                    <p class="greyblock leftpadding">请上传最大宽158像素，最大高度150像素的图片。如果超过，系统将自动截取成158*36的缩略图。</p>

                    <p class="greyblock leftpadding">点击浏览选择JPG或GIF格式的文件。子类别不能上传图片，最大支持上传100KB的文件</p>
                </div>
            </li>
            <li>
                <label class="keyzone">
                    排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序：
                    <em>*</em>
                </label>

                <div class="valuezone">
                    <input name="sortorder" type="number">
                    <span class="greyblock">数字越小排越前，仅对网店有效</span>
                </div>
            </li>
            <li>
                <label class="keyzone">是否显示：</label>
                <div class="valuezone">
                    <input id="edit_status" name="status">
                </div>
            </li>
        </ul>
        <div style="text-align:center;padding:5px; clear: both;">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="GoodsList.submitForm('edit_categoryform', 'edit_panel')">保存</a>
        </div>
    </form>
</div>

</body>


</html>
