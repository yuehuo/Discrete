package com.qianfeng.service;

import com.qianfeng.pojo.Role;

public interface RoleService {
    /**
     * 查询所有的用户
     * @return
     */
    Role findModelById(int id);
}
