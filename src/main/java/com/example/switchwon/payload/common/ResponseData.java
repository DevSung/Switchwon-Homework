package com.example.switchwon.payload.common;

import org.springframework.http.ResponseEntity;

public record ResponseData(Boolean success, Object data) {
    public static ResponseEntity<Object> success(Object data) {
        return ResponseEntity.ok(new ResponseData(true, data));
    }
}