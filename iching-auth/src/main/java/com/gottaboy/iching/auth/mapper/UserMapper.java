package com.gottaboy.iching.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gottaboy.iching.common.entity.system.SystemUser;

/**
 * @author gottaboy
 */
public interface UserMapper extends BaseMapper<SystemUser> {

    SystemUser findByName(String username);
}
