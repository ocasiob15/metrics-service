package cygnus.platform.metric.dal;

import cygnus.platform.metric.model.MetricLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface MetricLogJpaRepository extends JpaRepository<MetricLog, String> {

    @Query("SELECT o FROM MetricLog o WHERE o.metricId = :metricId")
    List<MetricLog> getMetricLogsByMetricId(@Param("metricId") String metricId);

}
