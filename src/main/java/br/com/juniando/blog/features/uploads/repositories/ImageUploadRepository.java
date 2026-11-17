package br.com.juniando.blog.features.uploads.repositories;

import br.com.juniando.blog.features.uploads.models.ImageUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageUploadRepository extends JpaRepository<ImageUpload, Long> {
}
