package ru.fridrock.jir_backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fridrock.jir_backend.dto.AiDto;
import ru.fridrock.jir_backend.dto.AiTaskDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {
    private final OllamaChatModel ollamaChatModel;
    private final ObjectMapper objectMapper;

    @GetMapping
    public AiTaskDto getAi(AiDto dto) throws JsonProcessingException {
        String currentDate = LocalDateTime.now()
            .toString();
        String inputText =
            "Сделать домашку через два дня, для этого надо сделать информатику математику и биологию, очень важно";
        String promptText =
            String.format("""
                inputText is: %s
                Current Date is %s.  
                Parse date offset from inputText
                Determine next date using formula: current date + offset
                Determine title - name for task from inputText, don't provide information about date, or priority
                Determine description for task from inputText, if there is no data for description, just write empty string
                If there is a lot of data, you can subtract it on subparagraphs and format each on new line
                Determine priority of task from inputText
                Priority can be one of three values: LOW, HIGH, CRITICAL
                return json with nextDate, title, description, priority"
                """, dto.message(), LocalDateTime.now());

        ChatResponse response = ollamaChatModel.call(new Prompt(promptText));
        String output = response.getResult()
            .getOutput()
            .getText()
            .replaceAll("```json|```", "")
            .trim();
        AiTaskDto taskDto = objectMapper.readValue(output, AiTaskDto.class);
        return taskDto;
    }
}
