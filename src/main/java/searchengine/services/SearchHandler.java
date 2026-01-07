package services;

package searchengine.services.search;import org.springframework.stereotype.Service;
import searchengine.dto.search.SearchRequestDto;
import searchengine.dto.search.SearchResponse;

import java.util.List;

@Service
public class SearchHandler {
    public SearchResponse getResult(SearchRequestDto request) {
        // stubbed data
        List<String> results = List.of("Найдено по запросу: " + request.getQuery(),
                "Сайт: " + (request.getSite() != null ? request.getSite() : "все сайты"));
        return new SearchResponse(true, results.size(), results);
    }
}