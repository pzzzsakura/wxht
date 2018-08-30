$(function() {
  //绑定帐号的controller url
	var verifyCodeUrl = '/wxht/local/getverifycode';
	var bindUrl = '/wxht/local/bindlocalauth';
  $('#submit').click(function() {
    // 获取输入的帐号
		var userName = $('#username').val();
    // 获取输入的密码
		var password = $('#psw').val();
    var userPhone = $('#tel').val();
    //获取输入的性别
		var sex = $('#sex').find("option").not(function () {
      return !this.selected;
    }).data('id');
    //获取头像
    var userImg = $('#user-img')[0].files[0];
    // 获取输入的验证码
		var verifyCode = $('#verifyCode').val();
   // var needVerify = false;
    if (!verifyCode) {
      $.toast('请输入验证码！');
      return;
    }
    if(isNull(userName)){
      alert("用户名不能为空")
    }
    if(isNull(password)){
      alert("密码不能为空")
    }
    if(isNull(userPhone)){
      alert("电话不能为空")
    }
    if(isNull(verifyCode)){
      alert("验证码不能为空")
    }
    var formData = new FormData();
    formData.append("userName",userName);
    formData.append("password",password);
    formData.append("verifyCode",verifyCode);
    formData.append("userPhone",userPhone);
    formData.append("userImg",userImg);
    formData.append("sex",sex);
    // 访问后台，绑定帐号
		$.ajax({
			url : bindUrl,
      contentType: false,
      processData: false,
      cache: false,
			type : "post",
			data : formData,
			success : function(data) {
				if (data.success) {
					$.toast('绑定成功！');
						window.location.href = '/wxht/wxmanage/wxmanage';
				} else {
					$.toast('提交失败！' + data.errMsg);
				}
			}
		});

	});
  $("#msg-num-btn").click(function () {
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