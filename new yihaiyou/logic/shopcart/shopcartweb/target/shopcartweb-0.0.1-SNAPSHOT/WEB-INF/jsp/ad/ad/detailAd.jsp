<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台管理</title>
<%@ include file="../../common/common141.jsp"%>
<script type="text/javascript" src="/js/ad/addetail.js"></script>
<link href="/css/ad/detailAd.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/common/preview.js"></script>
<script type="text/javascript">
	$(function(){
		AD.init();
	});
</script>
</head>
<body >
	<div id="tb"></div>
	<div class="easyui-tabs" fit="true" >
        <div title="图片对比" style="padding:10px" >
        	<div class="inputItem">
	        	图片名称:<input type="text" placeholder="图片名称"  id="image_name" class="ui-txt02 width500 height25" 
	        		onpaste="return false;" oncopy="return false;"/>
	        	<i class="ui-ico-password"></i>
	        </div>
	        <div class="inputItem">
	        	图片位置:<input type="text" placeholder="图片位置"  id="image_position" class="ui-txt02 width500 height25" 
	        		onpaste="return false;" oncopy="return false;"/>
	        	<i class="ui-ico-password"></i>
	        </div>
	        <div class="m_t10"   >
	        <script id="compareTemplate" type="text/x-jsrender">
				<div id="compare_{{:index}}" class="comparePanel" >
	        	<div class="panel-header" style="width: 354px;">
	        		<div class="panel-title"> </div>
	        		<div class="panel-tool"><a class="panel-tool-collapse" href="javascript:void(0)"></a><a class="panel-tool-close" href="javascript:void(0)"></a>
	        		</div>
        		</div>
			        <div class="camparefrom" >
			        <form id="ff_{{:index}}" method="post">
			        	<div>
        					<ul>
			        			<li class="fl ">
			        				<div class="inputItem">
							        	目 标 组 :<input type="text"   id="target_group" class="ui-txt02  m_b10" 
							        		onpaste="return false;" oncopy="return false;"/>
							        </div>
							        <div class="inputItem">
							        	跳转链接:<input type="text"   id="skip_url" class="ui-txt02  m_b10" 
							        		onpaste="return false;" oncopy="return false;"/>
							        </div>
							        <div class="inputItem">
							        	打开方式:<input type="radio" class="radio" value="0" name="open_method" />新窗口 <input type="radio" class="radio" value="1" name="open_method" />当前窗口
							        </div>
							        <div class="inputItem">
							        	目标活动:<input type="text"   id="target_group" class="ui-txt02  m_b10" 
							        		onpaste="return false;" oncopy="return false;"/>
							        </div>
							        <div class="inputItem">
							        	用户特征:<input type="text"   id="target_url" class="ui-txt02  m_b10" 
							        		onpaste="return false;" oncopy="return false;"/>
							        </div>
							        <div class="inputItem">
							        	产品特征:<input type="text"   id="skip_url" class="ui-txt02  m_b10" 
							        		onpaste="return false;" oncopy="return false;"/>
							        </div>
			        			</li>
			        			<li class="fl ml20">
			        				<div class="inputItem m_b10">
			        					<div class="imgbox" id="imgbox_{{:index}}">
			        						<img src="" alt="" id="imgshow_{{:index}}" />
			        					</div>
							        </div>
							        <div class="inputItem">
			        					<input  type="file"  onchange="previewImage(this,'imgbox_{{:index}}','imgshow_{{:index}}')" id="file1" name="file1" value="选择" style="display: none">
			        					<a href="javascript:void(0)"  class="easyui-linkbutton"	  onclick="$('#file1').click()">浏览</a>
			        					<a href="javascript:void(0)" class="easyui-linkbutton"	  onclick="">上传</a>
							        </div>
			        			</li>
			        		</ul>
			        	</div>
			        </form>
			        </div>
			    </div>
			</script>
			    <div id="compare_1"   class="comparePanel" >
	        	<div class="panel-header" style="width: 354px;">
	        		<div class="panel-title"> </div>
	        		<div class="panel-tool"><a class="panel-tool-collapse" href="javascript:void(0)"></a><a class="panel-tool-close" href="javascript:void(0)"></a>
	        		</div>
        		</div>
			        <div style="padding:5px 10px;border: 1px solid #95B8E7;border-top:0;height: 207px;">
			        <form id="ff0" method="post">
			        	<div>
        					<ul>
			        			<li class="fl ">
			        				<div class="inputItem">
							        	目 标 组 :<input type="text"   id="target_group" class="ui-txt02  m_b10" 
							        		onpaste="return false;" oncopy="return false;"/>
							        </div>
							        <div class="inputItem">
							        	跳转链接:<input type="text"   id="skip_url" class="ui-txt02  m_b10" 
							        		onpaste="return false;" oncopy="return false;"/>
							        </div>
							        <div class="inputItem">
							        	打开方式:<input type="radio" class="radio" value="0" name="open_method" />新窗口 <input type="radio" class="radio" value="1" name="open_method" />当前窗口
							        </div>
							        <div class="inputItem">
							        	目标活动:<input type="text"   id="target_group" class="ui-txt02  m_b10" 
							        		onpaste="return false;" oncopy="return false;"/>
							        </div>
							        <div class="inputItem">
							        	用户特征:<input type="text"   id="target_url" class="ui-txt02  m_b10" 
							        		onpaste="return false;" oncopy="return false;"/>
							        </div>
							        <div class="inputItem">
							        	产品特征:<input type="text"   id="skip_url" class="ui-txt02  m_b10" 
							        		onpaste="return false;" oncopy="return false;"/>
							        </div>
			        			</li>
			        			<li class="fl ml20">
			        				<div class="inputItem m_b10">
			        					<div class="imgbox" id="imgbox">
			        						<img src="" alt="" id="imgshow" />
			        					</div>
							        </div>
							        <div class="inputItem">
			        					<input  type="file"  onchange="previewImage(this,'imgbox','imgshow')" id="file1" name="file1" value="选择" style="display: none">
			        					<a href="javascript:void(0)"  class="easyui-linkbutton"	  onclick="$('#file1').click()">浏览</a>
			        					<a href="javascript:void(0)" class="easyui-linkbutton"	  onclick="">上传</a>
							        </div>
			        			</li>
			        		</ul>
			        	</div>
			        </form>
			        </div>
			    </div>
	        </div>
        </div>
        <div title="页面对比" style="padding:10px" >
        	bb
        </div>
    </div>
</body>
</html>