package com.ai.analyzer.dto.gemini;

public class GenerationConfig {
    private String responseMimeType;

    public GenerationConfig(String responseMimeType) {
        this.responseMimeType = responseMimeType;
    }

    // Getters and setters
    public String getResponseMimeType() {
        return responseMimeType;
    }

    public void setResponseMimeType(String responseMimeType) {
        this.responseMimeType = responseMimeType;
    }
}
