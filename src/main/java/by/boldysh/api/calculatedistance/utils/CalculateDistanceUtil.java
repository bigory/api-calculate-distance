package by.boldysh.api.calculatedistance.utils;


import by.boldysh.api.calculatedistance.entity.Coordinates;

public class CalculateDistanceUtil {

    public static double calculateDistance(Coordinates coordinates) {
        if (coordinates.getStartLatitude() == coordinates.getFinishLatitude()
                && coordinates.getStartLongitude() == coordinates.getFinishLongitude()) {
            return 0.0;
        } else {
            final int radiusEarth = 6371;
            double latitudeDegree = deg2rad(coordinates.getFinishLatitude() - coordinates.getStartLatitude());
            double longitudeDegree = deg2rad(coordinates.getFinishLongitude() - coordinates.getStartLongitude());
            double factor =
                    Math.sin(latitudeDegree / 2) * Math.sin(latitudeDegree / 2) +
                            Math.cos(deg2rad(coordinates.getStartLatitude()))
                                    * Math.cos(deg2rad(coordinates.getFinishLatitude())) *
                                    Math.sin(longitudeDegree / 2) * Math.sin(longitudeDegree / 2);
            double angleRadians = 2 * Math.atan2(Math.sqrt(factor), Math.sqrt(1 - factor));
            double distance = radiusEarth * angleRadians;
            return round(distance);
        }
    }

    private static double deg2rad(double degree) {
        return degree * (Math.PI / 180);
    }

    private static double round(double distance) {
        return Math.round(distance * 1000.0) / 1000.0;
    }
}
