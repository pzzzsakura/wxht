$(function() {
	// 从地址栏的URL里获取wxId
	var wxId = getQueryString('wxId');
	// 获取微信信息的URL
	var wxUrl = '/wxht/frontend/getwxdetail?wxId='
			+ wxId;
	// 访问后台获取该微信的信息并渲染
	$.getJSON(wxUrl, function(data) {
		if (data.success) {
			// 获取微信信息
			var wx = data.wx;
			// 给微信信息相关HTML控件赋值
			// 微信缩略图
			$('#wx-img').attr('src', getContextPath()+wx.wxImg);
			// 微信更新时间
			$('#wx-time').text(
					new Date(wx.updateTime).Format("yyyy-MM-dd"));
			// 微信名称
			$('#wx-name').text(wx.wxName);
			// 微信简介
			$('#wx-desc').text(wx.wxDesc);
			var imgListHtml = '';
			// 遍历微信详情图列表，并生成批量img标签
			wx.wxImgList.map(function(item, index) {
				imgListHtml += '<div> <img src="' + getContextPath()+item.imgAddr
						+ '" width="100%" /></div>';
			});
			$('#imgList').html(imgListHtml);
		}
	});
});
