package cn.itcast.bos.web.action;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.service.IDecidedzoneService;
import cn.itcast.bos.web.action.base.BaseAction;
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	@Autowired
	private IDecidedzoneService decidedzoneService;
	private String[] subareaid;
	private String ids;
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}

	private String query;
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * 添加定区
	 * @return
	 */
	public String add() {
		Decidedzone decidedzone = decidedzoneService.findById(model.getId());
		if (decidedzone != null) {
			decidedzone.setName(model.getName());
			decidedzone.setStaff(model.getStaff());
			decidedzone.setSubareas(model.getSubareas());
			decidedzoneService.update(decidedzone);
		} else {
			decidedzoneService.save(model,subareaid);
		}
		return LIST;
	}
	
	/**
	 * 分页查询
	 * @return
	 */
	public String pageQuery() {
		DetachedCriteria dc = pageBean.getDetachedCriteria();
		//动态添加过滤条件
		String id = model.getId();
		Staff staff = model.getStaff();
		if (StringUtils.isNotBlank(id)) {
			//添加过滤条件，根据定区编码进行模糊查询
			dc.add(Restrictions.like("id", "%"+id+"%"));
		}
		if (staff != null) {
			String station = staff.getStation();
			dc.createAlias("staff", "s");
			if (StringUtils.isNotBlank(station)) {
				//添加过滤条件，根据定区编码进行模糊查询
				dc.add(Restrictions.like("s.station", "%"+station+"%"));
			}
		}
		decidedzoneService.pageQuery(pageBean);
		WriteObject2Json(pageBean, new String[] {"currentPage","pageSize","detachedCriteria","subareas","decidedzones"});
		return NONE;
	}
	
	/**
	 * 批量删除定区
	 * @return
	 */
	public String delete() {
		decidedzoneService.batch(ids);
		return LIST;
	}
}
