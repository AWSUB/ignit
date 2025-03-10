package com.ignit.internship.service.community;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ignit.internship.dto.community.CommunityRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.community.Community;
import com.ignit.internship.model.community.UserComment;
import com.ignit.internship.model.community.UserThread;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.repository.community.CommentRepository;
import com.ignit.internship.repository.community.CommunityRepository;
import com.ignit.internship.repository.community.ThreadRepository;
import com.ignit.internship.repository.profile.ProfileRepository;

@Service
public final class CommunityService {

    private final CommunityRepository communityRepository;

    private final ThreadRepository threadRepository;

    private final CommentRepository commentRepository;

    private final ProfileRepository profileRepository;

    public CommunityService(
        final CommunityRepository communityRepository,
        final ThreadRepository threadRepository, 
        final CommentRepository commentRepository,
        final ProfileRepository profileRepository
    ) {
        this.communityRepository = communityRepository;
        this.threadRepository = threadRepository;
        this.commentRepository = commentRepository;
        this.profileRepository = profileRepository;
    }

    public Community createCommunity(CommunityRequest request) {
        return communityRepository.save(new Community(request.getTitle(), request.getContent()));
    }

    public Community getCommunity(Long id) throws IdNotFoundException {
        return communityRepository.findById(id).orElseThrow(() -> new IdNotFoundException("Community not found"));
    }

    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    public UserThread createThread(
        CommunityRequest request, 
        Long communityId, 
        Long profileId
    ) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        Community community = communityRepository.findById(communityId).orElseThrow(() -> new IdNotFoundException("Community not found"));
        UserThread thread = new UserThread(request.getTitle(), request.getContent(), community, profile);

        profile.addUserThreads(thread);
        community.addThread(thread);

        return threadRepository.save(thread);
    }

    public UserThread getThread(
        Long communityId, 
        Long threadId
    ) throws IdNotFoundException {
        UserThread thread = threadRepository.findById(threadId).orElseThrow(() -> new IdNotFoundException("Thread not found"));

        if (thread.getCommunity().getId() != communityId) throw new IdNotFoundException("Thread not in specified community");

        return thread;
    }

    public List<UserThread> getAllThreadsByCommunity(Long id) throws IdNotFoundException {
        Community community = getCommunity(id);
        return threadRepository.findAllByCommunity(community);
    }

    public UserComment createComment(
        CommunityRequest request, 
        Long communityId, 
        Long threadId, 
        Long profileId
    ) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        UserThread thread = getThread(communityId, threadId);
        UserComment comment = new UserComment(request.getContent(), thread, profile);
        
        thread.addComments(comment);
        profile.addUserComments(comment);

        return commentRepository.save(comment);
    }

    public UserComment getComment(
        Long communityId, 
        Long threadId, 
        Long commentId
    ) throws IdNotFoundException {
        UserComment comment = commentRepository.findById(commentId).orElseThrow(() -> new IdNotFoundException("Comment not found"));

        if (
            comment.getThread().getId() != threadId ||
            comment.getThread().getCommunity().getId() != communityId
        ) throw new IdNotFoundException("Comment not in specified thread or community");

        return comment;
    }

    public List<UserComment> getAllCommentsByThread(Long communityId, Long threadId) throws IdNotFoundException {
        UserThread thread = getThread(communityId, threadId);
        return commentRepository.findAllByThread(thread);
    }

    public UserComment createReply(
        CommunityRequest request, 
        Long communityId, 
        Long threadId, 
        Long commentId, 
        Long profileId
    ) throws IdNotFoundException {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));
        UserThread thread = threadRepository.findById(threadId).orElseThrow(() -> new IdNotFoundException("Thread not found"));
        UserComment parentComment = getComment(communityId, threadId, commentId);

        UserComment comment = new UserComment(request.getContent(), thread, parentComment, profile);

        thread.addComments(comment);
        profile.addUserComments(comment);

        return commentRepository.save(comment);
    }
}
