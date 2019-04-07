package com.bootdo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootdo.dao.SysMenuDao;
import com.bootdo.domain.SysMenuDO;
import com.bootdo.service.ISysMenuService;
import com.bootdo.utils.CollectionUtils;

/**
 * 
 * @author created by zjw on 2019年1月29日 下午9:59:21
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {
	
	@Autowired
	private SysMenuDao sysMenuDao;

	@Override
	public List<SysMenuDO> findListBySysRoleIds(List<Long> sysRoleIds) {
		
		if (CollectionUtils.isNotEmpty(sysRoleIds)) {
			return sysMenuDao.findListBySysRoleIds(sysRoleIds);
		}
		return null;
	}
}