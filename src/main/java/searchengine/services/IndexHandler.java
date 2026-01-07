package services;

import org.springframework.stereotype.Service;
import searchengine.dto.index.IndexResponse;

@Service
public class IndexHandler {
    public IndexResponse startIndexing() {
        return new IndexResponse(true, "Индексация запущена");
    }

    public IndexResponse stopIndexing() {
        return new IndexResponse(true, "Индексация остановлена");
    }

    public IndexResponse indexPage(String url) {
        return new IndexResponse(true, "Страница проиндексирована: " + url);
    }
}