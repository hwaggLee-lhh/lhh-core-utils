//验证

/**
 * 验证是否邮箱格式
 * @param str
 * @returns
 */
function isEmail(str){ 
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
	return reg.test(str); 
}
