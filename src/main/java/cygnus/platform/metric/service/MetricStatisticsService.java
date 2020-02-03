package cygnus.platform.metric.service;

import cygnus.platform.metric.dal.MetricLogJpaRepository;
import cygnus.platform.metric.dal.MetricStatisticsJpaRepository;
import cygnus.platform.metric.model.MetricLog;
import cygnus.platform.metric.model.MetricStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MetricStatisticsService {

    private MetricStatisticsJpaRepository metricStatisticsJpaRepository;
    private MetricLogJpaRepository metricLogJpaRepository;

    @Autowired
    public MetricStatisticsService(
            final MetricStatisticsJpaRepository metricStatisticsJpaRepository,
            final MetricLogJpaRepository metricLogJpaRepository
    ) {
        this.metricStatisticsJpaRepository = metricStatisticsJpaRepository;
        this.metricLogJpaRepository = metricLogJpaRepository;
    }

    public MetricStatistics findMetricStatisticsByMetricId(final String metricId) {

        return Optional.ofNullable(metricStatisticsJpaRepository
                .getMetricStatisticsByMetricId(metricId)).orElse(updateMetricStatisticsForMetricId(metricId));
    }

    public MetricStatistics updateMetricStatisticsForMetricId(final String metricId) {

        final List<MetricLog> metricLogs = metricLogJpaRepository.getMetricLogsByMetricId(metricId);

        if (metricLogs.isEmpty()) {
            final MetricStatistics metricStatistics = new MetricStatistics();
            metricStatistics.setMetricId(metricId);
            metricStatistics.setMedian(BigDecimal.ZERO);
            metricStatistics.setMean(BigDecimal.ZERO);
            metricStatistics.setMinValue(BigDecimal.ZERO);
            metricStatistics.setMaxValue(BigDecimal.ZERO);
            metricStatisticsJpaRepository.save(metricStatistics);
            return metricStatistics;
        }

        final MetricStatistics updatedStatistics = calculateStatistics(metricLogs, metricId);

        final Optional<MetricStatistics> metricStatistics =
                Optional.ofNullable(metricStatisticsJpaRepository.getMetricStatisticsByMetricId(metricId));

        if (!metricStatistics.isPresent()) {
            metricStatisticsJpaRepository.save(updatedStatistics);
            return updatedStatistics;
        } else {
            final MetricStatistics toUpdateStatistics = metricStatistics.get();
            toUpdateStatistics.setMaxValue(updatedStatistics.getMaxValue());
            toUpdateStatistics.setMean(updatedStatistics.getMean());
            toUpdateStatistics.setMedian(updatedStatistics.getMedian());
            toUpdateStatistics.setMinValue(updatedStatistics.getMinValue());
            metricStatisticsJpaRepository.save(toUpdateStatistics);
            return toUpdateStatistics;
        }
    }

    private MetricStatistics calculateStatistics(final List<MetricLog> metricLogs, final String metricId) {
        final List<BigDecimal> values = metricLogs.stream().map(MetricLog::getValue).collect(Collectors.toList());
        Collections.sort(values, BigDecimal::compareTo);

        final MetricStatistics metricStatistics = new MetricStatistics();
        metricStatistics.setMetricId(metricId);
        metricStatistics.setMean(calculateMean(values));
        metricStatistics.setMedian(calculateMedian(values));
        metricStatistics.setMaxValue(values.get(values.size() - 1));
        metricStatistics.setMinValue(values.get(0));
        return metricStatistics;
    }

    private BigDecimal calculateMean(final List<BigDecimal> values) {
        Double sum = 0.0;
        for (int i = 0; i < values.size(); i++) {
            sum += values.get(i).doubleValue();
        }
        return BigDecimal.valueOf(sum / values.size());
    }

    private BigDecimal calculateMedian(final List<BigDecimal> values) {
        final Integer middle = values.size() / 2;
        if (values.size() % 2 == 1) {
            return values.get(middle);
        } else {
            return BigDecimal.valueOf(values.get(middle - 1).doubleValue() + values.get(middle).doubleValue() / 2.0);
        }
    }

}
