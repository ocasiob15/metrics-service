package cygnus.platform.metric.service;

import cygnus.platform.metric.dal.MetricLogJpaRepository;
import cygnus.platform.metric.model.MetricLog;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
public class MetricLogServiceTest {

    private static final Integer ID = 1;
    private static final String METRIC_ID = "metricId";
    private static final BigDecimal METRIC_VALUE = BigDecimal.valueOf(10);
    private static final Timestamp TIMESTAMP = Timestamp.from(Instant.now());

    @Mock
    private MetricLogJpaRepository metricLogJpaRepository;

    @Mock
    private MetricStatisticsService metricStatisticsService;

    private MetricLogService metricLogService;

    @Before
    public void setup() {
        metricLogService = new MetricLogService(metricLogJpaRepository, metricStatisticsService);
    }

    @Test
    public void testCreateMetricLog() {
        final MetricLog metricLogRequest = createMetricLog();
        Mockito.when(metricLogJpaRepository.save(metricLogRequest)).thenReturn(createMetricLog());

        final MetricLog metricLog = metricLogService.createMetricLog(metricLogRequest, METRIC_ID);
        Assert.assertEquals(ID, metricLog.getId());
        Assert.assertEquals(METRIC_ID, metricLog.getMetricId());
        Assert.assertEquals(METRIC_VALUE.doubleValue(), metricLog.getValue().doubleValue(), 1);
        Assert.assertEquals(metricLog.getTimestamp().toInstant(), metricLog.getTimestamp().toInstant());
    }

    @Test
    public void testGetMetricLogsForMetric() {
        Mockito.when(metricLogJpaRepository.getMetricLogsByMetricId(METRIC_ID))
                .thenReturn(Collections.singletonList(createMetricLog()));

        final MetricLog metricLog = metricLogService.getMetricLogsForMetricId(METRIC_ID).get(0);
        Assert.assertEquals(ID, metricLog.getId());
        Assert.assertEquals(METRIC_ID, metricLog.getMetricId());
        Assert.assertEquals(METRIC_VALUE.doubleValue(), metricLog.getValue().doubleValue(), 1);
        Assert.assertEquals(metricLog.getTimestamp().toInstant(), metricLog.getTimestamp().toInstant());

    }


    private MetricLog createMetricLog() {
        final MetricLog metricLog = new MetricLog();
        metricLog.setId(ID);
        metricLog.setMetricId(METRIC_ID);
        metricLog.setTimestamp(TIMESTAMP);
        metricLog.setValue(METRIC_VALUE);
        return metricLog;
    }
}
