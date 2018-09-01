package cn.itcast.bos.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.dao.IUserDao;
import cn.itcast.bos.domain.User;

public class BOSRealm extends AuthorizingRealm{
	@Autowired
	private IUserDao userDao;
	
	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken passwordToken = (UsernamePasswordToken)token;
		//获得页面输入的用户名
		String username = passwordToken.getUsername();
		//根据用户名查询数据库中的密码
		User user = userDao.findUserByUsername(username);
		if(user == null){
			//页面输入的用户名不存在
			return null;
		}
		//简单认证信息对象
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		//框架负责比对数据库中的密码和页面输入的密码是否一致
		return info;
	}

	//授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//为用户授权
		info.addStringPermission("staff-list");
		
		//TODO 后期需要修改为根据当前登录用户查询数据库，获取实际对应的权限
		User user1 = (User) SecurityUtils.getSubject().getPrincipal();
		User user2 = (User) principals.getPrimaryPrincipal();
		return info;
	}
}
