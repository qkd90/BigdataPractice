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
    <link href="/js/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css">
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
    <div data-options="region:'west'" style="width:32%;padding:0px;background:#F8F8F8;">
        <input type="hidden" id="hidden_newlist" value='${editJson}'>
        <input type="hidden" id="hidden_reply" value='${reply}'>

        <div id="show_text">
            <h2>图文导航</h2>
        </div>
        <div class="appmsg_container_bd">

            <div id="show_news_1" class="show_news" onclick="AddNews.selectDiv(1)"
                 style="width:320px;height:130px;background:#fff;border:1px solid #ccc;">
                <input type="hidden" id="news_1" class="news_index" value="1">
                <!-- 						<div id="title_1" class="news_title" style="float:left;margin-left:10px;margin-top:40px;display:none;"></div> -->
                <div id="mytitle_1" class="news_img_index"
                     style="width:100%;height:100%;padding-left:5px;padding-right:5px;top: -95px; position: relative;">
                    <img id="mytitle_img_1" alt="" src="" width="320" height="130"
                         style="position: relative; top: 95px; left: -5;">

                    <div id="title_1"
                         style="width: 300px; height: 20px;  bottom: 35; background: #040404; margin-left: 5px;  position: relative;  margin-top: 106px;opacity: 0.4;color:white;font-size:14px;padding:2px;">
                    </div>

                </div>
                <!-- <input class="news_click" style="display:none;" onclick="AddNews.delNews('+index+')" type="button" value="删除" class="main"> -->
                <div id="hide_tool_div1" class="tooldiv"
                     style=" top: -40px; position: relative; bottom: 0px;display:none;">
                    <div style="width:320px;height:40px; background: #ddd; position: relative; opacity: 0.5;"></div>
                </div>
            </div>
            <div>
                <a id="btn_addNews" href="javascript:void(0)" class="easyui-linkbutton"
                   style="width:320px;margin-left:2px;margin-top:5px;"
                    onclick="AddNews.addNews()">增加图文</a>
            </div>
        </div>
    </div>
    <div id="east_id" data-options="region:'east'" style="width:68%;padding:0px">
        <Iframe id="show_iframe" name="show" src="/wechat/wechatData/editNews.jhtml?index=1" width="100%" height="100%"
                scrolling="yes" frameborder="0"></iframe>
        <input type="hidden" id="itemId" value="">
        <input type="hidden" name="app_data" id="app_1" value="">
    </div>
</div>

<script type="text/javascript">
</script>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="/js/wechat/wechat/addNews.js"></script>
</body>
</html>
