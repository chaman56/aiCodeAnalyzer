package com.ai.analyzer.dto;

import java.util.List;

public class AnalysisResponse {

    private List<String> issues;
    private List<String> suggestions;
    private String generatedFix;
    private List<String> generatedTestCases;
    private CodeQualityReport codeQualityReport;

    public AnalysisResponse(List<String> issues, List<String> suggestions, String generatedFix, List<String> generatedTestCases, CodeQualityReport codeQualityReport) {
        this.issues = issues;
        this.suggestions = suggestions;
        this.generatedFix = generatedFix;
        this.generatedTestCases = generatedTestCases;
        this.codeQualityReport = codeQualityReport;
    }

    // Getters and Setters
    public List<String> getIssues() {
        return issues;
    }

    public void setIssues(List<String> issues) {
        this.issues = issues;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public String getGeneratedFix() {
        return generatedFix;
    }

    public void setGeneratedFix(String generatedFix) {
        this.generatedFix = generatedFix;
    }

    public List<String> getGeneratedTestCases() {
        return generatedTestCases;
    }

    public void setGeneratedTestCases(List<String> generatedTestCases) {
        this.generatedTestCases = generatedTestCases;
    }

    public CodeQualityReport getCodeQualityReport() {
        return codeQualityReport;
    }

    public void setCodeQualityReport(CodeQualityReport codeQualityReport) {
        this.codeQualityReport = codeQualityReport;
    }
}
