<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
var FG_DOMAIN = '<s:property value="fgDomain"/>';
</script> 
<%@ include file="../../common/common141.jsp"%>
<link href="/css/ticket/form.css" rel="stylesheet" type="text/css">
<link href="/js/kindeditor/themes/default/default.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="/js/uploadprivew/uploadPreview.js"></script>
<script type="text/javascript" src="/js/sailboat/ticketUtil.js"></script>
<script type="text/javascript" src="/js/sailboat/ticketEdit.js"></script>
<base href="<%=basePath%>">
<title>用户管理</title>
</head>
<body>
	<style type="text/css">
		.text {
			font-size: 13px;
			color: rgb(2, 48, 97);
		}
	</style>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',split:true">
			<!--表单区域开始-->
			<form id="userInputForm" name="userInputForm" method="post" style="margin-top: 10px;">
				<input type="hidden" id="type_ticket" value="${ticket.ticketType}">
				<input type="hidden" id="ticket_agent" value="${ticket.agent}">
				<div class="row">
					<div class="first">船票类别：<em>*</em></div>
					<div class="second">
						<%--<a href="javascript:;" class="radio_tag "--%>
							<%--onClick="TicketEdit.sleTicCategory(1)" style="background: #FE7700" id="secTicket">景点门票</a>--%>
                        <%--<a href="javascript:;" class="radio_tag "--%>
							<%--onClick="TicketEdit.sleTicCategory(2)" id="perTicket">演出票</a>--%>
                        <a href="javascript:;" class="radio_tag "
							onClick="TicketEdit.sleTicCategory(3)" id="boatTicket">帆船船票</a>
                        <a href="javascript:;" class="radio_tag "
                           onClick="TicketEdit.sleTicCategory(4)" id="yachtTicket">游艇船票</a>
					</div>
					<input type="hidden" id="ticketId" name="ticket.id" value="${ticket.id}"/>
					<input type="hidden" id="ticke_type" name="ticket.ticketType" value="sailboat"/>
					<input type="hidden" id="scenic_id" name="ticket.scenicInfo.id" value="${ticket.scenicInfo.id}"/>
					<input type="hidden" id="ticket_name" name="ticket.ticketName" value="${ticket.ticketName}"/>
					<input type="hidden" id="ticket_area" name="ticket.cityId" value="${ticket.cityId}"/>
					<input type="hidden" id="ticket_address" name="ticket.address" value="${ticket.address}"/>
					<input type="hidden" id="ticket_startTime" name="beginTime" value="${ticket.startTime}"/>
					<input type="hidden" id="ticket_aji" value="${ticket.sceAji}"/>
				</div>
				<div class="row" id="div_jd_name">
					<div class="first">
						所属景点/码头：<em>*</em>
					</div>
					<div id="div1_sce" class="second">
						<input class="easyui-combobox" style="width: 220px" value=""
							id="scenicNameId"
							data-options="loader: TicketEdit.scenicLoader,
									mode: 'remote',
									valueField: 'id',
									textField: 'name'">
					</div>
					<input type="hidden" id="hidden_secName" value="${ticket.ticketName}">
					<div id="div2_sce" style="display:none;" class="second">
						<input class="easyui-combobox" id="scenicName" style="width: 220px" data-options="editable:false" value="${ticket.ticketName}" id="displaySecName" />
						<a id="btn_editSce" href="javascript:void(0)" class="easyui-linkbutton"
									 onclick="TicketEdit.editScenic()">修改景点/码头</a>
					</div>
					
				</div>
				<div class="row" id="div_jd_area">
					<div class="first">
						所属地区：<em>*</em>
					</div>
					<div class="second">
						<span id="sce_span_area"></span>
						<div id="sce_div_area">
						
							<select class="easyui-combobox" style="width: 193px"
								id="sec_proNameId" value="" >
								<option>请选择省份</option>
								</select>
							<select class="easyui-combobox" style="width: 193px"
							id="sec_cityNameId" value="0">
								<option>请选择城市或地区</option>
							</select>
							
							<input type="hidden" id="hidden_proId" value="">
							<input id="startCityIdHidden" type="hidden" value=""/>
						</div>
					</div>
				</div>
				<div class="row" id="div_jd_aji">
					<div class="first">
						景区A级：<em>*</em>
					</div>
					<div class="second">
					<span id="sce_span_aji"></span>
					<div id="sce_div_aji">
						<input type="hidden" id="hidden_sceAji" value="">
						<label class="input_radio"> <input name="ticket.sceAji" type="radio" value="5A">5A</label>
						<label class="input_radio"><input name="ticket.sceAji" type="radio" value="4A">4A</label>
						<label class="input_radio"><input name="ticket.sceAji" type="radio" value="3A">3A</label>
						<label class="input_radio"><input name="ticket.sceAji" type="radio" value="2A">2A</label>
						<label class="input_radio"><input name="ticket.sceAji" type="radio" value="1A">1A</label>
						<label class="input_radio"><input name="ticket.sceAji" type="radio" value="0A" checked="checked">未评级</label>
					
					</div>
					</div>
				</div>
				<div class="row" id="div_jd_address">
					<div class="first">
						地址：<em>*</em>
					</div>
					<div class="second">
						<span id="sce_span_address"></span>
						<div id="sce_div_address">
							<input class="easyui-textbox" id="jd_address" value=""
								style="width: 200px; line-height: 20px; border: 1px solid #ccc">
						</div>
					</div>
				</div>
				<!--演出类别选中后展现 -->
				<div class="row" id="div_yc_name" style="display: none">
					<div class="first">
						演出名称：<em>*</em>
					</div>
					<div class="second">
						<input class="easyui-textbox" id="yc_showName" value=""
							data-options="prompt:'18个字以内'"
							style="width: 200px; line-height: 20px; border: 1px solid #ccc">
					</div>
				</div>
				<div class="row" id="div_yc_area" style="display: none">
					<div class="first">
						所属地区：<em>*</em>
					</div>
					<div class="second">
					
					<select class="easyui-combobox" style="width: 193px"
							id="yc_proNameId" value="" >
							<option>请选择省份</option>
					</select>
					<select class="easyui-combobox" style="width: 193px"
						id="yc_cityNameId" value="">
						<option>请选择城市或地区</option>
					</select>
					<input id="yc_startCityIdHidden" type="hidden" value=""/>
					</div>
				</div>
				<div class="row" id="div_yc_time" style="display: none">
					<div class="first">
						演出时间：<em>*</em>
					</div>
					<div class="second">
						<input class="easyui-datetimebox" id="yc_showTime" value=""
							style="width: 200px; line-height: 20px; border: 1px solid #ccc">
					</div>
				</div>
				<div class="row" id="div_yc_address" style="display: none">
					<div class="first">
						演出地点：<em>*</em>
					</div>
					<div class="second">
						<input class="easyui-textbox" id="yc_showAddress" value=""
							style="width: 200px; line-height: 20px; border: 1px solid #ccc">
					</div>
				</div>
				<!--船票类别选中后展现 -->
				<div class="row" id="div_cz_area" style="display: none">
					<div class="first">
						所属地区：<em>*</em>
					</div>
					<div class="second">
					<select class="easyui-combobox" style="width: 193px" id="boat_proNameId" value="" >
						<option>请选择省份</option>
					</select>
					<select class="easyui-combobox" style="width: 193px" id="boat_cityNameId" value="">
						<option>请选择城市或地区</option>
					</select>
					<input id="boat_startCityIdHidden" type="hidden" value=""/>
					</div>
				</div>
				<div class="row" id="div_cz_name" style="display: none">
					<div class="first">
						船票名称：<em>*</em>
					</div>
					<div class="second">
						<input class="easyui-textbox" id="boat_name" value=""
							data-options="prompt:'18个字以内'"
							style="width: 200px; line-height: 20px; border: 1px solid #ccc">
					</div>
				</div>
				<div class="row" id="div_cz_time" style="display: none">
					<div class="first">
						出发时间：<em>*</em>
					</div>
					<div class="second">
						<input class="easyui-datetimebox" id="boat_startTime" value=""
							style="width: 200px; line-height: 20px; border: 1px solid #ccc">
					</div>
				</div>
				<div class="row_hd">以下自定义内容只显示在自己网店内：</div>
				
					
						<div class="accordion_child1">
							<div class="first">产品标题：</div>
							<div class="second">
								<input class="easyui-textbox" id="jd_name" name="ticket.name" value="${ticket.name}"
									data-options="prompt:'18个字以内'"
									style="width: 200px; line-height: 20px; border: 1px solid #ccc">
							</div>
						</div>
						<div class="accordion_child1">
							<div class="first">自定义分类：<em>*</em></div>
							<div class="second">
							<input type="hidden" value="${ticket.category}" id="ticket_category">
							
							<input class="easyui-combotree" data-options="prompt:'自定义分类'"
                                   style="width: 193px" id="search_classify" name="ticket.category" value="" >
