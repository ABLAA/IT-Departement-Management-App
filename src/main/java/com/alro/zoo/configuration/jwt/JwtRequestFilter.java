package com.alro.zoo.configuration.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import com.alro.zoo.login.LoginService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	
	@Autowired
	private LoginService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");
		if( requestTokenHeader!= null) {
			String jwtToken = removeBearerPrefixFromTheToken(requestTokenHeader);
			String userCode = tryToGetUserCodefromToken(jwtToken);
			validateToken(jwtToken, userCode, request);
		}
		
		chain.doFilter(request, response);
	}
	
	private String removeBearerPrefixFromTheToken(String requestTokenHeader) {
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			return requestTokenHeader.substring(7);
		} else {
			System.err.println("JWT Token does not begin with Bearer String");
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT Token does not begin with Bearer String");
		}
	}
	
	private String tryToGetUserCodefromToken(String jwtToken) {
		try {
			return jwtTokenUtil.getUserCodeFromToken(jwtToken);
		} catch (IllegalArgumentException e) {
			System.out.println("Unable to get JWT Token");
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unable to get JWT Token");
		} catch (ExpiredJwtException e) {
			System.out.println("JWT Token has expired");
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT Token has expired");
		}
	}
	
	private void validateToken(String jwtToken, String userCode, HttpServletRequest request) {
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByCode(userCode);
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				configureSpringSecurityToManuallySetAuthentication(userDetails, request);
			}
		}
	}
	
	private void configureSpringSecurityToManuallySetAuthentication(UserDetails userDetails, HttpServletRequest request) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		usernamePasswordAuthenticationToken
				.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}
}
