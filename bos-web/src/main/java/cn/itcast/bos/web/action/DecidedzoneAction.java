package cn.itcast.bos.web.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.service.IDecidedzoneService;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.Customer;
import cn.itcast.crm.ICustomerService;
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {
	@Autowired
	private IDecidedzoneService decidedzoneService;
	//注入crm代理对象
	@Autowired
	private ICustomerService proxy;
	private String[] subareaid;
	private String ids;
	private List<Integer> customerIds;
	
	public List<Integer> getCustomerIds() {
		return customerIds;
	}

	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}

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
	@RequiresPermissions("decidedzone-add")
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
	@RequiresPermissions("decidedzone-list")
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
	@RequiresPermissions("decidedzone-delete")
	public String delete() {
		decidedzoneService.batch(ids);
		return LIST;
	}
	
	/**
	 * 远程调用crm服务，获取未关联到定区的客户
	 * @return
	 */
	public String findListNotAssociation() {
		List<Customer> list = proxy.findCustomerNotAssociation();
		this.WriteObject2Json(list, new String[] {});
		return NONE;
	}
	
	/**
	 * 远程调用crm服务，获取已经关联到指定的定区的客户
	 */
	public String findListHasAssociation(){
		String id = model.getId();
		List<Customer> list = proxy.findCustomerHasAssociation(id);
		this.WriteObject2Json(list, new String[]{});
		return NONE;
	}
	
	/**
	 * 远程调用crm服务，将客户关联到定区
	 */
	@RequiresPermissions("decidedzone-association")
	public String assigncustomerstodecidedzone(){
		proxy.assigncustomerstodecidedzone(customerIds, model.getId());
		return LIST;
	}
}
