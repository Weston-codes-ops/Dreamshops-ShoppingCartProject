package com.dailycodework.dream_shops.interfaces;


import com.dailycodework.dream_shops.dto.imageDto;
import com.dailycodework.dream_shops.model.image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.awt.*;

public interface ImageInterface {
image getImageById(Long id);
void deleteImageById(Long id);
List<imageDto> saveImage(List<MultipartFile> files, Long productId);
void updateImage(MultipartFile file, Long imageId);


}
