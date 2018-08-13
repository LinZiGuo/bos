package cn.itcast.bos.web.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.domain.User;
import cn.itcast.bos.service.IUserService;
import cn.itcast.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
	//属性驱动，接收页面输入的验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 用户登录
	 * @return
	 */
	public String login() {
		//从session中获取生成的验证码
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//校验验证码是否输入正确
		if (StringUtils.isNotBlank(checkcode) && checkcode.equals(validatecode)) {
			//输入验证码正确
			User user = userService.login(model);
			if (user != null) {
				//登录成功，将user对象放入session，跳转到首页
				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				return HOME;
			} else {
				//登录失败，设置提示信息，跳转到首页
				//输入的验证码有错误，设置提示信息，跳转到登录页面
				this.addActionError("用户名或密码输入错误！");
				return LOGIN;
			}
		} else {
			//输入验证码错误，设置提示信息，跳转到登录页面
			this.addActionError("输入的验证码错误！");
			return LOGIN;
		}
	}
	
	/**
	 * 用户注销
	 * @return
	 */
	public String logout() {
		//销毁session
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
}
