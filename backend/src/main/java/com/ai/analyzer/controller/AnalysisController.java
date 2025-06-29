package com.ai.analyzer.controller;

import com.ai.analyzer.dto.AnalysisRequest;
import com.ai.analyzer.dto.AnalysisResponse;
import com.ai.analyzer.dto.CodeQualityReport;
import com.ai.analyzer.service.CodeQualityService;
import com.ai.analyzer.service.GeminiService;
import com.ai.analyzer.service.StaticAnalysisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnalysisController {

    private final StaticAnalysisService staticAnalysisService;
    private final GeminiService geminiService;
    private final CodeQualityService codeQualityService;

    public AnalysisController(StaticAnalysisService staticAnalysisService, GeminiService geminiService, CodeQualityService codeQualityService) {
        this.staticAnalysisService = staticAnalysisService;
        this.geminiService = geminiService;
        this.codeQualityService = codeQualityService;
    }

    @PostMapping("/analyze")
    public AnalysisResponse analyze(@RequestBody AnalysisRequest request) {
        List<String> issues = staticAnalysisService.analyze(request.getCode());

        String suggestions = geminiService.getSuggestions(request.getCode());
        String autoFix = geminiService.getAutoFix(request.getCode());
        String testCases = geminiService.getTestCases(request.getCode());
        CodeQualityReport codeQualityReport = codeQualityService.analyzeCodeQuality(request.getCode());

        return new AnalysisResponse(
                issues,
                List.of(suggestions),
                autoFix,
                List.of(testCases),
                codeQualityReport
        );
    }
}
