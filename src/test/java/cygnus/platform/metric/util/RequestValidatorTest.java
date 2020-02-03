package cygnus.platform.metric.util;

import cygnus.platform.metric.dto.MetricLogRequest;
import cygnus.platform.metric.exception.InvalidRequestException;
import cygnus.platform.metric.model.Metric;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

@RunWith(MockitoJUnitRunner.class)
public class RequestValidatorTest {

    @Test(expected = InvalidRequestException.class)
    public void emptyMetricRequestThrowsException() {
        RequestValidator.validateMetricRequest(null);
    }

    @Test(expected = InvalidRequestException.class)
    public void nullMetricIdThrowsExceptionForValidateMetricRequest() {
        final Metric metricRequest = new Metric(null, "description");
        RequestValidator.validateMetricRequest(metricRequest);
    }

    @Test(expected = InvalidRequestException.class)
    public void blankMetricIdThrowsException() {
        final Metric metric = new Metric("", "description");
        RequestValidator.validateMetricRequest(metric);
    }

    @Test(expected = InvalidRequestException.class)
    public void nullMetricIdForMetricLogVerificationThrowsException() {
        RequestValidator.validateMetricLogRequest(new MetricLogRequest(), null);
    }

    @Test(expected = InvalidRequestException.class)
    public void nullMetricIdOnMetricLogObjectThrowsException() {
        final MetricLogRequest metricLogRequest = new MetricLogRequest(null, BigDecimal.ZERO);
        RequestValidator.validateMetricLogRequest(metricLogRequest, "id");
    }

    @Test(expected = InvalidRequestException.class)
    public void nullMetricLogValueThrowsException() {
        final MetricLogRequest metricLogRequest = new MetricLogRequest("id", null);
        RequestValidator.validateMetricLogRequest(metricLogRequest, "id");
    }

    @Test(expected = InvalidRequestException.class)
    public void mismatchingIdsThrowsExceptionForMetricLogRequest() {
        final MetricLogRequest metricLogRequest = new MetricLogRequest("id", BigDecimal.ZERO);
        RequestValidator.validateMetricLogRequest(metricLogRequest, "id2");
    }

    @Test
    public void success() {
        final MetricLogRequest metricLogRequest = new MetricLogRequest("id", BigDecimal.ZERO);
        RequestValidator.validateMetricLogRequest(metricLogRequest, "id");
    }

}
