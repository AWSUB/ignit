package com.ignit.internship.service.cakrawala;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.cakrawala.InfoRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.cakrawala.Info;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.cakrawala.InfoRepository;
import com.ignit.internship.repository.utils.TagRepository;
import com.ignit.internship.service.utils.ImageService;

import jakarta.transaction.Transactional;

@Service
public class InfoService {

    private final InfoRepository infoRepository;

    private final TagRepository tagRepository;

    private final ImageService imageService;

    public InfoService(
        final InfoRepository infoRepository, 
        final TagRepository tagRepository,
        final ImageService imageService
    ) {
        this.infoRepository = infoRepository;
        this.tagRepository = tagRepository;
        this.imageService = imageService;
    }

    public Info getInfo(String name) throws IdNotFoundException {
        return infoRepository.findById(name).orElseThrow(() -> new IdNotFoundException("Info not found"));
    }

    @Transactional
    public Info createInfo(List<MultipartFile> files, InfoRequest request) throws Exception {
        if (!tagRepository.existsById(request.getTag())) {
            throw new IdNotFoundException("Tag not found");
        }       

        List<Long> imageIds = new ArrayList<Long>();
        for (MultipartFile file : files) {
            Image image = imageService.uploadImage(file);
            imageIds.add(image.getId());
        }

        Info info = new Info(
            request.getDescription(),
            request.getSalaryRange(),
            request.getCriteria(),
            request.getSkills(),
            request.getRelatedStudies(),
            request.getCareerOpportunities(),
            request.getResponsibilites(),
            request.getQuestion(),
            request.getOptions(),
            request.getAnswer(),
            tagRepository.findById(request.getTag()).orElseThrow(() -> new IdNotFoundException("Tag not found")),
            imageIds
        );

        return infoRepository.save(info);
    }

    @Transactional
    public Info updateInfo(InfoRequest request, String name) throws IdNotFoundException, IOException {
        if (!tagRepository.existsById(request.getTag())) {
            throw new IdNotFoundException("Tag not found");
        }
        
        Info info = getInfo(name);

        info.setDescription(request.getDescription());
        info.setSalaryRange(request.getSalaryRange());
        info.setCriteria(request.getCriteria());
        info.setSkills(request.getSkills());
        info.setRelatedStudies(request.getRelatedStudies());
        info.setCareerOpportunities(request.getCareerOpportunities());
        info.setResponsibilites(request.getResponsibilites());
        info.setQuestion(request.getQuestion());
        info.setOptions(request.getOptions());
        info.setAnswer(request.getAnswer());

        return info;
    }
}
