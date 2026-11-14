package br.com.juniando.blog.features.uploads.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class ImageUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String urlFile;

    public ImageUpload (String urlFile){
        this.urlFile = urlFile;
    }

}
