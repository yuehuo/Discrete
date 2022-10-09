package com.qianfeng.controller;

import com.qianfeng.pojo.Comment;
import com.qianfeng.pojo.Document;
import com.qianfeng.pojo.User;
import com.qianfeng.pojo.Video;
import com.qianfeng.service.CommentService;
import com.qianfeng.service.DocumentService;
import com.qianfeng.service.UserService;
import com.qianfeng.service.VideoService;
import com.qianfeng.vo.ResultVo;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 返回对应视图页面的controller
 */
@Controller
public class PageController {
    @Resource
    private UserService userService;
    @Resource
    private DocumentService documentService;
    @Resource
    private VideoService videoService;
    @Resource
    private CommentService commentService;
    /**
     * 返回index.html
     */
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
    /**
     * 返回register.html
     */
    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    /**
     * 返回login.html
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    /**
     * 返回welcome.html
     */
    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
    /**
     * 返回password.html
     * 修改当前用户密码
     */
    @RequestMapping("/password")
    public String password(){
        return "user/password";
    }

    /**
     * 返回编辑当前用户页面
     */
    @RequestMapping("/userdata")
    public String userdata(HttpServletRequest req, HttpServletResponse resp, Model model) throws ServletException, IOException {
        //获取当前登录用户
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");
        //将用户信息返回
        model.addAttribute("user", user);
        return "user/userdata";
    }

    /**
     * 返回documentuser.html
     * 管理当前用户文档
     */
    @RequestMapping("/documentuser")
    public String documentuser(){
        return "document/documentuser";
    }

    /**
     * 返回videouser.html
     * 管理当前用户视频
     */
    @RequestMapping("/videouser")
    public String videouser(){
        return "video/videouser";
    }

    /**
     * 返回commentuser.html
     * 管理当前用户评论
     */
    @RequestMapping("/commentuser")
    public String commentuser(){
        return "comment/commentuser";
    }

    /**
     * 返回userlist.html
     */
    @RequestMapping("/userlist")
    public String userlist(){
        return "user/userlist";
    }

    /**
     * 返回documentlist.html
     */
    @RequestMapping("/documentlist")
    public String documentlist(){
        return "document/documentlist";
    }

    /**
     * 返回videolist.html
     */
    @RequestMapping("/videolist")
    public String videolist(){
        return "video/videolist";
    }

    /**
     * 返回commentlist.html
     */
    @RequestMapping("/commentlist")
    public String commentlist(){
        return "comment/commentlist";
    }

    /**
     * 返回添加用户页面
     */
    @RequestMapping("/add_user")
    public String addUser(){
        return "user/add_user";
    }

    /**
     * 返回上传文档页面
     */
    @RequestMapping("/add_document")
    public String addDocument(){
        return "document/add_document";
    }

    /**
     * 返回上传视频页面
     */
    @RequestMapping("/add_video")
    public String addVideo(){
        return "video/add_video";
    }

    /**
     * 返回发表评论页面
     */
    @RequestMapping("/add_comment")
    public String addComment(){
        return "comment/add_comment";
    }

    /**
     * 返回编辑用户页面
     */
    @RequestMapping("/update_user")
    public String updateUser(int id, Model model) {
        User user = userService.fingByid(id);
        model.addAttribute("user", user);
        return "user/update_user";
    }

    /**
     * 返回编辑文档页面
     */
    @RequestMapping("/update_document")
    public String updateDocument(int id, Model model) {
        Document document = documentService.fingByid(id);
        model.addAttribute("document", document);
        return "document/update_document";
    }

    /**
     * 返回编辑视频页面
     */
    @RequestMapping("/update_video")
    public String updateVideo(int id, Model model) {
        Video video = videoService.fingByid(id);
        model.addAttribute("video", video);
        return "video/update_video";
    }

    /**
     * 返回编辑视频页面
     */
    @RequestMapping("/update_comment")
    public String updateComment(int id, Model model) {
        Comment comment = commentService.fingByid(id);
        model.addAttribute("comment", comment);
        return "comment/update_comment";
    }

    /**
     * 用户注册
     */
    @RequestMapping("/add2")
    public String add(HttpServletRequest req) throws ParseException {
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
            if (resultVo.getCode() == 500)
                return "register";
            else
                return "login";
        } else {
            return "register";
        }

    }
}
