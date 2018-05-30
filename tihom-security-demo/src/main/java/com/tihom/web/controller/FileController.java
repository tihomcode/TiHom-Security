package com.tihom.web.controller;

import com.tihom.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author TiHom
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private String folder = "/home/tihom/WorkPackage/project/tihom-security/tihom-security-demo/src/main/java/com/tihom/web/controller";

    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        File localFile = new File(folder,System.currentTimeMillis()+".txt");
//        file.getInputStream(); 把输入流读出来,上传到自己的阿里云服务器或者fastdfs上都可以
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id,HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (InputStream inputStream = new FileInputStream(new File(folder,id+".txt"));
            OutputStream outputStream = response.getOutputStream();){
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition","attachment;filename=test.txt");
            //把文件的输入流copy到输出流里面去,直接把文件的内容写入到响应里面去
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
        }
    }
}
