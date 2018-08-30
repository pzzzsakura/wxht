$(function() {
	// 登录验证的controller url
	var loginUrl = '/wxht/local/logincheck';
	// 登录次数，累积登录三次失败之后自动弹出验证码要求输入
	var loginCount = 0;
	$('#submit').click(function() {
		// 获取输入的帐号
		var userPhone = $('#userPhone').val();
		// 获取输入的密码
		var password = $('#psw').val();
		if(isNull(userPhone)){
			alert("电话不能为空")
		}
		if(isNull(password)){
			alert("密码不能为空")
		}
		// 获取验证码信息
		var verifyCodeActual = $('#j_captcha').val();
		// 是否需要验证码验证，默认为false,即不需要
		var needVerify = false;
		// 如果登录三次都失败
		if (loginCount >= 3) {
			// 那么就需要验证码校验了
			if (!verifyCodeActual) {
				$.toast('请输入验证码！');
				return;
			} else {
				needVerify = true;
			}
		}
		// 访问后台进行登录验证
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : 'json',
			data : {
        userPhone : userPhone,
        password : password,
				verifyCodeActual : verifyCodeActual,
				//是否需要做验证码校验
				needVerify : needVerify
			},
			success : function(data) {
				if (data.success) {
					$.toast('登录成功！');
						// 若用户在前端展示系统页面则自动链接到前端展示系统首页
						window.location.href = '/wxht/wxmanage/wxmanage';
				} else {
          $.toast('登录失败！' + data.errMsg);
          loginCount++;
          if (loginCount >= 3) {
            changeVerifyCode($("#captcha_img"));
            // 登录失败三次，需要做验证码校验
						$('#verifyPart').show();
					}
				}
			}
		});
	});
});