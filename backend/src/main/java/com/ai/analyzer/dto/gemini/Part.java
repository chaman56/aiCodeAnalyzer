package com.ai.analyzer.dto.gemini;

public class Part {
    private String text;

    public Part(String text) {
        this.text = text;
    }

    // Getters and setters
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
