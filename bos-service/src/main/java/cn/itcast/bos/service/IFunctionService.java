package cn.itcast.bos.service;

import java.util.List;

import cn.itcast.bos.domain.Function;
import cn.itcast.bos.utils.PageBean;

public interface IFunctionService {

	public List<Function> findAll();

	public void save(Function model);

	public void pageQuery(PageBean pageBean);

	public List<Function> findMenu();

	public void batch(String ids);

}
