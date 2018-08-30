Date.prototype.Format = function(fmt) {
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
}
// 点击更换验证码
function changeVerifyCode(img) {
 // 四位随机数
  img.src = "../Kaptcha?" + Math.floor(Math.random() * 100);
}

//判空
function isNull(str){
  if ( str == "" ) return true;
  var regu = "^[ ]+$";
  var re = new RegExp(regu);
  return re.test(str);
}
/**
 * 取uri带参数的值
 * @param name
 * @returns {*}
 */
function  getQueryString(name) {
  var reg = new RegExp("(^|&)"+name+"=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if(r!=null){
    return decodeURIComponent(r[2]);
  }
  return '';
}
function getContextPath() {
  return "/wxht";
}
