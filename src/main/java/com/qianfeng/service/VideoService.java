package com.qianfeng.service;

import com.qianfeng.pojo.Document;
import com.qianfeng.pojo.Video;
import com.qianfeng.vo.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface VideoService {
    /**
     * 查询所有的视频
     * @return
     */
    ResultVo getVideoList();

    /**
     * 分页查询视频列表
     * page:页数
     * limit:条数
     * @return
     */
    ResultVo getVideoListByPage(Video video);

    /**
     * 上传文件并返回文件相对路径的方法
     * @param uploadFile
     * @param parentName
     * @param request
     * @return
     */
    ResultVo upload(MultipartFile uploadFile, String parentName, HttpServletRequest request);

    /**
     * 根据id更新视频数据
     * @param video
     */
    ResultVo updateByPrimaryKey(Video video);

    /**
     * 添加视频
     * @param video
     * @return
     */
    ResultVo add(Video video);

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
    ResultVo deleteVideo(int id);

    /**
     * 根据id查找视频数据
     * @param id
     * @return
     */
    Video fingByid(int id);

}
