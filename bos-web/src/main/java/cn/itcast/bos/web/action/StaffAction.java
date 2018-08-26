package cn.itcast.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.service.IStaffService;
import cn.itcast.bos.utils.PageBean;
import cn.itcast.bos.web.action.base.BaseAction;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	@Autowired
	private IStaffService staffService;
	//定义属性，接收分页参数
	private String ids;
	private String query;
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	/**
	 * 添加取派员
	 * @return
	 */
	public String add() {
		staffService.save(model);
		return LIST;
	}
	
	/**
	 * 分页查询
	 * @return
	 * @throws IOException 
	 */
	public String pageQuery() throws IOException {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		if (query != null) {
			String name = model.getName();
			String telephone = model.getTelephone();
			String station = model.getStation();
			String haspda = model.getHaspda();
			String standard = model.getStandard();
			if (StringUtils.isNotBlank(name)) {
				dc.add(Restrictions.like("name", "%"+name+"%"));
			}
			if (StringUtils.isNotBlank(telephone)) {
				dc.add(Restrictions.like("telephone", "%"+telephone+"%"));
			}
			if (StringUtils.isNotBlank(station)) {
				dc.add(Restrictions.like("station", "%"+station+"%"));
			}
			if (StringUtils.isNotBlank(haspda)) {
				dc.add(Restrictions.eq("haspda", haspda));
			}
			if (StringUtils.isNotBlank(standard)) {
				dc.add(Restrictions.like("standard", "%"+standard+"%"));
			}
		}
		staffService.pageQuery(pageBean);
		this.WriteObject2Json(pageBean, new String[] {"currentPage","pageSize","detachedCriteria","decidedzones"});
		return NONE;
	}
	
	/**
	 * 批量删除取派员
	 * @return
	 */
	public String delete() {
		staffService.batch(ids, "staff.delete");
		return LIST;
	}
	
	/**
	 * 批量还原取派员
	 * @return
	 */
	public String restore() {
		staffService.batch(ids, "staff.restore");
		return LIST;
	}
	
	/**
	 * 修改取派员
	 * @return
	 */
	public String edit() {
		//查询数据库原始数据
		String id = model.getId();
		Staff staff = staffService.findById(id);
		//根据页面提交的参数进行覆盖，参数已经封装到model对象
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStation(model.getStation());
		staff.setStandard(model.getStandard());
		//提交事务
		staffService.update(staff);
		return LIST;
	}
	
	/**
	 * 查询未删除的取派员，返回json
	 * @return
	 */
	public String listajax() {
		List<Staff> list = staffService.findListNotDelete();
		WriteObject2Json(list, new String[] {"decidedzones"});
		return NONE;
	}
}
