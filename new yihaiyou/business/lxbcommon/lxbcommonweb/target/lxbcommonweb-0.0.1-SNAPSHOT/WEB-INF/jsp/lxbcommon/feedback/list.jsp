<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/4/15
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户反馈列表</title>
    <%@ include file="../../common/common141.jsp" %>
    <link rel="stylesheet" href="/css/feedback.list.css">
</head>
<body>
<div title="用户反馈管理" data-options="fit:true,border:false" style="width:100%;height:100%;"
     class="easyui-layout" id="content">
    <div id="feedback-searcher" style="padding:3px">
        <span>
            <input id="qry_contact" class="easyui-textbox" data-options="prompt:'联系方式'" style="width:200px;">
        </span>
        <span>
            <input id="qry_status" class="easyui-combobox" editable="false"
                   data-options="prompt:'状态',
											valueField:'id',
											textField:'text',
											data:[
												{id:'',text:'全部'},
												{id:'OPEN',text:'未回复'},
												{id:'REPLYED',text:'已回复'},
												{id:'CLOSED',text:'已关闭'}
											]
						" style="width:120px; visibility: hidden;">
        </span>
        <a href="#" class="easyui-linkbutton" style="width: 80px;" onclick="Feedback.doSearch()">查询</a>
        <a href="#" class="easyui-linkbutton" style="width: 80px;" onclick="Feedback.doClear()">重置</a>
    </div>
    <!-- 数据表格 始 -->
    <div data-options="region:'center',border:false">
        <table id="feedbackDg"></table>
    </div>
    <!-- 数据表格 终-->
    <div class="easyui-dialog feedback_dialog" id="detail_panel" closed="true" style="width:500px;top: 80px;">
        <form id="detail_form" method="post" enctype="multipart/form-data" action="">
            <ul>
                <li>
                    <label>反&nbsp;馈&nbsp;ID：</label>
                    <input class="disa" name="feedback.id" disabled style="width: 60px;">
                    <input type="hidden" class="disa" name="id" style="width: 60px;">
                </li>
                <li>
                    <label>反&nbsp;馈&nbsp;者：</label>
                    <input class="disa" name="creatorName" disabled>
                </li>
                <li>
                    <label>联系方式：</label>
                    <input class="disa" name="contact" disabled>
                </li>
                <li>
                    <label>反馈时间：</label>
                    <input class="disa" name="createTime" disabled>
                </li>
                <li>
                    <label class="vam">反馈内容：</label>
                    <input class="easyui-textbox vam" name="content" data-options="prompt:'反馈内容', multiline:true" style="width: 350px; height: 80px;" readonly>
                    <%--<textarea class="vam" name="content" disabled
                              style="width: 350px; height: 80px; background: #FFFFFF; cursor: not-allowed;">
                    </textarea>--%>
                </li>
                <li>
                    <label class="vam">回复内容：</label>
                    <input class="easyui-textbox vam" name="feedback.replyContent" data-options="prompt:'回复内容', multiline:true" style="width: 350px; height: 80px;" readonly>
                    <%--<textarea class="vam" name="feedback.replyContent" disabled
                              style="width: 350px; height: 80px;background: #FFFFFF; cursor: not-allowed;">
                    </textarea>--%>
                </li>
            </ul>
        </form>
    </div>
    <!-- reply -->
    <div class="easyui-dialog feedback_dialog" id="reply_panel" closed="true" style="width:500px;top: 80px;">
        <form id="reply_form" method="post" enctype="multipart/form-data" action="">
            <ul>
                <li>
                    <label>反&nbsp;馈&nbsp;ID：</label>
                    <input class="disa" name="feedback.id" disabled style="width: 60px;">
                    <input type="hidden" class="disa" name="id" style="width: 60px;">
                </li>
                <li>
                    <label>反&nbsp;馈&nbsp;者：</label>
                    <input class="disa" name="creatorName" disabled>
                </li>
                <li>
                    <label>联系方式：</label>
                    <input class="disa" name="contact" disabled>
                </li>
                <li>
                    <label>反馈时间：</label>
                    <input class="disa" name="createTime" disabled>
                </li>
                <li>
                    <label class="vam">反馈内容：</label>
                    <input class="easyui-textbox vam" name="content" data-options="prompt:'反馈内容', multiline:true" style="width: 350px; height: 80px;" readonly>
                    <%--<textarea class="vam" name="content" disabled
                              style="width: 350px; height: 80px; background: #FFFFFF; cursor: not-allowed;">
                    </textarea>--%>
                </li>
                <li>
                    <label class="vam">回复内容：</label>
                    <input class="easyui-textbox vam" name="feedback.replyContent" data-options="prompt:'回复内容', multiline:true" style="width: 350px; height: 80px;">
                    <%--<textarea class="vam" name="feedback.replyContent" style="width: 350px; height: 80px;"></textarea>--%>
                </li>
            </ul>
            <div style="text-align:center;padding:5px; clear: both;">
                <a href="javascript:void(0)" class="easyui-linkbutton"
                   onclick="Feedback.commitForm('reply_form', 'reply_panel')">确认回复</a>
            </div>
        </form>
    </div>
    <!-- reply end -->
</div>
<script src="/js/lxbcommon/feedback/list.js" type="text/javascript"></script>
</body>
</html>
