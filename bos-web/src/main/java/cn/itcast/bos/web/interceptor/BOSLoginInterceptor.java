package cn.itcast.bos.web.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

import cn.itcast.bos.domain.User;
import cn.itcast.bos.utils.BOSUtils;
/**
 * 自定义的拦截器，实现用户未登录自动跳转到登录页面
 * @author 郭子灵
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor {

	//拦截方法
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		// 从session中获取用户对象
		User user = BOSUtils.getLoginUser();
		//没有登录，跳转到登录页面
		if (user == null) {
			return "login";
		}
		//放行
		return invocation.invoke();
	}

}
