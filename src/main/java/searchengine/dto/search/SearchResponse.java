package searchengine.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {
    private boolean result;
    private int count;
    private List<Object> data; // Используем Object, чтобы не зависеть от SearchData
}