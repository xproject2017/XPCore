package com.xproject.auth;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hay on 2016/9/19.
 */
public class CrossFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Expose-Headers", "x-requested-with,content-type,CA-Token,Client-Flag");
            response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type,CA-Token,Client-Flag,X_Requested_With");

        try {
            filterChain.doFilter(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}