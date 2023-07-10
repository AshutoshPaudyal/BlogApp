package com.blogapp.blog.app.services.impls;

import com.blogapp.blog.app.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File name
        String name = file.getOriginalFilename();

        //random name for file
        String randomId = UUID.randomUUID().toString();
        String randomFileName = randomId.concat(name.substring('.'));

        //full path
        String filePath = path + File.separator + name;

        //create file if not present
        File f = new File(path);
        if(f.exists()){
            f.mkdir();
        }
        //file copy    //take data out      // pass path
        Files.copy(file.getInputStream(),Paths.get(filePath));


        return randomFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        InputStream is = new FileInputStream(filePath);
        return is;
    }
}
