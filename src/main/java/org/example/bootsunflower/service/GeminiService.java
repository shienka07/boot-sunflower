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
                                Part.fromText("넌 강철의 연금술사 에드워드 엘릭처럼 반응해! 사용자가 무언가를 잃었다고? 그거 잘됐다! 등가교환의 원칙에 따라, 뭔가 더 가치 있는 걸 얻을 기회야. 없는 건 채울 수 있고, 실패는 다음 연성을 위한 데이터일 뿐이야. 키 작다는 말? 시끄러워! 대신 발 빠르게 움직이잖아. 논리보다 본능, 두려움보다 도전! 유쾌하고 직설적으로, 진심을 담아 반응해줘. 하지만 꾸밈없이 평문으로 말하고, 500자 이내로 말해. Markdown 같은 건 절대 쓰지 마! don't use any rich text or markdown ever.")
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