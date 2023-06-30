package com.aritmetic.op.api.filters;

import com.aritmetic.op.api.repositories.TokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public final JwtService jwtService;
    public final UserDetailsService userDetailsService;
    public final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, ExpiredJwtException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                final String jwtToken = authHeader.substring(7);
                final String username = jwtService.extractUsername(jwtToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                    if (isTokenValid(jwtToken, userDetails)) {
                        setTokenToSecurityContext(request, userDetails, false);
                    }
                }
            } catch (ExpiredJwtException e) {
                setTokenToSecurityContext(request, null, true);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isTokenValid(String jwtToken, UserDetails userDetails) {
//        (tokenRepository.findByToken(jwtToken)
//                .map(t -> !t.isExpired() && !t.isRevoked())
//                .orElse(false)
        boolean isTokenValid = jwtService.isTokenValid(jwtToken, userDetails);
        return isTokenValid;
    }

    private void setTokenToSecurityContext(HttpServletRequest request, UserDetails userDetails, boolean tokenExpired) {
        UsernamePasswordAuthenticationToken authToken = tokenExpired ?
                new UsernamePasswordAuthenticationToken(new HashMap<>(), null) :
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }


}
