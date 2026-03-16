package com.gic.minesweeper.backend.utils;

public class LocationUtils {
    public static int getRowNumber(int squareNumber, int areaSize) {
        return (int) Math.round((squareNumber - 1) / areaSize) + 1;
    }

    public static int getColumnNumber(int squareNumber, int areaSize) {
        int rowNumber = LocationUtils.getRowNumber(squareNumber, areaSize);
        return squareNumber - ((rowNumber - 1) * areaSize);
    }

    public static Location getLocation(String input) {
        if (input == null || input.length() < 1 || input.length() > 2) {
            return null;
        }
        try {
            input = input.toUpperCase();
            int column = Integer.parseInt(input.substring(1, 2));
            int row = input.charAt(0) - 64;
            return new Location(row, column);
        } catch (Exception e) {
            return null;
        }
    }
}
