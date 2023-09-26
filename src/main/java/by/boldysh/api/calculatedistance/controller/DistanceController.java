package by.boldysh.api.calculatedistance.controller;


import by.boldysh.api.calculatedistance.entity.Coordinates;
import by.boldysh.api.calculatedistance.service.RateLimitService;
import by.boldysh.api.calculatedistance.utils.CalculateDistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DistanceController {

    @Autowired
    private RateLimitService rateLimitService;

    @GetMapping( "api/calculate-distance")
    public ResponseEntity<?> getCalculateDistance(@RequestParam("id") Long id, @RequestBody Coordinates coordinates) {
        boolean rateLimited = rateLimitService.isRateLimited(id);
        if (rateLimited) {
            return ResponseEntity
                    .status(HttpStatus.TOO_MANY_REQUESTS)
                    .build();
        }
        return ResponseEntity.ok(CalculateDistanceUtil.calculateDistance(coordinates));
    }

}
