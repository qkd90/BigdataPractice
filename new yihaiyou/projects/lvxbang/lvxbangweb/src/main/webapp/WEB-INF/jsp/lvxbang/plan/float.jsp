<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--Float_w-->
<link rel="stylesheet" href="/css/float.css"/>
<div class="posiA Float_w plan_editor">
    <input type="hidden" value="${planId}" id="planId"/>
    <div class="Float_w_l posiR fl">
        <div class="posiA Float_w_l_c" id="end" >
            <a href="javaScript:;" class="Myitinerary b fs13"><i class="p_img5"></i><i></i>我的线路</a>
        </div>
        <div class="posiA Float_w_l_b">
            <ul>
                <li><a href="javascript:scroll(0,0)" class="apex"></a>
                    <div class="Float_w_l_b_d"><a href="javascript:scroll(0,0)">回顶</a></div>
                </li>
                <li><a href="javaScript:;" class="shareit"></a>
                    <%--<div class="Float_w_l_b_d Float_w_l_b_a ">--%>

                        <%--&lt;%&ndash;  bshare-custom <a title="分享到" href="http://www.bshare.cn/share" id="bshare-shareto" class="bshare-more">分享到:</a>&ndash;%&gt;--%>
                        <%--<a href="javaScript:;" class="Float_qq " >--%>
                        <%--</a>--%>
                        <%--<a href="javaScript:;" class="Float_wb " ></a>--%>
                        <%--<a href="javaScript:;" class="Float_wx " ></a>--%>
                        <%--<a href="javaScript:;" class="Float_kj " ></a>--%>
                    <%--</div>--%>
                    <div class="Float_w_l_b_d Float_w_l_b_a bshare-custom">
                        <a title="分享到QQ好友" class="bshare-qqim bshare-ico"></a>
                        <a title="分享到新浪微博" class="bshare-sinaminiblog bshare-ico"></a>
                        <a title="分享到微信" class="bshare-weixin bshare-ico"></a>
                        <a title="分享到QQ空间" class="bshare-qzone bshare-ico"></a>
                    </div>

                    <%--因为分享插件会导致两次重复请求，暂时先注释掉--%>
                    <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/button.js#style=999&amp;uuid=&amp;pophcol=1&amp;lang=zh&amp;h=35&amp;w=35"></script>
                    <%--<a class="bshareDiv" onclick="javascript:return false;"></a>--%>
                    <script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>

                </li>
                <li><a href="javaScript:;" class="feedback"></a>
                    <div class="Float_w_l_b_d"><a href="${INDEX_PATH}/lvxbang/help/index.jhtml?page=7" >反馈</a></div>
                </li>
            </ul>
        </div>
    </div>
    <div class="Float_w_r fl">

        <!--快速规划行程的秘诀-->
        <div class="Itineraryfk disn">
            <div class="Itinerary_title textC posiR fs14"><b class="posiA"><a href="#" class="overgreen">&gt;&gt;</a></b>我的线路(空)</div>
            <p class="Itinerary_top textC posiR fs14">如何定制旅游线路<i class="posiA"></i></p>
            <ul class="Itinerary_ul">
                <li class="Itinerary_one">
                    <i></i>
                    <p class="Itinerary_num b">-<span>1</span>-</p>
                    <p class="Itinerary_name">查找心仪景点</p>
                </li>
                <li class="Itinerary_two">
                    <i></i>
                    <p class="Itinerary_num b">-<span>2</span>-</p>
                    <p class="Itinerary_name">添加至线路</p>
                </li>
                <li class="Itinerary_three">
                    <i></i>
                    <p class="Itinerary_num b">-<span>3</span>-</p>
                    <p class="Itinerary_name">智能规划线路</p>
                </li>
                <li class="Itinerary_four">
                    <i></i>
                    <p class="Itinerary_num b">-<span>4</span>-</p>
                    <p class="Itinerary_name">选择交通酒店</p>
                </li>
                <li class="Itinerary_fives">
                    <i></i>
                    <p class="Itinerary_num b">-<span>5</span>-</p>
                    <p class="Itinerary_name">轻松一站式采购</p>
                </li>
            </ul>
        </div>

        <!--添加景点-->
        <div class="Add_attractions posiR disn">
            <div class="Itinerary_title textC posiR fs14"><b class="posiA">&gt;&gt;</b>我的线路</div>
            <b class="Add_attractions_T">目的地：</b>
            <div class="cart_stock">
                <ul class="cart_stock_ul">
                </ul>
            </div>
            <div class="Add_attractions_C cl"><b class="fl">已添加景点:</b><a href="javascript:;" class="close fr">快速重置</a></div>
            <div class="Add_attractions_X">
                <ul class="Add_attractions_u">

                </ul>
            </div>

            <div class="Add_attractions_B posiA">
                <ul>
                    <li>
                        <p class="checked_p"></p>
                        <div class="textC">宽松安排<p>4-6h/天</p></div>
                    </li>
                    <li class="checked">
                        <p class="checked_p"></p>
                        <div class="textC">适中安排<p>6-10h/天</p></div>
                    </li>
                    <li>
                        <p class="checked_p"></p>
                        <div class="textC">紧凑安排<p>10-14h/天</p></div>
                    </li>
                </ul>
                <a href="javaScript:;" class="Add_attractions_But b"><i></i>智能排序</a>
            </div>

            <p class="cl"></p>
        </div>


        <!--编辑行程-->
        <div class="EditStroke posiR disn">
            <div class="Itinerary_title textC posiR fs14"><b class="posiA">&gt;&gt;</b>我的线路</div>
            <%--<p class="EditStroke_t">考虑到行程合理性，我们为您删除了以下<br/>景点：</p>--%>
            <p class="EditStroke_s">

            </p>
            <%--<p class="EditStroke_c cl">对排序结果不满意？你可以自主<a href="/lvxbang/plan/edit.jhtml" class="EditStroke_c_a">编辑行程&gt;</a></p>--%>
            <ul class="EditStroke_b">
                <img src="/images/loadingx.gif" class="loading-img" alt="努力排，努力排" title="努力排，努力排"/>
                <p class="loading-title">排呀排，排呀排</p>
            </ul>

            <div class="posiA EditStroke_but">
                <%--<a href="javaScript:;" class="fl mr10 b save-plan">保存</a>--%>
                <%--<a href="javaScript:;" class="fr b">快速选择机票+酒店</a>--%>
            </div>
            <p class="cl"></p>
        </div>

    </div>
