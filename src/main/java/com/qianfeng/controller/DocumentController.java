package com.qianfeng.controller;

import com.qianfeng.pojo.Document;
import com.qianfeng.pojo.User;
import com.qianfeng.service.DocumentService;
import com.qianfeng.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

@Controller
@RequestMapping("/admin/document")
public class DocumentController {

    @Resource
    private DocumentService documentService;

    private User loginUser = new User();

    @RequestMapping("/list")
    @ResponseBody  //返回json数据
    public ResultVo list(HttpServletRequest req, HttpServletResponse resp, Document document){
        //获取当前登录用户
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");
        loginUser = user;
        //System.out.println(user.getId());
        ResultVo resultVo = documentService.getDocumentListByPage(document);
        return resultVo;
    }

    @RequestMapping("/userdocumentlist")
    @ResponseBody  //返回json数据
    public ResultVo userdocumentlist(HttpServletRequest req, HttpServletResponse resp, Document document){
        //获取当前登录用户
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");
        loginUser = user;
        document.setuId(user.getId());
        System.out.println(document.toString());
        System.out.println(user.toString());
        ResultVo resultVo = documentService.getDocumentListByPage(document);
        return resultVo;
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResultVo add(Document document){
        Date date = new Date();
        document.setCreationdate(date);
        document.setuId(loginUser.getId());
        ResultVo resultVo = documentService.add(document);
        return resultVo;
    }

    /**
     * 接受前端传递的文件，并且上传文件，而且返回文件在服务器存储的相对路径
     */
    @RequestMapping("/upload")
    @ResponseBody  //返回json数据
    public ResultVo upload(MultipartFile uploadFile, String parentName, HttpServletRequest request){
        return documentService.upload(uploadFile,parentName,request);
    }

    /**
     * 删除当前行
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteDocument")
    @ResponseBody
    public ResultVo deleteDocument(int id) {

        return documentService.deleteDocument(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public ResultVo deleteBatch(Integer []  ids){

        return documentService.deleteBatch(ids);
    }

    @RequestMapping("/updateDocument")
    @ResponseBody
    public ResultVo updateDocument(Document document){
        System.out.println(document.toString());
        ResultVo resultVo =  documentService.updateByPrimaryKey(document);
        return resultVo;
    }

}
