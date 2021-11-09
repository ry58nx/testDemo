package com.test.test.repository;


import com.test.test.dto.EventLogsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLogsRepository extends CrudRepository<EventLogsEntity, String> {
}
