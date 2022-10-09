package com.qianfeng.service;

import com.qianfeng.dao.CommentMapper;
import com.qianfeng.dao.UserMapper;
import com.qianfeng.pojo.Comment;
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
public class CommentServiceImpl implements CommentService{

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentMapper commentMapper;

    @Override
    public ResultVo getCommentListByPage(Comment comment) {
        //分页查询的起始位置
        comment.setPage((comment.getPage()-1)*comment.getLimit());
        try {
            //分页查询
            List<Comment> commentList = commentMapper.getCommentListByPage(comment);
            List<Comment> comments = new ArrayList<Comment>();
            for(Comment doc: commentList) {
                //如果发布者存在查找文档发布者信息
                if (doc.getuId() > 0)
                {
                    User user = userMapper.selectByPrimaryKey(doc.getuId());
                    doc.setuName(user.getUsername());
                    doc.setuProfile(user.getProfile());
                }
                comments.add(doc);
            }
            //查询数据总条数
            Integer totalSize = commentMapper.selectCount(comment);
            ResultVo successVo = ResultVo.getSuccessVo(comments);
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
    public ResultVo updateByPrimaryKey(Comment comment) {
        try {
            commentMapper.updateByPrimaryKey(comment);
            return ResultVo.getSuccessVo("删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public ResultVo add(Comment comment) {
        try{
            commentMapper.insert(comment);
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
            commentMapper.deleteBatch(ids);
            return ResultVo.getSuccessVo("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public ResultVo deleteComment(int id) {
        try{
            commentMapper.deleteByPrimaryKey(id);
            return ResultVo.getSuccessVo("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public Comment fingByid(int id) {
        Comment comment = commentMapper.selectByPrimaryKey(id);
        return comment;
    }
}
