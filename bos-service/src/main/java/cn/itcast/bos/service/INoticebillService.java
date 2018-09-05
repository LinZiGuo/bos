package cn.itcast.bos.service;

import cn.itcast.bos.domain.Noticebill;
import cn.itcast.bos.utils.PageBean;

public interface INoticebillService {
	public void add(Noticebill model);

	public void pageQuery(PageBean pageBean);

	public Noticebill findById(String id);

	public void update(Noticebill noticebill);
}
