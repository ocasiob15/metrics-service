package cygnus.platform.metric.controller;

import cygnus.platform.metric.exception.DuplicateResourceException;
import cygnus.platform.metric.exception.InvalidRequestException;
import cygnus.platform.metric.exception.ResourceNotFoundException;
import cygnus.platform.metric.model.Metric;
import cygnus.platform.metric.service.MetricService;
import cygnus.platform.metric.util.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping(path = "/metrics")
public class MetricController {

    private static final Logger LOG = LoggerFactory.getLogger(MetricController.class);

    private MetricService metricService;

    @Autowired
    public MetricController(
            final MetricService metricService
    ) {
        this.metricService = metricService;
    }

    @RequestMapping(path = "/{metricId}", method = RequestMethod.GET)
    public Metric getMetricById(
            @PathVariable("metricId") final String metricId
    ) {
        metricService.validateMetricExistById(metricId);

        LOG.info(String.format("Retrieving metric with id: %s", metricId));
        return metricService.findMetricById(metricId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Metric> createMetric(
            @RequestBody final Metric metricRequest
    ) {
        RequestValidator.validateMetricRequest(metricRequest);
        LOG.info(String.format("Creating new metric %s", metricRequest.getId()));
        final Metric response = metricService.createMetric(metricRequest);
        return ResponseEntity.created(URI.create(response.getId())).body(response);
    }

    @RequestMapping(path = "/{metricId}", method = RequestMethod.PUT)
    public Metric updateMetricById(
            @PathVariable("metricId") final String metricId,
            @RequestBody final Metric metricRequest
    ) {
        RequestValidator.validateMetricRequest(metricRequest);
        metricService.validateMetricExistById(metricId);

        LOG.info(String.format("Updated metric with id: %s", metricId));
        return metricService.updateMetricById(metricRequest);
    }

    @RequestMapping(path = "/{metricId}", method = RequestMethod.DELETE)
    public void deleteMetricById(
            @PathVariable("metricId") final String metricId
    ) {
        metricService.validateMetricExistById(metricId);
        LOG.info(String.format("Deleting metric with id: %s", metricId));
        metricService.deleteMetricById(metricId);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFound(final HttpServletResponse response, final RuntimeException e)
            throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public void handleDuplicateResource(final HttpServletResponse response, final RuntimeException e)
            throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public void handleInvalidRequest(final HttpServletResponse response, final RuntimeException e)
            throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
