package com.toyproject.notTodoList.domain.auth.jwt;

import com.toyproject.notTodoList.core.security.PrincipalDetails;
import com.toyproject.notTodoList.domain.auth.application.AuthService;
import com.toyproject.notTodoList.domain.auth.application.dto.req.JwtFilterRequest;
import com.toyproject.notTodoList.domain.member.application.service.MemberService;
import com.toyproject.notTodoList.domain.member.domain.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;

    private final MemberService memberService;
    private final AuthService authService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        JwtFilterRequest jwtFilterRequest = JwtFilterRequest.builder()
                .authorization(request.getHeader("Authorization"))
                .refreshToken(request.getHeader("refresh-token"))
                .build();

        String username = null;
        String accessToken = null;
        Long extractedId = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            accessToken = jwtFilterRequest.getAuthorization().substring(7);
            extractedId = jwtHelper.extractUserId(accessToken);
            username = memberService.findUsernameById(extractedId);
            jwtFilterRequest.setUsername(username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username, null, new ArrayList<>());
            if (jwtHelper.isTokenExpired(accessToken)) {
                String updatedRefreshToken = jwtHelper.updateRefreshToken();
                jwtFilterRequest.setUpdateRefreshToken(updatedRefreshToken);
                jwtFilterRequest.setUpdatedExpiryDate(jwtHelper.extractExpiration(updatedRefreshToken));
                authService.updateRefreshToken(jwtFilterRequest);
            }
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);
    }

}
