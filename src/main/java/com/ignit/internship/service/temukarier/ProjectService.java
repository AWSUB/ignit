package com.ignit.internship.service.temukarier;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.temukarier.ProjectRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.exception.NonCreatorException;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.model.temukarier.Project;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.profile.ProfileRepository;
import com.ignit.internship.repository.temukarier.ProjectRepository;
import com.ignit.internship.repository.utils.TagRepository;
import com.ignit.internship.service.utils.EmailService;
import com.ignit.internship.service.utils.ImageService;

import jakarta.transaction.Transactional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final TagRepository tagRepository;

    private final ImageService imageService;

    private final ProfileRepository profileRepository;

    private final EmailService emailService;

    @Value("${base.url}")
    private String baseUrl;

    public ProjectService(
        final ProjectRepository projectRepository, 
        final TagRepository tagRepository, 
        final ImageService imageService,
        final ProfileRepository profileRepository,
        final EmailService emailService
    ) {
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
        this.imageService = imageService;
        this.profileRepository = profileRepository;
        this.emailService = emailService;
    }

    public Project getProjectById(Long id) throws IdNotFoundException {
        return projectRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Project not found"));
    }

    public List<Project> getProjectByPage(Pageable pageable) {
        return projectRepository.findAll(pageable).toList();
    }

    public List<Project> getProjectByPageAndTag(Pageable pageable, List<String> tags) {
        return projectRepository.findByMultipleTagName(tags, tags.size(), pageable).toList();
    }

    @Transactional
    public Project createProject(MultipartFile file, ProjectRequest request, Long id) throws Exception {
        if (!tagRepository.existsAllById(request.getTags())) {
            throw new IdNotFoundException("Tag not found");
        }

        Image image = imageService.uploadImage(file);
        
        UserProfile profile = profileRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Profile not found"));

        Project project = projectRepository.save(new Project(
            request.getName(),
            request.getDescription(),
            image.getId(),
            request.getStatus(),
            request.getDeadline(),
            tagRepository.findAllById(request.getTags()),
            profile
        ));

        profile.addProject(project);

        return project;
    }

    @Transactional
    public Project updateProject(ProjectRequest request, Long projectId, Long profileId) throws IdNotFoundException, IOException {
        if (!tagRepository.existsAllById(request.getTags())) {
            throw new IdNotFoundException("Tag not found");
        }

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IdNotFoundException("Project not found"));

        if (project.getProfile().getId() != profileId) throw new IdNotFoundException("User can only update their own project");

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        project.setDeadline(request.getDeadline());
        project.setTags(tagRepository.findAllById(request.getTags()));

        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Long id) throws IdNotFoundException {
        Project project = getProjectById(id);
        imageService.deleteImage(project.getImageId());
        projectRepository.deleteById(id);
    }

    //use front-end url for email
    public void joinProject(Long profileId, Long projectId) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(profile.getEmail());
        mailMessage.setSubject("Project Application Ignit");
        mailMessage.setText(String.format("""
            Applicants:
            Username    : %s
            Email       : %s
            Full Name   : %s

            Approve by clicking this link:
            %s/api/temukarier/projects/%d/approve/%d
            """, profile.getUsername(), profile.getEmail(), profile.getFullName(), baseUrl, projectId, profile.getId()));

        emailService.sendEmail(mailMessage);
    }

    @Transactional
    public void approveJoinProject(Long profileId, Long projectId, Long applicantId) throws Exception {
        Project project = getProjectById(projectId);
        if (project.getProfile().getId() != profileId) {
            throw new NonCreatorException("Only project creator can approve new member");
        }
        UserProfile profile = profileRepository.findById(applicantId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        project.addMembers(profile);
        profile.addProject(project);
        projectRepository.save(project);
    }
}
