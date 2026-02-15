package com.dailycodework.dream_shops.repository;

import com.dailycodework.dream_shops.model.image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;

public interface ImageRepository extends JpaRepository<image,Long> {
}
