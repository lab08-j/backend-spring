package br.com.juniando.blog.features.uploads.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "upload_image")
public class ImageUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(name = "url_file")
    private String urlFile;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public ImageUpload (String urlFile, Post post) {
        this.urlFile = urlFile;
        this.post = post;
    }
    */

    public ImageUpload (String urlFile){
        this.urlFile = urlFile;
    }

}
