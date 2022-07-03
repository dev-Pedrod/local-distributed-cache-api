package com.devpedrod.localdistributedcacheapi.services;

import com.devpedrod.localdistributedcacheapi.client.ViaCepClient;
import com.devpedrod.localdistributedcacheapi.dto.CepResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CepService {

    @Autowired
    private ViaCepClient viaCepClient;
    @Autowired
    private ObjectMapper objectMapper;

    public Mono<CepResponse> findByCep(String cep) {
        return viaCepClient
                .findByCep(cep).flatMap(this::handleResponse);
    }

    private Mono<CepResponse> handleResponse(String response) {
        if(!response.isEmpty()){
            try {
                return Mono.just(objectMapper.readValue(response, CepResponse.class));
            }catch (Exception ex){
                log.error("Erro ao tentar converter resposta do CEP. {}", ex.getMessage());
            }
        }
        return Mono.empty();
    }
}
