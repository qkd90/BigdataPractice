<%--
  Created by IntelliJ IDEA.
  User: huangpeijie
  Date: 2017-01-12,0012
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="rightBar">
  <div class="ulbar">
    <ul class="ulleft">
      <li class="myMarch"><span class="word">我的行程</span><span class="picture"></span></li>
      <li class="myOrder"><a target="_blank" href="/yhypc/personal/index.jhtml"><span class="word">我的订单</span><span class="picture"></span></a></li>
      <li class="mySelected"><a target="_blank" href="/yhypc/personal/myCollection.jhtml"><span class="word">我的收藏</span><span class="picture"></span></a></li>
      <%--<li class="myEditor"><span class="word">我的编辑</span><span class="picture"></span></li>--%>
      <li class="service"><span class="word">联系客服</span><span class="picture"></span></li>
      <li class="toTop"><span class="word">回到顶部</span><span class="picture"></span></li>
    </ul>
  </div>
  <div class="select_contain">
    <div class="con_title">我的DIY行程 <span class="hidebtn"></span></div>
    <div class="p_bot">
      <div class="p_num">
        <label>出发日期</label>
        <input type="text" id="rightPlayDate" onclick="WdatePicker({doubleCalendar:false, readOnly:true, minDate:'%y-%M-%d', onpicked:RightBar.setDate()})">
      </div>
      <div class="p_num">
        <label>游玩天数</label>
        <span class="countbox"><span class="sub">－</span><span class="num num_gray">1</span><span class="add">＋</span></span>
      </div>
    </div>
    <div class="has_selected">已添加景点：<span class="clearall">快速清空</span></div>
    <div class="march_atention" style="display: none;">
      <p>您还没有选择行程哦</p>
      <p>快去选择吧！</p>
    </div>
    <ul class="selected_list clearfix" id="rightScenicList">
    </ul>
    <div class="buildMarch">
      <ul class="clearfix">
        <li data-hour="1">
          <p class="p_fir">宽松安排</p>
          <p>3-4h/天</p>
        </li>
        <li data-hour="2">
          <p class="p_fir">适中安排</p>
          <p>6-8h/天</p>
        </li>
        <li data-hour="3">
          <p class="p_fir">紧凑安排</p>
          <p>10-14h/天</p>
        </li>
      </ul>
      <span class="buildbtn" id="optimize"><a disabled="disable">智能生成行程</a></span>
    </div>
  </div>
</div>
<script type="text/javascript" src="/js/public/rightBar.js"></script>
<script type="text/html" id="right_scenic_item">
  <li>
    <div class="pic"><img src="{{cover}}"></div>
    <div class="mess">
      <p class="name">{{name}}</p>
      <P class="score"><span class="now">{{score}}分</span>/5分</P>
      <p class="time">[{{adviceTime}}]</p>
    </div>
    <div class="delete" data-id="{{id}}"></div>
  </li>
</script>
