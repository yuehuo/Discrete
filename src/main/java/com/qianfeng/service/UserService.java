package com.qianfeng.service;

import com.qianfeng.pojo.User;
import com.qianfeng.vo.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    /**
     * 查询所有的用户
     * @return
     */
    ResultVo getUserList();

    /**
     * 分页查询用户列表
     * page:页数
     * limit:条数
     * @return
     */
    ResultVo getUserListByPage(User user);

    /**
     * 上传文件并返回文件相对路径的方法
     * @param uploadFile
     * @param parentName
     * @param request
     * @return
     */
    ResultVo upload(MultipartFile uploadFile, String parentName, HttpServletRequest request);

    /**
     * 根据id更新用户数据
     * @param user
     */
    ResultVo updateByPrimaryKey(User user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    ResultVo add(User user);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    ResultVo deleteBatch(Integer[] ids);

    /**
     * 删除当前行
     * @param id
     * @return
     */
    ResultVo deleteUser(int id);

    /**
     * 根据id查找用户数据
     * @param id
     * @return
     */
    User fingByid(int id);

    /**
     * 登录验证
     * @param user
     * @return
     */
    public User loginUser(User user);

    User changePassword(User user, String oldPassword, String passWordOne);
}
