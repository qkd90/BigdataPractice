<div class="container" id="gys-header">
    <div class="row">
        <div class="col-xs-12">
            <h1><a href="#">${sysUnit.name} </a></h1>
        </div>
    </div>
</div>
<input id="unit-id" type="hidden" value="${sysUnit.id?c}">
<div class="container-fluid" id="topnav">
    <div class="container">
        <div class="mainnav"><a href="/mall/supplier/home.jhtml?id=${sysUnit.id?c}" class="curr">企业首页</a> <a href="/mall/supplier/lines.jhtml?id=${sysUnit.id?c}">旅游线路</a> <a href="/mall/ticket/gysTicket.jhtml?supplierId=${sysUnit.id?c}">景点门票</a> <a
                href="/mall/supplier/about.jhtml?id=${sysUnit.id?c}">关于我们</a> <span href="#" class="pull-right cart"></span></div>
    </div>
</div>
<!--头部结束-->

<div class="container">
    <div class="row">
        <div class="col-xs-9">

            <!--企业动态-->
            <div class="qybox qydt">
                <h4>
                    <span class="title">企业动态</span>

                    <form class="form-inline pull-right" id="search-form">
                        <select class="form-control" id="product-type">
                            <option>线路</option>
                            <#--<option>景点</option>-->
                            <option>门票</option>
                        </select>
                        <input type="text" class="form-control" placeholder="请输入关键词" id="product-name">
                        <input class="btn btn-warning" type="button" value="搜索" id="search-btn">
                    </form>
                </h4>
                <div class="row">
                    <div class="col-xs-3">
                        <div class="cpdtall">产品发布${lastUpProduct.totalCount}条<br>产品上架${lastDistributionList.totalCount}次
                        </div>
                        <div class="qydtall">合作分销商${distributorCount}家</div>
                    </div>
                    <div class="col-xs-9">
                        <div class="gys-newslist">
                            <ul>
                            <#list lastDistributionList.list as product>
                                <li>
                                <#if product.proType=='line'>
                                    <a href="/mall/line/detail.jhtml?id=${product.id}">
                                <#elseif product.proType=='scenic'>
                                    <a href="/mall/ticket/detail.jhtml?id=${product.id}">
                                </#if>
                                ${product.user.userName}上架了[
                                    <#if product.proType=='line'>
                                        线路
                                    <#elseif product.proType=='scenic'>
                                        门票
                                    <#elseif product.proType=='hotel'>
                                        酒店
                                    <#elseif product.proType=='restaurant'>
                                        餐厅
                                    </#if>
                                    ]${product.name}</a><span class="time pull-right">${product.createTime}</span></li>
                            </#list>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <!--企业动态-->
            <!--精品推荐-->
            <div class="qybox jptj">
                <h4><span class="title">精品推荐</span></h4>

                <div class="jplist row">
                    <#list recommendList.list as product>
                        <div class="col-xs-4">
                            <div class="thumb"><a href="#"><img src="<#if product.imgUrl!=''>${imguriPreffix}${product.imgUrl}</#if>" class="img-responsive" style="width: 279px; height: 273px"></a></div>
                            <div class="content clearfix">
                                <h2>
                                <#if product.proType=='line'>
                                    <a href="/mall/line/detail.jhtml?id=${product.id}">
                                <#elseif product.proType=='scenic'>
                                    <a href="/mall/ticket/detail.jhtml?id=${product.id}">
                                </#if>
                                ${product.name}</a></h2>

                                <p class="price">销售价:￥${product.price}</p>
                            </div>
                        </div>
                    </#list>

                </div>
            </div>
            <!--精品推荐-->
            <!--所有产品-->
            <div class="qybox allpro">
                <h4><span class="title">所有产品</span></h4>
                <#list lastUpProduct.list as product>
                    <div class="media allprolist">
                        <div class="media-left"><img src="<#if product.imgUrl!=''>${imguriPreffix}${product.imgUrl}</#if>" class="img-responsive" style="width: 220px; height: 120px"></div>
                        <div class="media-body">
                            <h2>
                            <#if product.proType=='line'>
                                <a href="/mall/line/detail.jhtml?id=${product.id}">
                            <#elseif product.proType=='scenic'>
                                <a href="/mall/ticket/detail.jhtml?id=${product.id}">
                            </#if>
                            ${product.name}</a></h2>

                            <p class="price pull-left">销售价：¥${product.price}</p>
                            <#if product.proType=='line'>
                                <a href="/mall/line/agent.jhtml?productId=${product.id}" class="btn btn-danger btn-lg pull-right iagent">我要分销</a>
                            <#elseif product.proType=='scenic'>
                                <a href="/mall/ticket/agent.jhtml?productId=${product.id}" class="btn btn-danger btn-lg pull-right iagent">我要分销</a>
                            </#if>

                        </div>
                    </div>
                </#list>
            </div>
            <!--所有产品-->
            <!--公司图片-->
            <div class="qybox gstp">
                <h4><span class="title">公司图片</span></h4>

                <div class="tplist row">
                <#if (sysUnit.sysUnitImages?size>0)>
                    <#list sysUnit.sysUnitImages as image>
                        <#if (image_index<=4)>
                            <div class="col-xs-3"><a href="#"><img src="${imguriPreffix}${image.path}" class="img-responsive" style="width: 220px; height: 120px"></a></div>
                        </#if>
                    </#list>
                <#else>
                    <#list lastUpProduct.list as product>
                        <#if (product_index<=4)>
                            <div class="col-xs-3"><a href="#"><img src="<#if product.imgUrl!=''>${imguriPreffix}${product.imgUrl}</#if>" class="img-responsive"></a></div>
                        </#if>
                    </#list>
                </#if>

                </div>
            </div>
            <!--公司图片-->
            <div class="qybox gsjj">
                <h4><span class="title">公司简介</span></h4>

                <div class="content">
                    ${sysUnit.sysUnitDetail.introduction}
                </div>
            </div>
            <!--公司图片-->

        </div>
        <div class="col-xs-3">
            <div class="sjpx clearfix">
                <h4>上架排行</h4>
                <ul class="clearfix">
                    <#list mostDistributionList.list as productMap>
                        <li>
                            <p class="title"><span>${productMap_index+1}</span><a href="#">${productMap.product.name}</a></p>

                            <p class="num">${productMap.count}次上架</p>
                        </li>
                    </#list>

                </ul>
            </div>
        </div>
    </div>
</div>