<!-- 								<option>自定义分类</option> -->
							</select>
							
								<%-- <select class="easyui-combobox" id="search_classify"
									name="category" 
									style="width: 150px; line-height: 22px; border: 1px solid #95B8E7">
									<option value="">自定义分类（不限）</option>
									<c:forEach items="${linecategorgs}" var="categorg">
										<option value="${categorg.id}">${categorg.name}</option>
									</c:forEach>
									<option value="">默认分类</option>
								</select>  --%>
								<%--<a href="javascript:void(0)" class="easyui-linkbutton"--%>
									<%-- onclick="TicketEdit.editFenlei()">修改分类</a>--%>
							</div>
						</div>
						<div class="accordion_child1">
							<div class="first">
								游客需提前：<em>*</em>
							</div>
							<div class="second">
								<input class="easyui-numberbox" style="width: 50px;"
									type="text" name="ticket.preOrderDay" id="adviceHours" value="${ticket.preOrderDay}"
									data-options="min:0,required:true">天预定,指定购买日期后
									<input class="easyui-numberbox" style="width: 50px;" type="text" name="ticket.validOrderDay" id="validOrderDay" value="${ticket.validOrderDay}"
									data-options="min:1,required:true">天内有效
							</div>
						</div>
						<div class="accordion_child1">
							<div class="first">描述图：</div>
							<div class="second">
								<div id="imgView" style="display:none">
									<img alt="" src="" width="240" height="160" style="border-radius: 5px;"><br>
									<a href="javascript:;" id="remove_img_id" class="easyui-linkbutton line-btn" >删除</a>
								</div>
								<div class="btn_class" style="margin-top: 5px;">
									<input id="filePath" name="ticket.ticketImgUrl" type="hidden" value="${ticket.ticketImgUrl}"/>
										<input type="button" class="J_add_pic" id="add_descpic" onclick="" value="添加描述图">
										<p class="suffix_tip">最佳尺寸480*320</p>
								</div>
								
								
							</div>
							
							
						</div>
						<div class="accordion_child1">
							<div class="first">
								营业时间：<em>*</em>
							</div>
							<div class="second" id="div_kindRuyuan">
								<input type="text" class="easyui-textbox" name="openTime" data-options="multiline: true" style="height: 100px; width: 300px;" value="${ticketExplain.openTime}">
							</div>
						</div>
						<div class="accordion_child1" style="">
							<div class="first">
								小贴士：<em>*</em>
							</div>
							<div class="second" id="div_kindTuigai">
									<textarea id="kindTuigai" name="tips"
                                              style="width: 700px; height: 200px; visibility: hidden;">${ticketExplain.tips}</textarea>
									<a class="use_template" href="javascript:;" id="use_tuigai">使用模板</a>
									<span class="img_tip_offer">
										最多可输入<em id="em_tuigai">1000</em>字
									</span>
							</div>
						</div>
						<%--<div class="accordion_child1">
							<div class="first">
								特惠政策：<em>*</em>
							</div>
							<div class="second" id="div_kindprivilege">
								<textarea id="kindprivilege" name="privilege"
									style="width: 700px; height: 200px; visibility: hidden;">${ticketExplain.privilege }</textarea>
									
									<a class="use_template" href="javascript:;" id="use_privilege">使用模板</a>
									<span class="img_tip_offer">
										最多可输入<em id="em_youhui">1000</em>字
									</span>
							</div>
						</div>--%>
						<div class="accordion_child1">
							<div class="first">景点介绍：</div>
							<div class="second">
