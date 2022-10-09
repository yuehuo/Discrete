package com.qianfeng.controller;

import com.qianfeng.pojo.User;
import com.qianfeng.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class PasswordController {
    @Resource
    private UserService userService;
    @RequestMapping("/updatepassWord")
    @ResponseBody  //返回json数据
    public String updatePassWord(HttpServletRequest req, HttpServletResponse resp, Model model) throws ServletException, IOException {

        //取数据
        String oldPassword = req.getParameter("oldPassword");//旧密码
        String passWordOne = req.getParameter("newPassword");//新密码
        //获取当前登录用户
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");

        //判断用户是否可以修改密码
        user = userService.changePassword(user, oldPassword, passWordOne) ;
        //3.返回结果
        if (user.isBool()) {
            user.setUserpassword(passWordOne);
            //将用户的名字放到session对象里面
            //model.addAttribute("activeUser", user);
            session.setAttribute("activeUser", user);
            //req.getRequestDispatcher("welcome.html").forward(req, resp);
            resp.sendRedirect("password.html");
            return "ok";
        } else{
            resp.sendRedirect("password.html");
            return "no";
        }

    }
}
