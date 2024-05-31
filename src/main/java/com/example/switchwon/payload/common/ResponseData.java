package com.example.switchwon.payload.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record ResponseData(int responseCode, Boolean success, Object data) {

    public static ResponseEntity<Object> success(Object data) {
        return ResponseEntity.ok(new ResponseData(HttpStatus.OK.value(), true, data));
    }

    public static ResponseEntity<Object> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ResponseData(status.value(), false, message));
    }
}