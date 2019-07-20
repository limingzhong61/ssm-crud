package com.atguigu.crud.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用的返回类
 * @author lmz
 *
 */
public class Msg {
	//状态码 100-success 200-fail
	private int code;
	//提示prompt information
	private String msg;
	//用户返回给浏览器的数据
	private Map<String, Object> extend = new HashMap<String, Object>();
	
	public static Msg success() {
		Msg msg = new Msg();
		msg.setCode(100);
		msg.setMsg("处理成功!");
		return  msg;
	}
	
	public static Msg fail() {
		Msg msg = new Msg();
		msg.setCode(200);
		msg.setMsg("处理失败!");
		return  msg;
	}
	
	public Msg add(String key,Object value) {
		this.getExtend().put(key, value);
		return this;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getExtend() {
		return extend;
	}
	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
	
}