</div>
<!--Float_w end-->
<script type="text/html" id="tpl-scenic-in-editor">
    <li>
        <a href='javascript:;' class='close' data-id='{{id}}' data-city-id="{{cityId}}"></a>

        <div class='Add_attractions_u_d'>
            <p class='img'>
                <img src="{{cover | floatScenicCover}}"/></p>

            <div class='nr'>
                <p class='name'>{{name}}</p>

                <p class='fraction'><b>{{score/20}}分</b>/5分</p>

                <p class='synopsis'>建议游玩<span class="adviceMinute_{{cityId | subCityId}}" >{{adviceMinute | calTime}}</span>小时</p>
            </div>
        </div>
    </li>
</script>
<script type="text/html" id="tpl-optimized-day">
    <li>
        <i></i>
        <p class="name"><span>第{{day}}天</span>{{city.name}}</p>
        <div class="js">
            {{each tripList as node index}}
            {{if index>0}}--{{/if}}
            {{node.name}}
            {{/each}}
        </div>
    </li>
</script>
<script id="tpl-city-in-editor" type="text/html">
    <li class="city-in-editor" data-id="{{id}}">
    <label>{{name}}</label>
    <div class="opera_num">
    <a href="javascript:;" class="minus">-</a>
    <p class="posiR"><input class="quantity" id="cityId_{{id | subCityId}}" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" value="{{if count}}{{count}}{{else}}1{{/if}}" maxlength="3">天</p>
    <a href="javascript:;"  class="plus">+</a>
    </div>
        {{if notSupport}}<span class="not-support">(暂不支持智能排序)</span>{{/if}}
    </li>
</script>
<script src="/js/lvxbang/float.js" type="text/javascript"></script>


