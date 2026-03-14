package com.prakash.ecommerce.Api_Gateway.Filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoggingFilter implements GlobalFilter, Ordered
{
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //Pre Filter
        log.info("Logging from PRE GlobalLoggingFilter : {}",exchange.getRequest().getURI());


        //.then part is postFilter
       return chain.filter(exchange).then(Mono.fromRunnable(()->{

           log.info("Logging from POST GlobalLoggingFilter , status code : {}",exchange.getResponse().getStatusCode());

       }));

    }

    @Override
    public int getOrder() {
        return 2;
    }
}
