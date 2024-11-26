package com.example.demo.config;

import com.example.demo.domain.MarsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitData {

    private final MarsService marsService;

    @EventListener(ApplicationReadyEvent.class)
    void initializeDatabaseData(){
        marsService.initializeData();
    }
}
