package com.pat.gateway.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.RewritePathGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class GatewayApplication {

    @Resource
    private RewritePathGatewayFilterFactory rewritePathGatewayFilterFactory;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        return builder.routes()
                .route("raken-api-v1", r -> r.path("/ra/**").and().header("X-API-VERSION", "1")
                        .filter(rewritePathGatewayFilterFactory.apply("/ra/(?<PATH>.*)", "${PATH}"))
                        .uri("http://localhost:8080")
                )
                .route("raken-api-v2", r -> r.path("/ra/**").and().header("X-API-VERSION", "2")
                        .filter(rewritePathGatewayFilterFactory.apply("/ra/(?<PATH>.*)", "${PATH}"))
                        .uri("http://localhost:8081")
                )
                .build();
    }

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

}
