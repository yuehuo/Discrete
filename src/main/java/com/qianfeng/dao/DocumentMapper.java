package com.qianfeng.dao;

import com.qianfeng.pojo.Document;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocumentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Document record);

    Document selectByPrimaryKey(Integer id);

    List<Document> selectAll();

    int updateByPrimaryKey(Document record);
    /**
     * 分页查询
     * @param record:起始位置
     * mybatis一次传入多个参数：1. 将多个参数封装成对象传入  2. 单独分开传多个参数时，要加@Param("")标识参数
     * @return
     */
    List<Document> getDocumentListByPage(Document record);

    /**
     * 总条数
     * @return
     */
    Integer selectCount(Document record);

    /**
     * 相同发布者的总条数
     * @return
     */
    Integer selectCountByUid(Document record);

    /**
     * 通过文档上传者id查找数据
     * @param record
     * @return
     */
    List<Document> selectByUid(Document record);

    /**
     * 根据id批量删除用户
     * @param ids
     */
    void deleteBatch(@Param("ids") Integer[] ids);
}