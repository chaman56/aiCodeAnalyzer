package com.ai.analyzer.dto;

import java.util.List;

public class QualityMetric {
    private String metricName;
    private int score;
    private List<String> suggestions;

    // Getters and Setters
    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}
