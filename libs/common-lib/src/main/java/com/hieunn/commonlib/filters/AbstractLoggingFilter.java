package com.hieunn.commonlib.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public abstract class AbstractLoggingFilter extends OncePerRequestFilter implements Ordered {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        long start = System.currentTimeMillis();

        String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        );
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String fullApi = uri + (query != null ? ("?" + query) : "");

        log.info(
                "[REQUEST] {} {} | timestamp = {}",
                method,
                fullApi,
                timestamp
        );

        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;

        log.info(
                "[RESPONSE] {} {} | status = {} | duration = {}ms | timestamp = {}",
                method,
                fullApi,
                response.getStatus(),
                duration,
                timestamp
        );
    }
}
