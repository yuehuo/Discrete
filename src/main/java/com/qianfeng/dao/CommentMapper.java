package com.qianfeng.dao;

import com.qianfeng.pojo.Comment;
import com.qianfeng.pojo.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    Comment selectByPrimaryKey(Integer id);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);

    /**
     * 分页查询
     * @param record:起始位置
     * mybatis一次传入多个参数：1. 将多个参数封装成对象传入  2. 单独分开传多个参数时，要加@Param("")标识参数
     * @return
     */
    List<Comment> getCommentListByPage(Comment record);

    /**
     * 总条数
     * @return
     */
    Integer selectCount(Comment record);

    /**
     * 根据id批量删除用户
     * @param ids
     */
    void deleteBatch(@Param("ids") Integer[] ids);
}