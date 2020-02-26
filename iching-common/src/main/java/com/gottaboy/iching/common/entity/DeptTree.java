package com.gottaboy.iching.common.entity;

import com.gottaboy.iching.common.entity.system.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gottaboy
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends Tree<Dept>{

    private Integer orderNum;
}
