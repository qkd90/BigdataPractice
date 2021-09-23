
<%--
  Created by IntelliJ IDEA.
  User: vacuity
  Date: 15/11/23
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link href="${mallConfig.resourcePath}/css/mobile/user/team.css?${mallConfig.resourceVersion}" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${mallConfig.resourcePath}/css/mobile/common.css?${mallConfig.resourceVersion}">
</head>
<body>
<div>
    <div id="title">
        <ul id="titile-ul" class="cf">
            <li class="vip-level">
                <a href="#" id="one-level" class="level curr">一级会员</a>
            </li>
            <li class="vip-level">
                <a href="#" id="two-level" class="level">二级会员</a>
            </li>
            <li class="vip-level">
                <a href="#" id="three-level" class="level">三级会员</a>
            </li>
        </ul>
    </div>

    <div id="level-one" class="level-div display-block">
        <p class="vip-count">一级会员${firstCount}人</p>
        <div>
            <s:if test="firstCount == 0">
                您暂时还没有下属会员
            </s:if>
            <s:else>
                <c:forEach items="${firstChildren}" var="user">
                    <div class="vip-member">
                        <div class="team-info cf">
                            <div class="header-face">
                                <div class="face-div">
                                    <img class="face-img" src="${user.headImgUrl}">
                                </div>
                            </div>
                            <div class="user-info">
                                <p>会员ID：${user.id}</p>
                                <p>昵称：${user.nickName}</p>
                                <p>当月消费银两：
                                    <span class="orange-color">0</span>
                                </p>
                                <p class="cf">
                                    <span class="team">他的团队：</span>
                                <span class="team-count">
                                        ${user.totalLevelCount}
                                </span>
                                    <a href="#" class="his-team-btn"></a>
                                </p>


                            </div>
                        </div>
                        <div class="his-team display-none">
                            <p>
                                一级会员：
                                <span class="orange-color team-team-count">${user.oneLevelCount}</span>
                            </p>

                            <p>
                                二级会员：
                                <span class="orange-color team-team-count">${user.twoLevelCount}</span>
                            </p>

                            <p>
                                三级会员：
                                <span class="orange-color team-team-count">${user.threeLevelCount}</span>
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </s:else>
        </div>
    </div>

    <div id="level-two" class="level-div display-none">
        <p class="vip-count">二级会员${secondCount}人</p>
        <div>
            <s:if test="secondCount == 0">
                您暂时还没有下属二级会员
            </s:if>
            <s:else>
                <c:forEach items="${secondChildren}" var="user">
                    <div class="vip-member">
                        <div class="team-info cf">
                            <div class="header-face">
                                <div class="face-div">
                                    <img class="face-img" src="${user.headImgUrl}">
                                </div>
                            </div>
                            <div class="user-info">
                                <p>会员ID：${user.id}</p>
                                <p>昵称：${user.nickName}</p>
                                <p>当月消费银两：
                                    <span class="orange-color">0</span>
                                </p>
                                <p class="cf">
                                    <span class="team">他的团队：</span>
                                <span class="team-count">
                                        ${user.totalLevelCount}
                                </span>
                                    <a href="#" class="his-team-btn"></a>
                                </p>


                            </div>
                        </div>
                        <div class="his-team display-none">
                            <p>
                                一级会员：
                                <span class="orange-color team-team-count">${user.oneLevelCount}</span>
                            </p>

                            <p>
                                二级会员：
                                <span class="orange-color team-team-count">${user.twoLevelCount}</span>
                            </p>

                            <p>
                                三级会员：
                                <span class="orange-color team-team-count">${user.threeLevelCount}</span>
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </s:else>
        </div>
    </div>

    <div id="level-three" class="level-div display-none">
        <p class="vip-count">三级会员${thirdCount}人</p>
        <div>
            <s:if test="thirdCount == 0">
                您暂时还没有下属三级会员
            </s:if>
            <s:else>
                <c:forEach items="${thirdChildren}" var="user">
                    <div class="vip-member">
                        <div class="team-info cf">
                            <div class="header-face">
                                <div class="face-div">
                                    <img class="face-img" src="${user.headImgUrl}">
                                </div>
                            </div>
                            <div class="user-info">
                                <p>会员ID：${user.id}</p>
                                <p>昵称：${user.nickName}</p>
                                <p>当月消费银两：
                                    <span class="orange-color">0</span>
                                </p>
                                <p class="cf">
                                    <span class="team">他的团队：</span>
                                <span class="team-count">
                                        ${user.totalLevelCount}
                                </span>
                                    <a href="#" class="his-team-btn"></a>
                                </p>


                            </div>
                        </div>
                        <div class="his-team display-none">
                            <p>
                                一级会员：
                                <span class="orange-color team-team-count">${user.oneLevelCount}</span>
                            </p>

                            <p>
                                二级会员：
                                <span class="orange-color team-team-count">${user.twoLevelCount}</span>
                            </p>

                            <p>
                                三级会员：
                                <span class="orange-color team-team-count">${user.threeLevelCount}</span>
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </s:else>
        </div>
    </div>

</div>
<jsp:include page="/WEB-INF/jsp/mobile/common/common-script.jsp"></jsp:include>
<script src="${mallConfig.resourcePath}/js/mobile/user/team.js?${mallConfig.resourceVersion}"></script>
</body>
</html>
