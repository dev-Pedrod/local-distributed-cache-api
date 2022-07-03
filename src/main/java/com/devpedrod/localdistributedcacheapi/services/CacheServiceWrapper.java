package com.devpedrod.localdistributedcacheapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CacheServiceWrapper {

    @Autowired
    private LocalCacheService localCacheService;

    @Value("${app-config.cache.redis-enabled}")
    private Boolean redisEnabled;

    public Mono<String> get(String key){
        if(redisEnabled){
            return Mono.empty();
        }
        return localCacheService.get(key);
    }

    public Mono<Boolean> exists(String key){
        if(redisEnabled){
            return Mono.just(Boolean.FALSE);
        }
        return localCacheService.existsForKey(key);
    }

    public Mono<String> save(String key, String value){
        if(redisEnabled){
            return Mono.empty();
        }
        return localCacheService.save(key, value);
    }
}
