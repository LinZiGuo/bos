package cn.itcast.bos.service;

import cn.itcast.bos.domain.Decidedzone;
import cn.itcast.bos.utils.PageBean;

public interface IDecidedzoneService {

	public void pageQuery(PageBean pageBean);

	public void save(Decidedzone model, String[] subareaid);

	public Decidedzone findById(String id);

	public void update(Decidedzone decidedzone);

	public void batch(String ids);

}
