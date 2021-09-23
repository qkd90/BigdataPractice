<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/4/25
  Time: 10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>评论数据管理</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" type="text/css" href="/css/comment.list.css">
</head>
<body>
<div data-options="fit:true,border:false" style="width:100%;height:100%;"
     class="easyui-layout" id="content">
    <div id="comment-searcher" style="padding:3px">
        <span>
            <select id="search-type">
                <option value="comment.user.userName" selected>评论者用户名</option>
                <option value="comment.user.nickName" selected>评论者昵称</option>
                <option value="comment.content" selected>评论内容</option>
            </select>
        </span>
        <input id="search-content" placeholder="输入查询内容"
               style="line-height:22px;border:1px solid #ccc; padding: 0 3px;">
        <span>
            <select id="search-status">
                <option value="" selected>所有状态</option>
                <option value="NORMAL">正常</option>
                <option value="DELETED">已删除</option>
            </select>
        </span>
        <span>
            <select id="search-sort-property">
                <option value="createTime" selected>评论时间</option>
            </select>
        </span>
        <span>
            <select id="search-sort-type">
                <option value="desc" selected>倒序</option>
                <option value="asc">升序</option>
            </select>
        </span>
        <span>
            <label>评论类型：</label>
            <input id="search-comment-type" name="productType" class="easyui-combobox">
        </span>
        <span>
            <label>评分项: </label>
            <input id="search-comment-scoreType" name="scoreTypeId" class="easyui-combobox" disabled
                    data-options="width:90">
        </span>
        <span>
            <label>分数范围(百分制)：</label>
            <input style="width: 60px; line-height:22px;border:1px solid #ccc; padding: 0 3px;"
                   id="minScore" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="最低评分">
            <label>-</label>
            <input style="width: 60px; line-height:22px;border:1px solid #ccc; padding: 0 3px;"
                   id="maxScore" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="最高评分">
        </span>
        <a href="#" class="easyui-linkbutton" style="width: 80px;"
           onclick="CommentMgr.doSearch()">查询</a>
    </div>
    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="commentTg"></table>
    </div>
    <!-- 数据表格 终-->
    <div class="easyui-dialog comment_dialog" id="reply_panel" closed="true" style="width:500px;top: 80px;">
        <form id="reply_form" method="post" enctype="multipart/form-data" action="">
            <ul>
                <li>
                    <label>评&nbsp;论&nbsp;ID：</label>
                    <input class="disa" name="comment.id" disabled style="width: 60px;">
                    <input type="hidden" class="disa" name="id" style="width: 60px;">
                </li>
                <li>
                    <label>用&nbsp;户&nbsp;名：</label>
                    <input class="disa" name="userName" disabled>
                </li>
                <li>
                    <label class="vam">评论内容：</label>
                    <textarea class="vam" name="content" disabled
                              style="width: 350px; height: 80px; background: #FFFFFF; cursor: not-allowed;">
                    </textarea>
                </li>
                <li>
                    <label class="vam">回复内容：</label>
                    <textarea class="vam" name="replyContent" style="width: 350px; height: 80px;"></textarea>
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="CommentMgr.commitForm('reply_form', 'reply_panel')">确认回复</a>
            </div>
        </form>
    </div>
    <div class="easyui-dialog comment_dialog" id="edit_panel" closed="true" style="width:500px;top: 80px;">
        <form id="edit_form" method="post" enctype="multipart/form-data" action="">
            <ul>
                <li>
                    <label>评&nbsp;论&nbsp;ID：</label>
                    <input class="disa" name="comment.id" disabled style="width: 60px;">
                    <input type="hidden" class="disa" name="id" style="width: 60px;">
                </li>
                <li>
                    <label>用&nbsp;户&nbsp;名：</label>
                    <input class="disa" name="userName" disabled>
                </li>
                <li>
                    <label class="vam">评论内容：</label>
                    <textarea class="vam" name="content" style="width: 350px; height: 80px;">
                    </textarea>
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="CommentMgr.editForm('edit_form', 'edit_panel')">确认修改</a>
            </div>
        </form>
    </div>
</div>
<script src="/js/lxbcommon/comment/list.js" type="text/javascript"></script>
</body>
</html>
