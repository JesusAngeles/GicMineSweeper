package com.gic.minesweeper.frontend;

import com.gic.minesweeper.backend.service.MineTracker;
import com.gic.minesweeper.backend.service.Square;

public class MineTrackerDisplayer {
    private static int displaySquareSpacesOccupied = 2;
    private static String displaySpaceBetweenSquares = " ";

    public static void display(MineTracker mineTracker) {

        System.out.println("Here is your current minefield:");
        System.out.println(getDisplayHeader(mineTracker.getSize()));

        for (int row = 1; row <= mineTracker.getSize(); row++) {
            System.out.print(getPadded(getRowLabel(row)) + displaySpaceBetweenSquares);
            for (int column = 1; column <= mineTracker.getSize(); column++) {
                System.out.print(getSquareForDisplay(mineTracker.getSquares().get(row - 1).get(column - 1)));
            }
            System.out.println("");
        }
    }

    public static void displaySolutionAdjacentMines(MineTracker mineTracker) {

        System.out.println("Here is the actual minefield with number of adjacent squares with mines:");
        System.out.println(getDisplayHeader(mineTracker.getSize()));

        for (int row = 1; row <= mineTracker.getSize(); row++) {
            System.out.print(getPadded(getRowLabel(row)) + displaySpaceBetweenSquares);
            for (int column = 1; column <= mineTracker.getSize(); column++) {
                System.out.print(getPadded(Integer.toString(mineTracker.getSquares().get(row - 1).get(column - 1).getNumberOfAdjacentMines())) + displaySpaceBetweenSquares);
            }
            System.out.println("");
        }
    }

    public static void displaySolutionSquaresWithMines(MineTracker mineTracker) {

        System.out.println("Here is the actual minefield where x indicates that the square has a mine:");
        System.out.println(getDisplayHeader(mineTracker.getSize()));

        for (int row = 1; row <= mineTracker.getSize(); row++) {
            System.out.print(getPadded(getRowLabel(row)) + displaySpaceBetweenSquares);
            for (int column = 1; column <= mineTracker.getSize(); column++) {
                System.out.print(getPadded(mineTracker.getSquares().get(row - 1).get(column - 1).isHavingMine() ? "x" : "_") + displaySpaceBetweenSquares);
            }
            System.out.println("");
        }
    }

    private static String getSquareForDisplay(Square square) {
        if (!square.isOpened()) {
            return getPadded("_") + displaySpaceBetweenSquares;
        }
        return getPadded(Integer.toString(square.getNumberOfAdjacentMines())) + displaySpaceBetweenSquares;
    }

    private static String getRowLabel(int row) {
        return String.valueOf((char) (row + 64));
    }

    private static String getDisplayHeader(int size) {
        String header = getPadded("") + displaySpaceBetweenSquares;
        for (int column = 1; column <= size; column++) {
            header = header + getPadded(Integer.toString(column)) + " ";
        }
        return header;
    }

    private static String getPadded(String item) {
        return String.format("%-" + displaySquareSpacesOccupied + "s", item);
    }
}
