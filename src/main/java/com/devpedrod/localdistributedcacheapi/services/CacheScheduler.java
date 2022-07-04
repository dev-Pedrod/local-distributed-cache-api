package com.devpedrod.localdistributedcacheapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheScheduler {

    @Autowired
    private LocalCacheService localCacheService;

    /*
        Este metódo irá rodar a cada minuto (de acordo com a minha configuração "app-config.cache.scheduler")
        verificando se há chaves expiradas, e se houver, irá remove-las.
        veja: https://cloud.google.com/scheduler/docs/configuring/cron-job-schedules
    */
    @Scheduled(cron = "${app-config.cache.scheduler}")
    public void removeExpiredKeys(){
        localCacheService.removeExpiredKeys();
    }
}
