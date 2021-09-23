<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邀请供应商</title>
<%@ include file="../../common/common141.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/sys/sysUser/inivite.css">
<script type="text/javascript" src="/js/sys/sysUser/inivite.js"></script>
</head>
<body>
<div class="main">
    <input type="hidden" name="" value="fenxiao" id="J_operator">
    <div class="mod_header">
        <div class="tit">邀请供应商</div>
    </div>
    <div class="mod_basic bg_gray">
        <div class="mod_info">
        	<div class="tit">邀请方式</div>
            <label for="" class="input_text w_400 input_disabled"><input type="text" value='<s:property value="iniviteUrl"/>' class="J_link_copy" readonly/>
            </label><a href="javascript:void(0);" class="btn_common m_l10 J_copy_btn">复制邀请链接</a>
            <p class="m_t10">将您的邀请链接发送给供应商，并邀请它入驻到旅游同业交易平台，每成功邀请一位可获得200积分。</p>  
        </div>
        <div class="tips_area">
            <h3>温馨提示</h3>
                <div class="item">1.邀请奖励为价值200元积分，奖励无上限，邀请越多，奖励越多；</div>
                <div class="item">2.邀请奖励需在供应商通过您的邀请链接，成功入驻平台后方可生效；</div>
                <div class="item">3.邀请过程中如遇相关问题，可直接联系客服小清：0592-5372156。</div>
        </div> 
        
        
    </div>
</div>
	
</body>
</html>
