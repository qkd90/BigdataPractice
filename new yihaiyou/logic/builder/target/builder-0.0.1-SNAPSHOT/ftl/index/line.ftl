<div class="col-xs-12"><h4 class="box-title clearfix lyxl"><span class="title">旅游线路</span><a href="/mall/line/list.jhtml" class="more pull-right">更多&gt;&gt;</a></h4></div>
<#list lineList as line>
<div class="col-xs-6 xianlu box">
    <div class="thumb"><a href="/mall/line/detail.jhtml?id=${line.id?c}"><img src="<#if line.lineImageUrl!=''>${imguriPreffix}${line.lineImageUrl}</#if>" class="img-responsive" style="width: 583px; height: 320px"></a></div>
    <div class="content clearfix">
        <h2 class="pull-left"><span>${line.name}</span><span class="price">销售价:<b>${line.price}</b></span></h2>
        <a href="/mall/line/detail.jhtml?id=${line.id?c}" class="more pull-right"><img src="/images/more.jpg"></a>
    </div>
</div>
</#list>