package by.boldysh.api.calculatedistance.utils;


import by.boldysh.api.calculatedistance.entity.Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CalculateDistanceUtilTest {

    @Test
    void calculateDistance() {
        double distanceMinskAmsterdam = 1530.854;
        Coordinates coordinates = new Coordinates(53.5359, 27.3400, 52.2226, 4.5322);
        double calculatedDistance = CalculateDistanceUtil.calculateDistance(coordinates);
        assertEquals(calculatedDistance, distanceMinskAmsterdam);
    }
}