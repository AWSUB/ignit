package com.ignit.internship.service.temukarier;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.temukarier.ProjectRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.model.temukarier.Project;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.repository.profile.ProfileRepository;
import com.ignit.internship.repository.temukarier.ProjectRepository;
import com.ignit.internship.repository.utils.TagRepository;
import com.ignit.internship.service.utils.ImageService;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final TagRepository tagRepository;

    private final ImageService imageService;

    private final ProfileRepository profileRepository;

    public ProjectService(
        final ProjectRepository projectRepository, 
        final TagRepository tagRepository, 
        final ImageService imageService,
        final ProfileRepository profileRepository
    ) {
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
        this.imageService = imageService;
        this.profileRepository = profileRepository;
    }

    public Project getProjectById(Long id) throws IdNotFoundException {
        return projectRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Project not found"));
    }

    public List<Project> getProjectByPage(Pageable pageable) {
        return projectRepository.findAll(pageable).toList();
    }

    public List<Project> getProjectByPageAndTag(Pageable pageable, String tag) {
        return projectRepository.findByTagName(tag, pageable).toList();
    }

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

    public Project updateProject(ProjectRequest request, Long projectId, Long profileId) throws IdNotFoundException, IOException {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IdNotFoundException("Project not found"));

        if (project.getProfile().getId() != profileId) throw new IdNotFoundException("User can only update their own project");

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(request.getStatus());
        project.setDeadline(request.getDeadline());
        project.setTags(tagRepository.findAllById(request.getTags()));

        return projectRepository.save(project);
    }
}
