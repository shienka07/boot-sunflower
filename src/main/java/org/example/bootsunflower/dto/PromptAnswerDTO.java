package org.example.bootsunflower.dto;

public record PromptAnswerDTO (
        String url,
        String question,
        String answer,
        String createdAt)
{
}
