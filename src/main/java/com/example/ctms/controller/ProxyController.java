package com.example.ctms.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/proxy")
public class ProxyController {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String SEA_ROUTE_API_URL = "https://searoute.captv.ovh/waypoints";

    @PostMapping("/waypoints")
    public ResponseEntity<String> proxyWaypoints(@RequestBody String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        // Add any other headers you need

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(SEA_ROUTE_API_URL, entity, String.class);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
}
