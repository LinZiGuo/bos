package cn.itcast.bos.service;

import cn.itcast.bos.domain.Subarea;
import cn.itcast.bos.utils.PageBean;

public interface ISubareaService {

	public void save(Subarea model);

	public void pageQuery(PageBean pageBean);

}
