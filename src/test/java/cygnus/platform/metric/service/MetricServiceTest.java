package cygnus.platform.metric.service;

import cygnus.platform.metric.dal.MetricJpaRepository;
import cygnus.platform.metric.model.Metric;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class MetricServiceTest {

    private static final String METRIC_ID = "id";
    private static final String METRIC_DESCRIPTION = "description";

    @Mock
    private MetricJpaRepository metricJpaRepository;

    private MetricService metricService;

    @Before
    public void setup() {
        metricService = new MetricService(metricJpaRepository);
    }

    @Test
    public void testFindMetricById() {
        Mockito.when(metricJpaRepository.findById(METRIC_ID)).thenReturn(Optional.ofNullable(createMetric()));
        final Metric metric = metricService.findMetricById(METRIC_ID);
        Assert.assertEquals(METRIC_ID, metric.getId());
        Assert.assertEquals(METRIC_DESCRIPTION, metric.getDescription());
    }

    @Test
    public void testUpdateMetricById() {
        final Metric metric = new Metric("someonthing", "asc");
        Mockito.when(metricJpaRepository.save(metric)).thenReturn(createMetric());

        final Metric response = metricService.updateMetricById(metric);
        Assert.assertEquals(METRIC_ID, response.getId());
        Assert.assertEquals(METRIC_DESCRIPTION, response.getDescription());
    }

    private Metric createMetric() {
        return new Metric(METRIC_ID, METRIC_DESCRIPTION);
    }
}
