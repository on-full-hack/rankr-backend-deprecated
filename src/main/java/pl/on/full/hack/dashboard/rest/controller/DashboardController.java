package pl.on.full.hack.dashboard.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardController {

    @GetMapping(value = "/dashboard")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("Hello");
    }
}
