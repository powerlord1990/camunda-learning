package com.company.dmnscoring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ScoringResponse {

    private Boolean result;
    private Map<String, Object> details;
}
