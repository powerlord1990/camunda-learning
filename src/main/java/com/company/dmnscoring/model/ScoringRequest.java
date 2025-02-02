package com.company.dmnscoring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ScoringRequest {

    @JsonProperty("inn")
    private String inn;

    @JsonProperty("region_code")
    private Integer regionCode;

    @JsonProperty("capital")
    private Long capital;

    @JsonProperty("is_resident")
    private Boolean isResident;

}
