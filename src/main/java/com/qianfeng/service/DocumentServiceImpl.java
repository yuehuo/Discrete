package com.qianfeng.service;

import com.qianfeng.dao.DocumentMapper;
import com.qianfeng.dao.UserMapper;
import com.qianfeng.pojo.Document;
import com.qianfeng.pojo.User;
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
public class DocumentServiceImpl implements DocumentService{

    @Resource
    private DocumentMapper documentMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public ResultVo getDocumentList() {
        try {
            //查询所有用户
            List<Document> documentList = documentMapper.selectAll();
            List<Document> documents = new ArrayList<Document>();;
            for(Document document: documentList) {
                //如果发布者存在查找文档发布者信息
                if (document.getuId() > 0)
                {
                    User user = userMapper.selectByPrimaryKey(document.getuId());
                    document.setuName(user.getUsername());
                    document.setuProfile(user.getProfile());
                }
                documents.add(document);
            }
            ResultVo successVo = ResultVo.getSuccessVo(documents);
            //长度单独赋值，因为增删改时候是不需要给长度赋值(长度只有查询分页时候需要)
            successVo.setCount(documents.size());
            return successVo;
        }catch (Exception e){
            e.printStackTrace();
            ResultVo failVo = ResultVo.getFailVo();
            return failVo;
        }
    }

    @Override
    public ResultVo getDocumentListByPage(Document document) {
        //分页查询的起始位置
        document.setPage((document.getPage()-1)*document.getLimit());
        try {
            //分页查询
            List<Document> documentList = documentMapper.getDocumentListByPage(document);
            List<Document> documents = new ArrayList<Document>();
            for(Document doc: documentList) {
                //如果发布者存在查找文档发布者信息名字与头像
                if (doc.getuId() > 0)
                {
                    User user = userMapper.selectByPrimaryKey(doc.getuId());
                    doc.setuName(user.getUsername());
                    doc.setuProfile(user.getProfile());
                }
                documents.add(doc);
            }
            //查询数据总条数
            Integer totalSize = documentMapper.selectCount(document);
            ResultVo successVo = ResultVo.getSuccessVo(documents);
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
    public ResultVo updateByPrimaryKey(Document document) {
        try {
            documentMapper.updateByPrimaryKey(document);
            return ResultVo.getSuccessVo("删除成功");
        }catch (Exception e) {
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public ResultVo add(Document document) {
        try{
            documentMapper.insert(document);
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
            documentMapper.deleteBatch(ids);
            return ResultVo.getSuccessVo("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public ResultVo deleteDocument(int id) {
        try{
            documentMapper.deleteByPrimaryKey(id);
            return ResultVo.getSuccessVo("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResultVo.getFailVo();
        }
    }

    @Override
    public ResultVo selectByUid(Document document) {
        try {
            //分页查询
            List<Document> documentList = documentMapper.selectByUid(document);
            List<Document> documents = new ArrayList<Document>();;
            for(Document doc: documentList) {
                //如果发布者存在查找文档发布者信息
                if (doc.getuId() > 0)
                {
                    User user = userMapper.selectByPrimaryKey(doc.getuId());
                    doc.setuName(user.getUsername());
                    doc.setuProfile(user.getProfile());
                }
                documents.add(doc);
            }
            //查询数据总条数
            Integer totalSize = documentMapper.selectCountByUid(document);
            ResultVo successVo = ResultVo.getSuccessVo(documents);
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
    public Document fingByid(int id) {
        Document document = documentMapper.selectByPrimaryKey(id);
        return document;
    }
}
