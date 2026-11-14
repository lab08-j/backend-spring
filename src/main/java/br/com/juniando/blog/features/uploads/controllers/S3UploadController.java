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
    public ResponseEntity<?> upload (@RequestParam("file") List<MultipartFile> fileList){

        if (fileList.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Upload Failed, no files.");
        }

        //2MB limit
        long sizeLimit = 2 * 1024 * 1024;
        for (MultipartFile fileTemp : fileList) {
            if (fileTemp.getSize() > sizeLimit){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Upload Failed, the file size exceeds the limit. Only 2 MB per image.");
            }
        }

        //5 file limit
        int filesLimit = 5;
        if (fileList.size() > filesLimit) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Upload Failed, only 5 files per request.");
        }

        try {

            List<AwsS3ResponseDto> fileResponseList = new ArrayList<AwsS3ResponseDto>();
            List<ImageUpload> imageUploadList = s3Service.uploadFiles(fileList);
            for (ImageUpload imageUpload : imageUploadList) {
                fileResponseList.add(new AwsS3ResponseDto(imageUpload.getId(), imageUpload.getUrlFile()));
            }

            return ResponseEntity.status(HttpStatus.OK).body(fileResponseList);

        } catch (IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload Failed, no file url created.");
        }

    }

}
