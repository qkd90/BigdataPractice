<div class="box typelist">
	<#if areas??>
		<dl class="clearfix">
			<dt>
				<span class="title">目的地：</span>
				<span class="curr" id="nocity" key="">不限</span>
			</dt>
			<dd id="city">
				<#list areas as area>
					<a key="${area.id?c}">${area.name}</a>
				</#list>
			</dd>
	
		</dl>
	</#if>
	<#if supplierTypes??>
		<dl class="clearfix">
			<dt>
				<span class="title">类型：</span>
				<span class="curr" id="nosupplierType" key="">不限</span>
			</dt>
			<dd id="supplierType">
				<#list supplierTypes as supplierType>
					<a key="${supplierType}">
						<#if supplierType=='zhuangxiang'>
		                 	  专线
		                <#elseif supplierType=='zhonghe'>
		                   	 综合
		                <#elseif supplierType=='dijie'>
		                   	 地接
		                <#elseif supplierType=='chujing'>
		                   	 出境
		                <#elseif supplierType=='ticket'>
		                   	 票务
		                <#elseif supplierType=='hotel'>
		                   	 酒店
		                </#if></a>
				</#list>
			</dd>
	
		</dl>
	</#if>

</div>