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
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "metric_logs")
public class MetricLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "metric_id")
    private String metricId;

    private BigDecimal value;
    private Timestamp timestamp;

}
