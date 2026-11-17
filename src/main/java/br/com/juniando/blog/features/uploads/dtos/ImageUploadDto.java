package br.com.juniando.blog.features.uploads.dtos;

import jakarta.validation.constraints.NotBlank;

public record ImageUploadDto(@NotBlank String urlFile) {
}
