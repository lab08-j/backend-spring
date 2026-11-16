package br.com.juniando.blog.features.uploads.controllers;

import br.com.juniando.blog.features.uploads.dtos.AwsS3ResponseDto;
import br.com.juniando.blog.features.uploads.models.ImageUpload;
import br.com.juniando.blog.features.uploads.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class S3UploadController {

    @Autowired
    private S3Service s3Service;

    @PostMapping
    public ResponseEntity<?> upload (@RequestParam(value = "file", required = false) List<MultipartFile> fileList){

        if (fileList == null || fileList.isEmpty() || fileList.stream().allMatch(MultipartFile::isEmpty)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file uploaded.");
        }

        try {
            List<AwsS3ResponseDto> fileResponseList = new ArrayList<AwsS3ResponseDto>();
            List<ImageUpload> imageUploadList = s3Service.uploadFiles(fileList);
            for (ImageUpload imageUpload : imageUploadList) {
                fileResponseList.add(new AwsS3ResponseDto(imageUpload.getId(), imageUpload.getUrlFile()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(fileResponseList);

        } catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (MultipartException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }

    }

}
