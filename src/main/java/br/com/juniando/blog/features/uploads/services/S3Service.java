package br.com.juniando.blog.features.uploads.services;

import br.com.juniando.blog.features.uploads.models.ImageUpload;
import br.com.juniando.blog.features.uploads.repositories.ImageUploadRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;
    private final ImageUploadRepository imageUploadRepository;

    public S3Service (S3Client s3Client, @Value("${aws.s3.bucket-name}") String bucketName, ImageUploadRepository imageUploadRepository){
        this.bucketName = bucketName;
        this.s3Client = s3Client;
        this.imageUploadRepository = imageUploadRepository;
    }

    public List<ImageUpload> uploadFiles (List<MultipartFile> files) throws IOException {

        if (files.isEmpty()) {
            return new ArrayList<>();
        }

        //List<String> linksUploaded = new ArrayList<>();
        List<ImageUpload> imageUploadList = new ArrayList<>();
        for (MultipartFile fileTemp : files){
            imageUploadList.add(uploadFile(fileTemp));
        }

        return imageUploadList;
    }

    /*
    public List<String> uploadFiles (List<MultipartFile> files) throws IOException {

        if (files.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> linksUploaded = new ArrayList<>();
        for (MultipartFile fileTemp : files){
            linksUploaded.add(uploadFile(fileTemp));
        }

        return linksUploaded;
    }
    */

    public ImageUpload uploadFile(MultipartFile file) throws IOException {
        String key = UUID.randomUUID()+"-"+file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        String urlImage = "https://" + bucketName + ".s3.amazonaws.com/" + key;

        return saveUrlImage(urlImage);
    }

    /*
    public String uploadFile(MultipartFile file) throws IOException {
        String key = UUID.randomUUID()+"-"+file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        String urlImage = "https://" + bucketName + ".s3.amazonaws.com/" + key;
        //Save data
        var imageUpload = saveUrlImage(urlImage);

        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }
    */

    public ImageUpload saveUrlImage (String imageUrl){
        /*
        for (String urlTemp : imageUrlList) {
            if (urlTemp.isBlank()) {
                return false;
            }
            imageUploadRepository.save(new ImageUpload(urlTemp));
        }
        */
        return imageUploadRepository.save(new ImageUpload(imageUrl));
    }
}
