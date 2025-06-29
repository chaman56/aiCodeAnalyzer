package com.ai.analyzer.dto.gemini;

import java.util.List;

public class Content {
    private String role;
    private List<Part> parts;

    public Content(String role, List<Part> parts) {
        this.role = role;
        this.parts = parts;
    }

    // Getters and setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
