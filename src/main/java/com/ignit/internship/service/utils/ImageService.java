package com.ignit.internship.service.utils;

import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.utils.ImageRepository;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @SuppressWarnings("null")
    public Image uploadImage(MultipartFile file) throws Exception {
        if (file == null) throw new FileUploadException("No file provided");

        if (file.getContentType() == null || !file.getContentType().startsWith("image")) {
            throw new Exception("Not image type");
        }
        
        return imageRepository.save(
            new Image(
                file.getOriginalFilename(), 
                file.getContentType(), 
                file.getBytes()
            )
        );
    }

    public Image getImage(Long id) throws IdNotFoundException {
        return imageRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Image not found"));
    }

    public Image updateImage(MultipartFile file, Long id) throws IdNotFoundException, IOException {
        Image image = getImage(id);
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setData(file.getBytes());
        return imageRepository.save(image);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
