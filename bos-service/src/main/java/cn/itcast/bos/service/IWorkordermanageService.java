package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.Workordermanage;
import cn.itcast.bos.utils.PageBean;

public interface IWorkordermanageService {

	public void save(Workordermanage model);

	public void pageQuery(PageBean pageBean);

	public void saveBatch(List<Workordermanage> list);

}
