package com.store.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${fileUpLoadPath}")
    private String filePath;

    public String uploadFile(MultipartFile file){
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID()+suffixName;
        //指定本地文件夹存储图片，写到需要保存的目录下

        try {
            //将图片保存到static文件夹里
            file.transferTo(new File(filePath+fileName));
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    public void deleteFile(String name){
        if(!name.equals("default.png")){
            File file = new File(filePath+name);
            file.delete();
        }
    }

}
