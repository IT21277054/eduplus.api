package com.ds.assignment.filter;

import com.ds.assignment.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RouteValidator routeValidator;

//    private RestTemplate restTemplate;
    @Autowired
    private JwtUtil jwtUtil;
    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(AuthenticationFilter.Config config) {
        return ((exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if(authHeader !=null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }
                try{
//                    restTemplate.getForObject("http://auth-service/validate?token"+authHeader,String.class);
                    jwtUtil.validateToken(authHeader);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return chain.filter(exchange);
        });

    }

    public static class Config{

    }

}
