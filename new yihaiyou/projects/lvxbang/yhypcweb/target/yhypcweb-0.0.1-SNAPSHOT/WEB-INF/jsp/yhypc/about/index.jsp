<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>
    <%@include file="../../yhypc/public/header.jsp"%>
    <link rel="stylesheet" href="/css/about/aboutUs.css">
    <title>关于我们-一海游</title>
</head>
<body>
	<div class="mainBox">
		<div class="advert"></div>
		<div class="total clearfix">
		<div class="ft_left">
			<span class="line1"></span>
			<span class="line2"></span>
			<div class="describcontian">
                <c:set var="yhyjj" value='<%=com.zuipin.util.PinyinUtil.hanziToPinyin("一海游简介", "")%>'></c:set>
				<h3 id="${yhyjj}">一海游简介</h3>
                <c:if test="${result[yhyjj].img_path != null && result[yhyjj].img_path != ''}"><div class="apic"><img src="${QINIU_BUCKET_URL}${result[yhyjj].img_path}"></div></c:if>
                ${result[yhyjj].content}
			</div>
            <div class="describcontian">
                <c:set var="yhygwjj" value='<%=com.zuipin.util.PinyinUtil.hanziToPinyin("港务简介", "")%>'></c:set>
                <h3 id="${yhygwjj}">港务简介</h3>
                <c:if test="${result[yhygwjj].img_path != null && result[yhygwjj].img_path != ''}"><div class="apic"><img src="${QINIU_BUCKET_URL}${result[yhygwjj].img_path}"></div></c:if>
                ${result[yhygwjj].content}
            </div>
            <div class="describcontian">
                <c:set var="yhymgjj" value='<%=com.zuipin.util.PinyinUtil.hanziToPinyin("邮轮母港简介", "")%>'></c:set>
                <h3 id="${yhymgjj}">邮轮母港简介</h3>
                <c:if test="${result[yhymgjj].img_path != null && result[yhymgjj].img_path != ''}"><div class="apic"><img src="${QINIU_BUCKET_URL}${result[yhymgjj].img_path}"></div></c:if>
                ${result[yhymgjj].content}
            </div>
            <div class="describcontian">
                <c:set var="yhyswhz" value='<%=com.zuipin.util.PinyinUtil.hanziToPinyin("商务合作", "")%>'></c:set>
                <h3 id="${yhyswhz}">商务合作</h3>
                <c:if test="${result[yhyswhz].img_path != null && result[yhyswhz].img_path != ''}"><div class="apic"><img src="${QINIU_BUCKET_URL}${result[yhyswhz].img_path}"></div></c:if>
                ${result[yhyswhz].content}
            </div>
            <div class="describcontian">
                <c:set var="yhycpyc" value='<%=com.zuipin.util.PinyinUtil.hanziToPinyin("诚聘英才", "")%>'></c:set>
                <h3 id="${yhycpyc}">诚聘英才</h3>
                <c:if test="${result[yhycpyc].img_path != null && result[yhycpyc].img_path != ''}"><div class="apic"><img src="${QINIU_BUCKET_URL}${result[yhycpyc].img_path}"></div></c:if>
                ${result[yhycpyc].content}
            </div>
            <div class="describcontian">
                <c:set var="yhylxwm" value='<%=com.zuipin.util.PinyinUtil.hanziToPinyin("联系我们", "")%>'></c:set>
                <h3 id="${yhylxwm}">联系我们</h3>
                <c:if test="${result[yhylxwm].img_path != null && result[yhylxwm].img_path != ''}"><div class="apic"><img src="${QINIU_BUCKET_URL}${result[yhylxwm].img_path}"></div></c:if>
                ${result[yhylxwm].content}
            </div>
            <div class="describcontian">
                <c:set var="yhymzsm" value='<%=com.zuipin.util.PinyinUtil.hanziToPinyin("免责声明", "")%>'></c:set>
                <h3 id="${yhymzsm}">免责声明</h3>
                <c:if test="${result[yhymzsm].img_path != null && result[yhymzsm].img_path != ''}"><div class="apic"><img src="${QINIU_BUCKET_URL}${result[yhymzsm].img_path}"></div></c:if>
                ${result[yhymzsm].content}
            </div>
		</div>
		<div class="ft_right">
			<ul>
				<h3>关于我们</h3>
				<a href="#${yhyjj}"><li class="li_active">一海游简介</li></a>
                <a href="#${yhygwjj}"><li>港务简介</li></a>
                <a href="#${yhymgjj}"><li>邮轮母港简介</li></a>
			</ul>
			<ul>
				<h3>加盟我们</h3>
                <a href="#${yhyswhz}"><li>商务合作</li></a>
			</ul>
            <ul>
				<h3>加入我们</h3>
                <a href="#${yhycpyc}"><li>诚聘英才</li></a>
			</ul>
            <ul>
				<h3>帮助中心</h3>
                <a href="#${yhylxwm}"><li>联系我们</li></a>
			</ul>
			<ul>
				<h3>法律声明</h3>
                <a href="#${yhymzsm}"><li>免责声明</li></a>
			</ul>
		</div>
		</div>
	</div>
</body>
<%@include file="../../yhypc/public/footer.jsp"%>
<script type="text/javascript" src="/js/about/aboutUs.js"></script>
</html>