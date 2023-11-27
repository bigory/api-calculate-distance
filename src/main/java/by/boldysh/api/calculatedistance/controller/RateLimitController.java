package by.boldysh.api.calculatedistance.controller;


import by.boldysh.api.calculatedistance.dto.RateLimitDto;
import by.boldysh.api.calculatedistance.service.RateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    private final RateLimitService rateLimitService;

    @Autowired
    public RateLimitController(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @PostMapping(value = "api/save-limit")
    public ResponseEntity<?> saveRateLimit(@RequestBody RateLimitDto rateLimitDto) {
        rateLimitService.saveRateLimit(rateLimitDto);
        return ResponseEntity.ok(rateLimitDto);
    }


    @PostMapping(value = "api/delete-limit")
    public ResponseEntity<?> deleteRateLimit(@RequestBody RateLimitDto rateLimitDto) {
        rateLimitService.deleteRateLimit(rateLimitDto);
        return ResponseEntity.ok(rateLimitDto);
    }

    @PostMapping(value = "api/update-limit")
    public ResponseEntity<?> updateRateLimit(@RequestParam("id") Long id, @RequestBody RateLimitDto rateLimitDto) {
        rateLimitService.updateRateLimit(id, rateLimitDto);
        return ResponseEntity.ok(rateLimitDto);
    }

}
