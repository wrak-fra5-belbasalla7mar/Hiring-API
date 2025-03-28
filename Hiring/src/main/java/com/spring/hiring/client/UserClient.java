package com.spring.hiring.client;

import com.spring.hiring.dto.DepartmentDTO;
import com.spring.hiring.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class UserClient {

    private final WebClient webClient;
    private final String userServiceUrl;

    public UserClient(WebClient.Builder webClientBuilder, @Value("${user.service.url}") String userServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(userServiceUrl).build();
        this.userServiceUrl = userServiceUrl;
    }

    public Mono<UserDTO> getUserById(int userId) {
        return webClient.get()
                .uri("/manager/find?id={id}", userId)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .onErrorResume(e -> Mono.empty());
    }
}