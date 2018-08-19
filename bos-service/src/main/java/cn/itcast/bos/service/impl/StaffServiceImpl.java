package cn.itcast.bos.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IStaffDao;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.service.IStaffService;
import cn.itcast.bos.utils.PageBean;
@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

	@Autowired
	private IStaffDao staffDao;
	
	/**
	 * 添加派送员
	 */
	public void save(Staff model) {
		staffDao.save(model);
	}

	/**
	 * 分页查询
	 */
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}

	/**
	 * 批量删除派送员
	 */
	public void batch(String ids, String type) {
		if (StringUtils.isNotBlank(ids)) {
			String[] staffIds = ids.split(",");
			for (String id : staffIds) {
				staffDao.executeUpdate(type, id);
			}
		}
	}

	/**
	 * 修改取派员
	 */
	public void update(Staff staff) {
		staffDao.update(staff);
	}

	/**
	 * 根据ID查询取派员
	 */
	public Staff findById(String id) {
		return staffDao.findById(id);
	}
}
