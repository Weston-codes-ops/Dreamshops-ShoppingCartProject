package com.dailycodework.dream_shops.Services;

import com.dailycodework.dream_shops.dto.imageDto;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.interfaces.ImageInterface;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.model.image;
import com.dailycodework.dream_shops.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.*;



@Service
@RequiredArgsConstructor
public class ImageService implements ImageInterface{

    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
      imageRepository.findById(id)
             .ifPresentOrElse(imageRepository::delete, ()-> {
                 throw new ResourceNotFoundException("No image found with id: " + id);
             });

    }



    @Override
    public List<imageDto> saveImage(List <MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List <imageDto> savedImageDto = new ArrayList<>();


        for(MultipartFile file : files){

            try {
                image imageItem = new image();
                imageItem.setFileName(file.getOriginalFilename());
                imageItem.setFileType(file.getContentType());
                imageItem.setImage(new SerialBlob(file.getBytes()));
                imageItem.setProduct(product);

                String downloadURL = "/api/v1/images/image/download" + imageItem.getId(); // Path where image is gonna be downloaded
                imageItem.setDownloadURL(downloadURL);

                image savedImage = imageRepository.save(imageItem);

                //Get current id of saved image to minimize/prevent errors when downloading
                savedImage.setDownloadURL("/api/v1/images/image/download" + savedImage.getId());

                imageRepository.save(savedImage);

                imageDto imageDtoObject = new imageDto();
                imageDtoObject.setImageId(savedImage.getId());
                imageDtoObject.setImageName(savedImage.getFileName());
                imageDtoObject.setDownloadURL(savedImage.getDownloadURL());
                savedImageDto.add(imageDtoObject);

            } catch (IOException  | SQLException e){
             throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void  updateImage(MultipartFile file, Long imageId) {
       image imageItem = getImageById(imageId);
        try {
            imageItem.setFileName(file.getOriginalFilename());
            imageItem.setFileType(file.getContentType());
            imageItem.setImage(new SerialBlob(file.getBytes()));

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }


    }
}
