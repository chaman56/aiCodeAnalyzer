package com.ai.analyzer.service;

import com.ai.analyzer.dto.CodeQualityReport;
import com.ai.analyzer.dto.QualityMetric;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class CodeQualityService {

    private final GeminiService geminiService;

    public CodeQualityService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public CodeQualityReport analyzeCodeQuality(String code) {
        String prompt = "Analyze the following Java code and provide a code quality report. The report should be a JSON object with a 'metrics' array. Each object in the array should have 'metricName' (e.g., 'Readability', 'Maintainability', 'Cyclomatic Complexity', 'Best-practices Adherence'), 'score' (out of 100), and 'suggestions' for improvement. Code: " + code;
        String jsonResponse = geminiService.callApiJson(prompt);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonResponse, CodeQualityReport.class);
        } catch (IOException e) {
            System.err.println("Failed to parse JSON response: " + e.getMessage());
            System.err.println("JSON Response: " + jsonResponse);
            // Handle parsing exception
            CodeQualityReport errorReport = new CodeQualityReport();
            QualityMetric errorMetric = new QualityMetric();
            errorMetric.setMetricName("Error");
            errorMetric.setScore(0);
            errorMetric.setSuggestions(java.util.Arrays.asList("Failed to parse code quality report from AI service."));
            errorReport.setMetrics(new ArrayList<>());
            errorReport.getMetrics().add(errorMetric);
            return errorReport;
        }
    }
}
