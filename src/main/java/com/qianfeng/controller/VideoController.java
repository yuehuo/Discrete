package com.qianfeng.controller;

import com.qianfeng.pojo.Document;
import com.qianfeng.pojo.User;
import com.qianfeng.pojo.Video;
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
@RequestMapping("/admin/video")
public class VideoController {
    @Resource
    private VideoService videoService;

    private User loginUser = new User();

    @RequestMapping("/list")
    @ResponseBody  //返回json数据
    public ResultVo list(HttpServletRequest req, HttpServletResponse resp, Video video){
        //获取当前登录用户
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");
        loginUser = user;
        //System.out.println(user.getId());
        ResultVo resultVo = videoService.getVideoListByPage(video);
        return resultVo;
    }

    @RequestMapping("/uservideolist")
    @ResponseBody  //返回json数据
    public ResultVo uservideolist(HttpServletRequest req, HttpServletResponse resp, Video video){
        //获取当前登录用户
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");
        loginUser = user;
        video.setuId(user.getId());
        //System.out.println(video.toString());
       // System.out.println(user.toString());
        ResultVo resultVo = videoService.getVideoListByPage(video);
        return resultVo;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResultVo add(Video video){
        Date date = new Date();
        video.setCreationdate(date);
        video.setuId(loginUser.getId());
        ResultVo resultVo = videoService.add(video);
        return resultVo;
    }

    /**
     * 接受前端传递的文件，并且上传文件，而且返回文件在服务器存储的相对路径
     */
    @RequestMapping("/upload")
    @ResponseBody  //返回json数据
    public ResultVo upload(MultipartFile uploadFile, String parentName, HttpServletRequest request){
        return videoService.upload(uploadFile,parentName,request);
    }

    /**
     * 删除当前行
     * @param id
     * @return
     */
    @RequestMapping("/deleteVideo")
    @ResponseBody
    public ResultVo deleteVideo(int id) {
        return videoService.deleteVideo(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public ResultVo deleteBatch(Integer []  ids){
        return videoService.deleteBatch(ids);
    }

    @RequestMapping("/updateVideo")
    @ResponseBody
    public ResultVo updateVideo(Video video){
        System.out.println(video.toString());
        ResultVo resultVo =  videoService.updateByPrimaryKey(video);
        return resultVo;
    }
}
