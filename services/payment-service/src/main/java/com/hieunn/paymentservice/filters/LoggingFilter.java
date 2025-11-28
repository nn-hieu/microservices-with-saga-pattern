package com.hieunn.paymentservice.filters;

import com.hieunn.commonlib.filters.AbstractLoggingFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoggingFilter extends AbstractLoggingFilter {
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/h2-console");
    }
}
