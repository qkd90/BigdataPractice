<%--
  Created by IntelliJ IDEA.
  User: HMLY
  Date: 2016/1/8
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../common/upload.jsp" %>
<script type="text/html" id="tpl-no-comment-item">
  <li>
    <div style="text-align: center;margin-top: 50px;"><img src="/images/wupinglun.png"></div>
    <%--<p class="h50 cl" style="border-bottom:1px dashed #ccc;"></p>--%>
  </li>
</script>
<script type="text/html" id="tpl-comment-item">
  <li>
    <div class="synopsis mb10">
      <p class="img fl mr10">
        <a>

          {{if head != ""}}
          <%--http://7u2inn.com2.z0.glb.qiniucdn.com/--%>
          <img src="{{commentImg head}}">
          {{/if}}

          {{if head == ""}}
          <%--<img src="/images/portrait2.jpg">--%>
          <%--/images/toux.PNG--%>
          {{/if}}

        </a>
      </p>
      <div class="fl nr mt5">
        <b class="name fl mr10">{{userName}}</b>

				<span class="hstar fl s_star4">

				<i style="width:{{score}}%" ></i>

				</span>
        <p class="time cl">
          {{createTime}}
          <%--<label class="ml20">此游记来自<span class="Orange">《趁青春去远行----厦门》</span></label>--%>
        </p>

      </div>
      <a href="javaScript:;" class="reply fr mr20">回复</a>
    </div>
    <div class="fs13 color6 lh30 ml60 mb10">{{content}}

      <br/>
      {{each imagePaths as imgp index}}
      <img style="display: inline;" data-original="http://7u2inn.com2.z0.glb.qiniucdn.com/{{imgp}}" width="226px"
           height="210px"/>
      {{/each}}
      <div {{if replies != ""}} class="xinzengtou" {{/if}}>
         <ul class="reply_list">

           {{each replies as comment index}}
           <li style="margin-bottom:0;line-height: 25px; {{if replies.length > 2}} border-bottom: 1px dashed #d4d4d4; {{/if}}">&nbsp;

             {{if comment.head != ""}}
             <%--http://7u2inn.com2.z0.glb.qiniucdn.com/--%>
             <img src="{{commentImg head}}" style="height: 16px;width: 16px;vertical-align:sub;">
             {{/if}}

             {{if comment.head == ""}}
             <img src="" class="img" style="
             height: 16px;width: 16px;vertical-align:sub;
             ">
             <%--/images/toux.PNG--%>
             {{/if}}

             <strong style="color:#34bf82">{{comment.userName}}</strong>回复<strong
                   style="color:#34bf82">{{userName}}</strong>：{{comment.content}}
           <span style="float:right;">{{comment.createTime}}</span>
           </li>
           {{/each}}

         </ul>
         <a href="javaScript:;" class="reply_more " style="color:#ff6000;line-height: 20px;">查看更多回复<i></i></a>
       </div>

    </div>

    <div class="message mb20 ml60" >
      <textarea name="comment" id="reply_comment_{{id}}" commentId="{{id}}" maxlength="200" mname="200"   class="mb10 textarea" ></textarea>

      <p class="fl color6">还能输入<strong class="orange word ">200</strong>字</p>
      <a href="javaScript:;" class="fr but2 oval4" onclick="cancel_reply(this);">取消回复</a>
      <a href="javaScript:;" class="fr but oval4 mr20" onclick="replyComment({{id}});">发表回复</a>
    </div>
  </li>
</script>

<script type="text/html" id="tpl-image-item">
  <div class="img fl tup posiR ml10 img_div">
    <a href="javaScript:;" onclick="deleteImage(this);">
    <span class="oval4 disn">
      删除
    </span>
    </a>
    <img src="http://7u2inn.com2.z0.glb.qiniucdn.com/{{path}}"/>
    <input type="hidden" class="image_photo" imagephoto="{{path}}"/>
  </div>

</script>
<script src="/js/lib/common_util.js" type="application/javascript"></script>
<%--<script src="/js/star.js" type="text/javascript"></script>--%>
<script src="/js/lib/pager.js" type="text/javascript"></script>
<script src="/js/lvxbang/comment/comment.js" type="text/javascript"></script>
<script src="/js/lib/webuploader/webuploader.min.js" type="text/javascript"></script>
<script src="/js/lvxbang/comment/upload.commentimg.js" type="text/javascript"></script>