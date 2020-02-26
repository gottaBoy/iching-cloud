package com.gottaboy.iching.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gottaboy.iching.common.entity.system.Menu;

import java.util.List;

/**
 * @author gottaboy
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findUserPermissions(String username);
}