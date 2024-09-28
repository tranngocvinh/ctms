package com.ctms.repository;

import com.ctms.entity.Container;
import com.ctms.entity.EmptyContainerDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmptyContainerDetailRepository extends JpaRepository<EmptyContainerDetail, Integer> {

    List<EmptyContainerDetail> findEmptyContainerDetailByEmptyContainerId(Integer emptyContainer_id);
    Optional<EmptyContainerDetail> findEmptyContainerDetailByContainer_ContainerCode(String container_code);



}
