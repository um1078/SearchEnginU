package searchengine.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.StatisticsService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final StatisticsService statisticsService;

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    // Заглушка для запуска индексации
    @GetMapping("/startIndexing")
    public ResponseEntity<Map<String, Object>> startIndexing() {
        Map<String, Object> response = new HashMap<>();
        response.put("result", true);
        return ResponseEntity.ok(response);
    }

    // Заглушка для остановки индексации
    @GetMapping("/stopIndexing")
    public ResponseEntity<Map<String, Object>> stopIndexing() {
        Map<String, Object> response = new HashMap<>();
        response.put("result", true);
        return ResponseEntity.ok(response);
    }

    // Заглушка для индексации отдельной страницы
    @PostMapping("/indexPage")
    public ResponseEntity<Map<String, Object>> indexPage(@RequestParam String url) {
        Map<String, Object> response = new HashMap<>();
        response.put("result", true);
        return ResponseEntity.ok(response);
    }

    // Заглушка для поиска (принимает Object, чтобы не зависеть от SearchRequestDto)
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> search(@RequestParam(required = false) String query) {
        Map<String, Object> response = new HashMap<>();
        response.put("result", true);
        response.put("count", 0);
        response.put("data", new Object[0]); // Пустой массив результатов
        return ResponseEntity.ok(response);
    }
}