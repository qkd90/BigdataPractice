<%--
  Created by IntelliJ IDEA.
  User: HMLY
  Date: 2016/1/14
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--操作提示-->
<%--<div class="mask"></div>--%>
<div class="cg_prompt b fs16">


    <div class="ico" style="width: 68px"></div>
    <p style="position: relative; top: 37%; margin-right: 20px;"></p>
</div>

<div class="cg_prompt2 b fs16">
    <div class="ico" ></div>
    <p style="position: relative; top: 37%; margin-right: 20px;"></p>
</div>

<%--过渡动画--%>
<div class="loading-background"></div>
<div id="loading" class="cq_login jiaotong cl disB textC loading"
     style="margin-top:70px;color:#666;">
    <img src="/images/loadingx.gif" style="width: 100px; height: 100px">
    <p class="mt20">
        正在处理...
    </p>
</div>

<!--个人中心删除弹窗-->
<div class="Popups oval4 fs16 PPClear grxxccx">
    <div class="posiR">
        <div class="ppc_nr textC fs16">
        </div>
        <a href="javaScript:;" class="determine b fs14" style="width:149px;">确定</a>
        <a href="javaScript:;" class="cancel b fs14">取消</a>
    </div>
</div>

<div class="protocol_pop" style="position: fixed; top: 20%; left: 20%; z-index: 99999; background: #fff; display: none; width: 60%; height: 60%; padding: 20px 30px 20px 30px;">
    <h1 style="margin-bottom: 20px;"></h1>
    <i style="width: 22px; height: 22px; top:10px; right:10px; position: absolute; cursor: pointer; display: block; background: url(/images/closex.png) no-repeat;"></i>
    <div style="overflow: auto; max-height: 90%; max-width: 100%;"></div>
</div>

<script src="/js/lvxbang/popup/popup1.js" type="text/javascript"></script>