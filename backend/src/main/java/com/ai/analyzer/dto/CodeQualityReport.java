package com.ai.analyzer.dto;

import java.util.List;

public class CodeQualityReport {
    private List<QualityMetric> metrics;

    // Getters and Setters
    public List<QualityMetric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<QualityMetric> metrics) {
        this.metrics = metrics;
    }
}
