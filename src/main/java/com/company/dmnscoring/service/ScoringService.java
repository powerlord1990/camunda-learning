package com.company.dmnscoring.service;

import com.company.dmnscoring.model.ScoringRequest;
import com.company.dmnscoring.model.ScoringResponse;

public interface ScoringService {

    ScoringResponse evaluateScoring(ScoringRequest request);
}
