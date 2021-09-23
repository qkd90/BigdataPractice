<!--所在位置-->
<div class="title-box">
    <div class="title-wrap">
        <h3>您在这里：<a href="/yhypc/index/index.jhtml">首页</a>><a href="/yhypc/sailboat/index.jhtml">海上休闲</a>><a href="/yhypc/sailboat/list.jhtml">海上休闲产品</a>>${ticket.name}</h3>
    </div>
</div>
<!--产品图-->
<div class="product-box">
    <div class="product-wrap clearfix">
            <div class="pictrue clearfix">
                <div class="pic_max">
                    <#list productimages as productImg>
                            <input id="sailboat-id" type="hidden" value="${ticket.id}">
                            <#if productImg.path != null>
                                <img src="${QINIU_BUCKET_URL}${productImg.path}?imageView2/1/w/645/h/295/q/75" alt="${ticket.name}">
                            <#else>
                                <img src="/image/details-carousel-pic1.png" alt="${ticket.name}">
                            </#if>
                    </#list>
                </div>
                <div class="pic_min">
                    <label class="t_left"><</label>
                    <ul class="clearfix">
                        <#list productimages as productImg>
                            <li>
                                <#if productImg.path != null>
                                    <img src="${QINIU_BUCKET_URL}${productImg.path}?imageView2/1/w/645/h/295/q/75" alt="${ticket.name}">
                                <#else>
                                    <img src="/image/details-carousel-pic1.png" alt="${ticket.name}">
                                </#if>
                            </li>
                        </#list>
                    </ul>
                    <label class="t_right">&gt;</label>
                </div>
            </div>
        <div class="product-info">
            <h2>${ticket.name}</h2>
            <p><mark>地址：</mark> ${ticket.address}
                <a href="javascript:void(0);" id="map-id">
                    <i class="product-map">地图</i>
                </a>
            </p>
            <p><mark>营业时间</mark>：${explain.openTime}</p>
            <div class="grade">
            </div>
        </div>
    </div>
</div>

<!--产品详情-->

<div class="details-title clearfix">
    <ul class="details-nav clearfix" id="details-nav">
        <li id="nav-ticket" class="active">票型</li>
        <li id="nav-sightport">景点介绍</li>
        <li id="nav-comment">评价（<span id="nav-comment-span"></span>）</li>
        <li id="nav-traffic">交通</li>
    </ul>
</div>

<div class="details-wrap">
    <h3 id="ticket-target"><i></i>票型</h3>
    <div class="ticket-details">
        <p class="ticket_title" style="display: none;">暂无可售票型</p>
        <ul class="ticket-details-header clearfix">
            <li>票型名称</li>
            <li>销售价</li>
        </ul>
        <ul class="ticket-details-body" id="ticketPriceList" data-id="${ticket.id}">
        </ul>
        <div class="ticket-details-unflod clearfix">
            <a href="javascript:void(0)" class="check-more-item" class="pull-right" data-flag="0"><span id="html-content">展开全部票型</span><#--（）-->
                <i class="down"></i>
            </a>
        </div>
    </div>
    <h3 id="sightport-target"><i></i>景点介绍</h3>
    <div class="sightport-details">
        ${explain.proInfo}
    </div>
    <h3 id="comment-target"><i></i>评价详情</h3>
    <div class="comment-details">
        <div class="comment-details-header clearfix">

        </div>
        <ul class="comment-details-body" id="commentList" data-target-id="${ticket.id}">
        </ul>
        <div class="paging m-pager">
        </div>

    </div>

    <h3 id="traffic-target"><i></i>周边交通</h3>
    <div class="details-comment-traffic" >
        <div class="details-comment-map" id="traffic-map" data-lng="${ticket.scenicInfo.scenicGeoinfo.baiduLng}" data-lat="${ticket.scenicInfo.scenicGeoinfo.baiduLat}">
        </div>
    </div>

</div>


