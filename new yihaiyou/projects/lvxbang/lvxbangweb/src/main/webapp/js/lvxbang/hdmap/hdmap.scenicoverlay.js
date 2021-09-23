function HandDrawScenic(point, customclass, width, height, cityCode, level, name, scenicId) {
    this._point = point;
    this._customclass = customclass;
    this._width = width;
    this._height = height;
    this._cityCode = cityCode;
    this._level = level;
    this._scenicId = scenicId;
    this._name = name;
}

HandDrawScenic.prototype = new BMap.Overlay();

HandDrawScenic.prototype.initialize = function (map) {
    this._map = map;
    var div = document.createElement("a");
    //div.setAttribute("class", "scenic-fullMarker #customclass".replace("#customclass", this._customclass));
    div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
    div.style.width = this._width + "px";
    div.style.position = "absolute";
    div.style.height = this._height + "px";
    var img = document.createElement("img");
    img.setAttribute("src", "http://7u2inn.com2.z0.glb.qiniucdn.com/hand-draw-scenic/" + this._cityCode + "/" + this._level + "/" + this._name + ".png");
    img.style.width = this._width + "px";
    img.style.height = this._height + "px";
    img.className = "map-scenic-img";
    img.id = 'img_' + this._scenicId;
    var img_hover = document.createElement("img");
    img_hover.setAttribute("src", "http://7u2inn.com2.z0.glb.qiniucdn.com/hand-draw-scenic/" + this._cityCode + "/" + this._level + "/" + this._name + "%20(2).png");
    img_hover.style.width = this._width + "px";
    img_hover.style.height = this._height + "px";
    img_hover.className = "map-scenic-img shineImg";
    img_hover.id = 'img_hover_' + this._scenicId;
    div.appendChild(img);
    div.appendChild(img_hover);
    this._div = div;
    map.getPanes().labelPane.appendChild(div);
    return div;
};

HandDrawScenic.prototype.draw = function () {
    // 根据地理坐标转换为像素坐标，并设置给容器
    var position = this._map.pointToOverlayPixel(this._point);
    this._div.style.left = position.x - this._width / 2 + "px";
    this._div.style.top = position.y - this._height / 2 + "px";
};

HandDrawScenic.prototype.addEventListener = function (event, func) {
    $(this._div).on(event, func);
};

HandDrawScenic.prototype.clickExt = function (callback) {
    var mc;
    $(this._div).on("mousedown", function (e) {
        mc = mouseCoords(e);
    });
    $(this._div).on("click", function (e) {
        var target = mouseCoords(e);
        if (target.x == mc.x && target.y == mc.y) {
            callback();
        }
    });

    function mouseCoords(e) {
        if (e.pageX || e.pageY) {
            return {x: e.pageX, y: e.pageY};
        }
        else {
            return {x: e.clientX, y: e.clientY};
        }
    }
};

