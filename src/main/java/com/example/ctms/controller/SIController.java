package com.example.ctms.controller;

import com.example.ctms.dto.SIDTO;
import com.example.ctms.entity.SI;
import com.example.ctms.service.SIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/si")
public class SIController {

    @Autowired
    private SIService siService;

    @GetMapping
    public List<SIDTO> getAllSIs() {
        return siService.getAllSIs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SIDTO> getSIById(@PathVariable Integer id) {
        Optional<SIDTO> si = siService.getSIById(id);
        return si.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createSI(@RequestBody SIDTO siDTO) {
        SI createdSI = siService.createSI(siDTO);
        return ResponseEntity.ok(createdSI);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SI> updateSI(@PathVariable Integer id, @RequestBody SIDTO siDTO) {
        SI updatedSI = siService.updateSI(id, siDTO);
        return ResponseEntity.ok(updatedSI);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSI(@PathVariable Integer id) {
        siService.deleteSI(id);
        return ResponseEntity.noContent().build();
    }
}
