package cygnus.platform.metric.controller;

import cygnus.platform.metric.dto.MetricStatisticsResponse;
import cygnus.platform.metric.model.MetricStatistics;
import cygnus.platform.metric.service.MetricService;
import cygnus.platform.metric.service.MetricStatisticsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MetricStatisticsControllerTest {

    private static final String METRIC_ID = "metricID";
    private static final BigDecimal MAX = BigDecimal.TEN;
    private static final BigDecimal MIN = BigDecimal.ZERO;
    private static final BigDecimal MEAN = BigDecimal.valueOf(5);
    private static final BigDecimal MEDIAN= BigDecimal.valueOf(5);

    @Mock
    private MetricService metricService;

    @Mock
    private MetricStatisticsService metricStatisticsService;

    private MetricStatisticsController metricStatisticsController;

    @Before
    public void setup() {
        metricStatisticsController = new MetricStatisticsController(metricService, metricStatisticsService);
    }

    @Test
    public void testGetStatistics() {
        when(metricStatisticsService.findMetricStatisticsByMetricId(METRIC_ID)).thenReturn(createMetricStatistics());
        metricStatisticsController.getMetricStatistics(METRIC_ID);
        verify(metricService).validateMetricExistById(METRIC_ID);

    }

    private MetricStatistics createMetricStatistics() {
        final MetricStatistics metricStatistics = new MetricStatistics();
        metricStatistics.setMetricId(METRIC_ID);
        metricStatistics.setMedian(MEDIAN);
        metricStatistics.setMean(MEAN);
        metricStatistics.setMinValue(MIN);
        metricStatistics.setMaxValue(MAX);
        return metricStatistics;
    }
}
