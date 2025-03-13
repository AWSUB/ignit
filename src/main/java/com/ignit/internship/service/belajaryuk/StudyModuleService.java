package com.ignit.internship.service.belajaryuk;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.model.belajaryuk.StudyExercise;
import com.ignit.internship.model.belajaryuk.StudyMaterial;
import com.ignit.internship.model.belajaryuk.StudyModule;
import com.ignit.internship.model.belajaryuk.StudyPackage;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.belajaryuk.StudyModuleRepository;
import com.ignit.internship.repository.belajaryuk.StudyPackageRepository;
import com.ignit.internship.service.utils.ImageService;

import jakarta.transaction.Transactional;

import com.ignit.internship.dto.belajaryuk.StudyExerciseRequest;
import com.ignit.internship.dto.belajaryuk.StudyMaterialRequest;
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

    public StudyModule getStudyModuleById(Long moduleId) throws IdNotFoundException {
        return studyModuleRepository.findById(moduleId).orElseThrow(() -> new IdNotFoundException("Study Module not found"));
    }

    public StudyModule getStudyModuleByModuleIdAndProfileId(Long moduleId, Long profileId) throws IdNotFoundException {
        return studyModuleRepository.findByModuleIdAndProfileId(moduleId, profileId).orElseThrow(() -> new IdNotFoundException("Study Module not found"));
    }
    
    @Transactional
    public StudyModule createStudyModule(MultipartFile file, StudyModuleRequest request) throws Exception {
        StudyPackage studyPackage = studyPackageRepository.findById(request.getPackageId()).orElseThrow(() -> new IdNotFoundException("Study Package not found"));

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

    @Transactional
    public StudyModule updateStudyModule(StudyModuleRequest request, Long moduleId) throws IdNotFoundException {
        StudyModule module = getStudyModuleById(moduleId);
        
        module.setTitle(request.getTitle());
        module.setIntroduction(request.getIntroduction());

        return studyModuleRepository.save(module);
    }

    @Transactional
    public void deleteStudyModule(Long id) throws IdNotFoundException {
        StudyModule module = studyModuleRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Study Module not found"));
        imageService.deleteImage(module.getImageId());
        studyModuleRepository.deleteById(id);
    }

    @Transactional
    public StudyMaterial getStudyMaterial(Long moduleId, Long profileId, Long materialId) throws IdNotFoundException {
        StudyModule module = getStudyModuleByModuleIdAndProfileId(moduleId, profileId);
        return studyModuleRepository.findStudyMaterialById(module.getId(), materialId).orElseThrow(() -> new IdNotFoundException("Study Material not found"));
    }

    @Transactional
    public List<StudyMaterial> getAllStudyMaterial(Long moduleId, Long profileId) throws IdNotFoundException {
        StudyModule module = getStudyModuleByModuleIdAndProfileId(moduleId, profileId);
        return studyModuleRepository.findAllStudyMaterialByModule(module.getId());
    }

    @Transactional
    public StudyMaterial createStudyMaterial(
        MultipartFile thumbnail,
        List<MultipartFile> files,
        Long moduleId, 
        StudyMaterialRequest request
    ) throws Exception {
        StudyModule module = getStudyModuleById(moduleId);
        
        Image thumbnailImage = imageService.uploadImage(thumbnail);
        
        List<Long> imageIds = new ArrayList<Long>();
        for (MultipartFile file : files) {
            Image image = imageService.uploadImage(file);
            imageIds.add(image.getId());
        }

        StudyMaterial material = new StudyMaterial(
            request.getTitle(),
            thumbnailImage.getId(),
            imageIds,
            request.getContent(),
            module
        );

        module.addMaterial(material);
        
        studyModuleRepository.save(module);

        return material;
    }

    @Transactional
    public StudyMaterial updateStudyMaterial(
        Long moduleId,
        Long materialId,
        StudyMaterialRequest request
    ) throws Exception {
        StudyModule module = getStudyModuleById(moduleId);
        StudyMaterial material = studyModuleRepository.findStudyMaterialById(module.getId(), materialId).orElseThrow(() -> new IdNotFoundException("Study Material not found"));

        material.setTitle(request.getTitle());
        material.setContent(request.getContent());

        studyModuleRepository.save(module);

        return material;
    }

    @Transactional
    public void deleteStudyMaterial(Long moduleId, Long materialId) throws IdNotFoundException {
        StudyModule module = getStudyModuleById(moduleId);
        StudyMaterial material = studyModuleRepository.findStudyMaterialById(moduleId, materialId).orElseThrow(() -> new IdNotFoundException("Study Material not found"));
        imageService.deleteImage(material.getThumbnailId());
        material.getImageIds().forEach(imageId -> imageService.deleteImage(imageId));
        module.removeMaterial(materialId);
        studyModuleRepository.save(module);
    }

    @Transactional
    public StudyExercise getStudyExercise(Long moduleId, Long profileId, Long exerciseId) throws IdNotFoundException {
        StudyModule module = getStudyModuleByModuleIdAndProfileId(moduleId, profileId);
        return studyModuleRepository.findStudyExerciseById(module.getId(), exerciseId).orElseThrow(() -> new IdNotFoundException("Study Exercise not found"));
    }

    @Transactional
    public List<StudyExercise> getAllStudyExercise(Long moduleId, Long profileId) throws IdNotFoundException {
        StudyModule module = getStudyModuleByModuleIdAndProfileId(moduleId, profileId);
        return studyModuleRepository.findAllStudyExerciseByModule(module.getId());
    }

    @Transactional
    public StudyExercise createStudyExercise(
        MultipartFile thumbnail, 
        List<MultipartFile> files, 
        Long moduleId, 
        StudyExerciseRequest request
    ) throws Exception {
        StudyModule module = getStudyModuleById(moduleId);
        
        Image thumbnailImage = imageService.uploadImage(thumbnail);
        
        List<Long> imageIds = new ArrayList<Long>();
        for (MultipartFile file : files) {
            Image image = imageService.uploadImage(file);
            imageIds.add(image.getId());
        }

        StudyExercise exercise = new StudyExercise(
            request.getTitle(),
            thumbnailImage.getId(),
            imageIds,
            request.getContent(),
            module
        );

        module.addExercise(exercise);
        
        studyModuleRepository.save(module);

        return exercise;
    }

    @Transactional
    public StudyExercise updateStudyExercise(
        Long moduleId,
        Long exerciseId,
        StudyExerciseRequest request
    ) throws Exception {
        StudyModule module = getStudyModuleById(moduleId);
        StudyExercise exercise = studyModuleRepository.findStudyExerciseById(module.getId(), exerciseId).orElseThrow(() -> new IdNotFoundException("Study Exercise not found"));

        exercise.setTitle(request.getTitle());
        exercise.setContent(request.getContent());

        studyModuleRepository.save(module);

        return exercise;
    }

    @Transactional
    public void deleteStudyExercise(Long moduleId, Long exerciseId) throws IdNotFoundException {
        StudyModule module = getStudyModuleById(moduleId);
        StudyExercise exercise = studyModuleRepository.findStudyExerciseById(moduleId, exerciseId).orElseThrow(() -> new IdNotFoundException("Study Exercise not found"));
        imageService.deleteImage(exercise.getThumbnailId());
        exercise.getImageIds().forEach(imageId -> imageService.deleteImage(imageId));
        module.removeExercise(exerciseId);
        studyModuleRepository.save(module);
    }
}
