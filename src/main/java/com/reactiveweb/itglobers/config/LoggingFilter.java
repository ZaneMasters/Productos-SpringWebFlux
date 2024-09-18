package com.reactiveweb.itglobers.config;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
public class LoggingFilter implements WebFilter {

    private BufferedWriter bufferedWriter;

    @PostConstruct
    public void init() throws IOException {
        // Usa BufferedWriter para una escritura más eficiente
        bufferedWriter = new BufferedWriter(new FileWriter("response_times.log", true));
    }

    @PreDestroy
    public void close() throws IOException {
        // Asegúrate de cerrar el writer cuando la aplicación se apague
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Instant start = Instant.now();

        return chain.filter(exchange).doFinally(signalType -> {
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);

            // Escritura al archivo de manera sincronizada
            synchronized (this) {
                try {
                    bufferedWriter.write("Request " + exchange.getRequest().getURI() + " took " + timeElapsed.toMillis() + " ms\n");
                    bufferedWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
