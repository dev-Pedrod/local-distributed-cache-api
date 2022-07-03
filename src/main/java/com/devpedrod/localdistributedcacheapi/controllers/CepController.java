package com.devpedrod.localdistributedcacheapi.controllers;

import com.devpedrod.localdistributedcacheapi.dto.CepResponse;
import com.devpedrod.localdistributedcacheapi.services.CepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/cep")
public class CepController {

    @Autowired
    private CepService cepService;

    @GetMapping("/{cep}")
    public Mono<CepResponse> getByCep(@PathVariable String cep){
        return cepService.findByCep(cep);
    }
}
