package com.store.controller;

import com.store.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/image")
public class ImageController {

    ImageService service;

    public ImageController(@Autowired ImageService uploader){
        this.service = uploader;
    }

    @RequestMapping(path = "/uploadImg",method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam(name = "image", required = false) MultipartFile file){
        return service.uploadFile(file);
    }

    @RequestMapping(path = "/deleteImg",method = RequestMethod.DELETE)
    public void deleteImage(String name){
        service.deleteFile(name);
    }
}
