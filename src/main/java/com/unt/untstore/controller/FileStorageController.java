package com.unt.untstore.controller;

import com.unt.untstore.dto.Response;
import com.unt.untstore.integration.AwsS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file/")
public class FileStorageController {

    private AwsS3Client amazonS3Client;

    @Autowired
    FileStorageController(AwsS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @PostMapping("/uploadFile")
    public Response<String> uploadFile(@RequestPart(value = "file") MultipartFile file) throws Exception {
        return new Response<String>(this.amazonS3Client.uploadFile(file));
    }

    @DeleteMapping("/deleteFile")
    public Response<String> deleteFile(@RequestPart(value = "fileName") String fileUrl) throws Exception {
        return new Response<String>(this.amazonS3Client.deleteFileFromS3Bucket(fileUrl));
    }
}
