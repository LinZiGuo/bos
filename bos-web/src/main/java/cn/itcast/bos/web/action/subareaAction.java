package cn.itcast.bos.web.action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.service.ISubareaService;
import cn.itcast.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class subareaAction extends BaseAction<Subarea> {
	@Autowired
	private ISubareaService subareaService;
	
	/**
	 * 保存分区
	 * @return
	 */
	public String add() {
		subareaService.save(model);
		return LIST;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public String pageQuery() {
		subareaService.pageQuery(pageBean);
		WriteObject2Json(pageBean, new String[] {"decidedzone", "subareas"});
		return NONE;
	}
}
