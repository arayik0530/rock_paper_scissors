package com.nobel.rock_paper_scissors.security.jwt;

import com.nobel.rock_paper_scissors.repository.PlayerRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;
    private final PlayerRepository playerRepository;

    private static final String[] PATHS_TO_SKIP = {"/api/auth/", "/swagger/", "/v3/api-docs/", "/swagger-ui/"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();

        // Skip token validation for certain paths
        if (shouldSkipTokenValidation(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        try {
            Optional<String> optionalJwtToken = jwtTokenProvider.resolveToken(request);

            optionalJwtToken
                    .filter(jwtTokenProvider::validateToken)
                    .map(token -> jwtTokenProvider.getAuthentication(token, request, response))
                    .ifPresent(SecurityContextHolder.getContext()::setAuthentication);

        } catch (JwtAuthenticationException e) {
            //            exceptionHandlerController.handleFilterExceptions(jwtException, (HttpServletResponse) servletResponse);
            System.err.println("JWT Authentication Exception: " + e.getMessage());
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean shouldSkipTokenValidation(String path) {
        for (String skipPath : PATHS_TO_SKIP) {
            if (path.matches(skipPath)) {
                return true;
            }
        }
        return false;
    }
}
