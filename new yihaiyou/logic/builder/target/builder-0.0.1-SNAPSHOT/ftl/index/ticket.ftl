<div class="col-xs-12"><h4 class="box-title clearfix jdmp-title"><span class="title">景点门票</span><a href="/mall/ticket/list.jhtml" class="more pull-right">更多&gt;&gt;</a></h4></div>
<#list ticketList as ticket>
<div class="col-xs-3 jdmp box">
    <div class="thumb"><a href="/mall/ticket/detail.jhtml?id=${ticket.id?c}"><img src="<#if ticket.ticketImgUrl!=''>${imguriPreffix}${ticket.ticketImgUrl}</#if>" class="img-responsive"style="width: 279px; height: 273px"></a></div>
    <div class="content clearfix">
        <h2 class="pull-left"><span>${ticket.name}</span><span class="price">销售价:${ticket.price}</span></h2>
        <a href="/mall/ticket/detail.jhtml?id=${ticket.id?c}" class="more pull-right"><img src="/images/more1.jpg"></a>
    </div>
</div>
</#list>