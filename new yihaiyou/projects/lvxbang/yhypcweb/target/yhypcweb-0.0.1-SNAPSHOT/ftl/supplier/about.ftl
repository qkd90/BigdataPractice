<!--供应商头部开始-->
<div class="container" id="gys-header">
    <div class="row">
        <div class="col-xs-12">
            <h1><a href="#">${sysUnit.name} </a></h1>
        </div>
    </div>
</div>
<div class="container-fluid" id="topnav">
    <div class="container">
        <div class="mainnav"> <a href="/mall/supplier/home.jhtml?id=${sysUnit.id?c}">企业首页</a> <a href="/mall/supplier/lines.jhtml?id=${sysUnit.id?c}">旅游线路</a> <a href="/mall/ticket/gysTicket.jhtml?supplierId=${sysUnit.id?c}">景点门票</a> <a href="/mall/supplier/about.jhtml?id=${sysUnit.id}" class="curr">关于我们</a> <span href="#" class="pull-right cart"></span> </div>
    </div>
</div>
<!--头部结束-->

<div class="container-bg">
    <div class="container">
        <div class="row">
            <div class="col-xs-2">
                <div class="leftnav">
                    <dt>关于我们</dt>
                    <dd class="curr"><a href="/mall/supplier/about.jhtml?id=${sysUnit.id?c}">公司简介</a></dd>
                    <dd><a href="/mall/supplier/contact.jhtml?id=${sysUnit.id?c}">联系我们</a></dd>
                </div>
            </div>
            <div class="col-xs-10">
                <div class="box" id="about">
                    <h4 class="title"><span><span>公司简介</span></span></h4>
                    <div class="media">
                        <div class="media-left"><img src="${imguriPreffix}${sysUnit.sysUnitDetail.logoImgPath}" class="img-responsive" style="width: 220px; height: 120px"></div>
                        <div class="media-body">
                            <h2>${sysUnit.name}</h2>
                            <p>成立时间：${sysUnit.sysUnitDetail.buildTime}</p>
                            <p>${sysUnit.address}</p>
                            <p>经营范围：${sysUnit.sysUnitDetail.mainBusiness}</p>
                        </div>
                    </div>
                    <div class="clearfix content">
                       ${sysUnit.sysUnitDetail.indtroduction}
                    </div>
                </div>
                <div class="box" id="about">
                    <h4 class="title"><span><span>公司图片</span></span></h4>
                    <div class="tplist row">
                    <#if (sysUnit.sysUnitImages?size>0)>
                        <#list sysUnit.sysUnitImages as image>
                            <#if (image_index<=4)>
                                <div class="col-xs-3"><a href="#"><img src="${imguriPreffix}${image.path}" class="img-responsive"></a></div>
                            </#if>
                        </#list>
                    <#else>
                        <#list lastUpProduct.list as product>
                            <#if (product_index<=4)>
                                <div class="col-xs-3"><a href="#"><img src="${product.imgUrl}" class="img-responsive"></a></div>
                            </#if>
                        </#list>
                    </#if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
