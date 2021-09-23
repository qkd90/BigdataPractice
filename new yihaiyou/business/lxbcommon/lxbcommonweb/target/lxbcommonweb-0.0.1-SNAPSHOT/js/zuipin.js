////////////////////////////Zuipin.js用于绑定快捷键/////////////////
(function (window, undefined) {
	// 避免重复创建window.Yunpos对象
    if (window.Zuipin) {
        return;
    }
	var Zuipin={};
	//keyCode对
	var KeyCode=[];
    KeyCode.push({
    	"0": 48,
        "1": 49,
        "2": 50,
        "3": 51,
        "4": 52,
        "5": 53,
        "6": 54,
        "7": 55,
        "8": 56,
        "9": 57,
        "a": 65,
        "b": 66,
        "c": 67,
        "d": 68,
        "e": 69,
        "f": 70,
        "g": 71,
        "h": 72,
        "i": 73,
        "j": 74,
        "k": 75,
        "l": 76,
        "m": 77,
        "n": 78,
        "o": 79,
        "p": 80,
        "q": 81,
        "r": 82,
        "s": 83,
        "t": 84,
        "u": 85,
        "v": 86,
        "w": 87,
        "x": 88,
        "y": 89,
        "z": 90,
        "Num0": 96,
        "Num1": 97,
        "Num2": 98,
        "Num3": 99,
        "Num4": 100,
        "Num5": 101,
        "Num6": 102,
        "Num7": 103,
        "Num8": 104,
        "Num9": 105,
        "+":107,
        "F1": 112,
        "F2": 113,
        "F3": 114,
        "F4": 115,
        "F5": 116,
        "F6": 117,
        "F7": 118,
        "F8": 119,
        "F9": 120,
        "F10": 121,
        "F11": 122,
        "F12": 123,
        "F13": 124,
        "F14": 125,
        "F15": 126,
        "Backspace": 8,
        "Tab": 9,
        "Clear": 12,
        "Enter": 13,
        "Shift": 16,
        "Ctrl": 17,
        "Alt": 18,
        "Caps Lock": 20,
        "Esc": 27,
        "Space": 32,
        "Page Up": 33,
        "Page Down": 34,
        "End": 35,
        "Home": 36,
        "Arrow Left": 37,
        "Arrow Up": 38,
        "Arrow Right": 39,
        "Arrow Down": 40,
        "Insert": 45,
        "Delete": 46,
        "Help": 47,
        "Num Lock": 144
    });	
	//快捷键注册
	Zuipin.KeyMap = (function () {
		return {
            // 快捷键注册标记，当往父窗口注册按键监控时，判断标记是否存在，避免重复绑定事件
            stamps: [],
            /*
            ** 为某个元素注册按键监控
            ** @options: {
            **     key:           单个按键"F1" 或多个按键["F1", "F2", "F3"]，支持的按键列表在Yunpos.Config.js里
            **     el:            要监控的页面元素
            **     ctrl:          可选参数，是否按住ctrl，true或false，默认为false
            **     shift:         可选参数，是否按住shift，true或false，默认为false
            **     stopBubble:    可选参数，是否阻止事件冒泡，true或false，默认为false
            **     stopDefault:   可选参数，是否阻止浏览器默认事件，true或false，默认为false
            **     watchParent:   可选参数，是否同时监听父窗口的快捷键，监听父窗口是为了解决焦点在父窗口，想通过快捷键触发子窗口事件的问题，默认为false
            **     watchChildren: 可选参数，是否同时监听子窗口以及子窗口内部子窗口的快捷键，监听子窗口是为了解决焦点在子窗口，想通过快捷键触发父窗口事件的问题，默认为false
            **     whenVisible:   可选参数，配合watchParent使用，表示是否只在子窗口可见时才触发子窗口事件，默认为true
            **     whenUnlock:    可选参数，配合window["LOCK"]使用，传入true，则只有当window["LOCK"] == false时才会触发回调
            **     stamp:         唯一性标记，配合watchParent使用，防止父窗口重复绑定按键事件
            **     fn:            触发监控按键时要执行的函数
            ** }
            */
            add: function (options) {
                var options = options || {}, mapArr = [];
                // 未传入必须参数，则退出
                if (!options.key || !options.el || !options.fn) {
                    return;
                }
                // 判断当前窗口或父级窗口是否被锁定
                var isLock = function () {
                    if (window["LOCK"]) {
                        return true;
                    }
                };

                // 监控是否按下了指定的按键，并执行回调
                var keyWatch = function (e, callback) {
                    // 如果设置只在window["LOCK"]为false时才执行回调，
                    // 则当window["LOCK"]为true时，提前退出
                    if (options.whenUnlock && isLock()) {
                        return;
                    }
                    var code = e.keyCode;
                    if (!!options.ctrl != e.ctrlKey || !!options.shift != e.shiftKey || !!options.alt != e.altKey) {
                        return;
                    }
                    if (mapArr.indexOf(e.keyCode) > -1) {
                        callback(e, e.keyCode);
                    }
                };
                // 当前页面keydown事件
                var keydown = function (e) {
                    keyWatch(e, function (e, key) {
                    	if (options.stopBubble) {
                    		e.stopPropagation();
                    	}
                    	if (options.stopDefault) {
                    		e.preventDefault();
                    	}
                        options.fn(e, key);
                    });
                };
                // 将传入的参数options.key保存到mapArr备用
                if (options.key.constructor === String) {
                    mapArr.push(KeyCode[0][options.key]);
                } else if (Array.isArray(options.key)) {
                    options.key.forEach(function (item) {
                        mapArr.push(KeyCode[0][item]);
                    });
                }
                // 为指定的元素绑定按键事件
                options.el.bind("keydown", keydown);
            }
        };
	})();
	// 使Yunpos成为全局变量
    window["Zuipin"] = Zuipin;
})(window, undefined);

