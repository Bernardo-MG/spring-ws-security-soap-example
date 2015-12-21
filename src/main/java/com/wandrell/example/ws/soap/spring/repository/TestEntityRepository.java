package com.wandrell.example.ws.soap.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wandrell.example.ws.soap.spring.model.JPATestEntity;

/**
 * The Interface TestEntityRepository.
 */
public interface TestEntityRepository extends
        JpaRepository<JPATestEntity, Integer> {

}
