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
<script type="text/javascript" src="/js/customer/custype.js"></script>
<link href="/css/customer/custype.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	$(function(){
		CusType.init();
	});
</script>
</head>
<body class="easyui-layout">
	<!-- 属性展示 布局右 -->
    <div data-options="region:'east',split:true" style="width:400px;">
    	<div id="aa" class="easyui-accordion" style="width:400px;height:auto;">
    		<s:iterator value="cptlist" var="item" status="st">
    			<div title="<s:property value='typeName' />"  style="overflow:auto;padding:10px;">
    				<s:iterator value="cpmap[#item.id]" var="item2" status="#st2">
    					<a href="#" cptid="<s:property value='#item.id' />" cpid="<s:property value='#item2.id' />" cpname="<s:property value='#item2.propertyName' />" class="easyui-linkbutton propertys" ><s:property value="#item2.propertyName" /></a>
    				</s:iterator>
    			</div>
    		</s:iterator>
    	</div>
    </div>   
    <div data-options="region:'west',title:'客户分组类别',split:true" style="width:220px;">
    	<!-- 客户分层树 -->
    	<div id="tt"></div>
    	<!-- 右键菜单 -->
		<div id="mm" class="easyui-menu"  style="width:120px;">
			<div onClick="showAddPackage()" >添加客户分组</div>
			<div  >添加分组类目</div>
			<div  >修改分组类目</div>
			<div  >显示</div>
			<div  >隐藏</div>
			<div  >上移</div>
			<div  >下移</div>
			<div class="menu-sep"></div>
			<div  >移除</div>
		</div>
    </div>
    <!-- begin 地址管理工具栏 包括搜索框和增删改按钮  -->
	<div id="toolbar">
		<span>分类:</span> <input id="consignee" style="width: 80px;" class="easyui-validatebox">
		<a href="#" class="easyui-linkbutton"
			 onclick="doSearch()">查询</a>
	</div>   
    <div data-options="region:'center',title:''"  style="padding:5px;background:#eee;">
    	<div class="easyui-panel m_b10">
	    	<div class="inputItem">
	        	分组名称:<input type="text" placeholder="分组名称"  id="password_dz" class="ui-txt02 width300 height25" 
	        		onpaste="return false;" oncopy="return false;"/>
	        	<i class="ui-ico-password"></i>
	        </div>
	        <div class="inputItem">
	        	分组类目:<input type="text" placeholder="分组类目"  id="password_dz" class="ui-txt02 width300 height25" 
	        		onpaste="return false;" oncopy="return false;"/>
	        	<i class="ui-ico-password"></i>
	        </div>
    	</div>
    	<s:iterator value="cptlist" var="item" status="st">
    		<s:if test="#st.index==0">
    			<div cpptid="<s:property value='id' />" class="easyui-panel pd5" title="<s:property value='typeName' />" data-options="collapsible:true" >
    				<ul cptid="<s:property value='id' />" class="propertyUL">
    					<li  cpid="1">
    						<span class="fl">
	    						地区:<s:select list="#{'等于':'等于','不等于':'不等于'}"></s:select> <s:select list="#{'厦门':'厦门','北京':'北京'}"></s:select>
    						</span>
    						<span class="fr">
    							<a href="javascript:void(0)" class="easyui-linkbutton"	 ></a>
    						</span>
    					</li>
    				</ul>
    			</div>
    		</s:if>
    		<s:else>
    			<div cpptid="<s:property value='id' />" class="easyui-panel pd5" title="<s:property value='typeName' />" data-options="collapsible:true,closed:true" >
    				
    			</div>
    		</s:else>
   			
   		</s:iterator>
    	<div class="easyui-panel m_t10" >
    		<a href="javascript:void(0)" class="easyui-linkbutton"	 onclick="newAddress()">统计</a>
    		<a href="javascript:void(0)" class="easyui-linkbutton"	 onclick="newAddress()">客户分析</a>
    		<a href="javascript:void(0)" class="easyui-linkbutton"	 onclick="newAddress()">保存</a>
    	</div>
    </div>  
</body>
</html>