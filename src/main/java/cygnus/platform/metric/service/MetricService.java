package cygnus.platform.metric.service;

import cygnus.platform.metric.dal.MetricJpaRepository;
import cygnus.platform.metric.exception.DuplicateResourceException;
import cygnus.platform.metric.exception.ResourceNotFoundException;
import cygnus.platform.metric.model.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MetricService {

    private MetricJpaRepository metricJpaRepository;

    @Autowired
    public MetricService(final MetricJpaRepository metricJpaRepository){
        this.metricJpaRepository = metricJpaRepository;
    }

    public Metric findMetricById(final String metricId) {
        final Optional<Metric> metricResponse = metricJpaRepository.findById(metricId);
        if (metricResponse.isPresent()) {
            return metricResponse.get();
        }
        throw new ResourceNotFoundException(String.format("Metric with id: %s not found", metricId));
    }

    public Metric createMetric(final Metric metric) {
        validateMetricDoesNotExistById(metric.getId());
        return metricJpaRepository.save(metric);
    }

    public Metric updateMetricById(final Metric metric) {
        return metricJpaRepository.save(metric);
    }

    public void deleteMetricById(final String metricId) {
        metricJpaRepository.deleteById(metricId);
    }

    public void validateMetricExistById(final String metricId) {
        if (!metricJpaRepository.existsById(metricId)) {
            throw new ResourceNotFoundException(String.format("Metric with id: %s does not exists", metricId));
        }
    }

    public void validateMetricDoesNotExistById(final String metricId) {
        if (metricJpaRepository.existsById(metricId)) {
            throw new DuplicateResourceException(String.format("Metric with id: %s already exists", metricId));
        }
    }

}
