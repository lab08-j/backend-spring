package br.com.juniando.blog.features.uploads.controllers;

import br.com.juniando.blog.features.uploads.dtos.AwsS3ResponseDto;
import br.com.juniando.blog.features.uploads.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class S3UploadController {

    @Autowired
    private S3Service s3Service;

    @PostMapping
    public ResponseEntity<AwsS3ResponseDto> upload (@RequestParam("file")MultipartFile file){

        try {
            String fileUrl = s3Service.uploadFile(file);
            AwsS3ResponseDto fileResponse = new AwsS3ResponseDto("Upload Success", fileUrl);
            return ResponseEntity.ok(fileResponse);
        } catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AwsS3ResponseDto("Upload Failed", "No File Url Created"));
        }

    }

}
