package com.ctms.controller;

import com.ctms.dto.ContainerUpdateRequest;
import com.ctms.entity.ContainerSupplier;
import com.ctms.service.ContainerSupplierService;
import com.ctms.test.ImageUtils;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@EnableMBeanExport
@RestController
@RequestMapping("/api/v1/supplier")
public class ContainerSupplierController {

    private ContainerSupplierService containerSupplierService ;

    public ContainerSupplierController(ContainerSupplierService containerSupplierService) {
        this.containerSupplierService = containerSupplierService;
    }

    @PostMapping
    public void insert_supplier( @RequestParam("name") String name,
                                 @RequestParam("address") String address,
                                 @RequestParam("phoneNumber") String phoneNumber,
                                 @RequestParam("email") String email,
                                 @RequestParam("website") String website,
                                 @RequestParam("detailService") String detailService,
                                 @RequestParam("image") MultipartFile image) throws IOException {

        // Create and save supplier
        ContainerSupplier supplier = new ContainerSupplier();
        supplier.setName(name);
        supplier.setAddress(address);
        supplier.setPhoneNumber(phoneNumber);
        supplier.setEmail(email);
        supplier.setWebsite(website);
        supplier.setDetailService(detailService);

        // Handle image upload
        supplier.setImageData(ImageUtils.compressImage(image.getBytes()));

        containerSupplierService.insert_supplier(supplier);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Integer id) {
       byte[] imageData = containerSupplierService.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @GetMapping
    public List<ContainerSupplier> getAllSuppliers() {
        return containerSupplierService.getAllSupplers() ;
    }

    @PutMapping("/{supplierId}")
    public void updateSupplier(
            @PathVariable("supplierId") Integer id,
            @RequestPart("data") ContainerUpdateRequest updateRequest,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        containerSupplierService.updateSupplier(id, updateRequest, image);

    }

    @DeleteMapping("{supplierId}")
    public void deleteSupplier(
            @PathVariable("supplierId") Integer id) {
        containerSupplierService.deleteSupplierById(id);
    }


}
