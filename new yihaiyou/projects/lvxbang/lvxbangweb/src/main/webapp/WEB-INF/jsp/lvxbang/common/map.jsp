<%--
  Created by IntelliJ IDEA.
  User: guoshijie
  Date: 2016/1/26
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<a href="#" target="_blank" id="jumpToUrl"></a>
<script type="text/html" id="tpl-map-plan-trip">
    <div class="posiA map_plan_day map_plan_day_{{day}}" data-url="${SCENIC_PATH}/scenic_detail_{{id}}.html">
        <div class="posiR Pop_ups_posiR">
            <div class="Pop_ups posiA">
                <div class="posiR Pop_ups_div banner_product">
                    <a href="javaScript:;" class="closex"></a>
                    <div class="bacSlideBox">
                        <div class="bacSlideBox_div fl posiR jumpTo"><ul class="Pop_ups_ul posiR"><li><p class="img"><img src="{{cover | planMapCover}}"></p></li></ul></div>
                    </div>
                    <div class="Pop_ups_nr">
                        <div class="Pop_ups_nr_div">
                            <div class="Pop_ups_fl fl">
                                <p class="name fs14 b"><a href="#" class="b jumpTo">{{substring name 15}}</a></p>
                                <p class="Pop_ups_fl_star cl">{{if star != null && star.length > 0}}{{star.length}}A{{/if}}</p>
                                <p class="add">地址：{{address}}</p>
                            </div>

                            {{if comment != null && comment.length > 0}}
                            <p class="comment cl fs13" data-title="{{comment}}">
                                <img src="/images/right.png" class="ml5">{{comment | briefText:30}}<img src="/images/left.png" class="ml5">
                            </p>
                            {{else}}
                            <p class="cl fs13 comment"></p>
                            {{/if}}
                            <p class="cl"></p>
                        </div>

                    </div>
                </div>
            </div>

            <div class="location location_{{id}}">
                <label class="fl location_l {{color}}">
                    <b class="ff_yh {{if day>9}}fs14{{else}}fs20{{/if}} disB title-day">{{index}}</b>
                    <b class="ff_yh {{if day>9}}fs11{{else}}fs14{{/if}} disB title-whole">D{{day}}</b>
                </label>
                <div class="fl">{{name}}</div>
            </div>
        </div>
    </div>
</script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=RQN2eMlsSmuNV2wq7bRdq4g3"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/RichMarker/1.2/src/RichMarker_min.js"></script>
<script src="/js/lvxbang/map/map.js" type="text/javascript"></script>
