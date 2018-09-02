package cn.itcast.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Function;
import cn.itcast.bos.service.IFunctionService;
import cn.itcast.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
	@Autowired
	private IFunctionService functionService;
	
	/**
	 * 查询所有的权限数据，返回json数据
	 * @return
	 */
	public String listajax() {
		List<Function> list = functionService.findAll();
		this.WriteObject2Json(list, new String[] {"parentFunction","roles"});
		return NONE;
	}
	
	/**
	 * 添加权限
	 * @return
	 */
	public String add() {
		functionService.save(model);
		return LIST;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public String pageQuery() {
		//pageBean的page属性与model的page属性重名，优先封装到model中
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.valueOf(page));
		functionService.pageQuery(pageBean);
		this.WriteObject2Json(pageBean, new String[] {"currentPage","pageSize","detachedCriteria","parentFunction","children","roles"});
		return NONE;
	}
	
	/**
	 * 根据登录人查询对应的菜单，返回json
	 * @return
	 */
	public String findMenu() {
		List<Function> list = functionService.findMenu();
		this.WriteObject2Json(list, new String[] {"parentFunction","children","roles"});
		return NONE;
	}
}
