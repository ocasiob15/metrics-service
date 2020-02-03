package cygnus.platform.metric.controller;

import cygnus.platform.metric.dto.MetricStatisticsResponse;
import cygnus.platform.metric.exception.InvalidRequestException;
import cygnus.platform.metric.exception.ResourceNotFoundException;
import cygnus.platform.metric.service.MetricService;
import cygnus.platform.metric.service.MetricStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "/metrics/{metricId}/statistics")
public class MetricStatisticsController {

    private static final Logger LOG = LoggerFactory.getLogger(MetricStatisticsController.class);

    private MetricService metricService;
    private MetricStatisticsService metricStatisticsService;

    public MetricStatisticsController(
            final MetricService metricService,
            final MetricStatisticsService metricStatisticsService
    ) {
        this.metricService = metricService;
        this.metricStatisticsService = metricStatisticsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public MetricStatisticsResponse getMetricStatistics(
            @PathVariable("metricId") final String metricId
    ) {
        metricService.validateMetricExistById(metricId);

        LOG.info(String.format("Retrieving statistics for metric %s", metricId));
        return new MetricStatisticsResponse(metricStatisticsService.findMetricStatisticsByMetricId(metricId));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFound(final HttpServletResponse response, final RuntimeException e)
            throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

}
