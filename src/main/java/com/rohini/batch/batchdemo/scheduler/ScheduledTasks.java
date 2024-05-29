package com.rohini.batch.batchdemo.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("The time is now " + System.currentTimeMillis());
    }
}
