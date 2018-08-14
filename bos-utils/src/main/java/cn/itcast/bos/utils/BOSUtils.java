package cn.itcast.bos.utils;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.itcast.bos.domain.User;
/**
 * BOS项目的工具类
 * @author 郭子灵
 *
 */

public class BOSUtils {
	//获取session对象
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	
	//获取登录用户对象
	public static User getLoginUser() {
		return (User) getSession().getAttribute("user");
	}
}
