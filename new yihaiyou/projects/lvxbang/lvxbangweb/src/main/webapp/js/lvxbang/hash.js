/**
 * Created by huangpeijie on 2016-02-29,0029.
 */
function setToHash(key, value) {
    if (key == null || key.length <= 0) {
        return;
    }
    if (value == null || value.length <= 0) {
        deleteFromHash(key);
        return;
    }
    var hash = location.hash;
    var array = hash.substring(hash.indexOf("#") + 1).split("&");
    hash = "";
    var flag = false;
    for (var i = 0; i < array.length; i++) {
        var object = array[i].split("=");
        if (object[0] == key) {
            array[i] = object[0] + "=" + value;
            flag = true;
        }
        if (hash.length > 0) {
            hash += "&";
        }
        hash += array[i];
    }
    if (!flag) {
        if (hash.length > 0) {
            hash += "&";
        }
        hash += key + "=" + value;
    }
    location.hash = hash;
}

function getFromHash(key) {
    var value = "";
    if (key == null || key.length <= 0) {
        return value;
    }
    var hash = location.hash;
    var array = hash.substring(hash.indexOf("#") + 1).split("&");
    for (var i = 0; i < array.length; i++) {
        var object = array[i].split("=");
        if (object[0] == key) {
            value = object[1];
        }
    }
    return value;
}

function deleteFromHash(key) {
    if (key == null || key.length <= 0) {
        return;
    }
    var hash = location.hash;
    var array = hash.substring(hash.indexOf("#") + 1).split("&");
    hash = "";
    for (var i = 0; i < array.length; i++) {
        var object = array[i].split("=");
        if (object[0] == key) {
            continue;
        }
        if (hash.length > 0) {
            hash += "&";
        }
        hash += array[i];
    }
    location.hash = hash;
}