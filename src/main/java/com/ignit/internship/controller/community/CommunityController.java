package com.ignit.internship.controller.community;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.ResponseReturn;
import com.ignit.internship.dto.community.CommunityRequest;
import com.ignit.internship.dto.community.CommunityResponse;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.service.community.CommunityService;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<CommunityResponse>> createCommunity(@RequestBody CommunityRequest request) throws IdNotFoundException {
        return ResponseReturn.ok(new CommunityResponse(communityService.createCommunity(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<CommunityResponse>> getCommunity(@PathVariable Long id) throws IdNotFoundException {
        return ResponseReturn.ok(new CommunityResponse(communityService.getCommunity(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse<CommunityResponse>> updateCommunity(
        @PathVariable Long id,
        @RequestBody CommunityRequest request
    ) throws IdNotFoundException {
        return ResponseReturn.ok(new CommunityResponse(communityService.updateCommunity(request, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse<Object>> deleteCommunity(@PathVariable Long id) {
        communityService.deleteCommunity(id);
        return ResponseEntity.ok(null);
    }
}
