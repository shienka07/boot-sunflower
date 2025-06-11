package org.example.bootsunflower.controller;

import lombok.RequiredArgsConstructor;
import org.example.bootsunflower.dto.PromptForm;
import org.example.bootsunflower.service.PromptService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PromptController {
    private final PromptService promptService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("promptForm", new PromptForm(""));
        return "index";
    }
}