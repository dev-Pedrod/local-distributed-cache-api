package com.devpedrod.localdistributedcacheapi.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocalCacheService {
    private static final String VALUE_FIELD = "value";
    private static final String EXPIRE_FIELD = "expire";
    private static final Map<String, Map<String, Object>> CACHE = new HashMap<>();

    @Value("${app-config.cache.ttl}")
    private Integer ttl;

    public Mono<String> save(String key, String value) {
        try {
            if(!exists(key)){
                Map<String, Object> data = new HashMap<>();
                data.put(VALUE_FIELD, value);
                data.put(EXPIRE_FIELD, LocalDateTime.now().plusSeconds(ttl));
                CACHE.put(key, data);
                log.info("Salvando cache para a chave: {}", key);
            }
        }catch (Exception ex){
            log.error("Erro ao tentar salvar cache para a chave: {}", key);
        }
        return Mono.just(value);
    }

    public Mono<String> get(String key){
        try {
            if(exists(key)){
                log.info("Cache existente para a chave: {}", key);
                Map<String, Object> data = CACHE.get(key);
                log.info("Retornando cache para a chave: {}", key);
                return Mono.just((String) data.get(VALUE_FIELD));
            }
            log.info("Cache não encontrado para a chave: {}", key);
        }catch(Exception ex){
            log.error("Erro ao tentar encontrar cache para a chave: {}", key);
        }
        return Mono.empty();
    }

    public Mono<Boolean> existsForKey(String key) {
        return Mono.just(exists(key));
    }

    public void removeExpiredKeys(){
        List<String> keysToRemove = CACHE
                .keySet()
                .stream()
                .filter(key -> isExpires((LocalDateTime) CACHE.get(key).get(EXPIRE_FIELD)))
                .collect(Collectors.toList());

        keysToRemove.forEach(CACHE::remove);
        if (!keysToRemove.isEmpty()) {
            log.info("{} chaves removidas", keysToRemove.size());
        }
    }

    private boolean exists(String key) {
        boolean exists = CACHE.containsKey(key);
        if(exists && isExpires((LocalDateTime) CACHE.get(key).get(EXPIRE_FIELD))){
            CACHE.remove(key);
            return false;
        }
        return exists;
    }

    private Boolean isExpires (LocalDateTime expiration) {
        return LocalDateTime.now().isAfter(expiration);
    }

    private void remove(String key){
        CACHE.remove(key);
    }
}
