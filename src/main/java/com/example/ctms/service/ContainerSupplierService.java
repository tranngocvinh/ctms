package com.example.ctms.service;

import com.example.ctms.dto.ContainerUpdateRequest;
import com.example.ctms.entity.ContainerSupplier;
import com.example.ctms.repository.ContainerSupplierRepository;
import com.example.ctms.test.ImageUtils;
import com.example.ctms.test.file;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ContainerSupplierService {

    private ContainerSupplierRepository containerSupplierRepository;

    public ContainerSupplierService(ContainerSupplierRepository containerSupplierRepository) {
        this.containerSupplierRepository = containerSupplierRepository;
    }

    public void insert_supplier(ContainerSupplier supplier) throws IOException {
        containerSupplierRepository.save(supplier);

    }




    public byte[] getCustomerById(Integer id) {
        Optional<ContainerSupplier> dbImageData = containerSupplierRepository.findById(id);
        byte[] images= ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    public List<ContainerSupplier> getAllSupplers() {
        return containerSupplierRepository.findAll() ;
    }
    @Transactional
    public void updateSupplier(Integer id, ContainerUpdateRequest updateRequest, MultipartFile image) throws IOException {
        Optional<ContainerSupplier> containerSupplier = Optional.of(containerSupplierRepository.getReferenceById(id));

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(containerSupplier.get().getName())) {
            containerSupplier.get().setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.address() != null && !updateRequest.address().equals(containerSupplier.get().getAddress())) {
            containerSupplier.get().setAddress(updateRequest.address());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(containerSupplier.get().getEmail())) {

            containerSupplier.get().setEmail(updateRequest.email());
            changes = true;
        }

        if (updateRequest.phoneNumber() != null && !updateRequest.phoneNumber().equals(containerSupplier.get().getPhoneNumber())) {
            containerSupplier.get().setPhoneNumber(updateRequest.phoneNumber());
            changes = true;
        }

        if (updateRequest.detailService() != null && !updateRequest.detailService().equals(containerSupplier.get().getDetailService())) {
            containerSupplier.get().setDetailService(updateRequest.detailService());
            changes = true;
        }

        if (updateRequest.website() != null && !updateRequest.website().equals(containerSupplier.get().getWebsite())) {
            containerSupplier.get().setWebsite(updateRequest.website());
            changes = true;
        }

        if (image != null && !image.isEmpty()) {
            byte[] imageData = ImageUtils.compressImage(image.getBytes());
            containerSupplier.get().setImageData(imageData);
            changes = true;
        }


        if (!changes) {
            System.out.println("false");
        }

        //containerSupplierRepository.updateContainerSupplier(containerSupplier);
    }

    public void deleteSupplierById(Integer id) {
        containerSupplierRepository.deleteById(id);
    }
}
