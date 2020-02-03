package cygnus.platform.metric.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/info")
public class InfoController {

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, String> getInfo() {
        final Map<String, String> infoResponse = new HashMap<>();
        infoResponse.put("serviceName", "metric-service");
        infoResponse.put("author", "Brian Ocasio");
        return infoResponse;
    }
}
