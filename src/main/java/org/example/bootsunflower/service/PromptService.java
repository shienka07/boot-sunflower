package org.example.bootsunflower.service;

import lombok.RequiredArgsConstructor;
import org.example.bootsunflower.entity.Prompt;
import org.example.bootsunflower.repository.PromptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromptService {
    final private PromptRepository promptRepository;

    List<Prompt> getAllPrompts() {
        return promptRepository.findAll();
    }

    Prompt getPromptById(String id) {
        return promptRepository.findById(id).orElseThrow();
    }

    Prompt savePrompt(Prompt prompt) {
        return promptRepository.save(prompt);
    }
}