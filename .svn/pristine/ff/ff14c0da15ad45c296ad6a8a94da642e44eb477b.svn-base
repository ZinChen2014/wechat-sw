var expressionCommon = expressionCommon || {
	// 不能为空
	checkEmpty : function(value) {
		var isFlag = true;
		if (value == null || $.trim(value) == "" || value == "undefined" || value == undefined) {
			isFlag = false;
		}
		return isFlag;
	},
	// 邮箱
	checkEmail : function(value) {
		var isFlag = true;
		if (!/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(value) && value != "") {
			isFlag = false;
		}
		return isFlag;
	},
	// 手机
	checkMobile : function(value) {
		var isFlag = true;
		if (!/^[1][0-9]{10}$/.test(value) && value != "") {
			isFlag = false;
		}
		return isFlag;
	},
	// 邮编
	checkZipCode : function(value) {
		var isFlag = true;
		if (!/^[0-9]{6}$/.test(value) && value != "") {
			isFlag = false;
		}
		return isFlag;
	},
	// QQ
	checkQQ : function(value) {
		var isFlag = true;
		if (!/^[-]{0,1}[0-9]{1,}$/.test(value) && value != "") {
			isFlag = false;
		}
		return isFlag;
	},
	// 整数
	checkInteger : function(value) {
		var isFlag = true;
		if (!/^[-]{0,1}[0-9]{1,}$/.test(value) && value != "") {
			isFlag = false;
		}
		return isFlag;
	},
	// 四位价格
	checkPrice : function(value) {
		var isFlag = true;
		if (!/^[0-9]+(.[0-9]{1,4})?$/.test(value) && value != "") {
			isFlag = false;
		}
		return isFlag;
	},
	// 两位价格
	checkDoublePrice : function(value) {
		var isFlag = true;
		if (!/^-?\d+\.?\d{0,2}$/.test(value) && value != "") {
			isFlag = false;
		}
		return isFlag;
	},
	// 汉字
	checkChinese : function(value) {
		var isFlag = true;
		if (!/^[\u4e00-\u9fa5]+$/.test(value) && value != "") {
			isFlag = false;
		}
		return isFlag;
	},
	// 只由字母，数字，下划线组成
	checkLdu : function(value) {
		var isFlag = true;
		if (!/^[0-9a-zA-Z\_]+$/.test(value) && value != "") {
			isFlag = false;
		}
		return isFlag;
	},
	// 限制输入长度，不能超过20个字符
	checkMaxLength : function(value, num) {
		var isFlag = true;
		if (value.length > num && value != "") {
			isFlag = false;
		}
		return isFlag;
	},
	// 不能少于6个字符
	checkMinLength : function(value, num) {
		var isFlag = true;
		if (value.length < num && value != "") {
			isFlag = false;
		}
		return isFlag;
	},
	// 验证身份证
	checkCard : function(value) {
		var isFlag = true;
		//身份证正则表达式
		var isIDCard=/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; 
		if(!isIDCard.test(value)){
			isFlag = false;
		}
		return isFlag;
	},
	// 给对象添加span
	addErrorMessage : function(obj, message) {
		$(obj).next(".gr_red").remove();
		$(obj).after('<span class="gr_red">' + message + '</span>');
	},
	// 移除错误信息span
	removeErrorMessage : function(obj) {
		$(obj).next(".gr_red").remove();
	},
	errorMsg : {

	}
};

// ----------------------------------------------------------------------
// <summary>
// 限制只能输入数字
// </summary>
// ----------------------------------------------------------------------
$.fn.onlyNum = function() {
	$(this).keypress(function(event) {
		var eventObj = event || e;
		var keyCode = eventObj.keyCode || eventObj.which;
		if ((keyCode >= 48 && keyCode <= 57 || keyCode == 8))
			return true;
		else
			return false;
	}).focus(function() {
		// 禁用输入法
		this.style.imeMode = 'disabled';
	}).blur(function() {
		if (!expressionCommon.checkInteger($(this).val())) {
			$(this).val("");
		}
	});
	// .bind("paste", function () {
	// //获取剪切板的内容
	// var clipboard = window.clipboardData.getData("Text");
	// if (/^\d+$/.test(clipboard))
	// return true;
	// else
	// return false;
	// });
};