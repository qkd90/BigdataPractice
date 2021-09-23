<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/10/26
  Time: 13:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%@ include file="../../common/common141.jsp"%>
  <link href="/js/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css">
  <%--<link href="/css/ticket/form.css" rel="stylesheet" type="text/css">--%>
  <script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
  <link rel="stylesheet" type="text/css" href="/css/ad/addAds.css">
  <script type="text/javascript" src="/js/ad/manage.js"></script>
  <script type="text/javascript" src="/js/ad/adsUtil.js"></script>
  <title></title>
</head>
<body>
    <div id="content" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;">
    <!-- 表格工具条 始 -->
      <div id="tb">
        <div style="padding:2px 2px;">
          <form id="searchform">
              <label>广告位置：</label>
            <input id="ads_location" style="width:220px;"/>
            <label>开始日期：</label>
            <input id="ads_stime" name="sTime" style="width:100px;"/>
            <label>结束日期：</label>
            <input id="ads_etime" name="eTime" style="width:100px;"/>
            <input id="ads_opentype" style="width:100px;"/>
            <input id="ads_status" style="width:100px;"/>
            <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Ads.doSearch()">查询</a>
            <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Ads.doClear()">重置</a>
          </form>
        </div>
        <div style="padding:2px 2px;">
          <a href="javascript:void(0)" onclick="Ads.addForm()" class="easyui-linkbutton" >新增广告</a>
          <a href="javascript:void(0)" onclick="Ads.doDel('dg');" class="easyui-linkbutton" >删除</a>
          <a href="javascript:void(0)" onclick="Ads.doUp('dg');" class="easyui-linkbutton" >上架</a>
          <a href="javascript:void(0)" onclick="Ads.doDown('dg');" class="easyui-linkbutton" >下架</a>
        </div>
      </div>
    <!-- 表格工具条 终 -->

    <!-- 数据表格 始 -->
      <div data-options="region:'center',border:false">
        <table id="dg"></table>
      </div>

        <!-- 编辑窗口 -->
      <div id="editPanel" class="easyui-dialog" title="录入订单信息"
           data-options="resizable:false,modal:true,closed:true" style="top:25%;left: 35%;">
        <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:100%;"></iframe>
      </div>

    <!-- 数据表格 终-->
      <%--<div class="easyui-dialog" id="add_panel" closed="true" style="width:500px;top: 80px; padding: 5px;">
          <form id="adsform" action="" method="post" enctype="multipart/form-data">
            <ul>
              <li>
                <label class="keyzone">广告位置：</label>
                <div class="valuezone">
                  <input id="add_ads_location" name="adsLocation" style="width:220px;"/>
                </div>
              </li>
                <li>
                    <label class="keyzone">广告标题：</label>
                    <div class="valuezone">
                        <input id="add_ads_adTitle" class="easyui-textbox" name="adTitle" style="width:120px;"/>
                    </div>
                </li>
                <li>
                    <label class="keyzone">广告排序：</label>
                    <div class="valuezone">
                        <input id="add_ads_sort" class="easyui-numberbox" name="adSort" style="width:120px;"/>
                    </div>
                </li>
              <li>
                <label class="keyzone">广告URL：</label>
                <div class="valuezone">
                  <input id="qry_keyword" name="adsUrl" class="easyui-textbox" data-options="prompt:'线路名称关键词或编号'" style="width:200px;">
                </div>
              </li>
              <li id="picture">
                <label class="keyzone">广告图片：</label>

                <div class="valuezone">
                  <input id="filePath" name="imgPath" type="hidden" />
                  <input type="button" class="J_add_pic" id="add_descpic" value="添加描述图">
                    &lt;%&ndash;<input type="file" name="img" class="leftpadding">&ndash;%&gt;
                </div>
              </li>
              <li>
                <label  class="keyzone">
                  开始时间：
                </label>
                <div class="valuezone">
                  <input id="add_ads_stime" name="adsStime" style="width:120px;"/>
                </div>
              </li>
              <li>
                <label  class="keyzone">
                  结束时间：
                </label>
                <div class="valuezone">
                  <input id="add_ads_etime" name="adsEtime" style="width:120px;"/>
                </div>
              </li>

              <li>
                <label class="keyzone">
                  打开方式：
                </label>
                <div class="valuezone">
                  <input id="add_ads_opentype" name="adsOpenType" style="width:120px;"/>
                </div>
              </li>
              <li>
                <label class="keyzone">广告状态：</label>
                <div class="valuezone">
                  <input id="add_ads_status" name="adsStatus" style="width:120px;"/>
                </div>
              </li>
            </ul>

            <div id="imgView" style="display:none;position: absolute;bottom: 55px;left: 245px;">
              <img alt="" src="" width="200" height="120" style="border-radius: 5px;">
              <a href="javascript:;" id="remove_img_id" class="easyui-linkbutton line-btn" onclick="Ads.deleteImg()" style="margin-left: -48px;margin-top: -23px;">删除</a>
            </div>

            &lt;%&ndash;<div style="text-align:center;padding:5px">
              &lt;%&ndash;<input id="submitbutton" type="submit" value="提交" href="/ad/ad/manage.jhtml">&ndash;%&gt;
              &lt;%&ndash;<a href="javascript:void(0)" class="easyui-linkbutton"  onclick="">提交</a>&ndash;%&gt;
            </div>&ndash;%&gt;
          </form>

      </div>

      <div class="easyui-dialog" id="edit_panel" closed="true" style="width:500px;top: 80px;padding: 5px;">
        <form id="editform" action="" method="post" enctype="multipart/form-data">
          <ul>
            <li>
              <input type="hidden" id="idValue" name="id" value="">
            </li>
            <li>
              <label class="keyzone">广告位置：</label>
              <div class="valuezone">
                <input id="edit_ads_location" name="adsLocation" style="width:220px;" value=""/>
              </div>
            </li>
              <li>
                  <label class="keyzone">广告标题：</label>
                  <div class="valuezone">
                      <input id="edit_ads_adTitle" class="easyui-textbox" name="adTitle" style="width:120px;" value=""/>
                  </div>
              </li>
              <li>
                  <label class="keyzone">广告排序：</label>
                  <div class="valuezone">
                      <input id="edit_ads_sort" class="easyui-numberbox" name="adSort" style="width:120px;" value=""/>
                  </div>
              </li>
            <li>
              <label class="keyzone">广告URL：</label>
              <div class="valuezone">
                <input id="adsurl" name="adsUrl" class="easyui-textbox" style="width:200px;" value="">
              </div>
            </li>
            <li id="edit_picture">
              <label class="keyzone">广告图片：</label>

              <div class="valuezone">
                <input id="edit_filePath" name="imgPath" type="hidden" />
                <input type="button" class="J_add_pic" id="edit_descpic" value="添加描述图">
                &lt;%&ndash;<input type="file" name="img" class="leftpadding">&ndash;%&gt;
              </div>
            </li>
            <li>
              <label  class="keyzone">
                开始时间：
              </label>
              <div class="valuezone">
                <input id="edit_ads_stime" name="adsStime" style="width:120px;" value=""/>
              </div>
            </li>
            <li>
              <label  class="keyzone">
                结束时间：
              </label>
              <div class="valuezone">
                <input id="edit_ads_etime" name="adsEtime" style="width:120px;" value=""/>
              </div>
            </li>
            <li>
              <label class="keyzone">
                打开方式：
              </label>
              <div class="valuezone">
                <input id="edit_ads_opentype" name="adsOpenType" style="width:120px;" value=""/>
              </div>
            </li>
            <li>
              <label class="keyzone">广告状态：</label>
              <div class="valuezone">
                <input id="edit_ads_status" name="adsStatus" style="width:120px;" value=""/>
              </div>
            </li>
          </ul>

          <div id="edit_imgView" style="display:none; position: absolute;bottom: 55px;left: 245px;">
            <img alt="" src="" width="200" height="120" style="border-radius: 5px;">
            <a href="javascript:;" class="easyui-linkbutton line-btn" onclick="Ads.deleteEditImg()" style="margin-left: -48px;margin-top: -23px;">删除</a>
          </div>

          &lt;%&ndash;<div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton"  onclick="Ads.commitForm('editform', 'edit_panel')">提交</a>
          </div>&ndash;%&gt;
        </form>
      </div>--%>

  </div>

</body>
</html>
