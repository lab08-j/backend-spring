package br.com.juniando.blog.features.uploads.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwsS3ResponseDto {

    public Long id;
    public String urlFile;

}
