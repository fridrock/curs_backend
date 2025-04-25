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
        String inputText =
            "Сделать домашку через два дня, для этого надо сделать информатику математику и биологию, очень важно";
        String promptText =
            String.format("""
                inputText is: %s
                Current Date is 2025:04:25:19:26:10.  
                Parse date offset from inputText
                Determine next date using formula: current date + offset
                Determine title and description for task from inputText
                Determine priority of task from inputText
                Priority can be one of three values: LOW, NORMAL, HIGH
                return json with nextDate, title, description, priority, date in format LocalDateTime"
                """, inputText);
        System.out.println(promptText);
        ChatResponse response = ollamaChatModel.call(new Prompt(promptText));
        return response.getResult()
            .getOutput()
            .getText();
    }
}
