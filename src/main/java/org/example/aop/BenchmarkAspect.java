package org.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class BenchmarkAspect {

    private final Map<String, List<Long>> timings = new ConcurrentHashMap<>();

    @Around("@annotation(org.example.aop.Benchmark)")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        long start = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long duration = System.nanoTime() - start;

            if (!timings.containsKey(methodName)) {
                timings.put(methodName, new ArrayList<>());
            }

            timings.get(methodName).add(duration);
        }
    }

    public Map<String, Map<String, Object>> getStats() {
        Map<String, Map<String, Object>> result = new HashMap<>();

        for (String method : timings.keySet()) {
            List<Long> times = new ArrayList<>(timings.get(method));
            if (times.isEmpty()) continue;

            Collections.sort(times);
            long min = times.get(0);
            long max = times.get(times.size() - 1);
            double avg = times.stream().mapToLong(Long::longValue).average().orElse(0);

            int index90 = (int)(times.size() * 0.9);
            long p90 = times.get(Math.min(index90, times.size() - 1));

            Map<String, Object> stats = new HashMap<>();
            stats.put("min", min);
            stats.put("max", max);
            stats.put("avg", avg);
            stats.put("p90", p90);

            result.put(method, stats);
        }

        return result;
    }
}