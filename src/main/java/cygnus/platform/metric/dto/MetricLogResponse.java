package cygnus.platform.metric.dto;

import cygnus.platform.metric.model.MetricLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricLogResponse {

    private String metricId;
    private BigDecimal value;
    private Timestamp timestamp;

    public MetricLogResponse(final MetricLog metricLog) {
        this.metricId = metricLog.getMetricId();
        this.value = metricLog.getValue();
        this.timestamp = metricLog.getTimestamp();
    }

}
