package cn.itcast.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.Noticebill;
import cn.itcast.bos.service.INoticebillService;
import cn.itcast.bos.web.action.base.BaseAction;
import cn.itcast.crm.Customer;
import cn.itcast.crm.ICustomerService;
/**
 * 业务通知单管理
 * @author 郭子灵
 *
 */
@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {
	//注入crm客户端代理对象
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private INoticebillService noticebillService;
	
	/**
	 * 远程调用crm服务，根据手机号查询客户信息
	 */
	public String findCustomerByTelephone() {
		String telephone = model.getTelephone();
		Customer customer = customerService.findCustomerByTelephone(telephone);
		this.WriteObject2Json(customer, new String[] {});
		return NONE;
	}
	
	/**
	 * 保存一个业务通知单，并尝试自动分单
	 */
	public String add(){
		noticebillService.add(model);
		return "noticebill_add";
	}
}
