<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/14
  Time: 10:09
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>产品分类</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/goodslist.css">

</head>
<body>
<div id="tt" class="easyui-tabs" data-options="tools: '#tab-tools', toolPosition:'right'"
     style="height:250px;" fit="true">
</div>
<div id="tab-tools">
    <a id="refreshbtn" class="easyui-linkbutton needmargin" onclick="GoodsList.refresh()">刷新</a>
    <%--<a id="addsuperbtn" class="easyui-linkbutton needmargin" onclick="GoodsList.addSuperCatrgory()">添加顶级分类</a>--%>
    <a id="addbtn" class="easyui-linkbutton needmargin" onclick="GoodsList.addCategory()">添加子级分类</a>
    <label id="exinfo">(最多添加50个分类)</label>
</div>
<div class="easyui-dialog c-panel" id="add_panel" closed="true" style="z-Index: 100; width: 550px; height: 435px">
    <form id="add_categoryform" class="c-form" method="post" enctype="multipart/form-data">
        <ul>
            <li>
                <label for="category" class="keyzone">所属分类：</label>

                <div class="valuezone">
                    <input data-options="required:true" id="add_category_type" name="categoryType" class="categoryType">
                    <span class="greyblock">(单项服务请选择)</span>
                </div>
            </li>
            <li>
                <label for="categoryname" class="keyzone">
                    分类名称：
                    <em>*</em>
                </label>

                <div class="valuezone">
                    <input data-options="required:true" class="easyui-textbox" id="add-categoryname"
                           name="categoryname" type="text" maxlength="20" required="true">
                    <span class="greyblock">
                        (分类名称最多20个汉字)
                    </span>
                </div>
            </li>
            <li>
                <label class="keyzone">上级类别：</label>

                <div class="valuezone">
                    <input id="add_parentcategory" class="easyui-combobox" name="parentcategory">

                    <p class="greyblock leftpadding">
                        一级分类中如果已有线路，此类别下添加二级分类，系统将默认清除线路属于该一级类别，请注意重新分类。
                    </p>
                </div>
            </li>
            <li id="add-picture">
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
                    <input data-options="required:true" min="0" class="easyui-numberbox" name="sortorder">
                    <span class="greyblock">数字越小排越前，仅对网店有效</span>
                </div>
            </li>
            <li>
                <label class="keyzone">是否显示：</label>

                <div class="valuezone">
                    <input data-options="required:true" id="add_status" name="status">
                </div>
            </li>
        </ul>
        <div style="text-align:center;padding:5px; clear: both;">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="GoodsList.submitForm('add_categoryform', 'add_panel')">保存</a>
        </div>
    </form>
</div>

<div class="easyui-dialog c-panel" id="edit_panel" closed="true" style="z-Index: 100; width: 550px; height: 435px">
    <form id="edit_categoryform" class="c-form" name="cateform" method="post" enctype="multipart/form-data">
        <ul>
            <input type="hidden" id="idValue" name="id">
            <li>
                <label for="edit_category_type" class="keyzone">所属分类：</label>

                <div class="valuezone">
                    <input id="edit_category_type" data-options="required:true" class="easyui-textbox"
                           name="categoryType" class="categoryType">
                    <span class="greyblock">(单项服务请选择)</span>
                </div>
            </li>
            <li>
                <label for="edit_categoryname" class="keyzone">分类名称：<em>*</em></label>

                <div class="valuezone">
                    <input id="edit_categoryname" data-options="required:true" class="easyui-textbox"
                           name="categoryname" type="text" maxlength="20">
                    <span class="greyblock">(分类名称最多为20个汉字)</span>
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
                    <input data-options="required:true" min="0" class="easyui-numberbox" name="sortorder">
                    <span class="greyblock">数字越小排越前，仅对网店有效</span>
                </div>
            </li>
            <li>
                <label class="keyzone">是否显示：</label>

                <div class="valuezone">
                    <input id="edit_status" data-options="required:true" class="easyui-textbox"name="status">
                </div>
            </li>
        </ul>
        <div style="text-align:center;padding:5px; clear: both;">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="GoodsList.submitForm('edit_categoryform', 'edit_panel')">保存</a>
        </div>
    </form>
</div>
<div class="easyui-dialog" id="add_super_panel" closed="true"
     style="z-Index: 100; width: 350px; height: 150px; text-align: center">
    <form id="add_supercategoryform" method="post" enctype="multipart/form-data">
        <ul>
            <li>
                <label for="add_supercategory_name" class="keyzone">顶级分类名称：<em>*</em></label>

                <div class="valuezone">
                    <input id="add_supercategory_name" name="typeDes" type="text"
                           class="easyui-textbox" data-options="required:true">
                </div>
            </li>
        </ul>
        <div style="text-align:center;padding:5px; clear: both;">
            <a href="javascript:void(0)" class="easyui-linkbutton"
               onclick="GoodsList.doAddSuperCatrgory()">保存</a>
        </div>
    </form>
</div>
<script type="text/javascript" src="/js/goodsList.js"></script>
<script type="text/javascript" src="/js/addcategory.js"></script>
<script type="text/javascript" src="/js/editcategory.js"></script>
</body>
</html>