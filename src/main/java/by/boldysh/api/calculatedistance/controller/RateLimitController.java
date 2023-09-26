package by.boldysh.api.calculatedistance.controller;


import by.boldysh.api.calculatedistance.dto.RateLimitDto;
import by.boldysh.api.calculatedistance.service.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @Autowired
    private RateLimitService rateLimitService;

    @PostMapping(value = "api/save-limit")
    public ResponseEntity<?> saveRateLimit(@RequestBody RateLimitDto rateLimitDto) {
        rateLimitService.saveRateLimit(rateLimitDto);
        return ResponseEntity.ok(rateLimitDto);
    }

}
