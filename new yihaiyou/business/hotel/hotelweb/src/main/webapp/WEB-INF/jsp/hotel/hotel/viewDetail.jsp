<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>第一步</title>
<%@ include file="../../common/common141.jsp"%>
<%--<script type="text/javascript" src="/js/line/line/lineUtil.js"></script>--%>
<link rel="stylesheet" type="text/css" href="/js/kindeditor/themes/default/default.css"/>
<script type="text/javascript" src="/js/kindeditor/kindeditor-min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/hotel/addWizard.css"/>
<link rel="stylesheet" type="text/css" href="/css/hotel/iconfont/iconfont.css"/>

<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/diyUpload.css">
<link rel="stylesheet" type="text/css" href="/js/diyUpload/css/webuploader.css">
<script type="text/javascript" src="/js/diyUpload/js/webuploader.html5only.min.js"></script>
<script type="text/javascript" src="/js/diyUpload/js/diyUpload.js"></script>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=R9424rkP6oyCzex5FuLa7XIw"></script>
<script type="text/javascript" src="/js/hotel/hotelUtil.js"></script>
<script type="text/javascript" src="/js/hotel/viewDetail.js"></script>
	<style type="text/css">
		.icon-more {
			float: left;
			border: 1px solid #EFF1F2;
			border-radius: 5px;
			width: 170px;
			height: 150px;
			margin-top: 5px;
			margin-left: 5px;
			overflow: hidden;
			position: relative;
			background-color: rgba(0, 153, 153, 0);
		}
		.viewIcon {
			position: absolute;
			top: 0;
			left: 0;
			width: 170px;
			height: 150px;
			overflow: hidden;
		}
	</style>
