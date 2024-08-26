package com.ctms.repository;

import com.ctms.entity.ContainerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContainerTypeRepository extends JpaRepository<ContainerType, Integer> {
    List<ContainerType> findByNameAndType(String name, String type);
}