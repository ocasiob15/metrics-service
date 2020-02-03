package cygnus.platform.metric.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricLogRequest {
    private String metricId;
    private BigDecimal value;
}
