package cygnus.platform.metric.dal;

import cygnus.platform.metric.model.MetricStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MetricStatisticsJpaRepository extends JpaRepository<MetricStatistics, String> {

    @Query("SELECT o FROM MetricStatistics o WHERE o.metricId = :metricId")
    MetricStatistics getMetricStatisticsByMetricId(@Param("metricId") String metricId);
}
