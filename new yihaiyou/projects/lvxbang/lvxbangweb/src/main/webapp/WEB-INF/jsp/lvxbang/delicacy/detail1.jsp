<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib prefix="jx" uri="/includeTag" %>
<html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title></title>
  <script language="javascript" type="text/javascript" src="/js/jquery-1.4.3.js"></script>
  <script>
    $(function(){
       findComments();
    });

    function findComments(){

        $.ajax({
            url:"/lvxbang/delicacy/commentList.jhtml",
            type:"post",
            dataType:"json",
            data:{
            'delicacyIds':1
        },
        success:function(data){
            console.log(JSON.stringify(data));
            $('#tb1').empty();
            for(var i=0;i<data.length;i++){
            var s = data[i];
                var list = s.comments;
                var tr = "tr"+i;
                var targetId = s.targetId;
                var commentId = s.id;
                alert(targetId);
                alert(commentId);
                if(s.repliedId == 0){
            $('#tb1').append('<tr id='+tr+'><td>'+s.user.userName+'</td><td>'+s.content+'</td><td><span targetId="'+targetId+'"  commentId="'+commentId +'" onclick="add1(this);">回复评论</span></td></tr>');
                    if(list.length !=0){
                        for(var j=list.length-1;j< list.length;j--){
                            var c = list[j];
                            $('#'+tr).after('<tr><td style=" color:lightblue;">'+c.user.userName+'对'+s.user.userName+'的回复：</td><td colspan="2">'+c.content+'</td></tr>');
                        }
                    }

                }
            }
        },
        error:function(){
          alert("error");
            }
        });
    }

    function add1(t){
        var $c = $(t);
        var targetId = $c.attr("targetId");
        var commentId = $c.attr("commentId");
        var content = "lllalalal";
        var userId = 6;
        $.ajax({
            url:"/lvxbang/delicacy/saveComment.jhtml",
            type:"post",
            dataType:"json",
            data:{
                'comment.targetId':targetId,
                'comment.repliedId': commentId,
                'comment.content':content,

            },
            success:function(data) {


                alert(data[0]);

            },
            error:function(){
                alert("error");
            }
        });
    }

  </script>
</head>
<body>
<jx:include fileAttr="${LVXBANG_DELICACY_DETAIL}"></jx:include>
<br/>
<div>
   <h1>美食点评</h1>
  <table cellpadding="0" cellspacing="0" width="100%" >
    <thead>
    <tr><td>用户名</td><td>评论内容</td></tr>
    </thead>
    <tbody id="tb1">
    </tbody>
  </table>
</div>


</body>
</html>
