package cn.itcast.bos.service;

import java.util.List;
import java.util.Map;

import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.utils.PageBean;

public interface IStaffService {

	public void save(Staff model);

	public void pageQuery(PageBean pageBean);

	public void batch(String ids, String type);

	public void update(Staff staff);

	public Staff findById(String id);
}
