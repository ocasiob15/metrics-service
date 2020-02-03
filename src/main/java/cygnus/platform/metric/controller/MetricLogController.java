package cygnus.platform.metric.controller;

import cygnus.platform.metric.dto.MetricLogRequest;
import cygnus.platform.metric.dto.MetricLogResponse;
import cygnus.platform.metric.exception.InvalidRequestException;
import cygnus.platform.metric.exception.ResourceNotFoundException;
import cygnus.platform.metric.service.MetricLogService;
import cygnus.platform.metric.model.MetricLog;
import cygnus.platform.metric.service.MetricService;
import cygnus.platform.metric.util.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/metrics/{metricId}/logs")
public class MetricLogController {

    private static final Logger LOG = LoggerFactory.getLogger(MetricLogController.class);

    private MetricLogService metricLogService;
    private MetricService metricService;

    @Autowired
    public MetricLogController(
            final MetricLogService metricLogService,
            final MetricService metricService
    ) {
        this.metricLogService = metricLogService;
        this.metricService = metricService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public MetricLogResponse createMetricLog(
            @PathVariable("metricId") final String metricId,
            @RequestBody final MetricLogRequest metricLogRequest
    ) {
        RequestValidator.validateMetricLogRequest(metricLogRequest, metricId);
        LOG.info(String.format("Creating metric log for metric with id: %s", metricId));

        // Validate metric exist
        metricService.validateMetricExistById(metricId);

        final MetricLog metricLog = new MetricLog();
        metricLog.setTimestamp(Timestamp.from(Instant.now()));
        metricLog.setValue(metricLogRequest.getValue());
        metricLog.setMetricId(metricId);

        final MetricLog metricLogResponse = metricLogService.createMetricLog(metricLog, metricId);

        return new MetricLogResponse(metricLogResponse);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<MetricLogResponse> getMetricLogs(
            @PathVariable("metricId") final String metricId
    ) {
        LOG.info(String.format("Getting metric logs for metric with id: %s", metricId));
        metricService.validateMetricExistById(metricId);
        return metricLogService.getMetricLogsForMetricId(metricId).stream()
                .map(MetricLogResponse::new)
                .collect(Collectors.toList());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFound(final HttpServletResponse response, final RuntimeException e)
    throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public void handleInvalidRequest(final HttpServletResponse response, final RuntimeException e)
            throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