</head>
<body style="margin:0;padding:0px 0px 0px 0px;">
<div id="content" title="" class="easyui-layout" data-options="fit:true,border:false" style="width:100%;height:100%;"> 
	<div data-options="region:'center',border:false" style="padding:10px 10px 10px 10px" style="width:100%;">
		<form id="editForm" method="post">
			<input id="productId" name="productId" type="hidden" value="${hotel.id}"/>
			<table style="float:left;">
				<tr>
					<td class="label">名称:</td>
					<td>
						${hotel.name}
					</td>
				</tr>
				<c:if test="${not empty hotel.cover}">
					<tr>
						<td class="label">民宿封面图:</td>
						<td>
							<div id="imgView">
								<img alt="" src="${hotel.cover}" width="240" height="160" style="border-radius: 5px;">
							</div>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="label">所属区域:</td>
					<td>
						${hotel.extend.address}
					</td>
				</tr>
				<tr>
					<td class="label">星级:</td>
					<td>
						<c:choose>
							<c:when test="${hotel.star == 1}">
								<span>一星级</span>
							</c:when>
							<c:when test="${hotel.star == 2}">
								<span>二星级</span>
							</c:when>
							<c:when test="${hotel.star == 3}">
								<span>三星级</span>
							</c:when>
							<c:when test="${hotel.star == 4}">
								<span>四星级</span>
							</c:when>
							<c:when test="${hotel.star == 5}">
								<span>五星级</span>
							</c:when>
							<c:otherwise>
								<span>未评级</span>
							</c:otherwise>
						</c:choose>

					</td>
				</tr>
				<c:if test="${not empty productimages}">
					<tr>
						<td class="label">民宿相册:</td>
						<td>
							<div id="demo" style="width: 650px; /*background: #CF9; min-height: 200px;*/">
								<div class="parentFileBox" style="width: 832px;">
									<ul class="fileBoxUl">
										<c:forEach items="${productimages}" var="image" varStatus="status">
											<c:choose>
												<c:when test="${status.index <= 2}">
													<li id="fileBox_${status.index}" class="diyUploadHover">
														<div class="viewThumb">
															<img src="<%=com.zuipin.util.QiniuUtil.URL%>${image.path}">
														</div>
														<div class="diyCancel" style="display: none;"></div>
														<div class="diySuccess" style="display: none;"></div>
														<div class="diyFileName" style="display: none;"></div>
														<div class="diyBar">
															<div class="diyProgress"></div>
															<div class="diyProgressText">0%</div>
														</div>
													</li>
												</c:when>
												<c:otherwise>
													<li id="fileBox_${status.index}" class="diyUploadHover moreImage" style="display: none">
														<div class="viewThumb">
															<img src="<%=com.zuipin.util.QiniuUtil.URL%>${image.path}">
														</div>
														<div class="diyCancel" style="display: none;"></div>
														<div class="diySuccess" style="display: none;"></div>
														<div class="diyFileName" style="display: none;"></div>
														<div class="diyBar">
															<div class="diyProgress"></div>
															<div class="diyProgressText">0%</div>
														</div>
													</li>
												</c:otherwise>
											</c:choose>
										</c:forEach>

										<c:if test="${productimages.size() > 5}">
											<li id="fileBox1" class="diyUploadHover" style="">
												<div class="viewThumb" style="padding: 35px 45px;">
													<img onclick="ViewDetail.addPiceMore()" alt="点击查看更多" style="width: 80px; height: 80px; cursor: pointer;" src="<%=com.zuipin.util.QiniuUtil.URL%>hotel/hotelDesc/1465370452755.png">
												</div>
											</li>
										</c:if>
										<li id="fileBox2" class="diyUploadHover" style="display: none">
											<div class="viewThumb" style="padding: 35px 45px;">
												<img onclick="ViewDetail.hidePiceMore()" alt="点击查看更多" style="width: 80px; height: 80px; cursor: pointer;" src="<%=com.zuipin.util.QiniuUtil.URL%>hotel/hotelDesc/1465380130678.png">
											</div>
										</li>
									</ul>
								</div>
							</div>
						</td>
					</tr>
				</c:if>


				<c:if test="${not empty hotel.theme}">
					<tr>
						<td class="label">民宿主题:</td>
						<td>
							<div style="width: 700px;">
								<label>${hotel.theme}</label>
							</div>
						</td>
					</tr>
				</c:if>


				<c:if test="${not empty hotel.recreationAmenities}">
					<tr>
						<td class="label">民宿休闲:</td>
						<td>
							<div style="width: 700px;">
								<label>${hotel.recreationAmenities}</label>
							</div>
						</td>
					</tr>
				</c:if>

				<c:if test="${not empty hotel.serviceAmenities}">
					<tr>
						<td class="label">民宿服务:</td>
						<td>
							<div style="width: 700px;">
								<label>${hotel.serviceAmenities}</label>
							</div>
						</td>
					</tr>
				</c:if>

				<c:if test="${not empty hotel.generalAmenities}">
					<tr>
						<td class="label">民宿设施:</td>
						<td>
							<div style="width: 700px;">
								<label>${hotel.generalAmenities}</label>
							</div>
						</td>
					</tr>
				</c:if>

				<tr>
					<td class="label">民宿房型:</td>
					<td>
						<div style="width: 550px; height: 250px;">
							<div id="roomTypeList" ></div>
						</div>
					</td>
				</tr>

				<c:if test="${not empty hotel.shortDesc}">
					<tr>
						<td class="label">民宿简介:</td>
						<td>
							<input class="easyui-textbox" name="hotel.shortDesc" value="${hotel.shortDesc}" style="width: 700px; height: 120px;" id="com_hotelShortDesc" data-options="required:true, prompt:'民宿简介', multiline:true, readonly:true, validType:['maxLength[300]']"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="label">交通指南:</td>
					<td>
						<div id="baiduMap" style="border: 1px solid rgb(205, 204, 204); border-radius: 5px; width: 700px; height: 300px; background-color: #9ad2d2"></div>
						<input type="hidden" id="lon" value="${hotel.extend.longitude}">
						<input type="hidden" id="lat" value="${hotel.extend.latitude}">
					</td>
				</tr>

				<c:if test="${not empty hotel.extend.description}">
					<tr>
						<td class="label">民宿详情:</td>
						<td>
							<textarea id="hotelInfoDesc"
									  style="width: 700px; height: 300px;">${hotel.extend.description}</textarea>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="label">排序:</td>
					<td>
						<c:choose>
							<c:when test="${not empty hotel.ranking}">
								<span>${hotel.ranking}</span>
							</c:when>
							<c:otherwise>
								<span>0</span>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
	    </form>
	</div>

	<div id="sel_startTime" class="easyui-dialog" title="价格日历" style="width:350px;height:450px;"
		 data-options="resizable:true,modal:true,closed:true">
		<iframe name="editIframe" id="editIframe" scrolling="no" frameborder="0"  style="width:100%;height:330px;"></iframe>
		<input type="hidden" id="hipt_startTime_index"/>
	</div>
</div>
</body>
</html>
