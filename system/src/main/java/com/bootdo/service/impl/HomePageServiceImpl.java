package com.bootdo.service.impl;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bootdo.constant.AdminConstant;
import com.bootdo.domain.SysMenuDO;
import com.bootdo.domain.SysRoleDO;
import com.bootdo.domain.SysUserDO;
import com.bootdo.enums.StatusEnum;
import com.bootdo.enums.SysMenuTypeEnum;
import com.bootdo.service.IHomePageService;
import com.bootdo.service.ISysMenuService;
import com.bootdo.service.ISysRoleService;
import com.bootdo.utils.CollectionUtils;
import com.bootdo.utils.ShiroUtils;

/**
 * @author created by zjw on 2019年1月30日 下午6:05:57
 */
@Service
public class HomePageServiceImpl implements IHomePageService {

	@Autowired
	private ISysMenuService sysMenuService;
	@Autowired
	private ISysRoleService sysRoleService;

	@Override
	public String mainContent(Model model) {

		// 获取当前登录的用户
		SysUserDO sysUser = ShiroUtils.getSysUser();

		// 菜单键值对（菜单ID：菜单）
		Map<Long, SysMenuDO> sysMenuMap = new HashMap<>();

		// 管理员实时更新菜单
		// isAdmin（1：管理员；0：非管理员）
		// 管理员拥有所有权限
		if (Objects.nonNull(sysUser.getIsAdmin()) && Objects.equals(AdminConstant.IS_ADMIN, sysUser.getIsAdmin())) {
			List<SysMenuDO> sysMenus = sysMenuService.findListBySysRoleIds(Arrays.asList(AdminConstant.ADMIN_ROLE_ID));
			if (CollectionUtils.isNotEmpty(sysMenus)) {
				sysMenuMap = sysMenus.stream().filter(
						s -> Objects.nonNull(s.getStatus()) && Objects.equals(StatusEnum.OK.getCode(), s.getStatus()))
						.collect(toMap(SysMenuDO::getId, Function.identity(), (v1, v2) -> v2));
			}
		} else {
			// 非管理员需从相应的角色中获取菜单资源
			// 根据用户ID查询该用户角色列表
			List<SysRoleDO> sysRoles = sysRoleService.findListBySysUserId(sysUser.getId());
			if (CollectionUtils.isNotEmpty(sysRoles)) {
				List<Long> sysRoleIds = sysRoles.stream().map(SysRoleDO::getId).collect(toList());
				List<SysMenuDO> sysMenus = sysMenuService.findListBySysRoleIds(sysRoleIds);
				if (CollectionUtils.isNotEmpty(sysMenus)) {
					sysMenuMap = sysMenus.stream()
							.filter(s -> Objects.nonNull(s.getStatus())
									&& Objects.equals(StatusEnum.OK.getCode(), s.getStatus()))
							.collect(toMap(SysMenuDO::getId, Function.identity(), (v1, v2) -> v2));
				}
			}
		}

		// 封装菜单树形数据
		Map<Long, SysMenuDO> treeSysMenuMap = new HashMap<>();
		Byte type;
		for (Entry<Long, SysMenuDO> e : sysMenuMap.entrySet()) {
			SysMenuDO s = e.getValue();
			type = s.getType();
			// 菜单
			if (Objects.nonNull(type) && !Objects.equals(SysMenuTypeEnum.BUTTON.getType(), type)) {
				SysMenuDO sysMenu = sysMenuMap.get(s.getPid());
				// 二级菜单
				if (Objects.nonNull(sysMenu)) {
					sysMenu.getChildren().put(Long.valueOf(s.getSort()), s);
				} else {
					// 一级菜单
					if (Objects.equals(SysMenuTypeEnum.TOP_LEVEL.getType(), type)) {
						treeSysMenuMap.put(Long.valueOf(s.getSort()), s);
					}
				}
			}
		}

		model.addAttribute("sysUser", sysUser);
		model.addAttribute("treeSysMenuMap", treeSysMenuMap);
		return "main";
	}
}