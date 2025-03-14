package com.ignit.internship.controller.cakrawala;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.ResponseReturn;
import com.ignit.internship.dto.cakrawala.InfoRequest;
import com.ignit.internship.dto.cakrawala.InfoResponse;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.service.cakrawala.InfoService;

import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/cakrawala/info")
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @Operation(description = "Create Info")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<DefaultResponse<InfoResponse>> createInfo(
        @RequestPart List<MultipartFile> files,
        @RequestPart InfoRequest request
    ) throws Exception {
        return ResponseReturn.ok(new InfoResponse(infoService.createInfo(files, request)));
    }

    @Operation(description = "Get Info by Tag")
    @GetMapping("/{name}")
    public ResponseEntity<DefaultResponse<InfoResponse>> getInfo(@PathVariable String name) throws IdNotFoundException {
        return ResponseReturn.ok(new InfoResponse(infoService.getInfo(name)));
    }

    @Operation(description = "Update Info by Tag")
    @PatchMapping("/{name}")
    public ResponseEntity<DefaultResponse<InfoResponse>> updateInfo(
        @RequestBody InfoRequest request,
        @PathVariable String name
    ) throws IdNotFoundException, IOException {
        return ResponseReturn.ok(new InfoResponse(infoService.updateInfo(request, name)));
    }
}
