package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

//文件上传模块功能实现

@Service("iFileService")
public class FIleServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FIleServiceImpl.class);

    public String upload(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        //扩展名,比如abc.jpg
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);//指针往后移一位，不要点本身
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;//文件随机命名
        logger.info("开始上传文件,上传文件的文件名:{},上传路径:{},新文件名:{}",fileName,path,uploadFileName);

        //判断目录是否存在，如果不存在，先创建目录
        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //文件已经上传成功了

            //将targetFile上传到我们的FTP服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到发图片服务器上

            //上传完之后，删除upload下面的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }
        return targetFile.getName();
    }
}
