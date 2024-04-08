package com.example.snapEvent.member.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// exceptionhandler로는 필터에서의 예외를 처리할수 없어서 필터를 새로 추가함.
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (TokenNotValidateException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, request, response, ex);
//        } catch (UsernameNotFoundException ex) {
//            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, response, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletRequest request,
                                 HttpServletResponse response, Throwable ex) throws IOException {

        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        response.getWriter().write(
                TokenErrorResponse.of(
                                HttpStatus.UNAUTHORIZED,
                                ex.getMessage(),
                                request
                        )
                        .convertToJson()
        );
    }
}

