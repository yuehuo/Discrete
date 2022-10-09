package com.qianfeng.service;

import com.qianfeng.dao.UserMapper;
import com.qianfeng.pojo.Role;
import com.qianfeng.pojo.User;
import com.qianfeng.util.FileUploadUtil;
import com.qianfeng.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleService roleService;
    @Override
    public ResultVo getUserList() {
        try {
            //查询所有用户
            List<User> users = userMapper.selectAll();
            List<User> userList = new ArrayList<User>();;
            for(User user: users) {
                //查找用户角色
                Role role = roleService.findModelById(user.getUserrole());
                System.out.println(role);
                user.setRoleName(role.getRolename());
                //换算性别

                if (user.getGender() == 1)
                    user.setSex("女");
                else
                    user.setSex("男");
                //根据出生日期更新用户年龄

                int age = UserMapper.getAge(user.getBirthday());
                if (user.getAge() != age){
                    user.setAge(age);
                    userMapper.updateByPrimaryKey(user);
                }
                userList.add(user);
            }
            ResultVo successVo = ResultVo.getSuccessVo(userList);
            //长度单独赋值，因为增删改时候是不需要给长度赋值(长度只有查询分页时候需要)
            successVo.setCount(userList.size());
            return successVo;
        }catch (Exception e){
            e.printStackTrace();
            ResultVo failVo = ResultVo.getFailVo();
            return failVo;
        }
    }

    @Override
    public ResultVo getUserListByPage(User u) {
        //分页查询的起始位置
        u.setPage((u.getPage()-1)*u.getLimit());
        try {
            //分页查询
            List<User> userList = userMapper.getUserListByPage(u);
            List<User> users = new ArrayList<User>();
            for(User user: userList) {
                //查找用户角色
                Role role = roleService.findModelById(user.getUserrole());
                System.out.println(role);
                user.setRoleName(role.getRolename());
                //换算性别

                if (user.getGender() == 1)
                    user.setSex("女");
                else
                    user.setSex("男");
                //根据出生日期更新用户年龄

                int age = UserMapper.getAge(user.getBirthday());
                if (user.getAge() != age){
                    user.setAge(age);
                    userMapper.updateByPrimaryKey(user);
                }
                users.add(user);
            }
            //查询数据总条数
            Integer totalSize = userMapper.selectCount(u);
            //Integer totalSize = users.size();
            ResultVo successVo = ResultVo.getSuccessVo(users);
            //分页时必须传递总数量，layui才能自动计算到底多少页
            successVo.setCount(totalSize);
            return successVo;
        }catch (Exception e){
            e.printStackTrace();
            ResultVo failVo = ResultVo.getFailVo();
            return failVo;
        }
    }

    @Override
    public ResultVo upload(MultipartFile uploadFile, String parentName, HttpServletRequest request) {
        try{
            String filePath = FileUploadUtil.uploadFile(uploadFile, parentName, request);
            ResultVo successVo = ResultVo.getSuccessVo(filePath);
            return successVo;
        }catch (Exception e){
            e.printStackTrace();
            ResultVo failVo = ResultVo.getFailVo();
            return failVo;
        }
    }

    @Override
    public ResultVo updateByPrimaryKey(User user) {
        try {
            int age = UserMapper.getAge(user.getBirthday());
            user.setAge(age);
            userMapper.updateByPrimaryKey(user);
            return ResultVo.getSuccessVo("更新成功");
        }catch (Exception e) {
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public ResultVo add(User user) {
        try{
            User u = userMapper.selectByUsercode(user);
            //判断用户编码重不重复
            if(u == null){
                //计算年龄
                int age = UserMapper.getAge(user.getBirthday());
                user.setAge(age);
                userMapper.insert(user);
                return ResultVo.getSuccessVo("添加成功");
            }else{
                return ResultVo.getFailVo();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }
    @Override
    public ResultVo deleteBatch(Integer[] ids) {
        try{
            //1. 批量删除用户的sql  :  [1,2,3]   delete from tb_album where al_id in (1,2,3);
            userMapper.deleteBatch(ids);
            return ResultVo.getSuccessVo("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultVo.getFailVo();
        }

    }

    @Override
    public ResultVo deleteUser(int id) {
        try{
            userMapper.deleteByPrimaryKey(id);
            return ResultVo.getSuccessVo("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public User fingByid(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user.getGender() == 1)
            user.setSex("女");
        else
            user.setSex("男");
        return user;
    }

    @Override
    public User loginUser(User user) {
        User u = userMapper.selectByUsercode(user);
        if (u == null){
            u.setBool(false);
            return u;
        }
        else {
            //判断数据库里面存储的密码是否为前端输入的密码
            if (u.getUserpassword().equals(user.getUserpassword())){
                u.setBool(true);
            }
            else{
                u.setBool(false);
            }
            return u;
        }
    }
    //修改密码
    @Override
    public User changePassword(User user, String oldPassword, String passWordOne) {
        //与数据库是否匹配
        User u = userMapper.selectByPrimaryKey(user.getId());
        if(u.getUserpassword().equals(oldPassword)){
            //可以修改
            u.setUserpassword(passWordOne);
            u.setBool(true);
            userMapper.updateByPrimaryKey_pass(u);
        }else{
            //与数据库密码不一样
            u.setBool(false);
        }
        return u;
    }
}
