package com.dailycodework.dream_shops.controller;


import com.dailycodework.dream_shops.Services.ImageService;
import com.dailycodework.dream_shops.dto.imageDto;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.interfaces.ImageInterface;
import com.dailycodework.dream_shops.model.image;
import com.dailycodework.dream_shops.response.ApiResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class imageController {

    private final ImageInterface imageInterface;
    private final ImageService imageService;

    //Return data to frontend
    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try {
            List<imageDto> imageDtos = imageInterface.saveImage(files, productId);
            return  ResponseEntity.ok(new ApiResponse("Upload successfull", imageDtos));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImages(@PathVariable Long imageId) throws SQLException {
        image i = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(i.getImage().getBytes(1
                ,(int) i.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(i.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + i.getFileName() + "\"")
                .body(resource);
    }


    @PostMapping("/image/{imageId}/update")
    public  ResponseEntity <ApiResponse> updateImage(@PathVariable Long imageId, @RequestParam MultipartFile file) {
        try {
            image i = imageService.getImageById(imageId);
            return ResponseEntity.ok(new ApiResponse("update success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("update failed!", null));
        }
    }

    @PostMapping("/image/{imageId}/delete")
    public  ResponseEntity <ApiResponse> deleteImage(@PathVariable Long imageId, @RequestParam MultipartFile file) {
        try {
            image i = imageService.getImageById(imageId);
            if (i != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("delete success!", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", null));



    }


}

