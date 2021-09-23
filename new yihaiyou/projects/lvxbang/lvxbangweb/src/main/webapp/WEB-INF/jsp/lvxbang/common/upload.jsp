<%--
  Created by IntelliJ IDEA.
  User: zzl
  Date: 2016/1/21
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="/js/lib/webuploader/webuploader.css" rel="stylesheet" type="text/css">
<div class="Upload_xp">
    <div class="posiR Upload_xp_r">
        <i class="close"></i>
        <p class="name">添加照片</p>
        <div id="upload_container">
            <div id="picker">
                ＋添加图片
            </div>
            <ul class="Upload_xp_ul total">
            </ul>
        </div>
        <div class="Upload_xp_div posiA">
            <a id="start_upload_btn" href="javascript:;" class="fr o_line oval4 b">开始上传</a>
            <p id="upload_count_info" class="s fr"><span class="orange">0</span>/<span id="total_file_num">0</span>张</p>
            <p id="upload_total_info" class="n fr">
                <span style="width:0%"></span>
            </p>
            <p class="tip">
                <span id="upload_tip_a" class="orange"></span><br>
                <span id="upload_tip_b" class="orange"></span>
            </p>
        </div>
    </div>
</div>
<div class="mask"></div>
