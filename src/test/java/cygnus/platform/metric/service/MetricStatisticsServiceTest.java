package cygnus.platform.metric.service;

import cygnus.platform.metric.dal.MetricLogJpaRepository;
import cygnus.platform.metric.dal.MetricStatisticsJpaRepository;
import cygnus.platform.metric.model.MetricLog;
import cygnus.platform.metric.model.MetricStatistics;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class MetricStatisticsServiceTest {

    private static final String METRIC_ID = "metricId";

    @Mock
    private MetricStatisticsJpaRepository metricStatisticsJpaRepository;

    @Mock
    private MetricLogJpaRepository metricLogJpaRepository;

    private MetricStatisticsService metricStatisticsService;

    @Before
    public void setup() {
        metricStatisticsService = new MetricStatisticsService(metricStatisticsJpaRepository, metricLogJpaRepository);
    }

    @Test
    public void testUpdateStatisticsNoLogs() {
        Mockito.when(metricLogJpaRepository.getMetricLogsByMetricId(METRIC_ID)).thenReturn(Collections.EMPTY_LIST);
        final MetricStatistics metricStatistics = metricStatisticsService.updateMetricStatisticsForMetricId(METRIC_ID);
        Assert.assertEquals(BigDecimal.ZERO, metricStatistics.getMaxValue());
        Assert.assertEquals(BigDecimal.ZERO, metricStatistics.getMean());
        Assert.assertEquals(BigDecimal.ZERO, metricStatistics.getMedian());
        Assert.assertEquals(BigDecimal.ZERO, metricStatistics.getMinValue());
    }

    @Test
    public void testUpdateStatistics() {
        Mockito.when(metricStatisticsJpaRepository.getMetricStatisticsByMetricId(METRIC_ID))
                .thenReturn(createMetricStatistics());
        Mockito.when(metricLogJpaRepository.getMetricLogsByMetricId(METRIC_ID)).thenReturn(createMetricLogs());
        final MetricStatistics metricStatistics = metricStatisticsService.updateMetricStatisticsForMetricId(METRIC_ID);

        Assert.assertNotEquals(BigDecimal.TEN, metricStatistics.getMaxValue());
        Assert.assertNotEquals(BigDecimal.valueOf(5), metricStatistics.getMean());
        Assert.assertNotEquals(BigDecimal.valueOf(5), metricStatistics.getMedian());
        Assert.assertNotEquals(BigDecimal.ZERO, metricStatistics.getMinValue());
    }

    @Test
    public void testUpdateStatisticsNoStatisticsAvailable() {
        Mockito.when(metricStatisticsJpaRepository.getMetricStatisticsByMetricId(METRIC_ID))
                .thenReturn(null);
        Mockito.when(metricLogJpaRepository.getMetricLogsByMetricId(METRIC_ID)).thenReturn(createMetricLogs());
        final MetricStatistics metricStatistics = metricStatisticsService.updateMetricStatisticsForMetricId(METRIC_ID);

        Assert.assertNotEquals(BigDecimal.TEN, metricStatistics.getMaxValue());
        Assert.assertNotEquals(BigDecimal.valueOf(5), metricStatistics.getMean());
        Assert.assertNotEquals(BigDecimal.valueOf(5), metricStatistics.getMedian());
        Assert.assertNotEquals(BigDecimal.ZERO, metricStatistics.getMinValue());
    }

    @Test
    public void testUpdateStatisticsKnownAnswer() {
        Mockito.when(metricStatisticsJpaRepository.getMetricStatisticsByMetricId(METRIC_ID))
                .thenReturn(createMetricStatistics());
        Mockito.when(metricLogJpaRepository.getMetricLogsByMetricId(METRIC_ID)).thenReturn(createMetricLogsForKnownTesting());

        final MetricStatistics metricStatistics = metricStatisticsService.updateMetricStatisticsForMetricId(METRIC_ID);

        Assert.assertEquals(BigDecimal.TEN, metricStatistics.getMaxValue());
        Assert.assertEquals(BigDecimal.valueOf(5.333333333333333), metricStatistics.getMean());
        Assert.assertEquals(BigDecimal.valueOf(5), metricStatistics.getMedian());
        Assert.assertEquals(BigDecimal.ONE, metricStatistics.getMinValue());
    }

    private MetricStatistics createMetricStatistics() {
        final MetricStatistics metricStatistics = new MetricStatistics();
        metricStatistics.setMaxValue(BigDecimal.TEN);
        metricStatistics.setMinValue(BigDecimal.ZERO);
        metricStatistics.setMean(BigDecimal.valueOf(5));
        metricStatistics.setMedian(BigDecimal.valueOf(5));
        metricStatistics.setMetricId(METRIC_ID);
        return metricStatistics;
    }

    private List<MetricLog> createMetricLogs() {
        final MetricLog metricLog1 = new MetricLog();
        metricLog1.setValue(BigDecimal.valueOf(10));
        metricLog1.setTimestamp(Timestamp.from(Instant.now()));
        metricLog1.setMetricId(METRIC_ID);

        final MetricLog metricLog2 = new MetricLog();
        metricLog2.setValue(BigDecimal.valueOf(20));
        metricLog2.setTimestamp(Timestamp.from(Instant.now()));
        metricLog2.setMetricId(METRIC_ID);

        final List<MetricLog> metricLogArrayList = new ArrayList<>();
        metricLogArrayList.add(metricLog1);
        metricLogArrayList.add(metricLog2);
        return metricLogArrayList;
    }

    private List<MetricLog> createMetricLogsForKnownTesting() {
        final MetricLog metricLog1 = new MetricLog();
        metricLog1.setValue(BigDecimal.valueOf(1));
        metricLog1.setTimestamp(Timestamp.from(Instant.now()));
        metricLog1.setMetricId(METRIC_ID);

        final MetricLog metricLog2 = new MetricLog();
        metricLog2.setValue(BigDecimal.valueOf(5));
        metricLog2.setTimestamp(Timestamp.from(Instant.now()));
        metricLog2.setMetricId(METRIC_ID);

        final MetricLog metricLog3 = new MetricLog();
        metricLog3.setValue(BigDecimal.valueOf(10));
        metricLog3.setTimestamp(Timestamp.from(Instant.now()));
        metricLog3.setMetricId(METRIC_ID);

        final List<MetricLog> metricLogArrayList = new ArrayList<>();
        metricLogArrayList.add(metricLog1);
        metricLogArrayList.add(metricLog2);
        metricLogArrayList.add(metricLog3);
        return metricLogArrayList;
    }
}
