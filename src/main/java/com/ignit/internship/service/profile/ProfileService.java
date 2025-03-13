package com.ignit.internship.service.profile;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.profile.SkillRequest;
import com.ignit.internship.dto.profile.EducationRequest;
import com.ignit.internship.dto.profile.ProfileRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.profile.Education;
import com.ignit.internship.model.profile.Skill;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.repository.profile.ProfileRepository;
import com.ignit.internship.service.utils.ImageService;

@Service
public final class ProfileService {

    private final ProfileRepository profileRepository;

    private final ImageService imageService;

    public ProfileService(
        final ProfileRepository profileRepository,
        final ImageService imageService
    ) {
        this.profileRepository = profileRepository;
        this.imageService = imageService;
    }

    public UserProfile getProfile(Long id) throws IdNotFoundException {
        return profileRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Profile not found"));
    }

    public UserProfile updateProfile(ProfileRequest request, Long id) throws IdNotFoundException {
        UserProfile profile = getProfile(id);
        profile.setFullName(request.getFullName());
        profile.setPassion(request.getPassion());
        profile.setSummary(request.getSummary());
        return profileRepository.save(profile);
    }

    public void updateProfilePicture(MultipartFile file, Long id) throws IdNotFoundException, IOException {
        UserProfile profile = getProfile(id);
        imageService.updateImage(file, profile.getImageId());
    }

    public UserProfile addSkill(SkillRequest request, Long id) throws IdNotFoundException {
        UserProfile profile = getProfile(id);
        profile.addSkill(new Skill(request.getName(), request.getDescription(), request.getYear(), profile));
        return profileRepository.save(profile);
    }

    public UserProfile removeSkill(Long skillId, Long profileId) throws IdNotFoundException {
        UserProfile profile = getProfile(profileId);
        profile.removeSkill(skillId);
        return profileRepository.save(profile);
    }

    public UserProfile addEducation(EducationRequest request, Long id) throws IdNotFoundException {
        UserProfile profile = getProfile(id);
        profile.addEducation(new Education(
            request.getDegree(),
            request.getSchool(),
            request.getFieldOfStudy(),
            request.getStartDate(),
            request.getEndDate(),
            profile
        ));
        return profileRepository.save(profile);
    }

    public UserProfile removeEducation(Long educationId, Long profileId) throws IdNotFoundException {
        UserProfile profile = getProfile(profileId);
        profile.removeEducation(educationId);
        return profileRepository.save(profile);
    }
}
