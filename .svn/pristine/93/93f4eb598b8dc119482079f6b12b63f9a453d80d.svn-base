//设置Cookie
function setCookie(name, value) {
	var exp = new Date();
	exp.setTime(exp.getTime() + 30 * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + encodeURIComponent(value) + ";expires="
			+ exp.toGMTString();
}
// 设置Cookie
function setCookie(name, value, expireDay) {
	var exp = new Date();
	exp.setTime(exp.getTime() + expireDay * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + encodeURIComponent(value) + ";expires="
			+ exp.toGMTString();
}
// 获得Cookie
function getCookie(name) {
	var arr = document.cookie
			.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return decodeURIComponent(arr[2]);
	return null;
}
// 删除Cookie
function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}

// 获取地址栏参数的值
function getQueryStringValue(url, name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = url.substr(url.indexOf('?') + 1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return "";
}

function formatDate(timestamp, formatStr) {
	var now;
	if (timestamp != "") {
		now = new Date(parseInt(timestamp));
	} else {
		now = new Date().getTime();
	}
	var year = now.getYear();
	var month = now.getMonth() + 1;
	var date = now.getDate();
	var hour = now.getHours();
	var minute = now.getMinutes();
	var second = now.getSeconds();
	var d = now.format(formatStr);
	return d;
}

String.prototype.trim=function() {  
    return this.replace(/(^\s*)|(\s*$)/g,'');  
};

Date.prototype.format =function(format){
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

/**
 * 是否全是数字
 */
function isNumber(str) {
	var reg = new RegExp("^[0-9]*$");
	return reg.test(str);
}

