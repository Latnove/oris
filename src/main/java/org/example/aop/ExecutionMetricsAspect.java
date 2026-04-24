package org.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class ExecutionMetricsAspect {

    private final Map<String, MethodStats> stats = new ConcurrentHashMap<>();

    @Around("@annotation(org.example.aop.TrackExecution)")
    public Object trackExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();

        stats.putIfAbsent(methodName, new MethodStats());
        MethodStats methodStats = stats.get(methodName);

        try {
            Object result = joinPoint.proceed();
            methodStats.success.incrementAndGet();
            return result;
        } catch (Throwable ex) {
            methodStats.failure.incrementAndGet();
            throw ex;
        }
    }

    public Map<String, MethodStats> getStats() {
        return stats;
    }

    public static class MethodStats {
        private final AtomicInteger success = new AtomicInteger();
        private final AtomicInteger failure = new AtomicInteger();

        public int getSuccess() {
            return success.get();
        }

        public int getFailure() {
            return failure.get();
        }
    }
}