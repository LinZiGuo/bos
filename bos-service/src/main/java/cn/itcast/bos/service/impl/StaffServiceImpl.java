package cn.itcast.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.IStaffDao;
import cn.itcast.bos.domain.Staff;
import cn.itcast.bos.service.IStaffService;
@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

	@Autowired
	private IStaffDao staffDao;
	
	//添加取派员
	public void save(Staff model) {
		staffDao.save(model);
	}

}
