package by.boldysh.api.calculatedistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class Coordinates  {

    private double startLatitude;
    private double startLongitude;
    private double finishLatitude;
    private double finishLongitude;

}
