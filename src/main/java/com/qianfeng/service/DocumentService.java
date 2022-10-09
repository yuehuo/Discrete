package com.qianfeng.service;

import com.qianfeng.pojo.Document;
import com.qianfeng.pojo.User;
import com.qianfeng.vo.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface DocumentService {
    /**
     * 查询所有的文档
     * @return
     */
    ResultVo getDocumentList();

    /**
     * 分页查询文档列表
     * page:页数
     * limit:条数
     * @return
     */
    ResultVo getDocumentListByPage(Document document);

    /**
     * 上传文件并返回文件相对路径的方法
     * @param uploadFile
     * @param parentName
     * @param request
     * @return
     */
    ResultVo upload(MultipartFile uploadFile, String parentName, HttpServletRequest request);

    /**
     * 根据id更新文档数据
     * @param document
     */
    ResultVo updateByPrimaryKey(Document document);

    /**
     * 添加文档
     * @param document
     * @return
     */
    ResultVo add(Document document);

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
    ResultVo deleteDocument(int id);

    /**
     * 根据u_id查找文件数据
     * @param document
     * @return
     */
    ResultVo selectByUid(Document document);

    /**
     * 根据id查找文档数据
     * @param id
     * @return
     */
    Document fingByid(int id);
}
