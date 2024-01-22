package com.practice.kafka.repository;

import com.practice.kafka.entity.Practice;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@Transactional
public interface PracticeRepository extends JpaRepository<Practice, UUID> {
}
