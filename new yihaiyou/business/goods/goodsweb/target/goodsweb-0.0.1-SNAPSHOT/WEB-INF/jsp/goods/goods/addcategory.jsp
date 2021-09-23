<%--
  Created by IntelliJ IDEA.
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
    <link rel="stylesheet" type="text/css" href="/css/addcategory.css">
    <script type="text/javascript" src="/js/addcategory.js"></script>
</head>
<body>
<div>

    <form id="cateCateFormId" class="c-form" action="/goods/goods/getForm.jhtml" method="post" enctype="multipart/form-data">

            <ul>
                <li>
                    <label for="category" class="keyzone">所属分类：</label>
                    <div class="valuezone">
                        <input id="add-servicecategory" name="category" style="display:none;" type="radio" value="service" checked="checked">服务分类
                    <span class="greyblock">
                        (单项服务请选择)
                    </span>
                    </div>
                </li>
                <li>
                    <label for="categoryname" class="keyzone">
                        分类类别：
                        <em>*</em>
                    </label>
                    <div class="valuezone">
                        <input id="add-categoryname" name="inputname" type="text" maxlength="10" required="true">
                    <span class="greyblock">
                        (分类名称最多为十个汉字)
                    </span>
                    </div>
                </li>
                <li>
                    <label class="keyzone">上级类别：</label>
                    <div class="valuezone">
                        <input id="add-parentcategory" class="easyui-combobox" name="parentcate" value="上级分类">
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
                        <input name="sortorder" type="number" required="true">
                        <span class="greyblock">数字越小排越前，仅对网店有效</span>
                    </div>
                </li>
                <li>
                    <label class="keyzone">是否显示：</label>
                    <div class="valuezone">
                        <input name="visableflag" type="radio" value="1">显示
                        <input name="visableflag" type="radio" value="0">隐藏
                    </div>
                </li>
            </ul>
            <input id="add-submitbutton" class="c-subbtn" type="submit" value="提交发布" href="/goods/goods/goodslist.jhtml">
        </form>
</div>

</body>


</html>
