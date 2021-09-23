<div class="col-xs-12"><h4 class="box-title clearfix gys-title">
    <span class="title">供应商</span><a href="/mall/supplier/list.jhtml" class="more pull-right">更多&gt;&gt;</a></h4></div>
<#list supplierList as supplier>
<div class="col-xs-4 gyslist box">
    <div class="media">
        <div class="media-left"><img src="/images/home/g${supplier_index+1}.jpg" title="这个暂时是假的" style="width: 90px; height: 106px"></div>
        <div class="media-body"><h2>${supplier.name}</h2><span>${supplier.remark}</span></div>
    </div>
    <div class="info clearfix">主营业务：国内旅游，省内周边旅游，团队旅游，拓展培训、自驾游，提供酒店。</div>
</div>
</#list>