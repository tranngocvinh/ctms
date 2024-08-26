package com.ctms.service;

import com.ctms.entity.PortLocation;
import com.ctms.repository.PortLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortLocationService {

    private final PortLocationRepository portLocationRepository;

    @Autowired
    public PortLocationService(PortLocationRepository portLocationRepository) {
        this.portLocationRepository = portLocationRepository;
    }
    public List<PortLocation> getAllPorts() {
        return portLocationRepository.findAll();
    }

    public List<PortLocation> searchPortsByName(String portName) {
        return portLocationRepository.findByPortNameContainingIgnoreCase(portName);
    }
}
