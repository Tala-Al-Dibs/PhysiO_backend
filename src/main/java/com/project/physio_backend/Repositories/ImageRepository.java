package com.project.physio_backend.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.physio_backend.Entities.Image.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
