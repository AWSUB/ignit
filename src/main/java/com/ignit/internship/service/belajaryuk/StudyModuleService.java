package com.ignit.internship.service.belajaryuk;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.model.belajaryuk.StudyModule;
import com.ignit.internship.model.belajaryuk.StudyPackage;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.belajaryuk.StudyModuleRepository;
import com.ignit.internship.repository.belajaryuk.StudyPackageRepository;
import com.ignit.internship.service.utils.ImageService;

import jakarta.transaction.Transactional;

import com.ignit.internship.dto.belajaryuk.StudyModuleRequest;
import com.ignit.internship.exception.IdNotFoundException;

@Service
public class StudyModuleService {

    private final StudyModuleRepository studyModuleRepository;

    private final StudyPackageRepository studyPackageRepository;

    private final ImageService imageService;

    public StudyModuleService(
        final StudyModuleRepository studyModuleRepository,
        final StudyPackageRepository studyPackageRepository,
        final ImageService imageService
    ) {
        this.studyModuleRepository = studyModuleRepository;
        this.studyPackageRepository = studyPackageRepository;
        this.imageService = imageService;
    }

    public StudyModule getStudyModuleById(Long id) throws IdNotFoundException {
        return studyModuleRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Study Module not found"));
    }
    
    @Transactional
    public StudyModule createStudyModule(MultipartFile file, StudyModuleRequest request) throws Exception {
        StudyPackage studyPackage = studyPackageRepository.findById(request.getPackageId()).orElseThrow(() -> new IdNotFoundException("Package not found"));

        Image image = imageService.uploadImage(file);

        StudyModule studyModule = studyModuleRepository.save(new StudyModule(
            request.getTitle(),
            request.getIntroduction(), 
            image.getId(),
            studyPackage
        ));
        
        studyPackage.addModule(studyModule);

        return studyModule;
    }

    
}
