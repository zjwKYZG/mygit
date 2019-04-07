package com.bootdo.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootdo.dao.SysRoleDao;
import com.bootdo.domain.SysRoleDO;
import com.bootdo.service.ISysRoleService;

/**
 * 
 * @author created by zjw on 2019年1月29日 下午5:40:21
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {
	
	@Autowired
	private SysRoleDao sysRoleDao;

	@Override
	public List<SysRoleDO> findListBySysUserId(Long sysUserId) {
		
		if (Objects.isNull(sysUserId) || sysUserId.longValue() == 0L) {
			return null;
		}
		return sysRoleDao.findListBySysUserId(sysUserId);
	}
}