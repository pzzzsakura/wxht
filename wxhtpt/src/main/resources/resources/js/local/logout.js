$(function() {
	$('#log-out').click(function() {
		// 清除session
		$.ajax({
			url : "/wxht/local/logout",
			type : "post",
			async : false,
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data.success) {
					// 清除成功后退出到登录界面
					window.location.href = "/wxht/local/login";
					return false;
				}
			},
			error : function(data, error) {
				alert(error);
			}
		});
	});
});