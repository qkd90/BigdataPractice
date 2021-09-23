<div class="box typelist col-xs-12">
	<#if crossCitys??>
		<dl class="clearfix">
			<dt>
				<span class="title">目的地：</span>
				<span class="curr" id="nocity" href="#">不限</span>
			</dt>
			<dd id="city">
				<#list crossCitys as destination>
					<a href="#" key="${destination}" >${destination}</a>
	            </#list>
			</dd>
		</dl>
	</#if>
	<#if productAttr??>
		<dl class="clearfix">
			<dt>
				<span class="title">出游方式：</span>
				<span class="curr" id="noproductAttr" href="#">不限</span>
			</dt>
			<dd id="productAttr">
				<#list productAttr as trafficType>
					<a href="#" key="${trafficType}">${trafficType}</a>
		        </#list>
			</dd>
		</dl>
	</#if>
	<#if lineDay??>
		<dl class="clearfix">
			<dt>
				<span class="title">线路天数：</span>
				<span class="curr" id="nolineDay" href="#">不限</span>
			</dt>
			<dd id="lineDay">
				<#list lineDay as dayCount>
		            <#if !dayCount_has_next>
		                <a href="#" key="${dayCount}">${dayCount}以上</a>
		            <#else>    
						<a href="#" key="${dayCount}">${dayCount}天</a>
		            </#if>
		        </#list>
			</dd>
		</dl>
	</#if>
	<#if supplierName??>
		<dl class="clearfix">
			<dt>
				<span class="title">供应商：</span>
				<span class="curr" id="nosupplierName" href="#">不限</span>
			</dt>
			<dd id="supplierName">
				<#list supplierName as supplier>
					<a href="#" key="${supplier}">${supplier}</a>
		        </#list>
			</dd>
		</dl>
	</#if>
	<dl class="clearfix last id=" id="selectedItemPanel" style="display:none;">
		<dt>
			<span class="title">已选条件：</span>
			<span class="curr" id="selectedItems">
				<span>跟团游<a href="#" class="close">x</a></span>
			</span>
		</dt>
		<dd>
			<a href="#" class="update">重新筛选</a>
		</dd>
	</dl>
</div>
