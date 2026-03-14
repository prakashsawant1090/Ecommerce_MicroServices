package com.prakash.ecommerce.Api_Gateway.Filters;

import com.prakash.ecommerce.Api_Gateway.Service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

            if(authHeader == null){
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();

            }

            String bearerToken = authHeader.split("Bearer ")[1];

            String userName = jwtService.extractUsername(bearerToken);

            log.info("{} is Authenticated and allowd to access the endpoint : {}",userName,exchange.getRequest().getURI());




            return chain.filter(exchange);
        });
    }

    public static class Config{ }
}
