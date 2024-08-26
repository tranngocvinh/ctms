package com.ctms.repository;

import com.ctms.entity.EmptyContainerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmptyContainerDetailRepository extends JpaRepository<EmptyContainerDetail, Integer> {

    List<EmptyContainerDetail> findEmptyContainerDetailByEmptyContainerId(Integer emptyContainer_id);
}
