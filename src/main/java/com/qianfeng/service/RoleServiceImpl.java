package com.qianfeng.service;

import com.qianfeng.dao.RoleMapper;
import com.qianfeng.pojo.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
    @Resource
    private RoleMapper RoleMapper;

    @Override
    public Role findModelById(int id) {
        return RoleMapper.selectByPrimaryKey(id);
    }
}
