package cygnus.platform.metric.controller;

import cygnus.platform.metric.model.Metric;
import cygnus.platform.metric.service.MetricService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MetricControllerTest {

    private static final String METRIC_ID = "metricId";
    private static final String DESCRIPTION = "metricDescription";

    @Mock
    private MetricService metricService;

    private MetricController metricController;

    @Before
    public void setup() {
        metricController = new MetricController(metricService);
    }

    @Test
    public void testGetMetrics() {
        when(metricService.findMetricById(METRIC_ID)).thenReturn(createMetric());
        final Metric metric = metricController.getMetricById(METRIC_ID);
        verify(metricService).validateMetricExistById(METRIC_ID);

        Assert.assertEquals(METRIC_ID, metric.getId());
        Assert.assertEquals(DESCRIPTION, metric.getDescription());
    }

    @Test
    public void testCreateMetric() {
        final Metric metricRequest = createMetric();
        when(metricService.createMetric(metricRequest)).thenReturn(metricRequest);
        final Metric metric = metricController.createMetric(metricRequest).getBody();
        Assert.assertEquals(METRIC_ID, metric.getId());
        Assert.assertEquals(DESCRIPTION, metric.getDescription());
    }

    private Metric createMetric() {
        return new Metric(METRIC_ID, DESCRIPTION);
    }

}
