/**
 * ===============================================================
 * File name : PersonRepository.java
 * Created by injeahwang on 2021-04-10
 * ===============================================================
 */
package com.example.kafkamultichannelpractice.repository;

import com.example.kafkamultichannelpractice.entity.PersonHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonHistoryRepository extends JpaRepository<PersonHistory, Long> {
}
