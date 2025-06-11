package org.example.bootsunflower.dto;

import java.util.List;

public record GeminiRequestDTO(List<Content> contents) {
    public record Content(List<Part> parts){}
    public record Part(String text) {}
}
