package com.ignit.internship.dto;

import java.net.URI;

import org.springframework.http.ResponseEntity;

public class ResponseReturn {

    public static <T> ResponseEntity<DefaultResponse<T>> ok(T payload) {
        return ResponseEntity.ok().body(DefaultResponse.success(payload));
    }

    public static <T> ResponseEntity<DefaultResponse<T>> created(URI uri, T payload) {
        return ResponseEntity.created(uri).body(DefaultResponse.success(payload));
    }

    public static <T> ResponseEntity<DefaultResponse<T>> internalServerError(T payload, Exception ex) {
        return ResponseEntity.internalServerError().body(DefaultResponse.failed(payload, ex));
    }   
}
