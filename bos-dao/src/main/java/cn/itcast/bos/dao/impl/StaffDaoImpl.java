package cn.itcast.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.itcast.bos.dao.IStaffDao;
import cn.itcast.bos.dao.base.impl.BaseDaoImpl;
import cn.itcast.bos.domain.Staff;
@Repository
public class StaffDaoImpl extends BaseDaoImpl<Staff> implements IStaffDao {

}
