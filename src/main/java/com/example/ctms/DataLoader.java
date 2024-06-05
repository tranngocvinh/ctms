package com.example.ctms;

import com.example.ctms.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ContainerService containerService;

    @Override
    public void run(String... args) throws Exception {
        containerService.initializeContainerTypes();
    }
}
