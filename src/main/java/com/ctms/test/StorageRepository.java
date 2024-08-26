package com.ctms.test;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface StorageRepository extends JpaRepository<file, Long> {


    Optional<file> findByName(String fileName);
}
