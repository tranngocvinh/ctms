package com.example.ctms.repository;

import com.example.ctms.entity.EmptyContainerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmptyContainerDetailRepository extends JpaRepository<EmptyContainerDetail, Integer> {

    List<EmptyContainerDetail> findEmptyContainerDetailByEmptyContainerId(Integer emptyContainer_id);
}
