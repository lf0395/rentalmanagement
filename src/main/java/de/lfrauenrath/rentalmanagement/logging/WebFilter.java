package de.lfrauenrath.rentalmanagement.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class WebFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = wrapRequest(request);
        ContentCachingResponseWrapper wrappedResponse = wrapResponse(response);

        try {
            doFilterWrapped(wrappedRequest, wrappedResponse, filterChain);
        } finally {
            logResponse(wrappedResponse);
            wrappedResponse.copyBodyToResponse();
        }
    }

    private ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
        return request instanceof ContentCachingRequestWrapper
                ? (ContentCachingRequestWrapper) request
                : new ContentCachingRequestWrapper(request);
    }

    private ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
        return response instanceof ContentCachingResponseWrapper
                ? (ContentCachingResponseWrapper) response
                : new ContentCachingResponseWrapper(response);
    }

    private void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        if (request.getQueryString() != null) {
            uri += "?" + request.getQueryString();
        }

        LOGGER.info(">> Request");
        LOGGER.info("{} {}", request.getMethod(), uri);

        byte[] requestBody = request.getContentAsByteArray();
        if (requestBody.length > 0) {
            LOGGER.info("Body: {}", new String(requestBody, StandardCharsets.UTF_8));
        }

        filterChain.doFilter(request, response);
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        LOGGER.info(">> Response");
        byte[] responseBody = response.getContentAsByteArray();
        if (responseBody.length > 0) {
            LOGGER.info("Body: {}", new String(responseBody, StandardCharsets.UTF_8));
        }
    }
}