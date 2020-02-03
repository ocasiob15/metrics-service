package cygnus.platform.metric.util;

import cygnus.platform.metric.dto.MetricLogRequest;
import cygnus.platform.metric.exception.InvalidRequestException;
import cygnus.platform.metric.model.Metric;

import java.util.Optional;

public abstract class RequestValidator {

    private static final String BLANK = "";

    public static void validateMetricRequest(final Metric metricRequest) {
        if (!Optional.ofNullable(metricRequest).isPresent()) {
            throw new InvalidRequestException("Metric can not be null");
        }

        if (!Optional.ofNullable(metricRequest.getId()).isPresent()) {
            throw new InvalidRequestException("No metric id specified");
        }

        if (BLANK.equals(metricRequest.getId())) {
            throw new InvalidRequestException("Metric id can not be empty");
        }
    }

    public static void validateMetricLogRequest(final MetricLogRequest metricLogRequest, final String metricId) {
        if (!Optional.ofNullable(metricId).isPresent()) {
            throw new InvalidRequestException("Metric log request is missing metric id in the url path");
        }

        if (!Optional.ofNullable(metricLogRequest.getMetricId()).isPresent()) {
            throw new InvalidRequestException("Metric log request is missing metric id");
        }

        if (!Optional.ofNullable(metricLogRequest.getValue()).isPresent()) {
            throw new InvalidRequestException("Metric log request is missing a value");
        }

        if (!metricId.equals(metricLogRequest.getMetricId())) {
            throw new InvalidRequestException("Request metric id and path metric id do not match");
        }
    }
}
