<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/1/23
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>目的地编辑</title>
    <%@include file="../common/common141.jsp" %>
    <script src="/js/xheditor-1.2.2/xheditor-1.2.2.min.js" type="text/javascript"></script>
    <script src="/js/xheditor-1.2.2/xheditor_lang/zh-cn.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
    <script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
    <script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>
    <script type="text/javascript" src="/js/destination/desEdit.js"></script>
</head>
<body style="overflow: auto">
<style>
    #imageBox {
        margin: 3px 3px;
        width: 300px;
        height: 200px;
        background: #e4f5ff
    }
</style>
<form id="destination_edit_form" method="post" enctype="multipart/form-data">
    <div class="easyui-accordion" data-options="multiple:true" style="width:100%;">
        <div title="基本信息"  selected="selected" style="padding:10px;">
            <div style="width:400px;margin-top: 10px;float:right">
                目的地封面图片：
                <div id="imageBox">
                    <div id="imagePanel"></div>
                </div>
            </div>
            <table>
                <tr>
                    <td>目的地名称:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="tbArea.name" id="areaName"
                               value="${tbArea.name}" data-options="required:true" disabled>
                    </td>
                    <td>目的地全名:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="tbArea.fullPath" id="fullPath"
                               value="${tbArea.fullPath}" data-options="required:true" disabled>
                    </td>
                </tr>
                <tr>
                    <td>目的地名称拼音:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="tbArea.pinyin" id="pinyin"
                               value="${tbArea.pinyin}" data-options="required:true">
                    </td>
                    <td>建议游玩时间:</td>
                    <td>
                        <input class="easyui-textbox" type="text" name="tbArea.tbAreaExtend.adviceTime" id="adviceTime"
                               value="${tbArea.tbAreaExtend.adviceTime}" data-options="required:true">
                    </td>
                </tr>
            </table>
            <div id="extend_info">
                <p>最佳游玩时间</p>
                 <textarea id="bestVisitTime_" rows="18" cols="100" maxlength="100"
                           data-options="multiline:true, required:true, max:100">
                     ${tbArea.tbAreaExtend.bestVisitTime}
                 </textarea>
                <input type="hidden" id="bestVisitTime" name="tbArea.tbAreaExtend.bestVisitTime"
                       value="${tbArea.tbAreaExtend.bestVisitTime}">
                <p>简介</p>
                 <textarea id="abs_" rows="18" cols="100"
                           data-options="multiline:true, required:true">
                     ${tbArea.tbAreaExtend.abs}
                 </textarea>
                <input type="hidden" id="abs" name="tbArea.tbAreaExtend.abs"
                       value="${tbArea.tbAreaExtend.abs}">
                <p>历史</p>
                 <textarea id="history_" rows="18" cols="100"
                           data-options="multiline:true, required:true">
                     ${tbArea.tbAreaExtend.history}
                 </textarea>
                <input type="hidden" id="history" name="tbArea.tbAreaExtend.history"
                       value="${tbArea.tbAreaExtend.history}">
                <p>艺术文化</p>
                 <textarea id="art_" rows="18" cols="100"
                           data-options="multiline:true, required:true">
                     ${tbArea.tbAreaExtend.art}
                 </textarea>
                <input type="hidden" id="art" name="tbArea.tbAreaExtend.art"
                       value="${tbArea.tbAreaExtend.art}">
                <p>当地气候</p>
                 <textarea id="weather_" rows="18" cols="100"
                           data-options="multiline:true, required:true">
                     ${tbArea.tbAreaExtend.weather}
                 </textarea>
                <input type="hidden" id="weather" name="tbArea.tbAreaExtend.weather"
                       value="${tbArea.tbAreaExtend.weather}">
                <p>地理</p>
                 <textarea id="geography_" rows="18" cols="100"
                           data-options="multiline:true, required:true">
                     ${tbArea.tbAreaExtend.geography}
                 </textarea>
                <input type="hidden" id="geography" name="tbArea.tbAreaExtend.geography"
                       value="${tbArea.tbAreaExtend.geography}">
                <p>当地环境</p>
                 <textarea id="environment_" rows="18" cols="100"
                           data-options="multiline:true, required:true">
                     ${tbArea.tbAreaExtend.environment}
                 </textarea>
                <input type="hidden" id="environment" name="tbArea.tbAreaExtend.environment"
                       value="${tbArea.tbAreaExtend.environment}">
                <p>当地文化</p>
                 <textarea id="culture_" rows="18" cols="100"
                           data-options="multiline:true, required:true">
                     ${tbArea.tbAreaExtend.culture}
                 </textarea>
                <input type="hidden" id="culture" name="tbArea.tbAreaExtend.culture"
                       value="${tbArea.tbAreaExtend.culture}">
                <p>当地语言</p>
                 <textarea id="language_" rows="18" cols="100"
                           data-options="multiline:true, required:true">
                     ${tbArea.tbAreaExtend.language}
                 </textarea>
                <input type="hidden" id="language" name="tbArea.tbAreaExtend.language"
                       value="${tbArea.tbAreaExtend.language}">
                <p>节日特色</p>
                 <textarea id="festival_" rows="18" cols="100"
                           data-options="multiline:true, required:true">
                     ${tbArea.tbAreaExtend.festival}
                 </textarea>
                <input type="hidden" id="festival" name="tbArea.tbAreaExtend.festival"
                       value="${tbArea.tbAreaExtend.festival}">
                <p>宗教信仰</p>
                 <textarea id="religion_" rows="18" cols="100"
                           data-options="multiline:true, required:true">
                     ${tbArea.tbAreaExtend.religion}
                 </textarea>
                <input type="hidden" id="religion" name="tbArea.tbAreaExtend.religion"
                       value="${tbArea.tbAreaExtend.religion}">
                <p>民族</p>
                 <textarea id="nation_" rows="18" cols="100"
                           data-options="multiline:true, required:true">
                     ${tbArea.tbAreaExtend.nation}
                 </textarea>
                <input type="hidden" id="nation" name="tbArea.tbAreaExtend.nation"
                       value="${tbArea.tbAreaExtend.nation}">
            </div>
        </div>
    </div>
    <div title="提交" id="submitDiv" style="text-align:right;padding:5px 10px">
        <input type="hidden" name="tbArea.tbAreaExtend.cover" id="cover" value="${tbArea.tbAreaExtend.cover}">
        <input type="hidden" name="tbArea.id" id="tbAreaId" value="${tbArea.id}"/>
        <input type="hidden" name="tbArea.cityCode" id="cityCode" value="${tbArea.cityCode}">
        <div style="padding:5px 0;">
            <p style="color:red;">管理员提交即时生效，请谨慎操作</p>
            <a href="#" style="width:120px;height:30px" class="easyui-linkbutton"
                onclick="saveDestination()">提交</a>
        </div>
    </div>
    </div>
</form>
</body>
</html>
