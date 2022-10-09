package com.qianfeng.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUploadUtil {

    /**
     * 上传文件并且返回存储的相对路径的方法
     * parentName：取值可能是img,可能是mp3,可能是mp4
     */
    public static String  uploadFile(MultipartFile uploadFile, String parentName, HttpServletRequest request){
        //1. 获取文件的原始名字
        String originalFilename = uploadFile.getOriginalFilename();
        //2. 获取项目的根路径
        //   D:\software\JetBrains\ProjectSpring\chaoshi\target\ chaoshi \ upload\
        String basepath = request.getServletContext().getRealPath("/upload/");
        //3. 获取存放文件的父路径
        // img\
        String parentPath = parentName+"/";
        //4. 判断父文件夹是否存在，不存在就创建，存在直接使用
        File parentFile = new File(basepath + parentPath);
        if(!parentFile.exists()){
            //5. 如果不存在，创建文件夹
            parentFile.mkdirs();
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmssSSS");//年月日时分秒毫秒
        //6. 将这个不会重复的时间作为文件名
        String fileName = simpleDateFormat.format(date);
        //7. 文件上传
        try {
            uploadFile.transferTo(new File(basepath+parentPath+fileName+getSuffix(originalFilename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //   upload/img/20210101231321312.jpg
        return "upload/"+parentName+"/"+fileName+getSuffix(originalFilename);
    }
    /**
     * 根据文件名称返回文件后缀
     *
     *  1.png  3.mp3   1.a.mp3
     */
    public static String getSuffix(String originalFilename){
        //获取最后一个.的位置（.的下标）
        int index = originalFilename.lastIndexOf(".");
        //字符串的截取方法(从index开始截取，默认到末尾)
        return originalFilename.substring(index);
    }
}
