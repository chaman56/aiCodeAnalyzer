package com.ai.analyzer.service;

import com.ai.analyzer.dto.gemini.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeminiService {

    private static final Logger logger = LoggerFactory.getLogger(GeminiService.class);
    private final RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public GeminiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getSuggestions(String code) {
        return callApi("Analyze this Java code and provide suggestions for improvement, best practices, and potential refactoring: " + code);
    }

    public String getTestCases(String code) {
        return callApi("Generate JUnit test cases for this Java code: " + code);
    }

    public String getAutoFix(String code) {
        return callApi("Provide an auto-fixed version of this Java code: " + code);
    }

    public String callApi(String prompt) {
        GeminiRequest request = new GeminiRequest(
                List.of(new Content("user", List.of(new Part(prompt)))),
                new GenerationConfig("text/plain")
        );

        try {
            String url = apiUrl + "?key=" + apiKey;
            GeminiResponse response = restTemplate.postForObject(url, request, GeminiResponse.class);
            return extractTextFromResponse(response);
        } catch (RestClientException e) {
            logger.error("Error calling Gemini API: {}", e.getMessage());
            return "Error: Could not get a response from the AI service. Please check the API key and backend logs.";
        }
    }

    public String callApiJson(String prompt) {
        GeminiRequest request = new GeminiRequest(
                List.of(new Content("user", List.of(new Part(prompt)))),
                new GenerationConfig("application/json")
        );

        try {
            String url = apiUrl + "?key=" + apiKey;
            GeminiResponse response = restTemplate.postForObject(url, request, GeminiResponse.class);
            return extractTextFromResponse(response);
        } catch (RestClientException e) {
            logger.error("Error calling Gemini API: {}", e.getMessage());
            return "Error: Could not get a response from the AI service. Please check the API key and backend logs.";
        }
    }

    private String extractTextFromResponse(GeminiResponse response) {
        if (response != null && response.getCandidates() != null && !response.getCandidates().isEmpty()) {
            Candidate candidate = response.getCandidates().get(0);
            if (candidate.getContent() != null && candidate.getContent().getParts() != null && !candidate.getContent().getParts().isEmpty()) {
                return candidate.getContent().getParts().get(0).getText();
            }
        }
        return "";
    }
}
