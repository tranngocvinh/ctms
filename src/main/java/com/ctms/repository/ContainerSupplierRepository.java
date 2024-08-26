package com.ctms.repository;

import com.ctms.entity.ContainerSupplier;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
@Transactional
public interface ContainerSupplierRepository extends JpaRepository<ContainerSupplier,Integer> {


    boolean existsContainerSupplierByEmail(String email);

    //void updateContainerSupplier(Optional<ContainerSupplier> containerSupplier);
}
