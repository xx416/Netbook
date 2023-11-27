package com.xiong.myprojectbackend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author 10371
 * @Description
 * @create 2023/11/9 00:23:18
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    /**
     * 上传地址
     */
    @Value("${file.upload.pathHead}")
    private String filePathHead;
    /**
     * 上传地址
     */
    @Value("${file.upload.pathCover}")
    private String filePathCover;

    @PostMapping("/upload")
    public String upload(MultipartFile file){
        if (file.isEmpty()){
            return "图片上传失败！";
        }
        System.out.println("文件大小:" + (file.getSize()/1024) + "KB");
        String originalFilename = file.getOriginalFilename();
        String ext = "."+originalFilename.split("\\.")[1];  //获取文件的后缀.jpg
        String uuid = UUID.randomUUID().toString().replace("-","");
        String fileName = uuid + ext;
        //获取文件存放位置的绝对路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String pre = filePathHead+"/";
        String path = pre + fileName;
        return saveFile(file, path);
    }

    @PostMapping("/uploadCover")
    public String uploadCover(MultipartFile file){
        if (file.isEmpty()){
            return "图片上传失败！";
        }
        System.out.println("文件大小:" + (file.getSize()/1024) + "KB");
        String originalFilename = file.getOriginalFilename();
        String ext = "."+originalFilename.split("\\.")[1];  //获取文件的后缀.jpg
        String uuid = UUID.randomUUID().toString().replace("-","");
        String fileName = uuid + ext;
        //获取文件存放位置的绝对路径
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String pre = filePathCover+"/";
        String path = pre + fileName;
        return saveFile(file,path);
    }

    private String saveFile(MultipartFile file, String path) {
        try {
            //判断存储的目录是否存在，如果不存在则创建
            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdirs(); //创建多级目录
            }
            System.out.println("上传文件存储的绝对路径：" + path);
            file.transferTo(new File(path));  //把网络中上传的文件存储到路径当中
            return path.substring(3);
        }catch (Exception e){
            System.out.println("上传失败！出现异常！！！" + e);
            return "上传失败，出现异常！";
        }
    }

}
