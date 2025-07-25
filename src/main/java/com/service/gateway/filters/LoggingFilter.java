package com.service.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
	
		String path = exchange.getRequest().getURI().getPath();
		String method = exchange.getRequest().getMethod().name();
		System.out.println("Solicitud Recibida: " + method + " " + path);
		
		return chain.filter(exchange)
				.then(Mono.fromRunnable(() ->
					System.out.println("Respuesta enviada para: " + method + " " + path)
				));
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -1; //prioridad alta
	}

}
