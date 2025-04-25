package ru.fridrock.jir_backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fridrock.jir_backend.dto.AiDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {
    private final OllamaChatModel ollamaChatModel;

    @GetMapping
    public String getAi(AiDto dto) {
        String currentDate = LocalDateTime.now()
            .toString();

        String promptText =
            String.format("Current Date is 2025:04:25:19:26:10.  There is some date offset in this text : " +
                    "'Сделать домашку через неделю , для этого надо сделать информатику математику и биологию, очень важно'" +
                    ",determine next date using formula: current date + offset, return json {date: currentDate}, date в формате LocalDateTime",
                currentDate);
        System.out.println(promptText);
        ChatResponse response = ollamaChatModel.call(new Prompt(promptText));
        return response.getResult()
            .getOutput()
            .getText();
    }
}
