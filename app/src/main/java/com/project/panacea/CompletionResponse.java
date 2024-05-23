package com.project.panacea;

import java.util.List;

public class CompletionResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public class Choice {
        private String text;

        public String getText() {
            return text;
        }
    }
}