<%-- 								<input id="filePath" name="filePath" type="hidden" value="${ticket.ticketImgUrl}"/> --%>
<!-- 								<input type="button" class="J_add_pic" style="margin-left:5px;" id="add_pictoeditor" onclick="" value="添加图片到详情"> -->
									<%--<input type="hidden" id="hidden_xiangq" value='${ticketExplain.proInfo}'>--%>
										<textarea id="kindXiangqing" name="proInfo"
											style="width: 700px; height: 300px; visibility: hidden;">${ticketExplain.proInfo}</textarea>
								<p id="suffix_tip_id" class="suffix_tip" style="/*margin-left: 250px;*/"></p>
							</div>
						</div>
						<div style="margin-left: 125px;; margin-bottom: 10px;" id="div_kindXiangqing">
							
						</div>
						<div class="accordion_child1" style="display: none">
							<div class="first">排序：</div>
							<div class="second">
								<input class="easyui-numberspinner" style="width: 50px;" 
									type="text" name="ticket.showOrder" value="${ticket.showOrder}"
									data-options="min:0,required:true">
							</div>
						</div>
						<div class="accordion_child1" style="display: none;">
							<div class="first">使用保障：</div>
							<div class="second">
								<div id="check_div_id" style="cursor: pointer;width: 320px;">
									<input type="hidden" id="hidden_input_agree" name="agreeRuleStr" value="${ticket.agreeRule}">
									<label><input id="input_agree" type="checkbox"> <span id="div_agree" style="  padding-left: 25px;  margin-top: -25px;  display: block;">同意加入旅行帮船票使用保障服务</span></label>
								</div>
							</div>
						</div>
				<div class="row">
					<div class="first_offer">
						是否确认：<em>*</em>
					</div>
					<div class="second">
						<input type="hidden" id="hidden_conf" value="${ticket.needConfirm}">
						<label class="input_radio"> <input name="ticket.needConfirm" type="radio" value="true" <s:if test="ticket.needConfirm==true">checked="checked"</s:if>>需要确认</label>
						<label class="input_radio"> <input name="ticket.needConfirm" type="radio" value="false" <s:if test="ticket.needConfirm == null || ticket.needConfirm==false">checked="checked"</s:if>>无需确认</label>
						<%--<span id="look_span" style="color: #0091c8;margin-left:10px;cursor: pointer;">查看《订单无需确认服务条款》</span>--%>
					</div>

				</div>
						<div style="margin-left: 115px;">
							<a href="javascript:;" class="easyui-linkbutton" id="add_pic_toEditor"
												style="width: 130px;" onclick="TicketEdit.nextGuide()" >保存，下一步</a> 
						</div>
				</div>
				
			</form>
			
			
			
			
		</div>
		
	</div>
	
	<div id="dd" class="easyui-dialog" title="修改分类" style="left: 200px; top:200px;width:320px;height:320px;"   
        data-options="resizable:true,modal:true">
        <iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:320px;"></iframe>
	</div>  
	
	
</body>
</html>