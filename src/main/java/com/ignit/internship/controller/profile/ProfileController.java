package com.ignit.internship.controller.profile;

import java.io.IOException;

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
import com.ignit.internship.dto.profile.SkillRequest;
import com.ignit.internship.dto.profile.EducationRequest;
import com.ignit.internship.dto.profile.ProfileRequest;
import com.ignit.internship.dto.profile.ProfileResponse;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.service.profile.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api/profiles")
public final class ProfileController {

    private final ProfileService profileService;

    public ProfileController(final ProfileService profileService) {
        this.profileService = profileService;
    }

    @Operation(description = "Get current Profile")
    @GetMapping("/me")
    public ResponseEntity<DefaultResponse<ProfileResponse>> getCurrentProfile(
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(new ProfileResponse(profileService.getProfile(user.getId())));
    }

    @Operation(description = "Get Profile by id")
    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<ProfileResponse>> getProfileById(@PathVariable Long id) throws IdNotFoundException {
        return ResponseReturn.ok(new ProfileResponse(profileService.getProfile(id)));
    }

    @Operation(description = "Update current Profile")
    @PatchMapping("/me")
    public ResponseEntity<DefaultResponse<ProfileResponse>> updateProfile(
        @RequestBody ProfileRequest request,
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(new ProfileResponse(profileService.updateProfile(request, user.getId())));
    }

    @Operation(description = "Update current Profile picture")
    @PatchMapping(path = "/me/picture", consumes = "multipart/form-data")
    public ResponseEntity<DefaultResponse<Object>> updateProfilePicture(
        @RequestPart MultipartFile file,
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException, IOException {
        User user = (User) context.getAuthentication().getPrincipal();
        profileService.updateProfilePicture(file, user.getId());
        return ResponseReturn.ok(null);
    }

    @Operation(description = "Add current Profile skill")
    @PostMapping("/me/skills")
    public ResponseEntity<DefaultResponse<ProfileResponse>> addSkill(
        @RequestBody SkillRequest request,
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(new ProfileResponse(profileService.addSkill(request, user.getId())));
    }

    @Operation(description = "Remove current Profile skill")
    @DeleteMapping("/me/skills/{id}")
    public ResponseEntity<DefaultResponse<ProfileResponse>> removeSkill(
        @PathVariable Long id,
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(new ProfileResponse(profileService.removeSkill(id, user.getId())));
    }

    @Operation(description = "Add current Profile education")
    @PostMapping("/me/educations")
    public ResponseEntity<DefaultResponse<ProfileResponse>> addEducation(
        @RequestBody EducationRequest request,
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(new ProfileResponse(profileService.addEducation(request, user.getId())));
    }

    @Operation(description = "Remove current Profile skill")
    @DeleteMapping("/me/education/{id}")
    public ResponseEntity<DefaultResponse<ProfileResponse>> removeEducation(
        @PathVariable Long id,
        @Parameter(hidden = true)
        @CurrentSecurityContext SecurityContext context
    ) throws IdNotFoundException {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(new ProfileResponse(profileService.removeEducation(id, user.getId())));
    }
}
