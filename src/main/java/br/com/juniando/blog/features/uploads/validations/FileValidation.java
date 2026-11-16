package br.com.juniando.blog.features.uploads.validations;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileValidation {

    private static final List<String> ALLOWED_MIME_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private static final List<String> ALLOWED_EXTENSIONS = List.of(
            ".jpg", "jpeg", ".png", ".webp"
    );

    public static boolean isValidFileType (MultipartFile file){
        if (file == null || file.isEmpty()) {
            return false;
        }

        String contentType = file.getContentType();
        String originalFileName = file.getOriginalFilename();

        boolean isMimeValid = ALLOWED_MIME_TYPES.contains(contentType);

        boolean isExtensionValid = ALLOWED_EXTENSIONS.stream()
                .anyMatch(ext -> originalFileName != null && originalFileName.toLowerCase().endsWith(ext));

        return isMimeValid && isExtensionValid;
    }

}
