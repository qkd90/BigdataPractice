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
        <div class="mainnav"> <a href="/mall/supplier/home.jhtml?id=${sysUnit.id?c}">企业首页</a> <a href="#">旅游线路</a> <a href="#">景点门票</a> <a href="/mall/supplier/about.jhtml?id=${sysUnit.id} class="curr">关于我们</a> <span href="#" class="pull-right cart"></span> </div>
    </div>
</div>
<!--头部结束-->

<div class="container-bg">
    <div class="container">
        <div class="row">
            <div class="col-xs-2">
                <div class="leftnav">
                    <dt>关于我们</dt>
                    <dd><a href="/mall/supplier/about.jhtml?id=${sysUnit.id?c}">公司简介</a></dd>
                    <dd class="curr"><a href="/mall/supplier/contact.jhtml?id=${sysUnit.id?c}">联系我们</a></dd>
                </div>
            </div>
            <div class="col-xs-10">
                <div class="box" id="about">
                    <h4 class="title"><span><span>联系我们</span></span></h4>
                    <div class="clearfix content box">
                        <br><br><br><p><img src="${imguriPreffix}${sysUnit.sysUnitDetail.logoImgPath}" class="img-responsive" style="width: 220px; height: 120px"></p>
                        <p><b>${sysUnit.name}</b></p>
                        <p>联系人：${sysUnit.sysUnitDetail.contactName}</p>
                        <p>电   话：${sysUnit.sysUnitDetail.telphone}</p>
                        <p>手   机：${sysUnit.sysUnitDetail.mobile}</p>
                        <p>传   真：${sysUnit.sysUnitDetail.fax}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>