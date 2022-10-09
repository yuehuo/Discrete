package com.qianfeng.dao;

import com.qianfeng.pojo.Document;
import com.qianfeng.pojo.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Video record);

    Video selectByPrimaryKey(Integer id);

    List<Video> selectAll();

    int updateByPrimaryKey(Video record);
    /**
     * 分页查询
     * @param record:起始位置
     * mybatis一次传入多个参数：1. 将多个参数封装成对象传入  2. 单独分开传多个参数时，要加@Param("")标识参数
     * @return
     */
    List<Video> getVideoListByPage(Video record);

    /**
     * 总条数
     * @return
     */
    Integer selectCount(Video record);

    /**
     * 相同发布者的总条数
     * @return
     */
    Integer selectCountByUid(Video record);

    /**
     * 通过文档上传者id查找数据
     * @param record
     * @return
     */
    List<Video> selectByUid(Video record);

    /**
     * 根据id批量删除用户
     * @param ids
     */
    void deleteBatch(@Param("ids") Integer[] ids);
}