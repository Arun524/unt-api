package com.unt.untstore.integration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AwsS3Client {

    private AmazonS3 s3client;

    @Value("${cloud.aws.s3.endpointUrl}")
    private String endpointUrl;
    
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Value("${cloud.aws.s3.accessKeyId}")
    private String accessKeyId;

    @Value("${cloud.aws.s3.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.region}")
    private String region;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKeyId, this.secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    public String uploadFile(MultipartFile multipartFile) throws Exception {

        String fileUrl = "";
        File file = convertMultiPartToFile(multipartFile);
        String fileName = generateFileName(multipartFile);
        fileUrl = endpointUrl + "/" + fileName;
        uploadFileTos3bucket(fileName, file);
        file.delete();
        return fileUrl;
    }

    public String deleteFileFromS3Bucket(String fileName) {
        s3client.deleteObject(bucketName, fileName);
        return "Successfully deleted";
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(bucketName, fileName, file);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
}