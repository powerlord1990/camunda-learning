package com.company.dmnscoring.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ScoringResponse {

    private Boolean result;
    private List<String> details;
}
