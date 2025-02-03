package com.company.dmnscoring.controller;

import com.company.dmnscoring.model.ScoringRequest;
import com.company.dmnscoring.model.ScoringResponse;
import com.company.dmnscoring.service.ScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scoring")
@Slf4j
public class ScoringController {

    private final ScoringService scoringService;

    @PostMapping
    public ResponseEntity<ScoringResponse> scoreCompany(@RequestBody ScoringRequest request) {
        log.info("Получен запрос на оценку: {}", request);
        return ResponseEntity.ok(scoringService.evaluateScoring(request));
    }
}
