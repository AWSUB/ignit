package com.ignit.internship.controller.belajaryuk;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.ResponseReturn;
import com.ignit.internship.dto.belajaryuk.StudyExerciseRequest;
import com.ignit.internship.dto.belajaryuk.StudyExerciseResponse;
import com.ignit.internship.dto.belajaryuk.StudyMaterialRequest;
import com.ignit.internship.dto.belajaryuk.StudyMaterialResponse;
import com.ignit.internship.dto.belajaryuk.StudyModuleRequest;
import com.ignit.internship.dto.belajaryuk.StudyModuleResponse;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.service.belajaryuk.StudyModuleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/belajaryuk/modules")
public class StudyModuleController {

    private final StudyModuleService studyModuleService;

    public StudyModuleController(final StudyModuleService studyModuleService) {
        this.studyModuleService = studyModuleService;
    }

    @Operation(description = "Create Study Module")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<DefaultResponse<StudyModuleResponse>> createStudyModule(
        @RequestPart MultipartFile file,
        @RequestPart StudyModuleRequest request
    ) throws Exception {
        return ResponseReturn.ok(new StudyModuleResponse(studyModuleService.createStudyModule(file, request)));
    }

    @Operation(description = "Get Study Module by id")
    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<StudyModuleResponse>> getStudyModule(
        @PathVariable Long id,
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(new StudyModuleResponse(studyModuleService.getStudyModuleByModuleIdAndProfileId(id, user.getId())));
    }

    @Operation(description = "Update Study Module by id")
    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse<StudyModuleResponse>> updateStudyModule(
        @PathVariable Long id,
        @RequestBody StudyModuleRequest request
    ) throws IdNotFoundException {
        return ResponseReturn.ok(new StudyModuleResponse(studyModuleService.updateStudyModule(request, id)));
    }

    @Operation(description = "Delete Study Module by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse<Object>> deleteStudyModule(@PathVariable Long id) throws IdNotFoundException {
        studyModuleService.deleteStudyModule(id);
        return ResponseReturn.ok(null);
    }

    @Operation(description = "Get Study Material by Study Module id and Study Material id")
    @GetMapping("/{moduleId}/materials/{materialId}")
    public ResponseEntity<DefaultResponse<StudyMaterialResponse>> getStudyMaterial(
        @PathVariable Long moduleId,
        @PathVariable Long materialId,
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(new StudyMaterialResponse(
            studyModuleService.getStudyMaterial(moduleId, user.getId(), materialId)
        ));
    }

    @Operation(description = "Get All Study Material by Study Module id")
    @GetMapping("/{moduleId}/materials")
    public ResponseEntity<DefaultResponse<List<StudyMaterialResponse>>> getAllStudyMaterial(
        @PathVariable Long moduleId,
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(
            studyModuleService.getAllStudyMaterial(moduleId, user.getId()).stream().map(m -> new StudyMaterialResponse(m)).toList()
        );
    }

    @Operation(description = "Create Study Material by Study Module id")
    @PostMapping("/{moduleId}/materials")
    public ResponseEntity<DefaultResponse<StudyMaterialResponse>> createStudyMaterial(
        @PathVariable Long moduleId,
        @RequestPart MultipartFile thumbnail,
        @RequestPart List<MultipartFile> files,
        @RequestPart StudyMaterialRequest request
    ) throws Exception {
        return ResponseReturn.ok(new StudyMaterialResponse(
            studyModuleService.createStudyMaterial(
                thumbnail,
                files, 
                moduleId,
                request
            )
        ));
    }

    @Operation(description = "Update Study Material by Study Module id and Study Material id")
    @PatchMapping("/{moduleId}/materials/{materialId}")
    public ResponseEntity<DefaultResponse<StudyMaterialResponse>> updateStudyMaterial(
        @PathVariable Long moduleId,
        @PathVariable Long materialId,
        @RequestBody StudyMaterialRequest request
    ) throws Exception {
        return ResponseReturn.ok(new StudyMaterialResponse(
            studyModuleService.updateStudyMaterial(moduleId, materialId, request)
        ));
    }

    @Operation(description = "Delete Study Material by Study Module id and Study Material id")
    @DeleteMapping("/{moduleId}/materials/{materialId}")
    public ResponseEntity<DefaultResponse<Object>> deleteStudyMaterial(
        @PathVariable Long moduleId,
        @PathVariable Long materialId
    ) throws IdNotFoundException {
        studyModuleService.deleteStudyMaterial(moduleId, materialId);
        return ResponseReturn.ok(null);
    }

    @Operation(description = "Get Study Exercise by Study Module id and Study Exercise id")
    @GetMapping("/{moduleId}/exercises/{exerciseId}")
    public ResponseEntity<DefaultResponse<StudyExerciseResponse>> getStudyExercise(
        @PathVariable Long moduleId,
        @PathVariable Long exerciseId,
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(new StudyExerciseResponse(
            studyModuleService.getStudyExercise(moduleId, user.getId(), exerciseId)
        ));
    }

    @Operation(description = "Get All Study Exercise by Study Module id")
    @GetMapping("/{moduleId}/exercises")
    public ResponseEntity<DefaultResponse<List<StudyExerciseResponse>>> getAllStudyExercise(
        @PathVariable Long moduleId,
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(
            studyModuleService.getAllStudyExercise(moduleId, user.getId()).stream().map(m -> new StudyExerciseResponse(m)).toList()
        );
    }

    @Operation(description = "Create Study Exercise by Study Module id")
    @PostMapping("/{moduleId}/exercise")
    public ResponseEntity<DefaultResponse<StudyExerciseResponse>> createStudyExercise(
        @PathVariable Long moduleId,
        @RequestPart MultipartFile thumbnail,
        @RequestPart List<MultipartFile> files,
        @RequestPart StudyExerciseRequest request
    ) throws Exception {
        return ResponseReturn.ok(new StudyExerciseResponse(
            studyModuleService.createStudyExercise(
                thumbnail,
                files, 
                moduleId,
                request
            )
        ));
    }

    @Operation(description = "Update Study Exercise by Study Module id and Study Exercise id")
    @PatchMapping("/{moduleId}/exercises/{exerciseId}")
    public ResponseEntity<DefaultResponse<StudyExerciseResponse>> updateStudyExercise(
        @PathVariable Long moduleId,
        @PathVariable Long exerciseId,
        @RequestBody StudyExerciseRequest request
    ) throws Exception {
        return ResponseReturn.ok(new StudyExerciseResponse(
            studyModuleService.updateStudyExercise(moduleId, exerciseId, request)
        ));
    }

    @Operation(description = "Delete Study Exercise by Study Module id and Study Exercise id")
    @DeleteMapping("/{moduleId}/exercises/{exerciseId}")
    public ResponseEntity<DefaultResponse<Object>> deleteStudyExercise(
        @PathVariable Long moduleId,
        @PathVariable Long exerciseId
    ) throws IdNotFoundException {
        studyModuleService.deleteStudyExercise(moduleId, exerciseId);
        return ResponseReturn.ok(null);
    }
}
