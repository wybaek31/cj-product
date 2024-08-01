package com.cj.product.api.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Profile({"local"})
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.data.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedisServer() {
        try {
            int port = isRedisRunning(redisPort) ? findAvailablePort() : redisPort;
            redisServer = new RedisServer(port);
            redisServer.start();
            log.info("Embedded Redis started at port: {}", port);
        } catch (IOException e) {
            log.error("Failed to start embedded Redis", e);
        }
    }

    @PreDestroy
    public void stopRedisServer() throws IOException {
        if (redisServer != null) {
            redisServer.stop();
            log.info("Embedded Redis stopped");
        }
    }

    private boolean isRedisRunning(int port) throws IOException {
        Process process = executeGrepProcessCommand(port);
        return isRunning(process);
    }

    private int findAvailablePort() throws IOException {
        for (int port = 10000; port <= 65535; port++) {
            if (!isRunning(executeGrepProcessCommand(port))) {
                return port;
            }
        }
        throw new IllegalStateException("No available port found between 10000 and 65535");
    }

    private Process executeGrepProcessCommand(int port) throws IOException {
        String command = String.format("netstat -nat | grep LISTEN | grep %d", port);
        String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    private boolean isRunning(Process process) {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return input.lines().anyMatch(line -> !line.isEmpty());
        } catch (IOException e) {
            log.error("Error checking if Redis is running", e);
            return false;
        }
    }
}
