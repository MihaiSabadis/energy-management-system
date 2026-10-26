package com.mihai.auth_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class UserClient {

    private final RestClient restClient;

    public UserClient(@Value("${user-service.url}") String baseUrl){
        this.restClient = RestClient.create(baseUrl);
    }

    public record ProfileRequest(String username, String fullName, String email, String address){}
    public record ProfileResponse(UUID id){}

    public UUID createProfile(String username, String fullName, String email, String address){
        try{
            ProfileResponse res = restClient.post()
                    .uri("api/users")
                    .body(new ProfileRequest(username, fullName, email, address))
                    .retrieve()
                    .body(ProfileResponse.class);
            assert res != null;
            return res.id();
        }catch (HttpClientErrorException.Conflict e){
            throw new RuntimeException("Username or email already in use");
        }
    }
}
