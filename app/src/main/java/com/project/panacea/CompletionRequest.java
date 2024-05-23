package com.project.panacea;

public class CompletionRequest {
    private String prompt;
    private int max_tokens;
    private String model;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CompletionRequest() {}
    public CompletionRequest(String prompt, int max_tokens, String model) {
        this.prompt = prompt;
        this.max_tokens = max_tokens;
        this.model = model;
    }
}
