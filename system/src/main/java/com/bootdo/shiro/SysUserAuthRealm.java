package com.bootdo.shiro;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.bootdo.constant.AdminConstant;
import com.bootdo.constant.ShiroConstant;
import com.bootdo.domain.SysMenuDO;
import com.bootdo.domain.SysRoleDO;
import com.bootdo.domain.SysUserDO;
import com.bootdo.enums.ResponseEnum;
import com.bootdo.enums.StatusEnum;
import com.bootdo.exception.ResultException;
import com.bootdo.service.ISysMenuService;
import com.bootdo.service.ISysRoleService;
import com.bootdo.service.ISysUserService;
import com.bootdo.utils.CollectionUtils;
import com.bootdo.utils.StringUtils;

/**
 * 自定义用户Realm：在Shiro中，最终是通过Realm来获取应用程序中的用户、角色及权限信息的，
 * 在Realm中会直接从我们的数据源中获取Shiro需要的验证信息，也可以说，Realm是专用于安全框架的DAO
 * 
 * @author created by zjw on 2019年1月21日 下午10:25:35
 */
public class SysUserAuthRealm extends AuthorizingRealm {

	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysMenuService sysMenuService;

	/**
	 * 用户授权：授权的方法是在遇到@RequiresPermissions("index:home:page")注解的时候调用的
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获取用户Principal对象
		SysUserDO sysUser = (SysUserDO) principal.getPrimaryPrincipal();
		if (Objects.isNull(sysUser)) {
			throw new ResultException(ResponseEnum.SERVER_INTERNAL_ERROR);
		}

		// isAdmin（1：管理员；0：非管理员）
		// 管理员拥有所有权限
		if (Objects.nonNull(sysUser.getIsAdmin()) && Objects.equals(AdminConstant.IS_ADMIN, sysUser.getIsAdmin())) {
			info.addRole(AdminConstant.ADMIN_ROLE_NAME);
			info.addStringPermission(ShiroConstant.ALL_PERMISSION);
			return info;
		}

		// 根据用户ID查询该用户角色列表
		List<SysRoleDO> sysRoles = sysRoleService.findListBySysUserId(sysUser.getId());
		if (CollectionUtils.isNotEmpty(sysRoles)) {
			// 赋予该用户下的角色资源权限授权
			List<String> sysRoleNames = sysRoles.stream().map(SysRoleDO::getRoleName).collect(toList());
			info.addRoles(sysRoleNames);

			List<Long> sysRoleIds = sysRoles.stream().map(SysRoleDO::getId).collect(toList());
			List<SysMenuDO> sysMenus = sysMenuService.findListBySysRoleIds(sysRoleIds);
			if (CollectionUtils.isNotEmpty(sysMenus)) {
				List<String> authCodes = sysMenus.stream()
						.filter(s -> StringUtils.isNotBlank(s.getAuthCode())
								&& Objects.equals(StatusEnum.OK.getCode(), s.getStatus()))
						.map(SysMenuDO::getAuthCode).collect(toList());
				info.addStringPermissions(authCodes);
			}
		}
		return info;
	}

	/**
	 * 用户身份验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();

		Map<String, Object> params = new HashMap<>(1);
		params.put("username", username);
		String password = new String((char[]) token.getCredentials());

		List<SysUserDO> sysUsers = sysUserService.findListByMap(params);
		if (CollectionUtils.isEmpty(sysUsers)) {
			throw new ResultException(ResponseEnum.USER_NAME_PWD_ERROR);
		}
		SysUserDO sysUser = sysUsers.get(0);
		if (!Objects.equals(password, sysUser.getPassword())) {
			throw new ResultException(ResponseEnum.USER_NAME_PWD_ERROR);
		}

		// status：状态（0：正常；1：冻结；-1：删除）
		if (Objects.nonNull(sysUser.getStatus()) && sysUser.getStatus().byteValue() != 0) {
			throw new ResultException(ResponseEnum.USER_NAME_LOCKED);
		}
		return new SimpleAuthenticationInfo(sysUser, password, super.getName());
	}

	/**
	 * 重写方法：清除当前用户的授权缓存
	 * 
	 * @param principals
	 */
	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 重写方法：清除当前用户的身份验证缓存
	 * 
	 * @param principals
	 */
	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	/**
	 * 重写方法：清除当前用户的标识/标识关联的任何缓存数据
	 */
	@Override
	protected void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	/**
	 * 自定义方法：清除所有用户的授权缓存
	 */
	public void clearAllCachedAuthorizationInfo() {
		super.getAuthorizationCache().clear();
	}

	/**
	 * 自定义方法：清除所有用户的身份验证缓存
	 */

	public void clearAllCachedAuthenticationInfo() {
		super.getAuthenticationCache().clear();
	}

	/**
	 * 自定义方法：清除所有用户的授权缓存和身份验证缓存
	 */
	public void clearAllCache() {
		this.clearAllCachedAuthorizationInfo();
		this.clearAllCachedAuthenticationInfo();
	}
}