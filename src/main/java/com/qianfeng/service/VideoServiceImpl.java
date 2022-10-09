package com.qianfeng.service;

import com.qianfeng.dao.UserMapper;
import com.qianfeng.dao.VideoMapper;
import com.qianfeng.pojo.Document;
import com.qianfeng.pojo.User;
import com.qianfeng.pojo.Video;
import com.qianfeng.util.FileUploadUtil;
import com.qianfeng.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class VideoServiceImpl implements VideoService{

    @Resource
    private UserMapper userMapper;
    @Resource
    private VideoMapper videoMapper;

    @Override
    public ResultVo getVideoList() {
        try {
            //查询所有用户
            List<Video> videoList = videoMapper.selectAll();
            List<Video> videos = new ArrayList<Video>();;
            for(Video video: videoList) {
                //如果发布者存在查找文档发布者信息
                if (video.getuId() > 0)
                {
                    User user = userMapper.selectByPrimaryKey(video.getuId());
                    video.setuName(user.getUsername());
                    video.setuProfile(user.getProfile());
                }
                videos.add(video);
            }
            ResultVo successVo = ResultVo.getSuccessVo(videos);
            //长度单独赋值，因为增删改时候是不需要给长度赋值(长度只有查询分页时候需要)
            successVo.setCount(videos.size());
            return successVo;
        }catch (Exception e){
            e.printStackTrace();
            ResultVo failVo = ResultVo.getFailVo();
            return failVo;
        }
    }

    @Override
    public ResultVo getVideoListByPage(Video video) {
        //分页查询的起始位置
        video.setPage((video.getPage()-1)*video.getLimit());
        try {
            //分页查询
            List<Video> videoList = videoMapper.getVideoListByPage(video);
            List<Video> videos = new ArrayList<Video>();
            for(Video doc: videoList) {
                //如果发布者存在查找文档发布者信息
                if (doc.getuId() > 0)
                {
                    User user = userMapper.selectByPrimaryKey(doc.getuId());
                    doc.setuName(user.getUsername());
                    doc.setuProfile(user.getProfile());
                }
                videos.add(doc);
            }
            //查询数据总条数
            Integer totalSize = videoMapper.selectCount(video);
            ResultVo successVo = ResultVo.getSuccessVo(videos);
            //分页时必须传递总数量，layui才能自动计算到底多少页
            successVo.setCount(totalSize);
            return successVo;
        }catch (Exception e){
            e.printStackTrace();
            ResultVo failVo = ResultVo.getFailVo();
            return failVo;
        }
    }

    @Override
    public ResultVo upload(MultipartFile uploadFile, String parentName, HttpServletRequest request) {
        try{
            String filePath = FileUploadUtil.uploadFile(uploadFile, parentName, request);
            ResultVo successVo = ResultVo.getSuccessVo(filePath);
            return successVo;
        }catch (Exception e){
            e.printStackTrace();
            ResultVo failVo = ResultVo.getFailVo();
            return failVo;
        }
    }

    @Override
    public ResultVo updateByPrimaryKey(Video video) {
        try {
            videoMapper.updateByPrimaryKey(video);
            return ResultVo.getSuccessVo("删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public ResultVo add(Video video) {
        try{
            videoMapper.insert(video);
            return ResultVo.getSuccessVo("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public ResultVo deleteBatch(Integer[] ids) {
        try{
            //1. 批量删除用户的sql  :  [1,2,3]   delete from tb_album where al_id in (1,2,3);
            videoMapper.deleteBatch(ids);
            return ResultVo.getSuccessVo("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public ResultVo deleteVideo(int id) {
        try{
            videoMapper.deleteByPrimaryKey(id);
            return ResultVo.getSuccessVo("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public Video fingByid(int id) {
        Video video = videoMapper.selectByPrimaryKey(id);
        return video;
    }
}
