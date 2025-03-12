package com.ignit.internship.service.temukarier;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.temukarier.MagangRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.temukarier.Magang;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.temukarier.MagangRepository;
import com.ignit.internship.repository.utils.TagRepository;
import com.ignit.internship.service.utils.ImageService;

@Service
public class MagangService {

    private final MagangRepository magangRepository;

    private final TagRepository tagRepository;

    private final ImageService imageService;

    public MagangService(
        final MagangRepository magangRepository, 
        final TagRepository tagRepository, 
        final ImageService imageService
    ) {
        this.magangRepository = magangRepository;
        this.tagRepository = tagRepository;
        this.imageService = imageService;
    }

    public Magang getMagangById(Long id) throws IdNotFoundException {
        return magangRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Magang not found"));
    } 

    public List<Magang> getMagangByPage(Pageable pageable) {
        return magangRepository.findAll(pageable).toList();
    }

    public List<Magang> getMagangByPageAndTag(Pageable pageable, List<String> tags) {
        return magangRepository.findByMultipleTagName(tags, tags.size(), pageable).toList();
    }

    public Magang createMagang(MultipartFile file, MagangRequest request) throws Exception {
        if (!tagRepository.existsAllById(request.getTags())) {
            throw new IdNotFoundException("Tag not found");
        }

        Image image = imageService.uploadImage(file);

        return magangRepository.save(new Magang(
            request.getName(), 
            request.getDescription(),
            image.getId(), 
            request.getUrl(),
            tagRepository.findAllById(request.getTags())
        ));
    }

    public Magang updateMagang(MagangRequest request, Long id) throws IdNotFoundException, IOException {
        Magang magang = magangRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Magang not found"));

        magang.setName(request.getName());
        magang.setDescription(request.getDescription());
        magang.setUrl(request.getUrl());
        magang.setTags(tagRepository.findAllById(request.getTags()));

        return magangRepository.save(magang);
    }

    public void deleteMagang(Long id) throws IdNotFoundException {
        Magang magang = getMagangById(id);
        imageService.deleteImage(magang.getImageId());
        magangRepository.deleteById(id);
    }
}
