package cygnus.platform.metric.service;

import cygnus.platform.metric.dal.MetricLogJpaRepository;
import cygnus.platform.metric.model.MetricLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MetricLogService {

    private MetricLogJpaRepository metricLogJpaRepository;
    private MetricStatisticsService metricStatisticsService;

    @Autowired
    public MetricLogService(
            final MetricLogJpaRepository metricLogJpaRepository,
            final MetricStatisticsService metricStatisticsService
    ) {
        this.metricLogJpaRepository = metricLogJpaRepository;
        this.metricStatisticsService = metricStatisticsService;
    }

    public MetricLog createMetricLog(final MetricLog metricLog, final String metricId) {

        final MetricLog response = metricLogJpaRepository.save(metricLog);
        metricStatisticsService.updateMetricStatisticsForMetricId(metricId);

        return response;
    }

    public List<MetricLog> getMetricLogsForMetricId(final String metricId) {
        return metricLogJpaRepository.getMetricLogsByMetricId(metricId);
    }
}
