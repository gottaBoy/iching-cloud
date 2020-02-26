package com.gottaboy.iching.auth.manager;

import com.gottaboy.iching.auth.mapper.MenuMapper;
import com.gottaboy.iching.auth.mapper.UserMapper;
import com.gottaboy.iching.auth.mapper.UserRoleMapper;
import com.gottaboy.iching.common.entity.constant.IchingConstant;
import com.gottaboy.iching.common.entity.system.Menu;
import com.gottaboy.iching.common.entity.system.SystemUser;
import com.gottaboy.iching.common.entity.system.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户业务逻辑
 *
 * @author gottaoby
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserManager {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户
     */
    public SystemUser findByName(String username) {
        return userMapper.findByName(username);
    }

    /**
     * 通过用户名查询用户权限串
     *
     * @param username 用户名
     * @return 权限
     */
    public String findUserPermissions(String username) {
        List<Menu> userPermissions = menuMapper.findUserPermissions(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.joining(","));
    }

    /**
     * 注册用户
     *
     * @param username username
     * @param password password
     * @return SystemUser SystemUser
     */
    @Transactional
    public SystemUser registUser(String username,String password) {
        SystemUser systemUser = new SystemUser();
        systemUser.setUsername(username);
        systemUser.setPassword(password);
        systemUser.setCreateTime(new Date());
        systemUser.setStatus(SystemUser.STATUS_VALID);
        systemUser.setSex(SystemUser.SEX_UNKNOW);
        systemUser.setAvatar(SystemUser.DEFAULT_AVATAR);
        systemUser.setDescription("注册用户");
        this.userMapper.insert(systemUser);

        UserRole userRole = new UserRole();
        userRole.setUserId(systemUser.getUserId());
        userRole.setRoleId(IchingConstant.REGISTER_ROLE_ID); // 注册用户角色 ID
        this.userRoleMapper.insert(userRole);
        return systemUser;
    }
}