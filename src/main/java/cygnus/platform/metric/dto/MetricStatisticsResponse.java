package cygnus.platform.metric.dto;

import cygnus.platform.metric.model.MetricStatistics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricStatisticsResponse {

    private String metricId;
    private BigDecimal mean;
    private BigDecimal median;
    private BigDecimal minValue;
    private BigDecimal maxValue;

    public MetricStatisticsResponse(final MetricStatistics metricStatistics) {
        this.metricId = metricStatistics.getMetricId();
        this.mean = metricStatistics.getMean();
        this.median = metricStatistics.getMedian();
        this.maxValue = metricStatistics.getMaxValue();
        this.minValue = metricStatistics.getMinValue();
    }
}
