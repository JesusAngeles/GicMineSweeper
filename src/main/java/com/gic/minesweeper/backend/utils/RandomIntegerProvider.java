package com.gic.minesweeper.backend.utils;

public class RandomIntegerProvider {

    public static int getRandomNumber(int highestNumber) {
        return (int) (Math.random() * highestNumber * highestNumber) + 1;
    }

}
