package com.gic.minesweeper.backend.utils;

public class MineTrackerUtil {

    private static final int allowedMaxPercentageOfMinesInArea = 35;

    public static int getMaximumNumberOfMines(int size) {
        return Math.round(size * size * allowedMaxPercentageOfMinesInArea / 100);
    }
}
