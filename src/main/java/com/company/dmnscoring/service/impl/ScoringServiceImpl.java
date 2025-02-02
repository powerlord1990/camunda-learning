package com.company.dmnscoring.service.impl;

import com.company.dmnscoring.model.ScoringRequest;
import com.company.dmnscoring.model.ScoringResult;
import com.company.dmnscoring.repository.ScoringRepository;
import com.company.dmnscoring.service.ScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.expression.TypedValue;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
@Slf4j
public class ScoringServiceImpl implements ScoringService {

//    private final ScoringRepository scoringRepository;

    @Override
    public Map<String, Object> evaluateScoring(ScoringRequest request) {
        DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();

        var decisionTable = dmnEngine.parseDecision("Decision_1", getClass().getResourceAsStream("/dmn/scoring-decision.dmn"));

        Map<String, Object> variables = new HashMap<>();
        variables.put("isIP", request.getInn().length() == 12);
        variables.put("regionCode", request.getRegionCode());
        variables.put("capital", request.getCapital());
        variables.put("inn", request.getInn());

        DmnDecisionResult decisionResult = dmnEngine.evaluateDecision(decisionTable, variables);

        Map<String, Object> result = new HashMap<>();

        List<Map<String, Object>> resultList = decisionResult.getResultList();
        log.info(resultList.toString());
        if (!resultList.isEmpty()) {
            result.putAll(resultList.get(0));
        }

        saveToElasticSearch(request, result);

        return result;
    }

    private void saveToElasticSearch(ScoringRequest request, Map<String, Object> result) {
        ScoringResult scoringResult = new ScoringResult();
        scoringResult.setInn(request.getInn());
        scoringResult.setRegionCode(request.getRegionCode());
        scoringResult.setCapital(request.getCapital());
        scoringResult.setIsResident(request.getIsResident());
        scoringResult.setResult((Boolean) result.get("result"));
        scoringResult.setDetails(result);

//        scoringRepository.save(scoringResult);
    }
}

