package searchengine.dto.search;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class SearchResponse {
    private final boolean result;
    private final int count;
    private final List<SearchData> data;

    public class SearchResponse {
        private boolean result;
        private int count;
        private List<String> data;

        // simplify: list of snippets/links
        public SearchResponse() {
        }

        public SearchResponse(boolean result, int count, List<String> data) {
            this.result = result;
            this.count = count;
            this.data = data;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<String> getData() {
            return data;
        }

        public void setData(List<String> data) {
            this.data = data;
        }
    }
}
