package org.example.controller;

import org.example.aop.BenchmarkAspect;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/benchmark")
public class BenchmarkController {

    private final BenchmarkAspect benchmarkAspect;

    public BenchmarkController(BenchmarkAspect benchmarkAspect) {
        this.benchmarkAspect = benchmarkAspect;
    }

    @GetMapping
    public Map<String, Map<String, Object>> getStats() {
        return benchmarkAspect.getStats();
    }
}