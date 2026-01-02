package searchengine.dto.statistics;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Data
public class StatisticsData {
    private searchengine.dto.statistics.TotalStatistics total;
    private List<searchengine.dto.statistics.DetailedStatisticsItem> detailed;
}

