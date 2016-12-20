package com.lhh;

/**
 * 错误信息提示
 * @author hwaggLee
 * @createDate 2016年12月19日
 */
public enum LhhUtilsErrorMsg {

	e_0001("非法请求");
	
	private String errormsg;
	private LhhUtilsErrorMsg(String errormsg){
		this.errormsg = errormsg;
	}
	public String getErrormsg(){
		return errormsg;
	}
}
