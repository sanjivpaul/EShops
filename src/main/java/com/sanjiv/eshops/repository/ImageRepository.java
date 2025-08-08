package com.sanjiv.eshops.repository;

import com.sanjiv.eshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
