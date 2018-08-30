$(function() {
	//获得在那个页面跳过来的
  var verifyCodeUrl = '/wxht/local/getverifycode';
	var id = getQueryString("id");
if(id==2){
  var userInfoUrl = "/wxht/local/getuserinfo";
  $.getJSON(userInfoUrl,function (data) {
    if(data.success){
      $("#tel").val(data.user.userPhone);
      $("#tel").attr("readOnly",true);
    }else{
      $.toast(data.errMsg);
    }
  });
}

	// 修改平台密码的controller url
	var url = '/wxht/local/modifypwd';
	$('#submit').click(function() {
		// 获取帐号
		var tel = $('#tel').val();
		// 获取原密码
		var password = $('#newPassword').val();
		// 获取新密码
		var verifyCode = $('#verifyCode').val();
    if(isNull(tel)){
      alert("电话不能为空")
    }
    if(isNull(password)){
      alert("密码不能为空")
    }
    if(isNull(verifyCode)){
      alert("验证码不能为空")
    }
		// 将参数post到后台去修改密码
		$.ajax({
			url : url,
			type : 'POST',
			data : {
        tel:tel,
        password:password,
        verifyCode:verifyCode
			},
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
						window.location.href = '/wxht/local/login';
					}else {
					$.toast('提交失败！' + data.errMsg);
				}
			}
		});
	});

  $("#msg-num-btn").click(function () {
    var o = this;
    var tel = $("#tel").val();//获取手机号码输入框值
    var reg = /^1[3|4|5|8][0-9]\d{4,8}$/;
    if(!reg.test(tel)){ //校验手机号码格式
      alert("请先输入您的正确手机号！");
      return false;
    }
    $.post(verifyCodeUrl+"?tel="+tel,function (data) {
      if(data.success){
        get_code_time(o);  //发送成功则出发get_code_time（）函数
      }else{
        alert(data.errMsg)
        $.toast("短信验证码发送失败！请重新获取。");
      }
    });

  });
  var wait = 60;
  get_code_time = function (o) {
    if (wait == 0) {
      o.removeAttribute("disabled");
      $("#msg-num-btn").text("获取验证码");
      wait = 60;
    } else {
      o.setAttribute("disabled", true);
      $("#msg-num-btn").text("(" + wait + ")秒后重新获取");
      wait--;
      setTimeout(function() {
        get_code_time(o)
      }, 1000);
    }
  };

});
