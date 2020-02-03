package cygnus.platform.metric.dal;

import cygnus.platform.metric.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MetricJpaRepository extends JpaRepository<Metric, String> {

}
