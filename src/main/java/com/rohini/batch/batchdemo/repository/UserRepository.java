package com.rohini.batch.batchdemo.repository;

import com.rohini.batch.batchdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
