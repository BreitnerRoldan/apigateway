package com.service.gateway.security;

import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

	@Value("#{systemEnvironment['JWT_SECRET']}")
	private String secret;

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);

			return exchange.getResponse().setComplete();
		}

		String token = authHeader.substring(7);// quita Bearer

		try {

			Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();

			String user = claims.getSubject();
			System.out.print("Token Valido. Usuario: " + user);

			exchange.getRequest().mutate().headers(HttpHeaders -> HttpHeaders.add("user-id", user)).build();
		} catch (Exception e) {
			exchange.getResponse().setComplete();
		}

		return chain.filter(exchange);
	}

}
