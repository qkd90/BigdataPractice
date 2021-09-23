<%@ page language="java" pageEncoding="utf-8"%>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/hScroll.js${mallConfig.resourceVersion}"></script>
<script type="text/javascript" src="${mallConfig.resourcePath}/js/tab.js${mallConfig.resourceVersion}"></script>

<div id="J_mlistimg" class="bottom-panel">
	<a class="imgs_close" id="J_mlist-close"></a>
	<div class="mlistimg-wrap" id="gallery_main">
		<div class="tab-viewport">
			<div class="gallery-list tab-cnt-panel cf" id="J_gallery_list">
			</div>
		</div>
	</div>
</div>

<script type="text/html" id="tpl_mlist-img">
{{each data}}
<div class="img-list tab-cnt" tab-index="{{$index}}">
	<div class="img-wrap">
		<img src="{{$value}}" curl="{{$value}}">
	</div>
</div>
{{/each}}
</script>
