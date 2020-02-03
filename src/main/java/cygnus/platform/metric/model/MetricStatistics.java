package cygnus.platform.metric.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metric_statistics")
public class MetricStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "metric_id")
    private String metricId;
    private BigDecimal mean;
    private BigDecimal median;

    @Column(name = "min_value")
    private BigDecimal minValue;

    @Column(name = "max_value")
    private BigDecimal maxValue;

}

