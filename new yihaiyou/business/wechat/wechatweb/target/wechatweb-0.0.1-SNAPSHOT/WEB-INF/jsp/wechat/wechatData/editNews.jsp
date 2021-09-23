<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/11/20
  Time: 09:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../../common/common141.jsp" %>
    <link href="/lib/bootstrap-fileinput4.3.6/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />

    <title>公众号管理</title>
    <style type="text/css">
        #main_body {
            margin: 0;
        }

        #left_div {
            width: 30%;
            height: 100%;
            background: #F8F8F8;
            float: left;
        }

        #right_div {
            width: 70%;
            height: 100%;
            padding: 10px;
        }

        #right_div table td {
            padding: 4px;
        }

        #show_text {
            padding-left: 12px;
            padding-top: 10px;
            height: 8%;
        }

        .appmsg_container_bd {
            padding-left: 12px;
            padding-right: 12px;
            height: 500px;
        }

        .appmsg_multi_editing {
            width: 210px;
            height: 138;
        }

        .appmsg_content {
            border-bottom-width: 1px;
        }

        .js_appmsg_item_appmsg_item_wrp_current {
            width: 210px;
            height: 138;
            border: 2px solid #43b548;
            position: relative;
        }

        .drag-item {
            list-style-type: none;
            display: block;
            padding: 0px;
            border: 1px solid #ccc;
            margin: 2px;
            width: 300px;
            background: #fafafa;
            color: #444;
        }

        .indicator {
            position: absolute;
            font-size: 9px;
            width: 10px;
            height: 10px;
            display: none;
            color: red;
        }

        .show_news {
            cursor: pointer;
        }

        .tooldiv {
            width: 320px;
            height: 40px;
        }

        .main {
            margin-left: 12px;
            position: relative;
            left: 250px;
            top: -30px;
        }

        #east_id table td {
            padding: 3px;
        }
    </style>
</head>
<body style="background:white;">


<div class="easyui-layout" data-options="fit:true">
    <div id="east_id" data-options="region:'center'" style="width:100%;padding:10px">
        <input type="hidden" id="hidden_index" name="index" value="${index}">
        <input type="hidden" id="newsId" name="id" value="">
        <input type="hidden" id="newsUserId" name="userId" value="">
        <input type="hidden" id="newsItemId" name="itemId" value="">
        <!-- 	<input type="hidden" id="" name="updateTime" value=""> --><!--
				<input type="hidden" id="" name="createTime" value=""> -->
        <table style="margin-left:10px;">
            <tr>
                <td><label>分类:</label></td>
                <td>
                    <input id="editNews_category" class="easyui-combotree" data-options="
                    url: '/goods/goods/getComboCatgoryData.jhtml?type=article',
                    required: true,
                    width: 350">
                    <input type="hidden" name="category">
                </td>
            </tr>
            <tr>
                <td><label>标题：</label></td>
                <td>
                    <input class="easyui-textbox" id="textbox_title" name="title" data-options="prompt:'必填'"
                           style="width:600px;height:30px;">
                </td>
            </tr>
            <tr>
                <td><label>作者：</label></td>
                <td>
                    <input class="easyui-textbox" id="textbox_author" name="author" data-options="prompt:'选填'"
                           style="width:600px;height:30px;">
                </td>
            </tr>
            <tr>
                <td><label>封面：</label></td>
                <td>
                    <div id="imgView" style="display:none;">
                        <img alt="" src="" width="100" height="70">
                        <a href="javascript:;" id="remove_img_id" onclick="EditNews.removeImg()"
                           class="easyui-linkbutton line-btn" style="margin-top:-20px;">删除</a>
                    </div>
                    <input type="hidden" id="filePath" name="img_path" value=""/>
                    <input type="button" id="add_descpic" onclick="" value="添加描述图"> <span style="color:gray;">大图片建议尺寸：900像素 * 500像素</span>
                    <br/>
                    <input type="hidden" id="hidden_ischeck" name="is_checked" value="0">

                    <div id="checkbox_div" style="color:gray;margin-top:5px;width:160px;cursor:pointer;">
                        <input id="img2text" type="checkbox"> <span>封面图片显示在正文中</span>
                    </div>
                </td>
            </tr>
            <tr>
                <td><label>摘要：</label></td>
                <td>
                    <input type="hidden" name="abstractText" id="hidden_abstract">
                    <!-- <textarea id="" rows=5 name="" class="textarea easyui-validatebox" }</textarea> -->
                    <input class="easyui-textbox" data-options="multiline:true" id="abstractStr" name="abstractText"
                           class="textarea easyui-validatebox"
                           style="width: 600px; height: 80px;"/>
                </td>
            </tr>
            <tr>
                <td><label>正文：</label></td>
                <td>
                    <input type="hidden" name="content" id="hidden_content" value=''>
	 						<textarea id="news_content" value=""
                                      style="width: 670px; height: 300px; visibility: hidden;"></textarea>
                </td>
            </tr>
            <tr>
                <td><label>原文链接：</label></td>
                <td>
                    <input class="easyui-textbox" id="text_box_url" name="url"
                           data-options="prompt:'选填',validType:'url'" style="width:600px;height:30px;">
                </td>
            </tr>

        </table>
        <table>
            <tr>
                <td colspan="1"></td>
                <td>

                </td>
            </tr>
        </table>


        <a id="btn" href="#" class="easyui-linkbutton" style="margin-left: 80px; width: 100px; margin-top: 10px;"
           onclick="EditNews.saveItem()" >保存</a>

    </div>

</div>
<script type="text/javascript" src="/lib/kindeditor-4.1.11-zh-CN/kindeditor-all-min.js"></script>
<script type="text/javascript" src="/js/wechat/wechat/editNews.js"></script>
</body>
</html>
