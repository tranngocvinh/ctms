package com.ctms.repository;

import com.ctms.entity.Cleaning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerCleaningRepository extends JpaRepository<Cleaning, Long>
{
}
