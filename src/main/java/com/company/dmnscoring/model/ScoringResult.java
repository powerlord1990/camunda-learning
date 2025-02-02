package com.company.dmnscoring.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.util.Map;

@Data
@Document(indexName = "scoring-results")
public class ScoringResult {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String inn;

    @Field(type = FieldType.Integer)
    private Integer regionCode;

    @Field(type = FieldType.Long)
    private Long capital;

    @Field(type = FieldType.Boolean)
    private Boolean isResident;

    @Field(type = FieldType.Boolean)
    private Boolean result;

    @Field(type = FieldType.Object)
    private Map<String, Object> details;
}
