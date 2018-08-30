$(function() {
	var loading = false;
	// 分页允许返回的最大条数，超过此数则禁止访问后台
	var maxItems = 20;
	// 默认一页返回的微信号数
	var pageSize = 3;
	// 列出微信列表的URL
	var listUrl = '/wxht/frontend/getwxlist';
	// 默认的页码
	var pageNum = 1;
	// 从地址栏里获取wxManageId
	var wxManageId = getQueryString('wxManageId');
	var wmcId = '';
	var wxName = '';
	// 获取本微信信息以及微信号群组信息列表的URL
	var searchDivUrl = '/wxht/frontend/getwxmanagedetail?wxManageId=' + wxManageId;
	// 渲染出群组基本信息以及微信号群组列表以供搜索
	getSearchDivData();
	// 预先加载10条商品信息
	addItems(pageSize, pageNum);

	// 获取本群组信息
	function getSearchDivData() {
		var url = searchDivUrl;
		$.getJSON(
						url,
						function(data) {
							if (data.success) {
								var wxManage = data.wxManage;
								$('#wxmanage-cover-pic').attr('src', getContextPath()+wxManage.wxManageImg);
								$('#wxmanage-update-time').html(
										new Date(wxManage.updateTime)
												.Format("yyyy-MM-dd"));
								$('#wxmanage-name').html(wxManage.wxManageName);
								$('#wxmanage-desc').html(wxManage.wxManageDesc);
							}
						});
	}
	/**
	 * 获取分页展示的商品列表信息
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @returns
	 */
	function addItems(pageSize, pageIndex) {
		// 拼接出查询的URL，赋空值默认就去掉这个条件的限制，有值就代表按这个条件去查询
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
				+ pageSize + '&wxName=' + wxName + '&wxManageId=' + wxManageId;
		// 设定加载符，若还在后台取数据则不能再次访问后台，避免多次重复加载
		loading = true;
		// 访问后台获取相应查询条件下的微信号列表
		$.getJSON(url, function(data) {
			if (data.success) {
				// 获取当前查询条件下微信号的总数
				maxItems = data.count;
				var html = '';
				// 遍历微信列表，拼接出卡片集合
				data.wxList.map(function(item, index) {
					html += '' + '<div class="card" data-wx-id='
							+ item.wxId + '>'
							+ '<div class="card-header">' + item.wxName
							+ '</div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ getContextPath()+item.wxImg + '" width="44"/>' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.wxDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.updateTime).Format("yyyy-MM-dd")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
				// 将卡片集合添加到目标HTML组件里
				$('.list-div').append(html);
				// 获取目前为止已显示的卡片总数，包含之前已经加载的
				var total = $('.list-div .card').length;
				// 若总数达到跟按照此查询条件列出来的总数一致，则停止后台的加载
				if (total >= maxItems) {
					// 隐藏提示符
					$('.infinite-scroll-preloader').hide();
				} else {
					$('.infinite-scroll-preloader').show();
				}
				// 否则页码加1，继续load出新的微信号
				pageNum += 1;
				// 加载结束，可以再次加载了
				loading = false;
				// 刷新页面，显示新加载的微信号
				$.refreshScroller();
			}
		});
	}

	// 下滑屏幕自动进行分页搜索
	$(document).on('infinite', '.infinite-scroll-bottom', function() {
		if (loading)
			return;
		addItems(pageSize, pageNum);
	});
	// 点击微信号的卡片进入该微信号的详情页
	$('.list-div').on(
			'click',
			'.card',
			function(e) {
				var wxId = e.currentTarget.dataset.wxId;
				window.location.href = '/wxht/frontend/wxdetail?wxId='
						+ wxId;
			});
	// 需要的微信号名字发生变化后，重置页码，清空原先的列表，按照新的名字去查询
	$('#search').on('change', function(e) {
		wxName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize, pageNum);
	});

});
