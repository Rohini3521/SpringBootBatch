package com.rohini.batch.batchdemo.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BatchJobRunner implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job migrateUserJob;

    @Override
    public void run(String... args) throws Exception {
        jobLauncher.run(migrateUserJob, new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters());
    }
}
