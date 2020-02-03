package cygnus.platform.metric.controller;

import cygnus.platform.metric.dto.MetricLogRequest;
import cygnus.platform.metric.dto.MetricLogResponse;
import cygnus.platform.metric.model.MetricLog;
import cygnus.platform.metric.service.MetricLogService;
import cygnus.platform.metric.service.MetricService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MetricLogControllerTest {

    private static final String METRIC_ID = "metricId";
    private static final Integer ID = 1;
    private static final Timestamp TIMESTAMP = Timestamp.from(Instant.now());
    private static final BigDecimal VALUE = BigDecimal.ZERO;

    @Mock
    private MetricLogService metricLogService;

    @Mock
    private MetricService metricService;

    private MetricLogController metricLogController;

    @Before
    public void setup() {
        metricLogController = new MetricLogController(metricLogService, metricService);
    }

    @Test
    public void testCreateLog() {
        final MetricLogRequest metricLogRequest = new MetricLogRequest(METRIC_ID, BigDecimal.ZERO);
        when(metricLogService.createMetricLog(any(), any())).thenReturn(createMetricLog());

        final MetricLogResponse response = metricLogController.createMetricLog(METRIC_ID, metricLogRequest);
        verify(metricService).validateMetricExistById(METRIC_ID);
        Assert.assertEquals(METRIC_ID, response.getMetricId());
        Assert.assertEquals(TIMESTAMP, response.getTimestamp());
        Assert.assertEquals(VALUE, response.getValue());
    }

    private MetricLog createMetricLog() {
        final MetricLog metricLog = new MetricLog();
        metricLog.setMetricId(METRIC_ID);
        metricLog.setTimestamp(TIMESTAMP);
        metricLog.setValue(VALUE);
        metricLog.setId(ID);
        return metricLog;
    }
}
