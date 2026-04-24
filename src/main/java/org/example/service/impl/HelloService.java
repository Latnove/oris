package org.example.service.impl;

import org.example.aop.Benchmark;
import org.example.aop.TrackExecution;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @TrackExecution
    @Benchmark
    public String sayHello(String name) {
        return "Hello, %s".formatted(name);
    }
}
