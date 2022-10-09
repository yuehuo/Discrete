package com.qianfeng.controller;

import com.qianfeng.pojo.User;
import com.qianfeng.service.UserService;
import com.qianfeng.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/list")
    @ResponseBody  //返回json数据
    public ResultVo list(User user) {

        ResultVo resultVo = userService.getUserListByPage(user);
        return resultVo;
    }


    /**
     * 添加用户
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResultVo add(HttpServletRequest req) throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//年月日时分秒毫秒
        //1.获取数据
        String usercode = req.getParameter("usercode");
        String userpassword = req.getParameter("userpassword");
        String username = req.getParameter("username");
        int gender = Integer.parseInt(req.getParameter("gender"));
        Date birthday = sdf1.parse(req.getParameter("birthday"));
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        int userrole = Integer.parseInt(req.getParameter("userrole"));
        String profile = req.getParameter("profile");
        Date creationdate = date;
        //2.操作数据
        //判断确认密码是否一致并符号要求长度
        System.out.println(userpassword.length());
        if (userpassword.length() >= 6 && userpassword.length() <= 20) {
            User user = new User(usercode, username, userpassword, gender, birthday,
                    phone, address, profile, userrole, creationdate);
            ResultVo resultVo = userService.add(user);
            return resultVo;
        } else {
            return ResultVo.getFailVo();
        }

    }


    /**
     * 接受前端传递的文件，并且上传文件，而且返回文件在服务器存储的相对路径
     */
    @RequestMapping("/upload")
    @ResponseBody  //返回json数据
    public ResultVo upload(MultipartFile uploadFile, String parentName, HttpServletRequest request) {
        return userService.upload(uploadFile, parentName, request);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public ResultVo deleteBatch(Integer[] ids) {

        return userService.deleteBatch(ids);
    }

    /**
     * 删除当前行
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public ResultVo deleteUser(int id) {

        return userService.deleteUser(id);
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public ResultVo updateUser(HttpServletRequest req) throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//年月日时分秒毫秒
        //1.获取数据
        int id = Integer.parseInt(req.getParameter("id"));
        String usercode = req.getParameter("usercode");
        String userpassword = req.getParameter("userpassword");
        String username = req.getParameter("username");
        int gender = Integer.parseInt(req.getParameter("gender"));
        Date birthday = sdf1.parse(req.getParameter("birthday"));
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        int userrole = Integer.parseInt(req.getParameter("userrole"));
        String profile = req.getParameter("profile");
        Date creationdate = simpleDateFormat.parse(req.getParameter("creationdate"));
        //2.操作数据
        //判断确认密码是否一致并符号要求长度
        System.out.println(userpassword.length());
        if (userpassword.length() >= 6 && userpassword.length() <= 20) {
            User user = new User(id, usercode, username, userpassword, gender, birthday,
                    phone, address, profile, userrole, creationdate);
            ResultVo resultVo = userService.updateByPrimaryKey(user);
            return resultVo;
        } else {
            return ResultVo.getFailVo();
        }
    }

}
