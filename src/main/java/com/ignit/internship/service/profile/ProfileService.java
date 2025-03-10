package com.ignit.internship.service.profile;

import org.springframework.stereotype.Service;

import com.ignit.internship.dto.profile.SkillRequest;
import com.ignit.internship.dto.profile.EducationRequest;
import com.ignit.internship.dto.profile.ProfileRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.profile.Education;
import com.ignit.internship.model.profile.Skill;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.repository.profile.ProfileRepository;

@Service
public final class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
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
