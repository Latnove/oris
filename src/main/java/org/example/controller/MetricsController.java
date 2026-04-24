package org.example.controller;

import org.example.aop.ExecutionMetricsAspect;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    private final ExecutionMetricsAspect aspect;

    public MetricsController(ExecutionMetricsAspect aspect) {
        this.aspect = aspect;
    }

    @GetMapping
    public Map<String, ExecutionMetricsAspect.MethodStats> getAllMetrics() {
        return aspect.getStats();
    }
}
