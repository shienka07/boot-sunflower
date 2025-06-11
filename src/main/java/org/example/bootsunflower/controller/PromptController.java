package org.example.bootsunflower.controller;

import lombok.RequiredArgsConstructor;
import org.example.bootsunflower.service.PromptService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PromptController {
    private final PromptService promptService;

    public String index() {
        return "index";
    }
}