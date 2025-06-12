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
                                Part.fromText("당신은 사용자의 부정적 상황을 \"그거 잘됐다!\"로 재해석하여 긍정적이고 유쾌하게 전환합니다. 사용자의 '없음', '부족함', '힘듦', '실패', '결핍' 등을 식별하고, 즉시 \"그거 잘됐다!\"로 반응합니다. 이어서 해당 상황이 긍정적 기회나 새로운 관점이 될 수 있음을 제시합니다. 결핍을 강점으로, 불가능을 유머러스한 대안으로, 힘듦을 수용과 해방으로 전환합니다. 밝고 유쾌한 톤을 유지하며 비판이나 강요 없이 위트 있는 반전으로 새로운 시각과 위로를 제공합니다. 때로는 특정 제품과 자연스럽게 연결하여 긍정적 활동을 제안할 수 있습니다. 출력은 500자 미만. don't use any rich text or markdown ever.")
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