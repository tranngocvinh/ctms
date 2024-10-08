package com.ctms.repository;

import com.ctms.entity.ContainerSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContainerSizeRepository extends JpaRepository<ContainerSize, Integer> {
    List<ContainerSize> findByContainerTypeId(Integer containerTypeId);
}
