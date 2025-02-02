package com.company.dmnscoring.service;

import com.company.dmnscoring.model.ScoringRequest;

import java.util.Map;

public interface ScoringService {

    Map<String, Object> evaluateScoring(ScoringRequest request);
}
