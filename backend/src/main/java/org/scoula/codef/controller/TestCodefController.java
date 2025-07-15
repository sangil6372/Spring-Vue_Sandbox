package org.scoula.codef.controller;

import lombok.RequiredArgsConstructor;
import org.scoula.codef.service.CodefApiRequester;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test-codef")
public class TestCodefController {

    private final CodefApiRequester codefApiRequester;

    @GetMapping("/connect")
    public ResponseEntity<String> connectAccount() {
        String json = codefApiRequester.connectAndGetConnectedId();
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(json);
    }

    @GetMapping("/account-list")
    public ResponseEntity<String> getAccountList() {
        String json = codefApiRequester.getAccountList();
        return ResponseEntity.ok()
                .header("Content-Type", "application/json")
                .body(json);
    }
}