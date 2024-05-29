package com.rohini.batch.batchdemo.batch;

import com.rohini.batch.batchdemo.model.User;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public DataSource dataSource;

    @Bean
    public Job migrateUserJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("migrateUserJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      ItemReader<User> reader, ItemProcessor<User, User> processor, ItemWriter<User> writer) {
        return new StepBuilder("step1", jobRepository)
                .<User, User>chunk(100, transactionManager)  // Batch size of 100
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<User> reader() {
        return new JdbcCursorItemReaderBuilder<User>()
                .dataSource(dataSource)
                .name("userReader")
                .sql("SELECT id, name, email FROM source_user_table")  // Change to your source table name
                .rowMapper(new BeanPropertyRowMapper<>(User.class))
                .build();
    }

    @Bean
    public ItemProcessor<User, User> processor() {
        return user -> user;  // No processing required, just pass the user through
    }

    @Bean
    public ItemWriter<User> writer() {
        return new JdbcBatchItemWriterBuilder<User>()
                .dataSource(dataSource)
                .sql("INSERT INTO destination_user_table (id, name, email) VALUES (:id, :name, :email)")  // Change to your destination table name
                .beanMapped()
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }
}
