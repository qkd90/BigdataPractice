<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
	<title>景点相册</title>
	<%@ include file="/WEB-INF/pages/common/head.jsp" %>
	<link rel="stylesheet" type="text/css" href="${ctxPath}/static/diyUpload/css/webuploader.css">
	<link rel="stylesheet" type="text/css" href="${ctxPath}/static/diyUpload/css/diyUpload.css">
	<script type="text/javascript" src="${ctxPath}/static/diyUpload/js/webuploader.html5only.min.js"></script>
	<script type="text/javascript" src="${ctxPath}/static/diyUpload/js/diyUpload.js"></script>
	<script>
		$(pageInit);
		function pageInit() {
			$('#gp').val(1);
			<c:if test="${galleryId != null}">
			loadPics($('#gp').val());
			</c:if>
		}
		function loadPics (gp){
			var gi = $('#galleryId').val();
			if(gi==undefined) gi = '';
			$.get("${ctxPath}/mgrer/scenic/scenicGalleryList?rows=20&scenicIds=${scenicIds}&galleryId="+gi+"&type=scenic&page="+$('#gp').val(), function(result){
				if(result.rows.length==0){
					$('#webuploader-more').html('已无更多');
					$('#webuploader-more').css("background-color","grey");
					return;
				}
				$.each(result.rows,function(i,n){
					var id  = n.id;
					var address = QINIU_BUCKET_URL +n.addressLarge;
					showImage( $('#imagePanel'),address,id);
					$('#fileBox_' + id).find('.diyCancel').click( function () {//增加事件
						var removed = '<input name="removedImgs" value="'+id+'"> ';
						$('#imageBox').append(removed);
					});
				});
			});
			$('#gp').prop('value', parseInt(gp)+1);
		}
		function submitGallery() {
			alertMsg('开发中');
		}
		function cancel() {
			alertMsg('开发中');
		}

	</script>
	<style>
		#imageBox{ margin:3px 3px; width:800px; min-height:600px; background:#e4f5ff}
	</style>
</head>
<body>
<form action="/mgrer/scenic/saveScenicCallery" method="post" enctype="multipart/form-data">
	<div class="easyui-accordion" data-options="multiple:true" style="width:100%;">
		<div title="景点相册" id="picsDiv"  style="padding:10px;">
			<div id="imageBox">
				<input type="hidden" id="galleryId" name="galleryId" value="${galleryId}">
				<input type="hidden" id="gp" name="galleryPage" value="1">
				<div id="imagePanel" ></div>
				<c:if test="${scenicIds > 0 }">
					<div class="webuploader-more" id="webuploader-more" onclick="loadPics($('#gp').val())">加载更多</div>
				</c:if>
			</div>
		</div>
		<div style="padding:5px 0;">
			<input type="hidden" name="sid" value="${scenicIds }"/>
			<a href="#" style="width:120px;height:30px" class="easyui-linkbutton"  onclick="submitGallery()"
			>提交审核</a>
			<a href="#" style="width:120px;height:30px" class="easyui-linkbutton"  onclick="cancel()"
					>取消</a>
		</div>
	</div>
</form>
</body>
</html>