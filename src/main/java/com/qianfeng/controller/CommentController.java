package com.qianfeng.controller;

import com.qianfeng.pojo.Comment;
import com.qianfeng.pojo.User;
import com.qianfeng.pojo.Video;
import com.qianfeng.service.CommentService;
import com.qianfeng.service.VideoService;
import com.qianfeng.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/admin/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    private User loginUser = new User();

    @RequestMapping("/list")
    @ResponseBody  //返回json数据
    public ResultVo list(HttpServletRequest req, HttpServletResponse resp, Comment comment){
        //获取当前登录用户
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");
        loginUser = user;
        //System.out.println(user.getId());
        ResultVo resultVo = commentService.getCommentListByPage(comment);
        return resultVo;
    }

    @RequestMapping("/usercommentlist")
    @ResponseBody  //返回json数据
    public ResultVo usercommentlist(HttpServletRequest req, HttpServletResponse resp, Comment comment){
        //获取当前登录用户
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");
        loginUser = user;
        comment.setuId(user.getId());
        //System.out.println(comment.toString());
        // System.out.println(user.toString());
        ResultVo resultVo = commentService.getCommentListByPage(comment);
        return resultVo;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResultVo add(Comment comment){
        Date date = new Date();
        comment.setCreationdate(date);
        comment.setuId(loginUser.getId());
        ResultVo resultVo = commentService.add(comment);
        return resultVo;
    }

    /**
     * 接受前端传递的文件，并且上传文件，而且返回文件在服务器存储的相对路径
     */
    @RequestMapping("/upload")
    @ResponseBody  //返回json数据
    public ResultVo upload(MultipartFile uploadFile, String parentName, HttpServletRequest request){
        return commentService.upload(uploadFile,parentName,request);
    }

    /**
     * 删除当前行
     * @param id
     * @return
     */
    @RequestMapping("/deleteComment")
    @ResponseBody
    public ResultVo deleteComment(int id) {
        return commentService.deleteComment(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public ResultVo deleteBatch(Integer []  ids){
        return commentService.deleteBatch(ids);
    }

    @RequestMapping("/updateComment")
    @ResponseBody
    public ResultVo updateComment(Comment comment){
        System.out.println(comment.toString());
        ResultVo resultVo =  commentService.updateByPrimaryKey(comment);
        return resultVo;
    }
}
