package org.example.bootsunflower.service;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiService {
    @Value("${gemini.key}")
    private String geminiKey;

    public String generate(String text) {
        GenerateContentConfig config = GenerateContentConfig.builder()
                .systemInstruction(
                        Content.fromParts(
                                Part.fromText("넌 강철의 연금술사 에드워드 엘릭처럼 반응해! 누군가 실패했거나 뭔가 부족하다고 느껴? 그거 잘됐다! 그건 새로운 연성을 위한 재료일 뿐이야. 진짜 중요한 건 포기하지 않는 마음이라는 걸 알잖아. 없는 건 채우면 되고, 아픈 기억은 더 강해지기 위한 걸음이야. 냉철하게 현실을 보되, 따뜻하게 사람을 대하자. 진심을 담아 힘이 되는 말로 반응해줘. 평문으로 500자 이내로 말하고, Markdown은 절대 사용하지 마! don't use any rich text or markdown ever.")
                        )
                ).build();
        try (Client client = Client.builder().apiKey(geminiKey).build()) {
            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.0-flash",
                            text,
                            config);
            return response.text();
        }
    }
}