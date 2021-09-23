<%@ page language="java" pageEncoding="utf-8"%>
<%--<script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/1.11.3/jquery.min.js"></script>--%>
<%--<script type="text/javascript">--%>
    <%--!window.jQuery && document.write('<script type="text/javascript" src="/js/jquery.min.js"><\/script>');--%>
<%--</script>--%>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/jquery.min.js${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/jquery.lazyload.min.js${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/common_util.js${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/template.js${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/template.helper.js${mallConfig.resourceVersion}"></script>
<%--<script type="text/javascript">--%>
	<%--var CFG = { SRV_ADDR_AUTH : "${CFG.SRV_ADDR_AUTH}", SRV_ADDR_IMAGE : "${CFG.SRV_ADDR_IMAGE}", WEIXIN_APPID : "${CFG.WEIXIN_APPID}" };--%>
<%--</script>--%>
<script type="text/javascript">
$(function()
{
	$("#backbutton").click(function(){
		window.history.go(-1);
	});
});

function getMemberId()
{
	return ${CURRENT_LOGIN_USER_MEMBER.id};
}
</script>
