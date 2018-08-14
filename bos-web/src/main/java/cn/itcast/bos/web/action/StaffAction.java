package cn.itcast.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.service.IStaffService;
import cn.itcast.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	@Autowired
	private IStaffService staffService;
	
	/**
	 * 添加取派员
	 * @return
	 */
	public String add() {
		staffService.save(model);
		return LIST;
	}
}
