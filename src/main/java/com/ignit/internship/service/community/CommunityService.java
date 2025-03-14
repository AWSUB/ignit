package com.ignit.internship.service.community;

import org.springframework.stereotype.Service;

import com.ignit.internship.dto.community.CommunityRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.community.Community;
import com.ignit.internship.repository.community.CommunityRepository;
import com.ignit.internship.repository.utils.TagRepository;

import jakarta.transaction.Transactional;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;

    private final TagRepository tagRepository;

    public CommunityService(
        final CommunityRepository communityRepository,
        final TagRepository tagRepository
    ) {
        this.communityRepository = communityRepository;
        this.tagRepository = tagRepository;
    }

    public Community getCommunity(Long id) throws IdNotFoundException {
        return communityRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Community not found"));
    }

    @Transactional
    public Community createCommunity(CommunityRequest request) throws IdNotFoundException {
        if (!tagRepository.existsAllById(request.getTags())) {
            throw new IdNotFoundException("Tag not found");
        }

        return communityRepository.save(new Community(
            request.getTitle(),
            request.getContent(),
            request.getUrl(),
            tagRepository.findAllById(request.getTags())
        ));
    }

    @Transactional
    public Community updateCommunity(CommunityRequest request, Long id) throws IdNotFoundException {
        if (!tagRepository.existsAllById(request.getTags())) {
            throw new IdNotFoundException("Tag not found");
        }

        Community community = getCommunity(id);

        community.setTitle(request.getTitle());
        community.setContent(request.getContent());
        community.setUrl(request.getUrl());
        community.setTags(tagRepository.findAllById(request.getTags()));

        return communityRepository.save(community);
    }

    public void deleteCommunity(Long id) {
        communityRepository.deleteById(id);
    }
}
