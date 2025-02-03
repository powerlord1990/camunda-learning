package com.company.dmnscoring.service.impl;

import com.company.dmnscoring.entity.ScoringResult;
import com.company.dmnscoring.model.ScoringRequest;
import com.company.dmnscoring.model.ScoringResponse;
import com.company.dmnscoring.repository.ScoringRepository;
import com.company.dmnscoring.service.ScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoringServiceImpl implements ScoringService {

   // private final ScoringRepository scoringRepository;

    @Override
    public ScoringResponse evaluateScoring(ScoringRequest request) {
        try {
            // готовим DMN
            DmnEngine dmnEngine = DmnEngineConfiguration
                    .createDefaultDmnEngineConfiguration()
                    .buildEngine();

            // Попытка загрузки DMN-модели
            InputStream dmnModelStream = getClass().getResourceAsStream("/dmn/scoring-decision.dmn");
            if (dmnModelStream == null) {
                log.error("DMN-модель не найдена по пути /dmn/scoring-decision.dmn");
                throw new RuntimeException("DMN-модель не найдена");
            }

            var decisionTable = dmnEngine.parseDecision("companyScoreDecision", dmnModelStream);

            Map<String, Object> variables = new HashMap<>();
            variables.put("Inn", String.valueOf(request.getInn()));
            variables.put("RegionCode", request.getRegionCode());
            variables.put("Capital", request.getCapital());

            DmnDecisionTableResult decisionResult = dmnEngine.evaluateDecisionTable(decisionTable, variables);

            List<Map<String, Object>> resultList = decisionResult.getResultList();

            List<String> resultsList = resultList.stream()
                    .flatMap(row -> row.values().stream())
                    .filter(value -> value instanceof String)
                    .map(value -> (String) value)
                    .filter(stringValue -> !stringValue.isEmpty())
                    .collect(Collectors.toList());

            boolean success = !resultsList.isEmpty();

            ScoringResponse scoringResponse = new ScoringResponse(success, resultsList);

//            saveToElasticSearch(request, scoringResponse);

            return scoringResponse;

        } catch (RuntimeException e) {
            // Логирование ошибки, если модель не найдена или произошла другая ошибка
            log.error("Ошибка при выполнении DMN: {}", e.getMessage(), e);
            return new ScoringResponse(false, Collections.singletonList("Ошибка выполнения DMN: " + e.getMessage()));
        }
    }

//    private void saveToElasticSearch(ScoringRequest request, ScoringResponse response) {
//        ScoringResult scoringResult = new ScoringResult();
//        scoringResult.setInn(request.getInn());
//        scoringResult.setRegionCode(request.getRegionCode());
//        scoringResult.setCapital(request.getCapital());
//        scoringResult.setDetails(response.getDetails());
//        scoringResult.setSuccess(response.getResult());
//
//        scoringRepository.save(scoringResult);
//    }
}

