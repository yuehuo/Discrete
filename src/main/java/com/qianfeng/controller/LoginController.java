package com.qianfeng.controller;

import com.qianfeng.pojo.User;
import com.qianfeng.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Resource
    private UserService userService;
    @RequestMapping("/loginuser")
    public String loginUser(HttpServletRequest req, Model model) {
        //1.取数据
        String name = req.getParameter("username");
        String pwd = req.getParameter("password");
        String code = req.getParameter("code");//获取前端验证码文本框里面的内容
        //***************验证码******************
        HttpSession session = req.getSession();
        String c = (String) session.getAttribute("CODE_SESSION");
        //验证码对比
        if (c.equals(code)) {
            //**************登陆逻辑******************
            //2.操作数据
            User user = new User();
            user.setUsercode(name);
            user.setUserpassword(pwd);
            user = userService.loginUser(user);
            //3.返回结果
            if (user.isBool()) {
                //将用户的名字放到session对象里面
                session.setAttribute("activeUser", user);
                //model.addAttribute("activeUser", user);
                return "index";
            } else
                return "login";

        } else
            return "login";
    }

}
